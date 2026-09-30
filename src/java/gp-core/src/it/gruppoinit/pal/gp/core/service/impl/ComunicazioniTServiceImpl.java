package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniTDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniDConcessioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDConcessioniService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniManagerService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniTService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipoComunicazioniTService;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDStatoEnum;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniTStatoEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoComunicazioniTEnum;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ComunicazioniTServiceImpl extends BaseServiceImpl<ComunicazioniT, PkId> implements ComunicazioniTService {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniTServiceImpl.class);
    private ComunicazioniTDAO comunicazioniTDAO;
    private AmministrazioniService amministrazioniService;
    private MailtipoService mailtipoService;
    private LetteretipoService letteretipoService;
    private TipiMovimentoService tipiMovimentoService;
    private ResponsabiliService responsabiliService;
    private ComunicazioniDService comunicazioniDService;
    private ComunicazioniManagerService comunicazioniManagerService;
    private TipoComunicazioniTService tipoComunicazioniTService;
    private ComunicazioniDConcessioniService comunicazioniDConcessioniService;

    @Autowired
    public void setComunicazioniDConcessioniService(ComunicazioniDConcessioniService comunicazioniDConcessioniService) {

	this.comunicazioniDConcessioniService = comunicazioniDConcessioniService;
    }

    @Autowired
    public void setTipoComunicazioniTService(TipoComunicazioniTService tipoComunicazioniTService) {

	this.tipoComunicazioniTService = tipoComunicazioniTService;
    }

    @Autowired
    public void setComunicazioniManagerService(ComunicazioniManagerService comunicazioniManagerService) {

	this.comunicazioniManagerService = comunicazioniManagerService;
    }

    @Autowired
    public void setComunicazioniDService(ComunicazioniDService comunicazioniDService) {

	this.comunicazioniDService = comunicazioniDService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setLetteretipoService(LetteretipoService letteretipoService) {

	this.letteretipoService = letteretipoService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setComunicazioniTDAO(ComunicazioniTDAO comunicazioniTDAO) {

	this.comunicazioniTDAO = comunicazioniTDAO;
    }

    @Override
    protected Class<ComunicazioniT> getEntityClass() {

	return ComunicazioniT.class;
    }

    @Override
    public List<ComunicazioniT> findAll(Integer firstResult, Integer maxResult) {

	return comunicazioniTDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ComunicazioniT entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    comunicazioniTDAO.insert(entity);
	}
    }

    @Override
    public ComunicazioniT findById(PkId id) {

	return comunicazioniTDAO.findById(id);
    }

    @Override
    public void update(ComunicazioniT entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    comunicazioniTDAO.update(entity);
	}
    }

    @Override
    public void delete(ComunicazioniT entity) {

	if (isDeleteAllowed(entity)) {
	    comunicazioniTDAO.delete(entity);
	}
    }

    //    @Override
    //    public void elaboraComunicazione(ComunicazioniT comunicazioniT) {
    //
    //	if (validateEntityPerComunicazione(comunicazioniT)) {
    //	    comunicazioniManagerService.insertInizializzazioniStep(comunicazioniT.getId().getCodice());
    //	    comunicazioniT.setStatoElaborazione(ComunicazioniTStatoEnum.DA_ELABORARE.value());
    //	    this.update(comunicazioniT);
    //	    this.inviaComunicazioni(comunicazioniT);
    //	}
    //    }
    //
    //    private void inviaComunicazioni(ComunicazioniT comunicazioniT) {
    //
    //	TipoComunicazioniT tipoComunicazioniT= comunicazioniT.getTipoComunicazioniT();
    //	List<ComunicazioniDHelper> list = new ArrayList<ComunicazioniDHelper>();
    //	// TODO metodo
    //	for (ComunicazioniDHelper comunicazioniDHelper : list) {
    //	    List<TmpStatiComunicazioniD> tmpStatiComunicazioniDs=comunicazioniManagerService.findStepNonEseguiti(comunicazioniDHelper.getCodiceComunicazioneD());
    //	    for (TmpStatiComunicazioniD tmpStatiComunicazioniD : tmpStatiComunicazioniDs) {
    //		comunicazioniManagerService.eseguiStep(tmpStatiComunicazioniD.getStato(),comunicazioniDHelper);
    //	    }
    //	    
    //	    
    //	}
    //	
    //	
    //	
    //	
    //	
    //    }
    //
    //    private boolean validateEntityPerComunicazione(ComunicazioniT comunicazioniT) {
    //
    //	// validazione //TODO
    //	return true;
    //    }
    @Override
    public String findListaFirmatariToHtml(Integer codiceComunicazionet) {

	if (codiceComunicazionet == null) {
	    throw new IllegalArgumentException("Il parametro codiceComunicazionet non può essere nullo");
	}
	ComunicazioniT g = this.findById(new PkId(codiceComunicazionet));
	if (g != null) {
	    String listaFirmatari = StringUtils.defaultString(g.getListaFirmatari()).trim();
	    List<Responsabili> resp = responsabiliService.findResponsabili(listaFirmatari);
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

    private void dataIntegration(ComunicazioniT entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro ComunicazioniT da validare è nullo");
	}
	if (entity.getStatoElaborazione() == null) {
	    entity.setStatoElaborazione(0);
	}
	if (entity.getFlagPrevedePresenzaIstanza() == null) {
	    entity.setFlagPrevedePresenzaIstanza(false);
	}
	if (entity.getFlagMettiallafirma() == null) {
	    entity.setFlagMettiallafirma(false);
	}
	if (entity.getFlgProtocolla() == null) {
	    entity.setFlgProtocolla(false);
	}
	if (entity.getFlgTrasformaPdf() == null) {
	    entity.setFlgTrasformaPdf(false);
	}
	if (entity.getLetteretipo() == null) {
	    entity.setFlagMettiallafirma(Boolean.FALSE);
	    entity.setListaFirmatari(null);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(ComunicazioniT entity) {

	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
	Letteretipo letteretipo = letteretipoService.bindDomainObject(entity.getLetteretipo(), PkId.class, "id.codice");
	entity.setLetteretipo(letteretipo);
	Mailtipo mailtipo = mailtipoService.bindDomainObject(entity.getMailtipo(), PkId.class, "id.codice");
	entity.setMailtipo(mailtipo);
	Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(entity.getTipimovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipimovimento(tipimovimento);
    }

    protected boolean isDeleteAllowed(ComunicazioniTDAO entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public ComunicazioniT inserCreaComunicazioniAutorizzazioniConcessioni(List<AutorizzazioniConcessioni> listAutorizzazioniConcessioni) {

	ComunicazioniT comunicazioniT = new ComunicazioniT();
	comunicazioniT.setData(new Date());
	TipoComunicazioniT tipoComunicazioniT = tipoComunicazioniTService.findById(TipoComunicazioniTEnum.CONCESSIONI.value());
	comunicazioniT.setTipoComunicazioniT(tipoComunicazioniT);
	log.debug("inserCreaComunicazioniAutorizzazioniConcessioni# Comunicazione prevede istanza = {}", true);
	comunicazioniT.setFlagPrevedePresenzaIstanza(true);
	log.debug("inserCreaComunicazioniAutorizzazioniConcessioni# setto la comunicazione come da = {}",
		ComunicazioniTStatoEnum.PRE_ELABORAZIONE.toString());
	comunicazioniT.setStatoElaborazione(ComunicazioniTStatoEnum.PRE_ELABORAZIONE.value());
	this.insert(comunicazioniT);
	log.debug("inserCreaComunicazioniAutorizzazioniConcessioni# Inserita comunicazioneT di tipo = {}", tipoComunicazioniT.getCodice());
	for (AutorizzazioniConcessioni autorizzazioniConcessioni : listAutorizzazioniConcessioni) {
	    ComunicazioniD comunicazioniD = new ComunicazioniD();
	    comunicazioniD.setComunicazioniT(comunicazioniT);
	    comunicazioniD.setStatoElaborazione(ComunicazioniDStatoEnum.NON_INIZIALIZZATA.value());
	    comunicazioniDService.insert(comunicazioniD);
	    ComunicazioniDConcessioni comunicazioniDConcessioni = new ComunicazioniDConcessioni();
	    comunicazioniDConcessioni.setComunicazioniD(comunicazioniD);
	    comunicazioniDConcessioni.setAutorizzazioniConcessioni(autorizzazioniConcessioni);
	    comunicazioniDConcessioniService.insert(comunicazioniDConcessioni);
	    log.debug(
		    "inserCreaComunicazioniAutorizzazioniConcessioni# Inserita dettaglio comunicazione per la concessione = {}, autorizzazione numero = {}",
		    autorizzazioniConcessioni.getId().getCodice(), autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutatt().getAutorigNumero());
	}
	log.debug("inserCreaComunicazioniAutorizzazioniConcessioni# Popolata struttura base per le comunicazioni.");
	return comunicazioniT;
    }

    @Override
    public void updateStatoComunicazioneT(Integer codiceComunicazioneT, ComunicazioniTStatoEnum comunicazioniTStatoEnum) {

	comunicazioniTDAO.updateStatoComunicazioneT(codiceComunicazioneT, comunicazioniTStatoEnum);
    }
}
