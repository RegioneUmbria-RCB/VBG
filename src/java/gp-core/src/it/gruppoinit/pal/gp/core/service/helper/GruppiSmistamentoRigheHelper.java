package it.gruppoinit.pal.gp.core.service.helper;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GruppiSmistamentoRigheHelper {

    private int riga;
    private String gruppo1;
    private String gruppo2;
    private String gruppo3;
    private Integer codiceAlberoproc;
    private Integer codiceProceduraScia;
    private Integer codiceProceduraOrdinario;

    public String getGruppo1() {

	return gruppo1;
    }

    public void setGruppo1(String gruppo1) {

	this.gruppo1 = gruppo1;
    }

    public String getGruppo2() {

	return gruppo2;
    }

    public void setGruppo2(String gruppo2) {

	this.gruppo2 = gruppo2;
    }

    public String getGruppo3() {

	return gruppo3;
    }

    public void setGruppo3(String gruppo3) {

	this.gruppo3 = gruppo3;
    }

    public Integer getCodiceAlberoproc() {

	return codiceAlberoproc;
    }

    public void setCodiceAlberoproc(Integer codiceAlberoproc) {

	this.codiceAlberoproc = codiceAlberoproc;
    }

    public Integer getCodiceProceduraScia() {

	return codiceProceduraScia;
    }

    public void setCodiceProceduraScia(Integer codiceProceduraScia) {

	this.codiceProceduraScia = codiceProceduraScia;
    }

    public Integer getCodiceProceduraOrdinario() {

	return codiceProceduraOrdinario;
    }

    public void setCodiceProceduraOrdinario(Integer codiceProceduraOrdinario) {

	this.codiceProceduraOrdinario = codiceProceduraOrdinario;
    }

    public int getRiga() {

	return riga;
    }

    public void setRiga(int riga) {

	this.riga = riga;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
