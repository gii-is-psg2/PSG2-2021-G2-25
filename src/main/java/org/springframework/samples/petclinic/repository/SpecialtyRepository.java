package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;

public interface SpecialtyRepository extends Repository<Specialty, Integer>{

	void save(Specialty specialty) throws DataAccessException;
	
	
	@Query("SELECT specialty FROM Specialty specialty")
	public Collection<Specialty> findSpecialtys();
	
	
	@Query("SELECT specialty FROM Specialty specialty WHERE specialty.name LIKE ?1")
    public Specialty findSpecialty(String s);
}
