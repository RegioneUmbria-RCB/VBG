package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RichiestaDovuto {

    @XmlElement
    private String codiceIPA;

    @XmlElement
    private String codiceServizio;

    @XmlElement
    private Dovuto dovuto;

    @XmlElement
    private boolean transactional;

	public String getCodiceIPA() {
		return codiceIPA;
	}

	public void setCodiceIPA(String codiceIPA) {
		this.codiceIPA = codiceIPA;
	}

	public String getCodiceServizio() {
		return codiceServizio;
	}

	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}

	public Dovuto getDovuto() {
		return dovuto;
	}

	public void setDovuto(Dovuto dovuto) {
		this.dovuto = dovuto;
	}

	public boolean isTransactional() {
		return transactional;
	}

	public void setTransactional(boolean transactional) {
		this.transactional = transactional;
	}

    
}
