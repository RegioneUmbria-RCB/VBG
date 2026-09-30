package it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocMovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class AlberoprocMovimentiServiceImpl extends BaseServiceImpl<AlberoprocMovimenti, PkId> implements AlberoprocMovimentiService {

    private AlberoprocService alberoprocService;
    private AlberoprocMovimentiDAO alberoprocMovimentiDAO;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocMovimentiDAO(AlberoprocMovimentiDAO alberoprocMovimentiDAO) {

	this.alberoprocMovimentiDAO = alberoprocMovimentiDAO;
    }

    @Override
    public void insert(AlberoprocMovimenti entity) {

	if (validateEntity(entity)) {
	    this.alberoprocMovimentiDAO.insert(entity);
	}
    }

    @Override
    public void update(AlberoprocMovimenti entity) {

	if (validateEntity(entity)) {
	    this.alberoprocMovimentiDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocMovimenti entity) {

	if (isDeleteAllowed(entity)) {
	    this.alberoprocMovimentiDAO.delete(entity);
	}
    }

    @Override
    public List<AlberoprocMovimenti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public AlberoprocMovimenti findById(PkId id) {

	return this.alberoprocMovimentiDAO.findById(id);
    }

    @Override
    protected Class<AlberoprocMovimenti> getEntityClass() {

	return AlberoprocMovimenti.class;
    }

    @Override
    public List<AlberoprocMovimenti> findByAlberoprocId(Integer codiceAlberoproc) {

	return this.alberoprocMovimentiDAO.findByAlberoproc(codiceAlberoproc);
    }

    @Override
    public List<AlberoprocMovimenti> findConfigurazioneAttivaByAlberoprocId(Integer codiceAlberoproc) {

	List<AlberoprocMovimenti> ret = this.findByAlberoprocId(codiceAlberoproc);
	if (ret.isEmpty()) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	    String scCodice = alberoproc.getScCodice();
	    int lengthCodice = scCodice.length();
	    int lengthTree = lengthCodice / 2;
	    for (int i = 0; i < lengthTree - 1; i++) {
		lengthCodice = lengthCodice - 2;
		String sccodicePadre = scCodice.substring(0, lengthCodice);
		Alberoproc alberoprocPadre = alberoprocService.findByScCodice(sccodicePadre);
		if (alberoprocPadre == null) {
		    break;
		}
		ret = this.findByAlberoprocId(alberoprocPadre.getId().getCodice());
		if (!ret.isEmpty()) {
		    return ret;
		}
	    }
	}
	return ret;
    }
}
