package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.TempirispostaId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface TempirispostaDAO extends BaseDAO<Tempirisposta, TempirispostaId> {

    /**
     * Lista ti pempi di risposta filtrati per idcomune e software
     * 
     */
    public List<Tempirisposta> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di tempi di risposta filtrati tramite un oggetto filter Il filtro effettua la ricerca sui campi
     * codice di: tipimovimento, tipicontromovimento, amministrazione, tipiprocedure.
     * 
     * @param tempirisposta
     * @return
     */
    public List<Tempirisposta> findByFilter(Tempirisposta tempirisposta);
}
