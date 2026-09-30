package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkAzioniDocumentiDaFirmare;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;

public class DocumentiDaFirmareTable extends GenerateTable<DocumentiDaFirmare> {

    private int countedRecords = 0;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private OggettiMetadatiService oggettiMetadatiService;
    private Responsabili utenteLoggato;
    private ResponsabiliService responsabiliService;

    public DocumentiDaFirmareTable(Responsabili utenteLoggato, DocumentiDaFirmareService documentiDaFirmareService,
	    OggettiMetadatiService oggettiMetadatiService, ResponsabiliService responsabiliService) {

	this.utenteLoggato = utenteLoggato;
	this.documentiDaFirmareService = documentiDaFirmareService;
	this.oggettiMetadatiService = oggettiMetadatiService;
	this.responsabiliService = responsabiliService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	String uriBack = "";
	if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    uriBack = "../history/back.htm?GoTo=%2F";
	} else {
	    uriBack = "/documentidafirmare/documentiDaFirmareList.htm";
	}
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addDataBaseColumn("dataRichiesta", "label.data", WebConstants.DATE_FORMAT_PATTERN, false, false, true, "", "7%");
	JMesaTableLinkHelper dettaglioIstanzaLink = new JMesaTableLinkHelper("istanze.numeroistanza", uriBack,
		LinkTargetEnum.DOCUMENTI_DA_FIRMARE_ISTANZE);
	columnJmesa.addLinkColumn("istanze.numeroistanza", dettaglioIstanzaLink, "label.numero_pratica", "");
	JMesaTableLinkHelper dettaglioMovimentoDaFareLink = new JMesaTableLinkHelper("movimentiallegati.movimento.movimento", uriBack,
		LinkTargetEnum.DOCUMENTI_DA_FIRMARE_MOVIMENTI);
	columnJmesa.addLinkColumn("movimentiallegati.movimento.movimento", dettaglioMovimentoDaFareLink, "label.tipimovimento", "");
	columnJmesa.addBaseColumn("movimentiallegati.movimento.numeroprotocollo", "label.numero_protocollo", false, false, true, "", "");
	columnJmesa.addDataBaseColumn("movimentiallegati.movimento.dataprotocollo", "label.data_protocollo", WebConstants.DATE_FORMAT_PATTERN, false,
		false, true, "", "7%");
	columnJmesa.addBaseColumn("istanze.transientRichiedenteQualitaAzienda", "batchscadenzario.label.richiedente", false, false, true, "", "");
	columnJmesa.addBaseColumn("oggetti.nomefile", "info.label.oggetti", false, false, true, "", "15%");
	columnJmesa.addBaseColumn("richiedente.responsabile", "documentidafirmare.label.op_richiedente", false, false, true, "", "15%");
	columnJmesa.addBaseColumn("firmatario.responsabile", "label.firmatario", false, false, true, "", "15%");
	columnJmesa.addBaseColumn("annotazioniRichiedente", "label.note", false, false, true, "", "30%");
	final String label = getMessageFromBundle(getContext(), "label.seleziona_deseleziona_tutti", null);
	final String header = getMessageFromBundle(getContext(), "label.firma_i_documenti", null);
	HeaderEditor customHeaderEditor = new HeaderEditor() {

	    @Override
	    public Object getValue() {

		return "<span title=\"" + header + "\">" + header
			+ "</span><br /><input type=\"checkbox\" id=\"ddf_seleziona_tutti\" onclick=\"selezionaTuttiDDF()\" title=\"" + label
			+ "\"/>";
	    }
	};
	columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", new LinkAzioniDocumentiDaFirmare(documentiDaFirmareService,
		oggettiMetadatiService, this.getUtenteLoggato().getId().getCodice(), responsabiliService, request), customHeaderEditor, false, false,
		false, "8%");
    }

    @Override
    protected Collection<?> setItems() {

	int count = documentiDaFirmareService.countDocumentiDaFirmarePerFirmatario(this.getUtenteLoggato().getId().getCodice());
	getFacade().setTotalRows(count);
	this.countedRecords = count;
	List<DocumentiDaFirmare> list = new ArrayList<DocumentiDaFirmare>();
	if (count > 0) {
	    list = documentiDaFirmareService.findDocumentiDaFirmareDTOPerFirmatario(this.getUtenteLoggato().getId().getCodice(), getStartRowPage(),
		    getEndRowPage());
	}
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista documenti da firmare");
	int count = documentiDaFirmareService.countDocumentiDaFirmarePerFirmatario(this.getUtenteLoggato().getId().getCodice());
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
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * (Double.valueOf(pageSize).intValue());
		rowEnd = (Double.valueOf(pageSize).intValue());
		documentiDaFirmareService.clear();
		List<DocumentiDaFirmare> list = documentiDaFirmareService.findDocumentiDaFirmareDTOPerFirmatario(this.getUtenteLoggato().getId()
			.getCodice(), startRow, rowEnd);
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
	    }
	}
	result.setRighe(righe);
	return result;
    }

    private double pageSize = 100;

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
