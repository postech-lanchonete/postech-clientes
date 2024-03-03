package br.com.postech.clientes.adapters.controllers;

import br.com.postech.clientes.adapters.adapter.BackOfficeAdapter;
import br.com.postech.clientes.adapters.adapter.ClienteAdapter;
import br.com.postech.clientes.adapters.dto.ClienteDTO;
import br.com.postech.clientes.adapters.dto.CriacaoClienteDTO;
import br.com.postech.clientes.adapters.dto.CriacaoOperacaoBackOfficeDTO;
import br.com.postech.clientes.adapters.dto.OperacaoBackOfficeDTO;
import br.com.postech.clientes.business.usecases.UseCase;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.core.entities.Cliente;
import br.com.postech.clientes.drivers.web.BackOfficeAPI;
import br.com.postech.clientes.drivers.web.ClienteAPI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BackOfficeController implements BackOfficeAPI {
    private final UseCase.SemEntrada<List<BackOfficeOperacao>> backOfficeBuscarOperacoesUseCase;
    private final UseCase<CriacaoOperacaoBackOfficeDTO, BackOfficeOperacao> backOfficeRealizarOperacaoUseCase;
    private final BackOfficeAdapter backOfficeAdapter;

    public BackOfficeController(@Qualifier("backOfficeBuscarOperacoesUseCase") UseCase.SemEntrada<List<BackOfficeOperacao>> backOfficeBuscarOperacoesUseCase,
                                @Qualifier("backOfficeRealizarOperacaoUseCase") UseCase<CriacaoOperacaoBackOfficeDTO, BackOfficeOperacao> backOfficeRealizarOperacaoUseCase,
                                BackOfficeAdapter backOfficeAdapter) {
        this.backOfficeBuscarOperacoesUseCase = backOfficeBuscarOperacoesUseCase;
        this.backOfficeRealizarOperacaoUseCase = backOfficeRealizarOperacaoUseCase;
        this.backOfficeAdapter = backOfficeAdapter;
    }

    @Override
    @PostMapping
    public OperacaoBackOfficeDTO criarOperacao(@Valid @RequestBody CriacaoOperacaoBackOfficeDTO operacao) {
        operacao.isValidInput();
        BackOfficeOperacao backOfficeOperacao = backOfficeRealizarOperacaoUseCase.realizar(operacao);
        return backOfficeAdapter.toDto(backOfficeOperacao);
    }

    @Override
    @GetMapping
    public List<OperacaoBackOfficeDTO> buscarOperacoes() {
        return backOfficeBuscarOperacoesUseCase.realizar().stream()
                .map(backOfficeAdapter::toDto)
                .toList();
    }
}
