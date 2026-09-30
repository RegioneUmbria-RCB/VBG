package it.sgp.middleware.security.service;

import java.util.List;

import it.sgp.middleware.security.domain.AmbienteEnum;
import it.sgp.middleware.security.domain.CheckTokenResult;
import it.sgp.middleware.security.domain.Comunisecurity;
import it.sgp.middleware.security.domain.ComunisecurityParam;
import it.sgp.middleware.security.domain.ContestoEnum;
import it.sgp.middleware.security.domain.DBConnectionInfo;
import it.sgp.middleware.security.exceptions.AmbienteNonTrovatoException;

public interface LoginService {

    /**
     * Verifica che il token passato sia ancora valido. <br />
     * Se il token risulta valido, non ci sono eccezioni, la chiamata torna anche il contesto per il quale il token è
     * stato generato, lasciando così alle applicazioni che verificano il token la possibilità di effettuare ulteriori
     * controlli.
     * 
     * @param token
     * @return una struttura contenente True / false a seconda che il token sia valido o meno ed il contesto per il
     *         quale il token è stato generato
     */
    public CheckTokenResult checkToken(String token);

    /**
     * metodo per invalidare il token
     * 
     * @param token
     * @return
     */
    public boolean logout(String token);

    /**
     * Torna le informazioni relative ai parametri dell'applicazione COMUNISECURITY_PARAM. <br />
     * Chi vuole ottenere la lista deve usare un token con contesto applicativo.
     * 
     * @return la lista dei parametri configurati
     */
    public List<ComunisecurityParam> getApplicationInfo();

    /**
     * Torna le informazione per la connessione all'ambiente passato. <br />
     * Chi vuole ottenere la lista deve usare un token con contesto applicativo.
     * 
     * @param token
     * @param ambiente
     * @return
     */
    public DBConnectionInfo getDBConnectionInfo(String alias, AmbienteEnum ambiente) throws AmbienteNonTrovatoException;

    /**
     * Torna la lista degli "enti" installati. <br />
     * Chi vuole ottenere la lista deve usare un token con contesto applicativo.
     * 
     * @param token
     * @return
     */
    public List<Comunisecurity> getSecurityList();

    /**
     * Richiede l'accesso di un operatore, di un utente (anagrafica), di un'amministrazione o di un'applicazione
     * 
     * @param alias
     *            l'identificativo dell'alias (tabella comunisecurity)
     * @param contesto
     *            il contesto per il quale validare le credenziali {@link ContestoEnum}
     * @param utente
     *            Identificativo della tabella ComunisecurityApp
     * @param password
     *            Password dell'utente
     * @param clientIp
     *            indirizzo Ip del chiamante
     * @return
     */
    public String login(String alias, ContestoEnum contesto, String utente, String password, String clientIp, boolean isSso);

    /**
     * 
     * @param alias
     * @param clientIp
     * @return
     */
    public String loginApp(String alias, String clientIp);
}
