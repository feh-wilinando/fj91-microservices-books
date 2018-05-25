package br.com.caelum.fj91.microservices.rest;

import br.com.caelum.fj91.microservices.dtos.AuthorDTO;
import br.com.caelum.fj91.microservices.exceptions.NotFoundException;
import br.com.caelum.fj91.microservices.models.AuthorLink;
import br.com.caelum.fj91.microservices.models.Book;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorClient {

    private RestTemplate rest = new RestTemplate();
    private UriTemplate baseURI = new UriTemplate("http://localhost:8080/authors/{id}");

    public List<String> getAuthorsByBook(Book book){
        return book.getAuthors().stream()
                .map(AuthorLink::getId)
                    .map(this::getAuthorById)
                        .collect(Collectors.toList());

    }

    private String getAuthorById(Long id) {
        URI uri = baseURI.expand(id);

        RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();

        ResponseEntity<AuthorDTO> response = rest.exchange(request, AuthorDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getName();
        }

        throw new NotFoundException();
    }
}
