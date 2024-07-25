package com.distribuida.cliente.repo;

import com.distribuida.cliente.db.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ClienteRepository implements PanacheRepositoryBase<Cliente, Integer> {
}