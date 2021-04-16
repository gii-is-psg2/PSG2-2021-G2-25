package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		List<AdoptionApplication> pets = adoptionService.findPetsInAdoption();
		model.put("pets", pets);
		return "adoptions/list";
	}
	
	@GetMapping(value = { "/{petId}/sendRequest"})
	public String sendRequestOfAdoption(Map<String, Object> model, @PathVariable("petId") int petId, final Principal principal) {
		Owner ownerActual = ownerService.findSessionOwner();
		Pet pet = petService.findPetById(petId);
		Owner owner = ownerService.findOwnerById(pet.getOwner().getId());
		if(ownerActual.equals(owner)) {
			return "exception";
		}
		return "adoptions/sendRequest";
	}
	
	@GetMapping("/{petId}/{decision}/{applicantId}")
	public String petsInAdoptionList(@PathVariable("petId") int petId, @PathVariable("applicantId") int applicantId, 
			@PathVariable("decision") String decision, ModelMap model) {
		
		adoptionService.resolveApplication(petId, applicantId, decision);
		return decision.equals("accept") ? "redirect:/owners/pets" : "redirect:/owners/pet/{petId}";
	}
}
