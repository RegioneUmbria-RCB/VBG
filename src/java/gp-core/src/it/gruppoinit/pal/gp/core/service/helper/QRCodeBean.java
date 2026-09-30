package it.gruppoinit.pal.gp.core.service.helper;

public class QRCodeBean {

    private byte[] image;
    private String url;

    public byte[] getImage() {

	return image;
    }

    public void setImage(byte[] image) {

	this.image = image;
    }

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }
}
