package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.sigepro.cart.service.helper.CartServiceConfigurationParameters;
import it.gruppoinit.sigepro.cart.service.helper.ICartParametersLoader;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerticalizzazioniCartParametersLoader implements ICartParametersLoader {

    private static final Logger log = LoggerFactory.getLogger(VerticalizzazioniCartParametersLoader.class);
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService vertService) {

	this.verticalizzazioniService = vertService;
    }

    @Override
    public CartServiceConfigurationParameters loadConfigurationParameters() {

	CartServiceConfigurationParameters cartGlobalConfig = new CartServiceConfigurationParameters();
	//	Verticalizzazioniparametri verticalizzazioniparametriAZIONI = verticalizzazioniService.getVerticalizzazioniparametri(
	//		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_AZIONI);
	//	if (verticalizzazioniparametriAZIONI == null) {
	//	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_ALBEROPROC_ENDO.FKAZID");
	//	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_ALBEROPROC_ENDO.FKAZID");
	//	}
	//	cartGlobalConfig.setAzioni(verticalizzazioniparametriAZIONI.getValore());
	// CODICE NATURA
	Verticalizzazioniparametri verticalizzazioniparametriCODICENATURA = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_CODICENATURA);
	if (verticalizzazioniparametriCODICENATURA == null) {
	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_INVENTARIOPROC.CODNATURA");
	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_INVENTARIOPROC.CODNATURA");
	}
	cartGlobalConfig.setCodiceNatura(verticalizzazioniparametriCODICENATURA.getValore());
	// famiglia endo dei procedimenti di tipo 2
	Verticalizzazioniparametri verticalizzazioniparametriFAMIGLIAENDO = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_FAMIGLIAENDO);
	if (verticalizzazioniparametriFAMIGLIAENDO == null) {
	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_TIPIFAMIGLIEENDO.CODICE");
	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_TIPIFAMIGLIEENDO.CODICE");
	}
	cartGlobalConfig.setFamigliaEndo(verticalizzazioniparametriFAMIGLIAENDO.getValore());
	// RECUPERO PARAMETRI VERTICALIZZAZIONE CART VERIFICO SE E' ABILITATA LA GESTIONE DEGLI
	// INVENTARIPROCEDIMENTI.
	Verticalizzazioniparametri verticalizzazioniparametriINVENTARIO = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_INVENTARIO);
	if (verticalizzazioniparametriINVENTARIO == null) {
	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_INVENTARIOPROCEDIMENTI");
	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_INVENTARIOPROCEDIMENTI");
	}
	cartGlobalConfig.setInventarioProcedimenti(verticalizzazioniparametriINVENTARIO.getValore());
	// root alberoproc
	Verticalizzazioniparametri verticalizzazioniparametriROOTALBEROPROC = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC);
	if (verticalizzazioniparametriROOTALBEROPROC == null) {
	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ROOT_ALBEROPROC_ID");
	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ROOT_ALBEROPROC_ID");
	}
	cartGlobalConfig.setRootAlberoproc(verticalizzazioniparametriROOTALBEROPROC.getValore());
	// SUAP_ID
	Verticalizzazioniparametri verticalizzazioniparametriSUAP_ID = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SUAP_ID);
	if (verticalizzazioniparametriSUAP_ID == null) {
	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SUAP_ID");
	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO VERTICALIZZAZIONE_CART_SUAP_ID");
	}
	cartGlobalConfig.setSuapId(verticalizzazioniparametriSUAP_ID.getValore());
	// TIPI MOVIMENTO
	// NPE 
	//	Verticalizzazioniparametri verticalizzazioniparametriTIPIMOVIMENTO = verticalizzazioniService.getVerticalizzazioniparametri(
	//		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO);
	//	if (verticalizzazioniparametriTIPIMOVIMENTO != null) {
	//	    cartGlobalConfig.setTipiMovimento(verticalizzazioniparametriTIPIMOVIMENTO.getValore());
	//	} else {
	//	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO " + WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO);
	//	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO " + WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO);
	//	}
	//	 	tempificazione
	//	Verticalizzazioniparametri verticalizzazioniparametriTEMPIFICAZIONE = verticalizzazioniService.getVerticalizzazioniparametri(
	//		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TEMPIFICAZIONE);
	//	if (verticalizzazioniparametriTEMPIFICAZIONE == null) {
	//	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TEMPIFICAZIONE");
	//	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TEMPIFICAZIONE");
	//	}
	//	cartGlobalConfig.setTempificazione(verticalizzazioniparametriTEMPIFICAZIONE.getValore());
	//	Verticalizzazioniparametri verticalizzazioniparametriAMMINISTRAZIONE = verticalizzazioniService.getVerticalizzazioniparametri(
	//		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_AMMINISTRAZIONE);
	//	if (verticalizzazioniparametriAMMINISTRAZIONE == null) {
	//	    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_INVENTARIOPROC.AMMIN");
	//	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_INVENTARIOPROC.AMMIN");
	//	}
	//	cartGlobalConfig.setAmministrazione(verticalizzazioniparametriAMMINISTRAZIONE.getValore());
	// verticalizzazione proxyabilitato
	Verticalizzazioniparametri paramProxyEnabled = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_PROXY_ENABLED);
	String proxyEnabled = "false";
	if (null != paramProxyEnabled) {
	    proxyEnabled = paramProxyEnabled.getValore() == null ? "false" : paramProxyEnabled.getValore();
	}
	cartGlobalConfig.setProxyEnabled(BooleanUtils.toBoolean(proxyEnabled));
	// verticalizzazione proxyHost = "";
	Verticalizzazioniparametri paramProxyHost = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_PROXY_HOST);
	String proxyHost = "";
	if (null != paramProxyHost) {
	    proxyHost = paramProxyHost.getValore() == null ? "" : paramProxyHost.getValore();
	}
	cartGlobalConfig.setProxyHost(proxyHost);
	// verticalizzazione  proxyPort = 0;
	Verticalizzazioniparametri paramProxyPort = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_PROXY_PORT);
	String proxyPort = "";
	if (null != paramProxyPort) {
	    proxyPort = paramProxyPort.getValore() == null ? "" : paramProxyPort.getValore();
	}
	cartGlobalConfig.setProxyPort(proxyPort);
	// verticalizzazione  proxyUserName = "";
	Verticalizzazioniparametri paramProxyUserName = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_PROXY_USER_NAME);
	String proxyUserName = "";
	if (null != paramProxyUserName) {
	    proxyUserName = paramProxyUserName.getValore() == null ? "" : paramProxyUserName.getValore();
	}
	cartGlobalConfig.setProxyUsername(proxyUserName);
	// verticalizzazione  proxyPwd = "";
	Verticalizzazioniparametri paramProxyPassword = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_PROXY_PASSWORD);
	String proxyPassword = "";
	if (null != paramProxyPassword) {
	    proxyPassword = paramProxyPassword.getValore() == null ? "" : paramProxyPassword.getValore();
	}
	cartGlobalConfig.setProxyPassword(proxyPassword);
	// verticalizzazione NUOVA_DOMANDA.ID_TIPOPROCEDURA
	//	Verticalizzazioniparametri paramCodiceTipoProcedura = verticalizzazioniService.getVerticalizzazioniparametri(
	//		WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_CODICETIPOPROCEDURA_NUOVEISTANZE);
	//	Integer codiceTipoProcedura = null;
	//	if (null != paramCodiceTipoProcedura && paramCodiceTipoProcedura.getValore() != null) {
	//	    try {
	//		codiceTipoProcedura = new Integer(paramCodiceTipoProcedura.getValore());
	//		cartGlobalConfig.setCodiceTipoProceduraNuoveIstanze(codiceTipoProcedura);
	//	    } catch (NumberFormatException e) {
	//		log.error("Il valore del parametro {} della verticalizzazione CART non è in un formato numerico valido. Valore trovato: {}",
	//			new Object[] { WebConstants.VERTICALIZZAZIONE_CART_CODICETIPOPROCEDURA_NUOVEISTANZE, paramCodiceTipoProcedura.getValore() });
	//		throw new RuntimeException(
	//			"VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO NUMERICO NUOVA_DOMANDA.ID_TIPOPROCEDURA NON VALIDO, VALORE: "
	//				+ paramCodiceTipoProcedura.getValore());
	//	    }
	//	}
	// verticalizzazione NUOVA_DOMANDA.ID_TIPOPROCEDURA
	Verticalizzazioniparametri paramTimeoutWsCall = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_TIMEOUTWSCALL);
	Integer timeout = null;
	if (null != paramTimeoutWsCall && paramTimeoutWsCall.getValore() != null) {
	    if (StringUtils.isNotBlank(paramTimeoutWsCall.getValore())) {
		try {
		    timeout = new Integer(paramTimeoutWsCall.getValore());
		    cartGlobalConfig.setTimeoutMilliseconds(timeout.intValue());
		} catch (NumberFormatException e) {
		    log.error("Il valore del parametro {} della verticalizzazione CART non è in un formato numerico valido. Valore trovato: {}",
			    new Object[] { WebConstants.VERTICALIZZAZIONE_CART_TIMEOUTWSCALL, paramTimeoutWsCall.getValore() });
		    throw new RuntimeException("VERTICALIZZAZIONE CART CONFIGURATA NON CORRETTAMENTE: PARAMETRO NUMERICO "
			    + WebConstants.VERTICALIZZAZIONE_CART_TIMEOUTWSCALL + " NON VALIDO, VALORE: " + paramTimeoutWsCall.getValore());
		}
	    }
	}
	return cartGlobalConfig;
    }
}
