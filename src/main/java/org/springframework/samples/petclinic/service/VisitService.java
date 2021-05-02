package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitService {

    private VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        visitRepository.save(visit);
    }

    public Collection<Visit> findVisitsByPetId(int petId) {
        return visitRepository.findByPetId(petId);
    }

    @Transactional
    public void deleteVisit(Integer visitId, Integer petId) throws DataAccessException {
        visitRepository.deleteById(visitId);
    }

    @Transactional(readOnly = true)
    public Visit findVisitById(int id) throws DataAccessException {
    	Visit visit = new Visit();
		Optional<Visit> value = this.visitRepository.findById(id);
		if (value.isPresent()) { //bug fix
			visit = value.get();
		}
		return visit;
    }
}