package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Date;

public class MercatiAnnoGiornoDTO {

    private Integer annoReg;
    private Date giornoReg;

    public MercatiAnnoGiornoDTO() {

    }

    public Integer getAnnoReg() {

	return annoReg;
    }

    public void setAnnoReg(Integer annoReg) {

	this.annoReg = annoReg;
    }

    public Date getGiornoReg() {

	return giornoReg;
    }

    public void setGiornoReg(Date giornoReg) {

	this.giornoReg = giornoReg;
    }
}
