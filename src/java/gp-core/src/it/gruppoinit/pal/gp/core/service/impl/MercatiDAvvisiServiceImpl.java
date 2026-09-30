package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDAvvisiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDAvvisi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.MercatiDAvvisiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MercatiDAvvisiServiceImpl extends BaseServiceImpl<MercatiDAvvisi, PkId> implements MercatiDAvvisiService {

    private MercatiDAvvisiDAO mercatidavvisiDAO;
    private AnagrafeService anagrafeService;
    private MercatiDService mercatiDService;
    private TipicausalioneriService tipicausalioneriService;

    @Autowired
    public void setMercatiDAvvisiDAO(MercatiDAvvisiDAO mercatidavvisiDAO) {

	this.mercatidavvisiDAO = mercatidavvisiDAO;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Override
    protected Class<MercatiDAvvisi> getEntityClass() {

	return MercatiDAvvisi.class;
    }

    @Override
    public List<MercatiDAvvisi> findAll(Integer firstResult, Integer maxResult) {

	return mercatidavvisiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MercatiDAvvisi entity) {

	if (validateEntity(entity)) {
	    dataIntegration(entity);
	    mercatidavvisiDAO.insert(entity);
	}
    }

    @Override
    public MercatiDAvvisi findById(PkId id) {

	return mercatidavvisiDAO.findById(id);
    }

    @Override
    public void update(MercatiDAvvisi entity) {

	if (validateEntity(entity)) {
	    dataIntegration(entity);
	    mercatidavvisiDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiDAvvisi entity) {

	if (isDeleteAllowed(entity)) {
	    mercatidavvisiDAO.delete(entity);
	}
    }

    private void dataIntegration(MercatiDAvvisi entity) {

	if (entity.getFlagVerificato() == null) {
	    entity.setFlagVerificato(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    public List<MercatiDAvvisi> findAllByPosteggio(Integer codice, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "mercatiD", Integer.class));
	ft.addRestriction(fr);
	List<MercatiDAvvisi> list = null;
	if (null != firstResult && null != maxResult) {
	    list = mercatidavvisiDAO.findByFilterTable(ft, null, null);
	} else {
	    list = mercatidavvisiDAO.findByFilterTable(ft);
	}
	return list;
    }

    @Override
    protected void fixMergeEntityProperties(MercatiDAvvisi entity) {

	Anagrafe anagrafe = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(anagrafe);
	MercatiD mercatiD = mercatiDService.bindDomainObject(entity.getMercatiD(), PkId.class, "id.codice");
	entity.setMercatiD(mercatiD);
	Tipicausalioneri tipicausalioneri = tipicausalioneriService.bindDomainObject(entity.getTipicausalioneri(), PkId.class, "id.codice");
	entity.setTipicausalioneri(tipicausalioneri);
    }

    @Override
    public List<Integer> findAvvisiByPosteggioAndAnagrafeDistincCausaliOneri(Integer codiceAnagrafe, Integer codicePosteggio) {

	return mercatidavvisiDAO.findAvvisiByPosteggioAndAnagrafeDistincCausaliOneri(codiceAnagrafe, codicePosteggio);
    }

    @Override
    public boolean isNotVerificatoAvvisoByAnagrafeAndPosteggioAndCausaleOnere(Integer codicePosteggio, Integer causaleOnere) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = setFilterAnagrafeAndPosteggioAndTipocausaleOnere(codicePosteggio, causaleOnere);
	fr.addFilterField(FilterUtils.equals("flagVerificato", false, Boolean.class));
	ft.addRestriction(fr);
	List<MercatiDAvvisi> mercatiDAvvisis = mercatidavvisiDAO.findByFilterTable(ft);
	if (!mercatiDAvvisis.isEmpty()) {
	    return true;
	}
	return false;
    }

    @Override
    public List<MercatiDAvvisi> findAvvisiMercatiD(Integer codicePosteggio, Integer codiceCausaleOnere) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = setFilterAnagrafeAndPosteggioAndTipocausaleOnere(codicePosteggio, codiceCausaleOnere);
	ft.addRestriction(fr);
	List<MercatiDAvvisi> mercatiDAvvisis = mercatidavvisiDAO.findByFilterTable(ft);
	return mercatiDAvvisis;
    }

    private FilterRestriction setFilterAnagrafeAndPosteggioAndTipocausaleOnere(Integer codicePosteggio, Integer codiceCausaleOnere) {

	FilterRestriction fr = new FilterRestriction();
	//fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codicePosteggio, "mercatiD", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceCausaleOnere, "tipicausalioneri", Integer.class));
	return fr;
    }
    //    protected boolean isDeleteAllowed(MercatiDAvvisi entity) {
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
