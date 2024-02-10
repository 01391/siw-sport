package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.PresidentService;

@Controller
public class PresidentController {

	@Autowired
	private PresidentService presidentService;

	//	@GetMapping("/president")
	//	public String getPresidents(Model model) {		
	//		model.addAttribute("presidents", this.presidentService.findAllPresidents());
	//		return "presidents.html";
	//	}

	@GetMapping("/president/{id}")
	public String getPresident(@PathVariable("id") Long id, Model model) {		
		model.addAttribute("president", this.presidentService.findPresidentById(id));
		return "president.html";
	}

}
