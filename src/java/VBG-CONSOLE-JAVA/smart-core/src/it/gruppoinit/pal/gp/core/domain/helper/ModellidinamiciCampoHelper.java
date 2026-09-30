package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.ProprietaCampi;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.helper.SchedeDinamicheTL;
import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jfree.util.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.FieldError;

@XmlRootElement(name = "ModellidinamiciCampoHelper")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModellidinamiciCampoHelper", propOrder = { "dyn2Campi", "valore", "valoreDecodificato", "indice", "indicemolteplicita",
	"indiceBlocco", "obbligatorio", "regoleDipendenti", "idModello" })
public class ModellidinamiciCampoHelper {

    @XmlElement(name = "dyn2Campi", required = true)
    private Dyn2Campi dyn2Campi;
    @XmlElement(name = "nomeCampo", required = true)
    private String nomeCampo;
    @XmlElement(name = "valore", required = true)
    private String valore;
    @XmlElement(name = "valoreDecodificato", required = true)
    private String valoreDecodificato;
    @XmlElement(name = "indice", required = false)
    private Integer indice = 0;
    @XmlElement(name = "indicemolteplicita", required = false)
    private Integer indicemolteplicita = 0;
    @XmlElement(name = "indiceBlocco", required = false)
    private Integer indiceBlocco;
    private List<FieldError> errors;
    private ApplicationContext context;
    @XmlElement(name = "obbligatorio", required = true)
    private boolean obbligatorio;
    @XmlElement(name = "regoleDipendenti", required = false)
    private List<Dyn2Regole> regoleDipendenti = new ArrayList<Dyn2Regole>();
    @XmlElement(name = "idModello", required = false)
    private Integer idModello;

    public enum tipoCampo {
	CHECKBOX, LISTA_MULTIPLA, LISTA_SIGEPRO, LISTA_VALORI, NUMERO_INTERO, NUMERO_DECIMALE, RICERCA_AJAX, TESTO
    }

    public void setDyn2Campi(Dyn2Campi dyn2Campi) {

	this.dyn2Campi = dyn2Campi;
    }

    public Dyn2Campi getDyn2Campi() {

	return dyn2Campi;
    }

