package it.gruppoinit.pal.gp.core.domain;

import it.init.sigepro.rte.types.CampoSchedaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public class CampoSchedaHelper {

    private CampoSchedaType campo;
    private int molteplicita;
    private SortedSet<ValoreParametroTypeHelper> vtpH = new TreeSet<ValoreParametroTypeHelper>(new ValoreParametroTypeHelperComparator());
    
    public static CampoSchedaHelper fromFieldName(String key) {

	String[] dato = key.split("_");
	if (dato.length > 3) {
	    //String fld = dato[0]; // FLD
	    String codiceCampo = dato[1];
	    //String indice = dato[2];
	    String indiceMolteplicita = dato[3];
	    if (!codiceCampo.equals("NULL")) {
		CampoSchedaHelper csH = new CampoSchedaHelper();
		csH.getCampo().setCodice(codiceCampo);
		csH.setMolteplicita(Integer.valueOf(indiceMolteplicita));
		return csH;
	    }
	}
	return null;
    }

    public CampoSchedaHelper() {

	campo = StcDomainHelper.getNewCampoSchedaType();
    }

    public CampoSchedaType getCampo() {

	return campo;
    }

    public void setCampo(CampoSchedaType campo) {

	this.campo = campo;
    }

    public int getMolteplicita() {

	return molteplicita;
    }

    public void setMolteplicita(int molteplicita) {

	this.molteplicita = molteplicita;
    }

    public SortedSet<ValoreParametroTypeHelper> getVtpH() {

	return vtpH;
    }

    /*
    public void setVtpH(List<ValoreParametroTypeHelper> vtpH) {

	this.vtpH = vtpH;
    }
    */
}
