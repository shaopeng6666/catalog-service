package com.zsp.catalogservice.demo;

import com.zsp.catalogservice.domain.Book;
import com.zsp.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata")
public class BookDataLoader {

    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        bookRepository.deleteAll();
        var book1 = Book.of("1234567890", "Title 1", "Author 1", 9.90, "Publisher");
        var book2 = Book.of("1234567891", "Title 2", "Author 2", 19.90, "Publisher");
        bookRepository.saveAll(List.of(book1, book2));
    }
}
