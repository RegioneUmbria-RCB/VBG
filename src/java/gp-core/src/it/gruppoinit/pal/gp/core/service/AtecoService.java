package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AtecoDAO;
import it.gruppoinit.pal.gp.core.domain.Ateco;
import it.gruppoinit.pal.gp.core.domain.web.AtecoCommand;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface AtecoService extends BaseService<Ateco, Integer> {

    /**
     * @see AtecoDAO#findAll(Integer, Integer)
     */
    public List<Ateco> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista di dati della tabella ateco organizzata a gerarchia padre/figli
     * 
     * @param inspectProperties
     *            se impostata a true allora ricerca le informazioni quali procedura, responsabile procedimento,
     *            movimento avvio, ecc...
     * 
     * @return
     */
    public List<AtecoCommand> findAtecoHierarchy(boolean inspectProperties);
}
