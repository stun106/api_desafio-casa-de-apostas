package api.desafio.web.controller;

import api.desafio.model.Cliente;
import api.desafio.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente) {
        return clienteService.criarCliente(cliente);
    }

    @PutMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente update(@PathVariable UUID idCliente, @RequestBody Cliente cliente) {
        return clienteService.editarCliente(idCliente, cliente);
    }

    @GetMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente findById(@PathVariable UUID idCliente) {
        return clienteService.exibirCliente(idCliente);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> findAll() {
        return clienteService.listarClientes();
    }

    @DeleteMapping("{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID idCliente) {
        clienteService.deletarCliente(idCliente);
    }

    @DeleteMapping("/excluirTelefone/{idTelefone}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTelefone(@PathVariable UUID idTelefone) {
        clienteService.excluirTelefone(idTelefone);
    }

    @DeleteMapping("/excluirEmail/{idEmail}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmail(@PathVariable UUID idEmail) {
        clienteService.excluirEmail(idEmail);
    }
}
