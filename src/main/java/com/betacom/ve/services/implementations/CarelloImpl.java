package com.betacom.ve.services.implementations;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.ve.dto.input.CarelloReq;
import com.betacom.ve.dto.output.CarelloDTO;
import com.betacom.ve.enums.StatoCarello;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.mappers.CarelloMapper;
import com.betacom.ve.models.Carello;
import com.betacom.ve.models.CarelloDetails;
import com.betacom.ve.models.Utente;
import com.betacom.ve.models.Veicolo;
import com.betacom.ve.repositories.ICarelloDetailsRepository;
import com.betacom.ve.repositories.ICarelloRepository;
import com.betacom.ve.repositories.IUtenteRepository;
import com.betacom.ve.repositories.IVeicoloRepository;
import com.betacom.ve.services.interfaces.ICarelloServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarelloImpl implements ICarelloServices{

	private final ICarelloRepository carelloR;
	private final ICarelloDetailsRepository rigaR;
	private final IUtenteRepository  utenteR;
	private final IVeicoloRepository veicoloR;
	private final CarelloMapper carelloM;
	
	@Transactional
	@Override
	public void addRiga(CarelloReq req) throws Exception {
		log.debug("addRiga {}" , req);
		Utente ut = utenteR.findById(req.getUtenteID())
				.orElseThrow(() -> new AcademyException("user_ntfnd"));
				
		Carello carello = (ut.getCarello() != null) ? ut.getCarello() : createCarello(ut);
		
		controlCarello(carello);

		CarelloDetails riga = new CarelloDetails();
		Veicolo veicolo = veicoloR.findById(req.getVeicoloID())
				.orElseThrow(() -> new AcademyException("veicolo_ntfnd"));
		riga.setCarello(carello);
		riga.setDataCreazione(LocalDate.now());
		riga.setVeicolo(veicolo);
		riga.setQuantita(req.getQuantita());
		rigaR.save(riga);
		
	}
	
	@Transactional
	public Carello createCarello(Utente ut) throws Exception{
		log.debug("createCarello {}", ut.getUserName());
		Carello car = new Carello();
		car.setDataCreazione(LocalDate.now());
		car.setUtente(ut);
		car.setStato(StatoCarello.valueOf("carello"));
		car.setId(carelloR.save(car).getId());
		return car;
	}
	

	private void controlCarello(Carello carello) throws Exception{
		Optional.ofNullable(carello.getStato())
		.filter(stato -> stato == StatoCarello.valueOf("ordine"))
		.ifPresent(stato -> {
			throw new AcademyException("carello_not_available");
		});

	}
	
	@Transactional
	@Override
	public void updateRiga(CarelloReq req) throws Exception {
		log.debug("updateRiga {}" , req);
		
		Utente ut = utenteR.findById(req.getUtenteID())
				.orElseThrow(() -> new AcademyException("user_ntfnd"));

		controlCarello(ut.getCarello());

		CarelloDetails riga = searchRigaCarello(ut.getCarello(), req.getId());
		
		Optional.ofNullable(req.getQuantita()).ifPresent(riga::setQuantita);

		rigaR.save(riga);
		
	}
	@Transactional
	@Override
	public void deleteRiga(String userName, Integer id) throws Exception {
		log.debug("deleteRiga {}/{}" ,userName,id);

		Utente ut = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));

		controlCarello(ut.getCarello());

		CarelloDetails riga = searchRigaCarello(ut.getCarello(), id);
		
		rigaR.delete(riga);
		
	}
	
	private CarelloDetails searchRigaCarello(Carello carello, Integer idRiga) {
		return carello.getRigaCarello().stream()
		        .filter(car -> Objects.equals(car.getId(), idRiga))
		        .findFirst()
		        .orElseThrow(() -> new AcademyException("carello_riga_ntfnd"));		
	}

	@Override
	public CarelloDTO getCarello(String userName) throws Exception {
		log.debug("getCarello {}" ,userName);
		
		Utente ut = utenteR.findById(userName)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));
		
		return carelloM.builCarelloDTO(ut);
	}

}
