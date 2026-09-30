package it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gianpaolot
 */
import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class TipologiaregistriServiceImpl extends BaseServiceImpl<Tipologiaregistri, PkId> implements TipologiaregistriService {

    private ConfigurazioneService configurazioneService;
    private TipologiaregistriDAO tipologiaregistriDAO;
    private ComuniService comuniService;
    private AutorizzazioniService autorizzazioniService;

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setTipologiaregistriDAO(TipologiaregistriDAO tipologiaregistriDAO) {

	this.tipologiaregistriDAO = tipologiaregistriDAO;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Override
    protected Class<Tipologiaregistri> getEntityClass() {

	return Tipologiaregistri.class;
    }

    @Override
    public void delete(Tipologiaregistri entity) {

	if (isDeleteAllowed(entity))
	    tipologiaregistriDAO.delete(entity);
    }

    @Override
    public List<Tipologiaregistri> findAll(Integer firstResult, Integer maxResult) {

	return tipologiaregistriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Tipologiaregistri findById(PkId id) {

	return tipologiaregistriDAO.findById(id);
    }

    @Override
    public void insert(Tipologiaregistri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    childDataIntegration(entity);
	    tipologiaregistriDAO.insert(entity);
	}
    }

    @Override
    protected boolean validateEntity(Tipologiaregistri entity) {

	super.validateEntity(entity);
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (StringUtils.isNotBlank(entity.getDurataAutorizzazione())) {
	    Date d = new Date();
	    try {
		Utilities.addDuration(d, entity.getDurataAutorizzazione());
	    } catch (Exception e) {
		_ivs.add(new InvalidValue("service_error.errore_nella_formattazione_duration", entity.getClass(), "durataAutorizzazione",
			entity.getDurataAutorizzazione(), entity));
	    }
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return true;
    }

    @Override
    public void update(Tipologiaregistri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    childDataIntegration(entity);
	    tipologiaregistriDAO.update(entity);
	}
    }

    private void dataIntegration(Tipologiaregistri entity) {

	if (entity == null) {
	    throw new RuntimeException("Tipologia registro nullo");
	}
	if (entity.getFlagUsaProgrConf() == null) {
	    entity.setFlagUsaProgrConf(Boolean.FALSE);
	}
	if (entity.getTrFlagdataauto() == null) {
	    entity.setTrFlagdataauto(Boolean.FALSE);
	}
	if (entity.getTrFlagprotocollo() == null) {
	    entity.setTrFlagprotocollo(Boolean.FALSE);
	}
	if (entity.getFlagManifestazioni() == null) {
	    entity.setFlagManifestazioni(Boolean.FALSE);
	}
    }

    private void childDataIntegration(Tipologiaregistri entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Tipologiaregistri entity) {

	Comuni comune = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(comune);
    }

    @Override
    public List<Tipologiaregistri> findByDescrizione(Tipologiaregistri entity, TIPO_RICERCA tipoRicerca) {

	return tipologiaregistriDAO.findByDescrizione(entity, null, tipoRicerca);
    }

    @Override
    public List<Tipologiaregistri> findByDescrizioneAndComune(Tipologiaregistri tipologiaregistri, String codicecomune, TIPO_RICERCA tipoRicerca) {

	return tipologiaregistriDAO.findByDescrizione(tipologiaregistri, codicecomune, tipoRicerca);
    }

    protected boolean isDeleteAllowed(Tipologiaregistri entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getProtocolloRegistris().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PROTOCOLLO_REGISTRI", null));
	}
	if (!entity.getMercatiConfigurazionesAutorizzazioni().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATI_CONFIGURAZIONE", null));
	}
	if (!entity.getMercatiConfigurazionesConcessioni().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATI_CONFIGURAZIONE", null));
	}
	if (!entity.getAlberoprocs().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC", null));
	}
	if (autorizzazioniService.countByTipologiaRegistri(entity.getId().getCodice()) > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "AUTORIZZAZIONI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	// listaTipiDocumentoMap = new HashMap<String, List<ListaTipiDocumentoDocumento>>();
    }

    @Override
    public boolean checkSeNumeratoreEsterno(int codiceRegistro) {

	Tipologiaregistri registro = this.findById(new PkId(codiceRegistro));
	if (null == registro) {
	    throw new RuntimeException("Il registro è nullo");
	}
	if (Boolean.TRUE.equals(registro.getTrFlagprotocollo())) {
	    return true;
	}
	return StringUtils.isNotBlank(registro.getNumerazioneCustom());
    }

    @Override
    public void scriviProgressivoRegistro(int codiceTipoRegistro, String progressivoUtente) {

	String nuovoProg = "";
	PkId idRegistro = new PkId(codiceTipoRegistro);
	Tipologiaregistri registro = this.findById(idRegistro);
	boolean isUsaConf = registro.getFlagUsaProgrConf() == null ? false : registro.getFlagUsaProgrConf().booleanValue();
	if (isUsaConf) {
	    ConfigurazioneId id = new ConfigurazioneId(registro.getSoftware().getCodice());
	    Configurazione c = configurazioneService.findById(id);
	    String progressivoPresente = c.getProgressivoRegistriAut();
	    if (StringUtils.isBlank(progressivoPresente)) {
		nuovoProg = progressivoPresente;
	    } else {
		nuovoProg = this.calcolaNuovoProgressivo(progressivoPresente, progressivoUtente);
	    }
	    if (StringUtils.isNotBlank(nuovoProg)) {
		c.setProgressivoRegistriAut(nuovoProg);
		configurazioneService.update(c);
	    }
	} else {
	    String progressivoPresente = registro.getTrProgressivo();
	    if (StringUtils.isBlank(progressivoPresente)) {
		nuovoProg = progressivoPresente;
	    } else {
		nuovoProg = this.calcolaNuovoProgressivo(progressivoPresente, progressivoUtente);
	    }
	    if (StringUtils.isNotBlank(nuovoProg)) {
		registro.setTrProgressivo(nuovoProg);
		this.update(registro);
	    }
	}
    }

    @Override
    public String calcolaNuovoProgressivo(String progressivoPresente, String progressivoUtente) {

	String answer = null;
	if (progressivoPresente == null) {
	    throw new RuntimeException(
		    "Errore nel calcolo del progressivo. Verificare le impostazioni in configurazione del modulo " + ORMHelper.getSoftware());
	}
	String[] prg = getProgressivoSplitted(progressivoPresente.trim(), false);
	String prgNumerico = StringUtils.isBlank(prg[0]) ? "0" : prg[0];
	String prgAlfa = StringUtils.isBlank(prg[1]) ? "" : prg[1];
	String[] ist = getProgressivoSplitted(progressivoUtente, true);
	String istNumerico = ist[0];
	String istAlfa = StringUtils.isBlank(ist[1]) ? "" : ist[1];
	if (istAlfa.equalsIgnoreCase(prgAlfa) && (Long.parseLong(istNumerico) > Long.parseLong(prgNumerico))) {
	    return istNumerico + istAlfa;
	} else {
	    Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId(ORMHelper.getSoftware()));
	    if (BooleanUtils.isTrue(configurazione.getFlagIgnoraalfanum())) {
		return progressivoPresente;
	    } else {
		if ((istAlfa.compareTo(prgAlfa) > 0) && istAlfa.length() == prgAlfa.length()) {
		    return istNumerico + istAlfa;
		}
	    }
	}
	return answer;
    }

    private String[] getProgressivoSplitted(String oldValue, boolean increment) {

	String temp = "";
	int suffixIdx = 0;
	boolean isSuffix = false;
	String suffix = "";
	String answer[] = new String[2];
	temp = oldValue;
	answer[0] = null;
	answer[1] = null;
	String patternStr = "^([0-9]+)";
	Pattern pattern = Pattern.compile(patternStr);
	Matcher matcher = pattern.matcher(temp);
	if (matcher.find()) {
	    temp = (matcher.group());
	} else {
	    temp = "";
	}
	if (oldValue == null) {
	    oldValue = "";
	}
	if (temp.length() < oldValue.length()) {
	    isSuffix = true;
	}
	if (isSuffix) {
	    suffixIdx = temp.length();
	    suffix += oldValue.substring(suffixIdx, oldValue.length());
	    answer[1] = suffix;
	}
	if (temp.equals("")) {
	    temp = "0";
	}
	try {
	    int newValue = 0;
	    if (increment) {
		newValue = Integer.parseInt(temp) + 1;
	    } else {
		newValue = Integer.parseInt(temp);
	    }
	    answer[0] = String.valueOf(newValue);
	    patternStr = "^([0]+)";
	    pattern = Pattern.compile(patternStr);
	    matcher = pattern.matcher(temp);
	    if (matcher.find()) {
		answer[0] = StringUtils.leftPad(answer[0], temp.length(), "0");
	    }
	    return answer;
	} catch (Exception ex) {
	    answer[0] = oldValue;
	    return answer;
	}
    }

    @Override
    public List<ChiaveValoreBean<String, String>> findAllDocer() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.isNotNull("codRegistroDocer"));
	fr.addFilterField(FilterUtils.notEquals("codRegistroDocer", "", String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("trDescrizione"));
	List<Tipologiaregistri> trs = tipologiaregistriDAO.findByFilterTable(ft);
	List<ChiaveValoreBean<String, String>> result = new ArrayList<ChiaveValoreBean<String, String>>();
	for (Tipologiaregistri tr : trs) {
	    if (StringUtils.isNotBlank(tr.getCodRegistroDocer())) {
		ChiaveValoreBean<String, String> c = new ChiaveValoreBean<String, String>();
		c.setChiave(tr.getCodRegistroDocer());
		c.setValore(tr.getTrDescrizione());
		result.add(c);
	    }
	}
	return result;
    }
}
