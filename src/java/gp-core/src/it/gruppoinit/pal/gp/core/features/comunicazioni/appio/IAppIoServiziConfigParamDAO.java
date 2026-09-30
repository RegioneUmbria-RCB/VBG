package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParam;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParamId;

public interface IAppIoServiziConfigParamDAO extends BaseDAO<AppIoServiziConfigParam, AppIoServiziConfigParamId> {

    public List<AppIoServiziConfigParam> findByIdServizio(String idServizio);

    public List<AppIoServiziConfigParam> findByIdServizioEComune(String idServizio, String codComune);
}
