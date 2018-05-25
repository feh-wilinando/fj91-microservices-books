package br.com.caelum.fj91.microservices.dtos;

import br.com.caelum.fj91.microservices.models.Book;

import java.util.List;

public class BookResource {

    private final Book book;
    private final List<String> authors;

    public BookResource(Book book, List<String> authors) {
        this.book = book;
        this.authors = authors;
    }

    public String getTitle() {
        return book.getTitle();
    }

    public List<String> getAuthors() {
        return authors;
    }

    public Long getId() {
        return book.getId();
    }
}
