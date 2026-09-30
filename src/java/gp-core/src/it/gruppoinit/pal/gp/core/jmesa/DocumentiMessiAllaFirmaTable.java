package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;

public class DocumentiMessiAllaFirmaTable extends GenerateTable<DocumentiDaFirmare> {

    private int countedRecords = 0;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private Responsabili utenteLoggato;
    private Boolean isMessiDallutenteLoggato;

    public DocumentiMessiAllaFirmaTable(Responsabili utenteLoggato, Boolean isMessiDallutenteLoggato,
	    DocumentiDaFirmareService documentiDaFirmareService, OggettiMetadatiService oggettiMetadatiService,
	    ResponsabiliService responsabiliService) {

	this.utenteLoggato = utenteLoggato;
	this.documentiDaFirmareService = documentiDaFirmareService;
	this.isMessiDallutenteLoggato = isMessiDallutenteLoggato;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	String uriBack = "/documentidafirmare/documentiMessiAllaFirmaList.htm";
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addDataBaseColumn("dataRichiesta", "label.data", WebConstants.DATE_FORMAT_PATTERN, false, false, true, "", "7%");
	columnJmesa.addLinkBaseColumn(request, "istanze.numeroistanza", "label.numero_pratica", "istanze.id.codice", "../istanze/view.htm?codice=",
		uriBack, false, false, true, null, "2%");
	columnJmesa.addLinkBaseColumn(request, "movimentiallegati.movimento.movimento", "label.tipimovimento",
		"movimentiallegati.movimento.id.codice", "../movimenti/view.htm?codice=", uriBack, false, false, true, null, "20%");
	columnJmesa.addBaseColumn("movimentiallegati.movimento.numeroprotocollo", "label.numero_protocollo", false, false, true, "", "");
	columnJmesa.addDataBaseColumn("movimentiallegati.movimento.dataprotocollo", "label.data_protocollo", WebConstants.DATE_FORMAT_PATTERN, false,
		false, true, "", "7%");
	columnJmesa.addBaseColumn("oggetti.nomefile", "info.label.oggetti", false, false, true, "", "15");
	columnJmesa.addBaseColumn("richiedente.responsabile", "documentidafirmare.label.op_richiedente", false, false, true, "", "15%");
	columnJmesa.addBaseColumn("firmatario.responsabile", "label.firmatario", false, false, true, "", "15%");
	columnJmesa.addBaseColumn("annotazioniRichiedente", "label.note", false, false, true, "", "30%");
	Map<String, String> mappaLabelValore = new HashMap<String, String>();
	mappaLabelValore.put("list.jmesa.celleditor.firma_richiesta", StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name());
	mappaLabelValore.put("list.jmesa.celleditor.firma_completata", StatiDocumentiDaFirmare.FIRMA_COMPLETA.name());
	mappaLabelValore.put("list.jmesa.celleditor.firma_negata", StatiDocumentiDaFirmare.FIRMA_NEGATA.name());
	columnJmesa.addStringCostunColumn("flagDaFirmare", "label.da.firmare", mappaLabelValore, true, true, true, null, "6%");
    }

    @Override
    protected Collection<?> setItems() {

	DocumentiDaFirmare documentiDaFirmare = new DocumentiDaFirmare();
	documentiDaFirmare = getFilterQuery(documentiDaFirmare);
	String flagDaFirmare = "";
	if (StringUtils.isNotBlank(documentiDaFirmare.getFlagDaFirmare())) {
	    flagDaFirmare = documentiDaFirmare.getFlagDaFirmare();
	}
	int count = documentiDaFirmareService.countDocumentiDaFirmareDTOPerRichiedenteAndStato(this.getUtenteLoggato().getId().getCodice(),
		isMessiDallutenteLoggato, flagDaFirmare);
	getFacade().setTotalRows(count);
	this.countedRecords = count;
	List<DocumentiDaFirmare> list = new ArrayList<DocumentiDaFirmare>();
	if (count > 0) {
	    list = documentiDaFirmareService.findDocumentiDaFirmareDTOPerRichiedenteAndStato(this.getUtenteLoggato().getId().getCodice(),
		    isMessiDallutenteLoggato, flagDaFirmare, getStartRowPage(), getEndRowPage());
	}
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista documenti da firmare");
	List<DocumentiDaFirmare> list = documentiDaFirmareService.findDocumentiDaFirmareDTOPerRichiedenteAndStato(
		this.getUtenteLoggato().getId().getCodice(), isMessiDallutenteLoggato, "", null, null);
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "dataRichiesta");
	colonne.add(1, "numeroistanza");
	colonne.add(2, "movimento");
	colonne.add(3, "nomefile");
	colonne.add(4, "messoAllaFirmaDa");
	colonne.add(5, "annotazioniRichiedente");
	result.setColonne(colonne);
	List<String[]> righe = new ArrayList<String[]>();
	int c = 0;
	for (DocumentiDaFirmare documentiDaFirmare : list) {
	    String[] riga = new String[colonne.size()];
	    riga[0] = Utilities.formatDate(documentiDaFirmare.getDataRichiesta(), false);
	    riga[1] = documentiDaFirmare.getIstanze().getNumeroistanza();
	    riga[2] = documentiDaFirmare.getMovimentiallegati().getMovimento().getMovimento();
	    riga[3] = documentiDaFirmare.getOggetti().getNomefile();
	    riga[4] = documentiDaFirmare.getRichiedente().getResponsabile();
	    riga[5] = documentiDaFirmare.getAnnotazioniRichiedente();
	    righe.add(c, riga);
	    c++;
	}
	result.setRighe(righe);
	return result;
    }

    public DocumentiDaFirmareService getDocumentiDaFirmareService() {

	return documentiDaFirmareService;
    }

    public void setDocumentiDaFirmareService(DocumentiDaFirmareService documentiDaFirmareService) {

	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    public int getCountedRecords() {

	return countedRecords;
    }

    public Responsabili getUtenteLoggato() {

	return utenteLoggato;
    }

    public void setUtenteLoggato(Responsabili utenteLoggato) {

	this.utenteLoggato = utenteLoggato;
    }
}
