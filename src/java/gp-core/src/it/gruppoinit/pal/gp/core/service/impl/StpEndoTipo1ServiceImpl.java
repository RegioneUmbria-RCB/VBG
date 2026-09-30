package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StpEndoTipo1DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StpEndoTipo1ServiceImpl extends BaseServiceImpl<StpEndoTipo1, PkId> implements StpEndoTipo1Service {

    private StpEndoTipo1DAO stpEndoTipo1DAO;
    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setStpEndoTipo1DAO(StpEndoTipo1DAO stpEndoTipo1DAO) {

	this.stpEndoTipo1DAO = stpEndoTipo1DAO;
    }

    @Override
    protected Class<StpEndoTipo1> getEntityClass() {

	return StpEndoTipo1.class;
    }

    @Override
    public void delete(StpEndoTipo1 entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	stpEndoTipo1DAO.delete(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public List<StpEndoTipo1> findAll(Integer firstResult, Integer maxResult) {

	return stpEndoTipo1DAO.findAll(firstResult, maxResult);
    }

    @Override
    public StpEndoTipo1 findById(PkId id) {

	return stpEndoTipo1DAO.findById(id);
    }

    @Override
    public void insert(StpEndoTipo1 entity) {

	if (validateEntity(entity)) {
	    stpEndoTipo1DAO.insert(entity);
	}
    }

    @Override
    public void update(StpEndoTipo1 entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    stpEndoTipo1DAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public StpEndoTipo1 findByInventarioProcedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	return stpEndoTipo1DAO.findByInventarioProcedimenti(inventarioprocedimenti);
    }

    @Override
    public StpEndoTipo1 findbyStpCodice(Integer stpCodice) {

	return stpEndoTipo1DAO.findbyStpCodice(stpCodice);
    }

    @Override
    public List<StpEndoTipo1> findBySoftware(String software) {

	return stpEndoTipo1DAO.findbySoftware(software);
    }

    @Override
    public List<StpEndoTipo1> verificaSchedeEndo1() {

	return stpEndoTipo1DAO.verificaSchedeEndo1();
    }

    @Override
    public StpEndoTipo1 findByCodiceEndoRegionale(String codReg) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(new FilterField<String>("codiceEndoRegionale", new String[] { codReg }, StpEndoTipo1.class));
	ft.addRestriction(fr);
	List<StpEndoTipo1> results = stpEndoTipo1DAO.findByFilterTable(ft);
	if (results.isEmpty()) {
	    return null;
	}
	return results.get(0);
    }

    @Override
    public StpEndoTipo1 isProcedimentoCART(Integer codiceProcedimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceProcedimento, "inventarioprocedimenti", Integer.class));
	fr.addFilterField(FilterUtils.isNotNull("id.codice", "oggetti"));
	ft.addRestriction(fr);
	List<StpEndoTipo1> stpEndoTipo1 = stpEndoTipo1DAO.findByFilterTable(ft);
	if (!stpEndoTipo1.isEmpty()) {
	    return stpEndoTipo1.get(0);
	}
	return null;
    }

    @Override
    public List<StpEndoTipo1> findListByInventarioProcedimenti(Integer codiceendo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceendo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<StpEndoTipo1> stpEndoTipo1 = stpEndoTipo1DAO.findByFilterTable(ft);
	return stpEndoTipo1;
    }

    @Override
    public List<StpEndoTipo1> findByCodiceRegionale(String codiceEndoRegionale) {

	if (StringUtils.isNotBlank(codiceEndoRegionale)) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("codiceEndoRegionale", codiceEndoRegionale, String.class));
	    ft.addRestriction(fr);
	    ft.addOrder(FilterUtils.orderDesc("id.codice"));
	    List<StpEndoTipo1> stpEndoTipo1 = stpEndoTipo1DAO.findByFilterTable(ft);
	    return stpEndoTipo1;
	} else {
	    return new ArrayList<StpEndoTipo1>();
	}
    }

    @Override
    public int count() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	return stpEndoTipo1DAO.countRecord(ft);
    }

    @Override
    public List<StpEndoTipo1> findAllByStpCodice(Integer stpCodice) {

	return this.stpEndoTipo1DAO.findAllByStpCodice(stpCodice);
    }
}
