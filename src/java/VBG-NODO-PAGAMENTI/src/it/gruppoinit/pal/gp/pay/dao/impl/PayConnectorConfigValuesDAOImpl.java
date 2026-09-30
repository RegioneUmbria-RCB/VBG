package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.pay.dao.PayConnectorConfigValuesDAO;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigValues;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.features.interfaccia.PayConnectorConfigValuesHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;

@Repository
public class PayConnectorConfigValuesDAOImpl extends BaseDAOImpl<PayConnectorConfigValues, PkId> implements PayConnectorConfigValuesDAO {

    private static final Logger log = LoggerFactory.getLogger(PayConnectorConfigValuesDAOImpl.class);

    @Override
    public Class<PayConnectorConfigValues> getEntityClass() {

	return PayConnectorConfigValues.class;
    }

    @Override
    public String getValoreParametroConfigurazione(ConfigParamNames paramName) {

	String connCode = null;
	PayProfiliEntiCreditori prof = PayConfigurationHelper.getProfiloEnteCreditore();
	if (prof != null) {
	    connCode = prof.getPayConnector().getCodice();
	} else {
	    log.warn(
		    "getValoreParametroConfigurazione - profilo ente creditore corrente non impostato. Sarà restiuto solo il valore di default valido per tutti i connettori");
	}
	return this.getValoreParametroConfigurazione(paramName, connCode);
    }

    @Override
    public String getValoreParametroConfigurazione(ConfigParamNames paramName, String connectorCode) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (paramName == null) {
	    throw new IllegalArgumentException("il parametro di configurazione da cercare non può essere nullo");
	}
	fr.addFilterField(FilterUtils.equals("configParam", paramName.name(), "configParam", String.class));
	ft.addRestriction(fr);
	fr = new FilterRestriction();
	if (StringUtils.isNotBlank(connectorCode)) {
	    fr.addFilterField(FilterUtils.equals("connettore.codice", connectorCode, String.class));
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	}
	fr.addFilterField(FilterUtils.isNull("connettore.codice"));
	ft.addRestriction(fr);
	List<PayConnectorConfigValues> values = this.findByFilterTable(ft);
	String val = null;
	String defaultVal = null;
	for (PayConnectorConfigValues ccv : values) {
	    if (ccv.getConfigParam().getConfigParam().equalsIgnoreCase(paramName.name())) {
		val = ccv.getValore();
	    } else if (ccv.getConfigParam().getConfigParam() == null) {
		defaultVal = ccv.getValore();
	    }
	}
	return val != null ? val : defaultVal;
    }

    @Override
    public List<PayConnectorConfigValuesHelper> getConnectorConfigValuesFromCodiceConnettore(String connectorCode) {

	String query = " select" + //
		       " pccv.ID as id," + //
		       " pccv.IDCOMUNE as idcomune ," + //
		       " pccv.CONFIG_PARAM as configparam," + //
		       " pccp.DESCRIZIONE as descrizione," + //
		       " pccv.VALORE as valore," + //
		       " pccv.CODICE_CONNETTORE as codiceconnettore" + //
		       " from" + //
		       " pay_connector_config_values pccv" + //
		       " inner join pay_connector_config_params pccp on" + //
		       " pccv.CONFIG_PARAM = pccp.CONFIG_PARAM" + //
		       " where" + //
		       " pccv.CODICE_CONNETTORE = ?";
	SQLQuery q = currentSession().createSQLQuery(query);
	q.addScalar("id", IntegerType.INSTANCE);
	q.addScalar("idcomune", StringType.INSTANCE);
	q.addScalar("configparam", StringType.INSTANCE);
	q.addScalar("valore", StringType.INSTANCE);
	q.addScalar("descrizione", StringType.INSTANCE);
	q.addScalar("codiceconnettore", StringType.INSTANCE);
	q.setString(0, connectorCode);
	q.setResultTransformer(Transformers.aliasToBean(PayConnectorConfigValuesHelper.class));
	return q.list();
    }
}
