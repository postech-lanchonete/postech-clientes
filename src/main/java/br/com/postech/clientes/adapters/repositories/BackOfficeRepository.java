package br.com.postech.clientes.adapters.repositories;

import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackOfficeRepository extends JpaRepository<BackOfficeOperacao, Long> {
}