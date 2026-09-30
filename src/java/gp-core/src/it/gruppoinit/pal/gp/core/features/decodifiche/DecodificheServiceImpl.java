package it.gruppoinit.pal.gp.core.features.decodifiche;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Decodifiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.decodifiche.dao.DecodificheDAO;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class DecodificheServiceImpl extends BaseServiceImpl<Decodifiche, PkId> implements DecodificheService {

    @Autowired
    private DecodificheDAO decodificheDAO;

    @Override
    public void insert(Decodifiche entity) {

	if (validateEntity(entity)) {
	    decodificheDAO.insert(entity);
	}
    }

    @Override
    public void update(Decodifiche entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(Decodifiche entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Decodifiche> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected Class<Decodifiche> getEntityClass() {

	return Decodifiche.class;
    }

    @Override
    public ListaDecodifiche findByTabella(String tabella) {

	ListaDecodifiche listaDecodifiche = new ListaDecodifiche();
	if (StringUtils.isNotEmpty(tabella)) {
	    listaDecodifiche.setDecodificheList(decodificheDAO.findByTabella(tabella));
	}
	return listaDecodifiche;
    }

    @Override
    public List<String> findDistinctTabelle() {

	return decodificheDAO.findDistinctTabelle();
    }

    @Override
    public Decodifiche findById(PkId id) {

	return decodificheDAO.findById(id);
    }

    @Override
    public ListaDecodifiche findByTabellaAndRaggruppamento(String tabella, String raggruppamento) {

	ListaDecodifiche listaDecodifiche = new ListaDecodifiche();
	if (StringUtils.isNotEmpty(tabella)) {
	    listaDecodifiche.setDecodificheList(decodificheDAO.findByTabellaAndRaggruppamento(tabella, raggruppamento));
	}
	return listaDecodifiche;
    }
}
