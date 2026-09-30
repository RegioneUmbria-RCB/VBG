package it.gruppoinit.pal.gp.core.jmesa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkTrasformaInSpuntistaPerMercati;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.domain.VwConcessionilista;
import it.gruppoinit.pal.gp.core.domain.VwConcessionlistaId;
import it.gruppoinit.pal.gp.core.domain.helper.ConcessioniListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.FactoryConcessioniListHelperComparator;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.VwConcessionilistaService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class VwConcessionilistaTable extends GenerateTable<VwConcessionilista> {

    private VwConcessionilista vwConcessionilista;
    private Integer funzionalitaRicercaConcessioni;
    private Integer codiceIstanza;
    private static final Logger log = LoggerFactory.getLogger(VwConcessionilistaTable.class);

    public VwConcessionilistaTable(VwConcessionilista vwConcessionilista, Integer funzionalitaRicercaConcessioni, Integer codiceIstanza) {

	super();
	this.vwConcessionilista = vwConcessionilista;
	this.codiceIstanza = codiceIstanza;
	this.funzionalitaRicercaConcessioni = funzionalitaRicercaConcessioni;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "concDatarilascio"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "concDatascadenza"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	//String uriBack = "/vwconcessionilista/search.htm";
	StringBuffer parametriHistoryBack = new StringBuffer("%3FcodiceIstanza%3D");
	if (codiceIstanza != null) {
	    parametriHistoryBack.append(codiceIstanza);
	}
	parametriHistoryBack.append("%26").append("modalita_ricerca%3D")
		.append(funzionalitaRicercaConcessioni != null ? funzionalitaRicercaConcessioni : WebConstants.SEARCH_AUT_DEFAULT);
	String uriBack = "/vwconcessionilista/search.htm".concat(parametriHistoryBack.toString());
	JMesaTableLinkHelper dettaglioIstanzaLink = new JMesaTableLinkHelper("", uriBack, LinkTargetEnum.DETTAGLIO_ISTANZA_VWCONCESSIONI);
	JMesaTableLinkHelper dettaglioAnagrafeLink = new JMesaTableLinkHelper("", uriBack, LinkTargetEnum.DETTAGLIO_ANAGRAFE_VWCONCESSIONI);
	JMesaTableLinkHelper dettaglioConcessioneLink = new JMesaTableLinkHelper("", uriBack, LinkTargetEnum.DETTAGLIO_CONCESSIONI);
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkColumn("conc_numero", dettaglioConcessioneLink, "form.vwconcessionilista.concNumero", false, false, false, "");
	columnJmesa.addDataBaseColumn("conc_datavalidita", "label.concessione_data_validita_concessione", WebConstants.DATE_FORMAT_PATTERN, false,
		false, false, null, "8%");
	columnJmesa.addDataBaseColumn("conc_datarilascio", "form.vwconcessionilista.concDatarilascio", WebConstants.DATE_FORMAT_PATTERN, false, false,
		false, null, "8%");
	if (funzionalitaRicercaConcessioni == WebConstants.SEARCH_AUT_DEFAULT) {
	    columnJmesa.addLinkColumn("ist_numeroistanza", dettaglioIstanzaLink, "form.vwconcessionilista.istNumeroistanza", false, false, false, "");
	    columnJmesa.addLinkColumn("conc_titolare", dettaglioAnagrafeLink, "form.vwconcessionilista.nominativo", false, false, false, "");
	    columnJmesa.addBaseColumn("conc_occupante", "mercatid.label.occupante", false, false, false, null, "");
	}
	columnJmesa.addBaseColumn("conc_mercato", "form.vwConcessionilista.mercati", false, false, false, null, "");
	columnJmesa.addBaseColumn("conc_descrizioneuso", "form.vwConcessionilista.mercatiUso", false, false, false, null, "");
	columnJmesa.addBaseColumn("conc_posteggio", "form.vwconcessionilista.posteggio", false, false, false, null, "");
	columnJmesa.addBaseColumn("iconc_causale", "form.vwconcessionilista.iconcCausale", false, false, false, null, "");
	columnJmesa.addDataBaseColumn("conc_datascadenza", "form.vwconcessionilista.concDatascadenza", WebConstants.DATE_FORMAT_PATTERN, false, false,
		false, null, "8%");
	columnJmesa.addBooleanColumn("conc_attiva", "form.vwconcessionilista.concAttiva", false, false, false, null, "");
	columnJmesa.addDataBaseColumn("data_storico", "label.data_cessazione", WebConstants.DATE_FORMAT_PATTERN, false, false, false, null, "8%");
	if (WebConstants.SEARCH_CONC_PER_GESTIONE_SPUNTA.equals(funzionalitaRicercaConcessioni)) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", "label.azione", "",
		    new LinkTrasformaInSpuntistaPerMercati(codiceIstanza, "conc_id", request), false, false, false, "", "5%");
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Collection<?> setItems() {

	List<ConcessioniListHelper> list = new ArrayList<ConcessioniListHelper>();
	VwConcessionilistaService vwConcessionilistaService = (VwConcessionilistaService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("vwConcessionilistaServiceImpl", VwConcessionilistaService.class);
	long t1 = System.currentTimeMillis();
	if (BooleanUtils.toBoolean(vwConcessionilista.isConcAttiva() && vwConcessionilista.getAttiveAllaDataTransient() != null)) {
	    //1. Count con filtri passati  + Distinc(CONC_ID,IDCOMUNE): Ritorna il numero atteso di tutti i recordo collassando quelli con stesso conc_id e idcomune
	    log.debug(
		    "setItems# Recupero il valore della count collassando i record con stesso conc_id e idcomune [COUNT(DISTINC(CONC_ID,IDCOMUNE))]");
	    int count = vwConcessionilistaService.countConcessioniListHelper(vwConcessionilista, true);
	    log.debug("setItems# Risultato : {}", count);
	    //2. Settare la il total row
	    getFacade().setTotalRows(count);
	    //3. Ricercare per i filtri con select Distinc(CONC_ID,IDCOMUNE) utilizzando la total row
	    // I PASSI 1,2,3 SERVONO PER MANTENERE LA PAGINAZIONE CORRETTA DI JMESA
	    log.debug("setItems# Recupero i record con stesso conc_id e idcomune [SELECT(DISTINC(CONC_ID,IDCOMUNE))]");
	    log.debug("setItems# Risultato : {}");
	    List<ConcessioniListHelper> listTemp = vwConcessionilistaService.findConcessioniListHelper(vwConcessionilista, getStartRowPage(),
		    getEndRowPage(), true);
	    log.debug("setItems# Risultato : {}", list.size());
	    //4. Creo la lista di record da mostrare in visualizzazione: Per ogni record applico un filtro per conc_id,idcomune e  attiveAllaDataTransient
	    //( per la data applico la stessa logica di filtraggio applicata nelle due query precedenti)  ordinando per conc_id DESC e data rilascio DESC
	    // prendo solo il primo valore e lo metto sulla lista di ritorno
	    VwConcessionilista filterTemp = null;
	    VwConcessionlistaId id = null;
	    // QUALSIASI MODIFICA AI FILTRI DEVE ESSERE RIPORTATA IN VWCONCESSIONILISTADAOIMPL.exportModalitaPentaho
	    for (ConcessioniListHelper concessioniListHelper : listTemp) {
		filterTemp = new VwConcessionilista();
		id = new VwConcessionlistaId();
		id.setIdcomune(concessioniListHelper.getIdcomune());
		filterTemp.setId(id);
		filterTemp.setConcId(concessioniListHelper.getConc_id().intValue());
		filterTemp.setConcAttiva(true);
		filterTemp.setAttiveAllaDataTransient(vwConcessionilista.getAttiveAllaDataTransient());
		//5. Popolare la lista da ritornare 
		List<ConcessioniListHelper> tempList = vwConcessionilistaService.findConcessioniListHelper(filterTemp, null, null,
			TipoQueryHelperEnum.SELECT_WHERE_CONC_ID);
		if (!tempList.isEmpty()) {
		    list.add(tempList.get(0));
		}
	    }
	    //6. APPLICO L'ORDINAMENTO
	    DAOOrderTypeEnum orderType = convertiEnumerationOrdinamento(vwConcessionilista.getOrderAscDesc());
	    FactoryConcessioniListHelperComparator factoryConcessioniListHelperComparator = new FactoryConcessioniListHelperComparator(
		    vwConcessionilista.getOrderBy(), orderType);
	    Collections.sort(list, factoryConcessioniListHelperComparator.createConcessioniListHelperComparator());
	} else {
	    int count = vwConcessionilistaService.countConcessioniListHelper(vwConcessionilista);
	    getFacade().setTotalRows(count);
	    list = vwConcessionilistaService.findConcessioniListHelper(vwConcessionilista, getStartRowPage(), getEndRowPage());
	}
	long t2 = System.currentTimeMillis();
	String time = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(t2 - t1),
		TimeUnit.MILLISECONDS.toSeconds(t2 - t1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(t2 - t1)));
	log.debug("setItems# Processo di ricerca servito in {}", time);
	return list;
    }

    private DAOOrderTypeEnum convertiEnumerationOrdinamento(OrderTypeEnum orderTypeEnum) {

	switch (orderTypeEnum) {
	case ASC:
	    return DAOOrderTypeEnum.ASC;
	case DESC:
	    return DAOOrderTypeEnum.DESC;
	default:
	    return null;
	}
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle concessioni");
	VwConcessionilistaService vwConcessionilistaService = (VwConcessionilistaService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("vwConcessionilistaServiceImpl", VwConcessionilistaService.class);
	int count = 0;
	if (BooleanUtils.toBoolean(vwConcessionilista.isConcAttiva() && vwConcessionilista.getAttiveAllaDataTransient() != null)) {
	    log.debug("generateTableHelper# Count per export jmesa modalità : RICERCA ATTIVE ALLA DATA {}",
		    vwConcessionilista.getAttiveAllaDataTransient());
	    count = vwConcessionilistaService.countConcessioniListHelper(vwConcessionilista, true);
	    getFacade().setTotalRows(count);
	} else {
	    log.debug("generateTableHelper# Count per export jmesa modalità : RICERCA DEFAULT", vwConcessionilista.getAttiveAllaDataTransient());
	    count = vwConcessionilistaService.countConcessioniListHelper(vwConcessionilista);
	}
	List<ConcessioniListHelper> listConcessioni = null;
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "Numero");
	colonne.add(1, "Data rilascio");
	colonne.add(2, "Istanza");
	colonne.add(3, "Titolare");
	colonne.add(4, "Occupante");
	colonne.add(5, "Manifestazione");
	colonne.add(6, "Giorno");
	colonne.add(7, "Posteggio");
	colonne.add(8, "Causale concessione");
	colonne.add(9, "Data scadenza");
	colonne.add(10, "Attiva");
	colonne.add(11, "data cessazione");
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
		vwConcessionilistaService.clear();
		listConcessioni = new ArrayList<ConcessioniListHelper>();
		if (BooleanUtils.toBoolean(vwConcessionilista.isConcAttiva() && vwConcessionilista.getAttiveAllaDataTransient() != null)) {
		    log.debug("generateTableHelper# SELECT per export jmesa modalità : RICERCA ATTIVE ALLA DATA {}. Dal record {} al record {}",
			    new Object[] { vwConcessionilista.getAttiveAllaDataTransient(), startRow, rowEnd });
		    log.debug("generateTableHelper# Recupero i record con stesso conc_id e idcomune [SELECT(DISTINC(CONC_ID,IDCOMUNE))]");
		    List<ConcessioniListHelper> listTemp = vwConcessionilistaService.findConcessioniListHelper(vwConcessionilista, startRow, rowEnd,
			    true);
		    //4. Creo la lista di record da mostrare in visualizzazione: Per ogni record applico un filtro per conc_id,idcomune e  attiveAllaDataTransient
		    //( per la data applico la stessa logica di filtraggio applicata nelle due query precedenti)  ordinando per conc_id DESC e data rilascio DESC
		    // prendo solo il primo valore e lo metto sulla lista di ritorno
		    VwConcessionilista filterTemp = null;
		    VwConcessionlistaId id = null;
		    for (ConcessioniListHelper concessioniListHelper : listTemp) {
			filterTemp = new VwConcessionilista();
			id = new VwConcessionlistaId();
			id.setIdcomune(concessioniListHelper.getIdcomune());
			filterTemp.setId(id);
			filterTemp.setConcId(concessioniListHelper.getConc_id().intValue());
			filterTemp.setConcAttiva(true);
			filterTemp.setAttiveAllaDataTransient(vwConcessionilista.getAttiveAllaDataTransient());
			//5. Popolare la lista da ritornare 
			List<ConcessioniListHelper> tempList = vwConcessionilistaService.findConcessioniListHelper(filterTemp, null, null,
				TipoQueryHelperEnum.SELECT_WHERE_CONC_ID);
			if (!tempList.isEmpty()) {
			    listConcessioni.add(tempList.get(0));
			}
		    }
		    //6. APPLICO L'ORDINAMENTO
		    DAOOrderTypeEnum orderType = convertiEnumerationOrdinamento(vwConcessionilista.getOrderAscDesc());
		    FactoryConcessioniListHelperComparator factoryConcessioniListHelperComparator = new FactoryConcessioniListHelperComparator(
			    vwConcessionilista.getOrderBy(), orderType);
		    log.debug("generateTableHelper# Applico ordinamento tramite classe JAVA");
		    Collections.sort(listConcessioni, factoryConcessioniListHelperComparator.createConcessioniListHelperComparator());
		} else {
		    log.debug("generateTableHelper# SELECT per export jmesa modalità : RICERCA DEFAULT. Dal record {} al record {}", startRow,
			    rowEnd);
		    listConcessioni = vwConcessionilistaService.findConcessioniListHelper(vwConcessionilista, startRow, rowEnd);
		}
		for (ConcessioniListHelper concessioniListHelper : listConcessioni) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(concessioniListHelper.getConc_numero());
		    riga[1] = Utilities.formatDate(concessioniListHelper.getConc_datarilascio(), false);
		    riga[2] = StringUtils.defaultIfEmpty(concessioniListHelper.getIst_numeroistanza(), "");
		    riga[3] = StringUtils.defaultIfEmpty(concessioniListHelper.getConc_titolare(), "");
		    riga[4] = StringUtils.defaultIfEmpty(concessioniListHelper.getConc_occupante(), "");
		    riga[5] = StringUtils.defaultIfEmpty(concessioniListHelper.getConc_mercato(), "");
		    riga[6] = StringUtils.defaultIfEmpty(concessioniListHelper.getConc_descrizioneuso(), "");
		    riga[7] = StringUtils.defaultIfEmpty(concessioniListHelper.getConc_posteggio(), "");
		    riga[8] = StringUtils.defaultIfEmpty(concessioniListHelper.getIconc_causale(), "");
		    riga[9] = Utilities.formatDate(concessioniListHelper.getConc_datascadenza(), false);
		    if (concessioniListHelper.getConc_attiva() == true) {
			riga[10] = "si";
			riga[11] = "";
		    } else {
			riga[10] = "no";
			riga[11] = Utilities.formatDate(concessioniListHelper.getData_storico(), false);
		    }
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
