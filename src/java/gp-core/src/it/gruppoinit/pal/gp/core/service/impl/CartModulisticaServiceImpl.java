package it.gruppoinit.pal.gp.core.service.impl;

import it.eng.suap.xengine.model.modulistica.AlfabeticoType;

import it.eng.suap.xengine.model.modulistica.CFPFPatternType;
import it.eng.suap.xengine.model.modulistica.CFPersonaFisicaType;
import it.eng.suap.xengine.model.modulistica.CFPersonaGiuridicaType;
import it.eng.suap.xengine.model.modulistica.CalendarioType;
import it.eng.suap.xengine.model.modulistica.CampoType;
import it.eng.suap.xengine.model.modulistica.ColonnaType;
import it.eng.suap.xengine.model.modulistica.EnumItemType;
import it.eng.suap.xengine.model.modulistica.EnumerazioneType;
import it.eng.suap.xengine.model.modulistica.EspressioneRegolareType;
import it.eng.suap.xengine.model.modulistica.EspressioneType;
import it.eng.suap.xengine.model.modulistica.FileType;
import it.eng.suap.xengine.model.modulistica.ItemType;
import it.eng.suap.xengine.model.modulistica.ModulisticaType;
import it.eng.suap.xengine.model.modulistica.ModuloType;
import it.eng.suap.xengine.model.modulistica.NumericoType;
import it.eng.suap.xengine.model.modulistica.PartitaIVAType;
import it.eng.suap.xengine.model.modulistica.QuadroType;
import it.eng.suap.xengine.model.modulistica.RangeDataType;
import it.eng.suap.xengine.model.modulistica.RangeType;
import it.eng.suap.xengine.model.modulistica.SetValoriType;
import it.eng.suap.xengine.model.modulistica.SezioneType;
import it.eng.suap.xengine.model.modulistica.TabellaType;
import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.fileconverter.ConvertRequest;
import it.gruppoinit.fileconverter.ConvertResponse;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerApplication;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerConfig;
import it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.DatiModulo;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.MessaggioErrore;
import it.gruppoinit.pal.gp.core.domain.cart.ModuloRendering;
import it.gruppoinit.pal.gp.core.domain.cart.ModuloWrapper;
import it.gruppoinit.pal.gp.core.domain.cart.ValoreIdSemantico;
import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;
import it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AutocompilerConfigurationService;
import it.gruppoinit.pal.gp.core.service.CartModulisticaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.sigepro.cart.service.fru.CartRfc186ModelliSUAPServiceClient;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;
import it.init.sigepro.rte.types.ErroreType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class CartModulisticaServiceImpl implements CartModulisticaService {

	private static final Logger log = LoggerFactory.getLogger(CartModulisticaServiceImpl.class);
	private static final SimpleDateFormat rangeDF = new SimpleDateFormat(FACCTConstants.RFC186_RANGE_DATE_FORMAT);
	// private static final int MAX_PORTRAIT_WIDTH = 1000;
	@Autowired
	private VelocityEngine templateEngine;
	private FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	@Autowired
	private VerticalizzazioniService verticalizzazioniService;
	@Autowired
	private ComuniService comuniService;
	@Autowired
	private AutocompilerConfigurationService autocompilerConfigurationService;
	@Autowired
	private OggettiService oggettiService;
	@Autowired
	private EndoRegioneToscanaService endoRegioneToscanaService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private CartRfc186ModelliSUAPServiceClient modulisticaRfc186Service;

	/**
	 * @return the fileConverterWsClient
	 */
	public FileConverterWsClient getFileConverterWsClient() {

		return fileConverterWsClient;
	}

	/**
	 * @param fileConverterWsClient
	 *            the fileConverterWsClient to set
	 */
	public void setFileConverterWsClient(FileConverterWsClient fileConverterWsClient) {

		this.fileConverterWsClient = fileConverterWsClient;
	}

	/**
	 * @return the templateEngine
	 */
	public VelocityEngine getTemplateEngine() {

		return templateEngine;
	}

	/**
	 * @param templateEngine
	 *            the templateEngine to set
	 */
	public void setTemplateEngine(VelocityEngine templateEngine) {

		this.templateEngine = templateEngine;
	}

	@Override
	public List<ModuloRendering> renderModulistica(DatiDomandaCart inputData, Map<Object, Object> contextData) {

		List<ModuloRendering> retData = new ArrayList<ModuloRendering>();
		// §§§BEGIN§§§
		ModuloType modulo = null;
		ModulisticaContentType modulistica = inputData.getModulistica();
		CartModuloHelper prevModuloHelper = null;
		if (modulistica != null && modulistica.getModulistica() != null) {
			AutocompilerConfig autocompilerConfig = this.autocompilerConfigurationService
					.getAutocompilerConfiguration(FACCTConstants.CFG_FILE_AUTOCOMPILERS);
			for (int i = 0; i < modulistica.getModulistica().getModulo().size(); i++) {
				modulo = modulistica.getModulistica().getModulo().get(i);
				if (modulo != null) {
					ModuloRendering modRender = new ModuloRendering();
					if (null == contextData) {
						contextData = new HashMap<Object, Object>();
					}
					CartModuloHelper moduloHelper = this.getModuloHelperInstance(modulo, inputData, autocompilerConfig);
					/*
					 * la cache per la generazione degli id univoci viene condivisa fra i vari moduli per garantire l'univocità degli id anche
					 * attraverso più moduli
					 */
					if (prevModuloHelper != null) {
						moduloHelper.useIdCache(prevModuloHelper.getIdCache());
					}
					contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_MODULO_HELPER, moduloHelper);
					contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_PRIMO_MODULO, i == 0);
					contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_PRIMO_QUADRO, true);
					contextData.put("esc", new EscapeTool());
					String htmlModulo = VelocityEngineUtils.mergeTemplateIntoString(this.templateEngine, "/cart/modulo.vm",
							FACCTConstants.DEFAULT_CHARSET, contextData);
					modRender.setHtml(htmlModulo);
					modRender.setName(modulo.getTitolo());
					modRender.setId(moduloHelper.getIdModulo());
					modRender.setRiferimento(CartModuloHelper.getRiferimentoModulo(modulo));
					modRender.setIdForJsVariablename(moduloHelper.getIdModuloForJsVariableName());
					StringBuilder sbHelp = new StringBuilder();
					if (StringUtils.isNotBlank(modulo.getNota())) {
						sbHelp.append(modulo.getNota());
					}
					if (StringUtils.isNotBlank(modulo.getHelp()) && !modulo.getHelp().equalsIgnoreCase(modulo.getNota())) {
						if (sbHelp.length() > 0) {
							sbHelp.append("\r\n");
						}
						sbHelp.append(modulo.getHelp());
					}
					modRender.setHelp(sbHelp.toString());
					retData.add(modRender);
					prevModuloHelper = moduloHelper;
				}
			}
		}
		// §§§END§§§
		return retData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.gruppoinit.pal.gp.cartfacct.service.ModulisticaService#renderModuloToFile (it.gruppoinit.pal.gp.cart.schema.modulistica.ModuloType,
	 * it.gruppoinit.pal.gp.cartfacct.domain.DatiDomandaCart, java.util.Map, java.io.File)
	 */
	@Override
	public void renderModuloToFile(ModuloType modulo, DatiDomandaCart inputData, Map<Object, Object> contextData, File outputFile) throws IOException {

		// §§§BEGIN§§§
		CartModuloHelper helper = getModuloHelperInstance(modulo, inputData, null);
		if (contextData == null) {
			contextData = new HashMap<Object, Object>();
		}
		contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_MODULO_HELPER, helper);
		contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_PRIMO_MODULO, true);
		contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_PRIMO_QUADRO, true);
		contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_PDF_OUTPUT, true);
		if (!outputFile.exists()) {
			if (log.isDebugEnabled())
				log.debug("renderModuloToFile() - il file di output {} non esisteva ed è stato creato", outputFile.getAbsolutePath());
			outputFile.createNewFile();
		}
		// VelocityEngineUtils.mergeTemplate(this.templateEngine,
		// "/pagina_modulo_pdf.vm", FACCTConstants.DEFAULT_CHARSET, contextData,
		// w);
		String htmlBytes = VelocityEngineUtils.mergeTemplateIntoString(this.templateEngine, "/cart/pagina_modulo_pdf.vm",
				FACCTConstants.DEFAULT_CHARSET, contextData);
		// TODO rimuovere le istruzioni successive che salvano in un file anche
		// la versione html intermedia
		/*
		 * String outputHtmlFileName = outputFile.getName().substring(0, outputFile.getName().lastIndexOf('.')).concat(".html"); File outputHtmlFile =
		 * new File(outputFile.getParent(), outputHtmlFileName); if (!outputHtmlFile.exists()) { outputHtmlFile.createNewFile(); } FileOutputStream
		 * fos = new FileOutputStream(outputHtmlFile); AttachmentsUtils.writeBytesToStream( htmlBytes.getBytes(FACCTConstants.DEFAULT_CHARSET), fos);
		 */
		// TODO fine istruzioni da rimuovere
		// converto il file HTML generato in un file PDF tramite il servizio
		// Fileconverter
		ConvertRequest creq = new ConvertRequest(ORMHelper.getToken(), htmlBytes, FileConverterWsClient.ContentType.HTML.name(),
				FileConverterWsClient.ConversionType.PDF.name());
		ConvertResponse cresp = getFileConverterWsClient().convert(creq);
		byte[] pdfBytes = cresp.getBinaryData();
		AttachmentsUtils.writeBytesToStream(pdfBytes, new FileOutputStream(outputFile));
		// §§§END§§§
	}

	@Override
	public List<ErroreValidazione> validazioneQuadro(DatiDomandaCart inputData, String refModulo, String idQuadro) {

		String errMsg = null;
		List<ErroreValidazione> errors = new ArrayList<ErroreValidazione>();
		// §§§BEGIN§§§
		ModulisticaContentType modulistica = inputData.getModulistica();
		QuadroType quadro = CartModuloHelper.findQuadro(modulistica, refModulo, idQuadro);
		if (null == quadro) {
			errMsg = MessageFormat.format("Impossibile trovare il quadro con id {0} all''interno del modulo {1}", idQuadro, refModulo);
			throw new RuntimeException(errMsg);
		} else {
			// recupero la configurazione degli autocompilers per info sulle
			// validazioni personalizzate
			AutocompilerConfig autocompilerConfig = this.autocompilerConfigurationService
					.getAutocompilerConfiguration(FACCTConstants.CFG_FILE_AUTOCOMPILERS);
			// valido tutti i campi del quadro
			List<ItemType> campiQuadro = quadro.getItem();
			for (ItemType item : campiQuadro) {
				IndiceIdSemantico indice = new IndiceIdSemantico();
				Boolean attivo = CartModuloHelper.evaluateExpression(quadro.getAttivo(), inputData, indice);
				validaCampoRecursive(item, idQuadro, refModulo, inputData, errors, 0, indice, attivo, null, autocompilerConfig);
			}
		}
		// §§§END§§§
		return errors;
	}

	@Override
	public List<ErroreValidazione> validazioneDomanda(DatiDomandaCart inputData) {

		List<ErroreValidazione> errors = new ArrayList<ErroreValidazione>();
		// §§§BEGIN§§§
		ModulisticaType modulilstica = inputData.getModulistica().getModulistica();
		List<ModuloType> moduli = modulilstica.getModulo();
		// recupero la configurazione degli autocompilers per info sulle
		// validazioni personalizzate
		AutocompilerConfig autocompilerConfig = this.autocompilerConfigurationService
				.getAutocompilerConfiguration(FACCTConstants.CFG_FILE_AUTOCOMPILERS);
		for (ModuloType modulo : moduli) {
			List<QuadroType> quadri = modulo.getQuadro();
			for (QuadroType quadro : quadri) {
				List<ItemType> campiQuadro = quadro.getItem();
				for (ItemType item : campiQuadro) {
					IndiceIdSemantico indice = new IndiceIdSemantico();
					Boolean attivo = CartModuloHelper.evaluateExpression(quadro.getAttivo(), inputData, indice);
					String refModulo = CartModuloHelper.getRiferimentoModulo(modulo);
					String idQuadro = CartModuloHelper.getIdQuadro(quadro);
					validaCampoRecursive(item, idQuadro, refModulo, inputData, errors, 0, indice, attivo, null, autocompilerConfig);
				}
			}
		}
		// §§§END§§§
		return errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.gruppoinit.pal.gp.cartfacct.service.ModulisticaService# impostaValoriDefault(java.lang.String,
	 * it.gruppoinit.pal.gp.cartfacct.domain.DatiDomandaCart)
	 */
	@Override
	public void impostaValoriDefault(String refModulo, DatiDomandaCart datiDomanda) {

		DatiModulo datiModulo = datiDomanda.getDatiModulo(refModulo);
		if (datiModulo == null) {
			datiModulo = new DatiModulo(refModulo);
			datiDomanda.getDatiModuli().add(datiModulo);
		}
		// ValoreIdSemantico vis = null;
		// GESTIONE DEL PARAMETRO CODICECOMUNE CHE COMPILA L'IDSEMANTICO
		// COMUNE_SUAP
		String actualValue = datiDomanda.getValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_COMUNE_SUAP);
		boolean recuperaComuneDaVert = true;
		String codiceComune = "";
		if (datiDomanda != null) {
			if (datiDomanda.getDatiContestoDomanda() != null) {
				if (StringUtils.isNotBlank(datiDomanda.getDatiContestoDomanda().getCodicecomune())) {
					recuperaComuneDaVert = false;
					codiceComune = datiDomanda.getDatiContestoDomanda().getCodicecomune();
				}
			}
		}
		Comuni foundComune = null;
		if (StringUtils.isBlank(actualValue)) {
			if (recuperaComuneDaVert) {
				Verticalizzazioniparametri paramVal = this.verticalizzazioniService.getVerticalizzazioniparametri(
						WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SUAP_ID);
				if (paramVal != null && StringUtils.isNotBlank(paramVal.getValore())) {
					int lastDotIndex = paramVal.getValore().lastIndexOf('.');
					if (lastDotIndex > -1) {
						String codIstat = paramVal.getValore().substring(lastDotIndex + 1);
						Comuni searchComune = new Comuni();
						searchComune.setCodiceistat(codIstat);
						foundComune = comuniService.findByComune(searchComune);
						if (null != foundComune) {
							// imposto valore id semantico COMUNE_SUAP
							datiDomanda.setValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_COMUNE_SUAP, foundComune.getComune());
						} else {
							if (log.isWarnEnabled())
								log.warn(
										"impostaValoriDefault() - impossibile impostare il valore di default per l'id semantico {} perchè non esiste nessun comune associato al codice istat {}",
										new Object[] { FACCTConstants.ID_SEMANTICO_COMUNE_SUAP, codIstat });
						}
					} else {
						if (log.isWarnEnabled())
							log.warn(
									"impostaValoriDefault() - impossibile impostare il valore di default per l'id semantico {} perchè è impossibile derivare il codice istat del comune dal valore {}",
									new Object[] { FACCTConstants.ID_SEMANTICO_COMUNE_SUAP, paramVal.getValore() });
					}
				} else {
					if (log.isWarnEnabled())
						log.warn(
								"impostaValoriDefault() - impossibile impostare il valore di default per l'id semantico {} perchè manca il parametro delle verticalizzazioni CART di nome {}",
								new Object[] { FACCTConstants.ID_SEMANTICO_COMUNE_SUAP, WebConstants.VERTICALIZZAZIONE_CART_SUAP_ID });
				}
			} else {
				foundComune = comuniService.findById(codiceComune);
				if (null != foundComune) {
					datiDomanda.setValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_COMUNE_SUAP, foundComune.getComune());
				} else {
					if (log.isWarnEnabled())
						log.warn(
								"impostaValoriDefault() - impossibile impostare il valore di default per l'id semantico {} perchè non esiste nessun comune associato al codice comune {}",
								new Object[] { FACCTConstants.ID_SEMANTICO_COMUNE_SUAP, codiceComune });
				}
			}
		} else {
			// se non è vuoto verifico se è stato modificato
			if (StringUtils.isNotBlank(codiceComune)) {
				foundComune = comuniService.findById(codiceComune);
				if (foundComune != null) {
					String nuovoValore = foundComune.getComune();
					datiDomanda.setValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_COMUNE_SUAP, nuovoValore);
				}
			}
		}
		if (null != foundComune) {
			ValoreIdSemantico vis1 = null;
			// imposto valore id semantico ATTIVITA.INDIRIZZO.COMUNE
			List<ValoreIdSemantico> vals = datiDomanda.getValoriIndicizzati(refModulo, FACCTConstants.ID_SEMANTICO_ATTIVITA_INDIRIZZO_COMUNE);
			if (vals == null) {
				vals = new ArrayList<ValoreIdSemantico>();
			}
			if (vals.size() == 0) {
				vis1 = new ValoreIdSemantico(foundComune.getComune());
				vals.add(vis1);
			} else {
				vis1 = vals.get(0);
				if (StringUtils.isEmpty(vis1.getValoreScalare())) {
					vis1.setValoreScalare(foundComune.getComune());
				}
			}
			datiDomanda.setValoriIndicizzati(refModulo, FACCTConstants.ID_SEMANTICO_ATTIVITA_INDIRIZZO_COMUNE, vals);
			// imposto valore id semantico ATTIVITA.INDIRIZZO.PROVINCIA
			vals = datiDomanda.getValoriIndicizzati(refModulo, FACCTConstants.ID_SEMANTICO_ATTIVITA_INDIRIZZO_PROVINCIA);
			if (vals == null) {
				vals = new ArrayList<ValoreIdSemantico>();
			}
			if (vals.size() == 0) {
				vis1 = new ValoreIdSemantico(foundComune.getProvincia());
				vals.add(vis1);
			} else {
				vis1 = vals.get(0);
				if (StringUtils.isEmpty(vis1.getValoreScalare())) {
					vis1.setValoreScalare(foundComune.getProvincia());
				}
			}
			datiDomanda.setValoriIndicizzati(refModulo, FACCTConstants.ID_SEMANTICO_ATTIVITA_INDIRIZZO_PROVINCIA, vals);
			// imposto valore id semantico ATTIVITA.INDIRIZZO.CAP
			vals = datiDomanda.getValoriIndicizzati(refModulo, FACCTConstants.ID_SEMANTICO_ATTIVITA_INDIRIZZO_CAP);
			if (vals == null) {
				vals = new ArrayList<ValoreIdSemantico>();
			}
			if (vals.size() == 0) {
				vis1 = new ValoreIdSemantico(foundComune.getCap());
				vals.add(vis1);
			} else {
				vis1 = vals.get(0);
				if (StringUtils.isEmpty(vis1.getValoreScalare())) {
					vis1.setValoreScalare(foundComune.getCap());
				}
			}
			datiDomanda.setValoriIndicizzati(refModulo, FACCTConstants.ID_SEMANTICO_ATTIVITA_INDIRIZZO_CAP, vals);
		}
		// imposto CODICE_ATTIVITA_REGIONALE e DESCR_ATTIVITA_REGIONALE che sono
		// già valorizzati nel command
		PresentazioneDomandaCartCommand command = datiDomanda.getDatiContestoDomanda();
		actualValue = datiDomanda.getValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_CODICE_ATTIVITA_REGIONALE);
		if (StringUtils.isBlank(actualValue)) {
			datiDomanda.setValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_CODICE_ATTIVITA_REGIONALE, command.getCodiceAttivitaBdr());
		} else {
			String nuovoCodice = StringUtils.defaultString(command.getCodiceAttivitaBdr()).trim();
			if (StringUtils.isNotBlank(nuovoCodice)) {
				if (!actualValue.equalsIgnoreCase(command.getCodiceAttivitaBdr())) {
					datiDomanda.setValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_CODICE_ATTIVITA_REGIONALE, command.getCodiceAttivitaBdr());
				}
			}
		}
		actualValue = datiDomanda.getValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_DESCR_ATTIVITA_REGIONALE);
		if (StringUtils.isBlank(actualValue)) {
			datiDomanda.setValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_DESCR_ATTIVITA_REGIONALE, command.getNomeAttivitaBdr());
		} else {
			String nuovoCodice = StringUtils.defaultString(command.getNomeAttivitaBdr()).trim();
			if (StringUtils.isNotBlank(nuovoCodice)) {
				if (!actualValue.equalsIgnoreCase(command.getNomeAttivitaBdr())) {
					datiDomanda.setValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_DESCR_ATTIVITA_REGIONALE, command.getNomeAttivitaBdr());
				}
			}
		}
		/*
		 * Imposto automaticamente il campo DATA_COMPILAZIONE con la data di oggi
		 */
		actualValue = datiDomanda.getValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_DATA_COMPILAZIONE);
		if (StringUtils.isBlank(actualValue)) {
			datiDomanda.setValoreScalare(refModulo, FACCTConstants.ID_SEMANTICO_DATA_COMPILAZIONE, Utilities.getToday(false));
		}
		/*
		 * imposto automaticamente N valori indicizzati nell'id semantico ALLEGATI.MODULO-CODICE (id semantici ALLEGATI.ENDOLOCALE-CODICE e
		 * ALLEGATI.ENDOLOCALE-DESCRIZIONE se STANDARD_2) per predisporre una riga nella tabella degli allegati per ciascun allegato previsto per gli
		 * endoprocedimenti non CART selezionati dall'utente
		 */
		List<String> codiciEndoNoCart = datiDomanda.getElencoEndoNoCART();
		boolean isStd0 = FACCTConstants.STANDARD_0.equals(refModulo);
		List<Allegati> allegatiEndo = endoRegioneToscanaService.getAllegatiEndoAttivi(codiciEndoNoCart, null, null, null);
		String idsemCodice = isStd0 ? FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_CODICE : FACCTConstants.ID_SEMANTICO_ALLEGATI_ENDOLOCALE_CODICE;
		String idsemAllegato = isStd0 ? FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_ALLEGATO
				: FACCTConstants.ID_SEMANTICO_ALLEGATI_ENDOLOCALE_ALLEGATI;
		String idsemDescrizione = FACCTConstants.ID_SEMANTICO_ALLEGATI_ENDOLOCALE_DESCRIZIONE;
		ValoreIdSemantico idsemValCodice = datiModulo.getValoreIdSemantico(idsemCodice);
		ValoreIdSemantico idsemValDescrizione = datiModulo.getValoreIdSemantico(idsemDescrizione);
		ValoreIdSemantico idsemValAllegato = datiModulo.getValoreIdSemantico(idsemAllegato);
		if (idsemValCodice == null) {
			datiModulo.setValoriIndicizzati(idsemCodice, new ArrayList<ValoreIdSemantico>());
			idsemValCodice = datiModulo.getValoreIdSemantico(idsemCodice);
		}
		if (!isStd0 && idsemValDescrizione == null) {
			datiModulo.setValoriIndicizzati(idsemDescrizione, new ArrayList<ValoreIdSemantico>());
			idsemValDescrizione = datiModulo.getValoreIdSemantico(idsemDescrizione);
		}
		List<ValoreIdSemantico> valoriIndicizzatiCodice = idsemValCodice.getValoreIndicizzato();
		if (valoriIndicizzatiCodice == null) {
			valoriIndicizzatiCodice = new ArrayList<ValoreIdSemantico>();
			idsemValCodice.setValoreIndicizzato(valoriIndicizzatiCodice);
		}
		List<ValoreIdSemantico> valoriIndicizzatiDescrizione = null;
		List<ValoreIdSemantico> valoriIndicizzatiAllegato = null;
		if (idsemValAllegato != null) {
			valoriIndicizzatiAllegato = idsemValAllegato.getValoreIndicizzato();
			if (valoriIndicizzatiAllegato == null) {
				valoriIndicizzatiAllegato = new ArrayList<ValoreIdSemantico>();
				idsemValAllegato.setValoreIndicizzato(valoriIndicizzatiAllegato);
			}
		}
		if (!isStd0) {
			valoriIndicizzatiDescrizione = idsemValDescrizione.getValoreIndicizzato();
			if (valoriIndicizzatiDescrizione == null) {
				valoriIndicizzatiDescrizione = new ArrayList<ValoreIdSemantico>();
				idsemValDescrizione.setValoreIndicizzato(valoriIndicizzatiDescrizione);
			}
		}
		// precaricamento righe anche per standard 2.
		/*
		 * per i valori già presenti nell'id semantico verifico che quelli automatici (che iniziano con E[codiceprocedimento]) corrispondano ad uno
		 * dei codici procedimenti non cart selezionati dall'utente: i valori che fanno riferimento ad endoprocedimenti non selezionati vengono
		 * eliminati dal ValoreIdSemantico
		 */
		Integer idAlberoProcDomanda = datiDomanda.getDatiContestoDomanda().getIdAlberoProc();
		Pattern idProcPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDPROCEDIMENTO);
		Pattern idDocAlberoPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDALBEROPROCDOC);
		Matcher idProcMatcher = null;
		List<String> endoNoCartEliminati = new ArrayList<String>();
		for (int i = 0; i < valoriIndicizzatiCodice.size();) {
			ValoreIdSemantico vis = valoriIndicizzatiCodice.get(i);
			String visVal = vis.getValoreScalare();
			boolean remove = false;
			if (null != visVal) {
				idProcMatcher = idProcPattern.matcher(visVal);
				if (idProcMatcher.find()) {
					String idProc = idProcMatcher.group(1);
					if (!codiciEndoNoCart.contains(idProc)) {
						remove = true;
						if (!endoNoCartEliminati.contains(idProc)) {
							endoNoCartEliminati.add(idProc);
						}
					}
				} else {
					idProcMatcher = idDocAlberoPattern.matcher(visVal);
					if (idProcMatcher.find()) {
						String idProc = idProcMatcher.group(1);
						if (!idProc.equals(idAlberoProcDomanda.toString())) {
							remove = true;
						}
					}
				}
			}
			if (remove) {
				valoriIndicizzatiCodice.remove(i);
				if (!isStd0 && valoriIndicizzatiDescrizione.size() > i) {
					valoriIndicizzatiDescrizione.remove(i);
				}
				// rimuovo anche i valori vettoriali del campo file che
				// contiene il modulo
				// uploadato e i corrispondenti riferimenti a FileInfo e
				// AllegatoDaFirmare
				if (valoriIndicizzatiAllegato != null) {
					ValoreIdSemantico valoriAllegatoIndice = null;
					if (valoriIndicizzatiAllegato.size() > i) {
						valoriAllegatoIndice = valoriIndicizzatiAllegato.get(i);
						List<FileInfo> filesToRemove = valoriAllegatoIndice.getFileInfo();
						valoriIndicizzatiAllegato.remove(i);
						for (FileInfo fileInfo : filesToRemove) {
							datiDomanda.removeAllegatoDaFirmare(fileInfo.getIdOggetto());
						}
						filesToRemove.clear();
					}
				}
			} else {
				i++;
			}
		}
		List<Inventarioprocdyn2modellit> schedeEliminate = endoRegioneToscanaService.getSchedeDinamicheEndoAttivi(endoNoCartEliminati, null, null,
				null);
		List<Inventarioprocdyn2modellit> schedeEndo = endoRegioneToscanaService.getSchedeDinamicheEndoAttivi(codiciEndoNoCart, null, null, null);
		String[] schedeCompilateVal = datiDomanda.getValoreVettoriale(FACCTConstants.MODELLO_INIT_DATI_DINAMICI,
				FACCTConstants.ID_SEMANTICO_INIT_SCHEDEDINAMICHE);
		if (null == schedeCompilateVal) {
			schedeCompilateVal = new String[0];
		}
		List<String> schedeCompilate = new ArrayList<String>(Arrays.asList(schedeCompilateVal));
		// rimuovo anche tutti i valori dagli id semantici speciali destinati ai
		// campi delle schede dinamiche relative a endo non più selezionati
		for (Inventarioprocdyn2modellit schedaEliminata : schedeEliminate) {
			// eliminazione dei riferimenti alle schede di endo non più
			// selezionati
			Integer idSchedaEliminata = schedaEliminata.getId().getFkD2mtId();
			boolean removed = schedeCompilate.remove(idSchedaEliminata.toString());
			if (removed && log.isDebugEnabled()) {
				log.debug("impostaValoriDiDefault() - eliminazione della scheda dinamica (id: {}, desc: {}) dagli id semantici.", new String[] {
						idSchedaEliminata.toString(), schedaEliminata.getDyn2Modellit().getDescrizione() });
			}
			// eliminazione dell'id semantico che contiene l'elenco dei codici
			// dei campi appartenenti alla scheda eleiminata
			String[] campiScheda = datiDomanda.getValoreVettoriale(FACCTConstants.MODELLO_INIT_DATI_DINAMICI,
					FACCTConstants.ID_SEMANTICO_INIT_CAMPISCHEDA_PREFIX + idSchedaEliminata.toString());
			if (null != campiScheda) {
				datiDomanda.removeIdSemantico(FACCTConstants.MODELLO_INIT_DATI_DINAMICI, FACCTConstants.ID_SEMANTICO_INIT_CAMPISCHEDA_PREFIX
						+ idSchedaEliminata.toString());
				// eliminazione dell'id semantico che contiene l'elenco dei
				// codic dei campi della scheda
				if (log.isDebugEnabled()) {
					log.debug("impostaValoriDiDefault() - eliminazione dell'id semantico con l'elenco dei campi della scheda {}",
							new String[] { idSchedaEliminata.toString() });
				}
				/*
				 * TODO eliminare anche tutti gli id semantici dei campi della scheda (solo se non utilizzati anche in altre schede ancora attive)
				 */
				for (int j = 0; j < campiScheda.length; j++) {
					boolean removeCampo = true;
					for (Inventarioprocdyn2modellit schedaAttiva : schedeEndo) {
						Integer idSchedaAttiva = schedaAttiva.getId().getFkD2mtId();
						String[] campiSchedaAttiva = datiDomanda.getValoreVettoriale(FACCTConstants.MODELLO_INIT_DATI_DINAMICI,
								FACCTConstants.ID_SEMANTICO_INIT_CAMPISCHEDA_PREFIX + idSchedaAttiva);
						if (campiSchedaAttiva != null) {
							if (ArrayUtils.contains(campiSchedaAttiva, campiScheda[j])) {
								removeCampo = false;
								break;
							}
						}
					}
					if (removeCampo) {
						StringBuilder sbIdSemCampo = new StringBuilder(FACCTConstants.ID_SEMANTICO_INIT_VALORECAMPO_PREFIX).append(campiScheda[j]);
						datiDomanda.removeIdSemantico(FACCTConstants.MODELLO_INIT_DATI_DINAMICI, sbIdSemCampo.toString());
						sbIdSemCampo.append(FACCTConstants.ID_SEMANTICO_INIT_VALOREDECODIFICATO_SUFFIX);
						datiDomanda.removeIdSemantico(FACCTConstants.MODELLO_INIT_DATI_DINAMICI, sbIdSemCampo.toString());
						if (log.isDebugEnabled()) {
							log.debug("impostaValoriDiDefault() - eliminazione dell'id semantico contenente il valore del campo dinamico {}",
									new String[] { campiScheda[j] });
						}
					} else {
						if (log.isDebugEnabled()) {
							log.debug(
									"impostaValoriDiDefault() - l'id semantico contenente il valore del campo dinamico {} non sarà eliminato perchè lo stesso campo è usato in altre schede dinamiche.",
									new String[] { campiScheda[j] });
						}
					}
				}
			}
		}
		datiDomanda.setValoreVettoriale(FACCTConstants.MODELLO_INIT_DATI_DINAMICI, FACCTConstants.ID_SEMANTICO_INIT_SCHEDEDINAMICHE,
				schedeCompilate.toArray(new String[schedeCompilate.size()]));
		// elimino eventuali righe vuote dalla tabella degli allegati
		if (idsemValCodice != null && !idsemValCodice.hasValue() && idsemValAllegato != null && !idsemValAllegato.hasValue()) {
			boolean clear = true;
			if (!isStd0) {
				clear = !idsemValDescrizione.hasValue();
				if (clear) {
					datiModulo.setValoriIndicizzati(idsemDescrizione, new ArrayList<ValoreIdSemantico>());
					idsemValDescrizione = datiModulo.getValoreIdSemantico(idsemDescrizione);
					valoriIndicizzatiDescrizione = idsemValDescrizione.getValoreIndicizzato();
				}
			}
			if (clear) {
				datiModulo.setValoriIndicizzati(idsemCodice, new ArrayList<ValoreIdSemantico>());
				idsemValCodice = datiModulo.getValoreIdSemantico(idsemCodice);
				valoriIndicizzatiCodice = idsemValCodice.getValoreIndicizzato();
				datiModulo.setValoriIndicizzati(idsemAllegato, new ArrayList<ValoreIdSemantico>());
				idsemValAllegato = datiModulo.getValoreIdSemantico(idsemAllegato);
				valoriIndicizzatiAllegato = idsemValAllegato.getValoreIndicizzato();
			}
		}
		List<AlberoprocDocumenti> alberoDocs = endoRegioneToscanaService.getDocumentiEreditatiEndoAttivi(idAlberoProcDomanda, null, null);
		for (AlberoprocDocumenti alberoDoc : alberoDocs) {
			Alberoproc aproc = alberoDoc.getAlberoproc();
			if (aproc != null && aproc.getId() != null) {
				String idProcVal = new StringBuilder("P[").append(idAlberoProcDomanda).append("]").toString();
				String descAllVal = alberoDoc.getDescrizione();
				boolean giaPresente = false;
				int count = 0;
				for (Iterator<ValoreIdSemantico> iterator = valoriIndicizzatiCodice.iterator(); iterator.hasNext() && !giaPresente;) {
					ValoreIdSemantico valoreIdSemantico = (ValoreIdSemantico) iterator.next();
					String value = valoreIdSemantico.getValoreScalare();
					if (StringUtils.isNotEmpty(value)) {
						if (value.startsWith(idProcVal)) {
							if (isStd0) {
								giaPresente = value.endsWith(descAllVal);
							} else {
								ValoreIdSemantico descValoreIdSemantico = null;
								if (valoriIndicizzatiDescrizione != null && valoriIndicizzatiDescrizione.size() > count) {
									descValoreIdSemantico = valoriIndicizzatiDescrizione.get(count);
									String desc = StringUtils.defaultString(descValoreIdSemantico.getValoreScalare());
									giaPresente = desc.equals(descAllVal);
								}
							}
						}
					}
					count++;
				}
				if (!giaPresente) {
					if (isStd0) {
						StringBuilder newValue = new StringBuilder(idProcVal).append("-").append(descAllVal);
						valoriIndicizzatiCodice.add(new ValoreIdSemantico(newValue.toString()));
					} else {
						valoriIndicizzatiCodice.add(new ValoreIdSemantico(idProcVal));
						valoriIndicizzatiDescrizione.add(new ValoreIdSemantico(descAllVal));
					}
				}
			}
		}
		for (Allegati allegato : allegatiEndo) {
			Inventarioprocedimenti proc = allegato.getInventarioprocedimento();
			Oggetti oggettoAllegato = allegato.getOggetti();
			if (oggettoAllegato != null && oggettoAllegato.getId() != null) {
				oggettoAllegato = oggettiService.findById(new PkId(oggettoAllegato.getId().getCodice()));
			}
			if (proc != null && proc.getId() != null) {
				// Integer idProc = proc.getId().getCodice();
				// String desc = allegato.getAllegato();
				String idProcVal = new StringBuilder("E[").append(proc.getId().getCodice()).append("]").toString();
				String descAllVal = allegato.getAllegato();
				boolean giaPresente = false;
				int count = 0;
				for (Iterator<ValoreIdSemantico> iterator = valoriIndicizzatiCodice.iterator(); iterator.hasNext() && !giaPresente;) {
					ValoreIdSemantico valoreIdSemantico = (ValoreIdSemantico) iterator.next();
					String value = valoreIdSemantico.getValoreScalare();
					if (StringUtils.isNotEmpty(value)) {
						if (value.startsWith(idProcVal)) {
							if (isStd0) {
								giaPresente = value.endsWith(descAllVal);
							} else {
								ValoreIdSemantico descValoreIdSemantico = null;
								if (valoriIndicizzatiDescrizione != null && valoriIndicizzatiDescrizione.size() > count) {
									descValoreIdSemantico = valoriIndicizzatiDescrizione.get(count);
									String desc = StringUtils.defaultString(descValoreIdSemantico.getValoreScalare());
									giaPresente = desc.equals(descAllVal);
								}
							}
						}
					}
					count++;
				}
				if (!giaPresente) {
					if (isStd0) {
						StringBuilder newValue = new StringBuilder(idProcVal).append("-").append(descAllVal);
						valoriIndicizzatiCodice.add(new ValoreIdSemantico(newValue.toString()));
					} else {
						valoriIndicizzatiCodice.add(new ValoreIdSemantico(idProcVal));
						valoriIndicizzatiDescrizione.add(new ValoreIdSemantico(descAllVal));
					}
				}
			}
		}
		/*
		 * imposto automaticamente anche N valori indicizzati nell'id semantico ALLEGATI.MODULO-CODICE per predisporre una riga nella tabella degli
		 * allegati per ciascuna scheda dinamica prevista per gli endoprocedimenti non CART selezionati dall'utente
		 */
		for (Inventarioprocdyn2modellit scheda : schedeEndo) {
			// Inventarioprocedimenti proc =
			// allegato.getInventarioprocedimento();
			Integer codInventario = scheda.getId().getCodiceinventario();
			Dyn2Modellit d2m = scheda.getDyn2Modellit();
			if (codInventario != null && d2m != null) {
				// Integer idProc = proc.getId().getCodice();
				// String desc = allegato.getAllegato();
				String idProcVal = new StringBuilder("E[").append(codInventario).append("]").toString();
				String descAllVal = d2m.getDescrizione();
				boolean giaPresente = false;
				int count = 0;
				for (Iterator<ValoreIdSemantico> iterator = valoriIndicizzatiCodice.iterator(); iterator.hasNext() && !giaPresente;) {
					ValoreIdSemantico valoreIdSemantico = (ValoreIdSemantico) iterator.next();
					String value = valoreIdSemantico.getValoreScalare();
					if (StringUtils.isNotEmpty(value)) {
						if (value.startsWith(idProcVal)) {
							if (isStd0) {
								giaPresente = value.endsWith(descAllVal);
							} else {
								ValoreIdSemantico descValoreIdSemantico = null;
								if (valoriIndicizzatiDescrizione.size() > count) {
									descValoreIdSemantico = valoriIndicizzatiDescrizione.get(count);
									String desc = StringUtils.defaultString(descValoreIdSemantico.getValoreScalare());
									giaPresente = desc.equals(descAllVal);
								}
							}
						}
					}
					count++;
				}
				if (!giaPresente) {
					if (isStd0) {
						StringBuilder newValue = new StringBuilder(idProcVal).append("-").append(descAllVal);
						valoriIndicizzatiCodice.add(new ValoreIdSemantico(newValue.toString()));
					} else {
						valoriIndicizzatiCodice.add(new ValoreIdSemantico(idProcVal));
						valoriIndicizzatiDescrizione.add(new ValoreIdSemantico(descAllVal));
					}
				}
			}
		}
		// TODO aggiungere valori id semantici ai campi della tabella allegati
		// anche per i documenti ereditati dall'albero dei procedimenti
	}

	private void validaCampoRecursive(ItemType item, String idQuadro, String riferimentoModulo, DatiDomandaCart dati, List<ErroreValidazione> errors,
			int nestingLevel, IndiceIdSemantico rowIndex, Boolean attivo, Boolean obbligatorio, AutocompilerConfig autocompilerConfig) {

		// §§§BEGIN§§§
		String idModulo = CartModuloHelper.buildIdModuloFromRefModulo(riferimentoModulo);
		if (item.getCampo() != null || item.getFile() != null) {
			EspressioneType campoAttivoExpr = null;
			EspressioneType campoObbligatorioExpr = null;
			boolean mandatory = false;
			String idSemantico = null;
			CampoType campo = null;
			FileType campoFile = null;
			CampoType campoClone = null;
			FileType campoFileClone = null;
			try {
				if (item.getCampo() != null) {
					campo = item.getCampo();
					campoAttivoExpr = campo.getAttivo();
					campoObbligatorioExpr = campo.getObbligatorio();
					idSemantico = campo.getIdSemantico();
					campoClone = (CampoType) (BeanUtils.cloneBean(item.getCampo()));
				} else if (item.getFile() != null) {
					campoFile = item.getFile();
					campoAttivoExpr = campoFile.getAttivo();
					campoObbligatorioExpr = campoFile.getObbligatorio();
					idSemantico = campoFile.getIdSemantico();
					campoFileClone = (FileType) (BeanUtils.cloneBean(item.getFile()));
				}
			} catch (Exception e) {
				Object cloning = campo != null ? campo : campoFile;
				log.error("validaCampoRecursive - errore nella copia del bean {}", cloning);
			}
			// validazione campo obbligatorio
			boolean active = attivo && CartModuloHelper.evaluateExpression(campoAttivoExpr, dati, rowIndex);
			/*
			 * Validazione custom da configurazione controlli a completamento automoatico: questa validazione viene eseguita prima di quelle standard
			 * previste dalla modulistica CART perchè in alcuni casi può essere utilizzata per manipolare e modificare le altre validazioni previste
			 * dalla modulistica per lo stesso campo (x es.: la validazione della data di presentazione deve accettare anche valori antecedenti alla
			 * data corrente quando nel BO si caricano i dati di un'istanza esistente nella modulistica CART allo scopo di consentire la notifica
			 * ell'ente terzo della pratica secondo lo standard del CART.)
			 */
			// Creo un clone dell'ItemType che definisce il campo della
			// modulistica perchè l'oggetto originale può essere modificato dal
			// validatore custom
			if (active) {
				List<ErroreValidazione> customErrors = null;
				if (autocompilerConfig != null) {
					List<AutocompilerApplication> autocomps = CartModuloHelper.getAutocompilersForIdSemantico(autocompilerConfig, idSemantico);
					for (AutocompilerApplication application : autocomps) {
						if (StringUtils.isNotBlank(application.getValidator())) {
							try {
								IAutocompilerValidator validator = getValidatorInstanceByName(application.getValidator());
								// errors.addAll(validator.validaValoreCampo(stringVal,
								// campo, idQuadro, idModulo, rowIndex, dati));
								if (item.getCampo() != null) {
									customErrors = validator.validaValoreCampo(item.getCampo(), riferimentoModulo, dati, idQuadro, idModulo,
											rowIndex, application.getConfigOptions());
								} else if (item.getFile() != null) {
									customErrors = validator.validaValoreFile(item.getFile(), riferimentoModulo, dati, idQuadro, idModulo, rowIndex,
											application.getConfigOptions());
								}
							} catch (Exception e) {
								// eccezione durante la validazione custom viene
								// intercettata e gestita con un messaggio di
								// warning all'utente
								log.error("validaCampoRecursive - eccezione durante la validazione {} sul campo {} : {}",
										new Object[] { application.getValidator(), application.getTriggerFieldId(), e });
								String errMsg = MessageFormat.format(
										"Si è verificato un''errore del sistema durante la validazione del campo {0}: {1}", new Object[] {
												application.getTriggerFieldId(), e });
								MessaggioErrore msg = new MessaggioErrore(errMsg, rowIndex.vettoreIndici());
								ErroreValidazione error = new ErroreValidazione(application.getTriggerFieldId(), msg);
								error.setBloccante(false);
								error.setIdModulo(idModulo);
								error.setIdQuadro(idQuadro);
								errors.add(error);
							}
						}
					}
					// rileggo il valore dell'espressione di obbligatorietà che può essere stato modificato dai validatori custom
					if (item.getCampo() != null) {
						campoObbligatorioExpr = item.getCampo().getObbligatorio();
					} else if (item.getFile() != null) {
						campoObbligatorioExpr = item.getFile().getObbligatorio();
					}
				}
				if (null != customErrors) {
					errors.addAll(customErrors);
				}
				if (null != obbligatorio) {
					mandatory = obbligatorio && CartModuloHelper.evaluateExpression(campoObbligatorioExpr, dati, rowIndex);
				} else {
					mandatory = CartModuloHelper.evaluateExpression(campoObbligatorioExpr, dati, rowIndex);
				}
			}
			// se item non è attivo svuoto tutti i valori contenuti
			else {
				if (rowIndex == null || rowIndex.contaLivelli() == 0) {
					dati.removeIdSemantico(riferimentoModulo, idSemantico);
				} else {
					dati.removeValoreIdSemanticoPerIndice(riferimentoModulo, idSemantico, rowIndex);
				}
				if (item.getFile() != null) {
					List<FileInfo> allegatiRimossi = dati.removeAllegatiByIdSemantico(idSemantico);
					dati.getFileDeletions().addAll(allegatiRimossi);
				}
			}
			boolean hasValue = dati.hasValore(idSemantico, rowIndex);
			if (mandatory && !hasValue) {
				StringBuilder errorSb = null;
				if (item.getCampo() != null) {
					errorSb = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
				} else if (item.getFile() != null) {
					errorSb = CartModuloHelper.initErrorMessageFor(campoFile, rowIndex.getIndice());
				}
				errorSb.append(" è obbligatorio.");
				MessaggioErrore errMsg = new MessaggioErrore(errorSb.toString(), rowIndex.vettoreIndici());
				ErroreValidazione error = new ErroreValidazione(idSemantico, errMsg);
				error.setIdQuadro(idQuadro);
				error.setIdModulo(idModulo);
				errors.add(error);
			}
			if (hasValue && active) {
				// validazione formale del contenuto del campo
				ErroreValidazione error = null;
				if (item.getCampo() != null) {
					error = validaValoreCampo(item.getCampo(), riferimentoModulo, dati, idQuadro, idModulo, rowIndex);
				} else if (item.getFile() != null) {
					error = validaValoreCampoFile(item.getFile(), riferimentoModulo, dati, idQuadro, idModulo, rowIndex);
				}
				if (error != null) {
					errors.add(error);
				}
			}
			// ripristino le definizioni originali CART dei campi perchè gli
			// attributi impostati inizialmente possono essere stati modificati
			// dai validatori custom
			if (campoClone != null) {
				item.setCampo(campoClone);
			} else if (campoFileClone != null) {
				item.setFile(campoFileClone);
			}
		} else if (item.getSezione() != null) {
			SezioneType sezione = item.getSezione();
			boolean active = attivo && CartModuloHelper.evaluateExpression(sezione.getAttivo(), dati, rowIndex);
			boolean mandatory = false;
			if (null != obbligatorio) {
				mandatory = obbligatorio && CartModuloHelper.evaluateExpression(sezione.getObbligatorio(), dati, rowIndex);
			} else {
				mandatory = CartModuloHelper.evaluateExpression(sezione.getObbligatorio(), dati, rowIndex);
			}
			/*
			 * TODO se la sezione è obbligatoria significa che deve essere compilata se non obbligatoria può essere omessa, la validazione
			 * dell'obbligatorietà della sezione deve dare errore quando manca qualcuno degli id semantici dei campi della sezione nei dati trasmessi
			 * senza badare al fatto che i valori siano vuoti o popolati, su questo comanda l'obbligatorietà dei singoli campi.
			 */
			List<ItemType> elementiSezione = sezione.getItem();
			for (ItemType itemSezione : elementiSezione) {
				validaCampoRecursive(itemSezione, idQuadro, riferimentoModulo, dati, errors, nestingLevel, rowIndex, active, null, autocompilerConfig);
			}
		} else if (item.getTabella() != null) {
			TabellaType tabella = item.getTabella();
			boolean active = attivo && CartModuloHelper.evaluateExpression(tabella.getAttivo(), dati, rowIndex);
			boolean mandatory = false;
			if (null != obbligatorio) {
				mandatory = obbligatorio && CartModuloHelper.evaluateExpression(tabella.getObbligatorio(), dati, rowIndex);
			} else {
				mandatory = CartModuloHelper.evaluateExpression(tabella.getObbligatorio(), dati, rowIndex);
			}
			List<ColonnaType> colonne = tabella.getColonna();
			nestingLevel++;
			rowIndex.incrementaLivello(tabella);
			int numRigheTabella = CartModuloHelper.contaRigheTabella(item, dati, rowIndex);
			rowIndex.decrementaLivello();
			if (active && mandatory && numRigheTabella < 1) {
				StringBuilder sbErr = new StringBuilder("La tabella ");
				if (StringUtils.isNotBlank(tabella.getTitolo())) {
					sbErr.append(tabella.getTitolo()).append(" ");
				}
				sbErr.append("deve contenere almeno una riga.");
				MessaggioErrore errMsg = new MessaggioErrore(sbErr.toString(), rowIndex.vettoreIndici());
				ErroreValidazione error = new ErroreValidazione("tabella_" + tabella.getId(), errMsg);
				error.setIdQuadro(idQuadro);
				error.setIdModulo(idModulo);
				errors.add(error);
			}
			for (Iterator<ColonnaType> ColumnIterator = colonne.iterator(); ColumnIterator.hasNext();) {
				ColonnaType colonna = ColumnIterator.next();
				/*
				 * poichè siamo in una tabella i campi da validare saranno ripetuti n volte e ciascuno di essi deve essere validato tenendo conto del
				 * suo indice
				 */
				rowIndex.incrementaLivello(tabella);
				for (int i = 0; i < numRigheTabella; i++) {
					validaCampoRecursive(colonna.getCella(), idQuadro, riferimentoModulo, dati, errors, nestingLevel, rowIndex, active, null,
							autocompilerConfig);
					rowIndex.incrementaIndice();
				}
				rowIndex.decrementaLivello();
			}
			nestingLevel--;
		}
		// §§§END§§§
	}

	/**
	 * effettua la validazione del campo di tipo {@link CampoType} alla riga rowIndex (-1 se il campo non è indicizzato).
	 * 
	 * @param campo
	 * @param valore
	 * @return
	 */
	public ErroreValidazione validaValoreCampo(CampoType campo, String riferimentoModulo, DatiDomandaCart dati, String idQuadro, String idModulo,
			IndiceIdSemantico rowIndex) {

		// §§§BEGIN§§§
		// recupero il valore per l'id semantico del campo dai dati della
		// domanda
		// se si tratta della validazione dell'i-esimo valore di un campo
		// indicizzato recupero il valore all'i-esimo indice
		ValoreIdSemantico valore = dati.getValoreIdSemanticoPerIndice(riferimentoModulo, campo.getIdSemantico(), rowIndex);
		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		SetValoriType setValori = campo.getSetValori();
		/*
		 * List<ValoreIdSemantico> valori = new ArrayList<ValoreIdSemantico>(); if (valore.isIndicizzato()) { valori = valore.getValoreIndicizzato();
		 * } else { valori.add(valore); } for (int i = 0; i < valori.size(); i++) {
		 */
		// ValoreIdSemantico currentValue = valori.get(i);
		List<String> valuesAsString = new ArrayList<String>();
		if (valore.isScalare()) {
			valuesAsString.add(valore.getValoreScalare());
		} else if (valore.isVettoriale()) {
			String[] valuesArray = valore.getValoreVettoriale();
			for (int j = 0; j < valuesArray.length; j++) {
				valuesAsString.add(valuesArray[j]);
			}
		} else if (valore.isIndicizzato()) {
			// non si dovrebbe verificare perchè viene recuperato ogni singolo
			// valore finale dell'id semantico
		}
		for (Iterator<String> iterator = valuesAsString.iterator(); iterator.hasNext();) {
			String stringVal = iterator.next();
			// validazione alfabetica
			if (setValori.getAlfabetico() != null) {
				AlfabeticoType alfabetico = setValori.getAlfabetico();
				errors.addAll(validazioneAlfabetica(alfabetico, stringVal, campo, idQuadro, idModulo, rowIndex));
			}
			// validazione data
			else if (setValori.getCalendario() != null) {
				CalendarioType calendario = setValori.getCalendario();
				errors.addAll(validazioneCalendario(calendario, stringVal, campo, idQuadro, idModulo, rowIndex));
			}
			// validazione CF persona fisica
			else if (setValori.getCodiceFiscalePF() != null) {
				CFPersonaFisicaType cfpf = setValori.getCodiceFiscalePF();
				errors.addAll(validazioneCFPersonaFisica(cfpf, stringVal, campo, idQuadro, idModulo, rowIndex));
			}
			// validazione CF persona giuridica
			else if (setValori.getCodiceFiscalePG() != null) {
				CFPersonaGiuridicaType cfpg = setValori.getCodiceFiscalePG();
				errors.addAll(validazioneCFPersonaGiuridica(cfpg, stringVal, campo, idQuadro, idModulo, rowIndex));
			}
			// validazione enumerazione. In teoria è inutile perchè la pagina
			// HTML sul client dovrebbe impedire di trasmettere valori non
			// previsti
			else if (setValori.getEnumerazione() != null) {
				EnumerazioneType enumerazione = setValori.getEnumerazione();
				errors.addAll(validazioneEnumerazione(enumerazione, stringVal, campo, idQuadro, idModulo, rowIndex));
			}
			// validazione con espressione regolare
			else if (setValori.getEspressioneRegolare() != null) {
				EspressioneRegolareType regex = setValori.getEspressioneRegolare();
				errors.addAll(validazioneEspressioneRegolare(regex, stringVal, campo, idQuadro, idModulo, rowIndex));
			}
			// TODO validazione intervallo valori: la validazione del range è
			// sempre utilizzata all'interno di NumericoType, questa non è
			// implementata
			else if (setValori.getIntervallo() != null) {
				RangeType range = setValori.getIntervallo();
				log.warn(
						"validaValoreCampo() - Attenzione! La validazione RangeType non é stata implementata ed è richiesta dal campo con id semantico {}",
						new Object[] { campo.getIdSemantico() });
			}
			// validazione numerica
			else if (setValori.getNumerico() != null) {
				NumericoType numerico = setValori.getNumerico();
				errors.addAll(validazioneNumerica(numerico, stringVal, campo, idQuadro, idModulo, rowIndex));
			}
			// validazione partita IVA
			else if (setValori.getPartitaIVA() != null) {
				PartitaIVAType pi = setValori.getPartitaIVA();
				errors.addAll(validazionePartitaIVA(pi, stringVal, campo, idQuadro, idModulo, rowIndex));
			}
		}
		// }
		ErroreValidazione retError = null;
		if (errors.size() > 0) {
			retError = new ErroreValidazione();
			retError.setIdSemantico(campo.getIdSemantico());
			retError.setErrori(errors);
			retError.setIdQuadro(idQuadro);
			retError.setIdModulo(idModulo);
		}
		return retError;
		// §§§END§§§
		// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
	}

	private ErroreValidazione validaValoreCampoFile(FileType campoFile, String riferimentoModulo, DatiDomandaCart dati, String idQuadro,
			String idModulo, IndiceIdSemantico rowIndex) {

		// §§§BEGIN§§§
		ValoreIdSemantico valore = dati.getValoreIdSemanticoPerIndice(riferimentoModulo, campoFile.getIdSemantico(), rowIndex);
		// List<ErroreValidazione> retErrors = new
		// ArrayList<ErroreValidazione>();
		ErroreValidazione retError = null;
		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		if (valore == null) {
			boolean hasMultipleValues = !campoFile.getMaxUpload().equals("1");
			if (hasMultipleValues) {
				valore = new ValoreIdSemantico(new String[0]);
			} else {
				valore = new ValoreIdSemantico("");
			}
		}
		boolean bloccante = false;
		if (StringUtils.isNotBlank(campoFile.getTipo())) {
			// validazione del tipo di files uploadati
			String[] allowedExtensions = campoFile.getTipo().split(";");
			errors.addAll(validazioneTipoFile(valore.getFileInfo(), campoFile, idQuadro, idModulo, rowIndex, allowedExtensions));
			bloccante = true;
		}
		if (StringUtils.isNotBlank(campoFile.getMaxUpload()) && !campoFile.getMaxUpload().equalsIgnoreCase(FACCTConstants.RFC186_UNBOUNDED_RANGE)) {
			// validazione del limite massimo di files uploadabili
			int maxUploads = -1;
			try {
				maxUploads = Integer.parseInt(campoFile.getMaxUpload());
				if (maxUploads < valore.getFileInfo().size()) {
					StringBuilder sbErr = CartModuloHelper.initErrorMessageFor(campoFile, rowIndex.getIndice());
					sbErr.append(" contiene ").append(valore.getFileInfo().size());
					sbErr.append(" files uploadati ma il limite massimo consentito é di ").append(campoFile.getMaxUpload());
					MessaggioErrore errMsg = new MessaggioErrore(sbErr.toString(), rowIndex.vettoreIndici());
					errors.add(errMsg);
					bloccante = true;
				}
			} catch (NumberFormatException e) {
				log.error("validaValoreCampoFile() - il limite massimo di files uploadabili per il campo {} ha un valore numerico non valido: {}",
						new Object[] { campoFile.getIdSemantico(), campoFile.getMaxUpload() });
			}
		}
		/*
		 * recupero dall'oggetto DatiDomandaCart i riferimenti agli errori di validazione della firma digitale del file e li aggiungo alla lista
		 * deglil errori da visualizzare all'utente sul client
		 */
		String[] codiciOggetti = new String[0];
		if (valore.isScalare()) {
			codiciOggetti = new String[] { valore.getValoreScalare() };
		} else if (valore.isVettoriale()) {
			codiciOggetti = valore.getValoreVettoriale();
		}
		for (int i = 0; i < codiciOggetti.length; i++) {
			String codiceOggetto = codiciOggetti[i];
			if (StringUtils.isNotEmpty(codiceOggetto)) {
				AllegatoDaFirmare toSign = dati.getAllegatoDaFirmare(new Integer(codiceOggetto));
				if (null != toSign && (StringUtils.isNotBlank(toSign.getErrorMessage()) || StringUtils.isNotBlank(toSign.getWarningMessage()))) {
					// se necessario completo il popolamento delle informazioni
					// relative all'errore di validazione della firma digitale
					if (StringUtils.isBlank(toSign.getDescrizione())) {
						// recupero informazioni descrittive relative al campo
						// incui è stato uploadato il file non firmato
						// se è presente recupero l'etichetta del campo file
						// se non è presente l'etichetta ma ci troviamo in una
						// tabella cerco l'etichetta della colonna
						TabellaType tabella = null;
						if (rowIndex.contaLivelli() > 0) {
							tabella = (TabellaType) rowIndex.getLivello();
						}
						String lbl = CartModuloHelper.getEtichettaPerCampoFile(campoFile, tabella);
						toSign.setDescrizione(StringUtils.defaultString(lbl));
					}
					if (StringUtils.isBlank(toSign.getIdSemantico())) {
						// imposto l'id semantico del campo in cui è stato
						// uploadato il file
						toSign.setIdSemantico(campoFile.getIdSemantico());
					}
					toSign.setRowIndex(rowIndex.getIndice());
					// aggiorno il rowIndex che potrebbe essere cambiato per via
					// di cancellazioni di intere righe
					MessaggioErrore errMsg = new MessaggioErrore(toSign.buildErrorMessage(), rowIndex.vettoreIndici());
					errors.add(errMsg);
				}
			}
		}
		if (errors.size() > 0) {
			retError = new ErroreValidazione();
			retError.setIdSemantico(campoFile.getIdSemantico());
			retError.setErrori(errors);
			retError.setBloccante(bloccante);
			retError.setIdQuadro(idQuadro);
			retError.setIdModulo(idModulo);
		}
		return retError;
		// §§§END§§§
		// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
	}

	public AllegatoDaFirmare validaFirmaDigitale(FileType campoFile, FileItem fileItem, int rowIndex) {

	    //Hergert: IL METODO E' STATO DISMESSO
	    return new AllegatoDaFirmare();
	}

	/**
	 * il metodo torna l'oggetto della base dati se la firma è valida (valido=true, revocato=false o non controllato) altrimeni null
	 */
	// @Override
	public Oggetti validaFirmaDigitale(Integer codiceOggetto) {

		//Hergert: IL METODO E' STATO DISMESSO
		return null;
	}

	/**
	 * il metodo riceve in input un'istanza di Oggetti effettua la validazione della firma digitale e se non viene superata restituisce il messaggio
	 * di errore o warning in un oggetto AllegatoDaFirmare. altrimeni null
	 */
	@Override
	public AllegatoDaFirmare validaFirmaDigitale(Oggetti oggetto) {

	    //Hergert: IL METODO E' STATO DISMESSO
	    return new AllegatoDaFirmare();
	}

	private List<MessaggioErrore> validazioneAlfabetica(AlfabeticoType alfabetico, String indexedValue, CampoType campo, String idQuadro,
			String idModulo, IndiceIdSemantico rowIndex) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		int maxChar = alfabetico.getMaxNumeroCaratteri().intValue();
		StringBuilder sbErr = new StringBuilder();
		if (indexedValue.length() > maxChar) {
			sbErr = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
			sbErr.append(" ha una lunghezza maggiore del limite consentito di ");
			sbErr.append(maxChar).append(" caratteri");
			MessaggioErrore errMsg = new MessaggioErrore(sbErr.toString(), rowIndex.vettoreIndici());
			errors.add(errMsg);
		}
		String extraChars = alfabetico.getCaratteriextra();
		if (extraChars == null) {
			extraChars = "";
		}
		List<Character> invalidChars = new ArrayList<Character>();
		for (int x = 0; x > indexedValue.length(); x++) {
			char c = indexedValue.charAt(x);
			if (!Character.isLetter(c)) {
				if (extraChars.indexOf(c) == -1) {
					invalidChars.add(new Character(c));
				}
			}
		}
		if (invalidChars.size() > 0) {
			sbErr = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
			sbErr.append(" contiene i seguenti caratteri non validi: ");
			for (Iterator<Character> iterator2 = invalidChars.iterator(); iterator2.hasNext();) {
				Character character = iterator2.next();
				sbErr.append(character).append(", ");
			}
			sbErr.delete(sbErr.length() - 2, sbErr.length());
			sbErr.append(". Il campo accetta solo caratteri alfabetici");
			char[] extraCharsArray = extraChars.toCharArray();
			if (extraCharsArray.length > 0) {
				sbErr.append(" oppure uno dei seguenti caratteri speciali: ");
				for (int j = 0; j < extraCharsArray.length; j++) {
					sbErr.append(extraCharsArray[j]).append(", ");
				}
				sbErr.delete(sbErr.length() - 2, sbErr.length());
			}
			sbErr.append(".");
			MessaggioErrore errMsg = new MessaggioErrore(sbErr.toString(), rowIndex.vettoreIndici());
			errors.add(errMsg);
		}
		return errors;
	}

	private List<MessaggioErrore> validazioneCalendario(CalendarioType calendario, String indexedValue, CampoType campo, String idQuadro,
			String idModulo, IndiceIdSemantico rowIndex) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		String pattern = calendario.getPattern();
		StringBuilder sbError = null;
		// validazione pattern data che in generale potrebbe essere superflua
		// perchè il formato dtata viene forzato già lato client
		if (StringUtils.isNotBlank(indexedValue) && StringUtils.isNotBlank(pattern)) {
			SimpleDateFormat valueDF = new SimpleDateFormat(toJavaDatePattern(pattern));
			Date date = null;
			try {
				date = valueDF.parse(indexedValue);
			} catch (ParseException e) {
				sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
				sbError.append(" contiene una data non valida. La data deve essere scritta nel formato ").append(pattern).append(".");
				MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
				errors.add(errMsg);
			}
			if (null != date) {
				// validazione range di date valide se specificato
				RangeDataType range = calendario.getRangeValidita();
				if (null != range) {
					Date minDate = null;
					String min = range.getMinimo();
					if (StringUtils.isNotBlank(min)) {
						try {
							minDate = rangeStringToDate(min);
						} catch (ParseException e) {
							MessageFormat mf = new MessageFormat(
									"Errore durante la procedura di validazione della modulistica. Il valore minimo specificato nella modulistica per la validità del campo data associato all'id semantico {0} non è una data valida. valore: {1}");
							String msg = mf.format(new Object[] { campo.getIdSemantico(), min });
							throw new RuntimeException(msg, e);
						}
					}
					Date maxDate = null;
					String max = range.getMassimo();
					if (StringUtils.isNotBlank(max)) {
						try {
							maxDate = rangeStringToDate(max);
						} catch (ParseException e) {
							MessageFormat mf = new MessageFormat(
									"Errore durante la procedura di validazione della modulistica. Il valore massimo specificato nella modulistica per la validità del campo data associato all'id semantico {0} non è una data valida. valore: {1}");
							String msg = mf.format(new Object[] { campo.getIdSemantico(), max });
							throw new RuntimeException(msg, e);
						}
					}
					// le date non tengono mai conto di hh:mm:ss
					date = DateUtils.truncate(date, Calendar.DATE);
					if (null != minDate) {
						Date minDateCpr = DateUtils.truncate(minDate, Calendar.DATE);
						if (range.isMinimoIncluso()) {
							minDateCpr = DateUtils.add(minDateCpr, Calendar.SECOND, -1);
						}
						if (!date.after(minDateCpr)) {
							sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
							sbError.append(" deve contenere una data successiva ");
							if (range.isMinimoIncluso()) {
								sbError.append(" o uguale ");
							}
							sbError.append("al ").append(valueDF.format(minDate)).append(".");
							MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
							errors.add(errMsg);
						}
					}
					if (null != maxDate) {
						Date maxDateCpr = DateUtils.truncate(maxDate, Calendar.DATE);
						if (range.isMassimoIncluso()) {
							maxDateCpr = DateUtils.add(maxDateCpr, Calendar.SECOND, 1);
						}
						if (!date.before(maxDateCpr)) {
							sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
							sbError.append(" deve contenere una data precedente ");
							if (range.isMinimoIncluso()) {
								sbError.append(" o uguale ");
							}
							sbError.append("al ").append(valueDF.format(maxDate)).append(".");
							MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
							errors.add(errMsg);
						}
					}
				}
			}
		}
		return errors;
	}

	private List<MessaggioErrore> validazioneCFPersonaFisica(CFPersonaFisicaType cfpf, String value, CampoType campo, String idQuadro,
			String idModulo, IndiceIdSemantico rowIndex) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		if (StringUtils.isNotBlank(value)) {
			CFPFPatternType cfPattern = cfpf.getPattern();
			switch (cfPattern) {
			case A_Z_A_Z_6_0_9_2_A_Z_A_Z_0_9_2_A_Z_A_Z_0_9_3_A_Z_A_Z:
				if (!regexMatches(value, cfPattern.value())) {
					StringBuilder sbErr = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
					sbErr.append(" non contiene un codice fiscale valido per una persona fisica. Il valore non corretto è ").append(value)
							.append(".");
					MessaggioErrore errMsg = new MessaggioErrore(sbErr.toString(), rowIndex.vettoreIndici());
					errors.add(errMsg);
				}
				break;
			default:
				MessageFormat mf = new MessageFormat(
						"Errore durante la procedura di validazione della modulistica. Il pattern specificato nella modulistica per la validazione del codice fiscale del campo avente id semantico {0} non è un pattern valido. Valore errato: {1}. Valore previsto: {2}");
				String msg = mf.format(new Object[] { campo.getIdSemantico(), cfPattern.value(),
						CFPFPatternType.A_Z_A_Z_6_0_9_2_A_Z_A_Z_0_9_2_A_Z_A_Z_0_9_3_A_Z_A_Z.value() });
				throw new RuntimeException(msg);
			}
		}
		return errors;
	}

	private List<MessaggioErrore> validazioneCFPersonaGiuridica(CFPersonaGiuridicaType cfpg, String value, CampoType campo, String idQuadro,
			String idModulo, IndiceIdSemantico rowIndex) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		String cfPattern = cfpg.getPattern();
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(cfPattern)) {
			if (!regexMatches(value, cfPattern)) {
				StringBuilder sbErr = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
				sbErr.append(" non contiene un codice fiscale valido per una persona giuridica. Il valore non corretto è ").append(value).append(".");
				MessaggioErrore errMsg = new MessaggioErrore(sbErr.toString(), rowIndex.vettoreIndici());
				errors.add(errMsg);
			}
		}
		return errors;
	}

	private List<MessaggioErrore> validazionePartitaIVA(PartitaIVAType pi, String value, CampoType campo, String idQuadro, String idModulo,
			IndiceIdSemantico rowIndex) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		String pattern = pi.getPattern();
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(pattern)) {
			if (!regexMatches(value, pattern)) {
				StringBuilder sbErr = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
				sbErr.append(" non contiene una partita IVA valida. Il valore non corretto è ").append(value).append(".");
				MessaggioErrore errMsg = new MessaggioErrore(sbErr.toString(), rowIndex.vettoreIndici());
				errors.add(errMsg);
			}
		}
		return errors;
	}

	private List<MessaggioErrore> validazioneEnumerazione(EnumerazioneType en, String value, CampoType campo, String idQuadro, String idModulo,
			IndiceIdSemantico rowIndex) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		List<EnumItemType> enumValues = en.getEnumItem();
		if (StringUtils.isNotBlank(value)) {
			boolean valid = false;
			for (Iterator iterator = enumValues.iterator(); iterator.hasNext() && !valid;) {
				EnumItemType enumVal = (EnumItemType) iterator.next();
				valid = enumVal.getValore().equals(value);
			}
			if (!valid) {
				StringBuilder sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
				sbError.append(" contiene il seguente valore non valido: ").append(value).append(". I valori ammessi sono: ");
				for (Iterator iterator = enumValues.iterator(); iterator.hasNext();) {
					EnumItemType enumVal = (EnumItemType) iterator.next();
					sbError.append(enumVal.getValore()).append(", ");
				}
				sbError.delete(sbError.length() - 2, sbError.length());
				MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
				errors.add(errMsg);
			}
		}
		return errors;
	}

	private List<MessaggioErrore> validazioneEspressioneRegolare(EspressioneRegolareType regex, String value, CampoType campo, String idQuadro,
			String idModulo, IndiceIdSemantico rowIndex) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		String regexValue = regex.getValore();
		if (StringUtils.isNotBlank(value) && StringUtils.isNotBlank(regexValue)) {
			if (!regexMatches(value, regexValue)) {
				StringBuilder sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
				sbError.append(" contiene il seguente valore non ammesso: ").append(value);
				MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
				errors.add(errMsg);
			}
		}
		return errors;
	}

	private List<MessaggioErrore> validazioneNumerica(NumericoType numerico, String value, CampoType campo, String idQuadro, String idModulo,
			IndiceIdSemantico rowIndex) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		value = value.replaceAll("\\.", "");
		String origValue = value;
		if (StringUtils.isNotBlank(value)) {
			// elimino i separatori delle migliaia dal valore se presenti
			int maxDecimal = numerico.getMaxNumeroCifreDecimali().intValue();
			int minDecimal = numerico.getMinNumeroCifreDecimali().intValue();
			int maxInteger = numerico.getMaxNumeroCifreIntere().intValue();
			int numDecimal = 0;
			int numInteger = value.length();
			int indexOfComma = value.indexOf(',');
			if (indexOfComma > -1) {
				numDecimal = value.length() - 1 - indexOfComma;
				numInteger = indexOfComma;
				if (numDecimal > maxDecimal) {
					StringBuilder sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
					sbError.append(" contiene il seguente valore : ").append(value).append(" che non é valido perchè contiene ");
					sbError.append(numDecimal).append(" cifre decimali, ma il massimo numero di cifre decimali consentite é ").append(maxDecimal);
					MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
					errors.add(errMsg);
				}
				if (numDecimal < minDecimal) {
					StringBuilder sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
					sbError.append(" contiene il seguente valore : ").append(value).append(" che non é valido perchè contiene ");
					sbError.append(numDecimal).append(" cifre decimali, ma il minimo numero di cifre decimali consentite é ").append(minDecimal);
					MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
					errors.add(errMsg);
				}
			}
			// se il numero è preceduto dal segno non lo conteggio come cifra
			// intera
			char first = value.charAt(0);
			if (first == '-' || first == '+') {
				numInteger--;
			}
			if (numInteger > maxInteger) {
				StringBuilder sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
				sbError.append(" contiene il seguente valore : ").append(value).append(" che non é valido perchè contiene ");
				sbError.append(numDecimal).append(" cifre intere, ma il massimo numero di cifre intere consentite é ").append(maxInteger);
				MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
				errors.add(errMsg);
			}
			RangeType range = numerico.getRangeValidita();
			if (null != range) {
				NumberFormat nf = NumberFormat.getNumberInstance(new Locale("it", "IT"));
				// try {
				// Number numericValue = nf.parse(value);
				// sostituisco il separatore decimale ',' con '.'
				value = value.replace(',', '.');
				BigDecimal numericValue = new BigDecimal(value);
				// verifica limite massimo
				BigDecimal maxValue = getMaxValueAsBigDecimal(range);
				BigDecimal unit = new BigDecimal(BigInteger.ONE, numerico.getMaxNumeroCifreDecimali().intValue());
				if (range.isMassimoIncluso()) {
					maxValue = maxValue.add(unit);
				}
				if (!(numericValue.compareTo(maxValue) < 0)) {
					StringBuilder sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
					sbError.append(" contiene il valore: ").append(origValue)
							.append(" che supera il valore massimo consentito per il campo che è di ").append(range.getMassimo());
					if (range.isMassimoIncluso()) {
						sbError.append(" incluso.");
					} else {
						sbError.append(" escluso.");
					}
					MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
					errors.add(errMsg);
				}
				// verifica limite minimo
				BigDecimal minValue = getMinValueAsBigDecimal(range);
				if (range.isMinimoIncluso()) {
					minValue = minValue.subtract(unit);
				}
				if (!(numericValue.compareTo(minValue) > 0)) {
					StringBuilder sbError = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
					sbError.append(" contiene il valore: ").append(origValue)
							.append(" che è più basso del valore minimo consentito per il campo che è di ").append(range.getMinimo());
					if (range.isMinimoIncluso()) {
						sbError.append(" incluso.");
					} else {
						sbError.append(" escluso.");
					}
					MessaggioErrore errMsg = new MessaggioErrore(sbError.toString(), rowIndex.vettoreIndici());
					errors.add(errMsg);
				}
				/*
				 * } catch (ParseException e) { throw new RuntimeException("Valore numerico non valido", e); }
				 */
			}
		}
		return errors;
	}

	private List<MessaggioErrore> validazioneTipoFile(List<FileInfo> indexedFileValue, FileType campo, String idQuadro, String idModulo,
			IndiceIdSemantico rowIndex, String[] allowedFileTypes) {

		List<MessaggioErrore> errors = new ArrayList<MessaggioErrore>();
		List<String> failingFiles = new ArrayList<String>();
		if (null != indexedFileValue) {
			for (FileInfo fi : indexedFileValue) {
				String fileName = fi.getNomeFileOriginale();
				int extensionIndex = fileName.lastIndexOf('.');
				String ext = extensionIndex > -1 ? fileName.substring(extensionIndex + 1) : "";
				boolean allowed = false;
				for (int i = 0; i < allowedFileTypes.length; i++) {
					if (allowedFileTypes[i].equalsIgnoreCase(ext)) {
						allowed = true;
						break;
					}
				}
				if (!allowed) {
					failingFiles.add(fileName);
				}
			}
			if (!failingFiles.isEmpty()) {
				StringBuilder sbErr = CartModuloHelper.initErrorMessageFor(campo, rowIndex.getIndice());
				sbErr.append("contiene i seguenti files che sono di un tipo non ammesso: ");
				for (String failing : failingFiles) {
					sbErr.append(failing).append(", ");
				}
				sbErr.delete(sbErr.length() - 2, sbErr.length());
				if (allowedFileTypes != null && allowedFileTypes.length > 0) {
					sbErr.append(". Sono ammessi solo files di tipo: ");
					for (int i = 0; i < allowedFileTypes.length - 1; i++) {
						sbErr.append(allowedFileTypes[i]).append(", ");
					}
					sbErr.append("e ").append(allowedFileTypes[allowedFileTypes.length - 1]);
				}
				MessaggioErrore errMsg = new MessaggioErrore(sbErr.toString(), rowIndex.vettoreIndici());
				errors.add(errMsg);
			}
		}
		return errors;
	}

	private IAutocompilerValidator getValidatorInstanceByName(String validatorName) {

		IAutocompilerValidator validator = null;
		/*
		 * TODO cercare di recuperare un'istanza già inizializzata da Spring e istanziare con classforname solo se non esiste alcun bean configurato
		 * con il nome specificato
		 */
		try {
			if (this.applicationContext.containsBean(validatorName)) {
				validator = (IAutocompilerValidator) this.applicationContext.getBean(validatorName);
			} else {
				validator = (IAutocompilerValidator) Class.forName(validatorName).newInstance();
			}
		} catch (Exception e) {
			log.error("getValidatorInstanceByName - errore nella creazione o nel recupero dell'istanza del validator: ", e);
			throw new RuntimeException(e);
		}
		return validator;
	}

	private BigDecimal getMinValueAsBigDecimal(RangeType range) {

		BigDecimal retVal = null;
		String minValStr = range.getMinimo();
		if (FACCTConstants.RFC186_UNBOUNDED_RANGE.equalsIgnoreCase(minValStr)) {
			retVal = new BigDecimal(Integer.MIN_VALUE);
		} else {
			minValStr = minValStr.replace(',', '.');
			retVal = new BigDecimal(minValStr);
		}
		return retVal;
	}

	private BigDecimal getMaxValueAsBigDecimal(RangeType range) {

		BigDecimal retVal = null;
		String maxValStr = range.getMassimo();
		if (FACCTConstants.RFC186_UNBOUNDED_RANGE.equalsIgnoreCase(maxValStr)) {
			retVal = new BigDecimal(Integer.MAX_VALUE);
		} else {
			maxValStr = maxValStr.replace(',', '.');
			retVal = new BigDecimal(maxValStr);
		}
		return retVal;
	}

	private String toJavaDatePattern(String inputPattern) {

		StringBuilder sbPattern = new StringBuilder();
		for (int i = 0; i < inputPattern.length(); i++) {
			char c = inputPattern.charAt(i);
			switch (c) {
			case 'g':
				sbPattern.append('d');
				break;
			case 'm':
				sbPattern.append('M');
				break;
			case 'a':
				sbPattern.append('y');
				break;
			default:
				sbPattern.append(c);
				break;
			}
		}
		return sbPattern.toString();
	}

	private Date rangeStringToDate(String range) throws ParseException {

		Date retDate = null;
		if (range.equals(FACCTConstants.RFC186_TODAY_RANGE)) {
			retDate = new Date();
		} else if (range.equals(FACCTConstants.RFC186_UNBOUNDED_RANGE)) {
			// no minimum date
		} else {
			retDate = rangeDF.parse(range);
		}
		return retDate;
	}

	private boolean regexMatches(String value, String regexPattern) {

		// escape dei costrutti speciali delle espressioni regolari
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	@Override
	public byte[] convertHtmlToPdFile(byte[] htmlContent) throws RemoteException {

		ConvertBinaryRequest req = new ConvertBinaryRequest(ORMHelper.getToken(), htmlContent, "html", "pdf");
		ConvertBinaryResponse resp = fileConverterWsClient.convertBinary(req);
		byte[] pdfBytes = resp.getBinaryData();
		return pdfBytes;
	}

	@Override
	public byte[] renderSchedaDinamicaPdf(String htmlContent, Map<Object, Object> context, File pdfOutput, int documentWidth) throws Exception {

		byte[] bytes = renderSchedaDinamicaPdfOO(htmlContent, context, pdfOutput, documentWidth);
		return bytes;
	}

	private byte[] renderSchedaDinamicaPdfOO(String htmlContent, Map<Object, Object> context, File pdfOutput, int documentWidth) throws Exception {

		// §§§BEGIN§§§
		if (context == null) {
			context = new HashMap<Object, Object>();
		}
		context.put(FACCTConstants.VELOCITY_CONTEXT_KEY_DYNMODELLO_HTML, htmlContent);
		if (pdfOutput != null && !pdfOutput.exists()) {
			if (log.isDebugEnabled())
				log.debug("renderModuloToFile() - il file di output {} non esisteva ed è stato creato", pdfOutput.getAbsolutePath());
			pdfOutput.createNewFile();
		}
		// VelocityEngineUtils.mergeTemplate(this.templateEngine,
		// "/pagina_modulo_pdf.vm", FACCTConstants.DEFAULT_CHARSET, contextData,
		// w);
		String htmlBytes = VelocityEngineUtils.mergeTemplateIntoString(this.templateEngine, "/cart/pagina_modello_dinamico_pdf_OO.vm",
				FACCTConstants.DEFAULT_CHARSET, context);
		// se l'argomento pdfOutput != null viene scritta una copia del file PDF
		// e dell'html intermedio nel file specificato
		if (pdfOutput != null) {
			String outputHtmlFileName = pdfOutput.getName().substring(0, pdfOutput.getName().lastIndexOf('.')).concat(".html");
			File outputHtmlFile = new File(pdfOutput.getParent(), outputHtmlFileName);
			if (!outputHtmlFile.exists()) {
				outputHtmlFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(outputHtmlFile);
			AttachmentsUtils.writeBytesToStream(htmlBytes.getBytes(FACCTConstants.DEFAULT_CHARSET), fos);
		}
		// converto il file HTML generato in un file PDF tramite il servizio
		// Fileconverter
		ConvertRequest creq = new ConvertRequest(ORMHelper.getToken(), htmlBytes, FileConverterWsClient.ContentType.HTML.name(),
				FileConverterWsClient.ConversionType.PDF.name());
		ConvertResponse cresp = getFileConverterWsClient().convert(creq);
		byte[] pdfBytes = cresp.getBinaryData();
		if (pdfOutput != null) {
			AttachmentsUtils.writeBytesToStream(pdfBytes, new FileOutputStream(pdfOutput));
		}
		return pdfBytes;
		// §§§END§§§
	}

	@Override
	public ModulisticaContentType getModulisticaStandard00() throws Exception {

		ModulisticaContentType modulistica = null;
		// caricamento modulistica STD_0_BO da file locale
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(FACCTConstants.MODULISTICA_STD_0_BO);
		ModuloWrapper wrapper = (ModuloWrapper) Utilities.unMarshallFromStream(is, ModuloWrapper.class);
		modulistica = new ModulisticaContentType();
		ModulisticaType modulisticaType = new ModulisticaType();
		modulisticaType.getModulo().add(wrapper.getModulo());
		modulistica.setModulistica(modulisticaType);
		// caricamento modulistica STD_0_BO da WS SUAP-Engine generaModulo
		/*
		 * CartServiceConfigurationParameters cartParams = this.modulisticaRfc186Service.getParametriConfigurazione(); if (cartParams != null) {
		 * String suapId = cartParams.getSuapId(); String codiceAttivitaBdr = FACCTConstants.CODICE_ATTIVITA_STD_0_BO; if
		 * (StringUtils.isNotBlank(suapId)) { // popolo la request GeneraModuloRequest modulisticaRequest = new GeneraModuloRequest(); HeaderType
		 * header = new HeaderType(); // per ottenere modulistica STD_0 viene usata come azione // AvvioSTD0 e come codice attivita' 01.12
		 * header.setAzione(AzioneType.AVVIO_ST_0); //TODO quando sarà presente il nuovo WSDL per il servizio generaModulo come azione sarà passata la
		 * stringa 'STAR-StandardBO' //header.setAzione(FACCTConstants.AZIONE_STD_0_BO); header.setCodiceAttivita(codiceAttivitaBdr);
		 * header.setCodiceComune(suapId); EndoprocedimentiType endos = new EndoprocedimentiType(); modulisticaRequest.setHeader(header);
		 * GeneraModuloResponse modulisticaResponse = null; if (log.isDebugEnabled()) log.debug(
		 * "getModulisticaStandard0() - caricamento della modulistica richiesta tramite invocazione del servizio SUAPENGINE. Azione:{}, CodiceAttivita:{}, CodiceComune (SUAP ID):{}"
		 * , new Object[] { header.getAzione().value(), header.getCodiceAttivita(), header.getCodiceComune() }); modulisticaResponse =
		 * this.modulisticaRfc186Service.generaModulo(modulisticaRequest); modulistica = modulisticaResponse.getModulisticaContent(); } else { throw
		 * new RuntimeException("Impossibile invocare i servizi CART: SUAP ID non configurato."); } } else { throw new
		 * RuntimeException("Impossibile invocare i servizi CART: parametri di configurazione del servizio mancanti."); }
		 */
		return modulistica;
	}

	@Override
	public DatiDomandaCart leggiDatiQuadro(HttpServletRequest req, String refModulo, String idQuadro, DatiDomandaCart dati) {

		// §§§BEGIN§§§
		// recupero i metadati che definiscono i campi previsti per il quadro
		QuadroType quadro = CartModuloHelper.findQuadro(dati.getModulistica(), refModulo, idQuadro);
		List<ItemType> items = quadro.getItem();
		for (ItemType item : items) {
			// List<Integer> indexes = new ArrayList<Integer>();
			leggiDatiItemRecursive(req, refModulo, item, dati, new IndiceIdSemantico());
		}
		return dati;
		// §§§END§§§
		// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void leggiDatiItemRecursive(HttpServletRequest req, String riferimentoModulo, ItemType item, DatiDomandaCart dati,
			IndiceIdSemantico indice) {

		// §§§BEGIN§§§
		/*
		 * se item è un campo o un file leggo il valore dalla request e lo carico in dati se invece è un contenitore invoco ricorsivamente la funzione
		 * per caricare i dati dei campi contenuti nel contenitore
		 */
		// boolean indexed = !nestedTables.isEmpty();
		// indice.
		int numlevels = indice.contaLivelli();
		String idSemantico = null;
		if (item.getCampo() != null || item.getFile() != null) {
			idSemantico = item.getCampo() != null ? item.getCampo().getIdSemantico() : item.getFile().getIdSemantico();
			/*
			 * se il campo è indicizzato (si trova all'interno di una tabella) mi aspetto di trovare n parametri della request <idSemantico>_row_0,
			 * <idSemantico>_row_1 , ..., <idSemantico>_row_n
			 */
			ValoreIdSemantico value = null;
			if (numlevels > 0) {
				List livelli = indice.getLivelli();
				value = leggiDatiLivelloTabella(req, (List<TabellaType>) livelli, item, dati, new IndiceIdSemantico());
			}
			/*
			 * se non è all'interno di una tabella allora leggo un solo parametro della request uguale all'idSemantico tenendo però presente che il
			 * parametro può essere associato a valori multipli.
			 */
			else {
				value = leggiValoreSingolo(req, item, dati, null);
			}
			dati.setValoreIdSemantico(riferimentoModulo, idSemantico, value);
			if (log.isDebugEnabled()) {
				log.debug("leggiDatiItemRecursive() - nuovo valore per l'idSemantico: {}: {}", new Object[] { idSemantico, value });
			}
		} else if (item.getSezione() != null) {
			List<ItemType> campi = item.getSezione().getItem();
			for (ItemType innerItem : campi) {
				leggiDatiItemRecursive(req, riferimentoModulo, innerItem, dati, indice);
			}
		} else if (item.getTabella() != null) {
			List<ColonnaType> colonne = item.getTabella().getColonna();
			indice.incrementaLivello(item.getTabella());
			for (ColonnaType column : colonne) {
				ItemType columnItem = column.getCella();
				leggiDatiItemRecursive(req, riferimentoModulo, columnItem, dati, indice);
			}
			indice.decrementaLivello();
		}
		// §§§END§§§
	}

	@SuppressWarnings("unchecked")
	private ValoreIdSemantico leggiValoreSingolo(HttpServletRequest req, ItemType item, DatiDomandaCart dati, IndiceIdSemantico indice) {

		String idSemantico = item.getCampo() != null ? item.getCampo().getIdSemantico() : item.getFile().getIdSemantico();
		boolean hasMultipleValues = item.getCampo() != null ? item.getCampo().isMultiValore() : !item.getFile().getMaxUpload().equals("1");
		Map<String, String[]> parametersMap = req.getParameterMap();
		String[] values = null;
		ValoreIdSemantico vis = null;
		String paramName = idSemantico + CartModuloHelper.buildIndexingSuffix(indice);
		if (parametersMap.containsKey(paramName)) {
			values = req.getParameterValues(paramName);
			if (hasMultipleValues) {
				vis = new ValoreIdSemantico(values);
			} else {
				String value = "";
				if (values != null && values.length > 0) {
					value = values[0];
				}
				vis = new ValoreIdSemantico(value);
			}
			/*
			 * Se si stratta di un campo file imposto il riferimento all'id semantico negli oggetti FileInfo presenti nei dati della domanda ad
			 * indicare che l'upload dei files è stato confermato con la conferma del quadro
			 */
			if (item.getFile() != null) {
				for (int i = 0; i < values.length; i++) {
					if (NumberUtils.isNumber(values[i])) {
						Integer codiceOggetto = NumberUtils.toInt(values[i]);
						// if (!Utilities.isBackOffice()) {
						FileInfo fi = dati.getAllegatoByCodiceOggetto(codiceOggetto);
						if (fi != null) {
							fi.setIdSemantico(idSemantico);
						} else {
							log.error("leggiValoreSingolo - nessun allegato presente nella domanda associato all'id oggetto {}.",
									new Object[] { values[i] });
						}
						// } else {
						/*
						 * nel BO i campi file sono dei semplici elenchi a tendina per la selezione degli allegati dell'istanza e => non viene postato
						 * nessun file. I files selezionati devono essere recuperati dalla tabella Oggetti
						 */
						/*
						 * Oggetti file = this.oggettiService.findById(new PkId(codiceOggetto)); FileInfo fi = new FileInfo();
						 * fi.setIdOggetto(codiceOggetto); fi.setIdSemantico(idSemantico); fi.setDimensione(file.getDimensioneFile()); String fileName
						 * = file.getNomefile(); String prefix = codiceOggetto.toString() + "-"; if (fileName.startsWith(prefix)) { fileName =
						 * fileName.substring(prefix.length()); } fi.setNomeFile(fileName); dati.addAllegato(fi); }
						 */
					} else {
						log.error("leggiValoreSingolo - trovato id oggetto non numerico {} associato all'id semantico {}.", new Object[] { values[i],
								idSemantico });
					}
				}
			}
		}
		/*
		 * il parametro previsto può non essere stato postato perchè si tratta di un checkbox deselezionato in questo caso devo impostare il valore id
		 * semantico a "" perchè poteva essere stato valorizzato precedentemente
		 */
		else {
			boolean rowDeleted = false;
			if (indice != null && indice.contaLivelli() > 0) {
				TabellaType tabella = (TabellaType) indice.getLivello();
				// gestione id tabelle non univoci
				String countTableIdsParamName = MessageFormat.format("{0}_idcount", new Object[] { tabella.getId() });
				String numIds = req.getParameter(countTableIdsParamName);
				String tableId = tabella.getId();
				int idsCount = NumberUtils.toInt(numIds, 0);
				// fine gestione id tabelle non univoci
				// gestione id tabelle annidate
				tableId = CartModuloHelper.getIdTabella(tabella, idsCount, indice);
				String checkRowExistsParamName = MessageFormat.format("tabella_{0}_row_{1}", new Object[] { tableId, indice.getIndice() });
				rowDeleted = !parametersMap.containsKey(checkRowExistsParamName);
			}
			if (!rowDeleted) {
				/*
				 * il parametro previsto può non essere stato postato perchè si tratta di un checkbox deselezionato in questo caso devo impostare il
				 * valore id semantico a "" perchè poteva essere stato valorizzato precedentemente
				 */
				if (CartModuloHelper.rendersAsCheckbox(item.getCampo())) {
					String strVal = "";
					if (hasMultipleValues) {
						values = new String[] { strVal };
						vis = new ValoreIdSemantico(values);
					} else {
						vis = new ValoreIdSemantico(strVal);
					}
				}
				/*
				 * Il parametro può non essere stato postato anche nel caso in cui l'iutente abbia selezionato uno o più files nel controllo
				 * jquery.Multifile e poi ha eliminato tutti i files selezionati
				 */
				else if (item.getFile() != null) {
					if (hasMultipleValues) {
						vis = new ValoreIdSemantico(new String[0]);
					} else {
						vis = new ValoreIdSemantico("");
					}
				}
			} else {
				return null;
			}
		}
		if (item.getFile() != null && vis != null) {
			vis.setFile(true);
		}
		return vis;
	}

	private ValoreIdSemantico leggiDatiLivelloTabella(HttpServletRequest req, List<TabellaType> tabelle, ItemType campo, DatiDomandaCart dati,
			IndiceIdSemantico indice) {

		List<ValoreIdSemantico> valori = new ArrayList<ValoreIdSemantico>();
		TabellaType tabella = tabelle.get(indice.contaLivelli());
		// int numLevels = indice.contaLivelli();
		// gestione id tabelle non univoci
		String countTableIdsParamName = MessageFormat.format("{0}_idcount", new Object[] { tabella.getId() });
		String numIds = req.getParameter(countTableIdsParamName);
		String tableId = tabella.getId();
		int idsCount = NumberUtils.toInt(numIds, 0);
		// fine gestione id tabelle non univoci
		// gestione id tabelle annidate
		// Integer[] arrayIndiciPrevlevels = indice.vettoreIndici();
		indice.incrementaLivello(tabella);
		tableId = CartModuloHelper.getIdTabella(tabella, idsCount, indice);
		indice.decrementaLivello();
		// recupero il valore massimo di progressivo riga da leggere dal
		// valore postato dal campo hidden derivato dall'id della
		// tabella: tabella_{tabella.id}_maxprog
		String maxProgParamName = MessageFormat.format("tabella_{0}_maxprog", new Object[] { tableId });
		int maxProg = -1;
		String maxProgString = req.getParameter(maxProgParamName);
		if (StringUtils.isNotBlank(maxProgString)) {
			try {
				maxProg = Integer.parseInt(maxProgString);
			} catch (NumberFormatException e) {
				String message = MessageFormat
						.format("Impossibile leggere i valori indicizzati per la tabella {0} perché il parametro request {1} non contiene un valore numerico valido.",
								new Object[] { tabella.getId(), maxProgParamName });
				log.error("leggiDatiItemRecursive() - Errore: {}", new Object[] { message });
				throw new RuntimeException(message, e);
			}
		} else {
			String message = MessageFormat.format(
					"Impossibile leggere i valori indicizzati per la tabella {0} perché manca il parametro request  di nome {1}.", new Object[] {
							tabella.getId(), maxProgParamName });
			log.error("leggiDatiItemRecursive() - Errore: {}", new Object[] { message });
			throw new RuntimeException(message);
		}
		indice.incrementaLivello(tabella);
		for (int i = 0; i <= maxProg; i++) {
			ValoreIdSemantico tempVis = null;
			if (indice.contaLivelli() == tabelle.size()) {
				// se è l'ultimo livello di annidamento leggo i valori del campo
				// ciclando tutti i progressivi per quel livello e quei valori
				// di indici
				tempVis = leggiValoreSingolo(req, campo, dati, indice);
			} else {
				tempVis = leggiDatiLivelloTabella(req, tabelle, campo, dati, indice);
			}
			// se il ValoreIdSemantico restituito è null significa che si
			// tratta di una riga cancellata
			if (tempVis != null) {
				valori.add(tempVis);
			}
			indice.incrementaIndice();
		}
		indice.decrementaLivello();
		ValoreIdSemantico vis = new ValoreIdSemantico(valori);
		vis.setFile(campo.getFile() != null);
		return vis;
	}

	@Override
	public CartModuloHelper getModuloHelperInstance(ModuloType modulo, DatiDomandaCart inputData, AutocompilerConfig autocompilerConfig) {

		CartModuloHelper helper = null;
		try {
			helper = new CartModuloHelper(modulo, inputData);
			helper.setAutocompilerConfig(autocompilerConfig);
			helper.setOggettiService(this.oggettiService);
		} catch (Exception e) {
			log.error("getValidatorInstanceByName - errore nella creazione o nel recupero dell'istanza del validator: ", e);
			throw new RuntimeException(e);
		}
		return helper;
	}
}
