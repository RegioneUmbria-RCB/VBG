//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation,
// v2.2.8-b130911.1802
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine.
// Generato il: 2022.01.17 alle 10:12:21 AM CET
//
package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schemanotify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Richesta utilizzata per notificare il pagamento di un debit
 * 
 * <p>
 * Classe Java per RichiestaNotificaPagamentoDebito complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaNotificaPagamentoDebito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chiaviDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/notify/1_2}ctChiaviDebito"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RichiestaNotificaPagamentoDebito")
public class RichiestaNotificaPagamentoDebito extends CtChiaviDebito{

   
}
