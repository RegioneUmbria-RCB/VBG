package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.math.BigDecimal;

import com.paevolution.ws.pagamenti_types.ImportoPagamentoWsInType;

public class ImportoBean {

    private BigDecimal importo;
    private String mappaturaNodoPag;

    public ImportoBean(BigDecimal importo, String mappaturaNodoPag) {

	super();
	if (importo.compareTo(BigDecimal.ZERO) < 1) {
	    throw new IllegalArgumentException(
		    "Non è possibile inviare al nodo dei pagamenti un importo pari a " + importo + " per la mappatura " + mappaturaNodoPag);
	}
	this.importo = importo;
	this.mappaturaNodoPag = mappaturaNodoPag;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public String getMappaturaNodoPag() {

	return mappaturaNodoPag;
    }

    public ImportoPagamentoWsInType toImportoPagamentoType() {

	ImportoPagamentoWsInType importoPagamentoType = new ImportoPagamentoWsInType();
	importoPagamentoType.setCodiceMappatura(mappaturaNodoPag);
	importoPagamentoType.setImporto(importo);
	return importoPagamentoType;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((importo == null) ? 0 : importo.hashCode());
	result = prime * result + ((mappaturaNodoPag == null) ? 0 : mappaturaNodoPag.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ImportoBean other = (ImportoBean) obj;
	if (importo == null) {
	    if (other.importo != null)
		return false;
	} else if (!importo.equals(other.importo))
	    return false;
	if (mappaturaNodoPag == null) {
	    if (other.mappaturaNodoPag != null)
		return false;
	} else if (!mappaturaNodoPag.equals(other.mappaturaNodoPag))
	    return false;
	return true;
    }

    public void addImporto(BigDecimal importoDaSommare) {

	if (this.importo == null) {
	    this.importo = BigDecimal.ZERO;
	}
	this.importo = this.importo.add(importoDaSommare);
    }

    public static ImportoBean copy(ImportoBean importoBean) {

	return new ImportoBean(importoBean.getImporto(), importoBean.getMappaturaNodoPag());
    }
}
