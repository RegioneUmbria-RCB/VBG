package it.gruppoinit.pal.gp.core.features.commissioni.appello;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.commissioni.appello.models.SoggettoPraticaModel;

public interface ICommissioniAppelloService {

    void convocaSoggettiIstanza(Integer idRiga, List<SoggettoPraticaModel> listSoggetti);
}
