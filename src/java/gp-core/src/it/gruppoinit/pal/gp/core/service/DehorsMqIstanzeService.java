package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.DehorsMqIstanzeDAO;
import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DehorsMqIstanzeHelper;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniSubentriCommand;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author
 */
public interface DehorsMqIstanzeService extends BaseService<DehorsMqIstanze, PkId> {

    /**
     * @see DehorsMqIstanzeDAO#findAll(Integer, Integer)
     */
    public List<DehorsMqIstanze> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna i record di DehorsMqIstanze associati all'istanza passata. E' possibile recuperare i recod con
     * Autorizzazione NULL isAutorizzazioneNull=true. [tale opzione è stata aggiunta perchè la tabella potrebbe essere
     * popolata tramite delle formule delle schede dinamiche, questo permetterebbe di avre il form prepopolato in fase
     * di rilascio autorizzazione.]
     * 
     * @param codicesistanza
     * @param isAutorizzazioneNull
     * @return
     */
    public List<DehorsMqIstanze> findByIstanza(Integer codicesistanza, boolean isAutorizzazioneNull);

    public void prepopulateDehorsMqIstanze(DehorsMqIstanze dehorsMqIstanze, Integer codiceIstanza);

    public void prepopulateDehorsMqAutorizzazione(DehorsMqIstanze dehorsMqIstanze, Integer codiceAut);

    /**
     * @see DehorsMqIstanzeDAO#findDehorsMqIstanzeHelper(Integer codiceArea)
     */
    public DehorsMqIstanzeHelper findDehorsMqIstanzeHelper(Integer codiceArea);

    public DehorsMqIstanze findAttiveByAutorizzazione(Integer codiceAut);

    public List<DehorsMqIstanze> findCessateByAutorizzazione(Integer codiceAut);

    public void update(DehorsMqIstanze entiry, BigDecimal mqIniziali);

    public void insertPerSubentro(DehorsMqIstanze entity, DehorsMqIstanze dehorsMqIstanzePrecedente);

    /**
     * Metodo per la gestione dell'interfaccia di inserimento dei dati dehors in fase di subentro
     */
    public Map<String, Object> preViewInsertSubentriDehors(AutorizzazioniSubentriCommand autorizzazioniSubentriCommand);

    public void insert(DehorsMqIstanze entity, BigDecimal mqIniziali);
}
