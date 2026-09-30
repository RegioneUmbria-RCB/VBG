package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeprocureDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeprocureService extends BaseService<Istanzeprocure, PkId> {

    /**
     * @see IstanzeprocureDAO#findAll(Integer, Integer)
     */
    public List<Istanzeprocure> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle procure di un'istanza ordinate per anagrafeProcuratore.nominativo anagrafeProcuratore.nome
     * asc
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Istanzeprocure> findByIstanza(Integer codiceIstanza);

    /**
     * Torna la lista delle procure di un'anagrafe definita come Procuratore asc
     * 
     * @param codiceAnagrafe
     * @return
     */
    public List<Istanzeprocure> findByAnagrafeProcuratore(Integer codiceAnagrafe);

    /**
     * Torna la lista delle procure di un'anagrafe definita come Rappresentato
     * 
     * @param codiceAnagrafe
     * @return
     */
    public List<Istanzeprocure> findByAnagrafeRappresentato(Integer codiceAnagrafe);

    /**
     * 
     * @param idAnagrafeStorico
     * @return
     */
    public List<Istanzeprocure> findByAnagrafeStoricoRappresentato(Integer idAnagrafeStorico);

    /**
     * 
     * @param idAnagrafeStorico
     * @return
     */
    public List<Istanzeprocure> findByAnagrafeStoricoProcuratore(Integer idAnagrafeStorico);

    public int countByIstanze(Integer codiceIstanza);

    public int countByAnagrafeRappresentato(Integer codiceAnagrafe);

    public int countByAnagrafeProcuratore(Integer codiceAnagrafe);

    public List<Istanzeprocure> findByIstanzaAndAnagrafe(Integer codiceIstanza, Integer codiceAnagrafe);

    /**
     * Il metodo controlla che le anagrafiche immesse nei record di istanzeprocure siano effettivamente presenti nelle
     * anagrafiche dell'istanza indicata
     * 
     * @param codiceIstanza
     * @return
     */
    public boolean checkValiditaProcuraInIstanza(Integer codiceIstanza);

    /**
     * Ritorna la lista di tutti gli allegati di ISTANZEPROCURE provenienti da STC (record con il campo stcIdallegato
     * notBlank e stcIdDocumento not Blank e codiceoggetto nullo) filtrati per istanza
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Istanzeprocure> findProvenientiDaSTC(Integer codiceIstanza);

    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza);

    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza, TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum);

    /**
     * Restituisce la lista delle istanzeprocure che non sono presenti in DOCUMENTI_AUTORIZZAZIONE.
     * 
     * @param codiceIstanza
     * @param tipoRicercaDocumentoEnum
     * @param codiceAutorizzazione
     * @return
     */
    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanzaNonInDocAutorizzazione(Integer codiceIstanza,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum, Integer codiceAutorizzazione);
}
