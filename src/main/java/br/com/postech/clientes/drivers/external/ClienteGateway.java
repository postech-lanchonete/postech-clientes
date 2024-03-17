package br.com.postech.clientes.drivers.external;

import br.com.postech.clientes.core.entities.Cliente;

public interface ClienteGateway extends Gateway<Cliente> {

    void delete(Long id);

}
