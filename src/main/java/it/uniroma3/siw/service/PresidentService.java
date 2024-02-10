package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.President;
import it.uniroma3.siw.repository.PresidentRepository;
import it.uniroma3.siw.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class PresidentService {
	
	@Autowired
	protected PresidentRepository presidentRepository;
	
	@Autowired
	protected UserRepository userRepository;

//	public Iterable<President> findAllPresidents() {
//		return this.presidentRepository.findAll();
//	}

	public President findPresidentById(Long id) {
		return this.presidentRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public President getPresident(Long id) {
		return this.presidentRepository.findById(id).get();
	}
	
//	@Transactional
//	public void registerPresident(User user, President president) {
//	    userRepository.save(user); // Salva il nuovo utente
//	    president.setId(user.getId()); // Imposta lo stesso ID per il presidente
//	    presidentRepository.save(president); // Salva il presidente con lo stesso ID
//	}
		
}
