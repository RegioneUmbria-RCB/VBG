package it.gruppoinit.pal.gp.core.features.sistema;

import org.apache.commons.lang.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;

@Service
public class UrlGeneraAllegatoMicrosoftServiceImpl implements IUrlGeneraAllegatoMicrosoftService {

    private IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService;
    
    @Autowired
    public void setVerticalizzazioneParametriSistemaService(IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService) {

	this.verticalizzazioneParametriSistemaService = verticalizzazioneParametriSistemaService;
    }
    
    
    
    @Override
    public String getUrl( ) {
	String urlGeneraAllegato = "";

	if( this.verticalizzazioneParametriSistemaService.isAttiva() ) {
	    String overrideUrlGeneraAllegato = this.verticalizzazioneParametriSistemaService.overrideUrlGeneraAllegato();
	    if (StringUtils.isNotBlank(overrideUrlGeneraAllegato)) {
		urlGeneraAllegato = overrideUrlGeneraAllegato.trim();
		if( urlGeneraAllegato.toLowerCase().startsWith("http") ) {
		    return urlGeneraAllegato;
		}
	    }
	}
	
	if(StringUtils.isBlank(urlGeneraAllegato)) {
	    urlGeneraAllegato = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.APP_ASP) + "/GeneraAllegato.asp";
	}
	
	String baseUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.BASE_URL);
	if( !StringUtils.isBlank(baseUrl) ) {
	    return baseUrl + '/' + urlGeneraAllegato;
	}
	
	urlGeneraAllegato = this.getBaseUrlFromAspnetBaseURL() +  urlGeneraAllegato;
	
	if( !urlGeneraAllegato.toLowerCase().startsWith("http") ) {
	    throw new InvalidArgumentException("Non è stato possibile ricavare la url completa per la generazione degli allegati tramite tecnologia MICROSOFT. Url ricavato: " + urlGeneraAllegato);
	}
	
	return urlGeneraAllegato;
    }
    
    private String getBaseUrlFromAspnetBaseURL() {

	String result = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET);
	String aspNetApp = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.APP_ASPNET);
	if (StringUtils.isNotBlank(result)) {
	    if (StringUtils.isNotBlank(aspNetApp)) {
		result = result.replaceAll("/" + aspNetApp, "/");
	    }
	    return result;
	}
	return "";
    }
}
