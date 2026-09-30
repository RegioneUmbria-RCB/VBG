package org.jmesa.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.jmesa.IstanzeHelperTable;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.jmesa.view.ComponentFactory;
import org.jmesa.view.ExportComponentFactory;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvComponentFactory;
import org.jmesa.view.html.HtmlComponentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.ContextLoader;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 
 * @author gianpaolot
 * 
 * 
 */
public abstract class GenerateTable<E> {

    private Limit limit;
    private TableFacade facade;
    private ComponentFactory factory;
    private Table table;
    private Row row;
    private ApplicationContext context;
    private boolean setHtmlProperties;
    private Collection<?> items;
    private static final Logger log = LoggerFactory.getLogger(GenerateTable.class);

    public GenerateTable() {

	this.context = ContextLoader.getCurrentWebApplicationContext();
    }

    /**
     * Metodo che crea la tabella jmesa
     * 
     * @param request
     * @param response
     * @param titolo
     *            :titolo della tabella, deve essere passata l'etichetta che verrà risolta dal
     *            ResourceBundleMessageSource
     * @param idTable
     *            :id della tabella
     * @param isExport
     * @return
     */
    public String createJMesaList(HttpServletRequest request, HttpServletResponse response, String titolo, String idTable, boolean isExport) {

	String output = null;
	// indica che non sono in fase di export
	setHtmlProperties = true;
	// creazione Table facade
	facade = createTableFacade(idTable, request, response);
	//(PARTE VARIABILE, DA IMPLEMNETARE NELLA CLASSE CHE CREA LA TABELLA SPECIFICA)
	// mi permette di inserire vari FilterMatch (il metodo è un metoto astratto che
	//deve eventualmente essere implementato dentro la casse principale che crea la tabella)
	addFilterFilterMatchMap(facade);
	if (facade.getLimit().isExported()) {
	    if (facade.getLimit().getExportType().toParam().equals("csv")) {
		response.setContentType("text/csv");
		byte[] content = generateContent(ExportType.CSV);
		try {
		    response.setHeader("Pragma", "public");
		    response.setHeader("Cache-Control", "max-age=0");
		    response.setHeader("Content-Disposition", "attachment; filename=Export" + System.currentTimeMillis() + ".csv");
		    response.setHeader("Content-transfer-encoding", "binary");
		    response.getOutputStream().write(content);
		} catch (IOException e) {
		    throw new RuntimeException(e.getMessage(), e);
		}
	    }
	    if (facade.getLimit().getExportType().toParam().equals("pdfp")) {
		response.setContentType("application/pdf");
		byte[] content = generateContent(ExportType.PDFP);
		try {
		    response.setHeader("Pragma", "public");
		    response.setHeader("Cache-Control", "max-age=0");
		    response.setHeader("Content-Disposition", "attachment; filename=Export" + System.currentTimeMillis() + ".pdf");
		    response.setHeader("Content-transfer-encoding", "binary");
		    response.getOutputStream().write(content);
		} catch (IOException e) {
		    throw new RuntimeException(e.getMessage(), e);
		}
	    }
	    if (facade.getLimit().getExportType().toParam().equals("excel")) {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		byte[] content = generateContent(ExportType.EXCEL);
		try {
		    response.setHeader("Pragma", "public");
		    response.setHeader("Cache-Control", "max-age=0");
		    response.setHeader("Content-Disposition", "attachment; filename=Export" + System.currentTimeMillis() + ".xlsx");
		    response.setHeader("Content-transfer-encoding", "binary");
		    response.getOutputStream().write(content);
		} catch (IOException e) {
		    throw new RuntimeException(e.getMessage(), e);
		}
	    }
	    return null;
	} else {
	    // Creazione della Component Factory
	    factory = createFactory(facade, response, isExport);
	    
	    // Istanza la tabella e alcune proprietà (titolo)
	    table = generateTable(factory, titolo);
	    // Istanzia l'oggetto Row che utilizzeremo per passare alla tabella le proprità da visualizzare
	    row = istanceRow(factory);
	    //(PARTE VARIABILE, DA IMPLEMNETARE NELLA CLASSE CHE CREA LA TABELLA SPECIFICA)
	    // Aggiunge campi
	    // Il metodo permette di inserire i capi da mostrare sulla tabella sia in visualizzazione sia in exporrt 
	    addField(request, table, row, factory, facade, setHtmlProperties);
	    // render della tabella (trasforma tutte le informazioni passate in codice html)
	    output = renderTable(facade, table);
	    return output;
	}
    }
    
