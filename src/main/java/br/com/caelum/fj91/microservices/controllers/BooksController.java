package br.com.caelum.fj91.microservices.controllers;

import br.com.caelum.fj91.microservices.dtos.BookResource;
import br.com.caelum.fj91.microservices.forms.BookForm;
import br.com.caelum.fj91.microservices.models.Book;
import br.com.caelum.fj91.microservices.services.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("books")
public class BooksController {

    private final BookService service;

    public BooksController(BookService service) {

        this.service = service;
    }


    @GetMapping
    public List<BookResource> list(){
        return service.findAll();
    }


    @GetMapping("{id}")
    public BookResource show(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResource> save(@RequestBody @Valid BookForm form){


        Book book = form.toEntity();

        BookResource resource = service.save(book);


        return ResponseEntity
                    .created(URI.create("/books/" + book.getId()))
                        .body(resource);

    }
}
