package com.trungtin.employeeservice.command.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeModel {

    @NotBlank(message = "Tên là bắt buộc")
    private String firstName;
    @NotBlank(message = "Họ là bắt buộc")
    private String lastName;
    @NotBlank(message = "Kin là bắt buộc")
    private String Kin;
}
