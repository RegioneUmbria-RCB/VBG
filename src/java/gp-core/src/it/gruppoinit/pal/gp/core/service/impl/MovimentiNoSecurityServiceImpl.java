package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;

import org.springframework.stereotype.Service;

@Service
public class MovimentiNoSecurityServiceImpl extends MovimentiBaseServiceImpl implements MovimentiNoSecurityService {

    @Override
    public void delete(Movimenti entity) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(Movimenti movimenti) {

	throw new NotImplementedException();
    }

    @Override
    public void operazioniAutomatiche(Movimenti entity) throws OperazioniAutomaticheException {

	throw new NotImplementedException();
    }

    @Override
    public void update(Movimenti entity) {

	throw new NotImplementedException();
    }

    @Override
    protected void childDelete(Movimenti entity) {

	throw new NotImplementedException();
    }

    @Override
    public void notificaStc(Movimenti entity) {

	throw new NotImplementedException();
    }

    @Override
    public void insertScadenza(Movimenti entity) {

	throw new NotImplementedException();
    }

    @Override
    public void updateScadenza(Movimenti entity) {

	throw new NotImplementedException();
    }

    @Override
    public void disabilitaMovimento(Movimenti entity) {

	throw new NotImplementedException();
    }

    @Override
    public void abilitaMovimento(Movimenti entity) {

	throw new NotImplementedException();
    }

    @Override
    public void updateAmministrazioniStc(Integer codiceMovimento, Integer codiceAmministrazioneStc) {

	throw new NotImplementedException();
    }

    @Override
    public void updateBooleanProperty(Integer codice, String propertyToUpdate, Boolean value) {

	throw new NotImplementedException();
    }

    @Override
    public void updateIntegerProperty(Integer codice, String propertyToUpdate, Integer value) {

	throw new NotImplementedException();
    }
}
