package it.gruppoinit.pdd.ri.interceptors;

import it.gruppoinit.pdd.spcoop.insiel.EccezioneType;
import it.gruppoinit.pdd.spcoop.insiel.Intestazione;
import it.gruppoinit.pdd.spcoop.insiel.ListaEccezioni;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class InsielMsgInterceptorIn extends AbstractSoapInterceptor {

    private String nomeMetodo;
    private String idcomunealias;
    private static Logger log = LoggerFactory.getLogger(InsielMsgInterceptorIn.class);
    private static final String INSIEL_SERVICELAYER_NAMESPACE = "http://www.insiel.it/ServiceLayer/";

    public InsielMsgInterceptorIn(String nomeMetodo, String idcomunealias) {

	super(Phase.INVOKE);
	this.nomeMetodo = nomeMetodo;
	this.idcomunealias = idcomunealias;
    }

    @Override
    public void handleMessage(SoapMessage msg) throws Fault {

	List<Header> list = msg.getHeaders();
	for (Header header : list) {
	    QName name = header.getName();
	    if (log.isDebugEnabled()) {
		log.debug("handleMessage# header: {}", name);
	    }
	    if (name.getNamespaceURI().equalsIgnoreCase(INSIEL_SERVICELAYER_NAMESPACE)) {
		if (log.isDebugEnabled()) {
		    log.debug("processo l'header: {}", name);
		}
		Object o = header.getObject();
		if (o instanceof Element) {
		    try {
			DOMSource domSource = new DOMSource((Element) o);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			Intestazione i = (Intestazione) unMarshallString(writer.toString(), Intestazione.class);
			if (i != null) {
			    if (i.getRisposta() != null) {
				if (i.getRisposta().getListaEccezioni() != null) {
				    ListaEccezioni le = i.getRisposta().getListaEccezioni();
				    if (le.getEccezione().size() > 0) {
					List<String> listaErrori = new ArrayList<String>();
					for (EccezioneType ex : le.getEccezione()) {
					    log.warn("handleMessage# Errore negli header: codice: {}, rilevanza: {}, eccezioneType: {}",
						    new Object[] { ex.getCodiceEccezione(), ex.getRilevanza().name(), ex.getValue() });
					    switch (ex.getRilevanza()) {
					    case INFO:
						break;
					    case LIEVE:
						listaErrori.add(eccezioneTypeToString(ex));
						break;
					    case GRAVE:
						listaErrori.add(eccezioneTypeToString(ex));
						break;
					    }
					}
					if (listaErrori.size() > 0) {
					    throw new RuntimeException("Errore nella chiamata ai Ws Registro Imprese. Lista degli errori rilevati: "
						    + listaErrori);
					}
				    }
				}
			    }
			}
		    } catch (Exception e) {
			throw new Fault(e);
		    }
		}
	    }
	}
    }

    private String eccezioneTypeToString(EccezioneType ex) {

	return ReflectionToStringBuilder.toString(ex, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static Object unMarshallString(String xml, Class<?> clazz) {

	try {
	    JAXBContext jc = JAXBContext.newInstance(clazz);
	    Unmarshaller u = jc.createUnmarshaller();
	    Object response = u.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
	    return response;
	} catch (Exception e1) {
	    log.error("unMarshallString: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }
}
