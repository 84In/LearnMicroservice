package com.trungtin.borrowingservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBorrowingCommand {
    @TargetAggregateIdentifier
    private String id;
}