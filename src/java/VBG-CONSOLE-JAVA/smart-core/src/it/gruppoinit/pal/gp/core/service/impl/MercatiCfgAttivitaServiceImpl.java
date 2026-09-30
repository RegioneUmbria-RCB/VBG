/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiCfgAttivitaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivitaId;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class MercatiCfgAttivitaServiceImpl extends BaseServiceImpl<MercatiCfgAttivita, MercatiCfgAttivitaId> implements MercatiCfgAttivitaService {

    private MercatiCfgAttivitaDAO mercatiCfgAttivitaDAO;

    @Autowired
    public void setMercatiCfgAttivitaDAO(MercatiCfgAttivitaDAO mercatiCfgAttivitaDAO) {

	this.mercatiCfgAttivitaDAO = mercatiCfgAttivitaDAO;
    }

    @Override
    protected Class<MercatiCfgAttivita> getEntityClass() {

	return MercatiCfgAttivita.class;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_MERCATICFGATTIVITA" })
    public void delete(MercatiCfgAttivita entity) {

	mercatiCfgAttivitaDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_MERCATICFGATTIVITA" })
    public List<MercatiCfgAttivita> findAll(Integer firstResult, Integer maxResult) {

	return mercatiCfgAttivitaDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "", null);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_MERCATICFGATTIVITA" })
    public MercatiCfgAttivita findById(MercatiCfgAttivitaId id) {

	return mercatiCfgAttivitaDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_MERCATICFGATTIVITA" })
    public void insert(MercatiCfgAttivita entity) {

	if (validateEntity(entity)) {
	    mercatiCfgAttivitaDAO.insert(entity);
	}
    }

    /**
     * metodo per aggiornare l'oggetto MercatiCfgAttivita. poichè tale oggetto ha una chiave primaria che coincide con
     * la chiave esterna. quindi è necessario elimininare l'oggetto con la vecchia chiave esterna e inserire l'oggetto
     * con la nuova chiave esterna.
     * 
     * la chiava primaria coincide con la chiave esterna che collega la tabella MercatiCfgAttivita con la tabella
     * Attivita
     */
    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_MERCATICFGATTIVITA" })
    public void update(MercatiCfgAttivita entity) {

	if (validateEntity(entity)) {
	    Attivita attivita = entity.getAttivita();
	    String fkCodiceattivita = attivita.getId().getCodiceistat();
	    MercatiCfgAttivita mercatiCfgAttivita = new MercatiCfgAttivita();
	    MercatiCfgAttivitaId id = new MercatiCfgAttivitaId(fkCodiceattivita);
	    mercatiCfgAttivita.setAttivita(attivita);
	    mercatiCfgAttivita.setCoefficiente(entity.getCoefficiente());
	    mercatiCfgAttivita.setSoftware(entity.getSoftware());
	    mercatiCfgAttivita.setId(id);
	    // elimino il vecchio oggetto
	    delete(entity);
	    // inserisco il nuovo oggetto
	    insert(mercatiCfgAttivita);
	}
    }
}
