package com.mgcfgs.amritsartourism.amritsar_tourism.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.BookingHistory;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {

}
