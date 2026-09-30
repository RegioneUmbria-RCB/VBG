package it.gruppoinit.pal.gp.core.features.segnaposto;

import org.apache.commons.lang.StringUtils;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

public class SegnapostoTemplateODTWrapper {

    private ISegnapostoService service;

    public SegnapostoTemplateODTWrapper(ISegnapostoService service) {

	this.service = service;
    }

    public TableODT getTemplate(String segnaposto) {

	String template = this.service.getTemplateHTML(segnaposto);
	if (StringUtils.isEmpty(template)) {
	    return null;
	}
	try {
	    TextDocument document = TextDocument.newTextDocument();
	    String[] colonneIntestazione = this.getColonneIntestazione(template);
	    Table table = document.addTable(1, colonneIntestazione.length - 1);
	    for (int i = 1; i < colonneIntestazione.length; i++) {
		String testo = colonneIntestazione[i];
		Cell cella = table.getCellByPosition(i - 1, 0);
		cella.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.DEFAULT);
		if (testo.indexOf("<CENTER>") > -1) {
		    cella.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		    testo = testo.replace("<CENTER>", "");
		    testo = testo.replace("</CENTER>", "");
		}
		cella.setStringValue(testo);
	    }
	    table.appendRow();
	    String[] colonneDettaglio = this.getColonneDettaglio(template);
	    for (int i = 1; i < colonneDettaglio.length; i++) {
		String testo = colonneDettaglio[i];
		Cell cella = table.getCellByPosition(i - 1, 1);
		cella.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.DEFAULT);
		if (testo.indexOf("<CENTER>") > -1) {
		    cella.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		    testo = testo.replace("<CENTER>", "");
		    testo = testo.replace("</CENTER>", "");
		}
		cella.setStringValue(testo);
	    }
	    document.close();
	    return new TableODT(table, this.hasBorders(template));
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private String[] getColonneIntestazione(String template) {

	String testo = template.substring(template.indexOf("<THEAD>") + 7);
	testo = testo.substring(0, testo.indexOf("</THEAD>"));
	testo = testo.substring(testo.indexOf("<TR>") + 4);
	testo = testo.substring(0, testo.indexOf("</TR>"));
	testo = testo.replace("</TH>", "");
	return testo.split("<TH>");
    }

    private String[] getColonneDettaglio(String template) {

	String testo = template.substring(template.indexOf("<TBODY>") + 7);
	testo = testo.substring(0, testo.indexOf("</TBODY>"));
	testo = testo.substring(testo.indexOf("<TR>") + 4);
	testo = testo.substring(0, testo.indexOf("</TR>"));
	testo = testo.replace("</TD>", "");
	return testo.split("<TD>");
    }

    private boolean hasBorders(String template) {

	return (template.toUpperCase().indexOf("BORDER") >= 0);
    }
}
