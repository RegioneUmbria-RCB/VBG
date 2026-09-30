package it.gruppoinit.pal.gp.core.jmesa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiFilter;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiLogService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class IstanzeAccessoAttiLogTable extends GenerateTable<IstanzeAccessoAttiLog> {

    private IstanzeAccessoAttiFilter filter;
    private Integer funzionalitaRicercaIstanze;

    public IstanzeAccessoAttiLogTable(IstanzeAccessoAttiFilter filter, Integer funzionalitaRicercaIstanze) {

	super();
	this.filter = filter;
	this.funzionalitaRicercaIstanze = funzionalitaRicercaIstanze;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "dataora"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	StringBuffer parametriHistoryBack = new StringBuffer();
	parametriHistoryBack.append("modalita_ricerca%3D")
		.append(funzionalitaRicercaIstanze != null ? funzionalitaRicercaIstanze : WebConstants.SEARCH_AUT_DEFAULT);
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("anagrafe.descrizioneRichiedente", "label.anagrafe", false, false, false, null, "");
	columnJmesa.addBaseColumn("istanze.numeroistanza", "label.num_istanza", false, false, false, null, "");
	columnJmesa.addBaseColumn("istanze.numeroprotocollo", "label.numero_protocollo", false, false, true, null, "8%");
	columnJmesa.addDataBaseColumn("istanze.dataprotocollo", "label.data_protocollo", WebConstants.DATE_FORMAT_PATTERN, false, false, false, null,
		"8%");
	columnJmesa.addBaseColumn("istanzeAccessoAttiT.istanze.richiedente.descrizioneRichiedente", "label.richiedente", false, false, true, null,
		"");
	columnJmesa.addDataBaseColumn("dataora", "label.data_accesso", WebConstants.DATE_WITH_TIME_FORMAT_PATTERN, false, false, true, null, "8%");
	columnJmesa.addBaseColumn("istanzeAccessoAttiT.istanze.numeroistanza", "label.num_istanza", false, false, false, null, "");
	columnJmesa.addBaseColumn("istanzeAccessoAttiT.istanze.numeroprotocollo", "label.numero_protocollo", false, false, false, null, "8%");
	columnJmesa.addDataBaseColumn("istanzeAccessoAttiT.istanze.dataprotocollo", "label.data_protocollo", WebConstants.DATE_FORMAT_PATTERN, false,
		false, false, null, "8%");
    }

    @Override
    protected Collection<?> setItems() {

	IstanzeAccessoAttiLogService accessoAttiLogService = (IstanzeAccessoAttiLogService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("istanzeAccessoAttiLogServiceImpl", IstanzeAccessoAttiLogService.class);
	int count = accessoAttiLogService.countByFilter(filter);
	getFacade().setTotalRows(count);
	List<IstanzeAccessoAttiLog> list = accessoAttiLogService.findIstanzeAccessoAttiLogByFilter(filter, getStartRowPage(), getEndRowPage());
	return list;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista accesso atti");
	IstanzeAccessoAttiLogService accessoAttiLogService = (IstanzeAccessoAttiLogService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("istanzeAccessoAttiLogServiceImpl", IstanzeAccessoAttiLogService.class);
	int count = accessoAttiLogService.countByFilter(filter);
	List<IstanzeAccessoAttiLog> listIstanzeAccessoAttiLog = new ArrayList<IstanzeAccessoAttiLog>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "Nominativo");
	colonne.add(1, "Num Pratica");
	colonne.add(2, "Num Protocollo");
	colonne.add(3, "Data Protocollo");
	colonne.add(4, "Richiedente");
	colonne.add(5, "Data Accesso");
	colonne.add(6, "Num Accesso atti");
	colonne.add(7, "Num Protocollo");
	colonne.add(8, "Data Protocollo");
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
		startRow = i * Double.valueOf(pageSize).intValue();
		rowEnd = (Double.valueOf(pageSize).intValue());
		accessoAttiLogService.clear();
		listIstanzeAccessoAttiLog = accessoAttiLogService.findIstanzeAccessoAttiLogByFilter(filter, startRow, rowEnd);
		for (IstanzeAccessoAttiLog istanzeAccessoAttiLog : listIstanzeAccessoAttiLog) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = StringUtils.defaultIfEmpty(istanzeAccessoAttiLog.getAnagrafe().getDescrizioneRichiedente(), "");
		    riga[1] = StringUtils.defaultIfEmpty(istanzeAccessoAttiLog.getIstanze().getNumeroistanza(), "");
		    riga[2] = StringUtils.defaultIfEmpty(istanzeAccessoAttiLog.getIstanze().getNumeroprotocollo(), "");
		    riga[3] = Utilities.formatDate(istanzeAccessoAttiLog.getIstanze().getDataprotocollo(), false);
		    riga[4] = StringUtils.defaultIfEmpty(
			    istanzeAccessoAttiLog.getIstanzeAccessoAttiT().getIstanze().getRichiedente().getDescrizioneRichiedente(), "");
		    riga[5] = Utilities.formatDate(istanzeAccessoAttiLog.getDataora(), true);
		    riga[6] = StringUtils.defaultIfEmpty(istanzeAccessoAttiLog.getIstanzeAccessoAttiT().getIstanze().getNumeroistanza(), "");
		    riga[7] = StringUtils.defaultIfEmpty(istanzeAccessoAttiLog.getIstanzeAccessoAttiT().getIstanze().getNumeroprotocollo(), "");
		    riga[8] = Utilities.formatDate(istanzeAccessoAttiLog.getIstanzeAccessoAttiT().getIstanze().getDataprotocollo(), false);
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
