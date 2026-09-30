package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatidLettureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.LettureContatoriCommand;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatidLetture;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;
import it.gruppoinit.pal.gp.core.domain.helper.ContiBean;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.service.MercatidLettureService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.VwConcessioniattiveService;

@Service
public class MercatidLettureServiceImpl extends BaseServiceImpl<MercatidLetture, PkId> implements MercatidLettureService {

    @Autowired
    private MercatidLettureDAO mercatidLettureDAO;
    @Autowired
    private RegistrazioniService registrazioniService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private VwConcessioniattiveService vwConcessioniattiveService;

    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    public void setOneritipirateizzazioneService(OneritipirateizzazioneService oneritipirateizzazioneService) {

	this.oneritipirateizzazioneService = oneritipirateizzazioneService;
    }

    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    public void setVwConcessioniattiveService(VwConcessioniattiveService vwConcessioniattiveService) {

	this.vwConcessioniattiveService = vwConcessioniattiveService;
    }

    public void setMercatidLettureDAO(MercatidLettureDAO mercatidLettureDAO) {

	this.mercatidLettureDAO = mercatidLettureDAO;
    }

    public void setRegistrazioniService(RegistrazioniService registrazioniService) {

	this.registrazioniService = registrazioniService;
    }

    @Override
    protected Class<MercatidLetture> getEntityClass() {

	return MercatidLetture.class;
    }

    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_MERCATIDLETTURE" })
    @Override
    public void delete(MercatidLetture entity) {

	mercatidLettureDAO.delete(entity);
    }

    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_MERCATIDLETTURE" })
    @Override
    public List<MercatidLetture> findAll(Integer firstResult, Integer maxResult) {

	return mercatidLettureDAO.findAll(firstResult, maxResult);
    }

    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_MERCATIDLETTURE" })
    @Override
    public MercatidLetture findById(PkId id) {

	return mercatidLettureDAO.findById(id);
    }

    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_MERCATIDLETTURE" })
    @Override
    public void insert(MercatidLetture entity) {

	if (validateNuovaLettura(entity)) {
	    mercatidLettureDAO.insert(entity);
	}
    }

    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_MERCATIDLETTURE" })
    @Override
    public void update(MercatidLetture entity) {

	if (validateNuovaLettura(entity)) {
	    mercatidLettureDAO.update(entity);
	}
    }

    @Override
    public List<MercatidLetture> findByFilter(MercatidLetture entity) {

	return mercatidLettureDAO.findByFilter(entity);
    }

    @Override
    public List<Date> findDataLettura(MercatiUso mercatiUso) {

	return mercatidLettureDAO.findDataLettura(mercatiUso);
    }

