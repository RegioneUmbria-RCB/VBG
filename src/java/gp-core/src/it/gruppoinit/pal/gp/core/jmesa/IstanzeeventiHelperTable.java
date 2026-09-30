package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeeventiListHelper;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.CheckBoxAjaxUpdateIstanzeeventi;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;

public class IstanzeeventiHelperTable extends GenerateTable<IstanzeeventiListHelper> {

    private BatchScadenzarioFilter batchScadenzarioFilter;
    private IstanzeeventiService istanzeeventiService;

    public IstanzeeventiHelperTable(BatchScadenzarioFilter batchScadenzarioFilter, IstanzeeventiService istanzeeventiService) {

	super();
	this.batchScadenzarioFilter = batchScadenzarioFilter;
	this.istanzeeventiService = istanzeeventiService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "dataevento"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    private int countedRecords = 0;

    public int getCountedRecords() {

	return countedRecords;
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	String requestUri = ".." + request.getRequestURI().replaceFirst(request.getSession().getServletContext().getContextPath(), "");
	String queryString = request.getQueryString();
	String uriBack = "";
	if (StringUtils.isNotBlank(queryString)) {
	    uriBack = requestUri + "?" + queryString;
	}
	try {
	    uriBack = URLEncoder.encode(uriBack, "UTF-8");
	    uriBack = URLEncoder.encode(uriBack, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	// "../batchscadenzario/list.htm?tab=" + WebConstants.TAB_SCADENZARIO;
	JMesaTableLinkHelper dettaglioIstanzaLink = new JMesaTableLinkHelper("numeroistanza", uriBack, LinkTargetEnum.ISTANZE_EVENTI_HELPER_ISTANZE);
	JMesaTableLinkHelper dettaglioMovimentoDaFareLink = new JMesaTableLinkHelper("descrmovimentodafare", uriBack,
		LinkTargetEnum.ISTANZE_EVENTI_HELPER_MOVIMENTI);
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("evento", "label.descrizione", false, false, true, "", "30%");
	columnJmesa.addBaseColumn("richiedente", "label.descrizione", false, false, true, "", "");
	columnJmesa.addDataBaseColumn("dataevento", "label.data", WebConstants.DATE_FORMAT_PATTERN, false, false, true, "", "");
	columnJmesa.addBaseColumn("categoria", "label.categoria", true, true, true, "", "");
	columnJmesa.addLinkColumn("numeroistanza", dettaglioIstanzaLink, "label.numero_istanza", "");
	columnJmesa.addLinkColumn("movimento", dettaglioMovimentoDaFareLink, "label.movimento", "");
	final String label = getMessageFromBundle(getContext(), "label.seleziona_deseleziona_tutti", null);
	final String header = getMessageFromBundle(getContext(), "label.segna_come_letti", null);
	HeaderEditor customHeaderEditor = new HeaderEditor() {

	    @Override
	    public Object getValue() {

		return "<span title=\"" + header + "\">" + header
			+ "</span><br /><input type=\"checkbox\" id=\"enl_seleziona_tutti\" onclick=\"selezionaTuttiENL()\" title=\"" + label
			+ "\"/>";
	    }
	};
	columnJmesa.addCellEditorCustomLabelColumn(request, "id", new CheckBoxAjaxUpdateIstanzeeventi("flagLetto", false), customHeaderEditor, false,
		false, false, "8%");
    }

    @Override
    protected Collection<?> setItems() {

	IstanzeeventiListHelper hlp = getFilterQuery(new IstanzeeventiListHelper());
	if (StringUtils.isNotBlank(hlp.getNumeroistanza()) && StringUtils.isBlank(batchScadenzarioFilter.getNumeroIstanza())) {
	    batchScadenzarioFilter.setNumeroIstanza(hlp.getNumeroistanza());
	}
	int count = istanzeeventiService.countByScadenzarioFilterHelper(batchScadenzarioFilter);
	this.countedRecords = count;
	getFacade().setTotalRows(count);
	List<IstanzeeventiListHelper> list = new ArrayList<IstanzeeventiListHelper>();
	if (count > 0) {
	    list = istanzeeventiService.findByScadenzarioFilterHelper(batchScadenzarioFilter, getStartRowPage(), getEndRowPage());
	}
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle istanze");
	int count = istanzeeventiService.countByScadenzarioFilterHelper(batchScadenzarioFilter);
	List<IstanzeeventiListHelper> listScadenze = new ArrayList<IstanzeeventiListHelper>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "codiceistanza");
	colonne.add(1, "numeroistanza");
	colonne.add(2, "software");
	colonne.add(3, "categoria");
	colonne.add(4, "evento");
	colonne.add(5, "dataevento");
	colonne.add(6, "movimento");
	colonne.add(7, "tipomovimento");
	colonne.add(8, "codicemovimento");
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
		istanzeeventiService.clear();
		listScadenze = istanzeeventiService.findByScadenzarioFilterHelper(batchScadenzarioFilter, startRow, rowEnd);
		for (IstanzeeventiListHelper scadenza : listScadenze) {
		    String[] riga = new String[colonne.size()];
		    if (scadenza.getCodiceistanza() != null) {
			riga[0] = String.valueOf(scadenza.getCodiceistanza());
		    } else {
			riga[0] = "";
		    }
		    riga[1] = StringUtils.defaultIfEmpty(scadenza.getNumeroistanza(), "");
		    riga[2] = StringUtils.defaultIfEmpty(scadenza.getDescrizionesoftware(), "");
		    riga[3] = StringUtils.defaultIfEmpty(scadenza.getCategoria(), "");
		    riga[4] = StringUtils.defaultIfEmpty(scadenza.getEvento(), "");
		    if (scadenza.getDataevento() != null) {
			riga[5] = Utilities.formatDate(scadenza.getDataevento(), false);
		    } else {
			riga[5] = "";
		    }
		    riga[6] = StringUtils.defaultIfEmpty(scadenza.getMovimento(), "");
		    riga[7] = StringUtils.defaultIfEmpty(scadenza.getTipomovimento(), "");
		    if (scadenza.getCodicemovimento() != null) {
			riga[8] = String.valueOf(scadenza.getCodicemovimento());
		    } else {
			riga[8] = "";
		    }
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
