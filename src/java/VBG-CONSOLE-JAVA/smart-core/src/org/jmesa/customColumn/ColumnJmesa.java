package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.jmesa.IJMesaLinkHelper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.celleditor.CustomBooleanCellEditor;
import org.jmesa.celleditor.CustomStringCellEditor;
import org.jmesa.celleditor.SiNoBooleanCellEditor;
import org.jmesa.celleditor.SiNoIntegerCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.filter.CustomObjectDropListEditor;
import org.jmesa.filter.DataCustomFilterEditor;
import org.jmesa.filter.IntegerCustomDropListEditor;
import org.jmesa.filter.SiNoIntegerDropListEditor;
import org.jmesa.filter.StringCustomDropListEditor;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.renderer.FilterRenderer;

public class ColumnJmesa implements ColumnJmesaInterface {

    public static final String DELETE = "delete";
    public static final String DETAIL = "detail";
    public static final String ADD = "add";
    public static final String VIEW_DOC = "view_doc";
    public static final String IMPORT = "import";
    private ComponentFactory factory;
    private Table table;
    private Row row;
    private TableFacade tableFacade;
    private boolean setHtmlProperties;

    public ColumnJmesa(ComponentFactory factory, Table table, Row row, TableFacade tableFacade, boolean setHtmlProperties) {

	super();
	this.factory = factory;
	this.table = table;
	this.row = row;
	this.tableFacade = tableFacade;
	this.setHtmlProperties = setHtmlProperties;
    }

    public void addBaseColumn(String path, String label, String titleHeader, String width) {

	// uso il cell edit standard
	CellEditor celEdit = factory.createBasicCellEditor();
	Column column = null;
	column = factory.createColumn(path, celEdit);
	column.setTitleKey(label);
	//se non sono in face di export setto di default che la colonna sia filtrabile ordinabile ed esportabile
	if (setHtmlProperties) {
	    ((HtmlColumn) column).setFilterable(true);
	    ((HtmlColumn) column).setSortable(true);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, true);
    }

