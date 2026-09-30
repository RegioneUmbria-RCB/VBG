package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface MailConfigService extends BaseService<MailConfig, PkId> {

    /**
     * metodo per il recupero della configurazione per l'invio delle mail il metodo ricerca la configurazione per il
     * software corrente e se non la trova la ricerca per il software TT
     * 
     * @return MailConfig o null
     */
    public MailConfig findMailConfig();

    public List<MailConfig> findBySoftware(String codiceSoftware, Boolean abilitato);

    public List<MailConfig> findBySoftwareAndCodiceComune(String software, String codicecomune, Boolean abilitati);

    public MailConfig findBySoftwareAndCodiceComuneAttiviAndPrincipali(String codiceSoftware, String codicecomune, Boolean abilitati);

    public MailConfig findBySoftwareAttiviAndPrincipali(String codiceSoftware, Boolean abilitati);

    public List<MailConfig> findBySoftwareAndListCodiceComune(String codiceSoftware, String[] codicicomune, boolean abilitati);

    //    /**
    //     * <pre>
    //     * Ritorna una lista di MailConfigHelper con la seguente logica:
    //     * 	1. recupero per SOFTWARE and CODICECOMUNE (primo quello di default) - ABILITATI
    //     *  2. recupero per TT and CODICECOMUNE (primo quello di default) - ABILITATI
    //     *  3. recupero per SOFTWARE and NULL (primo quello di default) - ABILITATI
    //     *  4. recupero per TT and NULL (primo quello di default) - ABILITATI
    //     *  
    //     * @param codiceSoftware
    //     * @param codicecomune
    //     * @return
    //     * </pre>
    //     */
    //    public List<MailConfigHelper> findForInvioEmailBySoftwareAndCodiceComuneAbilitati(String codiceSoftware, String codicecomune);
    /**
     * <pre>
     * Ritorna una lista di MailConfig sommando i record trovato secondo la  logica (se cercaInTT==truu i punti 2 e 4 non verranno svolti):
     * 	1. recupero per SOFTWARE and CODICECOMUNE (primo quello di default) - ABILITATI
     *  2. recupero per TT and CODICECOMUNE (primo quello di default) - ABILITATI
     *  3. recupero per SOFTWARE and NULL (primo quello di default) - ABILITATI
     *  4. recupero per TT and NULL (primo quello di default) - ABILITATI
     *  
     * @param codiceSoftware
     * @param codicecomune
     * @return
     * </pre>
     */
    public List<MailConfig> findForInvioEmailBySoftwareAndCodiceComuneAbilitati(String codiceSoftware, String codicecomune, boolean cercaInTT);

    /**
     * <pre>
     * Ritorna una lista di MailConfig sommando i record trovato secondo la  logica:
     * 	1. recupero per SOFTWARE and lista CODICECOMUNE (primo quello di default) - ABILITATI
     *  2. recupero per TT and Lista CODICECOMUNE (primo quello di default) - ABILITATI
     *  3. recupero per SOFTWARE and NULL (primo quello di default) - ABILITATI
     *  4. recupero per TT and NULL (primo quello di default) - ABILITATI
     *  
     * @param codiceSoftware
     * @param codicecomune
     * @return
     * </pre>
     */
    public List<MailConfig> findForInvioEmailBySoftwareAndCodiceComuneAbilitati(String codiceSoftware, String[] codicecomune);

    /**
     * <pre>
     * Ritorna una un oggetto MailConfig secondo la logica se cercaInTT == false i punti 2 e 4 non verranno eseguiti 
     * 	1. recupero per SOFTWARE and CODICECOMUNE (primo quello di default) - ABILITATI - indirizzo email not blank -> trovato uno esco e ritorno l'oggetto trovato
     *  2. recupero per TT and CODICECOMUNE (primo quello di default) - ABILITATI - indirizzo email not blank-> trovato uno esco e ritorno l'oggetto trovato
     *  3. recupero per SOFTWARE and NULL (primo quello di default) - ABILITATI - indirizzo email not blank-> trovato uno esco e ritorno l'oggetto trovato
     *  4. recupero per TT and NULL (primo quello di default) - ABILITATI - indirizzo email not blank-> trovato uno esco e ritorno l'oggetto trovato
     *  
     * @param codiceSoftware
     * @param codicecomune
     * @return
     * </pre>
     */
    public MailConfig findPrepopolaInvioEmailBySoftwareAndCodiceComune(String codiceSoftware, String codicecomune, boolean cercaInTT);

    public void encryptPassword(MailConfig mc);

    public void decryptPassword(MailConfig mc);
}
