package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.helper.EmailHelper;
import it.gruppoinit.pal.gp.core.helper.TIPO_MANIFESTAZIONE;

public interface MailService {

    public boolean sendEmail(String oggetto, String corpo);

    public EmailHelper pupolateEmail(Integer codiceManifestazione, TIPO_MANIFESTAZIONE tipo_MANIFESTAZIONE);
}
