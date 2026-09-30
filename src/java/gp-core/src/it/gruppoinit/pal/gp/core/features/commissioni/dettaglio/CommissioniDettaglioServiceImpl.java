package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.commissioni.appello.ICommissioniAppelloService;
import it.gruppoinit.pal.gp.core.features.commissioni.appello.models.SoggettoPraticaModel;

@Service
public class CommissioniDettaglioServiceImpl implements ICommissioniDettaglioService {

    private ICommissioniAppelloService appelloService;

    @Autowired
    public CommissioniDettaglioServiceImpl(ICommissioniAppelloService appelloService) {

	this.appelloService = appelloService;
    }

    @Override
    public void gestisciSoggettiIstanza(Integer idRiga, List<SoggettoPraticaModel> listSoggetti) {

	this.appelloService.convocaSoggettiIstanza(idRiga, listSoggetti);
    }
}
