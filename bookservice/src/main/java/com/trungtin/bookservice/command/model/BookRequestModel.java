package com.trungtin.bookservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestModel {
    private String id;

    @NotBlank(message = "Tên là bắt buộc")
    @Size(min = 2, max = 50, message = "Tên phải bắt buộc từ 2 đến 30 kí tự")
    private String name;

    @NotBlank(message = "Tác giả là bắt buộc")
    private String author;

    private Boolean isReady;
}
