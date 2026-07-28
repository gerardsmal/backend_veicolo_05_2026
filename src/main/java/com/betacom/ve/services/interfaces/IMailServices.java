package com.betacom.ve.services.interfaces;

import com.betacom.ve.dto.input.MailReq;

public interface IMailServices {
	void sendMail(MailReq req) throws Exception;
}
