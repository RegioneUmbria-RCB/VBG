package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentiAltriHelper;
import it.gruppoinit.pal.gp.areariservata.service.AlberoProcARJService;
import it.gruppoinit.pal.gp.areariservata.service.AlberoprocArendoARJService;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocArendoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoprocArendoARJServiceImpl implements AlberoprocArendoARJService {

    @Autowired
    private AlberoprocArendoService alberoprocArendoService;
    @Autowired
    private AlberoProcARJService alberoProcARJService;
    @Autowired
    private AlberoprocDAO alberoprocDAO;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;

    @Override
    public List<AlberoprocArendo> findByAlberoproc(Integer codiceIntervento) {

	List<AlberoprocArendo> list = alberoprocArendoService.findByAlberoProc(codiceIntervento, ORMHelper.getIdcomune(), ORMHelper.getIdcomune());
	if (list.isEmpty()) {
	    Alberoproc alberoproc = alberoProcARJService.findById(new PkId(codiceIntervento));
	    String scCodice = alberoproc.getScCodice();
	    int lengthCodice = scCodice.length();
	    int lengthTree = scCodice.length() / 2;
	    scCodice = scCodice.substring(0, lengthCodice - 2);
	    for (int i = 0; i < lengthTree; i++) {
		if (StringUtils.isBlank(scCodice)) {
		    break;
		}
		Alberoproc alberoprocTemp = alberoprocDAO.findByScCodice(ORMHelper.getIdcomune(), scCodice);
		list = alberoprocArendoService.findByAlberoProc(alberoprocTemp.getId().getCodice(), ORMHelper.getIdcomune(), ORMHelper.getIdcomune());
		if (!list.isEmpty()) {
		    break;
		}
		scCodice = scCodice.substring(0, lengthCodice - 2);
	    }
	}
	return list;
    }

    @Override
    public ProcedimentiAltriHelper createProcedimentiAltriHelper(Integer codiceIntervento) {

	List<Inventarioprocedimenti> list = new ArrayList<Inventarioprocedimenti>();
	List<AlberoprocArendo> listArendo = this.findByAlberoproc(codiceIntervento);
	for (AlberoprocArendo alberoprocArendo : listArendo) {
	    list.addAll(inventarioprocedimentiService.findByAlberoprocArendo(alberoprocArendo));
	}
	ProcedimentiAltriHelper procedimentiAltriHelper = new ProcedimentiAltriHelper(list);
	procedimentiAltriHelper.elabora();
	return procedimentiAltriHelper;
    }
}
