package br.com.postech.clientes.adapters.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unused")
public class ClienteDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
}
