package it.gruppoinit.pal.gp.core.domain.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <webresult> <result>OK</result> <message>Job started</message> <id>311165ac-3619-4d5e-9761-3813d3477e2c</id>
 * </webresult>
 **/
@XmlRootElement(name = "webresult")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "result", "message", "id", })
public class PentahoJobResponse {

    @XmlElement(name = "result")
    private String result;
    @XmlElement(name = "message")
    private String message;
    @XmlElement(name = "id")
    private String id;

    public String getResult() {

	return result;
    }

    public void setResult(String result) {

	this.result = result;
    }

    public String getMessage() {

	return message;
    }

    public void setMessage(String message) {

	this.message = message;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }
}
