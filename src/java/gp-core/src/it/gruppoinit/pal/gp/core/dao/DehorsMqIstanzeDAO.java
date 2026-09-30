package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DehorsMqIstanzeHelper;

import java.util.List;

/**
 * 
 * @author
 */
public interface DehorsMqIstanzeDAO extends BaseDAO<DehorsMqIstanze, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<DehorsMqIstanze> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera le informazioni sui mq disponibili,liberi e occupati per l'area dehors passata
     */
    public DehorsMqIstanzeHelper findDehorsMqIstanzeHelper(Integer codiceArea);
}
