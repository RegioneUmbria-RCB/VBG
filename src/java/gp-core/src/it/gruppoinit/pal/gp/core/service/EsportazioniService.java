package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.EsportazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;

/**
 * 
 * @author
 */
public interface EsportazioniService extends BaseService<Esportazioni, PkId> {

    /**
     * @see EsportazioniDAO#findAll(Integer, Integer)
     */
    public List<Esportazioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * ritorna una lista di esportazioni filtrare per idcomune in ('<PARAMETRO IDENTEBASE DELLA TABELLA
     * PENTAHOCFG >','<IDCOMUNE DELL'INSTALLAZIONE>)
     * 
     * @return
     */
    public List<Esportazioni> findEsportazioni(TipicontestoesportazioniEnum tipicontestoesportazioniEnum);

    /**
     * ritorna una lista di esportazioni filtrare per idcomune in ('<PARAMETRO IDENTEBASE DELLA TABELLA
     * PENTAHOCFG >','<IDCOMUNE DELL'INSTALLAZIONE>) escludendo quelli con id passati
     * 
     * @return
     */
    public List<Esportazioni> findEsportazioniEscludiRecord(TipicontestoesportazioniEnum tipicontestoesportazioniEnum, List<PkId> ids);

    public void inserisci(Esportazioni esportazioni);
}
