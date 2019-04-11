package com.FlightsReservations.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Date takeoffTime;

	@Column(nullable = false)
	private Date landingTime;

	@Column(nullable = false)
	private int flightTime;

	@Column(nullable = false)
	private double flightDistance;
	
	@Column(nullable = false)
	private double price;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airline airline;

	@OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Seat> seats = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "flight_stops", joinColumns = @JoinColumn(name = "flight_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "airport_id", referencedColumnName = "id"))
	private Set<Airport> stops = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airport start;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airport end;

	@OneToMany(mappedBy = "firstFlight")
	private Set<AirReservation> firstReservations = new HashSet<>();

	@OneToMany(mappedBy = "returnFlight")
	private Set<AirReservation> secondFlights = new HashSet<>();
	
	public Flight() {
		super();
	}

	public Flight(Date takeoffTime, Date landingTime, int flightTime, double flightDistance, double price
			, Airline airline, Airport start, Airport end, Set<Airport> stops) {
		super();
		this.takeoffTime = takeoffTime;
		this.landingTime = landingTime;
		this.flightTime = flightTime;
		this.flightDistance = flightDistance;
		this.price = price;
		this.airline = airline;
		this.start = start;
		this.end = end;
		this.stops = stops;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTakeoffTime() {
		return takeoffTime;
	}

	public void setTakeoffTime(Date takeoffTime) {
		this.takeoffTime = takeoffTime;
	}

	public Date getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(Date landingTime) {
		this.landingTime = landingTime;
	}

	public int getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	public double getFlightDistance() {
		return flightDistance;
	}

	public void setFlightDistance(double flightDistance) {
		this.flightDistance = flightDistance;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public Set<Airport> getStops() {
		return stops;
	}

	public void setStops(Set<Airport> stops) {
		this.stops = stops;
	}

	public Airport getStart() {
		return start;
	}

	public void setStart(Airport start) {
		this.start = start;
	}

	public Airport getEnd() {
		return end;
	}

	public void setEnd(Airport end) {
		this.end = end;
	}

	public Set<AirReservation> getFirstReservations() {
		return firstReservations;
	}

	public void setFirstReservations(Set<AirReservation> firstReservations) {
		this.firstReservations = firstReservations;
	}

	public Set<AirReservation> getSecondFlights() {
		return secondFlights;
	}

	public void setSecondFlights(Set<AirReservation> secondFlights) {
		this.secondFlights = secondFlights;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}