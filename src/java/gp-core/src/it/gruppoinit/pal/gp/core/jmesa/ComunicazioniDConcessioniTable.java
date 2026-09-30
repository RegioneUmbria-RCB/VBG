package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.domain.helper.ComunicazioniDConcessioniDTO;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDConcessioniService;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.DocumentiDaFirmareComunicazioniColumn;
import org.jmesa.customColumn.HeaderMailGraduatoriedComEditor;
import org.jmesa.customColumn.LinkDettaglioConcessioniComunicazioni;
import org.jmesa.customColumn.LinkDettaglioIstanzaComunicazioni;
import org.jmesa.customColumn.LinkDettaglioMovimentoComunicazioni;
import org.jmesa.customColumn.LinkMailComunicazioni;
import org.jmesa.customColumn.LinkOggetti;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.GenerateTable;

public class ComunicazioniDConcessioniTable extends GenerateTable<ComunicazioniDConcessioniDTO> {

    private Integer codiceComunicazioneT;
    private boolean isMettiallafirma;
    private ComunicazioniDConcessioniService comunicazioniDConcessioniService;
    private DocumentiDaFirmareService documentiDaFirmareService;

    public ComunicazioniDConcessioniTable(Integer codiceComunicazioneT, boolean isMettallafirma,
	    ComunicazioniDConcessioniService comunicazioniDConcessioniService, DocumentiDaFirmareService documentiDaFirmareService) {

	super();
	this.codiceComunicazioneT = codiceComunicazioneT;
	this.isMettiallafirma = isMettallafirma;
	this.comunicazioniDConcessioniService = comunicazioniDConcessioniService;
	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	String codice = (String) request.getParameter("codice");
	//	String pathHistory = "../comunicazionitmercato/view.htm?codice=" + codice;
	String pathHistory = "../comunicazionitmercato/view.htm";
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	//columnJmesa.addBaseColumn("id.codice_", "form.graduatoried.richiedente", null, "");
	columnJmesa.addBaseColumn("istanza.richiedente.descrizioneRichiedente", "label.richiedente", null, "30%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "istanza.id.codice", "label.istanza", "", new LinkDettaglioIstanzaComunicazioni(request,
		"istanza", pathHistory), false, false, false, "", "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "", "label.concessione", "", new LinkDettaglioConcessioniComunicazioni(request,
		"autorizzazioniConcessioni.autorizzazioni", "istanza", pathHistory), false, false, false, "", "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "istanza.id.codice", "label.movimento", "", new LinkDettaglioMovimentoComunicazioni(
		request, pathHistory), false, false, false, "", "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "istanza.id.codice", "label.allegato", "", new LinkOggetti(request), false, false, false,
		"", "5%");
	if (isMettiallafirma) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "istanza.id.codice", "label.da_firmare", "",
		    new DocumentiDaFirmareComunicazioniColumn(request, "istanza", documentiDaFirmareService), false, false, false, "", "5%");
	}
	columnJmesa.addCellEditorCustomLabelColumn(request, "istanza.id.codice", new LinkMailComunicazioni(request, pathHistory),
		new HeaderMailGraduatoriedComEditor(), false, false, false, "5%");
	columnJmesa.addBaseColumn("eventoTmpStatiComunicazioniD", "label.evento_errore", false, true, false, null, "20%");
	//	columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.istanza.id.codice", "label.evento", "", new LinkIstanzaAndMovimentiEventi(
	//		request), false, false, false, "", "25%");
	//	columnJmesa.addCellEditorCustomLabelColumn(request, "istanzeeventi.id.codice", "label.letto", "", new CheckBoxAjaxUpdateIstanzeeventi(
	//		"istanzeeventi.flagLetto"), false, false, false, "", "3%");
	//	columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.id.codice", "label.azioni", "", new LinkAzioniGraduatorieDCom(request),
	//		false, false, false, "", "8%");
    }

    @Override
    protected Collection<?> setItems() {

	int count = comunicazioniDConcessioniService.countComunicazioniT(codiceComunicazioneT);
	getFacade().setTotalRows(count);
	List<ComunicazioniDConcessioniDTO> list = comunicazioniDConcessioniService.findByComunicazioniTWithEvent(codiceComunicazioneT,
		getStartRowPage(), getEndRowPage());
	return list;
    }
}
