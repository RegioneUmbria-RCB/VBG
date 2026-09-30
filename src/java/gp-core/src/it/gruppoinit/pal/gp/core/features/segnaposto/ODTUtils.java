package it.gruppoinit.pal.gp.core.features.segnaposto;

import java.util.Iterator;

import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.text.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ODTUtils {

    private final Logger log = LoggerFactory.getLogger(ODTUtils.class);

    public ODTUtils() {

	super();
    }

    public Font getFontFromParagraph(Paragraph p, String tag, boolean getDefaultFont) {

	Font font = null;
	try {
	    font = p.getFont();
	} catch (NullPointerException npe) {
	    log.warn("Non sono riuscito a settare il font per il segna posto {}", tag, npe);
	}
	if (font == null) {
	    if (getDefaultFont) {
		font = new Font("Arial", StyleTypeDefinitions.FontStyle.REGULAR, 10);
	    }
	}
	return font;
    }

    public Font getFontFromParagraphIterator(Iterator<Paragraph> paragraf, String tag, boolean getDefaultFont) {

	boolean esci = false;
	Font font = null;
	while (!esci) {
	    if (paragraf.hasNext()) {
		Paragraph p = paragraf.next();
		try {
		    font = getFontFromParagraph(p, tag, getDefaultFont);
		    if (font != null) {
			esci = true;
		    }
		} catch (NullPointerException npe) {
		    log.warn("Non sono riuscito a settare il font per il segna posto {}", tag, npe);
		}
	    } else {
		esci = true;
	    }
	}
	if (font == null) {
	    if (getDefaultFont) {
		font = new Font("Arial", StyleTypeDefinitions.FontStyle.REGULAR, 10);
	    }
	}
	return font;
    }
}
