package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.SpecialtyService;
import org.springframework.stereotype.Component;

@Component
public class SpecialtiesFormatter implements Formatter<Specialty>{

    private final SpecialtyService specService;

    @Autowired
    public SpecialtiesFormatter(SpecialtyService specService) {
        this.specService = specService;
    }

    @Override
    public String print(Specialty object, Locale locale) {
        return object.getName();
    }

    @Override
    public Specialty parse(String text, Locale locale) throws ParseException {
        Collection<Specialty> findSpecialties = this.specService.findAllSpecialtys();
        for (Specialty spec : findSpecialties) {
            if (spec.getName().equals(text)) {
                return spec;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
}