package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SpecialtyService {
	
	private SpecialtyRepository specRepository;
	
	private VetService vetService;
	
	@Autowired
	public SpecialtyService(SpecialtyRepository specRepository, VetService vetService) {
		this.specRepository = specRepository;
		this.vetService = vetService;
	}
	
	@Transactional(readOnly = true)	
	public Collection<Specialty> findAllSpecialtys() throws DataAccessException {
		return specRepository.findSpecialtys();
	}
	
	
	
	@Transactional
    public void saveSpecialties(Vet vet, List<String> specialties) throws DataAccessException {
        Set<Specialty> specialitiesSet = new HashSet<>();
        if (specialties != null) {
        	 for (String specialty : specialties) {
                 Specialty sp = this.specRepository.findSpecialty(specialty);
                 specialitiesSet.add(sp);
             }
        }    	
        vet.setSpecialtiesInternal(specialitiesSet);
        vetService.saveVet(vet);
    }
	
}
