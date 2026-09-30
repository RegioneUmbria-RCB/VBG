package it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniConcessioniDatiGenerali;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author fabrizioc
 */
public interface AutorizzazioniConcessioniService extends BaseService<AutorizzazioniConcessioni, PkId> {

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniConcessioniDAO#findAll(Integer, Integer)
     */
    public List<AutorizzazioniConcessioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniConcessioniDAO#findConcessioniByIstanza(Istanze)
     */
    public List<AutorizzazioniConcessioni> findConcessioniByIstanza(Integer codiceIstanza);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniConcessioniDAO#findConcessioniAttiveByMercatoEUso(Mercati, MercatiUso)
     * @param mercato
     * @param uso
     * @return
     */
    public List<AutorizzazioniConcessioni> findConcessioniAttiveByMercatoEUso(Mercati mercato, MercatiUso uso);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniConcessioniDAO#findConcessioniAttiveByMercato(Mercati mercato)
     */
    public List<AutorizzazioniConcessioni> findConcessioniAttiveByMercato(Mercati mercato);

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniConcessioniDAO#findConcessioniByMercato(Mercati mercato)
     */
    public List<AutorizzazioniConcessioni> findConcessioniByMercato(Mercati mercato);

    /**
     * Ritorna le concessioni attive filtrando per mercato ed uso (opzionale), se l'uso non viene passato (null o
     * id.codice==null) verranno mostrate tutte le concessioni attive per il mercato.
     * 
     * @param mercato
     * @param uso
     *            (opzionale)
     * @return
     */
    public List<AutorizzazioniConcessioni> findConcessioniAttive(Mercati mercato, MercatiUso uso);

    public int countConcessioniByCodiceMercato(Integer codiceMercato);

    public AutorizzazioniConcessioni findConcessioneAttualeByMercatoAndUsoAndPosteggio(Integer codiceMercato, Integer codiceUso,
	    Integer codicePosteggio);

    public List<AutorizzazioniConcessioni> findByAutorizzazioneAttuale(Integer codiceAutorizzazione);

    public AutorizzazioniConcessioniDatiGenerali findDatiGeneraliConcessione(Integer codiceAutorizzazione);

    /**
     * Cessa tutte le concessioni attive presenti un determinato posteggio
     * 
     * @param id
     *            del posteggio
     * @param data
     *            di cessazione
     * @param id
     *            della causale di cessazione (opzionale)
     * @return numero di concessioni realmente cessate
     */
    public int cessaConcessioniByIdPosteggio(int idPosteggio, Date dataCessazione, int idCausaleCessazione);

    /**
     * Cerca su autorizzazioni_concessioni i record dove fkIdautCollegata è presente e torna il primo record
     * fk_idaut_attuale trovato (attuale identificativo della concessione)
     * 
     * @param fkIdautCollegata
     * @return
     */
    public Integer getFkIdautAttualePerAutCollegata(Integer fkIdautCollegata);

    /**
     * Per ogni idAutorizzazione (autorizzazioni.fkidautattuale) trova l'autorizzazione collegata (se presente)
     * 
     * @param auts
     * @return
     */
    public Map<Integer, IdentificativoDescrizioneBean> findAutorizzazioniCollegate(Set<Integer> auts);
}
