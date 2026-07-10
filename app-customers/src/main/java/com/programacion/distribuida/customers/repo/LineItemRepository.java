package com.programacion.distribuida.customers.repo;

import com.programacion.distribuida.customers.db.LineItem;
import com.programacion.distribuida.customers.db.LineItemId;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class LineItemRepository implements PanacheRepositoryBase<LineItem, LineItemId> {
}
