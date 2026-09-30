package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class ImportoResponseType {

    @XmlElement(name = "rate")
    private Set<RataResponseType> rate = new HashSet<RataResponseType>();

    @XmlTransient
    public Set<RataResponseType> getRate() {

	if (rate == null) {
	    return new HashSet<RataResponseType>();
	}
	return rate;
    }

    public void setRate(Set<RataResponseType> rate) {

	this.rate = rate;
    }
}
