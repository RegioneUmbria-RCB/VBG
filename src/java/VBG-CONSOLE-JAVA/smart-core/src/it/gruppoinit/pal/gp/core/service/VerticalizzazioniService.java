package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.VerticalizzazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneRegoleParametroHelper;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniconfigurazioniHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VerticalizzazioniService extends BaseService<Verticalizzazioni, PkId> {

    /**
     * 
     * @see VerticalizzazioniparametriService#findParametriConfiguratiByModulo(String)
     */
    public List<Verticalizzazioniparametri> getVerticalizzazioniparametri(String modulo);

    /**
     * Se la verticalizzazione esiste ed è <b>attiva</b>, recupera il parametro del modulo(verticalizzazione)
     * specificato. la ricerca è effettuata sia per il software corrente che per TT. Se trova il parametro per il
     * software corrente ritorna quello altrimenti quello per il software TT altrimenti <b>null</b>.
     * 
     * @param modulo
     *            verticalizzazione scelta
     * @param parametro
     *            parametro della verticalizzazione
     * @return il record della tabella verticalizzazioniparametri o null
     */
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro);

    /**
     * Se la verticalizzazione esiste ed è <b>attiva</b>, recupera il parametro del modulo(verticalizzazione)
     * specificato. la ricerca è effettuata sia per il software corrente che per TT. Se trova il parametro per il
     * software corrente ritorna quello altrimenti quello per il software TT altrimenti <b>null</b>.
     * 
     * @param modulo
     *            verticalizzazione scelta
     * @param parametro
     *            parametro della verticalizzazione
     * @param codiceComune
     * @return il record della tabella verticalizzazioniparametri o null
     */
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComune(String modulo, String parametro, String codiceComune);

    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComuneESoftware(String modulo, String parametro, String codiceComune,
	    String software);

    /**
     * Se la verticalizzazione esiste ed è <b>attiva</b>, recupera il parametro del modulo(verticalizzazione)
     * specificato. la ricerca è effettuata sia per il software corrente che per TT. Se trova il parametro per il
     * software corrente ritorna quello altrimenti quello per il software TT altrimenti <b>null</b>.
     * 
     * @param modulo
     *            verticalizzazione scelta
     * @param parametro
     *            parametro della verticalizzazione
     * @return il record della tabella verticalizzazioniparametri o null
     */
    public String getVerticalizzazioniparametriValore(String modulo, String parametro);

    /**
     * Se la verticalizzazione esiste ed è <b>attiva</b>, recupera il parametro del modulo(verticalizzazione)
     * specificato. la ricerca è effettuata sia per il software passato che per TT. Se trova il parametro per il
     * software passato ritorna quello altrimenti quello per il software TT altrimenti <b>null</b>.
     * 
     * @param modulo
     *            verticalizzazione scelta
     * @param parametro
     *            parametro della verticalizzazione
     * @param codiceSoftware
     *            codice del software
     * @return il record della tabella verticalizzazioniparametri o null
     */
    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro, String codiceSoftware);

    /**
     * @see VerticalizzazioniDAO#findByModulo(String)
     * @param modulo
     * @return
     */
    public Verticalizzazioni findByModulo(String modulo);

    /**
     * Verifica che per quel software e comune sia attiva una determinata verticalizzazione passata come parametro
     * 
     * @param modulo
     * @return false se non è attiva, true se è attiva
     */
    public boolean isAttiva(String modulo);

    /**
     * Verifica che per quel software e comune sia attiva una determinata verticalizzazione passata come parametro
     * 
     * @param modulo
     * @param software
     * @return false se non è attiva, true se è attiva
     */
    public boolean isAttiva(String modulo, String software);

    /**
     * @see VerticalizzazioniDAO#findByModulo(String)
     * @param modulo
     * @return
     */
    public Verticalizzazioni findByModuloEComune(String modulo, String codiceComune);

    /**
     * 
     * @param modulo
     * @param codiceComune
     * @return
     */
    public boolean isAttivaPerComune(String modulo, String codiceComune);

    /**
     * Verifica che per quel software e comune sia attiva una determinata verticalizzazione passata come parametro
     * 
     * @param modulo
     * @param software
     * @return false se non è attiva, true se è attiva
     */
    public boolean isAttivaPerComuneESoftware(String modulo, String software, String codiceComune);

    /**
     * Verifica che per quel software e comune sia attiva una determinata verticalizzazione passata come parametro e se
     * attiva verifica che il valore sia uguale a quello passato come argomento
     * 
     * @param modulo
     * @param parametro
     * @param valore
     * @return
     */
    public boolean isAttivaAndParametroEqualsToValore(String modulo, String parametro, String valore);

    /**
     * @see VerticalizzazioniDAO#findByVerticalizzazionibase(Verticalizzazionibase verticalizzazionibase)
     */
    public List<Verticalizzazioni> findByVerticalizzazionibase(Verticalizzazionibase verticalizzazionibase);

    /**
     * Il metodo recupare la lista di tutte le verticalizzazioni configurate per la verticalizzazione base passata e
     * setta il campo transiet flagSoftwarePerAbilitatotransiet a "true" se l'operatore loggato è abilitato per il
     * software per cui la verticalizzazione è settata ,altrimenti lo setta a false
     * 
     * @param verticalizzazionibase
     * @return
     */
    public Set<Verticalizzazioni> findByVerticalizzazionibaseAndCheckConfigurabilePerOperatore(Verticalizzazionibase verticalizzazionibase);

    /**
     * Torna true se l'installazione è di tipo Enterprise
     * 
     * @return
     */
    public boolean isInstallazioneEnterprise();

    @DeletableCacheElements
    public void resetObjectCached();

    /**
     * Il metodo ritorna una mappa contenente i parametri della verticalizzazione recuperati dalla chiamata a
     * {@link VerticalizzazioniService#getVerticalizzazioniparametri(String)}. la chiave della mappa corriesponde al
     * nome del parametro ed il valore della mappa al valore del parametro
     * 
     * @param modulo
     * @return
     */
    public Map<String, String> getVerticalizzazioniparametriMap(String modulo);

    /**
     * Ricerca se esiste una verticalizzazione filtrando per modulo (verticalizzazionibase), comune e software, nel caso
     * non sia presnete il comune verranno ricercate quelle codicecomune == null (verticalizzazione che sono valide per
     * tutti i comuni dell'associazione)
     * 
     * @param verticalizzazionibase
     * @param codicecomune
     * @param software
     * @return
     */
    public Verticalizzazioni findByComuneAndModulo(Verticalizzazionibase verticalizzazionibase, String codicecomune, String software);

    /**
     * Inserisce una verticalizzazione per modulo (verticalizzazionibase), comune e software, nel caso non sia presnete
     * il comune verrà inserita una verticalizzazione con codicecomune == null (verticalizzazione valida per tutti i
     * comuni dell'associazione). Di default la verticalazzazione verrà attivata.
     * 
     * @param modulo
     * @param codicecomune
     * @param software
     * @return
     */
    public void insert(String modulo, String codicecomune, String software);

    /**
     * Elimina una verticalizzazione cercando per per modulo (verticalizzazionibase), comune e software, nel caso non
     * sia presnete il comune verrà eliminata una verticalizzazione con codicecomune == null (verticalizzazione valida
     * per tutti i comuni dell'associazione).
     * 
     * @param modulo
     * @param codicecomune
     * @param software
     * @return
     */
    public void delete(String modulo, String codicecomune, String software);

    public boolean checkConfigurazioneMultiplaPerComune(String modulo, String parametro);

    public boolean checkConfigurazioneMultiplaPerSoftware(String modulo, String parametro);

    public List<VerticalizzazioniconfigurazioniHelper> findListaConfigurazioniPerComuneESoftware(String modulo, String parametro);

    public List<ConfigurazioneRegoleParametroHelper> findConfigurazioneComune(String modulo, String codiceComune);

    public Verticalizzazioniparametri getVerticalizzazioniparametri(String modulo, String parametro, boolean ignoraVerticalizzazioneAttiva);
}
