package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniTMercatoDAO;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniTMercato;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunicazioniTMercatoService extends BaseService<ComunicazioniTMercato, PkId> {

    /**
     * @see ComunicazioniTMercatoDAO#findAll(Integer, Integer)
     */
    public List<ComunicazioniTMercato> findAll(Integer firstResult, Integer maxResult);

    public List<ComunicazioniTMercato> findByCodiceMercato(Integer codicemercato);

    public boolean isExistByCodiceMercato(Integer codicemercato);

    /**
     * Il metodo crea la struttura base per le comunicazioni verso i posteggi. La tipologia della comunicazione sarà
     * messa di default MERCATO in quanto si sta sviluppando una procedura ad hoc per il mercato e non cisarà la
     * possibilità di scelta tramite form
     * 
     * @param codice
     * @param listacodici
     */
    public ComunicazioniTMercato insertCreaComunicazione(Integer codiceMercato, String[] listacodici, List<Integer> codiciUsoPercomunicazione);

    public void deleteComunicazione(ComunicazioniTMercato comunicazioniTMercato);

    public void updateComunicazione(ComunicazioniTMercato comunicazioniTMercato, ComunicazioniT comunicazioniT, Boolean flagbloccaconfigurazione);
}
