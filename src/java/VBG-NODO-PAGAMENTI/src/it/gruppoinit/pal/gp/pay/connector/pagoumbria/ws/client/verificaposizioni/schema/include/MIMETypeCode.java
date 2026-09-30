
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per MIMETypeCode.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="MIMETypeCode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="GIF_"/&gt;
 *     &lt;enumeration value="HTML"/&gt;
 *     &lt;enumeration value="JPEG"/&gt;
 *     &lt;enumeration value="LNK_"/&gt;
 *     &lt;enumeration value="MSWD"/&gt;
 *     &lt;enumeration value="MSEX"/&gt;
 *     &lt;enumeration value="MSPP"/&gt;
 *     &lt;enumeration value="PDF_"/&gt;
 *     &lt;enumeration value="PNG_"/&gt;
 *     &lt;enumeration value="TEXT"/&gt;
 *     &lt;enumeration value="XML_"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MIMETypeCode")
@XmlEnum
public enum MIMETypeCode {

    @XmlEnumValue("GIF_")
    GIF("GIF_"),
    HTML("HTML"),
    JPEG("JPEG"),
    @XmlEnumValue("LNK_")
    LNK("LNK_"),
    MSWD("MSWD"),
    MSEX("MSEX"),
    MSPP("MSPP"),
    @XmlEnumValue("PDF_")
    PDF("PDF_"),
    @XmlEnumValue("PNG_")
    PNG("PNG_"),
    TEXT("TEXT"),
    @XmlEnumValue("XML_")
    XML("XML_");
    private final String value;

    MIMETypeCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MIMETypeCode fromValue(String v) {
        for (MIMETypeCode c: MIMETypeCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
