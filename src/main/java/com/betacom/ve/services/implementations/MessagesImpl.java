package com.betacom.ve.services.implementations;

import org.springframework.stereotype.Service;

import com.betacom.ve.models.Messaggi;
import com.betacom.ve.repositories.IMessageRepository;
import com.betacom.ve.services.interfaces.IMessageServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessagesImpl  implements IMessageServices{

	private final IMessageRepository msgR;
	
	@Override
	public String get(String code) {
		log.debug("get {}", code);
		Messaggi msg = msgR.findById(code)
				.orElse( new  Messaggi(code, code));
		return msg.getMsg();
	}


	
}
