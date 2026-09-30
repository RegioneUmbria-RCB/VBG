package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.domain.CampoSchedaHelper;
import it.gruppoinit.pal.gp.areariservata.domain.SchedaHelper;
import it.gruppoinit.pal.gp.areariservata.service.Dyn2ModellitARJService;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciColonnaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciRigaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciTabellaHelper;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.impl.Dyn2ModellitServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Dyn2Utils;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.ValoreCampoDinamicoType;
import it.init.sigepro.rte.types.ValoreParametroType;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class Dyn2ModellitARJServiceImpl extends Dyn2ModellitServiceImpl implements Dyn2ModellitARJService {

    //private static final Logger log = LoggerFactory.getLogger(Dyn2ModellitARJServiceImpl.class);
    @Autowired
    private Dyn2CampiService dyn2CampiService;

    /*
    @Override
    protected String renderRicerca() {

    String elementId = campo.getNameOrIdFromCampo(true);
    String elementName = campo.getNameOrIdFromCampo(false);
    StringBuffer buf = new StringBuffer();
    String size = campo.getProprietaCampo(ProprietaCampi.DescriptionBoxColumns, "40");
    String result = "";
    if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
        result = getPrintValue(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
    } else {
        result = "<div class=\"searchbox\" style=\"padding-left: 0px\"><input id=\"" + elementId + "\" name=\"" + elementName + "_DESC\""
    	    + " size=\"" + size + "\"" + " value=\"" + StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), "") + "\"/>"
    	    + "<input type=\"hidden\" id=\"" + elementId + "_hidden\" name=\"" + elementName + "_ID\" value=\""
    	    + StringUtils.defaultIfEmpty(campo.getValore(), "") + "\" /></div>";
        String errMessg = campo.validaRicerca();
        result += spanErrors(elementId, elementName, errMessg);
        buf.append("<script type=\"text/javascript\">");
        buf.append("settaRicercaDyncampi('").append(elementId).append("','").append(d2c.getId().getCodice()).append("');");
        buf.append("</script>");
        result += buf.toString();
    }
    return result;
    }
    */
    /*
    @Override
    protected String renderData() {

    if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
        String result = getPrintValue(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
        return result;
    } else {
        CustomHtmlBuilder result = new CustomHtmlBuilder();
        String elementId = campo.getNameOrIdFromCampo(true);
        String elementName = campo.getNameOrIdFromCampo(false);
        String readonly = getProprietaCampo(proprietaCampo, ProprietaCampi.ReadOnly, "");
        result.input().type("text").id(elementId).onchange("isValidDate(this,true);validaCampo(this);").name(elementName).size("10");
        if (readonly.equalsIgnoreCase("true")) {
    	result.readonly();
        }
        result.value(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), "")).end();
        result.script().type("text/javascript").close().append(calendarString(elementId, null)).scriptEnd();
        String errMessg = validaData(d2c, proprietaCampo, campo.getValoreDecodificato());
        result.append(spanErrors(elementId, elementName, errMessg));
        return result.toString();
    }
    }
    */
    /*
    @Override
    protected String calendarString(String id, String name) {

    return "$('#" + id + "').datepicker();";
    }
    */
    /*
    protected String validaData(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, String valore) {

    String obbligatorio = validaObbligatorio(valore, proprietaCampo);
    if (StringUtils.isNotBlank(obbligatorio)) {
        return obbligatorio;
    }
    if (StringUtils.isNotBlank(valore)) {
        String validaData = "";
        try {
    	getDate(valore, WebConstants.DATE_FORMAT_PATTERN);
        } catch (Exception e) {
    	validaData = "Il formato della data non è corretto [" + WebConstants.DATE_FORMAT_PATTERN + "]";
        }
        if (StringUtils.isNotBlank(validaData)) {
    	return validaData;
        }
    }
    return "";
    }

    private String validaObbligatorio(String valore, Set<Dyn2Campiproprieta> proprietaCampo) {

    String obbligatorio = getProprietaCampo(proprietaCampo, ProprietaCampi.Obbligatorio, "false");
    String errMessg = "";
    if (obbligatorio.equalsIgnoreCase("true")) {
        if (StringUtils.isBlank(valore)) {
    	errMessg = getMessageFromBundle("alert.required", null);
        }
    }
    return errMessg;
    }

    public GregorianCalendar getDate(String date, String format) throws Exception {

    GregorianCalendar gd = new GregorianCalendar();
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date d = sdf.parse(date);
    gd.setTime(d);
    return gd;
    }
    */
    /*
    @Override
    protected String renderHelp(String tagId, String testo) {

    if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
        return "";
    } else {
        return "<span class=\"help_image\" id=\"" + tagId + "_HELP\" title=\"" + testo + "\"><label>(?)</label></span>";
    }
    }
    */
    /*
    @Override
    protected Object spanErrors(String elementId, String elementName, String errMessage) {

    String display = "display: none;";
    if (StringUtils.isNotBlank(errMessage)) {
        display = "";
    }
    return "<span id=\"" + elementId + "_ERRORS\" style=\"" + display + "\" class=\"error\">" + StringUtils.defaultIfEmpty(errMessage, "")
    	+ "</span>";
    }
    */
    @Override
    public ModellidinamiciHelper populateScheda(SchedaHelper schedaH) {

	ModellidinamiciHelper helper = populateModellodinamicoHelper(Integer.valueOf(schedaH.getScheda().getCodice()).intValue());
	ModellidinamiciTabellaHelper tabellaprincipale = helper.getTabella();
	List<ModellidinamiciRigaHelper> righe = tabellaprincipale.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			if (colonna.getTabelle() != null) {
			    // caso blocchi multipli
			    if (colonna.getTabelle().size() > 0) {
				ModellidinamiciTabellaHelper tabellaNested = colonna.getTabelle().get(0);
				int molteplicita = trovaMolteplicitaTabelleBlocchiMultipli(tabellaNested, schedaH);
				colonna.setTabelle(new ArrayList<ModellidinamiciTabellaHelper>());
				for (int i = 0; i < molteplicita; i++) {
				    ModellidinamiciTabellaHelper tabella = populateTabellaBloccoMultiplo(schedaH, tabellaNested, i);
				    colonna.getTabelle().add(i, tabella);
				}
			    }
			}
			if (colonna.getCampo() != null) {
			    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
			    d2c = dyn2CampiService.findById(d2c.getId());
			    if (d2c != null) {
				for (CampoSchedaHelper dato : schedaH.getCampi()) {
				    if (dato.getCampo().getCodice().equals(d2c.getId().getCodice().toString())) {
					List<ElementoValoreCampoDinamicoType> valori = dato.getCampo().getCampoDinamico().getValoreUtente()
						.getValore();
					for (int i = 0; i < valori.size(); i++) {
					    colonna.getCampo().setValore(valori.get(i).getCodice());
					    colonna.getCampo().setValoreDecodificato(valori.get(i).getDescrizione());
					    colonna.getCampo().setIndice(0);
					    colonna.getCampo().setIndicemolteplicita(i);
					}
					break;
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return helper;
    }

    /**
     * metodo per conteggio del numero di blocchi multipli
     * 
     * @param tabellaNested
     * @param schedaH
     * @return
     */
    private int trovaMolteplicitaTabelleBlocchiMultipli(ModellidinamiciTabellaHelper tabellaNested, SchedaHelper schedaH) {

	List<ModellidinamiciRigaHelper> righe = tabellaNested.getRighe();
	int numTabelle = 0;
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			int numTabelleColonna = 0;
			if (colonna.getCampo() != null) {
			    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
			    d2c = dyn2CampiService.findById(d2c.getId());
			    if (d2c != null) {
				for (CampoSchedaHelper campo : schedaH.getCampi()) {
				    if (campo.getCampo().getCodice().equals(d2c.getId().getCodice().toString())) {
					numTabelleColonna = campo.getCampo().getCampoDinamico().getValoreUtente().getValore().size();
					break;
				    }
				}
			    }
			}
			if (numTabelle < numTabelleColonna) {
			    numTabelle = numTabelleColonna;
			}
		    }
		}
	    }
	}
	return numTabelle == 0 ? numTabelle + 1 : numTabelle;
    }

    /**
     * metodo per il popolamento di un blocco multiplo
     * 
     * @param schedaH
     * @param tabellaNested
     * @param i
     * @return
     */
    private ModellidinamiciTabellaHelper populateTabellaBloccoMultiplo(SchedaHelper schedaH, ModellidinamiciTabellaHelper tabellaNested, int i) {

	ModellidinamiciTabellaHelper result = new ModellidinamiciTabellaHelper();
	Dyn2Utils.tabellaDTO(result, tabellaNested, false);
	for (ModellidinamiciRigaHelper rc : result.getRighe()) {
	    List<ModellidinamiciColonnaHelper> colCopy = rc.getColonne();
	    for (ModellidinamiciColonnaHelper colonna : colCopy) {
		if (colonna.getCampo() != null) {
		    //Dyn2Modellid d2md = colonna.getDyn2Modellid();
		    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
		    d2c = dyn2CampiService.findById(d2c.getId());
		    if (d2c != null) {
			for (int j = 0; j < schedaH.getCampi().size(); j++) {
			    if (schedaH.getCampi().get(j).getCampo().getCodice().equals(d2c.getId().getCodice().toString())) {
				ValoreCampoDinamicoType valoreUtente = schedaH.getCampi().get(j).getCampo().getCampoDinamico().getValoreUtente();
				ValoreParametroType val = new ValoreParametroType();
				if (valoreUtente.getValore().size() > i) {
				    val = valoreUtente.getValore().get(i);
				}
				colonna.getCampo().setValore(val.getCodice());
				colonna.getCampo().setValoreDecodificato(val.getDescrizione());
				colonna.getCampo().setIndice(0);
				colonna.getCampo().setIndicemolteplicita(i);
				colonna.getCampo().setIndiceBlocco(i);
			    }
			}
		    }
		}
	    }
	}
	return result;
    }
    /*
    @Override
    protected String getPrintValue(String value) {

    return "<b>" + value + "</b>";
    }
    */
}
