package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TempirispostaDAO;
import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.TempirispostaId;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.helper.TempirispostaHelper;
import it.gruppoinit.pal.gp.core.domain.web.TempirispostaCommand;
import it.gruppoinit.pal.gp.core.service.helper.TempirispostaHelperBean;

import java.util.List;

/**
 * 
 * @author
 */
public interface TempirispostaService extends BaseService<Tempirisposta, TempirispostaId> {

    /**
     * @see TempirispostaDAO#findAll(Integer, Integer)
     */
    public List<Tempirisposta> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see TempirispostaDAO#findByFilter(Tempirisposta tempirisposta)
     */
    public List<Tempirisposta> findByFilter(Tempirisposta tempirisposta);

    /**
     * Il metodo ritorna una lista di Tempi di risposta helper, il metdodo controlla anche che se esisto nel DB dei
     * record dei tempi di risposta associate alle amministrazioni che intervengono nel processo di una procedura e le
     * setta in modo da poter visualizzarel
     * 
     * @param tipicontromovimento
     * @return
     */
    public List<TempirispostaHelper> findByTempirispostaHelperByTipoControMov(Tipicontromovimento tipicontromovimento);

    public void insertAndUpdateTempirisposta(TempirispostaHelperBean tempirispostaHelperBean);

    public void insertAndUpdateTempirisposta(TempirispostaCommand tempirispostaCommand);

    /**
     * Torna la lista dei Tempirisposta di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tempirisposta> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista dei Tempirisposta filtrati per idcomune, tipimovimento e tipicontromovimento
     * 
     * @param codiceTipimovimento
     * @param codiceTipicontromovimento
     * @return
     */
    public List<Tempirisposta> findByTipimovimentoAndTipicontromovimento(String codiceTipimovimento, String codiceTipicontromovimento);
}
