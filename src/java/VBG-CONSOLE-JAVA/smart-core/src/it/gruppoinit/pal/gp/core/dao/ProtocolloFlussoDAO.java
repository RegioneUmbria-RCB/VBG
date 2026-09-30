/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 */
public interface ProtocolloFlussoDAO extends BaseDAO<ProtocolloFlusso, String> {

    /**
     * Il metodo deve restituire una lista di flussi in base a quelli che sono attivi per il responsabile che gli viene
     * passato, la lista può contenere i flussi che non vogliono essere mostrati anche se attivi per quell'operatore,
     * mettendo NULL tale parametro verranno mostrati tutti.
     * 
     * @param responsabile
     * @param escludiFlussi
     * @return lista dei protcollo flussi attivi
     */
    public List<ProtocolloFlusso> findByResponsabile(Responsabili responsabili, List<String> escludiFlussi);

    /**
     * Restituisce tutti i Protocolli Flusso ordinandoli per il campo descrizione ascendente
     */
    public List<ProtocolloFlusso> findAll(Integer firstResult, Integer maxResult);
}