    public String getNomeCampo() {

	return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {

	this.nomeCampo = nomeCampo;
    }

    public String getValore() {

	return valore;
    }

    public String getValore(String defaultValue) {

	return valore != null ? valore : defaultValue;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getValoreDecodificato() {

	return valoreDecodificato;
    }

    public String getValoreDecodificato(String defaultValue) {

	return valoreDecodificato != null ? valoreDecodificato : defaultValue;
    }

    public void setValoreDecodificato(String valoreDecodificato) {

	this.valoreDecodificato = valoreDecodificato;
    }

    public Integer getIndice() {

	return indice;
    }

    public void setIndice(Integer indice) {

	this.indice = indice;
    }

    public Integer getIndicemolteplicita() {

	return indicemolteplicita;
    }

    public void setIndicemolteplicita(Integer indicemolteplicita) {

	this.indicemolteplicita = indicemolteplicita;
    }

    public void setErrors(List<FieldError> errorMessages) {

	this.errors = errorMessages;
    }

    public List<FieldError> getErrors() {

	return errors;
    }

    public void setApplicationContext(ApplicationContext context) {

	this.context = context;
    }

    public ApplicationContext getApplicationContext() {

	return this.context;
    }

    public void setObbligatorio(boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    public boolean isObbligatorio() {

	return this.obbligatorio;
    }

    public List<Dyn2Regole> getRegoleDipendenti() {

	return regoleDipendenti;
    }

    public void setRegoleDipendenti(List<Dyn2Regole> regoleDipendenti) {

	this.regoleDipendenti = regoleDipendenti;
    }

    public Integer getIdModello() {

	return idModello;
    }

    public void setIdModello(int idModello) {

	this.idModello = idModello;
    }

    public Integer getIndiceBlocco() {

	return indiceBlocco;
    }

    public void setIndiceBlocco(Integer indiceBlocco) {

	this.indiceBlocco = indiceBlocco;
    }

    public boolean isRigaBlocco() {

	return getIndiceBlocco() != null && getIndiceBlocco().intValue() > -1;
    }

    public String renderCampo() {

	return renderCampo(false);
    }

    /**
     * TODO se questo metodo deve essere esposto il rendering deve essere modificato utilizzando i template di velocity
     * 
     * @param renderEmptyField
     * @return
     */
    public String renderCampo(boolean renderEmptyField) {

	String campoHtml = gestControllo(renderEmptyField);
	return campoHtml;
    }

    private String gestControllo(boolean renderEmpty) {

	Dyn2Campi d2c = getDyn2Campi();
	TipoControlloEnum tipodato = TipoControlloEnum.valueOf(d2c.getTipodato());
	switch (tipodato) {
	case Checkbox:
	    return renderCheckBox();
	case Lista:
	    return renderLista(false);
	case ListaSIGePro:
	    return renderListaSigepro();
	case Data:
	    return renderData();
	case MultiLista:
	    return renderLista(true);
	case NumericoDouble:
	    return renderNumerico(true);
	case NumericoIntero:
	    return renderNumerico(false);
	case Ricerca:
	    return renderRicerca();
	case Testo:
	    return renderTesto();
	case Upload:
	    return renderFileUpload();
	case Bottone:
	    return renderBottone();
	case RadioButtons:
	    return renderRadioButtons();
	default:
	    break;
	}
	return "...";
    }

    public String getNameOrIdFromCampo(boolean isId) {

	Integer codiceCampo = this.getDyn2Campi().getId().getCodice();
	Integer indice = this.getIndice();
	Integer indiceMolteplicita = this.getIndicemolteplicita();
	String result = "FLD_".concat(StringUtils.defaultIfEmpty(String.valueOf(codiceCampo), "NULL")).concat("_")
		.concat(StringUtils.defaultIfEmpty(String.valueOf(indice), "NULL")).concat("_")
		.concat(StringUtils.defaultIfEmpty(String.valueOf(indiceMolteplicita), "NULL"));
	if (isId) {
	    result = result.concat("_id");
	}
	return result;
    }

    public String getProprietaCampo(String nomeProprieta, String defaultValue) {

	ProprietaCampi prop = ProprietaCampi.valueOf(nomeProprieta);
	return getProprietaCampo(prop, defaultValue);
    }

    public String getProprietaCampo(ProprietaCampi proprieta, String defaultValue) {

	String result = StringUtils.defaultIfEmpty(defaultValue, "");
	if (this.getDyn2Campi() != null) {
	    Set<Dyn2Campiproprieta> proprietaCampo = this.getDyn2Campi().getDyn2Campiproprietas();
	    for (Dyn2Campiproprieta dyn2Campiproprieta : proprietaCampo) {
		if (dyn2Campiproprieta.getId().getProprieta().equalsIgnoreCase(proprieta.name())) {
		    result = dyn2Campiproprieta.getValore();
		}
	    }
	}
	return result;
    }

    public List<ChiaveValoreBean<String, String>> splitOpzioniLista(String fullValue) {

	List<ChiaveValoreBean<String, String>> options = new ArrayList<ChiaveValoreBean<String, String>>();
	if (StringUtils.isNotBlank(fullValue)) {
	    String[] parts = fullValue.split(";");
	    for (String part : parts) {
		ChiaveValoreBean<String, String> option = new ChiaveValoreBean<String, String>();
		String[] subParts = part.split("\\$");
		if (subParts.length == 0) {
		    subParts = new String[] { "" };
		}
		option.setChiave(subParts[0].trim());
		if (subParts.length > 1) {
		    option.setValore(subParts[1].trim());
		} else {
		    option.setValore(subParts[0].trim());
		}
		options.add(option);
	    }
	}
	return options;
    }

    public ChiaveValoreBean<String, String> creaOpzioneLista(String value) {

	ChiaveValoreBean<String, String> emptyOption = new ChiaveValoreBean<String, String>();
	emptyOption.setChiave(value);
	emptyOption.setValore(value);
	return emptyOption;
    }

    public ChiaveValoreBean<String, String> creaOpzioneListaVuota() {

	return creaOpzioneLista("");
    }

    public boolean isEmptyValue(Object val) {

	if (val != null) {
	    String strVal = val.toString();
	    return StringUtils.isBlank(strVal);
	} else {
	    return true;
	}
    }

    public static List<String> splitEstensioniConsentite(String fullValue) {

	List<String> exts = new ArrayList<String>();
	if (StringUtils.isNotBlank(fullValue)) {
	    String[] parts = fullValue.split(";");
	    for (String part : parts) {
		part = part.trim();
		if (!part.startsWith(".")) {
		    part = "." + part;
		}
		exts.add(part.toLowerCase());
	    }
	}
	return exts;
    }

    public String getFormattedAcceptFileAttribute(String allowedExtensionsFieldProperty) {

	if (StringUtils.isBlank(allowedExtensionsFieldProperty)) {
	    allowedExtensionsFieldProperty = FACCTConstants.RFC239_ALLOWED_EXTENSIONS;
	}
	List<String> acceptedExtensions = splitEstensioniConsentite(allowedExtensionsFieldProperty);
	StringBuilder sb = new StringBuilder();
	if (acceptedExtensions != null) {
	    boolean isFirst = true;
	    for (int i = 0; i < acceptedExtensions.size(); i++) {
		String ext = acceptedExtensions.get(i);
		if (StringUtils.isNotBlank(ext)) {
		    if (!isFirst) {
			sb.append(", ");
		    }
		    sb.append(ext);
		    isFirst = false;
		}
	    }
	}
	return sb.toString();
    }

    public List<FieldError> validaCampo() {

	Dyn2Campi d2c = getDyn2Campi();
	List<FieldError> retErrors = new ArrayList<FieldError>();
	if (d2c != null) {
	    switch (TipoControlloEnum.valueOf(d2c.getTipodato())) {
	    case Bottone:
		retErrors = validaBottone();
		break;
	    case Checkbox:
		retErrors = validaCheckBox();
		break;
	    case Lista:
		retErrors = validaLista(false);
		break;
	    case ListaSIGePro:
		retErrors = validaListaSigepro();
		break;
	    case Data:
		retErrors = validaData();
		break;
	    case MultiLista:
		retErrors = validaLista(true);
		break;
	    case NumericoDouble:
		retErrors = validaNumerico(true);
		break;
	    case NumericoIntero:
		retErrors = validaNumerico(false);
		break;
	    case RadioButtons:
		retErrors = validaRadioButtons();
		break;
	    case Ricerca:
		retErrors = validaRicerca();
		break;
	    case Testo:
		retErrors = validaTesto();
		break;
	    case Upload:
		retErrors = validaUpload();//TODO verificare anche che il file uploadato non sia vuoto
		break;
	    default:
		break;
	    }
	}
	this.errors = retErrors;
	return retErrors;
    }

    private List<FieldError> validaTesto() {

	List<FieldError> errors = new ArrayList<FieldError>();
	FieldError error = validaObbligatorio();
	if (error != null) {
	    errors.add(error);
	} else {
	    error = validaMaxLength();
	    if (null != error) {
		errors.add(error);
	    }
	    error = validaRegExp();
	    if (null != error) {
		errors.add(error);
	    }
	}
	return errors;
    }

    private FieldError validaObbligatorio() {

	FieldError fe = null;
	if (isObbligatorio()) {
	    if (StringUtils.isBlank(valore)) {
		String errMsg = Utilities.getMessageFromBundle(context, "alert.required", null);
		String errCode = "dyn2modellit.error.mandatory";
		fe = buildFieldError(errCode, null, errMsg);
	    }
	}
	return fe;
    }

    private FieldError validaMaxLength() {

	String maxLength = getProprietaCampo(ProprietaCampi.MaxLength, "");
	FieldError error = null;
	if (StringUtils.isNotBlank(maxLength)) {
	    if (StringUtils.isNotBlank(valore)) {
		Integer maxLengthNum = Integer.valueOf(maxLength);
		if (valore.length() > maxLengthNum.intValue()) {
		    String msg = "la lunghezza del campo non deve superare i [" + maxLength + "] caratteri.";
		    String errCode = "dyn2modellit.error.maxlength";
		    Object[] params = new Object[] { maxLength };
		    error = buildFieldError(errCode, params, msg);
		}
	    }
	}
	return error;
    }

    private FieldError validaRegExp() {

	String regexp = getProprietaCampo(ProprietaCampi.EspressioneRegolare, "");
	String errMessg = "";
	FieldError err = null;
	if (StringUtils.isNotBlank(regexp)) {
	    if (StringUtils.isNotBlank(valore)) {
		try {
		    Pattern p = Pattern.compile(regexp);
		    Matcher m = p.matcher(valore);
		    if (m.find() == false) {
			errMessg = "Il valore non rispetta il formato richiesto. [ " + regexp + " ]";
			String errCode = "dyn2modellit.error.regexp";
			Object[] msgParams = new Object[] { regexp };
			err = buildFieldError(errCode, msgParams, errMessg);
		    }
		} catch (Exception e) {
		    errMessg = "L'espressione regolare configurata non è corretta [" + regexp + "].[" + e.getMessage() + "]";
		    String errCode = "dyn2modellit.error.invalidregexp";
		    Object[] msgParams = new Object[] { regexp, e.getMessage() };
		    err = buildFieldError(errCode, msgParams, errMessg);
		}
	    }
	}
	return err;
    }

    private FieldError validaMaxValue(boolean isDouble) {

	String validationMaxValue = getProprietaCampo(ProprietaCampi.ValidationMaxValue, "");
	FieldError error = null;
	String errMessg = "";
	if (StringUtils.isNotBlank(validationMaxValue)) {
	    if (StringUtils.isNotBlank(valore)) {
		if (isDouble) {
		    try {
			NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
			Number numberRif = format.parse(validationMaxValue);
			Double validationMaxValueNum = numberRif.doubleValue();
			Number number = format.parse(valore);
			Double valoreNum = number.doubleValue();
			if (valoreNum.compareTo(validationMaxValueNum) > 0) {
			    errMessg = "Il valore deve essere minore o uguale a " + validationMaxValue;
			    String errCode = "dyn2modellit.error.maxvalue";
			    error = buildFieldError(errCode, new Object[] { validationMaxValueNum }, errMessg);
			}
		    } catch (Exception e) {
			String label = StringUtils.isNotEmpty(getDyn2Campi().getEtichetta()) ? getDyn2Campi().getEtichetta() : getDyn2Campi()
				.getNomecampo();
			errMessg = "Il valore " + valore + " nel campo " + label + " non rappresenta un numero decimale valido.";
			String errCode = "dyn2modellit.error.decimalformat";
			error = buildFieldError(errCode, new Object[] { valore }, errMessg);
		    }
		} else {
		    try {
			Integer validationMaxValueInt = Integer.valueOf(validationMaxValue);
			Integer valoreInt = Integer.valueOf(valore);
			if (valoreInt.compareTo(validationMaxValueInt) > 0) {
			    errMessg = "Il valore deve essere minore o uguale a " + validationMaxValue;
			    String errCode = "dyn2modellit.error.maxvalue";
			    error = buildFieldError(errCode, new Object[] { validationMaxValueInt }, errMessg);
			}
		    } catch (Exception e) {
			String label = StringUtils.isNotEmpty(getDyn2Campi().getEtichetta()) ? getDyn2Campi().getEtichetta() : getDyn2Campi()
				.getNomecampo();
			errMessg = "Il valore " + valore + " nel campo " + label + " non rappresenta un numero intero valido.";
			String errCode = "dyn2modellit.error.integerformat";
			error = buildFieldError(errCode, new Object[] { valore }, errMessg);
		    }
		}
	    }
	}
	return error;
    }

    private FieldError validaMinValue(boolean isDouble) {

	String validationMinValue = getProprietaCampo(ProprietaCampi.ValidationMinValue, "");
	FieldError error = null;
	String errMessg = "";
	if (StringUtils.isNotBlank(validationMinValue)) {
	    if (StringUtils.isNotBlank(valore)) {
		if (isDouble) {
		    try {
			NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
			Number numberRif = format.parse(validationMinValue);
			Double validationMinValueNum = numberRif.doubleValue();
			Number number = format.parse(valore);
			Double valoreNum = number.doubleValue();
			if (valoreNum.compareTo(validationMinValueNum) < 0) {
			    errMessg = "Il valore deve essere maggiore o uguale a " + validationMinValue;
			    String errCode = "dyn2modellit.error.minvalue";
			    error = buildFieldError(errCode, new Object[] { validationMinValueNum }, errMessg);
			}
		    } catch (Exception e) {
			String label = StringUtils.isNotEmpty(getDyn2Campi().getEtichetta()) ? getDyn2Campi().getEtichetta() : getDyn2Campi()
				.getNomecampo();
			errMessg = "Il valore " + valore + " nel campo " + label + " non rappresenta un numero decimale valido.";
			String errCode = "dyn2modellit.error.decimalformat";
			error = buildFieldError(errCode, new Object[] { valore }, errMessg);
		    }
		} else {
		    try {
			Integer validationMinValueInt = Integer.valueOf(validationMinValue);
			Integer valoreInt = Integer.valueOf(valore);
			if (valoreInt.compareTo(validationMinValueInt) < 0) {
			    errMessg = "Il valore deve essere maggiore o uguale a " + validationMinValue;
			    String errCode = "dyn2modellit.error.minvalue";
			    error = buildFieldError(errCode, new Object[] { validationMinValueInt }, errMessg);
			}
		    } catch (Exception e) {
			String label = StringUtils.isNotEmpty(getDyn2Campi().getEtichetta()) ? getDyn2Campi().getEtichetta() : getDyn2Campi()
				.getNomecampo();
			errMessg = "Il valore " + valore + " nel campo " + label + " non rappresenta un numero intero valido.";
			String errCode = "dyn2modellit.error.integerformat";
			error = buildFieldError(errCode, new Object[] { valore }, errMessg);
		    }
		}
	    }
	}
	return error;
    }

    private FieldError validaValoriAmmessi(String allowedValuesProperty) {

	//TODO verificare se valore è fra quellil ammessi
	FieldError retVal = null;
	if (StringUtils.isNotBlank(allowedValuesProperty)) {
	    List<ChiaveValoreBean<String, String>> accepted = splitOpzioniLista(allowedValuesProperty);
	    boolean found = false;
	    for (ChiaveValoreBean<String, String> val : accepted) {
		if (val.getChiave().equals(this.valoreDecodificato)) {
		    found = true;
		    break;
		}
	    }
	    if (!found && accepted.size() > 0) {
		StringBuilder sbValues = new StringBuilder(accepted.get(0).getValore());
		for (int i = 1; i < accepted.size(); i++) {
		    sbValues.append(", ").append(accepted.get(i).getValore());
		}
		String errMessg = Utilities.getMessageFromBundle(context, "alert.invalid.value", null);
		String errCode = "dyn2modellit.error.notallowedvalue";
		FieldError fe = buildFieldError(errCode, new String[] { sbValues.toString() }, errMessg);
	    }
	}
	return retVal;
    }

    public List<FieldError> validaRicerca() {

	List<FieldError> errors = new ArrayList<FieldError>();
	FieldError err = validaObbligatorio();
	if (err != null) {
	    errors.add(err);
	}
	return errors;
    }

    private List<FieldError> validaNumerico(boolean isDouble) {

	List<FieldError> errors = new ArrayList<FieldError>();
	FieldError error = validaObbligatorio();
	if (error != null) {
	    errors.add(error);
	} else {
	    /*
	    error = validaMaxLength();
	    if (error != null) {
	    errors.add(error);
	    }
	    */
	    error = validaMaxValue(isDouble);
	    if (error != null) {
		errors.add(error);
	    } else {
		error = validaMinValue(isDouble);
		if (error != null) {
		    errors.add(error);
		}
	    }
	}
	return errors;
    }

    protected List<FieldError> validaData() {

	List<FieldError> errors = new ArrayList<FieldError>();
	FieldError error = validaObbligatorio();
	if (error != null) {
	    errors.add(error);
	} else {
	    if (StringUtils.isNotBlank(valoreDecodificato)) {
		String validaData = "";
		try {
		    Utilities.getDate(valoreDecodificato, WebConstants.DATE_FORMAT_PATTERN);
		} catch (Exception e) {
		    validaData = "Il formato della data non è corretto [" + WebConstants.DATE_FORMAT_PATTERN + "]";
		    String errCode = "dyn2modellit.error.dateformat";
		    error = buildFieldError(errCode, new Object[] { WebConstants.DATE_FORMAT_PATTERN }, validaData);
		    errors.add(error);
		}
	    }
	}
	return errors;
    }

    private List<FieldError> validaListaSigepro() {

	return validaLista(false);
    }

    private List<FieldError> validaLista(boolean multiSelect) {

	List<FieldError> errs = new ArrayList<FieldError>();
	FieldError err = validaObbligatorio();
	if (err != null) {
	    errs.add(err);
	} else {
	    String allowedValues = getProprietaCampo("ElementiLista", "");
	    if (StringUtils.isNotBlank(allowedValues)) {
		errs.add(validaValoriAmmessi(allowedValues));
	    }
	}
	return errs;
    }

    private List<FieldError> validaUpload() {

	List<FieldError> errors = new ArrayList<FieldError>();
	FieldError err = validaObbligatorio();
	if (err != null) {
	    errors.add(err);
	}
	String allowedExt = getProprietaCampo("AllowedExtensions", "");
	if (StringUtils.isBlank(allowedExt)) {
	    allowedExt = FACCTConstants.RFC239_ALLOWED_EXTENSIONS;
	}
	if (NumberUtils.isNumber(getValore())) {
	    Object obj = this.context.getBean("oggettiServiceImpl");
	    if (obj != null) {
		OggettiService oggettiService = (OggettiService) obj;
		Oggetti file = oggettiService.findByIdLazy(new PkId(new Integer(getValore())));
		String nomeFile = file.getNomefile();
		boolean isAllowed = validaEstensioneFile(nomeFile, allowedExt);
		if (!isAllowed) {
		    String hrExts = StringUtils.replace(allowedExt, ";", ",");
		    err = buildFieldError("dyn2modellit.error.fileformat", new Object[] { hrExts },
			    "Il file caricato deve avere una delle seguenti estensioni");
		    errors.add(err);
		}
	    }
	} else {
	    Log.error("Valore non numerico in un campo di tipo upload.");
	}
	return errors;
    }

    public static boolean validaEstensioneFile(String fileName, String allowedExtensionsString) {

	boolean isValid = StringUtils.isBlank(allowedExtensionsString);
	if (!isValid) {
	    List<String> exts = splitEstensioniConsentite(allowedExtensionsString);
	    if (exts.size() > 0) {
		for (int i = 0; i < exts.size() && !isValid; i++) {
		    if (fileName.toLowerCase().endsWith(exts.get(i).toLowerCase())) {
			isValid = true;
		    }
		}
	    }
	}
	return isValid;
    }

    private List<FieldError> validaRadioButtons() {

	List<FieldError> errors = new ArrayList<FieldError>();
	FieldError error = validaObbligatorio();
	if (null != error) {
	    errors.add(error);
	}
	return errors;
    }

    /*
     * Al momento il bottone non trasmette alcun valore 
     */
    private List<FieldError> validaBottone() {

	return new ArrayList<FieldError>();
    }

    private List<FieldError> validaCheckBox() {

	List<FieldError> errors = new ArrayList<FieldError>();
	String valoreFalse = getProprietaCampo(ProprietaCampi.ValoreFalse, "0");
	String valoreTrue = getProprietaCampo(ProprietaCampi.ValoreTrue, "1");
	if (isObbligatorio()) {
	    if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase(valoreFalse)) {
		String errMessg = Utilities.getMessageFromBundle(context, "alert.required", null);
		String errCode = "dyn2modellit.error.mandatory";
		FieldError fe = buildFieldError(errCode, null, errMessg);
		errors.add(fe);
	    }
	}
	//check valori ammessi
	if (errors.isEmpty() && !valoreTrue.equals(valore) && !valoreFalse.equals(valore)) {
	    String errMessg = Utilities.getMessageFromBundle(context, "alert.invalid.value", null);
	    String errCode = "dyn2modellit.error.notallowedvalue";
	    StringBuilder validValues = new StringBuilder(valoreTrue).append(", ").append(valoreFalse);
	    FieldError fe = buildFieldError(errCode, new String[] { validValues.toString() }, errMessg);
	    errors.add(fe);
	}
	return errors;
    }

    private FieldError buildFieldError(String messageCode, Object[] messageParams, String defaultMessage) {

	StringBuilder objName = new StringBuilder("dyn2modellit_");
	if (getIdModello() != null) {
	    objName.append(getIdModello());
	} else {
	    objName.append("NULL");
	}
	if (messageParams == null) {
	    messageParams = new Object[0];
	}
	Object[] allParams = new Object[5 + messageParams.length];
	//PARAMETRI NEI MESSAGGI DI ERRORE
	//0 ETICHETTA O NOME CAMPO
	allParams[0] = StringUtils.isNotBlank(getDyn2Campi().getEtichetta()) ? getDyn2Campi().getEtichetta() : getDyn2Campi().getNomecampo();
	//1 VALORE DECODIFICATO
	allParams[1] = getValoreDecodificato("");
	//2 VALORE non decodificato
	allParams[2] = getValore("");
	//3 INDICE (della scheda)
	allParams[3] = getIndice();
	//4 INDICE MOLTEPLICITA (del blocco)
	allParams[4] = getIndiceBlocco();
	//5.6.... altri parametri opzionali passati nel secondo argomento
	for (int i = 0; i < messageParams.length; i++) {
	    allParams[5 + i] = messageParams[i];
	}
	if (isRigaBlocco()) {
	    //messaggi di errore per campi all'interno dei blocchi
	    messageCode = messageCode.concat(".indexed");
	}
	FieldError fe = new FieldError(objName.toString(), getNameOrIdFromCampo(false), getValoreDecodificato(), false, new String[] { messageCode },
		allParams, defaultMessage);
	return fe;
    }

    public String renderError(FieldError error) {

	String errString = "";
	if (error != null) {
	    errString = this.context.getMessage(error, Locale.ITALIAN);
	}
	return errString;
    }

    private String renderTesto() {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    return getPrintValue(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String maxLength = getProprietaCampo(ProprietaCampi.MaxLength, "");
	    String readonly = getProprietaCampo(ProprietaCampi.ReadOnly, "");
	    String size = getProprietaCampo(ProprietaCampi.Columns, "40");
	    String textarea = getProprietaCampo(ProprietaCampi.MultiLine, "");
	    String rows = getProprietaCampo(ProprietaCampi.Rows, "");
	    if (textarea.equalsIgnoreCase("true")) {
		result.textarea();
		if (StringUtils.isNotBlank(size)) {
		    result.cols(size);
		}
		if (StringUtils.isNotBlank(rows)) {
		    result.rows(rows);
		}
	    } else {
		result.input().type("text");
		if (StringUtils.isNotBlank(size)) {
		    result.size(size);
		}
	    }
	    String elementId = getNameOrIdFromCampo(true);
	    String elementName = getNameOrIdFromCampo(false);
	    result.id(elementId).name(elementName).append(validationFX(elementId, false));
	    if (StringUtils.isNotBlank(maxLength)) {
		result.maxlength(maxLength);
	    }
	    if (StringUtils.defaultIfEmpty(readonly, "false").equalsIgnoreCase("true")) {
		result.readonly();
	    }
	    if (textarea.equalsIgnoreCase("true")) {
		result.close();
		result.append(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
		result.textareaEnd();
	    } else {
		result.value(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
		result.end();
	    }
	    String errMessage = "";
	    List<FieldError> errors = validaTesto();
	    if (errors != null && errors.size() > 0) {
		FieldError firstError = errors.get(0);
		errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
		if (errMessage.startsWith("???")) {
		    errMessage = firstError.getDefaultMessage();
		}
	    }
	    result.append(spanErrors(elementId, elementName, errMessage));
	    return result.toString();
	}
    }

    private String renderFileUpload() {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    String nomeFile = "";
	    if (NumberUtils.isNumber(getValore())) {
		Object obj = this.context.getBean("OggettiService");
		if (obj != null) {
		    OggettiService oggettiService = (OggettiService) obj;
		    Oggetti file = oggettiService.findByIdLazy(new PkId(new Integer(getValore())));
		    nomeFile = file.getNomefile();
		}
		//TODO risolvere riferimento a oggettiservice
		/*
		Oggetti obj = oggettiservice.findByIdLazy(new PkId(new Integer(getValore())));
		nomeFile = obj.getNomefile();
		*/
	    }
	    return getPrintValue(nomeFile);
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    //String maxLength = campo.getProprietaCampo( ProprietaCampi.MaxLength, "");
	    //String readonly = campo.getProprietaCampo( ProprietaCampi.ReadOnly, "");
	    //String size = campo.getProprietaCampo( ProprietaCampi.Columns, "40");
	    //String textarea = campo.getProprietaCampo( ProprietaCampi.MultiLine, "");
	    //String rows = campo.getProprietaCampo( ProprietaCampi.Rows, "");
	    result.input().type("text");
	    String elementId = getNameOrIdFromCampo(true);
	    String elementName = getNameOrIdFromCampo(false);
	    result.id(elementId).name(elementName).append(validationFX(elementId, false));
	    result.value(getValoreDecodificato());
	    result.end();
	    //script che attiva il controllo js per l'upload
	    result.script().type("text/javascript").end().newline();
	    StringBuilder sbjs = new StringBuilder("jQuery(document).ready(function(){");
	    sbjs.append("jQuery('#").append(elementId).append("').uploadDatiDinamici({");
	    sbjs.append("");
	    sbjs.append("});});");
	    result.append(sbjs.toString()).newline();
	    result.scriptEnd();
	    String errMessage = "";
	    List<FieldError> errors = validaUpload();
	    if (errors != null && errors.size() > 0) {
		FieldError firstError = errors.get(0);
		errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
		if (errMessage.startsWith("???")) {
		    errMessage = firstError.getDefaultMessage();
		}
	    }
	    result.append(spanErrors(elementId, elementName, errMessage));
	    return result.toString();
	}
    }

    private String renderBottone() {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    return getPrintValue(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    //String maxLength = campo.getProprietaCampo( ProprietaCampi.MaxLength, "");
	    //String readonly = campo.getProprietaCampo( ProprietaCampi.ReadOnly, "");
	    //String size = campo.getProprietaCampo( ProprietaCampi.Columns, "40");
	    //String textarea = campo.getProprietaCampo( ProprietaCampi.MultiLine, "");
	    String testo = getProprietaCampo(ProprietaCampi.Testo, "");
	    result.input().type("button").value(testo);
	    String elementId = getNameOrIdFromCampo(true);
	    String elementName = getNameOrIdFromCampo(false);
	    result.id(elementId).name(elementName).append(validationFX(elementId, false));
	    result.end();
	    //nessuna validazione necessaria per i bottoni
	    //String errMessage = validaFileUpload(d2c, proprietaCampo, campo.getValoreDecodificato());
	    //result.append(spanErrors(elementId, elementName, errMessage));
	    return result.toString();
	}
    }

    private String validationFX(String elementId, boolean isCheckbox) {

	return isCheckbox ? " onclick=\"validaCampo(this);\" " : " onchange=\"validaCampo(this);\" ";
    }

    protected String renderRicerca() {

	String result = "";
	if (Utilities.isBackOffice()) {
	    if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
		return getPrintValue(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
	    } else {
		String elementId = getNameOrIdFromCampo(true);
		String elementName = getNameOrIdFromCampo(false);
		String idHidden = elementId + "_hidden";
		String idChoices = elementId + "_choices";
		StringBuffer buf = new StringBuffer();
		buf.append("setHiddenField");
		buf.append(elementId);
		buf.append("" /*callBackparameters*/);
		buf.append("});");
		buf.append("function setHiddenField");
		buf.append(elementId);
		buf.append("(inputField,listItem){var a = listItem.id;document.getElementById('");
		buf.append(elementId);
		buf.append("').value = inputField.value;");
		buf.append("document.getElementById('");
		buf.append(idHidden);
		buf.append("').value = a;$('");
		buf.append(idChoices);
		buf.append("').fade();}");
		String _afterUpdateElement = buf.toString();
		//	    if (!(afterUpdateElement == null || afterUpdateElement.equals(""))) {
		//		_afterUpdateElement = afterUpdateElement + "";
		//	    }
		// Recupero lo stream di output e scrivo il contenuto personalizzato	    
		StringBuffer buf1 = new StringBuffer();
		buf1.append("<div id=\"" + elementId + "_indicator\" class=\"indicator\" style=\"display:none;\">&nbsp;</div>");
		buf1.append("<div id=\"").append(idChoices).append("\" class=\"autocomplete\"></div>");
		buf1.append("<script type='text/javascript'>jQuery(document).ready(function(){new Ajax.Autocompleter(\"");
		buf1.append(elementId);
		buf1.append("\", \"");
		buf1.append(idChoices);
		buf1.append("\", \"");
		buf1.append("../ajax/findRicercaDyncampi.htm?codiceCampo=" + getDyn2Campi().getId().getCodice());
		buf1.append("\", {paramName: \"textToSearch\",indicator: \"" + elementId + "_indicator\", minChars: ");
		buf1.append("1");
		buf1.append(",frequency: 0.7,afterUpdateElement: ");
		buf1.append(_afterUpdateElement);
		//	    if (!(afterUpdateElement == null || afterUpdateElement.equals(""))) {
		//		buf1.append(callBackparameters);
		//		buf1.append("});");
		//	    }
		buf1.append("});");
		buf1.append("</script>");
		//	    buf1.append("<div dojoType=\"dijit.Tooltip\" connectId=\"");
		//	    buf1.append(elementId);
		//	    buf1.append("\" position=\"after\" style=\"display: none;\">");
		//	    buf1.append("messaggio di help");
		//	    buf1.append("</div>");
		String output = buf1.toString();
		String size = getProprietaCampo(ProprietaCampi.DescriptionBoxColumns, "40");
		result = "<input id=\"" + elementId + "\" name=\"" + elementName + "_DESC\" class=\"searchbox\" " + "	size=\"" + size + "\" 	"
			+ " onchange=\"checkValue(this,'" + elementId + "_hidden')\" onkeydown=\"return searchAll(this,event)\" value=\""
			+ StringUtils.defaultIfEmpty(getValoreDecodificato(), "") + "\"/> 	" + output.toString() + "	<input type=\"hidden\" id=\""
			+ elementId + "_hidden\" name=\"" + elementName + "_ID\" value=\"" + StringUtils.defaultIfEmpty(getValore(), "") + "\" />";
		// String obbligatorio = campo.getProprietaCampo( ProprietaCampi.Obbligatorio, "false");
		String errMessage = "";
		List<FieldError> errors = validaRicerca();
		if (errors != null && errors.size() > 0) {
		    FieldError firstError = errors.get(0);
		    errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
		    if (errMessage.startsWith("???")) {
			errMessage = firstError.getDefaultMessage();
		    }
		}
		result += spanErrors(elementId, elementName, errMessage);
	    }
	} else {
	    String elementId = getNameOrIdFromCampo(true);
	    String elementName = getNameOrIdFromCampo(false);
	    StringBuffer buf = new StringBuffer();
	    String size = getProprietaCampo(ProprietaCampi.DescriptionBoxColumns, "40");
	    //String result = "";
	    if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
		result = getPrintValue(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
	    } else {
		result = "<div class=\"searchbox\" style=\"padding-left: 0px\"><input id=\"" + elementId + "\" name=\"" + elementName + "_DESC\""
			+ " size=\"" + size + "\"" + " value=\"" + StringUtils.defaultIfEmpty(getValoreDecodificato(), "") + "\"/>"
			+ "<input type=\"hidden\" id=\"" + elementId + "_hidden\" name=\"" + elementName + "_ID\" value=\""
			+ StringUtils.defaultIfEmpty(getValore(), "") + "\" /></div>";
		String errMessage = "";
		List<FieldError> errors = validaRicerca();
		if (errors != null && errors.size() > 0) {
		    FieldError firstError = errors.get(0);
		    errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
		    if (errMessage.startsWith("???")) {
			errMessage = firstError.getDefaultMessage();
		    }
		}
		result += spanErrors(elementId, elementName, errMessage);
		buf.append("<script type=\"text/javascript\">");
		buf.append("settaRicercaDyncampi('").append(elementId).append("','").append(getDyn2Campi().getId().getCodice()).append("');");
		buf.append("</script>");
		result += buf.toString();
	    }
	}
	return result;
    }

    private String renderNumerico(boolean isDouble) {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    return getPrintValue(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String maxLength = getProprietaCampo(ProprietaCampi.MaxLength, "");
	    String readonly = getProprietaCampo(ProprietaCampi.ReadOnly, "");
	    String size = getProprietaCampo(ProprietaCampi.Columns, "10");
	    String elementId = getNameOrIdFromCampo(true);
	    String elementName = getNameOrIdFromCampo(false);
	    result.input().style("text-align: right;").type("text").id(elementId).name(elementName).append(validationFX(elementId, false));
	    if (StringUtils.isNotBlank(size)) {
		result.size(size);
	    }
	    if (StringUtils.isNotBlank(maxLength)) {
		result.maxlength(maxLength);
	    }
	    if (StringUtils.defaultIfEmpty(readonly, "false").equalsIgnoreCase("true")) {
		result.readonly();
	    }
	    result.value(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
	    String errMessage = "";
	    List<FieldError> errors = validaNumerico(isDouble);
	    if (errors != null && errors.size() > 0) {
		FieldError firstError = errors.get(0);
		errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
		if (errMessage.startsWith("???")) {
		    errMessage = firstError.getDefaultMessage();
		}
	    }
	    result.end().append(spanErrors(elementId, elementName, errMessage));
	    return result.toString();
	}
    }

    protected String renderData() {

	if (Utilities.isBackOffice()) {
	    if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
		String result = getPrintValue(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
		return result;
	    } else {
		CustomHtmlBuilder result = new CustomHtmlBuilder();
		String elementId = getNameOrIdFromCampo(true);
		String elementName = getNameOrIdFromCampo(false);
		String readonly = getProprietaCampo(ProprietaCampi.ReadOnly, "");
		// String obbligatorio = campo.getProprietaCampo( ProprietaCampi.Obbligatorio, "false");
		result.input().type("text").id(elementId).onblur("isValidDate(this,true);").name(elementName).size("10");
		if (readonly.equalsIgnoreCase("true")) {
		    result.readonly();
		}
		result.value(StringUtils.defaultIfEmpty(getValoreDecodificato(), "")).end();
		// <a id="calDataInizio" href="" title="Calendario"> <img src="/vbg/images/cal.gif" alt="Calendario"/></a>
		String calName = "CAL_" + elementId;
		result.a().append(" href=\"\" ").id(calName).title("Calendario").close();
		result.img().alt("Calendario").src("../images/cal.gif").close().aEnd();
		String errMessage = "";
		List<FieldError> errors = validaData();
		if (errors != null && errors.size() > 0) {
		    FieldError firstError = errors.get(0);
		    errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
		    if (errMessage.startsWith("???")) {
			errMessage = firstError.getDefaultMessage();
		    }
		}
		result.script().type("text/javascript").close().append(calendarString(elementId, calName)).scriptEnd()
			.append(spanErrors(elementId, elementName, errMessage));
		return result.toString();
	    }
	} else {
	    if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
		String result = getPrintValue(StringUtils.defaultIfEmpty(getValoreDecodificato(), ""));
		return result;
	    } else {
		CustomHtmlBuilder result = new CustomHtmlBuilder();
		String elementId = getNameOrIdFromCampo(true);
		String elementName = getNameOrIdFromCampo(false);
		String readonly = getProprietaCampo(ProprietaCampi.ReadOnly, "");
		result.input().type("text").id(elementId).onchange("isValidDate(this,true);validaCampo(this);").name(elementName).size("10");
		if (readonly.equalsIgnoreCase("true")) {
		    result.readonly();
		}
		result.value(StringUtils.defaultIfEmpty(getValoreDecodificato(), "")).end();
		result.script().type("text/javascript").close().append(calendarString(elementId, null)).scriptEnd();
		String errMessage = "";
		List<FieldError> errors = validaData();
		if (errors != null && errors.size() > 0) {
		    FieldError firstError = errors.get(0);
		    errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
		    if (errMessage.startsWith("???")) {
			errMessage = firstError.getDefaultMessage();
		    }
		}
		result.append(spanErrors(elementId, elementName, errMessage));
		return result.toString();
	    }
	}
    }

    public String renderHelp(String tagId) {

	String testo = getDyn2Campi().getDescrizione();
	String rethelp = "";
	if (StringUtils.isNotBlank(testo)) {
	    if (!BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
		if (Utilities.isBackOffice()) {
		    rethelp = "<span class=\"help_image\" id=\"" + tagId + "_HELP\"><label>help</label></span><div id=\"" + tagId
			    + "_HELP_tooltip\" dojoType=\"dijit.Tooltip\" connectId=\"" + tagId
			    + "_HELP\" position=\"after\" style=\"display: none;\">" + testo + "</div>";
		} else {
		    rethelp = "<span class=\"help_image\" id=\"" + tagId + "_HELP\" title=\"" + testo + "\"><label>(?)</label></span>";
		}
	    }
	}
	return rethelp;
    }

    protected String calendarString(String id, String name) {

	if (Utilities.isBackOffice()) {
	    return "jQuery(document).ready(function(){Calendar.setup({inputField     :    \"" + id + "\",    button         :    \"" + name
		    + "\" });});";
	} else {
	    return "$('#" + id + "').datepicker();";
	}
    }

    private String renderListaSigepro() {

	return renderRicerca();
    }

    private String renderLista(boolean isMultiSelect) {

	String valoriLista = getProprietaCampo(ProprietaCampi.ElementiLista, "");
	// String obbligatorio = campo.getProprietaCampo( ProprietaCampi.Obbligatorio, "");
	String[] valori = null;
	if (StringUtils.isNotBlank(valoriLista)) {
	    valori = valoriLista.split(";");
	}
	CustomHtmlBuilder result = new CustomHtmlBuilder();
	String elementId = getNameOrIdFromCampo(true);
	String elementName = getNameOrIdFromCampo(false);
	result.select().name(elementName).id(elementId).append(validationFX(elementId, false));
	List<String> valoriSelezionati = new ArrayList<String>();
	String valoreSelezionato = StringUtils.defaultIfEmpty(getValore(), "");
	if (isMultiSelect) {
	    result.append(" multiple=\"multiple\"");
	    String[] valoriSelezionatiAr = null;
	    if (StringUtils.isNotBlank(valoriLista)) {
		valoriSelezionatiAr = valoreSelezionato.split(";");
	    }
	    if (valoriSelezionatiAr != null && valoriSelezionatiAr.length > 0) {
		for (String val : valoriSelezionatiAr) {
		    if (StringUtils.isNotBlank(val)) {
			valoriSelezionati.add(val);
		    }
		}
	    }
	    if (valori != null) {
		result.size(String.valueOf(valori.length));
	    }
	} else {
	    valoriSelezionati.add(valoreSelezionato);
	}
	String errMessage = "";
	List<FieldError> errors = validaLista(isMultiSelect);
	if (errors != null && errors.size() > 0) {
	    FieldError firstError = errors.get(0);
	    errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
	    if (errMessage.startsWith("???")) {
		errMessage = firstError.getDefaultMessage();
	    }
	}
	result.close();
	if (!isMultiSelect) {
	    result.option().value("").close().append("").optionEnd();
	}
	if (valori != null) {
	    for (String val : valori) {
		if (StringUtils.isNotBlank(val)) {
		    result.newline();
		    result.option().value(val);
		    if (valoriSelezionati != null) {
			if (valoriSelezionati.contains(StringUtils.defaultIfEmpty(val, "").trim())) {
			    result.selected();
			}
		    }
		    result.close().append(val).optionEnd();
		}
	    }
	}
	result.selectEnd().append(spanErrors(elementId, elementName, errMessage));
	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    StringBuffer resultBuffer = new StringBuffer();
	    for (String val : valoriSelezionati) {
		resultBuffer.append(val);
		resultBuffer.append(", ");
	    }
	    return getPrintValue(resultBuffer.toString());
	} else {
	    return result.toString();
	}
    }

    private String renderRadioButtons() {

	String valoriLista = getProprietaCampo(ProprietaCampi.ElementiLista, "");
	// String obbligatorio = campo.getProprietaCampo( ProprietaCampi.Obbligatorio, "");
	String[] valori = null;
	if (StringUtils.isNotBlank(valoriLista)) {
	    valori = valoriLista.split(";");
	}
	CustomHtmlBuilder result = new CustomHtmlBuilder();
	String elementId = getNameOrIdFromCampo(true);
	String elementName = getNameOrIdFromCampo(false);
	//result.select().name(elementName).id(elementId).append(validationFX(elementId, false));
	String valoreSelezionato = StringUtils.defaultIfEmpty(getValore(), "");
	String errMessage = "";
	List<FieldError> errors = validaRadioButtons();
	if (errors != null && errors.size() > 0) {
	    FieldError firstError = errors.get(0);
	    errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
	    if (errMessage.startsWith("???")) {
		errMessage = firstError.getDefaultMessage();
	    }
	}
	//result.close();
	if (valori != null) {
	    StringBuilder sbId = null;
	    for (int i = 0; i < valori.length; i++) {
		sbId = new StringBuilder(elementId).append("_").append(i);
		String val = valori[i];
		if (StringUtils.isNotBlank(val)) {
		    result.input().type("radio").name(elementName).id(sbId.toString()).append(validationFX(sbId.toString(), true));
		    if (valoreSelezionato.equals(val)) {
			result.checked();
		    }
		    result.value(val).close();
		    result.span().close().label().forAttr(sbId.toString()).close();
		    result.append(val).labelEnd().spanEnd();
		    result.newline().br();
		}
	    }
	}
	result.append(spanErrors(elementId, elementName, errMessage));
	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    return getPrintValue(valoreSelezionato);
	} else {
	    return result.toString();
	}
    }

    private String renderCheckBox() {

	String valoreTrue = getProprietaCampo(ProprietaCampi.ValoreTrue, "1");
	String valoreFalse = getProprietaCampo(ProprietaCampi.ValoreFalse, "0");
	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    String result = getPrintValue("[ ]");
	    if (StringUtils.defaultIfEmpty(getValore(), "").equalsIgnoreCase(valoreTrue)) {
		result = getPrintValue("[x]");
	    }
	    return result;
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    // String obbligatorio = campo.getProprietaCampo( ProprietaCampi.Obbligatorio, "false");
	    String elementId = getNameOrIdFromCampo(true);
	    String elementName = getNameOrIdFromCampo(false);
	    result.input().type("checkbox").value(valoreTrue).name("TMP_" + elementName).id("TMP_" + elementId);
	    if (StringUtils.defaultIfEmpty(getValore(), "").equalsIgnoreCase(valoreTrue)) {
		result.checked();
	    }
	    String errMessage = "";
	    List<FieldError> errors = validaCheckBox();
	    if (errors != null && errors.size() > 0) {
		FieldError firstError = errors.get(0);
		errMessage = Utilities.getMessageFromBundle(this.context, firstError.getCode(), firstError.getArguments());
		if (errMessage.startsWith("???")) {
		    errMessage = firstError.getDefaultMessage();
		}
	    }
	    result.onclick("settaValore(this, '" + elementId + "','" + valoreTrue + "','" + valoreFalse + "');").end()
		    .append(spanErrors(elementId, elementName, errMessage));
	    result.input().type("hidden").name(elementName).id(elementId).value(getValore()).end();
	    return result.toString();
	}
    }

    protected Object spanErrors(String elementId, String elementName, String errMessage) {

	String display = "display: none;";
	if (StringUtils.isNotBlank(errMessage)) {
	    display = "";
	}
	if (Utilities.isBackOffice()) {
	    return "<span id=\"" + elementId + "_ERRORS\" style=\"clear: left;" + display + "\" class=\"error\">"
		    + StringUtils.defaultIfEmpty(errMessage, "") + "</span>";
	} else {
	    return "<span id=\"" + elementId + "_ERRORS\" style=\"" + display + "\" class=\"error\">" + StringUtils.defaultIfEmpty(errMessage, "")
		    + "</span>";
	}
    }

    protected String getPrintValue(String value) {

	return "<b>" + value + "</b>";
    }
}
