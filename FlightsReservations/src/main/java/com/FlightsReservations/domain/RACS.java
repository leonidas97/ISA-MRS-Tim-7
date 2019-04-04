package com.FlightsReservations.domain;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class RACS {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	@NotBlank
	private String address;
	@NotBlank
	private String description;
	@NotNull	
	private ArrayList<PricelistItem> pricelist;
	
	@NotNull
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Car> cars;
	
	@NotNull
	private ArrayList<String> branchOffices;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<PricelistItem> getPricelist() {
		return pricelist;
	}
	public void setPricelist(ArrayList<PricelistItem> pricelist) {
		this.pricelist = pricelist;
	}
	public Set<Car> getCars() {
		return cars;
	}
	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}
	public ArrayList<String> getBranchOffices() {
		return branchOffices;
	}
	public void setBranchOffices(ArrayList<String> branchOffices) {
		this.branchOffices = branchOffices;
	}
	public RACS(String name, String address, String description, ArrayList<PricelistItem> pricelist,
			Set<Car> cars, ArrayList<String> branchOffices) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.pricelist = pricelist;
		this.cars = cars;
		this.branchOffices = branchOffices;
	}
	public RACS() {
		super();
	}
	
		
	
}
