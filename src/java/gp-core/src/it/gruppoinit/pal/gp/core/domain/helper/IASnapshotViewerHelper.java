package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class IASnapshotViewerHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2557071745760646686L;
    private List<IASnapshotSchedaHelper> listaSchede;
    private List<IASnapshotValoriHelper> listaSnapshot;

    public List<IASnapshotSchedaHelper> getListaSchede() {

	return listaSchede;
    }

    public void setListaSchede(List<IASnapshotSchedaHelper> listaSchede) {

	this.listaSchede = listaSchede;
    }

    public List<IASnapshotValoriHelper> getListaSnapshot() {

	return listaSnapshot;
    }

    public void setListaSnapshot(List<IASnapshotValoriHelper> listaSnapshot) {

	this.listaSnapshot = listaSnapshot;
    }

    public String scriviValoriSnapshotSchedaAnagrafe(String campo) {

	String result = new String();
	boolean primocodiceOsservatorio = true;
	if (this.getListaSnapshot() != null) {
	    for (IASnapshotValoriHelper isvh : this.getListaSnapshot()) {
		result += "<td class=\"contenutoCampo\">";
		String valore = "";
		if (campo.equalsIgnoreCase("id")) {
		    valore = isvh.getId() + "";
		}
		if (campo.equalsIgnoreCase("data")) {
		    Date d = isvh.getData();
		    if (d != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
			valore = sdf.format(d);
		    }
		}
		if (campo.equalsIgnoreCase("denominazione")) {
		    if (isvh.getDenominazione() != null) {
			valore = isvh.getDenominazione();
		    }
		}
		if (campo.equalsIgnoreCase("tipologiaAttivita")) {
		    if (isvh.getTipologiaAttivita() != null) {
			valore = isvh.getTipologiaAttivita();
		    }
		}
		if (campo.equalsIgnoreCase("istanza")) {
		    valore = "<a href=\"javascript: void 0\" onclick=\"dettaglioIstanza('" + isvh.getCodiceIstanzaUltima() + "','"
			    + isvh.getSoftwareIstanzaUltima() + "');\" class=\"linkIstanza\">" + isvh.getNumeroIstanzaUltima() + "</a>";
		}
		if (campo.equalsIgnoreCase("descrizioneIntervento")) {
		    valore = "<div style=\"word-wrap: break-word;width: 200px\">";
		    valore += isvh.getDescrizioneIntervento();
		    valore += "</div>";
		}
		if (campo.equalsIgnoreCase("attiva")) {
		    if (isvh.getAttiva() != null) {
			if (isvh.getAttiva().booleanValue()) {
			    valore = "Si";
			} else {
			    valore = "No";
			}
		    }
		}
		if (campo.equalsIgnoreCase("operante")) {
		    if (isvh.getOperante() != null) {
			if (isvh.getOperante().booleanValue()) {
			    valore = "Si";
			} else {
			    valore = "No";
			}
		    }
		}
		if (campo.equalsIgnoreCase("codiceOsservatorio")) {
		    valore = "<div style=\"word-wrap: break-word;width: 200px\">";
		    if (primocodiceOsservatorio) {
			valore += (isvh.getCodiceOsservatorio() == null ? "" : isvh.getCodiceOsservatorio());
		    } else {
			valore += "<input style=\"text-align: right;\" type=\"text\" size=\"10\" class=\"mod_codice_osservatorio\" data-idsnapshot=\""
				+ isvh.getId() + "\" value=\"" + (isvh.getCodiceOsservatorio() == null ? "" : isvh.getCodiceOsservatorio()) + "\"/>";
		    }
		    valore += "</div>";
		    primocodiceOsservatorio = false;
		}
		result += valore + "</td>";
	    }
	}
	return result;
    }

    public String scriviValoriSnapshotScheda(Integer codiceCampo) {

	String result = new String();
	if (this.getListaSnapshot() != null) {
	    for (IASnapshotValoriHelper isvh : this.getListaSnapshot()) {
		if (!isvh.getMapValori().isEmpty()) {
		    String valore = "";
		    String key = IASnapshotValoriHelper.builCodiceOggettoMappa(isvh.getId(), codiceCampo.intValue(), 0, 0);
		    valore = isvh.getMapValori().get(key);
		    result += "<td id=\"val_campo_id_" + codiceCampo.intValue() + "_" + isvh.getId() + "\" class=\"contenutoCampo\">"
			    + StringUtils.defaultString(valore) + "</td>";
		}
	    }
	}
	return result;
    }
}
