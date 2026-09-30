package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.custom.LinkAltriIndirizziCellEditor;
import org.jmesa.custom.LinkAutorizzazioniCellEditor;
import org.jmesa.custom.LinkSorteggiCellEditor;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkIstanzeprocedimentiCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.context.ContextLoader;

public class IstanzeTable extends GenerateTable<Istanze> {

    private IstanzeFilter filter;
    private boolean isArchiviopratiche;
    private boolean isTipologiaistanza;
    private ResponsabiliService responsabiliService;
    private ConfigurazioneutenteService configurazioneutenteService;
    private UserSecurityService userSecurityService;

    public IstanzeTable(IstanzeFilter filter, ResponsabiliService responsabiliService, ConfigurazioneutenteService configurazioneutenteService,
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
	columnJmesa.addLinkBaseColumn(request, "numeroistanza", "label.numeroistanza", "id.codice", "../istanze/view.htm?codice=", urlBack, false,
		false, true, null, "2%");
	columnJmesa.addLinkLabelBaseColumn(request, "id.codice", "label.M", "label.M", "../movimenti/list.htm?codiceIstanza=", urlBack, false, false,
		"label.colonna_movimenti_help", "2%");
	columnJmesa.addLinkLabelBaseColumn(request, "id.codice", "label.E", "label.E", "../movimenti/listElaborazione.htm?codiceIstanza=", urlBack,
		false, false, "label.colonna_elaborazione_help", "2%");
	String visualizzaAutorizzazioni = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISAUTORIZZAZIONI, "0", request);
	if (visualizzaAutorizzazioni.equalsIgnoreCase("1")) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "sofware.ordine", "label.A", "label.A", new LinkAutorizzazioniCellEditor(request,
		    urlBack), false, false, false, "label.colonna_autorizzazioni_help", "2%");
	}
	columnJmesa.addDataBaseColumn("data", "label.data_presentazione", WebConstants.DATE_FORMAT_PATTERN, false, false, true, null, "6%");
	String visualizzaProtocollo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISNUMPROTOCOLLO, "1", request);
	if (visualizzaProtocollo.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("numeroprotocollo", "label.numero_protocollo", false, false, true, null, "8%");
	}
	String visualizzaDataProtocollo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISDATAPROTOCOLLO, "0", request);
	if (visualizzaDataProtocollo.equalsIgnoreCase("1")) {
	    columnJmesa
		    .addDataBaseColumn("dataprotocollo", "label.data_protocollo", WebConstants.DATE_FORMAT_PATTERN, false, false, true, null, "6%");
	}
	if (isTipologiaistanza) {
	    String visualizzaTipologiaIstanza = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISTIPOLOGIAISTANZA, "0",
		    request);
	    if (visualizzaTipologiaIstanza.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("tipologiaistanza.tiDescrizione", "label.tipologia_istanza", false, false, true, null, "8%");
	    }
	}
	String visualizzaRichiedente = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRICHIEDENTE, "1", request);
	if (visualizzaRichiedente.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("transientRichiedenteQualitaAzienda", "label.richiedente", false, false, true, null, "");
	}
	String visualizzaOperatore = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISOPERATORE, "0", request);
	if (visualizzaOperatore.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("responsabile.responsabile", "label.operatore", false, false, true, null, "");
	}
	String visualizzaResprocedimento = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISRESPPROC, "0", request);
	if (visualizzaResprocedimento.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("responsabileProcedimento.responsabile", "label.responsabile_procedimento", false, false, true, null, "");
	}
	String visualizzaIstruttore = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTRUTTORE, "0", request);
	if (visualizzaIstruttore.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("istruttore.responsabile", "label.responsabile_istruttoria", false, false, true, null, "");
	}
	String visualizzaLocalizzazione = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLOCALIZZAZIONE, "1", request);
	if (visualizzaLocalizzazione.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("transientLocalizzazionePrimario", "label.localizzazione", false, false, true, null, "");
	}
	String visualizzaIndirizzi = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISINDIRIZZIISTANZA, "0", request);
	if (visualizzaIndirizzi.equalsIgnoreCase("1")) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "", "label.I", "label.I", new LinkAltriIndirizziCellEditor(), false, false, false,
		    "label.colonna_altri_indirizzi_help", "2%");
	}
	String visualizzaEndo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISENDOPROCEDIMENTI, "0", request);
	if (visualizzaEndo.equalsIgnoreCase("1")) {
	    //	    columnJmesa.addLinkLabelBaseColumn(request, "id.codice", "label.P", "label.P", "../istanzeprocedimenti/riepilogo.htm?codiceIstanza=",
	    //		    urlBack, false, false, "label.colonna_endoprocedimenti_help", "2%");
	    columnJmesa.addCellEditorCustomLabelColumn(request, "", "label.P", "label.P", new LinkIstanzeprocedimentiCellEditor(), false, false,
		    false, "label.colonna_endoprocedimenti_help", "2%");
	}
	String visualizzaIntervento = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISINTERVENTO, "1", request);
	if (visualizzaIntervento.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("alberoproc.vwAlberoproc.scDescrizione", "label.alberoproc", false, false, true, null, "");
	}
	String visualizzaProcedura = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPROCEDURA, "0", request);
	if (visualizzaProcedura.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("procedura.procedura", "label.procedura", false, false, true, null, "");
	}
	String visualizzaLavori = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISLAVORI, "0", request);
	if (visualizzaLavori.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("lavori", "label.lavori", false, false, true, null, "");
	}
	if (isArchiviopratiche) {
	    String visualizzaArchivio = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISARCHIVIO, "0", request);
	    if (visualizzaArchivio.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("tipiarchivioistanza.archivio", "label.archivio_pratiche", false, false, true, null, "");
	    }
	}
	String visualizzaPosizioneArchivio = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISPOSIZIONEARCHIVIO, "1",
		request);
	if (visualizzaPosizioneArchivio.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("posizionearchivio", "label.posizione_in_archivio", false, false, true, null, "");
	}
	String visualizzaSorteggi = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISSORTEGGIATE, "0", request);
	if (visualizzaSorteggi.equalsIgnoreCase("1")) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "", "label.S", "label.S", new LinkSorteggiCellEditor(), false, false, false,
		    "label.colonna_sorteggi_help", "2%");
	}
	String visualizzaStato = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISSTATOISTANZA, "1", request);
	if (visualizzaStato.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("chiusura.stato", "label.stato_istanza", false, false, true, null, "");
	}
	String visualizzaColonnaComune = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISCOMUNE, "1", request);
	if (visualizzaColonnaComune.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("comune.comune", "label.comune", false, false, true, null, "");
	}
    }

    @Override
    protected Collection<?> setItems() {

	IstanzeService istanzeService = (IstanzeService) ContextLoader.getCurrentWebApplicationContext().getBean("istanzeServiceImpl",
		IstanzeService.class);
	int count = istanzeService.countByFilter(filter);
	getFacade().setTotalRows(count);
	List<Istanze> listIstanze = new ArrayList<Istanze>();
	if (count > 0) {
	    listIstanze = istanzeService.findByFilter(filter, getStartRowPage(), getEndRowPage());
	}
	return listIstanze;
    }

    /**
     * Permette di leggere un parametro di configurazione (Impostando un valore di default se non trovato). Mette in
     * request con attributo = nomeParametro e valore quello preso da DB o valore di default specificato
     * 
     * @param nomeParametro
     * @param valorePredefinito
     * @param request
     */
    private String leggiParametroConfigurazioneUtente(String nomeParametro, String valorePredefinito, HttpServletRequest request) {

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
    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle istanze");
	IstanzeService istanzeService = (IstanzeService) ContextLoader.getCurrentWebApplicationContext().getBean("istanzeServiceImpl",
		IstanzeService.class);
	int count = istanzeService.countByFilter(filter);
	List<Istanze> listIstanze = new ArrayList<Istanze>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "codiceistanza");
	colonne.add(1, "numeroistanza");
	colonne.add(2, "richiedente");
	colonne.add(3, "inqualitadi");
	colonne.add(4, "aziendarappresentata");
	colonne.add(5, "tecnico");
	colonne.add(6, "archiviopratiche");
	colonne.add(7, "posizionearchivio");
	colonne.add(8, "tipologiaistanza");
	colonne.add(9, "operatore");
	colonne.add(10, "datapresentazione");
	colonne.add(11, "numeroprotocollo");
	colonne.add(12, "dataprotocollo");
	colonne.add(13, "procedimento");
	colonne.add(14, "procedura");
	colonne.add(15, "responsabileprocedimento");
	colonne.add(16, "responsabileistruttoria");
	colonne.add(17, "localizzazioneprimaria");
	colonne.add(18, "descrizionelavori");
	colonne.add(19, "denominazioneattivita");
	colonne.add(20, "note");
	// Codice anagrafiche
	colonne.add(21, "codicerichiedente");
	colonne.add(23, "codiceaziendarappresentata");
	colonne.add(22, "codicetecnico");
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
		listIstanze = istanzeService.findByFilter(filter, startRow, rowEnd);
		for (Istanze istanze : listIstanze) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(istanze.getId().getCodice());
		    riga[1] = istanze.getNumeroistanza();
		    riga[2] = (String) EntityUtils.getNestedProperty(istanze.getRichiedente(), "descrizioneRichiedente");
		    riga[3] = (String) EntityUtils.getNestedProperty(istanze.getTipisoggetto(), "tiposoggetto");
		    riga[4] = (String) EntityUtils.getNestedProperty(istanze.getTitolarelegale(), "descrizioneRichiedente");
		    riga[5] = (String) EntityUtils.getNestedProperty(istanze.getProfessionista(), "descrizioneRichiedente");
		    riga[6] = (String) EntityUtils.getNestedProperty(istanze.getTipiarchivioistanza(), "archivio");
		    riga[7] = istanze.getPosizionearchivio();
		    riga[8] = (String) EntityUtils.getNestedProperty(istanze.getTipologiaistanza(), "tiDescrizione");
		    riga[9] = (String) EntityUtils.getNestedProperty(istanze.getResponsabile(), "responsabile");
		    if (istanze.getData() != null) {
			riga[10] = Utilities.formatDate(istanze.getData(), false);
		    } else {
			riga[10] = "";
		    }
		    riga[11] = istanze.getNumeroprotocollo();
		    if (istanze.getDataprotocollo() != null) {
			riga[12] = Utilities.formatDate(istanze.getDataprotocollo(), false);
		    } else {
			riga[12] = "";
		    }
		    riga[13] = (String) EntityUtils.getNestedProperty(istanze.getAlberoproc(), "vwAlberoproc.scDescrizione");
		    riga[14] = (String) EntityUtils.getNestedProperty(istanze.getProcedura(), "procedura");
		    riga[15] = (String) EntityUtils.getNestedProperty(istanze.getResponsabileProcedimento(), "responsabile");
		    riga[16] = (String) EntityUtils.getNestedProperty(istanze.getIstruttore(), "responsabile");
		    riga[17] = istanze.getTransientLocalizzazionePrimario();
		    riga[18] = istanze.getLavori();
		    riga[19] = istanze.getNomeattivita();
		    riga[20] = istanze.getLavoriestesa();
		    riga[21] = (String) EntityUtils.getNestedProperty(istanze.getRichiedente(), "id.codice");
		    riga[22] = (String) EntityUtils.getNestedProperty(istanze.getTitolarelegale(), "id.codice");
		    riga[23] = (String) EntityUtils.getNestedProperty(istanze.getProfessionista(), "id.codice");
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
