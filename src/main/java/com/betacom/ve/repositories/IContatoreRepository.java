package com.betacom.ve.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.ve.models.Contatore;

import jakarta.persistence.LockModeType;

public interface IContatoreRepository extends JpaRepository<Contatore, String> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from Contatore c where c.tipo = :tipo")
	Optional<Contatore> findByTipoForUpdate(@Param("tipo") String tipo);
}
