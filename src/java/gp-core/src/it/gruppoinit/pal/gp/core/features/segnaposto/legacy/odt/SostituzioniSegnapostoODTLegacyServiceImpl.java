package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.odt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.list.ListItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto.LinkallegatiNoHyperlinkODTResolver;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto.ZipLogicoTabellaHashODTResolver;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ODTUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.DatiOggettoLettera;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.OdtConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.RtfConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared.IIncapsulaPlaceholderValueByIfElseService;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared.SostituzioneNelModello;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared.TypeFiled;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.IOdtSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.ISostituzioneSegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.SegnapostoParser;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.StrutturaSegnaposto;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

@Service
public class SostituzioniSegnapostoODTLegacyServiceImpl implements ISostituzioniSegnapostoODTLegacyService {

    private final Logger log = LoggerFactory.getLogger(SostituzioniSegnapostoODTLegacyServiceImpl.class);
    private ISegnapostoService segnapostoService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private OggettiService oggettiService;
    private IIncapsulaPlaceholderValueByIfElseService incapsulaPlaceholderValueByIfElseService;
    private ISostituzioneSegnapostoService sostituzioneSegnapostoService;
    private TempLinkallegatiService tempLinkallegatiService;

    @Autowired
    public SostituzioniSegnapostoODTLegacyServiceImpl(ISegnapostoService segnapostoService, MovimentiZipLogicoService movimentiZipLogicoService,
	    OggettiService oggettiService, IIncapsulaPlaceholderValueByIfElseService incapsulaPlaceholderValueByIfElseService,
	    ISostituzioneSegnapostoService sostituzioneSegnapostoService, TempLinkallegatiService tempLinkallegatiService) {

	super();
	this.segnapostoService = segnapostoService;
	this.movimentiZipLogicoService = movimentiZipLogicoService;
	this.oggettiService = oggettiService;
	this.incapsulaPlaceholderValueByIfElseService = incapsulaPlaceholderValueByIfElseService;
	this.sostituzioneSegnapostoService = sostituzioneSegnapostoService;
	this.tempLinkallegatiService = tempLinkallegatiService;
    }

    @Override
    public byte[] effettuaSostituzioniBaseOdt(DocumentMergeHelper userData, DatiOggettoLettera oggettoLettera,
	    IUsefulDataForPlaceholderReplacement data) {

	log.debug("effettuaSostituzioniBaseOdt# DocumentoTipo di tipo odt");
	// Recupero l'oggetto TextDocument. E' l'oggetto su cui lavoriamo per modificare il modello odt caricato
	log.debug("effettuaSostituzioniBaseOdt# Creo il modello da cui creare il documento.");
	TextDocument textdoc = odtLoadTexdocument(oggettoLettera.getContenutoFile());
	// Cerca nel documento tutti gli elenchi con un segnaposto (Es: 1. [-NomeSegnaposto-]) ed effettua le sostituzioni
	log.debug("effettuaSostituzioniBaseOdt# Popola gli elenchi con segnaposto presenti nel documento");
	odtPopolaSegnapostoInListe(textdoc, data, userData);
	// Per ogni segna posto trovato lo sostituice con il valore recuperato per il segna posto
	log.debug("effettuaSostituzioniBaseOdt# Per ogni segnaposto trovato eseguo la sostituzione");
	odtPopolaSegnaPostiPresenti(textdoc, data, userData);
	log.debug("effettuaSostituzioniBaseOdt# Esamina il documento per applicare i caratteri TAB presenti");
	return odtApplicaCarattereTAB(textdoc);
    }

    private TextDocument odtLoadTexdocument(byte[] b) {

	InputStream is = new ByteArrayInputStream(b);
	try {
	    return TextDocument.loadDocument(is);
	} catch (Exception e) {
	    log.error("createTexdocument# Errore durante la creazione del modello: " + e.getMessage(), e);
	    throw new InvalidConfigurationException("Errore nel caricamento del modello " + e.getMessage(), e);
	}
    }

