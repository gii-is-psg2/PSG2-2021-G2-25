package org.springframework.samples.petclinic.web;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes")
public class CauseController {

	private static final String VIEW_CREATE_CAUSE="causes/createCause";

	private static final String VIEW_LIST_CAUSE="causes/causesList";

	private static final String VIEW_CREATE_DONATION = "donation/createDonationForm";	

	private final CauseService causeService;

	private final DonationService donationService;

	private final UserService userService;

	@Autowired
	public CauseController(CauseService causeService, DonationService donationService, UserService userService, OwnerService ownerService){
		this.causeService=causeService;
		this.donationService=donationService;
		this.userService=userService;
	}
	
	@GetMapping()
	public String causeActiveList(ModelMap modelMap) {
		List<Cause> causes = causeService.findCauseByTargetNotReached(true);
		modelMap.addAttribute("causes",causes);
		return VIEW_LIST_CAUSE;
	}

	@GetMapping(path="/inactive")
	public String causeInactiveList(ModelMap modelMap) {
		List<Cause> causes = causeService.findCauseByTargetNotReached(false);
		modelMap.addAttribute("causes",causes);
		return VIEW_LIST_CAUSE;
	}

	
	@GetMapping(path="/{causeId}")
	public String causeDetails(@PathVariable("causeId") final int causeId, ModelMap modelMap) {
		Cause cause = causeService.findCauseById(causeId);
		modelMap.addAttribute("cause",cause);
		return "causes/showCause";
	}
	
	
	@GetMapping(path="/new")
	public String newCause(ModelMap modelMap) {
		modelMap.addAttribute("cause", new Cause());
		return VIEW_CREATE_CAUSE;
	}
	
	@PostMapping(path="/save")
	public String saveCause(@Valid Cause cause, BindingResult result, ModelMap modelMap) {
		
		if(result.hasErrors()) {
			modelMap.addAttribute("cause",cause);
			return VIEW_CREATE_CAUSE;
		}else {
			causeService.save(cause);
			return "redirect:/causes";
		}
		
	}
	
	
	@GetMapping(path="{causeId}/newDonation")
	public String newDonation(@PathVariable("causeId") int causeId, ModelMap modelMap) {
		modelMap.addAttribute("donation", new Donation());
		Cause cause = this.causeService.findCauseById(causeId);
		modelMap.addAttribute("cause", cause);
		return VIEW_CREATE_DONATION;
	}

	@PostMapping(path="{causeId}/saveDonation")
	public String saveDonation(@PathVariable("causeId") final int causeId, @Valid Donation donation, BindingResult result, ModelMap modelMap) {
		
		if(result.hasErrors()) {
			modelMap.addAttribute("donation", donation);
			return VIEW_CREATE_DONATION;
		}else {
			Cause cause = this.causeService.findCauseById(causeId);
			donation.setCause(cause);
			User user=userService.getUser();
			//donation.setClient(user);
			donationService.saveDonation(donation, cause, user);
			causeService.save(cause);
			return "redirect:/causes";
		}
		
	}
	
	@GetMapping(path="{causeId}/delete")
	public String deleteCause(@PathVariable("causeId") int causeId, ModelMap modelMap) {
		String view =VIEW_LIST_CAUSE;
		Optional<Cause> cause = Optional.of(causeService.findCauseById(causeId));
		if(cause.isPresent()) {
			causeService.delete(cause.get());
			modelMap.addAttribute("message", "Se ha eliminado la causa");
			view = causeActiveList(modelMap);		
		}else {
			modelMap.addAttribute("message", "No se ha encontrado el elemento");
			view = causeActiveList(modelMap);
		}
		return view;
	}
	@GetMapping(path="{causeId}/edit")
	public String initUpdateCauseForm(@PathVariable("causeId") int causeId, ModelMap modelMap) {
		Cause cause = this.causeService.findCauseById(causeId);
		modelMap.addAttribute(cause);
		return VIEW_CREATE_CAUSE;
	}
	@PostMapping(path="{causeId}/save")
	public String processUpdateCauseForm(@Valid Cause cause, BindingResult result, @PathVariable("causeId") int causeId) {
		
		if(result.hasErrors()) {
			return VIEW_CREATE_CAUSE;
		}else {
			Cause causeToUpdate = causeService.findCauseById(causeId);
			BeanUtils.copyProperties(cause, causeToUpdate, "donations");
			causeService.save(causeToUpdate);
			return "redirect:/causes";
		}
	}
	
}