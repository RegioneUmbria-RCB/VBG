package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.upgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpgrCollegamentiIstanzeStessaIstanzaServiceImpl implements UpgrCollegamentiIstanzeStessaIstanzaService {

    @Autowired
    private UpgrIstanzeCollegateDAO upgrIstanzeCollegateDAO;

    @Override
    public void eseguiUpgr() {

	//1. Cancellazione di tutte i collegamenti in cui codiceistanza e codiceistanzacollegate coincidono
	this.upgrIstanzeCollegateDAO.deleteCollegamentiStessaIstanza();
	this.upgrIstanzeCollegateDAO.commitFlush();
    }
}
