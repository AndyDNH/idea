package com.programacion.distribuida.customers.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class PurchaseOrderRequest {

    private LocalDateTime placedOn;
    private LocalDateTime deliveredOn;
    private Integer status;
    private Integer total;
    private Long customerId;
    private List<LineItemDto> lineItems;
}
