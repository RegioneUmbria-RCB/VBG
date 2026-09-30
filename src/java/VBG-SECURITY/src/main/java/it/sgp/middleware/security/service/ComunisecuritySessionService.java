package it.sgp.middleware.security.service;

import java.util.Date;
import java.util.List;

import it.sgp.middleware.security.dao.ComunisecuritySessionDAO;
import it.sgp.middleware.security.domain.ComunisecuritySession;

/**
 * 
 * @author
 */
public interface ComunisecuritySessionService extends BaseService<ComunisecuritySession, String> {

    /**
     * @see ComunisecuritySessionDAO#findAll(Integer, Integer)
     */
    public List<ComunisecuritySession> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see ComunisecuritySessionDAO#deleteAll()
     */
    public void deleteAll();

    /**
     * @see ComunisecuritySessionDAO#countBeforeDate(Date date)
     *
     *      public int countBeforeDate(Date date);
     * 
     *      /**
     * @see ComunisecuritySessionDAO#findBeforeDate(Date date, Integer firstResult, Integer maxResult)
     *
     *      public List<ComunisecuritySession> findBeforeDate(Date date, Integer firstResult, Integer maxResult);
     * 
     *      /** Il metodo crea un file di testo dove verranno riportato il testo contenuto nello string buffer passato
     * 
     * @param date
     */
    public void writeComunisecuritySessionOnFile(StringBuffer testo);

    /**
     * Calcella i record di ComunisecuritySession con data ultimo accesso (lastrequest) minore a quella passata
     * 
     * @param date
     * @param saveTextFile
     */
    public void deleteBeforeDate(Date date, boolean saveTextFile);
    /**
     * Crea un oggetto DetachedCriteria a partire dalle variabili popolate dell' oggetto ComunisecuritySession
     * 
     * @param comunisecuritySession
     * @return
     */
    //public DetachedCriteria createDetachedCriteriaByEntity(ComunisecuritySession comunisecuritySession);
    /**
     * @see ComunisecuritySessionDAO#countRecord(DetachedCriteria criteria)
     */
    // public int countRecord(DetachedCriteria criteria);

    /**
     * @see ComunisecuritySessionDAO#findComunisecuritySessionServiceByCriteria(DetachedCriteria criteria, Integer
     *      firstResult, Integer maxResult)
     */
    // public List<ComunisecuritySession> findComunisecuritySessionServiceByCriteria(DetachedCriteria criteria, Integer firstResult, Integer maxResult);
    public void setTokenPartnerApp(String token, String tokenPartnerApp);

    public String getTokenPartnerApp(String token);

    public void setAuthLevel(String token, Integer authLevel);

    public Integer getAuthLevel(String token);
}
