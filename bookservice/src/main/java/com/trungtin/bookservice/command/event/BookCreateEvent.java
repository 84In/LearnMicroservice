package com.trungtin.bookservice.command.event;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateEvent {
    private String id;
    private String name;
    private String author;
    private Boolean isReady;

}
