package br.com.postech.clientes.adapters.gateways;

import br.com.postech.clientes.core.entities.Cliente;

public interface ClienteGateway extends Gateway<Cliente> {

    void delete(Long id);

}
