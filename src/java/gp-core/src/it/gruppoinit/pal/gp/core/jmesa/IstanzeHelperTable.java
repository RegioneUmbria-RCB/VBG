package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkAltriIndirizziHelperCellEditor;
import org.jmesa.customColumn.LinkAutorizzazioniHelperCellEditor;
import org.jmesa.customColumn.LinkIstanzeprocedimentiHelperCellEditor;
import org.jmesa.customColumn.LinkSorteggiCellEditorHelper;
import org.jmesa.customColumn.LinkStatoIstanzaCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.context.ContextLoader;

public class IstanzeHelperTable extends GenerateTable<IstanzeListHelper> {

    private IstanzeFilter filter;
    private boolean isArchiviopratiche;
    private boolean isTipologiaistanza;
    private ResponsabiliService responsabiliService;
    private ConfigurazioneutenteService configurazioneutenteService;
    private UserSecurityService userSecurityService;

    public IstanzeHelperTable(IstanzeFilter filter, ResponsabiliService responsabiliService, ConfigurazioneutenteService configurazioneutenteService,
	    UserSecurityService userSecurityService, boolean isArchiviopratiche, boolean isTipologiaistanza) {

	super();
	this.filter = filter;
	this.isArchiviopratiche = isArchiviopratiche;
	this.isTipologiaistanza = isTipologiaistanza;
	this.responsabiliService = responsabiliService;
	this.configurazioneutenteService = configurazioneutenteService;
	this.userSecurityService = userSecurityService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "dataprotocollo"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	String urlBack = "../istanze/listIstanze.htm";
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkBaseColumn(request, "numeroistanza", "label.numeroistanza", "codiceistanza", "../istanze/view.htm?codice=", urlBack,
		false, false, true, null, "2%");
	String visualizzaCodicePraticaTelematica = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISCODPRATEL, "1");
	if (visualizzaCodicePraticaTelematica.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("codicepraticatelematica", "label.codice_pratica_telematica", false, false, true, null, "8%");
	}
	columnJmesa.addLinkLabelBaseColumn(request, "codiceistanza", "label.M", "label.M", "../movimenti/list.htm?codiceIstanza=", urlBack, false,
		false, "label.colonna_movimenti_help", "2%");
	columnJmesa.addLinkLabelBaseColumn(request, "codiceistanza", "label.E", "label.E", "../movimenti/listElaborazione.htm?codiceIstanza=",
		urlBack, false, false, "label.colonna_elaborazione_help", "2%");
	String visualizzaAutorizzazioni = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI, "0");
	if (visualizzaAutorizzazioni.equalsIgnoreCase("1")) {
	    //  se countautorizzazioni > 0 allora ci va messa la A e chiamata ajax che visualizza le autorizzazioni e le concessioni tramite il metodo
	    columnJmesa.addCellEditorCustomLabelColumn(request, "countautorizzazioni", "label.A", "label.A", new LinkAutorizzazioniHelperCellEditor(
		    request, urlBack), false, false, false, "label.colonna_autorizzazioni_help", "2%");
	}
	// Colonna che permette di entrare direttamente nella lista dei documenti di un istanza
	String visualizzaLinkDocistanza = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLINKDOCISTANZA, "0");
	if (visualizzaLinkDocistanza.equalsIgnoreCase("1")) {
	    columnJmesa.addLinkLabelBaseColumn(request, "codiceistanza", "label.D", "label.D", "../documentiistanza/list.htm?codiceIstanza=",
		    urlBack, false, false, "label.colonna_documenti_istanza_help", "2%");
	}
	columnJmesa.addDataBaseColumn("data", "label.data_presentazione", WebConstants.DATE_FORMAT_PATTERN, false, false, true, null, "6%");
	String visualizzaOperatoreIncarico = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISNUMOPERATOREINCARICO, "1");
	if (visualizzaOperatoreIncarico.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("operatoreincarico", "label.operatore_in_carico", false, false, true, null, "8%");
	}
	String visualizzaProtocollo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO, "1");
	if (visualizzaProtocollo.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("numeroprotocollo", "label.numero_protocollo", false, false, true, null, "8%");
	}
	String visualizzaDataProtocollo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO, "0");
	if (visualizzaDataProtocollo.equalsIgnoreCase("1")) {
	    columnJmesa
		    .addDataBaseColumn("dataprotocollo", "label.data_protocollo", WebConstants.DATE_FORMAT_PATTERN, false, false, true, null, "6%");
	}
	if (isTipologiaistanza) {
	    String visualizzaTipologiaIstanza = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA, "0");
	    if (visualizzaTipologiaIstanza.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("tipologiaistanza", "label.tipologia_istanza", false, false, true, null, "8%");
	    }
	}
	String visualizzaRichiedente = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE, "1");
	if (visualizzaRichiedente.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("transientDescrizioneRichiedenteQualitaAzienda", "label.richiedente", false, false, true, null, "");
	}
	String visualizzaRichiedenteStorico = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRICHIEDENTESTORICO, "1");
	if (visualizzaRichiedenteStorico.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("transientDescrizioneRichiedenteAziendaStorico", "label.richiedente_storico", false, false, true, null, "");
	}
	String visualizzaTecnico = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISTECNICO, "1");
	if (visualizzaTecnico.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("transientDescrizioneTecnico", "label.tecnico", false, false, true, null, "");
	}
	String visualizzaOperatore = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISOPERATORE, "0");
	if (visualizzaOperatore.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("operatorenome", "label.operatore", false, false, true, null, "");
	}
	String visualizzaResprocedimento = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRESPPROC, "0");
	if (visualizzaResprocedimento.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("responsabileprocnome", "label.responsabile_procedimento", false, false, true, null, "");
	}
	String visualizzaIstruttore = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTRUTTORE, "0");
	if (visualizzaIstruttore.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("istruttorenome", "label.responsabile_istruttoria", false, false, true, null, "");
	}
	String visualizzadenominazioneAttivita = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISDENOMINAZIONEATTIVITA,
		"1");
	if (visualizzadenominazioneAttivita.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("denominazioneAttivita", "label.denominazione_attivita", false, false, true, null, "");
	}
	String visualizzaLocalizzazione = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE, "1");
	if (visualizzaLocalizzazione.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("transientDescrizioneLocalizzazione", "label.localizzazione", false, false, true, null, "");
	}
	String visualizzaIndirizzi = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA, "0");
	if (visualizzaIndirizzi.equalsIgnoreCase("1")) {
	    // se countstradari > 1 allora si visualizza la I e mostra via ajax la lista di tutti gli stradari non primari
	    columnJmesa.addCellEditorCustomLabelColumn(request, "countstradari", "label.I", "label.I", new LinkAltriIndirizziHelperCellEditor(),
		    false, false, false, "label.colonna_altri_indirizzi_help", "2%");
	}
	String visualizzaEndo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI, "0");
	if (visualizzaEndo.equalsIgnoreCase("1")) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "countendoprocedimenti", "label.P", "label.P",
		    new LinkIstanzeprocedimentiHelperCellEditor(request, "codiceistanza", urlBack), false, false, false,
		    "label.colonna_endoprocedimenti_help", "2%");
	}
	String visualizzaIntervento = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISINTERVENTO, "1");
	if (visualizzaIntervento.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("interventoproc", "label.alberoproc", false, false, true, null, "");
	}
	String visualizzaProcedura = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPROCEDURA, "0");
	if (visualizzaProcedura.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("procedura", "label.procedura", false, false, true, null, "");
	}
	String visualizzaLavori = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLAVORI, "0");
	if (visualizzaLavori.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("oggettoistanza", "label.lavori", false, false, true, null, "");
	}
	//Note
	String visualizzaLavoriestesi = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLAVORIESTESI, "0");
	if (visualizzaLavoriestesi.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("note", "label.note", false, false, true, null, "20%");
	}
	if (isArchiviopratiche) {
	    String visualizzaArchivio = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISARCHIVIO, "0");
	    if (visualizzaArchivio.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("archivio", "label.archivio_pratiche", false, false, true, null, "");
	    }
	}
	String visualizzaPosizioneArchivio = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO, "1");
	if (visualizzaPosizioneArchivio.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("posizionearchivio", "label.posizione_in_archivio", false, false, true, null, "");
	}
	String visualizzaSorteggi = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE, "0");
	if (visualizzaSorteggi.equalsIgnoreCase("1")) {
	    //se countsorteggiata>0 allora fa vedere la S con chiamata ajax alla lista dei sorteggi
	    columnJmesa.addCellEditorCustomLabelColumn(request, "countsorteggiata", "label.S", "label.S", new LinkSorteggiCellEditorHelper(), false,
		    false, false, "label.colonna_sorteggi_help", "2%");
	}
	String visualizzaStato = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA, "1");
	if (visualizzaStato.equalsIgnoreCase("1")) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "statoistanza", "label.stato_istanza", "label.stato_istanza",
		    new LinkStatoIstanzaCellEditor(), false, false, false, "label.colonna_sorteggi_help", "2%");
	    //  columnJmesa.addBaseColumn("statoistanza", "label.stato_istanza", false, false, true, null, "");
	}
	String visualizzaColonnaComune = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISCOMUNE, "1");
	if (visualizzaColonnaComune.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("comune", "label.comune", false, false, true, null, "");
	}
    }

    @Override
    protected Collection<IstanzeListHelper> setItems() {

	int count = 10;
	IstanzeService istanzeService = (IstanzeService) ContextLoader.getCurrentWebApplicationContext().getBean("istanzeServiceImpl",
		IstanzeService.class);
	if (!filter.isRicercaVeloce()) {
	    count = istanzeService.countIstanzeListHelperByFilter(filter);
	} else {
	    String countPref = leggiParametroConfigurazioneUtente("NUMRECORDLISTE", "10");
	    if (StringUtils.isNotBlank(countPref)) {
		try {
		    count = Integer.parseInt(countPref);
		} catch (NumberFormatException e) {
		    // non deve far niente l'impostazione utente non è un numero e prende di default il valore iniziale di count
		}
	    }
	}
	getFacade().setTotalRows(count);
	List<IstanzeListHelper> listIstanze = new ArrayList<IstanzeListHelper>();
	if (count > 0) {
	    listIstanze = istanzeService.findIstanzeListHelperByFilter(filter, getStartRowPage(), getEndRowPage());
	}
	return listIstanze;
    }

    /**
     * Permette di leggere un parametro di configurazione (Impostando un valore di default se non trovato). Mette in
     * request con attributo = nomeParametro e valore quello preso da DB o valore di default specificato
     * 
     * @param nomeParametro
     * @param valorePredefinito
     */
    private String leggiParametroConfigurazioneUtente(String nomeParametro, String valorePredefinito) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), nomeParametro);
	Configurazioneutente configurazioneUtente = configurazioneutenteService.findById(confUteId);
	if (configurazioneUtente == null) {
	    return valorePredefinito;
	} else {
	    return configurazioneUtente.getValore();
	}
    }

    private UserDetails getCurrentlyAuthenticatedUser() {

	return userSecurityService.getCurrentlyAuthenticatedUser();
    }

    //    @Autowired
    //    public void setIstanzeService(IstanzeService istanzeService) {
    //
    //	this.istanzeService = istanzeService;
    //    }
    /**
     * <pre>
     * Lista delle colonne che verranno esportate. 
     * L'ordine con cui sono posizionate rappresenta l'ordine in cui verranno mostrate sulla lista. 
     * E' possibile spostare l'ordine senza alterare il funzionamento della funzionalità.
     * 
     * @return
     * </pre>
     */
    private List<String> colonne() {

	List<String> colonne = new ArrayList<String>();
	colonne.add("codiceistanza");
	colonne.add("numeroistanza");
	colonne.add("codicerichiedente");
	colonne.add("richiedente");
	colonne.add("richiedentecodicefiscale");
	colonne.add("richiedentepartitaiva");
	colonne.add("inqualitadi");
	colonne.add("codiceaziendarappresentata");
	colonne.add("aziendarappresentata");
	colonne.add("archiviopratiche");
	colonne.add("posizionearchivio");
	colonne.add("tipologiaistanza");
	colonne.add("operatore");
	colonne.add("datapresentazione");
	colonne.add("numeroprotocollo");
	colonne.add("dataprotocollo");
	colonne.add("procedimento");
	colonne.add("procedura");
	colonne.add("responsabileprocedimento");
	colonne.add("responsabileistruttoria");
	colonne.add("localizzazioneprimaria");
	colonne.add("descrizionelavori");
	colonne.add("comune");
	colonne.add("statoistanza");
	colonne.add("codicetecnico");
	colonne.add("intermediario");
	colonne.add("tecnicocf");
	colonne.add("tecnicopiva");
	///////////////////////////////////////
	colonne.add("codicestradarioprimario");
	colonne.add("codiceviarioprimario");
	colonne.add("stradarioprefisso");
	colonne.add("stradariodescrizione");
	colonne.add("stradariocivico");
	colonne.add("stradarioesponente");
	colonne.add("stradariocolore");
	colonne.add("stradarioscala");
	colonne.add("stradariopiano");
	colonne.add("stradariointerno");
	colonne.add("stradarioespinterno");
	colonne.add("stradariocap");
	colonne.add("stradariolocalitafrazione");
	colonne.add("stradarioquartiere");
	colonne.add("stradariokm");
	colonne.add("aziendacf");
	colonne.add("aziendapiva");
	colonne.add("note");
	colonne.add("incaricoa");
	// Codice anagrafiche
	return colonne;
    }

    /**
     * <pre>
     * Il metodo crea mappa delle colonne da esportare. La mappa sarà del tipo
     * <key, value> <"nomeColonna",pos>
     * Il nome colonna verrà preso dalla lista creata dal metodo "colonne()",
     * mentre pos sarà un integer che si increamente mentre si scorre la lista colonne
     * 
     * Es: <"nomecolonna0",0>,<"nomecolonna1",1>,..,<"nomecolonnaN",N>
     *   
     * @return
     * </pre>
     */
    private Map<String, Integer> creaMappaColonne() {

	List<String> colonne = this.colonne();
	Map<String, Integer> mapColonne = new HashMap<String, Integer>();
	Integer pos = 0;
	for (String colonna : colonne) {
	    mapColonne.put(colonna, pos);
	    pos++;
	}
	return mapColonne;
    }

    /**
     * <pre>
     * A partire dalla mappa crea una lista di stringhe che rappresenta le colonne,
     * le stringhe sarnno la chiave della mappa, la posizione nella lista sarà data 
     * dal value(int) associato alla chiave della mappa
     * 
     * Map:  <"nomecolonna0",0>,<"nomecolonna1",1>,..,<"nomecolonnaN",N>
     * List: "nomecolonna0,nomecolonna1,nomecolonnaN"
     * </pre>
     * 
     * @param mapColonne
     * @return
     */
    private List<String> creaColonne(Map<String, Integer> mapColonne) {

	List<String> colonne = new ArrayList<String>();
	Map<Integer, String> mapColonneOrderByValue = Utilities.sortByValues((HashMap) mapColonne);
	Set set2 = mapColonneOrderByValue.entrySet();
	Iterator iterator2 = set2.iterator();
	while (iterator2.hasNext()) {
	    Map.Entry _colonna = (Map.Entry) iterator2.next();
	    Integer value = (Integer) _colonna.getValue();
	    colonne.add(value.intValue(), (String) _colonna.getKey());
	}
	return colonne;
    }

    /**
     * Crea le singole righe a partire dalla mappa e dall'oggetto DTO istanze.
     * 
     * Con la key della mappa si estrare la posizione nell'array che rappresenta la posizione rispetto alle colonne, il
     * valore sarà associtao in base alle regole per ogni singolo campo
     * 
     * @param istanze
     * @param mapColonne
     * @return
     */
    private String[] creaRighe(IstanzeListHelper istanze, Map<String, Integer> mapColonne) {

	String[] riga = new String[mapColonne.size()];
	riga[(Integer) mapColonne.get("codiceistanza")] = String.valueOf(istanze.getCodiceistanza());
	riga[(Integer) mapColonne.get("numeroistanza")] = StringUtils.defaultIfEmpty(istanze.getNumeroistanza(), "");
	riga[(Integer) mapColonne.get("richiedente")] = StringUtils.defaultIfEmpty(istanze.getRichiedentenominativo(), "") + " "
		+ StringUtils.defaultIfEmpty(istanze.getRichiedentenome(), "");
	riga[(Integer) mapColonne.get("richiedentecodicefiscale")] = StringUtils.defaultIfEmpty(istanze.getRichiedentecodicefiscale(), "");
	riga[(Integer) mapColonne.get("richiedentepartitaiva")] = StringUtils.defaultIfEmpty(istanze.getRichiedentepartitaiva(), "");
	///////////////////////////////////////
	riga[(Integer) mapColonne.get("inqualitadi")] = StringUtils.defaultIfEmpty(istanze.getTiposoggetto(), "");
	riga[(Integer) mapColonne.get("aziendarappresentata")] = StringUtils.defaultIfEmpty(istanze.getAziendanominativo(), "");
	if (StringUtils.isNotBlank(istanze.getAziendanome())) {
	    riga[(Integer) mapColonne.get("aziendarappresentata")] += " " + StringUtils.defaultIfEmpty(istanze.getAziendanome(), "");
	}
	riga[(Integer) mapColonne.get("archiviopratiche")] = StringUtils.defaultIfEmpty(istanze.getArchivio(), "");
	riga[(Integer) mapColonne.get("posizionearchivio")] = StringUtils.defaultIfEmpty(istanze.getPosizionearchivio(), "");
	riga[(Integer) mapColonne.get("tipologiaistanza")] = StringUtils.defaultIfEmpty(istanze.getTipologiaistanza(), "");
	riga[(Integer) mapColonne.get("operatore")] = StringUtils.defaultIfEmpty(istanze.getOperatorenome(), "");
	if (istanze.getData() != null) {
	    riga[(Integer) mapColonne.get("datapresentazione")] = Utilities.formatDate(istanze.getData(), false);
	} else {
	    riga[(Integer) mapColonne.get("datapresentazione")] = "";
	}
	riga[(Integer) mapColonne.get("numeroprotocollo")] = StringUtils.defaultIfEmpty(istanze.getNumeroprotocollo(), "");
	if (istanze.getDataprotocollo() != null) {
	    riga[(Integer) mapColonne.get("dataprotocollo")] = Utilities.formatDate(istanze.getDataprotocollo(), false);
	} else {
	    riga[(Integer) mapColonne.get("dataprotocollo")] = "";
	}
	riga[(Integer) mapColonne.get("procedimento")] = StringUtils.defaultIfEmpty(istanze.getInterventoproc(), "");
	riga[(Integer) mapColonne.get("procedura")] = StringUtils.defaultIfEmpty(istanze.getProcedura(), "");
	riga[(Integer) mapColonne.get("responsabileprocedimento")] = StringUtils.defaultIfEmpty(istanze.getResponsabileprocnome(), "");
	riga[(Integer) mapColonne.get("responsabileistruttoria")] = StringUtils.defaultIfEmpty(istanze.getIstruttorenome(), "");
	riga[(Integer) mapColonne.get("localizzazioneprimaria")] = istanze.getTransientDescrizioneLocalizzazione();
	riga[(Integer) mapColonne.get("descrizionelavori")] = istanze.getOggettoistanza();
	riga[(Integer) mapColonne.get("comune")] = istanze.getComune();
	riga[(Integer) mapColonne.get("statoistanza")] = istanze.getStatoistanza();
	riga[(Integer) mapColonne.get("intermediario")] = istanze.getTransientDescrizioneTecnico();
	riga[(Integer) mapColonne.get("tecnicocf")] = StringUtils.defaultIfEmpty(istanze.getTecnicocf(), "");
	riga[(Integer) mapColonne.get("tecnicopiva")] = StringUtils.defaultIfEmpty(istanze.getTecnicopiva(), "");
	///////////////////////////////////////////////////////
	riga[(Integer) mapColonne.get("stradarioprefisso")] = istanze.getStradarioprefisso();
	riga[(Integer) mapColonne.get("stradariodescrizione")] = istanze.getStradariodescrizione();
	riga[(Integer) mapColonne.get("stradariocivico")] = istanze.getStradariocivico();
	riga[(Integer) mapColonne.get("stradarioesponente")] = istanze.getStradarioesponente();
	riga[(Integer) mapColonne.get("stradariocolore")] = istanze.getStradariocolore();
	riga[(Integer) mapColonne.get("stradarioscala")] = istanze.getStradarioscala();
	riga[(Integer) mapColonne.get("stradariopiano")] = istanze.getStradariopiano();
	riga[(Integer) mapColonne.get("stradariointerno")] = istanze.getStradariointerno();
	riga[(Integer) mapColonne.get("stradarioespinterno")] = istanze.getStradarioespinterno();
	riga[(Integer) mapColonne.get("stradariocap")] = istanze.getStradariocap();
	riga[(Integer) mapColonne.get("stradariolocalitafrazione")] = istanze.getStradariolocalitafrazione();
	riga[(Integer) mapColonne.get("stradarioquartiere")] = istanze.getStradarioquartiere();
	riga[(Integer) mapColonne.get("stradariokm")] = istanze.getStradariokm();
	riga[(Integer) mapColonne.get("aziendacf")] = StringUtils.defaultIfEmpty(istanze.getAziendacodicefiscale(), "");
	riga[(Integer) mapColonne.get("aziendapiva")] = StringUtils.defaultIfEmpty(istanze.getAziendapartitaiva(), "");
	riga[(Integer) mapColonne.get("note")] = StringUtils.defaultIfEmpty(istanze.getNote(), "");
	// Codice anagrafiche
	riga[(Integer) mapColonne.get("codicerichiedente")] = (String) istanze.getCodicerichiedente().toString();
	String codAzienda = (istanze.getCodiceazienda() != null ? (String) istanze.getCodiceazienda().toString() : "");
	riga[(Integer) mapColonne.get("codiceaziendarappresentata")] = codAzienda;
	String codiceTecnico = (istanze.getCodicetecnico() != null ? (String) istanze.getCodicetecnico().toString() : "");
	riga[(Integer) mapColonne.get("codicetecnico")] = codiceTecnico;
	String codiceStradarioPrimario = (istanze.getCodicestradarioprimario() != null ? (String) istanze.getCodicestradarioprimario().toString()
		: "");
	riga[(Integer) mapColonne.get("codicestradarioprimario")] = codiceStradarioPrimario;
	String codiceviarioprimario = (istanze.getCodiceviarioprimario() != null ? (String) istanze.getCodiceviarioprimario().toString() : "");
	riga[(Integer) mapColonne.get("codiceviarioprimario")] = codiceviarioprimario;
	String opincarico = (istanze.getOperatoreincarico() != null ? (String) istanze.getOperatoreincarico().toString() : "");
	riga[(Integer) mapColonne.get("incaricoa")] = opincarico;
	return riga;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle istanze");
	IstanzeService istanzeService = (IstanzeService) ContextLoader.getCurrentWebApplicationContext().getBean("istanzeServiceImpl",
		IstanzeService.class);
	// int count = istanzeService.countByFilter(filter);
	int count = istanzeService.countIstanzeListHelperByFilter(filter);
	List<IstanzeListHelper> listIstanze = new ArrayList<IstanzeListHelper>();
	List<String> colonne = new ArrayList<String>();
	Map<String, Integer> mappaColonne = creaMappaColonne();
	colonne = creaColonne(mappaColonne);
	result.setColonne(colonne);
	List<String[]> righe = new ArrayList<String[]>();
	double pageNumber = 0;
	double conta = 0;
	int startRow = 0;
	int rowEnd;
	if (count > 0) {
	    if (count < pageSize) {
		pageNumber = 1;
	    } else {
		conta = Double.valueOf(count) / Double.valueOf(pageSize);
		pageNumber = Math.ceil(conta);
	    }
	    int c = 0;
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * (Double.valueOf(pageSize).intValue());
		rowEnd = (Double.valueOf(pageSize).intValue());
		// listIstanze = istanzeService.findByFilter(filter, startRow, rowEnd);
		istanzeService.clear();
		listIstanze = istanzeService.findIstanzeListHelperByFilter(filter, startRow, rowEnd);
		for (IstanzeListHelper istanze : listIstanze) {
		    String[] riga = new String[colonne.size()];
		    riga = creaRighe(istanze, mappaColonne);
		    righe.add(c, riga);
		    c++;
		}
	    }
	}
	result.setRighe(righe);
	return result;
    }

    private double pageSize = 100;
}
