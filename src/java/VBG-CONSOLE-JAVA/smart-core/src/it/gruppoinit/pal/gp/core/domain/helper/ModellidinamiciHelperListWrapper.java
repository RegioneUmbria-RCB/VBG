package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ModellidinamiciHelperListWrapper")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModellidinamiciHelperListWrapper", propOrder = { "moduliModelli", "codiceEndo" })
public class ModellidinamiciHelperListWrapper {

    @XmlElementWrapper(name = "moduliModelli", required = false)
    private List<String> moduliModelli = new ArrayList<String>();
    @XmlElement(name = "codiceEndo", required = false)
    private String codiceEndo = "";

    public List<String> getModuliModelli() {

	return moduliModelli;
    }

    public void setModuliModelli(List<String> moduliModelli) {

	this.moduliModelli = moduliModelli;
    }

    
    public String getCodiceEndo() {
    
        return codiceEndo;
    }

    
    public void setCodiceEndo(String codiceEndo) {
    
        this.codiceEndo = codiceEndo;
    }
}
