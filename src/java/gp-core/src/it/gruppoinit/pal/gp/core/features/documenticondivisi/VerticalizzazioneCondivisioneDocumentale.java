package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class VerticalizzazioneCondivisioneDocumentale {

    private Integer idAccountFTP;
    VerticalizzazioniService verticalizzazioniService;

    public VerticalizzazioneCondivisioneDocumentale(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
	if (!this.isAttiva()) {
	    return;
	}
	Verticalizzazioniparametri parametro = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE, WebConstants.VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE_ID_ACCOUNT_FTP);
	this.idAccountFTP = (parametro != null && StringUtils.isNotEmpty(parametro.getValore())) ? Integer.parseInt(parametro.getValore()) : null;
    }

    public Integer getIdAccountFTP() {

	return idAccountFTP;
    }

    public boolean isAttiva() {

	return verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CONDIVISIONE_DOCUMENTALE, ORMHelper.getSoftware());
    }
}
