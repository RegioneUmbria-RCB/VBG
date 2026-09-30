package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BollCfgTipoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ImplementazioniEnum;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollCfgTipo;

/**
 * 
 * @author
 */
public interface BollCfgTipoService extends BaseService<BollCfgTipo, PkId> {

    /**
     * @see BollCfgTipoDAO#findAll(Integer, Integer)
     */
    public List<BollCfgTipo> findAll(Integer firstResult, Integer maxResult);

    public List<String> selectImplementazione();

    public List<CodiceDescrizioneBean> selectTitolaritaPagamenti();

    public List<String> selectPeriodo();

    /**
     * 
     * @see BollCfgTipoDAO#findByResponsabile(Integer)
     * @param codiceResponsabile
     * @return
     */
    public List<CreazioneBollCfgTipo> findByResponsabile(Integer codiceResponsabile);

    public ImplementazioniEnum findImplementazioneByTipo(Integer codiceTipo);

    public PeriodiEnum findPeriodiByTipo(Integer codiceTipo);
}
