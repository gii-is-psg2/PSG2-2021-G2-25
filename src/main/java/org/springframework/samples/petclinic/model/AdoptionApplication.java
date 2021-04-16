package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "applications")
public class AdoptionApplication extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "application_applicants", joinColumns = @JoinColumn(name = "application_id"),
			inverseJoinColumns = @JoinColumn(name = "owner_id"))
	private List<Owner> applicants;
	
	@Column(name = "description")
    @Size(min = 10, max = 200)
	private String description;
	
	@Column(name = "status")
	@NotNull
	private Boolean status;
	
	@Column(name = "available")
	@NotNull
	private Boolean available;

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public List<Owner> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Owner> applicants) {
		this.applicants = applicants;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
