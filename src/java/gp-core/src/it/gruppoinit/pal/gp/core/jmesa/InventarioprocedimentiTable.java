package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.helper.InventarioprocedimentiFilter;
import it.gruppoinit.pal.gp.core.service.helper.VerticalizzazioneFvgSol;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

public class InventarioprocedimentiTable extends GenerateTable<Inventarioprocedimenti> {

    private VerticalizzazioniService verticalizzazioniService;
    private static final Logger log = LoggerFactory.getLogger(InventarioprocedimentiTable.class);

    public InventarioprocedimentiTable(VerticalizzazioniService verticalizzazioniService) {

	super();
	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	VerticalizzazioneFvgSol vFvgSol = new VerticalizzazioneFvgSol(verticalizzazioniService, false);
	boolean isFvgSolAttivaAndConsole = vFvgSol.isConsolleAttiva();
	//	if (vFvgSol.getIsAttiva() && "1".equals(vFvgSol.getUSA_BACKOFFICE_COME_CONSOLE())) {
	//	    isFvgSolAttivaAndConsole = true;
	//	}
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkBaseColumn(request, "id.codice", "label.codice", "id.codice", "../inventarioprocedimenti/view.htm?codice=",
		"../inventarioprocedimenti/list.htm", true, true, true, null, "2%");
	if (!isFvgSolAttivaAndConsole) {
	    columnJmesa.addBaseColumn("procedimento", "inventarioprocedimenti.label.procedimento", null, "");
	} else {
	    columnJmesa.addBaseColumn("procedimento", "label.modulo", null, "");
	}
	if (!isFvgSolAttivaAndConsole) {
	    columnJmesa.addBaseColumn("tipoendo.tipo", "inventarioprocedimenti.label.tipologia", null, "");
	    columnJmesa.addBaseColumn("tipoendo.tipifamiglieendo.tipo", "inventarioprocedimenti.label.tipologia_famiglia", null, "");
	}
	columnJmesa.addBaseColumn("ordine", "label.ordine", null, "2%");
	columnJmesa.addBaseColumn("transientListaCodiciPeople", "label.codici_procedimenti_stp", true, false, true, null, "");
	if (!isFvgSolAttivaAndConsole) {
	    Map<String, Boolean> mappaLabelValoreStato = new HashMap<String, Boolean>();
	    mappaLabelValoreStato.put("label.si", Boolean.TRUE);
	    mappaLabelValoreStato.put("label.no", Boolean.FALSE);
	    columnJmesa.addBooleanCustomColumn("disabilitato", "label.disabilitato", mappaLabelValoreStato, true, true, true, "label.disabilitato",
		    "4%");
	}
	columnJmesa.addBaseActionColumn(request, "id.codice", "label.edit.record", ColumnJmesa.DETAIL, "../inventarioprocedimenti/view.htm?codice=",
		"../inventarioprocedimenti/list.htm", null, "5%");
    }

