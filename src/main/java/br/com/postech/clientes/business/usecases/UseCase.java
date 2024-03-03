package br.com.postech.clientes.business.usecases;

public interface UseCase<E, S> {
    S realizar(E entrada);

    interface SemEntrada<S> {
        S realizar();
    }

}
