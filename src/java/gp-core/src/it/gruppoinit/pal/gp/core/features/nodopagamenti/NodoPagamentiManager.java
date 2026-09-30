package it.gruppoinit.pal.gp.core.features.nodopagamenti;

public interface NodoPagamentiManager {

    /**
     * Il metodo cerca tutti i record di DETT_POSIZIONE_DEBITORIA CHE APPARTENGANO AGLI STATI INDICATI NEI PARAMETRI
     * 
     * @param statiDaVerificare
     */
    void aggiornaStatoPosizioniDebitorieInStati(String[] statiDaVerificareArr);
}
