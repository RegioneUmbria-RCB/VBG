/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Classe per i tipi download permessi sulla gestione dei documenti dei Procedimenti
 * 
 * @author francescop
 * 
 */
public class TipoDownload {

    public TipoDownload() {

	super();
    }

    public TipoDownload(String codice, String descrizione) {

	this();
	this.codice = codice;
	this.descrizione = descrizione;
    }

    public static final String PDF = "pdf";
    public static final String PDF_DESC = "Portable Document Format (PDF)";
    public static final String RTF = "rtf";
    public static final String RTF_DESC = "Rich Text Format (RTF)";
    public static final String DOC = "doc";
    public static final String DOC_DESC = "Documento Word (DOC)";
    public static final String ODT = "odt";
    public static final String ODT_DESC = "Documento OpenOffice (ODT)";
    public static final String PDFC = "pdfc";
    public static final String PDFC_DESC = "Portable Document Format Compilabile (PDF)";
    public static Map<String, String> map = new HashMap<String, String>();
    static {
	map.put(PDF, PDF_DESC);
	map.put(RTF, RTF_DESC);
	map.put(DOC, DOC_DESC);
	map.put(ODT, ODT_DESC);
	map.put(PDFC, PDFC_DESC);
    }
    private String codice;
    private String descrizione;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public static List<TipoDownload> getTipoDownloads() {

	List<TipoDownload> tipoDownloads = new ArrayList<TipoDownload>(0);
	tipoDownloads.add(new TipoDownload(TipoDownload.PDF, TipoDownload.PDF_DESC));
	tipoDownloads.add(new TipoDownload(TipoDownload.PDFC, TipoDownload.PDFC_DESC));
	tipoDownloads.add(new TipoDownload(TipoDownload.DOC, TipoDownload.DOC_DESC));
	tipoDownloads.add(new TipoDownload(TipoDownload.ODT, TipoDownload.ODT_DESC));
	tipoDownloads.add(new TipoDownload(TipoDownload.RTF, TipoDownload.RTF_DESC));
	return tipoDownloads;
    }

    public static Set<TipoDownload> fromString(String[] codici, List<TipoDownload> tipoDownloads) {

	Set<TipoDownload> tipoDownloadList = new HashSet<TipoDownload>(0);
	for (String type : codici) {
	    if (StringUtils.isNotBlank(type)) {
		tipoDownloadList.add(getFromString(type, tipoDownloads));
	    }
	}
	return tipoDownloadList;
    }

    private static TipoDownload getFromString(String codice, List<TipoDownload> tipoDownloads) {

	for (TipoDownload tipoDownload : tipoDownloads) {
	    if (codice.equalsIgnoreCase(tipoDownload.getCodice())) {
		return tipoDownload;
	    }
	}
	return null;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codice == null) ? 0 : codice.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TipoDownload other = (TipoDownload) obj;
	if (codice == null) {
	    if (other.codice != null)
		return false;
	} else if (!codice.equals(other.codice))
	    return false;
	return true;
    }
}
