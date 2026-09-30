package it.alveo.firmaremota.aruba.configurazione;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.alveo.firmaremota.aruba.BaseDelegate;
import it.alveo.firmaremota.aruba.CryptoUtils;
import it.alveo.firmaremota.aruba.api.ConfigurazioneApiDelegate;
import it.alveo.firmaremota.aruba.configurazione.params.CertIdParam;
import it.alveo.firmaremota.aruba.configurazione.params.DetachedParam;
import it.alveo.firmaremota.aruba.configurazione.params.EmailNotificaParam;
import it.alveo.firmaremota.aruba.configurazione.params.FirmaCongiuntaCadesParam;
import it.alveo.firmaremota.aruba.configurazione.params.GeneraOTPParam;
import it.alveo.firmaremota.aruba.configurazione.params.MarcaTemporaleRichiestaParam;
import it.alveo.firmaremota.aruba.configurazione.params.MotivoFirmaPadesParam;
import it.alveo.firmaremota.aruba.configurazione.params.NumPaginaFirmaPadesParam;
import it.alveo.firmaremota.aruba.configurazione.params.OTPParam;
import it.alveo.firmaremota.aruba.configurazione.params.PasswordParam;
import it.alveo.firmaremota.aruba.configurazione.params.PosRettFirmLeftPadesParam;
import it.alveo.firmaremota.aruba.configurazione.params.PosRettFirmRightPadesParam;
import it.alveo.firmaremota.aruba.configurazione.params.ProfiloFirmaPadesParam;
import it.alveo.firmaremota.aruba.configurazione.params.ProfiloFirmaParam;
import it.alveo.firmaremota.aruba.configurazione.params.RelaxSSLParam;
import it.alveo.firmaremota.aruba.configurazione.params.ReturnDerParam;
import it.alveo.firmaremota.aruba.configurazione.params.TestoFirmaPadesParam;
import it.alveo.firmaremota.aruba.configurazione.params.TipoFirmaParam;
import it.alveo.firmaremota.aruba.configurazione.params.TypeHSMParam;
import it.alveo.firmaremota.aruba.configurazione.params.TypeSendOTPParam;
import it.alveo.firmaremota.aruba.configurazione.params.UrlServizioParam;
import it.alveo.firmaremota.aruba.configurazione.params.UserNameParam;
import it.alveo.firmaremota.aruba.model.Configurazione;
import it.alveo.firmaremota.aruba.model.Param;

