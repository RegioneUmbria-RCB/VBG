package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.GraduatorietComDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedComDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatorietComHelper;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedComService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.GraduatorietComService;
import it.gruppoinit.pal.gp.core.service.GraduatorietService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class GraduatorietComServiceImpl extends BaseServiceImpl<GraduatorietCom, PkId> implements GraduatorietComService {

    private static final Logger log = LoggerFactory.getLogger(GraduatorietComServiceImpl.class);
    private AmministrazioniService amministrazioniService;
    private Dyn2CampiService dyn2CampiService;
    private GraduatoriedService graduatoriedService;
    private GraduatorietComDAO graduatorietcomDAO;
    private GraduatoriedComService graduatoriedComService;
    private GraduatorietService graduatorietService;
    private LetteretipoService letteretipoService;
    private MailtipoService mailtipoService;
    private TipiMovimentoService tipiMovimentoService;
    private ResponsabiliService responsabiliService;

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setGraduatoriedService(GraduatoriedService graduatoriedService) {

	this.graduatoriedService = graduatoriedService;
    }

    @Autowired
    public void setGraduatorietComDAO(GraduatorietComDAO graduatorietcomDAO) {

	this.graduatorietcomDAO = graduatorietcomDAO;
    }

    @Autowired
    public void setGraduatoriedComService(GraduatoriedComService graduatoriedComService) {

	this.graduatoriedComService = graduatoriedComService;
    }

    @Autowired
    public void setGraduatorietService(GraduatorietService graduatorietService) {

	this.graduatorietService = graduatorietService;
    }

    @Autowired
    public void setLetteretipoService(LetteretipoService letteretipoService) {

	this.letteretipoService = letteretipoService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Override
    protected Class<GraduatorietCom> getEntityClass() {

	return GraduatorietCom.class;
    }

    @Override
    public List<GraduatorietCom> findAll(Integer firstResult, Integer maxResult) {

	return graduatorietcomDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(GraduatorietCom entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    graduatorietcomDAO.insert(entity);
	}
    }

    @Override
    public GraduatorietCom findById(PkId id) {

	return graduatorietcomDAO.findById(id);
    }

    @Override
    public void update(GraduatorietCom entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    graduatorietcomDAO.update(entity);
	}
    }

    @Override
    protected boolean validateEntity(GraduatorietCom entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getLetteretipo() != null) {
	    if (BooleanUtils.isTrue(entity.getFlagMettiallafirma())) {
		if (StringUtils.isBlank(entity.getListaFirmatari())) {
		    _ivs.add(new InvalidValue("service_error.graduatorietcom.listafirmatari.required", entity.getClass(), "listaFirmatari",
			    "NOME_TABELLA", entity));
		}
	    }
	} else {
	    if (BooleanUtils.isTrue(entity.getFlagMettiallafirma())) {
		if (StringUtils.isBlank(entity.getListaFirmatari())) {
		    _ivs.add(new InvalidValue("service_error.graduatorietcom.listafirmatari.allegato.required", entity.getClass(), "listaFirmatari",
			    "NOME_TABELLA", entity));
		}
	    }
	}
	if (!_ivs.isEmpty()) {
	    throwValidationMessages(_ivs);
	}
	return super.validateEntity(entity);
    }

    @Override
    public void delete(GraduatorietCom entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    graduatorietcomDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(GraduatorietCom entity) {

	log.debug("childDelete# Inizio cancellazione GraduatoriedCom legati al GraduatorietCom : {} ({})........",
		new Object[] { entity.getDescrizione(), entity.getId().getCodice() });
	log.debug("childDelete# Recupero la lista di GraduatoriedCom ");
	List<GraduatoriedComDTO> graduatoriedComDTOs = graduatoriedComService.findGraduatoriedComDTOByGraduatoriatCom(entity.getId().getCodice(),
		null, null);
	// Per ogni record GraduatoriedCom vado ad elminare le dipendenze con allegati movimento,movimentimail,movimenti e poi lo cancello
	for (GraduatoriedComDTO graduatoriedComDTO : graduatoriedComDTOs) {
	    log.debug(
		    "childDelete# Inizione cancellazione del record graduatoriedCom con codice {} e dei record collegati (movimenti,movimentiallegati,maovimentimail)",
		    graduatoriedComDTO.getId().getCodice());
	    GraduatoriedCom graduatoriedCom = graduatoriedComService.findById(new PkId(graduatoriedComDTO.getId().getCodice()));
	    graduatoriedComService.delete(graduatoriedCom);
	}
	log.debug("childDelete# Fine  cancellazione GraduatoriedComs legati e oggetti referenziati");
	//////
    }

    @Override
    public List<GraduatorietCom> findByGraduatoriT(Integer codiceGraduatoriaT, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("graduatorietId", codiceGraduatoriaT, Integer.class));
	ft.addRestriction(fr);
	return graduatorietcomDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<GraduatorietComHelper> findGraduatorietComHelperByGraduatoriT(Integer codiceGraduatoriaT, Integer firstResult, Integer maxResult) {

	List<GraduatorietCom> list = this.findByGraduatoriT(codiceGraduatoriaT, null, null);
	List<GraduatorietComHelper> risultato = new ArrayList<GraduatorietComHelper>();
	GraduatorietComHelper helper = null;
	for (GraduatorietCom graduatorietCom : list) {
	    helper = new GraduatorietComHelper();
	    helper.setCodice(graduatorietCom.getId().getCodice());
	    helper.setData(graduatorietCom.getData());
	    helper.setDescrizione(graduatorietCom.getDescrizione());
	    // Conto domande
	    Integer countDomande = graduatoriedComService.countGraduatoriedComDomande(graduatorietCom.getId().getCodice());
	    helper.setNumeroDomande(countDomande);
	    // Conto domande con movimenti
	    Integer countdomandeConMovimenti = graduatoriedComService.countGraduatoriedComMovimenti(graduatorietCom.getId().getCodice());
	    helper.setNumeroDomandeConMovimento(countdomandeConMovimenti);
	    // Conto domande con Allegati
	    Integer countdomandeConAllegati = graduatoriedComService.countGraduatoriedComAllegati(graduatorietCom.getId().getCodice());
	    helper.setNumeroDomandeConAllegato(countdomandeConAllegati);
	    // Conto domande che hanno inviato mail
	    Integer countdomandeConMail = graduatoriedComService.countGraduatoriedComMailInviate(graduatorietCom.getId().getCodice());
	    helper.setNumeroDomandeConMailInviate(countdomandeConMail);
	    risultato.add(helper);
	}
	return risultato;
    }

    @Override
    public void insertComunicazioni(GraduatorietCom entity, SchedaDinamicaFilter dinamicaFilter) {

    }

    private void dataIntegration(GraduatorietCom entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il GraduatorietCom passato è nullo");
	}
	if (entity.getFlgProtocolla() == null) {
	    entity.setFlgProtocolla(Boolean.FALSE);
	}
	if (entity.getFlagMettiallafirma() == null) {
	    entity.setFlagMettiallafirma(Boolean.FALSE);
	}
	if (entity.getFlgTrasformaPdf() == null) {
	    entity.setFlgTrasformaPdf(Boolean.FALSE);
	}
	//	if (entity.getProtDopoCreazioneAllegato() == null) {
	//	    entity.setFlgTrasformaPdf(Boolean.FALSE);
	//	}
	fixMergeEntityProperties(entity);
	if (entity.getLetteretipo() == null) {
	    entity.setFlagMettiallafirma(Boolean.FALSE);
	    entity.setListaFirmatari(null);
	}
    }

    protected void fixMergeEntityProperties(GraduatorietCom entity) {

	Graduatoriet graduatoriet = graduatorietService.bindDomainObject(entity.getGraduatoriet(), PkId.class, "id.codice");
	entity.setGraduatoriet(graduatoriet);
	Letteretipo letteretipo = letteretipoService.bindDomainObject(entity.getLetteretipo(), PkId.class, "id.codice");
	entity.setLetteretipo(letteretipo);
	Mailtipo mailtipo = mailtipoService.bindDomainObject(entity.getMailtipo(), PkId.class, "id.codice");
	entity.setMailtipo(mailtipo);
	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
	Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(entity.getTipimovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipimovimento(tipimovimento);
    }

    protected boolean isDeleteAllowed(GraduatorietCom entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public String findListaFirmatariToHtml(Integer codiceGraduatoriatcom) {

	if (codiceGraduatoriatcom == null) {
	    throw new IllegalArgumentException("Il parametro codiceGraduatoriatcom non può essere nullo");
	}
	GraduatorietCom g = this.findById(new PkId(codiceGraduatoriatcom));
	if (g != null) {
	    String listaFirmatari = StringUtils.defaultString(g.getListaFirmatari()).trim();
	    List<Responsabili> resp = findResponsabiliFirmatari(listaFirmatari);
	    if (resp.size() == 0) {
		return "";
	    }
	    String result = "<ul>";
	    for (Responsabili responsabili : resp) {
		result += "<li>" + responsabili.getResponsabile() + "</li>";
	    }
	    result += "</ul>";
	    return result;
	}
	return "";
    }

    @Override
    public List<Responsabili> findResponsabiliFirmatari(String listaFirmatari) {

	List<Responsabili> result = new ArrayList<Responsabili>();
	if (StringUtils.isNotBlank(listaFirmatari)) {
	    String[] arValori = listaFirmatari.split(",");
	    for (String codResp : arValori) {
		String codiceR = StringUtils.defaultString(codResp).trim();
		if (StringUtils.isNotBlank(codiceR)) {
		    if (Utilities.isInteger(codiceR)) {
			Responsabili r = responsabiliService.findById(new PkId(Integer.parseInt(codiceR)));
			result.add(r);
		    }
		}
	    }
	}
	return result;
    }

    @Override
    public int countByGraduatoriet(Integer graduatoriaid) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("graduatorietId", graduatoriaid, Integer.class));
	ft.addRestriction(fr);
	return graduatorietcomDAO.countRecord(ft);
    }
}
