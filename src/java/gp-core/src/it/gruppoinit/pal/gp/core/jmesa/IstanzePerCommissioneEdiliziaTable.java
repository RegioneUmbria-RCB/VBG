package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.CheckBoxColumn;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

/**
 * 
 * @author gianpaolot Rapprensenta la tabella di tutte le istanze che possono essere discusse una specifica commissione
 *         edilizia. Le istanze verranno recuperate a pratire dal movimento che le manda in commissione
 */
public class IstanzePerCommissioneEdiliziaTable extends GenerateTable<Movimenti> {

    private Date date;
    private CommissioniedilizieT commissioniedilizieT;

    public IstanzePerCommissioneEdiliziaTable(Date date, CommissioniedilizieT commissioniedilizieT) {

	super();
	this.date = date;
	this.commissioniedilizieT = commissioniedilizieT;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	//filterMatcherMap.put(new MatcherKey(Integer.class, "flagaperta"), new StatoCommissioneEdiliziaTFilterMatcher());
	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "istanza.data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "istanza.dataprotocollo"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkBaseColumn(request, "istanza.numeroistanza", "label.codice", "istanza.id.codice", "../istanze/view.htm?codice=", "../commissioniediliziet/searchIstanzeInCommissione.htm", true, false,
		true, "", "8%");
	columnJmesa.addDataBaseColumn("istanza.data", "label.data_presentazione", WebConstants.DATE_FORMAT_PATTERN, true, false, true, "", "9%");
	columnJmesa.addBaseColumn("istanza.numeroprotocollo", "label.numero_protocollo", null, "");
	columnJmesa.addDataBaseColumn("istanza.dataprotocollo", "label.data_protocollo", WebConstants.DATE_FORMAT_PATTERN, true, false, true, "",
		"8%");
	columnJmesa.addBaseColumn("istanza.richiedente.richiedente", "label.richiedente", null, "");
	columnJmesa.addBaseColumn("istanza.lavori", "label.lavori", null, "20%");
	columnJmesa.addDataBaseColumn("data", "label.data_richiesta", WebConstants.DATE_FORMAT_PATTERN, true, false, true, "", "9%");
	columnJmesa.addBaseColumn("istanza.alberoproc.vwAlberoproc.scDescrizione", "label.intervento", null, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "", "label.azioni", "", new CheckBoxColumn("checkbox_id", "codiciMovimentiScelti",
		"id.codice"), false, false, false, "", "5%");
	/*
	  <jmesa:htmlColumn property="istanza.data" titleKey="label.data_presentazione" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataIstanzaCommissioneCustomFilter" width="10%"/>								
		
		<jmesa:htmlColumn property="istanza.dataprotocollo" titleKey="label.data_protocollo" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataIstazaProtocolloCommissioneCustomFilter" width="10%"/>
		
		
		<jmesa:htmlColumn property="data" titleKey="label.data_richiesta" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataRichiestaCommissioneCustomFilter" width="10%"/>
		
	 */
    }

    @Override
    protected Collection<?> setItems() {

	MovimentiService movimentiService = (MovimentiService) ContextLoader.getCurrentWebApplicationContext().getBean("movimentiServiceImpl",
		MovimentiService.class);
	int count = movimentiService.countMovimentiDaAssociareAllaCommissione(date, commissioniedilizieT);
	getFacade().setTotalRows(count);
	List<Movimenti> listMovimenti = new ArrayList<Movimenti>();
	if (count > 0) {
	    listMovimenti = movimentiService.findMovimentiDaAssociareAllaCommissione(date, commissioniedilizieT, getStartRowPage(), getEndRowPage());
	}
	return listMovimenti;
    }
}
