package br.com.postech.clientes.business.usecases.implementation;

import br.com.postech.clientes.adapters.presenters.BackOfficePresenter;
import br.com.postech.clientes.adapters.dto.CriacaoOperacaoBackOfficeDTO;
import br.com.postech.clientes.drivers.external.BackOfficeComprovanteGateway;
import br.com.postech.clientes.drivers.external.BackOfficeGateway;
import br.com.postech.clientes.drivers.external.ClienteGateway;
import br.com.postech.clientes.business.exceptions.NotFoundException;
import br.com.postech.clientes.business.usecases.UseCase;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.core.entities.Cliente;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component("backOfficeRealizarOperacaoUseCase")
public class BackOfficeRealizarOperacaoUseCase implements UseCase<CriacaoOperacaoBackOfficeDTO, BackOfficeOperacao> {

    private final BackOfficeGateway backOfficeGateway;
    private final BackOfficeComprovanteGateway backOfficeComprovanteGateway;
    private final ClienteGateway clienteGateway;

    private final BackOfficePresenter backOfficePresenter;

    public BackOfficeRealizarOperacaoUseCase(BackOfficeGateway backOfficeGateway,
                                             BackOfficeComprovanteGateway backOfficeComprovanteGateway,
                                             ClienteGateway clienteGateway, BackOfficePresenter backOfficePresenter) {
        this.backOfficeGateway = backOfficeGateway;
        this.backOfficeComprovanteGateway = backOfficeComprovanteGateway;
        this.clienteGateway = clienteGateway;
        this.backOfficePresenter = backOfficePresenter;
    }
    @Override
    @Transactional
    public BackOfficeOperacao realizar(CriacaoOperacaoBackOfficeDTO operacaoBackOfficeDTO) {
        BackOfficeOperacao operacao;
        var cliente = buscarCliente(operacaoBackOfficeDTO);
        switch (operacaoBackOfficeDTO.getOperacao()) {
            case ANONIMIZACAO:
                operacao = realizarAnomizacao(cliente, operacaoBackOfficeDTO);
                break;
            case EXCLUSAO:
                operacao = realizarExclusao(cliente, operacaoBackOfficeDTO);
                break;
            case INATIVACAO:
                operacao = realizarInativacao(cliente, operacaoBackOfficeDTO);
                break;
            default:
                throw new NotFoundException("Operação não encontrada");
        }
        backOfficeComprovanteGateway.salvarComprovante(operacao, cliente);
        return this.backOfficeGateway.salvar(operacao);
    }

    private BackOfficeOperacao realizarInativacao(Cliente cliente,CriacaoOperacaoBackOfficeDTO operacao) {
        cliente.setAtivo(false);
        clienteGateway.salvar(cliente);
        return backOfficePresenter.toEntity(operacao, cliente.getId());
    }


    private BackOfficeOperacao realizarExclusao(Cliente cliente,CriacaoOperacaoBackOfficeDTO operacao) {
        clienteGateway.delete(cliente.getId());
        return backOfficePresenter.toEntity(operacao, cliente.getId());
    }

    private BackOfficeOperacao realizarAnomizacao(Cliente cliente, CriacaoOperacaoBackOfficeDTO operacao) {
        cliente.setNome(UUID.randomUUID().toString());
        cliente.setSobrenome(UUID.randomUUID().toString());
        cliente.setCpf(UUID.randomUUID().toString());
        clienteGateway.salvar(cliente);
        return backOfficePresenter.toEntity(operacao, cliente.getId());
    }

    private Cliente buscarCliente(CriacaoOperacaoBackOfficeDTO operacao) {
        Cliente cliente = new Cliente();
        cliente.setAtivo(true);
        cliente.setCpf(operacao.getCliente().getCpf());
        cliente.setId(operacao.getCliente().getId());
        cliente.setEmail(operacao.getCliente().getEmail());

        return clienteGateway.buscarPor(Example.of(cliente))
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
    }
}
