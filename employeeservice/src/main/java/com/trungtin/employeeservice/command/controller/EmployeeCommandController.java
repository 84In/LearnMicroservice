package com.trungtin.employeeservice.command.controller;

import com.trungtin.employeeservice.command.command.CreateEmployeeCommand;
import com.trungtin.employeeservice.command.command.DeleteEmployeeCommand;
import com.trungtin.employeeservice.command.command.UpdateEmployeeCommand;
import com.trungtin.employeeservice.command.model.CreateEmployeeModel;
import com.trungtin.employeeservice.command.model.UpdateEmployeeModel;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addEmployee(@Valid @RequestBody CreateEmployeeModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand(UUID.randomUUID().toString(), model.getFirstName(),model.getLastName(), model.getKin(), false);
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{employeeId}")
    public String updateEmployee(@PathVariable String employeeId, @Valid @RequestBody UpdateEmployeeModel model) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(employeeId, model.getFirstName(),model.getLastName(), model.getKin(), model.getIsDisciplined());
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable String employeeId) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        return commandGateway.sendAndWait(command);
    }

}
