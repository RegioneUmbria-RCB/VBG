package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigValues;
import it.gruppoinit.pal.gp.pay.features.interfaccia.PayConnectorConfigValuesHelper;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;

public class FakePayConnectorConfigValuesService implements PayConnectorConfigValuesService {

    private Map<String, String> parametri = new HashMap<>();

    public FakePayConnectorConfigValuesService(Map<String, String> parametri) {

	this.parametri = parametri;
    }

    @Override
    public void insert(PayConnectorConfigValues entity) {

	//non necessario
    }

    @Override
    public void update(PayConnectorConfigValues entity) {

	//non necessario
    }

    @Override
    public void delete(PayConnectorConfigValues entity) {

	//non necessario
    }

    @Override
    public List<PayConnectorConfigValues> findAll(Integer firstResult, Integer maxResult) {

	//non necessario
	return null;
    }

    @Override
    public PayConnectorConfigValues findById(PkId id) {

	//non necessario
	return null;
    }

    @Override
    public PayConnectorConfigValues bindDomainObject(PayConnectorConfigValues entity, Class<?> idClass, String idPath) {

	//non necessario
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(PayConnectorConfigValues entity) {

	//non necessario
	return null;
    }

    @Override
    public boolean isDocumentiSuFilesystem() {

	//non necessario
	return false;
    }

    @Override
    public String getDocumentiSuFilesystemPath() {

	//non necessario
	return null;
    }

    @Override
    public String getValoreParametroConfigurazione(ConfigParamNames paramName) {

	return parametri.get(paramName.name());
    }

    @Override
    public String getValoreParametroConfigurazione(ConfigParamNames paramName, String connectorCode) {

	//non necessario
	return null;
    }

    @Override
    public List<PayConnectorConfigValuesHelper> getConnectorConfigValuesFromCodiceConnettore(String connectorCode) {

	//non necessario
	return null;
    }
}
