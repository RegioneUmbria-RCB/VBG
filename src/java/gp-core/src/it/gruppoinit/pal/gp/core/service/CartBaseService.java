package it.gruppoinit.pal.gp.core.service;

public interface CartBaseService {

    /**
     * Ricarica le configurazioni delle credenziali per l'accesso ai servizi
     * 
     * @return
     */
    public void reloadConfiguration();

    /**
     * Sovrascrive il comportamento di effettuare la validazione formale degli schemi in invio/ricezione.<br/>
     * Di default la validazione viene effettuata, ma è accaduto che scaricando alcune schede di spiegazione dal CART i
     * dati non vengano validati bloccando di fatto l'operazione. Con questo flag si sovrascrive questo comporatmento.
     * 
     * @param effettuaValidazioneSchema
     */
    public void setEffettuaValidazioneSchema(boolean effettuaValidazioneSchema);

    /**
     * Ritorna true o false a seconda che la validazione degli schemi sia attivata o meno
     */
    public boolean isEffettuavalidazioneSchema();
}
