package com.nerfilin.library.service;

import com.nerfilin.library.dao.BookRepository;
import com.nerfilin.library.dao.CheckoutRepository;
import com.nerfilin.library.entity.Book;
import com.nerfilin.library.entity.Checkout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepo;

    private CheckoutRepository checkoutRepo;

    public BookService(BookRepository bookRepo, CheckoutRepository checkoutRepo) {
        this.bookRepo = bookRepo;
        this.checkoutRepo = checkoutRepo;
    }

    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepo.findById(bookId);

        Checkout validateCheckout = checkoutRepo.findByUserEmailAndBookId(userEmail, bookId);

        if(!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("El libro no existe o ya esta reservado.");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepo.save(book.get()); //persistencia copias

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );

        checkoutRepo.save(checkout); //persistencia reservas

        return book.get();
    }
}
