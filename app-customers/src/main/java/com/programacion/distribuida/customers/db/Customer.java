//package com.programacion.distribuida.customers.db;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Table(name = "customer")
//@Getter
//@Setter
//public class Customer {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String book_isbn;
//    private Integer sold;
//    private Integer supplied;
//    private Integer version;
//
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    private List<PurchaseOrder> orders;
//}
