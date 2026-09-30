package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppIoServiziConfigRestWrapperResponse {

    @XmlElement(name = "entiServizi")
    private List<AppIoServiziConfigRestResponse> entiServizi;

    public List<AppIoServiziConfigRestResponse> getEntiServizi() {

	return entiServizi;
    }

    public void setEntiServizi(List<AppIoServiziConfigRestResponse> entiServizi) {

	this.entiServizi = entiServizi;
    }
}
