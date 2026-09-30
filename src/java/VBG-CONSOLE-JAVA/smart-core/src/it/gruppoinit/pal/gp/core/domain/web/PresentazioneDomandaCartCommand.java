package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContestoPresentazioneDomanda", propOrder = { "token", "idComuneAlias", "idAlberoProc", "codiceAttivitaBdr", "nomeAttivitaBdr",
	"tipoAzione", "endoAttivi", "endoNoCartAttivi", "endoCount", "idDomandaFo", WebConstants.RETURNTO, "codicecomune", "lavoriSuFabbricati",
	"codiceIstanza", "codiceMovimento", "interventiLocali" })
public class PresentazioneDomandaCartCommand {

    @XmlElement(name = "token", required = true)
    private String token;
    @XmlElement(name = "idComuneAlias", required = true)
    private String idComuneAlias;
    @XmlElement(name = "idAlberoProc", required = false)
    private Integer idAlberoProc;
    @XmlElement(name = "codiceAttivitaBdr", required = true)
    private String codiceAttivitaBdr;
    @XmlElement(name = "nomeAttivitaBdr", required = false)
    private String nomeAttivitaBdr;
    @XmlElement(name = "tipoAzione", defaultValue = "Avvio")
    private String tipoAzione;
    @XmlElement(name = "endoAttivi", required = true)
    private Set<String> endoAttivi = new HashSet<String>();
    @XmlElement(name = "endoNoCartAttivi", required = false)
    private Set<String> endoNoCartAttivi = new HashSet<String>();
    @XmlElement(name = "endoCount", required = false)
    private Integer endoCount;
    @XmlElement(name = "idDomandaFo", required = true)
    private Integer idDomandaFo;
    @XmlTransient
    private String idDomandaCart;
    @XmlElement(name = WebConstants.RETURNTO, required = true)
    private String ReturnTo;
    @XmlElement(name = "codicecomune", required = true)
    private String codicecomune;
    @XmlElement(name = "lavoriSuFabbricati", required = false)
    private Boolean lavoriSuFabbricati;
    /*
     * utilizzati quando si generano gli allegati CART a partire da un'istanza nel BO
     * per poter effettuare una notifica all'ente terzo secondo le specifiche CART
     */
    @XmlElement(name = "codiceIstanza", required = false)
    private Integer codiceIstanza;
    @XmlElement(name = "codiceMovimento", required = false)
    private Integer codiceMovimento;
    @XmlElement(name = "interventiLocali", required = false)
    private Set<String> interventiLocali = new HashSet<String>();

