package br.com.postech.clientes.core.entities;

import br.com.postech.clientes.core.enums.OperacaoBackOffice;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@SuppressWarnings("unused")
@Entity
@Table(name = "BackOfficeOperacao")
public class BackOfficeOperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OperacaoBackOffice operacao;
    private Long clienteId;
    private LocalDateTime dataOperacao;
    private String usuario;

}
