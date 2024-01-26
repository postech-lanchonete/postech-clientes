package br.com.lanchonete.clientes.business.usecases.implementation;

import br.com.lanchonete.clientes.adapters.gateways.ClienteGateway;
import br.com.lanchonete.clientes.core.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteCriarUseCaseTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private ClienteCriarUseCase clienteCriarUseCase;

    @Test
    void realizar_DeveCriarCliente_QuandoNaoExistirDuplicidadeENaoExistirEmailCadastrado() {
        Cliente cliente = criarCliente();

        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(new ArrayList<>());
        when(clienteGateway.salvar(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteCriarUseCase.realizar(cliente);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteGateway, times(1)).salvar(cliente);
    }

    @Test
    void realizar_DeveLancarExcecao_QuandoExistirDuplicidade() {
        Cliente cliente = criarCliente();
        List<Cliente> clientesDuplicados = List.of(cliente);

        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(clientesDuplicados);

        assertThrows(DataIntegrityViolationException.class, () -> clienteCriarUseCase.realizar(cliente));
        verify(clienteGateway, never()).salvar(any(Cliente.class));
    }

    private static Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-01");
        cliente.setNome("Nome");
        cliente.setSobrenome("Sobrenome");
        cliente.setSenha("123");
        cliente.setEmail("email@example.com");
        return cliente;
    }

    @Test
    void realizar_DeveLancarExcecao_QuandoExistirEmailCadastrado() {
        Cliente cliente = criarCliente();
        List<Cliente> clientesComMesmoEmail = List.of(new Cliente());

        when(clienteGateway.buscarPor(any(Example.class))).thenReturn(clientesComMesmoEmail);

        assertThrows(DataIntegrityViolationException.class, () -> clienteCriarUseCase.realizar(cliente));
        verify(clienteGateway, never()).salvar(any(Cliente.class));
    }
}
