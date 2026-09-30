package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Metadata needed to process pagoPA payments.
 */
@ApiModel(description = "Metadata needed to process pagoPA payments.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentData implements Serializable {

    private static final long serialVersionUID = 3010754751516474970L;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("notice_number")
    private String noticeNumber;
    @JsonProperty("invalid_after_due_date")
    private Boolean invalidAfterDueDate = false;

    public PaymentData amount(Integer amount) {

	this.amount = amount;
	return this;
    }

    /**
     * Amount of payment in euro cent. PagoPA accepts up to 9999999999 euro cents. minimum: 1 maximum: 9999999999
     * 
     * @return amount
     */
    @ApiModelProperty(required = true, value = "Amount of payment in euro cent. PagoPA accepts up to 9999999999 euro cents.")
    @NotNull
    @Min(1)
    @Max(Integer.MAX_VALUE)
    public Integer getAmount() {

	return amount;
    }

    public void setAmount(Integer amount) {

	this.amount = amount;
    }

    public PaymentData noticeNumber(String noticeNumber) {

	this.noticeNumber = noticeNumber;
	return this;
    }

    /**
     * The field [\"Numero
     * Avviso\"](https://pagopa-specifichepagamenti.readthedocs.io/it/latest/_docs/Capitolo7.html#il-numero-avviso-e-larchivio-dei-pagamenti-in-attesa)
     * of pagoPa, needed to identify the payment. Format is `<aux digit (1n)>[<application code> (2n)]<codice IUV
     * (15|17n)>`. See [pagoPa
     * specs](https://www.agid.gov.it/sites/default/files/repository_files/specifiche_attuative_pagamenti_1_3_1_0.pdf)
     * for more info on this field and the IUV.
     * 
     * @return noticeNumber
     */
    @ApiModelProperty(required = true, value = "The field [\"Numero Avviso\"](https://pagopa-specifichepagamenti.readthedocs.io/it/latest/_docs/Capitolo7.html#il-numero-avviso-e-larchivio-dei-pagamenti-in-attesa) of pagoPa, needed to identify the payment. Format is `<aux digit (1n)>[<application code> (2n)]<codice IUV (15|17n)>`. See [pagoPa specs](https://www.agid.gov.it/sites/default/files/repository_files/specifiche_attuative_pagamenti_1_3_1_0.pdf) for more info on this field and the IUV.")
    @NotNull
    @Pattern(regexp = "^[0123][0-9]{17}$")
    public String getNoticeNumber() {

	return noticeNumber;
    }

    public void setNoticeNumber(String noticeNumber) {

	this.noticeNumber = noticeNumber;
    }

    public PaymentData invalidAfterDueDate(Boolean invalidAfterDueDate) {

	this.invalidAfterDueDate = invalidAfterDueDate;
	return this;
    }

    /**
     * Get invalidAfterDueDate
     * 
     * @return invalidAfterDueDate
     */
    @ApiModelProperty(value = "")
    public Boolean getInvalidAfterDueDate() {

	return invalidAfterDueDate;
    }

    public void setInvalidAfterDueDate(Boolean invalidAfterDueDate) {

	this.invalidAfterDueDate = invalidAfterDueDate;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	PaymentData paymentData = (PaymentData) o;
	return Objects.equals(this.amount, paymentData.amount) && Objects.equals(this.noticeNumber, paymentData.noticeNumber)
		&& Objects.equals(this.invalidAfterDueDate, paymentData.invalidAfterDueDate);
    }

    @Override
    public int hashCode() {

	return Objects.hash(amount, noticeNumber, invalidAfterDueDate);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class PaymentData {\n");
	sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
	sb.append("    noticeNumber: ").append(toIndentedString(noticeNumber)).append("\n");
	sb.append("    invalidAfterDueDate: ").append(toIndentedString(invalidAfterDueDate)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
