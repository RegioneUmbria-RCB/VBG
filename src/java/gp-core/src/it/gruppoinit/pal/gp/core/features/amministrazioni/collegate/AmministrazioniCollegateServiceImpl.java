package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniCollegate;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniCollegateId;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model.AmmCollComuneBean;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model.AmministrazioneCollegataBean;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model.QueryAmministrazioniFilter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Service
public class AmministrazioniCollegateServiceImpl implements AmministrazioniCollegateService {

    @Autowired
    private AmministrazioniCollegateDAO amministrazioniCollegateDAO;

    @Override
    public Amministrazioni findCollegataByAmministrazioneAndComune(Integer codiceAmministrazione, String codiceComune) {

	return amministrazioniCollegateDAO.findCollegataByAmministrazioneAndComune(codiceAmministrazione, codiceComune);
    }

    @Override
    public List<AmministrazioneCollegataBean> findByAmministrazione(Integer codiceAmministrazione) {

	return this.findByFilter(new QueryAmministrazioniFilter(codiceAmministrazione));
    }

    @Override
    public void salva(Integer codiceAmministrazione, Integer codicesottoamministrazione, String codiceComune) {

	AmministrazioniCollegate amm = new AmministrazioniCollegate();
	AmministrazioniCollegateId id = new AmministrazioniCollegateId(codiceAmministrazione, codiceComune);
	amm.setId(id);
	Amministrazioni ammCollegata = amministrazioniCollegateDAO.getById(Amministrazioni.class, codicesottoamministrazione);
	amm.setAmmCollegata(ammCollegata);
	amministrazioniCollegateDAO.saveEntity(amm);
    }

    @Override
    public void elimina(Integer codiceAmministrazione, Integer codicesottoamministrazione, String codiceComune) {

	amministrazioniCollegateDAO.delete(amministrazioniCollegateDAO.findById(new AmministrazioniCollegateId(codiceAmministrazione, codiceComune)));
    }

    @Override
    public List<AmmCollComuneBean> comuniDisponibili(Integer codiceAmministrazione) {

	return amministrazioniCollegateDAO.comuniDisponibili(codiceAmministrazione);
    }

    @Override
    public List<AmministrazioneCollegataBean> findByAmministrazioneCollegata(Integer codiceAmministrazione, Integer codiceAmministrazioneCollegata) {

	return this.findByFilter(new QueryAmministrazioniFilter(codiceAmministrazione, codiceAmministrazioneCollegata));
    }

    private List<AmministrazioniCollegate> findByQueryFilter(QueryAmministrazioniFilter filter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (filter.getCodiceAmministrazione() != null) {
	    fr.addFilterField(FilterUtils.equals("id.codiceamministrazione", filter.getCodiceAmministrazione(), Integer.class));
	}
	if (filter.getCodiceAmministrazioneCollegata() != null) {
	    fr.addFilterField(FilterUtils.equals("ammCollegataId", filter.getCodiceAmministrazioneCollegata(), Integer.class));
	}
	if (filter.getCodiceComune() != null) {
	    fr.addFilterField(FilterUtils.equals("id.codicecomune", filter.getCodiceComune(), String.class));
	}
	ft.addRestriction(fr);
	return amministrazioniCollegateDAO.findByFilterTable(ft);
    }

    private List<AmministrazioneCollegataBean> findByFilter(QueryAmministrazioniFilter filter) {

	List<AmministrazioniCollegate> list = findByQueryFilter(filter);
	List<AmministrazioneCollegataBean> ret = new ArrayList<AmministrazioneCollegataBean>();
	Map<Integer, AmministrazioneCollegataBean> mappaAmministrazioniComuni = new HashMap<Integer, AmministrazioneCollegataBean>();
	for (AmministrazioniCollegate amministrazioniCollegate : list) {
	    Amministrazioni ammCollegata = amministrazioniCollegate.getAmmCollegata();
	    if (ammCollegata == null) {
		continue;
	    }
	    Integer idAmministrazione = ammCollegata.getId().getCodice();
	    AmministrazioneCollegataBean a = mappaAmministrazioniComuni.get(idAmministrazione);
	    if (a == null) {
		a = new AmministrazioneCollegataBean(amministrazioniCollegate);
	    } else {
		a.getComuni().add(new AmmCollComuneBean(amministrazioniCollegate.getComune()));
	    }
	    mappaAmministrazioniComuni.put(idAmministrazione, a);
	}
	for (Entry<Integer, AmministrazioneCollegataBean> entry : mappaAmministrazioniComuni.entrySet()) {
	    ret.add(entry.getValue());
	}
	Collections.sort(ret);
	return ret;
    }

    @Override
    public List<Integer> findCodiciAmmCollegate(Integer idAmministrazione) {

	List<Integer> ret = new ArrayList<Integer>(0);
	List<AmministrazioniCollegate> list = this.findByQueryFilter(new QueryAmministrazioniFilter(idAmministrazione));
	for (AmministrazioniCollegate a : list) {
	    ret.add(a.getAmmCollegata().getId().getCodice());
	}
	return ret;
    }
}
