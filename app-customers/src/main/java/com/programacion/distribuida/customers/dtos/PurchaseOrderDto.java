package com.programacion.distribuida.customers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class PurchaseOrderDto {

    private Long id;
    private LocalDateTime placedOn;
    private LocalDateTime deliveredOn;
    private Integer status;
    private Integer total;
    private Long customerId;
    private String customerName;
    private List<LineItemDto> lineItems;
}
