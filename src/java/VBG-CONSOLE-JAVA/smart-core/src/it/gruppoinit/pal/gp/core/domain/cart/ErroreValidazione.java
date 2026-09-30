package it.gruppoinit.pal.gp.core.domain.cart;

import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ErroreValidazione {

    private String idSemantico;
    private List<MessaggioErrore> errori = new ArrayList<MessaggioErrore>();
    private boolean bloccante = true;
    private String idModulo;
    private String idQuadro;
    private String idCampo;

    public ErroreValidazione() {

    }

    public ErroreValidazione(String idSemantico, List<MessaggioErrore> errori) {

	this.idSemantico = idSemantico;
	this.errori = errori;
    }

    public ErroreValidazione(String idSemantico, MessaggioErrore errore) {

	this.idSemantico = idSemantico;
	getErrori().add(errore);
    }

    /**
     * @return the idSemantico
     */
    public String getIdSemantico() {

	return idSemantico;
    }

    /**
     * @param idSemantico
     *            the idSemantico to set
     */
    public void setIdSemantico(String idSemantico) {

	this.idSemantico = idSemantico;
    }

    /**
     * @return the errori
     */
    public List<MessaggioErrore> getErrori() {

	if (null == errori) {
	    errori = new ArrayList<MessaggioErrore>();
	}
	return errori;
    }

    /**
     * @param errori
     *            the errori to set
     */
    public void setErrori(List<MessaggioErrore> errori) {

	this.errori = errori;
    }

    /**
     * Restituisce true se la presenza dell'errore impedicsce la presentazione della domanda. Di default tutti gli
     * errori sono considerati bloccanti tranne quelli relativi alla firma digitale degli allegati utente che vengono
     * gestiti appositamente nella pagina specifica per la firma digitale.
     * 
     * @return the bloccante
     */
    public boolean getBloccante() {

	return bloccante;
    }

    /**
     * @param bloccante
     *            the bloccante to set
     */
    public void setBloccante(boolean bloccante) {

	this.bloccante = bloccante;
    }

    public String getIdModulo() {

	return idModulo;
    }

    public void setIdModulo(String idModulo) {
	if(StringUtils.isNotEmpty(idSemantico)){
	    idModulo = CartModuloHelper.buildIdModuloFromRefModulo(idModulo);
	}
	this.idModulo = idModulo;
    }

    public String getIdQuadro() {

	return idQuadro;
    }

    public void setIdQuadro(String idQuadro) {

	this.idQuadro = idQuadro;
    }

    public String getIdCampo() {

	return idCampo;
    }

    public void setIdCampo(String idCampo) {

	this.idCampo = idCampo;
    }
}
