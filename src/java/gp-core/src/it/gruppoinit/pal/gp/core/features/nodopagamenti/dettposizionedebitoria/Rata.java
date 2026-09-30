package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.math.BigDecimal;

public class Rata implements Comparable<Rata> {

    private Integer numero;
    private String dataScadenza;
    private String raggruppamento;
    private String causale;
    private BigDecimal importo;

    public Integer getNumero() {

	return numero;
    }

    public String getDataScadenza() {

	return dataScadenza;
    }

    public String getCausale() {

	return causale;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public String getRaggruppamento() {

	return raggruppamento;
    }

    public static Rata fromDettagli(Integer numero, String dataScadenza, String raggruppamento, String causale, BigDecimal importo) {

	Rata rata = new Rata();
	rata.numero = numero;
	rata.dataScadenza = dataScadenza;
	rata.raggruppamento = raggruppamento;
	rata.causale = causale;
	rata.importo = importo;
	return rata;
    }

    public static Rata fromPagamentoDaNodoPagamenti(BigDecimal importo) {

	Rata rata = new Rata();
	rata.importo = importo;
	rata.numero = 0;
	rata.causale = "";
	return rata;
    }

    @Override
    public int compareTo(Rata o) {

	if (o == null) {
	    return 1;
	}
	int c = this.numero.compareTo(o.numero);
	if (c == 0) {
	    c = this.causale.equals(o.causale) ? 0 : 1;
	}
	if (c == 0) {
	    c = this.importo.compareTo(o.importo);
	}
	return c;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((causale == null) ? 0 : causale.hashCode());
	result = prime * result + ((dataScadenza == null) ? 0 : dataScadenza.hashCode());
	result = prime * result + ((importo == null) ? 0 : importo.hashCode());
	result = prime * result + ((numero == null) ? 0 : numero.hashCode());
	result = prime * result + ((raggruppamento == null) ? 0 : raggruppamento.hashCode());
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
	Rata other = (Rata) obj;
	if (causale == null) {
	    if (other.causale != null)
		return false;
	} else if (!causale.equals(other.causale))
	    return false;
	if (dataScadenza == null) {
	    if (other.dataScadenza != null)
		return false;
	} else if (dataScadenza.compareTo(other.dataScadenza) != 0)
	    return false;
	if (importo == null) {
	    if (other.importo != null)
		return false;
	} else if (!importo.equals(other.importo))
	    return false;
	if (numero == null) {
	    if (other.numero != null)
		return false;
	} else if (!numero.equals(other.numero))
	    return false;
	if (raggruppamento == null) {
	    if (other.raggruppamento != null)
		return false;
	} else if (!raggruppamento.equals(other.raggruppamento))
	    return false;
	return true;
    }
}
