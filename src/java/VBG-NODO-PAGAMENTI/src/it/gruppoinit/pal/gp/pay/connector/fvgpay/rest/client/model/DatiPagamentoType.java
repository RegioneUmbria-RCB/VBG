package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class DatiPagamentoType  {
  
  
  private String canalePagamento = null;

  
  private Date dataPagamento = null;

  
  private ImportoType importoPagato = null;
 /**
   * Get canalePagamento
   * @return canalePagamento
  **/
  @XmlElement(name="canalePagamento")
  public String getCanalePagamento() {
    return canalePagamento;
  }

  public void setCanalePagamento(String canalePagamento) {
    this.canalePagamento = canalePagamento;
  }

  public DatiPagamentoType canalePagamento(String canalePagamento) {
    this.canalePagamento = canalePagamento;
    return this;
  }

 /**
   * Get dataPagamento
   * @return dataPagamento
  **/
  @XmlElement(name="dataPagamento")
  public Date getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(Date dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public DatiPagamentoType dataPagamento(Date dataPagamento) {
    this.dataPagamento = dataPagamento;
    return this;
  }

 /**
   * Get importoPagato
   * @return importoPagato
  **/
  @XmlElement(name="importoPagato")
  public ImportoType getImportoPagato() {
    return importoPagato;
  }

  public void setImportoPagato(ImportoType importoPagato) {
    this.importoPagato = importoPagato;
  }

  public DatiPagamentoType importoPagato(ImportoType importoPagato) {
    this.importoPagato = importoPagato;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiPagamentoType {\n");
    
    sb.append("    canalePagamento: ").append(toIndentedString(canalePagamento)).append("\n");
    sb.append("    dataPagamento: ").append(toIndentedString(dataPagamento)).append("\n");
    sb.append("    importoPagato: ").append(toIndentedString(importoPagato)).append("\n");
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

