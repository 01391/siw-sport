package it.uniroma3.siw.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Team;
import it.uniroma3.siw.repository.ImageRepository;
import it.uniroma3.siw.repository.TeamRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class TeamService {

	@Autowired
	protected TeamRepository teamRepository;
	
	@Autowired
	protected ImageRepository imageRepository;

	public Iterable<Team> findAllTeams() {
		return this.teamRepository.findAll();
	}

	public Team findTeamById(Long id) {
		return this.teamRepository.findById(id).orElse(null);
	}

	@Transactional
	public Team saveTeam(Team team) {
		return this.teamRepository.save(team);
	}

	@Transactional
	public Team updateTeam(Team team) {
		return this.teamRepository.save(team);
	}

	@Transactional
	public Team getTeam(Long id) {
		return this.teamRepository.findById(id).get();
	}

	@Transactional
	public Team createNewTeam(@Valid Team team, MultipartFile file) {
		try {
			if (!file.isEmpty())
				team.setImage(imageRepository.save(new Image(file.getBytes())));
		}
		catch (IOException e) {}
		this.teamRepository.save(team);
		return team;
	}

//	public long count() {
//		return teamRepository.count();
//	}

}
