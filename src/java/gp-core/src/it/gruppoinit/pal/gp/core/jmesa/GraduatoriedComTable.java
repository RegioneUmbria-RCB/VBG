package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedComDTO;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedComService;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.customColumn.CheckBoxAjaxUpdateIstanzeeventi;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.DocDaFirmareCustomColumn;
import org.jmesa.customColumn.HeaderMailGraduatoriedComEditor;
import org.jmesa.customColumn.LinkAzioniGraduatorieDCom;
import org.jmesa.customColumn.LinkDettaglioIstanzaInGraduatoriedCom;
import org.jmesa.customColumn.LinkDettaglioMovimentoInGraduatoriedCom;
import org.jmesa.customColumn.LinkIstanzaAndMovimentiEventi;
import org.jmesa.customColumn.LinkMailGraduatoridCom;
import org.jmesa.customColumn.LinkOggetti;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.GenerateTable;

public class GraduatoriedComTable extends GenerateTable<GraduatoriedComDTO> {

    private Integer codiceGraduatoriaComT;
    private boolean isMettiallafirma;
    private GraduatoriedComService graduatoriedComService;
    private DocumentiDaFirmareService documentiDaFirmareService;

    public GraduatoriedComTable(Integer codiceGraduatoriaComT, boolean isMettallafirma, GraduatoriedComService graduatoriedComService,
	    DocumentiDaFirmareService documentiDaFirmareService) {

	super();
	this.codiceGraduatoriaComT = codiceGraduatoriaComT;
	this.isMettiallafirma = isMettallafirma;
	this.graduatoriedComService = graduatoriedComService;
	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("graduatoried.posizione", "form.graduatoried.posizione", null, "");
	columnJmesa.addBaseColumn("graduatoried.istanza.richiedente.descrizioneRichiedente", "form.graduatoried.richiedente", null, "");
	columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.istanza.id.codice", "label.istanza", "",
		new LinkDettaglioIstanzaInGraduatoriedCom(request), false, false, false, "", "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.istanza.id.codice", "label.movimento", "",
		new LinkDettaglioMovimentoInGraduatoriedCom(request), false, false, false, "", "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.istanza.id.codice", "label.allegato", "", new LinkOggetti(request), false,
		false, false, "", "5%");
	if (isMettiallafirma) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.istanza.id.codice", "label.da_firmare", "",
		    new DocDaFirmareCustomColumn(request, documentiDaFirmareService), false, false, false, "", "5%");
	}
	columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.istanza.id.codice", new LinkMailGraduatoridCom(request),
		new HeaderMailGraduatoriedComEditor(), false, false, false, "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.istanza.id.codice", "label.evento", "", new LinkIstanzaAndMovimentiEventi(
		request), false, false, false, "", "25%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "istanzeeventi.id.codice", "label.letto", "", new CheckBoxAjaxUpdateIstanzeeventi(
		"istanzeeventi.flagLetto"), false, false, false, "", "3%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "graduatoried.id.codice", "label.azioni", "", new LinkAzioniGraduatorieDCom(request),
		false, false, false, "", "8%");
    }

    @Override
    protected Collection<?> setItems() {

	int count = graduatoriedComService.countGraduatoriedComDomande(codiceGraduatoriaComT);
	getFacade().setTotalRows(count);
	List<GraduatoriedComDTO> list = graduatoriedComService.findGraduatoriedComDTOByGraduatoriatComWithEvent(codiceGraduatoriaComT,
		getStartRowPage(), getEndRowPage());
	return list;
    }
}
