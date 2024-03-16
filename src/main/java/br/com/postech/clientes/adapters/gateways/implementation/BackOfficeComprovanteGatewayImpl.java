package br.com.postech.clientes.adapters.gateways.implementation;

import br.com.postech.clientes.adapters.gateways.BackOfficeComprovanteGateway;
import br.com.postech.clientes.business.exceptions.BadRequestException;
import br.com.postech.clientes.core.entities.BackOfficeOperacao;
import br.com.postech.clientes.core.entities.Cliente;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

@Component
public class BackOfficeComprovanteGatewayImpl implements BackOfficeComprovanteGateway {
    @Override
    public void salvarComprovante(BackOfficeOperacao operacao, Cliente cliente) {
        try {
            String fileName = operacao.getOperacao().name() + "_" + operacao.getDataOperacao().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";

            FileWriter fileWriter = new FileWriter("comprovantes/" + fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("Detalhes da Operação:");
            printWriter.println("Tipo de Operação: " + operacao.getOperacao());
            printWriter.println("Data da Operação: " + operacao.getDataOperacao());
            printWriter.println("Usuário: " + operacao.getUsuario());
            printWriter.println();
            printWriter.println("Detalhes do Cliente:");
            printWriter.println("ID do Cliente: " + cliente.getId());
            printWriter.println("Nome: " + cliente.getNome());
            printWriter.println("Sobrenome: " + cliente.getSobrenome());
            printWriter.println("E-mail: " + cliente.getEmail());
            printWriter.println("CPF: " + cliente.getCpf());

            printWriter.close();

            System.out.println("Comprovante salvo com sucesso em: " + fileName);
        } catch (IOException e) {
            throw new BadRequestException("Erro ao salvar comprovante");
        }
    }
}
