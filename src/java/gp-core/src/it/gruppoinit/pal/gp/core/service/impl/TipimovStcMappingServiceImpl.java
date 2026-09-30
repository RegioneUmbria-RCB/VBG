package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipimovStcMappingDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAlberoproc;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.TipimovStcModelli;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.TipiMovimentoStcMappingProtocollo;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovStcAlberoprocService;
import it.gruppoinit.pal.gp.core.service.TipimovStcAltridatiService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.pal.gp.core.service.TipimovStcModelliService;

@Service
public class TipimovStcMappingServiceImpl extends BaseServiceImpl<TipimovStcMapping, PkId> implements TipimovStcMappingService {

    private TipimovStcMappingDAO tipimovStcMappingDAO;
    private TipiMovimentoService tipiMovimentoService;
    private TipimovStcAltridatiService tipimovStcAltridatiService;
    private TipimovStcAlberoprocService tipimovStcAlberoprocService;
    private TipimovStcModelliService tipimovStcModelliService;
    private AmministrProtocolloService amministrProtocolloService;

    @Autowired
    public void setAmministrProtocolloService(AmministrProtocolloService amministrProtocolloService) {

	this.amministrProtocolloService = amministrProtocolloService;
    }

    @Autowired
    public void setTipimovStcMappingDAO(TipimovStcMappingDAO tipimovStcMappingDAO) {

	this.tipimovStcMappingDAO = tipimovStcMappingDAO;
    }

    private AmministrazioniService amministrazioniService;

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    private ProtocolloFlussoService protocolloFlussoService;

    @Autowired
    public void setProtocolloFlussoService(ProtocolloFlussoService protocolloFlussoService) {

	this.protocolloFlussoService = protocolloFlussoService;
    }

    private MailtipoService mailtipoService;

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setTipimovStcModelliService(TipimovStcModelliService tipimovStcModelliService) {

	this.tipimovStcModelliService = tipimovStcModelliService;
    }

    @Autowired
    public void setTipimovStcAlberoprocService(TipimovStcAlberoprocService tipimovStcAlberoprocService) {

	this.tipimovStcAlberoprocService = tipimovStcAlberoprocService;
    }

    @Autowired
    public void setTipimovStcAltridatiService(TipimovStcAltridatiService tipimovStcAltridatiService) {

	this.tipimovStcAltridatiService = tipimovStcAltridatiService;
    }

    @Override
    protected Class<TipimovStcMapping> getEntityClass() {

	return TipimovStcMapping.class;
    }

    @Override
    public void delete(TipimovStcMapping entity) {

	childDelete(entity);
	tipimovStcMappingDAO.delete(entity);
    }

    @Override
    protected void childDelete(TipimovStcMapping entity) {

	List<TipimovStcModelli> mods = tipimovStcModelliService.findByTipimovimentoAndAmministrazioni(
		entity.getTipimovimento().getId().getTipomovimento(), entity.getAmministrazioni().getId().getCodice());
	for (TipimovStcModelli tipimovStcModelli : mods) {
	    tipimovStcModelliService.delete(tipimovStcModelli);
	}
	List<TipimovStcAlberoproc> abps = tipimovStcAlberoprocService.findByTipimovimentoAndAmministrazione(
		entity.getTipimovimento().getId().getTipomovimento(), entity.getAmministrazioni().getId().getCodice());
	for (TipimovStcAlberoproc tipimovStcAlberoproc : abps) {
	    tipimovStcAlberoprocService.delete(tipimovStcAlberoproc);
	}
	List<TipimovStcAltridati> ads = tipimovStcAltridatiService.findByTipimovimentoAndAmministrazione(
		entity.getTipimovimento().getId().getTipomovimento(), entity.getAmministrazioni().getId().getCodice());
	for (TipimovStcAltridati tipimovStcAltridati : ads) {
	    tipimovStcAltridatiService.delete(tipimovStcAltridati);
	}
    }

