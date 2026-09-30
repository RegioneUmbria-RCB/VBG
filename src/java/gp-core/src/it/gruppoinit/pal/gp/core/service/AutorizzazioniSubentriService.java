package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSubentriDAO;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface AutorizzazioniSubentriService extends BaseService<AutorizzazioniSubentri, PkId> {

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniSubentriDAO#findAll(Integer, Integer)
     */
    public List<AutorizzazioniSubentri> findAll(Integer firstResult, Integer maxResult);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniSubentriDAO#findByEstremi(String, Date, String, Integer)
     * @param autoriznumero
     * @param autorizdata
     * @param codicecomune
     * @param codiceregistro
     * @return
     */
    public AutorizzazioniSubentri findAutSubOConcSubByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniSubentriDAO#findAutorizzazioniSubentriByIstanza(Istanze)
     * @param istanza
     * @return
     */
    public List<AutorizzazioniSubentri> findAutorizzazioniSubentriByIstanza(Istanze istanza);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniSubentriDAO#findConcessioniSubentriByIstanza(Istanze)
     * @param istanza
     * @return
     */
    public List<AutorizzazioniSubentri> findConcessioniSubentriByIstanza(Integer codiceIstanza);

    /**
     * Ritorna la lista dei subentri collegati alla concessione attiva passata order by data cessazione , id @param
     * order
     * 
     * @param codiceConcessioneAttiva
     * @return
     */
    public List<AutorizzazioniSubentri> findSubentriByConcessione(Integer codiceConcessioneAttiva, Integer firstResult, Integer maxResult,
	    OrderTypeEnum order);

    /**
     * Torna la lista delle AutorizzazioniSubentri di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<AutorizzazioniSubentri> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * order by data_cess desc, id desc
     * 
     * @param codiceAutorizzazione
     * @param i
     * @param j
     * @return
     */
    public List<AutorizzazioniSubentri> findByAutorizzazione(Integer codiceAutorizzazione, Integer firstResult, Integer maxResults);

    public int countByFilter(AutorizzazioniFilter filter);

    public List<AutorizzazioniSubentri> findAutorizzazioniSubentriByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult);

    public AutorizzazioniSubentri findByNumeroAndComune(String autoriznumero, String codicecomune);

    /**
     * metodo per inserire i subentri all'autorizzazione e concessione passata come argomento.
     * 
     * @param autorizzazioniSubentri
     * @param autorizzazioneSubentrata
     * @return 
     */
    public AutorizzazioniSubentri inserisciSubentroDaImport(AutorizzazioniSubentri autorizzazioniSubentri, Autorizzazioni autorizzazioneSubentrata);

    public void clear();
}
