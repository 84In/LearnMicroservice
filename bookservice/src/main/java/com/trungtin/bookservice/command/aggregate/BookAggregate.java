package com.trungtin.bookservice.command.aggregate;

import com.trungtin.bookservice.command.command.CreateBookCommand;
import com.trungtin.bookservice.command.command.DeleteBookCommand;
import com.trungtin.bookservice.command.command.UpdateBookCommand;
import com.trungtin.bookservice.command.event.BookCreateEvent;
import com.trungtin.bookservice.command.event.BookDeletedEvent;
import com.trungtin.bookservice.command.event.BookUpdatedEvent;
import com.trungtin.commonservice.command.RollbackStatusBookCommand;
import com.trungtin.commonservice.command.UpdateStatusBookCommand;
import com.trungtin.commonservice.event.BookRollbackStatusEvent;
import com.trungtin.commonservice.event.BookUpdateStatusEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Getter
@Setter
@NoArgsConstructor
public class BookAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean isReady;


    @CommandHandler
    public BookAggregate(CreateBookCommand command) {
        BookCreateEvent bookCreateEvent = new BookCreateEvent();
        BeanUtils.copyProperties(command, bookCreateEvent);
        AggregateLifecycle.apply(bookCreateEvent);

    }

    @CommandHandler
    public void handle(UpdateBookCommand command) {
        BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();
        BeanUtils.copyProperties(command, bookUpdatedEvent);
        AggregateLifecycle.apply(bookUpdatedEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand command) {
        BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
        BeanUtils.copyProperties(command, bookDeletedEvent);
        AggregateLifecycle.apply(bookDeletedEvent);
    }

    @CommandHandler
    public void handle(UpdateStatusBookCommand command) {
        BookUpdateStatusEvent bookUpdateStatusEvent = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(command, bookUpdateStatusEvent);
        AggregateLifecycle.apply(bookUpdateStatusEvent);
    }

    @CommandHandler
    public void handle(RollbackStatusBookCommand command) {
        BookRollbackStatusEvent bookRollbackStatusEvent = new BookRollbackStatusEvent();
        BeanUtils.copyProperties(command, bookRollbackStatusEvent);
        AggregateLifecycle.apply(bookRollbackStatusEvent);
    }

    @EventSourcingHandler
    public void on(BookCreateEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookDeletedEvent event) {
        this.id = event.getId();
    }

    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event) {
        this.id = event.getBookId();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookRollbackStatusEvent event) {
        this.id = event.getBookId();
        this.isReady = event.getIsReady();
    }
}
