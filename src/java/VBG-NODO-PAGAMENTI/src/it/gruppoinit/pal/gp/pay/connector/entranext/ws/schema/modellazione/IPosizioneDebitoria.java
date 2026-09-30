package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

public interface IPosizioneDebitoria {

    void setAnnoImposta(int anno);

    void setAnnullato(boolean annullato);

    void setContribuente(Contribuente contribuente);
}