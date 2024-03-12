package br.com.postech.clientes.business.usecases.implementation;

import br.com.postech.clientes.adapters.gateways.BackOfficeGateway;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BackOfficeBuscarOperacoesUseCaseTest {

    @Mock
    private BackOfficeGateway backOfficeGateway;

    @InjectMocks
    private BackOfficeBuscarOperacoesUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRealizar() {
        BackOfficeOperacao operacao1 = new BackOfficeOperacao();
        BackOfficeOperacao operacao2 = new BackOfficeOperacao();
        List<BackOfficeOperacao> operacoesMock = Arrays.asList(operacao1, operacao2);

        when(backOfficeGateway.buscarPor(any(Example.class))).thenReturn(operacoesMock);

        List<BackOfficeOperacao> resultado = useCase.realizar();

        assertEquals(operacoesMock, resultado);

        verify(backOfficeGateway, times(1)).buscarPor(any(Example.class));
    }
}
