/**
 * 
 */
package it.gruppoinit.regulus.gestoreincassi;

/**
 * Constanti utilizzate da Regulus.
 * 
 * @author francescop
 * 
 */
public class RegulusConstants {

    public static final String VERSIONE_MESSAGGIO = "2.0.000";
    public static final String CODICE_ENTE_ISTAT = "ISTAT";
    public static final String TIPO_CODICE_ENTE_ISTAT = "2";
    public static final String STATO_DEL_PAGAMENTO_DA_PAGARE = "0";
    public static final String STATO_DEL_PAGAMENTO_NON_PAGABILE = "4";
    public static final String STATO_DEL_PAGAMENTO_IN_PAGAMENTO = "20";
    public static final String CANALE_RISCOSSIONE = "WEB";
    public static final Integer NUMERO_CIFRE_DECIMALI_REGULUS = 2;
    public static final String TIPO_PAGAMENTO_DOC_REGISTRATO = "pagamento di un documento";
    public static final String TIPO_PAGAMENTO_SPONTANEO = "pagamento spontaneo";
    public static final String TIPO_PAGAMENTO_DOC_REGISTRATO_CODICE = "0";
    public static final String TIPO_PAGAMENTO_SPONTANEO_CODICE = "1";
    public static final int LUNGHEZZA_CODICEFISCALE = 16;
    public static final int LUNGHEZZA_PARTITAIVA = 11;
    /*
     * PARAMETRI DELLA VERTICALIZZAZIONE SISTEMA PAGAMENTI REGULUS
     */
    public static final String VERTICALIZZAZIONE_REGULUS = "SISTEMAPAGAMENTI_ATTIVO";
    public static final String VERTICALIZZAZIONE_REGULUS_PARAMETRO = "TIPOPAGAMENTO";
    public static final String VERTICALIZZAZIONE_REGULUS_PARAMETRO_VALORE = "REGULUS";
    public static final String DESCRIZIONE_ENTE = "Comune di Ravenna";
}
