package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.pay.connector.easybridge.EasyBridgeConnector;
import it.gruppoinit.pal.gp.pay.connector.easypa.UnicreditEasyPAConnector;
import it.gruppoinit.pal.gp.pay.connector.entranext.EntraNextConnector;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.FvgPayConnector;
import it.gruppoinit.pal.gp.pay.connector.govpay.GovPayConnector;
import it.gruppoinit.pal.gp.pay.connector.iris.IrisPayConnector;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.JCityGovConnector;
import it.gruppoinit.pal.gp.pay.connector.mip.MIPConnector;
import it.gruppoinit.pal.gp.pay.connector.mock.MockPayConnector;
import it.gruppoinit.pal.gp.pay.connector.openweb.OpenWebConnector;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.PagoUmbriaPayConnector;
import it.gruppoinit.pal.gp.pay.connector.payer.PayerConnector;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.PiemontePayConnector;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.rest.PiemontePayRestConnector;
import it.gruppoinit.pal.gp.pay.connector.plugandpay.PlugAndPayConnector;
import it.gruppoinit.pal.gp.pay.connector.silfi.SilfiPayConnector;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroAggiungiGiorniADataScadenzaAvviso;
import it.gruppoinit.pal.gp.pay.parameters.ParametroAnnoAccertamento;
import it.gruppoinit.pal.gp.pay.parameters.ParametroChiaveApplicationCodeIUV;
import it.gruppoinit.pal.gp.pay.parameters.ParametroCodiceServizio;
import it.gruppoinit.pal.gp.pay.parameters.ParametroCodiceTassonomia;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDataScadenza;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDataScadenzaStampabile;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDatiRiscossione;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDescrizioneCausalePSP;
import it.gruppoinit.pal.gp.pay.parameters.ParametroIdUnitaOperativa;
import it.gruppoinit.pal.gp.pay.parameters.ParametroInviaDettagliPagamento;
import it.gruppoinit.pal.gp.pay.parameters.ParametroInviaSoloPosizioniDiSoggettiConMail;
import it.gruppoinit.pal.gp.pay.parameters.ParametroNumeroAccertamento;
import it.gruppoinit.pal.gp.pay.parameters.ParametroNumeroSottoAccertamento;
import it.gruppoinit.pal.gp.pay.parameters.ParametroTipoDocumentoSdi;

public class ParametriConnettoreHelper {

    private static final String SERVE_PER_LA_GENERAZIONE_DELLO_IUV_INDICARE_L_ID_DELLA_CAUSALE = "Serve per la generazione dello IUV, indicare l'id della causale, il parametro è obbligatorio";
    private static final String NUMERO_ACCERTAMENTO = "Numero accertamento";
    private static final String INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA = "Indicare la descrizione della causale come se lo aspetta";
    private static final String INDICARE_L_ANNO_DI_ACCERTAMENTO = "Indicare l'anno di accertamento";
    private static final String INDICARE_IL_NUMERO_DI_ACCERTAMENTO = "Indicare il numero di accertamento";
    private static final String IDENTIFICATIVO_PER_CALCOLO_IUV = "Identificativo per calcolo IUV";
    private static final String DESCRIZIONE_CAUSALE = "Descrizione causale";
    private static final String CODICE_VOCE = "Codice Voce";
    private static final String CAPITOLO_BILANCIO = "Capitolo Bilancio";
    private static final String ANNO_ACCERTAMENTO = "Anno accertamento";
    private static final String ACCERTAMENTO = "Accertamento";

    private ParametriConnettoreHelper() {

	super();
    }

    private static Map<String, List<IParameter>> p = new HashMap<>();
    static {
	inizializzaEasyBridgePESConnector();
	inizializzaEasyPaConector();
	inizializzaEntraNextConnector();
	inizializzaFvgPay();
	inizializzaGovPay();
	inizializzaIrisConnector();
	inizializzaJCityGov();
	inizializzaMipConnector();
	inizializzaMockConnector();
	inizializzaOpenWeb();
	inizializzaPagoUmbria();
	inizializzaPayer();
	inizializzaPiemontePay();
	inizializzaPiemontePayRest();
	inizializzaPlugAndPay();
	inizializzaSilfi();
    }

    public static List<IParameter> getMappaParametriConnettore(String connectorName) {

	return p.get(connectorName);
    }

