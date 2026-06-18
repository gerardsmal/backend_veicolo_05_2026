package com.betacom.ve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.ve.models.Messaggi;

public interface IMessageRepository extends JpaRepository<Messaggi, String>{

}
