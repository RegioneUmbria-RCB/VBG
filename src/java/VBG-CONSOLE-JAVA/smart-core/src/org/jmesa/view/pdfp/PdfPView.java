/*
 * Copyright 2004 original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.jmesa.view.pdfp;

import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.Font.NORMAL;
import static com.lowagie.text.FontFactory.HELVETICA;
import static com.lowagie.text.FontFactory.getFont;
import static com.lowagie.text.pdf.BaseFont.NOT_EMBEDDED;
import static com.lowagie.text.pdf.BaseFont.createFont;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jmesa.view.ExportConstants.PDF_FONT_ENCODING;
import static org.jmesa.view.ExportConstants.PDF_FONT_NAME;
import static org.jmesa.view.ViewUtils.isRowEven;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.security.LoggedUser;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractExportView;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * A PDF view that uses the iText PdfPTable.
 * 
 * @since 2.3.4
 * @author Ismail Seyfi
 * @author francescop
 */
public class PdfPView extends AbstractExportView {

    private Logger logger = LoggerFactory.getLogger(PdfPView.class);
    private WebContext webContext;
    private Color evenCellBackgroundColor;
    private Color oddCellBackgroundColor;
    private Color headerBackgroundColor;
    private Color headerFontColor;
    private Color captionFontColor;
    private String captionAlignment;

    public PdfPView(Table table, Toolbar toolbar, WebContext webContext, CoreContext coreContext) {

	super(table, coreContext);
	this.webContext = webContext;
	this.evenCellBackgroundColor = new Color(227, 227, 227);
	this.oddCellBackgroundColor = new Color(255, 255, 255);
	// RGB jmesa table in gp-backoffice: 24,44,51
	// RGB jmesa table default: 114, 159, 207
	this.headerBackgroundColor = new Color(24, 44, 51);
	this.headerFontColor = new Color(255, 255, 255);
	this.captionFontColor = new Color(0, 0, 0);
	this.captionAlignment = "center";
    }

    public byte[] getBytes() {

	return null;
    }

    public Paragraph getTableCaption() throws Exception {

	Paragraph p = new Paragraph(getTable().getCaption(), getFont(HELVETICA, 18, BOLD, getCaptionFontColor()));
	p.setAlignment(getCaptionAlignment());
	return p;
    }

