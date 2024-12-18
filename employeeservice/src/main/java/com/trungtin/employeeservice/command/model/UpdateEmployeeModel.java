package com.trungtin.employeeservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeModel {
    @NotBlank(message = "Tên là bắt buộc")
    private String firstName;
    @NotBlank(message = "Họ là bắt buộc")
    private String lastName;
    @NotBlank(message = "Kin là bắt buộc")
    private String Kin;

    @NotNull(message = "Không thể bỏ trống")
    private Boolean isDisciplined;

}
