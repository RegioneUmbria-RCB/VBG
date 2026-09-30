package it.gruppoinit.pal.gp.core.features.alfresco.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Component
public class VerticalizzazioneNodoAlfrescoAPIServiceImpl implements IVerticalizzazioneNodoAlfrescoAPIService {

    private static final String DEFAULT_CM_CONTENT = "cm:content";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO = "FILESYSTEM_API_ALFRESCO";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_URL = "ALFRESCO_API_URL";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_USR = "ALFRESCO_API_USR";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_PWD = "ALFRESCO_API_PWD";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_CON_TIMEOUT = "CONNECT_TIMEOUT";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_READ_TIMEOUT = "READ_TIMEOUT";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_ALFRESCO_SOLA_LETTURA = "ALFRESCO_SOLA_LETTURA";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_DOCUMENT_ROOT_FOLDER = "ALFRESCO_DOCUMENT_ROOT_FOLDER";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_ROOT_RELATIVE_PATH = "ALFRESCO_ROOT_RELATIVE_PATH";
    private static final String VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_DOCUMENT_CONTENT_NAME = "ALFRESCO_DOCUMENT_CONTENT_NAME";
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @Override
    public String getAlfrescoApiUrl() {

	return getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_URL, true);
    }

    @Override
    public String getAlfrescoApiUser() {

	return getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_USR, true);
    }

    @Override
    public String getAlfrescoApiPwd() {

	return getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_PWD, true);
    }

    @Override
    public int getAlfrescoApiConnTimeout() {

	String v = getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_CON_TIMEOUT, false);
	if (Utilities.isInteger(v)) {
	    return Integer.parseInt(v);
	}
	return 20000;
    }

    @Override
    public int getAlfrescoApiReadTimeout() {

	String v = getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_READ_TIMEOUT, false);
	if (Utilities.isInteger(v)) {
	    return Integer.parseInt(v);
	}
	return 300000;
    }

    @Override
    public String getAlfrescoDocumentRootFolder() {

	return getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_DOCUMENT_ROOT_FOLDER, true);
    }

    @Override
    public String getAlfrescoRootRelativePath() {

	return getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_ROOT_RELATIVE_PATH, true);
    }

    @Override
    public boolean isAttiva() {

	return verticalizzazioniService.isAttiva(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO);
    }

    @Override
    public String alfrescoDocumentContentName() {

	return StringUtils.defaultString(getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_DOCUMENT_CONTENT_NAME, false),
		DEFAULT_CM_CONTENT);
    }

    private String getValoreFromVerticalizzazione(String parametro, boolean rilanciaEccezione) {

	String valore = verticalizzazioniService.getVerticalizzazioniparametriValore(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO, parametro);
	if (StringUtils.isBlank(valore) && rilanciaEccezione) {
	    throw new InvalidConfigurationException("Il parametro " + parametro + //
						    " della regola " + //
						    VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO + //
						    " non e' stato impostato correttamente.");
	}
	return valore;
    }

    @Override
    public boolean solaLettura() {

	return StringUtils.defaultIfEmpty(getValoreFromVerticalizzazione(VERTICALIZZAZIONE_FILESYSTEM_API_ALFRESCO_ALFRESCO_SOLA_LETTURA, false), "N")
		.equalsIgnoreCase("S");
    }
}
