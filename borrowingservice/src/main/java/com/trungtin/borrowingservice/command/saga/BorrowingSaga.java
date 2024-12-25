package com.trungtin.borrowingservice.command.saga;

import com.trungtin.borrowingservice.command.command.DeleteBorrowingCommand;
import com.trungtin.borrowingservice.command.event.BorrowingCreatedEvent;
import com.trungtin.borrowingservice.command.event.BorrowingDeletedEvent;
import com.trungtin.commonservice.command.RollbackStatusBookCommand;
import com.trungtin.commonservice.command.UpdateStatusBookCommand;
import com.trungtin.commonservice.event.BookRollbackStatusEvent;
import com.trungtin.commonservice.event.BookUpdateStatusEvent;
import com.trungtin.commonservice.model.BookResponseCommonModel;
import com.trungtin.commonservice.model.EmployeeResponseCommonModel;
import com.trungtin.commonservice.queries.GetBookDetailQuery;
import com.trungtin.commonservice.queries.GetDetailEmployeeQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@Slf4j
public class BorrowingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowingCreatedEvent event) {
      log.info("Borrowing created event in saga for BookId: {} : EmployeeId: {}", event.getBookId(), event.getEmployeeId());

      try {
          GetBookDetailQuery getBookDetailQuery = new GetBookDetailQuery(event.getBookId());
          BookResponseCommonModel bookResponseCommonModel = queryGateway
                  .query(getBookDetailQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
          if (!bookResponseCommonModel.getIsReady()) {
              throw new Exception("Sach da co nguoi muon");
          }else{
              SagaLifecycle.associateWith("bookId", event.getBookId());
              UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false, event.getEmployeeId(), event.getId());
              commandGateway.sendAndWait(command);
          }

      } catch (Exception e) {
          rollbackBorrowingRecord(event.getId());
          log.error(e.getMessage());
      }

    }

    @SagaEventHandler(associationProperty = "bookId")
    public void handle(BookUpdateStatusEvent event){
        log.info("Book update status event in saga for BookId: {}", event.getBookId());
        try {
            GetDetailEmployeeQuery query = new GetDetailEmployeeQuery(event.getEmployeeId());
            EmployeeResponseCommonModel employeeModel = queryGateway.query(query, ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
            if(employeeModel.getIsDisciplined()){
                throw new Exception("Nhan vien bi ky luat");
            }else {
                log.info("Da muon sach thanh cong!");
            }
            SagaLifecycle.end();
        } catch (Exception e) {
            rollbackBookStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowingId());
            log.error(e.getMessage());
        }

    }


    private void rollbackBorrowingRecord(String id) {
        DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
        commandGateway.sendAndWait(command);
    }

    private void rollbackBookStatus(String bookId, String employeeId, String borrowingId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollbackStatusBookCommand command = new RollbackStatusBookCommand(bookId, true, employeeId, borrowingId);
        commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookRollbackStatusEvent event){
        log.info("Book rollback status event in saga for BookId: {}", event.getBookId());
        rollbackBorrowingRecord(event.getBorrowingId());
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(BorrowingDeletedEvent event){
        log.info("BorrowingDeletedEvent in Saga for borrowing id: {}", event.getId());
        SagaLifecycle.end();
    }
}
