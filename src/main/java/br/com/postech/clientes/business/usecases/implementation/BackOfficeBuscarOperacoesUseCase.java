package br.com.postech.clientes.business.usecases.implementation;

import br.com.postech.clientes.adapters.gateways.BackOfficeGateway;
import br.com.postech.clientes.business.usecases.UseCase;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("backOfficeBuscarOperacoesUseCase")
public class BackOfficeBuscarOperacoesUseCase implements UseCase.SemEntrada<List<BackOfficeOperacao>> {

    private final BackOfficeGateway clienteGateway;

    public BackOfficeBuscarOperacoesUseCase(BackOfficeGateway backOfficeGateway) {
        this.clienteGateway = backOfficeGateway;
    }
    @Override
    public List<BackOfficeOperacao> realizar() {
        return clienteGateway.buscarPor(Example.of(new BackOfficeOperacao()));
    }

}
