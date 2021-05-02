package org.springframework.samples.petclinic.web;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes")
public class CauseController {

	private static final String CREATE="causes/createCause";

	private static final String LIST="causes/causesList";
	
	private static final String CAUSE="cause";

	private final CauseService causeService;

	private final UserService userService;


	@Autowired
	public CauseController(CauseService causeService, UserService userService){
		this.causeService=causeService;

		this.userService=userService;

	}
	
	@GetMapping()
	public String causeActiveList(ModelMap modelMap) {
		List<Cause> causes = causeService.findCauseByTargetNotReached(true);
		modelMap.addAttribute("causes",causes);
		modelMap.addAttribute("username",userService.obtenerUsername());
		return LIST;
	}

	@GetMapping(path="/inactive")
	public String causeInactiveList(ModelMap modelMap) {
		List<Cause> causes = causeService.findCauseByTargetNotReached(false);
		modelMap.addAttribute("causes",causes);
		return LIST;
	}

	
	@GetMapping(path="/{causeId}")
	public String causeDetails(@PathVariable("causeId") final int causeId, ModelMap modelMap) {
		Cause cause = causeService.findCauseById(causeId);
		modelMap.addAttribute(CAUSE,cause);
		return "causes/showCause";
	}
	
	
	@GetMapping(path="/new")
	public String newCause(ModelMap modelMap) {
		modelMap.addAttribute(CAUSE, new Cause());
		return CREATE;
	}
	
	@PostMapping(path="/save")
	public String saveCause(@Valid Cause cause, BindingResult result, ModelMap modelMap) {
		
		if(result.hasErrors()) {
			modelMap.addAttribute(CAUSE,cause);
			return CREATE;
		}else {
			causeService.save(cause);
			return "redirect:/causes";
		}
		
	}
	
	
	@GetMapping(path="{causeId}/delete")
	public String deleteCause(@PathVariable("causeId") int causeId, ModelMap modelMap) {
		String view =LIST;
		Optional<Cause> cause = Optional.of(causeService.findCauseById(causeId));
		if(cause.isPresent()) {
			causeService.delete(cause.get());
			modelMap.addAttribute("message", "Se ha eliminado la causa");
			view = causeActiveList(modelMap);		
		}else {
			modelMap.addAttribute("message", "No se ha encontrado el elemento");
			view = causeActiveList(modelMap);
		}
		return view;
	}
	@GetMapping(path="{causeId}/edit")
	public String initUpdateCauseForm(@PathVariable("causeId") int causeId, ModelMap modelMap) {
		Cause cause = this.causeService.findCauseById(causeId);
		modelMap.addAttribute(cause);
		return CREATE;
	}
	@PostMapping(path="{causeId}/save")
	public String processUpdateCauseForm(@Valid Cause cause, BindingResult result, @PathVariable("causeId") int causeId) {
		
		if(result.hasErrors()) {
			return CREATE;
		}else {
			Cause causeToUpdate = causeService.findCauseById(causeId);
			BeanUtils.copyProperties(cause, causeToUpdate, "donations");
			causeService.save(causeToUpdate);
			return "redirect:/causes";
		}
	}
	
}