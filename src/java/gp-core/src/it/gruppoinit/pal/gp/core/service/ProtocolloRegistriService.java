package it.gruppoinit.pal.gp.core.service;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;
import it.gruppoinit.pal.gp.core.domain.helper.ProtocolloRegistriHelper;

import java.util.List;

public interface ProtocolloRegistriService extends BaseService<ProtocolloRegistri, PkId> {

    /**
     * Torna la lista dei ProtocolloRegistri di un'AmministrazioneMittente
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<ProtocolloRegistri> findByAmministrazioniMittente(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista dei ProtocolloRegistri di un'AmministrazioneDestinatario
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<ProtocolloRegistri> findByAmministrazioniDestinatario(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista dei ProtocolloRegistri legati ad un tipomovimento
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<ProtocolloRegistri> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult);

    public ProtocolloRegistriHelper findByRegistroSoftwareComune(Integer idRegistro, String codiceComune);

    public List<ProtocolloRegistri> findByRegistro(Integer codiceRegistro);
}
