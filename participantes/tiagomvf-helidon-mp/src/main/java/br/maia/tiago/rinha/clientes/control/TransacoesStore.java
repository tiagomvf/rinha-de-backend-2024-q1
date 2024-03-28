package br.maia.tiago.rinha.clientes.control;

import br.maia.tiago.rinha.clientes.boundary.ClientesResource;
import br.maia.tiago.rinha.clientes.entity.Transacao;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Dependent
@Transactional(Transactional.TxType.MANDATORY)
public class TransacoesStore {

    @PersistenceContext(name = "pu1")
    EntityManager em;

    @Inject
    ClienteStore clienteStore;

    public void debitar(Integer clientId, Integer valor, String descricao) {
        var cliente = clienteStore.findById(clientId);
        var newSaldo = cliente.getSaldo() - valor;
        if(cliente.getLimite() + newSaldo < 0) {
            throw new SaldoInsuficienteException();
        }
        cliente.setSaldo(newSaldo);
        save(clientId, valor, descricao, 'd');
    }


    private void save(Integer clientId, Integer valor, String descricao, char tipo) {
        Transacao transacao = new Transacao();
        transacao.setTipo(tipo);
        transacao.setValor(valor);
        transacao.setDescricao(descricao);
        transacao.setRealizadaEm(LocalDateTime.now(ZoneId.systemDefault()));
        transacao.setClienteId(clientId);
        em.persist(transacao);
    }
    public void creditar(Integer clientId, Integer valor, String descricao) {
        var cliente = clienteStore.findById(clientId);
        cliente.setSaldo(cliente.getSaldo() + valor);
        save(clientId, valor, descricao, 'c');
    }

    public List<Transacao> extrato(Integer clientId) {

        return em.createQuery("""
                select t from Transacao t 
                where t.clienteId = :id 
                order by t.realizadaEm desc
                limit 10""",
                Transacao.class)
            .setParameter("id", clientId)
            .getResultList();
    }
}
