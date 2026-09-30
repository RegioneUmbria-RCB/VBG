package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniparametriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniparametriHelper;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author gianpaolot
 */
public interface VerticalizzazioniparametriService extends BaseService<Verticalizzazioniparametri, PkId> {

    /**
     * @see VerticalizzazioniparametriDAO#findAll(Integer, Integer)
     */
    public List<Verticalizzazioniparametri> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see VerticalizzazioniparametriDAO#findByModuloAndIdcomune(String modulo, String idcomune)
     */
    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftware(String modulo, String idcomune, String software);

    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftwareAndComune(String modulo, String comune, String software);

    /**
     * Il metodo torna i parametri configurati per il modulo. I parametri trovati saranno cercati tra il software
     * corrente e il software TT. Quelli che vengono trovati per il software corrente sovrascrivono quelli del software
     * TT.
     */
    public List<Verticalizzazioniparametri> findParametriConfiguratiByModulo(String modulo);

    public List<Verticalizzazioniparametri> findParametriConfiguratiByModuloAndComune(String modulo, String codiceComune);

    /**
     * Il metdoto ritorna una lista di oggetti verticalizzaioniParametriHelper.<br/>
     * L'oggetto è coposto da due campi<br/>
     * 1.software<br/>
     * 2.Lista di oggetti verticalizzazioniParamertri (filtrati per il software in esame ed idcoume).<br/>
     * Ogni oggetto della lista di verticalizzazioneParametri avrà il campo @Transient flagSoftwarePerAbilitatotransiet
     * settato a true.<br/>
     * Tale campo aggiuntivo indica che l'operatore loggato può operare su quel record.<br/>
     * 
     * @param modulo
     * @return
     */
    public List<VerticalizzazioniparametriHelper> findVerticalizzazioniparametriHelperAndCheckConfigurabilePerOperatore(String modulo,
	    String software, String codiceComune);

    /**
     * Il metdoto ritorna una lista di oggetti verticalizzaioniParametriHelper filtrati per comune.<br/>
     * L'oggetto è coposto da due campi<br/>
     * 1.software<br/>
     * 2.Lista di oggetti verticalizzazioniParamertri (filtrati per il software in esame ed idcoume).<br/>
     * Ogni oggetto della lista di verticalizzazioneParametri avrà il campo @Transient flagSoftwarePerAbilitatotransiet
     * settato a true.<br/>
     * Tale campo aggiuntivo indica che l'operatore loggato può operare su quel record.<br/>
     * 
     * @param modulo
     * @param responsabilisoftwares
     * @return
     */
    public List<VerticalizzazioniparametriHelper> findVerticalizzazioniparametriHelperAndCheckConfigurabilePerOperatoreAndComune(String modulo,
	    String comune, Set<Responsabilisoftware> responsabilisoftwares);

    /**
     * <pre>
     * Ritorna il nome del parametro per la verticalizzazione STC e il valore passato. 
     * Prima di effettuare la query il
     * metodo controlla se il valore è contenuto nella mappa; se lo trova restituisce il valore della mappa altrimenti
     * lo cerca su db e salva il valore nella mappa. La chiave per salvare il valore nella mappa sarà composta:
     * idcomuneAlias + "-" + software + "-" + value.
     * 
     * @return
     * 
     * </pre>
     */
    public String findNomeNodoModuloSTC(String value);

    /**
     * <pre>
     * Ritorna il nome del parametro filtrando per modulo e valore passato:
     * <ol>
     * 	<li>1- Ritorna l'oggetto Verticalizzazioniparametri se esiste </li>
     *  <li>2- null se non esiste</li>
     * </ol>
     * @param modulo
     * @param value
     * @return
     * </pre>
     */
    public Verticalizzazioniparametri findVerticalizzazioniparametriByModuloAndValue(String codiceNodo, String idMittente);

    /**
     * </pre> Il medoto deve:
     * <ol>
     * <li>A partire dal codice del nodo STC recupera il nome associato (Es.
     * [codNodo,nomeNodo:1200,NLA_IDNODO_AREARISERVATA]</li>
     * <li>Dal nome del nodo deve decofificare a quale servizio appartiene (Es. AREARISERVATA,PEOPLE,PEC...). Il metodo
     * funziona se viene rispettata la nomenclatura NLA_IDNODO_NOMESERVIZIO. Il metodo ritornerà NOMESERVIZIO (Es
     * NLA_IDNODO_AREARISERVATA ---> AREARISERVATA). L'unico caso particolare sarà per il nodo NLA_IDNODO che
     * rappresenta il BACKOFFICE, nel caso di nodo di backoffice verrà recuperato il modulo che ha inviato la pratica
     * tramite il parametro "idMittente" (Es COMMERCIO,EDILIZIA...).</li>
     * </ol>
     * <ul>
     * <li>
     * a. Nel caso non venga rispettata la nomenclatura e/o il parametro codiceNodo stringa vuota il sistema riporterà
     * il nome del servizio come NON_DEFINITO</li>
     * <li>
     * b. Nel caso il servizio recuperato sia quello di BACKOFFICE (NLA_NODOID) se l'idMittente è stringa vuota il nome
     * del servizio riportato sarà NON_DEFINITO</li>
     * <ol>
     * 
     * 
     * @param codiceNodo
     * @param idMittente
     * @return </pre>
     */
    public String decodeNomeNodoModuloSTC(String codiceNodo, String idMittente);

    @DeletableCacheElements
    public void resetObjectCached();
}
