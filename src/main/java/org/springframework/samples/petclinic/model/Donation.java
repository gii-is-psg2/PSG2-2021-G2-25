package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name= "donations" )
public class Donation {
	
	@NotNull
	@Min(1)
	private Double quantity;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate dateOfDonation;
	
	// ========= RELATIONS =========

//	 @ManyToOne
//	 private Cause cause;
	
	@ManyToOne
	private User client;

	
	// ======= Getters and Setters =======

	public Double getQuantity() {
		return quantity;
	}


	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}


	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}


	public LocalDate getDateOfDonation() {
		return dateOfDonation;
	}


	public void setDateOfDonation(LocalDate dateOfDonation) {
		this.dateOfDonation = dateOfDonation;
	}

//
//	public Cause getCause() {
//		return cause;
//	}
//
//
//	public void setCause(Cause cause) {
//		this.cause = cause;
//	}
	  

}
