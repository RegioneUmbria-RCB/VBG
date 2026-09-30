package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfig;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigId;
import it.gruppoinit.pal.gp.core.domain.Istanze;

@Service
public class AppIoServiziConfigServiceImpl implements IAppIoServiziConfigService {

    @Autowired
    IAppIoServiziConfigDAO appIoServiziConfigDAO;

    @SuppressWarnings("unchecked")
    @Override
    public void insert(AppIoServiziConfig entity, AppIoServiziConfigId id, boolean isUpdate) {

	appIoServiziConfigDAO.insertOrUpdate(entity, id, isUpdate);
    }

    @Override
    public List<AppIoServiziConfig> findByIdServizio(String idServizio) {

	return appIoServiziConfigDAO.findByIdServizio(idServizio);
    }

    @Override
    public AppIoServiziConfig findByIdServizioEIstanza(String identServizio, Istanze istanza) {

	return this.appIoServiziConfigDAO.findByIdServizioEIstanza(identServizio, istanza);
    }

    @Override
    public AppIoServiziConfigRestResponse findByIdServizioEComune(String idServizio, String comune) {

	return this.appIoServiziConfigDAO.findByIdServizioEComune(idServizio, comune);
    }

    @Override
    public List<AppIoServizi> findAllBySoftware(String idcomune, String codiceComune, String software) {

	return appIoServiziConfigDAO.findAllBySoftware(idcomune, codiceComune, software);
    }

    @Override
    public Map<String, AppIoServiziConfigRestResponse> findByIdServizioAndComuni(String idServizio, Set<String> comuni) {

	return appIoServiziConfigDAO.findByIdServizioAndComuni(idServizio, comuni);
    }
}
