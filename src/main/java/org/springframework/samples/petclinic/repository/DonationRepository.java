package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Donation;

public interface DonationRepository extends Repository<Donation, Integer> {

	void save(Donation donation) throws DataAccessException;
	
	Donation findById(Integer id);
	
	@Query("SELECT donation from Donation donation")
	public Collection<Donation> findDonations();
	
	@Query("SELECT donation FROM Donation donation WHERE donation.cause LIKE ?1")
	public Collection<Donation> findDonationsForCause(Integer causeId);
}
