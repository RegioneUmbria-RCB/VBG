package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.CaricamentoMassivoPosizioniNEXIGenovaServiceImpl;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakeInvioFlussoService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakeLetturaEsitiNexiGenovaService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakeMIPGenovaPayConnector;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakePayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakePayPosDebMassiveService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakePayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakePayRegistrazioniContabiliService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakePayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakePdfDebitoService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakePosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services.FakeSecurityClient;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.service.PayPosDebMassiveService;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;

public class CaricamentoMassivoPosizioniNEXIGenovaServiceImplTests {

    private CaricamentoMassivoPosizioniNEXIGenovaServiceImpl inizializzaTest() {

	//
	Map<String, String> parametri = new HashMap<>();
	parametri.put("MIPGE_CODICE_ENTE", "D969");
	parametri.put("CENTRO_DI_COSTO", "CDC");
	parametri.put("DOCUMENTI_SERVICE", "http://localhost");
	parametri.put("SECURITY_URL", "http://localhost");
	parametri.put("SECURITY_USER", "USER");
	parametri.put("SECURITY_PWD", "PASSWORD");
	parametri.put("SECURITY_ALIAS", "D969");
	//
	Amministrazioni amministrazione = new Amministrazioni();
	amministrazione.setId(new PkId(1));
	amministrazione.setAmministrazione("TEST");
	amministrazione.setIndirizzo("VIA");
	amministrazione.setCap("00000");
	amministrazione.setCitta("CITTA");
	amministrazione.setProvincia("PG");
	amministrazione.setTelefono1("111111111");
	amministrazione.setTelefono2("2222222");
	//
	PayConnectorConfig cfg = new PayConnectorConfig();
	cfg.setCodice("NEXI");
	//
	PayProfiliEntiCreditori profilo = new PayProfiliEntiCreditori();
	profilo.setPayConnector(cfg);
	profilo.setAmministrazione(amministrazione);
	//
	PayConfigurationHelper.setDocumentiFilesystemPath("c:\\");
	PayConfigurationHelper.setProfiloEnteCreditore(profilo);
	//
	PayRegistrazioniContabili registrazione = new PayRegistrazioniContabili();
	registrazione.setId(new PkId(1));
	//
	PaySoggettiDebitori soggetto = new PaySoggettiDebitori();
	soggetto.setVia("VIA NINO BIXIO");
	soggetto.setCap("00000");
	soggetto.setLocalita("PERUGIA");
	soggetto.setProvincia("PG");
	soggetto.setCfPi("MMMNNN83L26E123I");
	soggetto.setCognome("MMM");
	soggetto.setEmail("test@test.it");
	soggetto.setNome("NNN");
	//
	PayDettaglioImporti importo = new PayDettaglioImporti();
	importo.setAnnoAccertamento(2026);
	importo.setNumeroAccertamento("123");
	importo.setNumeroSottoAccertamento("456");
	importo.setImporto(BigDecimal.ONE);
	//
	PayPosizioniDebitorie posizione = new PayPosizioniDebitorie();
	posizione.setAnno(1983);
	posizione.setId(new PkId(1));
	posizione.setSoggettoDebitore(soggetto);
	posizione.getDettagliImporto().add(importo);
	posizione.setRegistrazioneContabile(registrazione);
	//
	List<Integer> lista = new ArrayList<>(Arrays.asList(2379, 2380, 2381, 2382));
	Map<String, List<Integer>> posizioniDaElaborare = new HashMap<>();
	posizioniDaElaborare.put("MER_VAR_AVV_APPMER", lista);
	//
	CaricamentoMassivoPosizioniNEXIGenovaServiceImpl service = new CaricamentoMassivoPosizioniNEXIGenovaServiceImpl();
	service.setPayPosDebMassiveService(new FakePayPosDebMassiveService(posizioniDaElaborare));
	service.setPayPosizioniDebitorieService(new FakePayPosizioniDebitorieService(posizione));
	service.setPosizioniDebitorieCommandService(new FakePosizioniDebitorieCommandService("MER_VAR_AVV_APPMER", "MER_VAR_AVV_APPMER"));
	service.setPayRegistrazioniContabiliService(new FakePayRegistrazioniContabiliService());
	service.setPayConnectorConfigValuesService(new FakePayConnectorConfigValuesService(parametri));
	service.setSecurityClient(new FakeSecurityClient("1234567890-0987654321"));
	service.setPdfDebitoService(new FakePdfDebitoService("test.pdf", 2));
	service.setInvioFlussoService(new FakeInvioFlussoService());
	service.setPayStatoPagamentiService(new FakePayStatoPagamentiService());
	service.setLetturaEsitiNexiGenovaService(new FakeLetturaEsitiNexiGenovaService(true, null));
	return service;
    }

    @Test()
    public void elaboraCaricamentoMassivoPosizioni() {

	//1. Parametri
	Map<String, String> params = new HashMap<>();
	params.put("GE", "MER_VAR_AVV_APPMER");
	//2. Connettore
	IPayConnector connector = new FakeMIPGenovaPayConnector();
	//3. Test
	CaricamentoMassivoPosizioniNEXIGenovaServiceImpl service = this.inizializzaTest();
	EsitoElaborazione esito = service.elaboraCaricamentoMassivoPosizioni(params, connector);
	//4. Assert
	Assert.assertTrue(esito.isEsito());
    }

    @Test()
    public void elaboraCaricamentoMassivoPosizioniSenzaParametri() {

	PayPosDebMassiveService posDebMassiveService = new FakePayPosDebMassiveService();
	CaricamentoMassivoPosizioniNEXIGenovaServiceImpl service = new CaricamentoMassivoPosizioniNEXIGenovaServiceImpl();
	service.setPayPosDebMassiveService(posDebMassiveService);
	//2. Connettore
	IPayConnector connector = new FakeMIPGenovaPayConnector();
	EsitoElaborazione esito = service.elaboraCaricamentoMassivoPosizioni(null, connector);
	Assert.assertFalse(esito.isEsito());
    }

    @Test()
    public void elaboraCaricamentoMassivoPosizioniSenzaConnettore() {

	PayPosDebMassiveService posDebMassiveService = new FakePayPosDebMassiveService();
	CaricamentoMassivoPosizioniNEXIGenovaServiceImpl service = new CaricamentoMassivoPosizioniNEXIGenovaServiceImpl();
	service.setPayPosDebMassiveService(posDebMassiveService);
	//1. Parametri
	Map<String, String> params = new HashMap<>();
	EsitoElaborazione esito = service.elaboraCaricamentoMassivoPosizioni(params, null);
	Assert.assertFalse(esito.isEsito());
    }
}
