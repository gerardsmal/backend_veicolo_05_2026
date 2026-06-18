package com.betacom.ve.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.ve.models.TipoAlimentazione;

public interface ITipoAlimentazioneRepository extends JpaRepository<TipoAlimentazione, String>{

	@Query (name ="alim.validazioneById")
	Optional<TipoAlimentazione> validazioneById (
			@Param("id") String filter,
			@Param("pattern") String pattern);
	
	@Query (name ="alim.findByFilter")
	List<TipoAlimentazione> findByFilter (@Param("id") String filter);

}