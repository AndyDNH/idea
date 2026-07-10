package com.programacion.distribuida.books.db;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String isbn;
    private Integer version;
    private String title;
    private Double price;

    @OneToOne(mappedBy = "book")
    private Inventory inventory;


}
