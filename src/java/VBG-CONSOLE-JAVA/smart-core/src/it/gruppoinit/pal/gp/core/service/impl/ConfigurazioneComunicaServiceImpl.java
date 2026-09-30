package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ConfigurazioneComunicaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneComunica;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneComunicaService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsTestataService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurazioneComunicaServiceImpl extends BaseServiceImpl<ConfigurazioneComunica, String> implements ConfigurazioneComunicaService {

    private ConfigurazioneComunicaDAO configurazioneComunicaDAO;
    private OggettiService oggettiService;
    private InventarioprocedimentiService inventarioprocedimentiService;

    @Autowired
    public void setConfigurazioneComunicaDAO(ConfigurazioneComunicaDAO configurazioneComunicaDAO) {

	this.configurazioneComunicaDAO = configurazioneComunicaDAO;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    public void insert(ConfigurazioneComunica entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    configurazioneComunicaDAO.insert(entity);
	}
    }

    @Override
    public void update(ConfigurazioneComunica entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    configurazioneComunicaDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(ConfigurazioneComunica entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    configurazioneComunicaDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<ConfigurazioneComunica> findAll(Integer firstResult, Integer maxResult) {

	return configurazioneComunicaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public ConfigurazioneComunica findById(String id) {

	return configurazioneComunicaDAO.findById(id);
    }

    @Override
    protected Class<ConfigurazioneComunica> getEntityClass() {

	return ConfigurazioneComunica.class;
    }

    private void dataIntegration(ConfigurazioneComunica entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro entity è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(ConfigurazioneComunica entity) {

	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggettoWorkflowAreaRis(), PkId.class, "id.codice");
	entity.setOggettoWorkflowAreaRis(oggetto);
	Inventarioprocedimenti ip = inventarioprocedimentiService.bindDomainObjectForPkId(entity.getInventarioprocedimento());
	entity.setInventarioprocedimento(ip);
    }

    @Override
    public boolean existsByCodiceinventario(String idcomune, Integer codiceinventario) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentoId", codiceinventario, Integer.class));
	ft.addRestriction(fr);
	return configurazioneComunicaDAO.existsRecords(ft);
    }
}