    @Override
    protected Collection<?> setItems() {

	InventarioprocedimentiService inventarioprocedimentiService = (InventarioprocedimentiService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("inventarioprocedimentiServiceImpl", InventarioprocedimentiService.class);
	InventarioprocedimentiFilter inventarioprocedimentiF = new InventarioprocedimentiFilter();
	Map<String, String> mappaOrdinamento = getOrdinamentoQuery();
	inventarioprocedimentiF.setOrdinamentoMap(mappaOrdinamento);
	Inventarioprocedimenti inventarioprocedimenti = new Inventarioprocedimenti();
	inventarioprocedimenti.setDisabilitato(null);
	inventarioprocedimenti = getFilterQuery(inventarioprocedimenti);
	inventarioprocedimentiF.setInventarioprocedimenti(inventarioprocedimenti);
	//FilterTable filterTable = inventarioprocedimentiService.createFilterTableByEntity(inventarioprocedimenti);
	FilterTable filterTable = inventarioprocedimentiService.createFilterTableByInventarioprocFilter(inventarioprocedimentiF);
	int count = inventarioprocedimentiService.countRecord(filterTable);
	getFacade().setTotalRows(count);
	List<Inventarioprocedimenti> list = inventarioprocedimentiService.findByFilterTable(filterTable, getStartRowPage(), getEndRowPage());
	return list;
    }

    /**
     * Metodo di utilità che crea e restituisce una mappa <K,V> popolata dagli elementi contenuti nel SortSet. Le chiavi
     * sono le property contenute nel SortSet mentre i valori sono i sort order ovvero la modalità di ordine scelta
     * dall'utente (es. "asc" o "desc").
     * 
     * @return
     */
    private Map<String, String> getOrdinamentoQuery() {

	SortSet sortSet = getFacade().getLimit().getSortSet();
	Collection<Sort> sortFilters = sortSet.getSorts();
	Map<String, String> ordinamentoMap = new HashMap<String, String>();
	for (Sort sort : sortFilters) {
	    ordinamentoMap.put(sort.getProperty(), sort.getOrder().toString().toLowerCase());
	}
	return ordinamentoMap;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista degli endoprocedimenti");
	InventarioprocedimentiService inventarioprocedimentiService = (InventarioprocedimentiService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("inventarioprocedimentiServiceImpl", InventarioprocedimentiService.class);
	Inventarioprocedimenti inventarioprocedimenti = new Inventarioprocedimenti();
	inventarioprocedimenti.setDisabilitato(null);
	inventarioprocedimenti = getFilterQuery(inventarioprocedimenti);
	FilterTable filterTable = inventarioprocedimentiService.createFilterTableByEntity(inventarioprocedimenti);
	int count = inventarioprocedimentiService.countRecord(filterTable);
	List<Inventarioprocedimenti> list = new ArrayList<Inventarioprocedimenti>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "codice");
	colonne.add(1, "procedimento");
	colonne.add(2, "datigenerali");
	colonne.add(3, "amministrazioni");
	colonne.add(4, "dataaggiornamento");
	colonne.add(5, "tempificazione");
	colonne.add(6, "normativaue");
	colonne.add(7, "normativana");
	colonne.add(8, "normativare");
	colonne.add(9, "regolamenti");
	colonne.add(10, "adempimenti");
	colonne.add(11, "tipoendo");
	colonne.add(12, "collaudo");
	colonne.add(13, "perprovvedimento");
	colonne.add(14, "disabilitato");
	colonne.add(15, "ordine");
	colonne.add(16, "amministrazionireferente");
	colonne.add(17, "tipomovimento");
	colonne.add(18, "naturaendo");
	colonne.add(19, "codiceancitel");
	colonne.add(20, "famigliaendo");
	colonne.add(21, "ordine");
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
		list = inventarioprocedimentiService.findByFilterTable(filterTable, startRow, rowEnd);
		for (Inventarioprocedimenti endo : list) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(endo.getId().getCodice());
		    riga[1] = endo.getProcedimento();
		    riga[2] = endo.getDatigenerali();
		    riga[3] = (String) EntityUtils.getNestedProperty(endo.getAmministrazioni(), "amministrazione");
		    riga[4] = Utilities.formatDate(endo.getDataaggiornamento(), false);
		    riga[5] = (String) EntityUtils.getNestedProperty(endo.getTempificazione(), "tempificazione");
		    riga[6] = endo.getNormativaue();
		    riga[7] = endo.getNormativana();
		    riga[8] = endo.getNormativare();
		    riga[9] = endo.getRegolamenti();
		    riga[10] = endo.getAdempimenti();
		    riga[11] = (String) EntityUtils.getNestedProperty(endo.getTipoendo(), "tipo");
		    riga[12] = (endo.getCollaudo() == null) ? "false" : String.valueOf(endo.getCollaudo().booleanValue());
		    riga[13] = endo.getPerprovvedimento() == null ? "false" : String.valueOf(endo.getPerprovvedimento().booleanValue());
		    riga[14] = endo.getDisabilitato() == null ? "false" : String.valueOf(endo.getDisabilitato().booleanValue());
		    riga[15] = endo.getOrdine() == null ? "" : String.valueOf(endo.getOrdine());
		    riga[16] = (String) EntityUtils.getNestedProperty(endo.getAmministrazionireferente(), "ufficio");
		    riga[17] = (String) EntityUtils.getNestedProperty(endo.getTipomovimento(), "descrizioneEstesa");
		    riga[18] = (String) EntityUtils.getNestedProperty(endo.getNaturaendo(), "natura");
		    riga[19] = endo.getCodiceancitel();
		    riga[20] = (String) EntityUtils.getNestedProperty(endo.getTipoendo(), "tipifamiglieendo.tipo");
		    riga[21] = endo.getOrdine() == null ? "" : String.valueOf(endo.getOrdine());
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
