package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface AnagrafestoricoDAO extends BaseDAO<Anagrafestorico, PkId> {

    /**
     * Restituisce la lista di anagrafiche storiche filtrate per idcumune e ordinate per ordinandole per il campo
     * nominativo
     * 
     */
    public List<Anagrafestorico> findAll(Integer firstResult, Integer maxResult);

    /**
     * Cerca il record anagrafe storico adeguato secondo la Logica: anagrafe storico che abbia dataInizioValidita
     * minore/uguale alla data passata e dataFineValidita maggiore/uguale alla data passata se non lo trova ripete la
     * ricerca con dataFineValidita=null se non lo trova restituisce null
     * 
     * @param entity
     * @param data
     */
    public Anagrafestorico findStoricoId(Anagrafe entity, Date data);

    /**
     * Cerca il record anagrafe storico adeguato secondo la Logica: a ricerca con dataFineValidita=null se non lo trova
     * rilancia eccezione perchè non è presente (anomalia nel DB)
     * 
     * @param entity
     */
    public Anagrafestorico findUltimoAnagrafestoricoByAnagrafe(Anagrafe entity);

    /**
     * Ritorna la lista dello storico dell'anagrafe passata
     * 
     * @param entity
     * @return
     */
    public List<Anagrafestorico> findStoricoByAnagrafe(Anagrafe entity);

    /**
     * <pre>
     * Il metodo ricalcola le date di validità dei record di anagrafe storico quando quando viene effettuata una
     * cancellazione di un record di Anagrafe storico secondo la logica:
     * 
     * La "Fine Validità" dell'anagrafica storica cancellata deve impostare la "Fine Validità" dell'anagrafica storica  precedente
     * </pre>
     * 
     * @param anagrafeStorico
     */
    public void ricalcoloStoricoAnagrafiche(Anagrafestorico anagrafeStorico);
}
