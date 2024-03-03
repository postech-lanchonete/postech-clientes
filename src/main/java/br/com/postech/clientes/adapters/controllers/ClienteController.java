package br.com.postech.clientes.adapters.controllers;

import br.com.postech.clientes.adapters.adapter.ClienteAdapter;
import br.com.postech.clientes.adapters.dto.ClienteDTO;
import br.com.postech.clientes.adapters.dto.CriacaoClienteDTO;
import br.com.postech.clientes.business.usecases.UseCase;
import br.com.postech.clientes.core.entities.Cliente;
import br.com.postech.clientes.drivers.web.ClienteAPI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController implements ClienteAPI {
    private final UseCase<Cliente, Cliente> buscarPorCpfUseCase;
    private final UseCase<Cliente, Cliente> criarUseCase;
    private final ClienteAdapter clienteAdapter;

    public ClienteController(@Qualifier("clienteBuscarPorCpfUseCase") UseCase<Cliente, Cliente> buscarPorCpfUseCase,
                             @Qualifier("clienteCriarUseCase") UseCase<Cliente, Cliente> criarUseCase,
                             ClienteAdapter clienteAdapter) {
        this.buscarPorCpfUseCase = buscarPorCpfUseCase;
        this.criarUseCase = criarUseCase;
        this.clienteAdapter = clienteAdapter;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO criar(@Valid @RequestBody CriacaoClienteDTO criacaoClienteDTO) {
        Cliente cliente = clienteAdapter.toEntity(criacaoClienteDTO);
        Cliente clienteCriado = criarUseCase.realizar(cliente);
        return clienteAdapter.toDto(clienteCriado);
    }

    @Override
    @GetMapping("/{cpf}")
    public ClienteDTO buscarPorCPF(@PathVariable String cpf) {
        Cliente cliente = clienteAdapter.toEntity(cpf);
        Cliente clienteEncontrado = buscarPorCpfUseCase.realizar(cliente);
        return clienteAdapter.toDto(clienteEncontrado);
    }
}
