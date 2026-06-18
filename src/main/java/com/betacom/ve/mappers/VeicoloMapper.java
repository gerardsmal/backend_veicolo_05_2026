package com.betacom.ve.mappers;

import org.springframework.stereotype.Component;

import com.betacom.ve.dto.output.VeicoloDTO;
import com.betacom.ve.models.Veicolo;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class VeicoloMapper {

	public  VeicoloDTO builVeicoloDTO(Veicolo v) {
		return VeicoloDTO.builder()
				.id(v.getId())
				.numeroRuote(v.getNumeroRuote())
				.modello(v.getModello())
				.annoProduzione(v.getAnnoProduzione())
				.prezzo(v.getPrezzo())
				.dataInserimento(v.getDataInserimento())
				.tipoVeicolo(TipoVeicoloMapper.tipoVeicoloToDTO(v.getTipoVeicolo()))
				.categoria(KeyStringMapper.keyStringToDTO(v.getCategorie()))
				.tipoAlimentazione(KeyStringMapper.keyStringToDTO(v.getTipoAlimentazione()))
				.colore(KeyIntegerMapper.keyIntegerToDTO(v.getColore()))
				.marca(KeyIntegerMapper.keyIntegerToDTO(v.getMarca()))
				.macchina(v.getMacchina() == null ? null :MacchinaMapper.builMacchinaDTO(v.getMacchina()))
				.moto(v.getMoto() == null ? null : MotoMapper.builMotoDTO(v.getMoto()))
				.bici(v.getBici() == null ? null : BiciMapper.builBiciDTO(v.getBici()))
				.build();								
	}
	
	public  List<VeicoloDTO> builVeicoloDTO(List<Veicolo> lV){
		return lV.stream()
				.map(v -> builVeicoloDTO(v))
				.toList();
				
				
	}
}
