package com.FlightsReservations.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.AirlineAdminDTO;
import com.FlightsReservations.service.AirlineAdminService;

@RestController
@RequestMapping("/airlineAdmin")
@CrossOrigin("*")
public class AirlineAdminController {
	
	@Autowired
	AirlineAdminService service;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody AirlineAdminDTO dto) {
		dto = service.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Airline admin already exists.", HttpStatus.BAD_REQUEST);
	}
	
	@PreAuthorize("hasRole('admin')")
	@GetMapping(value = "/adminDetails/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAdmin(@PathVariable @NotBlank String email) {
		AirlineAdminDTO dto = service.findOne(email);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}