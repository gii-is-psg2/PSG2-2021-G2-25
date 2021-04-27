package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "causes")
public class Cause extends BaseEntity{

	private String name;
	
	private String description;
	
	private Integer budgetTarget;
	
	private Boolean targetReached;
	
	@OneToMany
	private Collection<Donation> donation;

	public String getName() {
		return name;
	}

	public Boolean getTargetReached() {
		return targetReached;
	}

	public void setTargetReached(Boolean targetReached) {
		this.targetReached = targetReached;
	}

	public Collection<Donation> getDonation() {
		return donation;
	}

	public void setDonation(Collection<Donation> donation) {
		this.donation = donation;
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
	
}