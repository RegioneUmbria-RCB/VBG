package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdiliziePareriMovimentiModel;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface CommedilizieTipopareriDAO extends BaseDAO<CommedilizieTipopareri, PkId> {

    /**
     * torna la lista dei record ordinati per descrizione dalla A alla Z
     * 
     */
    public List<CommedilizieTipopareri> findAll(Integer firstResult, Integer maxResult);

    public List<CommissioniEdiliziePareriMovimentiModel> findMovimentiConfigurati(Integer codiceTipologiaParere);

    public boolean findConfigurazioniPerSoftware(Integer codiceTipologiaParere, String software);

    public void insertMovimentoPerSoftware(Integer codiceTipologiaParere, String software, String tipomovimento);

    public void eliminaMovimentoPerSoftware(Integer codiceTipologiaParere, String software);

    /**
     * Trova il movimento configurato per la tipologia di parere e il software dell'istanza del movimento
     * 
     * @param codiceTipoparere
     * @param codiceMovimento
     * @return
     */
    public String findTipomovPerTipologiaParereECommissioniEdilizieR(Integer codiceTipoparere, Integer codiceCommissioniEdilizieR);

    public void deleteMovimentiConfigurati(Integer codiceTipologiaParere);

    public List<CommedilizieTipopareri> findConfigurazioniPerTipomovimento(String tipoMovimento);
}
