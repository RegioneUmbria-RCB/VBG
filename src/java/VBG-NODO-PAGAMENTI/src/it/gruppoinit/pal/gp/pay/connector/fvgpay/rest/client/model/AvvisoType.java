package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

public class AvvisoType {

    private byte[] avviso = null;
    /**
     * mimeType dell'avviso di pagamento
     **/
    private String contentType = null;
    /**
     * nome del file dell'avvisatura generata
     **/
    private String nomeStampa = null;

    /**
     * Get avviso
     * 
     * @return avviso
     **/
    @XmlElement(name = "avviso")
    public byte[] getAvviso() {

	return avviso;
    }

    public void setAvviso(byte[] avviso) {

	this.avviso = avviso;
    }

    public AvvisoType avviso(byte[] avviso) {

	this.avviso = avviso;
	return this;
    }

    /**
     * mimeType dell&#39;avviso di pagamento
     * 
     * @return contentType
     **/
    @XmlElement(name = "content_type")
    public String getContentType() {

	return contentType;
    }

    public void setContentType(String contentType) {

	this.contentType = contentType;
    }

    public AvvisoType contentType(String contentType) {

	this.contentType = contentType;
	return this;
    }

    /**
     * nome del file dell&#39;avvisatura generata
     * 
     * @return nomeStampa
     **/
    @XmlElement(name = "nome_stampa")
    public String getNomeStampa() {

	return nomeStampa;
    }

    public void setNomeStampa(String nomeStampa) {

	this.nomeStampa = nomeStampa;
    }

    public AvvisoType nomeStampa(String nomeStampa) {

	this.nomeStampa = nomeStampa;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AvvisoType {\n");
	sb.append("    avviso: ").append(toIndentedString(avviso)).append("\n");
	sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
	sb.append("    nomeStampa: ").append(toIndentedString(nomeStampa)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private static String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
