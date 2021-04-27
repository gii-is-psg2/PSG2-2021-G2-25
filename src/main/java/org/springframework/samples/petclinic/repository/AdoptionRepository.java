package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;

public interface AdoptionRepository extends CrudRepository<AdoptionApplication, Integer> {

	@Query("SELECT distinct ad.pet from AdoptionApplication ad where ad.available = true and ad.pet.owner != :owner and ad.owner != :owner")
	List<Pet> findPetsInAdoption(@Param("owner") Owner owner) throws DataAccessException;

	@Query("Select count(u) from AdoptionApplication u where u.pet.id = :petId and u.available = true")
	Integer petInAdoption(@Param("petId") Integer petId);

	@Query("Select u from AdoptionApplication u where u.pet.id = :petId and u.available = true and u.owner != :owner")
	List<AdoptionApplication> getApplicantsOfAdoption(@Param("petId") Integer petId, @Param("owner") Owner owner);

	@Query("Select u from AdoptionApplication u where u.pet.id = :petId and u.owner.id = :applicantId")
	AdoptionApplication findApplicationByOwnerAndPet(@Param("applicantId") Integer applicantId,
			@Param("petId") Integer petId);
	
	@Query("Select count(ad) from AdoptionApplication ad where ad.owner = :owner and ad.pet = :pet")
	Integer findNumberOfAdoptionsFromOwnerOfPet(@Param("owner") Owner owner, @Param("pet") Pet pet);
	
	@Modifying
	@Query("Update AdoptionApplication ad set ad.available = false, ad.status ='DENIED' where ad.pet.id = :petId")
	void resolvedAdoptionApplicant(@Param("petId") int petId);
}