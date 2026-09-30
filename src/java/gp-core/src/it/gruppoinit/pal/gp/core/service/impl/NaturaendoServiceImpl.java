package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.NaturaendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
@Service
public class NaturaendoServiceImpl extends BaseServiceImpl<Naturaendo, NaturaendoId> implements NaturaendoService {

    private NaturaendoDAO naturaendoDAO;
    private static final Logger log = LoggerFactory.getLogger(NaturaendoServiceImpl.class);

    @Autowired
    public void setNaturaendoDAO(NaturaendoDAO naturaendoDAO) {

	this.naturaendoDAO = naturaendoDAO;
    }

    @Override
    protected Class<Naturaendo> getEntityClass() {

	return Naturaendo.class;
    }

    @Override
    public List<Naturaendo> findAll(Integer firstResult, Integer maxResult) {

	return naturaendoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Naturaendo entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    naturaendoDAO.insert(entity);
	}
    }

    @Override
    public Naturaendo findById(NaturaendoId id) {

	return naturaendoDAO.findById(id);
    }

    @Override
    public void update(Naturaendo entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    naturaendoDAO.update(entity);
	}
    }

    @Override
    public void delete(Naturaendo entity) {

	if (isDeleteAllowed(entity)) {
	    naturaendoDAO.delete(entity);
	}
    }

    @Override
    public List<Naturaendo> findBydescrizione(String descrizione) {

	return naturaendoDAO.findBydescrizione(descrizione);
    }

    @Override
    public List<Naturaendo> getNatureendoByDipendenze(List<Naturaendo> list, Naturaendo naturaendo, boolean isEscludiNaturaPassata) {

	if (list == null) {
	    if (isEscludiNaturaPassata) {
		list = this.findAllExcludeNatura(naturaendo, OrderTypeEnum.DESC);
	    } else {
		list = this.findAllExcludeNatura(null, OrderTypeEnum.DESC);
	    }
	} else {
	    if (isEscludiNaturaPassata)
		// Se uso la lista passata devo bonificarla, cioè devo essere sicuro che nella lista
		// non sia presente anche la natura passata.
		for (Naturaendo temp : list) {
		    if (temp.getId().getCodice().equals(naturaendo.getId().getCodice())) {
			list.remove(naturaendo);
			break;
		    }
		}
	}
	List<Naturaendo> risultato = new ArrayList<Naturaendo>();
	//Estraggo il valore della binario dipendenza
	Integer binarioDipendenzaNaturaendo = naturaendo.getBinariodipendenze();
	for (Naturaendo naturaendoTemp : list) {
	    if (isBinarioDipendenza(binarioDipendenzaNaturaendo, naturaendoTemp.getId().getCodice())) {
		naturaendoTemp.setTransietFlagBinariodipendenze(true);
		risultato.add(naturaendoTemp);
	    } else {
		naturaendoTemp.setTransietFlagBinariodipendenze(false);
		risultato.add(naturaendoTemp);
	    }
	}
	return risultato;
    }

    // Controlla se il valore passato è contenuto nel valore binario dipendenze
    private boolean isBinarioDipendenza(Integer binariodipendenza, Integer valore) {

	int controllo = binariodipendenza & valore;
	if (controllo == valore) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public List<Naturaendo> findAllExcludeNatura(Naturaendo naturaendo, OrderTypeEnum orderTypeEnum) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (EntityUtils.getNestedProperty(naturaendo, "id.codice") != null) {
	    fr.addFilterField(FilterUtils.notEquals("id.codice", naturaendo.getId().getCodice(), Integer.class));
	}
	ft.addRestriction(fr);
	if (orderTypeEnum != null) {
	    switch (orderTypeEnum) {
	    case DESC:
		ft.addOrder(FilterUtils.orderDesc("id.codice"));
		break;
	    case ASC:
		ft.addOrder(FilterUtils.orderAsc("id.codice"));
		break;
	    default:
		break;
	    }
	}
	return naturaendoDAO.findByFilterTable(ft);
    }

    @Override
    public Naturaendo findByStpModalitaAperturaAndTipoScheda(String nameModalitaAperturaEndo1, String tipoScheda) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	String path = "stpModalitaAperturaSchedaEndo1";
	if (StringUtils.defaultIfEmpty(tipoScheda, WebConstants.SCHEDA_TIPO_ENDO1).equalsIgnoreCase(WebConstants.SCHEDA_TIPO_ENDO2)) {
	    path = "stpModalitaAperturaSchedaEndo2";
	}
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id", nameModalitaAperturaEndo1, path, String.class));
	fr.addFilterField(FilterUtils.equals("tipoScheda", tipoScheda, path, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id", path));
	List<Naturaendo> list = naturaendoDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return naturaendoDAO.findByFilterTable(ft).get(0);
	}
	return null;
    }

    @Override
    public Integer findMaxCodicenatura() {

	return naturaendoDAO.findMaxCodicenatura();
    }

    protected boolean isDeleteAllowed(Naturaendo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// -------------------------------------CONTROLLO OGGETTI COLLEGATI -------------------------------------////
	//------------------------------------------------------------------------------------------------------/////
	// Controllo se le nature sono usate negli endoprocedimenti , non potranno essere cancellate
	Set<Inventarioprocedimenti> inventarioprocedimentis = entity.getInventarioprocedimentis();
	if (!inventarioprocedimentis.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "INVENTARIOPROCEDIMENTI", null));
	    delete = false;
	}
	// Controllo se le nature sono usate  nelle procedure, non potranno essere cancellate
	Set<Tipiprocedure> tipiprocedures = entity.getTipiprocedures();
	if (!tipiprocedures.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "TIPIPROCEDURE", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	// -------------------------------------CONTROLLO DIPENDENZE---------------------------------------------////
	//------------------------------------------------------------------------------------------------------/////
	// Controllo se la natura ha una dipendenza con un altra, nel caso non potrà essere eliminata.
	// Recupero tutte le nature endo tranne quella che stiamo eleminando
	List<Naturaendo> naturaendos = this.findAllExcludeNatura(entity, OrderTypeEnum.ASC);
	//Stringa che conterrà  le nature con cui è compatitibile quella che vogliamo eliminare
	StringBuffer buffer = new StringBuffer();
	// Ciclo tutte le nature endo
	for (Naturaendo naturaendo : naturaendos) {
	    // Per ognuna trovo quelle con cui è dipendente
	    List<Naturaendo> list = this.getNatureendoByDipendenze(null, naturaendo, true);
	    // Ciclo la lista trovata
	    for (Naturaendo naturaendo2 : list) {
		// Se è dipendente con quella che voglio eliminare esco da ciclo e l'aggiungo alla stringa che 
		//verrà mostrata in errore
		//if (naturaendo2.getTransietFlagBinariodipendenze().equals(true)) {
		if (naturaendo2.getId().getCodice().equals(entity.getId().getCodice()) && naturaendo2.getTransietFlagBinariodipendenze().equals(true)) {
		    buffer.append(naturaendo.getDescrizioneEstesa()).append(", ");
		    break;
		}
	    }
	}
	//se la stringa è non vuota allora devo rilanciare l'errore
	if (StringUtils.isNotBlank(buffer.toString())) {
	    buffer = new StringBuffer(buffer.substring(0, buffer.length() - 2));
	    String message = getMessageFromBundle("natureendo.service_error.dettaglio_errore_dipendenze", null);
	    buffer.append(message);
	    _ivs.add(new InvalidValue(WebConstants.ALERT_COMPATIBILE_IN, null, "", buffer.toString(), null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private void dataIntegration(Naturaendo entity, boolean isUpdate) {

	Integer codiceNatura = new Integer(1);
	//Inserisco 
	if (!isUpdate) {
	    //trovo il codice natura endo più grande
	    Integer codicemax = this.findMaxCodicenatura();
	    // se diverso da zero il nuovo codice natura sarà dato dal codice max  per 2,
	    //altrimenti sarà uguale a 1
	    if (codicemax != 0) {
		codiceNatura = codicemax * 2;
	    }
	    NaturaendoId id = new NaturaendoId(codiceNatura);
	    entity.setId(id);
	} else// Aggiorno
	{
	    codiceNatura = entity.getId().getCodice();
	}
	//-------------------------------------- GESTIONE DELLA COMPATIBILITà TRA LE VARIE NATURE---------------------------------//
	//-----------------------------------------------------------------------------------------------------------------------//
	// Sarà dato dalla somma dei codici natura delle nature endo scelte come compatibili
	// più il codice della natura che si sta inserendo
	Integer binariodipendenze = codiceNatura;
	if (StringUtils.isNotBlank(entity.getNaturecompatitibili())) {
	    String[] field = entity.getNaturecompatitibili().split(",");
	    for (int i = 0; i < field.length; i++) {
		try {
		    binariodipendenze += Integer.parseInt(field[i]);
		} catch (NumberFormatException e) {
		    log.error("Attenzione!Anomalia sul DB, esiste un codice natura di tipo non Integer: {}", field[i]);
		    throw new RuntimeException("Attenzione!Anomalia sul DB, esiste un codice natura di tipo non Integer: " + field[i]);
		}
	    }
	}
	entity.setBinariodipendenze(binariodipendenze);
	//-------------------------------------- Bonifica dei campi Booleani-----------------------------------------------------//
	//-----------------------------------------------------------------------------------------------------------------------//
	if (entity.getNoneseguecontromovobblig() == null) {
	    entity.setNoneseguecontromovobblig(new Boolean(false));
	}
    }

    @Override
    public Naturaendo findByNaturaBase(String codiceSistemaEsterno) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("naturabase", codiceSistemaEsterno, String.class));
	ft.addRestriction(fr);
	List<Naturaendo> list = naturaendoDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return naturaendoDAO.findByFilterTable(ft).get(0);
	}
	return null;
    }
}
