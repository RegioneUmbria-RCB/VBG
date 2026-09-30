package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class EsitoRicercaResponse  {
  
  
  private List<EsitoGetPosizioneDebitoriaResponse> lista = null;

  
  private Integer numTotalePosizioni = null;
 /**
   * Get lista
   * @return lista
  **/
  @XmlElement(name="lista")
  public List<EsitoGetPosizioneDebitoriaResponse> getLista() {
    return lista;
  }

  public void setLista(List<EsitoGetPosizioneDebitoriaResponse> lista) {
    this.lista = lista;
  }

  public EsitoRicercaResponse lista(List<EsitoGetPosizioneDebitoriaResponse> lista) {
    this.lista = lista;
    return this;
  }

  public EsitoRicercaResponse addListaItem(EsitoGetPosizioneDebitoriaResponse listaItem) {
    this.lista.add(listaItem);
    return this;
  }

 /**
   * Get numTotalePosizioni
   * @return numTotalePosizioni
  **/
  @XmlElement(name="numTotalePosizioni")
  public Integer getNumTotalePosizioni() {
    return numTotalePosizioni;
  }

  public void setNumTotalePosizioni(Integer numTotalePosizioni) {
    this.numTotalePosizioni = numTotalePosizioni;
  }

  public EsitoRicercaResponse numTotalePosizioni(Integer numTotalePosizioni) {
    this.numTotalePosizioni = numTotalePosizioni;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoRicercaResponse {\n");
    
    sb.append("    lista: ").append(toIndentedString(lista)).append("\n");
    sb.append("    numTotalePosizioni: ").append(toIndentedString(numTotalePosizioni)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

