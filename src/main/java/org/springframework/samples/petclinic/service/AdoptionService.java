package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

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
	public Pair<Boolean, List<AdoptionApplication>> petInAdoption(Integer petId) {
		Boolean inAdoption = (adoptionRepository.petInAdoption(petId) != 0) ? true : false;
		List<AdoptionApplication> applicants = inAdoption ? 
				adoptionRepository.getApplicantsOfAdoption(petId) : new ArrayList<>();

		return Pair.of(inAdoption, applicants);
	}

	@Transactional()
	public void resolveApplication(Integer petId, Integer applicantId, String decision) {
		AdoptionApplication ap = adoptionRepository.findApplicationByOwnerAndPet(applicantId, petId);
		if (decision.equals("accept")) {
			ap.setAvailable(false);
			ap.setStatus(Status.ACCEPTED);
			petService.transferPet(petId, applicantId);
		} else {
			ap.setStatus(Status.DENIED);
		}
	}
}
