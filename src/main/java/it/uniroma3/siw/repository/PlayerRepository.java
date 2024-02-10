package it.uniroma3.siw.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Long>{
		
	public boolean existsByNameAndSurnameAndDateOfBirth(String name, String surname, LocalDate dateOfBirth);


}
