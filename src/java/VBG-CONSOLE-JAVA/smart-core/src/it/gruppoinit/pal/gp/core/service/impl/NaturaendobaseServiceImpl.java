package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.NaturaendobaseDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Naturaendobase;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.NaturaendobaseService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NaturaendobaseServiceImpl extends BaseServiceImpl<Naturaendobase, Integer> implements NaturaendobaseService {

    private NaturaendobaseDAO naturaendobaseDAO;
    private static final Logger log = LoggerFactory.getLogger(NaturaendoServiceImpl.class);

    @Autowired
    public void setNaturaendoDAO(NaturaendobaseDAO naturaendoDAO) {

	this.naturaendobaseDAO = naturaendoDAO;
    }

    @Override
    protected Class<Naturaendobase> getEntityClass() {

	return Naturaendobase.class;
    }

    @Override
    public List<Naturaendobase> findAll(Integer firstResult, Integer maxResult) {

	return naturaendobaseDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Naturaendobase entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    naturaendobaseDAO.insert(entity);
	}
    }

    @Override
    public Naturaendobase findById(Integer id) {

	return naturaendobaseDAO.findById(id);
    }

    @Override
    public void update(Naturaendobase entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    naturaendobaseDAO.update(entity);
	}
    }

    @Override
    public void delete(Naturaendobase entity) {

	throw new NotImplementedException("Non si possono cancellare record della tabella di base");
	//	if (isDeleteAllowed(entity)) {
	//	    naturaendobaseDAO.delete(entity);
	//	}
    }

    @Override
    public List<Naturaendobase> findBydescrizione(String descrizione) {

	return naturaendobaseDAO.findBydescrizione(descrizione);
    }

    @Override
    public List<Naturaendobase> getNatureendoByDipendenze(List<Naturaendobase> list, Naturaendobase naturaendo, boolean isEscludiNaturaPassata) {

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
		for (Naturaendobase temp : list) {
		    if (temp.getId().equals(naturaendo.getId())) {
			list.remove(naturaendo);
			break;
		    }
		}
	}
	List<Naturaendobase> risultato = new ArrayList<Naturaendobase>();
	//Estraggo il valore della binario dipendenza
	Integer binarioDipendenzaNaturaendo = naturaendo.getBinariodipendenze();
	for (Naturaendobase naturaendoTemp : list) {
	    if (isBinarioDipendenza(binarioDipendenzaNaturaendo, naturaendoTemp.getId())) {
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
    public List<Naturaendobase> findAllExcludeNatura(Naturaendobase naturaendo, OrderTypeEnum orderTypeEnum) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	if (EntityUtils.getNestedProperty(naturaendo, "id") != null) {
	    fr.addFilterField(FilterUtils.notEquals("id", naturaendo.getId(), Integer.class));
	}
	ft.addRestriction(fr);
	if (orderTypeEnum != null) {
	    switch (orderTypeEnum) {
	    case DESC:
		ft.addOrder(FilterUtils.orderDesc("id"));
		break;
	    case ASC:
		ft.addOrder(FilterUtils.orderAsc("id"));
		break;
	    default:
		break;
	    }
	}
	return naturaendobaseDAO.findByFilterTable(ft);
    }

    //    @Override
    //    public Naturaendobase findByStpModalitaAperturaAndTipoScheda(String nameModalitaAperturaEndo1, String tipoScheda) {
    //
    //	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
    //	String path = "stpModalitaAperturaSchedaEndo1";
    //	if (StringUtils.defaultIfEmpty(tipoScheda, WebConstants.SCHEDA_TIPO_ENDO1).equalsIgnoreCase(WebConstants.SCHEDA_TIPO_ENDO2)) {
    //	    path = "stpModalitaAperturaSchedaEndo2";
    //	}
    //	FilterRestriction fr = new FilterRestriction();
    //	fr.addFilterField(FilterUtils.equals("id", nameModalitaAperturaEndo1, path, String.class));
    //	fr.addFilterField(FilterUtils.equals("tipoScheda", tipoScheda, path, String.class));
    //	ft.addRestriction(fr);
    //	ft.addOrder(FilterUtils.orderAsc("id", path));
    //	List<Naturaendobase> list = naturaendobaseDAO.findByFilterTable(ft);
    //	if (!list.isEmpty()) {
    //	    return naturaendobaseDAO.findByFilterTable(ft).get(0);
    //	}
    //	return null;
    //    }
    @Override
    public Integer findMaxCodicenatura() {

	return naturaendobaseDAO.findMaxCodicenatura();
    }

    protected boolean isDeleteAllowed(Naturaendobase entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// -------------------------------------CONTROLLO OGGETTI COLLEGATI -------------------------------------////
	//------------------------------------------------------------------------------------------------------/////
	// Controllo se le nature sono usate negli endoprocedimenti , non potranno essere cancellate
	// Controllo se le nature sono usate  nelle procedure, non potranno essere cancellate
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	// -------------------------------------CONTROLLO DIPENDENZE---------------------------------------------////
	//------------------------------------------------------------------------------------------------------/////
	// Controllo se la natura ha una dipendenza con un altra, nel caso non potrà essere eliminata.
	// Recupero tutte le nature endo tranne quella che stiamo eleminando
	List<Naturaendobase> naturaendos = this.findAllExcludeNatura(entity, OrderTypeEnum.ASC);
	//Stringa che conterrà  le nature con cui è compatitibile quella che vogliamo eliminare
	StringBuffer buffer = new StringBuffer();
	// Ciclo tutte le nature endo
	for (Naturaendobase naturaendo : naturaendos) {
	    // Per ognuna trovo quelle con cui è dipendente
	    List<Naturaendobase> list = this.getNatureendoByDipendenze(null, naturaendo, true);
	    // Ciclo la lista trovata
	    for (Naturaendobase naturaendo2 : list) {
		// Se è dipendente con quella che voglio eliminare esco da ciclo e l'aggiungo alla stringa che 
		//verrà mostrata in errore
		//if (naturaendo2.getTransietFlagBinariodipendenze().equals(true)) {
		if (naturaendo2.getId().equals(entity.getId()) && naturaendo2.getTransietFlagBinariodipendenze().equals(true)) {
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

    private void dataIntegration(Naturaendobase entity, boolean isUpdate) {

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
	    entity.setId(codiceNatura);
	} else// Aggiorno
	{
	    codiceNatura = entity.getId();
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
    }
}
