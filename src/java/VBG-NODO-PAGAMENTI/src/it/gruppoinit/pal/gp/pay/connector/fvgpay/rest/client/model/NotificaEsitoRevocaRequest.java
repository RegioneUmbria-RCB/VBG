package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
  * Descrive l'accettazione o il rifiuto di una richiesta di revoca proveniente dal nodo. Il sistema comporrà l'ER da mandare al nodo 
 **/

public class NotificaEsitoRevocaRequest  {
  
  
  private Date dataRevoca = null;


@XmlType(name="EsitoEnum")
@XmlEnum(String.class)
public enum EsitoEnum {

@XmlEnumValue("accettata") ACCETTATA(String.valueOf("accettata")), @XmlEnumValue("rifiutata") RIFIUTATA(String.valueOf("rifiutata")), @XmlEnumValue("parzialmente_accettata") PARZIALMENTE_ACCETTATA(String.valueOf("parzialmente_accettata"));


    private String value;

    EsitoEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static EsitoEnum fromValue(String v) {
        for (EsitoEnum b : EsitoEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  
 /**
   * Indica se si accetta la revova (true) oppure no (false)
  **/
  private EsitoEnum esito = null;

  
 /**
   * Se si tratta di una rata specificare iuv
  **/
  private String idDebito = null;

  
  private String idEnte = null;

  
  private String idServizio = null;

  
 /**
   * Obbligatorio se si tratta di una rata
  **/
  private String iuv = null;
 /**
   * Get dataRevoca
   * @return dataRevoca
  **/
  @XmlElement(name="data_revoca")
  public Date getDataRevoca() {
    return dataRevoca;
  }

  public void setDataRevoca(Date dataRevoca) {
    this.dataRevoca = dataRevoca;
  }

  public NotificaEsitoRevocaRequest dataRevoca(Date dataRevoca) {
    this.dataRevoca = dataRevoca;
    return this;
  }

 /**
   * Indica se si accetta la revova (true) oppure no (false)
   * @return esito
  **/
  @XmlElement(name="esito")
  public String getEsito() {
    if (esito == null) {
      return null;
    }
    return esito.value();
  }

  public void setEsito(EsitoEnum esito) {
    this.esito = esito;
  }

  public NotificaEsitoRevocaRequest esito(EsitoEnum esito) {
    this.esito = esito;
    return this;
  }

 /**
   * Se si tratta di una rata specificare iuv
   * @return idDebito
  **/
  @XmlElement(name="id_debito")
  public String getIdDebito() {
    return idDebito;
  }

  public void setIdDebito(String idDebito) {
    this.idDebito = idDebito;
  }

  public NotificaEsitoRevocaRequest idDebito(String idDebito) {
    this.idDebito = idDebito;
    return this;
  }

 /**
   * Get idEnte
   * @return idEnte
  **/
  @XmlElement(name="id_ente")
  public String getIdEnte() {
    return idEnte;
  }

  public void setIdEnte(String idEnte) {
    this.idEnte = idEnte;
  }

  public NotificaEsitoRevocaRequest idEnte(String idEnte) {
    this.idEnte = idEnte;
    return this;
  }

 /**
   * Get idServizio
   * @return idServizio
  **/
  @XmlElement(name="id_servizio")
  public String getIdServizio() {
    return idServizio;
  }

  public void setIdServizio(String idServizio) {
    this.idServizio = idServizio;
  }

  public NotificaEsitoRevocaRequest idServizio(String idServizio) {
    this.idServizio = idServizio;
    return this;
  }

 /**
   * Obbligatorio se si tratta di una rata
   * @return iuv
  **/
  @XmlElement(name="iuv")
  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  public NotificaEsitoRevocaRequest iuv(String iuv) {
    this.iuv = iuv;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotificaEsitoRevocaRequest {\n");
    
    sb.append("    dataRevoca: ").append(toIndentedString(dataRevoca)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    idDebito: ").append(toIndentedString(idDebito)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    idServizio: ").append(toIndentedString(idServizio)).append("\n");
    sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
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