    private byte[] generateContent(ExportType exportType) {

	ExportTableHelper helper = generateTableHelper();
	 if(helper==null) {
	     log.error("cannot generate export with null helper (org.jmesa.web.ExportTableHelper)");
	     throw new RuntimeException("cannot generate export with null helper (org.jmesa.web.ExportTableHelper)");
         }
	String csvSeparator = ";";
	switch (exportType) {
	case CSV:
	    return generateCSVContent(helper, csvSeparator);
	case EXCEL:
	    try {
		return generateExcelContent(helper, csvSeparator);
	    } catch (Exception e) {
		throw new RuntimeException(e.getMessage(), e);
	    }
	case PDFP:
	    try {
		return generatePDFContent(helper, csvSeparator);
	    } catch (Exception e) {
		throw new RuntimeException(e.getMessage(), e);
	    }
	default:
	    return generateCSVContent(helper, csvSeparator);
	}
    }

    private byte[] generatePDFContent(ExportTableHelper helper, String csvSeparator) throws Exception {

	Document document = new Document(PageSize.A4.rotate());
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PdfWriter.getInstance(document, baos);
	document.open();
	List<String> colonne = helper.getColonne();
	document.add(new Paragraph(StringUtils.defaultIfEmpty(helper.getTableCaption(), "Lista"),
		FontFactory.getFont(FontFactory.HELVETICA, 12F, Font.BOLDITALIC, new Color(0, 0, 255))));
	PdfPTable table = new PdfPTable(colonne.size());
	table.setWidthPercentage(100);
	if (colonne != null) {
	    if (colonne.size() > 0) {
		for (String colonna : colonne) {
		    Paragraph p = new Paragraph(StringUtils.defaultIfEmpty(colonna, "???"),
			    FontFactory.getFont(FontFactory.HELVETICA, 10F, Font.BOLD, new Color(0, 0, 255)));
		    PdfPCell itemCell = new PdfPCell(p);
		    itemCell.setBackgroundColor(Color.LIGHT_GRAY);
		    table.addCell(itemCell);
		}
	    }
	}
	List<String[]> righe = helper.getRighe();
	if (righe != null) {
	    if (righe.size() > 0) {
		for (String[] riga : righe) {
		    for (String colValue : riga) {
			Paragraph p = new Paragraph(StringUtils.defaultIfEmpty(colValue, ""),
				FontFactory.getFont(FontFactory.HELVETICA, 8F, Font.NORMAL, new Color(0, 0, 255)));
			PdfPCell itemCell = new PdfPCell(p);
			table.addCell(itemCell);
		    }
		}
	    }
	}
	document.add(table);
	document.close();
	return baos.toByteArray();
    }

    private byte[] generateExcelContent(ExportTableHelper helper, String csvSeparator) throws Exception {

	XSSFWorkbook wb = new XSSFWorkbook();
	Sheet s = wb.createSheet();	
	List<String> colonne = helper.getColonne();
	org.apache.poi.ss.usermodel.Row row = s.createRow(0);
	int columncount = 0;
	if (colonne != null && !colonne.isEmpty()) {
	    for (String colonna : colonne) {
		Cell cell = row.createCell(columncount++);
		cell.setCellValue(colonna);
	    }
	}
	// renderer body
	int rowcount = 1;
	List<String[]> righe = helper.getRighe();
	if (righe != null) {
	    if (!righe.isEmpty()) {
		for (String[] riga : righe) {
		    columncount = 0;
		    org.apache.poi.ss.usermodel.Row r = s.createRow(rowcount++);
		    for (String colValue : riga) {
			Cell cell = r.createCell(columncount++);
			cell.setCellValue(colValue);
		    }
		}
	    }
	}
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	wb.write(baos);
	return baos.toByteArray();
    }

