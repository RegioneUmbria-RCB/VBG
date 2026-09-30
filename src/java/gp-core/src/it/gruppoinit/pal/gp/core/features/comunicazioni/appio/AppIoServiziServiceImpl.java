package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziId;

@Service
public class AppIoServiziServiceImpl implements IAppIoServiziService {

    @Autowired
    private IAppIoServiziDAO appIoServiziDAO;

    @Override
    public void insert(AppIoServizi appIoServizi) {

	appIoServiziDAO.insert(appIoServizi);
    }

    @Override
    public List<AppIoServizi> findAll(Integer firstResult, Integer maxResult) {

	return this.appIoServiziDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AppIoServizi findById(AppIoServiziId id) {

	return (AppIoServizi) appIoServiziDAO.findById(id);
    }

    @Override
    public void updateServizio(String desc, String precIdServizio, String newIdServizio) {

	appIoServiziDAO.updateServizio(desc, precIdServizio, newIdServizio);
    }

    @Override
    public void update(AppIoServizi appIoServizi, AppIoServiziId id) {

	appIoServiziDAO.insertOrUpdate(appIoServizi, id, true);
    }
}
