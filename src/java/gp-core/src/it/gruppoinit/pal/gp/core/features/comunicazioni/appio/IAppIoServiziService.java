package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziId;

public interface IAppIoServiziService {

    public void insert(AppIoServizi appIoServizi);

    public AppIoServizi findById(AppIoServiziId id);

    public List<AppIoServizi> findAll(Integer firstResult, Integer maxResult);

    public void updateServizio(String desc, String precIdServizio, String newIdServizio);

    public void update(AppIoServizi appIoServizi, AppIoServiziId id);
}
