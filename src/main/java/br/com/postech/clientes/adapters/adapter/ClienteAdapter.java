package br.com.postech.clientes.adapters.adapter;

import br.com.postech.clientes.adapters.dto.ClienteResponseDTO;
import br.com.postech.clientes.adapters.dto.CriacaoClienteDTO;
import br.com.postech.clientes.core.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteAdapter {

    ClienteResponseDTO toDto(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    Cliente toEntity(CriacaoClienteDTO clienteDto);

    default Cliente toEntity(String cpf) {
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        return cliente;
    }
}
