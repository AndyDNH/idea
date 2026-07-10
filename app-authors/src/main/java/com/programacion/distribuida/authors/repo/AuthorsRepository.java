package com.programacion.distribuida.authors.repo;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.db.BookAuthor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class AuthorsRepository implements PanacheRepositoryBase<Author,Integer> {

public List<Author> findByBook(String isbn){
    BookAuthor pp;
    return this.find("select o.author from BookAuthor o where o.id.bookIsbn = ?1", isbn)
            .list();
}

}
