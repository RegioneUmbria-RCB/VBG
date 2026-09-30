package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.upgr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MassiveParametri;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;

@Service
public class UpgrParametriComunicazioniManifestazioniServiceImpl implements IUpgrParametriComunicazioniManifestazioniService {

    @Autowired
    private IUpgrComunicazioniDAO iUpgrComunicazioniDAO;

    @Override
    public void aggiornaTipoComunicazioni202309() {

	String idComune = ORMHelper.getIdcomune();
	List<PkId> ids = iUpgrComunicazioniDAO.cercaLeComunicazioniSenzaTipoCom();
	for (PkId pkId : ids) {
	    ORMHelper.setIdcomune(pkId.getIdcomune());
	    MassiveParametri p = new MassiveParametri();
	    p.setChiave(ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE_PARAM);
	    p.setValore(ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE.CHIUSURA_GIORNATA.name());
	    p.setMassiveTestata((MassiveTestata) iUpgrComunicazioniDAO.getById(MassiveTestata.class, pkId.getCodice()));
	    iUpgrComunicazioniDAO.saveEntity(p);
	    iUpgrComunicazioniDAO.commitFlush();
	}
	ORMHelper.setIdcomune(idComune);
    }
}
