package br.com.postech.clientes.business.usecases.implementation;

import br.com.postech.clientes.adapters.gateways.ClienteGateway;
import br.com.postech.clientes.business.exceptions.NotFoundException;
import br.com.postech.clientes.core.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteBuscarPorCpfUseCaseTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private ClienteBuscarPorCpfUseCase clienteBuscarPorCpfUseCase;

    @Test
    void realizar_DeveRetornarCliente_QuandoEncontradoPorCpf() {
        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-01");

        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(List.of(cliente));

        Cliente resultado = clienteBuscarPorCpfUseCase.realizar(cliente);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteGateway, times(1)).buscarPor(any(Example.class));
    }

    @Test
    void realizar_DeveLancarExcecao_QuandoClienteNaoEncontradoPorCpf() {
        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-01");

        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> clienteBuscarPorCpfUseCase.realizar(cliente));
        verify(clienteGateway, times(1)).buscarPor(any(Example.class));
    }
}
