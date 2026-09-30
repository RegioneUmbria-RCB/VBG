package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basecontesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.MercatiD2cAssegnaz;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Anagrafedyn2datiStoricoService;
import it.gruppoinit.pal.gp.core.service.Dyn2BasecontestiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiproprietaService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiBottoneEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiCheckboxEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiDataEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiDecimaliEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiInteroEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiListaEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiListaSigeproEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiMultiListaEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiRadioButtonsEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiRicercaEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiTestoEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiUploadEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiStoricoService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiStoricoService;
import it.gruppoinit.pal.gp.core.service.MercatiD2cAssegnazService;

@Service
public class Dyn2CampiServiceImpl extends BaseServiceImpl<Dyn2Campi, PkId> implements Dyn2CampiService {

    private Dyn2CampiDAO dyn2CampiDAO;
    private Dyn2CampiproprietaService dyn2CampiproprietaService;
    private Istanzedyn2datiStoricoService istanzedyn2datiStoricoService;
    private Anagrafedyn2datiStoricoService anagrafedyn2datiStoricoService;
    private IAttivitadyn2datiStoricoService iAttivitadyn2datiStoricoService;
    private MercatiD2cAssegnazService mercatiD2cAssegnazService;
    private Dyn2BasecontestiService dyn2BasecontestiService;

    @Autowired
    public void setDyn2CampiproprietaService(Dyn2CampiproprietaService dyn2CampiproprietaService) {

	this.dyn2CampiproprietaService = dyn2CampiproprietaService;
    }

    @Autowired
    public void setDyn2CampiDAO(Dyn2CampiDAO dyn2CampiDAO) {

	this.dyn2CampiDAO = dyn2CampiDAO;
    }

    @Autowired
    public void setIstanzedyn2datiStoricoService(Istanzedyn2datiStoricoService istanzedyn2datiStoricoService) {

	this.istanzedyn2datiStoricoService = istanzedyn2datiStoricoService;
    }

    @Autowired
    public void setAnagrafedyn2datiStoricoService(Anagrafedyn2datiStoricoService anagrafedyn2datiStoricoService) {

	this.anagrafedyn2datiStoricoService = anagrafedyn2datiStoricoService;
    }

    @Autowired
    public void setiAttivitadyn2datiStoricoService(IAttivitadyn2datiStoricoService iAttivitadyn2datiStoricoService) {

	this.iAttivitadyn2datiStoricoService = iAttivitadyn2datiStoricoService;
    }

    @Autowired
    public void setMercatiD2cAssegnazService(MercatiD2cAssegnazService mercatiD2cAssegnazService) {

	this.mercatiD2cAssegnazService = mercatiD2cAssegnazService;
    }

    @Autowired
    public void setDyn2BasecontestiService(Dyn2BasecontestiService dyn2BasecontestiService) {

	this.dyn2BasecontestiService = dyn2BasecontestiService;
    }

