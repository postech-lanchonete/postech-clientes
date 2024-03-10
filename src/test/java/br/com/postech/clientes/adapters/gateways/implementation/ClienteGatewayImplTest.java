package br.com.postech.clientes.adapters.gateways.implementation;

import br.com.postech.clientes.adapters.repositories.ClienteRepository;
import br.com.postech.clientes.core.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClienteGatewayImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteGatewayImpl clienteGateway;

    @Test
    void salvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente");
        cliente.setSobrenome("Teste");
        cliente.setCpf("1234567891");
        cliente.setSenha("1234567891");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteSalvo = clienteGateway.salvar(cliente);

        assertEquals(cliente, clienteSalvo);
    }

    @Test
    void buscarClientesPorExemplo() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente");
        cliente.setSobrenome("Teste");
        cliente.setCpf("1234567891");
        cliente.setSenha("1234567891");
        Example<Cliente> clienteExample = Example.of(cliente);

        when(clienteRepository.findAll(clienteExample)).thenReturn(Collections.singletonList(cliente));

        List<Cliente> clientesEncontrados = clienteGateway.buscarPor(clienteExample);

        assertEquals(1, clientesEncontrados.size());
        assertEquals(cliente, clientesEncontrados.get(0));
    }
}
