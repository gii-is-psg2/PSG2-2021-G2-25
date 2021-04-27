package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.samples.petclinic.service.exceptions.AdoptionDuplicatedException;
import org.springframework.samples.petclinic.service.exceptions.AdoptionProhibitedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

	private AdoptionRepository adoptionRepository;
	private PetService petService;
	private OwnerService ownerService;

	@Autowired
	public AdoptionService(AdoptionRepository adoptionRepository, PetService petService
			, OwnerService ownerService) {
		this.adoptionRepository = adoptionRepository;
		this.petService = petService;
		this.ownerService = ownerService;
	}

	@Transactional(readOnly = true)
	public List<Pet> findPetsInAdoption() {
		return adoptionRepository.findPetsInAdoption(ownerService.findSessionOwner());
	}

	@Transactional(readOnly = true)
	public Boolean petInAdoption(Integer petId) {
		return (adoptionRepository.petInAdoption(petId) != 0) ? true : false;
	}

	@Transactional(readOnly = true)
	public List<AdoptionApplication> AdoptionApplicants(Integer petId) {
		Boolean inAdoption = petInAdoption(petId);
		List<AdoptionApplication> applicants = inAdoption ? 
				adoptionRepository.getApplicantsOfAdoption(petId, ownerService.findSessionOwner()) : new ArrayList<>();

		return applicants;
	}

	@Transactional()
	public void resolveApplication(Integer petId, Integer applicantId, String decision) {
		AdoptionApplication ap = adoptionRepository.findApplicationByOwnerAndPet(applicantId, petId);
		if (decision.equals("accept")) {
    		this.adoptionRepository.resolvedAdoptionApplicant(petId);
			ap.setAvailable(false);
			ap.setStatus(Status.ACCEPTED);
			petService.transferPet(petId, applicantId);
		} else {
			ap.setStatus(Status.DENIED);
		}
	}
	@Transactional()
	public void sendAdoptionRequest(AdoptionApplication adoption, int petId) throws AdoptionDuplicatedException{
		Pet pet = petService.findPetById(petId);
		Owner owner = ownerService.findSessionOwner();
		adoption.setAvailable(true);
		adoption.setPet(pet);
		adoption.setOwner(owner);
		adoption.setStatus(Status.ON_HOLD);
		Integer adoptionsOfPet = this.adoptionRepository.findNumberOfAdoptionsFromOwnerOfPet(owner, pet);
		if(adoptionsOfPet != 0) {
			throw new AdoptionDuplicatedException();
		}
		this.adoptionRepository.save(adoption);
	}

	@Transactional()
	public void putInAdoption(Integer petId) throws AdoptionProhibitedException{
		Pet pet = petService.findPetById(petId);
		Owner owner = ownerService.findSessionOwner();

		if(!pet.getOwner().equals(owner)) {
			throw new AdoptionProhibitedException();			
		}
		AdoptionApplication ap = new AdoptionApplication();
		ap.setAvailable(true);
		ap.setDescription("This is the description of a owner");
		ap.setOwner(owner);
		ap.setPet(pet);
		ap.setStatus(Status.ON_HOLD);
		
		adoptionRepository.save(ap);
	}
}
