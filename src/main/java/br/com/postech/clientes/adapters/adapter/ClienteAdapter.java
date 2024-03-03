package br.com.postech.clientes.adapters.adapter;

import br.com.postech.clientes.adapters.dto.ClienteDTO;
import br.com.postech.clientes.adapters.dto.CriacaoClienteDTO;
import br.com.postech.clientes.core.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteAdapter {

    ClienteDTO toDto(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    Cliente toEntity(CriacaoClienteDTO clienteDto);

    default Cliente toEntity(String cpf) {
        Cliente cliente = new Cliente();
        cliente.setAtivo(true);
        cliente.setCpf(cpf);
        return cliente;
    }
}
