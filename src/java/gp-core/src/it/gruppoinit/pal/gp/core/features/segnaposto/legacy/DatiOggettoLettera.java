package it.gruppoinit.pal.gp.core.features.segnaposto.legacy;

import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;

public class DatiOggettoLettera {

    private byte[] contenutoFile;
    private TipoFileEnum tipoFile;

    public DatiOggettoLettera(byte[] contenutoFile, TipoFileEnum tipoFile) {

	super();
	this.contenutoFile = contenutoFile;
	this.tipoFile = tipoFile;
    }

    public byte[] getContenutoFile() {

	return contenutoFile;
    }

    public TipoFileEnum getTipoFile() {

	return tipoFile;
    }
}