package api.desafio.service;

import api.desafio.model.Contato;

public interface ContatoService {
    void sincronizarTelefones(Contato contatoExistente, Contato contatoNovo);
    void sincronizarEmails(Contato contatoExistente, Contato contatoNovo);
}
