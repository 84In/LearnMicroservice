package com.trungtin.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponseCommonModel {
    private String id;
    private String firstName;
    private String lastName;
    private String Kin;
    private Boolean isDisciplined;
}
