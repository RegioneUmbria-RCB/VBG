package it.gruppoinit.nlapec.util;

import it.gruppoinit.nlapec.schema.muta.Scheda2Type;
import it.gruppoinit.nlapec.schema.muta.SciaAvvioModificaAttivitaType;
import it.gruppoinit.nlapec.schema.muta.SciaSubCesSosRipCamAttivitaType;
import it.init.sigepro.rte.types.CampoDinamicoType;
import it.init.sigepro.rte.types.CampoSchedaType;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.ValoreCampoDinamicoType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParsingXML_MUTA {

    public SchedaType[] parsaXML_SciaSubCesSosRipCam(File f, SciaSubCesSosRipCamAttivitaType schedaMUTA, String nomeRootElement) {

	ArrayList<SchedaType> listaSchede = new ArrayList<SchedaType>();
	SchedaType[] schedeSTC = null;
	try {
	    String rootString = nomeRootElement;
	    DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	    Document doc = docBuilder.parse(f);
	    NodeList nodesRootList = doc.getChildNodes();
	    for (int j = 0; nodesRootList != null && j < nodesRootList.getLength(); j++) {
		Node nodoRoot = nodesRootList.item(j);
		if (nodoRoot.getNodeName().equalsIgnoreCase(nomeRootElement)) {
		    NodeList nodes = nodoRoot.getChildNodes();
		    for (int i = 0; nodes != null && i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String nodeName = node.getNodeName();
			if ("MODELLO_B".equalsIgnoreCase(nodeName)) {
			    NodeList nodeListModelloB = node.getChildNodes();
			    for (int k = 0; nodeListModelloB != null && k < nodeListModelloB.getLength(); k++) {
				Node nodeB = nodeListModelloB.item(k);
				if ("B_SCHEDA_B2".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("B_SCHEDA_B3".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("B_SCHEDA_B4".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("B_SCHEDA_B5".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("B_SCHEDA_B6".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("B_SCHEDA_B7".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("B_SCHEDA_B8".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				}
			    }
			} else if ("SCHEDA_2".equalsIgnoreCase(nodeName)) {
			    // le schede 2 possono avere molteplicità >1 quindi vanno gestite in maniera differente
			    //							SchedaType schedaSTC = buildSchedaSTC(rootString,node);
			    //							if (schedaSTC!=null) {listaSchede.add(schedaSTC);}
			} else if ("SCHEDA_3".equalsIgnoreCase(nodeName)) {
			    SchedaType schedaSTC = buildSchedaSTC(rootString, node);
			    if (schedaSTC != null) {
				listaSchede.add(schedaSTC);
			    }
			} else if ("SCHEDA_4".equalsIgnoreCase(nodeName)) {
			    SchedaType schedaSTC = buildSchedaSTC(rootString, node);
			    if (schedaSTC != null) {
				listaSchede.add(schedaSTC);
			    }
			} else if ("SCHEDA_5".equalsIgnoreCase(nodeName)) {
			    SchedaType schedaSTC = buildSchedaSTC(rootString, node);
			    if (schedaSTC != null) {
				listaSchede.add(schedaSTC);
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	}
	// gestisco la SCHEDA 2
	if (schedaMUTA.getSCHEDA2() != null && !schedaMUTA.getSCHEDA2().isEmpty()) {
	    buildScheda2(schedaMUTA.getSCHEDA2(), listaSchede);
	}
	if (listaSchede.size() > 0) {
	    schedeSTC = new SchedaType[listaSchede.size()];
	    int index = 0;
	    for (Iterator<SchedaType> iterator = listaSchede.iterator(); iterator.hasNext();) {
		SchedaType schedaType = iterator.next();
		schedeSTC[index] = schedaType;
		index++;
	    }
	}
	return schedeSTC;
    }

    private SchedaType buildSchedaSTC(String rootString, Node node) {

	String nomeScheda = node.getNodeName();
	SchedaType schedaSTC = new SchedaType();
	schedaSTC.setCodice(/*rootString+"."+*/nomeScheda);
	schedaSTC.setNome(nomeScheda);
	NodeList nodes = node.getChildNodes();
	for (int j = 0; nodes != null && j < nodes.getLength(); j++) {
	    Node nodo = nodes.item(j);
	    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
		if (isFoglia(nodo)) {
		    //System.out.println("<"+nodo.getNodeName()+">"+nodo.getTextContent()+"</"+nodo.getNodeName()+">");
		    CampoSchedaType campoDinamicoSTC = buildCampoSTC(nomeScheda + "." + nodo.getNodeName(), nodo.getTextContent());
		    schedaSTC.getCampi().add(campoDinamicoSTC);
		} else {
		    NodeList subNodesList = nodo.getChildNodes();
		    for (int k = 0; k < subNodesList.getLength(); k++) {
			Node subNode = subNodesList.item(k);
			if (subNode.getNodeType() == Node.ELEMENT_NODE && isFoglia(subNode)) {
			    //System.out.println("<"+nodo.getNodeName()+"."+subNode.getNodeName()+">"+subNode.getTextContent()+"</"+nodo.getNodeName()+"."+subNode.getNodeName()+">");
			    CampoSchedaType campoDinamicoSTC = buildCampoSTC(nomeScheda + "." + nodo.getNodeName() + "." + subNode.getNodeName(),
				    subNode.getTextContent());
			    schedaSTC.getCampi().add(campoDinamicoSTC);
			}
		    }
		}
	    }
	}
	return schedaSTC;
    }

    private boolean isFoglia(Node nodo) {

	if (nodo.hasChildNodes()) {
	    NodeList nodes = nodo.getChildNodes();
	    boolean trovato = false;
	    int index = 0;
	    while (!trovato && index < nodes.getLength()) {
		Node subNodo = nodes.item(index);
		if (subNodo.getNodeType() == Node.ELEMENT_NODE) {
		    trovato = true;
		}
		index++;
	    }
	    if (trovato) {
		return false;
	    } else {
		return true;
	    }
	} else {
	    return true;
	}
    }

    private static CampoSchedaType buildCampoSTC(String name, String value) {

	CampoSchedaType campoSchedaSTC = new CampoSchedaType();
	campoSchedaSTC.setCodice(name);
	campoSchedaSTC.setDescrizione(name);
	campoSchedaSTC.setCampoDinamico(new CampoDinamicoType());
	campoSchedaSTC.getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	campoSchedaSTC.getCampoDinamico().getValoreUtente().setNome(name);
	campoSchedaSTC.getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	campoSchedaSTC.getCampoDinamico().getValoreUtente().getValore().get(0).setCodice(value);
	campoSchedaSTC.getCampoDinamico().getValoreUtente().getValore().get(0).setDescrizione("");
	return campoSchedaSTC;
    }

    public SchedaType[] parsaXML_SciaAvvioModifica(File f, SciaAvvioModificaAttivitaType schedaMUTA, String nomeRootElement) {

	ArrayList<SchedaType> listaSchede = new ArrayList<SchedaType>();
	SchedaType[] schedeSTC = null;
	try {
	    String rootString = nomeRootElement;
	    DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	    Document doc = docBuilder.parse(f);
	    NodeList nodesRootList = doc.getChildNodes();
	    for (int j = 0; nodesRootList != null && j < nodesRootList.getLength(); j++) {
		Node nodoRoot = nodesRootList.item(j);
		if (nodoRoot.getNodeName().equalsIgnoreCase(nomeRootElement)) {
		    NodeList nodes = nodoRoot.getChildNodes();
		    for (int i = 0; nodes != null && i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String nodeName = node.getNodeName();
			if ("MODELLO_A".equalsIgnoreCase(nodeName)) {
			    NodeList nodeListModelloB = node.getChildNodes();
			    for (int k = 0; nodeListModelloB != null && k < nodeListModelloB.getLength(); k++) {
				Node nodeB = nodeListModelloB.item(k);
				if ("A_SCHEDA_A1".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("A_SCHEDA_A2".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("A_SCHEDA_A3".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("A_SCHEDA_A4".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("A_SCHEDA_A5".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("A_SCHEDA_A6".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				} else if ("A_DICHIARA".equalsIgnoreCase(nodeB.getNodeName())) {
				    SchedaType schedaSTC = buildSchedaSTC(/*rootString+"."+*/node.getNodeName(), nodeB);
				    if (schedaSTC != null) {
					listaSchede.add(schedaSTC);
				    }
				}
			    }
			} else if ("SCHEDA_2".equalsIgnoreCase(nodeName)) {
			    // le schede 2 possono avere molteplicità >1 quindi vanno gestite in maniera differente
			    //							SchedaType schedaSTC = buildSchedaSTC(rootString,node);
			    //							if (schedaSTC!=null) {listaSchede.add(schedaSTC);}
			} else if ("SCHEDA_3".equalsIgnoreCase(nodeName)) {
			    SchedaType schedaSTC = buildSchedaSTC(rootString, node);
			    if (schedaSTC != null) {
				listaSchede.add(schedaSTC);
			    }
			} else if ("SCHEDA_4".equalsIgnoreCase(nodeName)) {
			    SchedaType schedaSTC = buildSchedaSTC(rootString, node);
			    if (schedaSTC != null) {
				listaSchede.add(schedaSTC);
			    }
			} else if ("SCHEDA_5".equalsIgnoreCase(nodeName)) {
			    SchedaType schedaSTC = buildSchedaSTC(rootString, node);
			    if (schedaSTC != null) {
				listaSchede.add(schedaSTC);
			    }
			} else if ("SCHEDA_6".equalsIgnoreCase(nodeName)) {
			    SchedaType schedaSTC = buildSchedaSTC(rootString, node);
			    if (schedaSTC != null) {
				listaSchede.add(schedaSTC);
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	}
	// gestisco la SCHEDA 2
	if (schedaMUTA.getSCHEDA2() != null && !schedaMUTA.getSCHEDA2().isEmpty()) {
	    buildScheda2(schedaMUTA.getSCHEDA2(), listaSchede);
	}
	if (listaSchede.size() > 0) {
	    schedeSTC = new SchedaType[listaSchede.size()];
	    int index = 0;
	    for (Iterator<SchedaType> iterator = listaSchede.iterator(); iterator.hasNext();) {
		SchedaType schedaType = iterator.next();
		schedeSTC[index] = schedaType;
		index++;
	    }
	}
	return schedeSTC;
    }

    private void buildScheda2(List<Scheda2Type> scheda2List, ArrayList<SchedaType> listaSchedeSTC) {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	String nomeScheda = "SCHEDA_2";
	SchedaType schedaSTC = new SchedaType();
	schedaSTC.setCodice(nomeScheda);
	schedaSTC.setNome(nomeScheda);
	int numSchede = scheda2List.size();
	int numeroCampiInseriti = 0;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "CITTADINANZA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "CITTADINANZA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "CITTADINANZA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String value = scheda2List.get(i).getCITTADINANZA() == null ? "" : scheda2List.get(i).getCITTADINANZA().toString();
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setCodice(value);
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "CODICEFISCALE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "CODICEFISCALE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "CODICEFISCALE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getCODICEFISCALE(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COGNOME");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COGNOME");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COGNOME");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getCOGNOME(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COMMCORSO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COMMCORSO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COMMCORSO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).isCOMMCORSO() ? "true" : "false", ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COMMDATAFINE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COMMDATAFINE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COMMDATAFINE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String dateString = "";
	    if (scheda2List.get(i).getCOMMDATAFINE() != null) {
		Date date = scheda2List.get(i).getCOMMDATAFINE().toGregorianCalendar().getTime();
		dateString = formatter.format(date);
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(dateString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COMMNOMEISTITUTO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COMMNOMEISTITUTO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COMMNOMEISTITUTO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getCOMMNOMEISTITUTO(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COMMREGIONE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COMMREGIONE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COMMREGIONE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getCOMMREGIONE(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COMMSEDE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COMMSEDE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COMMSEDE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getCOMMSEDE(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COMMTIPO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COMMTIPO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COMMTIPO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getCOMMTIPO(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COMUNEDINASCITA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COMUNEDINASCITA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COMUNEDINASCITA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getCOMUNEDINASCITA(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "COMUNEDIRESIDENZA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "COMUNEDIRESIDENZA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "COMUNEDIRESIDENZA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getCOMUNEDIRESIDENZA(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "DATADINASCITA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "DATADINASCITA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "DATADINASCITA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String dateString = "";
	    if (scheda2List.get(i).getDATADINASCITA() != null) {
		Date date = scheda2List.get(i).getDATADINASCITA().toGregorianCalendar().getTime();
		dateString = formatter.format(date);
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(dateString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "DELEGATO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "DELEGATO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "DELEGATO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isDELEGATO();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "DELEGATOINDATA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "DELEGATOINDATA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "DELEGATOINDATA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String dateString = "";
	    if (scheda2List.get(i).getDELEGATOINDATA() != null) {
		Date date = scheda2List.get(i).getDELEGATOINDATA().toGregorianCalendar().getTime();
		dateString = formatter.format(date);
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(dateString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "DELEGATONOMESOCIETA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "DELEGATONOMESOCIETA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "DELEGATONOMESOCIETA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getDELEGATONOMESOCIETA(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "ISCRITTOREC");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "ISCRITTOREC");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "ISCRITTOREC");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isISCRITTOREC();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "ISCRITTORECCCIAA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "ISCRITTORECCCIAA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "ISCRITTORECCCIAA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getISCRITTORECCCIAA(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "ISCRITTORECDATA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "ISCRITTORECDATA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "ISCRITTORECDATA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String dateString = "";
	    if (scheda2List.get(i).getISCRITTORECDATA() != null) {
		Date date = scheda2List.get(i).getISCRITTORECDATA().toGregorianCalendar().getTime();
		dateString = formatter.format(date);
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(dateString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "ISCRITTORECNUM");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "ISCRITTORECNUM");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "ISCRITTORECNUM");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getISCRITTORECNUM(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "LEGALE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "LEGALE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "LEGALE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isLEGALE();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "LEGALENOMESOCIETA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "LEGALENOMESOCIETA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "LEGALENOMESOCIETA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getLEGALENOMESOCIETA(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "LUOGORESIDENZASTATO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "LUOGORESIDENZASTATO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "LUOGORESIDENZASTATO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String value = scheda2List.get(i).getLUOGORESIDENZASTATO() == null ? "" : scheda2List.get(i).getLUOGORESIDENZASTATO().toString();
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setCodice(value);
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "MEMBRO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "MEMBRO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "MEMBRO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isMEMBRO();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "NOME");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "NOME");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "NOME");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getNOME(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "PROVDIRESIDENZA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "PROVDIRESIDENZA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "PROVDIRESIDENZA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getPROVDIRESIDENZA(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "PROVINCIADINASCITA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "PROVINCIADINASCITA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "PROVINCIADINASCITA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getPROVINCIADINASCITA(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "RESIDENZACAP");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "RESIDENZACAP");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "RESIDENZACAP");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getRESIDENZACAP(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "RESIDENZANUM");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "RESIDENZANUM");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "RESIDENZANUM");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getRESIDENZANUM(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "RESIDENZAVIA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "RESIDENZAVIA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "RESIDENZAVIA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getRESIDENZAVIA(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "S2SESSO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "S2SESSO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "S2SESSO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String value = scheda2List.get(i).getS2SESSO() == null ? "" : scheda2List.get(i).getS2SESSO().toString();
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setCodice(value);
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOCIO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOCIO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOCIO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isSOCIO();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMDIPLOMADATAFINE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMDIPLOMADATAFINE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMDIPLOMADATAFINE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String dateString = "";
	    if (scheda2List.get(i).getSOMDIPLOMADATAFINE() != null) {
		Date date = scheda2List.get(i).getSOMDIPLOMADATAFINE().toGregorianCalendar().getTime();
		dateString = formatter.format(date);
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(dateString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMDIPLOMANOMEIST");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMDIPLOMANOMEIST");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMDIPLOMANOMEIST");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getSOMDIPLOMANOMEIST(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMDIPLOMASEDE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMDIPLOMASEDE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMDIPLOMASEDE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getSOMDIPLOMASEDE(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMDIPLOMATIPO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMDIPLOMATIPO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMDIPLOMATIPO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getSOMDIPLOMATIPO(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMDIPLOMATITOLO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMDIPLOMATITOLO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMDIPLOMATITOLO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getSOMDIPLOMATITOLO(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMMDIPLOMA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMMDIPLOMA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMMDIPLOMA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isSOMMDIPLOMA();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMMPRESTATO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMMPRESTATO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMMPRESTATO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isSOMMPRESTATO();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMPRESTCOADIUTORE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMPRESTCOADIUTORE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMPRESTCOADIUTORE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isSOMPRESTCOADIUTORE();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMPRESTDIPENDENTE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMPRESTDIPENDENTE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMPRESTDIPENDENTE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isSOMPRESTDIPENDENTE();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMPRESTINPROPRIO");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMPRESTINPROPRIO");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMPRESTINPROPRIO");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isSOMPRESTINPROPRIO();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMPRESTINPS");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMPRESTINPS");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMPRESTINPS");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getSOMPRESTINPS(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMPRESTINPSDEL");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMPRESTINPSDEL");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMPRESTINPSDEL");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String dateString = "";
	    if (scheda2List.get(i).getSOMPRESTINPSDEL() != null) {
		Date date = scheda2List.get(i).getSOMPRESTINPSDEL().toGregorianCalendar().getTime();
		dateString = formatter.format(date);
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(dateString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMPRESTNOMEIMP");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMPRESTNOMEIMP");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMPRESTNOMEIMP");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getSOMPRESTNOMEIMP(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMPRESTSEDEIMP");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMPRESTSEDEIMP");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMPRESTSEDEIMP");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(scheda2List.get(i).getSOMPRESTSEDEIMP(), ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "SOMPRESTSOCIOLAV");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "SOMPRESTSOCIOLAV");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "SOMPRESTSOCIOLAV");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isSOMPRESTSOCIOLAV();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "STATODINASCITA");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "STATODINASCITA");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "STATODINASCITA");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    String value = scheda2List.get(i).getSTATODINASCITA() == null ? " " : scheda2List.get(i).getSTATODINASCITA().toString();
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setCodice(value);
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	schedaSTC.getCampi().add(new CampoSchedaType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCampoDinamico(new CampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).setCodice(nomeScheda + "." + "TITOLARE");
	schedaSTC.getCampi().get(numeroCampiInseriti).setDescrizione(nomeScheda + "." + "TITOLARE");
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().setValoreUtente(new ValoreCampoDinamicoType());
	schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().setNome(nomeScheda + "." + "TITOLARE");
	for (int i = 0; i < numSchede; i++) {
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().add(new ElementoValoreCampoDinamicoType());
	    int posizione = schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().size() - 1;
	    Boolean b = scheda2List.get(i).isTITOLARE();
	    String booleanString = " ";
	    if (b == null) {
		booleanString = "false";
	    } else {
		booleanString = b.toString();
	    }
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione)
		    .setCodice(StringUtils.defaultIfEmpty(booleanString, ""));
	    schedaSTC.getCampi().get(numeroCampiInseriti).getCampoDinamico().getValoreUtente().getValore().get(posizione).setDescrizione("");
	}
	numeroCampiInseriti++;
	listaSchedeSTC.add(schedaSTC);
    }
}
