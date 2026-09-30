package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StpEndoTipo2DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StpEndoTipo2ServiceImpl extends BaseServiceImpl<StpEndoTipo2, PkId> implements StpEndoTipo2Service {

    private InventarioprocedimentiService inventarioprocedimentiService;
    private AlberoprocService alberoprocService;
    private OggettiService oggettiService;
    private StpEndoTipo2DAO stpEndoTipo2DAO;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setStpEndoTipo2DAO(StpEndoTipo2DAO stpEndoTipo2DAO) {

	this.stpEndoTipo2DAO = stpEndoTipo2DAO;
    }

    @Override
    protected Class<StpEndoTipo2> getEntityClass() {

	return StpEndoTipo2.class;
    }

    @Override
    public void delete(StpEndoTipo2 entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	stpEndoTipo2DAO.delete(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public List<StpEndoTipo2> findAll(Integer firstResult, Integer maxResult) {

	return stpEndoTipo2DAO.findAll(firstResult, maxResult);
    }

    @Override
    public StpEndoTipo2 findById(PkId id) {

	return stpEndoTipo2DAO.findById(id);
    }

    @Override
    public void insert(StpEndoTipo2 entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    stpEndoTipo2DAO.insert(entity);
	}
    }

    @Override
    public void update(StpEndoTipo2 entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    stpEndoTipo2DAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    private void dataIntegration(StpEndoTipo2 entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Attenzione! Il parametro StpEndoTipo2 è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(StpEndoTipo2 entity) {

	Inventarioprocedimenti endoprocedimento = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(), PkId.class,
		"id.codice");
	entity.setInventarioprocedimenti(endoprocedimento);
	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
	Oggetti oggetti = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetti);
    }

    @Override
    public StpEndoTipo2 findbyAlberoproc(Integer codiceAlberoproc) {

	return stpEndoTipo2DAO.findbyAlberoproc(codiceAlberoproc);
    }

    @Override
    public StpEndoTipo2 findbyStpCodice(Integer stpCodice, String tipo) {

	return stpEndoTipo2DAO.findbyStpCodice(stpCodice, tipo);
    }

    @Override
    public StpEndoTipo2 findbyStpCodice(Integer stpCodice) {

	return stpEndoTipo2DAO.findbyStpCodice(stpCodice);
    }

    @Override
    public List<StpEndoTipo2> findBySoftwareAndTipo(String software, String tipo) {

	return stpEndoTipo2DAO.findBySoftwareAndTipo(software, tipo);
    }

    @Override
    public List<Integer> findCodiciBySoftwareAndTipo(String software, String tipo) {

	return stpEndoTipo2DAO.findCodiciBySoftwareAndTipo(software, tipo);
    }

    @Override
    public List<StpEndoTipo2> verificaSchedeEndo2() {

	return stpEndoTipo2DAO.verificaSchedeEndo2();
    }

    @Override
    public StpEndoTipo2 findByInventarioproc(Integer codiceInventario) {

	if (codiceInventario == null) {
	    return null;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<StpEndoTipo2> stpEndoList = stpEndoTipo2DAO.findByFilterTable(ft);
	if (stpEndoList.size() > 0) {
	    return stpEndoList.get(0);
	}
	return null;
    }

    @Override
    public List<StpEndoTipo2> findbyTipoAndCodiceTipologiaEndo(Integer codiceTipologiaEndo, String tipo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipo", tipo, String.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceTipologiaEndo, "stpTipologieEndo2", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<StpEndoTipo2> stpEndoList = stpEndoTipo2DAO.findByFilterTable(ft);
	return stpEndoList;
    }

    @Override
    public StpEndoTipo2 findbyTipoAndCodiceTipologiaEndoAndCodiceRegionale(Integer codiceTipologiaEndo, String tipo, String codiceEndoRegionale) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipo", tipo, String.class));
	if (null != codiceTipologiaEndo) {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceTipologiaEndo, "stpTipologieEndo2", Integer.class));
	} else {
	    fr.addFilterField(FilterUtils.isNull("id.codice", "stpTipologieEndo2"));
	}
	fr.addFilterField(FilterUtils.equals("codiceEndoRegionale", codiceEndoRegionale, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<StpEndoTipo2> stpEndoList = stpEndoTipo2DAO.findByFilterTable(ft);
	if (!stpEndoList.isEmpty()) {
	    return stpEndoList.get(0);
	}
	return null;
    }

    @Override
    public Map<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> findListaAttivitaCartOrdinate() {

	return stpEndoTipo2DAO.findListaAttivitaCartOrdinate();
    }

    @Override
    public List<StpEndoTipo2> findbyTipoAndCodiceEndoRegionale(String codiceEndoRegionale, String tipo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipo", tipo, String.class));
	fr.addFilterField(FilterUtils.equals("codiceEndoRegionale", codiceEndoRegionale, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<StpEndoTipo2> stpEndoList = stpEndoTipo2DAO.findByFilterTable(ft);
	return stpEndoList;
    }

    @Override
    public StpEndoTipo2 isIntervetoCART(Integer codiceIntervento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIntervento, "alberoproc", Integer.class));
	fr.addFilterField(FilterUtils.isNotNull("id.codice", "oggetti"));
	ft.addRestriction(fr);
	List<StpEndoTipo2> stpEndoTipo2 = stpEndoTipo2DAO.findByFilterTable(ft);
	if (!stpEndoTipo2.isEmpty()) {
	    return stpEndoTipo2.get(0);
	}
	return null;
    }

    @Override
    public List<Integer> findListCategorieAndAttivita() {

	return stpEndoTipo2DAO.findListCategorieAndAttivita();
    }

    @Override
    public List<StpEndoTipo2> findListByInventarioprocedimenti(Integer codiceInventario) {

	if (codiceInventario == null) {
	    return null;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codice"));
	List<StpEndoTipo2> stpEndoList = stpEndoTipo2DAO.findByFilterTable(ft);
	return stpEndoList;
    }

    @Override
    public List<StpEndoTipo2> findByCodiceRegionale(String codiceEndoRegionale) {

	if (StringUtils.isNotBlank(codiceEndoRegionale)) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("codiceEndoRegionale", codiceEndoRegionale, String.class));
	    ft.addRestriction(fr);
	    ft.addOrder(FilterUtils.orderDesc("id.codice"));
	    List<StpEndoTipo2> stpEndoList = stpEndoTipo2DAO.findByFilterTable(ft);
	    return stpEndoList;
	} else {
	    return new ArrayList<StpEndoTipo2>();
	}
    }

    @Override
    public List<StpEndoTipo2> findAllByStpCodice(Integer stpCodice, String tipo) {

	return this.stpEndoTipo2DAO.findAllByStpCodice(stpCodice, tipo);
    }

    @Override
    public List<StpEndoTipo2> findByTipoSortByStpCodice(String tipo, boolean sortAsc) {

	return this.stpEndoTipo2DAO.findByTipoSortByStpCodice(tipo, sortAsc);
    }

    @Override
    public StpEndoTipo2 findbyStpCodiceAndSoftwareAlberoProc(Integer stpCodice, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codiceStp", stpCodice, Integer.class));
	fr.addFilterField(FilterUtils.equals("codice", software, "alberoproc.software", String.class));
	ft.addRestriction(fr);
	List<StpEndoTipo2> stpEndoTipo2 = stpEndoTipo2DAO.findByFilterTable(ft);
	if (!stpEndoTipo2.isEmpty()) {
	    return stpEndoTipo2.get(0);
	}
	return null;
    }
}