    /**
     * @return the token
     */
    public String getToken() {

	return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {

	this.token = token;
    }

    /**
     * @return the idComune
     */
    public String getIdComuneAlias() {

	return idComuneAlias;
    }

    /**
     * @param idComune
     *            the idComune to set
     */
    public void setIdComuneAlias(String idComuneAlias) {

	this.idComuneAlias = idComuneAlias;
    }

    /**
     * @return the idAlberoProc
     */
    public Integer getIdAlberoProc() {

	return idAlberoProc;
    }

    /**
     * @param idAlberoProc
     *            the idAlberoProc to set
     */
    public void setIdAlberoProc(Integer idAlberoProc) {

	this.idAlberoProc = idAlberoProc;
    }

    public Set<String> getEndoAttivi() {

	return endoAttivi;
    }

    public void setEndoAttivi(Set<String> endoAttivi) {

	this.endoAttivi = endoAttivi;
    }

    /**
     * @return the codiceAttivitaBdr
     */
    public String getCodiceAttivitaBdr() {

	return codiceAttivitaBdr;
    }

    /**
     * @param codiceAttivitaBdr
     *            the codiceAttivitaBdr to set
     */
    public void setCodiceAttivitaBdr(String codiceAttivitaBdr) {

	this.codiceAttivitaBdr = codiceAttivitaBdr;
    }

    /**
     * @return the nomeAttivitaBdr
     */
    public String getNomeAttivitaBdr() {

	return nomeAttivitaBdr;
    }

    /**
     * @param nomeAttivitaBdr
     *            the nomeAttivitaBdr to set
     */
    public void setNomeAttivitaBdr(String nomeAttivitaBdr) {

	this.nomeAttivitaBdr = nomeAttivitaBdr;
    }

    /**
     * @return the tipoAzione
     */
    public String getTipoAzione() {

	return tipoAzione;
    }

    /**
     * @param tipoAzione
     *            the tipoAzione to set
     */
    public void setTipoAzione(String tipoAzione) {

	this.tipoAzione = tipoAzione;
    }

    /**
     * @return the idDomandaFo
     */
    public Integer getIdDomandaFo() {

	return idDomandaFo;
    }

    /**
     * @param idDomandaFo
     *            the idDomandaFo to set
     */
    public void setIdDomandaFo(Integer idDomandaFo) {

	this.idDomandaFo = idDomandaFo;
    }

    public String getReturnTo() {

	return ReturnTo;
    }

    public void setReturnTo(String returnTo) {

	this.ReturnTo = returnTo;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public boolean isARJ() {

	return false;
    }

    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("IDCOMUNEALIAS=").append(getIdComuneAlias());
	sb.append(", IDDOMANDAFO=").append(getIdDomandaFo());
	sb.append(", IDALBEROPROC=").append(getIdAlberoProc());
	sb.append(", CODICEATTIVITABDR=").append(getCodiceAttivitaBdr());
	sb.append(", DESCRATTIVITABDR=").append(getNomeAttivitaBdr());
	sb.append(", TIPOAZIONE=").append(getTipoAzione());
	Set<String> endos = getEndoAttivi();
	sb.append(", ENDOATTIVI=[");
	if (null != endos) {
	    for (String endo : endos) {
		sb.append(endo).append(",");
	    }
	    if (endos.size() > 0) {
		sb.deleteCharAt(sb.length() - 1);
	    }
	}
	sb.append("], TOKEN=").append(getToken());
	sb.append(", REDIRECTURL=").append(getReturnTo());
	sb.append(", CODICECOMUNE=").append(getCodicecomune());
	return sb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceAttivitaBdr == null) ? 0 : codiceAttivitaBdr.hashCode());
	result = prime * result + ((endoAttivi == null) ? 0 : endoAttivi.hashCode());
	result = prime * result + ((idComuneAlias == null) ? 0 : idComuneAlias.hashCode());
	result = prime * result + ((idDomandaFo == null) ? 0 : idDomandaFo.hashCode());
	result = prime * result + ((tipoAzione == null) ? 0 : tipoAzione.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PresentazioneDomandaCartCommand other = (PresentazioneDomandaCartCommand) obj;
	if (codiceAttivitaBdr == null) {
	    if (other.codiceAttivitaBdr != null)
		return false;
	} else if (!codiceAttivitaBdr.equals(other.codiceAttivitaBdr))
	    return false;
	if (endoAttivi == null) {
	    if (other.endoAttivi != null)
		return false;
	} else if (!endoAttivi.equals(other.endoAttivi))
	    return false;
	if (idComuneAlias == null) {
	    if (other.idComuneAlias != null)
		return false;
	} else if (!idComuneAlias.equals(other.idComuneAlias))
	    return false;
	if (idDomandaFo == null) {
	    if (other.idDomandaFo != null)
		return false;
	} else if (!idDomandaFo.equals(other.idDomandaFo))
	    return false;
	return true;
    }

    /**
     * @return the idDomandaCart
     */
    public String getIdDomandaCart() {

	return idDomandaCart;
    }

    /**
     * @param idDomandaCart
     *            the idDomandaCart to set
     */
    public void setIdDomandaCart(String idDomandaCart) {

	this.idDomandaCart = idDomandaCart;
    }

    public Set<String> getEndoNoCartAttivi() {

	return endoNoCartAttivi;
    }

    public void setEndoNoCartAttivi(Set<String> endoNoCartAttivi) {

	this.endoNoCartAttivi = endoNoCartAttivi;
    }

    /**
     * restituisce il numero totale di endo non CART attivabili per l'utente e non il numero di quellil effettivamente
     * selezionati
     * 
     * @return
     */
    public Integer getEndoCount() {

	return endoCount;
    }

    public void setEndoCount(Integer endoCount) {

	this.endoCount = endoCount;
    }

    public Boolean getLavoriSuFabbricati() {

	return lavoriSuFabbricati;
    }

    public void setLavoriSuFabbricati(Boolean lavoriSuFabbricati) {

	this.lavoriSuFabbricati = lavoriSuFabbricati;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public Set<String> getInterventiLocali() {

	return interventiLocali;
    }

    public void setInterventiLocali(Set<String> interventiLocali) {

	this.interventiLocali = interventiLocali;
    }
    
    public String toQueryString(){
	StringBuilder qs = new StringBuilder("idAlberoProc=").append(this.getIdAlberoProc());
	qs.append("&codiceAttivitaBdr=").append(this.getCodiceAttivitaBdr());
	qs.append("&idDomandaFo=").append(this.getIdDomandaFo());
	qs.append("&" + WebConstants.RETURNTO + "=").append(this.getReturnTo());
	qs.append("&tipoAzione=").append(this.getTipoAzione());
	qs.append("&token=").append(this.getToken());
	return qs.toString();
    }
}
