package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.custom;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.EstremiAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.WSAttiService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.InserisciDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;

@Service
public class NumerazioneDaWSAttiServiceImpl implements NumerazioneCustomService {

    private WSAttiService service;
    private VerticalizzazioneWSAttiService verticalizzazioneService;
    private MailtipoService testoTipoService;
    private Istanze istanza;
    private Movimenti movimento;
    private String codiceComune;
    private String codiceFirmatario;

    @Autowired
    public void setService(WSAttiService service) {

	this.service = service;
    }

    @Autowired
    public void setVerticalizzazioneService(VerticalizzazioneWSAttiService verticalizzazioneService) {

	this.verticalizzazioneService = verticalizzazioneService;
    }

    @Autowired
    public void setTestoTipoService(MailtipoService testoTipoService) {

	this.testoTipoService = testoTipoService;
    }

    @Override
    public EstremiAutorizzazione get() {

	return new EstremiAutorizzazione(null, null, Calendar.getInstance().getTime());
    }

    @Override
    public String getDescrizione() {

	return "Numerazione da WS Atti";
    }

    @Override
    public EstremiAutorizzazione assegnaNumero() {

	String oggetto = this.movimento.getParere();
	if (StringUtils.isBlank(oggetto)) {
	    Mailtipo testoTipo = this.verticalizzazioneService.getTestoTipoOggetto();
	    if (testoTipo != null) {
		if (this.movimento != null) {
		    oggetto = this.testoTipoService.replaceOggettoCorpo(testoTipo, null, this.movimento).getOggetto();
		} else {
		    oggetto = this.testoTipoService.replaceOggettoCorpo(testoTipo, this.istanza, null).getOggetto();
		}
	    }
	}
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(new Date());
	Integer idAlberoProc = this.movimento != null ? this.movimento.getIstanza().getAlberoproc().getId().getCodice()
		: this.istanza.getAlberoproc().getId().getCodice();
	InserisciDeterminaResponse response = this.service.inserisci(this.codiceComune, oggetto, idAlberoProc, codiceFirmatario);
	if (!response.getEsito().isOk()) {
	    throw new RuntimeException(response.getEsito().getMessaggio());
	}
	return new EstremiAutorizzazione(response.getIdDocumento().toString(), response.getNumero().toString(), calendar.getTime());
    }

    @Override
    public void setMovimento(Movimenti movimento) {

	if (movimento == null) {
	    return;
	}
	this.movimento = movimento;
	if (movimento.getIstanza() != null && this.istanza == null) {
	    this.setIstanza(movimento.getIstanza());
	}
    }

    @Override
    public void setIstanza(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.istanza = istanza;
	this.codiceComune = istanza.getComune().getCodicecomune();
    }

    @Override
    public void setCodiceFirmatario(String codiceFirmatario) {

	this.codiceFirmatario = codiceFirmatario;
    }
}