    @Override
    public void delete(Dyn2Campi entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    dyn2CampiDAO.delete(entity);
	}
    }

    @Override
    public List<Dyn2Campi> findAll(Integer firstResult, Integer maxResult) {

	return dyn2CampiDAO.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "nomecampo", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Dyn2Campi findById(PkId id) {

	Dyn2Campi result = dyn2CampiDAO.findById(id);
	return result;
    }

    @Override
    public void insert(Dyn2Campi entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    dyn2CampiDAO.insert(entity);
	    childInsert(entity);
	}
    }

    private void dataIntegration(Dyn2Campi entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Dyn2Campi entity) {

	Dyn2Basecontesti dyn2Basecontesti = dyn2BasecontestiService.bindDomainObject(entity.getDyn2Basecontesti(), String.class, "id");
	entity.setDyn2Basecontesti(dyn2Basecontesti);
    }

    @Override
    public Set<Dyn2Campiproprieta> createDyn2Campiproprieta(Dyn2Campi entity) {

	Set<Dyn2Campiproprieta> lista = new LinkedHashSet<Dyn2Campiproprieta>();
	Dyn2Campiproprieta campiproprieta = null;
	Set<Dyn2Campiproprieta> campiConfigurati = entity.getDyn2Campiproprietas();
	//Vengono creati gli oggetti Dyn2Campiproprieta per il tipo campo scelto
	if (entity.getTipodato().equals(TipoControlloEnum.Bottone.name())) {
	    CampiBottoneEnum[] tipi = CampiBottoneEnum.values();
	    for (CampiBottoneEnum campiBottone : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiBottone.name(), campiBottone.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.Checkbox.name())) {
	    CampiCheckboxEnum[] tipi = CampiCheckboxEnum.values();
	    for (CampiCheckboxEnum campiCheckboxEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiCheckboxEnum.name(), campiCheckboxEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.Data.name())) {
	    CampiDataEnum[] tipi = CampiDataEnum.values();
	    for (CampiDataEnum dampiDataEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, dampiDataEnum.name(), dampiDataEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.NumericoDouble.name())) {
	    CampiDecimaliEnum[] tipi = CampiDecimaliEnum.values();
	    for (CampiDecimaliEnum campiDecimaliEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiDecimaliEnum.name(), campiDecimaliEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.NumericoIntero.name())) {
	    CampiInteroEnum[] tipi = CampiInteroEnum.values();
	    for (CampiInteroEnum campiInteroEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiInteroEnum.name(), campiInteroEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.Lista.name())) {
	    CampiListaEnum[] tipi = CampiListaEnum.values();
	    for (CampiListaEnum campiListaEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiListaEnum.name(), campiListaEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.MultiLista.name())) {
	    CampiMultiListaEnum[] tipi = CampiMultiListaEnum.values();
	    for (CampiMultiListaEnum campiMultiListaEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiMultiListaEnum.name(), campiMultiListaEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.Ricerca.name())) {
	    CampiRicercaEnum[] tipi = CampiRicercaEnum.values();
	    for (CampiRicercaEnum campiRicercaEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiRicercaEnum.name(), campiRicercaEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.ListaSIGePro.name())) {
	    CampiListaSigeproEnum[] tipi = CampiListaSigeproEnum.values();
	    for (CampiListaSigeproEnum campiListaSigeproEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiListaSigeproEnum.name(), campiListaSigeproEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.RadioButtons.name())) {
	    CampiRadioButtonsEnum[] tipi = CampiRadioButtonsEnum.values();
	    for (CampiRadioButtonsEnum campiRadioButtonsEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiRadioButtonsEnum.name(), campiRadioButtonsEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.Testo.name())) {
	    CampiTestoEnum[] tipi = CampiTestoEnum.values();
	    for (CampiTestoEnum campiTestoEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiTestoEnum.name(), campiTestoEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	if (entity.getTipodato().equals(TipoControlloEnum.Upload.name())) {
	    CampiUploadEnum[] tipi = CampiUploadEnum.values();
	    for (CampiUploadEnum campiUploadEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiUploadEnum.name(), campiUploadEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	return lista;
    }

    private void childInsert(Dyn2Campi entity) {

	Set<Dyn2Campiproprieta> lista = this.createDyn2Campiproprieta(entity);
	for (Dyn2Campiproprieta dyn2Campiproprieta : lista) {
	    dyn2Campiproprieta.setDyn2Campi(entity);
	    dyn2Campiproprieta.getId().setFkD2cId(entity.getId().getCodice());
	    dyn2CampiproprietaService.insert(dyn2Campiproprieta);
	}
    }

    private Dyn2Campiproprieta checkCampoExist(Set<Dyn2Campiproprieta> campiConfigurati, String nomeCampo, String valoreCampo, Dyn2Campi campi) {

	Dyn2Campiproprieta campiproprieta = new Dyn2Campiproprieta();
	Dyn2CampiproprietaId id = new Dyn2CampiproprietaId();
	campiproprieta.setDyn2Campi(campi);
	String[] valoriCampo = valoreCampo.split("#");
	// Caso in cui il campo già esiste e deve essere solo recuperato
	for (Dyn2Campiproprieta dyn2Campiproprieta : campiConfigurati) {
	    if (dyn2Campiproprieta.getId().getProprieta().equals(nomeCampo)) {
		campiproprieta = dyn2Campiproprieta;
		campiproprieta.setTipologiaCampoTransient(valoriCampo[1]);
		campiproprieta.setEtichettaTransiet(getMessageFromBundle(valoriCampo[0], null));
		// se valoriCampo.length == 3 (c'è un valore di deafult) e è non valorizzato allora metto il vaore
		// Di default
		if (valoriCampo.length == 3 && StringUtils.isBlank(campiproprieta.getValore())) {
		    campiproprieta.setValore(valoriCampo[2]);
		} else {
		    // Se non è valorizzato metto vuoto, altrimenti lascio il valore memorizzato
		    if (!StringUtils.isNotBlank(campiproprieta.getValore())) {
			campiproprieta.setValore("");
		    }
		}
		break;
	    }
	}
	// Il campo non esiste e viene creato
	if (campiproprieta.getId() == null) {
	    id = new Dyn2CampiproprietaId();
	    id.setIdcomune(ORMHelper.getIdcomune());
	    id.setFkD2cId(campi.getId().getCodice());
	    id.setProprieta(nomeCampo);
	    campiproprieta.setId(id);
	    campiproprieta.setTipologiaCampoTransient(valoriCampo[1]);
	    campiproprieta.setEtichettaTransiet(getMessageFromBundle(valoriCampo[0], null));
	    if (valoriCampo.length == 3) {
		campiproprieta.setValore(valoriCampo[2]);
	    } else {
		campiproprieta.setValore("");
	    }
	}
	return campiproprieta;
    }

    @Override
    public void update(Dyn2Campi entity) {

	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    Set<Dyn2Campiproprieta> list = entity.getDyn2Campiproprietas();
	    for (Dyn2Campiproprieta dyn2Campiproprieta : list) {
		dyn2CampiproprietaService.update(dyn2Campiproprieta);
	    }
	    dyn2CampiDAO.update(entity);
	}
    }

    @Override
    public List<Dyn2Campi> findByFilterAndIdModello(Dyn2Campi entity, Integer idModello) {

	return dyn2CampiDAO.findByFilterAndIdModello(entity, idModello);
    }

    @Override
    public List<Dyn2Campi> findByFilterAndIdModelloForNumeric(Dyn2Campi entity, Integer idModello) {

	return dyn2CampiDAO.findByFilterAndIdModelloForNumeric(entity, idModello);
    }

    @Override
    public Dyn2Campi findByNomeCampo(String nomecampo) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("nomecampo", nomecampo, String.class));
	filterTable.addRestriction(filterRestriction);
	List<Dyn2Campi> list = dyn2CampiDAO.findByFilterTable(filterTable);
	if (!list.isEmpty()) {
	    return list.get(0);
	} else {
	    filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    filterRestriction = new FilterRestriction();
	    filterRestriction.addFilterField(FilterUtils.equals("nomecampo", nomecampo, String.class));
	    filterRestriction.addFilterField(FilterUtils.equals("codice", WebConstants.SOFTWARE_TT, "software", String.class));
	    filterTable.addRestriction(filterRestriction);
	    list = dyn2CampiDAO.findByFilterTable(filterTable);
	    if (!list.isEmpty()) {
		return list.get(0);
	    }
	}
	return null;
    }

    @Override
    protected Class<Dyn2Campi> getEntityClass() {

	return Dyn2Campi.class;
    }

    @Override
    public List<Dyn2Campi> findByDescrizioneAndSoftware(String textToSearch, String codiceSoftware) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	if (StringUtils.isNotBlank(codiceSoftware)) {
	    restriction.addFilterField(FilterUtils.equals("codice", codiceSoftware, "software", String.class));
	} else {
	    restriction.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	}
	restriction.addFilterField(FilterUtils.like("nomecampo", textToSearch));
	filterTable.addRestriction(restriction);
	return dyn2CampiDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Dyn2Campi> findByDescrizioneAndSoftwareAndModello(String textToSearch, Integer codiceModello, String codiceSoftware) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	if (codiceModello != null) {
	    restriction.addFilterField(FilterUtils.equals("id.codice", codiceModello, "dyn2Modellids.dyn2Modellit", Integer.class));
	}
	if (codiceModello == null) {
	    if (StringUtils.isNotBlank(codiceSoftware)) {
		restriction.addFilterField(FilterUtils.equals("codice", codiceSoftware, "software", String.class));
	    } else {
		restriction.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	    }
	}
	if (StringUtils.isNotBlank(textToSearch)) {
	    FilterRestriction frOr = new FilterRestriction();
	    frOr.setAndOrRestriction(AndOrRestriction.OR);
	    frOr.addFilterField(FilterUtils.like("nomecampo", textToSearch));
	    frOr.addFilterField(FilterUtils.like("etichetta", textToSearch));
	    filterTable.addRestriction(frOr);
	    // restriction.addFilterField(FilterUtils.like("nomecampo", textToSearch));
	}
	filterTable.addRestriction(restriction);
	return dyn2CampiDAO.findByFilterTable(filterTable);
    }

    @Override
    protected void childDelete(Dyn2Campi entity) {

	Set<Istanzedyn2datiStorico> istanzedyn2datiStoricos = entity.getIstanzedyn2datiStoricos();
	for (Istanzedyn2datiStorico istanzedyn2datiStorico : istanzedyn2datiStoricos) {
	    istanzedyn2datiStoricoService.delete(istanzedyn2datiStorico);
	}
	Set<Anagrafedyn2datiStorico> anagrafedyn2datiStoricos = entity.getAnagrafedyn2datiStoricos();
	for (Anagrafedyn2datiStorico anagrafedyn2datiStorico : anagrafedyn2datiStoricos) {
	    anagrafedyn2datiStoricoService.delete(anagrafedyn2datiStorico);
	}
	Set<IAttivitadyn2datiStorico> iAttivitadyn2datiStoricos = entity.getAttivitadyn2datiStoricos();
	for (IAttivitadyn2datiStorico iAttivitadyn2datiStorico : iAttivitadyn2datiStoricos) {
	    iAttivitadyn2datiStoricoService.delete(iAttivitadyn2datiStorico);
	}
	Set<Dyn2Campiproprieta> campiproprietas = entity.getDyn2Campiproprietas();
	for (Dyn2Campiproprieta dyn2Campiproprieta : campiproprietas) {
	    dyn2CampiproprietaService.delete(dyn2Campiproprieta);
	}
    }

    private boolean isInsertAllowed(Dyn2Campi entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Dyn2Campi dyn2Campi = this.findByNomeCampo(entity.getNomecampo());
	if (dyn2Campi != null && !dyn2Campi.getId().getCodice().equals((entity.getId().getCodice()))) {
	    _ivs.add(new InvalidValue("service_error.campo_gia_esistente", null, null, null, null));
	    insert = false;
	}
	if (!insert) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    protected boolean isDeleteAllowed(Dyn2Campi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (isCampoUsedInIstanze(entity.getId().getCodice(), null)) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE", null));
	}
	if (isCampoUsedInAnagrafe(entity.getId().getCodice(), null)) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFE", null));
	}
	if (isCampoUsedInAttivita(entity.getId().getCodice(), null)) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "I_ATTIVITA", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<ChiaveValoreBean<String, String>> findValoriPerCampo(String textToSearch, Integer codiceCampo, int numMaxResults) {

	return dyn2CampiDAO.findValoriPerCampo(textToSearch, codiceCampo, numMaxResults);
    }

    @Override
    public boolean isCampoUsedInIstanzeOrAttivitaOrAnagrafe(Integer codiceCampo) {

	Assert.notNull(codiceCampo);
	return isCampoUsedInIstanze(codiceCampo, null) && isCampoUsedInAnagrafe(codiceCampo, null) && isCampoUsedInAttivita(codiceCampo, null);
    }

    @Override
    public boolean isCampoUsedInIstanze(Integer codiceCampo, Integer codiceModello) {

	return dyn2CampiDAO.isCampoUsedInIstanze(codiceCampo, codiceModello);
    }

    @Override
    public boolean isCampoUsedInAttivita(Integer codiceCampo, Integer codiceModello) {

	return dyn2CampiDAO.isCampoUsedInAttivita(codiceCampo, codiceModello);
    }

    @Override
    public boolean isCampoUsedInAnagrafe(Integer codiceCampo, Integer codiceModello) {

	return dyn2CampiDAO.isCampoUsedInAnagrafe(codiceCampo, codiceModello);
    }

    @Override
    public List<Dyn2Campi> findByIdModelloAndIdCampo(Integer codiceScheda, Integer codice) {

	return dyn2CampiDAO.findByIdModelloAndIdCampo(codiceScheda, codice);
    }

    @Override
    public List<Dyn2Campi> findByIdModello(Integer idModello) {

	return dyn2CampiDAO.findByIdModello(idModello);
    }

    @Override
    public List<Dyn2Campi> findByDescrizioneAndMercato(String textToSearch, Integer codiceMercato) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	if (codiceMercato != null) {
	    List<MercatiD2cAssegnaz> list = mercatiD2cAssegnazService.findByMercato(codiceMercato);
	    if (!list.isEmpty()) {
		Integer[] listCodiciCampiAmmissibili = new Integer[list.size()];
		int i = 0;
		for (MercatiD2cAssegnaz mercatiD2cAssegnaz : list) {
		    listCodiciCampiAmmissibili[i] = mercatiD2cAssegnaz.getDyn2Campi().getId().getCodice();
		    i++;
		}
		restriction.addFilterField(FilterUtils.in("id.codice", listCodiciCampiAmmissibili, Integer.class));
	    }
	}
	restriction.addFilterField(FilterUtils.like("nomecampo", textToSearch));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderAsc("nomecampo"));
	return dyn2CampiDAO.findByFilterTable(filterTable);
    }

    @Override
    public boolean deleteDyn2CampiNonUsati() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNull("id.codice", "dyn2Modellids"));
	ft.addRestriction(fr);
	List<Dyn2Campi> dyn2Campis = dyn2CampiDAO.findByFilterTable(ft);
	for (Dyn2Campi dyn2Campi : dyn2Campis) {
	    try {
		this.delete(dyn2Campi);
		dyn2CampiDAO.commit();
		dyn2CampiDAO.flush();
	    } catch (Exception e) {
		System.out.println(e.getMessage());
	    }
	}
	return false;
    }

    @Override
    public void insert(Dyn2Campi dyn2Campi, Set<Dyn2Campiproprieta> dyn2Campiproprietas) {

	dataIntegration(dyn2Campi);
	if (validateEntity(dyn2Campi) && isInsertAllowed(dyn2Campi)) {
	    dyn2Campi.setDyn2Campiproprietas(new HashSet<Dyn2Campiproprieta>());
	    dyn2CampiDAO.insert(dyn2Campi);
	    //dyn2Campi.setDyn2Campiproprietas(dyn2Campiproprietas);
	    //childInsert(dyn2Campi);
	}
    }

    @Override
    public boolean isCampoUpload(Integer codiceCampo) {

	Dyn2Campi d2c = this.findById(new PkId(codiceCampo));
	if (d2c != null) {
	    String tipoDatoUpload = StringUtils.defaultString(d2c.getTipodato());
	    if ("Upload".equalsIgnoreCase(tipoDatoUpload)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public List<Dyn2Campi> findAllByDescrizione(String textToSearch) {

	return dyn2CampiDAO.findAllByDescrizione(textToSearch);
    }
}
