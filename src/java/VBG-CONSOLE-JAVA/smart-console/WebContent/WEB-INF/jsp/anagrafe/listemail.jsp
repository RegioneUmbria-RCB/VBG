<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.archivio_email" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.archivio_email" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	    <div id="subcontent">
	    	<div class="parametriDiv">
				<div class="etichetta">
   					<div>
             			<fmt:message key="label.soggetto" />:
       				</div>
        		</div>
        		<div class="parametro">
       				<div>
           	 			<c:out value="${anagrafe.descrizioneRichiedente}"/>
       				</div>
		 		</div>
    		</div>
    	</div>  
		<form name="emailanagrForm" action="listemail.htm">
			<jmesa:springTableFacade
				id="emailanagr_id" 
				items="${emailanagrList}" 
				var="emailanagr_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.EmailAnagrafeFilterMatcherMap" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codiceemail" titleKey="label.codice" width="2%">
                           	<a href="viewEmail.htm?codice=${emailanagr_var.id.codice}">${emailanagr_var.id.codice}</a>
                        </jmesa:htmlColumn>		
                        <jmesa:htmlColumn property="data" titleKey="label.data" width="10%" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataemailAnagrafeCustomFilter" />						
						<jmesa:htmlColumn property="oggetto" titleKey="label.oggetto_email" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewEmail.htm?codice=${emailanagr_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${emailanagr_var.oggetto}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${anagrafe.id.codice}" name="codiceanagrafe" />
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listemail.htm?codiceanagrafe=${anagrafe.id.codice}&';
			var _captionTab='<fmt:message key="label.archivio_email" />';
		 </script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>