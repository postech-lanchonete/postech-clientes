package br.com.postech.clientes.adapters.gateways;

import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.core.entities.Cliente;

public interface BackOfficeComprovanteGateway {
    void salvarComprovante(BackOfficeOperacao operacao, Cliente cliente);
}