    public void addBaseColumn(String path, String label, boolean filterable, boolean sortable, boolean export, String titleHeader, String width) {

	// uso il cell edit standard
	CellEditor celEdit = factory.createBasicCellEditor();
	Column column = null;
	column = factory.createColumn(path, celEdit);
	column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    /**
     * metodo per la creazione di una cella con valore utilizzato come link html
     * 
     * @param path
     * @param linkHrefValue
     * @param linkHrefPlaceHolders
     * @param titleKey
     * @param width
     */
    @Override
    public void addLinkColumn(String path, IJMesaLinkHelper linkHelper, String titleKey, String width) {

	Column column = null;
	CellEditor cellEditor = new LinkCellEditor(linkHelper);
	column = factory.createColumn(path, cellEditor);
	column.setTitleKey(titleKey);
	if (setHtmlProperties) {
	    ((HtmlColumn) column).setFilterable(true);
	    ((HtmlColumn) column).setSortable(true);
	    ((HtmlColumn) column).setWidth(width);
	}
	setRowToTable(table, column, row, setHtmlProperties, true);
    }

    /**
     * metodo per la creazione di una cella con valore utilizzato come link html
     * 
     * @param path
     * @param linkHrefValue
     * @param linkHrefPlaceHolders
     * @param titleKey
     * @param width
     */
    @Override
    public void addLinkColumn(String path, IJMesaLinkHelper linkHelper, String titleKey, boolean filterable, boolean sortable, boolean export,
	    String width) {

	Column column = null;
	CellEditor cellEditor = new LinkCellEditor(linkHelper);
	column = factory.createColumn(path, cellEditor);
	column.setTitleKey(titleKey);
	if (setHtmlProperties) {
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    @Override
    public void addLinkWithIconColumn(String classIcon, String title, IJMesaLinkHelper linkHelper, String titleKey, boolean filterable,
	    boolean sortable, boolean export, String width) {

	Column column = null;
	CellEditor cellEditor = new LinkCellWithIconEditor(linkHelper, classIcon, title);
	column = factory.createColumn(cellEditor);
	column.setTitleKey(titleKey);
	if (setHtmlProperties) {
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    public void addLinkBaseColumn(HttpServletRequest request, String path, String label, String pathParametro, String urlGoTo, String urlback,
	    boolean filterable, boolean sortable, boolean export, String titleHeader, String width) {

	// Creo un cell edidor ad hoc per i link
	LinkFiledBaseCellEditor celEdit = null;
	CellEditor celEditBase = null;
	Column column = null;
	// In visualizzazione uso il cell editor creato
	if (setHtmlProperties) {
	    // se non è settata l'urlBack richiamo un costruttore del cell editor che non fa l'HistorySet
	    if (urlback != null)
		celEdit = new LinkFiledBaseCellEditor(request, path, urlGoTo, urlback, pathParametro);
	    else {
		celEdit = new LinkFiledBaseCellEditor(request, path, urlGoTo, pathParametro);
	    }
	    column = factory.createColumn(path, celEdit);
	    // in export uso il celle editor standard
	} else {
	    celEditBase = factory.createBasicCellEditor();
	    column = factory.createColumn(path, celEditBase);
	}
	column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    public void addLinkLabelBaseColumn(HttpServletRequest request, String path, String label, String nameLink, String urlGoTo, String urlback,
	    boolean filterable, boolean sortable, String titleHeader, String width) {

	// Creo un cell edidor ad hoc per i link
	LinkLabelBaseCellEditor celEdit = null;
	CellEditor celEditBase = null;
	Column column = null;
	// In visualizzazione uso il cell editor creato
	if (setHtmlProperties) {
	    celEdit = new LinkLabelBaseCellEditor(request, nameLink, urlGoTo, urlback, path);
	    column = factory.createColumn(path, celEdit);
	} else { // in export uso il celle editor standard
	    celEditBase = factory.createBasicCellEditor();
	    column = factory.createColumn(path, celEditBase);
	}
	if (StringUtils.isNotBlank(label)) {
	    column.setTitleKey(label);
	} else {
	    column.setTitleKey("");
	}
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    if (StringUtils.isNotBlank(titleHeader)) {
		((HtmlColumn) column).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(label, titleHeader));
	    }
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, false);
    }

    public void addDataBaseColumn(String path, String label, String dateFormat, boolean filterable, boolean sortable, boolean export,
	    String titleHeader, String width) {

	// Creo un cell edidor ad hoc per le date
	DateCellEditor celEdit = new DateCellEditor(dateFormat);
	Column column = null;
	column = factory.createColumn(path, celEdit);
	column.setTitleKey(label);
	//se non sono in fase di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) column).getFilterRenderer();
	    if (BooleanUtils.isTrue(filterable)) {
		renderer.setFilterEditor(new DataCustomFilterEditor(tableFacade.getLimit().getId(), path, "cal_" + path));
	    }
	    ((HtmlColumn) column).setFilterRenderer(renderer);
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    public void addBooleanColumn(String path, String label, boolean filterable, boolean sortable, boolean export, String titleHeader, String width) {

	// Creo un cell edidor ad hoc per un campo boolenano
	SiNoBooleanCellEditor celEdit = new SiNoBooleanCellEditor();
	Column column = null;
	column = factory.createColumn(path, celEdit);
	column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) column).getFilterRenderer();
	    renderer.setFilterEditor(new SiNoIntegerDropListEditor(1, 0));
	    ((HtmlColumn) column).setFilterRenderer(renderer);
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    public void addIntegerCustomColumn(String path, String label, Map<String, Integer> mappaLabelValore, boolean filterable, boolean sortable,
	    boolean export, String titleHeader, String width) {

	SiNoIntegerCellEditor celEdit = new SiNoIntegerCellEditor(mappaLabelValore);
	Column column = null;
	column = factory.createColumn(path, celEdit);
	column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) column).getFilterRenderer();
	    renderer.setFilterEditor(new IntegerCustomDropListEditor(mappaLabelValore));
	    ((HtmlColumn) column).setFilterRenderer(renderer);
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    @Override
    public void addStringCostunColumn(String path, String label, Map<String, String> mappaLabelValore, boolean filterable, boolean sortable,
	    boolean export, String titleHeader, String width) {

	// Creo un cell edidor ad hoc per un campo boolenano
	CellEditor celEdit = new CustomStringCellEditor(mappaLabelValore);
	Column column = null;
	column = factory.createColumn(path, celEdit);
	column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) column).getFilterRenderer();
	    renderer.setFilterEditor(new StringCustomDropListEditor(mappaLabelValore));
	    ((HtmlColumn) column).setFilterRenderer(renderer);
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    @Override
    public void addBaseActionColumn(HttpServletRequest request, String pathparametro, String label, String typeAction, String urlGoTo,
	    String urlback, String titleHeader, String width) {

	// Creo un cell edidor ad hoc per i link action
	LinkActionBaseCellEditor celEdit = null;
	Column column = null;
	// In visualizzazione uso il cell editor creato
	if (setHtmlProperties) {
	    // se non è settata l'urlBack richiamo un costruttore del cell editor che non fa l'HistorySet
	    if (urlback != null)
		celEdit = new LinkActionBaseCellEditor(request, urlback, urlGoTo, typeAction, pathparametro);
	    else {
		celEdit = new LinkActionBaseCellEditor(request, urlGoTo, typeAction, pathparametro);
	    }
	    column = factory.createColumn(pathparametro, celEdit);
	    column.setTitleKey(label);
	}
	//se non sono in fase di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    ((HtmlColumn) column).setFilterable(false);
	    ((HtmlColumn) column).setSortable(false);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, false);
    }

    @Override
    public void addDownloadActionColumn(HttpServletRequest request, String pathparametro) {

	this.addBaseActionColumn(request, pathparametro, "label.visualizza", ColumnJmesa.VIEW_DOC, "../file/ajaxDownload.htm?fileId=", "", "", "5%");
    }

    @Override
    public void addCellEditorCustomLabelColumn(HttpServletRequest request, String path, String label, String labelElementColumn,
	    AbstractCellEditor cellEditor, boolean filterable, boolean sortable, boolean export, String titleHeader, String width) {

	Column column = null;
	column = factory.createColumn(path, cellEditor);
	column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) column).getFilterRenderer();
	    if (StringUtils.isNotBlank(titleHeader)) {
		((HtmlColumn) column).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(label, titleHeader));
	    }
	    ((HtmlColumn) column).setFilterRenderer(renderer);
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    @Override
    public void addCellEditorCustomLabelColumn(HttpServletRequest request, String path, AbstractCellEditor cellEditor,
	    HeaderEditor customHeaderEditor, boolean filterable, boolean sortable, boolean export, String width) {

	Column column = null;
	column = factory.createColumn(path, cellEditor);
	//column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) column).getFilterRenderer();
	    ((HtmlColumn) column).getHeaderRenderer().setHeaderEditor(customHeaderEditor);
	    ((HtmlColumn) column).setFilterRenderer(renderer);
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    @Override
    public void addCellEditorCustomLabelColumn(HttpServletRequest request, String path, String label, String labelElementColumn,
	    AbstractCellEditor cellEditor, DroplistFilterEditor droplistFilterEditor, boolean filterable, boolean sortable, boolean export,
	    String titleHeader, String width) {

	Column column = null;
	column = factory.createColumn(path, cellEditor);
	column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) column).getFilterRenderer();
	    if (StringUtils.isNotBlank(titleHeader)) {
		((HtmlColumn) column).getHeaderRenderer().setHeaderEditor(new CustomHeaderEditor(label, titleHeader));
	    }
	    renderer.setFilterEditor(droplistFilterEditor); // new SiNoIntegerDropListEditor(1, 0)
	    ((HtmlColumn) column).setFilterRenderer(renderer);
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    @Override
    public void addBooleanCustomColumn(String path, String label, Map<String, Boolean> mappaLabelValore, boolean filterable, boolean sortable,
	    boolean export, String titleHeader, String width) {

	CustomBooleanCellEditor celEdit = new CustomBooleanCellEditor(mappaLabelValore);
	Column column = null;
	column = factory.createColumn(path, celEdit);
	column.setTitleKey(label);
	//se non sono in face di export  la colonna sia filtrabile ordinabile ed esportabile in base ai parametri passati
	if (setHtmlProperties) {
	    FilterRenderer renderer = ((HtmlColumn) column).getFilterRenderer();
	    renderer.setFilterEditor(new CustomObjectDropListEditor<Boolean>(mappaLabelValore));
	    ((HtmlColumn) column).setFilterRenderer(renderer);
	    ((HtmlColumn) column).setFilterable(filterable);
	    ((HtmlColumn) column).setSortable(sortable);
	    ((HtmlColumn) column).setWidth(width);
	}
	// setta la colonna alla tabella
	setRowToTable(table, column, row, setHtmlProperties, export);
    }

    // setta la colonna alla tabella , verfica nel caso siamo in export se la colonna è stata settata come esportabile
    private void setRowToTable(Table table, Column column, Row row, boolean setHtmlProperties, boolean export) {

	if (setHtmlProperties) {
	    row.addColumn(column);
	    table.setRow(row);
	} else {
	    if (export) {
		row.addColumn(column);
		table.setRow(row);
	    }
	}
    }
}
