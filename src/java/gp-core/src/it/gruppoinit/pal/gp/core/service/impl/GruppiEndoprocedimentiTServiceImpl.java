package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.GruppiEndoprocedimentiTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocGruppiSmist;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiD;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiT;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocGruppiSmistService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.GruppiEndoprocedimentiDService;
import it.gruppoinit.pal.gp.core.service.GruppiEndoprocedimentiTService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.helper.GruppiSmistamentoHelper;
import it.gruppoinit.pal.gp.core.service.helper.GruppiSmistamentoRigheHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GruppiEndoprocedimentiTServiceImpl extends BaseServiceImpl<GruppiEndoprocedimentiT, PkId> implements GruppiEndoprocedimentiTService {

    private static final Logger log = LoggerFactory.getLogger(GruppiEndoprocedimentiTServiceImpl.class);
    private GruppiEndoprocedimentiTDAO gruppiEndoprocedimentiTDAO;
    private GruppiEndoprocedimentiDService gruppiEndoprocedimentiDService;
    private TipiMovimentoService tipiMovimentoService;
    private SoftwareService softwareService;
    private AlberoprocGruppiSmistService alberoprocGruppiSmistService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private AlberoprocService alberoprocService;
    private TipiprocedureService tipiprocedureService;

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setAlberoprocGruppiSmistService(AlberoprocGruppiSmistService alberoprocGruppiSmistService) {

	this.alberoprocGruppiSmistService = alberoprocGruppiSmistService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setGruppiEndoprocedimentiDService(GruppiEndoprocedimentiDService gruppiEndoprocedimentiDService) {

	this.gruppiEndoprocedimentiDService = gruppiEndoprocedimentiDService;
    }

    @Autowired
    public void setGruppiEndoprocedimentiTDAO(GruppiEndoprocedimentiTDAO gruppiEndoprocedimentiTDAO) {

	this.gruppiEndoprocedimentiTDAO = gruppiEndoprocedimentiTDAO;
    }

    @Override
    public void insert(GruppiEndoprocedimentiT entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    gruppiEndoprocedimentiTDAO.insert(entity);
	}
    }

    @Override
    public void update(GruppiEndoprocedimentiT entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    gruppiEndoprocedimentiTDAO.update(entity);
	}
    }

    @Override
    public void delete(GruppiEndoprocedimentiT entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    gruppiEndoprocedimentiTDAO.delete(entity);
	}
    }

    @Override
    protected boolean isDeleteAllowed(GruppiEndoprocedimentiT entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!alberoprocGruppiSmistService.findByGruppiEndot(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC_GRUPPI_SMIST", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    @Override
    protected void childDelete(GruppiEndoprocedimentiT entity) {

	List<GruppiEndoprocedimentiD> geds = gruppiEndoprocedimentiDService.findByGruppiT(entity.getId().getCodice());
	for (GruppiEndoprocedimentiD ged : geds) {
	    gruppiEndoprocedimentiDService.delete(ged);
	}
    }

    @Override
    public List<GruppiEndoprocedimentiT> findAll(Integer firstResult, Integer maxResult) {

	return gruppiEndoprocedimentiTDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public GruppiEndoprocedimentiT findById(PkId id) {

	return gruppiEndoprocedimentiTDAO.findById(id);
    }

    @Override
    protected Class<GruppiEndoprocedimentiT> getEntityClass() {

	return GruppiEndoprocedimentiT.class;
    }

    @Override
    protected void fixMergeEntityProperties(GruppiEndoprocedimentiT entity) {

	Tipimovimento tm = tipiMovimentoService.bindDomainObject(entity.getTipimovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipimovimento(tm);
	Software soft = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(soft);
    }

    private void dataIntegration(GruppiEndoprocedimentiT entity) {

	if (entity.getNumEndoWarning() == null) {
	    entity.setNumEndoWarning(0);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    public List<GruppiEndoprocedimentiT> findByDescrizione(String textToSearch) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction desc = new FilterRestriction();
	desc.addFilterField(FilterUtils.like("descrizione", "%" + textToSearch + "%"));
	ft.addRestriction(desc);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return gruppiEndoprocedimentiTDAO.findByFilterTable(ft);
    }

    @Override
    public List<CodiceDescrizioneBean> elaboraFileExcel(InputStream inputStream) throws IOException {

	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	XSSFWorkbook wb = new XSSFWorkbook(inputStream);
	verificaPresenzaFogli(wb);
	List<GruppiSmistamentoHelper> grps = caricaGruppi(wb);
	List<GruppiSmistamentoRigheHelper> grpSmist = caricaGruppiSmistamento(wb);
	// verifico se gli endo selezionati esistano nella base dati
	verificaEsistenzaDati(result, grps, grpSmist);
	if (result.size() > 0) {
	    return result;
	}
	// azzero i dati delle tabelle e aggiorno i record
	this.deleteAllConfigurazioniSoftwareCorrente();
	// dati cancellati
	// inserisco i dati
	Map<String, Integer> codiciGruppi = new HashMap<String, Integer>();
	Software s = softwareService.findById(ORMHelper.getSoftware());
	int i = 0;
	for (GruppiSmistamentoHelper gsh : grps) {
	    if (StringUtils.isNotBlank(gsh.getDescrizione())) {
		log.debug("elaboraFileExcel: riga {} carico il gruppo {}", i, gsh);
		GruppiEndoprocedimentiT grp = new GruppiEndoprocedimentiT();
		grp.setDescrizione(gsh.getDescrizione());
		grp.setNumEndoWarning(gsh.getNumWarnings());
		String tipiMov = gsh.getTipmov();
		Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipiMov));
		grp.setTipimovimento(tm);
		grp.setSoftware(s);
		this.insert(grp);
		codiciGruppi.put(gsh.getDescrizione(), grp.getId().getCodice());
		Set<Integer> endoprocedimentis = gsh.getEndoprocedimentis();
		for (Integer codiceInventario : endoprocedimentis) {
		    Inventarioprocedimenti ip = inventarioprocedimentiService.findById(new PkId(codiceInventario));
		    if (ip != null) {
			GruppiEndoprocedimentiD e = new GruppiEndoprocedimentiD();
			e.setGruppiEndoprocedimentiT(grp);
			e.setInventarioprocedimento(ip);
			gruppiEndoprocedimentiDService.insert(e);
		    }
		}
	    }
	    i++;
	}
	log.debug("caricaFileExcel: codiciGruppiElaborati {}", codiciGruppi);
	i = 0;
	for (GruppiSmistamentoRigheHelper gsh : grpSmist) {
	    log.debug("caricaFileExcel: elaboro la riga helper {}, {}", i, gsh);
	    Set<Integer> codicigruppo = new HashSet<Integer>();
	    Integer g1 = codiciGruppi.get(gsh.getGruppo1());
	    if (g1 != null) {
		codicigruppo.add(g1);
	    }
	    Integer g2 = codiciGruppi.get(gsh.getGruppo2());
	    if (g2 != null) {
		codicigruppo.add(g2);
	    }
	    Integer g3 = codiciGruppi.get(gsh.getGruppo3());
	    if (g3 != null) {
		codicigruppo.add(g3);
	    }
	    if (!codicigruppo.isEmpty()) {
		alberoprocGruppiSmistService.insertConfigurazione(codicigruppo, gsh.getCodiceAlberoproc(), gsh.getCodiceProceduraScia(),
			gsh.getCodiceProceduraOrdinario());
	    } else {
		log.warn("caricaFileExcel: elaboro la riga helper {}, {} non ha i codici gruppo settati", i, gsh);
	    }
	    i++;
	}
	return result;
    }

    public static void main(String[] args) {

	Map<String, Integer> c = new HashMap<String, Integer>();
	c.put("rew", 1);
	c.put("rew2", 2);
	System.out.println(c);
    }

    private List<GruppiSmistamentoRigheHelper> caricaGruppiSmistamento(XSSFWorkbook wb) {

	List<GruppiSmistamentoRigheHelper> result = new ArrayList<GruppiSmistamentoRigheHelper>(500);
	log.debug("elaboro il foglio SMISTAMENTO");
	XSSFSheet sheet = wb.getSheet("SMISTAMENTO"); // first sheet	
	int numeroRighe = sheet.getLastRowNum();
	numeroRighe++;
	for (int i = 1; i < numeroRighe; i++) {
	    log.debug("caricaGruppiSmistamento: carico la riga {}", i);
	    GruppiSmistamentoRigheHelper g = new GruppiSmistamentoRigheHelper();
	    XSSFRow row = sheet.getRow(i); // first row
	    g.setRiga(i);
	    XSSFCell gruppo1 = row.getCell(0);
	    XSSFCell gruppo2 = row.getCell(1);
	    XSSFCell gruppo3 = row.getCell(2);
	    if (gruppo1 != null) {
		g.setGruppo1(gruppo1.getStringCellValue());
	    }
	    if (gruppo2 != null) {
		g.setGruppo2(gruppo2.getStringCellValue());
	    }
	    if (gruppo3 != null) {
		g.setGruppo3(gruppo3.getStringCellValue());
	    }
	    XSSFCell alberoproc = row.getCell(3);
	    if (alberoproc != null) {
		g.setCodiceAlberoproc(Double.valueOf(alberoproc.getNumericCellValue()).intValue());
	    }
	    XSSFCell scia = row.getCell(5);
	    if (scia != null) {
		g.setCodiceProceduraScia(Double.valueOf(scia.getNumericCellValue()).intValue());
	    }
	    XSSFCell ordinario = row.getCell(6);
	    if (ordinario != null) {
		g.setCodiceProceduraOrdinario(Double.valueOf(ordinario.getNumericCellValue()).intValue());
	    }
	    log.debug("caricaGruppiSmistamento: riga {} con grupposmistamento {}", i, g);
	    result.add(g);
	}
	return result;
    }

    private void verificaEsistenzaDati(List<CodiceDescrizioneBean> result, List<GruppiSmistamentoHelper> grps,
	    List<GruppiSmistamentoRigheHelper> grpSmist) {

	for (GruppiSmistamentoHelper gsh : grps) {
	    if (gsh.getEndoprocedimentis().size() > 0) {
		if (StringUtils.isNotBlank(gsh.getTipmov())) {
		    String tipiMov = gsh.getTipmov();
		    Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipiMov));
		    if (tm == null) {
			result.add(newCDB(
				"CONF_TIPIMOV",
				"Il foglio GRUPPI_PROCEDIMENTI contiene un riferimento ad un TIPOMOVIMENTO non esistente. Gruppo"
					+ gsh.getDescrizione() + ", endo codice: " + tipiMov));
		    }
		}
		Set<Integer> i = gsh.getEndoprocedimentis();
		// verifico l'esistenza degli endoprocedimenti
		for (Integer codiceInventario : i) {
		    Inventarioprocedimenti ip = inventarioprocedimentiService.findById(new PkId(codiceInventario));
		    if (ip == null) {
			result.add(newCDB("CONF_ENDO",
				"Il foglio GRUPPI_PROCEDIMENTI contiene un riferimento ad un endo non esistente. Gruppo" + gsh.getDescrizione()
					+ ", endo codice: " + codiceInventario));
		    }
		}
	    }
	}
	for (GruppiSmistamentoRigheHelper gsh : grpSmist) {
	    if (gsh.getCodiceAlberoproc() == null) {
		result.add(newCDB("CONF_ALBEROPROC",
			"Il foglio SMISTAMENTO contiene un riferimento ad un ALBEROPROC NULLO alla riga " + gsh.getRiga()));
	    } else {
		Integer ap = gsh.getCodiceAlberoproc();
		Alberoproc a = alberoprocService.findById(new PkId(ap));
		if (a == null) {
		    result.add(newCDB("CONF_ALBEROPROC", "Il foglio SMISTAMENTO contiene un riferimento ad un ALBEROPROC non esistente con codice "
			    + ap + " alla riga " + gsh.getRiga()));
		}
	    }
	    if (gsh.getCodiceProceduraScia() != null) {
		Tipiprocedure tp = tipiprocedureService.findById(new PkId(gsh.getCodiceProceduraScia()));
		if (tp == null) {
		    result.add(newCDB(
			    "CONF_TIPIPROCEDURE",
			    "Il foglio SMISTAMENTO contiene un riferimento ad un TIPOPROCEDURA non esistente con codice "
				    + gsh.getCodiceProceduraScia() + " alla riga " + gsh.getRiga()));
		}
	    }
	    if (gsh.getCodiceProceduraOrdinario() != null) {
		Tipiprocedure tp = tipiprocedureService.findById(new PkId(gsh.getCodiceProceduraOrdinario()));
		if (tp == null) {
		    result.add(newCDB(
			    "CONF_TIPIPROCEDURE",
			    "Il foglio SMISTAMENTO contiene un riferimento ad un TIPOPROCEDURA non esistente con codice "
				    + gsh.getCodiceProceduraOrdinario() + " alla riga " + gsh.getRiga()));
		}
	    }
	}
    }

    private void deleteAllConfigurazioniSoftwareCorrente() {

	List<AlberoprocGruppiSmist> smists = alberoprocGruppiSmistService.findAll(null, null);
	for (AlberoprocGruppiSmist alberoprocGruppiSmist : smists) {
	    alberoprocGruppiSmistService.delete(alberoprocGruppiSmist);
	}
	List<GruppiEndoprocedimentiT> tuttiIRecord = this.findAll(null, null);
	for (GruppiEndoprocedimentiT gruppiEndoprocedimentiT : tuttiIRecord) {
	    this.delete(gruppiEndoprocedimentiT);
	}
    }

    private List<GruppiSmistamentoHelper> caricaGruppi(XSSFWorkbook wb) {

	Map<String, GruppiSmistamentoHelper> mapGruppi = new HashMap<String, GruppiSmistamentoHelper>();
	List<GruppiSmistamentoHelper> result = new ArrayList<GruppiSmistamentoHelper>(18);
	XSSFSheet sheet = wb.getSheet("GRUPPI"); // first sheet	
	log.debug("ELABORO IL FOGLIO GRUPPI");
	int numeroRighe = sheet.getLastRowNum();
	numeroRighe++;
	for (int i = 1; i < numeroRighe; i++) {
	    XSSFRow row = sheet.getRow(i); // first row
	    XSSFCell gruppo = row.getCell(0);
	    XSSFCell warn = row.getCell(1);
	    XSSFCell mov = row.getCell(2);
	    String grps = "";
	    int warns = 0;
	    String movs = "";
	    if (gruppo != null) {
		grps = gruppo.getStringCellValue();
	    }
	    if (warn != null) {
		warns = Double.valueOf(warn.getNumericCellValue()).intValue();
	    }
	    if (mov != null) {
		movs = mov.getStringCellValue();
	    }
	    GruppiSmistamentoHelper g = new GruppiSmistamentoHelper();
	    g.setDescrizione(grps);
	    g.setNumWarnings(warns);
	    g.setTipmov(movs);
	    result.add(g);
	    mapGruppi.put(grps, g);
	}
	sheet = wb.getSheet("GRUPPI_PROCEDIMENTI"); // first sheet
	log.debug("ELABORO IL FOGLIO GRUPPI_PROCEDIMENTI");
	numeroRighe = sheet.getLastRowNum();
	numeroRighe++;
	for (int i = 1; i < numeroRighe; i++) {
	    XSSFRow row = sheet.getRow(i); // first row
	    XSSFCell gruppo = row.getCell(0);
	    XSSFCell endo = row.getCell(1);
	    String grps = "";
	    Integer codiceInventario = null;
	    if (gruppo != null) {
		grps = gruppo.getStringCellValue();
	    }
	    if (endo != null) {
		codiceInventario = Double.valueOf(endo.getNumericCellValue()).intValue();
	    }
	    if (codiceInventario != null) {
		GruppiSmistamentoHelper el = mapGruppi.get(grps);
		el.getEndoprocedimentis().add(codiceInventario);
	    }
	}
	return result;
    }

    private List<CodiceDescrizioneBean> verificaPresenzaFogli(XSSFWorkbook wb) throws RuntimeException {

	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	XSSFSheet sheet = wb.getSheet("GRUPPI");
	if (sheet == null) {
	    result.add(newCDB("CONF", "Il foglio GRUPPI non è stato trovato"));
	}
	sheet = wb.getSheet("GRUPPI_PROCEDIMENTI");
	if (sheet == null) {
	    result.add(newCDB("CONF", "Il foglio GRUPPI_PROCEDIMENTI non è stato trovato"));
	}
	sheet = wb.getSheet("SMISTAMENTO");
	if (sheet == null) {
	    result.add(newCDB("CONF", "Il foglio SMISTAMENTO non è stato trovato"));
	}
	return result;
    }

    private CodiceDescrizioneBean newCDB(String codice, String errore) {

	CodiceDescrizioneBean res = new CodiceDescrizioneBean();
	res.setCodice(codice);
	res.setDescrizione(errore);
	return res;
    }
}
