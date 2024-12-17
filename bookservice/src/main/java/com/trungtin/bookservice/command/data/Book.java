package com.trungtin.bookservice.command.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
