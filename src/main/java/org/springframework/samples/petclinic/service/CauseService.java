package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	private CauseRepository causeRepository;
	private OwnerService ownerService;

	@Autowired
	public CauseService(CauseRepository causeRepository, OwnerService ownerService) {
		this.causeRepository = causeRepository;
		this.ownerService = ownerService;
	}

	@Transactional
	public Iterable<Cause> findAll() {
		return causeRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Cause> findCauseByTargetNotReached(Boolean active) throws DataAccessException {
		return causeRepository.findByTargetNotReached(active);
	}

	@Transactional(readOnly = true)
	public Cause findCauseById(int id) throws DataAccessException {
		return causeRepository.findById(id);
	}

	@Transactional
	public void save(Cause cause) {
		cause.setTargetNotReached(cause.getTotalAmount() < cause.getBudgetTarget());
		cause.setOwner(ownerService.findSessionOwner());
		causeRepository.save(cause);
	}

	@Transactional
	public void delete(Cause cause) {
		causeRepository.delete(cause);
	}

}