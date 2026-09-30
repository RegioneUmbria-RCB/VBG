package it.gruppoinit.pal.gp.core.nlastc;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.features.comuniassociati.ComuneAssociato;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.helper.ComuniDTO;
import it.gruppoinit.pal.gp.core.service.impl.NlaHelperServiceImpl;

public class NlaHelperFakeAdapter extends NlaHelperServiceImpl {

    public NlaHelperFakeAdapter() {

	setComuniassociatiService(getComuniassociatiForGetComune());
	setComuniService(getComuniServiceForGetComune());
    }

    private ComuniService getComuniServiceForGetComune() {

	return new ComuniService() {

	    @Override
	    public void update(Comuni entity) {

	    }

	    @Override
	    public String newIdFromSequencetable(Comuni entity) {

		return null;
	    }

	    @Override
	    public void insert(Comuni entity) {

	    }

	    @Override
	    public Comuni findById(String id) {

		return null;
	    }

	    @Override
	    public List<Comuni> findAll(Integer firstResult, Integer maxResult) {

		return null;
	    }

	    @Override
	    public void delete(Comuni entity) {

	    }

	    @Override
	    public Comuni bindDomainObject(Comuni entity, Class<?> idClass, String idPath) {

		return null;
	    }

	    @Override
	    public List<Comuni> findByDescrizione(String comune, boolean escludiStatiEsteri) {

		return null;
	    }

	    @Override
	    public List<Comuni> findByDescrizione(String comune) {

		return null;
	    }

	    @Override
	    public Comuni findByComune(Comuni comuni) {

		if (!comuni.getCodicecomune().equalsIgnoreCase("E256")) {
		    return null;
		}
		Comuni c = new Comuni();
		c.setCodicecomune("E256");
		c.setComune("Gubbio");
		return c;
	    }

	    @Override
	    public Comuni findByCodiceIstat(String comuneresidenza) {

		return null;
	    }

	    @Override
	    public Comuni findByCodiceComune(Comuni entity) {

		return null;
	    }

	    @Override
	    public ComuniDTO comuniToDTO(Comuni c) {

		return null;
	    }
	};
    }

    public ComuniassociatiService getComuniassociatiForGetComune() {

	return new ComuniassociatiService() {

	    @Override
	    public void update(Comuniassociati entity) {

	    }

	    @Override
	    public ComuniassociatiId newIdFromSequencetable(Comuniassociati entity) {

		return null;
	    }

	    @Override
	    public void insert(Comuniassociati entity) {

	    }

	    @Override
	    public Comuniassociati findById(ComuniassociatiId id) {

		if (!id.getCodicecomune().equalsIgnoreCase("E256")) {
		    return null;
		}
		Comuniassociati ret = new Comuniassociati(id);
		Comuni c = new Comuni();
		c.setCodicecomune("E256");
		c.setComune("Gubbio");
		ret.setComune(c);
		return ret;
	    }

	    @Override
	    public List<Comuniassociati> findAll(Integer firstResult, Integer maxResult) {

		return null;
	    }

	    @Override
	    public void delete(Comuniassociati entity) {

	    }

	    @Override
	    public Comuniassociati bindDomainObject(Comuniassociati entity, Class<?> idClass, String idPath) {

		return null;
	    }

	    @Override
	    public boolean isComuniassociati(String idcomune) {

		return true;
	    }

	    @Override
	    public List<Comuniassociati> findByIdcomune(String idcomune) {

		List<Comuniassociati> l = new ArrayList<Comuniassociati>();
		Comuniassociati ca = new Comuniassociati();
		Comuni c = new Comuni();
		ca.setComune(c);
		l.add(ca);
		return l;
	    }

	    @Override
	    public List<Responsabilicomuni> checkComuniAbilitatiPerResponsabile(boolean rilanciaEccezioneSeUtenteLoggatoNullo) {

		return null;
	    }

	    @Override
	    public List<Comuniassociati> findByComuniEsclusioni(String[] codicecomuni) {

		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public List<ComuneAssociato> findAll() {

		// TODO Auto-generated method stub
		return null;
	    }
	};
    }
}
