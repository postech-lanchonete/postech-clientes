package br.com.postech.clientes.adapters.gateways.implementation;

import br.com.postech.clientes.adapters.gateways.BackOfficeGateway;
import br.com.postech.clientes.adapters.gateways.ClienteGateway;
import br.com.postech.clientes.adapters.repositories.BackOfficeRepository;
import br.com.postech.clientes.adapters.repositories.ClienteRepository;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.core.entities.Cliente;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BackOfficeGatewayImpl implements BackOfficeGateway {
    private final BackOfficeRepository backOfficeRepository;

    public BackOfficeGatewayImpl(BackOfficeRepository backOfficeRepository) {
        this.backOfficeRepository = backOfficeRepository;
    }

    @Override
    public BackOfficeOperacao salvar(BackOfficeOperacao cliente) {
        return backOfficeRepository.save(cliente);
    }

    @Override
    public List<BackOfficeOperacao> buscarPor(Example<BackOfficeOperacao> clienteExample) {
        return backOfficeRepository.findAll(clienteExample);
    }
}
