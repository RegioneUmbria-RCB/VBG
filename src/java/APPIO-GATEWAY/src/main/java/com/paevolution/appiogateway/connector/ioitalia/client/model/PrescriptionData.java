package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Metadata needed to process medical prescriptions.
 */
@ApiModel(description = "Metadata needed to process medical prescriptions.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionData implements Serializable {

    private static final long serialVersionUID = -6297017759589737584L;
    @JsonProperty("nre")
    private String nre;
    @JsonProperty("iup")
    private String iup;
    @JsonProperty("prescriber_fiscal_code")
    private String prescriberFiscalCode;

    public PrescriptionData nre(String nre) {

	this.nre = nre;
	return this;
    }

    /**
     * The field *Numero ricetta elettronica* identifies the medical prescription at national level.
     * 
     * @return nre
     */
    @ApiModelProperty(required = true, value = "The field *Numero ricetta elettronica* identifies the medical prescription at national level.")
    @NotNull
    @Size(min = 15, max = 15)
    public String getNre() {

	return nre;
    }

    public void setNre(String nre) {

	this.nre = nre;
    }

    public PrescriptionData iup(String iup) {

	this.iup = iup;
	return this;
    }

    /**
     * The field *Identificativo Unico di Prescrizione* identifies the medical prescription at regional level.
     * 
     * @return iup
     */
    @ApiModelProperty(value = "The field *Identificativo Unico di Prescrizione* identifies the medical prescription at regional level.")
    @Size(min = 1, max = 16)
    public String getIup() {

	return iup;
    }

    public void setIup(String iup) {

	this.iup = iup;
    }

    public PrescriptionData prescriberFiscalCode(String prescriberFiscalCode) {

	this.prescriberFiscalCode = prescriberFiscalCode;
	return this;
    }

    /**
     * Fiscal code of the Doctor that made the prescription.
     * 
     * @return prescriberFiscalCode
     */
    @ApiModelProperty(example = "TCNZRO80R13C555Y", value = "Fiscal code of the Doctor that made the prescription.")
    public String getPrescriberFiscalCode() {

	return prescriberFiscalCode;
    }

    public void setPrescriberFiscalCode(String prescriberFiscalCode) {

	this.prescriberFiscalCode = prescriberFiscalCode;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	PrescriptionData prescriptionData = (PrescriptionData) o;
	return Objects.equals(this.nre, prescriptionData.nre) && Objects.equals(this.iup, prescriptionData.iup)
		&& Objects.equals(this.prescriberFiscalCode, prescriptionData.prescriberFiscalCode);
    }

    @Override
    public int hashCode() {

	return Objects.hash(nre, iup, prescriberFiscalCode);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class PrescriptionData {\n");
	sb.append("    nre: ").append(toIndentedString(nre)).append("\n");
	sb.append("    iup: ").append(toIndentedString(iup)).append("\n");
	sb.append("    prescriberFiscalCode: ").append(toIndentedString(prescriberFiscalCode)).append("\n");
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
