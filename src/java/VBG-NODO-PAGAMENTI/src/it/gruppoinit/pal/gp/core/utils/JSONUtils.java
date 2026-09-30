package it.gruppoinit.pal.gp.core.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

public class JSONUtils {

    public static String marshal(Object obj, boolean includeRoot) throws JAXBException {

	StringWriter sw = new StringWriter();
	JAXBContext jc = JAXBContext.newInstance(obj.getClass());
	Marshaller marshaller = jc.createMarshaller();
	marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
	marshaller.marshal(obj, sw);
	return sw.toString();
    }

    public static <T> T unmarshal(Class<T> clazz, String in, boolean includeRoot) throws JAXBException {

	InputStream stream = new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8));
	return (T) unmarshal(clazz, stream, includeRoot);
    }

    public static <T> T unmarshal(Class<T> clazz, InputStream is, boolean includeRoot) throws JAXBException {

	JAXBContext jc = JAXBContext.newInstance(clazz);
	Unmarshaller unmarshaller = jc.createUnmarshaller();
	unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
	return (T) unmarshaller.unmarshal(new StreamSource(is), clazz).getValue();
    }
}
