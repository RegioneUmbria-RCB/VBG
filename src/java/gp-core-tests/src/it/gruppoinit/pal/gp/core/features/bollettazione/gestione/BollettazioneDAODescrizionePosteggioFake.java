package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;

public class BollettazioneDAODescrizionePosteggioFake extends BollettazioneDAOFakeAdapter implements BollettazioneDAO {

    @Override
    public <T> T getByIdForBollettazione(Class<T> cls, Integer id) {

	Conti c = new Conti();
	c.setDescrizione("Mercati merci varie");
	return (T) c;
    }

    @Override
    public String findDescrizionePosteggio(Integer idPosteggio) {

	return "Mercato merci varie DINEGRO Giovedì - 01";
    }
}
