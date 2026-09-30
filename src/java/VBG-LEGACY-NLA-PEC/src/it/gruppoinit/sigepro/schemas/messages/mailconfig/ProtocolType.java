
package it.gruppoinit.sigepro.schemas.messages.mailconfig;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for protocolType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="protocolType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SMTP"/>
 *     &lt;enumeration value="SMTP_SSL"/>
 *     &lt;enumeration value="SSMTP_SMTPS"/>
 *     &lt;enumeration value="POP3"/>
 *     &lt;enumeration value="IMAP"/>
 *     &lt;enumeration value="SSL_POP3"/>
 *     &lt;enumeration value="SSL_IMAP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "protocolType")
@XmlEnum
public enum ProtocolType {

    SMTP("SMTP"),
    SMTP_SSL("SMTP_SSL"),
    SSMTP_SMTPS("SSMTP_SMTPS"),
    @XmlEnumValue("POP3")
    POP_3("POP3"),
    IMAP("IMAP"),
    @XmlEnumValue("SSL_POP3")
    SSL_POP_3("SSL_POP3"),
    SSL_IMAP("SSL_IMAP");
    private final String value;

    ProtocolType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProtocolType fromValue(String v) {
        for (ProtocolType c: ProtocolType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
