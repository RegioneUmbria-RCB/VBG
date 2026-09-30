package it.gruppoinit.pal.gp.pay.domain;

/**
 * enumeration delle tipologie di eventi gestite nel nodo pagamenti. I nomi delle tipologie di evento non devono essere
 * cambiate perché i valori dell'enumeration sono usati in PAY_RICHIESTE per gestire le richieste in sospeso oltre che
 * in PAY_IO_EVENTI per tracciare le chiamate ricevute e fatte dal nodo. Perciò modificare il nome dei valori enueration
 * provocerebbe la rottura della compatibilità nei sistemi già in produzione.
 * 
 * @author Franco.Leone
 *
 */
public enum TipiEvento {

    REGISTRA_POSIZIONI("Richiesta al nodo di inserimento posizioni debitorie", true),
    INVIA_POSIZIONI_A_PSP("Invio al PSP delle posizioni debitorie", false),
    ANNULLA_POSIZIONI("Richiesta al nodo di annullamento posizioni debitorie", true),
    ANNULLA_POSIZIONI_PSP("Richiesta al PSP di annullamento delle posizioni debitorie", false),
    ATTIVA_PAGAMENTO_OTF("Richiesta al nodo di attivazione di un pagamento on the fly", true),
    ATTIVA_PAGAMENTO_OTF_PSP("Invio al PSP di una richiesta di attivazione di un pagamento on the fly", false),
    INVIA_AVVISO("Richiesta al nodo di invio/generazione avviso pagamento", true),
    GENERA_AVVISO_PSP("Richiesta al PSP di generare gli avvisi di pagamento", false),
    RICEZIONE_AVVISO_DA_PSP("Richiesta proveniente dal PSP per la trasmissione al nodo dell'avviso di pagamento già richiesto in precedenza tramite altri servizi", true),
    RECUPERA_AVVISO_PSP("Richiesta inviata al PSP per scaricare l'avviso di pagamento la cui generazione è stata già richiesta in precedenza.", false),
    RECUPERA_RICEVUTA("Richiesta al nodo di download della ricevuta telematica di pagamento", true),
    RECUPERA_RICEVUTA_PSP("Richiesta al PSP di fornire al nodo la ricevuta telematica di pagamento", false),
    GENERA_FATTURA("Richiesta al nodo di invio/generazione della fattura per una posizione debitoria", true),
    GENERA_FATTURA_PSP("Richiesta a un sistema esterno di generare la fattura per una posizione debitoria", false),
    RICEZIONE_FATTURA_DA_PSP("ricezione di una chiamata a un servizio esposto dal nodo tramite il quale fattura già richiesta viene trasmessa al nodo pagamenti", true),
    RECUPERA_FATTURA_PSP("Richiesta a un sistema esterno di scaricare la fattura la cui generazione è stata già richiesta in precedenza", false),
    ELENCO_DOCUMENTI("Richiesta al nodo di elencare i documenti disponibili per una posizione debitoria", true),
    ATTIVA_SESSIONE("Richiesta al nodo di attivazione di una sessione di pagamento", true),
    ATTIVA_SESSIONE_PSP("Richiesta al PSP di attivazione di una sessione di pagamento", false),
    REGISTRA_PAGAMENTI_OFFLINE("Richiesta al nodo di registrazione dei pagamenti effettuati offline", true),
    ANNULLA_PAGAMENTI_OFFLINE_PSP("Richiesta al PSP di annullare i pagamenti effettuati offline", false),
    VERIFICA_STATO_PAGAMENTO("Ricezione richiesta di verifica stato pagamento", true),
    VERIFICA_STATO_PAGAMENTO_PSP("Chiamata a un servizio del PSP per la sincronizzazione dello stato dei pagamenti", false),
    NOTIFICA_PAGAMENTO_DA_PSP("Ricezione notifica dal PSP dell'avvenuto pagamento", true),
    RENDICONTAZIONE_PAGAMENTO_DA_PSP("Ricezione dal PSP del flusso di rendicontazione bancaria dei pagamenti avvenuti", true),
    RENDICONTAZIONE_PAGAMENTO_PSP("Richiesta al PSP del flusso di rendicontazione bancaria dei pagamenti avvenuti", false),
    CONFERMA_RICEZIONE_PSP("Ricezione dal PSP della conferma dell'inserimento delle posizioni debitorie", true),
    CONFERMA_ANNULLAMENTO_PSP("Ricezione dal PSP della conferma dell'annullamento delle posizioni debitorie", true),
    NOTIFICA_CAMBIO_STATO("Notifica del cambiamento di stato al client in ascolto", false);

    private String desc;
    private boolean isInput;

    private TipiEvento(String desc, boolean isInput) {

	this.desc = desc;
	this.isInput = isInput;
    }

    public String value() {

	return name();
    }

    public String description() {

	return desc;
    }

    public boolean isInput() {

	return isInput;
    }

    public static TipiEvento fromValue(String v) {

	return valueOf(v);
    }
}
