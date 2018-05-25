package br.com.caelum.fj91.microservices.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
}
