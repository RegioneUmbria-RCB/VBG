package it.gruppoinit.pal.gp.core.features.movimenti.metadati;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadati;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadatiId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class MovimentiMetadatiServiceImpl extends BaseServiceImpl<MovimentiMetadati, MovimentiMetadatiId> implements IMovimentiMetadatiService {

    @Autowired
    private IMovimentiMetadatiDAO iMovimentiMetadatiDAO;

    @Override
    public void insert(MovimentiMetadati entity) {

	if (validateEntity(entity)) {
	    iMovimentiMetadatiDAO.insert(entity);
	}
    }

    @Override
    public void update(MovimentiMetadati entity) {

	if (validateEntity(entity)) {
	    iMovimentiMetadatiDAO.update(entity);
	}
    }

    @Override
    public void delete(MovimentiMetadati entity) {

	if (isDeleteAllowed(entity)) {
	    iMovimentiMetadatiDAO.delete(entity);
	}
    }

    @Override
    public List<MovimentiMetadati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public MovimentiMetadati findById(MovimentiMetadatiId id) {

	return iMovimentiMetadatiDAO.findById(id);
    }

    @Override
    protected Class<MovimentiMetadati> getEntityClass() {

	return MovimentiMetadati.class;
    }

    @Override
    public Movimenti findMovimentoByUuId(String uuidMovimento) {

	return iMovimentiMetadatiDAO.findMovimentoByUuId(uuidMovimento);
    }

    @Override
    public boolean isMetadatoPresente(Integer codice, String nomeMetadato) {

	return iMovimentiMetadatiDAO.isMetadatoPresente(codice, nomeMetadato);
    }
}
