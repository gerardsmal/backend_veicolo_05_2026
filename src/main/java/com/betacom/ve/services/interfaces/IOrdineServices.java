package com.betacom.ve.services.interfaces;

import java.util.List;

import com.betacom.ve.dto.input.OrdiniReq;
import com.betacom.ve.dto.input.SpedizioneReq;
import com.betacom.ve.dto.output.ModalitaPagamentoDTO;
import com.betacom.ve.dto.output.OrdiniDTO;
import com.betacom.ve.dto.output.SpedizioneDTO;
import com.betacom.ve.models.Carello;
import com.betacom.ve.models.Ordini;

public interface IOrdineServices {

	void create(String userName) throws Exception;

	Double createOrdiniDetails(Ordini ordine, Carello carello) throws Exception;

	List<SpedizioneDTO> listSpedizione(String userName) throws Exception;

	void createSpedizione(String userName, SpedizioneReq req) throws Exception;

	void removeSpedizione(String userName, Integer id) throws Exception;

	void assignSpedizione(String userName, OrdiniReq req) throws Exception;

	List<ModalitaPagamentoDTO> listModalitaPagamento() throws Exception;

	void assignModalitaPagamento(String userName, OrdiniReq req) throws Exception;

	OrdiniDTO confermo(String userName) throws Exception;

	void remove(String userName) throws Exception;
}
