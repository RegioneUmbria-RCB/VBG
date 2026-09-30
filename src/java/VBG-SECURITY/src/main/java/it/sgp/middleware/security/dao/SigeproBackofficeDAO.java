package it.sgp.middleware.security.dao;

import it.sgp.middleware.security.domain.InfoUtenteSigepro;

public interface SigeproBackofficeDAO {

    /**
     * 
     * @param alias
     * @param operatoreUserId
     * @return
     */
    public InfoUtenteSigepro verificaOperatore(String alias, String operatoreUserId);

    /**
     * 
     * @param alias
     * @param anagrafeUserId
     * @return
     */
    public InfoUtenteSigepro verificaAnagrafe(String alias, String anagrafeUserId);

    /**
     * 
     * @param alias
     * @param amministrazioneUserId
     * @return
     */
    public InfoUtenteSigepro verificaAmministrazione(String alias, String amministrazioneUserId);

    /**
     * 
     * @param alias
     * @param anagrafeUserId
     * @return
     */
    public InfoUtenteSigepro verificaAnagrafePg(String alias, String utente);
}
