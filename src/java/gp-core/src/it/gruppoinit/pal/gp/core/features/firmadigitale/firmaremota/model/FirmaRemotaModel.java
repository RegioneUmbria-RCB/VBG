package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.ProviderFirmaEnum;

public class FirmaRemotaModel {

    private Integer id;
    private String providerName;
    private String descrizione;
    private String endpoint;
    private boolean attiva;
    private List<FirmaRemotaParametroModel> parametri = ListUtils.lazyList(new ArrayList<FirmaRemotaParametroModel>(),
	    FactoryUtils.instantiateFactory(FirmaRemotaParametroModel.class));

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getProviderName() {

	return this.providerName;
    }

    public void setProviderName(String providerName) {

	this.providerName = providerName;
    }

    public ProviderFirmaModel getProvider() {

	if (StringUtils.isBlank(this.providerName)) {
	    return null;
	}
	ProviderFirmaEnum p = ProviderFirmaEnum.valueOf(this.providerName);
	return new ProviderFirmaModel(p.name(), p.value());
    }

    public void setProvider(ProviderFirmaModel provider) {

	if (provider == null) {
	    this.providerName = null;
	    return;
	}
	this.providerName = provider.getChiave();
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getEndpoint() {

	return endpoint;
    }

    public void setEndpoint(String endpoint) {

	this.endpoint = endpoint;
    }

    public boolean isAttiva() {

	return attiva;
    }

    public void setAttiva(boolean attiva) {

	this.attiva = attiva;
    }

    public List<FirmaRemotaParametroModel> getParametri() {

	if (this.parametri == null) {
	    this.parametri = new ArrayList<FirmaRemotaParametroModel>();
	}
	return parametri;
    }

    public void setParametri(List<FirmaRemotaParametroModel> parametri) {

	this.parametri = parametri;
    }
}
