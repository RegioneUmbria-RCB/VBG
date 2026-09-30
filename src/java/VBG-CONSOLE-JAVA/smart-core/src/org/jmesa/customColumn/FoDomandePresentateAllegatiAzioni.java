package org.jmesa.customColumn;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService.TIPO_FILE;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class FoDomandePresentateAllegatiAzioni extends AbstractCellEditor {

    private FoDomandeOggettiService foDomandeOggettiService;
    private OggettiService oggettiService;

    public FoDomandePresentateAllegatiAzioni(FoDomandeOggettiService foDomandeOggettiService, OggettiService oggettiService) {

	this.foDomandeOggettiService = foDomandeOggettiService;
	this.oggettiService = oggettiService;
    }

    @Override
    public Object getValue(Object item, String property, int countRecords) {

	FoDomande fod = (FoDomande) item;
	String output = "";
	String resultRicevuta = "";
	String resultZip = "";
	//	if (fod != null) {
	//	    String linkBase = getWebContext().getContextPath() + "/file/";
	//	    FoDomandeOggetti zip = foDomandeOggettiService.findAllegatoZip(fod.getId().getIdcomune(), fod.getId().getCodice());
	//	    FoDomandeOggetti ricevuta = foDomandeOggettiService.findAllegatoRicevuta(fod.getId().getIdcomune(), fod.getId().getCodice());
	//	    if (ricevuta != null) {
	//		Oggetti o = oggettiService.findByIdLazy(new PkId(ricevuta.getId().getIdcomune(), ricevuta.getId().getCodiceoggetto()));
	//		resultRicevuta = replaceFile(o, linkBase);
	//		output = resultRicevuta + "<br />";
	//	    }
	//	    if (zip != null) {
	//		Oggetti o = oggettiService.findByIdLazy(new PkId(zip.getId().getIdcomune(), zip.getId().getCodiceoggetto()));
	//		resultZip = replaceFile(o, linkBase);
	//		output += resultZip + "<br />";
	//	    }
	//	    // if (zip == null && ricevuta == null) {
	//	    List<FoDomandeOggetti> oggss = foDomandeOggettiService.findByIdDomandaFo(fod.getId().getCodice());
	//	    for (FoDomandeOggetti food : oggss) {
	//		if (!(FoDomandeOggettiService.TIPO_FILE.RICEVUTA.toString().equalsIgnoreCase(food.getTipoFile()) || FoDomandeOggettiService.TIPO_FILE.ZIP_DOMANDA
	//			.toString().equalsIgnoreCase(food.getTipoFile()))) {
	//		    Oggetti o = food.getOggetti();
	//		    String fileLink = replaceFile(o, linkBase);
	//		    output += fileLink + "<br />";
	//		}
	//	    }
	//	    //}
	//	}
	output += "<div class=\"downloadlink\" data-qsmac=\""
		+ Utilities.getLinkForFile("id=" + fod.getId().getCodice() + "&idComuneDomanda=" + fod.getId().getIdcomune() + "&ts_"
			+ System.currentTimeMillis()) + "\" >Scarica gli Allegati</div>";
	return output;
    }

    private String replaceFile(Oggetti o, String linkBase) {

	String link = Utilities.getLinkForFile("id=" + o.getId().getCodice() + "&idComuneOggetto=" + o.getId().getIdcomune() + "&ts_"
		+ System.currentTimeMillis());
	String result = "<a href=\"" + getWebContext().getContextPath() + "/ajax/download.htm?" + link + "\" title=\"\"><img src='"
		+ getWebContext().getContextPath() + "/images/download16x16.png' border=\"0\"/>" + o.getNomefile() + " ("
		+ o.getDimensioneFileLeggibile() + ")</a>";
	return result;
    }
}
