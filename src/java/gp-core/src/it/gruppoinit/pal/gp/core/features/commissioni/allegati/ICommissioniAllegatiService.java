package it.gruppoinit.pal.gp.core.features.commissioni.allegati;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdilizieAllegatiFirmeModel;

public interface ICommissioniAllegatiService {

    public long countFirmePerAllegato(Integer idAllegato);

    public List<CommissioniEdilizieAllegatiFirmeModel> findFirmePerAllegato(Integer idAllegato);

    public void eliminaFirmePerAllegato(Integer idAllegato);
}
