package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDTO;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.CheckBoxAjaxUpdateMovimenti;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.limit.FilterSet;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;

public class MovimentiSTCNonNotificatiTable extends GenerateTable<MovimentiDTO> {

    private static final int MAX_RESULT = 800;
    private MovimentiService movimentiService;
    private BatchScadenzarioFilter batchScadenzarioFilter;
    private ConfigurazioneutenteService configurazioneutenteService;
    private int countedRecords = 0;

    public int getCountedRecords() {

	return countedRecords;
    }

    public MovimentiSTCNonNotificatiTable(BatchScadenzarioFilter batchScadenzarioFilter, MovimentiService movimentiService,
	    ConfigurazioneutenteService configurazioneutenteService) {

	this.batchScadenzarioFilter = batchScadenzarioFilter;
	this.movimentiService = movimentiService;
	this.configurazioneutenteService = configurazioneutenteService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	//String uriBack = "/batchscadenzario/list.htm?tab=" + WebConstants.TAB_MOV_STC_NON_NOTIFICATI;
	String uriBack = "/batchscadenzario/listPerOperatore.htm?tab=" + WebConstants.TAB_MOV_STC_NON_NOTIFICATI + "%2526software=TT";
	String customIstanzeLink = "../istanze/view.htm%253fcodice%3d<codiceistanza>%2526software%3d<softwarecodice>";
	String customMovimentiLink = "../movimenti/view.htm%253fcodice%3d<codicemovimento>%2526software%3d<softwarecodice>";
	IJMesaLinkHelper dettaglioIstanzaLink = new JMesaCustomLinkHelper(customIstanzeLink, uriBack);
	IJMesaLinkHelper dettaglioMovimentoDaVisionareLink = new JMesaCustomLinkHelper(customMovimentiLink, uriBack);
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkColumn("numeroistanza", dettaglioIstanzaLink, "batchscadenzario.label.numero_istanza", true, false, true, "");
	/**
	 * <pre>
	 *  NON HA SENSO NASCONDERLO IN QUANTO è QUELLO CHE SI VOLE VEDERE PER QUESTO TAB DELLO SCANDENZARIO
	 * 	String visualizzaMovimentoFatto = configurazioneutenteService.leggiParametroConfigurazioneUtente(
	 * 		WebConstants.CONF_UTENTE_SCAD_COLONNA_MOVIMENTO_FATTO, "0");
	 * 	if (visualizzaMovimentoFatto.equalsIgnoreCase("1")) {
	 * 	    columnJmesa.addLinkColumn("movimentodescrizione", dettaglioMovimentoDaVisionareLink, "batchscadenzario.label.movimento_fatto", false,
	 * 		    false, true, "");
	 * 	}
	 * </pre>
	 */
	columnJmesa.addLinkColumn("movimentodescrizione", dettaglioMovimentoDaVisionareLink, "batchscadenzario.label.movimento_fatto", true, false,
		true, "");
	String visualizzaRichiedente = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_RICHIEDENTE, "0");
	if (visualizzaRichiedente.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("transientDescrizioneRichiedenteQualitaAzienda", "batchscadenzario.label.richiedente", true, false, true, "",
		    "");
	}
	String visualizzaIntervento = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_INTERVENTO, "0");
	if (visualizzaIntervento.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("intervento", "batchscadenzario.label.tipologia_intervento", false, false, true, "", "");
	}
	//. procedura
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
	String visualizzaEndo = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_ENDOPROCEDIMENTO, "0");
	if (visualizzaEndo.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("endoprocedimentodescrizione", "batchscadenzario.label.endoprocedimento", false, false, true, "", "");
	}
	String visualizzaAmministrazione = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_AMMINISTRAZIONE, "0");
	if (visualizzaAmministrazione.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("amministrazionidescrizione", "label.amministrazione", false, false, true, "", "");
	}
	columnJmesa.addDataBaseColumn("datamovimento", "batchscadenzario.label.data_registrazione", WebConstants.DATE_FORMAT_PATTERN, false, false,
		true, "", "");
	String visualizzaDataIstanza = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_DATA_ISTANZA, "0");
	if (visualizzaDataIstanza.equalsIgnoreCase("1")) {
	    columnJmesa
		    .addDataBaseColumn("dataistanza", "label.data_presentazione", WebConstants.DATE_FORMAT_PATTERN, false, true, true, null, "10%");
	}
	String visualizzaResponsabile = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_OPERATORE, "0");
	if (visualizzaResponsabile.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("responsabiledescrizione", "label.operatore", false, false, true, "", "");
	}
	String visualizzaIstruttore = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_ISTRUTTORE, "0");
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
	String visualizzaTermineProcedimento = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_TERMINE_PROCEDIMENTO, "0");
	if (visualizzaTermineProcedimento.equalsIgnoreCase("1")) {
	    columnJmesa.addDataBaseColumn("datafine", "label.termine_del_procedimento", WebConstants.DATE_FORMAT_PATTERN, false, true, true, null,
		    "10%");
	}
	columnJmesa.addCellEditorCustomLabelColumn(request, "codicemovimento", "label.disabilita", "label.disabilita",
		new CheckBoxAjaxUpdateMovimenti("inviatoConStc", "codicemovimento"), false, false, false, "label.disabilita", "2%");
    }

    @Override
    protected Collection<?> setItems() {

	FilterSet filterSet = getFacade().getLimit().getFilterSet();
	if (filterSet.getFilter("numeroistanza") != null) {
	    batchScadenzarioFilter.getMovimentiFilter().setNumeroistanza(filterSet.getFilter("numeroistanza").getValue());
	}
	if (filterSet.getFilter("movimentodescrizione") != null) {
	    batchScadenzarioFilter.getMovimentiFilter().setTipomovimento(filterSet.getFilter("movimentodescrizione").getValue());
	}
	if (filterSet.getFilter("transientDescrizioneRichiedenteQualitaAzienda") != null) {
	    batchScadenzarioFilter.getMovimentiFilter().setRichiedentenominativo(
		    filterSet.getFilter("transientDescrizioneRichiedenteQualitaAzienda").getValue());
	}
	if (filterSet.getFilter("responsabiledescrizione") != null) {
	    batchScadenzarioFilter.getMovimentiFilter().setResponsabiledescrizione(filterSet.getFilter("responsabiledescrizione").getValue());
	}
	List<MovimentiDTO> list = movimentiService.findMovimentiDTOSTCNonNotificati(batchScadenzarioFilter, 0, MAX_RESULT);
	getFacade().setTotalRows(list.size());
	this.countedRecords = list.size();
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista movimenti non notificati");
	List<MovimentiDTO> list = movimentiService.findMovimentiDTOSTCNonNotificati(batchScadenzarioFilter, 0, MAX_RESULT);
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "numeroistanza");
	colonne.add(1, "modulo");
	colonne.add(2, "stato");
	colonne.add(3, "richiedente");
	colonne.add(4, "intervento");
	colonne.add(5, "movimentofatto");
	colonne.add(6, "endoprocedimento");
	colonne.add(7, "dataregistrazione");
	colonne.add(8, "amministrazionidescrizione");
	colonne.add(9, "dataistanza");
	colonne.add(10, "datafine");
	colonne.add(11, "responsabiledescrizione");
	result.setColonne(colonne);
	List<String[]> righe = new ArrayList<String[]>();
	int c = 0;
	for (MovimentiDTO movimentiDTO : list) {
	    String[] riga = new String[colonne.size()];
	    riga[0] = movimentiDTO.getNumeroistanza();
	    riga[1] = movimentiDTO.getSoftwaredescrizione();
	    riga[2] = movimentiDTO.getStato();
	    riga[3] = movimentiDTO.getTransientDescrizioneRichiedenteQualitaAzienda();
	    riga[4] = movimentiDTO.getIntervento();
	    riga[5] = movimentiDTO.getMovimentodescrizione();
	    riga[6] = movimentiDTO.getEndoprocedimentodescrizione();
	    riga[7] = Utilities.formatDate(movimentiDTO.getDatamovimento(), false);
	    riga[8] = StringUtils.defaultIfEmpty(movimentiDTO.getAmministrazionidescrizione(), "");
	    if (movimentiDTO.getDataistanza() != null) {
		riga[9] = Utilities.formatDate(movimentiDTO.getDataistanza(), false);
	    } else {
		riga[9] = "";
	    }
	    if (movimentiDTO.getDatafine() != null) {
		riga[10] = Utilities.formatDate(movimentiDTO.getDatafine(), false);
	    } else {
		riga[10] = "";
	    }
	    riga[11] = StringUtils.defaultIfEmpty(movimentiDTO.getResponsabiledescrizione(), "");
	    righe.add(c, riga);
	    c++;
	}
	result.setRighe(righe);
	return result;
    }
}
