package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.rtf;

import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.DocumentMergeDAO;
import it.gruppoinit.pal.gp.core.dao.impl.SostituzioniValoriConQueryWork;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.DatiOggettoLettera;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.RtfConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared.IIncapsulaPlaceholderValueByIfElseService;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared.SostituzioneNelModello;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.IRtfSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.ISostituzioneSegnapostoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.SegnapostoParser;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.StrutturaSegnaposto;

@Service
public class SostituzioniSegnapostoRTFLegacyServiceImpl implements ISostituzioniSegnapostoRTFLegacyService {

    private final Logger log = LoggerFactory.getLogger(SostituzioniSegnapostoRTFLegacyServiceImpl.class);
    private DocumentMergeDAO documentMergeDAO;
    private OggettiService oggettiService;
    private IIncapsulaPlaceholderValueByIfElseService incapsulaPlaceholderValueByIfElseService;
    private ISostituzioneSegnapostoService sostituzioneSegnapostoService;

    @Autowired
    public SostituzioniSegnapostoRTFLegacyServiceImpl(DocumentMergeDAO documentMergeDAO, OggettiService oggettiService,
	    IIncapsulaPlaceholderValueByIfElseService incapsulaPlaceholderValueByIfElseService,
	    ISostituzioneSegnapostoService sostituzioneSegnapostoService) {

	super();
	this.documentMergeDAO = documentMergeDAO;
	this.oggettiService = oggettiService;
	this.incapsulaPlaceholderValueByIfElseService = incapsulaPlaceholderValueByIfElseService;
	this.sostituzioneSegnapostoService = sostituzioneSegnapostoService;
    }

    @Override
    public byte[] effettuaSostituzioniBaseRtf(DocumentMergeHelper documentMergeHelper, DatiOggettoLettera oggettoLettera,
	    IUsefulDataForPlaceholderReplacement data) {

	SegnapostoParser segnapostoParser = new SegnapostoParser();
	log.debug("effettuaSostituzioniBaseRtf# DocumentoTipo di tipo rtf");
	String input = new String(oggettoLettera.getContenutoFile());
	if (data.getIstanza() != null) {
	    documentMergeHelper.addParam("CODICEISTANZA", data.getIstanza().getId().getCodice().toString());
	}
	if (data.getMovimento() != null) {
	    documentMergeHelper.addParam("CODICEMOVIMENTO", data.getMovimento().getId().getCodice().toString());
	    documentMergeHelper.addParam("TIPOMOVIMENTO", data.getMovimento().getTipomovimento().getMovimento());
	}
	if (input.contains("#INIZIO")) {
	    log.debug("effettuaSostituzioniBaseRtf input contains #INIZIO ");
	    input = documentMergeDAO.eseguiSostituzioniConQuery(input, "rtf", documentMergeHelper);
	}
	StringBuffer sbOut = new StringBuffer(input.length());
	Matcher phMatcher = RtfConstants.PATTERN_SEGNAPOSTO.matcher(input);
	while (phMatcher.find()) {
	    String segnapostoOriginale = phMatcher.group();
	    log.debug("effettuaSostituzioniBaseRtf# processo il segnaposto {}", segnapostoOriginale);
	    StrutturaSegnaposto strutturaSegnaposto = segnapostoParser.analizza(segnapostoOriginale);
	    IRtfSubstitution sostituzione = sostituzioneSegnapostoService.sostituisciRtf(strutturaSegnaposto, data, documentMergeHelper);
	    if (sostituzione != null) {
		phMatcher.appendReplacement(sbOut, sostituzione.getValore());
		continue;
	    }
	    String placeHolderValue = rtfSostituisciValoreSegnaposto(segnapostoOriginale, data, documentMergeHelper);
	    log.debug("effettuaSostituzioniBaseRtf# il valore del segnaposto {} è {}", segnapostoOriginale, placeHolderValue);
	    phMatcher.appendReplacement(sbOut, placeHolderValue);
	}
	phMatcher.appendTail(sbOut);
	log.debug("effettuaSostituzioniBaseRtf# prima di eseguire SostituzioniValoriConQueryWork.sostituisciVariabiliGlobali");	
	String ris = SostituzioniValoriConQueryWork.sostituisciVariabiliGlobali(sbOut.toString());
	ris = FormatUtils.escapeUnicodeSpecialChars(ris);
	return ris.getBytes();
    }

