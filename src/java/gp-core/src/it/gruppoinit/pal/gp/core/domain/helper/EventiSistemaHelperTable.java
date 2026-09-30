package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.customColumn.CheckBoxAjaxUpdateIstanzeeventi;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;

public class EventiSistemaHelperTable extends GenerateTable<Istanzeeventi> {

    private BatchScadenzarioFilter batchScadenzarioFilter;
    private IstanzeeventiService istanzeeventiService;

    public EventiSistemaHelperTable(BatchScadenzarioFilter batchScadenzarioFilter, IstanzeeventiService istanzeeventiService) {

	super();
	this.batchScadenzarioFilter = batchScadenzarioFilter;
	this.istanzeeventiService = istanzeeventiService;
    }

    private int countedRecords = 0;

    public int getCountedRecords() {

	return countedRecords;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("descrizione", "label.descrizione", false, false, true, "", "60%");
	columnJmesa.addDataBaseColumn("data", "label.data", WebConstants.DATE_FORMAT_PATTERN, false, false, true, "", "8%");
	columnJmesa.addBaseColumn("categorieeventibase.descrizione", "label.categoria", true, true, true, "", "20%");
	columnJmesa.addBaseColumn("software.descrizione", "label.modulo", true, true, true, "", "10%");
	final String label = getMessageFromBundle(getContext(), "label.seleziona_deseleziona_tutti", null);
	final String header = getMessageFromBundle(getContext(), "label.segna_come_letti", null);
	HeaderEditor customHeaderEditor = new HeaderEditor() {

	    @Override
	    public Object getValue() {

		return "<span title=\"" + header + "\">" + header
			+ "</span><br /><input type=\"checkbox\" id=\"eds_seleziona_tutti\" onclick=\"selezionaTuttiEDS()\" title=\"" + label
			+ "\"/>";
	    }
	};
	columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", new CheckBoxAjaxUpdateIstanzeeventi("flagLetto", true), customHeaderEditor,
		false, false, false, "8%");
    }

    @Override
    protected Collection<?> setItems() {

	int count = istanzeeventiService.countByEventiSistema(batchScadenzarioFilter.getUtenteLoggato(), false);
	this.countedRecords = count;
	getFacade().setTotalRows(count);
	List<Istanzeeventi> list = new ArrayList<Istanzeeventi>();
	if (count > 0) {
	    list = istanzeeventiService.findEventiSistema(batchScadenzarioFilter.getUtenteLoggato(), getStartRowPage(), getEndRowPage(), false);
	}
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle istanze");
	int count = istanzeeventiService.countByEventiSistema(batchScadenzarioFilter.getUtenteLoggato(), false);
	List<Istanzeeventi> list = new ArrayList<Istanzeeventi>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "descrizione");
	colonne.add(1, "data");
	colonne.add(2, "categoria");
	colonne.add(3, "software");
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
		list = istanzeeventiService.findEventiSistema(batchScadenzarioFilter.getUtenteLoggato(), startRow, rowEnd, false);
		for (Istanzeeventi eventosistema : list) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(eventosistema.getDescrizione());
		    if (eventosistema.getData() != null) {
			riga[1] = Utilities.formatDate(eventosistema.getData(), false);
		    } else {
			riga[1] = "";
		    }
		    if (eventosistema.getCategorieeventibase() != null && StringUtils.isNotBlank(eventosistema.getCategorieeventibase().getId())) {
			riga[2] = StringUtils.defaultIfEmpty(eventosistema.getCategorieeventibase().getDescrizione(), "");
		    } else {
			riga[2] = "";
		    }
		    if (eventosistema.getSoftware() != null && StringUtils.isNotBlank(eventosistema.getSoftware().getCodice())) {
			riga[3] = StringUtils.defaultIfEmpty(eventosistema.getSoftware().getDescrizione(), "");
		    } else {
			riga[3] = "";
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
