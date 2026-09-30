package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FirmeRemote;
import it.gruppoinit.pal.gp.core.domain.FirmeRemoteParametri;
import it.gruppoinit.pal.gp.core.domain.FirmeRemoteParametriId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.ProviderFirmaEnum;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaRemotaListModel;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaRemotaModel;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaRemotaParametroModel;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.ProviderFirmaModel;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class FirmeRemoteServiceImpl extends BaseServiceImpl<FirmeRemote, PkId> implements FirmeRemoteService {

    @Autowired
    private FirmeRemoteDAO firmeRemoteDAO;
    @Autowired
    private FirmeRemoteParametriDAO parametriDAO;

    @Override
    protected Class<FirmeRemote> getEntityClass() {

	return FirmeRemote.class;
    }

    @Override
    public List<ProviderFirmaModel> getFirmeIntegrate() {

	List<ProviderFirmaModel> retVal = new ArrayList<ProviderFirmaModel>();
	for (ProviderFirmaEnum provider : ProviderFirmaEnum.values()) {
	    retVal.add(new ProviderFirmaModel(provider.toString(), provider.value()));
	}
	return retVal;
    }

    @Override
    public int insert(FirmaRemotaModel firma) {

	FirmeRemote entity = new FirmeRemote();
	entity.setProvider(firma.getProvider().getChiave());
	entity.setDescrizione(firma.getDescrizione());
	entity.setEndpoint(firma.getEndpoint());
	entity.setFlagAttiva(firma.isAttiva());
	this.insert(entity);
	for (FirmaRemotaParametroModel parametro : firma.getParametri()) {
	    FirmeRemoteParametri p = new FirmeRemoteParametri();
	    p.setDescrizione(parametro.getDescrizione());
	    p.setFirmaRemota(entity);
	    p.setObbligatorio(parametro.isObbligatorio());
	    p.setReadonly(parametro.isReadOnly());
	    p.setTipoCampo(parametro.getTipoCampo());
	    p.setValoreDefault(parametro.getValoreDefault());
	    p.setVisibile(parametro.isVisibile());
	    p.setOrdine(parametro.getOrdine());
	    this.insertParametro(p);
	}
	return entity.getId().getCodice();
    }

    @Override
    public FirmaRemotaModel getFirmaRemota(Integer codice) {

	FirmeRemote entity = this.findById(new PkId(codice));
	FirmaRemotaModel model = new FirmaRemotaModel();
	model.setId(entity.getId().getCodice());
	model.setProviderName(entity.getProvider());
	model.setAttiva(entity.getFlagAttiva());
	model.setDescrizione(entity.getDescrizione());
	model.setEndpoint(entity.getEndpoint());
	List<FirmeRemoteParametri> entityParametri = this.findParamertiByIdTestata(codice);
	for (FirmeRemoteParametri entityParametro : entityParametri) {
	    FirmaRemotaParametroModel parametro = new FirmaRemotaParametroModel();
	    parametro.setChiave(entityParametro.getId().getChiave());
	    parametro.setDescrizione(entityParametro.getDescrizione());
	    parametro.setObbligatorio(entityParametro.getObbligatorio());
	    parametro.setReadOnly(entityParametro.getReadonly());
	    parametro.setTipoCampo(entityParametro.getTipoCampo());
	    parametro.setValoreDefault(entityParametro.getValoreDefault());
	    parametro.setVisibile(entityParametro.getVisibile());
	    parametro.setOrdine(entityParametro.getOrdine());
	    model.getParametri().add(parametro);
	}
	return model;
    }

    @Override
    public List<FirmeRemoteParametri> findParamertiByIdTestata(Integer codice) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIdFirmaRemota", codice, Integer.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("id.chiave"));
	return this.parametriDAO.findByFilterTable(filterTable);
    }

    @Override
    public void updateFirma(FirmaRemotaModel firma) {

	FirmeRemote entity = this.findById(new PkId(firma.getId()));
	entity.setProvider(firma.getProvider().getChiave());
	entity.setDescrizione(firma.getDescrizione());
	entity.setEndpoint(firma.getEndpoint());
	entity.setFlagAttiva(firma.isAttiva());
	this.update(entity);
	this.parametriDAO.deleteByIdFirma(firma.getId());
	for (FirmaRemotaParametroModel parametroModel : firma.getParametri()) {
	    FirmeRemoteParametri parametro = new FirmeRemoteParametri();
	    parametro.setId(new FirmeRemoteParametriId(ORMHelper.getIdcomune(), firma.getId(), parametroModel.getChiave()));
	    parametro.setDescrizione(parametroModel.getDescrizione());
	    parametro.setFirmaRemota(entity);
	    parametro.setObbligatorio(parametroModel.isObbligatorio());
	    parametro.setReadonly(parametroModel.isReadOnly());
	    parametro.setTipoCampo(parametroModel.getTipoCampo());
	    parametro.setValoreDefault(parametroModel.getValoreDefault());
	    parametro.setVisibile(parametroModel.isVisibile());
	    parametro.setOrdine(parametroModel.getOrdine());
	    this.parametriDAO.insert(parametro);
	}
    }

    @Override
    public void delete(Integer codice) {

	FirmeRemote entity = this.findById(new PkId(codice));
	this.childDelete(entity);
	this.delete(entity);
    }

    @Override
    protected void childDelete(FirmeRemote entity) {

	if (entity.getParametri() != null) {
	    for (FirmeRemoteParametri parametro : entity.getParametri()) {
		this.parametriDAO.delete(parametro);
	    }
	}
    }

    @Override
    public void insert(FirmeRemote entity) {

	this.firmeRemoteDAO.insert(entity);
    }

    @Override
    public void update(FirmeRemote entity) {

	this.firmeRemoteDAO.update(entity);
    }

    @Override
    public void delete(FirmeRemote entity) {

	this.firmeRemoteDAO.delete(entity);
    }

    @Override
    public List<FirmeRemote> findAll(Integer firstResult, Integer maxResult) {

	return this.firmeRemoteDAO.findAll(firstResult, maxResult);
    }

    @Override
    public FirmeRemote findById(PkId id) {

	return this.firmeRemoteDAO.findById(id);
    }

    @Override
    public List<FirmaRemotaListModel> listaFirmeIntegrate() {

	List<FirmeRemote> elenco = this.findAll(null, null);
	List<FirmaRemotaListModel> retVal = new ArrayList<FirmaRemotaListModel>();
	for (FirmeRemote firma : elenco) {
	    FirmaRemotaListModel model = new FirmaRemotaListModel();
	    model.setId(firma.getId().getCodice());
	    ProviderFirmaEnum provider = ProviderFirmaEnum.valueOf(firma.getProvider());
	    model.setProvider(new ProviderFirmaModel(provider.name(), provider.value()));
	    model.setDescrizione(firma.getDescrizione());
	    model.setAttiva(firma.getFlagAttiva());
	    model.setEndpoint(firma.getEndpoint());
	    retVal.add(model);
	}
	return retVal;
    }

    @Override
    public void insertParametro(FirmeRemoteParametri parametro) {

	this.parametriDAO.insert(parametro);
    }

    @Override
    public List<ChiaveValoreBean<Integer, String>> getFirmeAttive() {

	List<FirmeRemote> firmeAttive = this.firmeRemoteDAO.getFirmeAttive();
	List<ChiaveValoreBean<Integer, String>> retVal = new ArrayList<ChiaveValoreBean<Integer, String>>();
	for (FirmeRemote firma : firmeAttive) {
	    ChiaveValoreBean<Integer, String> firmaAttiva = new ChiaveValoreBean<Integer, String>();
	    firmaAttiva.setChiave(firma.getId().getCodice());
	    firmaAttiva.setValore(firma.getDescrizione());
	    retVal.add(firmaAttiva);
	}
	return retVal;
    }

    @Override
    public boolean almenoUnaFirmaRemotaAttiva() {

	return this.getFirmeAttive().size() > 0;
    }
}
