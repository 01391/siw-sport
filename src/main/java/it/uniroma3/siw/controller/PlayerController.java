package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.PlayerValidator;
import it.uniroma3.siw.model.Player;
import it.uniroma3.siw.model.President;
import it.uniroma3.siw.model.Team;
import it.uniroma3.siw.service.PlayerService;
import it.uniroma3.siw.service.PresidentService;
import it.uniroma3.siw.service.TeamService;
import jakarta.validation.Valid;

@Controller
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PresidentService presidentService;

	@Autowired
	private TeamService teamService;
	
	@Autowired 
	private PlayerValidator playerValidator;
	
	@GetMapping("/player")
	public String getPlayers(Model model) {		
		model.addAttribute("players", this.playerService.findAllPlayers());
		return "players.html";
	}
	
	@GetMapping("/player/{id}")
	public String getPlayer(@PathVariable("id") Long id, Model model) {		
		model.addAttribute("player", this.playerService.findPlayerById(id));
		return "player.html";
	}
	
	@GetMapping("/admin/formNewPlayer")
	public String formNewPlayer(Model model) {
		model.addAttribute("player", new Player());
		return "admin/formNewPlayer.html";
	}
	
	@PostMapping("/admin/player")
	public String newPlayer(@Valid @ModelAttribute("player") Player player, BindingResult bindingResult, Model model) {
		this.playerValidator.validate(player, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.playerService.savePlayer(player);
			model.addAttribute("player", player);
			return "player.html";
		} else {
			return "admin/formNewPlayer.html"; 
		}
	}
	
	@GetMapping("/pres/playersFree/{id}")
	public String playersFree(@PathVariable("id") Long id, Model modelPlayers, Model modelTeam) {	
		President president = this.presidentService.findPresidentById(id);
		Team team = president.getTeam();
		modelPlayers.addAttribute("players", this.playerService.findAllPlayers());
		modelTeam.addAttribute("team", team);
		return "/pres/playersFree.html";
	}

	@GetMapping("/pres/addPlayer/{idPlayer}/{idTeam}")
	public String addPlayer(@PathVariable("idPlayer") Long idPlayer,@PathVariable("idTeam") Long idTeam, Model modelPlayer, Model modelTeam) {
		Player player = this.playerService.findPlayerById(idPlayer);
		Team team = this.teamService.findTeamById(idTeam);
		player.setTeam(team);
		modelPlayer.addAttribute("player", player);
		modelTeam.addAttribute("team", team);
		return "/pres/addContract.html";
	}

	@PostMapping("/pres/addContract")
	public String addContract(@ModelAttribute("player") Player player, Model modelPlayer, Model modelTeam) {
		
		Long idTeam = player.getTeam().getId();
		Team team = this.teamService.findTeamById(idTeam);
		player.setTeam(team);
		this.playerService.savePlayer(player);
		
		modelPlayer.addAttribute("player", player);
		modelTeam.addAttribute("team", team);
		return "/team.html";
	}
	
	
	
}
