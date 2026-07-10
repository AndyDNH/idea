//package com.programacion.distribuida.customers.db;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Table(name = "purchaseorder")
//@Getter
//@Setter
//public class PurchaseOrder {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private LocalDateTime deliveredon;
//    private LocalDateTime placedon;
//    private Integer status;
//    private Integer total;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
//
//
//    @ElementCollection
//    @CollectionTable(name = "lineitem", joinColumns = @JoinColumn(name = "order_id"))
//    @OrderColumn(name = "idx")
//    private List<LineItem> lineItems;
//}
