package com.betacom.ve.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.betacom.ve.dto.output.CarelloDTO;
import com.betacom.ve.dto.output.CarelloDetaglioDTO;
import com.betacom.ve.models.CarelloDetails;
import com.betacom.ve.models.User;
import com.betacom.ve.services.interfaces.IUploadServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CarelloMapper {

	private final IUploadServices uplS;

	public CarelloDTO builCarelloDTO(User ut) {
		if (ut.getCarello() == null)
			return null;
		double totale = ut.getCarello().getRigaCarello().stream()
				.mapToDouble(r -> r.getVeicolo().getPrezzo() * r.getQuantita()).sum();

		return CarelloDTO.builder()
				.id(ut.getCarello().getId())
				.nome(ut.getNome())
				.cognome(ut.getCognome())
				.userName(ut.getUserName())
				.prezzoTotale(totale)
				.status(ut.getCarello().getStato().toString())
				.carelloDetaglio(buildDetaglioDTO(ut.getCarello().getRigaCarello())).build();
	}

	public List<CarelloDetaglioDTO> buildDetaglioDTO(List<CarelloDetails> rige) {

		return rige.stream()
				.map(r -> CarelloDetaglioDTO.builder().id(r.getId())
						.alimentazione(r.getVeicolo().getTipoAlimentazione().getNome())
						.categoria(r.getVeicolo().getCategorie().getNome()).colore(r.getVeicolo().getColore().getNome())
						.dataCreazione(r.getDataCreazione())
						.image(r.getVeicolo().getImage() == null ? null : uplS.buildUrl(r.getVeicolo().getImage()))
						.modello(r.getVeicolo().getModello()).prezzo(r.getVeicolo().getPrezzo())
						.quantita(r.getQuantita()).veicoloID(r.getVeicolo().getId())
						.tipoVeicolo(r.getVeicolo().getTipoVeicolo().getNome()).build())
				.toList();

	}
}
