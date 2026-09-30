package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanze;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanzeId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class RicalcoloAreeIstanzeServiceImpl extends BaseServiceImpl<RicalcoloAreeIstanze, RicalcoloAreeIstanzeId>
	implements RicalcoloAreeIstanzeService {

    @Autowired
    RicalcoloAreeIstanzeDAO ricalcoloAreeIstanzeDAO;

    @Override
    public void insert(RicalcoloAreeIstanze entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(RicalcoloAreeIstanze entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(RicalcoloAreeIstanze entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<RicalcoloAreeIstanze> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public RicalcoloAreeIstanze findById(RicalcoloAreeIstanzeId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected Class<RicalcoloAreeIstanze> getEntityClass() {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<String> getTestateDaRicalcolare() {

	return ricalcoloAreeIstanzeDAO.getTestateDaRicalcolare();
    }
}
