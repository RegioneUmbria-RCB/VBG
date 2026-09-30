package it.gruppoinit.pal.gp.core.features.commissioni.allegati;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdilizieAllegatiFirmeModel;

@SuppressWarnings("rawtypes")
public interface ICommissioniAllegatiDAO extends BaseDAO {

    public long countFirmePerAllegato(Integer idAllegato);

    public List<CommissioniEdilizieAllegatiFirmeModel> findFirmePerAllegato(Integer idAllegato);

    public void eliminaFirmePerAllegato(Integer idAllegato);
}
