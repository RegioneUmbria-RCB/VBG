package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.IBorsellinoMovimentiDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class BorsellinoMovimentiServiceImpl extends BaseServiceImpl<BorsellinoMovimenti, PkId> implements IBorsellinoMovimentiService {

    @Autowired
    private IBorsellinoMovimentiDAO borsellinoMovimentiDAO;
    @Autowired
    private IBorsellinoMovimentiImportiDAO borsellinoMovimentiImportiDAO;

    @Override
    public void insert(BorsellinoMovimenti entity) {

	if (validateEntity(entity)) {
	    borsellinoMovimentiDAO.insert(entity);
	}
    }

    @Override
    public void update(BorsellinoMovimenti entity) {

	if (validateEntity(entity)) {
	    borsellinoMovimentiDAO.update(entity);
	}
    }

    @Override
    public List<BorsellinoMovimenti> findByBorsellino(Integer idBorsellino) {

	return borsellinoMovimentiDAO.findByBorsellino(idBorsellino);
    }
    
    @Override
    public List<BorsellinoMovimenti> findByBorsellino(Integer idBorsellino, Date dalladata, Date alladata, Integer firstResult, Integer maxResult, List<TipoEnum> tipoenums) {

	return borsellinoMovimentiDAO.findByBorsellino(idBorsellino, dalladata, alladata, firstResult, maxResult, tipoenums);
    }

    @Override
    public BorsellinoMovimenti findById(PkId pkId) {

	return borsellinoMovimentiDAO.findById(pkId);
    }

    @Override
    public void delete(BorsellinoMovimenti mov) {

	if (isDeleteAllowed(mov)) {
	    borsellinoMovimentiImportiDAO.deleteByIdMovimento(mov.getId().getCodice());
	    borsellinoMovimentiDAO.delete(mov);
	}
    }

    @Override
    protected Class<BorsellinoMovimenti> getEntityClass() {

	return BorsellinoMovimenti.class;
    }

    @Override
    public List<BorsellinoMovimenti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
