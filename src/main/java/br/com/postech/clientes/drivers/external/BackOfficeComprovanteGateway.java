package br.com.postech.clientes.drivers.external;

import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.core.entities.Cliente;

public interface BackOfficeComprovanteGateway {
    void salvarComprovante(BackOfficeOperacao operacao, Cliente cliente);
}
