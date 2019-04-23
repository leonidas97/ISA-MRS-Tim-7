package com.FlightsReservations.domain;


import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Hotel extends Company
{
	@NotNull
	private ArrayList<PricelistItem> pricelist;
	
	
	@NotNull
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
	private Set<Room> roomConfiguration;
	
	@NotNull
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
	private Set<HotelReservation> reservations;
	
	public Hotel() {
		super();
	}

	public Hotel(String name, Float longitude, Float latitude, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, promoDescription, avarageScore, numberOfVotes);
	}


	public ArrayList<PricelistItem> getPricelist() {
		return pricelist;
	}

	public void setPricelist(ArrayList<PricelistItem> pricelist) {
		this.pricelist = pricelist;
	}

	public Set<Room> getRoomConfiguration()
	{
		return roomConfiguration;
	}

	public void setRoomConfiguration(Set<Room> roomConfiguration)
	{
		this.roomConfiguration = roomConfiguration;
	}

	public Set<HotelReservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<HotelReservation> reservations) {
		this.reservations = reservations;
	}

	
}
