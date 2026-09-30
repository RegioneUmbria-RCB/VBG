/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.piemontepay.service;

import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.EsitoAggiornamentoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.EsitoInserimentoType;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema.CorpoNotifichePagamentoType.ElencoNotifichePagamento;

/**
 * @author francol
 *
 */
public interface PiemontepayService {
    
    
    
    public enum EsitiPPAY {
	
	CODE_000("L’invocazione del servizio si è conclusa correttamente"),
	CODE_050("Operazione eseguita parzialmente"),
	CODE_051("Nessuna posizione debitoria inserita"),	
	
	CODE_099("Trattamento manuale di servizio"),
	
	CODE_100("Errore applicativo generico."),
	CODE_101("I dati forniti in input al servizio non sono congruenti con l'XSD."),
	CODE_102("Ente Creditore non configurato nel sistema."),
	CODE_103("Codice versamento non configurato nel sistema."),
	CODE_104("Configurazione applicativo Ente Creditore non configurato nel sistema."),
	CODE_105("Configurazione Endpoint applicativo Ente Creditore non configurato nel sistema."),
	CODE_106("Configurazione non univoca Ente Creditore."),
	CODE_107("Numero di elementi dichiarato nei dati di testata, diverso dal numero di elementi effettivamente valorizzati."),
	CODE_108("Importo totale dichiarato nei dati di testata, diverso dalla somma degli importi dei valorizzati nei dettagli."),
	CODE_112("Id_messaggio duplicato per lo stesso tipo flusso"),
	CODE_117("I tipi aggiornamento gestiti sono: ANNULLAMENTO e MODIFICA"),
	CODE_150("Messaggio con id_messaggio già elaborato"),
	CODE_151("Codice fiscale ente creditore non trovato"),
	CODE_152("Codice versamento non trovato"),
	CODE_153("Codice versamento non univoco"),
	CODE_154("Non corrisponde il numero di posizioni debitorie"),
	CODE_155("Non corrisponde importo totale delle posizioni debitorie"),
	CODE_156("Operazione impossibile da eseguire: in una o più posizioni debitorie campo ImportoTotale assente oppure non valorizzato"),
	
	
	CODE_157( "Tipo pagamento del versamento non attivo"),
	CODE_158( "Tipo pagamento del versamento non permesso" ),
	
	
	
	CODE_160("Errore durante il caricamento della posizione debitoria"),
	CODE_161("IdPosizioneDebitoria già utilizzato per ente e CodiceVersamento"),
	CODE_162("Elemento IdPosizioneDebitoria mancante - elemento obbligatorio"),
	CODE_163("Il valore di elemento ImportoTotale deve essere maggiore di zero"),
	CODE_164("Elemento DescrizioneCausaleVersamento mancante - elemento obbligatorio"),
	CODE_165("Elemento SoggettoPagatore mancante - elemento obbligatorio"),
	CODE_166("Inserire elemento PersonaFisica oppure elemento PersonaGiuridica - elemento obbligatorio"),
	CODE_167("Elemento IdentificativoUnivocoFiscale mancante - elemento obbligatorio"),
	CODE_168("Elemento Nome mancante - elemento obbligatorio"),
	CODE_169("Elemento Cognome mancante - elemento obbligatorio"),
	CODE_170("Elemento RagioneSociale mancante - elemento obbligatorio"),


	CODE_171( "IUV mancante. L'elemento e' obbligatorio"),
	CODE_172( "Motivazione mancante. L'elemento e' obbligatorio"),
	CODE_173( "Tipo Aggiornamento mancante. L'elemento e' obbligatorio"),
	CODE_174( "Tipo Aggiornamento sconosciuto. Operazione non prevista."),
	CODE_175( "Errore durante l'aggiornamento di una posizione debitoria."),
	CODE_176( "Posizione debitoria non trovata per C.F ente $0, codice versamento $1, id posizione debitoria $2."),
	CODE_177( "Posizione debitoria in corso di pagamento"),

	CODE_178("Posizione debitoria già pagata"),
	CODE_179("Posizione debitoria già annullata"),

	CODE_180( "IdPosizioneDebitoria mancante. L'elemento e' obbligatorio"),
	CODE_181( "Pagamento (id Posizione Debitoria : $0) non modificabile."),
	CODE_182( "IdPosizioneDebitoria non univoca. (Ente: $0 - Codice Versamento $1 - IdPosizioneDebitori $2"),
	CODE_183( "Il pagamento ha troppi dettagli (piu' di 5). (Ente: $0 - Codice Versamento $1 - IdPosizioneDebitori $2"),
	CODE_184( "ImportoTotale ($3 Euro) differente dalla somma degli importi delle componenti ($4 Euro) del pagamento per IdPosizioneDebitoria $2, ente con C.F. $0 e CodiceVersamento $1 "),
	CODE_185( "Incongruenza nelle date di validita' per il pagamento con IdPosizioneDebitoria $2, ente con C.F. $0 e CodiceVersamento $1 "),
	CODE_186( "Dati Specifici RiscossioneImportoTotale obbligatori per la componente dell'importo."),
	CODE_187( "Pagamento gia' scaduto"),
	CODE_188( "Pagamento mai attivo"),
	CODE_191( "Anno accertamento della componente importo mancante" ),
	CODE_192( "Numero accertamento della componente importo mancante" ),
	CODE_193( "Dati mancanti nel riferimento pagamento" ),
	CODE_194( "Dati anagrafici della persona fisica errati" ),
	CODE_195( "Dati anagrafici della persona giuridica errati" ),

	//CODE_183("Il campo XML CodiceFiscaleEnte deve essere valorizzato con non meno di 1 caratteri e non più di 35 caratteri"),
	CODE_200("Errore generico di sistema."),
	CODE_201("Inserimento richiesta fallito per motivi di tipo tecnico."),
	CODE_202("Inserimento richiesta nella coda (Message Store) non andato a buon fine."),
	CODE_203("Aggiornamento richiesta fallito per motivi di tipo tecnico."),
	CODE_250("Errore caricamento. Problemi tecnici nella generazione degli IUV"),
	
	
	CODE_300( "Errore generico" ),
	CODE_301( "Il servizio non e' pronto" ),
	CODE_399( "Errore interno (CoopApplicativaPEC)" ),
	
	
	
	CODE_999("Errore generico non codificato")
	;

	private static final String CODE_PREFIX = "CODE_";
	private String desc;

	private EsitiPPAY(String desc) {

	    this.desc = desc;
	}

	public String value() {

	    return name();
	}
	
	public String errorCode() {
	    return this.name().substring(CODE_PREFIX.length());
	}

	public String description() {

	    return desc;
	}

	public static EsitiPPAY fromValue(String v) {

	    if(!v.startsWith(CODE_PREFIX)) {
		v = CODE_PREFIX + v;
	    }
	    return valueOf(v);
	}
    }

    public EsitiPPAY registraEsitoInserimentoPosizioni(EsitoInserimentoType esitoInserimento);
    
    public EsitiPPAY registraEsitoAggiornamentoPosizioni(EsitoAggiornamentoType esitoAggiornamento);
    
    public EsitiPPAY registraNotificaPagamenti(ElencoNotifichePagamento notifichePagamento);
}
