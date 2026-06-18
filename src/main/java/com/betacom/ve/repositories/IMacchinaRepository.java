package com.betacom.ve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.ve.models.Macchina;

public interface IMacchinaRepository extends JpaRepository<Macchina, Integer>{
	Boolean existsByTarga(String targa);
}