@Service
public class ConfigurazioneService extends BaseDelegate implements ConfigurazioneApiDelegate {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurazioneService.class);

    @Override
    public ResponseEntity<List<Param>> configurazioneGet() {

	logger.debug("Richiesta parametri di configurazione ");
	return new ResponseEntity<>(getParams(), HttpStatus.OK);
    }

    public ConfigurazioneBean getConfigurazione(String sessionId, Configurazione request) {

	ConfigurazioneBean config = new ConfigurazioneBean();
	config.setSessionId(sessionId);
	config.setUrl(this.getParam(request.getParams(), UrlServizioParam.newParam().getChiave()).getValore());
	config.setTypeHSM(this.getParam(request.getParams(), TypeHSMParam.newParam().getChiave()).getValore());
	config.setProfiloFirma(this.getParam(request.getParams(), ProfiloFirmaParam.newParam().getChiave()).getValore());
	config.setUsername(this.getParam(request.getParams(), UserNameParam.newParam().getChiave()).getValore());
	String pwd = CryptoUtils.encrypt(sessionId, this.getParam(request.getParams(), PasswordParam.newParam().getChiave()).getValore());
	config.setPassword(pwd);
	config.setRelaxSSL("1".equals(this.getParam(request.getParams(), RelaxSSLParam.newParam().getChiave()).getValore()));
	config.setTypeSendOTP(this.getParam(request.getParams(), TypeSendOTPParam.newParam().getChiave()).getValore());
	config.setRichiediOTP("1".equals(this.getParam(request.getParams(), GeneraOTPParam.newParam().getChiave()).getValore()));
	config.setDetached("1".equals(this.getParam(request.getParams(), DetachedParam.newParam().getChiave()).getValore()));
	config.setOtp(this.getParam(request.getParams(), OTPParam.newParam().getChiave()).getValore());
	config.setCertId(this.getParam(request.getParams(), CertIdParam.newParam().getChiave()).getValore());
	config.setReturnDer("1".equals(this.getParam(request.getParams(), ReturnDerParam.newParam().getChiave()).getValore()));
	config.setPosRettFirmLeftPades(this.getParam(request.getParams(), PosRettFirmLeftPadesParam.newParam().getChiave()).getValore());
	config.setPosRettFirmRightPades(this.getParam(request.getParams(), PosRettFirmRightPadesParam.newParam().getChiave()).getValore());
	config.setTestoFirmaPades(this.getParam(request.getParams(), TestoFirmaPadesParam.newParam().getChiave()).getValore());
	config.setProfiloFirmaPades(this.getParam(request.getParams(), ProfiloFirmaPadesParam.newParam().getChiave()).getValore());
	String numPagina = this.getParam(request.getParams(), NumPaginaFirmaPadesParam.newParam().getChiave()).getValore();
	config.setNumPaginaFirmaPades(StringUtils.isBlank(numPagina) ? null : Integer.valueOf(numPagina));
	config.setEmailNotifica(this.getParam(request.getParams(), EmailNotificaParam.newParam().getChiave()).getValore());
	config.setFirmaCongiuntaCades(
		"SI".equalsIgnoreCase(this.getParam(request.getParams(), FirmaCongiuntaCadesParam.newParam().getChiave()).getValore()));
	config.setMarcaTemporaleRichiesta(
		"1".equals(this.getParam(request.getParams(), MarcaTemporaleRichiestaParam.newParam().getChiave()).getValore()));
	config.setMotivoFirmaPades(this.getParam(request.getParams(), MotivoFirmaPadesParam.newParam().getChiave()).getValore());
	config.setTipoFirma(this.getParam(request.getParams(), TipoFirmaParam.newParam().getChiave()).getValore());
	return config;
    }

    private List<Param> getParams() {

	var params = new ArrayList<Param>();
	params.add(CertIdParam.newParam().toParam());
	params.add(DetachedParam.newParam().toParam());
	params.add(EmailNotificaParam.newParam().toParam());
	params.add(FirmaCongiuntaCadesParam.newParam().toParam());
	params.add(GeneraOTPParam.newParam().toParam());
	params.add(MarcaTemporaleRichiestaParam.newParam().toParam());
	params.add(NumPaginaFirmaPadesParam.newParam().toParam());
	params.add(OTPParam.newParam().toParam());
	params.add(PasswordParam.newParam().toParam());
	params.add(PosRettFirmLeftPadesParam.newParam().toParam());
	params.add(PosRettFirmRightPadesParam.newParam().toParam());
	params.add(ProfiloFirmaPadesParam.newParam().toParam());
	params.add(ProfiloFirmaParam.newParam().toParam());
	params.add(MotivoFirmaPadesParam.newParam().toParam());
	params.add(RelaxSSLParam.newParam().toParam());
	params.add(ReturnDerParam.newParam().toParam());
	params.add(TestoFirmaPadesParam.newParam().toParam());
	params.add(TipoFirmaParam.newParam().toParam());
	params.add(TypeHSMParam.newParam().toParam());
	params.add(TypeSendOTPParam.newParam().toParam());
	params.add(UrlServizioParam.newParam().toParam());
	params.add(UserNameParam.newParam().toParam());
	return params;
    }

    private Param getParam(List<Param> params, String paramName) {

	if (StringUtils.isBlank(paramName)) {
	    throw new IllegalArgumentException("Non è stato passato il parametro da ricercare");
	}
	if (params == null || params.isEmpty()) {
	    return new Param();
	}
	return params.stream().filter(p -> p.getChiave().equalsIgnoreCase(paramName)).findFirst().orElse(new Param());
    }
}
