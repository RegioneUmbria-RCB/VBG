/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.helper;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtDatiSingoloPagamentoRT;
import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRicevutaTelematica;
import it.gov.pagopa.api.CtReceipt;
import it.gov.pagopa.api.PaSendRTReq;
import it.gov.pagopa.api.PaSendRTV2Request;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;

/**
 * @author Franco.Leone
 *
 */
public class RTHelper {

    private static enum TIPO_RICEVUTA {
	CTRECEIPT,
	CTRICEVUTATELEMATICA,
	PASENDRTREQ,
	PASENDRTV2REQ
    }

    private static TIPO_RICEVUTA getTipoRicevutaBase64(String xmlStringBase64) {

	return getTipoRicevuta(Utilities.decodeBase64Binary(xmlStringBase64));
    }

    public static void main(String[] args) throws IOException {

	String readFileToString = FileUtils.readFileToString(new File("C:\\temp\\silfi\\rtV2.xml"), "utf-8");
	DatiPagamentoType popolaDatiPagamentoDaReceiptORicevutaTelematica = popolaDatiPagamentoDaReceiptORicevutaTelematica(readFileToString, null);
	System.out.println(popolaDatiPagamentoDaReceiptORicevutaTelematica);
    }

    private static TIPO_RICEVUTA getTipoRicevuta(String xmlString) {

	if (StringUtils.defaultString(xmlString).indexOf("paSendRTV2Request") > 0 && StringUtils.defaultString(xmlString).indexOf("receipt") > 0) {
	    return TIPO_RICEVUTA.PASENDRTV2REQ;
	}
	if (StringUtils.defaultString(xmlString).indexOf("paSendRTReq") > 0 && StringUtils.defaultString(xmlString).indexOf("receipt") > 0) {
	    return TIPO_RICEVUTA.PASENDRTREQ;
	}
	if (StringUtils.defaultString(xmlString).indexOf("receiptId") > 0 && StringUtils.defaultString(xmlString).indexOf("noticeNumber") > 0) {
	    return TIPO_RICEVUTA.CTRECEIPT;
	}
	return TIPO_RICEVUTA.CTRICEVUTATELEMATICA;
    }

