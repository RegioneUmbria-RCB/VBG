package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

public class InventarioprocedimentiTable extends GenerateTable<Inventarioprocedimenti> {

    private String tipo;

    public InventarioprocedimentiTable(String tipo) {

	this.tipo = tipo;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	String uriBack = URLEncoder.encode(URLEncoder.encode("../inventarioprocedimenti/list.htm?" + request.getQueryString()));
	String customIstanzeLink = "../inventarioprocedimenti/view.htm?codice=<id.codice>&codicecomune=<id.idcomune>";
	IJMesaLinkHelper dettaglioIstanzaLink = new JMesaCustomLinkHelper(customIstanzeLink, uriBack);
	columnJmesa.addLinkColumn("id.codice", dettaglioIstanzaLink, "label.codice", true, true, true, "2%");
	//	columnJmesa.addLinkBaseColumn(request, "id.codice", "label.codice", "id.codice", "../inventarioprocedimenti/view.htm?codice=",
	//		"../inventarioprocedimenti/list.htm", true, true, true, null, "2%");
	columnJmesa.addBaseColumn("procedimento", "inventarioprocedimenti.label.procedimento", null, "");
	columnJmesa.addBaseColumn("tipoendo.tipo", "inventarioprocedimenti.label.tipi_endo", null, "");
	columnJmesa.addBaseColumn("tipoendo.tipifamiglieendo.tipo", "inventarioprocedimenti.label.tipologia_famiglia", null, "");
	columnJmesa.addBaseColumn("amministrazioni.amministrazione", "label.amministrazione", null, "");
	columnJmesa.addBaseColumn("naturaendo.natura", "label.natura", null, "");
	//columnJmesa.addBaseColumn("transientListaCodiciPeople", "label.codici_procedimenti_stp", null, "");
	Map<String, Boolean> mappaLabelValoreStato1 = new HashMap<String, Boolean>();
	mappaLabelValoreStato1.put("label.si", Boolean.TRUE);
	mappaLabelValoreStato1.put("label.no", Boolean.FALSE);
	columnJmesa.addBooleanCustomColumn("flagPubblica", "label.pubblica", mappaLabelValoreStato1, true, true, true, "label.pubblica", "4%");
	Map<String, Boolean> mappaLabelValoreStato = new HashMap<String, Boolean>();
	mappaLabelValoreStato.put("label.si", Boolean.TRUE);
	mappaLabelValoreStato.put("label.no", Boolean.FALSE);
	columnJmesa.addBooleanCustomColumn("disabilitato", "label.disabilitato", mappaLabelValoreStato, true, true, true, "label.disabilitato", "4%");
	//	columnJmesa.addBaseActionColumn(request, "id.codice", "label.edit.record", ColumnJmesa.DETAIL, "../inventarioprocedimenti/view.htm?codice=",
	//		"../inventarioprocedimenti/list.htm", null, "5%");
    }

    @Override
    protected Collection<?> setItems() {

	InventarioprocedimentiService inventarioprocedimentiService = (InventarioprocedimentiService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("inventarioprocedimentiServiceImpl", InventarioprocedimentiService.class);
	Inventarioprocedimenti inventarioprocedimenti = new Inventarioprocedimenti();
	inventarioprocedimenti.setDisabilitato(null);
	inventarioprocedimenti = getFilterQuery(inventarioprocedimenti);
	if (StringUtils.isNotBlank(tipo)) {
	    if (WebConstants.TIPO_ENDO2.equalsIgnoreCase(tipo)) {
		inventarioprocedimenti.setFlagTransientIsTipo2(Boolean.TRUE);
	    }
	    if (WebConstants.TIPO_ENDO1.equalsIgnoreCase(tipo)) {
		inventarioprocedimenti.setFlagTransientIsTipo1(Boolean.TRUE);
	    }
	}
	FilterTable filterTable = inventarioprocedimentiService.createFilterTableByEntity(inventarioprocedimenti);
	int count = inventarioprocedimentiService.countRecord(filterTable);
	getFacade().setTotalRows(count);
	List<Inventarioprocedimenti> list = inventarioprocedimentiService.findByFilterTable(filterTable, getStartRowPage(), getEndRowPage());
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista degli endoprocedimenti");
	InventarioprocedimentiService inventarioprocedimentiService = (InventarioprocedimentiService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("inventarioprocedimentiServiceImpl", InventarioprocedimentiService.class);
	Inventarioprocedimenti inventarioprocedimenti = new Inventarioprocedimenti();
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
		    int pos = 0;
		    riga[pos++] = String.valueOf(endo.getId().getCodice());
		    riga[pos++] = endo.getProcedimento();
		    riga[pos++] = endo.getDatigenerali();
		    riga[pos++] = (String) EntityUtils.getNestedProperty(endo.getAmministrazioni(), "amministrazione");
		    riga[pos++] = Utilities.formatDate(endo.getDataaggiornamento(), false);
		    // riga[5] = (String) EntityUtils.getNestedProperty(endo.getTempificazione(), "tempificazione");
		    riga[pos++] = endo.getNormativaue();
		    riga[pos++] = endo.getNormativana();
		    riga[pos++] = endo.getNormativare();
		    riga[pos++] = endo.getRegolamenti();
		    riga[pos++] = endo.getAdempimenti();
		    riga[pos++] = (String) EntityUtils.getNestedProperty(endo.getTipoendo(), "tipo");
		    riga[pos++] = (endo.getCollaudo() == null) ? "false" : String.valueOf(endo.getCollaudo().booleanValue());
		    riga[pos++] = endo.getPerprovvedimento() == null ? "false" : String.valueOf(endo.getPerprovvedimento().booleanValue());
		    riga[pos++] = endo.getDisabilitato() == null ? "false" : String.valueOf(endo.getDisabilitato().booleanValue());
		    riga[pos++] = endo.getOrdine() == null ? "" : String.valueOf(endo.getOrdine());
		    riga[pos++] = (String) EntityUtils.getNestedProperty(endo.getAmministrazionireferente(), "ufficio");
		    // 		    riga[17] = (String) EntityUtils.getNestedProperty(endo.getTipomovimento(), "descrizioneEstesa");
		    riga[pos++] = (String) EntityUtils.getNestedProperty(endo.getNaturaendo(), "natura");
		    riga[pos++] = endo.getCodiceancitel();
		    riga[pos++] = (String) EntityUtils.getNestedProperty(endo.getTipoendo(), "tipifamiglieendo.tipo");
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
