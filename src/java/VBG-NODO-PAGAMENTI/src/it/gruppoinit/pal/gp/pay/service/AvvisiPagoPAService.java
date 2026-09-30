package it.gruppoinit.pal.gp.pay.service;

import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaRequest;

public interface AvvisiPagoPAService {

    BollettinopagopaRequest popolaAvvisoRequestDaPosizioneDebitoria(PayPosizioniDebitorie pos, PayProfiliEntiCreditori ente);
}