    public static CtReceipt parseReceipt(String xmlString) {

	CtReceipt ricevuta = null;
	if (StringUtils.isNotBlank(xmlString)) {
	    try {
		JAXBContext jc = JAXBContext.newInstance(CtReceipt.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StringReader reader = new StringReader(xmlString);
		ricevuta = (CtReceipt) unmarshaller.unmarshal(reader);
	    } catch (JAXBException e) {
		throw new RuntimeException(e);
	    }
	}
	return ricevuta;
    }

    public static PaSendRTReq parsePaSendRTReq(String xmlString) {

	PaSendRTReq ricevuta = null;
	if (StringUtils.isNotBlank(xmlString)) {
	    try {
		JAXBContext jc = JAXBContext.newInstance(PaSendRTReq.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StringReader reader = new StringReader(xmlString);
		ricevuta = (PaSendRTReq) unmarshaller.unmarshal(reader);
	    } catch (JAXBException e) {
		throw new RuntimeException(e);
	    }
	}
	return ricevuta;
    }

    public static CtRicevutaTelematica parseRicevutaTelematica(String xmlString) {

	CtRicevutaTelematica ricevuta = null;
	if (StringUtils.isNotBlank(xmlString)) {
	    try {
		JAXBContext jc = JAXBContext.newInstance(CtRicevutaTelematica.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StringReader reader = new StringReader(xmlString);
		ricevuta = (CtRicevutaTelematica) unmarshaller.unmarshal(reader);
	    } catch (JAXBException e) {
		throw new RuntimeException(e);
	    }
	}
	return ricevuta;
    }

    public static CtRicevutaTelematica parseRicevutaTelematicaBase64Binary(String base64String) {

	if (StringUtils.isNotBlank(base64String)) {
	    return parseRicevutaTelematica(Utilities.decodeBase64Binary(base64String));
	} else {
	    return null;
	}
    }

    public static DatiPagamentoType popolaDatiPagamentoDaRicevutaTelematica(CtRicevutaTelematica rtXml, DatiPagamentoType pagamenti) {

	if (rtXml != null) {
	    if (pagamenti == null) {
		pagamenti = new DatiPagamentoType();
	    }
	    pagamenti.setDataOraPagamento(rtXml.getDataOraMessaggioRicevuta());
	    pagamenti.setImportoPagato(rtXml.getDatiPagamento().getImportoTotalePagato());
	    pagamenti.setImportoTransato(rtXml.getDatiPagamento().getImportoTotalePagato());
	    pagamenti.setIuv(rtXml.getDatiPagamento().getIdentificativoUnivocoVersamento());
	    pagamenti.setRiferimentiPagamento(rtXml.getIdentificativoMessaggioRicevuta());
	    List<CtDatiSingoloPagamentoRT> datiSingoliPagamenti = rtXml.getDatiPagamento().getDatiSingoloPagamento();
	    if (!datiSingoliPagamenti.isEmpty()) {
		CtDatiSingoloPagamentoRT datiPag = datiSingoliPagamenti.get(0);
		//
		pagamenti.setDataOraAutorizzazione(datiPag.getDataEsitoSingoloPagamento());
		pagamenti.setIur(datiPag.getIdentificativoUnivocoRiscossione());
		BigDecimal importo = BigDecimal.ZERO;
		if (datiPag.getCommissioniApplicatePA() != null) {
		    importo = importo.add(datiPag.getCommissioniApplicatePA());
		}
		if (datiPag.getCommissioniApplicatePSP() != null) {
		    importo = importo.add(datiPag.getCommissioniApplicatePSP());
		}
		pagamenti.setImportoCommissioni(importo);
		pagamenti.setDataOraAutorizzazione(datiPag.getDataEsitoSingoloPagamento());
		pagamenti.setDescrizioneCausale(datiPag.getCausaleVersamento());
	    }
	    pagamenti.setModalitaPagamento(rtXml.getDatiPagamento().getCodiceContestoPagamento());
	    //
	    SoggettoDebitoreType soggetto = new SoggettoDebitoreType();
	    soggetto.setCfpi(rtXml.getSoggettoPagatore().getIdentificativoUnivocoPagatore().getCodiceIdentificativoUnivoco());
	    soggetto.setNome(rtXml.getSoggettoPagatore().getAnagraficaPagatore());
	    soggetto.setEmail(rtXml.getSoggettoPagatore().getEMailPagatore());
	    soggetto.setLocalita(rtXml.getSoggettoPagatore().getLocalitaPagatore());
	    soggetto.setProvincia(rtXml.getSoggettoPagatore().getProvinciaPagatore());
	    soggetto.setVia(rtXml.getSoggettoPagatore().getIndirizzoPagatore());
	    //
	    pagamenti.setSoggettoPagatore(soggetto);
	    pagamenti.setRagioneSocialePSP(rtXml.getIstitutoAttestante().getDenominazioneAttestante());
	    if (StringUtils.isBlank(rtXml.getIstitutoAttestante().getCodiceUnitOperAttestante())) {
		pagamenti.setIdPSP(rtXml.getIstitutoAttestante().getIdentificativoUnivocoAttestante().getCodiceIdentificativoUnivoco());
	    } else {
		pagamenti.setIdPSP(rtXml.getIstitutoAttestante().getCodiceUnitOperAttestante());
	    }
	}
	return pagamenti;
    }

    public static DatiPagamentoType popolaDatiPagamentoDaReceipt(CtReceipt rtXml, DatiPagamentoType pagamenti) {

	if (rtXml != null) {
	    if (pagamenti == null) {
		pagamenti = new DatiPagamentoType();
	    }
	    pagamenti.setDataOraPagamento(rtXml.getPaymentDateTime());
	    pagamenti.setDataOraAutorizzazione(rtXml.getPaymentDateTime());
	    pagamenti.setImportoPagato(rtXml.getPaymentAmount());
	    pagamenti.setImportoTransato(rtXml.getPaymentAmount());
	    pagamenti.setIuv(rtXml.getCreditorReferenceId());
	    pagamenti.setRiferimentiPagamento(rtXml.getReceiptId());
	    pagamenti.setImportoCommissioni(rtXml.getFee());
	    pagamenti.setDescrizioneCausale(rtXml.getDescription());
	    pagamenti.setIur(rtXml.getIdChannel());
	    pagamenti.setModalitaPagamento(rtXml.getPaymentMethod());
	    //
	    if (rtXml.getDebtor() != null) {
		SoggettoDebitoreType soggetto = new SoggettoDebitoreType();
		if (rtXml.getDebtor().getUniqueIdentifier() != null) {
		    soggetto.setCfpi(rtXml.getDebtor().getUniqueIdentifier().getEntityUniqueIdentifierValue());
		}
		soggetto.setNome(rtXml.getDebtor().getFullName());
		soggetto.setEmail(rtXml.getDebtor().getEMail());
		soggetto.setLocalita(rtXml.getDebtor().getCity());
		soggetto.setProvincia(rtXml.getDebtor().getStateProvinceRegion());
		soggetto.setVia(rtXml.getDebtor().getStreetName());
		//
		pagamenti.setSoggettoPagatore(soggetto);
	    }
	    pagamenti.setRagioneSocialePSP(rtXml.getPSPCompanyName());
	    pagamenti.setIdPSP(rtXml.getIdPSP());
	}
	return pagamenti;
    }

    public static DatiPagamentoType popolaDatiPagamentoDaReceiptORicevutaTelematica(String ricevutaXml, DatiPagamentoType pagamenti) {

	TIPO_RICEVUTA tipoRicevuta = getTipoRicevuta(ricevutaXml);
	DatiPagamentoType ret = null;
	switch (tipoRicevuta) {
	case CTRECEIPT:
	    ret = popolaDatiPagamentoDaReceipt(parseReceipt(ricevutaXml), pagamenti);
	    break;
	case CTRICEVUTATELEMATICA:
	    ret = popolaDatiPagamentoDaRicevutaTelematica(RTHelper.parseRicevutaTelematica(ricevutaXml), pagamenti);
	    break;
	case PASENDRTREQ:
	    ret = popolaDatiPagamentoDapaSendRTReq(ricevutaXml, pagamenti);
	    break;
	case PASENDRTV2REQ:
	    ret = popolaDatiPagamentoDapaSendRTV2Req(ricevutaXml, pagamenti);
	    break;
	default:
	    break;
	}
	return ret;
    }

    private static DatiPagamentoType popolaDatiPagamentoDapaSendRTReq(String ricevutaXml, DatiPagamentoType pagamenti) {

	PaSendRTReq paSendRTReq = parsePaSendRTReq(ricevutaXml);
	CtReceipt rtXml = paSendRTReq.getReceipt();
	return popolaDatiPagamentoDaReceipt(rtXml, pagamenti);
    }

    private static DatiPagamentoType popolaDatiPagamentoDapaSendRTV2Req(String ricevutaXml, DatiPagamentoType pagamenti) {

	PaSendRTV2Request paSendRTReq = parsePaSendRTV2Req(ricevutaXml);
	CtReceipt rtXml = paSendRTReq.getReceipt();
	return popolaDatiPagamentoDaReceipt(rtXml, pagamenti);
    }

    public static PaSendRTV2Request parsePaSendRTV2Req(String xmlString) {

	PaSendRTV2Request ricevuta = null;
	if (StringUtils.isNotBlank(xmlString)) {
	    try {
		JAXBContext jc = JAXBContext.newInstance(PaSendRTV2Request.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StringReader reader = new StringReader(xmlString);
		ricevuta = (PaSendRTV2Request) unmarshaller.unmarshal(reader);
	    } catch (JAXBException e) {
		throw new RuntimeException(e);
	    }
	}
	return ricevuta;
    }
}
