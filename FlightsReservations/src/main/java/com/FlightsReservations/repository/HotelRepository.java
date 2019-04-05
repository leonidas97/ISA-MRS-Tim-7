package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{
}