    private void odtPopolaSegnapostoInListe(TextDocument currentDocument, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	SegnapostoParser segnapostoParser = new SegnapostoParser();
	// Cerca nel documento tutte le liste presenti, nel caso una delle liste contiene un segnaposto del tipo
	// [-SEGNAPOSTO-]
	Iterator<org.odftoolkit.simple.text.list.List> listsIterator = currentDocument.getListIterator();
	while (listsIterator.hasNext()) {
	    org.odftoolkit.simple.text.list.List odtList = listsIterator.next();
	    List<ListItem> p = odtList.getItems();
	    // Se non ha elementi continuo
	    if (p.isEmpty()) {
		continue;
	    }
	    // Se è stato trovato almeno un elemento verifico se uno degli elementi della lista contiene un segnaposto
	    int indiceMatch = -1;
	    Iterator<ListItem> iterator = p.iterator();
	    while (iterator.hasNext()) {
		indiceMatch++;
		ListItem listItem = iterator.next();
		if (segnapostoVuotoONonCompatibileConElenchi(listItem)) {
		    continue;
		}
		String textContent = listItem.getTextContent();
		Matcher phMatcher = OdtConstants.PATTERN_SEGNAPOSTO.matcher(textContent);
		// Ho trovato un segnaposto in uno degli elementi della lista, sostituisco il segnaposto e se necessario
		// aggiungo gli ulteriori elementi alla lista
		while (phMatcher.find()) {
		    String placeholder = phMatcher.group();
		    StrutturaSegnaposto strutturaTag = segnapostoParser.analizza(placeholder);
		    IOdtSubstitution sostituzione = this.sostituzioneSegnapostoService.sostituisciOdt(currentDocument, strutturaTag, data, userData);
		    if (sostituzione != null) {
			sostituzione.applyTo(odtList, indiceMatch);
			continue;
		    }
		    // // // // // 
		    SostituzioneNelModello valoreSostituito = incapsulaPlaceholderValueByIfElseService.esegui(strutturaTag.toStringaSegnaposto(),
			    data, userData, TipoFileEnum.ODT);
		    if (valoreSostituito == null) {
			continue;
		    }
		    // I valori che andremo a sostitituire saranno sicuramente delle stringhe (!!! è vero???? - Nicola)
		    if (valoreSostituito.getType() == TypeFiled.STRING) {
			String[] valoriDaSostituire = valoreSostituito.getValori();
			if (valoriDaSostituire.length == 0 || StringUtils.isBlank(valoriDaSostituire[0])) {
			    continue;
			}
			odtSostituisciInLista(placeholder, odtList, indiceMatch, valoriDaSostituire);
		    }
		}
	    }
	}
    }

    private boolean segnapostoVuotoONonCompatibileConElenchi(ListItem listItem) {

	String textContent = listItem.getTextContent();
	if (StringUtils.isBlank(textContent)) {
	    return true;
	}
	boolean isCompatibile = odtIsSegnaPostoCompatibileConElenchi(textContent);
	if (!isCompatibile) {
	    // La gestione degli elenchi puntati per questi tipi di segna posto deve essere gestita ah hoc,
	    // vedi [-LINKALLEGATI-] in isLinkDocumentiODT
	    log.warn("popolaElenchiNelModello#Il segnaposto {} non è compatibile con gli elenchi puntati", textContent);
	    return true;
	}
	return false;
    }

    private List<String> odtSearchSegnapostoNelDocumento(TextDocument textdoc) {

	String espressioneRegolare = "\\[-.+?-\\]";
	log.debug("odtSearchSegnapostoNelDocumento# Recupero tutti i segnaposto presenti all'interno del documento, utilizzando la regex {}",
		espressioneRegolare);
	TextNavigation textNavigation = new TextNavigation(espressioneRegolare, textdoc);
	List<String> segnaPostoDocumento = new ArrayList<String>();
	while (textNavigation.hasNext()) {
	    TextSelection textSelection = (TextSelection) textNavigation.nextSelection();
	    String searchedText = textSelection.getText();
	    segnaPostoDocumento.add(creaEspressioneDiRicercaSegnaposto(searchedText));
	}
	return segnaPostoDocumento;
    }

