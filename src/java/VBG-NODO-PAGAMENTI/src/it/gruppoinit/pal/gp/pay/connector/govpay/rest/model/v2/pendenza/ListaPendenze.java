package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Lista", propOrder = { "numRisultati", "numPagine", "risultatiPerPagina", "pagina", "prossimiRisultati", "maxRisultati",
	"risultati" })
public class ListaPendenze {

    @XmlElement(name = "numRisultati")
    private long numRisultati;
    @XmlElement(name = "numPagine")
    private long numPagine;
    @XmlElement(name = "risultatiPerPagina")
    private long risultatiPerPagina;
    @XmlElement(name = "pagina")
    private long pagina;
    @XmlElement(name = "prossimiRisultati")
    private String prossimiRisultati;
    @XmlElement(name = "maxRisultati")
    private BigDecimal maxRisultati = null;
    @XmlElement(name = "risultati")
    private List<PendenzaIndex> risultati;

    public List<PendenzaIndex> getRisultati() {

	return this.risultati;
    }

    public void setRisultati(List<PendenzaIndex> risultati) {

	this.risultati = risultati;
    }

    public long getNumRisultati() {

	return this.numRisultati;
    }

    public void setNumRisultati(long numRisultati) {

	this.numRisultati = numRisultati;
    }

    public long getNumPagine() {

	return this.numPagine;
    }

    public void setNumPagine(long numPagine) {

	this.numPagine = numPagine;
    }

    public long getRisultatiPerPagina() {

	return this.risultatiPerPagina;
    }

    public void setRisultatiPerPagina(long risultatiPerPagina) {

	this.risultatiPerPagina = risultatiPerPagina;
    }

    public long getPagina() {

	return this.pagina;
    }

    public void setPagina(long pagina) {

	this.pagina = pagina;
    }

    public String getProssimiRisultati() {

	return this.prossimiRisultati;
    }

    public void setProssimiRisultati(String prossimiRisultati) {

	this.prossimiRisultati = prossimiRisultati;
    }

    public BigDecimal getMaxRisultati() {

	return maxRisultati;
    }

    public void setMaxRisultati(BigDecimal maxRisultati) {

	this.maxRisultati = maxRisultati;
    }
    //    public Lista(List<T> risultati, URI requestUri, long count, long pagina, long limit) {
    //
    //	this(risultati, requestUri, count, pagina, limit, null);
    //    }
    //    public Lista(List<T> risultati, URI requestUri, long count, long pagina, long limit, BigDecimal maxRisultati) {
    //
    //	this.risultati = risultati;
    //	this.numPagine = count == 0 ? 1 : (long) Math.ceil(count / (double) limit);
    //	//		this.pagina = (long) Math.ceil((offset+1)/(double)limit);
    //	this.pagina = pagina;
    //	this.risultatiPerPagina = limit;
    //	this.numRisultati = count;
    //	this.maxRisultati = maxRisultati;
    //	URIBuilder builder = new URIBuilder(requestUri);
    //	builder.setParameter(Costanti.PARAMETRO_RISULTATI_PER_PAGINA, Long.toString(this.risultatiPerPagina));
    //	if (this.pagina < this.numPagine) {
    //	    //			long nextPagina = offset+limit;
    //	    long nextPagina = pagina + 1;
    //	    builder.setParameter(Costanti.PARAMETRO_PAGINA, Long.toString(nextPagina));
    //	    try {
    //		this.prossimiRisultati = builder.build().toString();
    //	    } catch (URISyntaxException e) {
    //	    }
    //	}
    //    }
}
