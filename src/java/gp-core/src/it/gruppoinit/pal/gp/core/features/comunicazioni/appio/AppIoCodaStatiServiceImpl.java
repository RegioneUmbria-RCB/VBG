package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AppIoCodaStati;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStatiId;

@Service
public class AppIoCodaStatiServiceImpl implements IAppIoCodaStatiService {

    private IAppIoCodaStatiDAO appIoCodaStatiDAO;

    @Autowired
    public void setAppIoCodaStatiDAO(IAppIoCodaStatiDAO appIoCodaStatiDAO) {

	this.appIoCodaStatiDAO = appIoCodaStatiDAO;
    }

    @Override
    public void insert(AppIoCodaStati codaStati) {

	this.appIoCodaStatiDAO.insert(codaStati);
	this.appIoCodaStatiDAO.commitFlush();
    }

    @Override
    public AppIoCodaStati findById(AppIoCodaStatiId entity) {

	return this.appIoCodaStatiDAO.findById(entity);
    }

    @Override
    public List<AppIoCodaStati> findByGuid(String guid) {

	return appIoCodaStatiDAO.findByGuid(guid);
    }
}
