package api.desafio.service.impl;

import api.desafio.model.Contato;
import api.desafio.model.Email;
import api.desafio.model.Telefone;
import api.desafio.service.ContatoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoServiceImpl implements ContatoService {
    @Override
    public void sincronizarTelefones(Contato contatoExistente, Contato contatoNovo) {
        List<Telefone> telefonesAtuais = contatoExistente.getTelefones();
        List<Telefone> telefonesNovos = contatoNovo.getTelefones();

        telefonesAtuais.removeIf(tAntigo ->
                telefonesNovos.stream()
                        .noneMatch(tNovo -> tNovo.getIdTelefone() != null && tNovo.getIdTelefone().equals(tAntigo.getIdTelefone()))
        );

        for (Telefone novo : telefonesNovos) {
            if (novo.getIdTelefone() != null) {
                telefonesAtuais.stream()
                        .filter(t -> t.getIdTelefone().equals(novo.getIdTelefone()))
                        .findFirst()
                        .ifPresentOrElse(
                                existente -> {
                                    existente.setNumero(novo.getNumero());
                                    // mantém o mesmo contato
                                    existente.setContato(contatoExistente);
                                },
                                () -> {
                                    novo.setContato(contatoExistente);
                                    telefonesAtuais.add(novo);
                                }
                        );
            } else {
                novo.setContato(contatoExistente);
                telefonesAtuais.add(novo);
            }
        }
    }

    @Override
    public void sincronizarEmails(Contato contatoExistente, Contato contatoNovo) {
        List<Email> emailsAtuais = contatoExistente.getEmails();
        List<Email> emailsNovos = contatoNovo.getEmails();

        emailsAtuais.removeIf(emailAntigo ->
                emailsNovos.stream().noneMatch(e -> e.getIdEmail() != null && e.getIdEmail().equals(emailAntigo.getIdEmail()))
        );

        for (Email novo : emailsNovos) {
            if (novo.getIdEmail() != null) {
                emailsAtuais.stream()
                        .filter(e -> e.getIdEmail().equals(novo.getIdEmail()))
                        .findFirst()
                        .ifPresentOrElse(
                                existente -> existente.setEmail(novo.getEmail()),
                                () -> { // se não existir, adiciona
                                    novo.setContato(contatoExistente);
                                    emailsAtuais.add(novo);
                                });
            } else {
                novo.setContato(contatoExistente);
                emailsAtuais.add(novo);
            }
        }
    }
}
