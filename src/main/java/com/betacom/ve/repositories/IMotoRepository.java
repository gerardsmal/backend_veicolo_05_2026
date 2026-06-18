package com.betacom.ve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.ve.models.Moto;

public interface IMotoRepository extends JpaRepository<Moto, Integer>{
	Boolean existsByTarga(String targa);

}