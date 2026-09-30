package it.gruppoinit.pal.gp.core.features.datidinamici.metadati;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Metadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.dao.Dyn2MetadatiDAO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class Dyn2MetadatiServiceImpl extends BaseServiceImpl<Dyn2Metadati, PkId> implements Dyn2MetadatiService {

    @Autowired
    private Dyn2MetadatiDAO dyn2MetadatiDAO;

    @Override
    public void insert(Dyn2Metadati entity) {

	if (validateEntity(entity)) {
	    dyn2MetadatiDAO.insert(entity);
	}
    }

    @Override
    public void update(Dyn2Metadati entity) {

	if (validateEntity(entity)) {
	    dyn2MetadatiDAO.update(entity);
	}
    }

    @Override
    public void delete(Dyn2Metadati entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2MetadatiDAO.delete(entity);
	}
    }

    @Override
    public List<Dyn2Metadati> findAll(Integer firstResult, Integer maxResult) {

	//throw new NotImplementedException();
	return dyn2MetadatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Dyn2Metadati findById(PkId id) {

	return dyn2MetadatiDAO.findById(id);
    }

    @Override
    protected Class<Dyn2Metadati> getEntityClass() {

	return Dyn2Metadati.class;
    }

    private List<Dyn2Metadati> findByDyn2MetadatiContestoInternal(String dyn2MetadatiContesto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("contesto", dyn2MetadatiContesto, "dyn2MetadatiContesti", String.class));
	ft.addRestriction(fr);
	return dyn2MetadatiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Dyn2MetadatiRestBean> findByDyn2MetadatiContesto(String dyn2MetadatiContesto) {

	List<Dyn2MetadatiRestBean> r = new ArrayList<Dyn2MetadatiRestBean>();
	List<Dyn2Metadati> mds = findByDyn2MetadatiContestoInternal(dyn2MetadatiContesto);
	for (Dyn2Metadati d2md : mds) {
	    r.add(new Dyn2MetadatiRestBean(d2md));
	}
	return r;
    }

    @Override
    public List<Dyn2Metadati> findByIdDyn2MetadatoContesto(Integer dy2MetadatoContesto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", dy2MetadatoContesto, "dyn2MetadatiContesti", Integer.class));
	ft.addRestriction(fr);
	return dyn2MetadatiDAO.findByFilterTable(ft);
    }
}
