package it.gruppoinit.pal.gp.pay.connector.easybridge.service;

import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamento;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamentoResponse;

public interface EasyBridgeService {

    DpInviaEsitoPagamentoResponse elaboraEsito(DpInviaEsitoPagamento esito);
}
