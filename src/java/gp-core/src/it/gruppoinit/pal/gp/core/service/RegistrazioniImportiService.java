package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.RegistrazioniImportiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author francescop
 * 
 */
public interface RegistrazioniImportiService extends BaseService<RegistrazioniImporti, PkId> {

    public List<RegistrazioniImporti> findByRegistrazione(Registrazioni registrazioni);

    /**
     * @see RegistrazioniImportiDAO#findByRegistrazioniFilter(RegistrazioniFilter)
     * 
     * @param filter
     * @return
     */
    public List<RegistrazioniImporti> findByRegistrazioniFilter(RegistrazioniFilter filter);

    /**
     * Metodo per recuperare le scadenze (registrazioniImporti dove rimanenza > 0)
     * 
     * @see RegistrazioniImportiDAO#findByRegistrazioniFilter(RegistrazioniFilter)
     * 
     * @param filter
     * @return
     */
    public List<RegistrazioniImporti> findScadenzeByRegistrazioniFilter(RegistrazioniFilter filter);

    /**
     * Metodo che restituisce una lista di registrazioni importi raggruppati per conto. Come importo avrà la somma degli
     * importi.
     * 
     * @param registrazioni
     * @return
     */
    public List<RegistrazioniImporti> findByRegistrazioneGroupByConto(Registrazioni registrazioni);

    public int countPerAggiornamentoIVA(BigDecimal valoreIva, Date data, Software software);

    public List<Integer> findPerAggiornamentoIVA(BigDecimal valoreIva, Date data, Software software);
}
