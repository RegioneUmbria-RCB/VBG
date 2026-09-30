package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeDDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;

import java.util.List;

public interface MercatipresenzeDService extends BaseService<MercatipresenzeD, PkId> {

    /**
     * @see MercatipresenzeDDAO#findByMercatiPresenzeTAndPosteggio(MercatipresenzeT, MercatiD)
     * @param giornoMercato
     * @param posteggio
     * @return
     */
    public MercatipresenzeD findByMercatiPresenzeTAndPosteggio(MercatipresenzeT giornoMercato, MercatiD posteggio);

    /**
     * @see MercatipresenzeDDAO#findListaPosteggi(MercatipresenzeT)
     * @param giorno
     * @return
     */
    //public List<MercatipresenzeD> findListaPosteggi(MercatipresenzeT giorno);
    public List<MercatipresenzeDDTO> findListaPosteggi(MercatipresenzeT giorno);

    /**
     * metodo per determinare i concessionari o occupanti del posteggio
     * 
     * @param posteggio
     * @return
     */
    public MercatipresenzeD findByMercatiPosteggio(MercatiD posteggio);

    /**
     * Torna la lista delle MercatipresenzeD di un occupante
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<MercatipresenzeD> findByAnagrafeOccupante(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle MercatipresenzeD di un concessionario
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<MercatipresenzeD> findByAnagrafeConcessionario(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * verifica se è la prima volta che accedo al dettaglio della giornata di mercato. se è la prima volta la lista di
     * mercatipresenzed collegata è vuota
     * 
     * @param mercatipresenzeT
     * @return
     */
    public boolean isFirstAccess(MercatipresenzeT mercatipresenzeT);

    public List<MercatipresenzeD> findListaPresentiSenzaPosteggio(MercatipresenzeT giorno);

    /**
     * Metodo per verificare se un giorno di mercato può essere chiuso. Il giorno può essere chiuso se c'è almeno un
     * posteggio assegnato
     * 
     * @return boolean
     */
    public boolean isCloseMarketDayAllowed(MercatipresenzeT mercatipresenzeT);

    public MercatipresenzeD findSpuntistaNoPosteggio(MercatipresenzeT giorno, Integer codiceAnagrafe);

    public List<MercatipresenzeD> findSpuntisti(MercatipresenzeT giorno);

    public List<MercatipresenzeD> findByMercatiPresenzeT(Integer codiceMercatopresenzaT);

    @Override
    public void update(MercatipresenzeD entity);

    @Override
    public void insert(MercatipresenzeD entity);

    @Override
    public void delete(MercatipresenzeD entity);

    /**
     * Per i mercati il cui tipo conteggio sia uguale a 2 (
     * {@link WebConstants#MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_SINGOLA}) elabora tutti i record di mercatipresenzed con
     * lo stesso codiceanagrafe e fk_idautorizzazione e imposta il campo numeropresenze solamente nell'ultima giornata
     * 
     * @param codiceMercato
     * @param anno
     * @return
     */
    public boolean updateConsolidaPresenzePerAnno(Integer codiceMercato, Integer anno);
}
