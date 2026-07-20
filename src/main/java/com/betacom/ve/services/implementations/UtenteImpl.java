package com.betacom.ve.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ve.dto.input.ChangePwdReq;
import com.betacom.ve.dto.input.LoginReq;
import com.betacom.ve.dto.input.UtenteReq;
import com.betacom.ve.dto.output.MeDTO;
import com.betacom.ve.dto.output.UtenteDTO;
import com.betacom.ve.enums.Roles;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.models.Utente;
import com.betacom.ve.repositories.IUtenteRepository;
import com.betacom.ve.services.interfaces.IUtenteServices;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UtenteImpl implements IUtenteServices{
	private final IUtenteRepository utR;
	private final PasswordEncoder encoder;

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void create(UtenteReq req) throws Exception {
		log.debug("create {}", req);	
		if (utR.existsById(req.getUserName()))
			throw new AcademyException("user_exist");
		
		
		Utente acc = new Utente();
		acc.setUserName(req.getUserName());
		acc.setPwd(req.getPwd());  // encode password

		acc.setNome(req.getNome());
		acc.setCognome(req.getCognome());
		acc.setEmail(req.getEmail());
		acc.setComune(req.getCommune());
		acc.setVia(req.getVia());
		acc.setCap(req.getCap());
		acc.setTelefono(req.getTelefono());
		acc.setPwd(encoder.encode(req.getPwd()));  
		acc.setRole(Roles.valueOf(req.getRole()));
		acc.setDataCreazione(LocalDate.now());
		
		acc.setSesso(req.getSesso());
		acc.setValidate(false);
		utR.save(acc);
	//	sendMailValidation(acc);
		
	}
		
	

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void update(UtenteReq req) throws Exception {
		log.debug("update {}", req);
		boolean isMailChanged = false;
		Utente ut = utR.findById(req.getUserName())
				.orElseThrow(() -> new AcademyException("user_ntfnd"));
		
		Optional.ofNullable(req.getPwd()).ifPresent(u -> ut.setPwd(encoder.encode(req.getPwd())));
		if (req.getEmail() != null) {
		    ut.setEmail(req.getEmail());
		    ut.setValidate(false);
		    isMailChanged = true;
		}
		
		Optional.ofNullable(req.getRole()).ifPresent(u -> ut.setRole(Roles.valueOf(req.getRole())));
		Optional.ofNullable(req.getNome()).ifPresent(ut::setNome);
		Optional.ofNullable(req.getCognome()).ifPresent(ut::setCognome);
		Optional.ofNullable(req.getCommune()).ifPresent(ut::setComune);
		Optional.ofNullable(req.getVia()).ifPresent(ut::setVia);
		Optional.ofNullable(req.getCap()).ifPresent(ut::setCap);
		Optional.ofNullable(req.getTelefono()).ifPresent(ut::setTelefono);
		Optional.ofNullable(req.getSesso()).ifPresent(ut::setSesso);
	
		utR.save(ut);
//		if (isMailChanged)
//			sendMailValidation(ut);
	}
		
	
	
	@Transactional (rollbackFor = Exception.class)
	@Override
	public void delete(String userName) throws Exception {
		log.debug("delete {}", userName);
		Utente ut = utR.findById(userName)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));

		utR.delete(ut);
		
	}

	@Override
	public List<UtenteDTO> list(String userName, String nome, String cognome, String role) {
		log.debug("list {}/{}/{}/{}", userName, nome, cognome, role);
		Roles ro = null;
		if (role != null)  ro = Roles.valueOf(role.trim().toUpperCase());
		
		List<Utente> lU = utR.selectByFilter(userName, nome, cognome, ro);
		
		return lU.stream()
				.map((u -> UtenteDTO.builder()
						.userName(u.getUserName())
						.nome(u.getNome())
						.cognome(u.getCognome())
						.via(u.getVia())
						.comune(u.getComune())
						.cap(u.getCap())
						.email(u.getEmail())
						.role(u.getRole().toString())
						.sesso(u.getSesso())
						.telefono(u.getTelefono())
						.isValidate(u.getValidate())
						.build())
						).toList();
						
	}
	

	@Override
	public UtenteDTO getById(String userName) {
		Utente u = utR.findById(userName)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));

		return UtenteDTO.builder()
				.userName(u.getUserName())
				.nome(u.getNome())
				.cognome(u.getCognome())
				.via(u.getVia())
				.comune(u.getComune())
				.cap(u.getCap())
				.email(u.getEmail())
				.role(u.getRole().toString())
				.sesso(u.getSesso())
				.telefono(u.getTelefono())
				.isValidate(u.getValidate())
				.build();
	}

	
	
	@Override
	public MeDTO me(LoginReq req) throws Exception {
		log.debug("login {}", req);
		Utente ut = utR.findById(req.getUserName())
				.orElseThrow(() -> new AcademyException("user_invalid_pwd"));
		
		return MeDTO.builder()
				.id(ut.getUserName())
				.role(ut.getRole().toString())
				.mailValidate(ut.getValidate())
				.build();
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void changePwd(ChangePwdReq req) throws Exception {
		log.debug("changePwd {}", req);
		
		Utente ut = utR.findById(req.getUserName())
				.orElseThrow(() -> new AcademyException("user_ntfnd"));

		if (!encoder.matches(req.getOldPwd(), ut.getPwd()))
			throw new Exception("login_invalid");
		
		Optional.ofNullable(req.getNewPwd())
			.ifPresentOrElse(pwd -> {
				ut.setPwd(encoder.encode(req.getNewPwd()));
			}, () -> { 
				throw new RuntimeException("user_no_newpwd");
			});
		
		utR.save(ut);
	}


}
