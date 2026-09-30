package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.service.helper.SchedeDinamicheTL;
import it.gruppoinit.pal.gp.core.service.regole.Dyn2RegoleHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;

@XmlRootElement(name = "ModellidinamiciHelper")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModellidinamiciHelper", propOrder = { "idModello", "titolo", "tabella", "regoleHelper", "baseContesto", "idcomune" })
public class ModellidinamiciHelper {

    private static final Logger log = LoggerFactory.getLogger(ModellidinamiciHelper.class);
    @XmlElement(name = "idModello", required = true)
    private int idModello;
    @XmlElement(name = "titolo", required = true)
    private String titolo;
    @XmlElement(name = "tabella", required = true)
    private ModellidinamiciTabellaHelper tabella;
    @XmlElement(name = "regoleHelper", required = false)
    private Dyn2RegoleHelper regoleHelper;
    @XmlElement(name = "baseContesto", required = true)
    private String baseContesto;
    @XmlElement(name = "idcomune", required = true)
    private String idcomune;
    @XmlElement(name = "templateFor", required = false)
    private String templateFor;

    private ModellidinamiciHelper() {

	super();
	this.idModello = -1;
	this.titolo = "Modello non definito (-1)";
	this.baseContesto = "";
    }

    public ModellidinamiciHelper(String idcomune, int idModello, String titolo, String baseContesto) {

	this();
	this.idModello = idModello;
	this.titolo = titolo;
	this.baseContesto = baseContesto;
	this.idcomune = idcomune;
    }

    public void setTabella(ModellidinamiciTabellaHelper tabella) {

	this.tabella = tabella;
    }

    public ModellidinamiciTabellaHelper getTabella() {

	return tabella;
    }

    public int getIdModello() {

	return idModello;
    }

    public void setIdModello(int idModello) {

	this.idModello = idModello;
    }

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public Dyn2RegoleHelper getRegoleHelper() {

	return regoleHelper;
    }

    public void setRegoleHelper(Dyn2RegoleHelper regoleHelper) {

	this.regoleHelper = regoleHelper;
    }

    public String getBaseContesto() {

	return baseContesto;
    }

    public void setBaseContesto(String baseContesto) {

	this.baseContesto = baseContesto;
    }

    public boolean isRenderForPrint() {

	Boolean isPrint = SchedeDinamicheTL.getRenderForPrint();
	return BooleanUtils.isTrue(isPrint);
    }

    public boolean isBackOffice() {

	return Utilities.isBackOffice();
    }

    @SuppressWarnings("unused")
    public void debug(Object o) {

	if (o != null) {
	    int numRighe = o.hashCode();
	    if (o instanceof ModellidinamiciColonnaHelper) {
		ModellidinamiciColonnaHelper col = (ModellidinamiciColonnaHelper) o;
		Dyn2Modellid d2md = col.getDyn2Modellid();
		Dyn2Regole rule = col.getRegolaAttivazione();
	    }
	}
    }

    public static int contaElementi(Collection<Object> elements) {

	int retVal = 0;
	if (elements != null) {
	    retVal = elements.size();
	}
	return retVal;
    }

    public static int contaElementi(Object[] elements) {

	Collection<Object> elemColl = null;
	if (elements != null) {
	    elemColl = Arrays.asList(elements);
	}
	return contaElementi(elemColl);
    }

    public static boolean isNullOrEmpty(String test) {

	return StringUtils.isEmpty(test);
    }

    public List<FieldError> validaCampi() {

	List<FieldError> errors = validaCampiTabella(getTabella(), null);
	return errors;
    }

    public List<FieldError> validaCampiTabella(ModellidinamiciTabellaHelper tabella, Integer indiceBlocco) {

	List<FieldError> errors = new ArrayList<FieldError>();
	List<ModellidinamiciRigaHelper> righe = tabella.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			if (colonna.getTabelle() != null) {
			    // caso blocchi multipli
			    for (int i = 0; i < colonna.getTabelle().size(); i++) {
				ModellidinamiciTabellaHelper tabellaNested = colonna.getTabelle().get(i);
				errors.addAll(validaCampiTabella(tabellaNested, new Integer(i)));
			    }
			}
			ModellidinamiciCampoHelper campo = colonna.getCampo();
			if (campo != null) {
			    boolean isActive = true;
			    if (colonna.getRegolaAttivazione() != null) {
				try {
				    isActive = this.regoleHelper.evaluateRule(colonna.getRegolaAttivazione(), tabella, indiceBlocco);
				} catch (Dyn2RegoleSyntaxError e) {
				    log.error("validaCampiTabella - errore di sintassi nell'elaborazione della regola {}: {}", new Object[] {
					    colonna.getRegolaAttivazione().getDescrizione(), e });
				}
			    }
			    if (isActive) {
				errors.addAll(campo.validaCampo());
			    }
			}
		    }
		}
	    }
	}
	return errors;
    }

    public boolean isContestoIstanza() {

	return WebConstants.DYN2_BASECONTESTI_ISTANZA.equals(this.baseContesto);
    }

    public boolean isContestoAnagrafe() {

	return WebConstants.DYN2_BASECONTESTI_ANAGRAFE.equals(this.baseContesto);
    }

    public boolean isContestoAttivita() {

	return WebConstants.DYN2_BASECONTESTI_ATTIVITA.equals(this.baseContesto);
    }

    public boolean isContestoIndefinito() {

	return !isContestoAnagrafe() && !isContestoAttivita() && !isContestoIstanza();
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    
    public String getTemplateFor() {
    
        return templateFor;
    }

    
    public void setTemplateFor(String templateFor) {
    
        this.templateFor = templateFor;
    }
}
