package com.betacom.ve.services.interfaces;

import com.betacom.ve.models.Carello;
import com.betacom.ve.models.Ordini;

public interface IOrdineServices {

	void create(String userName) throws Exception;

	Double createOrdiniDetails(Ordini ordine, Carello carello) throws Exception;
}
