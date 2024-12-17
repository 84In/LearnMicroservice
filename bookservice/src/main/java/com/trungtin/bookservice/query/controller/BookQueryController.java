package com.trungtin.bookservice.query.controller;

import com.trungtin.bookservice.query.model.BookResponseModel;
import com.trungtin.bookservice.query.queries.GetAllBookQuery;
import com.trungtin.bookservice.query.queries.GetBookDetailQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBookQuery getAllBookQuery = new GetAllBookQuery();
        return queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
    }
    @GetMapping("{bookId}")
    public BookResponseModel getBookDetail(@PathVariable("bookId") String bookId) {
        GetBookDetailQuery getBookDetailQuery = new GetBookDetailQuery(bookId);
        return queryGateway.query(getBookDetailQuery, ResponseTypes.instanceOf(BookResponseModel.class)).join();
    }
}
