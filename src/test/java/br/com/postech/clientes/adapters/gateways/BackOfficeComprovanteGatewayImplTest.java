package br.com.postech.clientes.adapters.gateways;

import br.com.postech.clientes.adapters.repositories.ClienteRepository;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.core.entities.Cliente;
import br.com.postech.clientes.core.enums.OperacaoBackOffice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BackOfficeComprovanteGatewayImplTest {

    @InjectMocks
    private BackOfficeComprovanteGatewayImpl comprovanteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSalvarComprovante() throws IOException {
        BackOfficeOperacao operacao = new BackOfficeOperacao();
        operacao.setId(1L);
        operacao.setOperacao(OperacaoBackOffice.EXCLUSAO);
        operacao.setDataOperacao(LocalDateTime.now());
        operacao.setUsuario("usuario1");

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Nome");
        cliente.setSobrenome("Sobrenome");
        cliente.setEmail("cliente@test.com");
        cliente.setCpf("123456789");

        when(clienteRepository.findById(1L)).thenReturn(java.util.Optional.of(cliente));

        comprovanteService.salvarComprovante(operacao, cliente);

        String fileName = operacao.getOperacao().name() + "_" + operacao.getDataOperacao().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        File file = new File("comprovantes/" + fileName);
        assert file.exists();

        try (Scanner scanner = new Scanner(file)) {
            String expectedContent = "Detalhes da Operação:\n" +
                    "Tipo de Operação: EXCLUSAO\n" +
                    "Data da Operação: " + operacao.getDataOperacao().toString() + "\n" +
                    "Usuário: usuario1\n" +
                    "\n" +
                    "Detalhes do Cliente:\n" +
                    "ID do Cliente: 1\n" +
                    "Nome: Nome\n" +
                    "Sobrenome: Sobrenome\n" +
                    "E-mail: cliente@test.com\n" +
                    "CPF: 123456789\n";
            String fileContent = scanner.useDelimiter("\\A").next();
            assertEquals(expectedContent, fileContent);
        } finally {
            File dir = new File("comprovantes");
            deleteDirectory(dir);
        }
    }

    private static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDirectory(child);
                }
            }
        }
        dir.delete();
    }
}
