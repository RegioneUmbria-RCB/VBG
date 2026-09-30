package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.IstanzeattivitaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;
import it.gruppoinit.pal.gp.core.domain.helper.SuperficiAttivitaHelper;

/**
 * 
 * @author Luca Proietti
 */
public interface IstanzeattivitaService extends BaseService<Istanzeattivita, PkId> {

    /**
     * @see IstanzeattivitaDAO#findAll(Integer, Integer)
     * @throws NotImplementedException
     */
    public List<Istanzeattivita> findAll(Integer firstResult, Integer maxResult);

    /**
     * Trova la lista di istanzeattività per una determinata istanza ordinandole per settore, istat e codiceistat asc.
     * Se orderByCodiceistat==True ordina per settore, codiceistat, istat asc
     * 
     * @param istanza
     * @param orderByCodiceistat
     * @return IllegalArgumentException se l'istanza nulla o non trovata
     */
    public List<Istanzeattivita> findByIstanza(Istanze istanza, Boolean orderByCodiceistat);

    /**
     * Trova la lista di istanzeattività per una determinata istanza e un settore
     * 
     * @param istanza
     * @return
     * @throws IllegalArgumentException
     *             se l'istanza nulla o non trovata
     */
    public List<Istanzeattivita> findByIstanzaAndSettore(Istanze istanza, Settori settore);

    /**
     * Trova gli avvisi pertinenti ai settori per l'istanza passata
     * 
     * @param istanza
     * @throws IllegalArgumentException
     *             se l'istanza nulla o non trovata
     * @return
     */
    public List<Settoriavvisi> findAvvisiIstanza(Istanze istanza);

    /**
     * Inserisce un Set di istanzeattivita
     * 
     * @param istanzeattivitas
     */
    public void insertIstanzeattivitaSet(Set<Istanzeattivita> istanzeattivitas);

    /**
     * Il metodo copia le attività legati all'istanza sorgente all'istanza destinatario. Il metodo prima di replicare le
     * attività collegate nell'istanza destinatario effettuerà un controllo sul destinatario in modo da non duplicare le
     * attività
     * 
     * @param istanzaSorgente
     * @param istanzaDestinatario
     */
    public void copiaIstanzeAttivita(Istanze istanzaSorgente, Istanze istanzaDestinatario);

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
