package br.com.postech.clientes.drivers.web;

import br.com.postech.clientes.adapters.dto.CriacaoOperacaoBackOfficeDTO;
import br.com.postech.clientes.adapters.dto.OperacaoBackOfficeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/backoffice")
@Tag(name = "Operações de backoffice", description = "Todas as operações referentes a clientes no backoffice.")
public interface BackOfficeAPI {
    @Operation(
            summary = "Operação de backoffice no cliente",
            description = "Realiza uma operação de backoffice no cliente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso." ),
            @ApiResponse(responseCode = "400", description = "Algum dos atributos está incorreto.", content = { @Content(schema = @Schema()) })
    })
    OperacaoBackOfficeDTO criarOperacao(CriacaoOperacaoBackOfficeDTO operacao);

    @Operation(
            summary = "Buscar operações",
            description = "Busca todas as operações de backoffice."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operações encontradas." ),
            @ApiResponse(responseCode = "400", description = "Não existe nenhuma operação registrada no sistema.", content = { @Content(schema = @Schema()) })
    })
    List<OperacaoBackOfficeDTO> buscarOperacoes();

}
