package br.com.postech.clientes.adapters.dto;

import br.com.postech.clientes.core.enums.OperacaoBackOffice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unused")
public class CriacaoOperacaoBackOfficeDTO {
    @NotNull(message = "Operacao é mandatoria")
    private OperacaoBackOffice operacao;

    private ClienteDTO cliente;


    @NotBlank(message = "Usuario que esta fazendo a operacao é mandatorio")
    private String usuario;

    @JsonIgnore
    @AssertTrue(message = "Informe pelo menos um dos seguintes campos: email, cpf ou id do cliente")
    public boolean isValidInput() {
        return cliente.getEmail() != null || cliente.getCpf() != null || cliente.getId() != null;
    }
}
