package it.gruppoinit.pal.gp.core.features.messaggimail;

import it.gruppoinit.pal.gp.core.features.messaggimail.model.IMessaggiMail;

public interface IMessaggiMailService {

    public Integer collegaRicevutaAMessaggioMail(IMessaggiMail mail, Integer idPadre);

    /**
     * Cerca su messaggi_mail se esiste un record con quel messageID
     * 
     * @param messageId
     * @return
     */
    public Integer trovaPadreDelMessaggioConId(String messageId);
}
