package com.trungtin.employeeservice.query.projection;

import com.trungtin.commonservice.model.EmployeeResponseCommonModel;
import com.trungtin.employeeservice.command.data.Employee;
import com.trungtin.employeeservice.command.data.EmployeeRepository;
import com.trungtin.employeeservice.query.model.EmployeeResponseModel;
import com.trungtin.employeeservice.query.queries.GetAllEmployeeQuery;
import com.trungtin.commonservice.queries.GetDetailEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {

    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query) {
        List<Employee> employees = employeeRepository.findByIsDisciplined(query.getIsDisciplined());
        return employees.stream().map(employee -> EmployeeResponseModel.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .Kin(employee.getKin())
                .isDisciplined(employee.getIsDisciplined())
                .build()).toList();
    }

    @QueryHandler
    public EmployeeResponseCommonModel handle(GetDetailEmployeeQuery query) {
        Employee employee = employeeRepository.findById(query.getId()).orElseThrow(()-> new RuntimeException("Không tìm thấy nhân viên có id" + query.getId()));
        EmployeeResponseCommonModel employeeResponseModel = new EmployeeResponseCommonModel();
        BeanUtils.copyProperties(employee, employeeResponseModel);
        return employeeResponseModel;
    }
}
