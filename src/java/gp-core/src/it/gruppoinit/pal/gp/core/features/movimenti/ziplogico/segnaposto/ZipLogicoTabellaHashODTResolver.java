package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.CellValueAdapter;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoDTO;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.NumeroDataProtocolloZipLogico;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoComplessoResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ODTUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.SegnapostoTemplateODTWrapper;
import it.gruppoinit.pal.gp.core.features.segnaposto.TableODT;

public class ZipLogicoTabellaHashODTResolver implements ISegnapostoComplessoResolver<TextDocument> {

    private static final Logger log = LoggerFactory.getLogger(ZipLogicoTabellaHashODTResolver.class);
    public static final String TAG = "ZIPLOGICO_TABELLA_HASH";
    private MovimentiZipLogicoService zipLogicoService;
    private SegnapostoTemplateODTWrapper templateWrapper;
    private Integer codiceMovimento;
    private TextDocument textDoc;

    public ZipLogicoTabellaHashODTResolver(ISegnapostoService service, MovimentiZipLogicoService zipLogicoService, TextDocument textDoc,
	    Movimenti movimento) {

	if (zipLogicoService == null) {
	    throw new IllegalArgumentException("Impossibile richiamare ZipLogicoTabellaHashODTResolver senza passare il service per la sostituzione");
	}
	if (movimento == null) {
	    throw new IllegalArgumentException("Impossibile richiamare ZipLogicoTabellaHashODTResolver senza passare il movimento");
	}
	this.zipLogicoService = zipLogicoService;
	this.templateWrapper = new SegnapostoTemplateODTWrapper(service);
	this.codiceMovimento = movimento.getId().getCodice();
	this.textDoc = textDoc;
    }

    @Override
    public TextDocument sostituisciSegnapostoComplesso() {

	try {
	    //1. Recupero la lista degli allegati degli zip logici
	    List<MovimentiZipLogicoDTO> zipLogico = this.zipLogicoService.findMovimentiZipLogicoDTOByMovimento(this.codiceMovimento);
	    //2. Recupero il template ODT
	    TableODT template = this.templateWrapper.getTemplate(TAG);
	    Table tableTemplate = template.getTable();
	    Iterator<Paragraph> paragraf = this.textDoc.getParagraphIterator();
	    Font font = new ODTUtils().getFontFromParagraphIterator(paragraf, TAG, true);
	    //3. Preparo la nuova tabella
	    TextDocument retVal = TextDocument.newTextDocument();
	    Table table = retVal.addTable(zipLogico.size() + 1, tableTemplate.getColumnCount());
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
	    //5. Sostituisco i valori
	    for (MovimentiZipLogicoDTO movimentiZipLogico : zipLogico) {
		for (int i = 0; i < tableTemplate.getColumnCount(); i++) {
		    Cell cellaTemplate = tableTemplate.getCellByPosition(i, 1);
		    String testo = cellaTemplate.getDisplayText();
		    log.debug("testo cella {} {}", i, testo);
		    testo = testo.replace(ZipLogicoSegnapostoConstants.NOME_FILE, movimentiZipLogico.getNomeFile());
		    testo = testo.replace(ZipLogicoSegnapostoConstants.DESCRIZIONE, movimentiZipLogico.getDescrizione());
		    testo = testo.replace(ZipLogicoSegnapostoConstants.SHA_256, this.zipLogicoService.insertOrGetSHA256(movimentiZipLogico.getCodiceOggetto()));
		    NumeroDataProtocolloZipLogico ndp = this.zipLogicoService.getNumeroDataProtocolloZipLogico(movimentiZipLogico);
		    testo = testo.replace(ZipLogicoSegnapostoConstants.NUMERO_PROTOCOLLO, StringUtils.defaultString(ndp.getNumeroProtocollo()));
		    testo = testo.replace(ZipLogicoSegnapostoConstants.DATA_PROTOCOLLO, StringUtils.defaultString(ndp.getDataProtocolloFormattata()));
		    Cell cella = table.getCellByPosition(i, rowIndex);
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
}
