package it.gruppoinit.jms;

import java.io.Serializable;
import java.util.Date;

public interface AuditMessage extends Serializable {

    /**
     * Ritorna il nome/id dell'applicativo che sta inviando il messaggio
     * 
     * @return
     */
    public String getAppChiamante();

    /**
     * Setta il nome dell'applicativo che sta inviando il messaggio es.
     * <ul>
     * <li>BACKOFFICE</li>
     * <li>FRONTOFFICE</li>
     * <li>APP X</li>
     * </ul>
     * 
     * @param appChiamante
     */
    public void setAppChiamante(String appChiamante);

    /**
     * Ritorna l'utente che ha effettuato l'operazione
     */
    public String getUtente();

    /**
     * Setta l'utente che ha effettuato l'operazione
     * 
     * @param utente
     */
    public void setUtente(String utente);

    /**
     * Ritorna l'indirizzo IP dal quale la chiamata è stata eseguita
     * 
     * @return
     */
    public String getIndirizzoIp();

    /**
     * Setta l'indirizzo IP dal quale la chiamata è stata eseguita
     * 
     * @param indirizzoIP
     */
    public void setIndirizzoIp(String indirizzoIp);

    /**
     * Torna il tipo di messaggio che si invia
     * 
     * @return
     */
    public String getTipoMessaggio();

    /**
     * Setta il tipo di messaggio che si invia ad esempio:
     * <ul>
     * <li>WEB_LAYER</li>
     * <li>SERVICE_LAYER</li>
     * <li>EXCEPTION</li>
     * <li>.....</li>
     * </ul>
     * 
     * @param tipoMessaggio
     */
    public void setTipoMessaggio(String tipoMessaggio);

    /**
     * torna l'azione del messaggio
     * 
     * @return
     */
    public String getAzione();

    /**
     * setta l'azione del messaggio
     * 
     * @param azione
     */
    public void setAzione(String azione);

    /**
     * Torna il contenuto del messaggio
     * 
     * @return
     */
    public byte[] getMessaggio();

    /**
     * Setta il contenuto del messaggio
     * 
     * @param messaggio
     */
    public void setMessaggio(byte[] messaggio);

    /**
     * Torna la data/ora nella quale il messaggio è stato inviato
     * 
     * @return
     */
    public Date getDataOra();

    /**
     * Setta la data/ora nella quale il messaggio è stato inviato
     * 
     * @param dataOra
     */
    public void setDataOra(Date dataOra);

    /**
     * Torna l'id impostato dall'applicativo chiamante
     * 
     * @return
     */
    public String getDomainId();

    /**
     * Setta un'id impostato dall'applicativo chiamante
     * 
     * @param domainId
     */
    public void setDomainId(String domainId);
}