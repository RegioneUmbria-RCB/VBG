package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.RegIoAssegnazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegIoAssegnazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;
import it.gruppoinit.pal.gp.core.service.RegIoAssegnazioniService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegIoAssegnazioniServiceImpl extends BaseServiceImpl<RegIoAssegnazioni, PkId> implements RegIoAssegnazioniService {

    private RegIoAssegnazioniDAO regIoAssegnazioniDAO;

    @Autowired
    public void setRegIoAssegnazioniDAO(RegIoAssegnazioniDAO regIoAssegnazioniDAO) {

	this.regIoAssegnazioniDAO = regIoAssegnazioniDAO;
    }

    @Override
    protected Class<RegIoAssegnazioni> getEntityClass() {

	return RegIoAssegnazioni.class;
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_REGIOASSEGNAZIONI" })
    @Override
    public void delete(RegIoAssegnazioni entity) {

	regIoAssegnazioniDAO.delete(entity);
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_REGIOASSEGNAZIONI" })
    @Override
    public List<RegIoAssegnazioni> findAll(Integer firstResult, Integer maxResult) {

	return regIoAssegnazioniDAO.findAll(firstResult, maxResult);
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_REGIOASSEGNAZIONI" })
    @Override
    public RegIoAssegnazioni findById(PkId id) {

	return regIoAssegnazioniDAO.findById(id);
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_REGIOASSEGNAZIONI" })
    @Override
    public void insert(RegIoAssegnazioni entity) {

	if (validateEntity(entity)) {
	    regIoAssegnazioniDAO.insert(entity);
	}
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_REGIOASSEGNAZIONI" })
    @Override
    public void assegna(RegistrazioniInOut registrazioniInOut, Set<RegistrazioniImporti> registrazioniImportiList, BigDecimal importo) {

	// Set<RegIoAssegnazioni> regIoAssegnazioniList = registrazioniInOut.getRegIoAssegnazionis();
	// Riga modificata
	// BigDecimal tempTot = registrazioniInOut.getRimanenza();
	BigDecimal tempTot = importo;
	for (Iterator iterator = registrazioniImportiList.iterator(); iterator.hasNext();) {
	    RegistrazioniImporti registrazioniImporti = (RegistrazioniImporti) iterator.next();
	    // Verifico se ho scalato tutto l'importo del versamento
	    if (tempTot.equals(new BigDecimal(0)))
		break;
	    // Se la rata non � stata pagata
	    if (registrazioniImporti.getRimanenza().doubleValue() > 0) {
		// Caso in cui l'importo della rata � minore della quota di versamento a disposizione
		if (registrazioniImporti.getRimanenza().doubleValue() < tempTot.doubleValue()) {
		    RegIoAssegnazioni regIoAssegnazioni = new RegIoAssegnazioni();
		    regIoAssegnazioni.setImporto(registrazioniImporti.getRimanenza());
		    regIoAssegnazioni.setRegistrazioniImporti(registrazioniImporti);
		    regIoAssegnazioni.setRegistrazioniInOut(registrazioniInOut);
		    insert(regIoAssegnazioni);
		    tempTot = tempTot.subtract(registrazioniImporti.getRimanenza());
		} else {
		    RegIoAssegnazioni regIoAssegnazioni = new RegIoAssegnazioni();
		    regIoAssegnazioni.setImporto(tempTot);
		    regIoAssegnazioni.setRegistrazioniImporti(registrazioniImporti);
		    regIoAssegnazioni.setRegistrazioniInOut(registrazioniInOut);
		    insert(regIoAssegnazioni);
		    tempTot = new BigDecimal(0);
		}
	    }
	}
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_REGIOASSEGNAZIONI" })
    @Override
    public void resetAssegnazioni(Set<RegIoAssegnazioni> regIoAssegnazioniList) {

	for (Iterator iterator = regIoAssegnazioniList.iterator(); iterator.hasNext();) {
	    RegIoAssegnazioni regIoAssegnazioni = (RegIoAssegnazioni) iterator.next();
	    regIoAssegnazioniDAO.delete(regIoAssegnazioni);
	}
    }

    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_REGIOASSEGNAZIONI" })
    @Override
    public void update(RegIoAssegnazioni entity) {

	if (validateEntity(entity)) {
	    regIoAssegnazioniDAO.update(entity);
	}
    }

    @Override
    public void assegnaRigaImporto(RegistrazioniInOut registrazioniInOut, RegistrazioniImporti registrazioniImporti, BigDecimal incasso) {

	RegIoAssegnazioni regIoAssegnazioni = new RegIoAssegnazioni();
	regIoAssegnazioni.setImporto(incasso);
	regIoAssegnazioni.setRegistrazioniImporti(registrazioniImporti);
	regIoAssegnazioni.setRegistrazioniInOut(registrazioniInOut);
	insert(regIoAssegnazioni);
    }
}
