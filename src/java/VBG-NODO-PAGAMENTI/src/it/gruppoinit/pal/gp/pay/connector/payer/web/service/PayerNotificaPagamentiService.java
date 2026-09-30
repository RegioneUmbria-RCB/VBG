package it.gruppoinit.pal.gp.pay.connector.payer.web.service;

public interface PayerNotificaPagamentiService {

	void provaAsync();
	
	void avviaSincronizzazionePerCf(String cfEnteCreditore, String cfDebitore);
	
}
