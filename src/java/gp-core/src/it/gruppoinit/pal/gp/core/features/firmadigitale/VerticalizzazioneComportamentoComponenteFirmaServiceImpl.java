package it.gruppoinit.pal.gp.core.features.firmadigitale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class VerticalizzazioneComportamentoComponenteFirmaServiceImpl implements IVerticalizzazioneComportamentoComponenteFirmaService {

    @Autowired
    private VerticalizzazioniService service;
    private String nomeVerticalizzazione = WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA;
    private String parConvertiInPDf = WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA_CONVERTI_IN_PDF;
    private String parNoEstensioniMultiP7m = WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA_FIRMA_CADES_NO_EXT_MULTI_P7M;

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(this.nomeVerticalizzazione);
    }

    @Override
    public boolean convertibileInPdf(String nomeFileIn) {

	if (!this.isAttiva() || StringUtils.isBlank(nomeFileIn) || nomeFileIn.lastIndexOf(".") == -1) {
	    return false;
	}
	String estensioni = this.service.getString(nomeVerticalizzazione, parConvertiInPDf, "").toUpperCase() + ";";
	if (StringUtils.isBlank(estensioni)) {
	    return false;
	}
	String estensioneFile = nomeFileIn.substring((nomeFileIn.lastIndexOf(".") + 1)).toUpperCase() + ";";
	return estensioni.indexOf(estensioneFile) > -1;
    }

    @Override
    public boolean accodaEstensioneFirmaCades() {

	return this.isAttiva() && this.service.getBoolean(this.nomeVerticalizzazione, this.parNoEstensioniMultiP7m, "1");
    }
}
