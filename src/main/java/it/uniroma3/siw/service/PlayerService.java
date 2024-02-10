package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Player;
import it.uniroma3.siw.repository.PlayerRepository;
import jakarta.transaction.Transactional;

@Service
public class PlayerService {
	
	@Autowired
	protected PlayerRepository playerRepository;

	public Iterable<Player> findAllPlayers() {
		return this.playerRepository.findAll();
	}

	public Player findPlayerById(Long id) {
		return this.playerRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Player savePlayer(Player player) {
		return this.playerRepository.save(player);
	}
	
	@Transactional
	public Player getPlayer(Long id) {
		return this.playerRepository.findById(id).get();
	}

//	public long count() {
//		return playerRepository.count();
//	}
	
}
