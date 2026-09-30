package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniDConcessioniDAO;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniDConcessioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ComunicazioniDConcessioniDTO;

import java.util.List;

/**
 * 
 * @author
 */
public interface ComunicazioniDConcessioniService extends BaseService<ComunicazioniDConcessioni, PkId> {

    /**
     * @see ComunicazioniDConcessioniDAO#findAll(Integer, Integer)
     */
    public List<ComunicazioniDConcessioni> findAll(Integer firstResult, Integer maxResult);

    public List<ComunicazioniDConcessioni> findByComunicazioniD(Integer codiceComunicazioniD);

    public List<ComunicazioniDConcessioni> findByComunicazioniT(Integer codicecomunicaziot);

    public List<ComunicazioniDConcessioniDTO> findByComunicazioniT(Integer codiceComunicazione, Integer firstResult, Integer maxResult);

    public List<ComunicazioniDConcessioniDTO> findByComunicazioniTWithEvent(Integer codiceComunicazioneT, int startRowPage, int endRowPage);

    public int countComunicazioniT(Integer codiceComunicazioneT);

    public List<ComunicazioniDConcessioni> findByComunicazioniTNonTerminate(Integer codicecomunicaziot);
}
