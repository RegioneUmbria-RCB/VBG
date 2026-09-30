package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.VerticalAlignmentType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.CellValueAdapter;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoComplessoResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ODTUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.SegnapostoTemplateODTWrapper;
import it.gruppoinit.pal.gp.core.features.segnaposto.TableODT;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

public class LinkallegatiNoHyperlinkODTResolver implements ISegnapostoComplessoResolver<TextDocument> {

    protected static final String SEGNAPOSTO_URL_LINK = "@URL_LINK@";
    protected static final String SEGNAPOSTO_URL = "@URL@";
    protected static final String SEGNAPOSTO_URL_SCARICA = "@URL_SCARICA@";
    protected static final String SEGNAPOSTO_NOMEFILE = "@NOMEFILE@";
    protected static final String SEGNAPOSTO_PIN = "@PIN@";
    protected static final String SEGNAPOSTO_DESCRIZIONE = "@DESCRIZIONE@";
    private static final Logger log = LoggerFactory.getLogger(LinkallegatiNoHyperlinkODTResolver.class);
    public static final String TAG = "LINKALLEGATI_NO_HYPERLINK";
    private MovimentiZipLogicoService zipLogicoService;
    private SegnapostoTemplateODTWrapper templateWrapper;
    private DocumentMergeHelper userData;
    private TextDocument textDoc;
    private TempLinkallegatiService tempLinkallegatiService;

