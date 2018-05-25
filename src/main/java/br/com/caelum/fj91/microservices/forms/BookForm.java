package br.com.caelum.fj91.microservices.forms;

import br.com.caelum.fj91.microservices.models.AuthorLink;
import br.com.caelum.fj91.microservices.models.Book;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private List<AuthorForm> authors = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorForm> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorForm> authors) {
        this.authors = authors;
    }

    public Book toEntity() {



        List<AuthorLink> authorLinks = authors.stream()
                                            .map(AuthorForm::getId)
                                            .map(AuthorLink::new)
                                            .collect(Collectors.toList());

        return new Book(title, authorLinks);
    }
}
