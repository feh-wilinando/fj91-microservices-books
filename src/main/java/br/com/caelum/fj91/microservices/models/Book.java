package br.com.caelum.fj91.microservices.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Book{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ElementCollection
    private List<AuthorLink> authors = new ArrayList<>();

    /**
     * @deprecated frameworks only
     */
    @Deprecated
    Book(){}

    public Book(String title, List<AuthorLink> authorLinks) {
        this.title = title;
        this.authors = authorLinks;
    }

    public String getTitle() {
        return title;
    }

    public List<AuthorLink> getAuthors() {
        return authors;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title);
    }
}