    /***
     * Prende una stringa che rappresenta il valore di un segnaposto (es. [-TEST-] e lo trasforma in una stringa di
     * ricerca per un TextNavigation)
     * 
     * @param string
     * @return
     */
    private String creaEspressioneDiRicercaSegnaposto(String string) {

	int a = StringUtils.indexOf(string, "[");
	int a1 = StringUtils.indexOf(string, "]");
	string = StringUtils.substring(string, a + 1, a1);
	if (StringUtils.contains(string, "(") || StringUtils.contains(string, ")")) {
	    string = StringUtils.replace(string, "(", "\\(");
	    string = StringUtils.replace(string, ")", "\\)");
	}
	return "\\[" + string + "\\]";
    }

    private void odtPopolaSegnaPostiPresenti(TextDocument currentDocument, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	SegnapostoParser segnapostoParser = new SegnapostoParser();
	// Recupero tutti i segna posti presenti nel modello Es: [-NomeSegnaposto-]
	List<String> listaSegnapostoDocumento = odtSearchSegnapostoNelDocumento(currentDocument);
	// Ciclo tutti i segnaposti
	for (String espressioneRicercaDocumento : listaSegnapostoDocumento) {
	    // creo la regex per ritrovare il segnaposto nel documento
	    TextNavigation search = new TextNavigation(espressioneRicercaDocumento, currentDocument);
	    // Per ogni valore trovato eseguo la sostituizione
	    while (search.hasNext()) {
		TextSelection textSelection = (TextSelection) search.nextSelection();
		// // // // // 
		// Nuova logica di sostituzione
		// La chiamata deve restituire un TextDocument e se diverso da null deve chiamare textSelection.replacewith(TextDocument) e continuare
		StrutturaSegnaposto strutturaTag = segnapostoParser.analizza(textSelection.getText());
		IOdtSubstitution sostituzione = this.sostituzioneSegnapostoService.sostituisciOdt(currentDocument, strutturaTag, data, userData);
		if (sostituzione != null) {
		    sostituzione.applyTo(textSelection);
		    continue;
		}
		// // // // // 
		// Recupero il valore da sostituire in base alla vecchia logica di gestione segnaposto
		String tag = strutturaTag.toStringaSegnaposto();
		SostituzioneNelModello map = incapsulaPlaceholderValueByIfElseService.esegui(tag, data, userData, TipoFileEnum.ODT);
		if (map == null) {
		    continue;
		}
		// Controllo se il segnaposto dovrà essere sostituito con stringhe o un immagine
		String[] valoriDaSostituire = map.getValori();
		switch (map.getType()) {
		case STRING:
		    log.debug("popolaSegnaPostiPresenti# Il segna posto {} verrà sostituito con una stringa", tag);
		    // Recupero gli elementi da sostituire
		    if (valoriDaSostituire != null) {
			if (valoriDaSostituire.length <= 1) {
			    //l'array contiene un solo elemento
			    odtSostituisciInCampo(tag, textSelection, valoriDaSostituire);
			} else {
			    //l'array contiene n valori, verranno sostituiti utilizzanzo un ritorno a capo per ogni elemento
			    odtSostituisciCampoComeLista(currentDocument, textSelection, valoriDaSostituire);
			}
		    }
		    break;
		case HYPERLINK_DOC_LINK:
		    String[] elem = map.getValori();
		    log.debug("popolaSegnaPostiPresenti# Il segna posto {} verrà sostituito lista di hyperlink", tag);
		    odtSostituisciCampoComeListDocHyperLink(currentDocument, textSelection, elem);
		    break;
		case TABELLA_HASH_DOC:
		    log.debug("popolaSegnaPostiPresenti# Il segna posto {} verrà sostituito tabella hash", tag);
		    odtSostituisciCampoComeTabellaHash(currentDocument, textSelection, valoriDaSostituire);
		    break;
		case IMAGE:
		    log.debug("popolaSegnaPostiPresenti# Il segna posto {} verrà sostituito con un immagine", tag);
		    try {
			odtSostituisciInCampoImmagine(textSelection, new URI(valoriDaSostituire[0]));
		    } catch (URISyntaxException e) {
			log.error("popolaSegnaPostiPresenti#Errore durante il recupero dell'immagine da inserire {}", e.getMessage());
			// nella pagina sostituisco l'errore che si è generato
			valoriDaSostituire[0] = e.getMessage();
			odtSostituisciInCampo(tag, textSelection, valoriDaSostituire);
		    }
		    break;
		case ZIPLOGICO_TABELLA_HASH:
		    TextDocument doc = new ZipLogicoTabellaHashODTResolver(segnapostoService, movimentiZipLogicoService, currentDocument,
			    data.getMovimento()).sostituisciSegnapostoComplesso();
		    try {
			textSelection.replaceWith(doc);
		    } catch (Exception e) {
			log.error("popolaSegnaPostiPresenti# errore durante la sostituizionedel tag {}: {} {}",
				new Object[] { tag, e.getMessage(), e });
		    }
		    break;
		case LINKALLEGATI_NO_HYPERLINK:
		    TextDocument doc1 = new LinkallegatiNoHyperlinkODTResolver(segnapostoService, movimentiZipLogicoService, tempLinkallegatiService,
			    currentDocument, userData).sostituisciSegnapostoComplesso();
		    try {
			textSelection.replaceWith(doc1);
		    } catch (Exception e) {
			log.error("popolaSegnaPostiPresenti# errore durante la sostituizionedel tag {}: {} {}",
				new Object[] { tag, e.getMessage(), e });
		    }
		    break;
		case CONTEGGIO_ALLEGATI_HASH:
		    if (valoriDaSostituire != null) {
			if (valoriDaSostituire.length <= 1) {
			    //l'array contiene un solo elemento
			    odtSostituisciInCampo(tag, textSelection, valoriDaSostituire);
			} else {
			    //l'array contiene n valori, verranno sostituiti utilizzanzo un ritorno a capo per ogni elemento
			    odtSostituisciCampoComeLista(currentDocument, textSelection, valoriDaSostituire);
			}
		    }
		    break;
		}
		break;
	    }
	}
    }

