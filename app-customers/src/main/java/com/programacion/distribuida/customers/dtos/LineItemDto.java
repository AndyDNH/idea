package com.programacion.distribuida.customers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class LineItemDto {

    private Long orderId;
    private Integer idx;
    private String bookIsbn;
    private Integer quantity;
}
