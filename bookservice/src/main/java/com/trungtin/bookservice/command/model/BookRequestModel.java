package com.trungtin.bookservice.command.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestModel {
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
