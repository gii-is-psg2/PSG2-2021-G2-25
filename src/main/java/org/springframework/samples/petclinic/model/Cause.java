package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "causes")
public class Cause extends BaseEntity {

	@NotBlank
	@Length(max=50, message = "No puede tener más de 50 caracteres.")
	private String name;

	@NotBlank
	@Length(max=120, message = "No puede tener más de 150 caracteres.")
	private String description;

	@Min(value = 5, message = "El mínimo objetivo es 5.")
	private Double budgetTarget;

	@NotNull
	private Boolean targetNotReached = false;

	@NotBlank
	@Length(max=50, message = "No puede tener más de 50 caracteres.")
	private String ong;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause")
	private Collection<Donation> donations;

	public String getName() {
		return name;
	}

	public Boolean getTargetNotReached() {
		return targetNotReached;
	}

	public void setTargetNotReached(Boolean targetNotReached) {
		this.targetNotReached = targetNotReached;
	}

	public Collection<Donation> getDonations() {
		return donations;
	}

	public void setDonations(Collection<Donation> donation) {
		this.donations = donation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOng(String ong) {
		this.ong = ong;
	}

	public String getOng() {
		return ong;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Double getTotalAmount() {
		Double sumDonations = 0.0;
		if (donations != null && donations.isEmpty()) {
			for (Donation donation : donations) {
				if (donation.getCause().getName().equals(name)) {
					sumDonations = sumDonations + donation.getQuantity();
				}
			}
		}
		return Math.round(sumDonations * 100.0) / 100.0;
	}

}