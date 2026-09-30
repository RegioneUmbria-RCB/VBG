package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkDettaglioIstanzaInAutorizzazioniSub;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

public class AutorizzazioniSubentriTable extends GenerateTable<AutorizzazioniSubentri> {

    private AutorizzazioniFilter filter;
    private Integer codiceIstanza;

    public AutorizzazioniSubentriTable(AutorizzazioniFilter filter, Integer codiceIstanza) {

	super();
	this.filter = filter;
	this.codiceIstanza = codiceIstanza;
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
	String uriBack = "/autorizzazioni/list.htm".concat(parametriHistoryBack.toString());
	//JMesaTableLinkHelper dettaglioIstanzaLink = new JMesaTableLinkHelper("istanza", uriBack, LinkTargetEnum.DETTAGLIO_ISTANZA);
	JMesaTableLinkHelper dettaglioAutorizzazioneLink = new JMesaTableLinkHelper("", uriBack, LinkTargetEnum.CREATE_AUTORIZZAZIONE);
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
	//	Map<String, Boolean> mappaLabelValoreStato = new HashMap<String, Boolean>();
	//	mappaLabelValoreStato.put("label.cessata", false);
	//	mappaLabelValoreStato.put("label.attiva", true);
	//columnJmesa.addBooleanCustomColumn("flagAttiva", "label.stato", mappaLabelValoreStato, false, false, false, null, "");
	//columnJmesa.addLinkColumn("istanza.numeroistanza", dettaglioIstanzaLink, "label.numero_istanza", false, false, false, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", "label.numero_istanza", "", new LinkDettaglioIstanzaInAutorizzazioniSub(
		request), false, false, false, "", "5%");
	columnJmesa.addLinkWithIconColumn("dettaglioColumn", "label.edit.record", dettaglioAutorizzazioneLink, "label.edit.record", false, false,
		false, "5%");
	//	columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", "label.azione", "", new LinkStampaAutorizzazioni(request), false, false,
	//		false, "", "5%");
    }

    @Override
    protected Collection<?> setItems() {

	AutorizzazioniSubentriService autorizzazioniSubentriService = (AutorizzazioniSubentriService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("autorizzazioniSubentriServiceImpl", AutorizzazioniSubentriService.class);
	//	int count = autorizzazioniService.countByFilter(filter);
	int count = autorizzazioniSubentriService.countByFilter(filter);
	getFacade().setTotalRows(count);
	List<AutorizzazioniSubentri> list = autorizzazioniSubentriService.findAutorizzazioniSubentriByFilter(filter, getStartRowPage(),
		getEndRowPage());
	return list;
    }
}
