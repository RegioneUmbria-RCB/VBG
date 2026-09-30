package org.jmesa.customColumn;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.FoDomRichieste;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.sigepro.cart.service.utils.XmlUtils;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class FoDomandeDescrizione extends AbstractCellEditor {

    private OggettiService oggettiService;
    private AlberoprocService alberoprocService;

    public FoDomandeDescrizione(OggettiService oggettiService, AlberoprocService alberoprocService) {

	this.oggettiService = oggettiService;
	this.alberoprocService = alberoprocService;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	FoDomande f = null;
	if (item instanceof FoDomande) {
	    f = (FoDomande) item;
	} else {
	    FoDomRichieste o = (FoDomRichieste) item;
	    f = o.getFoDomande();
	}
	String codiceAttivitaBDR = "";
	String descrizioneAttivitaBDR = "";
	Integer codiceAlberoproc = null;
	if (f.getOggettoCart() != null) {
	    if (f.getOggettoCart().getId() != null) {
		if (f.getOggettoCart().getId().getCodice() != null) {
		    Oggetti oggettoCart = oggettiService.findById(new PkId(f.getOggettoCart().getId().getCodice()));
		    if (oggettoCart != null && oggettoCart.getOggetto() != null) {
			DatiDomandaCart retVal = null;
			try {
			    String domandaXml = new String(oggettoCart.getOggetto(), FACCTConstants.DEFAULT_CHARSET);
			    //String domandaXml = new String(oggettoCart.getOggetto());
			    //if(log.isDebugEnabled())log.debug("getDatiDomandaCart() - unmarshall dei dati della domanda {} dall'oggetto avente codice {}. XML : \r\n", new Object[]{idDomandaFo, oggettoCart.getId()});
			    retVal = (DatiDomandaCart) XmlUtils.unMarshallString(domandaXml, DatiDomandaCart.class);
			} catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
			} catch (JAXBException e) {
			    e.printStackTrace();
			}
			if (retVal != null) {
			    if (retVal.getDatiContestoDomanda() != null) {
				codiceAttivitaBDR = retVal.getDatiContestoDomanda().getCodiceAttivitaBdr();
				codiceAlberoproc = retVal.getDatiContestoDomanda().getIdAlberoProc();
			    }
			}
		    }
		}
	    }
	}
	String result = "";
	if (codiceAlberoproc != null) {
	    Alberoproc ap = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codiceAlberoproc));
	    if (ap != null) {
		result = ap.getVwAlberoproc().getScDescrizionepadre();
		if (StringUtils.isNotBlank(result)) {
		    result = result.substring((result.lastIndexOf(" - ") + 3)) + " - " + ap.getVwAlberoproc().getScDescrizionebreve();
		}
	    }
	}
	if (StringUtils.isNotBlank(codiceAttivitaBDR)) {
	    result += " <b>(" + codiceAttivitaBDR + ")</b>";
	}
	return result;
    }
}
