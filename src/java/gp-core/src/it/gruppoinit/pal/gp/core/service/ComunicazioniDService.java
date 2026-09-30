package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazioneEnum;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDStatoEnum;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunicazioniDService extends BaseService<ComunicazioniD, PkId> {

    /**
     * @see ComunicazioniDDAO#findAll(Integer, Integer)
     */
    public List<ComunicazioniD> findAll(Integer firstResult, Integer maxResult);

    public List<ComunicazioniD> findByComunicazioniT(Integer codiceComunicazioneT);

    //    public List<ComunicazioniDDTO> findByComunicazioniT(Integer codiceComunicazione, Integer firstResult, Integer maxResult);
    //
    //    public List<ComunicazioniDDTO> findByComunicazioniTWithEvent(Integer codice, Integer firstResult, Integer maxResult);
    public Integer countComunicazioniT(Integer codiceComunicazioneT);

    public void upadateStato(Integer codiceComD, ComunicazioniDStatoEnum inizializzata);

    public void updateComunicazioniDConErrore(Integer codiceComD, PassoCreazioneComunicazioneEnum passoCreazioneComunicazioneEnum, String errore);

   
}
