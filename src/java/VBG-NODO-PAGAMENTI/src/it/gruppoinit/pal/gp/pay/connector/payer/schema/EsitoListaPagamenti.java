package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoListaPagamenti", propOrder = { // 
	"codiceRisposta", //
	"dataoraRichiesta", // 
	"paginazione", //
	"pagamento", //
	"totPosizioni" //
}) //
public class EsitoListaPagamenti {

    @XmlElement(name = "codice_risposta")
    private String codiceRisposta;
    @XmlElement(name = "dataora_richiesta")
    private String dataoraRichiesta;
    @XmlElement(name = "paginazione")
    private Paginazione paginazione;
    @XmlElement(name = "tot_posizioni")
    private String totPosizioni;
    @XmlElement(name = "pagamento")
    private List<Pagamento> pagamento;

    public String getCodiceRisposta() {

	return codiceRisposta;
    }

    public void setCodiceRisposta(String codiceRisposta) {

	this.codiceRisposta = codiceRisposta;
    }

    public String getDataoraRichiesta() {

	return dataoraRichiesta;
    }

    public void setDataoraRichiesta(String dataoraRichiesta) {

	this.dataoraRichiesta = dataoraRichiesta;
    }

    public Paginazione getPaginazione() {

	return paginazione;
    }

    public void setPaginazione(Paginazione paginazione) {

	this.paginazione = paginazione;
    }

    public String getTotPosizioni() {

	return totPosizioni;
    }

    public void setTotPosizioni(String totPosizioni) {

	this.totPosizioni = totPosizioni;
    }

    public List<Pagamento> getPagamento() {

	return pagamento;
    }

    public void setPagamento(List<Pagamento> pagamento) {

	this.pagamento = pagamento;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
