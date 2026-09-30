package it.gruppoinit.nlaproxy.web;

import it.gruppoinit.service.MovimentiAttiService;
import it.gruppoinit.sigepro.definitions.movimenti.MovimentiWSClient;
import it.gruppoinit.sigepro.definitions.oggetti.OggettiWSClient;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;
import it.gruppoinit.ws.client.atti.AttiWSServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    @Autowired
    private AttiWSServiceClient attiWSServiceClient;
    @Autowired
    private OggettiWSClient oggettiWSClient;
    @Autowired
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;
    @Autowired
    private MovimentiAttiService movimentiAttiService;
    @Autowired
    private MovimentiWSClient movimentiWSClient;

    //    public void setMovimentiWSClient(MovimentiWSClient movimentiWSClient) {
    //
    //	this.movimentiWSClient = movimentiWSClient;
    //    }
    //    @RequestMapping
    //    public String index(HttpServletResponse response) throws IOException {
    //
    //	return "main/index";
    //    }
    //
    //    @RequestMapping
    //    public String leggiAtto(HttpServletResponse response) throws IOException {
    //
    //	//AttoOut attoOut = attiWSServiceClient.leggiAtto(new LeggiAttoHelper());
    //	//	attiWSServiceClient.inserisciDeterminaString(new InserisciDeterminaHelper());
    //	//System.out.println(attoOut);
    //	InserisciDeterminaHelper determinaHelper = new InserisciDeterminaHelper();
    //	determinaHelper.setClassifica("classifica");
    //	determinaHelper.setTipo("tipo");
    //	determinaHelper.setProponente("proponenete");
    //	//	InserisciDeterminaStringResponse inserisciDeterminaStringResponse = attiWSServiceClient.inserisciDeterminaString(determinaHelper);
    //	LeggiAttoHelper attoHelper = new LeggiAttoHelper();
    //	attoHelper.setAnno("2015");
    //	attoHelper.setIdDocumento("1");
    //	//	attiWSServiceClient.leggiAtto(attoHelper);
    //	InserisciDeterminaHelper determinaIn = new InserisciDeterminaHelper();
    //	determinaIn.setTipo("DET");
    //	determinaIn.setTrattamento("1");
    //	determinaIn.setProponente("x");
    //	determinaIn.setDirigente("xx");
    //	determinaIn.setClassifica("1");
    //	determinaIn.setUtente("utente");
    //	determinaIn.setRuolo("ruolo");
    //	attiWSServiceClient.inserisciDeterminaString(determinaIn);
    //	//System.out.println(attoInseritoOut);
    //	return "main/index";
    //    }
    //
    //    @RequestMapping
    //    public String operazioniOggetti(HttpServletResponse response) throws IOException {
    //
    //	String token = sigeproSecurityWebServiceClient.loginAPP();
    //	OggettiFindResponse findResponse = oggettiWSClient.find(new BigInteger("345"), token);
    //	return "main/index";
    //    }
    //
    //    @RequestMapping
    //    public String insertTest(HttpServletResponse response) throws IOException {
    //
    //	MovimentiAtti atti = new MovimentiAtti();
    //	atti.setAnno(2015);
    //	atti.setDataRicezioneAtto(new Date());
    //	atti.setDataRichiestaAtto(null);
    //	atti.setIdcomune("E256");
    //	atti.setMovimenti(68024);
    //	atti.setNumero(101);
    //	atti.setStato(0);
    //	atti.setTipoDocumento("DEF");
    //	atti.setIdDocumento(200);
    //	movimentiAttiService.insert(atti);
    //	return "main/index";
    //    }
    //
    //    @RequestMapping
    //    public String insertAllegato(HttpServletResponse response) throws IOException {
    //
    //	MovimentiAllegatiInsertRequest allegatiInsertRequest = new MovimentiAllegatiInsertRequest();
    //	allegatiInsertRequest.setCodicemovimento(68127);
    //	//	XMLGregorianCalendarImpl calendar = new XMLGregorianCalendarImpl();
    //	//allegatiInsertRequest.setDataRegistrazione(calendar);
    //	allegatiInsertRequest.setDescrizione("Determina numero :");
    //	allegatiInsertRequest.setFlagPubblica(false);
    //	// decidere cosa mettere
    //	allegatiInsertRequest.setNote("");
    //	allegatiInsertRequest.setSoftware("SS");
    //	allegatiInsertRequest.setToken("b022d84e-693c-4217-9771-1bcab6a922dd");
    //	//////////////////////////////////////////////////
    //	// Recuperare da atto l'allegato e settarlo, si potrebbe recuperare da qua anche il nome da setatre 
    //	//attoOut.getAllegati().getValue().getItem().get(0).ge
    //	AllegatoBaseType value = new AllegatoBaseType();
    //	FileBaseType baseType = new FileBaseType();
    //	File ogg = new File("C://test.pdf");
    //	InputStream inputStream = new FileInputStream(ogg);
    //	baseType.setBinaryData(it.gruppoinit.utilities.Utilities.bytesToDataHandler(IOUtils.toByteArray(inputStream)));
    //	baseType.setFileName("Determina numero :");
    //	baseType.setMimeType("application/pdf");
    //	value.setDatiFile(baseType);
    //	//	RiferimentoOggettoBackofficeType oggettoBackofficeType = new RiferimentoOggettoBackofficeType();
    //	//	//oggettoBackofficeType.setCodice(value);
    //	//	value.setRiferimento(oggettoBackofficeType);
    //	allegatiInsertRequest.setAllegato(value);
    //	movimentiWSClient.insertMovimentiAllegati(allegatiInsertRequest);
    //	return "main/index";
    //    }
    //    //    public void setOggettiWSClient(OggettiWSClient oggettiWSClient) {
    //    //
    //    //	this.oggettiWSClient = oggettiWSClient;
    //    //    }
}
