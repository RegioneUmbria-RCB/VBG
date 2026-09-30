package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaListHelper;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkAltriIndirizziHelperCellEditor;
import org.jmesa.customColumn.LinkAutorizzazioniIstanzeAttivitaCellEditor;
import org.jmesa.customColumn.LinkDettaglioIattivita;
import org.jmesa.customColumn.LinkSchededinamicheCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

public class IAttivitaHelperTable extends GenerateTable<IAttivitaListHelper> {

    private IAttivitaFilter filter;
    private Boolean isFunzioneDiUtility;
    private Integer conteggioAttivita = 0;
    // Popolato solo se la ricerca riporta una solo attività
    private Integer codiceAttivita;

    public IAttivitaHelperTable(IAttivitaFilter filter, Boolean isFunzioneDiUtility) {

	super();
	this.filter = filter;
	this.isFunzioneDiUtility = isFunzioneDiUtility;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "dataprotocollo"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	// Alla classe custom LinkDettaglioIattivita viene passato un parametro boleano 
	// false : è un link riporta la label con il codice
	if (!isFunzioneDiUtility) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "id", "label.azione", "", new LinkDettaglioIattivita(false), false, false, false, "",
		    "5%");
	} else {
	    columnJmesa.addBaseColumn("id", "label.codice", false, false, false, null, "");
	}
	columnJmesa.addBaseColumn("denominazione", "label.denominazione_dell_attivita", false, false, false, null, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "countschede", "label.s", "label.s", new LinkSchededinamicheCellEditor(request), false,
		false, false, "", "");
	columnJmesa.addBaseColumn("tipologiaattivita", "label.tipologia_attivita", false, false, false, null, "");
	columnJmesa.addBaseColumn("numeroistanza", "label.numeroistanza", false, false, false, null, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "sofware.ordine", "label.A", "label.A", new LinkAutorizzazioniIstanzeAttivitaCellEditor(
		request), false, false, false, "label.colonna_schede_dinamicge_help", "2%");
	columnJmesa.addBaseColumn("transientDescrizioneRichiedenteQualitaAzienda", "label.richiedente", false, false, false, null, "");
	columnJmesa.addBaseColumn("transientDescrizioneRichiedenteAziendaStorico", "label.richiedente_storico", false, false, false, null, "");
	columnJmesa.addBaseColumn("posizionearchivio", "label.posizione_archivio", false, false, false, null, "");
	columnJmesa.addBaseColumn("transientDescrizioneLocalizzazione", "label.localizzazione", false, false, true, null, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "countstradari", "label.I", "label.I", new LinkAltriIndirizziHelperCellEditor(), false,
		false, false, "label.colonna_altri_indirizzi_help", "2%");
	columnJmesa.addBaseColumn("interventoproc", "label.alberoproc", false, false, false, null, "");
	columnJmesa.addBooleanColumn("attiva", "label.attiva", false, false, false, null, "4%");
	columnJmesa.addBooleanColumn("operante", "label.operante", false, false, false, null, "4%");
	// Alla classe custom LinkDettaglioIattivita viene passato un parametro boleano 
	// true : è un link riporta l'icona di dettaglio, non deve essere mostrato nel caso si stai utilizzando
	// la funzionalità di utility per collegare una scheda ad una lista di attivita.
	if (!isFunzioneDiUtility) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "id", "label.edit.record", "", new LinkDettaglioIattivita(true), false, false, false,
		    "", "5%");
	}
    }

    @Override
    protected Collection<IAttivitaListHelper> setItems() {

	int count = 10;
	IAttivitaService iattivitaService = (IAttivitaService) ContextLoader.getCurrentWebApplicationContext().getBean("IAttivitaServiceImpl",
		IAttivitaService.class);
	count = iattivitaService.countIAttivitaListHelperByFilter(filter);
	getFacade().setTotalRows(count);
	this.conteggioAttivita = count;
	List<IAttivitaListHelper> listAttivita = new ArrayList<IAttivitaListHelper>();
	if (count > 0) {
	    listAttivita = iattivitaService.findIAttivitaListHelperByFilter(filter, getStartRowPage(), getEndRowPage());
	}
	if (count == 1) {
	    this.codiceAttivita = listAttivita.get(0).getId().intValue();
	}
	return listAttivita;
    }

    public Integer getConteggioAttivita() {

	return conteggioAttivita;
    }

    public Integer getCodiceAttivita() {

	return codiceAttivita;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle attività");
	IAttivitaService iattivitaService = (IAttivitaService) ContextLoader.getCurrentWebApplicationContext().getBean("IAttivitaServiceImpl",
		IAttivitaService.class);
	// int count = istanzeService.countByFilter(filter);
	int count = iattivitaService.countIAttivitaListHelperByFilter(filter);
	List<IAttivitaListHelper> listattivita = new ArrayList<IAttivitaListHelper>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "id");
	colonne.add(1, "denominazione");
	colonne.add(2, "attiva");
	colonne.add(3, "operante");
	colonne.add(4, "numeroistanza");
	colonne.add(5, "richiedente");
	colonne.add(6, "emailRichiedente");
	colonne.add(7, "pecRichiedente");
	colonne.add(8, "inqualitadi");
	colonne.add(9, "aziendarappresentata");
	colonne.add(10, "archiviopratiche");
	colonne.add(11, "posizionearchivio");
	colonne.add(12, "tipologiaistanza");
	colonne.add(13, "datapresentazione");
	colonne.add(14, "numeroprotocollo");
	colonne.add(15, "dataprotocollo");
	colonne.add(16, "procedimento");
	colonne.add(17, "responsabileprocedimento");
	colonne.add(18, "localizzazioneprimaria");
	colonne.add(19, "descrizionelavori");
	colonne.add(20, "comune");
	colonne.add(21, "tipologiaattivita");
	colonne.add(22, "aziendacf");
	colonne.add(23, "aziendapiva");
	colonne.add(24, "aziendasedelegale");
	colonne.add(25, "emailAziendaRichiedente");
	colonne.add(26, "pecAziendaRichiedente");
	colonne.add(27, "domicilioElettronico");
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
		iattivitaService.clear();
		listattivita = iattivitaService.findIAttivitaListHelperByFilter(filter, startRow, rowEnd);
		for (IAttivitaListHelper attivita : listattivita) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(attivita.getId());
		    riga[1] = StringUtils.defaultIfEmpty(attivita.getDenominazione(), "");
		    riga[2] = attivita.getAttiva() == null ? "false" : String.valueOf(attivita.getAttiva());
		    riga[3] = attivita.getOperante() == null ? "false" : String.valueOf(attivita.getOperante());
		    riga[4] = StringUtils.defaultIfEmpty(attivita.getNumeroistanza(), "");
		    riga[5] = StringUtils.defaultIfEmpty(attivita.getRichiedentenominativo(), "") + " "
			    + StringUtils.defaultIfEmpty(attivita.getRichiedentenome(), "");
		    riga[6] = StringUtils.defaultIfEmpty(attivita.getEmailRichiedente(), "");
		    riga[7] = StringUtils.defaultIfEmpty(attivita.getPecRichiedente(), "");
		    riga[8] = StringUtils.defaultIfEmpty(attivita.getTiposoggetto(), "");
		    riga[9] = StringUtils.defaultIfEmpty(attivita.getAziendanominativo(), "");
		    if (StringUtils.isNotBlank(attivita.getAziendanome())) {
			riga[9] += " " + StringUtils.defaultIfEmpty(attivita.getAziendanome(), "");
		    }
		    riga[10] = StringUtils.defaultIfEmpty(attivita.getArchivio(), "");
		    riga[11] = StringUtils.defaultIfEmpty(attivita.getPosizionearchivio(), "");
		    riga[12] = StringUtils.defaultIfEmpty(attivita.getTipologiaistanza(), "");
		    if (attivita.getData() != null) {
			riga[13] = Utilities.formatDate(attivita.getData(), false);
		    } else {
			riga[13] = "";
		    }
		    riga[14] = StringUtils.defaultIfEmpty(attivita.getNumeroprotocollo(), "");
		    if (attivita.getDataprotocollo() != null) {
			riga[15] = Utilities.formatDate(attivita.getDataprotocollo(), false);
		    } else {
			riga[15] = "";
		    }
		    riga[16] = StringUtils.defaultIfEmpty(attivita.getInterventoproc(), "");
		    riga[17] = StringUtils.defaultIfEmpty(attivita.getResponsabileprocnome(), "");
		    riga[18] = attivita.getTransientDescrizioneLocalizzazione();
		    riga[19] = attivita.getOggettoistanza();
		    riga[20] = attivita.getComune();
		    riga[21] = attivita.getTipologiaattivita();
		    riga[22] = StringUtils.defaultIfEmpty(attivita.getAziendacodicefiscale(), "");
		    riga[23] = StringUtils.defaultIfEmpty(attivita.getAziendapartitaiva(), "");
		    riga[24] = StringUtils.defaultIfEmpty(attivita.getSedeLegaleAzienda(), "");
		    riga[25] = StringUtils.defaultIfEmpty(attivita.getEmailAziendaRichiedente(), "");
		    riga[26] = StringUtils.defaultIfEmpty(attivita.getPecAziendaRichiedente(), "");
		    riga[27] = StringUtils.defaultIfEmpty(attivita.getDomicilioElettronico(), "");
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
