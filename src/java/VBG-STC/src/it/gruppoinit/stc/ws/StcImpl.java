package it.gruppoinit.stc.ws;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.stc.service.StcManager;
import it.gruppoinit.stc.service.StcService;
import it.init.sigepro.rte.AggiungiDocumentiRequest;
import it.init.sigepro.rte.AggiungiDocumentiResponse;
import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.CancellaAttivitaRequest;
import it.init.sigepro.rte.CheckTokenRequest;
import it.init.sigepro.rte.CheckTokenResponse;
import it.init.sigepro.rte.DirezioneSportelloRequest;
import it.init.sigepro.rte.DirezioneSportelloResponse;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.LoginRequest;
import it.init.sigepro.rte.LoginResponse;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.RichiestaPraticaCollegataDaAttivitaDestinatariaRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataDaAttivitaMittenteRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.RichiestaPraticheListaRequest;
import it.init.sigepro.rte.RichiestaPraticheListaResponse;
import it.init.sigepro.rte.definitions.Stc;

// @WebService(targetNamespace="http://sigepro.init.it/rte/definitions", name="Stc")
@WebService(serviceName = "StcService", portName = "StcSoap11", targetNamespace = "http://sigepro.init.it/rte/definitions", endpointInterface = "it.init.sigepro.rte.definitions.Stc")
public class StcImpl implements Stc {

    @Autowired
    private StcService stcService;
    @Autowired
    private StcManager stcManager;

    public LoginResponse login(LoginRequest request) {

	LoginResponse response = stcService.login(request);
	return response;
    }

    public CheckTokenResponse checkToken(CheckTokenRequest request) {

	CheckTokenResponse response = stcService.checkToken(request);
	return response;
    }

    public DirezioneSportelloResponse direzioneSportello(DirezioneSportelloRequest request) {

	DirezioneSportelloResponse response = stcService.direzioneSportello(request);
	return response;
    }

    public NotificaAttivitaResponse notificaAttivita(NotificaAttivitaRequest request) {

	NotificaAttivitaResponse response = stcManager.notificaAttivita(request);
	return response;
    }

    public AllegatoBinarioResponse allegatoBinario(AllegatoBinarioRequest request) {

	AllegatoBinarioResponse response = stcService.allegatoBinario(request);
	return response;
    }

    public RichiestaPraticheListaResponse richiestaPraticheLista(RichiestaPraticheListaRequest request) {

	RichiestaPraticheListaResponse response = stcService.richiestaPraticheLista(request);
	return response;
    }

    public RichiestaPraticaResponse richiestaPratica(RichiestaPraticaRequest request) {

	RichiestaPraticaResponse response = stcService.richiestaPratica(request);
	return response;
    }

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegata(RichiestaPraticaCollegataRequest request) {

	RichiestaPraticaCollegataResponse response = stcService.richiestaPraticaCollegata(request);
	return response;
    }

    public InserimentoPraticaResponse inserimentoPratica(InserimentoPraticaRequest request) {

	InserimentoPraticaResponse response = stcService.inserimentoPratica(request);
	return response;
    }

    public AggiungiDocumentiResponse aggiungiDocumenti(AggiungiDocumentiRequest request) {

	AggiungiDocumentiResponse response = stcService.aggiungiDocumenti(request);
	return response;
    }

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaMittente(RichiestaPraticaCollegataDaAttivitaMittenteRequest request) {

	return this.stcService.richiestaPraticaCollegataDaAttivitaMittente(request);
    }

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaDestinataria(
	    RichiestaPraticaCollegataDaAttivitaDestinatariaRequest request) {

	return this.stcService.richiestaPraticaCollegataDaAttivitaDestinataria(request);
    }

    public void cancellaAttivita(CancellaAttivitaRequest request) {

	this.stcService.cancellaAttivita(request);
    }
}
