package br.com.lanchonete.clientes.adapters.gateways.implementation;

import br.com.lanchonete.clientes.adapters.gateways.ClienteGateway;
import br.com.lanchonete.clientes.adapters.repositories.ClienteRepository;
import br.com.lanchonete.clientes.core.entities.Cliente;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteGatewayImpl implements ClienteGateway {
    private final ClienteRepository clienteRepository;

    public ClienteGatewayImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> buscarPor(Example<Cliente> clienteExample) {
        return clienteRepository.findAll(clienteExample);
    }
}
