package com.programacion.distribuida.customers.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class LineItemId implements Serializable {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "idx")
    private Integer idx;
}
