package api.desafio.service;

import api.desafio.model.Cliente;

import java.util.List;
import java.util.UUID;

public interface ClienteService {
    Cliente criarCliente(Cliente cliente);
    Cliente editarCliente(UUID idCliente, Cliente clienteAtualizado);
    Cliente exibirCliente(UUID idCliente);
    List<Cliente> listarClientes();
    void excluirTelefone(UUID idTelefone);
    void excluirEmail(UUID idEmail);
    void deletarCliente(UUID idCliente);
}
