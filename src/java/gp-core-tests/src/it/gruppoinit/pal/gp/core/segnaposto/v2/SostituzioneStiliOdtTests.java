package it.gruppoinit.pal.gp.core.segnaposto.v2;

import org.junit.Test;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;

public class SostituzioneStiliOdtTests {

    @Test
    public void applicaGliStessiStiliDellaDestinazione() throws Exception {

	//	TextDocument doc = TextDocument.loadDocument("c:\\temp\\odt\\modelloodt.odt");
	//	TextNavigation textNav = new TextNavigation("\\[-SEGNAPOSTO-\\]", doc);
	//	while (textNav.hasNext()) {
	//	    TextSelection textSelection = (TextSelection) textNav.nextSelection();
	//	    // TextDocument docSost = TextDocument.newTextDocument();
	//	    // docSost.addParagraph("Sostituito");
	//	    // textSelection.replaceWith(docSost);
	//	    textSelection.replaceWith("valore sostituito");
	//	}
	//	doc.save("c:\\temp\\odt\\modelloodt-sostituito.odt");
    }
}
