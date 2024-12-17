package com.trungtin.bookservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseModel {
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
