package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipoMetadati;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.metadati.IMetadatiComunicazioniResolver;
import it.gruppoinit.pal.gp.core.features.metadati.MetadatoComune;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Service
public class BollCfgTipoMetadatiServiceImpl implements BollCfgTipoMetadatiService {

    private static final String IT_GRUPPOINIT_PAL_GP_CORE_FEATURES = "it.gruppoinit.pal.gp.core.features";
    private BollCfgTipoMetadatiDAO metadatiDAO;

    @Autowired
    public void setMetadatiDAO(BollCfgTipoMetadatiDAO metadatiDAO) {

	this.metadatiDAO = metadatiDAO;
    }

    @Override
    public List<MetadatiBean> findMetadatiConfigurati(Integer codiceBollcfgTipo) {

	List<BollCfgTipoMetadati> metadatiDb = this.metadatiDAO.findMetadatiConfigurati(codiceBollcfgTipo);
	if (metadatiDb.isEmpty()) {
	    return new ArrayList<MetadatiBean>(0);
	}
	List<MetadatiBean> metadati = new ArrayList<MetadatiBean>(0);
	for (BollCfgTipoMetadati metadato : metadatiDb) {
	    metadati.add(new MetadatiBean(metadato.getChiave(), metadato.getValore()));
	}
	return metadati;
    }

    @Override
    public void updateMetadato(UpdateMetadatoRequest jsonRequest) {

	this.metadatiDAO.updateMetadato(jsonRequest);
    }

    @Override
    public void deleteMetadato(DeleteMetadatoRequest jsonRequest) {

	this.metadatiDAO.deleteMetadato(jsonRequest);
    }

    @Override
    public List<MetadatoBollettazione> elencoMetadati(Integer codiceBollcfgTipo) {

	List<MetadatoBollettazione> metadati = new ArrayList<MetadatoBollettazione>(0);
	//1. Recupero quelli già configurati
	List<BollCfgTipoMetadati> configurati = this.metadatiDAO.findMetadatiConfigurati(codiceBollcfgTipo);
	for (BollCfgTipoMetadati configurato : configurati) {
	    Integer id = configurato.getId().getCodice();
	    String codiceComune = configurato.getComune() != null ? configurato.getComune().getCodicecomune() : null;
	    String comune = configurato.getComune() != null ? configurato.getComune().getComune() : null;
	    String chiave = configurato.getChiave();
	    String valore = configurato.getValore();
	    MetadatoBollettazione metadato = new MetadatoBollettazione(id, codiceComune, comune, chiave, valore);
	    metadati.add(metadato);
	}
	//1. Recupero tutte le implementazioni dell'interfaccia IMetadatiBollettazioneResolver
	ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
	TypeFilter tf = new AssignableTypeFilter(IMetadatiComunicazioniResolver.class);
	scanner.addIncludeFilter(tf);
	for (BeanDefinition bd : scanner.findCandidateComponents(IT_GRUPPOINIT_PAL_GP_CORE_FEATURES)) {
	    try {
		String className = bd.getBeanClassName();
		if (className.indexOf(".") > 0) {
		    className = className.substring(className.lastIndexOf("."));
		    className = className.replace(".", "");
		    className = StringUtils.uncapitalize(className);
		}
		IMetadatiComunicazioniResolver resolver = (IMetadatiComunicazioniResolver) ContextLoader.getCurrentWebApplicationContext()
			.getBean(className);
		List<MetadatoComune> metadatiSpecifici = resolver.get();
		if (metadatiSpecifici == null || metadatiSpecifici.isEmpty()) {
		    continue;
		}
		for (MetadatoComune specifico : metadatiSpecifici) {
		    MetadatoBollettazione nuovo = MetadatoBollettazione.fromMetadatoComune(specifico);
		    if (!metadati.contains(nuovo)) {
			metadati.add(nuovo);
		    }
		}
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return metadati;
    }

    @Override
    public int insertMetadato(InsertMetadatoRequest jsonRequest) {

	return this.metadatiDAO.insertMetadato(jsonRequest);
    }

    @Override
    public List<BollCfgTipoMetadati> findByBollCfgTipo(Integer bollcfgTipo, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", bollcfgTipo, "bollCfgTipo", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("coDescrizione", "tipicausalioneri"));
	return this.metadatiDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public void delete(BollCfgTipoMetadati entity) {

	this.metadatiDAO.delete(entity);
    }
}
