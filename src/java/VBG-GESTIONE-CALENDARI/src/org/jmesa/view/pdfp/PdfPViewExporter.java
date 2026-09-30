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
package org.jmesa.view.pdfp;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractViewExporter;
import org.jmesa.view.View;

import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

/**
 * A PDF view that uses the iText PdfPTable.
 *
 * @since 2.3.4
 * @author Ismail Seyfi
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
	document.add(pdfView.getTableCaption());
	document.add(pdfView.render());
	document.close();
	HttpServletResponse response = getResponse();
	responseHeaders(response);
	ServletOutputStream out = response.getOutputStream();
	baos.writeTo(out);
	out.flush();
    }

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
