package it.gruppoinit.pal.gp.core.features.attivita;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaListHelper;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi.LocalizzazioniAttivitaDTO;

/**
 * 
 * @author
 */
public interface IAttivitaDAO extends BaseDAO<IAttivita, PkId> {

    /**
     * Ritorna la lista paginata delle attività
     * 
     */
    public List<IAttivita> findAll(Integer firstResult, Integer maxResult);

    /**
     * L'ordine delle istanze legate all'attività è per le proprietà
     * <ul>
     * <li>coalesce(_istanza.datavalidita,?) desc</li>
     * <li>coalesce(_istanza.attivitaOrdine,?) asc</li>
     * <li>_istanza.id.codice desc</li>
     * </ul>
     * 
     * @param iattivita
     * @param visstorico
     * @return
     */
    public List<Istanze> findIstanzeOrdinate(IAttivita iattivita, boolean visstorico);

    public int countIstanzeWithDateValiditaNull(IAttivita iattivita, boolean visstorico);

    /**
     * 
     * @param codiceIstanzaDaEscludere
     * @return
     */
    public List<BigDecimal> findCatenaIstanzeDaCollegare(Integer codiceIstanzaDaEscludere);

    /**
     * 
     * @param filter
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<IAttivitaListHelper> findIAttivitaListHelperByFilter(IAttivitaFilter filter, Integer firstResult, Integer maxResults);

    /**
     * 
     * @param filter
     * 
     * @return
     */
    public int countIAttivitaListHelperByFilter(IAttivitaFilter filter);

    /***
     * Torna gli identificativi numerici delle istanze per l'identificativo attività passato come argomento
     * 
     * @param idiattivita
     * @return
     */
    public List<Integer> findCodiciIstanza(Integer idiattivita);

    public String exportModalitaPentaho(IAttivitaFilter attivitaFilter, Esportazioni esportazioni, Date data, String emailResponsabile,
	    String contesto, boolean isInviaMail);

    public Integer generaCodiceOsservatorio();

    public List<IAttivitaDaChiudereHelper> findAttivitaScadute(Date data, Integer firstResult, Integer maxResult);

    public int updateNonOperanteENonAttiva(IAttivitaDaChiudereHelper iAttivitaDaChiudereHelper);

    public List<Integer> findIdAttivitaWithoutSnapshot(Integer firstResult, Integer maxResult);

    public void updateAttiva(Integer codiceAttivita, Boolean attiva);

    public void updateDenominazione(Integer codiceAttivitaDaAggiornare, String denominazione);

    public List<LocalizzazioniAttivitaDTO> findLocalizzazioniByFilter(IAttivitaFilter filter);
}
