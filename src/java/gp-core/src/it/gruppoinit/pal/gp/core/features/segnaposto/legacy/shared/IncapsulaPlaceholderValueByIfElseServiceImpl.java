package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto.LinkallegatiNoHyperlinkODTResolver;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto.ZipLogicoTabellaHashODTResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.RtfConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.ConteggioAllegatiHash;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

@Service
public class IncapsulaPlaceholderValueByIfElseServiceImpl implements IIncapsulaPlaceholderValueByIfElseService {

    private TempLinkallegatiService tempLinkallegatiService;
    private VerticalizzazioniService verticalizzazioniService;
    private IGetPlaceholderValueByIfElseService getPlaceholderValueByIfElseService;
    private IOdtUtilsService odtUtilsService;

    @Autowired
    public IncapsulaPlaceholderValueByIfElseServiceImpl(TempLinkallegatiService tempLinkallegatiService,
	    VerticalizzazioniService verticalizzazioniService, IGetPlaceholderValueByIfElseService getPlaceholderValueByIfElseService,
	    IOdtUtilsService odtUtilsService) {

	super();
	this.tempLinkallegatiService = tempLinkallegatiService;
	this.verticalizzazioniService = verticalizzazioniService;
	this.getPlaceholderValueByIfElseService = getPlaceholderValueByIfElseService;
	this.odtUtilsService = odtUtilsService;
    }

    @Override
    public SostituzioneNelModello esegui(String placeholder, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData,
	    TipoFileEnum typeLettereTipoEnum) {

	String palceHolderValue = getPlaceholderValueByIfElseService.esegui(placeholder, data, userData, typeLettereTipoEnum);
	if (typeLettereTipoEnum == TipoFileEnum.ODT) {
	    return odtPostProcess(placeholder, palceHolderValue, data, userData);
	}
	if (typeLettereTipoEnum == TipoFileEnum.RTF) {
	    return rtfPostProcess(placeholder, palceHolderValue, data, userData);
	}
	return null;
    }

    private SostituzioneNelModello rtfPostProcess(String placeholder, String palceholderValue, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	if (StringUtils.isNotBlank(palceholderValue)) {
	    return new SostituzioneNelModello(TypeFiled.STRING, palceholderValue);
	}
	String[] retVal = rtfGetLinkDocumenti(placeholder, data, userData);
	if (retVal != null) {
	    // Esegue un return direttamente perché il segnaposto non può essere
	    // contemporaneamente TABELLA_HASH_ALLEGATI e LINKALLEGATI
	    return new SostituzioneNelModello(TypeFiled.HYPERLINK_DOC_LINK, retVal);
	}
	retVal = rtfGetTabellaHash(placeholder, data, userData);
	if (retVal != null) {
	    // Esegue un return direttamente perché il segnaposto non può essere
	    // contemporaneamente TABELLA_HASH_ALLEGATI e LINKALLEGATI
	    return new SostituzioneNelModello(TypeFiled.TABELLA_HASH_DOC, retVal);
	}
	if (ConteggioAllegatiHash.TAG.equals(placeholder)) {
	    return new SostituzioneNelModello(TypeFiled.CONTEGGIO_ALLEGATI_HASH, (String) null);
	}
	return new SostituzioneNelModello(TypeFiled.STRING, "");
    }

    private SostituzioneNelModello odtPostProcess(String placeholder, String palceholderValue, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	palceholderValue = odtUtilsService.odtBonificaValueForODT(palceholderValue);
	// il secondo controllo serve per capire se staimo trattado un immagine. In odt non può essere gestita come una string
	// ma ha una gestione a parte vedi 'isImmagineForODT'
	if (StringUtils.isNotBlank(palceholderValue) && !palceholderValue.contains("{\\\\*\\\\shppict{\\\\pict\\\\jpegblip\n")) {
	    if (StringUtils.contains(palceholderValue, RtfConstants.RTF_CRLF)) {
		String[] retVal = palceholderValue.split("\\\\" + RtfConstants.RTF_CRLF);
		// Valori multipli saranno sempre stringhe
		return new SostituzioneNelModello(TypeFiled.STRING, retVal);
	    }
	    return new SostituzioneNelModello(TypeFiled.STRING, palceholderValue);
	}
	if (ZipLogicoTabellaHashODTResolver.TAG.equals(placeholder)) {
	    return new SostituzioneNelModello(TypeFiled.ZIPLOGICO_TABELLA_HASH, (String) null);
	}
	String[] retVal = rtfGetLinkDocumenti(placeholder, data, userData);
	if (retVal != null) {
	    // Esegui un return direttamente perché il segnaposto non può essere
	    // contemporaneamente TABELLA_HASH_ALLEGATI e LINKALLEGATI
	    return new SostituzioneNelModello(TypeFiled.HYPERLINK_DOC_LINK, retVal);
	}
	retVal = rtfGetTabellaHash(placeholder, data, userData);
	if (retVal != null) {
	    // Esegue un return direttamente perché il segnaposto non può essere
	    // contemporaneamente TABELLA_HASH_ALLEGATI e LINKALLEGATI
	    return new SostituzioneNelModello(TypeFiled.TABELLA_HASH_DOC, retVal);
	}
	if (ConteggioAllegatiHash.TAG.equals(placeholder)) {
	    return new SostituzioneNelModello(TypeFiled.CONTEGGIO_ALLEGATI_HASH, (String) null);
	}
	if (LinkallegatiNoHyperlinkODTResolver.TAG.equals(placeholder)) {
	    return new SostituzioneNelModello(TypeFiled.LINKALLEGATI_NO_HYPERLINK, retVal);
	}
	String filePath = odtUtilsService.odtIsImmagineForODT(palceholderValue, placeholder, data);
	if (StringUtils.isNotBlank(filePath)) {
	    return new SostituzioneNelModello(TypeFiled.IMAGE, filePath);
	}
	if (StringUtils.isBlank(palceholderValue)) {
	    palceholderValue = "";
	}
	return new SostituzioneNelModello(TypeFiled.STRING, palceholderValue);
    }

