package it.gruppoinit.pal.gp.core.dao.helper;


public class MercatiStoricoInsBean {
    
    private String idcomune;
    private Integer fkcodicemercato;
    private Integer fkmercatiduso;
    private Integer codiceanagrafe;
    private String data; //la metto come stringa
    private Integer fkautorizzazioniid;
    
    
    
    public MercatiStoricoInsBean(String idcomune, Integer fkcodicemercato, Integer fkmercatiduso, Integer codiceanagrafe, String data,
	    Integer fkautorizzazioniid) {

	super();
	this.idcomune = idcomune;
	this.fkcodicemercato = fkcodicemercato;
	this.fkmercatiduso = fkmercatiduso;
	this.codiceanagrafe = codiceanagrafe;
	this.data = data;
	this.fkautorizzazioniid = fkautorizzazioniid;
    }

    public String getIdcomune() {
    
        return idcomune;
    }
    
    public void setIdcomune(String idcomune) {
    
        this.idcomune = idcomune;
    }
    
    public Integer getFkcodicemercato() {
    
        return fkcodicemercato;
    }
    
    public void setFkcodicemercato(Integer fkcodicemercato) {
    
        this.fkcodicemercato = fkcodicemercato;
    }
    
    public Integer getFkmercatiduso() {
    
        return fkmercatiduso;
    }
    
    public void setFkmercatiduso(Integer fkmercatiduso) {
    
        this.fkmercatiduso = fkmercatiduso;
    }
    
    public Integer getCodiceanagrafe() {
    
        return codiceanagrafe;
    }
    
    public void setCodiceanagrafe(Integer codiceanagrafe) {
    
        this.codiceanagrafe = codiceanagrafe;
    }
    
    public String getData() {
    
        return data;
    }
    
    public void setData(String data) {
    
        this.data = data;
    }
    
    public Integer getFkautorizzazioniid() {
    
        return fkautorizzazioniid;
    }
    
    public void setFkautorizzazioniid(Integer fkautorizzazioniid) {
    
        this.fkautorizzazioniid = fkautorizzazioniid;
    }

    @Override
    public int hashCode() {

	return 0;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MercatiStoricoInsBean other = (MercatiStoricoInsBean) obj;
	if (data == null) {
	    if (other.data != null)
		return false;
	} else if (!data.equals(other.data))
	    return false;
	if (codiceanagrafe == null) {
	    if (other.codiceanagrafe != null)
		return false;
	} else if (!codiceanagrafe.equals(other.codiceanagrafe))
	    return false;
	if (fkautorizzazioniid == null) {
	    if (other.fkautorizzazioniid != null)
		return false;
	} else if (!fkautorizzazioniid.equals(other.fkautorizzazioniid))
	    return false;
	if (fkcodicemercato == null) {
	    if (other.fkcodicemercato != null)
		return false;
	} else if (!fkcodicemercato.equals(other.fkcodicemercato))
	    return false;
	if (fkmercatiduso == null) {
	    if (other.fkmercatiduso != null)
		return false;
	} else if (!fkmercatiduso.equals(other.fkmercatiduso))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	return true;
    }
   
}
