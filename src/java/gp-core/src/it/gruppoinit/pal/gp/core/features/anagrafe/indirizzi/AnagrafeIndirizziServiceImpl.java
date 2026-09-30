package it.gruppoinit.pal.gp.core.features.anagrafe.indirizzi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AnagrafeIndirizzi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class AnagrafeIndirizziServiceImpl extends BaseServiceImpl<AnagrafeIndirizzi, PkId> implements AnagrafeIndirizziService {

    private AnagrafeIndirizziDAO anagrafeIndirizziDAO;

    @Autowired
    public void setAnagrafeIndirizziDAO(AnagrafeIndirizziDAO anagrafeIndirizziDAO) {

	this.anagrafeIndirizziDAO = anagrafeIndirizziDAO;
    }

    @Override
    public void insert(AnagrafeIndirizzi entity) {

	this.anagrafeIndirizziDAO.insert(entity);
    }

    @Override
    public void update(AnagrafeIndirizzi entity) {

	this.anagrafeIndirizziDAO.update(entity);
    }

    @Override
    public void delete(AnagrafeIndirizzi entity) {

	this.anagrafeIndirizziDAO.delete(entity);
    }

    @Override
    public List<AnagrafeIndirizzi> findAll(Integer firstResult, Integer maxResult) {

	return this.anagrafeIndirizziDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AnagrafeIndirizzi findById(PkId id) {

	return this.anagrafeIndirizziDAO.findById(id);
    }

    @Override
    protected Class<AnagrafeIndirizzi> getEntityClass() {

	return AnagrafeIndirizzi.class;
    }
}
