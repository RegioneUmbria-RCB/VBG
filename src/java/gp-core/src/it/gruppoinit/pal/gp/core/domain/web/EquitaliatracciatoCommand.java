package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.service.helper.MessageTracciato450Helper;

import java.util.ArrayList;
import java.util.List;

public class EquitaliatracciatoCommand {

    private Integer numeroIstanzepacchetto;
    private Integer numeroIstanzeNonValidatepacchetto;
    private List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> resultValidazione = new ArrayList<ChiaveValoreBean<String, List<MessageTracciato450Helper>>>();
    private List<Integer> codiceIstanze = new ArrayList<Integer>();
    private EquitaliaTracciatiCfg equitaliaTracciatiCfg;
    private String nomeResponsabile;
    private String cognomeResponsabile;
    // Campi per filtrare istanze
    private TracciatoEquitaliaFilter tracciatoEquitaliaFilter;

    public Integer getNumeroIstanzepacchetto() {

	return numeroIstanzepacchetto;
    }

    public void setNumeroIstanzepacchetto(Integer numeroIstanzepacchetto) {

	this.numeroIstanzepacchetto = numeroIstanzepacchetto;
    }

    public Integer getNumeroIstanzeNonValidatepacchetto() {

	return numeroIstanzeNonValidatepacchetto;
    }

    public void setNumeroIstanzeNonValidatepacchetto(Integer numeroIstanzeNonValidatepacchetto) {

	this.numeroIstanzeNonValidatepacchetto = numeroIstanzeNonValidatepacchetto;
    }

    public List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> getResultValidazione() {

	return resultValidazione;
    }

    public void setResultValidazione(List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> resultValidazione) {

	this.resultValidazione = resultValidazione;
    }

    public List<Integer> getCodiceIstanze() {

	return codiceIstanze;
    }

    public void setCodiceIstanze(List<Integer> codiceIstanze) {

	this.codiceIstanze = codiceIstanze;
    }

    public EquitaliaTracciatiCfg getEquitaliaTracciatiCfg() {

	return equitaliaTracciatiCfg;
    }

    public void setEquitaliaTracciatiCfg(EquitaliaTracciatiCfg equitaliaTracciatiCfg) {

	this.equitaliaTracciatiCfg = equitaliaTracciatiCfg;
    }

    public String getNomeResponsabile() {

	return nomeResponsabile;
    }

    public void setNomeResponsabile(String nomeResponsabile) {

	this.nomeResponsabile = nomeResponsabile;
    }

    public String getCognomeResponsabile() {

	return cognomeResponsabile;
    }

    public void setCognomeResponsabile(String cognomeResponsabile) {

	this.cognomeResponsabile = cognomeResponsabile;
    }

    public TracciatoEquitaliaFilter getTracciatoEquitaliaFilter() {

	return tracciatoEquitaliaFilter;
    }

    public void setTracciatoEquitaliaFilter(TracciatoEquitaliaFilter tracciatoEquitaliaFilter) {

	this.tracciatoEquitaliaFilter = tracciatoEquitaliaFilter;
    }
}
