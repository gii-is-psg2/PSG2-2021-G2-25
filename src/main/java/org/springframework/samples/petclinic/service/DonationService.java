package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.samples.petclinic.service.exceptions.DonationProhibitedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {

	
	private DonationRepository donRepository;	
	private UserService userService;

	
	//CAUSES
	@Autowired
	public DonationService(DonationRepository donRepository, UserService userService) {
		this.donRepository = donRepository;
		this.userService = userService;
	}
	
	@Transactional(readOnly= true)
	public Donation findDonation(Integer id) throws DataAccessException {
		return donRepository.findById(id);
	}
	
	@Transactional(readOnly= true)
	public Collection<Donation> findDonations() throws DataAccessException {
		return donRepository.findDonations();
	}
	
	@Transactional(readOnly = true)
	public Collection<Donation> findDonationsForCause(Integer id) throws DataAccessException {
		return donRepository.findDonationsForCause(id);
	}
	
	@Transactional
	public void saveDonation(Donation donation, Cause cause) throws DonationProhibitedException {
		if(cause.getBudgetTarget() > (cause.getTotalAmount() + donation.getQuantity())) {
			donation.setDateOfDonation(LocalDate.now());
			donation.setCause(cause);
			String username = userService.obtenerUsername();
			
			if (userService.findUser(username).isPresent()) { //bug fix
				User user = userService.findUser(username).get();
				donation.setClient(user);			
				donRepository.save(donation);
			}
			
		 } else if(cause.getBudgetTarget() == (cause.getTotalAmount() + donation.getQuantity())) {
			cause.setTargetNotReached(Boolean.FALSE);
			donation.setDateOfDonation(LocalDate.now());
			donation.setCause(cause);
			String username = userService.obtenerUsername();
			
			if (userService.findUser(username).isPresent()) { //bug fix
				User user = userService.findUser(username).get();
				donation.setClient(user);			
				donRepository.save(donation);
			}
			
		 } else {
			 throw new DonationProhibitedException();
		 }
	}
}
