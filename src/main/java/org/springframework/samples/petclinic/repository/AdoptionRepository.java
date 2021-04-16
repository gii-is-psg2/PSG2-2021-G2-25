package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.AdoptionApplication;

public interface AdoptionRepository extends CrudRepository<AdoptionApplication, Integer> {

	@Query("SELECT ad from AdoptionApplication ad where ad.available = true")
	List<AdoptionApplication> findPetsInAdoption() throws DataAccessException;

	@Query("Select count(u) from AdoptionApplication u where u.pet.id = :petId and u.available = true")
	Integer petInAdoption(@Param("petId") Integer petId);

	@Query("Select u from AdoptionApplication u where u.pet.id = :petId and u.available = true")
	List<AdoptionApplication> getApplicantsOfAdoption(@Param("petId") Integer petId);

	@Query("Select u from AdoptionApplication u where u.pet.id = :petId and u.owner.id = :applicantId")
	AdoptionApplication findApplicationByOwnerAndPet(@Param("applicantId") Integer applicantId,
			@Param("petId") Integer petId);
}