    @Override
    public List<TipimovStcMapping> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return tipimovStcMappingDAO.findAll(firstResult, maxResult);
    }

    @Override
    public TipimovStcMapping findById(PkId id) {

	return tipimovStcMappingDAO.findById(id);
    }

    @Override
    public void insert(TipimovStcMapping entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && businessValidate(entity, true)) {
	    tipimovStcMappingDAO.insert(entity);
	}
    }

    @Override
    public void update(TipimovStcMapping entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && businessValidate(entity, false)) {
	    tipimovStcMappingDAO.update(entity);
	}
    }

    private void dataIntegration(TipimovStcMapping entity) {

	if (entity.getFlagNotificaAutomatica() == null) {
	    entity.setFlagNotificaAutomatica(Integer.valueOf(0));
	}
	if (entity.getFlagCreainviaAllegati() == null) {
	    entity.setFlagCreainviaAllegati(Boolean.FALSE);
	}
	if (entity.getFlagAllegaDocumentiIstanza() == null) {
	    entity.setFlagAllegaDocumentiIstanza(Boolean.FALSE);
	}
	if (entity.getFlagProtocolla() == null) {
	    entity.setFlagProtocolla(Boolean.FALSE);
	}
	if (entity.getFlagRifpratStorica() == null) {
	    entity.setFlagRifpratStorica(Boolean.FALSE);
	}
	if (entity.getNonInviareProcedimenti() == null) {
	    entity.setNonInviareProcedimenti(Boolean.FALSE);
	}
	//Comportamento se i flag FlagNotificaAutomatica o FlagProtocolla sono a false
	if (entity.getFlagNotificaAutomatica().equals(0)) {
	    entity.setFlagCreainviaAllegati(Boolean.FALSE);
	    //entity.setFlagProtocolla(Boolean.FALSE);
	}
	if (entity.getFlagProtocolla().equals(Boolean.FALSE)) {
	    entity.setAmministrazioneMittente(null);
	    entity.setProtocolloTipidocumentoCodice(null);
	    entity.setProtocolloFlusso(null);
	    entity.setMailtipo(null);
	}
	if (entity.getFlagInviaschedeistanza() == null) {
	    entity.setFlagInviaschedeistanza(Boolean.FALSE);
	}
	if (entity.getFlagAllegaDocumentiEndo() == null) {
	    entity.setFlagAllegaDocumentiEndo(Boolean.FALSE);
	}
	if (entity.getFlagNotificainterapratica() == null) {
	    entity.setFlagNotificainterapratica(Boolean.FALSE);
	}
	if (entity.getFlagConvertipdf() == null) {
	    entity.setFlagConvertipdf(Boolean.FALSE);
	}
	if (entity.getFlagAllegaDocPratica() == null) {
	    entity.setFlagConvertipdf(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(TipimovStcMapping entity) {

	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
	Amministrazioni amministrazioniMittente = amministrazioniService.bindDomainObject(entity.getAmministrazioneMittente(), PkId.class,
		"id.codice");
	entity.setAmministrazioneMittente(amministrazioniMittente);
	ProtocolloFlusso protocolloFlusso = protocolloFlussoService.bindDomainObject(entity.getProtocolloFlusso(), String.class, "codice");
	entity.setProtocolloFlusso(protocolloFlusso);
	Mailtipo mailtipo = mailtipoService.bindDomainObject(entity.getMailtipo(), PkId.class, "id.codice");
	entity.setMailtipo(mailtipo);
	Tipimovimento tm = tipiMovimentoService.bindDomainObject(entity.getTipimovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipimovimento(tm);
    }

    @Override
    public List<TipimovStcMapping> findByTipimovimento(TipimovimentoId tipimovimentoId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipimovimentoId", tipimovimentoId.getTipomovimento(), String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("amministrazione", "amministrazioni"));
	List<TipimovStcMapping> lst = tipimovStcMappingDAO.findByFilterTable(ft);
	return lst;
    }

    private boolean businessValidate(TipimovStcMapping entity, boolean isInsert) {

	boolean result = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// Gestione dell'indice di univocità, non può esistere un record che abbia (idcomune,tipomovimemto,amministrazione)
	if (isInsert) {
	    TipimovStcMapping tipimovStcMapping = this.findByTipimovimentoAndAmministrazione(entity.getTipimovimento().getId().getTipomovimento(),
		    entity.getAmministrazioni().getId().getCodice());
	    if (EntityUtils.getNestedProperty(tipimovStcMapping, "id.codice") != null) {
		_ivs.add(new InvalidValue("service_error.esiste_un_mapping_con_tipomov_e_amministrazione", null, null, null, null));
	    }
	}
	// è possibile configurare più notifiche automatiche ma sempre
	// con la regola che se dal movimento si recupera quella dell'amministrazione allora torna quella altrimenti quella valida
	// se esistono più configurazioni rilancia eccezione
	// 
	//	if (entity.getFlagNotificaAutomatica().equals(Integer.valueOf(1))) {	
	//	    List<TipimovStcMapping> mappings = this.findByTipimovimento(entity.getTipimovimento().getId());
	//	    for (TipimovStcMapping mappingEsistente : mappings) {
	//		TipoNotificaAutomatica tipo = decodeTipoNotifica(mappingEsistente);
	//		if (tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA)) {
	//		    if (isInsert) {
	//			_ivs.add(new InvalidValue("service_error.esiste_un_mapping_con_notifica_automatica", entity.getClass(),
	//				"flagNotificaAutomatica", entity.getFlagNotificaAutomatica(), entity));
	//			break;
	//		    } else {
	//			if (!mappingEsistente.getId().getCodice().equals(entity.getId().getCodice())) {
	//			    _ivs.add(new InvalidValue("service_error.esiste_un_mapping_con_notifica_automatica", entity.getClass(),
	//				    "flagNotificaAutomatica", entity.getFlagNotificaAutomatica(), entity));
	//			}
	//		    }
	//		}
	//	    }
	//	}
	if (entity.getFlagProtocolla() != null && entity.getFlagProtocolla().equals(Boolean.TRUE)) {
	    TipoNotificaAutomatica tipo = decodeTipoNotifica(entity);
	    if (!tipo.equals(TipoNotificaAutomatica.NESSUNA_NOTIFICA_AUTOMATICA)) {
		//Controllo che siano inseriti i campi relativi alla protocollazione 
		if (entity.getProtocolloFlusso() == null || entity.getProtocolloFlusso().getCodice() == null) {
		    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", entity.getClass(), "protocolloFlusso",
			    entity.getProtocolloFlusso(), entity));
		} else {
		    if (StringUtils.defaultString(entity.getProtocolloFlusso().getCodice()).equals(WebConstants.FLUSSO_INTERNO)) {
			// NEL CASO DI FLUSSO INTERNO DEVO VERIFICARE CHE ANCHE L'AMMINISTRAZIONE DESTINATARIA ABBIA SETTATI I PARAMETRI DI PROTOCOLLAZIONE
			int amm = amministrProtocolloService.countByAmministrazione(entity.getAmministrazioni().getId().getCodice());
			if (amm < 1) {
			    _ivs.add(new InvalidValue("service_error.parmatri_prot_amministrazione_non_configurati", entity.getClass(),
				    "amministrazioni", entity.getAmministrazioni(), entity));
			}
		    }
		}
		//		if (StringUtils.isBlank(entity.getProtocolloTipidocumentoCodice())) {
		//		    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", entity.getClass(), "protocolloTipidocumentoCodice", entity
		//			    .getProtocolloTipidocumentoCodice(), entity));
		//		}
		if (EntityUtils.getNestedProperty(entity.getMailtipo(), "id.codice") == null) {
		    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", entity.getClass(), "mailtipo", entity.getMailtipo(), entity));
		}
		if (EntityUtils.getNestedProperty(entity.getAmministrazioneMittente(), "id.codice") == null) {
		    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", entity.getClass(), "amministrazioneMittente",
			    entity.getAmministrazioneMittente(), entity));
		} else {
		    //Devo controllare se sono impostati i parametri di protocollazione dell'amministrazione mittente
		    int amm = amministrProtocolloService.countByAmministrazione(entity.getAmministrazioneMittente().getId().getCodice());
		    if (amm < 1) {
			_ivs.add(new InvalidValue("service_error.parmatri_prot_amministrazione_non_configurati", entity.getClass(),
				"amministrazioneMittente", entity.getAmministrazioneMittente(), entity));
		    }
		}
		//Devo controllare se sono impostati i parametri di protocollazione dell'amministrazione destinatario (non sarà mai nulla in quanto è una
		//select box semprer popolata)
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return result;
    }

    @Override
    public TipimovStcMapping findByTipimovimentoAndAmministrazione(String tipomovimento, Integer codiceAmministrazioneStc) {

	Assert.notNull(tipomovimento, "Il parametro tipoMovimento non può essere nullo");
	Assert.hasLength(tipomovimento, "Il parametro tipoMovimento non può essere vuoto");
	Assert.notNull(codiceAmministrazioneStc, "Il parametro codiceAmministrazione non può essere nullo");
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipimovimentoId", tipomovimento, String.class));
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazioneStc, Integer.class));
	ft.addRestriction(fr);
	List<TipimovStcMapping> lst = tipimovStcMappingDAO.findByFilterTable(ft);
	if (lst.size() > 0) {
	    return lst.get(0);
	}
	return null;
    }

    @Override
    public List<TipimovStcMapping> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	filterTable.addRestriction(fr);
	return tipimovStcMappingDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<TipimovStcMapping> findByAmministrazioneMittente(Integer codice, Integer firstResult, Integer maxResult) {

	if (codice == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioneMittenteId", codice, Integer.class));
	filterTable.addRestriction(fr);
	return tipimovStcMappingDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public TipoNotificaAutomatica decodeTipoNotifica(TipimovStcMapping tipimovStcMapping) {

	if (tipimovStcMapping != null) {
	    if (tipimovStcMapping.getFlagNotificaAutomatica() != null) {
		switch (tipimovStcMapping.getFlagNotificaAutomatica().intValue()) {
		case 1:
		    return TipoNotificaAutomatica.NOTIFICA_AUTOMATICA;
		case 2:
		    return TipoNotificaAutomatica.NOTIFICA_AUTOMATICA_INSERIMENTO;
		}
	    }
	}
	return TipoNotificaAutomatica.NESSUNA_NOTIFICA_AUTOMATICA;
    }

    @Override
    public TipimovStcMapping findNotificheAutomaticheByTipimovimentoAndAmministrazione(String tipoMovimento, Integer codiceAmministrazione) {

	// Se mapping per codiceAmministrazione == 1 allora prendo quello
	// Se non matcha e c'è una riga sola allora vecchia logica
	// se più righe solleva eccezione
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipimovimentoId", tipoMovimento, String.class));
	fr.addFilterField(FilterUtils.in("flagNotificaAutomatica",
		new Integer[] { 1 /*TipoNotificaAutomatica.NOTIFICA_AUTOMATICA*/, 2/*TipoNotificaAutomatica.NOTIFICA_AUTOMATICA_INSERIMENTO*/ },
		Integer.class));
	filterTable.addRestriction(fr);
	List<TipimovStcMapping> ret = tipimovStcMappingDAO.findByFilterTable(filterTable);
	int retSize = ret.size();
	if (retSize == 1) {
	    return ret.get(0);
	}
	if (retSize > 1) {
	    // 
	    throw new InvalidConfigurationException("Configurazione MAPPING STC di notifica automatica non valida per tipo movimento: " +
						    tipoMovimento + ", codiceAmministrazione: " + codiceAmministrazione);
	}
	// Se non matcha e c'è una riga sola allora vecchia logica
	filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipimovimentoId", tipoMovimento, String.class));
	fr.addFilterField(FilterUtils.in("flagNotificaAutomatica",
		new Integer[] { 1 /*TipoNotificaAutomatica.NOTIFICA_AUTOMATICA*/, 2/*TipoNotificaAutomatica.NOTIFICA_AUTOMATICA_INSERIMENTO*/ },
		Integer.class));
	filterTable.addRestriction(fr);
	ret = tipimovStcMappingDAO.findByFilterTable(filterTable, 0, 2);
	retSize = ret.size();
	if (retSize == 1) { // Se non matcha e c'è una riga sola allora vecchia logica
	    return ret.get(0);
	}
	if (retSize > 1) {
	    // se più righe solleva eccezione
	    throw new InvalidConfigurationException("Configurazione MAPPING STC di notifica automatica non valida per tipo movimento: " +
						    tipoMovimento + ", codiceAmministrazione: " + codiceAmministrazione);
	}
	return null;
    }

    @Override
    public TipiMovimentoStcMappingProtocollo findDatiProtocolloByTipoMovimento(String tipomovimento) {

	return this.tipimovStcMappingDAO.findDatiProtocolloByTipoMovimento(tipomovimento);
    }
}
