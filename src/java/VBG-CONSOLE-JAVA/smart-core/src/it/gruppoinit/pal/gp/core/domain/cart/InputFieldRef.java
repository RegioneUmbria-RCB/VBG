package it.gruppoinit.pal.gp.core.domain.cart;

public class InputFieldRef {

    private String idSemantico;
    private String idCampo;
    private Integer[] indexes;

    public String getIdSemantico() {

	return idSemantico;
    }

    public void setIdSemantico(String idSemantico) {

	this.idSemantico = idSemantico;
    }

    public String getIdCampo() {

	return idCampo;
    }

    public void setIdCampo(String idCampo) {

	this.idCampo = idCampo;
    }

    public Integer[] getIndexes() {

	return indexes;
    }

    public void setIndexes(Integer[] indexes) {

	this.indexes = indexes;
    }

    public void setIndiceIdSemantico(IndiceIdSemantico iis) {

	if (iis != null) {
	    this.indexes = iis.vettoreIndici();
	} else {
	    this.indexes = new Integer[0];
	}
    }
}
