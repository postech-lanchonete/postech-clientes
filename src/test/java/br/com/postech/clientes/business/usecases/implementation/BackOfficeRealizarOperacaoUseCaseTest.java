package br.com.postech.clientes.business.usecases.implementation;

import br.com.postech.clientes.adapters.presenters.BackOfficePresenter;
import br.com.postech.clientes.adapters.dto.ClienteDTO;
import br.com.postech.clientes.adapters.dto.CriacaoOperacaoBackOfficeDTO;
import br.com.postech.clientes.drivers.external.BackOfficeComprovanteGateway;
import br.com.postech.clientes.drivers.external.BackOfficeGateway;
import br.com.postech.clientes.drivers.external.ClienteGateway;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.core.entities.Cliente;
import br.com.postech.clientes.core.enums.OperacaoBackOffice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BackOfficeRealizarOperacaoUseCaseTest {

    @Mock
    private BackOfficeGateway backOfficeGateway;

    @Mock
    private BackOfficeComprovanteGateway backOfficeComprovanteGateway;

    @Mock
    private ClienteGateway clienteGateway;

    @Mock
    private BackOfficePresenter backOfficePresenter;

    @InjectMocks
    private BackOfficeRealizarOperacaoUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRealizarAnonimizacao() {
        CriacaoOperacaoBackOfficeDTO operacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        operacaoDTO.setOperacao(OperacaoBackOffice.ANONIMIZACAO);
        operacaoDTO.setCliente(new ClienteDTO());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        when(backOfficePresenter.toEntity(any(), anyLong())).thenReturn(new BackOfficeOperacao());

        useCase.realizar(operacaoDTO);

        verify(clienteGateway, times(1)).salvar(any());
        verify(backOfficePresenter, times(1)).toEntity(any(), anyLong());
        verify(backOfficeGateway, times(1)).salvar(any());
    }

    @Test
    void testRealizarExclusao() {
        CriacaoOperacaoBackOfficeDTO operacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        operacaoDTO.setOperacao(OperacaoBackOffice.EXCLUSAO);
        operacaoDTO.setCliente(new ClienteDTO());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        when(backOfficePresenter.toEntity(any(), anyLong())).thenReturn(new BackOfficeOperacao());

        useCase.realizar(operacaoDTO);

        verify(clienteGateway, times(1)).delete(anyLong());
        verify(backOfficePresenter, times(1)).toEntity(any(), anyLong());
        verify(backOfficeGateway, times(1)).salvar(any());
    }

    @Test
    void testRealizarInativacao() {
        CriacaoOperacaoBackOfficeDTO operacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        operacaoDTO.setOperacao(OperacaoBackOffice.INATIVACAO);
        operacaoDTO.setCliente(new ClienteDTO());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        when(backOfficePresenter.toEntity(any(), anyLong())).thenReturn(new BackOfficeOperacao());

        useCase.realizar(operacaoDTO);

        verify(clienteGateway, times(1)).salvar(any());
        verify(backOfficePresenter, times(1)).toEntity(any(), anyLong());
        verify(backOfficeGateway, times(1)).salvar(any());
    }

    @Test
    void testRealizarOperacaoNaoEncontrada() {
        CriacaoOperacaoBackOfficeDTO operacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        operacaoDTO.setOperacao(OperacaoBackOffice.INATIVACAO);
        operacaoDTO.setCliente(new ClienteDTO());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        when(backOfficePresenter.toEntity(any(), anyLong())).thenReturn(new BackOfficeOperacao());

        useCase.realizar(operacaoDTO);

        verify(clienteGateway, times(1)).salvar(any());
        verify(backOfficePresenter, times(1)).toEntity(any(), anyLong());
        verify(backOfficeGateway, times(1)).salvar(any());
    }
}
