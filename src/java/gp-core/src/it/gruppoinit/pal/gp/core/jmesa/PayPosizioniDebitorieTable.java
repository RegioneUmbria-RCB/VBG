package it.gruppoinit.pal.gp.core.jmesa;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.GenerateTable;

import it.gruppoinit.pal.gp.core.domain.helper.PayPosizioniDebitorieFilter;

public class PayPosizioniDebitorieTable extends GenerateTable {

    private PayPosizioniDebitorieFilter filter;

    public PayPosizioniDebitorieTable(PayPosizioniDebitorieFilter filter) {

	super();
	this.filter = filter;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addCellEditorCustomLabelColumn(request, "idposizionedebitoria", "label.codice", "",
		new PayPosizioniDebitorieCustomColumn(request, "idposizionedebitoria"), false, false, false, "", "2%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "presenzaspuntista", "label.posizione_debitoria.agganciata_a_posteggio", "",
		new PayPosizioniDebitorieCustomColumn(request, "presenzaspuntista"), false, false, false, "", "2%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "statopagato", "label.posizione_debitoria.statopagato", "",
		new PayPosizioniDebitorieCustomColumn(request, "statopagato"), false, false, false, "", "2%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "statopagamentoattivo", "label.posizione_debitoria.statopagamentoattivo", "",
		new PayPosizioniDebitorieCustomColumn(request, "statopagamentoattivo"), false, false, false, "", "2%");
	columnJmesa.addBaseColumn("descrizionecausale", "label.descrizione", false, false, false, null, "");
	columnJmesa.addBaseColumn("dataregistrazione", "label.data", false, false, false, null, "");
	columnJmesa.addBaseColumn("nome", "label.nome", false, false, false, null, "");
	columnJmesa.addBaseColumn("cognome", "label.cognome", false, false, false, null, "");
	columnJmesa.addBaseColumn("cfpiva", "label.codicefiscale", false, false, false, null, "");
    }

    @Override
    protected Collection<?> setItems() {

	// TODO NODOPAGAMENTI
	return null;
	//	int count = payPosizioniDebitorieService.countPosizioniDebitorieListByFilter(filter);
	//	getFacade().setTotalRows(count);
	//	List<PayPosizionidebitorieListHelper> list = payPosizioniDebitorieService.findPosizioniDebitorieListByFilter(filter, getStartRowPage(),
	//		getEndRowPage());
	//	return list;
    }
}
