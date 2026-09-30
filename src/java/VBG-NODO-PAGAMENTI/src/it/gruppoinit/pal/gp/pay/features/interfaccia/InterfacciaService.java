package it.gruppoinit.pal.gp.pay.features.interfaccia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.dao.PayConnectorConfigDAO;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.PayRegcausaliParametriService;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniCausaliService;

@Service
public class InterfacciaService {

    private static final String IT_GRUPPOINIT_PAL_GP_PAY_CONNECTOR = "it.gruppoinit.pal.gp.pay.connector";
    private PayConnectorConfigValuesService configValuesService;
    private PayRegistrazioniCausaliService payRegistrazioniCausaliService;
    private PayConnectorConfigDAO payConnectorConfigDAO;
    private PayProfiliEntiCreditoriService payProfiliEntiCreditoriService;
    private PayRegcausaliParametriService payRegcausaliParametriService;

    @Autowired
    public void setPayRegcausaliParametriService(PayRegcausaliParametriService payRegcausaliParametriService) {

	this.payRegcausaliParametriService = payRegcausaliParametriService;
    }

    @Autowired
    public void setPayProfiliEntiCreditoriService(PayProfiliEntiCreditoriService payProfiliEntiCreditoriService) {

	this.payProfiliEntiCreditoriService = payProfiliEntiCreditoriService;
    }

    @Autowired
    public void setPayRegistrazioniCausaliService(PayRegistrazioniCausaliService payRegistrazioniCausaliService) {

	this.payRegistrazioniCausaliService = payRegistrazioniCausaliService;
    }

    @Autowired
    public void setConfigValuesService(PayConnectorConfigValuesService configValuesService) {

	this.configValuesService = configValuesService;
    }

    @Autowired
    public void setPayConnectorConfigDAO(PayConnectorConfigDAO payConnectorConfigDAO) {

	this.payConnectorConfigDAO = payConnectorConfigDAO;
    }

    public List<String> getClasses() {

	List<String> classes = new ArrayList<>();
	BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
	ClassPathBeanDefinitionScanner s = new ClassPathBeanDefinitionScanner(bdr);
	TypeFilter tf = new AssignableTypeFilter(IPayConnector.class);
	s.addIncludeFilter(tf);
	s.scan(IT_GRUPPOINIT_PAL_GP_PAY_CONNECTOR);
	s.setIncludeAnnotationConfig(false);
	String[] beans = bdr.getBeanDefinitionNames();
	List<String> list = Arrays.asList(beans);
	for (String string : list) {
	    if (string.contains("Connector")) {
		classes.add(string);
	    }
	}
	return classes;
    }

    public ConnettoriBean findConnettoriClient() throws PayConfigurationException {

	ConnettoriBean ret = new ConnettoriBean();
	List<String> classes = this.getClasses();
	for (String s : classes) {
	    ConnettoreBean connettore;
	    List<ConnectorConfigHelper> helper = payProfiliEntiCreditoriService.findConfigHelperByJavaclass(s);
	    for (ConnectorConfigHelper h : helper) {
		connettore = new ConnettoreBean(h.getJavaClass(), h.getDescrizione());
		PayConnectorConfig pcc = payConnectorConfigDAO.findById(h.getCodice());
		//campi_gestiti
		CampiGestitiBean gestitiBean = new CampiGestitiBean();
		PayProfiliEntiCreditori ppec = payProfiliEntiCreditoriService.findByCfCodiceProfilo(h.getCfcodprofilo());
		gestitiBean.setPayConnectorConfig(PayConnectorConfigBean.popolaPayConnectorConfigBean(pcc));
		gestitiBean.setPayProfiliEntiCreditori(PayProfiliEntiCreditoriBean.popolaPayProfiliEntiCreditoriBean(ppec));
		connettore.setCampiGestiti(gestitiBean);
		//ws_endpoint
		WsEndpointBean wsEndpoint = WsEndpointBean.popolaWsEndpoint(pcc);
		if (wsEndpoint != null) {
		    connettore.setWsEndpoint(wsEndpoint);
		}
		//parametri_connettore
		List<PayConnectorConfigValuesHelper> pccv = configValuesService.getConnectorConfigValuesFromCodiceConnettore(h.getCodice());
		List<ParametriCDH> parametroConnettore = ParametriCDH.fromPayConnectorConfigValuesHelper(pccv);
		if (!parametroConnettore.isEmpty()) {
		    connettore.setParametriConnettore(parametroConnettore);
		}
		//parametri_causale
		popolaParametriCausale(connettore, h);
		ret.getConnettore().add(connettore);
	    }
	}
	return ret;
    }

    private void popolaParametriCausale(ConnettoreBean connettore, ConnectorConfigHelper h) {

	List<InfoCausaliParam> l = payRegistrazioniCausaliService.findInfoCausaliRidottePerConnettore(h.getCfcodprofilo());
	List<ParametriCDH> parametriCausale;
	Map<String, ParametriCDH> m = new HashMap<>();
	for (InfoCausaliParam c : l) {
	    // Recupero l'idcomune settato nell'ORMHELPER dal chiamante 
	    String idcomuneOrig = ORMHelper.getIdcomune();
	    // setto l'idcomune del recor della causale
	    ORMHelper.setIdcomune(c.getIdcomune());
	    List<PayRegcausaliParametri> regCausaliParams = payRegcausaliParametriService.findByCausale(c.getId());
	    if (!regCausaliParams.isEmpty()) {
		parametriCausale = ParametriCDH.fromPayRegistrazioniCausali(regCausaliParams);
		for (ParametriCDH p : parametriCausale) {
		    m.put(p.getChiave(), p);
		}
	    }
	    ORMHelper.setIdcomune(idcomuneOrig);
	}
	parametriCausale = new ArrayList<>();
	for (Entry<String, ParametriCDH> v : m.entrySet()) {
	    parametriCausale.add(v.getValue());
	}
	if (!parametriCausale.isEmpty()) {
	    connettore.setParametriCausale(parametriCausale);
	}
    }
}