    /**
     * @param helper
     * @param csvSeparator
     * @return
     */
    private byte[] generateCSVContent(ExportTableHelper helper, String csvSeparator) {

	StringBuffer result = new StringBuffer();
	List<String> colonne = helper.getColonne();
	if (colonne != null) {
	    if (colonne.size() > 0) {
		for (String colonna : colonne) {
		    result = result.append(StringUtils.defaultIfEmpty(colonna, "???")).append(csvSeparator);
		}
	    }
	}
	List<String[]> righe = helper.getRighe();
	if (righe != null) {
	    if (righe.size() > 0) {
		for (String[] riga : righe) {
		    result = result.append("\n");
		    for (String colValue : riga) {
			result = result.append(StringUtils.defaultIfEmpty(colValue, "")).append(csvSeparator);
		    }
		}
	    }
	}
	try {
	    return result.toString().getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	    return result.toString().getBytes();
	}
    }

    /**
     * Trasforma le informazioni passate in html (Stringa da passare alla jsp )
     * 
     * @param tableFacade
     * @param table
     * @return
     */
    private String renderTable(TableFacade tableFacade, Table table) {

	String output = null;
	tableFacade.setTable(table);
	if (!limit.isExported()) {
	    output = tableFacade.render();
	} else {
	    String x = tableFacade.render();
	}
	return output;
    }

    /**
     * Crea la table facade, in questo punto andremo settare l'id della tabella
     * 
     * @param idTable
     * @param request
     * @param response
     * @return
     */
    private TableFacade createTableFacade(String idTable, HttpServletRequest request, HttpServletResponse response) {

	// Ottengo dalla request l'id della table facade(JMesa)
	// genero una table facade
	HttpServletRequestSpringWebContext httpServletRequestSpringWebContext = new HttpServletRequestSpringWebContext(request);
	TableFacade tableFacade = TableFacadeFactory.createSpringTableFacade(idTable, httpServletRequestSpringWebContext);
	tableFacade.setStateAttr("restore");
	return tableFacade;
    }

