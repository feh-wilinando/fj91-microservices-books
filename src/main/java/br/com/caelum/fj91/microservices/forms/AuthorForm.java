package br.com.caelum.fj91.microservices.forms;

import javax.persistence.Embeddable;

@Embeddable
public class AuthorForm {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
