package it.gruppoinit.pal.gp.core.service.helper;

public class RiepilogoHelper {

    public RiepilogoHelper(byte[] content, String nomeFile, String mimeType) {

	super();
	this.content = content;
	this.nomeFile = nomeFile;
	this.mimeType = mimeType;
    }

    private byte[] content;
    private String nomeFile;
    private String mimeType;

    public byte[] getContent() {

	return content;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public String getMimeType() {

	return mimeType;
    }
}
