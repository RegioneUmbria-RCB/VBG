package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RegistrazioniImportiDAO extends BaseDAO<RegistrazioniImporti, PkId> {

    public List<RegistrazioniImporti> findByRegistrazione(Registrazioni registrazioni);

    /**
     * metodo che recupera la lista di tutti gli oggetti RegistraziniImporti in base ai filtri selezionati in filter.
     * <b>Il criterio recupera solo gli oggetti con proprietà nonPrevedeIncassi=false</b> La lista è ordinata in base
     * alla proprietà scadenza ASC
     * 
     * @param filter
     * @return
     */
    public List<RegistrazioniImporti> findByRegistrazioniFilter(RegistrazioniFilter filter);

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
