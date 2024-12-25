package com.trungtin.bookservice.command.event;

import com.trungtin.bookservice.command.data.Book;
import com.trungtin.bookservice.command.data.BookRepository;
import com.trungtin.commonservice.event.BookRollbackStatusEvent;
import com.trungtin.commonservice.event.BookUpdateStatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BookEventsHandler {

    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreateEvent event) {
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);

    }

    @EventHandler
    public void on(BookUpdatedEvent event) {
        Optional<Book> oldBook = bookRepository.findById(event.getId());
        oldBook.ifPresent(book -> {
            book.setName(event.getName());
            book.setAuthor(event.getAuthor());
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        });
    }

    @EventHandler
    public void on(BookDeletedEvent event) {
        try {
            bookRepository.findById(event.getId()).orElseThrow(()-> new RuntimeException("Không tìm thấy book id:" + event.getId()));
            bookRepository.deleteById(event.getId());
        }catch (RuntimeException e){
            log.error(e.getMessage());
        }
    }
    @EventHandler
    public void on(BookUpdateStatusEvent event) {
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());
        oldBook.ifPresent(book -> {
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        });
    }

    @EventHandler
    public void on(BookRollbackStatusEvent event) {
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());
        oldBook.ifPresent(book -> {
            book.setIsReady(event.getIsReady());
            bookRepository.save(book);
        });
    }
}
