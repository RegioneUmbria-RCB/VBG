package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.odftoolkit.simple.common.navigation.TextSelection;
import org.odftoolkit.simple.text.list.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.OdtConstants;

public class OdtMultipleTextSubstitution implements IOdtSubstitution {

    private final Logger log = LoggerFactory.getLogger(OdtMultipleTextSubstitution.class);
    private String[] valori;
    private TextDocument currentDocument;

    public OdtMultipleTextSubstitution(String[] values, TextDocument currentDocument) {

	this.valori = values;
	this.currentDocument = currentDocument;
	if (this.valori == null) {
	    this.valori = new String[0];
	}
    }

    @Override
    public void applyTo(TextSelection selection) {

	if (this.valori.length > 1) {
	    odtSostituisciCampoComeLista(selection);
	    return;
	}
	// Se si tratta di un solo valore applico le regole normali di sostituzione
	String valore = this.valori.length == 0 ? "" : this.valori[0];
	new OdtTextSubstitution(valore).applyTo(selection);
    }

    @Override
    public void applyTo(List odtList, int indiceSegnaposto) {

	if (this.valori.length > 1) {
	    odtSostituisciInLista(odtList, indiceSegnaposto);
	    return;
	}
	// Se si tratta di un solo valore applico le regole normali di sostituzione
	String valore = this.valori.length == 0 ? "" : this.valori[0];
	new OdtTextSubstitution(valore).applyTo(odtList, indiceSegnaposto);
    }

    /*
     * Copiato spudoratamente da SostituzioniSegnapostoODTLegacyServiceImpl nonostante abbia dei problemi
     * Per ora lo lascio così ma sicuramente va rivisto
     */
    private void odtSostituisciCampoComeLista(TextSelection textSelection) {

	StringBuilder sb = new StringBuilder();
	try {
	    for (String string : this.valori) {
		sb.append(string).append(OdtConstants.ODT_CRLF);
	    }
	    textSelection.replaceWith(sb.toString());
	} catch (InvalidNavigationException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	// TODO: Questo metodo va rivisto pesantemente
	// in pratica scorre tutti i paragrafi del documento e quando ne trova uno
	// che contiene un segnaposto ne prende il font e lo applica ai vari
	// valori che va ad inserire nella selezione.
	// Dovrebbe riprendere il font del paragrafo che contiene lo specifico segnaposto
	// e non uno qualsiasi
	//	Iterator<Paragraph> paragraphIterator = currentDocument.getParagraphIterator();
	//	String testoDellaSelezione = textSelection.getText();
	//	while (paragraphIterator.hasNext()) {
	//	    Paragraph paragraph = paragraphIterator.next();
	//	    if (paragraph.getTextContent().contains(testoDellaSelezione)) {
	//		try {
	//		    TextDocument document = TextDocument.newTextDocument();
	//		    for (int i = 0; i < this.valori.length; i++) {
	//			Font font = paragraph.getFont();
	//			Paragraph p1 = document.addParagraph(this.valori[i]);
	//			if (font != null) {
	//			    p1.setFont(font);
	//			} else {
	//			    log.warn("Non sono riuscito a settare il font per il segna posto {}", testoDellaSelezione);
	//			}
	//		    }
	//		    textSelection.replaceWith(document);
	//		} catch (Exception e) {
	//		    log.error("OdtMultipleTextSubstitution.odtSostituisciCampoComeLista# errore durante la sostituizionedel tag {}: {} ",
	//			    new Object[] { textSelection.getText(), e.getMessage() });
	//		}
	//	    }
	//	}
    }

    /*
     * Copiato spudoratamente da SostituzioniSegnapostoODTLegacyServiceImpl nonostante abbia dei problemi
     * Per ora lo lascio così ma sicuramente va rivisto
     */
    private void odtSostituisciInLista(List odtList, int indice) {

	// Per sostituire gli elementi di una lista la lista deve contenere un solo elemento
	if (odtList.getItems().size() == 1) {
	    for (int j = 0; j < this.valori.length; j++) {
		odtList.addItem(this.valori[j]);
	    }
	}
	odtList.removeItem(indice);
    }
}
