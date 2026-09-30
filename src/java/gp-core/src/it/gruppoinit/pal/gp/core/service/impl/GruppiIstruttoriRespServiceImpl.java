package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.GruppiIstruttoriRespDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttoriResp;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriRespService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliAssenzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class GruppiIstruttoriRespServiceImpl extends BaseServiceImpl<GruppiIstruttoriResp, PkId> implements GruppiIstruttoriRespService {

    private GruppiIstruttoriRespDAO gruppiistruttorirespDAO;
    private GruppiIstruttoriService gruppiIstruttoriService;
    private ResponsabiliService responsabiliService;
    private ResponsabiliAssenzeService responsabiliAssenzeService;
    private ResponsabilisoftwareService responsabilisoftwareService;

    @Autowired
    public void setGruppiIstruttoriRespDAO(GruppiIstruttoriRespDAO gruppiistruttorirespDAO) {

	this.gruppiistruttorirespDAO = gruppiistruttorirespDAO;
    }

    @Autowired
    public void setGruppiIstruttoriService(GruppiIstruttoriService gruppiIstruttoriService) {

	this.gruppiIstruttoriService = gruppiIstruttoriService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setResponsabiliAssenzeService(ResponsabiliAssenzeService responsabiliAssenzeService) {

	this.responsabiliAssenzeService = responsabiliAssenzeService;
    }

    @Autowired
    public void setResponsabilisoftwareService(ResponsabilisoftwareService responsabilisoftwareService) {

	this.responsabilisoftwareService = responsabilisoftwareService;
    }

    @Override
    protected Class<GruppiIstruttoriResp> getEntityClass() {

	return GruppiIstruttoriResp.class;
    }

    @Override
    public List<GruppiIstruttoriResp> findAll(Integer firstResult, Integer maxResult) {

	return gruppiistruttorirespDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(GruppiIstruttoriResp entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    gruppiistruttorirespDAO.insert(entity);
	}
    }

    @Override
    public GruppiIstruttoriResp findById(PkId id) {

	return gruppiistruttorirespDAO.findById(id);
    }

    @Override
    public void update(GruppiIstruttoriResp entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    gruppiistruttorirespDAO.update(entity);
	}
    }

    @Override
    public void delete(GruppiIstruttoriResp entity) {

	if (isDeleteAllowed(entity)) {
	    gruppiistruttorirespDAO.delete(entity);
	}
    }

    @Override
    public List<GruppiIstruttoriResp> findByGruppoIstruttori(Integer codice, Boolean controllaAssenza, Boolean controllaSeAttivoPerSoftwareCorrente,
	    Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "gruppiIstruttori", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("responsabile", "responsabili"));
	List<GruppiIstruttoriResp> list = gruppiistruttorirespDAO.findByFilterTable(ft, firstResult, maxResult);
	if (controllaAssenza) {
	    List<GruppiIstruttoriResp> _list = new ArrayList<GruppiIstruttoriResp>();
	    for (GruppiIstruttoriResp gruppiIstruttoriResp : list) {
		//.
		boolean isAssente = responsabiliAssenzeService.isAssente(gruppiIstruttoriResp.getResponsabili(), new Date());
		if (isAssente) {
		    gruppiIstruttoriResp.getResponsabili().setIsAssenteOra(isAssente);
		}
		_list.add(gruppiIstruttoriResp);
	    }
	    list = _list;
	}
	if (controllaSeAttivoPerSoftwareCorrente) {
	    List<GruppiIstruttoriResp> _list = new ArrayList<GruppiIstruttoriResp>();
	    for (GruppiIstruttoriResp gruppiIstruttoriResp : list) {
		if (StringUtils.isNotBlank(gruppiIstruttoriResp.getResponsabili().getAmministratore())
			&& gruppiIstruttoriResp.getResponsabili().getAmministratore().equals("1")) {
		    gruppiIstruttoriResp.setAttivoPerSoftwareCorrente(true);
		} else {
		    ResponsabilisoftwareId id = new ResponsabilisoftwareId(ORMHelper.getSoftware(), gruppiIstruttoriResp.getResponsabili().getId()
			    .getCodice());
		    // TUTTI LI DEVO CONSIDERARE, SEMBRA DI NO?????
		    Responsabilisoftware responsabilisoftware = responsabilisoftwareService.findById(id);
		    if (responsabilisoftware != null) {
			gruppiIstruttoriResp.setAttivoPerSoftwareCorrente(true);
		    }
		}
		_list.add(gruppiIstruttoriResp);
	    }
	    list = _list;
	}
	return list;
    }

    @Override
    public List<GruppiIstruttoriResp> findByGruppoIstruttoriSelezionabili(Integer codice) {

	List<GruppiIstruttoriResp> list = this.findByGruppoIstruttori(codice, true, true, null, null);
	List<GruppiIstruttoriResp> _list = new ArrayList<GruppiIstruttoriResp>();
	for (GruppiIstruttoriResp gruppiIstruttoriResp : list) {
	    if (gruppiIstruttoriResp.getIsAttivoPerSoftwareCorrente() && !gruppiIstruttoriResp.getResponsabili().getIsAssenteOra()) {
		_list.add(gruppiIstruttoriResp);
	    }
	}
	return _list;
    }

    private void dataIntegration(GruppiIstruttoriResp entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(GruppiIstruttoriResp entity) {

	Responsabili responsabili = responsabiliService.bindDomainObject(entity.getResponsabili(), PkId.class, "id.codice");
	entity.setResponsabili(responsabili);
	GruppiIstruttori gruppiIstruttori = gruppiIstruttoriService.bindDomainObject(entity.getGruppiIstruttori(), PkId.class, "id.codice");
	entity.setGruppiIstruttori(gruppiIstruttori);
    }
    //    protected boolean isDeleteAllowed(GruppiIstruttoriResp entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
