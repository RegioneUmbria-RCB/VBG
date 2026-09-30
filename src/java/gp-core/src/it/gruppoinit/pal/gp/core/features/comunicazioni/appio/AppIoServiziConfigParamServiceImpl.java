package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParam;

@Service
public class AppIoServiziConfigParamServiceImpl implements IAppIoServiziConfigParamService {

    @Autowired
    IAppIoServiziConfigParamDAO appIoServiziConfigParamDAO;

    @Override
    public void insert(AppIoServiziConfigParam entity) {

	appIoServiziConfigParamDAO.insert(entity);
    }

    @Override
    public void delete(AppIoServiziConfigParam entity) {

	appIoServiziConfigParamDAO.delete(entity);
    }

    @Override
    public List<AppIoServiziConfigParam> findByIdServizio(String idServizio) {

	return appIoServiziConfigParamDAO.findByIdServizio(idServizio);
    }

    @Override
    public List<AppIoServiziConfigParam> findByIdServizioEComune(String idServizio, String codComune) {

	return this.appIoServiziConfigParamDAO.findByIdServizioEComune(idServizio, codComune);
    }
}