    private byte[] odtApplicaCarattereTAB(TextDocument textdoc) {

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try {
	    // Applicata tab al docucumento
	    OdfPackage mPackage = textdoc.getPackage();
	    byte[] in = mPackage.getBytes(OdtConstants.FILE_CONTENT_PAKAGE_ODT);
	    String content = new String(in, "UTF-8");
	    content = content.replace(RtfConstants.RTF_TAB, OdtConstants.RTF_TAB_ODT);
	    mPackage.insert(content.getBytes("UTF-8"), OdtConstants.FILE_CONTENT_PAKAGE_ODT, OdtConstants.ESTENSIONE_FILE_XML);
	    TextDocument.loadDocument(mPackage);
	    // salva il documento cambiato.
	    textdoc.save(baos);
	    return baos.toByteArray();
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
	return null;
    }

    private boolean odtIsSegnaPostoCompatibileConElenchi(String sp) {

	if (sp.contains("[-LINKALLEGATI-]")) {
	    return false;
	}
	return true;
    }

    private void odtSostituisciInLista(String segnaposto, org.odftoolkit.simple.text.list.List odtList, int indice, String[] valoriDaSostituire) {

	log.debug("sostituisciInLista# Sostituisco nella lista il segnaposto {}", segnaposto);
	if (valoriDaSostituire == null || valoriDaSostituire.length == 0) {
	    return;
	}
	if (valoriDaSostituire.length == 1) {
	    List<ListItem> odfElement = odtList.getItems();
	    for (ListItem li : odfElement) {
		String textContent = li.getTextContent();
		textContent = textContent.replace(segnaposto, valoriDaSostituire[0]);
		li.setTextContent(textContent);
	    }
	    return;
	}
	// Per sostituire gli elementi di una lista la lista deve contenere un solo elemento
	if (odtList.getItems().size() == 1) {
	    for (int j = 0; j < valoriDaSostituire.length; j++) {
		odtList.addItem(valoriDaSostituire[j]);
	    }
	}
	odtList.removeItem(indice);
    }

    private void odtSostituisciInCampo(String segnaposto, TextSelection textSelection, String[] elementidasostituire) {

	try {
	    textSelection.replaceWith(elementidasostituire[0]);
	} catch (Exception e) {
	    log.error("sostituisciInCampo# Errore durante la sostituzione del tag {} : {}", new Object[] { segnaposto, e.getMessage() });
	}
    }

    private void odtSostituisciCampoComeLista(TextDocument textdoc, TextSelection textSelection, String[] elem) {

	// TODO: Questo metodo va rivisto pesantemente
	// in pratica scorre tutti i paragrafi del documento e quando ne trova uno
	// che contiene un segnaposto ne prende il font e lo applica ai vari
	// valori che va ad inserire nella selezione.
	// Dovrebbe riprendere il font del paragrafo che contiene lo specifico segnaposto
	// e non uno qualsiasi
	Iterator<Paragraph> paragraphIterator = textdoc.getParagraphIterator();
	while (paragraphIterator.hasNext()) {
	    Paragraph paragraph = paragraphIterator.next();
	    Matcher phMatcher = OdtConstants.PATTERN_SEGNAPOSTO.matcher(paragraph.getTextContent());
	    while (phMatcher.find()) {
		try {
		    TextDocument document = TextDocument.newTextDocument();
		    for (int i = 0; i < elem.length; i++) {
			Paragraph p1 = document.addParagraph(elem[i]);
			Font font = new ODTUtils().getFontFromParagraph(paragraph, textSelection.getText(), false);
			if (font != null) {
			    p1.setFont(font);
			}
		    }
		    textSelection.replaceWith(document);
		    return;
		} catch (Exception e) {
		    log.error("sostituisciCampoComeLista# errore durante la sostituizionedel tag {}: {} ",
			    new Object[] { textSelection.getText(), e.getMessage() });
		}
	    }
	}
    }

    private void odtSostituisciCampoComeListDocHyperLink(TextDocument textdoc, TextSelection textSelection, String[] elem) {

	if (elem == null) {
	    return;
	}
	// TODO: vedi commento al metodo odtSostituisciCampoComeLista
	Iterator<Paragraph> paragraphIterator = textdoc.getParagraphIterator();
	while (paragraphIterator.hasNext()) {
	    Paragraph paragraph = paragraphIterator.next();
	    Matcher phMatcher = OdtConstants.PATTERN_SEGNAPOSTO.matcher(paragraph.getTextContent());
	    while (phMatcher.find()) {
		try {
		    TextDocument document = TextDocument.newTextDocument();
		    for (int i = 0; i < elem.length; i++) {
			String[] campiDaSostiruire = elem[i].split(RtfConstants.SEPARATORE_LINK_ALLEGATI);
			String nomeFile = StringUtils.defaultString(campiDaSostiruire[0]);
			String descrizioneDocumento = StringUtils.defaultString(campiDaSostiruire[1]);
			String link = StringUtils.defaultString(campiDaSostiruire[2]);
			String pin = "";
			if (campiDaSostiruire.length > 4 && campiDaSostiruire[4] != null) {
			    pin = StringUtils.defaultString(campiDaSostiruire[4]);
			}
			Paragraph p1 = document.addParagraph(descrizioneDocumento);
			Font font = new ODTUtils().getFontFromParagraph(paragraph, textSelection.getText(), false);
			if (font != null) {
			    p1.setFont(font);
			}
			p1.setHorizontalAlignment(paragraph.getHorizontalAlignment());
			p1.setStyleName(paragraph.getStyleName());
			p1.appendTextContent(" (Scarica ");
			p1.appendHyperlink(nomeFile, new URI(link));
			p1.addTextbox();
			p1.appendTextContent(")");
			if (StringUtils.isNotBlank(pin)) {
			    p1.appendTextContent(" PIN=" + pin);
			}
			// mette un spazio dopo ogni paragrafo
			log.debug("sostituisciCampoComeListDocHyperLink# aggiungo un paragrafo come interlinea");
			document.addParagraph("");
		    }
		    log.debug("sostituisciCampoComeListDocHyperLink# replace della lista sul documento ");
		    textSelection.replaceWith(document);
		    //			 Paragraph p2 = document.addParagraph("               ");
		    return;
		} catch (Exception e) {
		    log.error("sostituisciCampoComeListDocHyperLink# errore durante la sostituizionedel tag {}: {} {}",
			    new Object[] { textSelection.getText(), e.getMessage(), e });
		}
	    }
	}
    }

    private void odtSostituisciCampoComeTabellaHash(TextDocument textdoc, TextSelection textSelection, String[] elem) {

	if (elem == null) {
	    return;
	}
	// TODO: vedi commento al metodo odtSostituisciCampoComeLista
	Iterator<Paragraph> paragraf = textdoc.getParagraphIterator();
	while (paragraf.hasNext()) {
	    Paragraph p = paragraf.next();
	    Matcher phMatcher = OdtConstants.PATTERN_SEGNAPOSTO.matcher(p.getTextContent());
	    while (phMatcher.find()) {
		try {
		    TextDocument document = TextDocument.newTextDocument();
		    Table table = document.addTable((elem.length + 1), 3);
		    Font font = new ODTUtils().getFontFromParagraph(p, textSelection.getText(), false);
		    if (font != null) {
			font.setFontStyle(StyleTypeDefinitions.FontStyle.BOLD);
			font.setSize(10);
		    }
		    Cell cellaHeaderDescrizione = table.getCellByPosition(0, 0);
		    cellaHeaderDescrizione.setStringValue(WebConstants.DESCRIZIONE_FILE_HEADER);
		    cellaHeaderDescrizione.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		    Cell cellaHeaderNomeFile = table.getCellByPosition(1, 0);
		    cellaHeaderNomeFile.setStringValue(WebConstants.NOME_FILE_HEADER);
		    cellaHeaderNomeFile.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		    Cell cellaHeaderImprontaHash = table.getCellByPosition(2, 0);
		    cellaHeaderImprontaHash.setStringValue(WebConstants.IMPRONTA_HASH_SHA256_HEADER);
		    cellaHeaderImprontaHash.setHorizontalAlignment(StyleTypeDefinitions.HorizontalAlignmentType.CENTER);
		    if (font != null) {
			cellaHeaderDescrizione.setFont(font);
			cellaHeaderNomeFile.setFont(font);
			cellaHeaderImprontaHash.setFont(font);
		    }
		    int rowIndex = 1;
		    Font fontCell = null;
		    if (font != null) {
			fontCell = new Font(font.getFamilyName(), font.getFontStyle(), 8);
		    }
		    // TODO : segnaposto
		    for (int i = 0; i < elem.length; i++) {
			String[] campiDaSostiruire = elem[i].split(RtfConstants.SEPARATORE_LINK_ALLEGATI);
			String descrizione = StringUtils.defaultString(campiDaSostiruire[0]);
			String coString = StringUtils.defaultString(campiDaSostiruire[1]);
			Integer codiceoggetto = Integer.parseInt(coString);
			Oggetti og = oggettiService.findByIdLazy(new PkId(codiceoggetto));
			String nomeFile = og.getNomefile();
			String codiceHash = oggettiService.insertOrGetSHA256(codiceoggetto);
			try {
			    table.getCellByPosition(0, rowIndex).setStringValue(descrizione);
			    table.getCellByPosition(1, rowIndex).setStringValue(nomeFile);
			    table.getCellByPosition(2, rowIndex).setStringValue(codiceHash);
			    if (fontCell != null) {
				table.getCellByPosition(0, rowIndex).setFont(fontCell);
				table.getCellByPosition(1, rowIndex).setFont(fontCell);
				table.getCellByPosition(2, rowIndex).setFont(fontCell);
			    }
			} catch (NullPointerException npe) {
			    log.warn("Non sono riuscito a settare il font per il segna posto {}", textSelection.getText());
			}
			// mette un spazio dopo ogni paragrafo
			log.debug("sostituisciCampoComeTabellaHash# aggiungo un paragrafo come interlinea");
			rowIndex++;
		    }
		    log.debug("sostituisciCampoComeTabellaHash# replace della lista sul documento ");
		    textSelection.replaceWith(document);
		    //			 Paragraph p2 = document.addParagraph("               ");
		    return;
		} catch (Exception e) {
		    log.error("sostituisciCampoComeListDocHyperLink# errore durante la sostituizionedel tag {}: {} {}",
			    new Object[] { textSelection.getText(), e.getMessage(), e });
		}
	    }
	}
    }

    private void odtSostituisciInCampoImmagine(TextSelection textSelection, URI uri) {

	try {
	    textSelection.replaceWith(uri);
	    // Devo cancellare il file creato
	    File tempFile = new File(uri);
	    tempFile.deleteOnExit();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
