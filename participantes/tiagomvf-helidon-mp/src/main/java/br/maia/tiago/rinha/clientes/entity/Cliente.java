package br.maia.tiago.rinha.clientes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Cliente {
    public void setId(Integer id) {
        this.id = id;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    @Id
    Integer id;
    Integer saldo;
    Integer limite;

    @Id
    public Integer getId() {
        return id;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public Integer getLimite() {
        return limite;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Cliente) obj;
        return Objects.equals(this.id, that.id) &&
            Objects.equals(this.saldo, that.saldo) &&
            Objects.equals(this.limite, that.limite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, saldo, limite);
    }

    @Override
    public String toString() {
        return "Cliente[" +
            "id=" + id + ", " +
            "saldo=" + saldo + ", " +
            "limite=" + limite + ']';
    }
}
