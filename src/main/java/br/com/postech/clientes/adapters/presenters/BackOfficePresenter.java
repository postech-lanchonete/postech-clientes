package br.com.postech.clientes.adapters.presenters;

import br.com.postech.clientes.adapters.dto.CriacaoOperacaoBackOfficeDTO;
import br.com.postech.clientes.adapters.dto.OperacaoBackOfficeDTO;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class})
public interface BackOfficePresenter {


    @Mapping(target = "cliente.id", source = "clienteId")
    OperacaoBackOfficeDTO toDto(BackOfficeOperacao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clienteId", source = "id")
    @Mapping(target = "dataOperacao", expression = "java(LocalDateTime.now())")
    BackOfficeOperacao toEntity(CriacaoOperacaoBackOfficeDTO dto, Long id);

}
