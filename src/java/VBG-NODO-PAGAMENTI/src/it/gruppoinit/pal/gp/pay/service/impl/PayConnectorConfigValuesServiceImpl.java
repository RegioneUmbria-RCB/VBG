package it.gruppoinit.pal.gp.pay.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayConnectorConfigValuesDAO;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigValues;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.features.interfaccia.PayConnectorConfigValuesHelper;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;

@Service
public class PayConnectorConfigValuesServiceImpl extends BaseServiceImpl<PayConnectorConfigValues, PkId> implements PayConnectorConfigValuesService {

    @Autowired
    private PayConnectorConfigValuesDAO payConnectorConfigValuesDAO;

    @Override
    public void insert(PayConnectorConfigValues entity) {

	this.payConnectorConfigValuesDAO.insert(entity);
    }

    @Override
    public void update(PayConnectorConfigValues entity) {

	this.payConnectorConfigValuesDAO.update(entity);
    }

    @Override
    public void delete(PayConnectorConfigValues entity) {

	if (isDeleteAllowed(entity)) {
	    this.payConnectorConfigValuesDAO.delete(entity);
	}
    }

    @Override
    public List<PayConnectorConfigValues> findAll(Integer firstResult, Integer maxResult) {

	return this.payConnectorConfigValuesDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayConnectorConfigValues findById(PkId id) {

	return this.payConnectorConfigValuesDAO.findById(id);
    }

    @Override
    protected Class<PayConnectorConfigValues> getEntityClass() {

	return PayConnectorConfigValues.class;
    }

    /**
     * se manca la configurazione specifica di deafault il nodo pagamenti salva i documenti su campi BLOB
     */
    @Override
    public boolean isDocumentiSuFilesystem() {

	String fsConfigParamValue = this.payConnectorConfigValuesDAO.getValoreParametroConfigurazione(ConfigParamNames.DOCUMENTI_SU_FILESYSTEM);
	return StringUtils.isNotBlank(fsConfigParamValue);
    }

    /**
     * restituisce l'oggetto {@link File} che rappresenta il percorso in cui il nodo pagamenti salva i documenti se
     * configurato il parametro DOCUMENTI_SU_FILESYSTEM
     * 
     * @throws IOException
     * @throws PayConfigurationException
     */
    @Override
    public String getDocumentiSuFilesystemPath() {

	String fsConfigParamValue = this.payConnectorConfigValuesDAO.getValoreParametroConfigurazione(ConfigParamNames.DOCUMENTI_SU_FILESYSTEM);
	if (StringUtils.isNotBlank(fsConfigParamValue)) {
	    return fsConfigParamValue;
	}
	return null;
    }

    @Override
    public String getValoreParametroConfigurazione(ConfigParamNames paramName) {

	return this.payConnectorConfigValuesDAO.getValoreParametroConfigurazione(paramName);
    }

    @Override
    public String getValoreParametroConfigurazione(ConfigParamNames paramName, String connectorCode) {

	return this.payConnectorConfigValuesDAO.getValoreParametroConfigurazione(paramName, connectorCode);
    }

    @Override
    public List<PayConnectorConfigValuesHelper> getConnectorConfigValuesFromCodiceConnettore(String connectorCode) {

	return this.payConnectorConfigValuesDAO.getConnectorConfigValuesFromCodiceConnettore(connectorCode);
    }
}
