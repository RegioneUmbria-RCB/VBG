package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.AmministrProtocolloDAO;
import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrProtocolloHelper;
import it.gruppoinit.pal.gp.core.features.amministrazioni.configurazione.protocollo.AmministrazioneProtocolloModel;
import it.gruppoinit.pal.gp.core.features.amministrazioni.configurazione.protocollo.AmministrazioniProtocolloRequest;

/**
 * 
 * @author
 */
public interface AmministrProtocolloService extends BaseService<AmministrProtocollo, PkId> {

    /**
     * @see AmministrProtocolloDAO#findAll(Integer, Integer)
     */
    public List<AmministrProtocollo> findAll(Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Ritorna una lista di oggetti AmministrProtocolloHelper che contengo il campo : comune : desc del comune
     * List<AmministrProtocollo> : lista di AmministrProtocollo
     * 
     * &#64;param codiceAmministrazione
     * &#64;param responsabile
     * &#64;return
     * </pre>
     */
    public List<AmministrProtocolloHelper> findByComuniAndSoftwarePerOperatore(Integer codiceAmministrazione, Responsabili responsabile);

    public List<AmministrazioneProtocolloModel> findByComuneAndSoftware(AmministrazioniProtocolloRequest request);

    public AmministrProtocollo findByAmministrazioneComuneESoftware(Integer codiceAmministrazione, String codiceComune, String software);

    /**
     * verifica se ci sono record per quell'amministrazione
     * 
     * @param codice
     * @return
     */
    public int countByAmministrazione(Integer codiceAmministrazione);
}
