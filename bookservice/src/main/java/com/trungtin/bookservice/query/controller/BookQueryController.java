package com.trungtin.bookservice.query.controller;

import com.trungtin.bookservice.query.model.BookResponseModel;
import com.trungtin.bookservice.query.queries.GetAllBookQuery;
import com.trungtin.commonservice.model.BookResponseCommonModel;
import com.trungtin.commonservice.queries.GetBookDetailQuery;
import com.trungtin.commonservice.services.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBookQuery getAllBookQuery = new GetAllBookQuery();
        return queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
    }
    @GetMapping("{bookId}")
    public BookResponseCommonModel getBookDetail(@PathVariable("bookId") String bookId) {
        GetBookDetailQuery getBookDetailQuery = new GetBookDetailQuery(bookId);
        return queryGateway.query(getBookDetailQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message){
        kafkaService.sendMessage("test", message);
    }
}
