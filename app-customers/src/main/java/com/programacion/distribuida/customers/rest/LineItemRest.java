package com.programacion.distribuida.customers.rest;

import com.programacion.distribuida.customers.db.LineItem;
import com.programacion.distribuida.customers.db.LineItemId;
import com.programacion.distribuida.customers.dtos.LineItemDto;
import com.programacion.distribuida.customers.repo.LineItemRepository;
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

import java.util.List;

@Path("/lineitems")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class LineItemRest {

    @Inject
    LineItemRepository lineItemRepository;

    @Inject
    PurchaseOrderRepository purchaseOrderRepository;

    @GET
    @Path("/order/{orderId}")
    public List<LineItemDto> findByOrder(@PathParam("orderId") Long orderId) {
        return lineItemRepository
                .stream("id.orderId", orderId)
                .map(this::toDto)
                .toList();
    }

    @GET
    @Path("/order/{orderId}/{idx}")
    public Response findById(@PathParam("orderId") Long orderId, @PathParam("idx") Integer idx) {
        return lineItemRepository.findByIdOptional(idOf(orderId, idx))
                .map(this::toDto)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    @Path("/order/{orderId}")
    public Response create(@PathParam("orderId") Long orderId, LineItemDto request) {
        var order = purchaseOrderRepository.findByIdOptional(orderId);
        if (order.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var item = new LineItem();
        item.setId(idOf(orderId, request.getIdx()));
        item.setBookIsbn(request.getBookIsbn());
        item.setQuantity(request.getQuantity());
        item.setOrder(order.get());

        lineItemRepository.persist(item);

        return Response.ok(toDto(item)).build();
    }

    @PUT
    @Path("/order/{orderId}/{idx}")
    public Response update(@PathParam("orderId") Long orderId, @PathParam("idx") Integer idx, LineItemDto request) {
        return lineItemRepository.findByIdOptional(idOf(orderId, idx))
                .map(current -> {
                    current.setBookIsbn(request.getBookIsbn());
                    current.setQuantity(request.getQuantity());
                    return Response.ok(toDto(current));
                })
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @DELETE
    @Path("/order/{orderId}/{idx}")
    public Response delete(@PathParam("orderId") Long orderId, @PathParam("idx") Integer idx) {
        if (!lineItemRepository.deleteById(idOf(orderId, idx))) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().build();
    }

    private LineItemId idOf(Long orderId, Integer idx) {
        var id = new LineItemId();
        id.setOrderId(orderId);
        id.setIdx(idx);
        return id;
    }

    private LineItemDto toDto(LineItem item) {
        return LineItemDto.builder()
                .orderId(item.getId().getOrderId())
                .idx(item.getId().getIdx())
                .bookIsbn(item.getBookIsbn())
                .quantity(item.getQuantity())
                .build();
    }
}
