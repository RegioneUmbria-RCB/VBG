/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.GraduatoriedDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Campigraduatoria;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CampigraduatoriaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2CampiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.Istanzedyn2datiDTO;
import it.gruppoinit.pal.gp.core.domain.web.GraduatoriedFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.service.CampigraduatoriaService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class GraduatoriedServiceImpl extends BaseServiceImpl<Graduatoried, PkId> implements GraduatoriedService {

    private static final Logger log = LoggerFactory.getLogger(GraduatoriedServiceImpl.class);
    private GraduatoriedDAO graduatoriedDAO;
    private CampigraduatoriaService campigraduatoriaService;
    private Istanzedyn2datiService istanzedyn2datiService;

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setCampigraduatoriaService(CampigraduatoriaService campigraduatoriaService) {

	this.campigraduatoriaService = campigraduatoriaService;
    }

    @Autowired
    public void setGraduatoriedDAO(GraduatoriedDAO graduatoriedDAO) {

	this.graduatoriedDAO = graduatoriedDAO;
    }

    @Override
    protected Class<Graduatoried> getEntityClass() {

	return Graduatoried.class;
    }

    @Override
    public void delete(Graduatoried entity) {

	graduatoriedDAO.delete(entity);
    }

    @Override
    public List<Graduatoried> findAll(Integer firstResult, Integer maxResult) {

	return graduatoriedDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Graduatoried findById(PkId id) {

	return graduatoriedDAO.findById(id);
    }

    @Override
    public void insert(Graduatoried entity) {

	if (validateEntity(entity)) {
	    graduatoriedDAO.insert(entity);
	}
    }

    @Override
    public void update(Graduatoried entity) {

	if (validateEntity(entity)) {
	    graduatoriedDAO.update(entity);
	}
    }

    @Override
    public List<Istanzedyn2dati> findBandoOutput(Graduatoried graduatoried) {

	return graduatoriedDAO.findBandoOutput(graduatoried);
    }

    @Override
    public List<GraduatoriedDTO> findByGraduatoriet(Graduatoriet graduatoriet) {

	return graduatoriedDAO.findByGraduatoriet(graduatoriet);
    }

    @Override
    public Set<GraduatoriedDTO> findGraduatoriedPerComunuicazione(Integer codicegraduatoriet, Integer posizioneDa, Integer posizioneA,
	    String destinatari, SchedaDinamicaFilter dinamicaFilter) {

	log.debug("findGraduatoriedPerComunuicazione# Ricerco lista graduatorie dettaglio (graduatoried) per i filtri impostati nella testata Graduatoriet_com  ");
	return graduatoriedDAO.findGraduatoriedPerComunuicazione(codicegraduatoriet, posizioneDa, posizioneA, destinatari, dinamicaFilter);
    }

    public int findExsistGraduatoridFilterByDynDatiIstanza(GraduatoriedFilter filter) {

	return graduatoriedDAO.findExsistGraduatoridFilterByDynDatiIstanza(filter);
    }

    @Override
    public List<GraduatoriedDTO> findByGraduatoriet(Graduatoriet graduatoriet, DAOOrderTypeEnum daoOrderTypeEnum, Integer firstResult,
	    Integer maxResult) {

	return graduatoriedDAO.findByGraduatoriet(graduatoriet, daoOrderTypeEnum, firstResult, maxResult);
    }

    @Override
    public GraduatoriedDTO populateListaCampigraduatoria(GraduatoriedDTO graduatoried, IstanzeDTO istanza) {

	List<Campigraduatoria> list = campigraduatoriaService.findByGraduatorieDAndOrderByOrdine(graduatoried.getId().getCodice());
	List<CampigraduatoriaDTO> listCampi = new ArrayList<CampigraduatoriaDTO>();
	CampigraduatoriaDTO campigraduatoriaDTO = null;
	for (Campigraduatoria campigraduatoria : list) {
	    campigraduatoriaDTO = new CampigraduatoriaDTO();
	    Dyn2CampiDTO dyn2Campi = new Dyn2CampiDTO();
	    campigraduatoriaDTO.setDyn2Campi(dyn2Campi);
	    campigraduatoriaDTO.getDyn2Campi().setEtichetta(campigraduatoria.getDyn2Campi().getEtichetta());
	    campigraduatoriaDTO.getDyn2Campi().setTipodato(campigraduatoria.getDyn2Campi().getTipodato());
	    List<Istanzedyn2datiDTO> listIstanzeDati = istanzedyn2datiService.findDTOByIstanzaAndDyn2Campi(istanza.getId().getCodice(),
		    campigraduatoria.getDyn2Campi().getId().getCodice(), 0, 0);
	    if (!listIstanzeDati.isEmpty()) {
		campigraduatoriaDTO.setValore(listIstanzeDati.get(0).getValoredecodificato());
	    }
	    listCampi.add(campigraduatoriaDTO);
	}
	graduatoried.setCampigraduatorias(listCampi);
	return graduatoried;
    }
}
