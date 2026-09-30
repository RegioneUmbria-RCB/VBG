package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipicontromovimentoDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

import java.util.List;

public interface TipicontromovimentoService extends BaseService<Tipicontromovimento, PkId> {

    /**
     * 
     * @param amministrazioni
     * @return Una lista di tipi contro mivimenti filtrata per amministrazioni tipi movimento
     */
    public List<Tipicontromovimento> findTipicontromovimentiByAmministrazioniTipiMovimento(Amministrazioni amministrazioni);

    /**
     * 
     * @param amministrazioni
     * @return Una lista di tipi contro mivimenti filtrata per amministrazioni tipi contro movimento
     */
    public List<Tipicontromovimento> findTipicontromovimentiByAmministrazioniTipiContromovimento(Amministrazioni amministrazioni);

    /**
     * @see TipicontromovimentoDAO#findByTipoMovimentoAndTipoContromovimento(Tipimovimento tipomovimento, Tipimovimento
     *      tipocontromovimento)
     */
    public Tipicontromovimento findByTipoMovimentoAndTipoContromovimento(Tipimovimento tipomovimento, Tipimovimento tipocontromovimento);

    public List<Tipicontromovimento> findByTipimovimento(Tipimovimento tipomovimento);

    /**
     * Restituisce una lista di tipicontromovimenti contenente i tipimovimenti di cui è contromovimento il tipo
     * movimento passato.
     * 
     * @param tipomovimento
     * @return
     */
    public List<Tipicontromovimento> findByContromovimento(Tipimovimento controMovimento);

    /**
     * Cancella tutti i tempirisposta di un Tipicontromovimento
     * 
     * @param entity
     */
    public void deleteTempirisposta(Tipicontromovimento entity);

    /**
     * Trova tutti i record collegati a TIPICONTROMOVIMENTO.CODICEPROCEDURA
     * 
     * @param codice
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipicontromovimento> findByTipiprocedure(Integer codiceProcedura, Integer firstResult, Integer maxResult);

    /**
     * ordinati per tipimovimento.software.codice,tipimovimento.software.descrizione,tipiprocedure.procedura,
     * tipimovimento.movimento
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipicontromovimento> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult);

    /**
     * ordinati per tipimovimento.software.codice,tipimovimento.software.descrizione,tipiprocedure.procedura,
     * tipimovimento.movimento
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipicontromovimento> findByTipicontromovimento(String tipomovimento, Integer firstResult, Integer maxResult);

    /**
     * Modificando il contro movimento, dovremo anche rivalutare i tempi di risposta non più validi
     * 
     * @param entity
     */
    public void updateAndEliminaTempiRispostaNonValidi(Tipicontromovimento entity);

    /**
     * Aggiona il tipo contromovimento e gli eventuali tempi di risposta associati
     * 
     * @param entity
     */
    public void aggiornaTipocontromovimento(Tipicontromovimento entity);
}
