package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieVotazioniDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface CommedilizieVotazioniService extends BaseService<CommedilizieVotazioni, PkId> {

    /**
     * @see CommedilizieVotazioniDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieVotazioni> findAll(Integer firstResult, Integer maxResult);

    public List<CommedilizieVotazioni> findByFilterTable(FilterTable filterTable);

    @Override
    void insert(CommedilizieVotazioni entity);

    @Override
    void update(CommedilizieVotazioni entity);

    /**
     * <pre>
     * Il metodo ritorna una lista di record della tabella commissioni edilizie votazioni filtrate per la commissione edilizia 
     * r passata.
     * Logica: Il numero di record tornato deveessere uguale al numero di record presenti sulla tabella commissione edilizia appello per la 
     * commissione edilizia t in esame.
     * Se non si verifica questa condizione devo essere aggiunte alla lista i record mancanti anadando aprendere le informazioni necessarie 
     * presenti sulla commissione edilizia appello.
     * 
     * 
     * &#64;param commissioniedilizieR
     * &#64;return
     * </pre>
     */
    public List<CommedilizieVotazioni> findAppelloByCommissioniedilizieR(CommissioniedilizieR commissioniedilizieR);

    /**
     * <pre>
     * Il metodo può inserire o fare l'update di un oggetto comm. edilizie votazioni
     * Logica:
     *  1- Se l'id del record è diverso da null : update.
     *  2- Se l'id del record è uguale a null   : insert.
     * &#64;param commedilizieVotazioni
     * </pre>
     */
    public void insertOrUpdate(CommedilizieVotazioni commedilizieVotazioni);

    /**
     * Ritorna la lista dei presenti della commissione di ordine inferiore a quello passato. Se non troviamo niente
     * verrà riportata una lista vuota
     * 
     * @param commissioniedilizieR
     * @param ordine
     * @return
     */
    public List<CommedilizieVotazioni> findByCommissioneOrdinePrecedente(CommissioniedilizieR commissioniedilizieR, Integer codiceCommissioneT,
	    Integer ordine);

    /**
     * Ritorna la lista dei presenti della prima commissione di ordine inferiore a quella che andiamo a discutere che è
     * stata già discussa.Risaliamo fino a quella di ordine inferiore fino a quando non viene trovata. Se non troviamo
     * niente verrà riportata la lista dei presenti configurati nell'appello iniziale.
     * 
     * @param commissioniedilizieR
     * @param ordine
     * @return
     */
    public List<CommedilizieVotazioni> findByCommissioneOrdinePrecedenteDiscussa(CommissioniedilizieR commissioniedilizieR,
	    Integer codiceCommissioneT, Integer ordineDiPartenza);

    public void salvaVotazioni(List<CommedilizieVotazioni> commedilizieVotazionis);
}
