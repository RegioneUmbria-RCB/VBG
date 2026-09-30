/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface TipimodalitapagamentoService extends BaseService<Tipimodalitapagamento, PkId> {

    /**
     * esegue una query filtrando per idcomune=ORMHelper.idcomune and lower(MP_DESCRESTESA) like lower(descrizione);
     * 
     * @param descrizione
     * @return
     */
    public List<Tipimodalitapagamento> findByDescrizioneEsatta(String descrizione);

    public List<Tipimodalitapagamento> findByMpDescrestesa(String mpDescrestesa, boolean mostraDisabilitati);

    public List<Tipimodalitapagamento> findAll(Integer firstResult, Integer maxResult, boolean mostraDisabilitati);
}
