/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

/**
 * @author francescop
 * 
 */
public class AlboPretorioFilter {

    private Integer maxresult;
    private Integer da;
    private Integer a;
    private Integer codicecategoria;
    private String oggetto;
    private Date validoAl;
    private Date validoDal;
    private Date dataValidaAl;

    public Date getDataValidaAl() {

	return dataValidaAl;
    }

    public void setDataValidaAl(Date dataValidaAl) {

	this.dataValidaAl = dataValidaAl;
    }

    public Integer getMaxresult() {

	return maxresult;
    }

    public void setMaxresult(Integer maxresult) {

	this.maxresult = maxresult;
    }

    public Integer getDa() {

	return da;
    }

    public void setDa(Integer da) {

	this.da = da;
    }

    public Integer getA() {

	return a;
    }

    public void setA(Integer a) {

	this.a = a;
    }

    public Integer getCodicecategoria() {

	return codicecategoria;
    }

    public void setCodicecategoria(Integer codicecategoria) {

	this.codicecategoria = codicecategoria;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public Date getValidoAl() {

	return validoAl;
    }

    public void setValidoAl(Date validoAl) {

	this.validoAl = validoAl;
    }

    public Date getValidoDal() {

	return validoDal;
    }

    public void setValidoDal(Date validoDal) {

	this.validoDal = validoDal;
    }
}
