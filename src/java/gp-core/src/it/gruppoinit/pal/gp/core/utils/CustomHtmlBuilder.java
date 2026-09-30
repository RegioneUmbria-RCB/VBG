package it.gruppoinit.pal.gp.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class CustomHtmlBuilder {

    private StringBuilder builder;

    public CustomHtmlBuilder() {

	this.builder = new StringBuilder();
    }

    public CustomHtmlBuilder append(Object text) {

	if (text != null) {
	    this.builder.append(text);
	}
	return this;
    }

    public int length() {

	return this.builder.toString().length();
    }

    public CustomHtmlBuilder format(int tabs, int newlines) {

	tabs(tabs);
	newlines(newlines);
	return this;
    }

    public CustomHtmlBuilder tabs(int tabs) {

	for (int i = 0; i < tabs; ++i) {
	    tab();
	}
	return this;
    }

    public CustomHtmlBuilder newlines(int newlines) {

	for (int i = 0; i < newlines; ++i) {
	    newline();
	}
	return this;
    }

    public CustomHtmlBuilder tab() {

	append("\t");
	return this;
    }

    public CustomHtmlBuilder newline() {

	append("\n");
	return this;
    }

    public CustomHtmlBuilder close() {

	append(">");
	return this;
    }

    public CustomHtmlBuilder end() {

	append("/>");
	return this;
    }

    public CustomHtmlBuilder table(int tabs) {

	newline();
	tabs(tabs);
	append("<table");
	return this;
    }

    public CustomHtmlBuilder tableEnd(int tabs) {

	newline();
	tabs(tabs);
	append("</table>");
	return this;
    }

    public CustomHtmlBuilder button() {

	append("<button");
	return this;
    }

    public CustomHtmlBuilder buttonEnd() {

	append("</button>");
	return this;
    }

    public CustomHtmlBuilder tr(int tabs) {

	newline();
	tabs(tabs);
	append("<tr");
	return this;
    }

    public CustomHtmlBuilder trEnd(int tabs) {

	newline();
	tabs(tabs);
	append("</tr>");
	return this;
    }

    public CustomHtmlBuilder th(int tabs) {

	newline();
	tabs(tabs);
	append("<th");
	return this;
    }

    public CustomHtmlBuilder thEnd() {

	append("</th>");
	return this;
    }

    public CustomHtmlBuilder td(int tabs) {

	newline();
	tabs(tabs);
	append("<td");
	return this;
    }

    public CustomHtmlBuilder tdEnd() {

	append("</td>");
	return this;
    }

    public CustomHtmlBuilder input() {

	append("<input");
	return this;
    }

    public CustomHtmlBuilder type(String type) {

	if (StringUtils.isNotBlank(type)) {
	    append(" type=\"").append(type).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder link() {

	append("<link");
	return this;
    }

    public CustomHtmlBuilder rel(String rel) {

	if (StringUtils.isNotBlank(rel)) {
	    append(" rel=\"").append(rel).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder media(String media) {

	if (StringUtils.isNotBlank(media)) {
	    append(" media=\"").append(media).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder name(String name) {

	if (StringUtils.isNotBlank(name)) {
	    append(" name=\"").append(name).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder value(String value) {

	if (StringUtils.isNotBlank(value))
	    append(" value=\"").append(value).append("\" ");
	else {
	    append(" value=\"").append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder select() {

	append("<select");
	return this;
    }

    public CustomHtmlBuilder selectEnd() {

	append("</select>");
	return this;
    }

    public CustomHtmlBuilder option() {

	append("<option");
	return this;
    }

    public CustomHtmlBuilder optionEnd() {

	append("</option>");
	return this;
    }

    public CustomHtmlBuilder form() {

	newline();
	append("<form");
	return this;
    }

    public CustomHtmlBuilder formEnd() {

	newline();
	append("</form>");
	return this;
    }

    public CustomHtmlBuilder title(String title) {

	if (StringUtils.isNotBlank(title)) {
	    append(" title=\"").append(title).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder action(String action) {

	append(" action=\"");
	if (StringUtils.isNotBlank(action)) {
	    append(action);
	}
	append("\" ");
	return this;
    }

    public CustomHtmlBuilder method(String method) {

	if (StringUtils.isNotBlank(method)) {
	    append(" method=\"").append(method).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder enctype(String enctype) {

	if (StringUtils.isNotBlank(enctype)) {
	    append(" enctype=\"").append(enctype).append("\" ");
	}
	return this;
    }
    public CustomHtmlBuilder onblur(String onblur) {

	if (StringUtils.isNotBlank(onblur)) {
	    append(" onblur=\"").append(onblur).append("\" ");
	}
	return this;
    }
    public CustomHtmlBuilder onchange(String onchange) {

	if (StringUtils.isNotBlank(onchange)) {
	    append(" onchange=\"").append(onchange).append("\" ");
	}
	return this;
    }

    
    public CustomHtmlBuilder onsubmit(String onsubmit) {

	if (StringUtils.isNotBlank(onsubmit)) {
	    append(" onsubmit=\"").append(onsubmit).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder onclick(String onclick) {

	if (StringUtils.isNotBlank(onclick)) {
	    append(" onclick=\"").append(onclick).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder onmouseover(String onmouseover) {

	if (StringUtils.isNotBlank(onmouseover)) {
	    append(" onmouseover=\"").append(onmouseover).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder onmouseout(String onmouseout) {

	if (StringUtils.isNotBlank(onmouseout)) {
	    append(" onmouseout=\"").append(onmouseout).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder onkeypress(String onkeypress) {

	if (StringUtils.isNotBlank(onkeypress)) {
	    append(" onkeypress=\"").append(onkeypress).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder onkeyup(String onkeyup) {

	if (StringUtils.isNotBlank(onkeyup)) {
	    append(" onkeyup=\"").append(onkeyup).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder id(String id) {

	if (StringUtils.isNotBlank(id)) {
	    append(" id=\"").append(id).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder styleClass(String styleClass) {

	if (StringUtils.isNotBlank(styleClass)) {
	    append(" class=\"").append(styleClass).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder style(String style) {

	if (StringUtils.isNotBlank(style)) {
	    append(" style=\"").append(style).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder width(String width) {

	if (StringUtils.isNotBlank(width)) {
	    append(" width=\"").append(width).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder align(String align) {

	if (StringUtils.isNotBlank(align)) {
	    append(" align=\"").append(align).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder valign(String valign) {

	if (StringUtils.isNotBlank(valign)) {
	    append(" valign=\"").append(valign).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder border(String border) {

	if (StringUtils.isNotBlank(border)) {
	    append(" border=\"").append(border).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder cellpadding(String cellPadding) {

	if (StringUtils.isNotBlank(cellPadding)) {
	    append(" cellpadding=\"").append(cellPadding).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder cellspacing(String cellSpacing) {

	if (StringUtils.isNotBlank(cellSpacing)) {
	    append(" cellspacing=\"").append(cellSpacing).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder colspan(String colspan) {

	if (StringUtils.isNotBlank(colspan)) {
	    append(" colspan=\"").append(colspan).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder rowspan(String rowspan) {

	if (StringUtils.isNotBlank(rowspan)) {
	    append(" rowspan=\"").append(rowspan).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder size(String size) {

	if (StringUtils.isNotBlank(size)) {
	    append(" size=\"").append(size).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder span() {

	append("<span");
	return this;
    }

    public CustomHtmlBuilder spanEnd() {

	append("</span>");
	return this;
    }

    public CustomHtmlBuilder div() {

	append("<div");
	return this;
    }

    public CustomHtmlBuilder divEnd() {

	append("</div>");
	return this;
    }

    public CustomHtmlBuilder param(String name, String value) {

	append(name);
	equals();
	append(value);
	return this;
    }

    public CustomHtmlBuilder a() {

	append("<a");
	return this;
    }

    public CustomHtmlBuilder ahref(String url, String displayText) {

	return ahref(url, displayText, null);
    }

    public CustomHtmlBuilder ahref(String url, String displayText, Map<String, String> params) {

	StringBuilder urlBuilder = new StringBuilder(url);
	boolean firstRow;
	if ((params != null) && (params.size() > 0)) {
	    firstRow = true;
	    for (Map.Entry entry : params.entrySet()) {
		if (firstRow)
		    urlBuilder.append("?");
		else {
		    urlBuilder.append("&amp;");
		}
		String key = (String) entry.getKey();
		String val = (String) entry.getValue();
		try {
		    key = URLEncoder.encode(key, "UTF-8");
		    val = URLEncoder.encode(val, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		urlBuilder.append(key).append("=").append(val);
		firstRow = false;
	    }
	}
	a().href().quote().append(urlBuilder.toString()).quote().close().append(displayText).aEnd();
	return this;
    }

    public CustomHtmlBuilder href(String href) {

	if (StringUtils.isNotBlank(href)) {
	    append(" href=\"").append(href).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder href() {

	append(" href=");
	return this;
    }

    public CustomHtmlBuilder aEnd() {

	append("</a>");
	return this;
    }

    public CustomHtmlBuilder bold() {

	append("<b>");
	return this;
    }

    public CustomHtmlBuilder boldEnd() {

	append("</b>");
	return this;
    }

    public CustomHtmlBuilder quote() {

	append("\"");
	return this;
    }

    public CustomHtmlBuilder question() {

	append("?");
	return this;
    }

    public CustomHtmlBuilder equals() {

	append("=");
	return this;
    }

    public CustomHtmlBuilder ampersand() {

	append("&");
	return this;
    }

    public CustomHtmlBuilder img() {

	append("<img");
	return this;
    }

    public CustomHtmlBuilder src(String src) {

	if (StringUtils.isNotBlank(src)) {
	    append(" src=\"").append(src).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder alt(String alt) {

	if (StringUtils.isNotBlank(alt)) {
	    append(" alt=\"").append(alt).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder textarea() {

	append("<textarea");
	return this;
    }

    public CustomHtmlBuilder textareaEnd() {

	append("</textarea>");
	return this;
    }

    public CustomHtmlBuilder cols(String cols) {

	if (StringUtils.isNotBlank(cols)) {
	    append(" cols=\"").append(cols).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder rows(String rows) {

	if (StringUtils.isNotBlank(rows)) {
	    append(" rows=\"").append(rows).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder checked() {

	append(" checked=\"checked\"");
	return this;
    }

    public CustomHtmlBuilder selected() {

	append(" selected=\"selected\"");
	return this;
    }

    public CustomHtmlBuilder readonly() {

	append(" readonly=\"readonly\"");
	return this;
    }

    public CustomHtmlBuilder nbsp() {

	append("&#160;");
	return this;
    }

    public CustomHtmlBuilder comment(String comment) {

	if (StringUtils.isNotBlank(comment)) {
	    append(" <!-- ").append(comment).append(" -->");
	}
	return this;
    }

    public CustomHtmlBuilder ul() {

	append("<ul");
	return this;
    }

    public CustomHtmlBuilder ulEnd() {

	append("</ul>");
	return this;
    }

    public CustomHtmlBuilder li() {

	append("<li");
	return this;
    }

    public CustomHtmlBuilder liEnd() {

	append("</li>");
	return this;
    }

    public CustomHtmlBuilder br() {

	append("<br/>");
	return this;
    }

    public CustomHtmlBuilder disabled() {

	append(" disabled=\"disabled\" ");
	return this;
    }

    public CustomHtmlBuilder nowrap() {

	append(" nowrap=\"nowrap\" ");
	return this;
    }

    public CustomHtmlBuilder maxlength(String maxlength) {

	if (StringUtils.isNotBlank(maxlength)) {
	    append(" maxlength=\"").append(maxlength).append("\" ");
	}
	return this;
    }

    public CustomHtmlBuilder tbody(int tabs) {

	newline();
	tabs(tabs);
	append("<tbody");
	return this;
    }

    public CustomHtmlBuilder tbodyEnd(int tabs) {

	newline();
	tabs(tabs);
	append("</tbody>");
	return this;
    }

    public CustomHtmlBuilder thead(int tabs) {

	newline();
	tabs(tabs);
	append("<thead");
	return this;
    }

    public CustomHtmlBuilder theadEnd(int tabs) {

	newline();
	tabs(tabs);
	append("</thead>");
	return this;
    }

    public CustomHtmlBuilder p() {

	append("<p");
	return this;
    }

    public CustomHtmlBuilder pEnd() {

	append("</p>");
	return this;
    }

    public CustomHtmlBuilder h1() {

	append("<h1");
	return this;
    }

    public CustomHtmlBuilder h1End() {

	append("</h1>");
	return this;
    }

    public CustomHtmlBuilder h2() {

	append("<h2");
	return this;
    }

    public CustomHtmlBuilder h2End() {

	append("</h2>");
	return this;
    }

    public CustomHtmlBuilder h3() {

	append("<h3");
	return this;
    }

    public CustomHtmlBuilder h3End() {

	append("</h3>");
	return this;
    }

    public CustomHtmlBuilder h4() {

	append("<h4");
	return this;
    }

    public CustomHtmlBuilder h4End() {

	append("</h4>");
	return this;
    }

    public CustomHtmlBuilder h5() {

	append("<h5");
	return this;
    }

    public CustomHtmlBuilder h5End() {

	append("</h5>");
	return this;
    }

    public CustomHtmlBuilder script() {

	append("<script");
	return this;
    }

    public CustomHtmlBuilder scriptEnd() {

	append("</script>");
	return this;
    }

    public CustomHtmlBuilder semicolon() {

	append(";");
	return this;
    }

    public CustomHtmlBuilder caption() {

	append("<caption");
	return this;
    }

    public CustomHtmlBuilder captionEnd() {

	append("</caption>");
	return this;
    }

    public CustomHtmlBuilder html() {

	append("<html");
	return this;
    }

    public CustomHtmlBuilder htmlEnd() {

	append("</html>");
	return this;
    }

    public CustomHtmlBuilder body() {

	append("<body");
	return this;
    }

    public CustomHtmlBuilder bodyEnd() {

	append("</body>");
	return this;
    }

    public CustomHtmlBuilder head() {

	append("<head");
	return this;
    }

    public CustomHtmlBuilder headEnd() {

	append("</head>");
	return this;
    }

    public CustomHtmlBuilder style() {

	append("<style");
	return this;
    }

    public CustomHtmlBuilder styleEnd() {

	append("</style>");
	return this;
    }

    public CustomHtmlBuilder dl() {

	append("<dl");
	return this;
    }

    public CustomHtmlBuilder dlEnd() {

	append("</dl>");
	return this;
    }

    public CustomHtmlBuilder dd() {

	append("<dd");
	return this;
    }

    public CustomHtmlBuilder ddEnd() {

	append("</dd>");
	return this;
    }

    public CustomHtmlBuilder dt() {

	append("<dt");
	return this;
    }

    public CustomHtmlBuilder dtEnd() {

	append("</dt>");
	return this;
    }

    public CustomHtmlBuilder label() {

	append("<label");
	return this;
    }

    public CustomHtmlBuilder labelEnd() {

	append("</label>");
	return this;
    }

    public CustomHtmlBuilder forAttr(String forAttr) {

	if (StringUtils.isNotBlank(forAttr)) {
	    append(" for=\"").append(forAttr).append("\" ");
	}
	return this;
    }

    public String toString() {

	return this.builder.toString();
    }
}