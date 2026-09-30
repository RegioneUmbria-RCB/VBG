package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parametri")
public class ProfiliRestBean {

    @XmlElement(name = "profili")
    private List<String> profili;

    public List<String> getProfili() {

	return profili;
    }

    public void setProfili(List<String> profili) {

	this.profili = profili;
    }
}
