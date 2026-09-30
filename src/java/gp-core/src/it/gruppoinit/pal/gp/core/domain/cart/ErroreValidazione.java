package it.gruppoinit.pal.gp.core.domain.cart;

import java.util.ArrayList;
import java.util.List;

public class ErroreValidazione {

    private String idSemantico;
    private List<MessaggioErrore> errori = new ArrayList<MessaggioErrore>();
    private boolean bloccante = true;
    private String idModulo;
    private String idQuadro;

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

	if(null == errori){
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
     * Restituisce true se la presenza dell'errore impedicsce la presentazione della domanda.
     * Di default tutti gli errori sono considerati bloccanti tranne quelli relativi alla firma digitale degli allegati utente
     * che vengono gestiti appositamente nella pagina specifica per la firma digitale.
     * @return the bloccante
     */
    public boolean getBloccante() {
    
        return bloccante;
    }

    /**
     * @param bloccante the bloccante to set
     */
    public void setBloccante(boolean bloccante) {
    
        this.bloccante = bloccante;
    }

    
    public String getIdModulo() {
    
        return idModulo;
    }

    
    public void setIdModulo(String idModulo) {
    
        this.idModulo = idModulo;
    }

    
    public String getIdQuadro() {
    
        return idQuadro;
    }

    
    public void setIdQuadro(String idQuadro) {
    
        this.idQuadro = idQuadro;
    }

}
