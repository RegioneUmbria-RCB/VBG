package it.paevolution.fileconverter2.health.model;

public class GotenbergHealthDetails {

    private GotenbergHealthElementStatus chromium;
    private GotenbergHealthElementStatus libreoffice;

    public GotenbergHealthElementStatus getChromium() {

	return chromium;
    }

    public void setChromium(GotenbergHealthElementStatus chromium) {

	this.chromium = chromium;
    }

    public GotenbergHealthElementStatus getLibreoffice() {

	return libreoffice;
    }

    public void setLibreoffice(GotenbergHealthElementStatus libreoffice) {

	this.libreoffice = libreoffice;
    }

    @Override
    public String toString() {

	return "chromium: {" + getChromium() + "}, libreoffice: {" + getLibreoffice() + "}";
    }
}
