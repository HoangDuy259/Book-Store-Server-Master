package com.example.assignment1_api.controller;

import com.example.assignment1_api.dto.request.product.book.BookCreateRequest;
import com.example.assignment1_api.dto.request.product.book.BookUpdateRequest;
import com.example.assignment1_api.dto.response.ApiResponse;
import com.example.assignment1_api.dto.response.product.BookResponse;
import com.example.assignment1_api.service.product.BookService;
import com.example.assignment1_api.utils.FeedBackMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {
    BookService bookService;
    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@RequestBody BookCreateRequest request) {
        try {
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse<>(FeedBackMessage.SUCCESS, bookService.createBook(request)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks() {
        try{
            return ResponseEntity.ok(new ApiResponse<>(FeedBackMessage.SUCCESS, bookService.getAllBooks()));
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    @GetMapping("/get-book/{title}")
    public ResponseEntity<ApiResponse<BookResponse>> getBook(@PathVariable("title") String title) {
        try{
            return ResponseEntity.ok(new ApiResponse<>(FeedBackMessage.SUCCESS, bookService.getBookByTitle(title)));
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    @PutMapping("/update-book/{title}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable("title") String title, @RequestBody BookUpdateRequest request) {
        try{
            return ResponseEntity.ok(new ApiResponse<>(FeedBackMessage.SUCCESS, bookService.updateBook(title, request)));
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-book/{title}")
    public ResponseEntity<ApiResponse<Boolean>> deleteBook(@PathVariable("title") String title) {
        try{
            return ResponseEntity.ok(new ApiResponse<>(FeedBackMessage.SUCCESS, bookService.deleteBookByTitle(title)));
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

}
