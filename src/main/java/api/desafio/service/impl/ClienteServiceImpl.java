package api.desafio.service.impl;

import api.desafio.model.Cliente;
import api.desafio.model.Contato;
import api.desafio.model.Email;
import api.desafio.model.Telefone;
import api.desafio.repository.ClienteRepository;
import api.desafio.repository.ContatoRepository;
import api.desafio.repository.EmailRepository;
import api.desafio.repository.TelefoneRepository;
import api.desafio.service.ClienteService;
import api.desafio.service.ContatoService;
import api.desafio.service.exception.EntityNotfoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;


@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ContatoService contatoService;
    @Autowired
    private TelefoneRepository telefoneRepository;
    @Autowired
    private EmailRepository emailRepository;

    @Override
    public Cliente criarCliente(Cliente cliente) {
        Contato contato = cliente.getContato();
        if (clienteRepository.existsClienteByNomeCompleto(cliente.getNomeCompleto())) {
            throw new EntityNotfoundException(String.format("Cliente %s já existe.",  cliente.getNomeCompleto()));
        }
        if (contato != null) {
            List<Email> emails = contato.getEmails();
            if (emails != null) {
                emails.forEach(email -> email.setContato(contato));
            }

            List<Telefone> telefones = contato.getTelefones();
            if (telefones != null) {
                telefones.forEach(tel -> tel.setContato(contato));
            }
        }

        return clienteRepository.save(cliente);
    }


    @Override
    @Transactional
    public Cliente editarCliente(UUID idCliente, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + idCliente));

        clienteExistente.setNomeCompleto(clienteAtualizado.getNomeCompleto());

        Contato contatoNovo = clienteAtualizado.getContato();
        Contato contatoExistente = clienteExistente.getContato();

        if (contatoNovo != null) {
            if (contatoExistente == null) {
                contatoExistente = new Contato();
                clienteExistente.setContato(contatoExistente);
            }

            contatoService.sincronizarEmails(contatoExistente, contatoNovo);

            contatoService.sincronizarTelefones(contatoExistente, contatoNovo);
        }

        return clienteRepository.save(clienteExistente);
    }

    @Override
    public Cliente exibirCliente(UUID idCliente) {
        return clienteRepository.findById(idCliente).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void excluirTelefone(UUID idTelefone) {
        telefoneRepository.deleteById(idTelefone);
    }

    @Override
    public void excluirEmail(UUID idEmail) {
        emailRepository.deleteById(idEmail);
    }


}
