package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.TracciatiService;
import it.gruppoinit.pal.gp.core.service.helper.ProprietaTracciatoDebitoBean;
import it.gruppoinit.pal.gp.core.service.helper.ProprietaTracciatoLottoBean;
import it.gruppoinit.pal.gp.core.service.helper.ProprietaTracciatoRataBean;
import it.gruppoinit.pal.gp.core.service.helper.ProprietaTracciatoRipartizioneRataBean;
import it.gruppoinit.pal.gp.core.service.helper.TracciatoDebitoBassilichi;
import it.gruppoinit.pal.gp.core.service.helper.TracciatoLottoBassilichi;
import it.gruppoinit.pal.gp.core.service.helper.TracciatoRataBassilichi;
import it.gruppoinit.pal.gp.core.service.helper.TracciatoRipartizioneRataBassilichi;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TracciatiServiceImpl implements TracciatiService {

    private static final Logger log = LoggerFactory.getLogger(TracciatiService.class.getName());
    private static final String BASSILICHI_VERSIONE_SPECIFICHE = "04.03";
    private RegistrazioniService registrazioniService;
    private RegistrazioniImportiService registrazioniImportiService;
    private DecimalFormat formatCurrency;
    private DecimalFormat printableFormatCurrency;

    @Autowired
    public void setRegistrazioniImportiService(RegistrazioniImportiService registrazioniImportiService) {

	this.registrazioniImportiService = registrazioniImportiService;
    }

    @Autowired
    public void setRegistrazioniService(RegistrazioniService registrazioniService) {

	this.registrazioniService = registrazioniService;
    }

    @Override
    public List<File> creaTracciatiRegistrazioni(List<Integer> codiciRegistrazioni, File tempFolder) {

	try {
	    List<File> result = new ArrayList<File>(2);
	    File pdfFolder = new File(tempFolder.getParentFile(), "pdfs");
	    if (!pdfFolder.exists()) {
		pdfFolder.mkdirs();
	    }
	    String datacreazionelotto = Utilities.formatDate(Calendar.getInstance().getTime(), "yyyy-MM-dd");
	    String dataFile = Utilities.formatDate(Calendar.getInstance().getTime(), "yyyyMMddHHmm");
	    String anno = Utilities.formatDate(Calendar.getInstance().getTime(), "yyyy");
	    String dataSenzaOreMinuti = Utilities.formatDate(Calendar.getInstance().getTime(), "yyyyMMdd");
	    String identificativoLotto = ORMHelper.getIdcomuneAlias() + ORMHelper.getSoftware() + "-" + dataSenzaOreMinuti;
	    String nomeDocumentoLotto = dataSenzaOreMinuti + "_" + identificativoLotto + ".PDF";
	    StringBuilder sbDebito = new StringBuilder();
	    StringBuilder sbRata = new StringBuilder();
	    StringBuilder sbRipartizioneRata = new StringBuilder();
	    StringBuilder sbLotto = new StringBuilder();
	    BigDecimal importo = BigDecimal.ZERO;
	    String tipologiaEntrata = null;
	    int numRighe = 0;
	    for (Integer codiceRegistrazione : codiciRegistrazioni) {
		Registrazioni reg = registrazioniService.findById(new PkId(codiceRegistrazione));
		tipologiaEntrata = reg.getRegistrazioniCausali().getDescrizione();
		importo = importo.add(reg.getImporto());
		log.debug("=========================={}", reg.getProgressivo());
		log.debug("importo: {}", reg.getImporto());
		log.debug("importo somma: {}", importo);
		List<RegistrazioniImporti> importi = registrazioniImportiService.findByRegistrazione(reg);
		String nomeDocumentoDebito = writeBassilichiDebito(sbDebito, reg, importi, dataSenzaOreMinuti, identificativoLotto);
		writePdf(reg, tempFolder, dataSenzaOreMinuti, nomeDocumentoDebito, pdfFolder);
		writeBassilichiRata(sbRata, reg, importi);
		writeBassilichiRipartizioneRate(sbRipartizioneRata, reg, importi);
		numRighe++;
	    }
	    //TODO FILE PDF LOTTO
	    String nomeFile = tipologiaEntrata + "_" + anno + "_" + dataFile;
	    // E ZIPPALI POI SPOSTA LO ZIP SULLA ROOT DA ZIPPARE
	    File lotto = new File(tempFolder, nomeFile + "_Lotto.txt");
	    File debito = new File(tempFolder, nomeFile + "_Debito.txt");
	    File rata = new File(tempFolder, nomeFile + "_Rata.txt");
	    File ripartizioneRate = new File(tempFolder, nomeFile + "_Ripartizione.txt");
	    writeBassilichiLotto(sbLotto, importo, datacreazionelotto, tipologiaEntrata, identificativoLotto, numRighe, nomeDocumentoLotto);
	    File pdfLotto = writePdfLotto(pdfFolder, importo, datacreazionelotto, tipologiaEntrata, identificativoLotto, numRighe, nomeDocumentoLotto);
	    File pdfZip = zipAndmovePdf(pdfFolder, tempFolder, nomeFile);
	    BufferedWriter writer = new BufferedWriter(new FileWriter(lotto));
	    writer.write(sbLotto.toString());
	    writer.close();
	    writer = new BufferedWriter(new FileWriter(debito));
	    writer.write(sbDebito.toString());
	    writer.close();
	    writer = new BufferedWriter(new FileWriter(rata));
	    writer.write(sbRata.toString());
	    writer.close();
	    writer = new BufferedWriter(new FileWriter(ripartizioneRate));
	    writer.write(sbRipartizioneRata.toString());
	    writer.close();
	    result.add(pdfZip);
	    result.add(debito);
	    result.add(rata);
	    result.add(lotto);
	    result.add(ripartizioneRate);
	    result.add(pdfLotto);
	    return result;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private File writePdfLotto(File tempFolder, BigDecimal importo, String datacreazionelotto, String tipologiaEntrata, String identificativoLotto,
	    int numRighe, String nomeDocumentoLotto) {

	File result = new File(tempFolder, nomeDocumentoLotto);
	StringBuffer str = new StringBuffer();
	str.append("<html><style>body{font-face: Verdana, Arial; font-size: 10px;}</style><body>");
	str.append("<div>Lotto id:");
	str.append(identificativoLotto);
	str.append("<br />Tipologia Entrata:");
	str.append(tipologiaEntrata);
	str.append("<br />Euro ");
	str.append(getPrintableFormatter().format(importo.doubleValue()));
	str.append("</div>");
	str.append("</body></html>");
	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	ConvertBinaryRequest req = new ConvertBinaryRequest();
	req.setToken(System.currentTimeMillis() + ""); // token non necessario
	req.setConversionType(FileConverterWsClient.ConversionType.PDF.name());
	req.setContentType(FileConverterWsClient.ConversionType.HTML.name());
	req.setBinaryData(str.toString().getBytes());
	ConvertBinaryResponse resp = null;
	try {
	    resp = fileConverterWsClient.convertBinary(req);
	} catch (Exception e) {
	    throw new RuntimeException("Errore durante la conversione del file in PDF: " + e.getMessage(), e);
	}
	try {
	    FileOutputStream fos = new FileOutputStream(result);
	    fos.write(resp.getBinaryData());
	    fos.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return result;
    }

    private File zipAndmovePdf(File src, File dest, String nomeFile) {

	File zipFile = new File(dest, nomeFile + "_PDF.zip");
	OutputStream writeTo = null;
	try {
	    writeTo = new FileOutputStream(zipFile);
	    Utilities.zipTo(src, writeTo);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	return zipFile;
    }

    private void writePdf(Registrazioni reg, File tempFolder, String dataSenzaOreMinuti, String nomeDocumentoDebito, File pdfFolder) {

	// SCRIVI IN UNA FOLDER A PARTE 
	StringBuffer str = new StringBuffer();
	str.append("<html><style>body{font-face: Verdana, Arial; font-size: 10px;}</style><body>");
	str.append("<div>");
	str.append(getDescrizioneRichiedente(reg.getAnagrafe()));
	if (StringUtils.isNotBlank(reg.getAnagrafe().getCodicefiscale())) {
	    str.append(" (").append(StringUtils.defaultString(reg.getAnagrafe().getCodicefiscale())).append(")");
	}
	str.append("<br />");
	str.append(reg.getDescrizione());
	str.append("<br />Euro ");
	str.append(getImportoText(reg));
	str.append("</div>");
	str.append("</body></html>");
	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	ConvertBinaryRequest req = new ConvertBinaryRequest();
	req.setToken(System.currentTimeMillis() + ""); // token non necessario
	req.setConversionType(FileConverterWsClient.ConversionType.PDF.name());
	req.setContentType(FileConverterWsClient.ConversionType.HTML.name());
	req.setBinaryData(str.toString().getBytes());
	ConvertBinaryResponse resp = null;
	try {
	    resp = fileConverterWsClient.convertBinary(req);
	} catch (Exception e) {
	    throw new RuntimeException("Errore durante la conversione del file in PDF: " + e.getMessage(), e);
	}
	try {
	    FileOutputStream fos = new FileOutputStream(new File(pdfFolder, nomeDocumentoDebito + ".pdf"));
	    fos.write(resp.getBinaryData());
	    fos.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void writeBassilichiLotto(StringBuilder sbLotto, BigDecimal importo, String datacreazionelotto, String tipologiaEntrata,
	    String identificativoLotto, int numRighe, String nomeDocumentoLotto) {

	TracciatoLottoBassilichi t = new TracciatoLottoBassilichi();
	List<ProprietaTracciatoLottoBean> proprieta = t.getProprieta();
	for (ProprietaTracciatoLottoBean ptb : proprieta) {
	    switch (ptb.getName()) {
	    case tipo_operazione:
		ptb.setValore("I");
		break;
	    case tipo_codice_ente:
		ptb.setValore("3");
		break;
	    case codice_ente:
		ptb.setValore(ORMHelper.getIdcomune());
		break;
	    case tipologia_entrata:
		ptb.setValore(tipologiaEntrata);
		break;
	    case identificativo_lotto:
		ptb.setValore(identificativoLotto);
		break;
	    case data_creazione_lotto:
		ptb.setValore(datacreazionelotto);
		break;
	    case numero_totale_debiti:
	    case numero_totale_rate:
		ptb.setValore(String.valueOf(numRighe));
		break;
	    case importo_totale_debiti:
	    case importo_totale_rate:
		ptb.setValore(getImportoTotale(importo));
		log.debug("\t{}\t{}", ptb.getName().name(), getImportoTotale(importo));
		break;
	    case tipologia_documento_di_pag_da_emettere:
		ptb.setValore("00000020000000000000");
		break;
	    case flag_accorpamento_debiti:
		ptb.setValore("1");
		break;
	    case nome_documento_lotto:
		ptb.setValore(nomeDocumentoLotto);
		break;
	    case numero_facciate_documento_lotto:
		ptb.setValore("1");
		break;
	    case flag_fronte_retro_lotto:
		ptb.setValore("0");
		break;
	    case flag_bianco_nero_colore_lotto:
		ptb.setValore("0");
		break;
	    case tipo_postalizzazione:
		ptb.setValore("1");
		break;
	    case identificativo_vettore:
		ptb.setValore("1");
		break;
	    case prima_parte_denominazione_creditore_mitt:
	    case seconda_parte_denominazione_creditore_mitt:
	    case indirizzo_creditore_mittente:
	    case completamento_indirizzo_creditore_mittente:
	    case cap_creditore_mittente:
	    case comune_e_provincia_creditore_mittente:
	    case sigla_provincia_creditore_mittente:
	    case prima_parte_denominazione_ente:
	    case seconda_parte_denominazione_ente:
	    case iban_conto_corrente_postale:
	    case autorizzazione_bollettino_postale:
	    case iban_conto_corrente_bancario:
	    case indirizzo_ente:
	    case cap_localita_provincia_ente:
	    case telefono_ente:
	    case municipio:
		break;
	    //
	    case filler:
		break;
	    case versione_specifiche:
		ptb.setValore(BASSILICHI_VERSIONE_SPECIFICHE);
		break;
	    }
	}
	sbLotto.append(t.writeRiga(true));
    }

    private void writeBassilichiRipartizioneRate(StringBuilder sbRata, Registrazioni reg, List<RegistrazioniImporti> importi) {
	
	TracciatoRipartizioneRataBassilichi t = new TracciatoRipartizioneRataBassilichi();
	List<ProprietaTracciatoRipartizioneRataBean> proprieta = t.getProprieta();
	Date dataScadenza = null;
	Conti conto = null;
	for (RegistrazioniImporti ri : importi) { // FIXME
	    dataScadenza = ri.getScadenza();
	    conto = ri.getConti();
	    break;
	}
	if (dataScadenza == null) {
	    dataScadenza = reg.getDataRegistrazione();
	}
	for (ProprietaTracciatoRipartizioneRataBean ptb : proprieta) {
	    switch (ptb.getName()) {
	    case tipo_operazione:
		ptb.setValore("I");
		break;
	    case tipo_codice_ente:
		ptb.setValore("3");
		break;
	    case codice_ente:
		ptb.setValore(ORMHelper.getIdcomune());
		break;
	    case tipologia_entrata:
		ptb.setValore(reg.getRegistrazioniCausali().getDescrizione());
		break;
	    case anno_debito:
		ptb.setValore(String.valueOf(reg.getAnno()));
		break;
	    case identificativo_debito:
		ptb.setValore(getRiferimentoDebito(reg.getProgressivo()));
		break;
	    case numero_rata:
		ptb.setValore("0");
		break;
	    case identificativo_rata:
		ptb.setValore(getRiferimentoDebito(reg.getProgressivo()));
		break;
	    case tipo_ripartizione:
		ptb.setValore("A");
		break;
	    case codice_ripartizione:
		throw new NotImplementedException("Non implementato");
		// ptb.setValore(conto.getCodiceconto());
		// break;
	    case anno_riferimento_ripartizione:
		throw new NotImplementedException("Non implementato");
		// ptb.setValore(conto.getCodicesottoconto()); // anno
		// break;
	    case numero_sub_accertamento:
		break;
	    case importo_per_ripartizione:
		ptb.setValore(getImportoTotale(reg.getImporto()));
		break;
	    case flag_bollo:
		break;
	    case filler:
		break;
	    case versione_specifiche:
		ptb.setValore(BASSILICHI_VERSIONE_SPECIFICHE);
		break;
	    }
	}
	sbRata.append(t.writeRiga(true));
    }

    private void writeBassilichiRata(StringBuilder sbRata, Registrazioni reg, List<RegistrazioniImporti> importi) {

	TracciatoRataBassilichi t = new TracciatoRataBassilichi();
	List<ProprietaTracciatoRataBean> proprieta = t.getProprieta();
	Date dataScadenza = null;
	for (RegistrazioniImporti ri : importi) { // FIXME
	    dataScadenza = ri.getScadenza();
	    break;
	}
	if (dataScadenza == null) {
	    dataScadenza = reg.getDataRegistrazione();
	}
	for (ProprietaTracciatoRataBean ptb : proprieta) {
	    switch (ptb.getName()) {
	    case tipo_operazione:
		ptb.setValore("I");
		break;
	    case tipo_codice_ente:
		ptb.setValore("3");
		break;
	    case codice_ente:
		ptb.setValore(ORMHelper.getIdcomune());
		break;
	    case tipologia_entrata:
		ptb.setValore(reg.getRegistrazioniCausali().getDescrizione());
		break;
	    case anno_debito:
		ptb.setValore(String.valueOf(reg.getAnno()));
		break;
	    case identificativo_debito:
		ptb.setValore(getRiferimentoDebito(reg.getProgressivo()));
		break;
	    case numero_rata:
		ptb.setValore("0");
		break;
	    case identificativo_rata:
		ptb.setValore(getRiferimentoDebito(reg.getProgressivo()));
		break;
	    case data_scadenza:
		ptb.setValore(Utilities.formatDate(dataScadenza, ptb.getFormat()));
		break;
	    case descrizione_rata:
		break;
	    case importo_da_pagare:
		ptb.setValore(getImportoTotale(reg.getImporto()));
		log.debug("\tRATA\t{}\t{}", ptb.getName().name(), getImportoTotale(reg.getImporto()));
		break;
	    case importo_nominale:
	    case importo_spese_supplementari:
	    case descrizione_spese_supplementari:
		break;
	    case flag_pagabile:
		ptb.setValore("1");
		break;
	    case codice_identificativo_mav:
	    case filler:
		break;
	    case versione_specifiche:
		ptb.setValore(BASSILICHI_VERSIONE_SPECIFICHE);
		break;
	    }
	}
	sbRata.append(t.writeRiga(true));
    }

    private String writeBassilichiDebito(StringBuilder sbDebito, Registrazioni reg, List<RegistrazioniImporti> importi, String dataSenzaOreMinuti,
	    String identificativoLotto) {

	String nomeDocumentoDebito = dataSenzaOreMinuti + "_" + getRiferimentoDebito(reg.getProgressivo()) + ".PDF";
	TracciatoDebitoBassilichi t = new TracciatoDebitoBassilichi();
	List<ProprietaTracciatoDebitoBean> proprieta = t.getProprieta();
	for (ProprietaTracciatoDebitoBean ptb : proprieta) {
	    switch (ptb.getName()) {
	    case tipo_operazione:
		ptb.setValore("I");
		break;
	    case tipo_codice_ente:
		ptb.setValore("3");
		break;
	    case codice_ente:
		ptb.setValore(ORMHelper.getIdcomune());
		break;
	    case tipologia_entrata:
		ptb.setValore(reg.getRegistrazioniCausali().getDescrizione());
		break;
	    case anno_debito:
		ptb.setValore(String.valueOf(reg.getAnno()));
		break;
	    case identificativo_debito:
		ptb.setValore(getRiferimentoDebito(reg.getProgressivo()));
		break;
	    case data_emissione_debito:
		ptb.setValore(Utilities.formatDate(reg.getDataRegistrazione(), ptb.getFormat()));
		break;
	    case numero_pratica_protocollo:
		ptb.setValore("");
		break;
	    case tipo_codice_intestatario:
		ptb.setValore("3");
		break;
	    case codice_intestatario:
		ptb.setValore(getPivaOCF(reg.getAnagrafe()));
		break;
	    case cognome_nome_intestatario:
		ptb.setValore(getDescrizioneRichiedente(reg.getAnagrafe()));
		break;
	    case tipo_codice_debitore:
		ptb.setValore("3");
		break;
	    case codice_debitore:
		ptb.setValore(getPivaOCF(reg.getAnagrafe()));
		break;
	    case codice_fiscale_debitore:
		ptb.setValore(getPivaOCF(reg.getAnagrafe()));
		break;
	    case prima_parte_denominazione_debitore:
		ptb.setValore(getDescrizioneRichiedente(reg.getAnagrafe()));
		break;
	    case seconda_parte_denominazione_debitore:
		ptb.setValore("");
		break;
	    case terza_parte_denominazione_debitore:
		ptb.setValore("");
		break;
	    case indirizzo_debitore:
		ptb.setValore(getIndirizzo(reg.getAnagrafe()));
		break;
	    case completamento_indirizzo_debitore:
		ptb.setValore("");
		break;
	    case cap_debitore:
		ptb.setValore(reg.getAnagrafe().getCap());
		break;
	    case comune_provincia_debitore:
		ptb.setValore(getComuneEProvincia(reg.getAnagrafe()));
		break;
	    case codice_istat_debitore:
		ptb.setValore(getCodiceistat(reg.getAnagrafe()));
		break;
	    case sigla_provincia_debitore:
		ptb.setValore("");
		break;
	    case codice_paese_debitore:
		ptb.setValore("");
		break;
	    case importo_totale:
		ptb.setValore(getImportoTotale(reg.getImporto()));
		log.debug("\tDEBITO\t{}\t{}", ptb.getName().name(), getImportoTotale(reg.getImporto()));
		break;
	    case modalita_spedizione:
		ptb.setValore("A");
		break;
	    case flag_pagamento:
		ptb.setValore("1");
		break;
	    case flag_rateizzazione:
		ptb.setValore("1");
		break;
	    case numero_rate:
		ptb.setValore("00");
		break;
	    case flag_ripartizione:
		ptb.setValore("C");
		break;
	    case flag_accorpamento:
		ptb.setValore("S");
		break;
	    case documenti_pagamento_da_generare:
		break;
	    case descrizione:
		ptb.setValore(getDescrizione(reg));
		break;
	    case codice_abi_conto_debitore:
		break;
	    case codice_cab_conto_debitore:
		break;
	    case conto_debitore:
		break;
	    case nome_documento_debito:
		ptb.setValore(nomeDocumentoDebito);
		break;
	    case numero_facciate_documento_debito:
		ptb.setValore("1");
		break;
	    case flag_presenza_disposizione:
		break;
	    case flag_presenza_indirizzo:
		ptb.setValore("1");
		break;
	    case nome_documento_allegato:
	    case numero_facciate_documento_allegato:
		break;
	    case indirizzo_email_debitore:
		ptb.setValore(reg.getAnagrafe().getEmail());
		break;
	    case identificativo_lotto:
		ptb.setValore(identificativoLotto);
		break;
	    case causale_versamento:
		ptb.setValore(reg.getDescrizione());
		break;
	    case filler:
		break;
	    case versione_specifiche:
		ptb.setValore(BASSILICHI_VERSIONE_SPECIFICHE);
		break;
	    }
	}
	sbDebito.append(t.writeRiga(true));
	return nomeDocumentoDebito;
    }

    private String getRiferimentoDebito(String progressivo) {

	return progressivo.replaceAll("\\/", "-");
    }

    private String getDescrizione(Registrazioni reg) {

	String result = "idPagamento: " + getRiferimentoDebito(reg.getProgressivo()) + ", descrizione:"
		+ reg.getDescrizione().replaceAll("\n", " ").replaceAll("\r", "") + ", importo: euro " + getImportoText(reg);
	return result;
    }

    private String getImportoText(Registrazioni reg) {

	return getPrintableFormatter().format(reg.getImporto().doubleValue());
    }

    private String getImportoTotale(BigDecimal importo) {

	return formatImporto(importo); //formatCurrency.format(importo.doubleValue());
    }

    private String formatImporto(BigDecimal importo) {

	String importoString = getFormatter().format(importo.doubleValue());
	return importoString.replaceAll(",", "").replaceAll("\\.", "");
    }

    public static void main(String[] args) {

	BigDecimal importo = BigDecimal.valueOf(12345.0);
	TracciatiServiceImpl t = new TracciatiServiceImpl();
	System.out.println(t.getPrintableFormatter().format(importo.doubleValue()));
	System.out.println(t.formatImporto(importo));
	importo = BigDecimal.valueOf(12345.678);
	System.out.println(t.getPrintableFormatter().format(importo.doubleValue()));
	System.out.println(t.formatImporto(importo));
	String dataFile = Utilities.formatDate(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
	System.out.println(dataFile);
    }

    private DecimalFormat getFormatter() {

	if (formatCurrency == null) {
	    formatCurrency = new DecimalFormat("0.00");
	}
	return formatCurrency;
    }

    private DecimalFormat getPrintableFormatter() {

	if (printableFormatCurrency == null) {
	    DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
	    unusualSymbols.setDecimalSeparator(',');
	    unusualSymbols.setGroupingSeparator('.');
	    String strange = "#,##0.00";
	    printableFormatCurrency = new DecimalFormat(strange, unusualSymbols);
	    printableFormatCurrency.setGroupingSize(3);
	}
	return printableFormatCurrency;
    }

    private String getCodiceistat(Anagrafe anagrafe) {

	String res = "";
	if (anagrafe.getComuneResidenza() != null) {
	    res = anagrafe.getComuneResidenza().getCodiceistat();
	}
	return res;
    }

    private String getComuneEProvincia(Anagrafe anagrafe) {

	String res = "";
	if (anagrafe.getComuneResidenza() != null) {
	    res = anagrafe.getComuneResidenza().getComune();
	    res += " " + anagrafe.getComuneResidenza().getSiglaprovincia();
	}
	return res;
    }

    private String getIndirizzo(Anagrafe anagrafe) {

	String indirizzo = StringUtils.defaultString(anagrafe.getIndirizzo()) + " " + StringUtils.defaultString(anagrafe.getCitta());
	return indirizzo;
    }

    private String getPivaOCF(Anagrafe anagrafe) {

	if (StringUtils.isNotBlank(anagrafe.getPartitaiva()) && (anagrafe.getPartitaiva().length() == 11 || anagrafe.getPartitaiva().length() == 16)) {
	    return anagrafe.getPartitaiva();
	}
	return anagrafe.getCodicefiscale();
    }

    private String getDescrizioneRichiedente(Anagrafe anagrafe) {

	String descrizione = StringUtils.defaultString(anagrafe.getNominativo());
	if (anagrafe.getTipoanagrafe().equals(WebConstants.PERSONA_FISICA)) {
	    if (StringUtils.isNotBlank(anagrafe.getNome())) {
		descrizione += "|" + anagrafe.getNome();
	    }
	    return descrizione;
	}
	if (StringUtils.isNotBlank(anagrafe.getPartitaiva()) && anagrafe.getPartitaiva().length() == 11) {
	    return descrizione;
	}
	descrizione = descrizione.replaceFirst(" ", "|");
	return descrizione;
    }
}
