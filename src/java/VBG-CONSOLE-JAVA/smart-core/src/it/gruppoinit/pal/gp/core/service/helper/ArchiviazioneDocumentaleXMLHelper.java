package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.infocamera.schema.legaldocs.tape.Argument;
import it.gruppoinit.infocamera.schema.legaldocs.tape.Control;
import it.gruppoinit.infocamera.schema.legaldocs.tape.DocumentClass;
import it.gruppoinit.infocamera.schema.legaldocs.tape.IndexValidation;
import it.gruppoinit.infocamera.schema.legaldocs.tape.ObjectFactory;
import it.gruppoinit.infocamera.schema.legaldocs.tape.Parameter;
import it.gruppoinit.infocamera.schema.legaldocs.tape.Parameters;
import it.gruppoinit.infocamera.schema.legaldocs.tape.ServiceValidation;
import it.gruppoinit.infocamera.schema.legaldocs.tape.Tape;
import it.gruppoinit.infocamera.schema.legaldocs.tape.TransportControls;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.io.File;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiviazioneDocumentaleXMLHelper {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioneDocumentaleXMLHelper.class);

    public static String getFileIndice(String fileNameIndexAttr, String classeDocumentale, Set<ArchiviazioneDocumentaleIndice> indici) {

	StringBuffer buff = new StringBuffer();
	buff.append("<ldmsg_index>");
	buff.append("<indici classe_documentale=\"").append(classeDocumentale).append("\" nome_file=\"").append(fileNameIndexAttr).append("\">");
	for (ArchiviazioneDocumentaleIndice indice : indici) {
	    buff.append("<indice nome='").append(indice.getNome()).append("' label='").append(indice.getLabel()).append("'>")
		    .append(indice.getValore()).append("</indice>");
	}
	buff.append("</indici>");
	buff.append("</ldmsg_index>");
	String fileIndice = buff.toString();
	log.debug("getFileIndice: {}", fileIndice);
	return fileIndice;
    }

    public static void writeFileAvvio(ArchiviazioneDocumentaleFileAvvio fAvvio, String folderName) throws Exception {

	try {
	    File fileAvvio = new File(folderName, "avvio.xml");
	    JAXBContext jc = JAXBContext.newInstance("it.gruppoinit.infocamera.schema.legaldocs.tape");
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
	    Tape tape = populateTapeObject(fAvvio);
	    marshaller.marshal(tape, fileAvvio);
	} catch (Exception e) {
	    log.error("writeFileAvvio", e);
	    throw e;
	}
    }

    public static Tape populateTapeObject(ArchiviazioneDocumentaleFileAvvio fAvvio) {

	ObjectFactory objFactory = new ObjectFactory();
	Tape tape = objFactory.createTape();
	DocumentClass documentClass = objFactory.createDocumentClass();
	documentClass.setService(fAvvio.getService());
	documentClass.setEndUserId(fAvvio.getEndUserId());
	documentClass.setFeedbackEmail(fAvvio.getFeedbackEmail());
	documentClass.setName(fAvvio.getNome());
	tape.setDocumentClass(documentClass);
	TransportControls transportControls = objFactory.createTransportControls();
	documentClass.setTransportControls(transportControls);
	Control controlFileTotali = objFactory.createControl();
	controlFileTotali.setId("numeroFileTotali");
	transportControls.getControl().add(controlFileTotali);
	Argument argumentFileTotali = objFactory.createArgument();
	argumentFileTotali.setName("number");
	argumentFileTotali.setValue(fAvvio.getNumeroFileTotali().toString());
	controlFileTotali.getArgument().add(argumentFileTotali);
	//
	Control controlFileIndice = objFactory.createControl();
	controlFileIndice.setId("numeroFileIndice");
	transportControls.getControl().add(controlFileIndice);
	Argument argumentFileIndice = objFactory.createArgument();
	argumentFileIndice.setName("number");
	argumentFileIndice.setValue(fAvvio.getNumeroFileIndice().toString());
	controlFileIndice.getArgument().add(argumentFileIndice);
	//
	for (ChiaveValoreBean<String, Integer> filePerEstensione : fAvvio.getFilePerEstensioneList()) {
	    Control controlNumeroFilePerEstensione = objFactory.createControl();
	    controlNumeroFilePerEstensione.setId("numeroFilePerEstensione");
	    transportControls.getControl().add(controlNumeroFilePerEstensione);
	    Argument argumentNumeroFilePerEstensioneNumber = objFactory.createArgument();
	    argumentNumeroFilePerEstensioneNumber.setName("number");
	    argumentNumeroFilePerEstensioneNumber.setValue(filePerEstensione.getValore().toString());
	    controlNumeroFilePerEstensione.getArgument().add(argumentNumeroFilePerEstensioneNumber);
	    Argument argumentNumeroFilePerEstensioneExtension = objFactory.createArgument();
	    argumentNumeroFilePerEstensioneExtension.setName("extension");
	    argumentNumeroFilePerEstensioneExtension.setValue(filePerEstensione.getChiave());
	    controlNumeroFilePerEstensione.getArgument().add(argumentNumeroFilePerEstensioneExtension);
	}
	ServiceValidation serviceValidation = objFactory.createServiceValidation();
	documentClass.setServiceValidation(serviceValidation);
	IndexValidation indexValidation = objFactory.createIndexValidation();
	serviceValidation.setIndexValidation(indexValidation);
	for (String fileIndice : fAvvio.getFileIndiceNameList()) {
	    indexValidation.getFile().add(fileIndice);
	}
	Parameters parameters = objFactory.createParameters();
	serviceValidation.setParameters(parameters);
	Parameter parameter = objFactory.createParameter();
	parameter.setId("sgd");
	parameter.setValue(fAvvio.getSgd());
	parameters.getParameter().add(parameter);
	return tape;
    }
}
