package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcCoeffcontributoDAO;
import it.gruppoinit.pal.gp.core.domain.CcCoeffcontributo;
import it.gruppoinit.pal.gp.core.domain.CcDestinazioni;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface CcCoeffcontributoService extends BaseService<CcCoeffcontributo, PkId> {

    /**
     * @see CcCoeffcontributoDAO#findAll(Integer, Integer)
     */
    public List<CcCoeffcontributo> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di CcCoeffcontributo filtrati per l'oggetto ccValiditacoefficienti
     * 
     * @param ccValiditacoefficienti
     * @return
     */
    public List<CcCoeffcontributo> findByValiditaCoefficiente(CcValiditacoefficienti ccValiditacoefficienti);

    /**
     * Controlla se esitse il record per il CcTipointervento passato
     * 
     * @param entity
     */
    public boolean existRecordByCcTipointervento(CcTipointervento entity);

    /**
     * Ritorna, se esiste, un oggetto CcCoeffcontributo filtrando per ccDestinazioni e ccTipointervento altrimenti null
     * 
     * @param ccDestinazioni
     * @param ccTipointervento
     * @return
     */
    public CcCoeffcontributo findByCoefficienteAndccDestinazioniAndccTipointervento(CcValiditacoefficienti validitacoefficienti,
	    CcDestinazioni ccDestinazioni, CcTipointervento ccTipointervento);
}
