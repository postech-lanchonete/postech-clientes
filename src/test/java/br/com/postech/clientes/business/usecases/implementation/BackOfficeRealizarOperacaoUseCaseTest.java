package br.com.postech.clientes.business.usecases.implementation;

import br.com.postech.clientes.adapters.adapter.BackOfficeAdapter;
import br.com.postech.clientes.adapters.dto.ClienteDTO;
import br.com.postech.clientes.adapters.dto.CriacaoOperacaoBackOfficeDTO;
import br.com.postech.clientes.adapters.gateways.BackOfficeGateway;
import br.com.postech.clientes.adapters.gateways.ClienteGateway;
import br.com.postech.clientes.business.exceptions.NotFoundException;
import br.com.postech.clientes.business.usecases.implementation.BackOfficeRealizarOperacaoUseCase;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BackOfficeRealizarOperacaoUseCaseTest {

    @Mock
    private BackOfficeGateway backOfficeGateway;

    @Mock
    private ClienteGateway clienteGateway;

    @Mock
    private BackOfficeAdapter backOfficeAdapter;

    @InjectMocks
    private BackOfficeRealizarOperacaoUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRealizarAnonimizacao() {
        CriacaoOperacaoBackOfficeDTO operacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        operacaoDTO.setOperacao(OperacaoBackOffice.ANONIMIZACAO);
        operacaoDTO.setCliente(new ClienteDTO());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        when(backOfficeAdapter.toEntity(any(), anyLong())).thenReturn(new BackOfficeOperacao());

        useCase.realizar(operacaoDTO);

        verify(clienteGateway, times(1)).salvar(any());
        verify(backOfficeAdapter, times(1)).toEntity(any(), anyLong());
        verify(backOfficeGateway, times(1)).salvar(any());
    }

    @Test
    public void testRealizarExclusao() {
        CriacaoOperacaoBackOfficeDTO operacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        operacaoDTO.setOperacao(OperacaoBackOffice.EXCLUSAO);
        operacaoDTO.setCliente(new ClienteDTO());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        when(backOfficeAdapter.toEntity(any(), anyLong())).thenReturn(new BackOfficeOperacao());

        useCase.realizar(operacaoDTO);

        verify(clienteGateway, times(1)).delete(anyLong());
        verify(backOfficeAdapter, times(1)).toEntity(any(), anyLong());
        verify(backOfficeGateway, times(1)).salvar(any());
    }

    @Test
    public void testRealizarInativacao() {
        CriacaoOperacaoBackOfficeDTO operacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        operacaoDTO.setOperacao(OperacaoBackOffice.INATIVACAO);
        operacaoDTO.setCliente(new ClienteDTO());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        when(backOfficeAdapter.toEntity(any(), anyLong())).thenReturn(new BackOfficeOperacao());

        useCase.realizar(operacaoDTO);

        verify(clienteGateway, times(1)).salvar(any());
        verify(backOfficeAdapter, times(1)).toEntity(any(), anyLong());
        verify(backOfficeGateway, times(1)).salvar(any());
    }

    @Test
    public void testRealizarOperacaoNaoEncontrada() {
        CriacaoOperacaoBackOfficeDTO operacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        operacaoDTO.setOperacao(OperacaoBackOffice.INATIVACAO);
        operacaoDTO.setCliente(new ClienteDTO());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        when(backOfficeAdapter.toEntity(any(), anyLong())).thenReturn(new BackOfficeOperacao());

        useCase.realizar(operacaoDTO);

        verify(clienteGateway, times(1)).salvar(any());
        verify(backOfficeAdapter, times(1)).toEntity(any(), anyLong());
        verify(backOfficeGateway, times(1)).salvar(any());
    }
}
