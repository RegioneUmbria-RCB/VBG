package it.gruppoinit.service;

import it.gruppoinit.domain.AvvisoPagamentoInput;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaRequest;

import java.io.File;

public interface AvvisoPagamentoPdfService {

    public File generaAvvisatuaPagoPa(AvvisoPagamentoInput input);

    public void populateAvvisoPagamentoInput(BollettinopagopaRequest bollettinopagopaRequest, AvvisoPagamentoInput avvisoPagamentoInput);
}
