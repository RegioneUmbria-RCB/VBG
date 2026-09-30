
package it.alveo.ricalcoloaree.sigeprosecurity;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.</p>
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.</p>
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="applicationInfo" type="{http://sigeprosecurity.gruppoinit.it/schema}ApplicationInfoType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "applicationInfo"
})
@XmlRootElement(name = "GetApplicationInfoResponse")
public class GetApplicationInfoResponse {

    protected List<ApplicationInfoType> applicationInfo;

    /**
     * Gets the value of the applicationInfo property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicationInfo property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getApplicationInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApplicationInfoType }
     * </p>
     * 
     * 
     * @return
     *     The value of the applicationInfo property.
     */
    public List<ApplicationInfoType> getApplicationInfo() {
        if (applicationInfo == null) {
            applicationInfo = new ArrayList<>();
        }
        return this.applicationInfo;
    }

}