    /**
     * <pre>
     * Il metodo crea la ComponentFactory. A questo livello saranno passati: 
     * 
     * 	1- I record da visulaiizare
     * 	2- I tipi di Export presenti
     * 	3- Il tipo di Export scelto, se siamo nel caso di aver scelto un export
     * &#64;param tableFacade
     * &#64;param response
     * &#64;return
     * </pre>
     */
    private ComponentFactory createFactory(TableFacade tableFacade, HttpServletResponse response, Boolean isExport) {

	// E' un metedo astratto che deve essre implementato nella classe che genera la tabella 
	// è utilizzato per recuperare la lista dei record che vogliamo visulaizzare 
	this.items = setItems();
	// Setto la lista di record da visualizzare alla table facade
	tableFacade.setItems(items);
	// definisco i tipi di export possibili
	if (isExport) {
	    tableFacade.setExportTypes(response, ExportType.CSV, ExportType.EXCEL, ExportType.PDFP);
	}
	// Ricavo l'oggetto limint della tabella che verrà utilizzato in fase di export 
	limit = tableFacade.getLimit();
	ComponentFactory factory = null;
	//Crea un ComponentFactory adeguato alla scelta.
	if (limit.isExported()) {
	    setHtmlProperties = false;
	    if (limit.getExportType().toParam().equals("csv")) {
		factory = new CsvComponentFactory(",", tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    if (limit.getExportType().toParam().equals("pdfp")) {
		factory = new ExportComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	    if (limit.getExportType().toParam().equals("excel")) {
		factory = new ExportComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	    }
	} else {
	    factory = new HtmlComponentFactory(tableFacade.getWebContext(), tableFacade.getCoreContext());
	}
	return factory;
    }

    /**
     * Crea un oggetto Row Di jemesa
     * 
     * @param factory
     * @return
     */
    private Row istanceRow(ComponentFactory factory) {

	Row row = factory.createRow();
	return row;
    }

    /**
     * Genera la tabella da mostrare /Esportare
     * 
     * @param factory
     * @param titolo
     * @return
     */
    private Table generateTable(ComponentFactory factory, String titolo) {

	// Generazione della tabella da esportare
	Table table = factory.createTable();
	if (facade.getLimit().getExportType() != null) {
	    // recupero dalla request del caption della tabella.
	    // il caption è utilizzato per dare il nome al file esportato
	    // e per il caption della tabella esportata
	    String caption = getMessageFromBundle(context, titolo, null);
	    table.setCaption(caption);
	}
	return table;
    }

    /**
     * Metodo per il recupero dell'etichetta associata alla chiave passata come argomento.
     * 
     * @see ApplicationContext#getMessage(String, Object[], java.util.Locale)
     * @param chiave
     * @param args
     *            lista di valori da sostituire nel caso in cul l'etichetta associata alla chiave presenti dei
     *            segnaposto es.{0}
     * @return //
     */
    public String getMessageFromBundle(ApplicationContext context, String chiave, Object[] args) {

	String message = "";
	try {
	    message = context.getMessage(chiave, args, LocaleContextHolder.getLocale());
	} catch (NoSuchMessageException e) {
	    message = "???" + chiave + "???";
	}
	return message;
    }

    protected ApplicationContext getContext() {

	return context;
    }

    /**
     * 
     * @return Oggetto Limit
     */
    protected Limit getLimit() {

	return limit;
    }

    /**
     * 
     * @return Oggetto Table facade
     */
    protected TableFacade getFacade() {

	return facade;
    }

    /**
     * <pre>
     * Il metodo fa la conversione tra un oggetto Filter (rappresenta la coppia [proprietà,valore] del filtro jmesa)
     * L'oggetto ritornato sarà l'oggetto che verrà passato al momento dell'invocazione del metodo
     * (Il metedo deve essere invocato al setItems())
     * &#64;param setFiltriJmesa
     * &#64;param oggetto
     * &#64;return
     * </pre>
     */
    public E getFilterJmesaToBean(FilterSet setFiltriJmesa, E oggetto) {

	// recupero il set di filtri passati a jmesa
	Collection<Filter> filters = setFiltriJmesa.getFilters();
	// cliclo tutti i filtri
	for (Filter filter : filters) {
	    try {
		// Controllo se la proprietà semplice o annidata.
		// Nel caso sia annidata (Es bean1.bean2.proprieta)
		if (filter.getProperty().contains(".")) {
		    // Faccio lo split con il regex "." e recupero metto il paht della proprietà
		    // in un array di stringhe.
		    String[] field = filter.getProperty().split("\\.");
		    Object object = null;
		    // Tramite la reflection faccio il get del primo valore dell'array [bean1]
		    Method get = oggetto.getClass().getMethod("get" + StringUtils.capitalize(field[0]));
		    object = get.invoke(oggetto, new Object[0]);
		    // ricorsivamente all'oggetto objec ricavato faccio il get usando il valore dell 'array
		    //[1,arrray.lenght-1]
		    for (int i = 1; i < field.length - 1; i++) {
			get = object.getClass().getMethod("get" + StringUtils.capitalize(field[i]));
			object = get.invoke(object, new Object[0]);
		    }
		    // L'ultimo campo dell'array rappresenta il valore mostrato sulla tabella e per cui vogliamo filtrare il risulato
		    // Il metodo get mi serve solo per recuperare il tipo di ogetto (Integer,String,Date....)
		    Method get2 = object.getClass().getMethod("get" + StringUtils.capitalize(field[field.length - 1]));
		    get2.getReturnType();
		    // A questo punto creo con la reflection il metodo set dela proprietà e setto il valore del filtro jmesa
		    Method set = object.getClass().getMethod("set" + StringUtils.capitalize(field[field.length - 1]), get2.getReturnType());
		    // L'oggetto con il tipo che la signatura del metodo si aspetta (Integer ,Date,String,..etc)
		    Object typePropertySet = convertStringToPrimitiveObject(filter.getValue(), get2.getReturnType().getName());
		    set.invoke(object, typePropertySet);
		} else {// Nel caso non sia annidata (proprieta)
			// esuguo subito il set della proprietà passata
		    Method get2 = oggetto.getClass().getMethod("get" + StringUtils.capitalize(filter.getProperty()));
		    Method set = oggetto.getClass().getMethod("set" + StringUtils.capitalize(filter.getProperty()), get2.getReturnType());
		    // L'oggetto con il tipo che la signatura del metodo si aspetta (Integer ,Date,String,..etc)
		    Object object = convertStringToPrimitiveObject(filter.getValue(), get2.getReturnType().getName());
		    set.invoke(oggetto, object);
		}
	    } catch (SecurityException e) {
		log.debug("SecurityException: " + e);
		e.printStackTrace();
	    } catch (NoSuchMethodException e) {
		log.debug("NoSuchMethodException " + e);
	    } catch (IllegalArgumentException e) {
		log.debug("IllegalArgumentException " + e);
	    } catch (IllegalAccessException e) {
		log.debug("IllegalAccessException " + e);
	    } catch (InvocationTargetException e) {
		log.debug("InvocationTargetException " + e);
	    }
	}
	return oggetto;
    }

    /**
     * Recupera il valore iniziale delle righe per pagina
     * 
     * @return
     */
    public int getStartRowPage() {

	return getFacade().getLimit().getRowSelect().getRowStart();
    }

    /**
     * Recupera il valore finale delle righe per pagina
     * 
     * @return
     */
    public int getEndRowPage() {

	return getFacade().getLimit().getRowSelect().getRowEnd() - getFacade().getLimit().getRowSelect().getRowStart();
    }

    /**
     * Ritorna l'oggetto passato popolato con i campi passati sui filtri di jmesa
     * 
     * @param oggetto
     * @return
     */
    public E getFilterQuery(E oggetto) {

	FilterSet filterSet = getFacade().getLimit().getFilterSet();
	return getFilterJmesaToBean(filterSet, oggetto);
    }

    /**
     * <pre>
     * Metodo che permette di aggiungere filtri custom uno o più campi della lista da filtrare ed esportare
     * Di deafault il metodo non associa nessun filtro particolare.
     * Il metodo potrà essere implementato quando si va a creare la lista jmesa con i filtri necessari.
     * &#64;param tableFacade
     * 
     * </pre>
     */
    protected abstract void addFilterFilterMatchMap(TableFacade tableFacade);

    /**
     * <pre>
     * 
     * Metodo che permette di aggiungere alla tabella le i campi da mostrare ed esportare
     * Il metodo dovrà essere implementato nella classe che definisce la tabella 
     * Il metodo conterrà N istanze di JmesaColumn  quante sono le colonne della nostra tabella che vogliamo creare
     * &#64;param request
     * &#64;param table
     * &#64;param row
     * &#64;param factory
     * &#64;param tableFacade
     * &#64;param setHtmlProperties
     * </pre>
     */
    protected abstract void addField(HttpServletRequest request, Table table, Row row, ComponentFactory factory, TableFacade tableFacade,
	    boolean setHtmlProperties);

    /**
     * Il metodo sarà implementato nella classe che creerà la tabella. Il metodo dovrà recuperare il filtri applicati,
     * se ce ne sono, ed effettuare la query sulla tabella da cui vogliamo recuperare i valori.
     * 
     * @return
     */
    protected abstract Collection<?> setItems();

    /**
     * 
     * @return
     */
    public int getTotalRows() {

	return getFacade().getLimit().getRowSelect().getTotalRows();
    }

    /**
     * Il metodo converte il valore stringa che viene passato dalla tabella jmesa nel valore primitivo che dobbiamo
     * settare al nostro filtro
     * 
     * @param filterValue
     * @param type
     * @return
     */
    private Object convertStringToPrimitiveObject(String filterValue, String type) {

	Object risultato = null;
	if (type.contains("Date")) {
	    risultato = new GregorianCalendar();
	    risultato = Utilities.getDate(filterValue, WebConstants.DATE_FORMAT_PATTERN).getTime();
	}
	if (type.contains("Integer"))
	    risultato = (Integer) new Integer(filterValue);
	if (type.contains("String"))
	    risultato = (String) filterValue;
	if (type.contains("Boolean"))
	    risultato = Boolean.valueOf(filterValue);
	return risultato;
    }

    public ExportTableHelper generateTableHelper() {

	return null;
    }
}
