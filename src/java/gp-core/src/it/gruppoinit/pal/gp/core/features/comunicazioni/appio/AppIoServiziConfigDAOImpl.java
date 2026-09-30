package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfig;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigId;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParam;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class AppIoServiziConfigDAOImpl extends BaseDAOImpl<AppIoServiziConfig, AppIoServiziConfigId> implements IAppIoServiziConfigDAO {

    @Autowired
    IAppIoServiziConfigParamDAO appIoServiziConfigParamDAO;
    @Autowired
    ComuniDAO comuniDAO;

    @Override
    public Class<AppIoServiziConfig> getEntityClass() {

	return AppIoServiziConfig.class;
    }

    @Override
    public List<AppIoServiziConfig> findByIdServizio(String idServizio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.identificativoServizio", idServizio, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	fr.addFilterField(FilterUtils.equals("id.software", ORMHelper.getSoftware(), String.class));
	ft.addRestriction(fr);
	List<AppIoServiziConfig> lst = findByFilterTable(ft);
	return lst;
    }

    @Override
    public AppIoServiziConfig findByIdServizioEIstanza(String identServizio, Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.identificativoServizio", identServizio, String.class));
	fr.addFilterField(FilterUtils.equals("id.codiceComune", istanza.getComune().getCodicecomune(), String.class));
	fr.addFilterField(FilterUtils.equals("id.software", istanza.getSoftware().getCodice(), String.class));
	ft.addRestriction(fr);
	List<AppIoServiziConfig> lst = findByFilterTable(ft);
	return lst.isEmpty() ? new AppIoServiziConfig() : lst.get(0); // non torno nullo ma oggetto vuoto 
    }

    @Override
    public AppIoServiziConfigRestResponse findByIdServizioEComune(String idServizio, String comune) {

	AppIoServiziConfigRestResponse response = new AppIoServiziConfigRestResponse();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.identificativoServizio", idServizio, String.class));
	fr.addFilterField(FilterUtils.equals("id.codiceComune", comune, String.class));
	fr.addFilterField(FilterUtils.equals("id.software", ORMHelper.getSoftware(), String.class));
	ft.addRestriction(fr);
	List<AppIoServiziConfig> lst = findByFilterTable(ft);
	if (lst.size() > 1) {
	    throw new RuntimeException("Ci sono più servizi configurati con lo stesso id: " + idServizio);
	}
	Comuni comuneRet = this.comuniDAO.findByCodiceComune(new Comuni(comune));
	if (lst.isEmpty()) {
	    return new AppIoServiziConfigRestResponse();
	}
	AppIoServiziConfig servizio = lst.get(0);
	response.setAmbito(servizio.getAmbito());
	response.setAttivo(servizio.isAttivo());
	response.setCodiceComune(comune);
	response.setComune(comuneRet.getComune());
	response.setIdentificativoServizio(idServizio);
	response.setMaxNumMessaggio(servizio.getMaxMessaggiGiorno().toString());
	response.setMessaggio(servizio.getTemplateMessaggio());
	response.setOggettoMesaggio(servizio.getTemplateOggetto());
	response.setSoftware(servizio.getId().getSoftware());
	List<AppIoServiziConfigParam> parametriCfg = this.appIoServiziConfigParamDAO.findByIdServizioEComune(idServizio, comune);
	List<AppIoParamRestResponse> parametri = new ArrayList<AppIoParamRestResponse>();
	for (AppIoServiziConfigParam appIoServiziConfigParam : parametriCfg) {
	    AppIoParamRestResponse r = new AppIoParamRestResponse();
	    r.setDescrizione(appIoServiziConfigParam.getDescrizione());
	    r.setParametro(appIoServiziConfigParam.getId().getParametro());
	    r.setIdentificativoServizio(appIoServiziConfigParam.getId().getIdentificativoServizio());
	    parametri.add(r);
	}
	response.setParametri(parametri);
	return response;
    }

    /**
     * Batch version: fetches configs for one servizio across multiple comuni in a single query.
     */
    @Override
    public Map<String, AppIoServiziConfigRestResponse> findByIdServizioAndComuni(String idServizio, Set<String> comuni) {

	Map<String, AppIoServiziConfigRestResponse> result = new HashMap<String, AppIoServiziConfigRestResponse>();
	if (comuni == null || comuni.isEmpty()) {
	    return result;
	}
	// 1. Single query for all comuni at once
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.identificativoServizio", idServizio, String.class));
	fr.addFilterField(FilterUtils.in("id.codiceComune", comuni.toArray(), String.class));
	fr.addFilterField(FilterUtils.equals("id.software", ORMHelper.getSoftware(), String.class));
	ft.addRestriction(fr);
	List<AppIoServiziConfig> lst = findByFilterTable(ft);
	// 2. Index results by codiceComune, check for duplicates
	Map<String, AppIoServiziConfig> configByComune = new HashMap<String, AppIoServiziConfig>();
	for (AppIoServiziConfig cfg : lst) {
	    String codice = cfg.getId().getCodiceComune();
	    if (configByComune.containsKey(codice)) {
		throw new RuntimeException("Ci sono più servizi configurati con lo stesso id: " + idServizio + " e comune: " + codice);
	    }
	    configByComune.put(codice, cfg);
	}
	// 3. Batch-fetch all Comuni entities
	Map<String, Comuni> comuniMap = new HashMap<String, Comuni>();
	for (String codice : comuni) {
	    Comuni comuneRet = this.comuniDAO.findByCodiceComune(new Comuni(codice));
	    if (comuneRet != null) {
		comuniMap.put(codice, comuneRet);
	    }
	}
	// 4. Batch-fetch all params for this servizio + all comuni
	//    (if your DAO supports it — otherwise fall back to per-comune calls)
	Map<String, List<AppIoServiziConfigParam>> paramsByComune = new HashMap<String, List<AppIoServiziConfigParam>>();
	for (String codice : configByComune.keySet()) {
	    paramsByComune.put(codice, this.appIoServiziConfigParamDAO.findByIdServizioEComune(idServizio, codice));
	}
	// 5. Assemble responses
	for (Map.Entry<String, AppIoServiziConfig> entry : configByComune.entrySet()) {
	    String codice = entry.getKey();
	    AppIoServiziConfig servizio = entry.getValue();
	    Comuni comuneRet = comuniMap.get(codice);
	    AppIoServiziConfigRestResponse response = new AppIoServiziConfigRestResponse();
	    response.setAmbito(servizio.getAmbito());
	    response.setAttivo(servizio.isAttivo());
	    response.setCodiceComune(codice);
	    response.setComune(comuneRet != null ? comuneRet.getComune() : null);
	    response.setIdentificativoServizio(idServizio);
	    response.setMaxNumMessaggio(servizio.getMaxMessaggiGiorno().toString());
	    response.setMessaggio(servizio.getTemplateMessaggio());
	    response.setOggettoMesaggio(servizio.getTemplateOggetto());
	    response.setSoftware(servizio.getId().getSoftware());
	    List<AppIoParamRestResponse> parametri = new ArrayList<AppIoParamRestResponse>();
	    List<AppIoServiziConfigParam> parametriCfg = paramsByComune.get(codice);
	    if (parametriCfg != null) {
		for (AppIoServiziConfigParam param : parametriCfg) {
		    AppIoParamRestResponse r = new AppIoParamRestResponse();
		    r.setDescrizione(param.getDescrizione());
		    r.setParametro(param.getId().getParametro());
		    r.setIdentificativoServizio(param.getId().getIdentificativoServizio());
		    parametri.add(r);
		}
	    }
	    response.setParametri(parametri);
	    result.put(codice, response);
	}
	return result;
    }

    @Override
    public List<AppIoServizi> findAllBySoftware(String idcomune, String codiceComune, String software) {

	String hql1 = "select distinct q from AppIoServiziConfig p inner join p.appIoServizi q where p.id.idcomune=q.id.idcomune and p.id.identificativoServizio=q.id.identificativoServizio and p.id.software=? and p.id.idcomune=? and q.id.idcomune=?";
	List<AppIoServizi> lst = getHibernateTemplate().find(hql1, new Object[] { software, ORMHelper.getIdcomune(), ORMHelper.getIdcomune() });
	/*FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("id.software", software, String.class));
	ft.addRestriction(fr);
	List<AppIoServiziConfig> lst = findByFilterTable(ft);*/
	return lst;
    }
}
