package it.gruppoinit.pal.gp.pay.connector.easybridge;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamento;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.DpInviaEsitoPagamentoRequest;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model.DpInviaEsitoPagamentoResult;

public class DpInvioEsitoPagamentoTest {

    @Test()
    public void verificaCostruzioneMessaggioInviaEsito() throws UnsupportedEncodingException {

	DpInviaEsitoPagamento invioEsito = new DpInviaEsitoPagamento();
	invioEsito.setIdentificativoDominio("0123456");
	invioEsito.setParam(getInviaEsitoPagamento());
	Assert.assertTrue("messaggio codificato correttamente ", invioEsito.getParam().equalsIgnoreCase(encodedInternal));
	String msg = IOUtils.marshallObject(invioEsito);
	Assert.assertTrue("messaggio da inviare ", msg.equals(getMessaggioDaInviare()));
	invioEsito = (DpInviaEsitoPagamento) IOUtils.unMarshallString(msg, DpInviaEsitoPagamento.class);
	Assert.assertTrue("identificativoDominio =  0123456", invioEsito.getIdentificativoDominio().equals("0123456"));
	String internal = new String(Base64.decodeBase64(invioEsito.getParam().getBytes("utf-8")));
	DpInviaEsitoPagamentoRequest req = (DpInviaEsitoPagamentoRequest) IOUtils.unMarshallString(internal, DpInviaEsitoPagamentoRequest.class);
	Assert.assertTrue("IUV =  iuv012345689", req.getIdentificativoUnivocoVersamento().equals("iuv012345689"));
    }

    @Test()
    public void verificaCostruzioneMessaggioResult() throws UnsupportedEncodingException {

	DpInviaEsitoPagamentoResult result = new DpInviaEsitoPagamentoResult();
	result.setCodiceErrore("KO");
	result.setEsitoOperazione("DP_STATO_NON_VALIDO");
	String msg = Base64.encodeBase64String(IOUtils.marshallObject(result).getBytes("utf-8"));
	Assert.assertTrue("messaggio codificato correttamente", msg.equals(encodedInternalResult));
    }

    private String encodedInternalResult = "PG5zMDpkcEludmlhRXNpdG9QYWdhbWVudG9SZXN1bHQgeG1sbnM6bnMwPSJodHRwOi8vZWFzeWJyaWRnZS5ldS9icmlkZ2UvIj4NCiAgIDxlc2l0b09wZXJhemlvbmU+RFBfU1RBVE9fTk9OX1ZBTElETzwvZXNpdG9PcGVyYXppb25lPg0KICAgPGNvZGljZUVycm9yZT5LTzwvY29kaWNlRXJyb3JlPg0KPC9uczA6ZHBJbnZpYUVzaXRvUGFnYW1lbnRvUmVzdWx0Pg==";
    private String encodedInternal = "PG5zMDpkcEludmlhRXNpdG9QYWdhbWVudG8geG1sbnM6bnMwPSJodHRwOi8vZWFzeWJyaWRnZS5ldS9icmlkZ2UvIj4NCiAgIDxpZGVudGlmaWNhdGl2b1VuaXZvY29WZXJzYW1lbnRvPml1djAxMjM0NTY4OTwvaWRlbnRpZmljYXRpdm9Vbml2b2NvVmVyc2FtZW50bz4NCiAgIDxjb2RpY2VDb250ZXN0b1BhZ2FtZW50bz5jY3AtMTIzNDU2PC9jb2RpY2VDb250ZXN0b1BhZ2FtZW50bz4NCiAgIDxlc2l0b1BhZ2FtZW50bz5BQ0NFVFRBVE88L2VzaXRvUGFnYW1lbnRvPg0KPC9uczA6ZHBJbnZpYUVzaXRvUGFnYW1lbnRvPg==";

    private String getMessaggioDaInviare() {

	String msgDaInviare = "<ns2:dpInviaEsitoPagamento xmlns:ns2=\"http://easybridge.eu/bridge/\">\n";
	msgDaInviare += "    <identificativoDominio>0123456</identificativoDominio>\n";
	msgDaInviare += "    <param>PG5zMDpkcEludmlhRXNpdG9QYWdhbWVudG8geG1sbnM6bnMwPSJodHRwOi8vZWFzeWJyaWRnZS5ldS9icmlkZ2UvIj4NCiAgIDxpZGVudGlmaWNhdGl2b1VuaXZvY29WZXJzYW1lbnRvPml1djAxMjM0NTY4OTwvaWRlbnRpZmljYXRpdm9Vbml2b2NvVmVyc2FtZW50bz4NCiAgIDxjb2RpY2VDb250ZXN0b1BhZ2FtZW50bz5jY3AtMTIzNDU2PC9jb2RpY2VDb250ZXN0b1BhZ2FtZW50bz4NCiAgIDxlc2l0b1BhZ2FtZW50bz5BQ0NFVFRBVE88L2VzaXRvUGFnYW1lbnRvPg0KPC9uczA6ZHBJbnZpYUVzaXRvUGFnYW1lbnRvPg==</param>\n";
	msgDaInviare += "</ns2:dpInviaEsitoPagamento>";
	return msgDaInviare;
    }

    private String getInviaEsitoPagamento() {

	DpInviaEsitoPagamentoRequest req = new DpInviaEsitoPagamentoRequest();
	req.setIdentificativoUnivocoVersamento("iuv012345689");
	req.setCodiceContestoPagamento("ccp-123456");
	req.setEsitoPagamento("ACCETTATO");
	return Base64.encodeBase64String(IOUtils.marshallObject(req).getBytes());
    }
}
