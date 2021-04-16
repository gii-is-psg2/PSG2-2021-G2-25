package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {
	
	@Autowired
	private AdoptionRepository adoptionRepository;
	private PetService petService;
	
	@Autowired
	public AdoptionService(AdoptionRepository adoptionRepository, PetService petService) {
		this.adoptionRepository = adoptionRepository;
		this.petService = petService;
	}
	
	@Transactional(readOnly = true)
	public List<AdoptionApplication> findPetsInAdoption() {
		return adoptionRepository.findPetsInAdoption();
	}	
	
	@Transactional(readOnly = true)
	public List<Owner> findApplicants() {
		return adoptionRepository.findApplicants();
	}	
	
}
