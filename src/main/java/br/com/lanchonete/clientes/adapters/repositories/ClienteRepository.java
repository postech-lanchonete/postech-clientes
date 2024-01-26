package br.com.lanchonete.clientes.adapters.repositories;

import br.com.lanchonete.clientes.core.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}