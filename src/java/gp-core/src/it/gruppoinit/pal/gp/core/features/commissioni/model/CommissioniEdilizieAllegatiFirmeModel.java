package it.gruppoinit.pal.gp.core.features.commissioni.model;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.CommedilizieAllFirma;

public class CommissioniEdilizieAllegatiFirmeModel {

    private Integer id;
    private Date dataFirma;
    private String convocato;
    private String hashSha256;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Date getDataFirma() {

	return dataFirma;
    }

    public void setDataFirma(Date dataFirma) {

	this.dataFirma = dataFirma;
    }

    public String getConvocato() {

	return convocato;
    }

    public void setConvocato(String convocato) {

	this.convocato = convocato;
    }

    public String getHashSha256() {

	return hashSha256;
    }

    public void setHashSha256(String hashSha256) {

	this.hashSha256 = hashSha256;
    }

    public static CommissioniEdilizieAllegatiFirmeModel fromEntity(CommedilizieAllFirma entity) {

	CommissioniEdilizieAllegatiFirmeModel result = new CommissioniEdilizieAllegatiFirmeModel();
	result.setId(entity.getId().getCodice());
	result.setDataFirma(entity.getDataFirma());
	result.setHashSha256(entity.getHashSha256());
	result.setConvocato(entity.getCommedilizieAppello().getComponente());
	return result;
    }
}
