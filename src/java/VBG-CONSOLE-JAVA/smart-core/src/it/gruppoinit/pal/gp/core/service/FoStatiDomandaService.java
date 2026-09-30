/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FoStatiDomanda;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francol
 *
 */
public interface FoStatiDomandaService extends BaseService<FoStatiDomanda, PkId> {

    public static final String ERROR_MESSAGES_SEPARATOR = "**";

    public static enum StatoDomandaFacctEnum {
	//CREAZIONE("Creazione domanda"), 
	//MODIFICA("Aggiornamento dati domanda"),
	IN_COMPILAZIONE("In compilazione"), //azioni: completare modulistica e presentare domanda
	INVIATA_CON_ERRORI("Inviata con errori"), //azioni: tentare reinvio (o attendere bugfix)
	RICEVUTA_DA_COMUNICA("Ricevuta da COMUNICA"), //azioni: attendere esito o richiesta integrazione dal SUAP
	INOLTRATA_SUAP("Inoltrata al SUAP"), //azioni: attendere conferma ricezione dal SUAP
	RICEVUTA_SUAP("In carico al SUAP"), //azioni: attendere esito o richiesta integrazione dal SUAP
	INTEGRAZIONE_RICHIESTA("In attesa di integrazione documentale"), //azioni: inviare documentazione richiesta
	INTEGRAZIONE_INOLTRATA("L'integrazione richiesta e' stata inoltrata"), //azioni: inviare documentazione richiesta
	CONFORMAZIONE_RICHIESTA("In attesa di Conformazione"), //azioni: inviare documentazione richiesta
	CONFORMAZIONE_INOLTRATA("La conformazione richiesta e' stata inoltrata"), //azioni: inviare documentazione richiesta
	ACCETTATA("Chiusa con esito positivo"), //azioni: va a lavorare basta burocrazia!
	COMUNICAZIONE_RICHIESTA("Nuova Comunicazione"), //azioni: inviare documentazione richiesta
	COMUNICAZIONE_INOLTRATA("Comunicazione inviata all'ente"), //azioni: inviare documentazione richiesta
	RIFIUTATA("Chiusa con esito negativo"), //azioni: va a lavorare basta burocrazia!
	DINIEGO("Chiusa con esito negativo dovuto a diniego"), //azioni: ritenta sarai più fortunato! 
	RIGETTO("Chiusa con esito negativo dovuto a rigetto")//azioni: ritenta sarai più fortunato!
	//ETC.ETC: aggiungere altri tipi di eventi all'enum se necessario
	;

	private String value;

	private StatoDomandaFacctEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public List<FoStatiDomanda> findByIdDomanda(String idComuneDomanda, Integer idDomanda);

    /**
     * Traccia l'avanzamento dello stato della domanda inserendo un nuovo record in FO_STATI_DOMANDA, restituisce il
     * record appena inserito
     * 
     * @param idDomanda
     * @param sdeproxy
     * @param newStatus
     * @param message
     */
    public FoStatiDomanda aggiornaStatoDomanda(Integer idDomanda, StatoDomandaFacctEnum newStatus, String message, String azioneRichiesta);

    /**
     * Restituisce lo stato aggiornato della domanda passata come argomento: viene restituito il record di
     * FO_STATI_DOMANDA con la data più recente
     * 
     * @param domanda
     * @return
     */
    public FoStatiDomanda getStatoDomanda(String idComuneDomanda, Integer idDomanda);
}
