package com.betacom.ve.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.ve.models.Categorie;

public interface ICategorieRepository extends JpaRepository<Categorie, String>{

	@Query (name ="categoria.validazioneById")
	Optional<Categorie> validazioneById (
			@Param("id") String filter,
			@Param("pattern") String pattern);
	
	@Query (name ="categoria.findByFilter")
	List<Categorie> findByFilter (@Param("id") String filter);

}
