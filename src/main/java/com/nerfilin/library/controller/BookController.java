package com.nerfilin.library.controller;

import com.nerfilin.library.entity.Book;
import com.nerfilin.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/secure/checkout", method = RequestMethod.PUT)
    public Book checkoutBook(@RequestParam Long bookId) throws  Exception {
        String userEmail = "testuser@email.com";
        return bookService.checkoutBook(userEmail, bookId);
    }
}
