package com.betacom.ve.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.betacom.ve.configuration.AppProperties;
import com.betacom.ve.dto.input.MacchinaReq;
import com.betacom.ve.dto.input.MailReq;
import com.betacom.ve.exceptions.AcademyException;
import com.betacom.ve.services.interfaces.IMailServices;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailImpl implements IMailServices{
	
	
	private final JavaMailSender mailSender;
	private final AppProperties prop;
	
	
	
	@Override
	public void sendMail(MailReq req) throws Exception {
		log.debug("sendMail {}", req);
		
		if (req.getTo() == null || req.getOggetto() == null || req.getBody() == null)
			throw new AcademyException("mail_error");
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
		
		helper.setTo(req.getTo());
		helper.setFrom(prop.getSender());
		helper.setSubject(req.getOggetto());
		helper.setText(req.getBody(), true);
		mailSender.send(mimeMessage);
		log.debug("dopo  send");
		
		
		
	}


}
