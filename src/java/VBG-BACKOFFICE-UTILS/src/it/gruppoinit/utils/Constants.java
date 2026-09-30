package it.gruppoinit.utils;

import java.io.File;

/**
 * CLasse per le costanti utilizzate nel progetto
 * 
 * @author riccardob
 *
 */
public class Constants {

    // DATI STC SPORTELLI
    // public static final String ID_NODO_BACKOFFICE = "400";
    // public static final String ID_NODO_SIEDER = "1234";
    // public static final String ID_SPORTELLO_EDILIZIA = "CE";
    public static final String ID_SPORTELLO_SIEDER = "SIEDER";
    public static final String ID_ENTE_SIEDER = "SIEDER";
    // DOCUMENTI
    public static final String DOC_RIEPILOGO_DOMANDA = "RiepilogoDomanda";
    public static final String DOC_ALLEGATO_ALTRO = "Altro";
    public static final String DOC_MUDE_XML = "MudeXml";
    public static final String DOC_RIEPILOGO_DOMANDA_COLLEGATA = "RiepilogoDomandaCollegata";
    public static final String DOC_MUDE_XML_COLLEGATO = "MudeXmlCollegato";
    public static final String META_DATI_ALLEGATI = "SIEDER_CODICE_TIPO_DOCUMENTO";
    // NLA SERVICE
    public static final String ALTRODATO_OPERAZIONE_CAMBIO_STATO = "CAMBIO_STATO";
    public static final String ALTRODATO_OPERAZIONE_PROTOCOLLA_MOVIMENTO = "PROTOCOLLA_MOVIMENTO_SIEDER";
    public static final String ALTRODATO_BACKOFFICE_TIPOLOGIA_ISTANZA = "#TIPOLOGIA_ISTANZA#";
    // CARTELLA PER SCRIVERE FILE TEMPORANEI
    public static final String DOC_PATH = System.getProperty("java.io.tmpdir") + File.separator + "nla-sieder";
    // REGOLE
    public static final String REGOLA_SIEDER = "SIEDER";
    public static final String PARAMETRO_INSERISCI_SEMPRE_PRATICHE = "INSERISCI_SEMPRE_PRATICHE";
    public static final String PARAMETRO_URL_WS_GESTIONALE = "URL_WS_GESTIONALE";
    public static final String PARAMETRO_KEYSTORE_LOCATION = "KEYSTORE_LOCATION";
    public static final String PARAMETRO_KEYSTORE_PASSWORD = "KEYSTORE_PASSWORD";
    public static final String PARAMETRO_TRUSTSTORE_LOCATION = "TRUSTSTORE_LOCATION";
    public static final String PARAMETRO_TRUSTSTORE_PASSWORD = "TRUSTSTORE_PASSWORD";
    public static final String PARAMETRO_NOME_CERTIFICATO_AUTH = "NOME_CERTIFICATO_AUTH";
    public static final String PARAMETRO_URL_WS_DOWNLOAD_ISTANZA = "URL_WS_DOWNLOAD_ISTANZA";
    public static final String PARAMETRO_URL_WS_DOWNLOAD_ALLEGATI = "URL_WS_DOWNLOAD_ALLEGATI";
    public static final String PARAMETRO_RICHIESTA_GENERICA = "TIPOMOV_RICHIESTA_GENERICA";
    public static final String PARAMETRO_COMUNICAZIONE_GENERICA = "TIPOMOV_COMUNICAZIONE_GENERICA";
    // PARAMETRI WS
    public static final String PATH_ESTRAI_FILE_ISTANZA = "estraiFileIstanza";
    public static final String PATH_ESTRAI_ALLEGATO_ISTANZA = "estraiAllegatoIstanza";
    public static final String QUERY_NUMERO_MUDE_ISTANZA = "numeroMUDEIstanza";
    public static final String QUERY_NOME_FILE_ALLEGATO = "nomeFileAllegato";
    public static final String QUERY_SBUSTATO = "sbustato";
    public static final String ALTRODATO_BACKOFFICE_CERCA_PRATICA_COLLEGATA_PADRE = "#CERCA_PRATICA_COLLEGATA_PADRE#";
    // sezione altri dati
    public static String ALTRODATO_SIEDER_SETTORE = "SIEDER_SETTORE";
    public static String ALTRODATO_SIEDER_OPERAZIONI = "SIEDER_OPERAZIONI";
    // sezione ALTRI SOGGETTI
    public static String ALTRI_SOGGETTI_ASSUNTORE_LAVORI_IN_PROPRIO = "Assuntore In Proprio";
    public static String ALTRI_SOGGETTI_ASSUNTORE_LAVORI_ASSUNTORE = "Assuntore";
    public static String ALTRI_SOGGETTI_ASSUNTORE_LAVORI_IMPRESA = "Impresa";
    public static String ALTRI_SOGGETTI_COINTESTATARIO_IN_PROPRIO = "Cointestatario In Proprio";
    public static String ALTRI_SOGGETTI_COINTESTATARIO_RAPPRESENTATO = "Cointestatario Rappresentato";
    public static String ALTRI_SOGGETTI_COINTESTATARIO_RAPPRESENTANTE = "Cointestatario Rappresentante";
}
