package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.soggetti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipimovTipiSoggettoServiceImpl implements ITipimovTipiSoggettoService {

    private ITipimovTipiSoggettoDao dao;

    @Autowired
    public TipimovTipiSoggettoServiceImpl(ITipimovTipiSoggettoDao dao) {

	super();
	this.dao = dao;
    }

    @Override
    public void elimina(int id) {

	this.dao.elimina(id);
    }

    @Override
    public int aggiungi(String idTipoMovimento, int idTipoSoggetto) {

	return this.dao.aggiungi(idTipoMovimento, idTipoSoggetto);
    }
}
