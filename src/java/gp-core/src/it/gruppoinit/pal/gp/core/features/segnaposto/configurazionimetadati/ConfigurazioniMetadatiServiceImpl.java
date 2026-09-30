package it.gruppoinit.pal.gp.core.features.segnaposto.configurazionimetadati;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioniMetadati;

@Service
public class ConfigurazioniMetadatiServiceImpl extends BaseDAOImpl implements IConfigurazioniMetadatiService {

    @Autowired
    private IConfigurazioniMetadatiDAO configurazioniMetadatiDAO;

    @Override
    public void insertOrUpdate(ConfigurazioniMetadati entity) {

	this.configurazioniMetadatiDAO.save(entity);
    }

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public void delete(ConfigurazioniMetadati entity) {

	this.configurazioniMetadatiDAO.delete(entity);
    }

    @Override
    public List<ConfigurazioniMetadati> findByIdComuniAssociatiSoftware(Integer idComuniAssociatiSoftware) {

	return this.configurazioniMetadatiDAO.findByIdComuniAssociatiSoftware(idComuniAssociatiSoftware);
    }

    @Override
    public List<ConfigurazioniMetadatiBean> getConfigurazioniMetadatiBean(Integer idComuniAssociatiSoftware) {

	List<ConfigurazioniMetadati> configurazioniMetadati = findByIdComuniAssociatiSoftware(idComuniAssociatiSoftware);
	List<ConfigurazioniMetadatiBean> configurazioniMetadatiBeans = new ArrayList<ConfigurazioniMetadatiBean>(0);
	for (ConfigurazioniMetadati coMetadati : configurazioniMetadati) {
	    ConfigurazioniMetadatiBean configurazioniMetadatiBean = new ConfigurazioniMetadatiBean();
	    configurazioniMetadatiBean.setChiave(coMetadati.getId().getChiave());
	    configurazioniMetadatiBean.setIdComuniAssociatiSoftware(coMetadati.getId().getIdComuniAssociatiSoftware());
	    configurazioniMetadatiBean.setValore(coMetadati.getValore());
	    configurazioniMetadatiBean.setOrdine(coMetadati.getOrdine());
	    configurazioniMetadatiBean.setCategoria(coMetadati.getCategoria());
	    configurazioniMetadatiBeans.add(configurazioniMetadatiBean);
	}
	return configurazioniMetadatiBeans;
    }

    @Override
    public ConfigurazioniMetadati findByChiaveAndIdcomune(String chiave) {

	ConfigurazioniMetadati confMeta = null;
	confMeta = this.configurazioniMetadatiDAO.findByChiaveAndIdcomune(chiave, false);
	if (confMeta == null) {
	    confMeta = this.configurazioniMetadatiDAO.findByChiaveAndIdcomune(chiave, true);
	}
	return confMeta;
    }

    @Override
    public List<String> findByCategoireDistinct() {

	List<String> list = this.configurazioniMetadatiDAO.findByCategoireDistinct();
	for (int i = 0; i < list.size(); i++) {
	    if (StringUtils.isNotBlank(list.get(i))) {
		list.set(i, list.get(i).toUpperCase());
	    }
	}
	return list;
    }

    @Override
    public int updateCategoria(String categoriaOrginale, String nuovaCategoria) {

	return this.configurazioniMetadatiDAO.updateCategoria(StringUtils.upperCase(categoriaOrginale), StringUtils.upperCase(nuovaCategoria));
    }
}
