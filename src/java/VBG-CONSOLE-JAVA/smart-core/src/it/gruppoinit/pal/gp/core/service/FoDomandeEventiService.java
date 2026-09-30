/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FoDomandeEventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import it.toscana.regione.suap.sem.types.procedimento.ConfermaRicezioneReqType;
import it.toscana.regione.suap.sem.types.procedimento.InviaAllegatoRespType;
import it.toscana.regione.suap.sem.types.procedimento.InviaStimoloResponseType;
import it.toscana.regione.suap.sem.types.procedimento.RichiediAllegatoRespType;
import it.toscana.regione.suap.sem.types.procedimento.StatoMessaggioRespType;

import java.util.List;

/**
 * @author francol
 *
 */
public interface FoDomandeEventiService extends BaseService<FoDomandeEventi, PkId> {

    public static enum TipoEventoEnum {
	//CREAZIONE("Creazione domanda"), 
	//MODIFICA("Aggiornamento dati domanda"), 
	PRESENTAZIONE("Presentazione pratica"), INVIO_ALLEGATO("Trasmissione allegato"), PUBBLICA_RICEVUTA("Trasmissione ricevuta al back office"), CONFERMA_RICEZIONE(
		"Conferma avvenuta ricezione"), STATO_MESSAGGIO("Verifica stato messaggio"), RICEZIONE_COMUNICA("Ricezione pratica da COMUNICA"), RICHIEDI_ALLEGATO(
		"Richiesta allegato"), RICEZIONE_RICHIESTA_INTEGRAZIONI("Richiesta integrazioni"), ERRORE_FACCT("Errore interno del FACCT"),
	//evento generico di ricezione_stimolo
	RICEZIONE_STIMOLO("Ricezione stimolo dal SEM")
	//ETC.ETC: aggiungere altri tipi di eventi all'enum se necessario
	;

	private String value;

	private TipoEventoEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public List<FoDomandeEventi> findByIdDomanda(String idComuneDomanda, Integer idDomanda);

    public List<FoDomandeEventi> findByIdSistemaAndAttore(String idMessaggioSistema, String attore);

    public void registraEventoDomandaRFC239(FoDomandeEventi evt);

    public FoDomandeEventi registraEventoInviaStimolo(InviaStimoloResponseType resp, Integer idDomanda, TipoEventoEnum tipoEvento);

    public FoDomandeEventi registraEventoInvioAllegato(InviaAllegatoRespType resp, Integer idDomanda, String idMessaggio, String docId);

    public FoDomandeEventi registraEventoStatoMessaggio(StatoMessaggioRespType resp, Integer idDomanda, String idMessaggio);

    public FoDomandeEventi registraEventoErroreFACCT(Integer idDomanda, String messaggioErrore);

    public FoDomandeEventi registraEventoRicezioneStimolo(String idMessaggio, String tipoStimolo, String mittente, String messaggioErrore);

    public FoDomandeEventi registraEventoRicezioneComunica(Integer idDomanda, String messaggioErrore);

    public FoDomandeEventi registraEventoRichiediAllegato(RichiediAllegatoRespType richAllResp, Integer idDomanda, String idMessaggio,
	    String idAllegato);

    public FoDomandeEventi registraEventoRicezioneRichiestaIntegrazioni(Integer idDomanda, String messaggioErrore, String mittente);

    public FoDomandeEventi registraEventoConfermaRicezione(ConfermaRicezioneReqType ricezioneReq, String info, String messaggioErrore);
}