    public PdfPTable render() {

	PdfPTable pdfpTable = new PdfPTable(getTable().getRow().getColumns().size());
	pdfpTable.setSpacingBefore(3);
	pdfpTable.setWidthPercentage(100);
	Row row = getTable().getRow();
	List<Column> columns = row.getColumns();
	// build table headers
	for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
	    Column column = iter.next();
	    PdfPCell cell = new PdfPCell(new Paragraph(column.getTitle(), getHeaderCellFont()));
	    cell.setPadding(3.0f);
	    cell.setBackgroundColor(getHeaderBackgroundColor());
	    pdfpTable.addCell(cell);
	}
	// build table body
	Collection<?> items = getCoreContext().getPageItems();
	int rowcount = 0;
	for (Object item : items) {
	    rowcount++;
	    columns = row.getColumns();
	    for (Iterator<Column> iter = columns.iterator(); iter.hasNext();) {
		Column column = iter.next();
		String property = column.getProperty();
		if (property != null) {
		    PdfPCell cell = null;
		    Object value = column.getCellRenderer().getCellEditor().getValue(item, property, rowcount);
		    // START: Format BigDecimal
		    // Controllo se il valore è un BigDecimal in tal caso lo formatto ###,##0.00
		    if (value instanceof BigDecimal) {
			NumberFormat numberFormat = new DecimalFormat("###,##0.00");
			String valueFormat = numberFormat.format(((BigDecimal) value).doubleValue());
			// aggiunto controllo se value is null. value sarà null quando si ha una property null.
			cell = new PdfPCell(new Paragraph(valueFormat, getCellFont()));
			// END: Format BigDecimal
		    } else {
			// aggiunto controllo se value is null. value sarà null quando si ha una property null.
			cell = new PdfPCell(new Paragraph(value == null ? "" : String.valueOf(value), getCellFont()));
		    }
		    cell.setPadding(3.0f);
		    if (isRowEven(rowcount)) {
			cell.setBackgroundColor(getEvenCellBackgroundColor());
		    } else {
			cell.setBackgroundColor(getOddCellBackgroundColor());
		    }
		    pdfpTable.addCell(cell);
		}
	    }
	}
	return pdfpTable;
    }

    public String getCaptionAlignment() {

	return captionAlignment;
    }

    public void setCaptionAlignment(String captionAlignment) {

	this.captionAlignment = captionAlignment;
    }

    public Color getCaptionFontColor() {

	return captionFontColor;
    }

    public void setCaptionFontColor(Color captionFontColor) {

	this.captionFontColor = captionFontColor;
    }

    public Color getHeaderBackgroundColor() {

	return headerBackgroundColor;
    }

    public void setHeaderBackgroundColor(Color headerBackgroundColor) {

	this.headerBackgroundColor = headerBackgroundColor;
    }

    /**
     * Create either the default helvetica 12 point font, or specify the font name and encoding in the preferences.
     * Either way it will use the header font color.
     * 
     * <p>
     * The preference settings are the following: export.pdf.fontName export.pdf.fontEncoding
     * </p>
     */
    public Font getHeaderCellFont() {

	return getFontWithColor(getHeaderFontColor());
    }

    public Color getHeaderFontColor() {

	return headerFontColor;
    }

    public void setHeaderFontColor(Color headerFontColor) {

	this.headerFontColor = headerFontColor;
    }

    /**
     * Create either the default helvetica 12 point font, or specify the font name and encoding in the preferences.
     * 
     * <p>
     * The preference settings are the following: export.pdf.fontName export.pdf.fontEncoding
     * </p>
     */
    public Font getCellFont() {

	return getFontWithColor(null);
    }

    public Color getEvenCellBackgroundColor() {

	return evenCellBackgroundColor;
    }

    public void setEvenCellBackgroundColor(Color evenCellBackgroundColor) {

	this.evenCellBackgroundColor = evenCellBackgroundColor;
    }

    public Color getOddCellBackgroundColor() {

	return oddCellBackgroundColor;
    }

    public void setOddCellBackgroundColor(Color oddCellBackgroundColor) {

	this.oddCellBackgroundColor = oddCellBackgroundColor;
    }

    private Font getFontWithColor(Color color) {

	SecurityContext sc = SecurityContextHolder.getContext();
	UserDetails ud = (UserDetails) sc.getAuthentication().getPrincipal();
	LoggedUser user = (LoggedUser) ud;
	String stileBO = (String) user.getImpostazioniUtente().get(WebConstants.CSS_USER_PREF_STYLE);
	String styleMSgiallo = getCoreContext().getMessage("export.pdf.namestyle.giallo");
	String fontName = getCoreContext().getPreference(PDF_FONT_NAME);
	String fontEncoding = getCoreContext().getPreference(PDF_FONT_ENCODING);
	if (isNotBlank(fontName) && isNotBlank(fontEncoding)) {
	    try {
		BaseFont baseFont = createFont(fontName, fontEncoding, NOT_EMBEDDED);
		if (color != null) {
		    if (stileBO.equals(styleMSgiallo)) {
			return new Font(baseFont, 16, 0, color);
		    } else {
			return new Font(baseFont, 12, 0, color);
		    }
		}
		if (stileBO.equals(styleMSgiallo)) {
		    return new Font(baseFont, 16, 0);
		} else {
		    return new Font(baseFont, 12, 0);
		}
	    } catch (Exception e) {
		logger.warn("Not able to create the requested font for the PDF export...will use the export.");
	    }
	}
	if (color != null) {
	    if (stileBO.equals(styleMSgiallo)) {
		return getFont(HELVETICA, 16, NORMAL, color);
	    } else {
		return getFont(HELVETICA, 12, NORMAL, color);
	    }
	}
	if (stileBO.equals(styleMSgiallo)) {
	    return getFont(HELVETICA, 16, NORMAL);
	} else {
	    return getFont(HELVETICA, 12, NORMAL);
	}
    }

    protected WebContext getWebContext() {

	return webContext;
    }
}
