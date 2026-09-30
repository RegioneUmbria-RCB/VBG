package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.paevolution.ws.pagamenti_types.InfoConnettoreType;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;

@XmlRootElement()
public class OperazioniConsentiteResponseType {

    @XmlElement(name = "generazioneAvviso")
    private String generazioneAvviso;
    @XmlElement(name = "verificaStato")
    private String verificaStato;
    @XmlElement(name = "pagamentiOffline")
    private String pagamentiOffline;
    @XmlElement(name = "generazioneFattura")
    private String generazioneFattura;
    @XmlElement(name = "generazioneRicevuta")
    private String generazioneRicevuta;
    @XmlElement(name = "attivazioneSessionePagamento")
    private String attivazioneSessionePagamento;
    @XmlElement(name = "modificaDataScadenza")
    private String modificaDataScadenza;
    @XmlElement(name = "annullaPosizioneDebitoria")
    private String annullaPosizioneDebitoria;

    public OperazioniConsentiteResponseType() {

	super();
    }

    private String baseUrl;
    InfoConnettoreType info;
    DettPosizioneDebitoria posizione;
    private static final String PATH_CONTROLLER = "dettposizionedebitoria";
    private static final String PATH_ATTIVASESSIONEPAGAMENTO = "attivaSessionePagamento.htm";
    private static final String PATH_GENERAAVVISO = "ajaxGeneraAvviso.htm";
    private static final String PATH_GENERAFATTURA = "ajaxGeneraFattura.htm";
    private static final String PATH_GENERARICEVUTA = "ajaxGeneraRicevuta.htm";
    private static final String PATH_PAGAOFFLINE = "pagaOffline.htm";
    private static final String PATH_VERIFICASTATO = "ajaxVerificaStato.htm";
    private static final String PATH_MODIFICA_DATA_SCADENZA = "ajaxModificaDataScadenza.htm";
    private static final String PATH_ANNULLA_POSIZIONE = "ajaxAnnullaPosizioneDebitoria.htm";

    public OperazioniConsentiteResponseType(InfoConnettoreType info, String baseUrl, DettPosizioneDebitoria posizione) {

	super();
	if (info == null) {
	    return;
	}
	this.baseUrl = baseUrl;
	this.info = info;
	this.posizione = posizione;
	this.attivazioneSessionePagamento = this.urlAttivaSessionePagamento();
	this.generazioneAvviso = this.urlGenerazioneAvviso();
	this.generazioneFattura = this.urlGenerazioneFattura();
	this.generazioneRicevuta = this.urlGenerazioneRicevuta();
	this.pagamentiOffline = this.urlPagamentiOffline();
	this.verificaStato = this.urlVerificaStato();
	this.modificaDataScadenza = this.urlModificaDataScadenza();
	this.annullaPosizioneDebitoria = this.urlAnnullaPosizioneDebitoria();
    }

    private String urlModificaDataScadenza() {

	boolean metodoConsentito = this.info.isSupportaModificaDataScadenza() && !this.posizione.isConclusa();
	if (!metodoConsentito) {
	    return null;
	}
	return this.costruisciUrl(OperazioniConsentiteResponseType.PATH_MODIFICA_DATA_SCADENZA);
    }

    private String urlAttivaSessionePagamento() {

	boolean metodoConsentito = this.info.isSupportaAttivaSessionePagamento() && !this.posizione.isConclusa();
	if (!metodoConsentito) {
	    return null;
	}
	return this.costruisciUrl(OperazioniConsentiteResponseType.PATH_ATTIVASESSIONEPAGAMENTO);
    }

    private String urlGenerazioneAvviso() {

	boolean metodoConsentito = this.info.isSupportaInvioAvviso() && !this.posizione.isConclusa();
	if (!metodoConsentito) {
	    return null;
	}
	return this.costruisciUrl(OperazioniConsentiteResponseType.PATH_GENERAAVVISO);
    }

    private String urlGenerazioneFattura() {

	boolean metodoConsentito = this.info.isSupportaGenerazioneFattura() && this.posizione.isPagata();
	if (!metodoConsentito) {
	    return null;
	}
	return this.costruisciUrl(OperazioniConsentiteResponseType.PATH_GENERAFATTURA);
    }

    private String urlGenerazioneRicevuta() {

	boolean metodoConsentito = this.info.isSupportaDownloadRicevuta() && this.posizione.isPagata();
	if (!metodoConsentito) {
	    return null;
	}
	return this.costruisciUrl(OperazioniConsentiteResponseType.PATH_GENERARICEVUTA);
    }

    private String urlPagamentiOffline() {

	boolean metodoConsentito = this.info.isSupportaPagamentoOffLine() && !this.posizione.isConclusa();
	if (!metodoConsentito) {
	    return null;
	}
	return this.costruisciUrl(OperazioniConsentiteResponseType.PATH_PAGAOFFLINE);
    }

    private String urlVerificaStato() {

	return this.costruisciUrl(OperazioniConsentiteResponseType.PATH_VERIFICASTATO);
    }

    private String urlAnnullaPosizioneDebitoria() {

	if (this.posizione.isConclusa() || this.posizione.isPagata()) {
	    return null;
	}
	return this.baseUrl + "/" + OperazioniConsentiteResponseType.PATH_CONTROLLER + "/" + OperazioniConsentiteResponseType.PATH_ANNULLA_POSIZIONE;
    }

    private String costruisciUrl(String urlOperazione) {

	return this.baseUrl + "/" + OperazioniConsentiteResponseType.PATH_CONTROLLER + "/" + urlOperazione + "?idDettPosizioneDebitoria="
		+ this.posizione.getId().getCodice();
    }
}
