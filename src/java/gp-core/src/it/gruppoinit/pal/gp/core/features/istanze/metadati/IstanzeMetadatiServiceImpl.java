package it.gruppoinit.pal.gp.core.features.istanze.metadati;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.IstanzeMetadati;
import it.gruppoinit.pal.gp.core.domain.IstanzeMetadatiId;

@Service
public class IstanzeMetadatiServiceImpl implements IIstanzeMetadatiService {

    private IIstanzeMetadatiDAO istanzeMetadatiDAO;

    @Autowired
    public IstanzeMetadatiServiceImpl(IIstanzeMetadatiDAO istanzeMetadatiDAO) {

	this.istanzeMetadatiDAO = istanzeMetadatiDAO;
    }

    @Override
    public void deleteByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Codice istanza nullo");
	}
	istanzeMetadatiDAO.deleteByIstanza(codiceIstanza);
    }

    @Override
    public void aggiornaMetadatoStatoIstanza(Integer codiceIstanza) {

	IstanzeMetadatiId id = new IstanzeMetadatiId(codiceIstanza, "CAMBIO_STATO_NOTIFICATO");
	IstanzeMetadati metadato = this.istanzeMetadatiDAO.findById(id);
	metadato.setValore("N");
	this.istanzeMetadatiDAO.insertOrUpdate(metadato, id, true);
	this.istanzeMetadatiDAO.commitFlush();
    }

    @Override
    public void insert(IstanzeMetadati istanzeMetadati) {

	this.istanzeMetadatiDAO.insertOrUpdate(istanzeMetadati, istanzeMetadati.getId(), true);
	this.istanzeMetadatiDAO.commitFlush();
    }
}
