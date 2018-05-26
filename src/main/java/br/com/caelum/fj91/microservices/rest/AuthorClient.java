package br.com.caelum.fj91.microservices.rest;

import br.com.caelum.fj91.microservices.dtos.AuthorDTO;
import br.com.caelum.fj91.microservices.exceptions.NotFoundException;
import br.com.caelum.fj91.microservices.models.AuthorLink;
import br.com.caelum.fj91.microservices.models.Book;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorClient {

    private final DiscoveryClient discovery;
    private RestTemplate rest = new RestTemplate();
    private String baseURI = "/authors/";

    public AuthorClient(DiscoveryClient discovery) {
        this.discovery = discovery;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @Cacheable("authors")
    public List<String> getAuthorsByBook(Book book){
        return book.getAuthors().stream()
                .map(AuthorLink::getId)
                    .map(this::getAuthorById)
                        .collect(Collectors.toList());

    }

    private String getAuthorById(Long id) {
        ServiceInstance instance = discovery.getInstances("authors").stream().findAny().orElseThrow(() -> new RuntimeException("No instances available!"));

        URI uri = instance.getUri().resolve(baseURI + id);

        RequestEntity<Void> request = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();

        ResponseEntity<AuthorDTO> response = rest.exchange(request, AuthorDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().getName();
        }

        throw new NotFoundException();
    }

    @CacheEvict(value = "authors", allEntries = true)
    public List<String> fallback(Book book){
        System.out.println("\n\n\n\n");
        System.out.println("FALLBACK");
        System.out.println("\n\n\n\n");
        return new ArrayList<>();
    }
}
