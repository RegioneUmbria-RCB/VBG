package it.gruppoinit.pal.gp.pay.features.interfaccia;

import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;

public class PayProfiliEntiCreditoriBean {

    private static final String CODICE_AMMINISTRAZIONE = "codice_amministrazione";
    private static final String SOFTWARE = "software";
    private static final String CBILL = "cbill";
    private static final String CC_POSTALE = "cc_postale";
    private static final String CF_CODICE_PROFILO = "cf_codice_profilo";
    private static final String CF_CODICE_PROFILO_PSP = "cf_codice_profilo_psp";
    private static final String URL_ESITO_PAGAMENTO = "url_esito_pagamento";
    private static final String URL_ANNULLAMENTO_PAGAMENTO = "url_annullamento_pagamento";
    private static final String CODICE_SEGREGAZIONE = "codice_segregazione";
    private static final String APPLICATION_CODE = "application_code";
    private static final String CF_ENTE_QRCODE_PAGOPA = "cf_ente_qrcode_pagopa";
    ////
    private ParametroEtichetta codiceAmministrazione;
    private ParametroEtichetta parametroSoftware;
    private ParametroEtichetta parametroCbill;
    private ParametroEtichetta ccPostale;
    private ParametroEtichetta cfCodiceProfilo;
    private ParametroEtichetta cfCodiceProfiloPsp;
    private ParametroEtichetta urlEsitoPagamento;
    private ParametroEtichetta urlAnnullamentoPagamento;
    private ParametroEtichetta codiceSegregazione;
    private ParametroEtichetta applicationCode;
    private ParametroEtichetta cfEnteQrcodePagopa;

    public ParametroEtichetta getCcPostale() {

	return ccPostale;
    }

    public void setCcPostale(ParametroEtichetta ccPostale) {

	this.ccPostale = ccPostale;
    }

    public ParametroEtichetta getUrlAnnullamentoPagamento() {

	return urlAnnullamentoPagamento;
    }

    public void setUrlAnnullamentoPagamento(ParametroEtichetta urlAnnullamentoPagamento) {

	this.urlAnnullamentoPagamento = urlAnnullamentoPagamento;
    }

    public ParametroEtichetta getCodiceSegregazione() {

	return codiceSegregazione;
    }

    public void setCodiceSegregazione(ParametroEtichetta codiceSegregazione) {

	this.codiceSegregazione = codiceSegregazione;
    }

    public ParametroEtichetta getApplicationCode() {

	return applicationCode;
    }

    public void setApplicationCode(ParametroEtichetta applicationCode) {

	this.applicationCode = applicationCode;
    }

    public ParametroEtichetta getCbill() {

	return parametroCbill;
    }

    public void setCbill(ParametroEtichetta cbill) {

	this.parametroCbill = cbill;
    }

    public ParametroEtichetta getCfCodiceProfilo() {

	return cfCodiceProfilo;
    }

    public void setCfCodiceProfilo(ParametroEtichetta cfCodiceProfilo) {

	this.cfCodiceProfilo = cfCodiceProfilo;
    }

    public ParametroEtichetta getSoftware() {

	return parametroSoftware;
    }

    public void setSoftware(ParametroEtichetta software) {

	this.parametroSoftware = software;
    }

    public ParametroEtichetta getCodiceAmministrazione() {

	return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(ParametroEtichetta codiceAmministrazione) {

	this.codiceAmministrazione = codiceAmministrazione;
    }

    public ParametroEtichetta getCfCodiceProfiloPsp() {

	return cfCodiceProfiloPsp;
    }

    public void setCfCodiceProfiloPsp(ParametroEtichetta cfCodiceProfiloPsp) {

	this.cfCodiceProfiloPsp = cfCodiceProfiloPsp;
    }

    public ParametroEtichetta getUrlEsitoPagamento() {

	return urlEsitoPagamento;
    }

    public void setUrlEsitoPagamento(ParametroEtichetta urlEsitoPagamento) {

	this.urlEsitoPagamento = urlEsitoPagamento;
    }

    public ParametroEtichetta getCfEnteQrcodePagopa() {

	return cfEnteQrcodePagopa;
    }

    public void setCfEnteQrcodePagopa(ParametroEtichetta cfEnteQrcodePagopa) {

	this.cfEnteQrcodePagopa = cfEnteQrcodePagopa;
    }

    public static PayProfiliEntiCreditoriBean popolaPayProfiliEntiCreditoriBean(PayProfiliEntiCreditori ppec) {

	PayProfiliEntiCreditoriBean ret = new PayProfiliEntiCreditoriBean();
	if (ppec.getAmministrazione() != null && ppec.getAmministrazione().getId().getCodice() != null) {
	    ParametroEtichetta amm = new ParametroEtichetta(CODICE_AMMINISTRAZIONE, "");
	    ret.setCodiceAmministrazione(amm);
	}
	if (ppec.getSoftware() != null) {
	    ParametroEtichetta software = new ParametroEtichetta(SOFTWARE, "");
	    ret.setSoftware(software);
	}
	if (ppec.getCbill() != null) {
	    ParametroEtichetta cbill = new ParametroEtichetta(CBILL, "");
	    ret.setCbill(cbill);
	}
	if (ppec.getCcPostale() != null) {
	    ParametroEtichetta cpost = new ParametroEtichetta(CC_POSTALE, "");
	    ret.setCcPostale(cpost);
	}
	ParametroEtichetta cfcodprof = new ParametroEtichetta(CF_CODICE_PROFILO, "");
	ret.setCfCodiceProfilo(cfcodprof);
	if (ppec.getCfCodiceProfiloPSP() != null) {
	    ParametroEtichetta cfcodprofPSP = new ParametroEtichetta(CF_CODICE_PROFILO_PSP, "");
	    ret.setCfCodiceProfiloPsp(cfcodprofPSP);
	}
	if (ppec.getUrlEsitoPagamento() != null) {
	    ParametroEtichetta urlEP = new ParametroEtichetta(URL_ESITO_PAGAMENTO, "");
	    ret.setUrlEsitoPagamento(urlEP);
	}
	if (ppec.getCodiceSegregazione() != null) {
	    ParametroEtichetta cs = new ParametroEtichetta(CODICE_SEGREGAZIONE, "");
	    ret.setCodiceSegregazione(cs);
	}
	if (ppec.getApplicationCode() != null) {
	    ParametroEtichetta ac = new ParametroEtichetta(APPLICATION_CODE, "");
	    ret.setApplicationCode(ac);
	}
	if (ppec.getCfEnteQrcodePagopa() != null) {
	    ParametroEtichetta qrcode = new ParametroEtichetta(CF_ENTE_QRCODE_PAGOPA, "");
	    ret.setCfEnteQrcodePagopa(qrcode);
	}
	if (ppec.getUrlAnnullamentoPagamento() != null) {
	    ParametroEtichetta urlAnn = new ParametroEtichetta(URL_ANNULLAMENTO_PAGAMENTO, "");
	    ret.setUrlAnnullamentoPagamento(urlAnn);
	}
	return ret;
    }
}
