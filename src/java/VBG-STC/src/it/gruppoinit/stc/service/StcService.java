package it.gruppoinit.stc.service;

import it.gruppoinit.stc.domain.Messaggiattivita;
import it.gruppoinit.stc.domain.Pratiche;
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

public interface StcService {

    public LoginResponse login(LoginRequest request);

    public CheckTokenResponse checkToken(CheckTokenRequest request);

    public DirezioneSportelloResponse direzioneSportello(DirezioneSportelloRequest request);

    /**
     * Notifica Attività: Gestione Pratiche.<br>
     * Gestisce la sola parte del metodo notificaAttivta relativa alla gestione delle pratiche del mittente e del
     * destinatario.<br>
     * Il metodo restituisce un array contenente la pratica del mittente e quella del destinatario
     * 
     * @param request
     * @return
     */
    public Pratiche[] notificaAttivitaGestionePratiche(NotificaAttivitaRequest request);

    public NotificaAttivitaResponse notificaAttivitaGestioneAttivita(NotificaAttivitaRequest request, Messaggiattivita messaggiattivita);

    public AllegatoBinarioResponse allegatoBinario(AllegatoBinarioRequest request);

    public RichiestaPraticaResponse richiestaPratica(RichiestaPraticaRequest request);

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegata(RichiestaPraticaCollegataRequest request);

    public InserimentoPraticaResponse inserimentoPratica(InserimentoPraticaRequest request);

    public RichiestaPraticheListaResponse richiestaPraticheLista(RichiestaPraticheListaRequest request);

    public AggiungiDocumentiResponse aggiungiDocumenti(AggiungiDocumentiRequest request);

    public Messaggiattivita notificaAttivitaCollegaAttivita(NotificaAttivitaRequest request, Pratiche pratiche, Pratiche pratiche2);

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaMittente(RichiestaPraticaCollegataDaAttivitaMittenteRequest request);

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaDestinataria(
	    RichiestaPraticaCollegataDaAttivitaDestinatariaRequest request);

    public Object cancellaAttivita(CancellaAttivitaRequest request);
}
