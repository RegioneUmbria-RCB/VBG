package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MappatureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Mappature;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2CampiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2ModellitHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.MappatureService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class MappatureServiceImpl extends BaseServiceImpl<Mappature, PkId> implements MappatureService {

    private MappatureDAO mappatureDAO;
    private SoftwareService softwareService;
    private Dyn2ModellitService dyn2ModellitService;
    private Dyn2CampiService dyn2CampiService;

    @Autowired
    public void setMappatureDAO(MappatureDAO mappatureDAO) {

	this.mappatureDAO = mappatureDAO;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Override
    protected Class<Mappature> getEntityClass() {

	return Mappature.class;
    }

    @Override
    public List<Mappature> findAll(Integer firstResult, Integer maxResult) {

	return mappatureDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Mappature entity) {

	if (validateEntity(entity)) {
	    mappatureDAO.insert(entity);
	}
    }

    @Override
    public Mappature findById(PkId id) {

	return mappatureDAO.findById(id);
    }

    @Override
    public void update(Mappature entity) {

	if (validateEntity(entity)) {
	    mappatureDAO.update(entity);
	}
    }

    @Override
    public void delete(Mappature entity) {

	if (isDeleteAllowed(entity)) {
	    mappatureDAO.delete(entity);
	}
    }

    @Override
    public List<Mappature> findByFilterTable(FilterTable filterTable) {

	return mappatureDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Mappature> findByNomeTagPeople(String nomeTagPeople) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("nometagpeople", nomeTagPeople, String.class));
	filterTable.addRestriction(restriction);
	return this.findByFilterTable(filterTable);
    }

    @Override
    public List<String> findByNometagpeopleDistinct(String textToSearch) {

	return mappatureDAO.findByNometagpeopleDistinct(textToSearch);
    }

    @Override
    public List<Software> findSoftwareByNometagpeople(String textToSearch, String nometagpeople) {

	List<String> codicesoftwares = mappatureDAO.findSoftwareByNometagpeople(textToSearch, nometagpeople);
	List<Software> softwares = new ArrayList<Software>();
	for (String string : codicesoftwares) {
	    Software software = softwareService.findById(string);
	    softwares.add(software);
	}
	return softwares;
    }

    @Override
    public List<Dyn2Modellit> findSchedaByTagAndSoftware(String textToSearch, String nometagpeople, String codicesoftware) {

	List<Integer> codiceschedas = mappatureDAO.findSchedaByTagAndSoftware(textToSearch, nometagpeople, codicesoftware);
	List<Dyn2Modellit> schedas = new ArrayList<Dyn2Modellit>();
	for (Integer codice : codiceschedas) {
	    Dyn2Modellit scheda = dyn2ModellitService.findById(new PkId(codice));
	    schedas.add(scheda);
	}
	return schedas;
    }

    @Override
    public void updateMapsFromDyn2ModellitHelper(Dyn2ModellitHelper dyn2ModellitHelper) {

	List<Dyn2CampiHelper> campiHelpers = dyn2ModellitHelper.getCampiHelpers();
	for (Dyn2CampiHelper dyn2CampiHelper : campiHelpers) {
	    // Elimino vecchie mappature
	    Dyn2Campi dyn2Campi = dyn2CampiService.findById(dyn2CampiHelper.getCampo().getId());
	    if (dyn2Campi.getMappatures() != null && !dyn2Campi.getMappatures().isEmpty()) {
		for (Mappature mappatura : dyn2Campi.getMappatures()) {
		    // Selezionare solo le mappature della relativa scheda		
		    if (mappatura.getDyn2Modellit().getId().getCodice().equals(dyn2ModellitHelper.getScheda().getId().getCodice())) {
			mappatureDAO.delete(mappatura);
		    }
		}
	    }
	    // Inserisco nuove mappature
	    if (dyn2CampiHelper.getMappatures() != null && !dyn2CampiHelper.getMappatures().isEmpty()) {
		List<Mappature> mappatures = dyn2CampiHelper.getMappatures();
		for (Mappature mappatura : mappatures) {
		    if (mappatura.getNometagpeople() != null && !mappatura.getNometagpeople().isEmpty()) {
			dataIntegration(mappatura);
			if (validateEntity(mappatura)) {
			    mappatura.setDyn2Campi(dyn2CampiHelper.getCampo());
			    mappatura.setDyn2Modellit(dyn2ModellitHelper.getScheda());
			    if (mappatura.getTiporegola() != 2 && mappatura.getTiporegola() != 3) {
				mappatura.setValoreconfronto(null);
			    }
			    mappatureDAO.insert(mappatura);
			}
		    }
		}
	    }
	}
    }

    private void dataIntegration(Mappature entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro mappatura è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Mappature entity) {

	Dyn2Modellit dyn2Modellit = dyn2ModellitService.bindDomainObject(entity.getDyn2Modellit(), PkId.class, "id.codice");
	entity.setDyn2Modellit(dyn2Modellit);
	Dyn2Campi dyn2Campi = dyn2CampiService.bindDomainObject(entity.getDyn2Campi(), PkId.class, "id.codice");
	entity.setDyn2Campi(dyn2Campi);
    }

    protected boolean isDeleteAllowed(Mappature entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
