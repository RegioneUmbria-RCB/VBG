package it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiR;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti.dao.IAlberoCoefficientiRDAO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class AlberoCoefficientiRServiceImpl extends BaseServiceImpl<AlberoCoefficientiR, PkId> implements IAlberoCoefficientiRService {

    private IAlberoCoefficientiRDAO alberoCoefficientiRDAO;

    @Autowired
    public void setAlberoCoefficientiTDAO(IAlberoCoefficientiRDAO alberoCoefficientiRDAO) {

	this.alberoCoefficientiRDAO = alberoCoefficientiRDAO;
    }

    @Override
    public void insert(AlberoCoefficientiR entity) {

	if (validateEntity(entity)) {
	    this.alberoCoefficientiRDAO.insert(entity);
	}
    }

    @Override
    public void update(AlberoCoefficientiR entity) {

	if (validateEntity(entity)) {
	    this.alberoCoefficientiRDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoCoefficientiR entity) {

	if (validateEntity(entity)) {
	    this.alberoCoefficientiRDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoCoefficientiR> findAll(Integer firstResult, Integer maxResult) {

	return this.alberoCoefficientiRDAO.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "codiceCoefficente", DAOOrderTypeEnum.ASC);
    }

    @Override
    public AlberoCoefficientiR findById(PkId id) {

	return this.alberoCoefficientiRDAO.findById(id);
    }

    @Override
    protected Class<AlberoCoefficientiR> getEntityClass() {

	return AlberoCoefficientiR.class;
    }

    @Override
    public List<AlberoCoefficientiR> findAllRigheByTestata(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("alberoCoefficientiTId", codice, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("codiceCoefficente"));
	return this.alberoCoefficientiRDAO.findByFilterTable(ft);
    }
}
