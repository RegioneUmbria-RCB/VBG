package it.gruppoinit.pal.gp.core.features.manifestazioni.presenze;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioneCancellazioneAutConcException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.CheckSubentroRequest;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

public interface IVerificaModificaAutorizzazioniSuPresenzeService {

    EsitoElaborazioneEvento checkPossoSubentrare(CheckSubentroRequest request);

    void effettuaSubentroSuPresenza(int idPresenza, Integer idSubentroEffettuato) throws OperazioniSubentriException;

    EsitoElaborazioneEvento checkPossoModificareOccupante(Integer idAutOConc, Integer nuovoOccupante);

    void effettuaModificaOccupanteSuPresenza(int idPresenza, Integer idAutorizzazione) throws OperazioniSubentriException;

    EsitoElaborazioneEvento checkPossoModificareDataCessazioneSubentro(Integer idSubentroDaModificare, Date nuovaDataCessazione);

    void effettuaModificaDataCessazioneSubentroSuPresenza(Integer idPresenza, Integer idAutorizzazioniSubentri, Date vecchiaDataCessazione)
	    throws OperazioniSubentriException;

    EsitoElaborazioneEvento checkPossoEliminareUltimoPassaggioSubentriAutConc(Integer idSubentroUltimoPassaggioDaRipristinare);

    void effettuaOperazioniCancellazioneAutConcSuPresenza(Integer idPresenza, Integer idAutorizzazioni) throws OperazioneCancellazioneAutConcException;
}
