package it.gruppoinit.pal.gp.core.web.util;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

public class BindingInitializer implements WebBindingInitializer {

    public void initBinder(WebDataBinder binder, WebRequest request) {

	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	dateFormat.setLenient(false);
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
	DecimalFormat decFormat = (DecimalFormat) format;
	decFormat.applyPattern("#0.00###");
	decFormat.setGroupingUsed(false);
	binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, decFormat, true));
	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	binder.registerCustomEditor(XMLGregorianCalendar.class, new CustomXMLGregorianCalendarEditor(dateFormat, true));
    }
}
