package br.maia.tiago.rinha.clientes.control;

import jakarta.ws.rs.WebApplicationException;

public class SaldoInsuficienteException extends WebApplicationException {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente", 422);
    }
}
