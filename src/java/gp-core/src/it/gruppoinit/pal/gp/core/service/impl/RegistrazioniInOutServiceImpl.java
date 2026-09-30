package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniInOutDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegIoAssegnazioni;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.helper.RataHelper;
import it.gruppoinit.pal.gp.core.domain.web.RegistrazioniInOutCommand;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.RegIoAssegnazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniInOutService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrazioniInOutServiceImpl extends BaseServiceImpl<RegistrazioniInOut, PkId> implements RegistrazioniInOutService {

    private RegistrazioniInOutDAO registrazioniInOutDAO;
    private RegistrazioniService registrazioniService;
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    private RegistrazioniImportiService registrazioniImportiService;

    @Autowired
    public void setRegistrazioniInOutDAO(RegistrazioniInOutDAO registrazioniInOutDAO) {

	this.registrazioniInOutDAO = registrazioniInOutDAO;
    }

    @Autowired
    public void setRegistrazioniImportiService(RegistrazioniImportiService registrazioniImportiService) {

	this.registrazioniImportiService = registrazioniImportiService;
    }

    @Autowired
    public void setRegistrazioniService(RegistrazioniService registrazioniService) {

	this.registrazioniService = registrazioniService;
    }

    @Autowired
    public void setTipimodalitapagamentoService(TipimodalitapagamentoService tipimodalitapagamentoService) {

	this.tipimodalitapagamentoService = tipimodalitapagamentoService;
    }

    private RegIoAssegnazioniService regIoAssegnazioniService;

    @Autowired
    public void setRegIoAssegnazioniService(RegIoAssegnazioniService regIoAssegnazioniService) {

	this.regIoAssegnazioniService = regIoAssegnazioniService;
    }

    @Override
    public void delete(RegistrazioniInOut entity) {

	// §§§BEGIN§§§
	validateDelete(entity);
	registrazioniInOutDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<RegistrazioniInOut> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return registrazioniInOutDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public RegistrazioniInOut findById(PkId id) {

	// §§§BEGIN§§§
	return registrazioniInOutDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(RegistrazioniInOut entity) {

	// §§§BEGIN§§§
	if ((entity.getAmministrazioni() == null && entity.getAnagrafe() == null)) {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    InvalidValue iv = new InvalidValue("field.required", entity.getClass(), "anagrafe", null, entity);
	    _ivs.add(iv);
	    this.throwValidationMessages(_ivs);
	}
	if (validateEntity(entity)) {
	    registrazioniInOutDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(RegistrazioniInOut entity) {

	// §§§BEGIN§§§
	if ((entity.getAmministrazioni() == null && entity.getAnagrafe() == null)) {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    InvalidValue iv = new InvalidValue("field.required", entity.getClass(), "anagrafe", null, entity);
	    _ivs.add(iv);
	    this.throwValidationMessages(_ivs);
	}
	if (validateEntity(entity)) {
	    registrazioniInOutDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    protected Class<RegistrazioniInOut> getEntityClass() {

	return RegistrazioniInOut.class;
    }

    private void validateDelete(RegistrazioniInOut entity) {

	// §§§BEGIN§§§
	if (entity.getRegIoAssegnazionis() != null) {
	    if (entity.getRegIoAssegnazionis().size() > 0) {
		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		InvalidValue iv = new InvalidValue("validator.child.records.found.in.table.reg_io_assegnazioni", entity.getClass(), "", null, entity);
		_ivs.add(iv);
		this.throwValidationMessages(_ivs);
	    }
	}
	// §§§END§§§
    }

    @Override
    public void updateScadenze(RegistrazioniInOutCommand command) {

	// §§§BEGIN§§§
	if (command.getEntity().getId().getCodice() == null) {
	    this.insert(command.getEntity());
	} else {
	    this.update(command.getEntity());
	}
	RegistrazioniInOut registrazioniInOut = this.findById(new PkId(command.getEntity().getId().getCodice()));
	Set<RegIoAssegnazioni> set = new HashSet<RegIoAssegnazioni>(0);
	for (RegistrazioniImporti registrazioniImporti : command.getScadenzeList()) {
	    if (registrazioniImporti.getTransientSommaDaAssegnare() != null
		    && registrazioniImporti.getTransientSommaDaAssegnare().compareTo(new BigDecimal(0)) > 0) {
		RegIoAssegnazioni regIoAssegnazioni = new RegIoAssegnazioni();
		regIoAssegnazioni.setRegistrazioniInOut(registrazioniInOut);
		regIoAssegnazioni.setRegistrazioniImporti(registrazioniImporti);
		regIoAssegnazioni.setImporto(registrazioniImporti.getTransientSommaDaAssegnare());
		regIoAssegnazioniService.insert(regIoAssegnazioni);
		set.add(regIoAssegnazioni);
	    }
	}
	registrazioniInOut.setRegIoAssegnazionis(set);
	// §§§END§§§
    }

    @Override
    public List<RegistrazioniInOut> findRegistrazioniInOutDaAssegnare(Anagrafe anagrafe) {

	// §§§BEGIN§§§
	List<RegistrazioniInOut> daAssegnare = new ArrayList<RegistrazioniInOut>();
	RegistrazioniFilter filter = new RegistrazioniFilter();
	filter.setAnagrafe(anagrafe);
	List<RegistrazioniInOut> all = registrazioniInOutDAO.findByFilter(filter);
	Integer cod = anagrafe.getId().getCodice();
	for (RegistrazioniInOut registrazioniInOut : all) {
	    if (registrazioniInOut.getAnagrafe().getId().getCodice().equals(cod)) {
		BigDecimal importo = registrazioniInOut.getImporto();
		BigDecimal assegnato = new BigDecimal(0);
		Set<RegIoAssegnazioni> ass = registrazioniInOut.getRegIoAssegnazionis();
		for (RegIoAssegnazioni regIoAssegnazioni : ass) {
		    assegnato = assegnato.add(regIoAssegnazioni.getImporto());
		}
		if (importo.compareTo(assegnato) > 0) {
		    daAssegnare.add(registrazioniInOut);
		}
	    }
	}
	return daAssegnare;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniFilter> findByDataAndAnagrafeAndMercato(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	return registrazioniInOutDAO.findByDataAndAnagrafeAndMercato(registrazioniFilter);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniInOut> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return registrazioniInOutDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniInOut> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return registrazioniInOutDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insertPagamentoRata(Software software, BigDecimal importo, Responsabili responsabile, Integer codiceRegistrazione, Integer nrRata,
	    Date dataDistinta, Date dataIncasso, Integer tipimodalitapagamentoId, String riferimentiPagamento, String note) {

	Registrazioni reg = registrazioniService.findById(new PkId(codiceRegistrazione));
	List<RataHelper> rhs = reg.getListaRate();
	RataHelper rh = null;
	List<RegistrazioniImporti> rimps = null;
	for (RataHelper rataHelper : rhs) {
	    if (rataHelper.getNumeroRata().equals(nrRata)) {
		rimps = rataHelper.getRegistrazioniImportiList();
		rh = rataHelper;
		break;
	    }
	}
	if (rimps != null) {
	    RegistrazioniInOut pagamento = new RegistrazioniInOut();
	    pagamento.setAnagrafe(reg.getAnagrafe());
	    pagamento.setSoftware(software);
	    pagamento.setImporto(importo);
	    pagamento.setNote(note);
	    pagamento.setRiferimentiPagamento(riferimentiPagamento);
	    Tipimodalitapagamento tmp = tipimodalitapagamentoService.findById(new PkId(tipimodalitapagamentoId));
	    pagamento.setTipimodalitapagamento(tmp);
	    pagamento.setTipo(WebConstants.REGISTRAZIONIINOUT_TIPO_E);
	    pagamento.setDataDistinta(dataDistinta);
	    pagamento.setDataDistinta(dataIncasso);
	    this.insert(pagamento);
	    // verifico che l'importo ricevuto non sia maggiore della somma di quanto devo pagare
	    BigDecimal importoRata = rh.getRimanenza();
	    if (importoRata.compareTo(importo) < 0) {
		throw new RuntimeException("Valore troppo grande per la rata, da pagare=" + importoRata.toPlainString() + ", immesso="
			+ importo.toPlainString());
	    }
	    BigDecimal importoDaAssegnare = importo;
	    for (RegistrazioniImporti registrazioniImporti : rimps) {
		RegistrazioniImporti ri = registrazioniImportiService.findById(new PkId(registrazioniImporti.getId().getCodice()));
		if (ri.getRimanenza().compareTo(BigDecimal.ZERO) > 0) {
		    BigDecimal daInputare = ri.getRimanenza();
		    BigDecimal rimanenza = ri.getRimanenza();
		    RegIoAssegnazioni regIoAssegnazioni = new RegIoAssegnazioni();
		    regIoAssegnazioni.setRegistrazioniInOut(pagamento);
		    regIoAssegnazioni.setRegistrazioniImporti(registrazioniImporti);
		    rimanenza = importoDaAssegnare.subtract(rimanenza);
		    if (BigDecimal.ZERO.compareTo(rimanenza) >= 0) {
			daInputare = daInputare.add(rimanenza); // aggiungo un valore negativo quindi sottraggo
		    }
		    importoDaAssegnare = importoDaAssegnare.subtract(daInputare);
		    regIoAssegnazioni.setImporto(daInputare);
		    regIoAssegnazioniService.insert(regIoAssegnazioni);
		}
	    }
	}
    }
}
