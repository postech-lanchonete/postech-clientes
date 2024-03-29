package br.com.postech.clientes.adapters.gateways;

import br.com.postech.clientes.drivers.external.ClienteGateway;
import br.com.postech.clientes.adapters.repositories.ClienteRepository;
import br.com.postech.clientes.core.entities.Cliente;
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

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}
