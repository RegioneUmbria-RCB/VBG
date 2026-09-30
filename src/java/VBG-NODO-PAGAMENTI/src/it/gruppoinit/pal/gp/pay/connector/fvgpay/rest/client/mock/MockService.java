package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.mock;

import java.util.Calendar;

import javax.xml.bind.JAXBException;

import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DatiSpecificiRiscossioneType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DatiSpecificiRiscossioneType.TipoContabilitaEnum;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DettaglioPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DettaglioVocePagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.ImportoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiPagamentoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.NotificaEsitiPagamentoTypeElenco;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.StatoPagamentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.StatoSingoloPagamentoType;

public class MockService {

    public static NotificaEsitiPagamentoType getNotificaEsiti() {

	NotificaEsitiPagamentoType not = new NotificaEsitiPagamentoType();
	not.setIdentificativoBeneficiario("id_beneficiario");
	NotificaEsitiPagamentoTypeElenco ne = new NotificaEsitiPagamentoTypeElenco();
	ne.setIdDebito("ID_DEBITO");
	ne.setIuv("IUV_012345678");
	StatoPagamentoPosizioneDebitoriaType spd = new StatoPagamentoPosizioneDebitoriaType();
	spd.setStatoPagamento("stat_pagamento");
	StatoSingoloPagamentoType spss = new StatoSingoloPagamentoType();
	spss.setCodiceAvvisoPagamento("avviso");
	spss.setIuv("iuv_rata_unica");
	spss.setStatoPagamento("statopagamento rata unica");
	DettaglioPagamentoType dp = new DettaglioPagamentoType();
	dp.setDataPagamento(Calendar.getInstance().getTime());
	ImportoType it = new ImportoType();
	it.setImporto(1d);
	it.setValuta("Euro");
	dp.setImportoTotale(it);
	DettaglioVocePagamentoType dvp = new DettaglioVocePagamentoType();
	dvp.setCausale("causale pagamento");
	dvp.setImporto(it);
	dvp.setCommissioneCaricoPa(it);
	DatiSpecificiRiscossioneType dsr = new DatiSpecificiRiscossioneType();
	dsr.setCodiceContabilita("codice_contabilità");
	dsr.setTipoContabilita(TipoContabilitaEnum._9);
	dvp.setDatiSpecificiRiscossione(dsr);
	dp.getDettaglioVociPagamento().add(dvp);
	spss.setDettaglioPagamento(dp);
	spd.setStatoPagamentoUnicaSoluzione(spss);
	ne.setStatoPagamentoPosizioneDebitoria(spd);
	not.getElenco().add(ne);
	return not;
    }

    public static void main(String[] args) throws JAXBException {

	System.out.println(JSONUtils.marshal(getNotificaEsiti(), false));
    }
}
