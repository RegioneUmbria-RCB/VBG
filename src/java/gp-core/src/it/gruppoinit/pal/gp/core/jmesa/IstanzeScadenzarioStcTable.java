package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.DomandeSTCScadenzarioDTO;
import it.gruppoinit.pal.gp.core.domain.web.DomandeStcFilter;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.NodoStcDecodeCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.GenerateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

public class IstanzeScadenzarioStcTable extends GenerateTable<DomandeSTCScadenzarioDTO> {

    Logger log = LoggerFactory.getLogger(IstanzeScadenzarioStcTable.class);
    private Responsabili utenteLoggato;
    private UserSecurityService userSecurityService;
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    private ConfigurazioneutenteService configurazioneutenteService;
    private boolean isPerOperatore;
    private int countedRecords;
    private boolean isImportate;

    public int getCountedRecords() {

	return countedRecords;
    }

    public IstanzeScadenzarioStcTable(Responsabili utenteLoggato, UserSecurityService userSecurityService,
	    VerticalizzazioniparametriService verticalizzazioniparametriService, ConfigurazioneutenteService configurazioneutenteService,
	    boolean isPerOperatore, boolean isImportate) {

	this.utenteLoggato = utenteLoggato;
	this.userSecurityService = userSecurityService;
	this.isPerOperatore = isPerOperatore;
	this.isImportate = isImportate;
	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
	this.configurazioneutenteService = configurazioneutenteService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	String uriBack = "";
	if (isPerOperatore) {
	    uriBack = "/";
	} else {
	    if (this.isImportate) {
		//uriBack = "../batchscadenzario/list.htm?tab=" + WebConstants.TAB_ISTANZE_STC;
		uriBack = "/batchscadenzario/listPerOperatore.htm?tab=" + WebConstants.TAB_ISTANZE_STC + "%2526software=TT";
	    } else {
		//uriBack = "../batchscadenzario/list.htm?tab=" + WebConstants.TAB_ISTANZE_STC_NON_IMPORTATE;
		uriBack = "/batchscadenzario/listPerOperatore.htm?tab=" + WebConstants.TAB_ISTANZE_STC_NON_IMPORTATE + "%2526software=TT";
		;
	    }
	}
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	if (this.isImportate) {
	    JMesaTableLinkHelper dettaglioIstanzaLink = new JMesaTableLinkHelper("numeroistanza", uriBack, LinkTargetEnum.ISTANZE_STC_SCADENZARIO);
	    columnJmesa.addLinkColumn("numeroistanza", dettaglioIstanzaLink, "label.numeroistanza", "");
	    String visualizzaModulo = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_SOFTWARE,
		    "0");
	    if (visualizzaModulo.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("descrizioneSoftware", "label.modulo", false, false, true, null, "");
	    }
	    String visualizzaDataIstanza = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		    WebConstants.CONF_UTENTE_SCAD_COLONNA_DATA_ISTANZA, "0");
	    if (visualizzaDataIstanza.equalsIgnoreCase("1")) {
		columnJmesa.addDataBaseColumn("data", "label.data_presentazione", WebConstants.DATE_FORMAT_PATTERN, false, true, true, null, "10%");
	    }
	    columnJmesa.addBaseColumn("numeroprotocollo", "label.numero_protocollo", false, false, true, "", "");
	    columnJmesa.addDataBaseColumn("dataprotocollo", "label.data_protocollo", WebConstants.DATE_FORMAT_PATTERN, false, false, true, null,
		    "10%");
	    String visualizzaRichiedente = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		    WebConstants.CONF_UTENTE_SCAD_COLONNA_RICHIEDENTE, "0");
	    if (visualizzaRichiedente.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("descrizioneRichiedente", "label.richiedente", false, false, true, null, "");
	    }
	    String visualizzaIntervento = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		    WebConstants.CONF_UTENTE_SCAD_COLONNA_INTERVENTO, "0");
	    if (visualizzaIntervento.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("scDescrizione", "label.alberoproc", false, false, true, "", "");
	    }
	    //. procedura
	    String visualizzaProcedura = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		    WebConstants.CONF_UTENTE_SCAD_COLONNA_PROCEDURA, "0");
	    if (visualizzaProcedura.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("procedura", "label.procedura", false, false, true, "", "");
	    }
	    //. Posizione Archivio
	    String visualizzaPosArch = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		    WebConstants.CONF_UTENTE_SCAD_COLONNA_POS_ARCHIVIO, "0");
	    if (visualizzaPosArch.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("posizionearchivio", "label.posizione_archivio", false, false, true, "", "");
	    }
	    String visualizzaStato = configurazioneutenteService.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_STATO, "0");
	    if (visualizzaStato.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("stato", "label.stato_istanza", false, false, true, null, "");
	    }
	    columnJmesa.addBaseColumn("comune", "label.comune", false, false, true, null, "");
	    String visualizzaResponsabile = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		    WebConstants.CONF_UTENTE_SCAD_COLONNA_OPERATORE, "0");
	    if (visualizzaResponsabile.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("responsabiledescrizione", "label.operatore", false, false, true, "", "");
	    }
	    // ISTRUTTORE
	    String visualizzaIstruttore = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		    WebConstants.CONF_UTENTE_SCAD_COLONNA_ISTRUTTORE, "0");
	    if (visualizzaIstruttore.equalsIgnoreCase("1")) {
		columnJmesa.addBaseColumn("istruttore", "label.responsabile_istruttoria", false, false, true, "", "");
	    }
	    String visualizzaTermineProcedimento = configurazioneutenteService.leggiParametroConfigurazioneUtente(
		    WebConstants.CONF_UTENTE_SCAD_COLONNA_TERMINE_PROCEDIMENTO, "0");
	    if (visualizzaTermineProcedimento.equalsIgnoreCase("1")) {
		columnJmesa.addDataBaseColumn("termineprocedimento", "label.termine_del_procedimento", WebConstants.DATE_FORMAT_PATTERN, false, true,
			true, null, "10%");
	    }
	} else {
	    String customIstanzeLink = "../domandestc/list.htm?software=<codSoftware>";
	    IJMesaLinkHelper dettaglioIstanzaLink = new JMesaCustomLinkHelper(customIstanzeLink, uriBack);
	    columnJmesa.addLinkColumn("numeroistanza", dettaglioIstanzaLink, "label.numeroistanza", false, false, true, "2%");
	    //	    String visualizzaRichiedente = configurazioneutenteService.leggiParametroConfigurazioneUtente(
	    //		    WebConstants.CONF_UTENTE_SCAD_COLONNA_RICHIEDENTE, "0");
	    //	    if (visualizzaRichiedente.equalsIgnoreCase("1")) {
	    columnJmesa.addBaseColumn("nominativo", "label.richiedente", false, false, true, null, "");
	    //	    }
	    columnJmesa.addBaseColumn("mittenteDomanda", "label.domande_mittente", false, false, true, "", "");
	    columnJmesa.addBaseColumn("ultimoerrore", "label.errore", false, false, true, "", "");
	}
	//columnJmesa.addBaseColumn("comune", "label.comune", false, false, true, null, "");
	//columnJmesa.addBaseColumn("descrizioneSoftware", "label.software", false, false, true, null, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "codiceModuloAndIdMittente", "label.nodo", "", new NodoStcDecodeCellEditor(
		verticalizzazioniparametriService), false, false, true, null, "");
	//columnJmesa.addBaseColumn("nodo", "label.nodo", false, false, true, "", "");
    }

    @Override
    protected Collection<DomandeSTCScadenzarioDTO> setItems() {

	DomandeStcFilter domandeStcFilter = new DomandeStcFilter();
	// Controllo se ho ordinato per data istanza
	if (getFacade().getLimit().getSortSet().getSort("data") != null) {
	    domandeStcFilter.setOrdineDataistanza(getFacade().getLimit().getSortSet().getSort("data").getOrder().toParam());
	    domandeStcFilter.setOrdineImpostato(true);
	}
	DomandestcService domandestcService = (DomandestcService) ContextLoader.getCurrentWebApplicationContext().getBean("domandestcServiceImpl",
		DomandestcService.class);
	String codiceSoftwareScad = "";
	if (utenteLoggato.getScadSoftware() != null && StringUtils.isNotBlank(utenteLoggato.getScadSoftware().getCodice())) {
	    codiceSoftwareScad = utenteLoggato.getScadSoftware().getCodice();
	}
	DomandeSTCScadenzarioDTO domandeSTCScadenzarioDTO = new DomandeSTCScadenzarioDTO();
	domandeSTCScadenzarioDTO = getFilterQuery(domandeSTCScadenzarioDTO);
	domandeStcFilter.setDomandeSTCScadenzarioDTO(domandeSTCScadenzarioDTO);
	int count = domandestcService.countScadenzarioDomandePervenuteSTC(isImportate, codiceSoftwareScad, domandeStcFilter);
	this.countedRecords = count;
	getFacade().setTotalRows(count);
	List<DomandeSTCScadenzarioDTO> list = new ArrayList<DomandeSTCScadenzarioDTO>();
	if (count > 0) {
	    list = domandestcService.findDomandePervenuteSTC(domandeStcFilter, isImportate, codiceSoftwareScad, getStartRowPage(), getEndRowPage());
	}
	return list;
    }
}
