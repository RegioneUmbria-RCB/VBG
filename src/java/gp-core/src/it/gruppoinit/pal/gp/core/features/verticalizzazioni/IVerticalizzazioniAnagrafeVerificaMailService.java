package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;

public interface IVerticalizzazioniAnagrafeVerificaMailService {

    boolean isAttiva();

    MailConfig codiceVerificaMailAccountId();

    Mailtipo codiceVerificaMailEmailTipo();
}
