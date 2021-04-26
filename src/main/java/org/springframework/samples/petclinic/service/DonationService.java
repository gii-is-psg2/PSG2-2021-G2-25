package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Causes;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {

	
	private DonationRepository donRepository;
	
//	@Autowired
//	private CausesService causeService;
	
	//CAUSES
	@Autowired
	public DonationService(DonationRepository donRepository) {
		this.donRepository = donRepository;
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
	public void saveDonation(Donation donation, Causes cause, User user) throws DataAccessException {
		//CAUSE TARGET IS NOT REACHED
		//if(cause.getBudgetTarget()< cause. + donation.getAmount())
			donation.setDateOfDonation(LocalDate.now());
			donation.setCause(cause);
			donation.setClient(user);
			donRepository.save(donation);
		// } else {
			//Set cause not active
			//donation.setDonationDate(LocalDate.now());
			//donationRepository.save(donation);	
	}
}
