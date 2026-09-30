package it.gruppoinit.pal.gp.core.jmesa;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.customColumn.BigDecimalCellEditor;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.customColumn.RegistrazioniAzioniCellEditor;
import org.jmesa.customColumn.RegistrazioniImportoCellEditor;
import org.jmesa.facade.TableFacade;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;

public class RegistrazioniTable extends GenerateTable<Registrazioni> {

    private RegistrazioniFilter registrazioniFilter;
    private RegistrazioniService registrazioniService;
    private ConfigurazioneutenteService configurazioneutenteService;
    private UserSecurityService userSecurityService;

    public RegistrazioniTable(RegistrazioniFilter registrazioniFilter, RegistrazioniService registrazioniService,
	    ResponsabiliService responsabiliService, ConfigurazioneutenteService configurazioneutenteService, UserSecurityService userSecurityService) {

	this.registrazioniFilter = registrazioniFilter;
	this.registrazioniService = registrazioniService;
	this.configurazioneutenteService = configurazioneutenteService;
	this.userSecurityService = userSecurityService;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	String adeguamenti = (String) request.getParameter("adeguamenti");
	String adeguamentoPercentuale = (String) request.getParameter("adeguamentoPercentuale");
	if ((adeguamentoPercentuale == null || adeguamentoPercentuale.equals(""))) {
	    adeguamentoPercentuale = "0.00";
	} else {
	    adeguamentoPercentuale = adeguamentoPercentuale.replace(',', '.');
	}
	if (adeguamentoPercentuale.indexOf('.') == -1) {
	    adeguamentoPercentuale += ".00";
	}
	boolean adeguamento = false;
	BigDecimal percentuale = BigDecimal.ZERO;
	if (StringUtils.isNotBlank(adeguamenti)) {
	    adeguamento = true;
	    percentuale = new BigDecimal(adeguamentoPercentuale);
	}
	String urlBack = "../registrazioni/search.htm";
	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkBaseColumn(request, "id.codice", "label.codice", "id.codice", "../registrazioni/view.htm?codice=", urlBack, false, false,
		true, null, "2%");
	columnJmesa.addBaseColumn("progressivo", "form.registrazioni.progressivo", false, false, true, null, "5%");
	columnJmesa.addBaseColumn("anno", "form.registrazioni.anno", false, false, true, null, "5%");
	columnJmesa.addDataBaseColumn("dataRegistrazione", "form.registrazioni.dataRegistrazione", WebConstants.DATE_FORMAT_PATTERN, false, false,
		true, "form.registrazioni.dataRegistrazione", "5%");
	columnJmesa.addBaseColumn("anagrafe.descrizioneRichiedente", "form.registrazioni.anagrafe", false, false, true, null, "10%");
	columnJmesa.addBaseColumn("registrazioniCausali.descrizione", "form.registrazioni.registrazioniCausali", false, false, true, null, "10%");
	columnJmesa.addBaseColumn("mercatiD.mercati.descrizione", "form.registrazioni.mercati", false, false, true, null, "10%");
	columnJmesa.addBaseColumn("mercatiUso.descrizione", "form.registrazioni.mercatiUso", false, false, true, null, "5%");
	columnJmesa.addBaseColumn("mercatiD.codiceposteggio", "form.registrazioni.mercati.posteggio", false, false, true, null, "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "importo", "form.registrazioniFilter.importo", "form.registrazioniFilter.importo",
		new RegistrazioniImportoCellEditor(request, "importo", adeguamento, percentuale), false, false, true, null, "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "vwRegistrazionisaldo.saldo", "form.registrazioniimporti.debito",
		"form.registrazioniimporti.debito", new BigDecimalCellEditor(2, 2, false), false, false, true, null, "5%");
	columnJmesa.addCellEditorCustomLabelColumn(request, "id.codice", "label.edit.record", "label.edit.record",
		new RegistrazioniAzioniCellEditor(), false, false, true, null, "5%");
    }

    @Override
    protected Collection<?> setItems() {

	int count = registrazioniService.countByRegistrazioniFilter(registrazioniFilter);
	getFacade().setTotalRows(count);
	List<Registrazioni> listIstanze = new ArrayList<Registrazioni>();
	if (count > 0) {
	    listIstanze = registrazioniService.findByRegistrazioniFilter(registrazioniFilter, getStartRowPage(), getEndRowPage());
	}
	return listIstanze;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle registrazioni");
	// int count = istanzeService.countByFilter(filter);
	int count = registrazioniService.countByRegistrazioniFilter(registrazioniFilter);
	List<Registrazioni> list = new ArrayList<Registrazioni>();
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "codice");
	colonne.add(1, "progressivo");
	colonne.add(2, "anno");
	colonne.add(3, "nominativo");
	colonne.add(4, "mercato");
	colonne.add(5, "uso");
	colonne.add(6, "posteggio");
	colonne.add(7, "data");
	colonne.add(8, "causale");
	colonne.add(9, "importo");
	colonne.add(10, "da incassare");
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
		registrazioniService.clear();
		list = registrazioniService.findByRegistrazioniFilter(registrazioniFilter, startRow, rowEnd);
		for (Registrazioni r : list) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(r.getId().getCodice());
		    riga[1] = StringUtils.defaultString(r.getProgressivo());
		    riga[2] = String.valueOf(r.getAnno());
		    if (r.getAnagrafe() != null) {
			riga[3] = StringUtils.defaultString(r.getAnagrafe().getDescrizioneRichiedente());
		    } else {
			riga[3] = "";
		    }
		    if (r.getMercatiD() != null) {
			if (r.getMercatiD().getMercati() != null) {
			    riga[4] = StringUtils.defaultString(r.getMercatiD().getMercati().getDescrizione());
			} else {
			    riga[4] = "";
			}
		    } else {
			riga[4] = "";
		    }
		    if (r.getMercatiUso() != null) {
			riga[5] = StringUtils.defaultString(r.getMercatiUso().getDescrizione());
		    } else {
			riga[5] = "";
		    }
		    if (r.getMercatiD() != null) {
			riga[6] = StringUtils.defaultString(r.getMercatiD().getCodiceposteggio());
		    } else {
			riga[6] = "";
		    }
		    if (r.getDataRegistrazione() != null) {
			riga[7] = Utilities.formatDate(r.getDataRegistrazione(), false);
		    } else {
			riga[7] = "";
		    }
		    if (r.getRegistrazioniCausali() != null) {
			riga[8] = StringUtils.defaultString(r.getRegistrazioniCausali().getDescrizione());
		    } else {
			riga[8] = "";
		    }
		    if (r.getImporto() != null) {
			riga[9] = String.valueOf(r.getImporto());
		    } else {
			riga[9] = "";
		    }
		    if (r.getVwRegistrazionisaldo() != null) {
			if (r.getVwRegistrazionisaldo().getSaldo() != null) {
			    riga[10] = String.valueOf(r.getVwRegistrazionisaldo().getSaldo());
			} else {
			    riga[10] = "";
			}
		    } else {
			riga[10] = "";
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
