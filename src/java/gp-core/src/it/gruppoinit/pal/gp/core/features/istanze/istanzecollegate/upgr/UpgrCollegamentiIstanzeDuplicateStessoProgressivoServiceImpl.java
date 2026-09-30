package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.upgr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpgrCollegamentiIstanzeDuplicateStessoProgressivoServiceImpl implements UpgrCollegamentiIstanzeDuplicateStessoProgressivoService {

    @Autowired
    private UpgrIstanzeCollegateDAO upgrIstanzeCollegateDAO;

    @Override
    public void eseguiUpgr() {

	//1. Estrazione delle istanze duplicate a parità di idcomune, progressivo, codiceistanza, codiceistanzacollegata
	List<CollegamentiDuplicatiBean> doppioni = this.upgrIstanzeCollegateDAO.findCollegamentiDuplicati();
	for (CollegamentiDuplicatiBean duplicato : doppioni) {
	    //2. Eliminazione di quella con id più basso
	    this.upgrIstanzeCollegateDAO.deleteDoppioni(duplicato.getIdComune(), duplicato.getProgressivo(), duplicato.getCodiceIstanza(),
		    duplicato.getCodiceIstanzaCollegata(), duplicato.getId());
	    this.upgrIstanzeCollegateDAO.commitFlush();
	}
    }
}
