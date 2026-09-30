package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiTDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeAccessoAttiTHelper;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeAccessoAttiTService extends BaseService<IstanzeAccessoAttiT, PkId> {

    /**
     * @see IstanzeAccessoAttiTDAO#findAll(Integer, Integer)
     */
    public List<IstanzeAccessoAttiT> findAll(Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * @param codiceIstanzaAccessoAttiT
     *            codice dell'istanza di accesso atti di partenza a cui associare le istanze di accesso atti d che si
     *            andranno a selezionare
     * @param codiceIstanze
     *            array dei codici delle istanze selezionate
     * @param codiciIstanzeFlagSelezionato
     *            array dei codici delle istanze selezionate per i quali si vogliono far visualizzare i documenti validi
     * @return una lista di IstanzeAccessoAttiTHelper 
     * 	      Ogni oggetto conterrà:
     *        1. Il codice di istanze accesso atti t
     *        2. IstanzeAccessoAttiDHelper 		: istanza rappresentativa del fascicolo
     *        3. List<IstanzeAccessoAttiDHelper>	: lista di istanze legate al fascicolo
     *  Nell'oggetto IstanzeAccessoAttiDHelper il campo <b>flgVisualizzaDoc</b> verrà popolato:
     *  	1. true	: se l'elemento dell'array codiceIstanze è presente nell'array codiciIstanzeFlagSelezionato
     *  	2. false  se l'elemento dell'array codiceIstanze non è presente nell'array codiciIstanzeFlagSelezionato
     * 
     * </pre>
     * 
     */
    public List<IstanzeAccessoAttiTHelper> findIstanzeCollegate(Integer codiceIstanzaAccessoAttiT, String[] codiceIstanze,
	    String[] codiciIstanzeFlagSelezionato);

    public List<IstanzeAccessoAttiT> findByIstanza(Integer codiceIstanza);
}
