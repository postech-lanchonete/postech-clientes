package br.com.postech.clientes.integrationTest;

import br.com.postech.clientes.adapters.dto.CriacaoClienteDTO;
import br.com.postech.clientes.adapters.repositories.ClienteRepository;
import br.com.postech.clientes.core.entities.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ClienteControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        // Limpar dados de teste antes de cada execução de teste
        clienteRepository.deleteAll();
    }

    @Test
    void criarCliente_deveRetornarClienteCriado() throws Exception {
        CriacaoClienteDTO criacaoClienteDTO = new CriacaoClienteDTO();
        criacaoClienteDTO.setNome("Cliente");
        criacaoClienteDTO.setSobrenome("Teste");
        criacaoClienteDTO.setEmail("teste.teste@test.com");
        criacaoClienteDTO.setCpf("1234567891");
        criacaoClienteDTO.setSenha("123456798");

        mockMvc.perform(post("/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criacaoClienteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Cliente"))
                .andExpect(jsonPath("$.sobrenome").value("Teste"))
                .andExpect(jsonPath("$.email").value("teste.teste@test.com"))
                .andExpect(jsonPath("$.cpf").value("1234567891"));
    }

    @Test
    void criarCliente_deveRetornar422_AoInserirClienteComEmailJaCadastrado() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setEmail("teste.teste@test.com");
        clienteRepository.save(cliente);

        CriacaoClienteDTO criacaoClienteDTO = new CriacaoClienteDTO();
        criacaoClienteDTO.setNome("Cliente");
        criacaoClienteDTO.setSobrenome("Teste");
        criacaoClienteDTO.setEmail("teste.teste@test.com");
        criacaoClienteDTO.setCpf("1234567891");
        criacaoClienteDTO.setSenha("123456798");

        mockMvc.perform(post("/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criacaoClienteDTO)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void criarCliente_deveRetornar422_AoInserirClienteComNomeSobrenomeCPFJaCadastrado() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setAtivo(true);
        cliente.setNome("Cliente");
        cliente.setSobrenome("Teste");
        cliente.setCpf("1234567891");
        cliente.setSenha("1234567891");
        clienteRepository.save(cliente);

        CriacaoClienteDTO criacaoClienteDTO = new CriacaoClienteDTO();
        criacaoClienteDTO.setNome("Cliente");
        criacaoClienteDTO.setSobrenome("Teste");
        criacaoClienteDTO.setEmail("teste.teste@test.com");
        criacaoClienteDTO.setCpf("1234567891");
        criacaoClienteDTO.setSenha("123456798");

        mockMvc.perform(post("/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criacaoClienteDTO)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void buscarPorCPF_deveRetornarClienteExistente() throws Exception {
        var randomText = UUID.randomUUID().toString();
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setAtivo(true);
        cliente.setNome(randomText);
        cliente.setSobrenome("Teste");
        cliente.setEmail(randomText + "@test.com");
        cliente.setCpf(randomText);
        clienteRepository.save(cliente);

        mockMvc.perform(get("/v1/clientes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(randomText))
                .andExpect(jsonPath("$.sobrenome").value("Teste"))
                .andExpect(jsonPath("$.email").value(randomText + "@test.com"))
                .andExpect(jsonPath("$.cpf").value(randomText));
    }

    @Test
    void buscarPorCPF_deveRetornarNotFoundSeClienteNaoExistir() throws Exception {
        mockMvc.perform(get("/v1/clientes/{cpf}", "1234567891"))
                .andExpect(status().isNotFound());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }
}
