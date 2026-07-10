package com.programacion.distribuida.books.dtos;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
@Builder
public class BookDTO {

    private String isbn;
    private String title;
    private Double price;

    private Integer inventorySold;
    private Integer inventorySupplied;

    public List<AuthorDto> authors;



}
