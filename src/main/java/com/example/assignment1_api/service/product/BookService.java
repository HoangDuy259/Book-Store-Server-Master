package com.example.assignment1_api.service.product;


import com.example.assignment1_api.dto.request.product.book.BookCreateRequest;
import com.example.assignment1_api.dto.request.product.book.BookUpdateRequest;
import com.example.assignment1_api.dto.response.product.BookResponse;
import com.example.assignment1_api.entity.product.Book;
import com.example.assignment1_api.mapper.BookMapper;
import com.example.assignment1_api.repository.AuthorRepository;
import com.example.assignment1_api.repository.BookRepository;
import com.example.assignment1_api.repository.CategoryRepository;
import com.example.assignment1_api.repository.PublisherRepository;
import com.example.assignment1_api.utils.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;
    AuthorRepository authorRepository;
    CategoryRepository categoryRepository;
    PublisherRepository publisherRepository;
    StringUtils stringUtils;

    public BookResponse createBook(BookCreateRequest request) {

        String bookName = stringUtils.getExactName(request.getTitle());


        if (bookRepository.existsByName(bookName)) {
            throw new IllegalArgumentException("Book already exists!");
        }

        var author = authorRepository.findByName(stringUtils.getExactName(request.getAuthor()));
        if (author == null) {
            throw new IllegalArgumentException("Author not found");
        }
        var publisher = publisherRepository.findByName(stringUtils.getExactName(request.getPublisher()));
        if (publisher == null) {
            throw new IllegalArgumentException("Publisher not found");
        }
        var category = categoryRepository.findByName(stringUtils.getExactName(request.getCategory()));
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }

        log.info("Creating book: " + bookName);
        log.info("Author: " + author);
        log.info("Publisher: " + publisher);
        log.info("Category: " + category);

        var book = bookMapper.toBook(request);

        book.setName(bookName);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);

        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    public List<BookResponse> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookResponse).collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id){
        return bookMapper.toBookResponse(
                bookRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("Book not found")));
    }

    public BookResponse getBookByTitle(String title){
        String bookName = stringUtils.getExactName(title);
        return bookMapper.toBookResponse(
                bookRepository.findByName(bookName).orElseThrow(
                        () -> new IllegalArgumentException("Book not found")));
    }

    public boolean deleteBookByTitle(String title){
        String bookName = stringUtils.getExactName(title);
        log.info("Deleting book: " + bookName);
        Book book = bookRepository.findByName(bookName).orElseThrow(
                () -> new IllegalArgumentException("Book not found"));
        bookRepository.delete(book);
        return !bookRepository.existsById(book.getId());
    }

    public BookResponse updateBook(String title, BookUpdateRequest request){
        String bookName = stringUtils.getExactName(title);
        log.info("Updating book: " + bookName);
        Book book = bookRepository.findByName(bookName).orElseThrow(
                () -> new IllegalArgumentException("Book not found"));
        bookMapper.updateBook(book, request);

        var author = authorRepository.findByName(stringUtils.getExactName(request.getAuthor()));
        if (author == null) {
            throw new IllegalArgumentException("Author not found");
        }
        var publisher = publisherRepository.findByName(stringUtils.getExactName(request.getPublisher()));
        if (publisher == null) {
            throw new IllegalArgumentException("Publisher not found");
        }
        var category = categoryRepository.findByName(stringUtils.getExactName(request.getCategory()));
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }

        book.setName(stringUtils.getExactName(request.getTitle()));
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);

        return bookMapper.toBookResponse(bookRepository.save(book));
    }

}
