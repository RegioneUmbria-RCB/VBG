/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * classe che modella la ricezione dei dati di pagamento da parete di PayER
 * 
 * @author lion
 *
 */
@XmlRootElement(name = "PaymentData")
@XmlType(name = "PaymentData", propOrder = { "portaleId", "numeroOperazione", "idOrdine", "dataOraOrdine", "idTransazione", "dataOraTransazione",
	"sistemaPagamento", "sistemaPagamentoD", "circuitoAutorizzativo", "circuitoAutorizzativoD", "importoTransato", "importoCommissioni",
	"importoCommissioniEnte", "esito", "esitoD", "autorizzazione", "datiSpecifici" })
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentData {

    @XmlElement(name = "PortaleID", required = true)
    private String portaleId;
    @XmlElement(name = "NumeroOperazione", required = true)
    private String numeroOperazione;
    @XmlElement(name = "IDOrdine")
    private String idOrdine;
    @XmlElement(name = "DataOraOrdine")
    private String dataOraOrdine;
    @XmlElement(name = "IDTransazione")
    private String idTransazione;
    @XmlElement(name = "DataOraTransazione")
    private String dataOraTransazione;
    @XmlElement(name = "SistemaPagamento")
    private String sistemaPagamento;
    @XmlElement(name = "SistemaPagamentoD")
    private String sistemaPagamentoD;
    @XmlElement(name = "CircuitoAutorizzativo")
    private String circuitoAutorizzativo;
    @XmlElement(name = "CircuitoAutorizzativoD")
    private String circuitoAutorizzativoD;
    @XmlElement(name = "ImportoTransato")
    private Integer importoTransato;
    @XmlElement(name = "ImportoCommissioni")
    private Integer importoCommissioni;
    @XmlElement(name = "ImportoCommissioniEnte")
    private Integer importoCommissioniEnte;
    @XmlElement(name = "Esito", required = true)
    private Esito esito;
    @XmlElement(name = "EsitoD", required = true)
    private String esitoD;
    @XmlElement(name = "Autorizzazione")
    private String autorizzazione;
    @XmlElement(name = "DatiSpecifici")
    private String datiSpecifici;

    public String getPortaleId() {

	return portaleId;
    }

    public void setPortaleId(String portaleId) {

	this.portaleId = portaleId;
    }

    public String getNumeroOperazione() {

	return numeroOperazione;
    }

    public void setNumeroOperazione(String numeroOperazione) {

	this.numeroOperazione = numeroOperazione;
    }

    public String getIdOrdine() {

	return idOrdine;
    }

    public void setIdOrdine(String idOrdine) {

	this.idOrdine = idOrdine;
    }

    public String getIdTransazione() {

	return idTransazione;
    }

    public void setIdTransazione(String idTransazione) {

	this.idTransazione = idTransazione;
    }

    public String getDataOraOrdine() {

	return dataOraOrdine;
    }

    public void setDataOraOrdine(String dataOraOrdine) {

	this.dataOraOrdine = dataOraOrdine;
    }

    public String getDataOraTransazione() {

	return dataOraTransazione;
    }

    public void setDataOraTransazione(String dataOraTransazione) {

	this.dataOraTransazione = dataOraTransazione;
    }

    public String getSistemaPagamento() {

	return sistemaPagamento;
    }

    public void setSistemaPagamento(String sistemaPagamento) {

	this.sistemaPagamento = sistemaPagamento;
    }

    public String getSistemaPagamentoD() {

	return sistemaPagamentoD;
    }

    public void setSistemaPagamentoD(String sistemaPagamentoD) {

	this.sistemaPagamentoD = sistemaPagamentoD;
    }

    public String getCircuitoAutorizzativo() {

	return circuitoAutorizzativo;
    }

    public void setCircuitoAutorizzativo(String circuitoAutorizzativo) {

	this.circuitoAutorizzativo = circuitoAutorizzativo;
    }

    public String getCircuitoAutorizzativoD() {

	return circuitoAutorizzativoD;
    }

    public void setCircuitoAutorizzativoD(String circuitoAutorizzativoD) {

	this.circuitoAutorizzativoD = circuitoAutorizzativoD;
    }

    public Integer getImportoTransato() {

	return importoTransato;
    }

    public void setImportoTransato(Integer importoTransato) {

	this.importoTransato = importoTransato;
    }

    public Integer getImportoCommissioni() {

	return importoCommissioni;
    }

    public void setImportoCommissioni(Integer importoCommissioni) {

	this.importoCommissioni = importoCommissioni;
    }

    public Integer getImportoCommissioniEnte() {

	return importoCommissioniEnte;
    }

    public void setImportoCommissioniEnte(Integer importoCommissioniEnte) {

	this.importoCommissioniEnte = importoCommissioniEnte;
    }

    public Esito getEsito() {

	return esito;
    }

    public void setEsito(Esito esito) {

	this.esito = esito;
    }

    public String getEsitoD() {

	return esitoD;
    }

    public void setEsitoD(String esitoD) {

	this.esitoD = esitoD;
    }

    public String getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(String autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public String getDatiSpecifici() {

	return datiSpecifici;
    }

    public void setDatiSpecifici(String datiSpecifici) {

	this.datiSpecifici = datiSpecifici;
    }
}
