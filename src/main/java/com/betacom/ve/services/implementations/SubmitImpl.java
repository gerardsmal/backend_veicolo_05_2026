package com.betacom.ve.services.implementations;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.betacom.ve.configuration.AppProperties;
import com.betacom.ve.services.interfaces.ISubmitServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class SubmitImpl implements ISubmitServices{

	private final AppProperties prop;
	

	@Override
	public void run() throws Exception {
		log.debug("Begin scriptBatch {}", prop.getScriptBatch());
		
		ProcessBuilder pb = new ProcessBuilder(prop.getScriptBatch());
		log.debug("After process builder");
		
		Process p = pb.start(); 
		log.debug("process is started");
		
	}
	/*
	 * ┌───────────── secondo (0-59)
	 * │ ┌─────────── minuto (0-59)
	 * │ │ ┌───────── ora (0-23)
	 * │ │ │ ┌─────── giorno del mese (1-31)
	 * │ │ │ │ ┌───── mese (1-12)
	 * │ │ │ │ │ ┌─── giorno della settimana (0-7)
	 * │ │ │ │ │ │
	 * * * * * *
	 */
	@Scheduled(cron = "0 30 11 * * *")
	@Override
	public void runScheduled() throws Exception {
		log.debug("Begin scriptBatch schedulato {}", prop.getScriptBatch());
		
		ProcessBuilder pb = new ProcessBuilder(prop.getScriptBatch());
		log.debug("After process builder");
		
		Process p = pb.start(); 
		log.debug("process is started");
		
	}

}
