package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;
import it.gruppoinit.pal.gp.core.domain.helper.SuperficiAttivitaHelper;

/**
 * 
 * @author
 */
public interface IstanzeattivitaDAO extends BaseDAO<Istanzeattivita, PkId> {

    /**
     * Non implementato
     * 
     */
    public List<Istanzeattivita> findAll(Integer firstResult, Integer maxResult);

    public List<Settoriavvisi> findAvvisiIstanza(Istanze istanza);

    /**
     * Restituisce la somma delle superfici delle istanze attività di una certa istanza aggregate per attivita.istat e
     * settore. Se il secondo argomento è diverso da null viene utilizzato per restringere i risultati alle istenze
     * attività appartenenti a quel settore.
     * 
     * @return
     */
    public List<SuperficiAttivitaHelper> getSommaSuperficiAttivitaPerSettore(Istanze istanza, String perSettore);

    /**
     * Restituisce la somma delle superfici delle istanze attività di una certa istanza aggregate per attività. Se il
     * secondo argomento è diverso da null viene utilizzato per restringere i risultati alle istanze attività
     * appartenenti a quell'attività.
     * 
     * @return
     */
    public List<SuperficiAttivitaHelper> getSommaSuperficiAttivitaPerAttivita(Istanze istanza, String perAttivita);
}
