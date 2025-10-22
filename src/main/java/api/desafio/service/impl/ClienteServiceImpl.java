package api.desafio.service.impl;

import api.desafio.model.Cliente;
import api.desafio.model.Contato;
import api.desafio.model.Email;
import api.desafio.model.Telefone;
import api.desafio.repository.ClienteRepository;
import api.desafio.repository.ContatoRepository;
import api.desafio.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ContatoRepository contatoRepository;

    @Override
    public Cliente criarCliente(Cliente cliente) {
        Contato contato = cliente.getContato();
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
}
