package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Archiviazioni;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniService;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.customColumn.ArchiviazioniCorrettoCellEditor;
import org.jmesa.customColumn.ArchiviazioniErroreCellEditor;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

public class ArchiviazioniHelperTable extends GenerateTable<Archiviazioni> {

    private Boolean isSoloConErrori;

    public ArchiviazioniHelperTable(Boolean isSoloConErrori) {

	super();
	this.isSoloConErrori = isSoloConErrori;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("id.codice", "label.codice", false, false, false, null, "");
	columnJmesa.addBaseColumn("software.codice", "label.software", false, false, false, null, "");
	columnJmesa.addBaseColumn("idJob", "label.jobId", true, false, false, null, "");
	columnJmesa.addBaseColumn("numero", "label.numero_oggetti_archiviati", false, false, false, null, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "errore", "label.errore", null, new ArchiviazioniErroreCellEditor(), false, false, false,
		null, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "corretto", "label.corretto", null, new ArchiviazioniCorrettoCellEditor(), false, false,
		false, null, "");
	columnJmesa.addDataBaseColumn("data", "label.data", WebConstants.DATE_FORMAT_PATTERN, false, false, true, "", "8%");
	columnJmesa.addBaseActionColumn(request, "id.codice", "label.azioni", "delete", "../archiviazioni/delete.htm?archId=", null, "label.elimina",
		"3%");
	columnJmesa.addBaseActionColumn(request, "id.codice", "label.archiviazioni.lista_istanze_escluse", ColumnJmesa.DETAIL,
		"../archiviazioni/listEscluse.htm?archId=", "../archiviazioni/list.htm", null, "3%");
	columnJmesa.addBaseActionColumn(request, "id.codice", "label.archiviazioni.lista_istanze_archiviate", ColumnJmesa.DETAIL,
		"../archiviazioni/listIstanze.htm?archId=", "../archiviazioni/list.htm", null, "3%");
    }

    @Override
    protected Collection<Archiviazioni> setItems() {

	ArchiviazioniService archiviazioniService = (ArchiviazioniService) ContextLoader.getCurrentWebApplicationContext().getBean(
		"archiviazioniServiceImpl", ArchiviazioniService.class);
	int numRecords = archiviazioniService.countRecords();
	getFacade().setTotalRows(numRecords);
	List<Archiviazioni> r = archiviazioniService.findAll(getStartRowPage(), getEndRowPage(), isSoloConErrori);
	return r;
    }
}
