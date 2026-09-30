package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkDettaglioIstanzaInAutorizzazioni;
import org.jmesa.customColumn.LinkStampaAutorizzazioni;
import org.jmesa.customColumn.LinkTrasformaInSpuntistaPerMercati;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

public class AutorizzazioniTable extends GenerateTable<Autorizzazioni> {

    private AutorizzazioniFilter filter;
    private Integer codiceIstanza;
    private Integer funzionalitaRicercaAutorizzazioni;

    public AutorizzazioniTable(AutorizzazioniFilter filter, Integer codiceIstanza, Integer funzionalitaRicercaAutorizzazioni) {

	super();
	this.filter = filter;
	this.codiceIstanza = codiceIstanza;
	this.funzionalitaRicercaAutorizzazioni = funzionalitaRicercaAutorizzazioni;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "autorizdata"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	StringBuffer parametriHistoryBack = new StringBuffer("%3FcodiceIstanza%3D");
	if (codiceIstanza != null) {
	    parametriHistoryBack.append(codiceIstanza);
	}
	parametriHistoryBack.append("%26").append("modalita_ricerca%3D")
		.append(funzionalitaRicercaAutorizzazioni != null ? funzionalitaRicercaAutorizzazioni : WebConstants.SEARCH_AUT_DEFAULT);
	String uriBack = "/autorizzazioni/list.htm".concat(parametriHistoryBack.toString());
	//JMesaTableLinkHelper dettaglioIstanzaLink = new JMesaTableLinkHelper("istanza", uriBack, LinkTargetEnum.DETTAGLIO_ISTANZA);
	JMesaTableLinkHelper dettaglioAutorizzazioneLink = new JMesaTableLinkHelper("", uriBack, LinkTargetEnum.DETTAGLIO_AUTORIZZAZIONE);
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("autoriznumero", "label.numero", false, false, false, null, "");
	columnJmesa.addDataBaseColumn("dataRilascio", "label.autorizzazione_data_rilascio_autorizzazione", WebConstants.DATE_FORMAT_PATTERN, false,
		false, false, null, "8%");
	columnJmesa.addDataBaseColumn("autorizdata", "label.autorizzazione_data_validita_autorizzazione", WebConstants.DATE_FORMAT_PATTERN, false,
		false, false, null, "8%");
	columnJmesa.addBaseColumn("autorizcomune.comune", "label.comune", false, false, false, null, "");
	columnJmesa.addBaseColumn("tipologiaregistro.trDescrizione", "label.registro", false, false, false, null, "");
	columnJmesa.addBaseColumn("anagrafe.descrizioneRichiedente", "label.concessione_titolare", false, false, false, null, "");
	columnJmesa.addBaseColumn("occupante.descrizioneRichiedente", "mercatid.label.occupante", false, false, false, null, "");
	columnJmesa.addDataBaseColumn("datascadenza", "label.data_scadenza", WebConstants.DATE_FORMAT_PATTERN, false, false, false, null, "8%");
	columnJmesa.addBaseColumn("autorizresponsabile", "label.concessione_autorizzata_da", false, false, true, null, "");
	Map<String, Boolean> mappaLabelValoreStato = new HashMap<String, Boolean>();
	mappaLabelValoreStato.put("label.cessata", false);
	mappaLabelValoreStato.put("label.attiva", true);
	columnJmesa.addBooleanCustomColumn("flagAttiva", "label.stato", mappaLabelValoreStato, false, false, false, null, "");
	//columnJmesa.addLinkColumn("istanza.numeroistanza", dettaglioIstanzaLink, "label.numero_istanza", false, false, false, "");
	if (WebConstants.SEARCH_AUT_PER_GESTIONE_SPUNTA.equals(funzionalitaRicercaAutorizzazioni)) {
	    columnJmesa.addBaseColumn("istanza.numeroistanza", "label.numero_istanza", false, false, false, null, "");
	} else {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", "label.numero_istanza", "", new LinkDettaglioIstanzaInAutorizzazioni(
		    request), false, false, false, "", "5%");
	}
	columnJmesa.addLinkWithIconColumn("dettaglioColumn", "label.edit.record", dettaglioAutorizzazioneLink, "label.edit.record", false, false,
		false, "5%");
	if (WebConstants.SEARCH_AUT_PER_GESTIONE_SPUNTA.equals(funzionalitaRicercaAutorizzazioni)) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", "label.azione", "", new LinkTrasformaInSpuntistaPerMercati(
		    codiceIstanza, "id.codice", request), false, false, false, "", "5%");
	} else {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", "label.azione", "", new LinkStampaAutorizzazioni(request), false, false,
		    false, "", "5%");
	}
    }

    @Override
    protected Collection<?> setItems() {

	AutorizzazioniService autorizzazioniService = (AutorizzazioniService) ContextLoader.getCurrentWebApplicationContext().getBean(
		"autorizzazioniServiceImpl", AutorizzazioniService.class);
	//	int count = autorizzazioniService.countByFilter(filter);
	int count = autorizzazioniService.countByAutorizzazioniFilter(filter);
	getFacade().setTotalRows(count);
	//List<Autorizzazioni> list = autorizzazioniService.findByFilter(filter, getStartRowPage(), getEndRowPage());
	List<Autorizzazioni> list = autorizzazioniService.findByAutorizzazioniFilter(filter, getStartRowPage(), getEndRowPage());
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle autorizzazioni");
	AutorizzazioniService autorizzazioniService = (AutorizzazioniService) ContextLoader.getCurrentWebApplicationContext().getBean(
		"autorizzazioniServiceImpl", AutorizzazioniService.class);
	int count = autorizzazioniService.countByAutorizzazioniFilter(filter);
	List<Autorizzazioni> listAutorizzazioni = new ArrayList<Autorizzazioni>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "Numero");
	colonne.add(1, "DataRilascio");
	colonne.add(2, "Comune");
	colonne.add(3, "Registro");
	colonne.add(4, "Titolare");
	colonne.add(5, "Occupante");
	colonne.add(6, "Data Scadenza");
	colonne.add(7, "Stato");
	colonne.add(8, "Cod istanza");
	colonne.add(9, "Autorizzata da");
	colonne.add(10, "Autorizzata il");
	colonne.add(11, "ValidaDA");
	//	label.concessione_autorizzata_da
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
		autorizzazioniService.clear();
		listAutorizzazioni = autorizzazioniService.findByAutorizzazioniFilter(filter, startRow, rowEnd);
		for (Autorizzazioni autorizzazione : listAutorizzazioni) {
		    String[] riga = new String[colonne.size()];
		    // Dato obbl.
		    riga[0] = StringUtils.defaultIfEmpty(autorizzazione.getAutoriznumero(), "");
		    // Dato obbl.
		    riga[1] = Utilities.formatDate(autorizzazione.getAutorizdata(), false);
		    // Dato obbl.
		    riga[2] = StringUtils.defaultIfEmpty(autorizzazione.getAutorizcomune().getComune(), "");
		    // Dato obbl.
		    riga[3] = StringUtils.defaultIfEmpty(autorizzazione.getTipologiaregistro().getTrDescrizione(), "");
		    if (EntityUtils.getNestedProperty(autorizzazione.getAnagrafe(), "id.codice") != null) {
			riga[4] = StringUtils.defaultIfEmpty(autorizzazione.getAnagrafe().getDescrizioneRichiedente(), "");
		    } else {
			riga[4] = "";
		    }
		    if (EntityUtils.getNestedProperty(autorizzazione.getOccupante(), "id.codice") != null) {
			riga[5] = StringUtils.defaultIfEmpty(autorizzazione.getOccupante().getDescrizioneRichiedente(), "");
		    } else {
			riga[5] = "";
		    }
		    riga[6] = Utilities.formatDate(autorizzazione.getDatascadenza(), false);
		    if (autorizzazione.getFlagAttiva() != null && autorizzazione.getFlagAttiva().equals(Boolean.TRUE)) {
			riga[7] = "Si";
		    } else {
			riga[7] = "No";
		    }
		    if (EntityUtils.getNestedProperty(autorizzazione.getIstanza(), "id.codice") != null) {
			riga[8] = StringUtils.defaultIfEmpty(autorizzazione.getIstanza().getNumeroistanza(), "");
		    } else {
			riga[8] = "";
		    }
		    riga[9] = StringUtils.defaultIfEmpty(autorizzazione.getAutorizresponsabile(), "");
		    riga[10] = Utilities.formatDate(autorizzazione.getAutorizdataregistr(), false);
		    // Dato obbl.
		    riga[11] = Utilities.formatDate(autorizzazione.getDataRilascio(), false);
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
