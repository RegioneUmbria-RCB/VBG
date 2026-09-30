package it.gruppoinit.pal.gp.backoffice.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;

@Controller
@RequestMapping("/oggettijson")
public class OggettiJsonController extends BaseJsonController<Oggetti> {

    @Autowired
    private OggettiService oggettiService;

    @XmlRootElement(name = "oggetto")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OggettoByIdResponse {

	@XmlElement(name = "id")
	public Integer id;
	@XmlElement(name = "nomeFile")
	public String nomeFile;
	@XmlElement(name = "downloadUrl")
	public String downloadUrl;

	public OggettoByIdResponse() {

	}

	public OggettoByIdResponse(Integer id, String nomeFile, String downloadUrl) {

	    this.id = id;
	    this.nomeFile = nomeFile;
	    this.downloadUrl = downloadUrl;
	}
    }

    @RequestMapping(method = RequestMethod.GET)
    public void index(@RequestParam("id") Integer id, HttpServletRequest request, HttpServletResponse response) {

	try {
	    Oggetti oggetto = this.oggettiService.findById(new PkId(id));
	    if (oggetto == null) {
		super.writeJsonError(response, "Il file con codiceoggetto " + id + " non esiste nella base dati");
		return;
	    }
	    String filePath = this.getSharedFileLink(oggetto.getId().getCodice());
	    OggettoByIdResponse jsonResponse = new OggettoByIdResponse(oggetto.getId().getCodice(), oggetto.getNomefile(), filePath);
	    response.setContentType("application/json");
	    response.getOutputStream().write(toJsonBytes(jsonResponse));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @RequestMapping(method = RequestMethod.POST)
    public void index(@RequestParam("fileUpload") MultipartFile mpFile, HttpServletRequest request, HttpServletResponse response) {

	try {
	    byte[] fileByteArray = mpFile.getBytes();
	    Oggetti oggetto = new Oggetti();
	    oggetto.setNomefile(mpFile.getOriginalFilename());
	    oggetto.setOggetto(fileByteArray);
	    this.oggettiService.insert(oggetto);
	    String filePath = this.getSharedFileLink(oggetto.getId().getCodice());
	    OggettoByIdResponse jsonResponse = new OggettoByIdResponse(oggetto.getId().getCodice(), oggetto.getNomefile(), filePath);
	    response.setContentType("application/json");
	    response.getOutputStream().write(toJsonBytes(jsonResponse));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void ajaxDelete(@RequestParam("id") Integer id, HttpServletRequest request, HttpServletResponse response) {

	try {
	    Oggetti oggetto = this.oggettiService.findById(new PkId(id));
	    if (oggetto == null) {
		super.writeJsonError(response, "Il file con codiceoggetto " + id + " non esiste nella base dati");
		return;
	    }
	    this.oggettiService.delete(oggetto);
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    private String getSharedFileLink(Integer codiceOggetto) {

	String filePath = this.oggettiService.getSharedFileLink(codiceOggetto);
	if (StringUtils.isNotBlank(filePath)) {
	    filePath = "file:///" + filePath;
	}
	return filePath;
    }
}
