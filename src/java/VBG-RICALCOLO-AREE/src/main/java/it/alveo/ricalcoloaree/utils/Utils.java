package it.alveo.ricalcoloaree.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

public class Utils {

    public static final String STANDARD_ERROR_TXT = "Parametro di configurazione non valorizzato nella configurazione E256-TT";

    public static boolean isNullOrEmpty(String value) {

        return value == null || value.trim().isEmpty();
    }

    public static WebServiceTemplate getSecurityWebServiceTemplate(ApplicationContext applicationContext, String securityUrl) {

        return (WebServiceTemplate) applicationContext.getBean("securityFileWebServiceTemplate", securityUrl);
    }
}
