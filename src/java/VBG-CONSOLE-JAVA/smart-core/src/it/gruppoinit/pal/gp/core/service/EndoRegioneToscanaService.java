package it.gruppoinit.pal.gp.core.service;

import it.eng.suap.xengine.model.service.xcommon.AzioneType;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.autocompiler.utils.ModuloEndoprocedimentoWrapper;
import it.gruppoinit.pal.gp.core.domain.cart.ElenchiEndoFACCT;
import it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface EndoRegioneToscanaService {

    /**
     * Cerca tutti gli endo attivabili definiti dal CART. Sono attivabili tutti gli endo che hanno un record in
     * stp_endo_tipo1.<br />
     * Se presente nel file deploy.properties la proprietà cart.codici_endo_regionali.attivabili è valorizzata allora
     * saranno attivabili solamente quelli censiti in questa proprietà
     * 
     * @param idAlberoProc
     * @return
     */
    public List<EndoFACCT> findElencoEndoCARTPerAttivita(String idcomune, Integer idAlberoProc);

    /**
     * Restituisce tre elenchi endo (endo CART, endo nono CART necessari e endo non CART ricorrenti) strutturati
     * gerarchicamente per famiglia e categoria
     * 
     * @param idAlberoProc
     * @return
     */
    public ElenchiEndoFACCT getElenchiEndoPerAttivita(String idcomune, Integer idAlberoProc, Collection<String> interventiLocali,
	    String codiceCatastaleComune);

    // public List<EndoFACCT> getEndoLocaliSelezionati(String idcomune, Integer idAlberoProc, List<String> codiciEndoSelez, String searchCodiceEndo);
    public StpEndoTipo2 getDatiAttivitaBdr(String idcomune, Integer idAttivita);

    public AzioneType checkAzione(String idcomune, Integer idAlberoproc, boolean isDomandaDinamica);

    /**
     * Restituisce tutti gli allegati degli endoprocedimenti i cui codici sono passati nella lista del primo argomento.
     * Gli altri argomenti servono per filtrare ulteriormente la lista dei risultati:
     * 
     * @param codiciEndoAttivi
     *            : lista dei codici endo da cui recuperare gli allegati
     * @param idProc
     *            : se valorizzato verranno restituiti solo gli allegati dell'endo avente il codice specificato
     * @param descAllegato
     *            : se valorizzato gli allegati restituiti verranno filtrati in base alla loro descrizione.
     * @param searchAllegatoStyle
     *            : serve per specificare la modalità di applicazione del filtro per descrizione.
     * @return
     */
    public List<Allegati> getAllegatiEndoAttivi(Set<String> codiciEndoAttivi, String idProc, String descAllegato,
	    FieldOperationsEnum searchAllegatoStyle, String codiceComune);

    /**
     * Restituisce tutti i documenti ereditati dall'albero per il procedimento selezionato passato nel primo argomento.
     * Gli altri argomenti servono per filtrare ulteriormente la lista dei risultati:
     * 
     * @param idAlberoProc
     *            : Id numerico del nodo di alberoproc selezionato per cui cercare i documenti ereditati
     * @param descAllegato
     *            : se valorizzato i documenti restituiti verranno filtrati in base alla loro descrizione.
     * @param searchAllegatoStyle
     *            : serve per specificare la modalità di applicazione del filtro per descrizione.
     * @return
     */
    public List<AlberoprocDocumenti> getDocumentiEreditatiEndoAttivi(Integer idAlberoProc, String descAllegato,
	    FieldOperationsEnum searchAllegatoStyle);

    /**
     * Restituisce tutti i modelli dinamici degli endoprocedimenti i cui codici sono passati nella lista del primo
     * argomento. Gli altri argomenti servono per filtrare ulteriormente la lista dei risultati:
     * 
     * @param codiciEndoAttivi
     *            : lista dei codici endo da cui recuperare i modelli dinamici
     * @param idProc
     *            : se valorizzato verranno restituite solo i modelli dinamici dell'endo avente il codice specificato
     * @param descDynModello
     *            : se valorizzato i modelli restituiti verranno filtrati in base alla loro descrizione.
     * @param searchAllegatoStyle
     *            : serve per specificare la modalità di applicazione del filtro per descrizione.
     * @return
     */
    public List<Inventarioprocdyn2modellit> getSchedeDinamicheEndoAttivi(Set<String> codiciEndoAttivi, String idProc, String descDynModello,
	    FieldOperationsEnum searchAllegatoStyle);

    /**
     * Restituisce tutti gli allegati, documenti ereditati e modelli dinamici degli endoprocedimenti i cui codici sono
     * passati nella lista del primo argomento. Gli altri argomenti servono per filtrare ulteriormente la lista dei
     * risultati. Il metodo restituisce tutti insieme i risultati dei metodi getAllegatiEndoAttivi,
     * getDocumentiEreditatiEndoAttivi e getSchedeDinamicheEndoAttivi in un'unica lista ordinata per ordine e
     * descrizione.
     * 
     * @param codiciEndoAttivi
     *            : lista dei codici endo da cui recuperare gli allegati e i modelli dinamici
     * @param idAlberoProc
     *            : Id numerico del nodo di alberoproc selezionato per cui cercare i documenti ereditati
     * @param idProc
     *            : se valorizzato verranno restituiti solo gli allegati dell'endo avente il codice specificato
     * @param descAllegato
     *            : se valorizzato i dati restituiti verranno filtrati in base alla loro descrizione.
     * @param searchAllegatoStyle
     *            : serve per specificare la modalità di applicazione del filtro per descrizione.
     * @return
     */
    public List<ModuloEndoprocedimentoWrapper> getAllegatiDocumentiSchedeEndoAttivi(Set<String> codiciEndoAttivi, Integer idAlberoProc,
	    String idProc, String descAllegato, FieldOperationsEnum searchAllegatoStyle, Boolean flagDomandadinamica, String codiceComune);

    List<Allegati> getAllegatiFromProcedimentoRegionale(String descAllegato, FieldOperationsEnum searchAllegatoStyle, Inventarioprocedimenti invProc,
	    String codiceComune);

    List<Allegati> getAllegatiFromProcedimentoLocale(String descAllegato, FieldOperationsEnum searchAllegatoStyle, Inventarioprocedimenti invProc,
	    String codiceComune);

    public EndoFACCT newEndoFacct(Inventarioprocedimenti ipComunica, boolean obbligatorio, Set<String> elencoEndoAttivabili);

    /**
     * Il metodo verifica che l'elenco degli endoprocedimenti selezionati in una pratica in compilazione sia ancora
     * compatibile con gli endo selezionabili per quel procedimento al momento di riprendere la pratica
     * 
     * @param idcomune
     * @param idAlberoProc
     * @param interventiLocali
     * @param codiceCatastaleComune
     * @param codiciEndoAttivi
     * @return
     */
    public boolean checkValiditaEndoAttivi(String idcomune, Integer idAlberoProc, Collection<String> interventiLocali, String codiceCatastaleComune,
	    Collection<String> codiciEndoReg, Collection<String> codiciEndoAttivi);
    
    /**
     * Se nell'endoprocedimento amministrativo associato all'intervento è richiesta la presenza di un endo collegato, 
     * il metodo verifica se tale endo collegato è stato selezionato dall'utente. Se non richiesto il metodo restituisce true.
     * @param codiciEndoAttivi
     * @param codiciEndoReg
     * @param codiciEndoLoc
     * @return
     */
    public boolean checkPresenzaEndoCollegato(Integer idAlberoProc, Collection<String> codiciEndoAttivi, Collection<String> codiciEndoReg, Collection<String> codiciEndoLoc);
}
