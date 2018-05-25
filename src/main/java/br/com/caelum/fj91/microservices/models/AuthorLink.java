package br.com.caelum.fj91.microservices.models;

import javax.persistence.Embeddable;

@Embeddable
public class AuthorLink{

    private Long id;

    /**
     * @deprecated frameworks only
     */
    @Deprecated
    AuthorLink(){}

    public AuthorLink(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
