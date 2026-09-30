package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.ui.Model;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class BaseJsonController<E> extends BaseController<E> {

    @XmlRootElement(name = "error")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class JsonErrorMessage {

	@XmlElement(name = "error")
	public String error;

	public JsonErrorMessage() {

	}

	public JsonErrorMessage(String error) {

	    this.error = error;
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(E entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(E entity) {

	// TODO Auto-generated method stub
    }

    protected <T> T fromJson(InputStream iStream, Class<T> cls) throws JAXBException {

	return Utilities.unMarshallJsonStream(iStream, cls, true);
    }

    protected String toJson(Object obj, boolean includiRoot) throws JAXBException {

	return Utilities.marshalJsonObject(obj, obj.getClass(), includiRoot, Utilities.JAXB_ENCODING_UTF_8);
    }

    protected byte[] toJsonBytes(Object obj) throws JAXBException, UnsupportedEncodingException {

	return this.toJsonBytes(obj, false);
    }

    protected byte[] toJsonBytes(Object obj, boolean includiRoot) throws JAXBException, UnsupportedEncodingException {

	return this.toJson(obj, includiRoot).getBytes("utf-8");
    }

    protected <T> String listToJson(List<T> list, Class<T> t, boolean includiRoot) throws JAXBException {

	return Utilities.marshalJsonObject(list, t, includiRoot, Utilities.JAXB_ENCODING_UTF_8);
    }

    protected <T> byte[] listToJsonBytes(List<T> list, Class<T> t, boolean includiRoot) throws JAXBException, UnsupportedEncodingException {

	return this.listToJson(list, t, includiRoot).getBytes("utf-8");
    }

    protected void writeJsonError(HttpServletResponse response, String message) {

	JsonErrorMessage err = new JsonErrorMessage(message);
	response.setStatus(500);
	response.setContentType("application/json");
	try {
	    response.getOutputStream().write(toJsonBytes(err, false));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JAXBException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
