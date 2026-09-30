package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidtestiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Dyn2ModellidServiceImpl extends BaseServiceImpl<Dyn2Modellid, PkId> implements Dyn2ModellidService {

    private Dyn2ModellidDAO dyn2ModellidDAO;
    private Dyn2ModellitService dyn2ModellitService;
    private Dyn2CampiService dyn2CampiService;
    private Dyn2ModellidtestiService dyn2ModellidtestiService;
    private Dyn2RegoleService dyn2RegoleService;

    @Autowired
    public void setDyn2ModellidDAO(Dyn2ModellidDAO dyn2ModellidDAO) {

	this.dyn2ModellidDAO = dyn2ModellidDAO;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setDyn2ModellidtestiService(Dyn2ModellidtestiService dyn2ModellidtestiService) {

	this.dyn2ModellidtestiService = dyn2ModellidtestiService;
    }

    @Autowired
    public void setDyn2RegoleService(Dyn2RegoleService dyn2RegoleService) {

	this.dyn2RegoleService = dyn2RegoleService;
    }

    @Override
    public void delete(Dyn2Modellid entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2ModellidDAO.delete(entity);
	    dyn2ModellidDAO.flush();
	    childDelete(entity);
	}
    }

    @Override
    protected void childDelete(Dyn2Modellid entity) {

	/*
	if (EntityUtils.getNestedProperty(entity.getDyn2Modellidtesti(), "id.codice") != null) {
	    Dyn2Modellidtesti dyn2Modellidtesti = dyn2ModellidtestiService.findById(new PkId(entity.getDyn2Modellidtesti().getId().getCodice()));
	    dyn2ModellidtestiService.delete(dyn2Modellidtesti);
	}
	*/
    }

    @Override
    public List<Dyn2Modellid> findAll(Integer firstResult, Integer maxResult) {

	return dyn2ModellidDAO.findAll(null, null);
    }

    @Override
    public Dyn2Modellid findById(PkId id) {

	return dyn2ModellidDAO.findById(id);
    }

    @Override
    public void insert(Dyn2Modellid entity) {

	childDataInsert(entity);
	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    dyn2ModellidDAO.insert(entity);
	}
    }

    @Override
    public void update(Dyn2Modellid entity) {

	childDataUpdate(entity);
	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    dyn2ModellidDAO.update(entity);
	}
    }

    @Override
    protected Class<Dyn2Modellid> getEntityClass() {

	return Dyn2Modellid.class;
    }

    @Override
    public List<Dyn2Modellid> findByModelloT(Dyn2Modellit dyn2Modellit) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("dyn2ModellitId", dyn2Modellit.getId().getCodice(), Integer.class));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("posverticale"));
	filterTable.addOrder(FilterUtils.orderAsc("posorizzontale"));
	return dyn2ModellidDAO.findByFilterTable(filterTable);
    }

    @Override
    public Integer findMaxRigaByModelloT(Dyn2Modellit dyn2Modellit) {

	return dyn2ModellidDAO.findMaxRigaByModelloT(dyn2Modellit);
    }

    @Override
    public Integer findMaXColonnaByRigaModelloDAndModelloT(Dyn2Modellit dyn2Modellit, Dyn2Modellid dyn2Modellid) {

	return dyn2ModellidDAO.findMaXColonnaByRigaModelloDAndModelloT(dyn2Modellit, dyn2Modellid);
    }

    @Override
    public void updateFlagMultiplo(Dyn2Modellid dyn2Modellid) {

	dyn2ModellidDAO.update(dyn2Modellid);
	// ricerco se ci sono altri modelli d associati al modello t che hanno la stessa riga del modello d passato
	List<Dyn2Modellid> modellids = this.findModelliDByModelliTAndRiga(dyn2Modellid.getDyn2Modellit(), dyn2Modellid.getPosverticale());
	// per ognuno dei modelli d trovati setto il flagMultiplo al valkore del modello D passato
	for (Dyn2Modellid dyn2ModellidTemp : modellids) {
	    dyn2ModellidTemp.setFlgMultiplo(dyn2Modellid.getFlgMultiplo());
	    dyn2ModellidDAO.update(dyn2ModellidTemp);
	}
    }

    @Override
    public List<Dyn2Modellid> findModelliDByModelliTAndRiga(Dyn2Modellit dyn2Modellit, Integer posverticale) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("dyn2ModellitId", dyn2Modellit.getId().getCodice(), Integer.class));
	filterRestriction.addFilterField(FilterUtils.equals("posverticale", posverticale, Integer.class));
	filterTable.addRestriction(filterRestriction);
	return dyn2ModellidDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Dyn2Modellid> findByCampo(Integer codiceCampo) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("dyn2CampiId", codiceCampo, Integer.class));
	filterTable.addRestriction(filterRestriction);
	return dyn2ModellidDAO.findByFilterTable(filterTable);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////    SEZIONE METODI PRIVATI //////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void dataIntegration(Dyn2Modellid entity) {

	// Gestione righe colonne se lasciate vuote
	//Se il campo righe(Posverticale) è lasciato vuoto, recupero il max righe dei dei modelli d associtati al modello t e 
	// la incremento di 10 e metto le colonne  (Posorizzontale) se il suo valore è null a 1 in quanto sicuramente non esistono colonne per quella righa in quanto
	// è stata inserita ora
	if (entity.getPosverticale() == null) {
	    // calcolo il max riga
	    Integer riga = this.findMaxRigaByModelloT(entity.getDyn2Modellit()) + 10;
	    entity.setPosverticale(riga);
	    if (entity.getPosorizzontale() == null) {
		entity.setPosorizzontale(new Integer(1));
	    }
	} else {
	    // metto il valore max + 1 per la riga passata, se ad esempio esiste già un modello d in riga
	    //10 con colonna 1 , se inserisco un altro modello con riga 10 la colonna verrà impostata ad 2
	    if (entity.getPosorizzontale() == null) {
		Integer colonna = this.findMaXColonnaByRigaModelloDAndModelloT(entity.getDyn2Modellit(), entity) + 1;
		entity.setPosorizzontale(colonna);
	    }
	}
	if (entity.getFlgMultiplo() == null) {
	    entity.setFlgMultiplo(false);
	}
	if (entity.getFlgObbligatorio() == null) {
	    entity.setFlgObbligatorio(false);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Dyn2Modellid entity) {

	Dyn2Modellit dyn2Modellit = dyn2ModellitService.bindDomainObject(entity.getDyn2Modellit(), PkId.class, "id.codice");
	entity.setDyn2Modellit(dyn2Modellit);
	Dyn2Campi dyn2Campi = dyn2CampiService.bindDomainObject(entity.getDyn2Campi(), PkId.class, "id.codice");
	entity.setDyn2Campi(dyn2Campi);
	Dyn2Modellidtesti dyn2Modellidtesti = dyn2ModellidtestiService.bindDomainObject(entity.getDyn2Modellidtesti(), PkId.class, "id.codice");
	entity.setDyn2Modellidtesti(dyn2Modellidtesti);
	Dyn2Regole dyn2Regole = dyn2RegoleService.bindDomainObject(entity.getDyn2RegoleAttivo(), PkId.class, "id.codice");
	entity.setDyn2RegoleAttivo(dyn2Regole);
    }

    private void childDataInsert(Dyn2Modellid entity) {

	if (entity.getTipocampoTransient().equals(WebConstants.CAMPO_TESTO)) {
	    Dyn2Modellidtesti dyn2Modellidtesti = entity.getDyn2Modellidtesti();
	    dyn2ModellidtestiService.insert(dyn2Modellidtesti);
	    entity.setDyn2Modellidtesti(dyn2Modellidtesti);
	}
    }

    private void childDataUpdate(Dyn2Modellid entity) {

	if (entity.getTipocampoTransient() != null && entity.getTipocampoTransient().equals(WebConstants.CAMPO_TESTO)) {
	    Dyn2Modellidtesti dyn2Modellidtesti = entity.getDyn2Modellidtesti();
	    dyn2ModellidtestiService.update(dyn2Modellidtesti);
	    entity.setDyn2Modellidtesti(dyn2Modellidtesti);
	}
    }

    private boolean isInsertAllowed(Dyn2Modellid entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getTipocampoTransient() != null) {
	    if (entity.getTipocampoTransient().equalsIgnoreCase(WebConstants.CAMPO_DINAMICO)) {
		if (EntityUtils.getNestedProperty(entity.getDyn2Campi(), "id.codice") == null) {
		    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", entity.getClass(), "dyn2Campi", entity.getDyn2Campi(), entity));
		    insert = false;
		}
		//	    String idContestoCampo = (String) EntityUtils.getNestedProperty(entity.getDyn2Campi(), "dyn2Basecontesti.id");
		//	    String idContestoModello = (String) EntityUtils.getNestedProperty(entity.getDyn2Modellit(), "basecontesti.id");
		//	    if (!(StringUtils.isBlank(idContestoCampo) || StringUtils.isBlank(idContestoModello))) {
		//		if (StringUtils.isNotBlank(idContestoCampo)) {
		//		    if (!idContestoCampo.equals(idContestoModello)) {
		//			_ivs.add(new InvalidValue("service_error.campo_diverso_contesto_modellot", entity.getClass(), "dyn2Campi", entity
		//				.getDyn2Campi(), entity));
		//			insert = false;
		//		    }
		//		}
		//	    }
	    }
	    if (entity.getTipocampoTransient().equalsIgnoreCase(WebConstants.CAMPO_TESTO)) {
		if (EntityUtils.getNestedProperty(entity.getDyn2Modellidtesti(), "id.codice") != null
			&& StringUtils.isBlank(entity.getDyn2Modellidtesti().getTesto())) {
		    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", entity.getClass(), "dyn2Modellidtesti.testo", entity
			    .getDyn2Modellidtesti(), entity.getDyn2Modellidtesti()));
		    insert = false;
		}
	    }
	}
	if (!insert) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @Override
    protected boolean isDeleteAllowed(Dyn2Modellid entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!EntityUtils.isNestedPropertyBlank(entity.getDyn2Campi(), "id.codice")) {
	    Integer codiceCampo = (Integer) EntityUtils.getNestedProperty(entity.getDyn2Campi(), "id.codice");
	    //	    if (dyn2CampiService.isCampoUsedInIstanze(codiceCampo, entity.getDyn2Modellit().getId().getCodice())) {
	    //		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEDYN2DATI", null));
	    //	    }
	    //	    if (dyn2CampiService.isCampoUsedInAnagrafe(codiceCampo, entity.getDyn2Modellit().getId().getCodice())) {
	    //		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFEDYN2DATI", null));
	    //	    }
	    //	    if (dyn2CampiService.isCampoUsedInAttivita(codiceCampo, entity.getDyn2Modellit().getId().getCodice())) {
	    //		_ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "I_ATTIVITADYN2DATI", null));
	    //	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    @Override
    public List<Dyn2Modellid> findRigheModello(String idComuneModello, int idModello) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComuneModello, "dyn2Modellit", String.class));
	fr.addFilterField(FilterUtils.equals("dyn2ModellitId", idModello, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("posverticale"));
	ft.addOrder(FilterUtils.orderAsc("posorizzontale"));
	return dyn2ModellidDAO.findByFilterTable(ft);
    }

    /*
    SELECT
    COUNT(*)
    FROM
    ISTANZE, ISTANZEDYN2MODELLIT, DYN2_MODELLID
    WHERE
    DYN2_MODELLID.IDCOMUNE = ISTANZEDYN2MODELLIT.IDCOMUNE AND
    DYN2_MODELLID.FK_D2MT_ID = ISTANZEDYN2MODELLIT.FK_D2MT_ID AND
    DYN2_MODELLID.FK_D2C_ID = 235 AND 
    ISTANZEDYN2MODELLIT.IDCOMUNE = ISTANZE.IDCOMUNE AND
    ISTANZEDYN2MODELLIT.CODICEISTANZA = ISTANZE.CODICEISTANZA AND 
    ISTANZE.IDCOMUNE = 'E256' AND
    ISTANZE.FK_IDI_ATTIVITA = 516 AND
    ISTANZE.DATAVALIDITA = TO_DATE('01/03/2013','DD/MM/YYYY')
    */
    @Override
    public boolean isExistDyn2CampiInDyn2ModelliDIstanzeAndAttivita(Integer codiceAttivita, Date dataSnapshot, Integer codiceCampo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceCampo, "dyn2Campi", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAttivita, "dyn2Modellit.istanzedyn2modellits.istanza.attivita", Integer.class));
	//La riga sottostante è stata commentata perchè la verifica se il campo dinamico viene gestito dalle schede
	//deve essere fatta su tutti i modelli di tutte le istanze di quell'attività ( altrimenti potrei incontrare 
	//come ultima istanza, una istanza che non prevede quella scheda ma il dato dinamico viene comunque gestito
	//dalle schede delle altre istanze!!! )
	//fr.addFilterField(FilterUtils.equals("datavalidita", dataSnapshot, "dyn2Modellit.istanzedyn2modellits.istanza", Date.class));
	ft.addRestriction(fr);
	//boolean risultato = dyn2ModellidDAO.existsRecords(ft);
	int conteggio = dyn2ModellidDAO.countRecord(ft);
	boolean risultato = (conteggio > 0);
	return risultato;
    }

    @Override
    public List<Dyn2Modellid> findByRegola(Integer codiceRegola) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceRegola, "dyn2RegoleAttivo", Integer.class));
	ft.addRestriction(fr);
	return dyn2ModellidDAO.findByFilterTable(ft);
    }

    @Override
    public List<Dyn2Modellid> findByModelloAndCampo(String idcomune, Integer codicemodello, Integer codiceCampo, Integer firstResult,
	    Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	filterRestriction.addFilterField(FilterUtils.equals("dyn2ModellitId", codicemodello, Integer.class));
	filterRestriction.addFilterField(FilterUtils.equals("dyn2CampiId", codiceCampo, Integer.class));
	filterTable.addRestriction(filterRestriction);
	filterTable.addOrder(FilterUtils.orderAsc("posverticale"));
	filterTable.addOrder(FilterUtils.orderAsc("posorizzontale"));
	if (firstResult != null && maxResult != null) {
	    return dyn2ModellidDAO.findByFilterTable(filterTable, firstResult, maxResult);
	} else {
	    return dyn2ModellidDAO.findByFilterTable(filterTable);
	}
    }

    @Override
    public Boolean findObbligatorioByModelloAndCampo(String idcomune, Integer codicemodello, Integer codiceCampo) {

	List<Dyn2Modellid> mds = findByModelloAndCampo(idcomune, codicemodello, codiceCampo, 0, 1);
	for (Dyn2Modellid d2s : mds) {
	    return d2s.getFlgObbligatorio();
	}
	return Boolean.FALSE;
    }
}