    /**
     * Se il segnaposto è "TABELLA_HASH_ALLEGATI" viene sostituito con la tabella hash, altrimenti restituisce null
     * 
     * @param placeholder
     * @param data
     * @param userData
     * @return lista link allegati, messaggio di errore oppure null se il segnaposto non è "TABELLA_HASH_ALLEGATI"
     */
    private String[] rtfGetTabellaHash(String placeholder, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	if (!placeholder.equalsIgnoreCase("TABELLA_HASH_ALLEGATI")) {
	    return null;
	}
	String uuid = userData.getUuidLinkTemp();
	if (StringUtils.isBlank(uuid)) {
	    String[] risultato = new String[1];
	    risultato[0] = "Impossibile sostituire il segna posto " + placeholder + " uuid non passato";
	    return risultato;
	}
	List<TempLinkallegati> list = tempLinkallegatiService.findByUuid(uuid);
	if (list.isEmpty()) {
	    return null;
	}
	List<String> risultato = new ArrayList<String>();
	for (TempLinkallegati tempLinkallegati : list) {
	    if (tempLinkallegati.getCodiceoggetto() != null) {
		// tabella hash allegati solamente per allegati e non per ZIP LOGICO che non ci mette il codiceoggetto
		StringBuilder ris = new StringBuilder("");
		ris = ris.append(tempLinkallegati.getDescrizioneDocumento()).append(RtfConstants.SEPARATORE_LINK_ALLEGATI)
			.append(tempLinkallegati.getCodiceoggetto());
		risultato.add(ris.toString());
	    }
	}
	return risultato.toArray(new String[0]);
    }

    private boolean isAllegatiPecDownloadSenzaPinAttivo() {

	Verticalizzazioniparametri vp = null;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC)) {
	    vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC,
		    WebConstants.VERTICALIZZAZIONE_PARAMETRI_ALLEGATI_PEC_DOWNLOAD_SENZA_PIN);
	}
	if (vp == null) {
	    return false;
	}
	return "S".equals(vp.getValore());
    }

    /**
     * Se il segnaposto è "LINKALLEGATI" viene sostituito con la lista dei link allegati, altrimenti restituisce null
     * 
     * @param placeholder
     * @param data
     * @param userData
     * @return lista link allegati, messaggio di errore oppure null se il segnaposto non è "LINKALLEGATI"
     */
    private String[] rtfGetLinkDocumenti(String placeholder, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	if (!placeholder.equalsIgnoreCase("LINKALLEGATI")) {
	    return null;
	}
	String uuid = userData.getUuidLinkTemp();
	if (StringUtils.isBlank(uuid)) {
	    return new String[] { "Impossibile sostituire il segna posto " + placeholder + " uuid non passato" };
	}
	List<TempLinkallegati> list = tempLinkallegatiService.findByUuid(uuid);
	if (list.isEmpty()) {
	    return null;
	}
	List<String> risultato = new ArrayList<String>(list.size());
	for (TempLinkallegati tempLinkallegati : list) {
	    StringBuilder ris = new StringBuilder("");
	    String codiceOggetto = "";
	    String pin = "";
	    if (tempLinkallegati.getCodiceoggetto() != null) {
		codiceOggetto = String.valueOf(tempLinkallegati.getCodiceoggetto());
	    }
	    if (tempLinkallegati.getPin() != null) {
		pin = String.valueOf(tempLinkallegati.getPin());
	    }
	    ris = ris.append(StringUtils.defaultString(tempLinkallegati.getNomedocumento())).append(RtfConstants.SEPARATORE_LINK_ALLEGATI)
		    .append(StringUtils.defaultString(tempLinkallegati.getDescrizioneDocumento())).append(RtfConstants.SEPARATORE_LINK_ALLEGATI)
		    .append(StringUtils.defaultString(tempLinkallegati.getLink())).append(RtfConstants.SEPARATORE_LINK_ALLEGATI)
		    .append(StringUtils.defaultString(codiceOggetto));
	    if (!isAllegatiPecDownloadSenzaPinAttivo()) {
		ris = ris.append(RtfConstants.SEPARATORE_LINK_ALLEGATI).append(StringUtils.defaultString(pin));
	    }
	    risultato.add(ris.toString());
	}
	return risultato.toArray(new String[0]);
    }
}
