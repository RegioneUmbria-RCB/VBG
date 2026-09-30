package it.gruppoinit.pal.gp.backoffice.web.util;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

/**
 * Codice Ripreso da {@link org.springframework.beans.propertyeditors.StringTrimmerEditor} per dare la possibilità di
 * decidere se trimmare o meno la stringa. Il problema era che la classifica del protocollo non doveva venire salvata
 * con il trim degli spazi.
 * 
 * 
 */
public class CustomStringTrimmerEditor extends PropertyEditorSupport {

    private final String charsToDelete;
    private final boolean emptyAsNull;
    private final boolean doTrim;

    /**
     * Create a new StringTrimmerEditor.
     * 
     * @param emptyAsNull
     *            <code>true</code> if an empty String is to be transformed into <code>null</code>
     * @param doTrim
     *            <code>true</code> if the String is to be trimmed
     */
    public CustomStringTrimmerEditor(boolean emptyAsNull, boolean doTrim) {

	this.doTrim = doTrim;
	this.charsToDelete = null;
	this.emptyAsNull = emptyAsNull;
    }

    /**
     * Create a new StringTrimmerEditor.
     * 
     * @param charsToDelete
     *            a set of characters to delete, in addition to trimming an input String. Useful for deleting unwanted
     *            line breaks: e.g. "\r\n\f" will delete all new lines and line feeds in a String.
     * 
     * @param emptyAsNull
     *            <code>true</code> if an empty String is to be transformed into <code>null</code>
     * @param doTrim
     *            <code>true</code> if the String is to be trimmed
     */
    public CustomStringTrimmerEditor(String charsToDelete, boolean emptyAsNull, boolean doTrim) {

	this.doTrim = doTrim;
	this.charsToDelete = charsToDelete;
	this.emptyAsNull = emptyAsNull;
    }

    public void setAsText(String text) {

	if (text == null) {
	    setValue(null);
	} else {
	    String value = this.doTrim == true ? text.trim() : text;
	    if (this.charsToDelete != null) {
		value = StringUtils.deleteAny(value, this.charsToDelete);
	    }
	    if (this.emptyAsNull && "".equals(value)) {
		setValue(null);
	    } else {
		setValue(value);
	    }
	}
    }

    public String getAsText() {

	Object value = getValue();
	return (value != null ? value.toString() : "");
    }
}
