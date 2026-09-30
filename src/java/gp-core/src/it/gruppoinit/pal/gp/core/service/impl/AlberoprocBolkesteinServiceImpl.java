package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocBolkesteinDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocBolkestein;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocBolkesteinCommand;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocBolkesteinHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocBolkesteinService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoprocBolkesteinServiceImpl extends BaseServiceImpl<AlberoprocBolkestein, PkId> implements AlberoprocBolkesteinService {

    private AlberoprocBolkesteinDAO alberoprocbolkesteinDAO;
    private MercatiDService mercatiDService;
    private MercatiService mercatiService;
    private AlberoprocService alberoprocService;
    private MercatiUsoService mercatiUsoService;

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setAlberoprocBolkesteinDAO(AlberoprocBolkesteinDAO alberoprocbolkesteinDAO) {

	this.alberoprocbolkesteinDAO = alberoprocbolkesteinDAO;
    }

    @Override
    protected Class<AlberoprocBolkestein> getEntityClass() {

	return AlberoprocBolkestein.class;
    }

    @Override
    public List<AlberoprocBolkestein> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocbolkesteinDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocBolkestein entity) {

	if (validateEntity(entity)) {
	    alberoprocbolkesteinDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocBolkestein findById(PkId id) {

	return alberoprocbolkesteinDAO.findById(id);
    }

    @Override
    public void update(AlberoprocBolkestein entity) {

	if (validateEntity(entity)) {
	    alberoprocbolkesteinDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocBolkestein entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocbolkesteinDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(AlberoprocBolkestein entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<AlberoprocBolkestein> findByAlberoProc(Integer codice) {

	return alberoprocbolkesteinDAO.findByAlberoProc(codice);
    }

    @Override
    public AlberoprocBolkestein findByAlberoProcAndMercatoAndUsoAndPosteggio(Integer codiceAlberoproc, Integer codiceMercato, Integer codiceUso,
	    Integer idPosteggio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("alberoprocId", codiceAlberoproc, Integer.class));
	r.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	r.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	r.addFilterField(FilterUtils.equals("mercatiDId", idPosteggio, Integer.class));
	ft.addRestriction(r);
	List<AlberoprocBolkestein> list = alberoprocbolkesteinDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<AlberoprocBolkestein> findByAlberoProcAndMercatoAndUso(Integer codiceAlberoproc, Integer codiceMercato, Integer codiceUso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("alberoprocId", codiceAlberoproc, Integer.class));
	r.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	r.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	ft.addRestriction(r);
	return alberoprocbolkesteinDAO.findByFilterTable(ft);
    }

    @Override
    public void populateCommand(AlberoprocBolkesteinCommand command) {

	List<MercatiD> mds = mercatiDService.findByMercato(command.getEntity(), PosteggiEnum.ACTIVE);
	List<AlberoprocBolkesteinHelper> hlps = new ArrayList<AlberoprocBolkesteinHelper>();
	for (MercatiD mercatiD : mds) {
	    AlberoprocBolkesteinHelper h = new AlberoprocBolkesteinHelper();
	    h.setIdposteggio(mercatiD.getId().getCodice());
	    h.setCodicePosteggio(mercatiD.getCodiceposteggio());
	    AlberoprocBolkestein alberoprocBolkestein = findByAlberoProcAndMercatoAndUsoAndPosteggio(command.getCodiceAlberoproc(), command
		    .getEntity().getId().getCodice(), command.getMercatiUso().getId().getCodice(), mercatiD.getId().getCodice());
	    if (alberoprocBolkestein != null) {
		h.setSelezionato(true);
		h.setCodiceAlberoprocbolkestein(alberoprocBolkestein.getId().getCodice());
	    }
	    hlps.add(h);
	}
	command.setPosteggis(hlps);
    }

    @Override
    public void deleteByAlberoProcAndMercatoAndUso(Integer codiceAlberoproc, Integer codiceMercato, Integer codiceUso) {

	List<AlberoprocBolkestein> list = findByAlberoProcAndMercatoAndUso(codiceAlberoproc, codiceMercato, codiceUso);
	for (AlberoprocBolkestein alberoprocBolkestein : list) {
	    delete(alberoprocBolkestein);
	}
    }

    @Override
    public void insertByAlberoProcAndMercatoAndUso(Integer codiceAlberoproc, Integer codiceMercato, Integer codiceUso) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	List<MercatiD> mds = mercatiDService.findByMercato(mercato, PosteggiEnum.ACTIVE);
	List<AlberoprocBolkestein> listapb = findByAlberoProcAndMercatoAndUso(codiceAlberoproc, codiceMercato, codiceUso);
	for (MercatiD mercatiD : mds) {
	    Boolean presente = false;
	    for (AlberoprocBolkestein alberoprocBolkestein : listapb) {
		if (mercatiD.getCodiceposteggio().equals(alberoprocBolkestein.getMercatiD().getCodiceposteggio())) {
		    presente = true;
		}
	    }
	    if (BooleanUtils.isFalse(presente)) {
		AlberoprocBolkestein apb = new AlberoprocBolkestein();
		apb.setAlberoproc(alberoproc);
		apb.setMercati(mercato);
		apb.setMercatiUso(uso);
		apb.setMercatiD(mercatiD);
		insert(apb);
	    }
	}
    }
}
