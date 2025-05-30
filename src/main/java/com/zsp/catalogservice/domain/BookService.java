package com.zsp.catalogservice.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetail(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetail(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn).map(existingBook -> {
            var bookForUpdate = new Book(
                    existingBook.isbn(),
                    book.title(),
                    book.author(),
                    book.price());
            return bookRepository.save(bookForUpdate);
        }).orElseGet(() -> bookRepository.save(book));
    }
}
