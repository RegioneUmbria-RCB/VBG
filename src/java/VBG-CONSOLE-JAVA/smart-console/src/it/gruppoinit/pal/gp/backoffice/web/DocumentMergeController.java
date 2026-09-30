package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.domain.web.DocumentMergeCommand;
import it.gruppoinit.pal.gp.core.service.DocumentMergeService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class DocumentMergeController extends BaseController<Letteretipo> {

    private static final int BUFFER_SIZE = 1024 * 100;//100Kb
    //@Autowired
    // private DocumentMergeService docMergeService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public String inputPage(Model model) {

	DocumentMergeCommand mergeCommand = new DocumentMergeCommand();
	mergeCommand.setUserOptions(new DocumentMergeHelper());
	model.addAttribute("mergeCommand", mergeCommand);
	return "documentmerge/inputPage";
    }

    @RequestMapping
    public String validateStampaDocumento(@ModelAttribute DocumentMergeCommand mergeCommand, BindingResult result) {

	Integer instId = null;
	Integer ltId = null;
	boolean doMerge = true;
	String retVal = "documentmerge/inputPage";
	if (mergeCommand.getLetteraTipo() != null) {
	    Letteretipo lt = mergeCommand.getLetteraTipo();
	    ltId = lt.getId().getCodice();
	    if (ltId == null) {
		result.reject("Selezionare il documento tipo da compilare.");
		doMerge = false;
	    }
	}
	if (doMerge) {
	    retVal = "redirect:stampaDocumento.htm?istanza.id.codice=" + instId.intValue() + "&letteraTipo.id.codice=" + ltId.intValue();
	}
	return retVal;
    }

    @RequestMapping
    public String stampaDocumento(HttpServletResponse response, @ModelAttribute("mergeCommand") DocumentMergeCommand mergeCommand,
	    BindingResult result, SessionStatus status) throws IOException {

	Integer instId = null;
	Integer ltId = null;
	Letteretipo lt = null;
	String retPath = null;
	//boolean doMerge = true;
	if (mergeCommand.getLetteraTipo() != null) {
	    lt = mergeCommand.getLetteraTipo();
	    ltId = lt.getId().getCodice();
	}
	OutputStream os = null;
	try {
	    byte[] docData = null; //docMergeService.eseguiSostituzioniBaseDocumento(ltId, instId, null, mergeCommand.getUserOptions());
	    lt = letteretipoService.findById(new PkId(ltId));
	    /*
	    Oggetti obj = lt.getFile();
	    obj = oggettiService.findById(new PkId(obj.getId().getCodice()));
	    */
	    String fileName = StringUtils.isNotBlank(lt.getNomefile()) ? lt.getNomefile() : lt.getDescrizione() + ".rtf";
	    ByteArrayInputStream bais = new ByteArrayInputStream(docData);
	    response.setContentLength(docData.length);
	    response.setContentType("application/rtf");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    os = response.getOutputStream();
	    int bytesRead = 0;
	    byte[] buffer = new byte[BUFFER_SIZE];
	    while ((bytesRead = bais.read(buffer)) != -1) {
		os.write(buffer, 0, bytesRead);
	    }
	    os.flush();
	} catch (BusinessValidationException bve) {
	    copyErrorsToBindingResult(result, null, bve);
	    retPath = "documentmerge/inputPage";
	} finally {
	    if (os != null) {
		os.close();
	    }
	}
	return retPath;
    }

    @Override
    protected void fixMergeEntityProperty(Letteretipo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Letteretipo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
