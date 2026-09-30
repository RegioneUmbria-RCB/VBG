package it.sgp.middleware.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sgp.middleware.security.dao.ComunisecurityTpartnerappDAO;
import it.sgp.middleware.security.domain.ComunisecurityTpartnerapp;
import it.sgp.middleware.security.service.ComunisecurityTpartnerappService;
import jakarta.validation.Valid;

@Service
@Transactional
public class ComunisecurityTpartnerappServiceImpl extends BaseServiceImpl<ComunisecurityTpartnerapp, Integer>
	implements ComunisecurityTpartnerappService {

    private ComunisecurityTpartnerappDAO comunisecurityTpartnerappDAO;

    @Autowired
    public void setComunisecurityTpartnerappDAO(ComunisecurityTpartnerappDAO comunisecurityTpartnerappDAO) {

	this.comunisecurityTpartnerappDAO = comunisecurityTpartnerappDAO;
    }

    @Override
    public void insert(@Valid ComunisecurityTpartnerapp entity) {

	if (validateEntity(entity)) {
	    // comunisecurityTpartnerappDAO.insert(entity);
	    comunisecurityTpartnerappDAO.saveAndFlush(entity);
	}
    }

    @Override
    public void update(ComunisecurityTpartnerapp entity) {

	if (validateEntity(entity)) {
	    // comunisecurityTpartnerappDAO.update(entity);
	    comunisecurityTpartnerappDAO.saveAndFlush(entity);
	}
    }

    @Override
    public void delete(ComunisecurityTpartnerapp entity) {

	if (isDeleteAllowed(entity)) {
	    comunisecurityTpartnerappDAO.delete(entity);
	}
    }

    @Override
    public List<ComunisecurityTpartnerapp> findAll() {

	throw new RuntimeException("Non Implementato");
    }

    @Override
    public List<ComunisecurityTpartnerapp> findAll(Integer firstResult, Integer maxResult) {

	throw new RuntimeException("Non Implementato");
    }

    @Override
    public ComunisecurityTpartnerapp findById(Integer id) {

	return comunisecurityTpartnerappDAO.findById(id).orElse(null);
    }

    @Override
    protected Class<ComunisecurityTpartnerapp> getEntityClass() {

	return ComunisecurityTpartnerapp.class;
    }

    @Override
    public List<ComunisecurityTpartnerapp> findByToken(String token) {

	ComunisecurityTpartnerapp c = new ComunisecurityTpartnerapp();
	c.setToken(token);
	Example<ComunisecurityTpartnerapp> filtro = Example.of(c);
	return comunisecurityTpartnerappDAO.findAll(filtro);
    }

    @Override
    public List<ComunisecurityTpartnerapp> findByTokenComuneESoftware(String token, String codicecomune, String software) {

	//	ComunisecurityTpartnerapp c = new ComunisecurityTpartnerapp();
	//	c.setToken(token);
	//	c.setSoftware(software);
	//	c.setCodicecomune(codicecomune);
	//	Example<ComunisecurityTpartnerapp> filtro = Example.of(c);
	//	return comunisecurityTpartnerappDAO.findAll(filtro);
	return comunisecurityTpartnerappDAO.findByTokenAndCodicecomuneAndSoftware(token, codicecomune, software);
    }

    @Override
    public String getTokenPartnerAppPerComuneESoftware(String token, String codicecomune, String software) {

	List<ComunisecurityTpartnerapp> ts = this.findByTokenComuneESoftware(token, codicecomune, software);
	if (ts != null) {
	    String ccomuneDef = StringUtils.defaultIfEmpty(codicecomune, "NLVCC0");
	    String softwareDef = StringUtils.defaultIfEmpty(software, "NLVSW0");
	    String keyrequest = ccomuneDef + "_" + softwareDef;
	    Map<String, String> resm = new HashMap<String, String>();
	    for (ComunisecurityTpartnerapp ct : ts) {
		String key = StringUtils.defaultIfEmpty(ct.getCodicecomune(), "NLVCC0") + "_" +
			     StringUtils.defaultIfEmpty(ct.getSoftware(), "NLVSW0");
		resm.put(key, ct.getTokenpartnerapp());
	    }
	    if (resm.get(keyrequest) != null) {
		return resm.get(keyrequest);
	    }
	}
	return null;
    }

    @Override
    public Page<ComunisecurityTpartnerapp> findAllByExamplePaginated(PageRequest pageable, Example<ComunisecurityTpartnerapp> example) {

	return comunisecurityTpartnerappDAO.findAll(example, pageable);
    }
}
