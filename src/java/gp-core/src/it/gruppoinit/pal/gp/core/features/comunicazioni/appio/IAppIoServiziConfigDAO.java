package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfig;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigId;
import it.gruppoinit.pal.gp.core.domain.Istanze;

public interface IAppIoServiziConfigDAO extends BaseDAO<AppIoServiziConfig, AppIoServiziConfigId> {

    public List<AppIoServiziConfig> findByIdServizio(String idServizio);

    public AppIoServiziConfig findByIdServizioEIstanza(String identServizio, Istanze istanza);

    public AppIoServiziConfigRestResponse findByIdServizioEComune(String idServizio, String comune);

    public List<AppIoServizi> findAllBySoftware(String idcomune, String codiceComune, String software);

    public Map<String, AppIoServiziConfigRestResponse> findByIdServizioAndComuni(String idServizio, Set<String> comuni);
}
