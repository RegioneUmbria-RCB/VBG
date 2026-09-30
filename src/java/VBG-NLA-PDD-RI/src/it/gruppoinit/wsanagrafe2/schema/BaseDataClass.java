
package it.gruppoinit.wsanagrafe2.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseDataClass complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseDataClass">
 *   &lt;complexContent>
 *     &lt;extension base="{http://init.sigepro.it}DataClass">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseDataClass")
@XmlSeeAlso({
    Anagrafe.class,
    ElencoInpsBase.class,
    Titoli.class,
    AnagrafeDocumenti.class,
    AnagrafeDyn2Dati.class,
    ElenchiProfessionaliBase.class,
    MercatiPresenzeStorico.class,
    VwProvince.class,
    Cittadinanza.class,
    Oggetti.class,
    FormeGiuridiche.class,
    ElencoInailBase.class,
    Comuni.class,
    AnagrafeDyn2ModelliT.class
})
public class BaseDataClass
    extends DataClass
{


}
