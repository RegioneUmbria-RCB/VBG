package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.util.Date;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;


public class BorsellinoAutorizzazioniHistModel {

    private Integer id;
    private String numero;
    private Date dataaut;
    private Date dataoperazione;
    private String tipooperazione;
    

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }                    
    
    public Date getDataaut() {
    
        return dataaut;
    }
    
    public void setDataaut(Date dataaut) {
    
        this.dataaut = dataaut;
    }

    public Date getDataoperazione() {
    
        return dataoperazione;
    }

    
    public void setDataoperazione(Date dataoperazione) {
    
        this.dataoperazione = dataoperazione;
    }

    
    public String getTipooperazione() {
    
        return tipooperazione;
    }

    
    public void setTipooperazione(String tipooperazione) {
    
        this.tipooperazione = tipooperazione;
    }
    
    
    //CONVERSIONI
    public String getDataautStr(){
	if(dataaut == null){
	    return null;
	}
	return Utilities.formatDate(dataaut, false);
    }
    
    public String getDataoperazioneStr(){
	if(dataoperazione == null){
	    return null;
	}
	return Utilities.formatDate(dataoperazione, WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN);
    }

}
