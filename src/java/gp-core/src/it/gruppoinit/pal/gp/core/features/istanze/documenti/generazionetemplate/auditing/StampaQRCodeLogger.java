package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.auditing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StampaQRCodeLogger {

    public static final Logger logger = LoggerFactory.getLogger("qrcode");
    private String messaggio;

    public StampaQRCodeLogger(String url) {

	this.messaggio = this.generaMessaggio(url);
    }

    private String generaMessaggio(String url) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("Lettura del QR code da un documento protocollato");
	sb.append("\nUrl chiamato: ").append(url);
	sb.append("\n").append("====================");
	return sb.toString();
    }

    public void log() {

	StringBuilder sb = new StringBuilder();
	sb.append(messaggio);
	logger.info(sb.toString());
    }
}
