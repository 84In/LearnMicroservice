package com.trungtin.borrowingservice.command.event;

import com.trungtin.borrowingservice.command.data.Borrowing;
import com.trungtin.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BorrowingEventsHandler {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @EventHandler
    public void on(BorrowingCreatedEvent event) {
        Borrowing borrowing = new Borrowing();
        borrowing.setId(event.getId());
        borrowing.setBorrowingDate(event.getBorrowingDate());
        borrowing.setBookId(event.getBookId());
        borrowing.setEmployeeId(event.getEmployeeId());
        borrowingRepository.save(borrowing);
    }

    @EventHandler
    public void on(BorrowingDeletedEvent event) {
        Optional<Borrowing> borrowing = borrowingRepository.findById(event.getId());
        borrowing.ifPresent(borrowingRepository::delete);
    }
}
