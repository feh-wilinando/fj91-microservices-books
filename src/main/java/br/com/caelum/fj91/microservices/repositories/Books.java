package br.com.caelum.fj91.microservices.repositories;

import br.com.caelum.fj91.microservices.models.Book;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface Books extends Repository<Book, Long> {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    void save(Book book);
}
