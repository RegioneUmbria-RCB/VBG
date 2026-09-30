package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.LinkCreaCollegamentoTraIstanzeCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

public class IstanzeCollegateTable extends GenerateTable<IstanzeListHelper> {

    private IstanzeFilter filter;
    private Integer codiceIstanzaDaconfigurare;
    private Boolean isPrecedenteIstanzeCollegate;
    // Indica il tipo di fuinzionalità per cui applichiamo la ricerca (es. istanze collegate, istanze accesso atti)
    private Integer funzionalita;

    public IstanzeCollegateTable(IstanzeFilter filter, Integer codiceIstanzaDaconfigurare, Boolean isPrecedenteIstanzeCollegate,
	    Integer funzionalita) {

	super();
	this.filter = filter;
	this.codiceIstanzaDaconfigurare = codiceIstanzaDaconfigurare;
	this.isPrecedenteIstanzeCollegate = isPrecedenteIstanzeCollegate;
	this.funzionalita = funzionalita;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "datavalidita"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("numeroistanza", "label.codice_istanza", true, true, true, null, "2%");
	columnJmesa.addDataBaseColumn("data", "label.data", WebConstants.DATE_FORMAT_PATTERN, true, true, true, null, "10%");
	columnJmesa.addDataBaseColumn("datavalidita", "label.data_validita", WebConstants.DATE_FORMAT_PATTERN, true, true, true, null, "10%");
	columnJmesa.addBaseColumn("transientDescrizioneRichiedenteQualitaAzienda", "label.richiedente", false, false, true, null, "");
	columnJmesa.addBaseColumn("transientDescrizioneLocalizzazione", "label.localizzazione", false, false, true, null, "");
	columnJmesa.addBaseColumn("interventoproc", "label.alberoproc", false, false, true, null, "");
	columnJmesa.addBaseColumn("procedura", "label.procedura", false, false, true, null, "");
	columnJmesa.addBaseColumn("statoistanza", "label.stato_istanza", false, false, true, null, "");
	columnJmesa.addBaseColumn("azione", "label.azione", true, true, true, null, "");
	// CASO 1:  isCollegamentoPrecedente=false il collegamento sarà standard, cioè verrà collegata come successiva.
	// CASO 2:  isCollegamentoPrecedente=true il collegamento sarà fatto come precedente.
	final String header = getMessageFromBundle(getContext(), "label.azione", null);
	final String headerMultiplo = getMessageFromBundle(getContext(), "label.selezione_multipla", null);
	final String headerCheckboxDocValidi = getMessageFromBundle(getContext(), "label.flag_doc_no_validi_accesso_atti", null);
	final String m = "Selezione<br />multipla";
	HeaderEditor customHeaderEditor = new HeaderEditor() {

	    @Override
	    public Object getValue() {

		return "<span title=\"" + header + "\">" + header + "</span>";
	    }
	};
	HeaderEditor customHeaderEditorMultiplo = new HeaderEditor() {

	    @Override
	    public Object getValue() {

		return "<span title=\"" + headerMultiplo + "\">" + m + "</span>";
	    }
	};
	HeaderEditor customHeaderEditorCheckboxDocValidi = new HeaderEditor() {

	    @Override
	    public Object getValue() {

		return "<span title=\"" + headerCheckboxDocValidi + "\">" + headerCheckboxDocValidi + "</span>";
	    }
	};
	if (funzionalita.equals(WebConstants.SEARCH_ISTANZE_COLLEGATE)) {
	    if (!isPrecedenteIstanzeCollegate) {
		columnJmesa.addCellEditorCustomLabelColumn(request, "codiceistanza",
			new LinkCreaCollegamentoTraIstanzeCellEditor("../istanzecollegate/addCollegamento.htm", codiceIstanzaDaconfigurare),
			customHeaderEditor, false, false, false, "5%");
	    } else {
		columnJmesa.addCellEditorCustomLabelColumn(request, "codiceistanza", "label.azione", "",
			new LinkCreaCollegamentoTraIstanzeCellEditor("../istanzecollegate/addCollegamentoPrecedente.htm", codiceIstanzaDaconfigurare),
			false, false, false, "", "5%");
	    }
	    columnJmesa.addCellEditorCustomLabelColumn(request, "codiceistanza",
		    new LinkCreaCollegamentoMultiploTraIstanzeCellEditor(isPrecedenteIstanzeCollegate), customHeaderEditorMultiplo, false, false,
		    false, "5%");
	} else if (funzionalita.equals(WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI)) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "codiceistanza", new LinkCreaCollegamentoMultiploIstanzeAttiDCellEditor(),
		    customHeaderEditorMultiplo, false, false, false, "5%");
	    String valoreZeroLabel = getMessageFromBundle(getContext(), "label.accesso_atti.solo_i_documenti_validi", null);
	    String valoreUnoLabel = getMessageFromBundle(getContext(), "label.accesso_atti.tutti_i_documenti", null);
	    String valoreDueLabel = getMessageFromBundle(getContext(), "label.accesso_atti.tutti_i_documenti_validi_o_da_verificare", null);
	    columnJmesa.addCellEditorCustomLabelColumn(request, "codiceistanza",
		    new CheckBoxAccessoAttiTDocValidiCellEditor(valoreZeroLabel, valoreUnoLabel, valoreDueLabel), customHeaderEditorCheckboxDocValidi,
		    false, false, false, "5%");
	} else if (funzionalita.equals(WebConstants.SEARCH_ISTANZE_ACCESSO_ANAGRAFE_TRIBUTARIA)) {
	    columnJmesa.addCellEditorCustomLabelColumn(request, "codiceistanza", new CheckBoxIstanzeCollegateCellEditor("anagrafe_tributaria_cls"),
		    customHeaderEditorCheckboxDocValidi, false, false, false, "5%");
	}
    }

    @Override
    protected Collection<?> setItems() {

	int count = 10;
	IstanzeService istanzeService = (IstanzeService) ContextLoader.getCurrentWebApplicationContext().getBean("istanzeServiceImpl",
		IstanzeService.class);
	count = istanzeService.countIstanzeListHelperByFilter(filter);
	getFacade().setTotalRows(count);
	List<IstanzeListHelper> listIstanze = new ArrayList<IstanzeListHelper>();
	if (count > 0) {
	    listIstanze = istanzeService.findIstanzeListHelperByFilter(filter, getStartRowPage(), getEndRowPage());
	}
	return listIstanze;
    }
}
