package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.io.InputStream;

public interface BollettazioneLettereService {

    public InputStream generaLetteraAccompagnamento(String cf_ente_creditore, Integer rifIdPosizioneDebitoria, boolean convertiInPdf);

    /**
     * 
     * @param idRigaDettaglioBollettazione
     * @param codiceLettera
     * @param convertiInPdf
     * @return il codice oggetto inserito
     */
    public int generaOggettoPerDettaglioMassiva(int idRigaDettaglioMassiva, int codiceLettera, boolean convertiInPdf);

    public LetteraGenerataPerComunicazione generaLetteraPerDettaglioMassiva(int idRigaDettaglioMassiva, int codiceLettera, boolean convertiInPdf);
}
