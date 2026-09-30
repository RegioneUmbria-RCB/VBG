package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParam;

public interface IAppIoServiziConfigParamService {

    public void insert(AppIoServiziConfigParam entity);

    public void delete(AppIoServiziConfigParam entity);

    public List<AppIoServiziConfigParam> findByIdServizio(String idServizio);

    List<AppIoServiziConfigParam> findByIdServizioEComune(String idServizio, String codComune);
}
