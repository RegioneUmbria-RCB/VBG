/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface TipimodalitapagamentoDAO extends BaseDAO<Tipimodalitapagamento, PkId> {

    public List<Tipimodalitapagamento> findByMpDescrestesa(String mpDescrestesa);
}
