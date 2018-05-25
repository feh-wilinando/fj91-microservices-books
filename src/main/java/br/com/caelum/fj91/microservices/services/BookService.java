package br.com.caelum.fj91.microservices.services;

import br.com.caelum.fj91.microservices.dtos.BookResource;
import br.com.caelum.fj91.microservices.exceptions.NotFoundException;
import br.com.caelum.fj91.microservices.models.Book;
import br.com.caelum.fj91.microservices.repositories.Books;
import br.com.caelum.fj91.microservices.rest.AuthorClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final Books books;
    private final AuthorClient rest;

    public BookService(Books books, AuthorClient rest) {
        this.books = books;
        this.rest = rest;
    }


    public BookResource getById(Long id){
        Book book = books.findById(id).orElseThrow(NotFoundException::new);
        return toResource(book);
    }

    public BookResource save(Book book) {
        books.save(book);
        return toResource(book);
    }

    public List<BookResource> findAll() {
        return books.findAll().stream().map(this::toResource).collect(Collectors.toList());
    }

    private BookResource toResource(Book book) {
        return new BookResource(book, rest.getAuthorsByBook(book));
    }
}
