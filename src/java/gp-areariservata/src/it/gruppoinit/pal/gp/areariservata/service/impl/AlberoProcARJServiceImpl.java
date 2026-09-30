package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.dao.AlberoProcARJDAO;
import it.gruppoinit.pal.gp.areariservata.service.AlberoProcARJService;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoProcARJServiceImpl extends BaseServiceImpl<Alberoproc, PkId> implements AlberoProcARJService {

    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AlberoProcARJDAO alberoProcARJDAO;
    @Autowired
    private AlberoprocDAO alberoProcDAO;
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;

    @Override
    public Alberoproc findById(PkId id) {

	return alberoProcDAO.findById(id);
    }

    @Override
    public List<Alberoproc> findSubTree(String scCodice) {

	return alberoProcARJDAO.findSubTree(scCodice);
    }

    @Override
    public boolean hasSubTree(String scCodice) {

	return alberoProcARJDAO.hasSubTree(scCodice);
    }

    @Override
    public List<AlberoprocEndo> findListaEndoPubblicati(Alberoproc alberoproc) {

	List<AlberoprocEndo> list = new ArrayList<AlberoprocEndo>();
	String scCodice = alberoproc.getScCodice();
	int lengthCodice = scCodice.length();
	int lengthTree = scCodice.length() / 2;
	for (int i = 0; i < lengthTree; i++) {
	    Alberoproc alberoprocTemp = alberoProcDAO.findByScCodice(scCodice);
	    list.addAll(alberoprocEndoService.findAllByAlberoprocAndFlagPubblica(alberoprocTemp.getId().getCodice()));
	    scCodice = scCodice.substring(0, lengthCodice - 2);
	}
	return list;
    }

    @Override
    public void insert(Alberoproc arg0) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Alberoproc arg0) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Alberoproc arg0) {

	throw new NotImplementedException();
    }

    @Override
    public List<Alberoproc> findAll(Integer arg0, Integer arg1) {

	throw new NotImplementedException();
    }

    @Override
    protected Class<Alberoproc> getEntityClass() {

	return Alberoproc.class;
    }
}
