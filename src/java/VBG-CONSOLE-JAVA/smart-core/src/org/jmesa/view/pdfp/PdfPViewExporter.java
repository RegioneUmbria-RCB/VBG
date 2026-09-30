package org.jmesa.view.pdfp;

/*
 * Copyright 2004 original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.security.LoggedUser;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractViewExporter;
import org.jmesa.view.View;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

/**
 * A PDF view that uses the iText PdfPTable.
 * 
 * @since 2.3.4
 * @author Ismail Seyfi
 * @author francescop
 */
public class PdfPViewExporter extends AbstractViewExporter {

    private HttpServletRequest request;

    public PdfPViewExporter(View view, CoreContext coreContext, HttpServletRequest request, HttpServletResponse response) {

	super(view, coreContext, response);
	this.request = request;
    }

    public PdfPViewExporter(View view, CoreContext coreContext, HttpServletRequest request, HttpServletResponse response, String fileName) {

	super(view, coreContext, response, fileName);
	this.request = request;
    }

    public void export() throws Exception {

	com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4.rotate());
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PdfWriter.getInstance(document, baos);
	document.open();
	PdfPView pdfView = (PdfPView) getView();
	/*
	 * com.lowagie.text.Image image = com.lowagie.text.Image.getInstance("init.gif"); document.add(image);
	 */
	if (request.getSession().getAttribute(WebConstants.TOKEN) != null) {
	    SecurityContext sc = SecurityContextHolder.getContext();
	    UserDetails ud = (UserDetails) sc.getAuthentication().getPrincipal();
	    LoggedUser user = (LoggedUser) ud;
	    String stileBO = (String) user.getImpostazioniUtente().get(WebConstants.CSS_USER_PREF_STYLE);
	    String styleMSgiallo = getCoreContext().getMessage("export.pdf.namestyle.giallo");
	    String styleMSgrigio = getCoreContext().getMessage("export.pdf.namestyle.grigio");
	    String styleMSstandard = getCoreContext().getMessage("export.pdf.namestyle.standard");
	    if (stileBO.equals(styleMSgiallo)) {
		(pdfView).setHeaderBackgroundColor(new Color(222, 221, 204));
		(pdfView).setHeaderFontColor(new Color(0, 0, 0));
		(pdfView).setEvenCellBackgroundColor(new Color(255, 255, 245));
		(pdfView).setOddCellBackgroundColor(new Color(240, 240, 230));
	    } else if (stileBO.equals(styleMSgrigio)) {
		(pdfView).setHeaderBackgroundColor(new Color(173, 173, 173));
		(pdfView).setHeaderFontColor(new Color(0, 0, 0));
		(pdfView).setEvenCellBackgroundColor(new Color(228, 228, 228));
		(pdfView).setOddCellBackgroundColor(new Color(198, 198, 198));
	    } else if (stileBO.equals(styleMSstandard)) {
		(pdfView).setHeaderBackgroundColor(new Color(222, 221, 204));
		(pdfView).setHeaderFontColor(new Color(0, 0, 0));
		(pdfView).setEvenCellBackgroundColor(new Color(255, 255, 255));
		(pdfView).setOddCellBackgroundColor(new Color(245, 245, 240));
	    } else {
		(pdfView).setHeaderBackgroundColor(new Color(222, 221, 204));
		(pdfView).setHeaderFontColor(new Color(0, 0, 0));
		(pdfView).setEvenCellBackgroundColor(new Color(255, 255, 255));
		(pdfView).setOddCellBackgroundColor(new Color(245, 245, 240));
	    }
	}
	document.add(pdfView.getTableCaption());
	document.add(pdfView.render());
	document.close();
	HttpServletResponse response = getResponse();
	responseHeaders(response);
	ServletOutputStream out = response.getOutputStream();
	baos.writeTo(out);
	out.flush();
    }

    @Override
    public String getContextType() {

	return "application/pdf";
    }

    public String getExtensionName() {

	return "pdf";
    }

    protected HttpServletRequest getRequest() {

	return request;
    }
}
