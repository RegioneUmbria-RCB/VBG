package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * @author Riccardo Bocci
 * 
 */
public interface StradarioDAO extends BaseDAO<Stradario, PkId> {

    /**
     * Cerca tutte le strade ordinate per descrizione
     * 
     */
    public List<Stradario> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ricerca tutti i record filtrati per idcomune e like ({@link Restrictions#ilike}, {@link MatchMode#ANYWHERE}) su
     * campo descrizione ordinati per denominazione. Di defalut cerca anche quelli disabilitati
     * 
     * @param descrizione
     *            la stringa, o sottostringa per la quale effettuare la ricerca
     * @param codiceComune
     *            il comune per il quale si ricercano i dati
     * @param codiciComuniAbilitati
     *            la lista dei comuni abilitati sul quale limitare la ricerca
     * @return
     */
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, String[] codiciComuniAbilitati, Integer firstResult,
	    Integer maxResult);

    /**
     * <pre>
     * Ricerca tutti i record filtrati per idcomune e like ({@link Restrictions#ilike}, {@link MatchMode#ANYWHERE}) su
     * campo descrizione ordinati per denominazione. 
     * 
     * 		searchDisabilitati == true cerca anche quelli disabilitati , cioè datavalidita !=null 
     * 		searchDisabilitati == false cerca solo quelli attivi , cioè datavalidita ==null
     * 
     * @param descrizione
     *            la stringa, o sottostringa per la quale effettuare la ricerca
     * @param codiceComune
     *            il comune per il quale si ricercano i dati
     * @param codiciComuniAbilitati
     *            la lista dei comuni abilitati sul quale limitare la ricerca
     * @param searchDisabilitati
     * @return
     * </pre>
     */
    public List<Stradario> findByDescrizione(String descrizione, String codiceComune, String[] codiciComuniAbilitati, Integer firstResult,
	    Integer maxResult, boolean searchDisabilitati);

    /**
     * Ricerca tutti i record filtrati per idcomune e like ({@link Restrictions#ilike}, {@link MatchMode#ANYWHERE}) su
     * campo descrizione ordinati per denominazione
     * 
     * @param descrizione
     *            la stringa, o sottostringa per la quale effettuare la ricerca
     * @param codiceComune
     *            il comune per il quale si ricercano i dati
     * @param codiciComuniAbilitati
     *            la lista dei comuni abilitati sul quale limitare la ricerca
     * @return
     */
    public List<Stradario> findByDescrizioneEsatta(String descrizione, String codiceComune, String[] codiciComuniAbilitati, Integer firstResult,
	    Integer maxResult);

    /**
     * Torna una lista di strade ordinate per comune, descrizione per i comuni indicati come parametri
     * 
     * @param codiciComune
     * @return
     */
    public List<Stradario> findAllByCodiciComuni(String[] codiciComune);

    /**
     * Ritorna una lista di strade filtrati per mercato e per il campo descrizione (tramite una ilike) e ordinati per
     * descrizione. Di deafult mostra anche quelli disabilitati (mantenere compatibilità con il comportamento
     * precedente)
     * 
     * @param mercato
     * @param descrizione
     * @return
     */
    public List<Stradario> findByMercatoAndDescrizione(Integer codicemercato, String descrizione);

    /**
     * Ritorna una lista di strade filtrati per mercato e per il campo descrizione (tramite una ilike) e ordinati per
     * descrizione. Se searchDisabilitati==true allora cercherà anche quelle disabilitate
     * 
     * @param mercato
     * @param descrizione
     * @param searchDisabilitati
     * @return
     */
    public List<Stradario> findByMercatoAndDescrizione(Integer codicemercato, String descrizione, boolean searchDisabilitati);

    /**
     * Fa una query su stradario senza idcomune raggruppando per prefisso
     * 
     * @return
     */
    public List<String> findToponimiRaggruppati();
}
