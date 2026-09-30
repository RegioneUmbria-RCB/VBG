package it.gruppoinit.pal.gp.core.documentihelper;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;

import java.util.List;

public class ResponsabilisoftwareServiceFake implements ResponsabilisoftwareService {

    @Override
    public void insert(Responsabilisoftware entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(Responsabilisoftware entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(Responsabilisoftware entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Responsabilisoftware> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Responsabilisoftware findById(ResponsabilisoftwareId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Responsabilisoftware bindDomainObject(Responsabilisoftware entity, Class<?> idClass, String idPath) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ResponsabilisoftwareId newIdFromSequencetable(Responsabilisoftware entity) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteByResponsabile(Responsabili entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Responsabilisoftware> findByResponsabile(Responsabili responsabili) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Responsabilisoftware> findBySoftware(Responsabili responsabili, Software software) {

	// TODO Auto-generated method stub
	return null;
    }

    public boolean checkByResponsabileAndSoftwareResult;

    @Override
    public boolean checkByResponsabileAndSoftware(Integer codicecresponsabile, String software) {

	// TODO Auto-generated method stub
	return checkByResponsabileAndSoftwareResult;
    }
}
