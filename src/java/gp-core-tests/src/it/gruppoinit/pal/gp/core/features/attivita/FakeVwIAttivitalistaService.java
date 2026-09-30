package it.gruppoinit.pal.gp.core.features.attivita;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitalista;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.VwIAttivitalistaService;

public class FakeVwIAttivitalistaService implements VwIAttivitalistaService {

    private List<Integer> lista;

    public FakeVwIAttivitalistaService(List<Integer> lista) {

	this.lista = lista;
    }

    @Override
    public void insert(VwIAttivitalista entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(VwIAttivitalista entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(VwIAttivitalista entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public VwIAttivitalista findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VwIAttivitalista bindDomainObject(VwIAttivitalista entity, Class<?> idClass, String idPath) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(VwIAttivitalista entity) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<VwIAttivitalista> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<VwIAttivitalista> findByFilterTable(FilterTable filterTable) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<VwIAttivitalista> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Integer> findBydenominazione(String denominazione, String[] software, boolean checkAttiva, Integer firstResult, Integer maxResult) {

	return this.lista;
    }

    @Override
    public List<Integer> findByLocalizzazioni(Set<Istanzestradario> localizzazioni, String[] software, boolean checkAttiva, Integer firstResult,
	    Integer maxResult) {

	return this.lista;
    }
}
