package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoRecuperaPagamento", propOrder = { "dataoraRichiesta", "pagamento", "rt" })
public class EsitoRecuperaPagamento {

    @XmlElement(name = "dataora_richiesta")
    private String dataoraRichiesta;
    @XmlElement(name = "pagamento")
    private List<Pagamento> pagamento = null;
    @XmlElement(name = "rt")
    private String rt;

    public String getDataoraRichiesta() {

	return dataoraRichiesta;
    }

    public void setDataoraRichiesta(String dataoraRichiesta) {

	this.dataoraRichiesta = dataoraRichiesta;
    }

    public List<Pagamento> getPagamento() {

	return pagamento;
    }

    public void setPagamento(List<Pagamento> pagamento) {

	this.pagamento = pagamento;
    }

    public String getRt() {

	return rt;
    }

    public void setRt(String rt) {

	this.rt = rt;
    }
}
