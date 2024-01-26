package br.com.postech.clientes.adapters.repositories;

import br.com.postech.clientes.core.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}