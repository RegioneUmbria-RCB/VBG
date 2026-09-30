package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.NotificheAuslDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.NotificheAusl;
import it.gruppoinit.pal.gp.core.domain.NotificheAuslId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.NotificheAuslService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificheAuslServiceImpl extends BaseServiceImpl<NotificheAusl, NotificheAuslId> implements NotificheAuslService {

    private NotificheAuslDAO notificheAuslDAO;

    @Autowired
    public void setNotificheAuslDAO(NotificheAuslDAO notificheAuslDAO) {

	this.notificheAuslDAO = notificheAuslDAO;
    }

    @Override
    protected Class<NotificheAusl> getEntityClass() {

	return NotificheAusl.class;
    }

    @Override
    public void delete(NotificheAusl entity) {

	notificheAuslDAO.delete(entity);
    }

    @Override
    public List<NotificheAusl> findAll(Integer firstResult, Integer maxResult) {

	return notificheAuslDAO.findAll(null, null);
    }

    @Override
    public NotificheAusl findById(NotificheAuslId id) {

	return notificheAuslDAO.findById(id);
    }

    @Override
    public void insert(NotificheAusl entity) {

	if (validateEntity(entity)) {
	    notificheAuslDAO.insert(entity);
	}
    }

    @Override
    public void update(NotificheAusl entity) {

	if (validateEntity(entity)) {
	    notificheAuslDAO.update(entity);
	}
    }

    @Override
    public void updateImportNotifiche(List<NotificheAusl> list, String updateDati) {

	// §§§BEGIN§§§
	if (updateDati.equals("true")) {
	    for (NotificheAusl notificheAusl : list) {
		this.insert(notificheAusl);
	    }
	} else {
	    for (NotificheAusl notificheAusl : list) {
		NotificheAuslId id = notificheAusl.getId();
		NotificheAusl temp = this.findById(id);
		if (temp == null) {
		    notificheAuslDAO.insert(notificheAusl);
		}
	    }
	}
	// §§§END§§§
    }

    @Override
    public boolean existsRecords() {

	return notificheAuslDAO.existsRecords(new FilterTable(DAOEnum.FIND_BY_IDCOMUNE));
    }
}
