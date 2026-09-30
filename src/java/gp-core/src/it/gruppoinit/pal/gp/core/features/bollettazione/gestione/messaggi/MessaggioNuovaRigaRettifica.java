package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi;

public class MessaggioNuovaRigaRettifica extends MessaggioBollettazione {

    String autore;
    String descrizioneVecchiaRiga;

    public MessaggioNuovaRigaRettifica(String autore, String descrizioneVecchiaRiga) {

	super();
	this.autore = autore;
	this.descrizioneVecchiaRiga = descrizioneVecchiaRiga;
    }

    @Override
    public String getTestoMessaggio() {

	return "Rettifica della riga \"" + descrizioneVecchiaRiga + "\" effettuata da " + autore + " in data " + getStringaData();
    }
}
