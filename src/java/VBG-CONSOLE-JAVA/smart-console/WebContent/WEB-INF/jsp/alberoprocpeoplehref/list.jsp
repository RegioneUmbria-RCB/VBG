<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="alberoprocpeoplehref.label.lista_alberoprocpeoplehref.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="alberoprocpeoplehref.label.lista_alberoprocpeoplehref.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<div class="parametriDiv">
       		<div class="etichetta">
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div> 
	        	<br />
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionebreve}
	        	</div>
	        </div>
       		</div>
       		<div class="clear"></div>
			<form name="alberoprocpeoplehrefForm" action="list.htm">
				<jmesa:springTableFacade
					id="alberoprocpeoplehref_id" 
					items="${alberoprocpeoplehrefList}" 
					var="alberoprocpeoplehref_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
							 <a href="view.htm?codice=${alberoprocpeoplehref_var.id.codice}">${alberoprocpeoplehref_var.id.codice}</a>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="nometag" titleKey="alberoprocpeoplehref.label.nomeTag.table" />
							<jmesa:htmlColumn property="valoretag" titleKey="alberoprocpeoplehref.label.valoreTag.table" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a href="view.htm?codice=${alberoprocpeoplehref_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${alberoprocpeoplehref_var.id.codice}">
									<img src="${pageContext.request.contextPath}/images/edit.gif" alt="<fmt:message key="label.edit.record" /> ${alberoprocpeoplehref_var.id.codice}"/>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" name="alberoproc.id.codice" value="${alberoproc.id.codice}"/>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?alberoproc.id.codice=${alberoproc.id.codice}&';
				var _captionTab='<fmt:message key="alberoprocpeoplehref.label.lista_alberoprocpeoplehref.title" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm?alberoproc.id.codice=${alberoproc.id.codice}','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>