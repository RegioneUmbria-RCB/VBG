package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoConsensiInformativiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ConsensiInformativi;
import it.gruppoinit.pal.gp.core.domain.FoConsensiInformativi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConsensoInformativoRestBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ConsensiInformativiService;
import it.gruppoinit.pal.gp.core.service.FoConsensiInformativiService;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoConsensiInformativiServiceImpl extends BaseServiceImpl<FoConsensiInformativi, PkId> implements FoConsensiInformativiService {

    @Autowired
    private FoConsensiInformativiDAO foConsensiInformativiDAO;
    @Autowired
    private ConsensiInformativiService consensiInformativiService;

    @Override
    public void insert(FoConsensiInformativi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    foConsensiInformativiDAO.insert(entity);
	}
    }

    @Override
    public void update(FoConsensiInformativi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    foConsensiInformativiDAO.update(entity);
	}
    }

    private void dataIntegration(FoConsensiInformativi entity) {

	if (entity.getFlagConsenso() == null) {
	    entity.setFlagConsenso(Boolean.FALSE);
	}
    }

    @Override
    public void delete(FoConsensiInformativi entity) {

	if (isDeleteAllowed(entity)) {
	    foConsensiInformativiDAO.delete(entity);
	}
    }

    @Override
    public List<FoConsensiInformativi> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public FoConsensiInformativi findById(PkId id) {

	return foConsensiInformativiDAO.findById(id);
    }

    @Override
    public List<FoConsensiInformativi> findByIdentificativoUtente(String identificativoUtente) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("identificativoUtente", identificativoUtente));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataAvvenutoConsenso"));
	ft.addOrder(FilterUtils.orderAsc("contesto"));
	return foConsensiInformativiDAO.findByFilterTable(ft);
    }

    @Override
    protected Class<FoConsensiInformativi> getEntityClass() {

	return FoConsensiInformativi.class;
    }

    @Override
    public boolean isConsensoPresente(String identificativoUtente, Integer identificativoConsenso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("identificativoUtente", identificativoUtente));
	fr.addFilterField(FilterUtils.equals("consensiInformativiId", identificativoConsenso, Integer.class));
	ft.addRestriction(fr);
	return foConsensiInformativiDAO.existsRecords(ft);
    }

    @Override
    public void inserisciConsenso(String userid, Integer identificativoConsenso) {

	if (this.isConsensoPresente(userid, identificativoConsenso)) {
	    FoConsensiInformativi entity = new FoConsensiInformativi();
	    entity.setFlagConsenso(Boolean.TRUE);
	    entity.setDataAvvenutoConsenso(Calendar.getInstance().getTime());
	    this.update(entity);
	} else {
	    FoConsensiInformativi entity = new FoConsensiInformativi();
	    ConsensiInformativi c = consensiInformativiService.findById(new PkId(identificativoConsenso));
	    entity.setConsensiInformativi(c);
	    entity.setIdentificativoUtente(userid);
	    entity.setFlagConsenso(Boolean.TRUE);
	    entity.setDataAvvenutoConsenso(Calendar.getInstance().getTime());
	    this.insert(entity);
	}
    }

    @Override
    public ConsensoInformativoRestBean leggiConsenso(String userid, Integer identificativoConsenso) {

	ConsensoInformativoRestBean result = new ConsensoInformativoRestBean();
	result.setDataConsenso(null);
	result.setFlagConsenso(false);
	List<FoConsensiInformativi> conss = this.findByIdentificativoUtenteAndIdContesto(userid, identificativoConsenso);
	if (conss.isEmpty()) {
	    return result;
	}
	FoConsensiInformativi foci = conss.get(0);
	if (foci.getFlagConsenso() != null && foci.getFlagConsenso().booleanValue()) {
	    result.setFlagConsenso(true);
	}
	result.setDataConsenso(foci.getDataAvvenutoConsenso());
	result.setId(foci.getId().getCodice());
	return result;
    }

    private List<FoConsensiInformativi> findByIdentificativoUtenteAndIdContesto(String userid, Integer identificativoConsenso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("identificativoUtente", userid));
	fr.addFilterField(FilterUtils.equals("consensiInformativiId", identificativoConsenso, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataAvvenutoConsenso"));
	return foConsensiInformativiDAO.findByFilterTable(ft);
    }
}
