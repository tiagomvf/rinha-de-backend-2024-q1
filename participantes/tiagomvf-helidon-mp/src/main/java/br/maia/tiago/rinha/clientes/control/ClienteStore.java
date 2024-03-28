package br.maia.tiago.rinha.clientes.control;

import br.maia.tiago.rinha.clientes.entity.Cliente;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

@Dependent
@Transactional(Transactional.TxType.MANDATORY)
public class ClienteStore {

    @PersistenceContext(name = "pu1")
    EntityManager em;

    public Cliente findById(Integer id) {
        return em.find(Cliente.class, id);
    }
}
