package it.gruppoinit.pal.gp.core.jmesa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.customColumn.ColumnJmesa;
import org.jmesa.facade.TableFacade;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Order;
import org.jmesa.limit.SortSet;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.web.ExportTableHelper;
import org.jmesa.web.GenerateTable;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class AnagrafeTable extends GenerateTable<Anagrafe> {

    @Override
    protected void addFilterFilterMatchMap(TableFacade tableFacade) {

    }

    @Override
    protected void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties) {

	ColumnJmesa columnJmesa = new ColumnJmesa(factory, table, row, tableFacade, setHtmlProperties);
	columnJmesa.addLinkBaseColumn(request, "id.codice", "label.codice", "id.codice", "../anagrafe/view.htm?codice=", "../anagrafe/list.htm", true,
		true, true, null, "2%");
	columnJmesa.addBaseColumn("nominativo", "anagrafe.label.nominativo", null, "20%");
	columnJmesa.addBaseColumn("nome", "label.nome", null, "20%");
	columnJmesa.addBaseColumn("codicefiscale", "label.codicefiscale", null, "5%");
	columnJmesa.addBaseColumn("partitaiva", "label.partitaiva", null, "5%");
	columnJmesa.addBaseColumn("descrizioneResidenza", "label.residenza", null, "15%");
	columnJmesa.addBaseColumn("corrispondenza", "anagrafe.label.indirizzo_corrispondenza", null, "15%");
	Map<String, Integer> mappaLabelValoreTipologia = new HashMap<String, Integer>();
	mappaLabelValoreTipologia.put("label.si", -1);
	mappaLabelValoreTipologia.put("label.no", 0);
	columnJmesa.addIntegerCustomColumn("tipologia", "anagrafe.label.tipologia_anagrafe", mappaLabelValoreTipologia, true, true, true, null, "4%");
	Map<String, String> mappaLabelValore = new HashMap<String, String>();
	mappaLabelValore.put("list.jmesa.celleditor.persona_fisica", "F");
	mappaLabelValore.put("list.jmesa.celleditor.persona_giuridica", "G");
	columnJmesa.addStringCostunColumn("tipoanagrafe", "anagrafe.label.tipo_anagrafe", mappaLabelValore, true, true, true, null, "6%");
	Map<String, Integer> mappaLabelValoreStato = new HashMap<String, Integer>();
	mappaLabelValoreStato.put("label.disabilitato", 1);
	mappaLabelValoreStato.put("label.attivo", 0);
	columnJmesa.addIntegerCustomColumn("flagDisabilitato", "anagrafe.label.stato", mappaLabelValoreStato, true, true, true, null, "4%");
	columnJmesa.addBaseActionColumn(request, "id.codice", "label.edit.record", ColumnJmesa.DETAIL, "../anagrafe/view.htm?codice=",
		"../anagrafe/list.htm", null, "5%");
    }

    @Override
    protected Collection<?> setItems() {

	AnagrafeService anagrafeService = (AnagrafeService) ContextLoader.getCurrentWebApplicationContext().getBean("anagrafeServiceImpl",
		AnagrafeService.class);
	FilterTable filterTable = this.populateFilterTable(getFacade());
	int count = anagrafeService.countRecord(filterTable);
	getFacade().setTotalRows(count);
	List<Anagrafe> list = new ArrayList<Anagrafe>();
	if (count > 0) {
	    list = anagrafeService.findByFilterTable(filterTable, getStartRowPage(), getEndRowPage());
	}
	return list;
    }

    private void addOrder(String proprieta, TableFacade facade, String[] orderLIst) {

	SortSet sortSet = facade.getLimit().getSortSet();
	if (sortSet != null && sortSet.getSort(proprieta) != null) {
	    org.jmesa.limit.Sort sort = sortSet.getSort(proprieta);
	    int p = sort.getPosition();
	    orderLIst[p] = proprieta;
	}
    }

    private org.jmesa.limit.Order getOrder(String proprieta, TableFacade facade) {

	SortSet sortSet = facade.getLimit().getSortSet();
	if (sortSet != null && sortSet.getSort(proprieta) != null) {
	    org.jmesa.limit.Sort sort = sortSet.getSort(proprieta);
	    if (sort != null && sortSet.getSort(proprieta) != null) {
		return sort.getOrder();
	    }
	}
	return Order.NONE;
    }

    private String getFilterValue(String proprieta, TableFacade facade) {

	FilterSet filterSet = facade.getLimit().getFilterSet();
	Filter f1 = filterSet.getFilter(proprieta);
	if (f1 != null) {
	    return StringUtils.defaultIfEmpty(f1.getValue(), "");
	}
	return "";
    }

    private FilterTable populateFilterTable(TableFacade facade) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	String[] orderLIst = new String[10];
	addOrder("descrizioneResidenza", facade, orderLIst);
	addOrder("id.codice", facade, orderLIst);
	addOrder("nominativo", facade, orderLIst);
	addOrder("nome", facade, orderLIst);
	addOrder("codicefiscale", facade, orderLIst);
	addOrder("partitaiva", facade, orderLIst);
	addOrder("corrispondenza", facade, orderLIst);
	addOrder("tipologia", facade, orderLIst);
	addOrder("tipoanagrafe", facade, orderLIst);
	addOrder("flagDisabilitato", facade, orderLIst);
	boolean orderSet = false;
	for (String proprieta : orderLIst) {
	    if (proprieta != null) {
		Order order = getOrder(proprieta, facade);
		if (order != null && !order.equals(Order.NONE)) {
		    OrderTypeEnum ord = OrderTypeEnum.ASC;
		    if (order.equals(Order.DESC)) {
			ord = OrderTypeEnum.DESC;
		    }
		    if (proprieta.equals("descrizioneResidenza")) {
			ft.addOrder(FilterUtils.order("indirizzo", ord));
			ft.addOrder(FilterUtils.order("cap", ord));
			ft.addOrder(FilterUtils.order("citta", ord));
		    } else if (proprieta.equals("corrispondenza")) {
			ft.addOrder(FilterUtils.order("indirizzocorrispondenza", ord));
			ft.addOrder(FilterUtils.order("capcorrispondenza", ord));
			ft.addOrder(FilterUtils.order("cittacorrispondenza", ord));
		    } else {
			ft.addOrder(FilterUtils.order(proprieta, ord));
		    }
		    orderSet = true;
		}
	    }
	}
	if (!orderSet) {
	    ft.addOrder(FilterUtils.order("nominativo", OrderTypeEnum.ASC));
	    ft.addOrder(FilterUtils.order("nome", OrderTypeEnum.ASC));
	}
	String descrizioneResidenza = getFilterValue("descrizioneResidenza", facade);
	if (StringUtils.isNotBlank(descrizioneResidenza)) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    fr.addFilterField(FilterUtils.like("indirizzo", "%" + descrizioneResidenza + "%"));
	    fr.addFilterField(FilterUtils.like("citta", "%" + descrizioneResidenza + "%"));
	    fr.addFilterField(FilterUtils.like("cap", "%" + descrizioneResidenza + "%"));
	    ft.addRestriction(fr);
	}
	String corrispondenza = getFilterValue("corrispondenza", facade);
	if (StringUtils.isNotBlank(corrispondenza)) {
	    FilterRestriction frCorrispondenza = new FilterRestriction();
	    frCorrispondenza.setAndOrRestriction(AndOrRestriction.OR);
	    frCorrispondenza.addFilterField(FilterUtils.like("indirizzocorrispondenza", "%" + corrispondenza + "%"));
	    frCorrispondenza.addFilterField(FilterUtils.like("cittacorrispondenza", "%" + corrispondenza + "%"));
	    frCorrispondenza.addFilterField(FilterUtils.like("capcorrispondenza", "%" + corrispondenza + "%"));
	    ft.addRestriction(frCorrispondenza);
	}
	String idCodice = getFilterValue("id.codice", facade);
	FilterRestriction frAltri = new FilterRestriction();
	if (Utilities.isInteger(idCodice.trim())) {
	    frAltri.addFilterField(FilterUtils.equals("id.codice", Integer.parseInt(idCodice.trim()), Integer.class));
	}
	String nominativo = getFilterValue("nominativo", facade);
	if (StringUtils.isNotBlank(nominativo)) {
	    frAltri.addFilterField(FilterUtils.like("nominativo", "%" + nominativo + "%"));
	}
	String nome = getFilterValue("nome", facade);
	if (StringUtils.isNotBlank(nome)) {
	    frAltri.addFilterField(FilterUtils.like("nome", "%" + nome + "%"));
	}
	String codicefiscale = getFilterValue("codicefiscale", facade);
	if (StringUtils.isNotBlank(codicefiscale)) {
	    frAltri.addFilterField(FilterUtils.like("codicefiscale", "%" + codicefiscale + "%"));
	}
	String partitaiva = getFilterValue("partitaiva", facade);
	if (StringUtils.isNotBlank(partitaiva)) {
	    frAltri.addFilterField(FilterUtils.like("partitaiva", "%" + partitaiva + "%"));
	}
	String tipologia = getFilterValue("tipologia", facade); // integer
	if (Utilities.isInteger(tipologia.trim())) {
	    frAltri.addFilterField(FilterUtils.equals("tipologia", Integer.parseInt(tipologia.trim()), Integer.class));
	}
	String tipoanagrafe = getFilterValue("tipoanagrafe", facade);
	if (StringUtils.isNotBlank(tipoanagrafe)) {
	    frAltri.addFilterField(FilterUtils.equals("tipoanagrafe", tipoanagrafe, String.class));
	}
	String flagDisabilitato = getFilterValue("flagDisabilitato", facade);
	if (Utilities.isInteger(flagDisabilitato.trim())) {
	    frAltri.addFilterField(FilterUtils.equals("flagDisabilitato", Integer.parseInt(flagDisabilitato.trim()), Integer.class));
	}
	ft.addRestriction(frAltri);
	return ft;
    }

    @Override
    public ExportTableHelper generateTableHelper() {

	ExportTableHelper result = new ExportTableHelper();
	result.setTableCaption("Lista delle anagrafiche");
	AnagrafeService anagrafeService = (AnagrafeService) ContextLoader.getCurrentWebApplicationContext().getBean("anagrafeServiceImpl",
		AnagrafeService.class);
	FilterTable filterTable = this.populateFilterTable(getFacade());
	int count = anagrafeService.countRecord(filterTable);
	List<Anagrafe> list = null;
	List<String> colonne = new ArrayList<String>();
	colonne.add(0, "codice");
	colonne.add(1, "nominativo");
	colonne.add(2, "nome");
	colonne.add(3, "formagiuridica");
	colonne.add(4, "tipologia");
	colonne.add(5, "indirizzoresidenza");
	colonne.add(6, "cittaresidenza");
	colonne.add(7, "capresidenza");
	colonne.add(8, "provinciaresidenza");
	colonne.add(9, "telefono");
	colonne.add(10, "telefonocellulare");
	colonne.add(11, "fax");
	colonne.add(12, "partitaiva");
	colonne.add(13, "codicefiscale");
	colonne.add(14, "email");
	colonne.add(15, "pec");
	colonne.add(16, "numcciaa");
	colonne.add(17, "regtribn");
	colonne.add(18, "comuneregcciaa");
	colonne.add(19, "comunenascita");
	colonne.add(20, "datanascita");
	colonne.add(21, "dataregcciaa");
	colonne.add(22, "dataregtrib");
	colonne.add(23, "sesso");
	colonne.add(24, "titolo");
	colonne.add(25, "tipoanagrafe");
	colonne.add(26, "cittadinanza");
	colonne.add(27, "comuneresidenza");
	colonne.add(28, "indirizzocorrispondenza");
	colonne.add(29, "cittacorrispondenza");
	colonne.add(30, "comunecorrispondenza");
	colonne.add(31, "provinciacorrispondenza");
	colonne.add(32, "provinciarea");
	colonne.add(33, "numiscrrea");
	colonne.add(34, "dataiscrrea");
	colonne.add(35, "ordineprofessionale");
	colonne.add(36, "numiscrizioneordine");
	colonne.add(37, "proviscrizioneordine");
	colonne.add(38, "stato");
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
		anagrafeService.clear();
		list = anagrafeService.findByFilterTable(filterTable, startRow, rowEnd);
		for (Anagrafe anag : list) {
		    String[] riga = new String[colonne.size()];
		    riga[0] = String.valueOf(anag.getId().getCodice());
		    riga[1] = anag.getNominativo();
		    riga[2] = anag.getNome();
		    riga[3] = (String) EntityUtils.getNestedProperty(anag.getFormagiuridica(), "formagiuridica");
		    String tecnico = "";
		    if (anag.getTipologia() != null) {
			tecnico = anag.getTipologia().intValue() == -1 ? "intermediario" : "";
		    }
		    riga[4] = tecnico;
		    riga[5] = anag.getIndirizzo();
		    riga[6] = anag.getCitta();
		    riga[7] = anag.getCap();
		    riga[8] = anag.getProvincia();
		    riga[9] = anag.getTelefono();
		    riga[10] = anag.getTelefonocellulare();
		    riga[11] = anag.getFax();
		    riga[12] = anag.getPartitaiva();
		    riga[13] = anag.getCodicefiscale();
		    riga[14] = anag.getEmail();
		    riga[15] = anag.getPec();
		    riga[16] = anag.getRegditte();
		    riga[17] = anag.getRegtrib();
		    riga[18] = (String) EntityUtils.getNestedProperty(anag.getComunecomregditte(), "comune");
		    riga[19] = (String) EntityUtils.getNestedProperty(anag.getComuneNascita(), "comune");
		    riga[20] = Utilities.formatDate(anag.getDatanascita(), false);
		    riga[21] = Utilities.formatDate(anag.getDataregditte(), false);
		    riga[22] = Utilities.formatDate(anag.getDataregtrib(), false);
		    riga[23] = anag.getSesso();
		    riga[24] = (String) EntityUtils.getNestedProperty(anag.getTitolo(), "titolo");
		    riga[25] = anag.getTipoanagrafe();
		    riga[26] = (String) EntityUtils.getNestedProperty(anag.getCittadinanza(), "cittadinanza");
		    riga[27] = (String) EntityUtils.getNestedProperty(anag.getComuneResidenza(), "comune");
		    riga[28] = anag.getIndirizzocorrispondenza();
		    riga[29] = anag.getCittacorrispondenza();
		    riga[30] = (String) EntityUtils.getNestedProperty(anag.getComunecorrispondenza(), "comune");
		    riga[31] = anag.getProvinciacorrispondenza();
		    riga[32] = (String) EntityUtils.getNestedProperty(anag, "provinciarea");
		    riga[33] = anag.getNumiscrrea();
		    riga[34] = Utilities.formatDate(anag.getDataiscrrea(), false);
		    riga[35] = (String) EntityUtils.getNestedProperty(anag.getElenchiprofessionalibase(), "epDescrizione");
		    riga[36] = anag.getNumeroelencopro();
		    riga[37] = (String) EntityUtils.getNestedProperty(anag, "provinciaelencopro");
		    String stato = "Attivo";
		    if(anag.getFlagDisabilitato()!=null && anag.getFlagDisabilitato().equals(1)) {
			stato = "Disabilitato";
		    }
		    riga[38] = stato;
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