    /*
     * Riceve in input il segnaposto nella forma [- SEGNAPOSTO -] e ne restituisce il valore da sostituire.
     * Se il segnaposto non è un segnaposto valido restituisce la stessa stringa ricevuta in input
     * @param placeholder
     * @param istanza
     * @param movimento
     * @return
     */
    private String rtfSostituisciValoreSegnaposto(String segnapostoOriginale, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	String tag = segnapostoOriginale.substring(2, segnapostoOriginale.length() - 2);
	SostituzioneNelModello valoreSostituito = this.incapsulaPlaceholderValueByIfElseService.esegui(tag, data, userData, TipoFileEnum.RTF);
	if (valoreSostituito == null) {
	    return segnapostoOriginale;
	}
	//in caso di RTF sarà sempre un campo di tipo stringa e popolato solo il primo elemento dell'array
	String[] elem = valoreSostituito.getValori();
	switch (valoreSostituito.getType()) {
	case STRING:
	    return rtfEscapeRegExPreviousMatchCharacters(elem[0]);
	case HYPERLINK_DOC_LINK:
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < elem.length; i++) {
		String[] campiDaSostituire = elem[i].split(RtfConstants.SEPARATORE_LINK_ALLEGATI);
		String nomeFile = campiDaSostituire[0];
		String descrizioneDocumento = campiDaSostituire[1];
		String link = campiDaSostituire[2];
		sb.append(descrizioneDocumento) //
			.append(" (scarica \"{\\\\field{\\\\*\\\\fldinst HYPERLINK \"") //
			.append(link) //
			.append("\"}{\\\\fldrslt {\\\\ul ") //
			.append(nomeFile) //
			.append("}}}\")");
		if (campiDaSostituire.length > 4 && campiDaSostituire[4] != null) {
		    sb.append(" PIN=" + campiDaSostituire[4]);
		}
		if (i < (elem.length - 1)) {
		    sb.append(" \\\\par ");
		}else {
		    sb.append(" ");
		}
		// mette un spazio dopo ogni paragrafo
		log.debug("sostituisciCampoComeListDocHyperLink# aggiungo un paragrafo come interlinea");
	    }
	    log.debug("sostituisciCampoComeListDocHyperLink# replace della lista sul documento ");
	    return sb.toString();
	case TABELLA_HASH_DOC:
	    String tableHead = "";
	    tableHead = "{\\\\rtf1\\\\ansi\\\\deff0" + //
		    "\\\\trowd\\\\tgraph144" + //
		    "\n\\\\irow0\\\\irowband0\\\\ltrrow\\\\ts11\\\\trrh244\\\\trleft-108\\\\trftsWidth2\\\\trwWidth5000" + //
		    "\n\\\\trautofit1\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid9389093\\\\tblind0\\\\tblindtype3" + //
		    "\n\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrl\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrb" + //
		    "\n\\\\brdrs\\\\brdrw10\\\\brdrcf1 \\\\clbrdrr\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
		    "\n\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1508\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil" + //
		    "\\\\cellx2229\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1 \\\\clbrdrl\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrb\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
		    "\n\\\\clbrdrr\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1730\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil" + //
		    "\n\\\\cellx6028\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrl\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrb\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrr" + //
		    "\n\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1762\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil\\\\cellx10080\\\\pard" + //
		    "\n\\\\ltrpar\\\\ql\\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\hyphpar0\\\\faauto\\\\rin0\\\\lin0" + //
		    "\n{\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\b\\\\f0\\\\insrsid3560216\\\\hich\\\\af0\\\\dbch\\\\af31505\\\\loch\\\\f0" +
		    WebConstants.DESCRIZIONE_FILE_HEADER +
		    "}" + // 
		    "\n{\\\\rtlch\\\\fcs1\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\insrsid3560216\\\\cell }" + //
		    "\n{\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\b\\\\f0\\\\insrsid3560216\\\\hich\\\\af0\\\\dbch\\\\af31505\\\\loch\\\\f0" +
		    WebConstants.NOME_FILE_HEADER +
		    "}" + // 
		    "\n{\\\\rtlch\\\\fcs1\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\insrsid3560216\\\\cell }" + //
		    "\n{\\\\rtlch\\\\fcs1\\\\ab\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\b\\\\f0\\\\insrsid3560216\\\\hich\\\\af0\\\\dbch\\\\af31505\\\\loch\\\\f0" +
		    WebConstants.IMPRONTA_HASH_SHA256_HEADER +
		    "}" + //
		    "\n{\\\\rtlch\\\\fcs1\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\insrsid3560216\\\\cell }" + //
		    "\n\\\\pard" + //
		    "\n\\\\ltrpar\\\\ql\\\\li0\\\\ri0\\\\sa160\\\\sl259\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0" + //
		    "\n{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\insrsid3560216\\\\trowd" + //
		    "\n\\\\irow0\\\\irowband0\\\\ltrrow\\\\ts11\\\\trrh244\\\\trleft-108\\\\trftsWidth2\\\\trwWidth5000\\\\trautofit1\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid9389093\\\\tblind0\\\\tblindtype3" + //
		    "\n\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrl" + //
		    "\n\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrb\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrr\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
		    "\n\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1508\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil" + //
		    "\n\\\\cellx2229\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrl\\\\brdrs\\\\brdrw10\\\\brdrcf1" + // 
		    "\n\\\\clbrdrb\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrr\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
		    "\n\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1730\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil" + //
		    "\n\\\\cellx6028\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrl\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrb" + //
		    "\n\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrr\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
		    "\n\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1762\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil\\\\cellx10080\\\\row\\\\ltrrow}";
	    String tableBody = "";
	    String tableFooter = "\n }";
	    for (int i = 0; i < elem.length; i++) {
		String[] campiDaSostituire = elem[i].split(RtfConstants.SEPARATORE_LINK_ALLEGATI);
		if (campiDaSostituire == null || campiDaSostituire.length != 2) {
		    log.error("Errore nel recupero del segnaposto {}, valore {} non è un elemento valido", segnapostoOriginale, campiDaSostituire);
		    continue;
		}
		String descrizioneFile = campiDaSostituire[0];
		String codiceOggettoString = campiDaSostituire[1]; // QUESTO VIENE IMPOSTATO SOLO CON DUE ELEMENTI DESCRZIONE E CODICEOGGETTO
		Integer codiceOggetto = Integer.parseInt(codiceOggettoString);
		Oggetti og = oggettiService.findByIdLazy(new PkId(codiceOggetto));
		String nomeFile = FormatUtils.splitStringByNCharacters(og.getNomefile(), 20, " ");
		String codiceHash = FormatUtils.splitStringByNCharacters(oggettiService.insertOrGetSHA256(codiceOggetto), 20, " ");
		tableBody += "\\\\trowd\\\\fs11\\\\irow1\\\\irowband1\\\\ltrrow" + //
			"\n\\\\ts11\\\\trrh278\\\\trleft-108\\\\trftsWidth2\\\\trwWidth5000\\\\trautofit1\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15615584\\\\tblind0\\\\tblindtype3" + //
			"\n\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrl\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrb\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrr" + //
			"\n\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1508\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil" + //
			"\n\\\\cellx2229\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrl\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrb\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
			"\n\\\\clbrdrr\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
			"\n\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1730\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil" + //
			"\n\\\\cellx6028\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrl\\\\brdrs\\\\brdrw10\\\\brdrcf1\\\\clbrdrb\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
			"\n\\\\clbrdrr\\\\brdrs\\\\brdrw10\\\\brdrcf1" + //
			"\n\\\\cltxlrtb\\\\clftsWidth2\\\\clwWidth1762\\\\clpadt108\\\\clpadr108\\\\clpadft3\\\\clpadfr3\\\\clshdrawnil\\\\cellx10080\\\\pard\\\\ltrpar\\\\ql" + //
			"\n\\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\hyphpar0\\\\faauto\\\\rin0\\\\lin0{\\\\rtlch\\\\fcs1\\\\af0\\\\afs20\\\\ltrch\\\\fcs0" + //
			"\n\\\\f0\\\\fs20\\\\insrsid3560216 " +
			descrizioneFile +
			"}{\\\\rtlch\\\\fcs1\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\insrsid3560216\\\\cell }" + //
			"\n{\\\\rtlch\\\\fcs1\\\\af0\\\\afs20\\\\ltrch\\\\fcs0\\\\f0\\\\fs20\\\\insrsid3560216 " +
			nomeFile +
			"}{\\\\rtlch\\\\fcs1\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\insrsid3560216\\\\cell }" + //
			"\n{\\\\rtlch\\\\fcs1\\\\af0\\\\afs20\\\\ltrch\\\\fcs0\\\\f0\\\\fs20\\\\insrsid3560216 " +
			codiceHash +
			"}{\\\\rtlch\\\\fcs1\\\\af0\\\\afs24\\\\ltrch\\\\fcs0\\\\insrsid3560216\\\\cell }\\\\pard" +
			"\n\\\\row";
	    }
	    return tableHead + tableBody + tableFooter;
	case CONTEGGIO_ALLEGATI_HASH:
	    return elem[0];
	default: // Tipo sostituzione non supportata su rtf, loggare?
	    break;
	}
	return segnapostoOriginale;
    }

    private String rtfEscapeRegExPreviousMatchCharacters(String value) {

	if (value.indexOf("$") > -1) {
	    value = value.replaceAll("[$\\\\]", "\\\\$0");
	}
	return value;
    }
    
    public static void main(String[] args) {

	String[] elem = new String[5];
	elem[0] = "primo";
	elem[1] = "secondo";
	elem[2] = "terzo";
	elem[3] = "quarto";
	elem[4] = "quinto";
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < elem.length; i++) {
	    sb.append("-").append(elem[i]); //
	    if (i < (elem.length-1)) {
		sb.append(" \\\\par \n");
	    }else {
		sb.append(" --- ");
	    }
	    // mette un spazio dopo ogni paragrafo
	}
	System.out.println(sb);
    }
}
