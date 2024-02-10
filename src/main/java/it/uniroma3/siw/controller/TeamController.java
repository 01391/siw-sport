package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.TeamValidator;
import it.uniroma3.siw.model.President;
import it.uniroma3.siw.model.Team;
import it.uniroma3.siw.service.PresidentService;
import it.uniroma3.siw.service.TeamService;
import jakarta.validation.Valid;

@Controller
public class TeamController {

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private PresidentService presidentService;

	@Autowired 
	private TeamValidator teamValidator;

	@GetMapping("/team")
	public String getTeams(Model model) {		
		model.addAttribute("teams", this.teamService.findAllTeams());
		return "teams.html";
	}

	@GetMapping("/team/{id}")
	public String getTeam(@PathVariable("id") Long id,Model model) {		
		model.addAttribute("team", this.teamService.findTeamById(id));
		return "team.html";
	}
	
	@GetMapping("/pres/team/{id}")
	public String getPresidentTeam(@PathVariable("id") Long id, Model model) {	
		President president = this.presidentService.findPresidentById(id);
		Team team = president.getTeam();
		model.addAttribute("team", this.teamService.findTeamById(team.getId()));
		return "team.html";
	}

//	@GetMapping(value="/admin/indexTeam")
//	public String indexTeam() {
//		return "admin/indexTeam.html";
//	}
	
	@PostMapping("/admin/team")
	public String newTeam(@Valid @ModelAttribute("team") Team team, BindingResult bindingResult,@RequestParam("teamImage") MultipartFile multipartFile, Model model) {

		this.teamValidator.validate(team, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.teamService.createNewTeam(team, multipartFile);
			model.addAttribute("team", team);
			return "team.html";
		} else {
			return "admin/formNewTeam.html"; 
		}
	}

	@GetMapping(value="/admin/formNewTeam")
	public String formNewTeam(Model model) {
		model.addAttribute("team", new Team());
		return "admin/formNewTeam.html";
	}

	@GetMapping(value="/admin/manageTeams")
	public String manageTeams(Model model) {
		model.addAttribute("teams", this.teamService.findAllTeams());
		return "admin/manageTeams.html";
	}

	@PostMapping(value="/admin/updateTeam")
	public String updateTeam(@ModelAttribute("team") Team team, Model model){
		this.teamService.updateTeam(team);
		model.addAttribute("teams", this.teamService.findAllTeams());
		return "teams.html";
	}

	@GetMapping(value="/admin/updateTeam/{id}")
	public String updateTeam(@PathVariable("id") Long id, Model model) {
		model.addAttribute("team", this.teamService.findTeamById(id));
		return "admin/updateTeam.html";
	}

}
