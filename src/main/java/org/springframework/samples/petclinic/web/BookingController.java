package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.BookingProhibitedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookings")
public class BookingController {
	
	private final BookingService bookingService;
	private final PetService petService;

	@Autowired
	public BookingController(BookingService bookingService, PetService petService) {
		this.bookingService = bookingService;
		this.petService = petService;
	}
	
	@GetMapping()
	public String showBookings(Model model) {

		List<Booking> bookings = bookingService.findBookingsByOwner();
		model.addAttribute("bookings", bookings);
		return "bookings/bookingList";
	}
	
	@GetMapping(value = "/new")
	public String selectPet(Model model) {

		List<Pet> pets = petService.findPetsByOwner();
		model.addAttribute("pets", pets);
		return "bookings/createBooking";
	}
	
	@PostMapping(value = "/new")
	public String createBooking(@Valid Pet pet, ModelMap model) {
		if(pet.getId() == null) {
			model.put("message", "You must select a pet to continue.");
			return "bookings/createBooking";
		}
		model.addAttribute("booking", new Booking());
		model.addAttribute("petId", pet.getId());		
		return "bookings/createBooking";
	}

	@PostMapping(value = "/new/{petId}")
	public String processBooking(@Valid Booking booking, BindingResult result,
			@PathVariable("petId") int petId, ModelMap model) {

		String vista;
		if (result.hasErrors()) {
			model.put("message", "Please, select a valid room and a valid date.");
			vista = "bookings/createBooking";
		} else {
			try {
				this.bookingService.saveBooking(booking, petId);
			} catch (BookingProhibitedException e) {

				model.put("message", "This room is already reserved. Please, try another.");
				return "bookings/createBooking";
			}
			vista = "redirect:/bookings/";
		}
		return vista;
	}
	
	@GetMapping(value = "/delete/{bookingId}")
	public String deleteBooking(Map<String, Object> model, @PathVariable("bookingId") int bookingId) {
		Booking booking = this.bookingService.findBookingById(bookingId);
		this.bookingService.deleteBooking(booking);
		return "redirect:/bookings";
	}

}