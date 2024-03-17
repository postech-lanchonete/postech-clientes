package br.com.postech.clientes.adapters.controllers;

import br.com.postech.clientes.adapters.presenters.BackOfficePresenter;
import br.com.postech.clientes.adapters.dto.CriacaoOperacaoBackOfficeDTO;
import br.com.postech.clientes.adapters.dto.OperacaoBackOfficeDTO;
import br.com.postech.clientes.business.usecases.UseCase;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.drivers.web.BackOfficeAPI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BackOfficeController implements BackOfficeAPI {
    private final UseCase.SemEntrada<List<BackOfficeOperacao>> backOfficeBuscarOperacoesUseCase;
    private final UseCase<CriacaoOperacaoBackOfficeDTO, BackOfficeOperacao> backOfficeRealizarOperacaoUseCase;
    private final BackOfficePresenter backOfficePresenter;

    public BackOfficeController(@Qualifier("backOfficeBuscarOperacoesUseCase") UseCase.SemEntrada<List<BackOfficeOperacao>> backOfficeBuscarOperacoesUseCase,
                                @Qualifier("backOfficeRealizarOperacaoUseCase") UseCase<CriacaoOperacaoBackOfficeDTO, BackOfficeOperacao> backOfficeRealizarOperacaoUseCase,
                                BackOfficePresenter backOfficePresenter) {
        this.backOfficeBuscarOperacoesUseCase = backOfficeBuscarOperacoesUseCase;
        this.backOfficeRealizarOperacaoUseCase = backOfficeRealizarOperacaoUseCase;
        this.backOfficePresenter = backOfficePresenter;
    }

    @Override
    @PostMapping
    public OperacaoBackOfficeDTO criarOperacao(@Valid @RequestBody CriacaoOperacaoBackOfficeDTO operacao) {
        operacao.isValidInput();
        BackOfficeOperacao backOfficeOperacao = backOfficeRealizarOperacaoUseCase.realizar(operacao);
        return backOfficePresenter.toDto(backOfficeOperacao);
    }

    @Override
    @GetMapping
    public List<OperacaoBackOfficeDTO> buscarOperacoes() {
        return backOfficeBuscarOperacoesUseCase.realizar().stream()
                .map(backOfficePresenter::toDto)
                .toList();
    }
}
