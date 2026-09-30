package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

public class PosizioneDaAggiungereABlackList {

    private Integer idDettPosizioneDebitoria;
    private Integer idPresenza;
    private Integer idAutorizzazione;
    private Integer codiceanagrafe;

    public Integer getIdDettPosizioneDebitoria() {

	return idDettPosizioneDebitoria;
    }

    public void setIdDettPosizioneDebitoria(Integer idDettPosizioneDebitoria) {

	this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
    }

    public Integer getIdPresenza() {

	return idPresenza;
    }

    public void setIdPresenza(Integer idPresenza) {

	this.idPresenza = idPresenza;
    }
    
    public Integer getIdAutorizzazione() {
    
        return idAutorizzazione;
    }

    
    public void setIdAutorizzazione(Integer idAutorizzazione) {
    
        this.idAutorizzazione = idAutorizzazione;
    }
    
    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	if (this.idDettPosizioneDebitoria != null) {
	    sb.append("Id posizione debitoria: ");
	    sb.append(this.idDettPosizioneDebitoria);
	    sb.append(" ");
	}
	if (this.idPresenza != null) {
	    sb.append("Id presenza: ");
	    sb.append(this.idPresenza);
	    sb.append(" ");
	}
	
	if(this.idAutorizzazione != null){
	    sb.append("Id autorizzazione: ");
	    sb.append(this.idAutorizzazione);
	    sb.append(" ");
	}
	
	if(this.codiceanagrafe != null){
	    sb.append("codiceanagrafe: ");
	    sb.append(this.codiceanagrafe);
	}
	
	return sb.toString();
    }
}