    private static void inizializzaEasyBridgePESConnector() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	String help = "La data di scadenza che viene visualizzata nel bollettino avviso è quella della posizione debitoria. " //
		      +
		      "La data di scadenza del pagamento (diversa dalla data stampabile e oltre la quale non è più pagabile in PAGO PA anche se scaduta) " //
		      + "è uguale alla data di scadenza della posizione debitoria. "// 
		      + "Questo parametro permette di allungare la data di scadenza del pagamento rispetto a quella stampabile. " // 
		      + "L'ente decide di fare pagare oltre la data di scadenza";
	ret.add(new ParametroAggiungiGiorniADataScadenzaAvviso("Aggiungi giorni a scadenza avviso", help));
	p.put(EasyBridgeConnector.class.getName(), ret);
    }

    private static void inizializzaEasyPaConector() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroInviaSoloPosizioniDiSoggettiConMail("Invia solo a soggetti con mail", "Il parametro indica se attivare il " + //
												   "comportamento di inviare le posizioni debitorie mediante cooperazione applicativa piuttosto che tramite tracciato. Valori ammessi 0 (Default) 1"));
	p.put(UnicreditEasyPAConnector.class.getName(), ret);
    }

    private static void inizializzaEntraNextConnector() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroTipoDocumentoSdi("Tipo documento",
		" TipoDocumento preso da queste voci <pre>\r\n" + " * &lt;simpleType name=\"TipiDocumentiSDI\"&gt;\r\n" //
								+ "    &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}string\"&gt;\r\n" //
								+ "      &lt;enumeration value=\"Avviso\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"Fattura\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"PromemoriaDiMancatoPagamento\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"SollecitoNonNotificato\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"SollecitoNotificato\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"IngiunzioneFiscale\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"Accertamento_Liquidazione\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"Accertamento_InfedeleDenuncia\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"Accertamento_OmessaDenuncia\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"Rateizzazione\"/&gt;\r\n" //
								+ "      &lt;enumeration value=\"AccertamentoEsecutivo\"/&gt;\r\n" //
								+ "    &lt;/restriction&gt;\r\n" //
								+ "  &lt;/simpleType&gt;\r\n" //
								+ "  </pre>"));
	ret.add(new ParametroAnnoAccertamento(ANNO_ACCERTAMENTO, ""));
	ret.add(new ParametroNumeroAccertamento("Numero Accertamento", ""));
	ret.add(new ParametroNumeroSottoAccertamento("Nome Voce di costo", ""));
	ret.add(new ParametroDatiRiscossione("Causale importo",
		" La definizio del valore della causale. Il valore deve essere preso da queste informazioni <pre>\r\n" +
								" &lt;simpleType name=\"CausaliImporti\"&gt;\r\n" //
								+ "   &lt;restriction base=\"{http://www.w3.org/2001/XMLSchema}string\"&gt;\r\n" //
								+ "     &lt;enumeration value=\"Servizi\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"Sanzioni\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"Spese\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"Bollo\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"Interessi\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"Arrotondamento\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"DepositiCauzionali\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"RimborsoDepositiCauzionali\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"RimborsoServizi\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"SpeseTenutaConto\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"ImpostaRegistro\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"Commissioni\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"InteressiPassiviCCP\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"SpeseDomiciliazione\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"CommissioniBolloSpeseTenutaConto\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"CommissioniSpeseTenutaConto\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"Urgenza\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"SanzioniInfedele\"/&gt;\r\n" // //
								+ "     &lt;enumeration value=\"SanzioniOmessa\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"SanzioniLiquidazione\"/&gt;\r\n" //
								+ "     &lt;enumeration value=\"Addizionali\"/&gt;\r\n" // //
								+ "   &lt;/restriction&gt;\r\n" + " * &lt;/simpleType&gt;\r\n" //
								+ " </pre>")); //
	p.put(EntraNextConnector.class.getName(), ret);
    }

    private static void inizializzaFvgPay() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroAggiungiGiorniADataScadenzaAvviso("Aggiungi giorni a scadenza avviso", ""));
	ret.add(new ParametroCodiceTassonomia("Codice tassonomia", ""));
	ret.add(new ParametroNumeroAccertamento(ACCERTAMENTO, ""));
	ret.add(new ParametroDatiRiscossione(CODICE_VOCE, ""));
	p.put(FvgPayConnector.class.getName(), ret);
    }

    private static void inizializzaGovPay() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroIdUnitaOperativa("Id Unità Operativa", "L'identificativo della unità operativa"));
	p.put(GovPayConnector.class.getName(), ret);
    }

    private static void inizializzaIrisConnector() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroChiaveApplicationCodeIUV(IDENTIFICATIVO_PER_CALCOLO_IUV,
		SERVE_PER_LA_GENERAZIONE_DELLO_IUV_INDICARE_L_ID_DELLA_CAUSALE));
	ret.add(new ParametroNumeroAccertamento(ACCERTAMENTO, ""));
	ret.add(new ParametroNumeroSottoAccertamento(CAPITOLO_BILANCIO, ""));
	ret.add(new ParametroDatiRiscossione(CODICE_VOCE, ""));
	p.put(IrisPayConnector.class.getName(), ret);
    }

    private static void inizializzaJCityGov() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroInviaDettagliPagamento("Invia il dettaglio Importo",
		"Se inviare il dettaglio immporto durante la composizione delle voci. Es: dettagli importo non li inserisco per Trieste in quanto è configurato un solo accertamento . Valore ammesso SI"));
	ret.add(new ParametroNumeroAccertamento("Codice dettaglio Importo", ""));
	ret.add(new ParametroCodiceServizio("Codice Servizio",
		"Indicare il codice servizio delle richieste. RICHIESTASTANDARD.CODICESERVIZIO. Il parametro e' obbligatorio"));
	ret.add(new ParametroNumeroSottoAccertamento(CAPITOLO_BILANCIO, ""));
	ret.add(new ParametroChiaveApplicationCodeIUV(IDENTIFICATIVO_PER_CALCOLO_IUV,
		SERVE_PER_LA_GENERAZIONE_DELLO_IUV_INDICARE_L_ID_DELLA_CAUSALE));
	p.put(JCityGovConnector.class.getName(), ret);
    }

    private static void inizializzaMipConnector() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroNumeroAccertamento("Identificativo importo contabile", ""));
	p.put(MIPConnector.class.getName(), ret);
    }

    private static void inizializzaMockConnector() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, ""));
	p.put(MockPayConnector.class.getName(), ret);
    }

    private static void inizializzaOpenWeb() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroAnnoAccertamento(ANNO_ACCERTAMENTO, INDICARE_L_ANNO_DI_ACCERTAMENTO));
	ret.add(new ParametroNumeroAccertamento(NUMERO_ACCERTAMENTO, INDICARE_IL_NUMERO_DI_ACCERTAMENTO));
	ret.add(new ParametroDatiRiscossione("Tipo dovuto", ""));
	p.put(OpenWebConnector.class.getName(), ret);
    }

    private static void inizializzaPagoUmbria() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroNumeroAccertamento(ACCERTAMENTO, ""));
	ret.add(new ParametroNumeroSottoAccertamento(CAPITOLO_BILANCIO, ""));
	ret.add(new ParametroDatiRiscossione(CODICE_VOCE, ""));
	p.put(PagoUmbriaPayConnector.class.getName(), ret);
    }

    private static void inizializzaPayer() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroCodiceTassonomia("Codice Tassonomia", "Indicare il codice tassonomia"));
	p.put(PayerConnector.class.getName(), ret);
    }

    private static void inizializzaPlugAndPay() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroNumeroAccertamento("Numero Accertamento", ""));
	ret.add(new ParametroAnnoAccertamento(ANNO_ACCERTAMENTO, INDICARE_L_ANNO_DI_ACCERTAMENTO));
	ret.add(new ParametroChiaveApplicationCodeIUV(IDENTIFICATIVO_PER_CALCOLO_IUV,
		SERVE_PER_LA_GENERAZIONE_DELLO_IUV_INDICARE_L_ID_DELLA_CAUSALE));
	p.put(PlugAndPayConnector.class.getName(), ret);
    }

    private static void inizializzaPiemontePay() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroAnnoAccertamento(ANNO_ACCERTAMENTO, INDICARE_L_ANNO_DI_ACCERTAMENTO));
	ret.add(new ParametroNumeroAccertamento(NUMERO_ACCERTAMENTO, INDICARE_IL_NUMERO_DI_ACCERTAMENTO));
	ret.add(new ParametroDatiRiscossione("Dati specifici riscossione", ""));
	p.put(PiemontePayConnector.class.getName(), ret);
    }

    private static void inizializzaPiemontePayRest() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroAnnoAccertamento(ANNO_ACCERTAMENTO, INDICARE_L_ANNO_DI_ACCERTAMENTO));
	ret.add(new ParametroNumeroAccertamento(NUMERO_ACCERTAMENTO, INDICARE_IL_NUMERO_DI_ACCERTAMENTO));
	ret.add(new ParametroDatiRiscossione("Dati specifici riscossione", ""));
	p.put(PiemontePayRestConnector.class.getName(), ret);
    }

    private static void inizializzaSilfi() {

	List<IParameter> ret = new ArrayList<>();
	ret.add(new ParametroDescrizioneCausalePSP(DESCRIZIONE_CAUSALE, INDICARE_LA_DESCRIZIONE_DELLA_CAUSALE_COME_SE_LO_ASPETTA));
	ret.add(new ParametroDataScadenza("Data scadenza", "Indicare la data scadenza per impostare un valore fisso di scadenza, Non obbligatorio"));
	ret.add(new ParametroDataScadenzaStampabile("Data scadenza stampabile",
		"Viene stampato sull'avviso al posto della data scadenza, non obbligatorio, es. 30 giorni dalla ricezione dell'avviso"));
	ret.add(new ParametroNumeroAccertamento("Codice Accertamento", ""));
	ret.add(new ParametroNumeroSottoAccertamento("Codice Capitolo", ""));
	ret.add(new ParametroDatiRiscossione("Codice Entrata", ""));
	ret.add(new ParametroCodiceServizio("Codice Servizio",
		"Va indicato il codice Servizio come da scheda di configurazione. Il dato è obbligatorio. Il codice Servizio va in accoppiata con il codice Ente. Per i pagamenti OTF non è possibile usare due servizi differenti."));
	p.put(SilfiPayConnector.class.getName(), ret);
    }
}
