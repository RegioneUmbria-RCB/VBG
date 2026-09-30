package it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;

@Service
public class AutorizzazioniMetadatiServiceImpl implements AutorizzazioniMetadatiService {

    private AutorizzazioniMetadatiDAO autorizzazioniMetadatiDAO;

    @Autowired
    public void setAutorizzazioniMetadatiDAO(AutorizzazioniMetadatiDAO autorizzazioniMetadatiDAO) {

	this.autorizzazioniMetadatiDAO = autorizzazioniMetadatiDAO;
    }

    @Override
    public void insertMetadati(List<IAutorizzazioneMetadato> metadati) {

	if (metadati == null) {
	    return;
	}
	for (IAutorizzazioneMetadato metadato : metadati) {
	    AutorizzazioniMetadati autMetadato = metadato.toAutorizzazioniMetadati();
	    if (StringUtils.isNotBlank(autMetadato.getValore())) {
		this.autorizzazioniMetadatiDAO.insert(autMetadato);
		this.autorizzazioniMetadatiDAO.flush();
		this.autorizzazioniMetadatiDAO.commit();
		this.autorizzazioniMetadatiDAO.flush();
	    }
	}
    }

    @Override
    public void deleteByIdAutorizzazione(Integer codice) {

	this.autorizzazioniMetadatiDAO.deleteByIdAutorizzazioni(codice);
    }

    @Override
    public void insert(AutorizzazioniMetadati metadato) {

	this.autorizzazioniMetadatiDAO.insert(metadato);
    }
}
