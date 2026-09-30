package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.custom;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.EstremiAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.NumerazioneEnum;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.NumerazioneService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati.CodiceFirmatario;

public class NumerazioneCustomFactory implements NumerazioneService {

    public static NumerazioneEnum TipoNumerazione = NumerazioneEnum.CUSTOM;
    private NumerazioneCustomService service;

    public NumerazioneCustomFactory(Autorizzazioni aut) {

	String serviceImplName = aut.getTipologiaregistro().getNumerazioneCustom();
	if (StringUtils.isNotBlank(serviceImplName)) {
	    this.service = (NumerazioneCustomService) ContextLoader.getCurrentWebApplicationContext().getBean(serviceImplName);
	    this.service.setIstanza(aut.getIstanza());
	    this.service.setMovimento(aut.getMovimenti());
	    //Verifico se nei metadati è indicato il firmatario
	    if (aut.getMetadati() == null || aut.getMetadati().isEmpty()) {
		return;
	    }
	    for (AutorizzazioniMetadati metadato : aut.getMetadati()) {
		if (CodiceFirmatario.CHIAVE.equalsIgnoreCase(metadato.getId().getChiave())) {
		    this.service.setCodiceFirmatario(metadato.getValore());
		    break;
		}
	    }
	}
    }

    @Override
    public EstremiAutorizzazione get() {

	if (this.service == null) {
	    throw new RuntimeException(
		    "Impossibile utilizzare un servizio di numerazione personalizzato senza configurare, nel registro, la classe che lo gestisce");
	}
	return this.service.get();
    }

    @Override
    public EstremiAutorizzazione assegnaNumero() {

	return this.service.assegnaNumero();
    }
}
