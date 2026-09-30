package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.LayouttestiId;
import it.gruppoinit.pal.gp.core.domain.Layouttestibase;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest.RipristinaTestoRequest;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest.SalvataggioTestoRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EtichettaApp;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class LayouttestiServiceImpl extends BaseServiceImpl<Layouttesti, LayouttestiId> implements LayouttestiService {

    private LayouttestiDAO layouttestiDAO;
    private LayouttestibaseService layouttestibaseService;
    private ApplicationContext context;

    @Autowired
    public void setLayouttestiDAO(LayouttestiDAO layouttestiDAO) {

	this.layouttestiDAO = layouttestiDAO;
    }

    @Autowired
    public void setLayouttestibaseService(LayouttestibaseService layouttestibaseService) {

	this.layouttestibaseService = layouttestibaseService;
    }

    @Autowired
    public void setContext(ApplicationContext context) {

	this.context = context;
    }

    @Override
    protected Class<Layouttesti> getEntityClass() {

	return Layouttesti.class;
    }

    @Override
    public void delete(Layouttesti entity) {

	layouttestiDAO.delete(entity);
    }

    @Override
    public List<Layouttesti> findAll(Integer firstResult, Integer maxResult) {

	return layouttestiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Layouttesti findById(LayouttestiId id) {

	return layouttestiDAO.findById(id);
    }

    @Override
    public void insert(Layouttesti entity) {

	layouttestiDAO.insert(entity);
    }

    @Override
    public void update(Layouttesti entity) {

	layouttestiDAO.update(entity);
    }

    @Override
    public String resolveCode(String code, String software) {

	String decodedText = layouttestiDAO.resolveCode(code, software);
	if (decodedText == null) {
	    decodedText = layouttestibaseService.resolveCode(code, software);
	}
	return decodedText;
    }

    @Override
    public void overrideCode(String code, String value) {

	LayouttestiId id = new LayouttestiId();
	id.setCodicetesto(code);
	id.setSoftware(ORMHelper.getSoftware());
	Layouttesti layouttesti = this.findById(id);
	if (layouttesti == null) {
	    Layouttesti layouttestiInsert = new Layouttesti();
	    layouttestiInsert.setId(id);
	    layouttestiInsert.setNuovotesto(value);
	    this.insert(layouttestiInsert);
	} else {
	    layouttesti.setNuovotesto(value);
	    this.update(layouttesti);
	}
	this.ricaricaLabel();
    }

    private void ricaricaLabel() {

	Object msgSource = context.getBean("messageSource");
	if (msgSource instanceof LayoutTestiMessageSource) {
	    LayoutTestiMessageSource layoutTestiMessageSource = (LayoutTestiMessageSource) msgSource;
	    layoutTestiMessageSource.readFromDB();
	}
    }

    @Override
    public List<EtichettaApp> findEtichetteConPrefisso(String prefissoEtichette) {

	Map<String, String> mEtichette = new HashMap<String, String>();
	List<EtichettaApp> ret = new ArrayList<EtichettaApp>();
	List<Layouttestibase> lBase = layouttestibaseService.findByPrefissoOrderBySoftware(prefissoEtichette);
	for (Layouttestibase l : lBase) {
	    if (l.getId().getCodicetesto().toLowerCase().startsWith(prefissoEtichette.toLowerCase())) {
		mEtichette.put(l.getId().getCodicetesto(), l.getTesto());
	    }
	}
	List<Layouttesti> lts = layouttestiDAO.findByPrefissoOrderBySoftware(prefissoEtichette);
	for (Layouttesti l : lts) {
	    mEtichette.put(l.getId().getCodicetesto(), l.getNuovotesto());
	}
	for (Entry<String, String> et : mEtichette.entrySet()) {
	    ret.add(new EtichettaApp(et.getKey(), et.getValue()).rimuoviPrefisso(prefissoEtichette));
	}
	return ret;
    }

    @Override
    public String testoDaEtichetta(String etichetta, String software) {

	if (StringUtils.isBlank(etichetta)) {
	    return null;
	}
	String retVal = this.resolveCode(etichetta, software);
	if (!StringUtils.isBlank(retVal) && !retVal.equalsIgnoreCase(etichetta)) {
	    return retVal;
	}
	return Utilities.getMessageFromBundle(this.context, etichetta, new Object[] {});
    }

    @Override
    public List<LayoutTestiDTO> findTesti() {

	return this.layouttestiDAO.findTesti();
    }

    @Override
    public void salvaTesto(SalvataggioTestoRequest request) {

	Layouttesti testo = new Layouttesti();
	testo.setId(new LayouttestiId(request.getSoftware(), request.getCodiceTesto()));
	testo.setNuovotesto(request.getTesto());
	this.layouttestiDAO.saveEntity(testo);
	this.ricaricaLabel();
    }

    @Override
    public void ripristinaTesto(RipristinaTestoRequest request) {

	Layouttesti testo = this.layouttestiDAO.findById(new LayouttestiId(request.getSoftware(), request.getCodiceTesto()));
	this.layouttestiDAO.delete(testo);
	this.ricaricaLabel();
    }
}
