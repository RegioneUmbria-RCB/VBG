package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;

@Service
public class VerticalizzazioneWSAttiServiceImpl implements VerticalizzazioneWSAttiService {

    private VerticalizzazioniService service;
    private MailtipoService testoTipoService;
    private TipiMovimentoService tipiMovimentoService;
    public static final String nomeVerticalizzazione = "WS_ATTI";
    public static final String parClassifica = "CLASSIFICA";
    public static final String parCodiceDirigente = "CODICE_DIRIGENTE";
    public static final String parCodiceProponente = "CODICE_PROPONENTE";
    public static final String parCodiceTrattamento = "CODICE_TRATTAMENTO";
    public static final String parFascicolaAtto = "FASCICOLA_ATTO";
    public static final String parMovPrecompilaDocAut = "MOV_PRECOMPILA_DOC_AUT";
    public static final String parMovAttoCompletato = "MOV_ATTO_COMPLETATO";
    public static final String parOggettoTestoTipo = "OGGETTO_TESTOTIPO";
    public static final String parTipoConnettore = "TIPO_CONNETTORE";
    public static final String parUrl = "URL";
    public static final String parUrlFirmatari = "URL_FIRMATARI";
    public static final String parUtente = "UTENTE";
    public static final String parRuolo = "RUOLO";

    @Autowired
    public void setService(VerticalizzazioniService service) {

	this.service = service;
    }

    @Autowired
    public void setTestoTipoService(MailtipoService testoTipoService) {

	this.testoTipoService = testoTipoService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione);
    }

    @Override
    public String getClassifica() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione, VerticalizzazioneWSAttiServiceImpl.parClassifica);
    }

    @Override
    public String getCodiceDirigente() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione,
		VerticalizzazioneWSAttiServiceImpl.parCodiceDirigente);
    }

    @Override
    public String getCodiceProponente() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione,
		VerticalizzazioneWSAttiServiceImpl.parCodiceProponente);
    }

    @Override
    public boolean isFascicolaAtto() {

	return this.service.getBoolean(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione, VerticalizzazioneWSAttiServiceImpl.parFascicolaAtto,
		"S");
    }

    @Override
    public Mailtipo getTestoTipoOggetto() {

	Integer idTestoTipo = this.service.getInteger(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione,
		VerticalizzazioneWSAttiServiceImpl.parOggettoTestoTipo);
	if (idTestoTipo == null) {
	    return null;
	}
	Mailtipo testoTipo = this.testoTipoService.findById(new PkId(idTestoTipo));
	if (testoTipo == null) {
	    throw new RuntimeException("Controllare la verticalizzazione " +
		    VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione +
		    ", nel parametro " +
		    VerticalizzazioneWSAttiServiceImpl.parOggettoTestoTipo +
		    " è impostato un riferimento ad un testo tipo ineistente con id " +
		    idTestoTipo);
	}
	return testoTipo;
    }

    @Override
    public String getTipoConnettore() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione, VerticalizzazioneWSAttiServiceImpl.parTipoConnettore);
    }

    @Override
    public String getUrl() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione, VerticalizzazioneWSAttiServiceImpl.parUrl);
    }

    @Override
    public String getUtente() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione, VerticalizzazioneWSAttiServiceImpl.parUtente);
    }

    @Override
    public String getTrattamento() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione,
		VerticalizzazioneWSAttiServiceImpl.parCodiceTrattamento);
    }

    @Override
    public String getRuolo() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione, VerticalizzazioneWSAttiServiceImpl.parRuolo);
    }

    @Override
    public String getUrlFirmatari() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione, VerticalizzazioneWSAttiServiceImpl.parUrlFirmatari);
    }

    @Override
    public String getMovPrecompilaDocAut() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione,
		VerticalizzazioneWSAttiServiceImpl.parMovPrecompilaDocAut);
    }

    @Override
    public String getMovimentoCompletamentoDetermina() {

	return this.service.getString(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione,
		VerticalizzazioneWSAttiServiceImpl.parMovAttoCompletato);
    }
}
