package it.gruppoinit.nlapec.util;

import it.gruppoinit.nlapec.daticert.PecDaticert;
import it.gruppoinit.nlapec.daticert.PecDest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class PECMessageInfos {

    private static final Logger log = LoggerFactory.getLogger(PECMessageInfos.class);
    private Set<Certificate> signatures;
    private Document certificate;
    private PECBodyParts bodyParts;

    public Set<Certificate> getSignatures() {

	return signatures;
    }

    public void setSignatures(Set<Certificate> signatures) {

	this.signatures = signatures;
    }

    public Document getCertificate() {

	return certificate;
    }

    public void setCertificate(Document certificate) {

	this.certificate = certificate;
    }

    public PECBodyParts getBodyParts() {

	return bodyParts;
    }

    public void setBodyParts(PECBodyParts bodyParts) {

	this.bodyParts = bodyParts;
    }

    public PecDaticert getDatiCertDaXML() {

	PecDaticert dc = new PecDaticert();
	dc.setReturnValue(PecDaticert.INVALID);
	try {
	    if (certificate == null) {
		return dc;
	    }
	    NodeList nodes = certificate.getChildNodes();
	    for (int i = 0; nodes != null && i < nodes.getLength(); i++) {
		Node node = nodes.item(i);
		String nodeName = node.getNodeName();
		if ("postacert".equalsIgnoreCase(nodeName)) {
		    NamedNodeMap map = node.getAttributes();
		    if (map != null) {
			dc.setTipo(map.getNamedItem("tipo").getNodeValue());
			dc.setErrore(map.getNamedItem("errore").getNodeValue());
			NodeList subNodes = node.getChildNodes();
			for (int j = 0; subNodes != null && j < subNodes.getLength(); j++) {
			    Node subNode = subNodes.item(j);
			    String subNodeName = subNode.getNodeName();
			    if ("intestazione".equalsIgnoreCase(subNodeName)) {
				NodeList intNodes = subNode.getChildNodes();
				for (int k = 0; intNodes != null && k < intNodes.getLength(); k++) {
				    Node intNode = intNodes.item(k);
				    String intNodeName = intNode.getNodeName();
				    String nodeValue = intNode.hasChildNodes() ? intNode.getFirstChild().getNodeValue() : "";
				    if ("mittente".equalsIgnoreCase(intNodeName)) {
					dc.getIntestazione().setMittente(nodeValue);
				    } else if ("destinatari".equalsIgnoreCase(intNodeName)) {
					NamedNodeMap attr = intNode.getAttributes();
					PecDest dest = new PecDest();
					dest.setTipo(attr.getNamedItem("tipo").getNodeValue());
					dest.setEmail(nodeValue);
					dc.getIntestazione().addDestinatario(dest);
				    } else if ("risposte".equalsIgnoreCase(intNodeName)) {
					dc.getIntestazione().setRisposte(nodeValue);
				    } else if ("oggetto".equalsIgnoreCase(intNodeName)) {
					dc.getIntestazione().setOggetto(nodeValue);
				    }
				}
			    } else if ("dati".equalsIgnoreCase(subNodeName)) {
				NodeList intNodes = subNode.getChildNodes();
				for (int k = 0; intNodes != null && k < intNodes.getLength(); k++) {
				    Node intNode = intNodes.item(k);
				    String intNodeName = intNode.getNodeName();
				    String nodeValue = intNode.hasChildNodes() ? intNode.getFirstChild().getNodeValue() : "";
				    if ("identificativo".equalsIgnoreCase(intNodeName)) {
					dc.getDati().setIdentificativo(nodeValue);
				    } else if ("data".equalsIgnoreCase(intNodeName)) {
					String giorno = null;
					String ora = null;
					NamedNodeMap attr = intNode.getAttributes();
					String zona = attr.getNamedItem("zona").getNodeValue();
					NodeList dataNodes = intNode.getChildNodes();
					for (int l = 0; dataNodes != null && l < dataNodes.getLength(); l++) {
					    Node dataNode = dataNodes.item(l);
					    String dataNodeValue = dataNode.hasChildNodes() ? dataNode.getFirstChild().getNodeValue() : "";
					    if ("giorno".equalsIgnoreCase(dataNode.getNodeName())) {
						giorno = dataNodeValue;
					    } else if ("ora".equalsIgnoreCase(dataNode.getNodeName())) {
						ora = dataNodeValue;
					    }
					}
					Date d = DateUtil.getDate(giorno, ora, zona);
					dc.getDati().setData(d);
				    } else if ("msgid".equalsIgnoreCase(intNodeName)) {
					dc.getDati().setMsgid(nodeValue);
				    } else if ("ricezione".equalsIgnoreCase(intNodeName)) {
					PecDest dest = new PecDest();
					dest.setTipo("");
					dest.setEmail(nodeValue);
					dc.getDati().addRicezione(dest);
				    } else if ("consegna".equalsIgnoreCase(intNodeName)) {
					PecDest dest = new PecDest();
					dest.setTipo("");
					dest.setEmail(nodeValue);
					dc.getDati().addConsegna(dest);
				    } else if ("gestore-emittente".equalsIgnoreCase(intNodeName)) {
					dc.getDati().setGestoreEmittente(nodeValue);
				    } else if ("errore-esteso".equalsIgnoreCase(intNodeName)) {
					dc.getDati().setErroreEsteso(nodeValue);
				    } else if ("ricevuta".equalsIgnoreCase(intNodeName)) {
					NamedNodeMap attr = intNode.getAttributes();
					dc.getDati().setRicevuta(attr.getNamedItem("tipo").getNodeValue());
				    }
				}
			    }
			}
		    }
		}
	    }
	    dc.setReturnValue(PecDaticert.VALID);
	} catch (Exception e) {
	    log.error("getDatiCertDaXML(): {}", e.getMessage());
	}
	return dc;
    }

    public static void main(String[] args) throws Exception {

	try {
	    PECMessageInfos infos = new PECMessageInfos();
	    final InputStream idataCert = new FileInputStream("C:\\SVILUPPO\\WORKSPACES\\eclipse_helios_workspace\\nla-pec\\src\\daticert.xml");
	    final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    final DocumentBuilder parser = builderFactory.newDocumentBuilder();
	    final InputSource source = new InputSource(idataCert);
	    final Document domCert = parser.parse(source);
	    infos.setCertificate(domCert);
	    PecDaticert daticert = infos.getDatiCertDaXML();
	    System.out.println(daticert.getTipo());
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }
}