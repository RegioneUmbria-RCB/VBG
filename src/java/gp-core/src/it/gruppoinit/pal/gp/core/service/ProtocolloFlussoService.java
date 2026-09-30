/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ProtocolloFlussoDAO;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 */
public interface ProtocolloFlussoService extends BaseService<ProtocolloFlusso, String> {

    /**
     * @see ProtocolloFlussoDAO#findByResponsabile(Responsabili responsabili, List<String> escludiFlussi)
     */
    public List<ProtocolloFlusso> findByResponsabile(Responsabili responsabili, List<String> escludiFlussi);

    /**
     * @see ProtocolloFlussoDAO#findAll(Integer, Integer)
     */
    public List<ProtocolloFlusso> findAll(Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Ritorna una lista di flussi protocollo. Il metodo accetta 3 stringhe
     * 
     *    1- WebCostants.FLUSSO_ARRIVO = "A"; 
     *    2- WebCostants.Flusso_INTERNO = "I"; 
     *    3- WebCostants.FLUSSO_PARTENZA = "P"; Se un
     *    
     *    parametro è null quel tipo di flusso non viene riportatao nella lista;
     * 
     * @return
     * </pre>
     */
    public List<ProtocolloFlusso> findByTipiFlussi(String flussoPartenza, String flussoArrivo, String flussoInterno);
}
