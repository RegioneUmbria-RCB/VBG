package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.commissioni.appello.models.SoggettoPraticaModel;

public interface ICommissioniDettaglioService {

    void gestisciSoggettiIstanza(Integer idRiga, List<SoggettoPraticaModel> listSoggetti);
}
