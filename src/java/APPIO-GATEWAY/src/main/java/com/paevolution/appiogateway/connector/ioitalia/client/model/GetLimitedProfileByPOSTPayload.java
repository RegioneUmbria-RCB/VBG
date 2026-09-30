package com.paevolution.appiogateway.connector.ioitalia.client.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * GetLimitedProfileByPOSTPayload
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-24T13:31:55.506+01:00[Europe/Berlin]")
public class GetLimitedProfileByPOSTPayload {

    @JsonProperty("fiscal_code")
    private String fiscalCode;

    public GetLimitedProfileByPOSTPayload fiscalCode(String fiscalCode) {

	this.fiscalCode = fiscalCode;
	return this;
    }

    /**
     * User's fiscal code.
     * 
     * @return fiscalCode
     */
    @ApiModelProperty(example = "SPNDNL80R13C555X", required = true, value = "User's fiscal code.")
    @NotNull
    public String getFiscalCode() {

	return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {

	this.fiscalCode = fiscalCode;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	GetLimitedProfileByPOSTPayload getLimitedProfileByPOSTPayload = (GetLimitedProfileByPOSTPayload) o;
	return Objects.equals(this.fiscalCode, getLimitedProfileByPOSTPayload.fiscalCode);
    }

    @Override
    public int hashCode() {

	return Objects.hash(fiscalCode);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class GetLimitedProfileByPOSTPayload {\n");
	sb.append("    fiscalCode: ").append(toIndentedString(fiscalCode)).append("\n");
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