    @Override
    public List<MercatidLetture> insertNuoveLetture(List<MercatidLetture> list) {

	List<MercatidLetture> nonInseriteList = new ArrayList<MercatidLetture>();
	for (MercatidLetture mercatidLetture : list) {
	    if (validateNuovaLettura(mercatidLetture)) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mercatidLetture.getDataLettura());
		mercatidLetture.setAnno((Short) ((Integer) calendar.get(Calendar.YEAR)).shortValue());
		mercatidLettureDAO.insert(mercatidLetture);
	    } else {
		nonInseriteList.add(mercatidLetture);
	    }
	}
	return nonInseriteList;
    }

    private boolean validateNuovaLettura(MercatidLetture letture) {

	boolean validate = false;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (letture.getDataLettura() != null && letture.getDataFine() != null && letture.getDataInizio() != null && letture.getMercatiUso() != null
		&& letture.getMercatiUso().getId().getCodice() != null && letture.getPosteggio() != null
		&& letture.getPosteggio().getId().getCodice() != null && letture.getTipiContatore() != null
		&& letture.getTipiContatore().getId() != null) {
	    validate = true;
	} else {
	    ClassValidator<MercatidLetture> validator = new ClassValidator<MercatidLetture>(getEntityClass());
	    InvalidValue[] validationMessages = validator.getInvalidValues(letture);
	    if (validationMessages != null && validationMessages.length > 0) {
		for (InvalidValue invalidValue : validationMessages) {
		    _ivs.add(invalidValue);
		}
		validate = false;
	    }
	}
	if (!validate) {
	    this.throwValidationMessages(_ivs);
	}
	return validate;
    }

    @Override
    public void updateNuoveLetture(List<MercatidLetture> list) {

	for (MercatidLetture mercatidLetture : list) {
	    if (validateNuovaLettura(mercatidLetture)) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mercatidLetture.getDataLettura());
		mercatidLetture.setAnno((Short) ((Integer) calendar.get(Calendar.YEAR)).shortValue());
		mercatidLettureDAO.update(mercatidLetture);
	    }
	}
    }

    @Override
    public void updateImportLetture(List<MercatidLetture> list) {

	for (MercatidLetture mercatidLetture : list) {
	    mercatidLettureDAO.update(mercatidLetture);
	}
    }

    @Override
    public List<MercatidLetture> findByLettureConImporto(MercatidLetture mercatidLetture) {

	return mercatidLettureDAO.findByLettureConImporto(mercatidLetture);
    }

    @Override
    public MercatidLetture findUltimaLetturaFinaleByPosteggio(MercatiD mercatiD) {

	return mercatidLettureDAO.findUltimaLetturaFinaleByPosteggio(mercatiD);
    }

    @Override
    public void insertRegistrazioneGestioneLetture(LettureContatoriCommand command, Integer codiceMercato, MercatiUso uso,
	    Responsabili utenteLoggato) {

	Registrazioni regTemp = command.getRegistrazioni();
	Conti conti = contiService.findById(command.getConti().getId());
	List<MercatidLetture> mercatidLettureList = command.getMercatidLettureList();
	this.validateRegistrazioniMercatidLetture(command);
	for (MercatidLetture mercatidLetture : mercatidLettureList) {
	    if (mercatidLetture.isCreaRegistrazioni()) {
		Registrazioni registrazioni = new Registrazioni();
		Software software = new Software();
		software.setCodice(ORMHelper.getSoftware());
		registrazioni.setSoftware(software);
		registrazioni.setResponsabili(utenteLoggato);
		registrazioni.setResponsabiliSistema(utenteLoggato);
		registrazioni.setDataRegistrazione(regTemp.getDataRegistrazione());
		registrazioni.setRegistrazioniCausali(regTemp.getRegistrazioniCausali());
		registrazioni.setMercatiUso(uso);
		registrazioni.setDescrizione(WebConstants.DESCRIZIONE_REGISTRAZIONI_LETTURE_CONTATORI);
		MercatiD mercatiD = mercatiDService.findById(mercatidLetture.getPosteggio().getId());
		registrazioni.setMercatiD(mercatiD);
		VwConcessioniattive concessioniattive = vwConcessioniattiveService.findByMercatoUsoPosteggio(codiceMercato, uso.getId().getCodice(),
			mercatiD.getId().getCodice());
		if (concessioniattive != null) {
		    if (concessioniattive.getOccupante() != null && concessioniattive.getOccupante().getId().getCodice() != null) {
			registrazioni.setAnagrafe(concessioniattive.getOccupante());
		    } else {
			registrazioni.setAnagrafe(concessioniattive.getTitolare());
		    }
		    Oneritipirateizzazione rate = command.getOneritipirateizzazione();
		    Oneritipirateizzazione oneritipirateizzazione = null;
		    if (rate.getId().getCodice() != null) {
			oneritipirateizzazione = oneritipirateizzazioneService.findById(rate.getId());
		    }
		    Set<RegistrazioniImporti> set = new HashSet<RegistrazioniImporti>();
		    if (oneritipirateizzazione != null) {
			RigaImporto rigaImporto = new RigaImporto();
			rigaImporto.setConto(new ContiBean(conti));
			rigaImporto.setImporto(mercatidLetture.getImporto());
			List<RigaImporto> rigaImportoList = new ArrayList<RigaImporto>();
			rigaImportoList.add(rigaImporto);
			List<RegistrazioniImporti> list = registrazioniService.getImportiRateizzati(registrazioni,
				registrazioni.getDataRegistrazione(), rigaImportoList, oneritipirateizzazione, null, null, null);
			for (RegistrazioniImporti registrazioniImporti : list) {
			    set.add(registrazioniImporti);
			}
		    } else {
			RegistrazioniImporti importi = new RegistrazioniImporti();
			importi.setConti(conti);
			Integer iva = null;
			if (conti != null) {
			    iva = conti.getIva();
			}
			if (iva == null) {
			    // iva = WebConstants.CONST_IVA;
			    throw new RuntimeException(
				    "Errore nella configurazione dei conti della contabilita'. Non e' stata definita l'iva per il conto " +
					    conti.getDescrizione() +
					    "(" +
					    conti.getId() +
					    ")");
			}
			importi.setIva(iva);
			importi.setNrRata(1);
			importi.setRegistrazioni(registrazioni);
			importi.setScadenza(registrazioni.getDataRegistrazione());
			importi.setImporto(mercatidLetture.getImporto());
			set.add(importi);
		    }
		    registrazioniService.insertRegistrazioniLettureContatori(registrazioni, set);
		}
	    }
	}
    }

    @Override
    public void validateRegistrazioniMercatidLetture(LettureContatoriCommand command) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (command.getConti() == null || command.getConti().getId().getCodice() == null) {
	    InvalidValue iv = new InvalidValue("validator.nonvuoto", command.getClass(), "conti", null, command);
	    _ivs.add(iv);
	}
	if (command.getRegistrazioni().getDataRegistrazione() == null) {
	    InvalidValue iv = new InvalidValue("validator.nonvuoto", command.getClass(), "registrazioni.dataRegistrazione", "", command);
	    _ivs.add(iv);
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
    }
}
