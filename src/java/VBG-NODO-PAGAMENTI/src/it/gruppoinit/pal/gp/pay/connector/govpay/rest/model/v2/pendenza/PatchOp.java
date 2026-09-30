package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatchOp", propOrder = { "op", "path", "value", })
public class PatchOp {

    @XmlElement(name = "op")
    private OpEnum op = null;
    @XmlElement(name = "path")
    private String path = null;
    @XmlElement(name = "value")
    private String value = null;

    /**
     * Operazione da eseguire
     */
    @XmlType(name = "OpEnum")
    @XmlEnum
    public enum OpEnum {

	@XmlEnumValue("ADD")
	ADD("ADD"),
	@XmlEnumValue("DELETE")
	DELETE("DELETE"),
	@XmlEnumValue("REPLACE")
	REPLACE("REPLACE");

	private String value;

	OpEnum(String value) {

	    this.value = value;
	}

	@Override
	public String toString() {

	    return String.valueOf(value);
	}

	public static OpEnum fromValue(String text) {

	    for (OpEnum b : OpEnum.values()) {
		if (String.valueOf(b.value).equals(text)) {
		    return b;
		}
	    }
	    return null;
	}
    }

    /**
     * Operazione da eseguire
     **/
    public PatchOp op(OpEnum op) {

	this.op = op;
	return this;
    }

    public OpEnum getOp() {

	return op;
    }

    public void setOp(OpEnum op) {

	this.op = op;
    }

    /**
     * path dell'oggetto dell'operazione
     **/
    public PatchOp path(String path) {

	this.path = path;
	return this;
    }

    public String getPath() {

	return path;
    }

    public void setPath(String path) {

	this.path = path;
    }

    /**
     * valore del'oggetto dell'operazione
     **/
    public PatchOp value(String value) {

	this.value = value;
	return this;
    }

    public String getValue() {

	return value;
    }

    public void setValue(String value) {

	this.value = value;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	PatchOp patchOp = (PatchOp) o;
	return Objects.equals(op, patchOp.op) && Objects.equals(path, patchOp.path) && Objects.equals(value, patchOp.value);
    }

    @Override
    public int hashCode() {

	return Objects.hash(op, path, value);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class PatchOp {\n");
	sb.append("    op: ").append(toIndentedString(op)).append("\n");
	sb.append("    path: ").append(toIndentedString(path)).append("\n");
	sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
