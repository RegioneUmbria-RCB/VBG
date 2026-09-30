package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AmministrProtocolloDAO;
import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrProtocolloHelper;

import java.util.List;

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
     * @param codiceAmministrazione
     * @param responsabile
     * @return
     * </pre>
     */
    public List<AmministrProtocolloHelper> findByComuniAndSoftwarePerOperatore(Integer codiceAmministrazione, Responsabili responsabile);

    public AmministrProtocollo findByAmministrazioneComuneESoftware(Integer codiceAmministrazione, String codiceComune, String software);

    /**
     * verifica se ci sono record per quell'amministrazione
     * 
     * @param codice
     * @return
     */
    public int countByAmministrazione(Integer codiceAmministrazione);
}
