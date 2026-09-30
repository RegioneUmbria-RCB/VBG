package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieTipopareriDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdiliziePareriMovimentiModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.EsitoOperazioneDML;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface CommedilizieTipopareriService extends BaseService<CommedilizieTipopareri, PkId> {

    /**
     * @see CommedilizieTipopareriDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieTipopareri> findAll(Integer firstResult, Integer maxResult);

    /**
     * record che hanno COMMEDILIZIE_TIPOPARERI.TIPOMOVIMENTO = tipomovimento. I record sono ordinati per la proprietà
     * descrizione.
     * 
     * @param tipomovimento
     * 
     * @return
     */
    public List<CommedilizieTipopareri> findConfigurazioniPerTipomovimento(String tipomovimento);

    public List<CommissioniEdiliziePareriMovimentiModel> findMovimentiConfigurati(Integer codiceTipoparere);

    public EsitoOperazioneDML insertMovimentoPerSoftware(Integer codiceTipoparere, String software, String tipomovimento);

    public EsitoOperazioneDML eliminaMovimentoPerSoftware(Integer codiceTipoparere, String software);

    public String findTipomovPerTipologiaParereECommissioniEdilizieR(Integer codiceTipoparere, Integer codiceCommissioniEdilizieR);
}
