package it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiR;
import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti.dao.IAlberoCoefficientiTDAO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocRuoliService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class AlberoCoefficientiTServiceImpl extends BaseServiceImpl<AlberoCoefficientiT, PkId> implements IAlberoCoefficientiTService {

    private IAlberoCoefficientiTDAO alberoCoefficientiTDAO;
    private AlberoprocRuoliService alberoprocRuoliService;
    @Autowired
    private IAlberoCoefficientiRService alberoCoefficientiRService;

    @Autowired
    public void setAlberoprocRuoliService(AlberoprocRuoliService alberoprocRuoliService) {

	this.alberoprocRuoliService = alberoprocRuoliService;
    }

    @Autowired
    public void setAlberoCoefficientiTDAO(IAlberoCoefficientiTDAO alberoCoefficientiTDAO) {

	this.alberoCoefficientiTDAO = alberoCoefficientiTDAO;
    }

    @Override
    public void insert(AlberoCoefficientiT entity) {

	if (validateEntity(entity)) {
	    this.alberoCoefficientiTDAO.insert(entity);
	}
    }

    @Override
    public void update(AlberoCoefficientiT entity) {

	if (validateEntity(entity)) {
	    this.alberoCoefficientiTDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoCoefficientiT entity) {

	if (validateEntity(entity)) {
	    childDelete(entity);
	    this.alberoCoefficientiTDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(AlberoCoefficientiT entity) {

	List<AlberoCoefficientiR> findAllRigheByTestata = alberoCoefficientiRService.findAllRigheByTestata(entity.getId().getCodice());
	for (AlberoCoefficientiR alberoCoefficientiR : findAllRigheByTestata) {
	    alberoCoefficientiRService.delete(alberoCoefficientiR);
	}
    }

    @Override
    public List<AlberoCoefficientiT> findAll(Integer firstResult, Integer maxResult) {

	return this.alberoCoefficientiTDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.DESC);
    }

    @Override
    public AlberoCoefficientiT findById(PkId id) {

	return this.alberoCoefficientiTDAO.findById(id);
    }

    @Override
    protected Class<AlberoCoefficientiT> getEntityClass() {

	return AlberoCoefficientiT.class;
    }

    @Override
    public List<AlberoCoefficientiT> findAllByResponsabile(Integer codiceResponsabile) {

	Set<Integer> vociAlbero = alberoprocRuoliService.trovaVociPerRuoliDelResponsabile(codiceResponsabile);
	if (vociAlbero.isEmpty()) {
	    return new ArrayList<AlberoCoefficientiT>();
	}
	Integer[] scId = new Integer[vociAlbero.size()];
	int i = 0;
	for (Integer id : vociAlbero) {
	    scId[i] = id;
	    i++;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), "alberoProc", String.class));
	fr.addFilterField(FilterUtils.in("id.codice", scId, "alberoProc", Integer.class));
	ft.addRestriction(fr);
	return alberoCoefficientiTDAO.findByFilterTable(ft);
    }

    @Override
    public boolean responsabileHaPermessiSuConfigurazione(Integer codiceResponsabile, Integer codiceTestata) {

	Set<Integer> vociAlbero = alberoprocRuoliService.trovaVociPerRuoliDelResponsabile(codiceResponsabile);
	if (vociAlbero.isEmpty()) {
	    return false;
	}
	AlberoCoefficientiT testata = this.findById(new PkId(codiceTestata));
	Integer scId = testata.getAlberoProc().getId().getCodice();
	return vociAlbero.contains(scId);
    }
}
