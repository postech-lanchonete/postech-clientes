package br.com.lanchonete.clientes.business.usecases;

public interface UseCase<E, S> {
    S realizar(E entrada);
}
