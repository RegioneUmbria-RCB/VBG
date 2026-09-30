package it.gruppoinit.pal.gp.core.domain.autocompiler;

import it.eng.suap.xengine.model.modulistica.CampoType;
import it.eng.suap.xengine.model.modulistica.EspressioneType;
import it.eng.suap.xengine.model.modulistica.FileType;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.MessaggioErrore;
import it.gruppoinit.pal.gp.core.domain.cart.ValoreIdSemantico;
import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
import it.gruppoinit.pal.gp.core.service.CartModulisticaService;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.service.OggettiinfoService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.criterion.MatchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "AllegatoEndoObbligatorioValidator")
public class AllegatoEndoObbligatorioValidator implements IAutocompilerValidator {

    private final Logger log = LoggerFactory.getLogger(AllegatoEndoObbligatorioValidator.class);
    @Autowired
    EndoRegioneToscanaService endoRegioneToscanaService;
    @Autowired
    CartModulisticaService cartModulisticaService;
    @Autowired
    OggettiService oggettiService;

    /**
     * La validazione viene scatenata sul campo che contiene il nome
     * dell'allegato endo o della scheda dinamica e deve restituire errore se
     * l'elemento selezionato (allegato o scheda) è configurato come
     * obbligatorio nel BO e almeno uno dei campi della riga è vuoto.
     */
    public List<ErroreValidazione> validaValoreCampo(CampoType campo, String refModulo, DatiDomandaCart datiDomanda, String idQuadro,
	    String idModulo, IndiceIdSemantico rowIndex, ConfigOptions configOptions) {

	// recupero il valore del campo all' indice rowIndex
	ValoreIdSemantico valore = datiDomanda.getValoreIdSemanticoPerIndice(refModulo, campo.getIdSemantico(), rowIndex);
	ValoreIdSemantico valoreEndo = null;
	List<ErroreValidazione> errors = new ArrayList<ErroreValidazione>();
	/*
	 * if(true){ throw new RuntimeException("Test Eccezione!!!"); }
	 */
	if (valore != null) {
	    valoreEndo = valore;
	    // recupero il codice dell'endoprocedimento e la descrizione
	    // dell'allegato/scheda
	    String codiceEndo = null;
	    String descAllegato = null;
	    boolean validateEndo = true;
	    /*
	     * se siamo in una domanda STD_2 la descrizione è contenuta nel
	     * valore del campo e il codice endo si trova nel valore del campo
	     * associato (il cui id semantico è specificato nel parametro di
	     * configurazione 'idSemanticoEndo') allo stesso indice rowIndex
	     */
	    List<ConfigOption> options = configOptions.getConfigOptions();
	    ConfigOption campoEndoOpt = null;
	    ConfigOption campoFileOpt = null;
	    String idSemanticoEndo = campo.getIdSemantico();
	    for (ConfigOption option : options) {
		if (option.getOptionName().equalsIgnoreCase(FACCTConstants.AUTOCOMP_CFG_IDSEMANTICOENDO)) {
		    campoEndoOpt = option;
		} else if (option.getOptionName().equalsIgnoreCase(FACCTConstants.AUTOCOMP_CFG_IDSEMANTICOALLEGATO)) {
		    campoFileOpt = option;
		}
	    }
	    if (campoFileOpt == null) {
		/*
		 * throw new RuntimeException(
		 * "Impossibile validare il campo perchè manca il parametro di configurazione "
		 * + FACCTConstants.AUTOCOMP_CFG_IDSEMANTICOALLEGATO);
		 */
		log.error("Impossibile validare il campo perchè manca il parametro di configurazione {}",
			FACCTConstants.AUTOCOMP_CFG_IDSEMANTICOALLEGATO);
		return errors;
	    }
	    if (campoEndoOpt != null) {
		idSemanticoEndo = campoEndoOpt.getOptionValue();
		valoreEndo = datiDomanda.getValoreIdSemanticoPerIndice(refModulo, idSemanticoEndo, rowIndex);
		if (valoreEndo != null) {
		    // do per scontato che il valore del codice endo alla
		    // rowIndex-esima riga si scalare
		    // codiceEndo = valoreEndo.getValoreScalare();
		    Pattern idProcFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDPROCEDIMENTO);
		    Pattern idAProcFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDALBEROPROCDOC);
		    Matcher m = idProcFilterPattern.matcher(valoreEndo.getValoreScalare());
		    if (m.find()) {
			codiceEndo = m.group(1);
		    } else {
			m = idAProcFilterPattern.matcher(valoreEndo.getValoreScalare());
			if (m.find()) {
			    codiceEndo = m.group(1);
			    validateEndo = false;
			}
		    }
		    descAllegato = valore.getValoreScalare();
		} else {
		    /*
		     * throw new RuntimeException(
		     * "Impossibile validare il campo perché nei dati della domanda non c'è nessun valore associato all'id semantico "
		     * + idSemanticoEndo);
		     */
		    log.error("Impossibile validare il campo perché nei dati della domanda non c'è nessun valore associato all'id semantico {}.",
			    idSemanticoEndo);
		    return errors;
		}
	    }
	    /*
	     * se siamo in una domanda STD_0 il valore di questo campo contiene
	     * sia il codice endo sia la descrizione dell'allegato/scheda. Sarà
	     * sufficiente estrarli dal valore che è scritto secondo il pattern
	     * E[CODICEENDO]-DESCRIZIONEENDO
	     */
	    else {
		Pattern idProcFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDPROCEDIMENTO);
		Pattern allegatoFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_DESCALLEGATO);
		Pattern idAProcFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDALBEROPROCDOC);
		Matcher m = idProcFilterPattern.matcher(valore.getValoreScalare());
		if (m.find()) {
		    codiceEndo = m.group(1);
		} else {
		    m = idAProcFilterPattern.matcher(valoreEndo.getValoreScalare());
		    if (m.find()) {
			codiceEndo = m.group(1);
			validateEndo = false;
		    }
		}
		m = allegatoFilterPattern.matcher(valore.getValoreScalare());
		if (m.find()) {
		    descAllegato = m.group(1);
		}
	    }
	    if (log.isDebugEnabled()) {
		log.debug("validaValoreCampo - codice procedimento: {}, descrizione allegato/scheda: {}", new Object[] { codiceEndo, descAllegato });
	    }
	    // determino se secondo le configurazioni del BO l'allegato o scheda
	    // selezionato dall'utente è obbligatorio
	    boolean isBOMandatory = false;
	    boolean isDsRequired = false;
	    boolean missingEndo = false;
	    String missingEndoErrMsg = null;
	    boolean missingAllegato = false;
	    String missingAllegatoErrMsg = null;
	    if (StringUtils.isNotEmpty(codiceEndo)) {
		List<String> endoAttivi = datiDomanda.getDatiContestoDomanda().getEndoNoCartAttivi();
		if (endoAttivi.contains(codiceEndo) || !validateEndo) {
		    // identifico l'allegato endo o la scheda dinamica o il
		    // documento ereditato dall'alberoproc
		    List<Allegati> allegatiEndo = this.endoRegioneToscanaService.getAllegatiEndoAttivi(endoAttivi, codiceEndo, descAllegato,
			    FieldOperationsEnum.EQ);
		    if (!allegatiEndo.isEmpty()) {
			if (allegatiEndo.size() > 1) {
			    String errMsg = MessageFormat
				    .format("Impossibile validare il campo perché sono stati trovati più di un allegato per l''endo {0} aventi descrizione {1}.",
					    new Object[] { codiceEndo, descAllegato });
			    log.error("validaValoreCampo - {}", new Object[] { errMsg });
			    // throw new RuntimeException(errMsg);
			}
			Allegati allegato = allegatiEndo.get(0);
			isBOMandatory = BooleanUtils.isTrue(allegato.getRichiesto());
			isDsRequired = BooleanUtils.toBoolean(allegato.getFoRichiedefirma());
		    } else {
			List<Inventarioprocdyn2modellit> schedeEndo = this.endoRegioneToscanaService.getSchedeDinamicheEndoAttivi(endoAttivi,
				codiceEndo, descAllegato, FieldOperationsEnum.EQ);
			if (!schedeEndo.isEmpty()) {
			    if (schedeEndo.size() > 1) {
				String errMsg = MessageFormat
					.format("Impossibile validare il campo perché sono state trovate più di una scheda dinamica per l''endo {0} aventi descrizione {1}.",
						new Object[] { codiceEndo, descAllegato });
				log.error("validaValoreCampo -  {}", new Object[] { errMsg });
				// throw new RuntimeException(errMsg);
			    }
			    Inventarioprocdyn2modellit scheda = schedeEndo.get(0);
			    isBOMandatory = !BooleanUtils.isTrue(scheda.getFlagFacoltativa());
			    isDsRequired = scheda.getFlagTipofirma() != null && scheda.getFlagTipofirma() > 0;
			} else {
			    // se non è ne un allegato endo ne una scheda
			    // dinamica allora può essere un documento ereditato
			    // dall'albero degli interventi
			    // Lis
			    List<AlberoprocDocumenti> aprocDocs = endoRegioneToscanaService.getDocumentiEreditatiEndoAttivi(
				    NumberUtils.toInt(codiceEndo), descAllegato, FieldOperationsEnum.EQIGNORECASE);
			    if (!aprocDocs.isEmpty()) {
				// se ho più di un documento pubblicato sul FO
				// con la stessa descrizione non posso
				// completare la validazione
				if (aprocDocs.size() > 1) {
				    String errMsg = MessageFormat
					    .format("Impossibile validare il campo perché è stato trovato più di un documento ereditato dall'albero dei procedimenti aventi descrizione {0}.",
						    new Object[] { descAllegato });
				    log.error("validaValoreCampo -  {}", new Object[] { errMsg });
				}
				AlberoprocDocumenti doc = aprocDocs.get(0);
				isBOMandatory = doc.getRichiesto() == null ? Boolean.FALSE : doc.getRichiesto();
				isDsRequired = BooleanUtils.toBoolean(doc.getFoRichiedefirma());
			    } else {
				missingAllegato = true;
				missingAllegatoErrMsg = MessageFormat
					.format("Non esiste nessun allegato o scheda dinamica per l''endoprocedimento {0} corrispondente alla descrizione specificata nel campo alla riga {1}",
						new Object[] { codiceEndo, rowIndex.getIndice() + 1 });
			    }
			}
		    }
		} else {
		    missingEndo = true;
		    missingEndoErrMsg = MessageFormat
			    .format("L''endoprocedimento specificato nel campo associato all''id semantico {0} alla riga {1} non è fra gli endoprocedimenti attivati per questa domanda.",
				    new Object[] { idSemanticoEndo, rowIndex.getIndice() + 1 });
		}
	    } else {
		/*
		 * if (valoreEndo != null &&
		 * StringUtils.isNotEmpty(valoreEndo.getValoreScalare())) {
		 * missingEndo = true; missingEndoErrMsg = MessageFormat
		 * .format(
		 * "Nel campo associato all''id semantico {0} alla riga {1} il codice endoprocedimento deve essere specificato nel formato ''E[numero]''."
		 * , new Object[] { idSemanticoEndo, rowIndex + 1 }); }
		 */
	    }
	    // se non è stato individuato l'endoprocedimento messaggio di errore
	    // sul campo codice endo
	    if (missingEndo) {
		MessaggioErrore msg = new MessaggioErrore(missingEndoErrMsg, rowIndex.vettoreIndici());
		ErroreValidazione error = new ErroreValidazione(idSemanticoEndo, msg);
		errors.add(error);
	    }
	    // se non è stato individuato l'allegato/scheda messaggio di errore
	    // sul campo descrizione
	    else if (missingAllegato) {
		MessaggioErrore msg = new MessaggioErrore(missingAllegatoErrMsg, rowIndex.vettoreIndici());
		ErroreValidazione error = new ErroreValidazione(campo.getIdSemantico(), msg);
		errors.add(error);
	    }
	    // se specificato ed individuato l'allegato/scheda
	    else if (StringUtils.isNotBlank(valoreEndo.getValoreScalare())) {
		// allegato/scheda specificato correttamente 
		// recupero il valore del campo file per l'upload degli allegati
		ValoreIdSemantico valoreCampoFile = datiDomanda.getValoreIdSemanticoPerIndice(refModulo, campoFileOpt.getOptionValue(), rowIndex);
		String missingFileErrMsg = null;
		boolean isError = false;
		// gestione della validazione della firma digitale
		String[] codiciOggetti = new String[0];
		if (valoreCampoFile != null) {
		    codiciOggetti = valoreCampoFile.getValoreVettoriale();
		}
		for (int i = 0; i < codiciOggetti.length; i++) {
		    String objId = codiciOggetti[i];
		    if (NumberUtils.isNumber(objId)) {
			Integer objIdInt = NumberUtils.toInt(objId);
			FileInfo fi = datiDomanda.getAllegatoByCodiceOggetto(objIdInt);
			AllegatoDaFirmare adf = datiDomanda.getAllegatoDaFirmare(objIdInt);
			if (fi != null) {
			    if (isDsRequired) {
				if (!fi.isFirmaValidata()) {
				    Oggetti file = this.oggettiService.findById(new PkId(objIdInt));
				    if (file != null) {
					adf = cartModulisticaService.validaFirmaDigitale(file);
					datiDomanda.addAllegatoDaFirmare(adf);
				    }
				}
			    }
			    else{
				if(fi.isFirmaValidata() && adf != null){
				    datiDomanda.removeAllegatoDaFirmare(objIdInt);
				    adf = null;
				}
			    }
			}
			if (adf != null) {
			    adf.setDescrizione(descAllegato);
			}
		    }
		}
		//l'obbligatorietà
		if (isBOMandatory) {
		    // se l'allegato/scheda specificato è obbligatorio sul BO e
		    // non nella modulistica CART -> messaggio di errore 'campo
		    // obbligatorio' sul campo file se vuoto
		    // if (!isCARTMandatory) {
		    List<FileInfo> uploadedFiles = null;
		    if (valoreCampoFile != null) {
			codiciOggetti = valoreCampoFile.getValoreVettoriale();
			uploadedFiles = new ArrayList<FileInfo>();
			for (int i = 0; i < codiciOggetti.length; i++) {
			    if (StringUtils.isNotBlank(codiciOggetti[i])) {
				FileInfo uploadedFile = datiDomanda.getAllegatoByCodiceOggetto(NumberUtils.toInt(codiciOggetti[i], 0));
				if (null != uploadedFile) {
				    uploadedFiles.add(uploadedFile);
				}
			    }
			}
		    }
		    if (uploadedFiles == null || uploadedFiles.isEmpty()) {
			isError = true;
			missingFileErrMsg = MessageFormat.format(
				"L''allegato specificato alla riga {0} è obbligatorio. E'' necessario caricare almeno un file.",
				new Object[] { rowIndex.getIndice() + 1 });
		    }
		    // }
		    // } else {
		    // if (isCARTMandatory) {
		    // //se l'allegato/scheda specificato non è obbligatorio sul
		    // BO ma lo è per la modulistica CART -> messaggio warning
		    // 'allegato non specificato: caricare il file o eliminare
		    // la riga' sul campo file
		    // missingFileErrMsg = MessageFormat
		    // .format("Alla riga {0} non è stato caricato nessun file per l''allegato selezionato. Caricare almeno un file oppure eliminare la riga con il bottone ''Elimina''.",
		    // new Object[] { rowIndex + 1 });
		    // }
		}
		if (StringUtils.isNotEmpty(missingFileErrMsg)) {
		    MessaggioErrore msg = new MessaggioErrore(missingFileErrMsg, rowIndex.vettoreIndici());
		    ErroreValidazione error = new ErroreValidazione(campoFileOpt.getOptionValue(), msg);
		    error.setBloccante(isError);
		    errors.add(error);
		}
	    }
	}
	for (ErroreValidazione error : errors) {
	    error.setIdModulo(idModulo);
	    error.setIdQuadro(idQuadro);
	}
	return errors;
    }

    /**
     * Il metodo non è implementato perchè non viene mai invocato; restituisce
     * una lista vuota di errori.
     */
    public List<ErroreValidazione> validaValoreFile(FileType campo, String refModulo, DatiDomandaCart datiDomanda, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions) {

	return new ArrayList<ErroreValidazione>();
    }

}
