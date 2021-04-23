package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "causes")
public class Causes extends BaseEntity{

	private String name;
	
	private String description;
	
	private Integer budgetTarget;
	
	@OneToMany
	@JoinColumn(name = "donation_id")
	private Donation donation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(Integer budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public Donation getDonation() {
		return donation;
	}

	public void setDonation(Donation donation) {
		this.donation = donation;
	}
	
}