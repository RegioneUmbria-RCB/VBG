package it.gruppoinit.pal.gp.core.jmesa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkTermineProcedimentoIstanzaCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.limit.FilterSet;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzarioListHelper;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.features.scadenzario.service.BatchScadenzarioService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.utils.TimeCalculator;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class BatchScadenzarioHelperTable extends GenerateTable<ScadenzarioListHelper> {

    private static final Logger log = LoggerFactory.getLogger(BatchScadenzarioHelperTable.class);
    private BatchScadenzarioFilter batchScadenzarioFilter;
    private BatchScadenzarioService batchScadenzarioService;
    private ConfigurazioneutenteService configurazioneutenteService;
    private int countedRecords = 0;

    public int getCountedRecords() {

	return countedRecords;
    }

    public BatchScadenzarioHelperTable(BatchScadenzarioFilter batchScadenzarioFilter, BatchScadenzarioService batchScadenzarioService,
	    ConfigurazioneutenteService configurazioneutenteService, boolean isPerOperatore) {

	this.batchScadenzarioFilter = batchScadenzarioFilter;
	this.batchScadenzarioService = batchScadenzarioService;
	this.configurazioneutenteService = configurazioneutenteService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "datascadenza"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	//String uriBack = isPerOperatore ? "/" : "../batchscadenzario/list.htm?tab=" + WebConstants.TAB_SCADENZARIO;
	String uriBack = "/batchscadenzario/listPerOperatore.htm?tab=" + WebConstants.TAB_SCADENZARIO + "%2526software=TT";
	JMesaTableLinkHelper dettaglioIstanzaLink = new JMesaTableLinkHelper("numeroistanza", uriBack, LinkTargetEnum.BATCH_SCADENZARIO_ISTANZE);
	JMesaTableLinkHelper dettaglioMovimentoDaFareLink = new JMesaTableLinkHelper("descrmovimentodafare", uriBack,
		LinkTargetEnum.BATCH_SCADENZARIO_MOVIMENTI);
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkColumn("numeroistanza", dettaglioIstanzaLink, "batchscadenzario.label.numero_istanza", "");
	columnJmesa.addLinkColumn("descrmovimentodafare", dettaglioMovimentoDaFareLink, "batchscadenzario.label.tipo_movimento_da_fare", "");
	/**
	 * <pre>
	 * 	 &#64;GIANPAOLOT
	 * 	NON HA SIGNIFICATO MOSTRARE MOVIMENTO FATTO, VERRà ELIMINATA LA POSSIBILITà DI VISUALIZZARLO TRA
	 * 	LE COLONNE DA ATTIVARE/DISATTIVARE NELLA CONFIGURAZIONE DELLO SCADENZARIO. NEI TAB MOVIMENTI DA VISIONARE
	 * 	MOVIMENTI NON NOTIFICATI APPARIRA' SEMPRE IN QUANTO è QUELLO CHE VOGLIAMO VEDERE DALLO SCADENZARIO
	 * 	 
	 * 	String visualizzaMovimentoFatto = configurazioneutenteService.leggiParametroConfigurazioneUtente(
	 * 		WebConstants.CONF_UTENTE_SCAD_COLONNA_MOVIMENTO_FATTO, "0");
	 * 	if (visualizzaMovimentoFatto.equalsIgnoreCase("1")) {
	 * 	    columnJmesa.addBaseColumn("descrmovimento", "batchscadenzario.label.movimento_fatto", false, false, true, "", "");
	 * 	}
	 * </pre>
	 * 
	 */
	String visualizzaRichiedente = configurazioneutenteService
		.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_RICHIEDENTE, "0");
	if (visualizzaRichiedente.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("transientDescrizioneRichiedenteQualitaAzienda", "batchscadenzario.label.richiedente", true, false, true, "",
		    "");
	}
	String visualizzaIntervento = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_INTERVENTO,
		"0");
	if (visualizzaIntervento.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("intervento", "batchscadenzario.label.tipologia_intervento", false, false, true, "", "");
	}
	//. proceura
	String visualizzaProcedura = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_PROCEDURA,
		"0");
	if (visualizzaProcedura.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("procedura", "label.procedura", false, false, true, "", "");
	}
	//. Posizione Archivio
	String visualizzaPosArch = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_POS_ARCHIVIO,
		"0");
	if (visualizzaPosArch.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("posizionearchivio", "label.posizione_archivio", false, false, true, "", "");
	}
	String visualizzaEndo = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_ENDOPROCEDIMENTO,
		"0");
	if (visualizzaEndo.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("endoprocedimento", "batchscadenzario.label.endoprocedimento", false, false, true, "", "");
	}
	String visualizzaAmministrazione = configurazioneutenteService
		.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_AMMINISTRAZIONE, "0");
	if (visualizzaAmministrazione.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("amministrazione", "label.amministrazione", false, false, true, "", "");
	}
	columnJmesa.addDataBaseColumn("datascadenza", "batchscadenzario.label.data_scadenza", WebConstants.DATE_FORMAT_PATTERN, false, false, true,
		"", "");
	String visualizzaDataIstanza = configurazioneutenteService
		.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_DATA_ISTANZA, "0");
	if (visualizzaDataIstanza.equalsIgnoreCase("1")) {
	    columnJmesa.addDataBaseColumn("dataistanza", "label.data_presentazione", WebConstants.DATE_FORMAT_PATTERN, false, true, true, null,
		    "10%");
	}
	String visualizzaResponsabile = configurazioneutenteService
		.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_OPERATORE, "0");
	if (visualizzaResponsabile.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("responsabile", "label.operatore", false, false, true, "", "");
	}
	String visualizzaIstruttore = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_ISTRUTTORE,
		"0");
	if (visualizzaIstruttore.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("istruttore", "label.responsabile_istruttoria", false, false, true, "", "");
	}
	columnJmesa.addBaseColumn("comune", "label.comune", false, false, true, null, "");
	String visualizzaModulo = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_SOFTWARE, "0");
	if (visualizzaModulo.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("softwaredescrizione", "batchscadenzario.label.modulo_software", false, false, true, "", "");
	}
	String visualizzaStato = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_STATO, "0");
	if (visualizzaStato.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("stato", "batchscadenzario.label.stato_istanza", false, false, true, "", "");
	}
	String visualizzaTermineProcedimento = configurazioneutenteService
		.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_TERMINE_PROCEDIMENTO, "0");
	if (visualizzaTermineProcedimento.equalsIgnoreCase("1")) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "termineprocedimento", "label.termine_del_procedimento",
		    "label.termine_del_procedimento", new LinkTermineProcedimentoIstanzaCellEditor(Calendar.getInstance()), false, false, true,
		    "label.termine_del_procedimento", "10%");
	    //	    columnJmesa.addDataBaseColumn("termineprocedimento", "label.termine_del_procedimento", WebConstants.DATE_FORMAT_PATTERN, false, true, true, null, "10%");
	}
    }

    @Override
    protected Collection<?> setItems() {

	// 1) recupero i filtri
	FilterSet filterSet = getFacade().getLimit().getFilterSet();
	// 2) popolo la property movimentiFilter di batchScadenzario scadenzariofilter.nuovapporotprietafilter
	if (filterSet.getFilter("numeroistanza") != null) {
	    batchScadenzarioFilter.getMovimentiFilter().setNumeroistanza(filterSet.getFilter("numeroistanza").getValue());
	}
	if (filterSet.getFilter("descrmovimentodafare") != null) {
	    batchScadenzarioFilter.getMovimentiFilter().setTipomovimento(filterSet.getFilter("descrmovimentodafare").getValue());
	}
	if (filterSet.getFilter("transientDescrizioneRichiedenteQualitaAzienda") != null) {
	    batchScadenzarioFilter.getMovimentiFilter()
		    .setRichiedentenominativo(filterSet.getFilter("transientDescrizioneRichiedenteQualitaAzienda").getValue());
	}
	if (filterSet.getFilter("responsabile") != null) {
	    batchScadenzarioFilter.getMovimentiFilter().setResponsabiledescrizione(filterSet.getFilter("responsabile").getValue());
	}
	List<ScadenzarioListHelper> list = new ArrayList<ScadenzarioListHelper>();
	TimeCalculator t = new TimeCalculator("scadenzario");
	if (log.isDebugEnabled()) {
	    log.debug("scadenzario#countByHelperFilter==>START: {}", t.getTimeElapsed());
	}
	int count = batchScadenzarioService.countByHelperFilter(batchScadenzarioFilter);
	if (log.isDebugEnabled()) {
	    log.debug("scadenzario#countByHelperFilter==>END: {}", t.getTimeElapsed());
	}
	getFacade().setTotalRows(count);
	if (log.isDebugEnabled()) {
	    log.debug("scadenzario#findByHelperFilter==>START: {}", t.getTimeElapsed());
	}
	list = batchScadenzarioService.findByHelperFilter(batchScadenzarioFilter, getStartRowPage(), getEndRowPage());
	if (log.isDebugEnabled()) {
	    log.debug("scadenzario#findByHelperFilter==>END: {}", t.getTimeElapsed());
	    log.debug("scadenzario#tempistiche: {}", t.tempistiche());
	}
	this.countedRecords = count;
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle istanze");
	int count = batchScadenzarioService.countByHelperFilter(batchScadenzarioFilter);
	List<ScadenzarioListHelper> listScadenze = new ArrayList<ScadenzarioListHelper>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "codiceistanza");
	colonne.add(1, "numeroistanza");
	colonne.add(2, "modulo");
	colonne.add(3, "stato");
	colonne.add(4, "richiedente");
	colonne.add(5, "intervento");
	colonne.add(6, "movimentofatto");
	colonne.add(7, "movimentodaeffettuare");
	colonne.add(8, "endoprocedimento");
	colonne.add(9, "amministrazione");
	colonne.add(10, "datascadenza");
	colonne.add(11, "dataistanza");
	colonne.add(12, "termineprocedimento");
	colonne.add(13, "responsabile");
	colonne.add(14, "istruttore");
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
		batchScadenzarioService.clear();
		listScadenze = batchScadenzarioService.findByHelperFilter(batchScadenzarioFilter, startRow, rowEnd);
		for (ScadenzarioListHelper scadenza : listScadenze) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(scadenza.getCodiceistanza());
		    riga[1] = StringUtils.defaultIfEmpty(scadenza.getNumeroistanza(), "");
		    riga[2] = StringUtils.defaultIfEmpty(scadenza.getSoftwaredescrizione(), "");
		    riga[3] = StringUtils.defaultIfEmpty(scadenza.getStato(), "");
		    riga[4] = scadenza.getTransientDescrizioneRichiedenteQualitaAzienda();
		    riga[5] = scadenza.getIntervento();
		    riga[6] = StringUtils.defaultIfEmpty(scadenza.getDescrmovimento(), "");
		    riga[7] = StringUtils.defaultIfEmpty(scadenza.getDescrmovimentodafare(), "");
		    riga[8] = StringUtils.defaultIfEmpty(scadenza.getEndoprocedimento(), "");
		    riga[9] = StringUtils.defaultIfEmpty(scadenza.getAmministrazione(), "");
		    if (scadenza.getDatascadenza() != null) {
			riga[10] = Utilities.formatDate(scadenza.getDatascadenza(), false);
		    } else {
			riga[10] = "";
		    }
		    if (scadenza.getDataistanza() != null) {
			riga[11] = Utilities.formatDate(scadenza.getDataistanza(), false);
		    } else {
			riga[11] = "";
		    }
		    if (scadenza.getTermineprocedimento() != null) {
			riga[12] = Utilities.formatDate(scadenza.getTermineprocedimento(), false);
		    } else {
			riga[12] = "";
		    }
		    riga[13] = StringUtils.defaultIfEmpty(scadenza.getResponsabile(), "");
		    riga[14] = StringUtils.defaultIfEmpty(scadenza.getIstruttore(), "");
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
