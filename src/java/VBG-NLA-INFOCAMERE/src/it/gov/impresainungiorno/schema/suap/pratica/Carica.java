
package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per Carica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Carica"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="codice" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="ACP"/&gt;
 *             &lt;enumeration value="ACR"/&gt;
 *             &lt;enumeration value="ADP"/&gt;
 *             &lt;enumeration value="AF"/&gt;
 *             &lt;enumeration value="AFF"/&gt;
 *             &lt;enumeration value="AMD"/&gt;
 *             &lt;enumeration value="AMG"/&gt;
 *             &lt;enumeration value="AMM"/&gt;
 *             &lt;enumeration value="AMP"/&gt;
 *             &lt;enumeration value="AMS"/&gt;
 *             &lt;enumeration value="APR"/&gt;
 *             &lt;enumeration value="ART"/&gt;
 *             &lt;enumeration value="ASO"/&gt;
 *             &lt;enumeration value="ATI"/&gt;
 *             &lt;enumeration value="AUN"/&gt;
 *             &lt;enumeration value="AUP"/&gt;
 *             &lt;enumeration value="CA"/&gt;
 *             &lt;enumeration value="CAD"/&gt;
 *             &lt;enumeration value="CAG"/&gt;
 *             &lt;enumeration value="CC"/&gt;
 *             &lt;enumeration value="CCG"/&gt;
 *             &lt;enumeration value="CD"/&gt;
 *             &lt;enumeration value="CDG"/&gt;
 *             &lt;enumeration value="CDP"/&gt;
 *             &lt;enumeration value="CDS"/&gt;
 *             &lt;enumeration value="CDT"/&gt;
 *             &lt;enumeration value="CE"/&gt;
 *             &lt;enumeration value="CEP"/&gt;
 *             &lt;enumeration value="CES"/&gt;
 *             &lt;enumeration value="CF"/&gt;
 *             &lt;enumeration value="CG"/&gt;
 *             &lt;enumeration value="CGE"/&gt;
 *             &lt;enumeration value="CGS"/&gt;
 *             &lt;enumeration value="CI"/&gt;
 *             &lt;enumeration value="CLD"/&gt;
 *             &lt;enumeration value="CLR"/&gt;
 *             &lt;enumeration value="CLT"/&gt;
 *             &lt;enumeration value="CM"/&gt;
 *             &lt;enumeration value="CMS"/&gt;
 *             &lt;enumeration value="CNG"/&gt;
 *             &lt;enumeration value="COA"/&gt;
 *             &lt;enumeration value="COD"/&gt;
 *             &lt;enumeration value="COE"/&gt;
 *             &lt;enumeration value="COF"/&gt;
 *             &lt;enumeration value="COG"/&gt;
 *             &lt;enumeration value="COL"/&gt;
 *             &lt;enumeration value="COM"/&gt;
 *             &lt;enumeration value="CON"/&gt;
 *             &lt;enumeration value="COO"/&gt;
 *             &lt;enumeration value="COP"/&gt;
 *             &lt;enumeration value="COS"/&gt;
 *             &lt;enumeration value="COT"/&gt;
 *             &lt;enumeration value="COV"/&gt;
 *             &lt;enumeration value="CPC"/&gt;
 *             &lt;enumeration value="CPR"/&gt;
 *             &lt;enumeration value="CRT"/&gt;
 *             &lt;enumeration value="CS"/&gt;
 *             &lt;enumeration value="CSA"/&gt;
 *             &lt;enumeration value="CSG"/&gt;
 *             &lt;enumeration value="CSS"/&gt;
 *             &lt;enumeration value="CST"/&gt;
 *             &lt;enumeration value="CT"/&gt;
 *             &lt;enumeration value="CTE"/&gt;
 *             &lt;enumeration value="CU"/&gt;
 *             &lt;enumeration value="CUE"/&gt;
 *             &lt;enumeration value="CUF"/&gt;
 *             &lt;enumeration value="CUM"/&gt;
 *             &lt;enumeration value="CUV"/&gt;
 *             &lt;enumeration value="CVE"/&gt;
 *             &lt;enumeration value="CZ"/&gt;
 *             &lt;enumeration value="C01"/&gt;
 *             &lt;enumeration value="C02"/&gt;
 *             &lt;enumeration value="C03"/&gt;
 *             &lt;enumeration value="C07"/&gt;
 *             &lt;enumeration value="C08"/&gt;
 *             &lt;enumeration value="C09"/&gt;
 *             &lt;enumeration value="C11"/&gt;
 *             &lt;enumeration value="C12"/&gt;
 *             &lt;enumeration value="C13"/&gt;
 *             &lt;enumeration value="C14"/&gt;
 *             &lt;enumeration value="C15"/&gt;
 *             &lt;enumeration value="C16"/&gt;
 *             &lt;enumeration value="C17"/&gt;
 *             &lt;enumeration value="C18"/&gt;
 *             &lt;enumeration value="C19"/&gt;
 *             &lt;enumeration value="C20"/&gt;
 *             &lt;enumeration value="C21"/&gt;
 *             &lt;enumeration value="C22"/&gt;
 *             &lt;enumeration value="C23"/&gt;
 *             &lt;enumeration value="C26"/&gt;
 *             &lt;enumeration value="C28"/&gt;
 *             &lt;enumeration value="C29"/&gt;
 *             &lt;enumeration value="C32"/&gt;
 *             &lt;enumeration value="C34"/&gt;
 *             &lt;enumeration value="DA"/&gt;
 *             &lt;enumeration value="DAM"/&gt;
 *             &lt;enumeration value="DC"/&gt;
 *             &lt;enumeration value="DCO"/&gt;
 *             &lt;enumeration value="DCP"/&gt;
 *             &lt;enumeration value="DE"/&gt;
 *             &lt;enumeration value="DES"/&gt;
 *             &lt;enumeration value="DF"/&gt;
 *             &lt;enumeration value="DFI"/&gt;
 *             &lt;enumeration value="DG"/&gt;
 *             &lt;enumeration value="DI"/&gt;
 *             &lt;enumeration value="DIA"/&gt;
 *             &lt;enumeration value="DIM"/&gt;
 *             &lt;enumeration value="DIP"/&gt;
 *             &lt;enumeration value="DIR"/&gt;
 *             &lt;enumeration value="DLF"/&gt;
 *             &lt;enumeration value="DL2"/&gt;
 *             &lt;enumeration value="DMK"/&gt;
 *             &lt;enumeration value="DNC"/&gt;
 *             &lt;enumeration value="DP"/&gt;
 *             &lt;enumeration value="DR"/&gt;
 *             &lt;enumeration value="DRE"/&gt;
 *             &lt;enumeration value="DRG"/&gt;
 *             &lt;enumeration value="DRR"/&gt;
 *             &lt;enumeration value="DS"/&gt;
 *             &lt;enumeration value="DSA"/&gt;
 *             &lt;enumeration value="DT"/&gt;
 *             &lt;enumeration value="DTD"/&gt;
 *             &lt;enumeration value="DZ"/&gt;
 *             &lt;enumeration value="ELE"/&gt;
 *             &lt;enumeration value="EXS"/&gt;
 *             &lt;enumeration value="FAT"/&gt;
 *             &lt;enumeration value="FC"/&gt;
 *             &lt;enumeration value="FU"/&gt;
 *             &lt;enumeration value="GE"/&gt;
 *             &lt;enumeration value="GER"/&gt;
 *             &lt;enumeration value="GID"/&gt;
 *             &lt;enumeration value="GOV"/&gt;
 *             &lt;enumeration value="GSG"/&gt;
 *             &lt;enumeration value="IG"/&gt;
 *             &lt;enumeration value="IMN"/&gt;
 *             &lt;enumeration value="IMR"/&gt;
 *             &lt;enumeration value="IN"/&gt;
 *             &lt;enumeration value="IS"/&gt;
 *             &lt;enumeration value="LER"/&gt;
 *             &lt;enumeration value="LGR"/&gt;
 *             &lt;enumeration value="LGT"/&gt;
 *             &lt;enumeration value="LI"/&gt;
 *             &lt;enumeration value="LIG"/&gt;
 *             &lt;enumeration value="LRF"/&gt;
 *             &lt;enumeration value="LRT"/&gt;
 *             &lt;enumeration value="LR2"/&gt;
 *             &lt;enumeration value="LSA"/&gt;
 *             &lt;enumeration value="LUL"/&gt;
 *             &lt;enumeration value="MA"/&gt;
 *             &lt;enumeration value="MCA"/&gt;
 *             &lt;enumeration value="MCD"/&gt;
 *             &lt;enumeration value="MCE"/&gt;
 *             &lt;enumeration value="MCG"/&gt;
 *             &lt;enumeration value="MCS"/&gt;
 *             &lt;enumeration value="MCT"/&gt;
 *             &lt;enumeration value="MDC"/&gt;
 *             &lt;enumeration value="MED"/&gt;
 *             &lt;enumeration value="MGD"/&gt;
 *             &lt;enumeration value="MGE"/&gt;
 *             &lt;enumeration value="MGS"/&gt;
 *             &lt;enumeration value="MI"/&gt;
 *             &lt;enumeration value="MP"/&gt;
 *             &lt;enumeration value="MPP"/&gt;
 *             &lt;enumeration value="MS"/&gt;
 *             &lt;enumeration value="MSD"/&gt;
 *             &lt;enumeration value="NE"/&gt;
 *             &lt;enumeration value="OAS"/&gt;
 *             &lt;enumeration value="OCA"/&gt;
 *             &lt;enumeration value="OCO"/&gt;
 *             &lt;enumeration value="ODI"/&gt;
 *             &lt;enumeration value="OES"/&gt;
 *             &lt;enumeration value="OPA"/&gt;
 *             &lt;enumeration value="OPC"/&gt;
 *             &lt;enumeration value="OPN"/&gt;
 *             &lt;enumeration value="PA"/&gt;
 *             &lt;enumeration value="PAD"/&gt;
 *             &lt;enumeration value="PAF"/&gt;
 *             &lt;enumeration value="PB"/&gt;
 *             &lt;enumeration value="PC"/&gt;
 *             &lt;enumeration value="PCA"/&gt;
 *             &lt;enumeration value="PCD"/&gt;
 *             &lt;enumeration value="PCE"/&gt;
 *             &lt;enumeration value="PCG"/&gt;
 *             &lt;enumeration value="PCM"/&gt;
 *             &lt;enumeration value="PCO"/&gt;
 *             &lt;enumeration value="PCP"/&gt;
 *             &lt;enumeration value="PCS"/&gt;
 *             &lt;enumeration value="PCT"/&gt;
 *             &lt;enumeration value="PCV"/&gt;
 *             &lt;enumeration value="PDC"/&gt;
 *             &lt;enumeration value="PDI"/&gt;
 *             &lt;enumeration value="PDS"/&gt;
 *             &lt;enumeration value="PE"/&gt;
 *             &lt;enumeration value="PED"/&gt;
 *             &lt;enumeration value="PEO"/&gt;
 *             &lt;enumeration value="PEP"/&gt;
 *             &lt;enumeration value="PES"/&gt;
 *             &lt;enumeration value="PF"/&gt;
 *             &lt;enumeration value="PG"/&gt;
 *             &lt;enumeration value="PGC"/&gt;
 *             &lt;enumeration value="PGD"/&gt;
 *             &lt;enumeration value="PGE"/&gt;
 *             &lt;enumeration value="PGF"/&gt;
 *             &lt;enumeration value="PGS"/&gt;
 *             &lt;enumeration value="PGT"/&gt;
 *             &lt;enumeration value="PL"/&gt;
 *             &lt;enumeration value="PM"/&gt;
 *             &lt;enumeration value="PN"/&gt;
 *             &lt;enumeration value="PP"/&gt;
 *             &lt;enumeration value="PPP"/&gt;
 *             &lt;enumeration value="PPR"/&gt;
 *             &lt;enumeration value="PR"/&gt;
 *             &lt;enumeration value="PRA"/&gt;
 *             &lt;enumeration value="PRC"/&gt;
 *             &lt;enumeration value="PRE"/&gt;
 *             &lt;enumeration value="PRO"/&gt;
 *             &lt;enumeration value="PRP"/&gt;
 *             &lt;enumeration value="PRQ"/&gt;
 *             &lt;enumeration value="PRS"/&gt;
 *             &lt;enumeration value="PRT"/&gt;
 *             &lt;enumeration value="PS"/&gt;
 *             &lt;enumeration value="PSD"/&gt;
 *             &lt;enumeration value="PSE"/&gt;
 *             &lt;enumeration value="PSS"/&gt;
 *             &lt;enumeration value="PT"/&gt;
 *             &lt;enumeration value="PTE"/&gt;
 *             &lt;enumeration value="PTR"/&gt;
 *             &lt;enumeration value="RA"/&gt;
 *             &lt;enumeration value="RAF"/&gt;
 *             &lt;enumeration value="RAP"/&gt;
 *             &lt;enumeration value="RAS"/&gt;
 *             &lt;enumeration value="RAZ"/&gt;
 *             &lt;enumeration value="RC"/&gt;
 *             &lt;enumeration value="RCD"/&gt;
 *             &lt;enumeration value="RCF"/&gt;
 *             &lt;enumeration value="RCO"/&gt;
 *             &lt;enumeration value="RCS"/&gt;
 *             &lt;enumeration value="RDF"/&gt;
 *             &lt;enumeration value="RE"/&gt;
 *             &lt;enumeration value="RES"/&gt;
 *             &lt;enumeration value="RFM"/&gt;
 *             &lt;enumeration value="RG"/&gt;
 *             &lt;enumeration value="RIN"/&gt;
 *             &lt;enumeration value="RIT"/&gt;
 *             &lt;enumeration value="RPS"/&gt;
 *             &lt;enumeration value="RSS"/&gt;
 *             &lt;enumeration value="RSU"/&gt;
 *             &lt;enumeration value="RTC"/&gt;
 *             &lt;enumeration value="RV"/&gt;
 *             &lt;enumeration value="SA"/&gt;
 *             &lt;enumeration value="SAB"/&gt;
 *             &lt;enumeration value="SAO"/&gt;
 *             &lt;enumeration value="SAP"/&gt;
 *             &lt;enumeration value="SCA"/&gt;
 *             &lt;enumeration value="SCR"/&gt;
 *             &lt;enumeration value="SD"/&gt;
 *             &lt;enumeration value="SDR"/&gt;
 *             &lt;enumeration value="SEP"/&gt;
 *             &lt;enumeration value="SFC"/&gt;
 *             &lt;enumeration value="SFI"/&gt;
 *             &lt;enumeration value="SG"/&gt;
 *             &lt;enumeration value="SGE"/&gt;
 *             &lt;enumeration value="SIE"/&gt;
 *             &lt;enumeration value="SIP"/&gt;
 *             &lt;enumeration value="SIS"/&gt;
 *             &lt;enumeration value="SLA"/&gt;
 *             &lt;enumeration value="SLR"/&gt;
 *             &lt;enumeration value="SNA"/&gt;
 *             &lt;enumeration value="SNC"/&gt;
 *             &lt;enumeration value="SNP"/&gt;
 *             &lt;enumeration value="SNQ"/&gt;
 *             &lt;enumeration value="SOA"/&gt;
 *             &lt;enumeration value="SOC"/&gt;
 *             &lt;enumeration value="SOF"/&gt;
 *             &lt;enumeration value="SOL"/&gt;
 *             &lt;enumeration value="SON"/&gt;
 *             &lt;enumeration value="SOP"/&gt;
 *             &lt;enumeration value="SOR"/&gt;
 *             &lt;enumeration value="SOS"/&gt;
 *             &lt;enumeration value="SOT"/&gt;
 *             &lt;enumeration value="SOU"/&gt;
 *             &lt;enumeration value="SPR"/&gt;
 *             &lt;enumeration value="SQ"/&gt;
 *             &lt;enumeration value="SQU"/&gt;
 *             &lt;enumeration value="STE"/&gt;
 *             &lt;enumeration value="SVR"/&gt;
 *             &lt;enumeration value="TES"/&gt;
 *             &lt;enumeration value="TI"/&gt;
 *             &lt;enumeration value="TIT"/&gt;
 *             &lt;enumeration value="TI2"/&gt;
 *             &lt;enumeration value="TMI"/&gt;
 *             &lt;enumeration value="TPS"/&gt;
 *             &lt;enumeration value="TTE"/&gt;
 *             &lt;enumeration value="TU"/&gt;
 *             &lt;enumeration value="UM1"/&gt;
 *             &lt;enumeration value="UM2"/&gt;
 *             &lt;enumeration value="US"/&gt;
 *             &lt;enumeration value="VAD"/&gt;
 *             &lt;enumeration value="VCA"/&gt;
 *             &lt;enumeration value="VCD"/&gt;
 *             &lt;enumeration value="VCG"/&gt;
 *             &lt;enumeration value="VCO"/&gt;
 *             &lt;enumeration value="VDA"/&gt;
 *             &lt;enumeration value="VDC"/&gt;
 *             &lt;enumeration value="VDE"/&gt;
 *             &lt;enumeration value="VDF"/&gt;
 *             &lt;enumeration value="VDG"/&gt;
 *             &lt;enumeration value="VDI"/&gt;
 *             &lt;enumeration value="VDS"/&gt;
 *             &lt;enumeration value="VDT"/&gt;
 *             &lt;enumeration value="VDZ"/&gt;
 *             &lt;enumeration value="VED"/&gt;
 *             &lt;enumeration value="VGD"/&gt;
 *             &lt;enumeration value="VGE"/&gt;
 *             &lt;enumeration value="VGO"/&gt;
 *             &lt;enumeration value="VIC"/&gt;
 *             &lt;enumeration value="VID"/&gt;
 *             &lt;enumeration value="VIV"/&gt;
 *             &lt;enumeration value="VPA"/&gt;
 *             &lt;enumeration value="VPC"/&gt;
 *             &lt;enumeration value="VPE"/&gt;
 *             &lt;enumeration value="VPP"/&gt;
 *             &lt;enumeration value="VSD"/&gt;
 *             &lt;enumeration value="VSG"/&gt;
 *             &lt;enumeration value="992"/&gt;
 *             &lt;enumeration value="996"/&gt;
 *             &lt;enumeration value="997"/&gt;
 *             &lt;enumeration value="998"/&gt;
 *             &lt;enumeration value="999"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Carica", propOrder = {
    "value"
})
public class Carica {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice", required = true)
    protected String codice;

    /**
     * Recupera il valore della proprietà value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

}
