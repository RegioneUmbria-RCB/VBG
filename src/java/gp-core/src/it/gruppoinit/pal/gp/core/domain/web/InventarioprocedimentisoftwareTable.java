package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.jmesa.JMesaTableLinkHelper;
import it.gruppoinit.pal.gp.core.jmesa.LinkTargetEnum;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.filter.MatcherKey;
import org.jmesa.custom.SiNoFilterMatcher;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

public class InventarioprocedimentisoftwareTable extends GenerateTable<Inventarioprocedimentisoftware> {

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Boolean.class, "attivo"), new SiNoFilterMatcher());
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	JMesaTableLinkHelper dettaglioEndo = new JMesaTableLinkHelper("", "../inventarioprocedimenti/listEndobase.htm?software="
		+ ORMHelper.getSoftware(), LinkTargetEnum.INVENTARIOPROCEDIMENTI);
	columnJmesa.addLinkColumn("inventarioprocedimento.id.codice", dettaglioEndo, "label.codice", true, true, true, "2%");
	columnJmesa.addBaseColumn("inventarioprocedimento.procedimento", "inventarioprocedimenti.label.procedimento", null, "");
	columnJmesa.addBaseColumn("inventarioprocedimento.tipoendo.tipo", "inventarioprocedimenti.label.tipologia", null, "");
	columnJmesa.addBaseColumn("inventarioprocedimento.tipoendo.tipifamiglieendo.tipo", "inventarioprocedimenti.label.tipologia_famiglia", null,
		"");
	Map<String, Boolean> mappaLabelValoreStato = new HashMap<String, Boolean>();
	mappaLabelValoreStato.put("label.si", Boolean.TRUE);
	mappaLabelValoreStato.put("label.no", Boolean.FALSE);
	columnJmesa.addBooleanCustomColumn("inventarioprocedimento.disabilitato", "label.disabilitato", mappaLabelValoreStato, true, true, true, "label.disabilitato",
		"4%");
	JMesaTableLinkHelper dettaglioConfigurazioneEndo = new JMesaTableLinkHelper("", "../inventarioprocedimenti/listEndobase.htm?software="
		+ ORMHelper.getSoftware(), LinkTargetEnum.INVENTARIOPROCEDIMENTISOFTWARE);
	columnJmesa.addLinkWithIconColumn("dettaglioColumn", "label.edit.record", dettaglioConfigurazioneEndo, "label.edit.record", false, false,
		false, "5%");
    }

    @Override
    protected Collection<?> setItems() {

	// Utilizzo il Service di Inventarioprocedimenti poiché voglio la lista di tutti gli Inventarioprocedimenti con software TT
	InventarioprocedimentiService inventarioprocedimentiService = (InventarioprocedimentiService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("inventarioprocedimentiServiceImpl", InventarioprocedimentiService.class);
	// Filtro per Inventarioprocedimentisoftware 
	Inventarioprocedimentisoftware inventarioprocedimentisoftware = new Inventarioprocedimentisoftware();
	inventarioprocedimentisoftware = getFilterQuery(inventarioprocedimentisoftware);
	FilterTable filterTable = inventarioprocedimentiService.createFilterTableByEntity(inventarioprocedimentisoftware);
	int count = inventarioprocedimentiService.countRecord(filterTable);
	getFacade().setTotalRows(count);
	List<Inventarioprocedimentisoftware> list = inventarioprocedimentiService.findInventarioprocedimentisoftwareByFilterTable(filterTable,
		getStartRowPage(), getEndRowPage());
	return list;
    }
}
