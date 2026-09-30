package it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages;

import java.util.Date;

public interface IMessaggioDiSistema {

    String getTestoMessaggio();

    String toString();

    String getStringaData();

    void sovrascriviDataLog(Date dataLog);
}
