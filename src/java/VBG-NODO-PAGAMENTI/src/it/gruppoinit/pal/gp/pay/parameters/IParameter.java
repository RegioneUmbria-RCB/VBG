package it.gruppoinit.pal.gp.pay.parameters;

public interface IParameter {

    /**
     * Torna il nome parametro
     * 
     * @return
     */
    String getNomeParametro();

    /**
     * Imposta il valore dedl parametro
     * 
     * @param valore
     */
    void setValore(String valore);

    /**
     * Ritorna il valore del parametro
     */
    String getValore();

    /**
     * La descrizione del parametro. Ogni connettore potrebbe Riportare una descrizione di quello che significa
     * 
     * @return
     */
    String getDescrizione();

    /**
     * Informazioni di aiuto per la compilazione
     */
    String getHelp();
}
