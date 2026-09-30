package it.main;

import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

public class MarshallRiepilogoPratica {

    public static void main(String[] args) {

	//	DataSource fds = new FileDataSource("C:/Temp/02313821007-20122018-1348.SUAP.XML");
	//	DataHandler handler = new DataHandler(fds);
	//	try {
	//	    byte[] xml_b = MarshallRiepilogoPratica.dataHandlerToBytes(handler);
	//	    JAXBContext jc = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
	//	    Unmarshaller u = jc.createUnmarshaller();
	//	    RiepilogoPraticaSUAP response = (RiepilogoPraticaSUAP) u.unmarshal(new ByteArrayInputStream(xml_b));
	//	    System.out.println(response.getInfoSchema().getVersione());
	//	} catch (Exception e1) {
	//	    throw new RuntimeException(e1);
	//	}
	File file = new File("C:/Temp/02313821007-20122018-1348.SUAP_MODIFICATA.XML");
	JAXBContext jaxbContext;
	try {
	    jaxbContext = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    RiepilogoPraticaSUAP riepilogoPraticaSUAP = (RiepilogoPraticaSUAP) unmarshaller.unmarshal(file);
	    System.out.println(riepilogoPraticaSUAP);
	} catch (JAXBException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static byte[] dataHandlerToBytes(DataHandler dh) {

	try {
	    return IOUtils.toByteArray(dh.getInputStream());
	} catch (IOException e) {
	    //log.error("dataHandlerToBytes: ", e);
	    throw new RuntimeException(e);
	}
    }
}
