package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimenti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.TemplateMessaggioAppIo;

@Service
public class AppIoCodaMovimentiServiceImpl implements IAppIoCodaMovimentiService {

    private IAppIoCodaMovimentiDAO appIoCodaMovimentiDAO;

    @Autowired
    public void setAppIoCodaMovimentiDAO(IAppIoCodaMovimentiDAO appIoCodaMovimentiDAO) {

	this.appIoCodaMovimentiDAO = appIoCodaMovimentiDAO;
    }

    @Override
    public void insert(AppIoCodaMovimenti codaMovimenti) {

	this.appIoCodaMovimentiDAO.insert(codaMovimenti);
	this.appIoCodaMovimentiDAO.commitFlush();
    }

    @Override
    public List<AppIoCodaMovimenti> findByMovimento(Integer codiceMovimento) {

	return appIoCodaMovimentiDAO.findByMovimento(codiceMovimento);
    }

    @Override
    public TemplateMessaggioAppIo findByIdServizioMovimentoAndIntervento(String identServizio, String tipomovimento, Integer codiceIntervento) {

	return appIoCodaMovimentiDAO.findByIdServizioMovimentoAndIntervento(identServizio, tipomovimento, codiceIntervento);
    }

    @Override
    public TemplateMessaggioAppIo findByIdServizioMovimentoAndEndo(String identServizio, String tipomovimento, Integer codiceEndo) {

	return appIoCodaMovimentiDAO.findByIdServizioMovimentoAndEndo(identServizio, tipomovimento, codiceEndo);
    }
}
