package br.com.postech.clientes.integrationTest;

import br.com.postech.clientes.adapters.dto.ClienteDTO;
import br.com.postech.clientes.adapters.dto.CriacaoOperacaoBackOfficeDTO;
import br.com.postech.clientes.adapters.repositories.ClienteRepository;
import br.com.postech.clientes.core.entities.Cliente;
import br.com.postech.clientes.core.enums.OperacaoBackOffice;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class BackOfficeControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        clienteRepository.deleteAll();
        var cliente = new Cliente();
        cliente.setAtivo(true);
        cliente.setNome("Cliente");
        cliente.setSobrenome("Teste");
        cliente.setEmail("cliente.teste@test.com");
        cliente.setCpf("1234567891");
        cliente.setSenha("123456798");
        clienteRepository.save(cliente);
    }

    @Test
    void criarOperacao_deveRetornarOperacaoCriada_mantendoClienteQuandoOperacaoForAnonimizacao() throws Exception {
        var criacaoOperacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        criacaoOperacaoDTO.setOperacao(OperacaoBackOffice.ANONIMIZACAO);
        criacaoOperacaoDTO.setCliente(new ClienteDTO());
        criacaoOperacaoDTO.getCliente().setId(1L);
        criacaoOperacaoDTO.setUsuario("usuario");

        mockMvc.perform(post("/v1/backoffice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criacaoOperacaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("usuario"))
                .andExpect(jsonPath("$.operacao").value("ANONIMIZACAO"))
                .andExpect(jsonPath("$.cliente.id").value("1"));

        var cliente = clienteRepository.findById(1L);
        assertTrue(cliente.isPresent());
    }

    @Test
    void criarOperacao_deveRetornarBadRequest_quandoFaltarInformacoesParaAcharCliente() throws Exception {
        var criacaoOperacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        criacaoOperacaoDTO.setOperacao(OperacaoBackOffice.ANONIMIZACAO);
        criacaoOperacaoDTO.setCliente(new ClienteDTO());
        criacaoOperacaoDTO.setUsuario("usuario");

        mockMvc.perform(post("/v1/backoffice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criacaoOperacaoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criarOperacao_deveRetornarNotFound_quandoUsuarioNaoExistir() throws Exception {
        var criacaoOperacaoDTO = new CriacaoOperacaoBackOfficeDTO();
        criacaoOperacaoDTO.setOperacao(OperacaoBackOffice.ANONIMIZACAO);
        criacaoOperacaoDTO.setCliente(new ClienteDTO());
        criacaoOperacaoDTO.getCliente().setId(2L);
        criacaoOperacaoDTO.setUsuario("usuario");

        mockMvc.perform(post("/v1/backoffice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criacaoOperacaoDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarOperacoes_deveRetornarOperacoes() throws Exception {
        mockMvc.perform(get("/v1/backoffice")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}