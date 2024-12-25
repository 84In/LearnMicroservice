package com.trungtin.commonservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRollbackStatusEvent {
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowingId;
}
