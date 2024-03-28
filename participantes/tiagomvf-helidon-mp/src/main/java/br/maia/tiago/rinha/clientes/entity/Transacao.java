package br.maia.tiago.rinha.clientes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    private Integer clienteId;
    private Character tipo;
    private Integer valor;
    private String descricao;
    @NotNull
    @Column(columnDefinition = "timestamp with time zone not null")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime realizadaEm;

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getRealizadaEm() {
        return realizadaEm;
    }

    public void setRealizadaEm(LocalDateTime realizadaEm) {
        this.realizadaEm = realizadaEm;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
}
