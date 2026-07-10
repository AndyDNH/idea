package com.programacion.distribuida.customers.db;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lineitem")
@Getter
@Setter
@ToString
public class LineItem {

    @EmbeddedId
    private LineItemId id;

    private Integer quantity;

    @Column(name = "book_isbn")
    private String bookIsbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private PurchaseOrder order;
}
