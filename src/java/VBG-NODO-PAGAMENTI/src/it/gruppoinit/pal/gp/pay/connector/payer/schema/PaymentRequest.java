/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Classe per la modellazione di una richiesta di caricamento posizione OTF in PayER
 * 
 * @author lion
 *
 */
@XmlRootElement(name = "PaymentRequest")
@XmlType(propOrder = { "portaleID", "funzione", "urlDiRitorno", "urlDiNotifica", "urlBack", "commitNotifica", "userData", "serviceData",
	"accountingData" })
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentRequest {

    @XmlElement(required = true, name = "PortaleID")
    private String portaleID;
    @XmlElement(required = true, name = "Funzione")
    private String funzione;
    @XmlElement(name = "URLDiRitorno", required = true)
    private String urlDiRitorno;
    @XmlElement(name = "URLDiNotifica", required = true)
    private String urlDiNotifica;
    @XmlElement(name = "URLBack", required = true)
    private String urlBack;
    @XmlElement(name = "CommitNotifica")
    private CommitNotifica commitNotifica;
    @XmlElement(required = true, name = "UserData")
    private UserData userData;
    @XmlElement(required = true, name = "ServiceData")
    private ServiceData serviceData;
    @XmlElement(required = true, name = "AccountingData")
    private AccountingData accountingData;

    public String getPortaleID() {

	return portaleID;
    }

    public void setPortaleID(String portaleID) {

	this.portaleID = portaleID;
    }

    public String getFunzione() {

	return funzione;
    }

    public void setFunzione(String funzione) {

	this.funzione = funzione;
    }

    public String getUrlDiRitorno() {

	return urlDiRitorno;
    }

    public void setUrlDiRitorno(String urlDiRitorno) {

	this.urlDiRitorno = urlDiRitorno;
    }

    public String getUrlDiNotifica() {

	return urlDiNotifica;
    }

    public void setUrlDiNotifica(String urlDiNotifica) {

	this.urlDiNotifica = urlDiNotifica;
    }

    public String getUrlBack() {

	return urlBack;
    }

    public void setUrlBack(String urlBack) {

	this.urlBack = urlBack;
    }

    public CommitNotifica getCommitNotifica() {

	return commitNotifica;
    }

    public void setCommitNotifica(CommitNotifica commitNotifica) {

	this.commitNotifica = commitNotifica;
    }

    public UserData getUserData() {

	return userData;
    }

    public void setUserData(UserData userData) {

	this.userData = userData;
    }

    public ServiceData getServiceData() {

	return serviceData;
    }

    public void setServiceData(ServiceData serviceData) {

	this.serviceData = serviceData;
    }

    public AccountingData getAccountingData() {

	return accountingData;
    }

    public void setAccountingData(AccountingData accountingData) {

	this.accountingData = accountingData;
    }
}
