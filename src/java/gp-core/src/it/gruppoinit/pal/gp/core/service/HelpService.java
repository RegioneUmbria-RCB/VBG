package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.HelpDAO;
import it.gruppoinit.pal.gp.core.domain.Help;
import it.gruppoinit.pal.gp.core.domain.HelpId;

public interface HelpService extends BaseService<Help, HelpId> {

    /**
     * 
     * Enumeration che contiene cosa può fare l'utente loggato
     * <ul>
     * <li>HELP_BASE: Può modificare sia l'HELP del proprio comune che quello di base</li>
     * <li>HELP: Può modificare solo l'HELP del proprio comune</li>
     * <li>VISUALIZZA: Può solo visualizzare l'help</li>
     * </ul>
     */
    public enum AccessoHelp {
	HELP_BASE, HELP, VISALIZZA
    }

    public boolean existHelp(Integer codiceResponsabile, String contentType);

    /**
     * <pre>
     * Il metodo permette di verificare se l'operatore loggato può modificare l'Help. 
     * 
     * @param codiceResponsabile
     * @param contentType
     * @return : il valore del campo updatehelp
     * </pre>
     */
    public boolean isAllowedChangeHelp(Integer codiceResponsabile);

    /**
     * @see HelpDAO#findByContentAndSoftwares(String contentType, String software)
     */
    public Help findByContentAndSoftwares(String contentType, String software);
}
