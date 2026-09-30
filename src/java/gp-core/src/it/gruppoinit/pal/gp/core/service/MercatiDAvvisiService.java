package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatiDAvvisiDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiDAvvisi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiDAvvisiService extends BaseService<MercatiDAvvisi, PkId> {

    /**
     * @see MercatiDAvvisiDAO#findAll(Integer, Integer)
     */
    public List<MercatiDAvvisi> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista degli avvisi legati al posteggio
     * 
     * @param codice
     * @param object
     * @param object2
     * @return
     */
    public List<MercatiDAvvisi> findAllByPosteggio(Integer codice, Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di codici (distinc) di tipi causali aoneri filtrati per codice anagrafe e codice posteggio. E'
     * possibile che più record con lo stesso codice; il metodo ne riporterà solo uno
     * 
     * @param codiceAnagrafe
     * @param codicePosteggio
     * @return
     */
    public List<Integer> findAvvisiByPosteggioAndAnagrafeDistincCausaliOneri(Integer codiceAnagrafe, Integer codicePosteggio);

    /**
     * verifica se ci sono tra gli avvisi filtrando per anagrafe,posteggio e causale onere almeno uno non verificato
     * 
     * @param codiceAnagrafe
     * @param codicePosteggio
     * @param causaleOnere
     * @return
     */
    public boolean isNotVerificatoAvvisoByAnagrafeAndPosteggioAndCausaleOnere(Integer codicePosteggio, Integer causaleOnere);

    public List<MercatiDAvvisi> findAvvisiMercatiD(Integer codicePosteggio, Integer codiceCausaleOnere);
}
