package org.springframework.samples.petclinic.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
@Service
public class CauseService {
	
	@Autowired
	private CauseRepository causeRepository;
	
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
		if(cause.getTotalAmount() < cause.getBudgetTarget()){
			cause.setTargetNotReached(true);
		} else {
			cause.setTargetNotReached(false);
		}
		causeRepository.save(cause);
	}

	public void delete(Cause cause) {
		causeRepository.delete(cause);
		
	}
	
}