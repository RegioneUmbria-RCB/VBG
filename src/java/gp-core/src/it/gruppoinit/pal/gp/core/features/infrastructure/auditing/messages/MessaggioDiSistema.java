package it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class MessaggioDiSistema implements IMessaggioDiSistema {

    private Date dataLog;

    @Override
    public void sovrascriviDataLog(Date dataLog) {

	this.dataLog = dataLog;
    }

    @Override
    public String getStringaData() {

	Date dataAttuale = new Date();
	if (this.dataLog != null) {
	    dataAttuale = this.dataLog;
	}
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	return format.format(dataAttuale);
    }

    @Override
    public abstract String getTestoMessaggio();

    @Override
    public String toString() {

	return getTestoMessaggio();
    }
}
