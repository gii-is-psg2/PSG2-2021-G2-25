package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;

public interface AdoptionRepository extends CrudRepository<AdoptionApplication, Integer> {
	
	@Query("SELECT ad from AdoptionApplication ad where ad.status = true")
	List<AdoptionApplication> findPetsInAdoption() throws DataAccessException;
	
	@Query("SELECT ad.applicants from AdoptionApplication ad where ad.status = true")
	List<Owner> findApplicants() throws DataAccessException;
	
}
