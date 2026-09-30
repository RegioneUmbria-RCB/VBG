package it.gruppoinit.pal.gp.core.features.scadenzario.service;

import javax.servlet.http.HttpServletRequest;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliScadenzario;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface IResponsabiliScadenzarioService extends BaseService<ResponsabiliScadenzario, PkId> {

    public ResponsabiliScadenzario findByAmbitoRespChiave(String ambito, Integer responsabile, String chiave);

    public String leggiParametriConfigurazioneScadenzario(String ambito, String chiave, HttpServletRequest request);

    public String gestisciParametriConfigurazioneScadenzario(String ambito, String chiave, String valore);

    public void aggiornaParametriConfigurazioneScadenzario(String ambito, String chiave, String valore);
}
