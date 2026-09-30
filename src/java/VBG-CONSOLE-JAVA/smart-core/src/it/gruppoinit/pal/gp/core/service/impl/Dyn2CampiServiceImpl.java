package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;
import it.gruppoinit.pal.gp.core.domain.MercatiD2cAssegnaz;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Anagrafedyn2datiStoricoService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiproprietaService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiBottoneEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiCheckboxEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiDataEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiDecimaliEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiInteroEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiLinkEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiListaEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiListaSigeproEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiMultiListaEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiRadioButtonsEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiRicercaEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiTestoEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.CampiUploadEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.MercatiD2cAssegnazService;

@Service
public class Dyn2CampiServiceImpl extends BaseServiceImpl<Dyn2Campi, PkId> implements Dyn2CampiService {

    private Dyn2CampiDAO dyn2CampiDAO;
    private Dyn2CampiproprietaService dyn2CampiproprietaService;
    private Anagrafedyn2datiStoricoService anagrafedyn2datiStoricoService;
    private MercatiD2cAssegnazService mercatiD2cAssegnazService;

    @Autowired
    public void setDyn2CampiproprietaService(Dyn2CampiproprietaService dyn2CampiproprietaService) {

	this.dyn2CampiproprietaService = dyn2CampiproprietaService;
    }

    @Autowired
    public void setDyn2CampiDAO(Dyn2CampiDAO dyn2CampiDAO) {

	this.dyn2CampiDAO = dyn2CampiDAO;
    }

    @Autowired
    public void setAnagrafedyn2datiStoricoService(Anagrafedyn2datiStoricoService anagrafedyn2datiStoricoService) {

	this.anagrafedyn2datiStoricoService = anagrafedyn2datiStoricoService;
    }

