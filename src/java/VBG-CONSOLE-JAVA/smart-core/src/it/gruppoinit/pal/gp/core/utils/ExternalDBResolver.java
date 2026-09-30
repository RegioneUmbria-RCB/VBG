package it.gruppoinit.pal.gp.core.utils;

import java.util.Properties;

/**
 * Classe per il settaggio delle properties necessarie a creare una nuova SessionFactory di Hibernate
 * 
 * @author fabrizioc
 * 
 */
public interface ExternalDBResolver {

    /**
     * Properties necessarie alla classe che implementa per inizializzarsi
     * 
     * @param config
     */
    public void setConfigurationProperties(Properties config);

    /**
     * Questo metodo recupera le properties di base e le integra con quelle necessarie per la connessione al database
     * corrispondente all'idcomune_alias passato
     * 
     * @param idcomune_alias
     * @return oggetto Properties popolato con i parametri per la creazione della SessionFactory
     */
    public Properties getConnectionProperties(String idcomune_alias);

    /**
     * Questo metodo recupera un token per l'idcomune alias passato
     * 
     * @param idcomune_alias
     * @return
     */
    public String getToken(String idcomune_alias);

    /**
     * Questo metodo serve per recuperare tutte le informazioni legate ad un token
     * 
     * @param token
     * @return Le informazioni recuperate o null se il token non è valido
     */
    public Properties checkToken(String token);

    /**
     * Metodo per verificare se un token è valido
     * 
     * @param token
     * @return
     */
    public boolean checkTokenValidity(String token);

    /**
     * Questo metodo recupera un token per l'utente autenticato
     * 
     * @param idcomune_alias
     * @param username
     * @param password
     * @param ipAddress
     * @return
     */
    public String getUserToken(String idcomune_alias, String username, String password, String ipAddress);

    public String getUserUTEToken(String idcomune_alias, String username, String ipAddress);

    /**
     * recupera un token utilizzando come alias quello presente in deploy.propertiea proprietà ws.token.default.alias
     * 
     */
    public String getTokenDefaultAlias();

    public void invalidateToken(String token);
}