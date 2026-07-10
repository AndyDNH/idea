package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dtos.BookDTO;
import com.programacion.distribuida.books.repo.BookRepository;
import jakarta.inject.Inject;
import jakarta.persistence.OneToOne;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@RequiredArgsConstructor
@Transactional
public class BookRest {


    //    -----Inyeccion de dependencia por constructor
//    el modificador final obliga a que se inicialicen en el constructor
//    @Inject
    final BookRepository bookRepository;

//    @Inject
//    @RestClient
    final AuthorRestClient client;


    @Inject
    public BookRest(BookRepository bookRepository,@RestClient AuthorRestClient client) {
        this.bookRepository = bookRepository;
        this.client = client;
    }


//    public BookRest(BookRepository bookRepository, AuthorRestClient client) {
//        this.bookRepository = bookRepository;
//        this.client = client;
//    }

//    @PostConstruct
//    public void init() {
//        client = RestClientBuilder.newBuilder()
//                .baseUri("http://127.0.0.1:8070")
//                .build(AuthorRestClient.class);
//    }


    @GET
//    @Retry(maxRetries = 4, delay = 200)
    public List<BookDTO> findAll() {
        return bookRepository.streamAll()
                .map(book -> {
                    // consultar los autores en http://127.0.0.1:8070
                    var authors = client.findByBook(book.getIsbn());

                    return BookDTO.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .authors(authors)
                            .inventorySold(book.getInventory() != null ? book.getInventory().getSold() : null)
                            .inventorySupplied(book.getInventory() != null ? book.getInventory().getSupplied() : null)
                            .build();
                })
                .toList();
    }

    @GET
    @Path("/{isbn}")
//    @Retry(maxRetries = 2, delay = 200)
    public Response findByIsbn(@PathParam("isbn") String isbn) {

//        AuthorRestClient client =  RestClientBuilder.newBuilder()
//                .baseUri("http://127.0.0.1:8070")
//                        .build(AuthorRestClient.class);


        return bookRepository.findByIdOptional(isbn)
                .map(book -> {
//                    Consultar los autores en http://127.0.0.1:8070
//                    List<AuthorDto> authors = List.of(
//                            AuthorDto.builder()
//                                    .id(1)
//                                    .name("Jason Mormoa")
//                                    .build()
//                    );
                    var authors = client.findByBook(isbn);
                    return BookDTO.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .authors(authors) // Esta variable 'authors' debe venir de una consulta previa
                            .inventorySold(book.getInventory().getSold())
                            .inventorySupplied(book.getInventory().getSupplied())
                            .build();
                })


//                .map(it -> BookDTO.builder()
//                        .isbn(it.getIsbn())
//                        .title(it.getTitle())
//                        .price(it.getPrice())
//                        .inventorySold(it.getInventory().getSold())
//                        .inventorySupplied(it.getInventory().getSupplied())
//                        .build())
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }



    @PUT
    @Path("/{isbn}")
    public Response update(@PathParam("isbn") String isbn, Book book) {
        bookRepository.update(isbn, book);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{isbn}")
    public Response delete(@PathParam("isbn") String isbn) {
        bookRepository.deleteById(isbn);
        return Response.ok().build();
    }

    @POST
    @Path("/{isbn}")
    public Response post(Book book) {
        bookRepository.persist(book);

        var uri = UriBuilder.
                fromUri("/books/{isbn}").
                build(book.getIsbn());
        return Response.created(uri).build();
    }

}
