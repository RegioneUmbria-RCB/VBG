package it.gruppoinit.pal.gp.core.features.oneri.messaggi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioOnereCopiatoDaIstanza extends MessaggioDiSistema {

    private String numeroIstanza;
    private Date data;

    public MessaggioOnereCopiatoDaIstanza(String numeroIstanza) {

	this(numeroIstanza, Calendar.getInstance().getTime());
    }

    public MessaggioOnereCopiatoDaIstanza(String numeroIstanza, Date data) {

	if (StringUtils.isEmpty(numeroIstanza)) {
	    throw new IllegalArgumentException("Numero istanza non specificato");
	}
	if (data == null) {
	    throw new IllegalArgumentException("Data non specificata");
	}
	this.numeroIstanza = numeroIstanza;
	this.data = data;
    }

    @Override
    public String getTestoMessaggio() {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String dataFormattata = sdf.format(this.data);
	return "Oneri copiati dalla pratica " + this.numeroIstanza + " in data " + dataFormattata;
    }
}
