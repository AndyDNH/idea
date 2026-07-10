package com.programacion.distribuida.authors.rest;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.dtos.AuthorDto;
import com.programacion.distribuida.authors.repo.AuthorsRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    @Inject
    AuthorsRepository authorsRepository;

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer port;


    @GET
    public List<Author> getAll(){
        return authorsRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id){
//        var obj =  authorsRepository.findByIdOptional(id);
//        if(obj.isPresent()){
//            return Response.ok(obj.get()).build();
//        }else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }

        return authorsRepository.findByIdOptional(id)
                .map(it->{
                    it.setName(it.getName() + " port - " + port);
                    return it;
                })
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/find/{isbn}")
    public List<AuthorDto> findByBook(@PathParam("isbn") String isbn) {
        return authorsRepository.findByBook(isbn)
                .stream()
                .peek(it -> it.setName(it.getName() + " - " + port))
                .map(it -> AuthorDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .build())
                .toList();
    }


}
