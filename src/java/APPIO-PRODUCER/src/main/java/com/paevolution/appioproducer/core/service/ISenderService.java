package com.paevolution.appioproducer.core.service;

import com.paevolution.appioproducer.core.domain.AppIoCoda;
import com.paevolution.appioproducer.core.domain.MovimentiIoComunicazioni;
import com.paevolution.appioproducer.core.domain.helper.MessageToSendHelper;

public interface ISenderService {
    
    public void sendMessage(AppIoCoda messageToSend);
    /**
     * 
     * @param messageToNotify
     */
    public void getMessage(AppIoCoda messageToNotify);
    /** 
     *  1. chiama l'API ritentaInvio<br>
     *  2. aggiorna lo stato in INVIATA_A_GATEWAY 
     *  @param messageToSend
     */
    public void resendMessage(AppIoCoda messageToSend);
}
