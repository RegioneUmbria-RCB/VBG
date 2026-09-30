package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoTemplateWrapper;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

public class LinkallegatiNoHyperlinkRTFResolver implements ISegnapostoResolver {

    public static final String TAG = "LINKALLEGATI_NO_HYPERLINK";
    private static final String SEGNAPOSTO_LINK = "@@SEGNAPOSTO_LINK@@";
    private ISegnapostoTemplateWrapper templateWrapper;
    private DocumentMergeHelper userData;
    private TempLinkallegatiService tempLinkallegatiService;
    private String rtfSyntaxLink = " {\\\\field{\\\\*\\\\fldinst {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid14436269 HYPERLINK \"" +
				   SEGNAPOSTO_LINK +
				   "\"}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid14436269 }}{\\\\fldrslt {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\cs36\\\\ul\\\\cf23\\\\insrsid14436269\\\\charrsid14436269 " +
				   SEGNAPOSTO_LINK + "}}} ";
    private String rtfSyntaxScarica = " {\\\\field{\\\\*\\\\fldinst {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid14436269 HYPERLINK \"" +
				      SEGNAPOSTO_LINK +
				      "\"}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid14436269 }}{\\\\fldrslt {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\cs36\\\\ul\\\\cf23\\\\insrsid14436269\\\\charrsid14436269 Scarica il documento}}} ";

    public LinkallegatiNoHyperlinkRTFResolver(ISegnapostoTemplateWrapper templateWrapper, TempLinkallegatiService tempLinkallegatiService,
	    DocumentMergeHelper userData) {

	if (templateWrapper == null) {
	    throw new IllegalArgumentException(
		    "Il tag prevede un template ma non è stato passato il wrapper specifico per la sostituzione del template");
	}
	this.userData = userData;
	this.tempLinkallegatiService = tempLinkallegatiService;
	this.templateWrapper = templateWrapper;
    }

    @Override
    public String sostituisci() {

	String template = this.templateWrapper.getTemplate(TAG);
	String testoRiga = "\\\\trowd";
	//1.Split sul tag \trowd 
	String[] righe = template.split(testoRiga);
	if (righe == null || righe.length == 0) {
	    return null;
	}
	//2. Cerco la riga che contiene le variabili da sostituire
	StringBuilder retVal = new StringBuilder();
	//il primo valore è da scartare per cui parto da 1
	for (int i = 0; i < righe.length; i++) {
	    if (this.rigaDaSostituire(righe[i])) {
		String uuid = userData.getUuidLinkTemp();
		List<TempLinkallegati> tempLinks = new ArrayList<TempLinkallegati>();
		if (StringUtils.isNotBlank(uuid)) {
		    tempLinks = tempLinkallegatiService.findByUuid(uuid);
		}
		for (TempLinkallegati linkallegati : tempLinks) {
		    retVal.append(testoRiga);
		    String value = righe[i];
		    if (value.indexOf(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_URL_SCARICA) >= 0) {
			value = value.replace(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_URL_SCARICA,
				rtfSyntaxScarica.replace(SEGNAPOSTO_LINK, FormatUtils.stringFormat(linkallegati.getLink())));
		    }
		    if (value.indexOf(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_URL_LINK) >= 0) {
			value = value.replace(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_URL_LINK,
				rtfSyntaxLink.replace(SEGNAPOSTO_LINK, FormatUtils.stringFormat(linkallegati.getLink())));
		    }
		    value = value.replace(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_URL, FormatUtils.stringFormat(linkallegati.getLink()));
		    value = value.replace(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_DESCRIZIONE,
			    FormatUtils.stringFormat(linkallegati.getDescrizioneDocumento()));
		    value = value.replace(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_NOMEFILE,
			    FormatUtils.splitStringByNCharacters(FormatUtils.stringFormat(linkallegati.getNomedocumento()), 20, " "));
		    value = value.replace(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_PIN,
			    FormatUtils.stringFormat(linkallegati.getPin().toString()));
		    retVal.append(value);
		}
	    } else {
		if (i == 0) {
		    retVal.append(righe[i]);
		} else {
		    retVal.append(testoRiga).append(righe[i]);
		}
	    }
	}
	return retVal.toString();
    }

    private boolean rigaDaSostituire(String riga) {

	return riga.indexOf(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_URL) >= 0
		|| riga.indexOf(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_URL_SCARICA) >= 0
		|| riga.indexOf(LinkallegatiNoHyperlinkODTResolver.SEGNAPOSTO_URL_LINK) >= 0;
    }
}
