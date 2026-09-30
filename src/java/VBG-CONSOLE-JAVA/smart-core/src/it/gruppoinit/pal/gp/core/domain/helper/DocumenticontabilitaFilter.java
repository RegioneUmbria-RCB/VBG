package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;

public class DocumenticontabilitaFilter {

    private PkId id;
    private String nomedocumento;
    private String documento;
    private Date data;
    private String mese;
    private String anno;
    private Date dataDa;
    private Date dataA;

    public DocumenticontabilitaFilter() {

	super();
	this.id = new PkId();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public String getNomedocumento() {

	return nomedocumento;
    }

    public void setNomedocumento(String nomedocumento) {

	this.nomedocumento = nomedocumento;
    }

    public String getDocumento() {

	return documento;
    }

    public void setDocumento(String documento) {

	this.documento = documento;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getMese() {

	return mese;
    }

    public void setMese(String mese) {

	this.mese = mese;
    }

    public String getAnno() {

	return anno;
    }

    public void setAnno(String anno) {

	this.anno = anno;
    }

    public Date getDataDa() {

	return dataDa;
    }

    public void setDataDa(Date dataDa) {

	this.dataDa = dataDa;
    }

    public Date getDataA() {

	return dataA;
    }

    public void setDataA(Date dataA) {

	this.dataA = dataA;
    }
}
