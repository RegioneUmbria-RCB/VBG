package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.FoRichiesteService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.customColumn.CheckBoxAjaxUpdateValue;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;

public class RichiesteFoNonLetteTable extends GenerateTable<FoRichieste> {

    private FoRichiesteService foRichiesteService;

    public RichiesteFoNonLetteTable(FoRichiesteService foRichiesteService) {

	this.foRichiesteService = foRichiesteService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("id.codice", "label.codice", false, false, true, "", "2%");
	columnJmesa.addDataBaseColumn("datarichiesta", "label.data", WebConstants.DATE_FORMAT_PATTERN, false, false, true, null, "6%");
	columnJmesa.addBaseColumn("anagrafe.descrizioneRichiedente", "label.richiedente", false, false, true, "", "");
	columnJmesa.addBaseActionColumn(request, "oggetto.id.codice", "label.oggetto", ColumnJmesa.VIEW_DOC, "../file/ajaxDownload.htm?fileId=",
		null, null, "6%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", "label.letto", "label.letto", new CheckBoxAjaxUpdateValue("foRichieste",
		"id.codice", "../batchscadenzario/ajaxSegnaLettoRichiestaFo.htm?codice="), false, false, false, "label.letto", "2%");
	String uriBack = "../batchscadenzario/list.htm";
	columnJmesa.addLinkLabelBaseColumn(request, "id.codice", "label.importa", "label.importa",
		"../anagrafe/controlloAggiornamentiAnagrafeByFE.htm?codice=", uriBack, false, false, "label.importa", "2%");
	JMesaTableLinkHelper eliminaLink = new JMesaTableLinkHelper("", uriBack, LinkTargetEnum.ELIMINA_FO_RICHIESTE, Utilities.getMessageFromBundle(
		getContext(), "javascript.confirm.delete", null));
	columnJmesa.addLinkWithIconColumn("eliminaRiga", "label.elimina", eliminaLink, "label.elimina", false, false, false, "2%");
    }

    @Override
    protected Collection<?> setItems() {

	int count = foRichiesteService.countRichiesteNonLette();
	getFacade().setTotalRows(count);
	List<FoRichieste> list = foRichiesteService.findRichiesteNonLette(getStartRowPage(), getEndRowPage());
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista movimenti da Effettuare");
	int count = foRichiesteService.countRichiesteNonLette();
	List<FoRichieste> list = new ArrayList<FoRichieste>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "codice");
	colonne.add(1, "data");
	//	colonne.add(1, "modulo");
	//	colonne.add(2, "stato");
	colonne.add(2, "richiedente");
	//	colonne.add(2, "modulo");
	//	colonne.add(4, "intervento");
	//	colonne.add(5, "movimentofatto");
	////	colonne.add(7, "movimentodaeffettuare");
	//	colonne.add(6, "endoprocedimento");
	//	colonne.add(7, "dataregistrazione");
	////	colonne.add(10, "datascadenza");
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
		list = foRichiesteService.findRichiesteNonLette(startRow, rowEnd);
		for (FoRichieste foRichieste : list) {
		    String[] riga = new String[colonne.size()];
		    // riga[0] = String.valueOf(movimento.getIstanza().getId().getCodice());
		    riga[0] = String.valueOf(foRichieste.getId().getCodice());
		    riga[1] = Utilities.formatDate(foRichieste.getDatarichiesta(), false);
		    riga[2] = (String) EntityUtils.getNestedProperty(foRichieste.getAnagrafe(), "descrizioneRichiedente");
		    //		    riga[2] = movimento.getIstanza().getChiusura().getStato();
		    //		    riga[4] = (String) EntityUtils.getNestedProperty(movimento.getIstanza().getAlberoproc(), "istanza.alberoproc.vwAlberoproc.scDescrizione");
		    //		    riga[5] = (String) EntityUtils.getNestedProperty(movimento, "movimento");
		    ////		    riga[6] = (String) EntityUtils.getNestedProperty(movimento, "movimento");
		    //		    riga[6] = (String) EntityUtils.getNestedProperty(movimento, "endoprocedimento.procedimento");
		    //		    riga[7] = Utilities.formatDate(movimento.getData(), false);
		    //		    riga[10] = Utilities.formatDate(movimento.getDatascadenza(), false);
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
