package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.BatchScadenzario;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.features.scadenzario.service.BatchScadenzarioService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;

public class BatchScadenzarioTable extends GenerateTable<BatchScadenzario> {

    private BatchScadenzarioFilter batchScadenzarioFilter;
    private BatchScadenzarioService batchScadenzarioService;
    private boolean isPerOperatore;
    private int countedRecords = 0;

    public int getCountedRecords() {

	return countedRecords;
    }

    public BatchScadenzarioTable(BatchScadenzarioFilter batchScadenzarioFilter, BatchScadenzarioService batchScadenzarioService,
	    boolean isPerOperatore) {

	this.batchScadenzarioFilter = batchScadenzarioFilter;
	this.batchScadenzarioService = batchScadenzarioService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "dataelaborazione"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "datascadenza"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	//String uriBack = isPerOperatore ? "/" : "/batchscadenzario/createSearch.htm";
	String uriBack = isPerOperatore ? "/" : "/batchscadenzario/list.htm?tab=" + WebConstants.TAB_SCADENZARIO;
	JMesaTableLinkHelper dettaglioIstanzaLink = new JMesaTableLinkHelper("istanza", uriBack, LinkTargetEnum.DETTAGLIO_ISTANZA);
	JMesaTableLinkHelper dettaglioMovimentoDaFareLink = new JMesaTableLinkHelper("movimentoDaFare", uriBack, LinkTargetEnum.DETTAGLIO_MOVIMENTO);
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkColumn("istanza.numeroistanza", dettaglioIstanzaLink, "batchscadenzario.label.numero_istanza", "");
	columnJmesa.addBaseColumn("software.descrizione", "batchscadenzario.label.modulo_software", false, false, true, "", "");
	columnJmesa.addBaseColumn("istanza.chiusura.stato", "batchscadenzario.label.stato_istanza", false, false, true, "", "");
	columnJmesa.addBaseColumn("istanza.richiedente.descrizioneRichiedente", "batchscadenzario.label.richiedente", false, false, true, "", "");
	columnJmesa.addBaseColumn("istanza.alberoproc.vwAlberoproc.scDescrizione", "batchscadenzario.label.tipologia_intervento", false, false, true,
		"", "");
	columnJmesa.addBaseColumn("movimentofatto.movimento", "batchscadenzario.label.movimento_fatto", false, false, true, "", "");
	columnJmesa.addLinkColumn("movimentoDaFare.tipomovimento.movimento", dettaglioMovimentoDaFareLink,
		"batchscadenzario.label.tipo_movimento_da_fare", "");
	columnJmesa.addBaseColumn("endoprocedimento.procedimento", "batchscadenzario.label.endoprocedimento", false, false, true, "", "");
	columnJmesa.addDataBaseColumn("dataelaborazione", "batchscadenzario.label.data_registrazione", WebConstants.DATE_FORMAT_PATTERN, false,
		false, true, "", "");
	columnJmesa.addDataBaseColumn("datascadenza", "batchscadenzario.label.data_scadenza", WebConstants.DATE_FORMAT_PATTERN, false, false, true,
		"", "");
    }

    @Override
    protected Collection<?> setItems() {

	int count = batchScadenzarioService.countByFilter(batchScadenzarioFilter);
	this.countedRecords = count;
	getFacade().setTotalRows(count);
	getFacade().setMaxRowsIncrements(10, 100);
	List<BatchScadenzario> list = new ArrayList<BatchScadenzario>();
	if (count > 0) {
	    list = batchScadenzarioService.findByFilter(batchScadenzarioFilter, getStartRowPage(), getEndRowPage());
	}
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle istanze");
	int count = batchScadenzarioService.countByFilter(batchScadenzarioFilter);
	List<BatchScadenzario> listScadenze = new ArrayList<BatchScadenzario>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "codiceistanza");
	colonne.add(1, "numeroistanza");
	colonne.add(2, "modulo");
	colonne.add(3, "stato");
	colonne.add(4, "richiedente");
	colonne.add(5, "intervento");
	colonne.add(6, "movimentofatto");
	colonne.add(7, "movimentodaeffettuare");
	colonne.add(8, "endoprocedimento");
	colonne.add(9, "dataregistrazione");
	colonne.add(10, "datascadenza");
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
		batchScadenzarioService.clear();
		listScadenze = batchScadenzarioService.findByFilter(batchScadenzarioFilter, startRow, rowEnd);
		for (BatchScadenzario scadenza : listScadenze) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(scadenza.getIstanza().getId().getCodice());
		    riga[1] = scadenza.getIstanza().getNumeroistanza();
		    riga[2] = scadenza.getIstanza().getSoftware().getDescrizione();
		    riga[3] = scadenza.getIstanza().getChiusura().getStato();
		    riga[4] = (String) EntityUtils.getNestedProperty(scadenza.getIstanza(), "transientRichiedenteQualitaAzienda");
		    riga[5] = (String) EntityUtils.getNestedProperty(scadenza.getIstanza().getAlberoproc(), "vwAlberoproc.scDescrizione");
		    riga[6] = (String) EntityUtils.getNestedProperty(scadenza.getMovimentofatto(), "movimento");
		    riga[7] = (String) EntityUtils.getNestedProperty(scadenza.getMovimentoDaFare(), "movimento");
		    riga[8] = (String) EntityUtils.getNestedProperty(scadenza.getMovimentoDaFare(), "endoprocedimento.procedimento");
		    riga[9] = Utilities.formatDate(scadenza.getDataelaborazione(), false);
		    riga[10] = Utilities.formatDate(scadenza.getDatascadenza(), false);
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
