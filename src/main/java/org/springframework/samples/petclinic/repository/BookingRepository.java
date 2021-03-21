package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;

public interface BookingRepository extends CrudRepository<Booking, Integer> {

	@Query("select u from Booking u where u.owner = :owner")
	List<Booking> findBookingsByOwner(@Param("owner") Owner owner);
	
}
