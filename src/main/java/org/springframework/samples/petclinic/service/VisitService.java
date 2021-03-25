package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitService {

	private VisitRepository visitRepository;
	

	@Autowired
	public VisitService(
			VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}
	
	@Transactional
	public void deleteVisit(Visit visit) throws DataAccessException {
		visitRepository.delete(visit);
	}
	@Transactional
	public Visit findVisitById(int id) throws DataAccessException {
		return visitRepository.findById(id);
	
	}
	
	
}
