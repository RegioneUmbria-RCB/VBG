/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.cart;

import it.eng.suap.xengine.model.modulistica.EspressioneType;
import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Classe che memorizza informazioni sullo stao attivo/disattivo dei singoli campi della modulistica tenendo conto degli
 * indici per i campi multipli
 * 
 * @author francol
 *
 */
public class MetadatiCampo implements Comparable<MetadatiCampo> {

    private String idSemantico;
    private String idCampo;
    private Boolean attivo;
    private IndiceIdSemantico indice;
     private EspressioneType condizioneAttivo;

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

    public Boolean isAttivo() {

	return attivo;
    }

    public void setAttivo(Boolean attivo) {

	this.attivo = attivo;
    }

    public IndiceIdSemantico getIndice() {

	return indice;
    }

    public void setIndice(IndiceIdSemantico indice) {

	this.indice = createCopy(indice);
    }
    
    private IndiceIdSemantico createCopy(IndiceIdSemantico index){
	IndiceIdSemantico copy = new IndiceIdSemantico();
	if(index != null){
	    for (int i = 0; i < index.contaLivelli(); i++) {
		copy.incrementaLivello(new Object());
		for (int j = 0; j < index.getIndicePerLivello(i); j++) {
		    copy.incrementaIndice();
		}
	    }
	}
	return copy;
    }

    public EspressioneType getCondizioneAttivo() {

	return condizioneAttivo;
    }

    public void setCondizioneAttivo(EspressioneType condizioneAttivo) {

	this.condizioneAttivo = condizioneAttivo;
    }

    @Override
    public int compareTo(MetadatiCampo o) {

	int retVal = 1;
	if (o != null) {
	    retVal = StringUtils.defaultString(this.idCampo).compareTo(StringUtils.defaultString(o.getIdCampo()));
	    if (retVal == 0) {
		if (this.indice == null) {
		    if (o.getIndice() == null) {
			retVal = 0;
		    } else {
			retVal = -1;
		    }
		} else {
		    if (o.getIndice() == null) {
			retVal = 1;
		    } else {
			Integer[] ix = this.indice.vettoreIndici();
			Integer[] ixcp = o.getIndice().vettoreIndici();
			int indexesIndex = 0;
			while (retVal == 0) {
			    if (indexesIndex >= ix.length) {
				retVal = -1;
			    } else if (indexesIndex >= ixcp.length) {
				retVal = 1;
			    } else {
				retVal = ix[indexesIndex].compareTo(ixcp[indexesIndex]);
			    }
			    indexesIndex++;
			}
		    }
		}
	    }
	}
	return retVal;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idCampo == null) ? 0 : idCampo.hashCode());
	result = prime * result + ((indice == null) ? 0 : indice.hashCode());
	return result;
    }

    @Override
    /**
     * i metadati sono considerati come relativi allo stesso campo solo se sono uguali: l'id semantico, l'id e l'indice
     * Questo perchè nella modulistica si possono trovare più occorrenze di campi associati allo stesso id semantico (usati come alternativi)
     * o perchè per errori della modulistica si possono avere campi con id semantici diversi associati allo stesso id.
     */
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	MetadatiCampo other = (MetadatiCampo) obj;
	if (idSemantico == null) {
	    if (other.idSemantico != null)
		return false;
	} else if (!idSemantico.equals(other.idSemantico))
	    return false;
	if (idCampo == null) {
	    if (other.idCampo != null)
		return false;
	} else if (!idCampo.equals(other.idCampo))
	    return false;
	if (indice == null) {
	    if (other.indice != null)
		return false;
	} else if (!indice.equals(other.indice))
	    return false;
	return true;
    }
    
    public Boolean elaboraStatoCampoAttivo(DatiDomandaCart datiDomanda){
	//la regola di attivazione viene elaborata solo se non è ancora noto lo stato attivo/disattivo del campo
	Boolean retVal = this.attivo;
	if(this.attivo == null){
	    if (this.condizioneAttivo != null) {
		this.attivo = CartModuloHelper.evaluateExpression(this.condizioneAttivo, datiDomanda, this.indice);
		retVal = this.attivo;
	    }
	    else{
		//in mancanza di informazioni il campo di default è considerato attivo
		retVal = Boolean.TRUE;
	    }
	}
	return retVal;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder(getIdCampo());
	IndiceIdSemantico index = getIndice() != null ? getIndice() : new IndiceIdSemantico();
	sb.append(index);
	if(StringUtils.isNotBlank(getIdSemantico())){
	    sb.append(getIdSemantico());
	}
	sb.append(":");
	sb.append(isAttivo());
	return sb.toString();
    }

}
