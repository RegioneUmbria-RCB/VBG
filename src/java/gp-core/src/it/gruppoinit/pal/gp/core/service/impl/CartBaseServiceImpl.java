package it.gruppoinit.pal.gp.core.service.impl;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.CartBaseService;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.AllegatoRichiesto;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoAllegatiRichiesti;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoQuadri;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.File;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoQuadri.Quadro;
import it.gruppoinit.sigepro.cart.service.CartRfcBaseService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CartBaseServiceImpl implements CartBaseService {

    private static final Logger log = LoggerFactory.getLogger(CartBaseServiceImpl.class);
    public static final int CODICE_ALBEROPROC_TIPIENDO_DEFAULT = -15555;
    protected AllegatiService allegatiService;
    protected OggettiService oggettiService;
    protected VerticalizzazioniService verticalizzazioniService;
    protected CartRfcBaseService cartService;

    // private boolean effettuaValidazioneSchema = true;
    @Autowired
    public void setAllegatiService(AllegatiService allegatiService) {

	this.allegatiService = allegatiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    protected CartRfcBaseService getCartService() {

	return cartService;
    }

    @Override
    public void reloadConfiguration() {

	getCartService().reloadConfiguration();
    }

    /**
     * @param endo
     * @param allegatoRichiesto
     */
    protected void inserisciAllegatoEndo1(Inventarioprocedimenti endo, AllegatoRichiesto allegatoRichiesto) {

	// §§§BEGIN§§§
	String codiceAllegato = allegatoRichiesto.getCodiceAllegato();
	String adempimentoAllegato = allegatoRichiesto.getAdempimentoAllegato();
	String spiegazioneAllegato = allegatoRichiesto.getSpiegazioniAllegato();
	String tipologiaAllegato = allegatoRichiesto.getTipologiaAllegato();
	log.debug(
		"estraiAllegatiDaQuadri: Trovato l'allegato codiceAllegato:[{}], tipologiaAllegato:[{}], adempimentoAllegato:[{}], spiegazioneAllegato:[{}]",
		new Object[] { codiceAllegato, tipologiaAllegato, adempimentoAllegato, spiegazioneAllegato });
	File documento = allegatoRichiesto.getTemplateAllegato();
	Oggetti template = null;
	if (documento != null) {
	    if (documento.getDatiFile() != null) {
		if (documento.getDatiFile().length > 0) {
		    template = new Oggetti();
		    template.setNomefile(StringUtils.right(
			    StringUtils.defaultIfEmpty(documento.getNomeFile(),
				    "Documento_non_codificato" + StringUtils.defaultIfEmpty(documento.getContentType(), "") + ".doc"), 255));
		    template.setOggetto(documento.getDatiFile());
		    log.debug("estraiAllegatiDaQuadri: Prima di inserire il file:[{}], BLOB length:[{}]", template.getNomefile(),
			    documento.getDatiFile().length);
		    oggettiService.insert(template);
		    log.debug("estraiAllegatiDaQuadri: inserito il file:[{}], cType:[{}]", template.getNomefile(),
			    StringUtils.defaultIfEmpty(documento.getContentType(), ""));
		}
	    }
	}
	String descrizione = tipologiaAllegato;
	if (StringUtils.isBlank(descrizione)) {
	    descrizione = spiegazioneAllegato;
	}
	if (StringUtils.isBlank(descrizione)) {
	    if (template == null) {
		log.warn("inserisciAllegatoEndo1: Allegato vuoto non inserito");
		return;
	    }
	    descrizione = template.getNomefile();
	}
	Allegati allegato = new Allegati();
	allegato.setAllegato(StringUtils.left(descrizione, 500));
	allegato.setInventarioprocedimento(endo);
	recuperaSettaggiAllegato(allegato);
	allegato.setOggetti(template);
	allegatiService.insert(allegato);
	log.debug("estraiAllegatiDaQuadri: effettuato l'inserimento nella tabella Allegati");
	// §§§END§§§
    }

    protected void gestisciElencoQuadri(ElencoQuadri elencoquadri, Inventarioprocedimenti endo) {

	// §§§BEGIN§§§
	if (elencoquadri != null) {
	    if (elencoquadri.getQuadro() != null) {
		if (elencoquadri.getQuadro().size() > 0) {
		    List<ElencoQuadri.Quadro> quadris = elencoquadri.getQuadro();
		    estraiAllegatiDaQuadri(endo, quadris);
		}
	    }
	}// §§§END§§§
    }

    protected void recuperaSettaggiAllegato(Allegati allegato) {

	// §§§BEGIN§§§
	// TODO LEGGERE DALLA CONFIGURAZIONE DELLA VERTICALIZZAZIONE
	allegato.setCosto(BigDecimal.ZERO);
	allegato.setFoRichiedefirma(Boolean.TRUE);
	allegato.setFoTipodownload(null);
	allegato.setOrdine(0);
	allegato.setPubblica(0);
	allegato.setFlagInserimentoAut(Boolean.TRUE);
	allegato.setRichiesto(Boolean.TRUE);
	// §§§END§§§
    }

    protected void recuperaSettaggiDocumentoAlberoproc(AlberoprocDocumenti allegato) {

	// §§§BEGIN§§§
	// TODO LEGGERE DALLA CONFIGURAZIONE DELLA VERTICALIZZAZIONE
	allegato.setFlgDomandafo(Boolean.FALSE);
	allegato.setFoRichiedefirma(Boolean.TRUE);
	allegato.setFoTipodownload(null);
	allegato.setOrdine(0);
	allegato.setPubblica(0);
	allegato.setFlagInserimentoAut(Boolean.TRUE);
	allegato.setRichiesto(Boolean.TRUE);
	// §§§END§§§
    }

    /**
     * @param endo
     * @param quadris
     */
    protected void estraiAllegatiDaQuadri(Inventarioprocedimenti endo, List<ElencoQuadri.Quadro> quadris) {

	// §§§BEGIN§§§
	for (Quadro quadro : quadris) {
	    File testoQuardo = quadro.getTestoQuadro();
	    // TODO CHE CI FO'?
	    ElencoAllegatiRichiesti allegati = quadro.getElencoAllegatiRichiestiQuadro();
	    if (allegati != null) {
		if (allegati.getAllegatoRichiesto() != null) {
		    List<AllegatoRichiesto> allegatoRichiestoList = allegati.getAllegatoRichiesto();
		    for (AllegatoRichiesto allegatoRichiesto : allegatoRichiestoList) {
			if (allegatoRichiesto != null) {
			    inserisciAllegatoEndo1(endo, allegatoRichiesto);
			}
		    }
		}
	    }
	}
	// §§§END§§§
    }

    @Override
    public void setEffettuaValidazioneSchema(boolean effettuaValidazioneSchema) {

	getCartService().setEffettuaValidazioneSchema(effettuaValidazioneSchema);
    }

    @Override
    public boolean isEffettuavalidazioneSchema() {

	return getCartService().isEffettuavalidazioneSchema();
    }
}