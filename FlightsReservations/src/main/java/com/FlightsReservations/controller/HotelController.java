package com.FlightsReservations.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.dto.RoomDTO;
import com.FlightsReservations.service.HotelService;

@RestController
@RequestMapping("/hotels")
@CrossOrigin("*")
public class HotelController {
	@Autowired
	private HotelService service;
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping(value="/getAll", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Collection<Hotel> getAll() {
		return service.findAll();
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> add(@RequestBody @Valid Hotel hotel) {
		Hotel h = service.create(hotel);
		if (h != null) {
			return new ResponseEntity<>(h, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(h, HttpStatus.CONFLICT);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> create(@RequestBody @Valid Hotel hotel) {
		Hotel h = service.create(hotel);
		if (h != null) {
			return new ResponseEntity<>(h, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(h, HttpStatus.CONFLICT);
	}
	
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody @Valid Hotel hotel) {
		if (service.update(hotel))
			return new ResponseEntity<>("Update successful", HttpStatus.OK);
		return new ResponseEntity<>("Hotel with given id does not exist", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value = "/addRoom", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addRoom(@RequestBody @Valid RoomDTO room) {
		if (service.addRoom(room))
			return new ResponseEntity<>(room, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}
