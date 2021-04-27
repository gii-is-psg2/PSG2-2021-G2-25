package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DonationController {
	
	
	//private final CausesService causeService;
	private final DonationService donService;
	
	private static final String VIEWS_DONATION_CREATE_FORM = "donation/createDonationForm";
	
	//TODO CAUSES SERVICE
	@Autowired
	public DonationController(DonationService donService) {
		this.donService = donService;
	}
	
	@GetMapping(value = { "/donations" })
	public @ResponseBody Collection<Donation> showDonationList(Map<String, Object> model) {
		
		Collection<Donation> donations = new HashSet<>();
		donations = this.donService.findDonations();
		return donations;
		
	}
	
	
	@GetMapping(value = { "causes/{causesId}/donations" })
	public @ResponseBody Collection<Donation> showDonationForCauseList(Map<String, Object> model, Integer causeID) {
		
		Collection<Donation> donations = new HashSet<>();
		donations = this.donService.findDonationsForCause(causeID);
		return donations;
		
	}

	
	
	@GetMapping(value = "/donation/new")
	public String initCreationForm(Map<String, Object> model) {
		Donation donation = new Donation();
		//TODO add donation to cause
		model.put("donation", donation);

		return VIEWS_DONATION_CREATE_FORM;
	}
	
	
	@PostMapping(value = "/donation/new")
	public String processCreationForm(@Valid Donation donation, BindingResult result, @RequestParam() Cause cause) {
		if (result.hasErrors()) {
			return VIEWS_DONATION_CREATE_FORM;
		}
		else {
			//TODO ADD USER 
			//ADD DONATION TO CAUSE
			this.donService.saveDonation(donation, cause, null );
			int causeId = cause.getId();
			return "redirect:/causes/"+ causeId;
		}
	}
}
