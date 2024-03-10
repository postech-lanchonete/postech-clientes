package br.com.postech.clientes.business.usecases.implementation;

import br.com.postech.clientes.adapters.gateways.ClienteGateway;
import br.com.postech.clientes.business.exceptions.NotFoundException;
import br.com.postech.clientes.business.usecases.UseCase;
import br.com.postech.clientes.core.entities.Cliente;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component("clienteBuscarPorIdUseCase")
public class ClienteBuscarPorIdUseCase implements UseCase<Cliente, Cliente> {

    private final ClienteGateway clienteGateway;

    public ClienteBuscarPorIdUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }
    @Override
    public Cliente realizar(Cliente cliente) {
        return clienteGateway.buscarPor(Example.of(cliente))
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }

}
