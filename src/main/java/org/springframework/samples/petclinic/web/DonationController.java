package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.service.exceptions.DonationProhibitedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DonationController {

	private final CauseService causeService;
	private final DonationService donService;

	private static final String VIEWS_DONATION_CREATE_FORM = "donation/createDonationForm";

	@Autowired
	public DonationController(DonationService donService, CauseService causeService) {
		this.causeService = causeService;
		this.donService = donService;
	}

	@GetMapping(value = { "/donations" })
	public @ResponseBody Collection<Donation> showDonationList(Map<String, Object> model) {

		Collection<Donation> donations = this.donService.findDonations();
		return donations;

	}

	@GetMapping(value = { "causes/{causesId}/donations" })
	public @ResponseBody Collection<Donation> showDonationForCauseList(Map<String, Object> model, Integer causeID) {

		 
		Collection<Donation> donations = this.donService.findDonationsForCause(causeID);
		return donations;

	}

	@GetMapping(value = "/donation/{causeId}/new")
	public String initCreationForm(Map<String, Object> model, @PathVariable("causeId") final int causeId) {
		model.put("CauseId", causeId);
		Donation donation = new Donation();
		model.put("donation", donation);

		return VIEWS_DONATION_CREATE_FORM;
	}

	@PostMapping(value = "/donation/{causeId}/new")
	public String processCreationForm(@Valid Donation donation, BindingResult result,
			@PathVariable("causeId") final int causeId) {
		if (result.hasErrors()) {
			return VIEWS_DONATION_CREATE_FORM;
		} else if (donation.getQuantity() < 0.01) {
			result.rejectValue("quantity", "Exceeded", "El importe mínimo para realizar una donación debe ser 0.01");
			return VIEWS_DONATION_CREATE_FORM;
		} else {
			Cause cause = causeService.findCauseById(causeId);
			try {
				this.donService.saveDonation(donation, cause);
				return "redirect:/causes/" + causeId;
			} catch (DonationProhibitedException e) {
				result.rejectValue("quantity", "Exceeded", "Valor objetivo sobrepasado, introduce una cantidad válida");
				return VIEWS_DONATION_CREATE_FORM;
			}

		}
	}
}
