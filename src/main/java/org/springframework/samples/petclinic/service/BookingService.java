package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.samples.petclinic.service.exceptions.BookingProhibitedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private BookingRepository bookingRepository;
	private OwnerService ownerService;
	private PetService petService;

	@Autowired
	public BookingService(BookingRepository bookingRepository, OwnerService ownerService, PetService petService) {
		this.bookingRepository = bookingRepository;
		this.ownerService = ownerService;
		this.petService = petService;
	}

	@Transactional
	public void saveBooking(Booking booking, Integer petId) throws BookingProhibitedException {
		if (booking.getEndDate().isBefore(booking.getInitDate())) {
			throw new BookingProhibitedException();
		}

		for (Booking b : bookingRepository.findAll()) {
			if (b.getRoom().equals(booking.getRoom()) && (!(b.getInitDate().isAfter(booking.getInitDate())
					&& b.getEndDate().isAfter(booking.getEndDate()))
					&& !(b.getInitDate().isBefore(booking.getInitDate())
							&& b.getEndDate().isBefore(booking.getEndDate())))) {
				throw new BookingProhibitedException();
			}
		}
		List<Booking> listaBooking = bookingRepository.mascotaReserva(petService.findPetById(petId));

		for (Booking b : listaBooking) {
			if ((!(b.getInitDate().isAfter(booking.getInitDate()) && b.getEndDate().isAfter(booking.getEndDate()))
					&& !(b.getInitDate().isBefore(booking.getInitDate())
							&& b.getEndDate().isBefore(booking.getEndDate())))) {
				throw new BookingProhibitedException();
			}
		}

		booking.setOwner(ownerService.findSessionOwner());
		booking.setPet(petService.findPetById(petId));
		bookingRepository.save(booking);
	}

	@Transactional(readOnly = true)
	public List<Booking> findBookingsByOwner() {
		return bookingRepository.findBookingsByOwner(ownerService.findSessionOwner());
	}

	@Transactional()
	public void deleteBooking(Booking booking) {
		bookingRepository.delete(booking);
	}

	@Transactional(readOnly = true)
	public Booking findBookingById(int bookingId) throws DataAccessException {
		return this.bookingRepository.findBookingById(bookingId);
	}
}