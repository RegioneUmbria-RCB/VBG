package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OrariaperturatestataDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface OrariaperturatestataService extends BaseService<Orariaperturatestata, PkId> {

    /**
     * @see OrariaperturatestataDAO#findAll(Integer, Integer)
     */
    public List<Orariaperturatestata> findAll(Integer firstResult, Integer maxResult);

    /**
     * Trova la lista di Orariaperturatestata per una determinata istanza ordinata per i campi toDescrizione (asc)
     * 
     * @param istanza
     * @return
     * @throws IllegalArgumentException
     *             se l'istanza nulla o non trovata
     */
    public List<Orariaperturatestata> findByIstanza(Istanze istanza);

    /**
     * Il metodo copia gli orari collegati all'istanza sorgente all'istanza destinatario. Il metodo prima di replicare
     * gli orari collegati nell'istanza destinatario effettuerà un controllo sul destinatario in modo da non duplicare
     * gli orari
     * 
     * @param istanzaSorgente
     * @param istanzaDestinatario
     */
    public void copiaOrariAperturaAttivita(Istanze istanzaSorgente, Istanze istanzaDestinatario);
}
