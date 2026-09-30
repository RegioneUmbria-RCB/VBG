package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.server;

import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.pay.connector.easybridge.service.EasyBridgeService;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamento;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamentoResponse;

public class EBEsitoPagamentoServiceImpl implements EBEsitiPagamentoInterface {

    @Autowired
    private EasyBridgeService easyBridgeService;

    @Override
    public DpInviaEsitoPagamentoResponse dpInviaEsitoPagamento(DpInviaEsitoPagamento parameters) {

	return easyBridgeService.elaboraEsito(parameters);
    }
}