    public LinkallegatiNoHyperlinkODTResolver(ISegnapostoService service, MovimentiZipLogicoService zipLogicoService,
	    TempLinkallegatiService tempLinkallegatiService, TextDocument textDoc, DocumentMergeHelper userData) {

	if (zipLogicoService == null) {
	    throw new IllegalArgumentException("Impossibile richiamare ZipLogicoTabellaHashODTResolver senza passare il service per la sostituzione");
	}
	this.zipLogicoService = zipLogicoService;
	this.templateWrapper = new SegnapostoTemplateODTWrapper(service);
	this.textDoc = textDoc;
	this.userData = userData;
	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    @Override
    public TextDocument sostituisciSegnapostoComplesso() {

	try {
	    // Recupero la lista
	    String uuid = userData.getUuidLinkTemp();
	    List<TempLinkallegati> tempLinks = new ArrayList<TempLinkallegati>();
	    if (StringUtils.isNotBlank(uuid)) {
		tempLinks = tempLinkallegatiService.findByUuid(uuid);
	    }
	    // Recupero il template ODT
	    TableODT template = this.templateWrapper.getTemplate(TAG);
	    Table tableTemplate = template.getTable();
	    Iterator<Paragraph> paragraf = this.textDoc.getParagraphIterator();
	    Font font = new ODTUtils().getFontFromParagraphIterator(paragraf, TAG, true);
	    // Preparo la nuova tabella
	    TextDocument retVal = TextDocument.newTextDocument();
	    Table table = retVal.addTable(tempLinks.size() + 1, tableTemplate.getColumnCount());
	    //4. Sostituisco l'intestazione della tabella
	    int rowIndex = 0;
	    for (int i = 0; i < tableTemplate.getColumnCount(); i++) {
		Cell cellaTemplate = tableTemplate.getCellByPosition(i, 0);
		Cell cella = table.getCellByPosition(i, rowIndex);
		cella.setValueType("string");
		cella.setDisplayText(cellaTemplate.getDisplayText());
		cella.setHorizontalAlignment(cellaTemplate.getHorizontalAlignmentType());
		if (!template.isBordi()) {
		    cella.setBorders(CellBordersType.NONE, Border.NONE);
		}
		if (font != null) {
		    font.setFontStyle(StyleTypeDefinitions.FontStyle.BOLD);
		    font.setSize(10);
		    cella.setFont(font);
		}
	    }
	    rowIndex++;
	    // Sostituisco i valori
	    for (TempLinkallegati tempLinkallegati : tempLinks) {
		for (int i = 0; i < tableTemplate.getColumnCount(); i++) {
		    Cell cellaTemplate = tableTemplate.getCellByPosition(i, 1);
		    String testo = cellaTemplate.getDisplayText();
		    log.debug("testo cella {} {}", i, testo);
		    Cell cella = table.getCellByPosition(i, rowIndex);
		    if (testo.indexOf(SEGNAPOSTO_URL_SCARICA) >= 0 || testo.indexOf(SEGNAPOSTO_URL_LINK) >= 0) {
			replaceStringaComplessa(testo, tempLinkallegati, cellaTemplate, cella, template, font);
			continue;
		    }
		    testo = testo.replace(SEGNAPOSTO_URL, tempLinkallegati.getLink());
		    testo = testo.replace(SEGNAPOSTO_NOMEFILE, tempLinkallegati.getNomedocumento());
		    testo = testo.replace(SEGNAPOSTO_DESCRIZIONE, FormatUtils.stringFormat(tempLinkallegati.getDescrizioneDocumento()));
		    testo = testo.replace(SEGNAPOSTO_PIN, tempLinkallegati.getPin().toString());
		    cella.setValueType("string");
		    log.debug("testo cella {} {} {}", new Object[] { i, rowIndex, testo });
		    cella.setDisplayText(testo, new CellValueAdapter() {

			@Override
			public void adaptValue(Cell cell, String value) {

			    cell.setStringValue(value);
			}
		    });
		    cella.setHorizontalAlignment(cellaTemplate.getHorizontalAlignmentType());
		    if (!template.isBordi()) {
			cella.setBorders(CellBordersType.NONE, Border.NONE);
		    }
		    if (font != null) {
			cella.setFont(new Font(font.getFamilyName(), font.getFontStyle(), 8));
		    }
		}
		rowIndex++;
	    }
	    return retVal;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private void replaceStringaComplessa(String testo, TempLinkallegati tempLinkallegati, Cell cellaTemplate, Cell cella, TableODT template,
	    Font font) {

	// o è presente uno o l'altro altrimenti errore di configurazione
	if (testo.indexOf(SEGNAPOSTO_URL_SCARICA) >= 0) {
	    replaceScaricaDocumentazione(testo, tempLinkallegati, cella);
	}
	if (testo.indexOf(SEGNAPOSTO_URL_LINK) >= 0) {
	    replaceScaricaLink(testo, tempLinkallegati, cella);
	}
	cella.setHorizontalAlignment(cellaTemplate.getHorizontalAlignmentType());
	if (!template.isBordi()) {
	    cella.setBorders(CellBordersType.NONE, Border.NONE);
	}
	if (font != null) {
	    cella.setFont(new Font(font.getFamilyName(), font.getFontStyle(), 8));
	}
    }

    private void replaceScaricaLink(String testo, TempLinkallegati tempLinkallegati, Cell cella) {

	String[] testoSostituito = testo.split(SEGNAPOSTO_URL_LINK);
	if (cella.getParagraphIterator() != null && cella.getParagraphIterator().hasNext()) {
	    cella.getParagraphIterator().next().remove();
	}
	Paragraph p1 = cella.addParagraph("");
	if (testoSostituito == null || testoSostituito.length == 0) {
	    // è la stringa esatta "@URL_LINK@"
	    try {
		p1.appendHyperlink(tempLinkallegati.getLink(), new URI(tempLinkallegati.getLink()));
	    } catch (URISyntaxException e) {
		log.error("Errore nell'elaborazione del link " + tempLinkallegati.getLink(), e);
		p1.appendTextContent(tempLinkallegati.getLink());
	    }
	} else {
	    for (int i = 0; i < testoSostituito.length; i++) {
		if ((i % 2) == 0) {
		    p1.appendTextContent(testoSostituito[i]);
		    try {
			p1.appendHyperlink(tempLinkallegati.getLink(), new URI(tempLinkallegati.getLink()));
		    } catch (URISyntaxException e) {
			log.error("Errore nell'elaborazione del link " + tempLinkallegati.getLink(), e);
			p1.appendTextContent(tempLinkallegati.getLink());
		    }
		}
	    }
	}
	cella.setVerticalAlignment(VerticalAlignmentType.TOP);
    }

    private void replaceScaricaDocumentazione(String testo, TempLinkallegati tempLinkallegati, Cell cella) {

	String[] testoSostituito = testo.split(SEGNAPOSTO_URL_SCARICA);
	if (cella.getParagraphIterator() != null && cella.getParagraphIterator().hasNext()) {
	    cella.getParagraphIterator().next().remove();
	}
	Paragraph p1 = cella.addParagraph("");
	if (testoSostituito == null || testoSostituito.length == 0) {
	    // è la stringa esatta @URL_SCARICA@	    
	    try {
		p1.appendHyperlink("(Scarica il documento)", new URI(tempLinkallegati.getLink()));
	    } catch (URISyntaxException e) {
		log.error("Errore nell'elaborazione del link " + tempLinkallegati.getLink(), e);
		p1.appendTextContent(tempLinkallegati.getLink());
	    }
	} else {
	    for (int i = 0; i < testoSostituito.length; i++) {
		if ((i % 2) == 0) {
		    p1.appendTextContent(testoSostituito[i]);
		    try {
			p1.appendHyperlink("(Scarica il documento)", new URI(tempLinkallegati.getLink()));
		    } catch (URISyntaxException e) {
			log.error("Errore nell'elaborazione del link " + tempLinkallegati.getLink(), e);
			p1.appendTextContent(tempLinkallegati.getLink());
		    }
		}
	    }
	}
	cella.setVerticalAlignment(VerticalAlignmentType.TOP);
    }
}
