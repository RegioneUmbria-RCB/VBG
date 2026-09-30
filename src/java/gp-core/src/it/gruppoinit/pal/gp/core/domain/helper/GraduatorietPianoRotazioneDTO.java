package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class GraduatorietPianoRotazioneDTO {

    private PkId id;
    private MercatiUsoDTO mercatiUso;
    private MercatiDDTO mercatiD;
    private IstanzeDTO istanze;
    private GraduatoriedDTO graduatoried;

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public MercatiUsoDTO getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUsoDTO mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public MercatiDDTO getMercatiD() {

	return mercatiD;
    }

    public void setMercatiD(MercatiDDTO mercatiD) {

	this.mercatiD = mercatiD;
    }

    public IstanzeDTO getIstanze() {

	return istanze;
    }

    public void setIstanze(IstanzeDTO istanze) {

	this.istanze = istanze;
    }

    public GraduatoriedDTO getGraduatoried() {

	return graduatoried;
    }

    public void setGraduatoried(GraduatoriedDTO graduatoried) {

	this.graduatoried = graduatoried;
    }
}
