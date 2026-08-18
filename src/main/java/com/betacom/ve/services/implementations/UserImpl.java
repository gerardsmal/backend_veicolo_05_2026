package com.betacom.ve.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.ve.configuration.AppProperties;
import com.betacom.ve.dto.input.ChangePwdReq;
import com.betacom.ve.dto.input.LoginReq;
import com.betacom.ve.dto.input.MailReq;
import com.betacom.ve.dto.input.UserReq;
import com.betacom.ve.dto.output.MeDTO;
import com.betacom.ve.dto.output.UserDTO;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.models.User;
import com.betacom.ve.repositories.IUserRepository;
import com.betacom.ve.services.interfaces.IMailServices;
import com.betacom.ve.services.interfaces.IUserServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserImpl implements IUserServices{
	
	private final IUserRepository utR;
	private final PasswordEncoder encoder;
	private final IMailServices mailS;
	private final AppProperties prop;
	
	@Transactional 
	@Override
	public void create(UserReq req) throws Exception {
		log.debug("create {}", req);	
		if (utR.existsByUserName(req.getUserName()))
			throw new AcademyException("user_exist");
		
		
		User acc = new User();
		acc.setUserName(req.getUserName());

		acc.setNome(req.getNome());
		acc.setCognome(req.getCognome());
		acc.setEmail(req.getEmail());
		acc.setComune(req.getCommune());
		acc.setVia(req.getVia());
		acc.setCap(req.getCap());
		acc.setTelefono(req.getTelefono());
		acc.setDataCreazione(LocalDate.now());
		
		acc.setSesso(req.getSesso());
		acc.setValidate(false);
		utR.save(acc);
	//	sendMailValidation(acc);
		
	}
		
	

	@Transactional
	@Override
	public void update(UserReq req) throws Exception {
		log.debug("update {}", req);
		boolean isMailChanged = false;
		User ut = utR.findById(req.getId())
				.orElseThrow(() -> new AcademyException("user_ntfnd"));
		
		if (req.getEmail() != null) {
		    ut.setEmail(req.getEmail());
		    ut.setValidate(false);
		    isMailChanged = true;
		}
		
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
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);
		User ut = utR.findById(id)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));

		utR.delete(ut);
		
	}

	@Override
	public List<UserDTO> list(String userName, String nome, String cognome) {
		log.debug("list {}/{}/{}/{}", userName, nome, cognome);
		
		List<User> lU = utR.selectByFilter(userName, nome, cognome);
		
		return lU.stream()
				.map((u -> UserDTO.builder()
						.id(u.getId())
						.userName(u.getUserName())
						.nome(u.getNome())
						.cognome(u.getCognome())
						.via(u.getVia())
						.comune(u.getComune())
						.cap(u.getCap())
						.email(u.getEmail())
						.sesso(u.getSesso())
						.telefono(u.getTelefono())
						.isValidate(u.getValidate())
						.build())
						).toList();
						
	}
	

	@Override
	public UserDTO getById(Long id) {
		User u = utR.findById(id)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));

		return UserDTO.builder()
				.id(u.getId())
				.userName(u.getUserName())
				.nome(u.getNome())
				.cognome(u.getCognome())
				.via(u.getVia())
				.comune(u.getComune())
				.cap(u.getCap())
				.email(u.getEmail())
				.sesso(u.getSesso())
				.telefono(u.getTelefono())
				.isValidate(u.getValidate())
				.build();
	}

	
	
	@Override
	public MeDTO me(LoginReq req) throws Exception {
		log.debug("login {}", req);
		User ut = utR.findByUserName(req.getUserName())
				.orElseThrow(() -> new AcademyException("user_invalid_pwd"));
		
		return MeDTO.builder()
				.id(ut.getUserName())
//				.role(ut.getRole().toString())
				.mailValidate(ut.getValidate())
				.carelloSize(ut.getCarello() == null ? 0 : ut.getCarello().getRigaCarello().size())
				.build();
	}

	@Transactional (rollbackFor = Exception.class)
	@Override
	public void changePwd(ChangePwdReq req) throws Exception {
		log.debug("changePwd {}", req);
		
//		Utente ut = utR.findById(req.getUserName())
//				.orElseThrow(() -> new AcademyException("user_ntfnd"));
//
//		if (!encoder.matches(req.getOldPwd(), ut.getPwd()))
//			throw new Exception("login_invalid");
//		
//		Optional.ofNullable(req.getNewPwd())
//			.ifPresentOrElse(pwd -> {
//				ut.setPwd(encoder.encode(req.getNewPwd()));
//			}, () -> { 
//				throw new RuntimeException("user_no_newpwd");
//			});
//		
//		utR.save(ut);
	}

	@Override
	public void sendResetPassword(String userName) throws Exception {
		log.debug("sendResetPassword {}", userName);
		
		User ut = utR.findByUserName(userName)
				.orElseThrow(() -> new AcademyException("user_ntfnd"));	
		StringBuilder body = new StringBuilder();
		body.append("<h2>Vendita Veicoli</h2><br><br>");
		body.append("Buongiorno ");
		body.append(ut.getNome());
		body.append("<br><br>");
		body.append("<br>Per inizializzare la tua password va sull'URL");
		body.append("<br><a>"+ prop.getUrlResetPassword()  + ut.getUserName()+ "</a><br>");
		body.append("<br><br>Il team Vendita Veicoli <br><br>");

		sendMail(ut, "Cambiamento password", body.toString());
	}
	
	@Override
	public void resetPassword(ChangePwdReq req) throws Exception {
		log.debug("resetPssword {}", req);
		User ut = utR.findByUserName(req.getUserName())
				.orElseThrow(() -> new AcademyException("user_ntfnd"));

//		Optional.ofNullable(req.getNewPwd())
//			.ifPresentOrElse(pwd -> {
//				ut.setPwd(encoder.encode(req.getNewPwd()));
//			}, () -> { 
//				throw new RuntimeException("user_no_newpwd");
//			});
//		
//		utR.save(ut);

		
	}
	

	private void sendMail(User account, String oggetto, String body) throws Exception{
		
		mailS.sendMail(MailReq.builder()
				.to(account.getEmail())
				.oggetto(oggetto)
				.body(body)
				.build()
				);
		

	}
}
