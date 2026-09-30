package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniTDAO;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniTStatoEnum;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunicazioniTService extends BaseService<ComunicazioniT, PkId> {

    /**
     * @see ComunicazioniTDAO#findAll(Integer, Integer)
     */
    public List<ComunicazioniT> findAll(Integer firstResult, Integer maxResult);

    public String findListaFirmatariToHtml(Integer codiceComunicazionet);

    //public void elaboraComunicazione(ComunicazioniT comunicazioniT);
    public ComunicazioniT inserCreaComunicazioniAutorizzazioniConcessioni(List<AutorizzazioniConcessioni> listAutorizzazioniConcessioni);

    public void updateStatoComunicazioneT(Integer codiceComunicazioneT, ComunicazioniTStatoEnum comunicazioniTStatoEnum);
}
