package it.sgp.middleware.security.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import it.sgp.middleware.security.SecurityConstants;
import it.sgp.middleware.security.utils.Utilities;
import it.sgp.middleware.security.validation.AddConstraintViolationsToErrors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

public abstract class BaseController<E> {

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

	String[] denylist = new String[] { "class.*", "Class.*", "*.class.*", "*.Class.*" };
	binder.setDisallowedFields(denylist);
    }

    /**
     * Limiti di risultati nelle ricerche ajax
     */
    protected int ajaxResultListLimit = 10;
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private ApplicationContext context;

    /**
     * metodo per aggiungere al model passato bean e collection non presente nell'entity di riferimento
     * 
     * @param model
     */
    protected abstract void setPageAttributes(Model model);

    /**
     * metodo da utilizzare prima delle insert e update per settare a null le property che contengono istanze vuote
     * (senza chiave primaria).
     * 
     * @param entity
     */
    protected abstract void fixMergeEntityProperty(E entity);

    /**
     * metodo da utilizzare prima della view per associare oggetti vuoti a property nulle
     * 
     * @param entity
     */
    protected abstract void fixRenderEntityProperty(E entity);

    /**
     * metodo per la lettura di un file da un url esterno tramite HttpClient
     * 
     * @param url
     * @return un array di byte con il contenuto del file
     *
     *         protected byte[] getContentFromHttpClientCall(String url) {
     * 
     *         byte[] responseBody = null; // Create an instance of HttpClient. HttpClient client = new HttpClient(); //
     *         Create a method instance. GetMethod method = new GetMethod(url); // Provide custom retry handler is
     *         necessary method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new
     *         DefaultHttpMethodRetryHandler(3, false)); try { // Execute the method. int statusCode =
     *         client.executeMethod(method); if (statusCode != HttpStatus.SC_OK) { log.error("Fatal http client return
     *         code: " + statusCode); return null; } // Read the response body. responseBody = method.getResponseBody();
     *         } catch (HttpException e) { log.error("Fatal protocol violation: " + e.getMessage()); } catch
     *         (IOException e) { log.error("Fatal transport error: " + e.getMessage()); } finally { // Release the
     *         connection. method.releaseConnection(); } return responseBody; }
     * 
     *         /** metodo per il recupero dell'etichetta associata alla chiave passata come argomento.
     * 
     * @see ApplicationContext#getMessage(String, Object[], java.util.Locale)
     * @param chiave
     * @param args
     *            lista di valori da sostituire nel caso in cul l'etichetta associata alla chiave presenti dei
     *            segnaposto es.{0}
     * @return
     */
    protected String getMessageFromBundle(String chiave, Object[] args) {

	String message = "";
	try {
	    message = context.getMessage(chiave, args, LocaleContextHolder.getLocale());
	} catch (NoSuchMessageException e) {
	    log.error(e.getMessage());
	    message = "???" + chiave + "???";
	}
	return message;
    }

    protected void copyErrorsToBindingResult(Exception errori, BindingResult result, Object entityOrCommand, boolean isCommand, String errorMessage) {

	result.reject("03", null, "Operazione Fallita");
	if (errori instanceof ConstraintViolationException) {
	    new AddConstraintViolationsToErrors().addConstraintViolations(((ConstraintViolationException) errori).getConstraintViolations(), result,
		    isCommand ? "entity" : null);
	} else {
	    result.reject("04", new Object[] { errorMessage }, "...");
	}
    }

    protected String getAjaxLimitExceedResultString(int listSize) {

	return "</ul><span>la ricerca ha tornato [".concat(String.valueOf(listSize))
		.concat("] record.<br />Digitare più caratteri per raffinare la ricerca.</span>");
    }

    protected PageRequest getPageFromRequest(Optional<Integer> page, Optional<Integer> size, HttpServletRequest request) {

	int currentPage = page.orElse(1);
	int pageSize = size.orElse(SecurityConstants.TABLES_DEFAULT_PAGE_SIZE);
	String sortField = request.getParameter("tableSortField");
	List<Order> orders = new ArrayList<>();
	if (StringUtils.isNotBlank(sortField)) {
	    String campiDaOrdinare[] = sortField.trim().split("|");
	    for (String campo : campiDaOrdinare) {
		String fields[] = campo.split(",");
		if (fields.length == 2) {
		    String field = StringUtils.defaultString(fields[0]).trim();
		    String sortDirection = StringUtils.defaultString(fields[0]).trim();
		    if (StringUtils.isNoneBlank(field, sortDirection)) {
			Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			orders.add(new Order(direction, field));
		    }
		}
	    }
	} else {
	    orders.add(getListDefaultOrder());
	}
	return PageRequest.of(currentPage - 1, pageSize, orders.isEmpty() ? Sort.unsorted() : Sort.by(orders));
    }

    public abstract Order getListDefaultOrder();

    protected Example<E> getExampleFromRequest(Model model, HttpServletRequest request, E instance) {

	Map<String, String[]> mFilterFields = request.getParameterMap();
	Map<String, String> mappaChiaviValori = new HashMap<>();
	for (Entry<String, String[]> m : mFilterFields.entrySet()) {
	    String[] value = m.getValue();
	    if (StringUtils.isNotBlank(m.getKey()) && // 
		    value != null && // 
		    value.length > 0 && // 
		    m.getKey().startsWith("table-filterable-element-") && //
		    StringUtils.isNotBlank(value[0])) {
		mappaChiaviValori.put(m.getKey(), value[0]); // lo rimetto in request così lo posso rileggere
		String propertyName = m.getKey().replace("table-filterable-element-", "");
		set(instance, propertyName, value[0]);
	    }
	}
	model.addAttribute("listaValoriFiltriTabelle", mappaChiaviValori);
	Example<E> ex = Example.of(instance, //
		ExampleMatcher.matching(). //
			withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING). //
			withIgnoreNullValues().//
			withIgnoreCase());
	return ex;
    }

    private static boolean set(Object object, String fieldName, String fieldValue) {

	Class<?> clazz = object.getClass();
	while (clazz != null) {
	    try {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		if (field.getType().isAssignableFrom(Date.class)) {
		    if (fieldValue.indexOf("-") > 0) {
			Calendar d = Utilities.getDateFromFormat(fieldValue, "yyyy-MM-dd");
			field.set(object, d.getTime());
		    } else if (fieldValue.indexOf("/") > 0) {
			Calendar d = Utilities.getDateFromStandardFormat(fieldValue);
			field.set(object, d.getTime());
		    }
		} else if (field.getType().isAssignableFrom(Integer.class)) {
		    if (Utilities.isInteger(fieldValue)) {
			field.set(object, Integer.parseInt(fieldValue.trim()));
		    }
		} else if (field.getType().isAssignableFrom(Boolean.class)) {
		    if (fieldValue.equalsIgnoreCase("true") || fieldValue.equalsIgnoreCase("false") || fieldValue.equalsIgnoreCase("1")
			    || fieldValue.equalsIgnoreCase("0")) {
			field.set(object, BooleanUtils.toBooleanObject(fieldValue));
		    }
		} else if (field.getType().isAssignableFrom(String.class)) {
		    field.set(object, fieldValue);
		} else {
		    log.error("Filtro con valore {} non applicato al campo {} di tipo {}", fieldValue, fieldName, field.getType());
		}
		return true;
	    } catch (NoSuchFieldException e) {
		clazz = clazz.getSuperclass();
	    } catch (Exception e) {
		throw new IllegalStateException(e);
	    }
	}
	return false;
    }
}