package br.com.postech.clientes.business.usecases.implementation;

import br.com.postech.clientes.drivers.external.ClienteGateway;
import br.com.postech.clientes.business.usecases.UseCase;
import br.com.postech.clientes.core.entities.Cliente;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component("clienteCriarUseCase")
public class ClienteCriarUseCase implements UseCase<Cliente, Cliente> {

    private final ClienteGateway clienteGateway;

    public ClienteCriarUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }
    @Override
    public Cliente realizar(Cliente cliente) {
        this.procurarPorDuplicidade(cliente);
        this.procurarPorEmail(cliente);
        return clienteGateway.salvar(cliente);
    }

    private void procurarPorDuplicidade(Cliente cliente) {
        Cliente clienteExample = new Cliente();
        clienteExample.setNome(cliente.getNome());
        clienteExample.setSobrenome(cliente.getSobrenome());
        clienteExample.setCpf(cliente.getCpf());
        clienteGateway.buscarPor(Example.of(clienteExample))
                .stream()
                .findFirst()
                .ifPresent(c -> {
                    throw new DataIntegrityViolationException("Não é permitido inserir dois clientes com o mesmo nome, sobrenome e CPF.");
                });
    }

    private void procurarPorEmail(Cliente cliente) {
        Cliente clienteExample = new Cliente();
        clienteExample.setEmail(cliente.getEmail());
        clienteGateway.buscarPor(Example.of(clienteExample))
                .stream()
                .findFirst()
                .ifPresent(c -> {
                    throw new DataIntegrityViolationException("E-mail já utilizado. Utilize outro e-mail.");
                });
    }
}
