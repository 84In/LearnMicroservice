package com.trungtin.bookservice.query.projection;

import com.trungtin.bookservice.command.data.Book;
import com.trungtin.bookservice.command.data.BookRepository;
import com.trungtin.bookservice.query.model.BookResponseModel;
import com.trungtin.bookservice.query.queries.GetAllBookQuery;
import com.trungtin.commonservice.model.BookResponseCommonModel;
import com.trungtin.commonservice.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query) {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book->{
            BookResponseModel bookResponseModel = new BookResponseModel();
            BeanUtils.copyProperties(book, bookResponseModel);
            return bookResponseModel;
        }).toList();
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetBookDetailQuery query) throws Exception {
        BookResponseCommonModel bookResponseModel = new BookResponseCommonModel();
        Book book = bookRepository.findById(query.getId()).orElseThrow(()-> new Exception("Not found Book with BookId: "+query.getId()));
        BeanUtils.copyProperties(book, bookResponseModel);
        return bookResponseModel;
    }


}