    @Autowired
    public void setMercatiD2cAssegnazService(MercatiD2cAssegnazService mercatiD2cAssegnazService) {

	this.mercatiD2cAssegnazService = mercatiD2cAssegnazService;
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

	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    dyn2CampiDAO.insert(entity);
	    childInsert(entity);
	}
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
	if (entity.getTipodato().equals(TipoControlloEnum.Link.name())) {
	    CampiLinkEnum[] tipi = CampiLinkEnum.values();
	    for (CampiLinkEnum campiLinkEnum : tipi) {
		campiproprieta = checkCampoExist(campiConfigurati, campiLinkEnum.name(), campiLinkEnum.value(), entity);
		lista.add(campiproprieta);
	    }
	}
	return lista;
    }

    private void childInsert(Dyn2Campi entity) {

	Set<Dyn2Campiproprieta> lista = this.createDyn2Campiproprieta(entity);
	for (Dyn2Campiproprieta dyn2Campiproprieta : lista) {
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
    public Dyn2Campi findByNomeCampo(String idcomune, String nomecampo) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	filterRestriction.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	filterRestriction.addFilterField(FilterUtils.equals("nomecampo", nomecampo, String.class));
	filterTable.addRestriction(filterRestriction);
	List<Dyn2Campi> list = dyn2CampiDAO.findByFilterTable(filterTable);
	if (!list.isEmpty()) {
	    return list.get(0);
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
    public List<Dyn2Campi> findByDescrizioneAndSoftwareAndModello(String textToSearch, String idcomune, Integer codiceModello,
	    String codiceSoftware) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
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
	    restriction.addFilterField(FilterUtils.like("nomecampo", textToSearch));
	}
	filterTable.addRestriction(restriction);
	return dyn2CampiDAO.findByFilterTable(filterTable);
    }

    @Override
    protected void childDelete(Dyn2Campi entity) {

	Set<Dyn2Campiproprieta> dyn2Campiproprietas = entity.getDyn2Campiproprietas();
	for (Dyn2Campiproprieta dyn2Campiproprieta : dyn2Campiproprietas) {
	    dyn2CampiproprietaService.delete(dyn2Campiproprieta);
	}
    }

    private boolean isInsertAllowed(Dyn2Campi entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Dyn2Campi dyn2Campi = this.findByNomeCampo(entity.getId().getIdcomune(), entity.getNomecampo());
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
	//	if (isCampoUsedInIstanze(entity.getId().getCodice(), null)) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE", null));
	//	}
	//	if (isCampoUsedInAnagrafe(entity.getId().getCodice(), null)) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFE", null));
	//	}
	//	if (isCampoUsedInAttivita(entity.getId().getCodice(), null)) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "I_ATTIVITA", null));
	//	}
	//	if (isCampoUsedInAttivita(entity.getId().getCodice(), null)) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "I_ATTIVITA", null));
	//	}
	if (entity.getDyn2Modellids().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "DYN2_MODELLID", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<ChiaveValoreBean<String, String>> findValoriPerCampo(String textToSearch, String idcomune, Integer codiceCampo, int numMaxResults) {

	return dyn2CampiDAO.findValoriPerCampo(textToSearch, idcomune, codiceCampo, numMaxResults);
    }

    //    @Override
    //    public boolean isCampoUsedInIstanzeOrAttivitaOrAnagrafe(Integer codiceCampo) {
    //
    //	Assert.notNull(codiceCampo);
    //	return isCampoUsedInIstanze(codiceCampo, null) && isCampoUsedInAnagrafe(codiceCampo, null) && isCampoUsedInAttivita(codiceCampo, null);
    //    }
    //
    //    @Override
    //    public boolean isCampoUsedInIstanze(Integer codiceCampo, Integer codiceModello) {
    //
    //	return dyn2CampiDAO.isCampoUsedInIstanze(codiceCampo, codiceModello);
    //    }
    //
    //    @Override
    //    public boolean isCampoUsedInAttivita(Integer codiceCampo, Integer codiceModello) {
    //
    //	return dyn2CampiDAO.isCampoUsedInAttivita(codiceCampo, codiceModello);
    //    }
    //
    //    @Override
    //    public boolean isCampoUsedInAnagrafe(Integer codiceCampo, Integer codiceModello) {
    //
    //	return dyn2CampiDAO.isCampoUsedInAnagrafe(codiceCampo, codiceModello);
    //    }
    @Override
    public List<Dyn2Campi> findByIdModelloAndIdCampo(String idcomune, Integer codiceScheda, Integer codice) {

	return dyn2CampiDAO.findByIdModelloAndIdCampo(idcomune, codiceScheda, codice);
    }

    @Override
    public List<Dyn2Campi> findByIdModello(String idComuneScheda, Integer idModello) {

	return dyn2CampiDAO.findByIdModello(idComuneScheda, idModello);
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
    public Dyn2Campi insertNewCampiproprietaAndView(Dyn2Campi dyn2campi, String tipodato) {

	//dyn2campi.setTipodato(tipodato);
	Dyn2Campi _dyn2campi = childInsert(dyn2campi, tipodato);
	return _dyn2campi;
    }

    private Dyn2Campi childInsert(Dyn2Campi entity, String tipodato) {

	Set<Dyn2Campiproprieta> dyn2Campiproprietas = entity.getDyn2Campiproprietas();
	for (Dyn2Campiproprieta dyn2Campiproprieta : dyn2Campiproprietas) {
	    dyn2CampiproprietaService.delete(dyn2Campiproprieta);
	}
	dyn2CampiDAO.flush();
	dyn2CampiDAO.clear();
	//	dyn2CampiDAO.commit();
	Dyn2Campi _temp = this.findById(new PkId(entity.getId().getCodice()));
	_temp.setTipodato(tipodato);
	Set<Dyn2Campiproprieta> lista = this.createDyn2Campiproprieta(_temp);
	for (Dyn2Campiproprieta dyn2Campiproprieta : lista) {
	    dyn2CampiproprietaService.insert(dyn2Campiproprieta);
	}
	//	Dyn2Campi _dyn2 = dyn2campiService.findById(entity.getId());
	//	//	List<Dyn2Campiproprieta> campiproprietas = dyn2CampiproprietaService.findByDyn2Dati(dyn2campi.getId().getCodice());
	//	_dyn2.setDyn2Campiproprietas(new HashSet<Dyn2Campiproprieta>(lista));
	//	_dyn2.setDyn2Campiproprietas(dyn2Campiproprietas);
	_temp.setDyn2Campiproprietas(lista);
	//Dyn2Campi campi = this.findById(new PkId(entity.getId().getCodice()));
	return _temp;
    }
}
