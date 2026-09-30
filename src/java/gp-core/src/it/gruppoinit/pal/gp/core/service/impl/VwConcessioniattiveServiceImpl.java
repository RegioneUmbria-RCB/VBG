package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwConcessioniattiveDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;
import it.gruppoinit.pal.gp.core.service.VwConcessioniattiveService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwConcessioniattiveServiceImpl extends BaseServiceImpl<VwConcessioniattive, PkId> implements VwConcessioniattiveService {

    private VwConcessioniattiveDAO vwConcessioniattiveDAO;

    @Autowired
    public void setVwConcessioniattiveDAO(VwConcessioniattiveDAO vwConcessioniattiveDAO) {

	this.vwConcessioniattiveDAO = vwConcessioniattiveDAO;
    }

    @Override
    public VwConcessioniattive findByMercatoUsoPosteggio(Integer codiceMercato, Integer codiceUso, Integer codicePosteggio) {

	return vwConcessioniattiveDAO.findByMercatoUsoPosteggio(codiceMercato, codiceUso, codicePosteggio);
    }

    @Override
    public void delete(VwConcessioniattive entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwConcessioniattive> findAll(Integer firstResult, Integer maxResult) {

	return vwConcessioniattiveDAO.findAll(firstResult, maxResult);
    }

    @Override
    public VwConcessioniattive findById(PkId id) {

	return vwConcessioniattiveDAO.findById(id);
    }

    @Override
    public Class<VwConcessioniattive> getEntityClass() {

	return VwConcessioniattive.class;
    }

    @Override
    public void insert(VwConcessioniattive entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwConcessioniattive entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwConcessioniattive> findByMercatoAndPosteggio(Integer codiceMercato, Integer codicePosteggio) {

	return vwConcessioniattiveDAO.findByMercatoAndPosteggio(codiceMercato, codicePosteggio);
    }

    @Override
    public List<VwConcessioniattive> findByMercatoMercatoUsoAndPosteggio(Integer codiceMercato, Integer codiceUso, Integer codicePosteggio) {

	return vwConcessioniattiveDAO.findByMercatoMercatoUsoAndPosteggio(codiceMercato, codiceUso, codicePosteggio);
    }

    @Override
    public List<Integer> findMercatoUsoConConcessioniAttiveByPosteggi(Integer codicemercato, String[] idPosteggi) {

	Set<Integer> r = new HashSet<Integer>();
	for (int i = 0; i < idPosteggi.length; i++) {
	    // ottimistare query che estre solo il codice // TODO // FIXME
	    List<VwConcessioniattive> conAttives = this.findByMercatoAndPosteggio(codicemercato, Integer.parseInt(idPosteggi[i]));
	    for (VwConcessioniattive vwConcessioniattive : conAttives) {
		r.add(vwConcessioniattive.getMercatiUso().getId().getCodice());
	    }
	}
	List<Integer> result = new ArrayList<Integer>();
	if (!r.isEmpty()) {
	    result.addAll(r);
	}
	return result;
    }
}
