package com.paevolution.appioproducer.core.service;

import com.paevolution.appioproducer.core.domain.MovimentiIoComunicazioni;
import com.paevolution.appioproducer.core.domain.helper.MessageToSendHelper;

public interface ISenderService {
    
    public void sendMessage(MessageToSendHelper messageToSendHelper);
    public void getMessage(MovimentiIoComunicazioni movimentiIoComunicazioni);
}
