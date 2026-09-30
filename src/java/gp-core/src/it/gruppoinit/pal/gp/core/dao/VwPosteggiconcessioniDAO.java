package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioni;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioniId;

import java.util.List;

public interface VwPosteggiconcessioniDAO extends BaseDAO<VwPosteggiconcessioni, VwPosteggiconcessioniId> {

    /**
     * metodo per il recupero dalla vista VW_POSTEGGICONCESSIONI di tutti i record filtrati per CODICEMERCATO e
     * IDPOSTEGGIO. INNER JOIN con MERCATI_D per ordinamento ASC per CODICEPOSTEGGIO. sono recuperati tutti i posteggi e
     * l'eventuale concessione. Non sono inclusi i posteggi disabilitati
     * 
     * @param codiceMercato
     * @param idUso
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<VwPosteggiconcessioni> findPosteggiMercatoUso(Integer codiceMercato, Integer idUso, Integer firstResult, Integer maxResults);

    /**
     * metodo per il recupero dalla vista VW_POSTEGGICONCESSIONI il conteggio di tutti i record filtrati per
     * CODICEMERCATO e IDPOSTEGGIO. INNER JOIN con MERCATI_D per ordinamento ASC per CODICEPOSTEGGIO. sono recuperati
     * tutti i posteggi e l'eventuale concessione. Non sono inclusi i posteggi disabilitati
     * 
     * @param codiceMercato
     * @param idUso
     * @param maxResults
     * @param firstResult
     * @return
     */
    public int countPosteggiMercatoUso(Integer codiceMercato, Integer idUso);

    public List<VwPosteggiconcessioni> findByMercatoUsoAndPosteggio(Integer codiceMercato, Integer idUso, Integer idPosteggio, Integer firstResult,
	    Integer maxResults);
}
