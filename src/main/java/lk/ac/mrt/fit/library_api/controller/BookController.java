package lk.ac.mrt.fit.library_api.controller;

import java.util.List;

import jakarta.validation.Valid;
import lk.ac.mrt.fit.library_api.model.Book;
import lk.ac.mrt.fit.library_api.service.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management", description = "APIs for managing books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Create a new book
    @PostMapping
    @Operation(
        summary = "Create a new book",
        description = "Add a new book to the library"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Book created successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid book details"
    )
    public ResponseEntity<Book> createBook(
            @Valid @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(bookService.createBook(book));
    }

    // Update a book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}