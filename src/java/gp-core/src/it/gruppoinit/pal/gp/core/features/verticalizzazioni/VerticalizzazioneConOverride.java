package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VerticalizzazioneConOverride {

    @XmlElement(name = "supportaoverride")
    private boolean supportaOverride;

    public boolean isSupportaOverride() {

	return supportaOverride;
    }

    public void setSupportaOverride(boolean supportaOverride) {

	this.supportaOverride = supportaOverride;
    }

    public static VerticalizzazioneConOverride fromSupportaOverride(boolean supportaOverride) {

	VerticalizzazioneConOverride vert = new VerticalizzazioneConOverride();
	vert.setSupportaOverride(supportaOverride);
	return vert;
    }
}
