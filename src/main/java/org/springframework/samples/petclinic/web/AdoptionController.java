package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.AdoptionApplication;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.AdoptionDuplicatedException;
import org.springframework.samples.petclinic.service.exceptions.AdoptionProhibitedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adoptions")
public class AdoptionController {

	private final AdoptionService adoptionService;
	private final PetService petService;
	private final OwnerService ownerService;

	@Autowired
	public AdoptionController(AdoptionService adoptionService, PetService petService, OwnerService ownerService) {
		this.adoptionService = adoptionService;
		this.petService = petService;
		this.ownerService = ownerService;
	}

	@GetMapping(value = { "/list" })
	public String petsInAdoptionList(Map<String, Object> model) {
		List<Pet> pets = adoptionService.findPetsInAdoption();
		model.put("pets", pets);
		return "adoptions/list";
	}

	@GetMapping(value = { "/{petId}/sendRequest" })
	public String initRequestOfAdoption(Map<String, Object> model, @PathVariable("petId") int petId,
			final Principal principal) {
		AdoptionApplication adoptionsApplication = new AdoptionApplication();
		Owner ownerActual = ownerService.findSessionOwner();
		Pet pet = petService.findPetById(petId);
		Owner owner = ownerService.findOwnerById(pet.getOwner().getId());
		model.put("adoptionsApplication", adoptionsApplication);
		if (ownerActual.equals(owner)) {
			return "exception";
		}
		return "adoptions/sendRequest";
	}

	@PostMapping(value = "/{petId}/sendRequest")
	public String processSendRequestForm(@Valid AdoptionApplication ad, BindingResult result,
			@PathVariable("petId") int petId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("adoptionsApplication", ad);
			model.put("message", "La descripción debe contener entre 10 y 200 caracteres");
			return "adoptions/sendRequest";
		}
		try {
			this.adoptionService.sendAdoptionRequest(ad, petId);
			return "/adoptions/confirmed";
		} catch (AdoptionDuplicatedException e) {
			model.put("message", "Ya has enviado una solicitud para esta mascota, no puedes solicitarla de nuevo");
			return petsInAdoptionList(model);
		}
	}

	@GetMapping("/{petId}/{decision}/{applicantId}")
	public String resolveApplication(@PathVariable("petId") int petId, @PathVariable("applicantId") int applicantId,
			@PathVariable("decision") String decision, ModelMap model) {

		adoptionService.resolveApplication(petId, applicantId, decision);
		return decision.equals("accept") ? "redirect:/owners/pets" : "redirect:/owners/pet/{petId}";
	}

	@GetMapping("/{petId}")
	public String putInAdoption(@PathVariable("petId") int petId, ModelMap model) {

		try {
			adoptionService.putInAdoption(petId);
		} catch (AdoptionProhibitedException e) {
			model.put("message", "No puedes poner en adopción una mascota que no es tuya");
			return "redirect:/owners/pet/{petId}";
		}
		return "redirect:/owners/pet/{petId}";
	}
}