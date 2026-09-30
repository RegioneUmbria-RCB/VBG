package it.gruppoinit.pal.gp.pay.connector.govpay.rest.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.Soggetto;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.NuovoPagamento;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.PagamentoCreato;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.NuovaPendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.NuovaVocePendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.PatchOp;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.Pendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.PendenzaCreata;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.StatoPendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.TipoSoggetto;
import it.gruppoinit.pal.gp.pay.exception.PayException;

public class RestClientTest {

    private static final String COD_ENTRATA = "ASL_TICKET";
    private static final String ID_DOMINIO = "02307130696";
    private static String ID_A2A = "PAEVOLUTION";

    private List<String> testModello1() throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	List<String> ret = new ArrayList<>();
	GovPayClient testClient = getTestClient();
	NuovoPagamento nuovoPagamento = new NuovoPagamento();
	nuovoPagamento.setUrlRitorno("http://pa-evolution.com/dev.htm");
	Soggetto soggettoVersante = new Soggetto();
	soggettoVersante.setTipo(TipoSoggetto.F);
	soggettoVersante.setAnagrafica("Mario Rossi");
	soggettoVersante.setIdentificativo("RSSMRA30A01H501I");
	soggettoVersante.setIndirizzo("Carducci");
	soggettoVersante.setCap("06024");
	nuovoPagamento.setSoggettoVersante(soggettoVersante);
	List<Object> lisRiferimento = new ArrayList<>();
	long sys = System.currentTimeMillis();
	String idPendenza = "MOD1_0_00193460680_" + sys;
	ret.add(idPendenza);
	NuovaPendenza nuovaPendenza = popolaPendenzaTest(idPendenza, true, ID_A2A);
	lisRiferimento.add(nuovaPendenza);
	idPendenza = "MOD1_1_00193460680_" + sys;
	ret.add(idPendenza);
	NuovaPendenza nuovaPendenza2 = popolaPendenzaTest(idPendenza, true, ID_A2A);
	lisRiferimento.add(nuovaPendenza2);
	nuovoPagamento.setPendenze(lisRiferimento);
	PagamentoCreato pendenzaCreata = testClient.avvioPagamento(nuovoPagamento);
	System.out.println(pendenzaCreata.toString());
	return ret;
    }

    private NuovaPendenza popolaPendenzaTest(String id, boolean isPagamento, String idA2A) {

	NuovaPendenza nuovaPendenza = new NuovaPendenza();
	long sys = System.currentTimeMillis();
	nuovaPendenza.setIdDominio(ID_DOMINIO);
	nuovaPendenza.setCausale("DIRITTI PROVA");
	if (isPagamento) {
	    nuovaPendenza.setIdA2A(idA2A);
	    nuovaPendenza.setIdPendenza(id);
	}
	Soggetto soggettoPagatore = new Soggetto();
	soggettoPagatore.setTipo(TipoSoggetto.F);
	soggettoPagatore.setIdentificativo("RSSMRA30A01H501I");
	soggettoPagatore.setAnagrafica("Mr Pippo3");
	nuovaPendenza.setSoggettoPagatore(soggettoPagatore);
	BigDecimal importo = BigDecimal.ZERO;
	NuovaVocePendenza vocePendenza = new NuovaVocePendenza();
	vocePendenza.setCodEntrata(COD_ENTRATA);
	vocePendenza.setIdVocePendenza("idvp_rm_" + sys);
	vocePendenza.setImporto(BigDecimal.valueOf(1.50));
	importo = importo.add(vocePendenza.getImporto());
	vocePendenza.setDescrizione("abcdef12345_rm_1");
	List<NuovaVocePendenza> l = new ArrayList<NuovaVocePendenza>();
	l.add(vocePendenza);
	if (isPagamento) { // NON GENERA LO IUV PER PENDENZE CON PIù DI UNA VOCE PENDENZA
	    NuovaVocePendenza vocePendenza2 = new NuovaVocePendenza();
	    vocePendenza2.setCodEntrata(COD_ENTRATA);
	    vocePendenza2.setIdVocePendenza("idvp_ds_" + sys);
	    vocePendenza2.setImporto(BigDecimal.valueOf(0.50));
	    importo = importo.add(vocePendenza2.getImporto());
	    vocePendenza2.setDescrizione("abcdef12345_ds_1");
	    l.add(vocePendenza2);
	}
	nuovaPendenza.setImporto(importo);
	nuovaPendenza.setVoci(l);
	nuovaPendenza.setDataScadenza("2021-02-17T23:59:59CET");
	return nuovaPendenza;
    }

    private void testAnnullaPendenza(String idPendenza) throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	GovPayClient testClient = getTestClient();
	PatchOp patchOp = new PatchOp();
	patchOp.setOp(PatchOp.OpEnum.REPLACE);
	patchOp.path("/stato");
	patchOp.setValue(StatoPendenza.ANNULLATA.name());
	List<PatchOp> listaPatch = new ArrayList<PatchOp>();
	listaPatch.add(patchOp);
	testClient.aggiornaPendenza(ID_A2A, idPendenza, listaPatch);
    }

    private void testDettaglioPendenza(String idPendenza) throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	GovPayClient testClient = getTestClient();
	Pendenza pendenza = testClient.getDettaglioPendenza(ID_A2A, idPendenza);
	System.out.println(pendenza);
    }

    private String testModello3() throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	GovPayClient testClient = getTestClient();
	long sys = System.currentTimeMillis();
	String idPendenza = "MOD3_00193460680_" + sys;
	NuovaPendenza nuovaPendenza = popolaPendenzaTest(idPendenza, false, ID_A2A);
	PendenzaCreata pendenzaCreata = testClient.inserisciPendenza(ID_A2A, idPendenza, nuovaPendenza, true);
	System.out.println(pendenzaCreata.toString());
	Pendenza pendenza = testClient.getDettaglioPendenza(ID_A2A, idPendenza);
	System.out.println(pendenza);
	return idPendenza;
    }

    private void testGetAvviso(String numeroAvviso) throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	GovPayClient testClient = getTestClient();
	InputStream is = testClient.getAvviso(ID_DOMINIO, numeroAvviso);
	FileOutputStream fos = new FileOutputStream("C:/temp/avviso_" + numeroAvviso + ".pdf");
	org.apache.commons.io.IOUtils.copy(is, fos);
    }

    private GovPayClient getTestClient() {

	BasicAuthParams c = new BasicAuthParams("demo", "123456");
	SSLClientParameters s = new SSLClientParameters("1", "c:/temp/arit/pae-gp-test4_truststore.jks", "aritdev", "c:/temp/arit/pae-gp-test4.jks",
		"aritdev");
	GovPayClientParameters p = new GovPayClientParameters("https://govway-dev.regione.abruzzo.it", //
		"/govway/in/RegioneAbruzzo/GovPay-Pendenze/v2/", // 
		"/govway/in/RegioneAbruzzo/GovPay-Pendenze/v2/pendenze", //
		"/govway/in/RegioneAbruzzo/GovPay-Pagamenti/v2/pagamenti", c, s, null);
	return GovPayClient.getClient(p);
    }

    @Test
    public void testDettaglioPendenze() {

	try {
	    testGetAvviso("300000000000022340");
	    // MOD1_0_00193460680_1613549475847
	    // testAnnullaPendenza("MOD3_00193460680_1613561504974");
	    // testModello3();
	    //testDettaglioPendenza("");
	    // testPagamento("bc77b05dc77f420aa3e849ddf36d64de");
	    //	    testDettaglioPendenza("MOD1_1_00193460680_1613549475847");
	} catch (IOException | GeneralSecurityException | URISyntaxException | PayException e) {
	    e.printStackTrace();
	}
    }

    private void testPagamento(String string) throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	GovPayClient testClient = getTestClient();
	Pagamento is = testClient.getDettaglioPagamento(string);
	// System.out.println(is);
	System.out.println(is.getStato());
    }
}
