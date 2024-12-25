package com.trungtin.borrowingservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, String> {

}
