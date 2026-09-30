package it.gruppoinit.pal.gp.pay.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PagoPAServiceImpl;

public class PagoPAServiceTest {

    @Test
    public void generateQRCode() {

	PagoPAServiceImpl service = new PagoPAServiceImpl();
	PayPosizioniDebitorie pd = new PayPosizioniDebitorie();
	PayDettaglioImporti pdi = new PayDettaglioImporti();
	pdi.setImporto(BigDecimal.valueOf(0.1));
	pd.getDettagliImporto().add(pdi);
	pd.setCodiceAvviso("001310000000002152");
	PayProfiliEntiCreditori pp = new PayProfiliEntiCreditori();
	pp.setCfCodiceProfiloPSP("00514490010");
	pp.setCfEnteQrcodePagopa("00514490010");
	PayConfigurationHelper.setProfiloEnteCreditore(pp);
	String generaQRCode = service.generaQRCode(pd);
	Assert.assertEquals("il Qrcode generato è PAGOPA|002|001310000000002152|00514490010|10 ", generaQRCode,
		"PAGOPA|002|001310000000002152|00514490010|10");
    }
}
