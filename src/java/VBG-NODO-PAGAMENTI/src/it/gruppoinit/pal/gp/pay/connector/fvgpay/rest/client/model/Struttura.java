package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class Struttura  {
  
  
  private ListaClassificazioni listaClassificazioni = null;

  
  private ListaRiferimentiVistaStruttura listaRiferimentiViste = null;

  
  private ListaSedi listaSedi = null;

  
  private StrutturaBase strutturaBase = null;
 /**
   * Get listaClassificazioni
   * @return listaClassificazioni
  **/
  @XmlElement(name="listaClassificazioni")
  public ListaClassificazioni getListaClassificazioni() {
    return listaClassificazioni;
  }

  public void setListaClassificazioni(ListaClassificazioni listaClassificazioni) {
    this.listaClassificazioni = listaClassificazioni;
  }

  public Struttura listaClassificazioni(ListaClassificazioni listaClassificazioni) {
    this.listaClassificazioni = listaClassificazioni;
    return this;
  }

 /**
   * Get listaRiferimentiViste
   * @return listaRiferimentiViste
  **/
  @XmlElement(name="listaRiferimentiViste")
  public ListaRiferimentiVistaStruttura getListaRiferimentiViste() {
    return listaRiferimentiViste;
  }

  public void setListaRiferimentiViste(ListaRiferimentiVistaStruttura listaRiferimentiViste) {
    this.listaRiferimentiViste = listaRiferimentiViste;
  }

  public Struttura listaRiferimentiViste(ListaRiferimentiVistaStruttura listaRiferimentiViste) {
    this.listaRiferimentiViste = listaRiferimentiViste;
    return this;
  }

 /**
   * Get listaSedi
   * @return listaSedi
  **/
  @XmlElement(name="listaSedi")
  public ListaSedi getListaSedi() {
    return listaSedi;
  }

  public void setListaSedi(ListaSedi listaSedi) {
    this.listaSedi = listaSedi;
  }

  public Struttura listaSedi(ListaSedi listaSedi) {
    this.listaSedi = listaSedi;
    return this;
  }

 /**
   * Get strutturaBase
   * @return strutturaBase
  **/
  @XmlElement(name="strutturaBase")
  public StrutturaBase getStrutturaBase() {
    return strutturaBase;
  }

  public void setStrutturaBase(StrutturaBase strutturaBase) {
    this.strutturaBase = strutturaBase;
  }

  public Struttura strutturaBase(StrutturaBase strutturaBase) {
    this.strutturaBase = strutturaBase;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Struttura {\n");
    
    sb.append("    listaClassificazioni: ").append(toIndentedString(listaClassificazioni)).append("\n");
    sb.append("    listaRiferimentiViste: ").append(toIndentedString(listaRiferimentiViste)).append("\n");
    sb.append("    listaSedi: ").append(toIndentedString(listaSedi)).append("\n");
    sb.append("    strutturaBase: ").append(toIndentedString(strutturaBase)).append("\n");
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

