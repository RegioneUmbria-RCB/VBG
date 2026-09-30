package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParametroConOverride {

    @XmlElement(name = "overridepresenti")
    private boolean overridePresenti;

    public boolean isOverridePresenti() {

	return overridePresenti;
    }

    public void setOverridePresenti(boolean overridePresenti) {

	this.overridePresenti = overridePresenti;
    }

    public static ParametroConOverride fromOverridePresenti(boolean overridePresenti) {

	ParametroConOverride par = new ParametroConOverride();
	par.setOverridePresenti(overridePresenti);
	return par;
    }
}
