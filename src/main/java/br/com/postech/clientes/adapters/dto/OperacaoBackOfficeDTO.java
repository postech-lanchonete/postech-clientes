package br.com.postech.clientes.adapters.dto;

import br.com.postech.clientes.core.enums.OperacaoBackOffice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@SuppressWarnings("unused")
public class OperacaoBackOfficeDTO {
    private Long id;
    private OperacaoBackOffice operacao;
    private ClienteDTO cliente;
    private LocalDateTime dataOperacao;
    private String usuario;
}
