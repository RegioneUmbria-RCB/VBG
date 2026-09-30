package it.gruppoinit.pal.gp.core.service.helper;

/**
 * @author fabrizioc
 *         <ul>
 *         <li>CONSENTITO è consentita la modifica/visualizzazione delle informazioni</li>
 *         <li>NON_CONSENTITO non è consentita la modifica/visualizzazione delle informazioni</li>
 *         <li>SOLA_LETTURA è consentita solamente la visualizzazione delle informazioni</li>
 *         <li>SOLA_LETTURA_TUTTI_MOVIMENTI accesso in sola lettura sulle istanze e abilitazione a creare, modificare e
 *         cancellare movimenti. (per le amministrazioni interne a cui ha accesso e per le amministrazioni non interne)</li>
 *         <li>SOLA_LETTURA_MOVIMENTI_AMM_INTERNA accesso in sola lettura sulle istanze e abilitazione a creare,
 *         modificare e cancellare movimenti per le sole amministrazioni interne a cui il ruolo è associato.</li>
 *         </ul>
 */
public enum TipoAccessoEnum {
    /**
     * È consentita la modifica/visualizzazione delle informazioni
     */
    CONSENTITO,
    /**
     * Non è consentita la modifica/visualizzazione delle informazioni
     */
    NON_CONSENTITO,
    /**
     * È consentita solamente la visualizzazione delle informazioni
     */
    SOLA_LETTURA,
    /**
     * accesso in sola lettura sulle istanze e abilitazione a creare, modificare e cancellare movimenti. (per le
     * amministrazioni interne a cui ha accesso e per le amministrazioni non interne)
     */
    SOLA_LETTURA_TUTTI_MOVIMENTI,
    /**
     * accesso in sola lettura sulle istanze e abilitazione a creare, modificare e cancellare movimenti per le sole
     * amministrazioni interne a cui il ruolo è associato.
     */
    SOLA_LETTURA_MOVIMENTI_AMM_INTERNA
}
