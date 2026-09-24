package com.betacom.ve.mappers;

import java.util.List;

import com.betacom.ve.dto.output.OrdiniDTO;
import com.betacom.ve.dto.output.OrdiniDetailsDTO;
import com.betacom.ve.models.Ordini;
import com.betacom.ve.models.OrdiniDetails;

public class OrdiniMapper {

	public static OrdiniDTO ordiniToDTO(Ordini ordine) {
		return OrdiniDTO.builder()
				.id(ordine.getId())
				.dataOrdine(ordine.getDataOrdine())
				.dataInvio(ordine.getDataInvio())
				.statusPagamento(ordine.getStatusPagamento().toString())
				.totale(ordine.getTotale())
				.numeroOrdine(ordine.getNumeroOrdine())
				.modalitaPagamentoID(ordine.getModalitaPagamento() == null ? null : ordine.getModalitaPagamento().getId())
				.tipoPagamento(ordine.getModalitaPagamento() == null ? null : ordine.getModalitaPagamento().getTipo())
				.spedizione(ordine.getSpedizione() == null ? null : SpedizioneMapper.spedizioneToDTO(ordine.getSpedizione()))
				.userName(ordine.getUtente().getUserName())
				.ordiniDetails(ordiniDetailsToDTO(ordine.getOrdiniDetails()))
				.build();
	}

	private static List<OrdiniDetailsDTO> ordiniDetailsToDTO(List<OrdiniDetails> dettagli) {
		if (dettagli == null)
			return List.of();

		return dettagli.stream()
				.map(dettaglio -> OrdiniDetailsDTO.builder()
						.id(dettaglio.getId())
						.dataCreazione(dettaglio.getDataCreazione())
						.quantita(dettaglio.getQuantita())
						.productName(dettaglio.getProductName())
						.image(dettaglio.getImage())
						.prezzoUnit(dettaglio.getPrezzoUnit())
						.prezzo(dettaglio.getPrezzo())
						.build())
				.toList();
	}
}
