package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.gruppoinit.pal.gp.core.domain.BaseDomainObject;
import it.gruppoinit.pal.gp.core.domain.IdAwareDomainObject;

@Entity
@Table(name = "PAY_CONNECTOR_CONFIG_PARAMS")
public class PayConnectorConfigParams extends BaseDomainObject implements Serializable, IdAwareDomainObject<String> {

    /**
     * Enumeration dei parametri dei connettori che possono essere condivisi da più connettori o che servono al
     * funzionamento generale del nodo. Per parametri utilizzati esclusivamente da singoli connettori si consiglia di
     * creare delle altre enumeration nelle classi dei connettori stessi.
     * 
     * @author Franco.Leone
     *
     */
    public enum ConfigParamNames {

	AUTH_CODICE_ENTE("Parametro codice_ente usato per i servizi soap/rest per il connettore EasyPA"), //
	AUTH_CODICE_ISTITUTO("Parametro codice_istituto usato per i servizi soap/rest per il connettore EasyPA"), //
	AUTH_GRANT_TYPE("Parametro grant_type usato per i servizi soap/rest per il connettore EasyPA"), //
	AUTH_ID_DOMINIO("Parametro id_dominio usato per i servizi soap/rest per il connettore EasyPA"), //
	AUTH_ID_ENTE("Parametro id_ente usato per i servizi soap/rest per il connettore EasyPA"), //
	CENTRO_DI_COSTO("Codice del centro di costo utilizzato per la nomenclatura dei files del flusso NEXI"), //
	CODICE_SIA_ENTE("Codice SIA dell'amministrazione / ente. E' rappresentato da 5 caratteri alfanumerici (ad esclusione della lettera O di Otranto), viene richiesto da una Banca per conto della Società interessata."), //
	DOCUMENTI_SERVICE("URL del servizio del BO per la generazione di documenti"), //
	DOCUMENTI_SU_FILESYSTEM("Specificando un percorso di una cartella su filesystem il nodo pagamenti salverà i documenti nella cartella specificata. Se la cartella non esiste verrà creata."), //
	FVG_PAY_USA_AUTH_PAG_IMMED("Nel pagamento immediato sovrascrive il parametro autenticazione (il valore predefinito se non impostato è true). Accetta Valori true/false."), //
	GOV_PAY_URL_API_PAGAMENTI("Indica la url per invocare le API del servizio pagamenti. es /govpay/frontend/api/pagamento/rs/basic/pagamenti"), //
	GOV_PAY_URL_API_PENDENZE("Indica la url per invocare le API del servizio pendenze. es /govpay/backend/api/pendenze/rs/basic/v2/pendenze"), //
	GOV_PAY_URL_API_PROFILO("Indica la url per invocare le API del servizio profilo. es /govpay/backend/api/pendenze/rs/basic/v2"), //
	GOV_PAY_BASE_URL_PATCH_OPS("Indica la base url per le operazioni REST con metodo PATCH. E' successo che per le operazioni con questo metodo abbiano dovuto configurare nginx con una path di base differente es: https://govway-patch-dev.regione.abruzzo.it invece che https://govway-dev.regione.abruzzo.it"),
	OFFLINE_PAYMENT_METHODS("Il parametro accetta valori true o false e indica sui sistemi tipo pago umbria se nei pagamenti online modello 1 presentare la possibilità di scaricare un documento per il pagamento offline. Attualmente usato da PAGOUMBRIA"), //
	PAGAMENTI_FUORI_VBG("Indicare la mappatura della causale che deve essere usata per registrare i pagamenti non creati in VBG. Serve per quei connettori che devono allineare i pagamenti di altri sistemi dell'ente non gestiti direttamente da VBG."),
	PAYER_HASH_PRIMARY_KEY("Chiave primaria per la generazione dell'hash dei messaggi nel connettore PayER"),
	PAYER_HASH_SECONDARY_KEY("Chiave secondaria per la generazione dell'hash dei messaggi nel connettore PayER"),
	PAYER_CODICE_UFFICIO("Parametro di configurazione dell'ente per PayER"),
	PAYER_TIPO_UFFICIO("Parametro di configurazione dell'ente per PayER"),
	PAYER_CODICE_UTENTE("Parametro di configurazione dell'ente per PayER"),
	PAYER_TIPOLOGIA_SERVIZIO("Parametro di configurazione dell'ente per PayER"),
	PAYER_WINDOW_MINUTES("Minuti di tolleranza sulla sincronizzazione del timestamp nelle comunicazioni con PayER"),
	PAYER_CLIENT_KEY("Chiave dell'api payer"),
	PAYER_CLIENT_SECRET("Secret dell'api payer"),
	SECURITY_ALIAS("Id comune alias per interrogare securiry"), //
	SECURITY_PWD("Password per la connessione a security"), //
	SECURITY_URL("URL del servizio di security"), //
	SECURITY_USER("Utente per la connessione a security"), //
	SSL_TRUST_STORE_LOCATION("Indicare il percorso al trust store per l'autenticazione dei client soape rest con certificato SSL"), //
	SSL_TRUST_STORE_PASSWORD("Indicare la password del trust store per l'autenticazione dei client soape rest con certificato SSL"), //
	SSL_KEY_STORE_LOCATION("Indicare il percorso del key store che contiene il certificato client per l'autenticazione dei client soape rest con certificato SSL"), //
	SSL_KEY_STORE_PASSWORD("Indicare la password del key store che contiene il certificato client per l'autenticazione dei client soape rest con certificato SSL"), //
	SSL_KEY_STORE_CERT_ALIAS("Indicare l'alias del certificato da estrare per dal key store del certificato client per l'autenticazione dei client soape rest con certificato SSL"), //
	TIPO_DOCUMENTO_DEBITO("Tipologia di documento di debito da emettere per il connettore NEXI: per i valori fare riferimento all'enum java NexiGenovaConnector.TipologiaDocumentoDebito"), //
	URL_CALLBACK_CAMBIO_STATO("Url del servizio che si mette in ascolto dei cambiamenti di stato delle posizioni debitorie"), //
	MAX_POSIZIONI("Limite massimo di posizioni debitorie che possono essere passate al servizio di pagamento esterno in ciascuna chiata ai servizi"), // 
	PPAY_USA_SERV_REST_ATTIVA_SESS("Nel caso del connettore Piemonte PAY REST determina se usare il servizio di attiva sessione del connettore non REST. Valori possibili (true o false) Il valore predefinito è true e indica che viene usato il servizio rest, mentre false indica che viene usato il servizio classico"), //
	PPAY_USA_SERV_REST_ANNULLA_POS("Nel caso del connettore Piemonte PAY REST determina se usare il servizio di annulla posizione debitoria del connettore non REST - modalità asincrona. Valori possibili (true o false) Il valore predefinito è true e indica che viene usato il servizio rest, mentre false indica che viene usato il servizio classico"), //
	PPAY_USA_SERV_GET_RT_SUPPORT("Nel caso del connettore Piemonte PAY REST determina se usare il servizio di GET RT oppure getDebtPositionData (nuove API). Valori possibili (true o false) Il valore predefinito è true e indica che viene usato il servizio GETRT, mentre false indica che viene usato il servizio getDebtPositiondata ovvero la ricevuta non sarà disponibile"), //
	VERSIONE("Se previsto specificare la versione del webservice da invocare"), //
	URL_RICEZIONE_NOTIFICHE("URL a cui il nodo pagamenti si aspetta di ricevere le notifiche di pagamento"), //
	DELAY_VERIFICA_STATO("Il parametro indica in millisecondi il tempo che deve essere trascorso dalla apertura della posizione debitoria per poter fare la verifica dello stato della posizione debitoria"), //
	MIPGE_GENOVA_APP_CODE("Codice app nel wso2"), //
	MIPGE_GENOVA_CLIENT_ID("Id del client sul ws02"), //
	MIPGE_GENOVA_SUBJECT("Soggetto nel wso2"), //
	MIPGE_GENOVA_IIS("issuer sul wso2"), //
	MIPGE_GENOVA_AUD("audience sul wso2"), //
	MIPGE_CODICE_ENTE("CODICE ENTE da specificare nei tracciati"), //
	// LETTURA TRACCIATI PAGO PA STANDARD
	SCHED_TRAC_PAGOPA_STRATEGIA("Parametri per la lettura dei tracciati Standard PAGOPA: La strategia usata per leggere i file di tracciato scegliere tra i valori SFTP, FTP"), //
	SCHED_TRAC_PAGOPA_FTP_USER("Parametri per la lettura dei tracciati Standard PAGOPA: Utente per il collegamento FTP/SFTP"), //
	SCHED_TRAC_PAGOPA_FTP_PASSWORD("Parametri per la lettura dei tracciati Standard PAGOPA: Password per il collegamento FTP/SFTP"), //
	SCHED_TRAC_PAGOPA_FTP_SERVER("Parametri per la lettura dei tracciati Standard PAGOPA: Indirizzo del server per il collegamento FTP/SFTP"), //
	SCHED_TRAC_PAGOPA_FTP_PORT("Parametri per la lettura dei tracciati Standard PAGOPA: Porta del servizio FTP/SFTP"), //
	SCHED_TRAC_PAGOPA_FTP_FOLDER("Parametri per la lettura dei tracciati Standard PAGOPA: La folder di accesso del servizio FTP/SFTP"), //
	SCHED_TRAC_PAGOPA_QUARTZEXP("Parametri per la lettura dei tracciati Standard PAGOPA: L'espressione QUARTZ per indicare la tempistica di schedulazione"), //
	SCHED_CARICAM_MASS_QUARTZEXP("Parametro per la gestione del caricamento massivo delle posizioni debitorie: Indicare la Strategia (es. MIP ==> GENOVA_NEXI il connettore MIP può gestire tracciati NEXI GENOVA)"), //
	SCHED_CARICAM_MASS_ATTIVO("Parametro per la gestione del caricamento massivo delle posizioni debitorie: Se attivare o meno l'elaborazione. Indicare i valori true, false"), //
	SCHED_CARICAM_MASS_STRATEGIA("Parametro per la gestione del caricamento massivo delle posizioni debitorie: L'espressione QUARTZ per indicare la tempistica di schedulazione"), //
	CODICE_ABI_ENTE_CREDITORE("Codice Abi dell'ente creditore"), //
	ID_INSTALLAZIONE("Id installazione dell'ente") //
	;

