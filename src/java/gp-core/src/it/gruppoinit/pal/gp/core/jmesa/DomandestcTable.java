package it.gruppoinit.pal.gp.core.jmesa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class DomandestcTable extends GenerateTable<Domandestc> {

    private Domandestc filter;

    public DomandestcTable() {

	super();
    }

    public DomandestcTable(Domandestc filter) {

	super();
	this.filter = filter;
    }

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

	tableFacade.addFilterMatcher(new MatcherKey(Date.class, "dataUltimoerrore"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addBaseColumn("idDomandamitt", "label.domande_mittente", null, "2%");
	columnJmesa.addBaseColumn("numeroistanza", "label.numero_istanza", null, "2%");
	columnJmesa.addBaseColumn("richiedente", "label.nominativo", null, "25%");
	columnJmesa.addBaseColumn("ultimoerrore", "label.descrizione_errore", null, "59%");
	columnJmesa.addDataBaseColumn("dataUltimoerrore", "label.data", WebConstants.DATE_FORMAT_PATTERN, true, true, true, null, "10%");
	columnJmesa.addBaseColumn("comune.comune", "label.comune", null, "10%");
	columnJmesa.addDownloadActionColumn(request, "oggetti.id.codice");
	columnJmesa.addBaseActionColumn(request, "id.codice", "label.importa_pratica", ColumnJmesa.IMPORT, "../domandestc/insertPratica.htm?codice=",
		null, null, "%3");
	columnJmesa.addBaseActionColumn(request, "id.codice", "label.elimina", ColumnJmesa.DELETE, "../domandestc/delete.htm?codice=", null, null,
		"%3");
	columnJmesa.addBaseActionColumn(request, "id.codice", "label.dettaglio", ColumnJmesa.DETAIL, "../domandestc/view.htm?codice=", null, null,
		"%3");
    }

    @Override
    protected Collection<?> setItems() {

	DomandestcService domandestcService = (DomandestcService) ContextLoader.getCurrentWebApplicationContext().getBean("domandestcServiceImpl",
		DomandestcService.class);
	Domandestc domandestc = new Domandestc();
	// se filter è diverso da null significa che ho impostato un filtro fisso sul controller
	// e setto questo filtro al query che andrò a fare
	if (filter != null) {
	    domandestc = filter;
	}
	domandestc = getFilterQuery(domandestc);
	FilterTable filterTable = domandestcService.createFilterTableByEntity(domandestc);
	FilterRestriction filterRestriction = new FilterRestriction();
	// Filtro aggiuntivo che deve rimanere fisso, devono essere sempre filtrate per flagImport == false
	filterRestriction.addFilterField(FilterUtils.equals("flagImport", false, Boolean.class));
	filterTable.addRestriction(filterRestriction);
	int count = domandestcService.countRecord(filterTable);
	getFacade().setTotalRows(count);
	filterTable.addOrder(FilterUtils.orderDesc("dataricezione"));
	List<Domandestc> list = domandestcService.findByFilterTable(filterTable, getStartRowPage(), getEndRowPage());
	return list;
    }
    
    
    
    
    
    @Override
    public ExportTableHelper generateTableHelper() {

        ExportTableHelper result = new ExportTableHelper();

        // Caption export
        result.setTableCaption(
            getMessageFromBundle(getContext(), "label.istanze_stc_non_importate", null)
        );

        DomandestcService domandestcService =
            (DomandestcService) ContextLoader.getCurrentWebApplicationContext()
                .getBean("domandestcServiceImpl", DomandestcService.class);

        // Ricostruzione filtro come in setItems()
        Domandestc domandestc = new Domandestc();
        if (filter != null) {
            domandestc = filter;
        }

        FilterTable filterTable = domandestcService.createFilterTableByEntity(domandestc);

        // Filtro fisso: flagImport = false
        FilterRestriction filterRestriction = new FilterRestriction();
        filterRestriction.addFilterField(
            FilterUtils.equals("flagImport", false, Boolean.class)
        );
        filterTable.addRestriction(filterRestriction);

        // Ordinamento coerente con la lista
        filterTable.addOrder(FilterUtils.orderDesc("dataricezione"));

        int count = domandestcService.countRecord(filterTable);

        /* =========================
         * COLONNE
         * ========================= */
        List<String> colonne = new ArrayList<String>();
        colonne.add(getMessageFromBundle(getContext(), "label.domande_mittente", null));
        colonne.add(getMessageFromBundle(getContext(), "label.numero_istanza", null));
        colonne.add(getMessageFromBundle(getContext(), "label.nominativo", null));
        colonne.add(getMessageFromBundle(getContext(), "label.descrizione_errore", null));
        colonne.add(getMessageFromBundle(getContext(), "label.data", null));
        colonne.add(getMessageFromBundle(getContext(), "label.comune", null));
        result.setColonne(colonne);

        /* =========================
         * RIGHE 
         * ========================= */
        List<String[]> righe = new ArrayList<String[]>();

        int pageSize = 100; 
        int startRow = 0;

        while (startRow < count) {

            List<Domandestc> list =
                domandestcService.findByFilterTable(filterTable, startRow, pageSize);

            for (Domandestc d : list) {

                String[] riga = new String[colonne.size()];

                riga[0] = d.getIdDomandamitt();
                riga[1] = d.getNumeroistanza();
                riga[2] = d.getRichiedente();
                riga[3] = d.getUltimoerrore();

                if (d.getDataUltimoerrore() != null) {
                    riga[4] = Utilities.formatDate(
                        d.getDataUltimoerrore(),
                        WebConstants.DATE_FORMAT_PATTERN
                    );
                } else {
                    riga[4] = "";
                }

                if (d.getComune() != null) {
                    riga[5] = d.getComune().getComune();
                } else {
                    riga[5] = "";
                }

                righe.add(riga);
            }

            startRow += pageSize;
        }

        result.setRighe(righe);
        return result;
    }


    
}
