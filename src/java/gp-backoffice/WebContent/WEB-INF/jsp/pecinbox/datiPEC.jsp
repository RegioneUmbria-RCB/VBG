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
<div class="vbg-form">
	<div class="form-group">
		<label><fmt:message key="pecinbox.label.dataricezione" /></label>
		<b><fmt:formatDate value="<%=cmd.getPec().getPecDate() %>" pattern="<%= WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" ></fmt:formatDate></b>
	</div>
	<div class="form-group">
		<label><fmt:message key="label.da" /></label>
		<b><%=cmd.getPec().getPecFrom() %></b>
	</div>	
	<div class="form-group">
		<label><fmt:message key="pecinbox.label.destinataricc" /></label>
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
	</div>
	<div class="form-group">
                <label><fmt:message key="label.oggetto" /></label>
                <div style="width: 800px;white-space: normal;font-weight:bold"><%=cmd.getPec().getPecSubject() %></div>
    </div>
	<fieldset>
	        <legend><fmt:message key="pecinbox.label.messaggio" /></legend>
	        <div class="form-group" id="txt_corpo_" style="width: 85%;height: 400px;overflow: auto" />
	</fieldset>

	<div class="form-group">	
		<label><fmt:message key="pecinbox.label.allegatipec" /></label>		
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
	</div>
</div>
	<script type="text/javascript">
	
		vbg.ready(() => {
			loadPecBody();			
		});	
		
		async function loadPecBody(){
			let idPec = '<%=cmd.getPec().getId().getId()%>';
			
			let response = await fetch("../pecinbox/ajaxLeggiCorpoPEC.htm?codicePec="+encodeURIComponent(idPec) , {
	            method: "POST",
	            cache: "no-cache"
	        });
			
			if(response.status == 200){
				let resp = await response.text();
				document.querySelector('#txt_corpo_').innerHTML= resp;
			}else{
				let mytxt = tinyMCE.get('txt_corpo');
				if(mytxt){
					mytxt.setContent(data.innerText);
				}
				console.error(data.innerText);
			}				
		}
		
	</script>