	private String desc;

	private ConfigParamNames(String desc) {

	    this.desc = desc;
	}

	public String value() {

	    return name();
	}

	public String description() {

	    return desc;
	}

	public static ConfigParamNames fromValue(String v) {

	    return valueOf(v);
	}
    }

    private static final long serialVersionUID = -599252403013756426L;
    private String configParam;
    private String descrizione;
    private PayConnectorConfig connettore;

    public PayConnectorConfigParams() {

	this.connettore = new PayConnectorConfig();
    }

    @Id
    @Column(name = "CONFIG_PARAM", unique = true, nullable = false, length = 30)
    public String getConfigParam() {

	return this.configParam;
    }

    public void setConfigParam(String configParam) {

	this.configParam = configParam;
    }

    @Override
    @Transient
    public String getId() {

	return getConfigParam();
    }

    @Override
    public void setId(String id) {

	setConfigParam(id);
    }

    @Column(name = "DESCRIZIONE", nullable = false, length = 200)
    public String getDescrizione() {

	return this.descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({ @JoinColumn(name = "CODICE_CONNETTORE", insertable = false, updatable = false) })
    public PayConnectorConfig getConnettore() {

	return connettore;
    }

    public void setConnettore(PayConnectorConfig connettore) {

	this.connettore = connettore;
    }
}
