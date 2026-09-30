<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.PECCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.PECAttachmentHelper"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%
String cmdName = request.getParameter("commandName");
Integer labelWidth = null;
String labelWidthPercentage = request.getParameter("labelWidthPercentage");
if(StringUtils.isNotBlank(labelWidthPercentage)){
    labelWidthPercentage = labelWidthPercentage.trim();
    if(NumberUtils.isNumber(labelWidthPercentage)){
		labelWidth = NumberUtils.toInt(labelWidthPercentage);
    }
}
PECCommand cmd = null;
Object o = request.getAttribute(cmdName);
if(o instanceof PECCommand){
    cmd = (PECCommand)o;
}
else if(o instanceof ProtocollazioneCommand){
    cmd = ((ProtocollazioneCommand)o).getPecCommand();
}
List<PECAttachmentHelper> allegati = cmd.getAllegati();
pageContext.setAttribute("allegati", allegati);
%>
	<tr>
		<td <%if(labelWidth != null){ %>style="width: <%= labelWidth%>%;"<%} %>><fmt:message key="pecinbox.label.dataricezione" /></td>
		<td colspan="5">
			<b><fmt:formatDate value="<%=cmd.getPec().getPecDate() %>" pattern="<%= WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" ></fmt:formatDate></b>
		</td>
	</tr>
	<tr>
		<td><fmt:message key="label.da" /></td>
		<td colspan="5">
			<b><%=cmd.getPec().getPecFrom() %></b>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: text-top;"><fmt:message key="pecinbox.label.destinataricc" /></td>
		<td colspan="5">
			<%
			String tocc = cmd.getPec().getPecToCC();
			if(StringUtils.isNotEmpty(tocc)){
				String[] ccs = tocc.split(PECMessageHelper.EMAIL_ADDRESS_SEPARATOR);
				for(int i = 0; i < ccs.length; i++){
			%>
			<b><%= ccs[i]%></b><br />
			<%
				}
			}
			%>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: text-top;"><fmt:message key="label.oggetto" /></td>
		<td colspan="5">
			<b><%=cmd.getPec().getPecSubject() %></b>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: text-top; padding-top: 10px;"><fmt:message key="pecinbox.label.messaggio" /></td>
		<td colspan="5">
			<%-- 
			<%if(StringUtils.isNotBlank(cmd.getCorpo().getTextContent())) {%>
				<div style="border: dashed 1px; padding: 10px; font-size: larger;">
					<b><pre><%=cmd.getCorpo().getTextContent() %></pre></b>
				</div>
			<%	}%>
			--%>
			<%-- caricamento del corpo della PEC in un editor tynimce per poter visualizzare correttamente anche i messaggi di tipo HTML --%>
			<textarea cols="70" id="txt_corpo"></textarea>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: text-top; padding-top: 10px;"><fmt:message key="pecinbox.label.allegatipec" /></td>
		<td colspan="5">
			<c:if test="${not empty allegati}">
				<div style="border: dashed 1px;">
						<ul>
						<c:forEach  items="${allegati}" var="attachment">
							<li>
								<b>
								<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="mall${attachment.codiceOggetto }" />
			       						<jsp:param name="fileId" value="${attachment.codiceOggetto}" />
			       						<jsp:param name="mostralabel" value="false" />
			       						<jsp:param name="readonly" value="true" />
			   					</jsp:include>&nbsp;${attachment.nomeFile}
								</b>
							</li>
						</c:forEach>
						</ul>
				</div>
			</c:if>
			<c:if test="${empty allegati}">
				<div style="padding-top: 10px;">
					<b><fmt:message key="pecinbox.message.noattachments" /></b>
				</div>								
			</c:if>
		</td>		
	</tr>
	<script type="text/javascript">
	<!--
		jQuery(document).ready(initPecView);
	
		function initPecView(){
			initTextEditors();
			/*
			tinyMCE.init({
				width: 700,
				height: 200,
	  			mode: "exact",   			
				elements: "txt_corpo", 
	  			theme: "advanced",
	  			//theme: "simple",
	  			readonly: 1,
	  			theme_advanced_disabled: "bold,italic,underline,strikethrough,undo,redo,bulllist,numlist,formatselect",
	  			/*
	  			theme_advanced_toolbar_location: "top",
	  			theme_advanced_toolbar_align: "left",  			
	  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
	  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
	  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
	  			theme_advanced_buttons3: "link,unlink,anchor,image,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,media,advhr",
	  			
	  			forced_root_block : false,
	  	        force_br_newlines : true,
	  	        force_p_newlines : false,
	  	        preformatted: true,
	  	        oninit: loadPecBody
			});
			*/			
			//loadPecBody();
		}
	
		function loadPecBody(){
			 var idPec = '<%= cmd.getPec().getId().getId()%>';
			jQuery.ajax({
				type: "POST",
				url: '${pageContext.request.contextPath}/pecinbox/ajaxLeggiCorpoPEC.htm?codicePec='+encodeURIComponent(idPec),
				dataType: "html",
				cache: false,
				success: function(data) {
					var mytxt = tinyMCE.get('txt_corpo');
					if(mytxt){
						mytxt.setContent(data);
						/*
						var rootElem = mytxt.dom.getRoot();
						if( !mytxt.dom.isBlock(rootElem)){
							mytxt.setContent("<div><pre>" + data + "</pre></div>");
						}
						*/
					}
				},
				error: function(data){
					var mytxt = tinyMCE.get('txt_corpo');
					if(mytxt){
						mytxt.setContent(data.innerText);
					}
					console.error(data.innerText);
				}	
			});				
		}
	//-->
	</script>
