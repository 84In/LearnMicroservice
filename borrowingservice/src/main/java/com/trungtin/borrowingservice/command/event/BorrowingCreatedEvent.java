package com.trungtin.borrowingservice.command.event;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingCreatedEvent {

    private String id;

    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;
}
