package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Status;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.AdoptionDuplicatedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/adoptions")
public class AdoptionController {

	private final AdoptionService adoptionService;
	private final PetService petService;
	private final OwnerService ownerService;
	
	@Autowired
	public AdoptionController(AdoptionService adoptionService, PetService petService, OwnerService ownerService) {
		this.adoptionService= adoptionService;
		this.petService = petService;
		this.ownerService = ownerService;
	}
	
	@GetMapping(value = { "/list" })
	public String petsInAdoptionList(Map<String, Object> model) {
		List<Pet> pets = adoptionService.findPetsInAdoption();
		model.put("pets", pets);
		return "adoptions/list";
	}
	
	@GetMapping(value = { "/{petId}/sendRequest"})
	public String initRequestOfAdoption(Map<String, Object> model, @PathVariable("petId") int petId, final Principal principal) {
		AdoptionApplication adoptionsApplication = new AdoptionApplication();
		Owner ownerActual = ownerService.findSessionOwner();
		Pet pet = petService.findPetById(petId);
		Owner owner = ownerService.findOwnerById(pet.getOwner().getId());
		model.put("adoptionsApplication", adoptionsApplication);
		if(ownerActual.equals(owner)) {
			return "exception";
		}
		return "adoptions/sendRequest";
	}
	
	@PostMapping(value = "/{petId}/sendRequest")
	public String processSendRequestForm(@Valid AdoptionApplication ad, BindingResult result,
			@PathVariable("petId") int petId, ModelMap model) {
		if(result.hasErrors()) {
			 model.put("adoptionsApplication", ad);
			 model.put("message", "La descripción debe contener entre 10 y 200 caracteres");
	         return "adoptions/sendRequest";
		}
		try {
			this.adoptionService.sendAdoptionRequest(ad, petId);
			return "/adoptions/confirmed";
		} catch (AdoptionDuplicatedException e) {
			return "/adoptions/list";
		}
		
	}
	
	@GetMapping("/{petId}/{decision}/{applicantId}")
	public String petsInAdoptionList(@PathVariable("petId") int petId, @PathVariable("applicantId") int applicantId, 
			@PathVariable("decision") String decision, ModelMap model) {
		
		adoptionService.resolveApplication(petId, applicantId, decision);
		return decision.equals("accept") ? "redirect:/owners/pets" : "redirect:/owners/pet/{petId}";
	}
}
