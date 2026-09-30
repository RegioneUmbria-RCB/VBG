package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziId;

public interface IAppIoServiziDAO extends BaseDAO<AppIoServizi, AppIoServiziId> {

    public void insert(AppIoServizi entity);

    public List<AppIoServizi> findAll(Integer firstResult, Integer maxResult);

    public void updateServizio(String desc, String precIdServizio, String newIdServizio);
}
