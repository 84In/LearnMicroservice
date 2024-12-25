package com.trungtin.borrowingservice.command.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "borrowing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Borrowing {

    @Id
    String id;

    String bookId;
    String employeeId;
    Date borrowingDate;
    Date returnDate;

}
