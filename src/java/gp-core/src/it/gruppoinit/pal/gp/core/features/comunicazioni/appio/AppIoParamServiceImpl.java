package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AppIoParam;

@Service
public class AppIoParamServiceImpl implements IAppIoParamService {

    @Autowired
    IAppIoParamDAO appIoParamDAO;

    @Override
    public void insert(AppIoParam entity) {

	appIoParamDAO.insert(entity);
    }

    @Override
    public void delete(AppIoParam entity) {

	appIoParamDAO.delete(entity);
    }
}
