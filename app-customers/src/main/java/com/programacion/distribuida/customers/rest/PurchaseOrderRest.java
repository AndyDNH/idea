package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.db.LineItem;
import com.programacion.distribuida.customers.db.LineItemId;
import com.programacion.distribuida.customers.db.PurchaseOrder;
import com.programacion.distribuida.customers.dtos.LineItemDto;
import com.programacion.distribuida.customers.dtos.PurchaseOrderDto;
import com.programacion.distribuida.customers.dtos.PurchaseOrderRequest;
import com.programacion.distribuida.customers.repo.CustomerRepository;
import com.programacion.distribuida.customers.repo.PurchaseOrderRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/purchaseorders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class PurchaseOrderRest {

    @Inject
    PurchaseOrderRepository purchaseOrderRepository;

    @Inject
    CustomerRepository customerRepository;

    @GET
    public List<PurchaseOrderDto> findAll() {
        return purchaseOrderRepository.streamAll()
                .map(this::toDto)
                .toList();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return purchaseOrderRepository.findByIdOptional(id)
                .map(this::toDto)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/customer/{customerId}")
    public List<PurchaseOrderDto> findByCustomer(@PathParam("customerId") Long customerId) {
        return purchaseOrderRepository
                .stream("customer.id", customerId)
                .map(this::toDto)
                .toList();
    }

    @POST
    public Response create(PurchaseOrderRequest request) {
        var customer = customerRepository.findByIdOptional(request.getCustomerId());
        if (customer.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var order = new PurchaseOrder();
        applyRequest(order, request);
        order.setCustomer(customer.get());
        addItems(order, request.getLineItems());

        purchaseOrderRepository.persist(order);

        var uri = UriBuilder
                .fromUri("/purchaseorders/{id}")
                .build(order.getId());

        return Response.created(uri).entity(toDto(order)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PurchaseOrderRequest request) {
        var order = purchaseOrderRepository.findByIdOptional(id);
        if (order.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var customer = customerRepository.findByIdOptional(request.getCustomerId());
        if (customer.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var current = order.get();
        applyRequest(current, request);
        current.setCustomer(customer.get());
        current.getLineItems().clear();
        addItems(current, request.getLineItems());

        return Response.ok(toDto(current)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (!purchaseOrderRepository.deleteById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().build();
    }

    private void applyRequest(PurchaseOrder order, PurchaseOrderRequest request) {
        order.setPlacedOn(request.getPlacedOn());
        order.setDeliveredOn(request.getDeliveredOn());
        order.setStatus(request.getStatus());
        order.setTotal(request.getTotal());
    }

    private void addItems(PurchaseOrder order, List<LineItemDto> items) {
        if (items == null) {
            return;
        }

        var nextIndex = new AtomicInteger(0);
        items.forEach(itemDto -> {
            var id = new LineItemId();
            id.setIdx(itemDto.getIdx() != null ? itemDto.getIdx() : nextIndex.getAndIncrement());

            var item = new LineItem();
            item.setId(id);
            item.setBookIsbn(itemDto.getBookIsbn());
            item.setQuantity(itemDto.getQuantity());
            order.addLineItem(item);
        });
    }

    private PurchaseOrderDto toDto(PurchaseOrder order) {
        return PurchaseOrderDto.builder()
                .id(order.getId())
                .placedOn(order.getPlacedOn())
                .deliveredOn(order.getDeliveredOn())
                .status(order.getStatus())
                .total(order.getTotal())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .customerName(order.getCustomer() != null ? order.getCustomer().getName() : null)
                .lineItems(order.getLineItems()
                        .stream()
                        .map(this::toDto)
                        .toList())
                .build();
    }

    private LineItemDto toDto(LineItem item) {
        return LineItemDto.builder()
                .orderId(item.getId() != null ? item.getId().getOrderId() : null)
                .idx(item.getId() != null ? item.getId().getIdx() : null)
                .bookIsbn(item.getBookIsbn())
                .quantity(item.getQuantity())
                .build();
    }
}
