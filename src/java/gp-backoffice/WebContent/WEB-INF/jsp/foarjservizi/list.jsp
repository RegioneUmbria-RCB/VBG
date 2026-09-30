<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="foarjservizi.label.lista_foarjservizi.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="foarjservizi.label.lista_foarjservizi.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		
		<div class="parametriDiv">
       		<div class="parametro"> 
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizione}
	        	</div>
	        </div>
	    </div>
		<form name="foarjserviziForm" action="list.htm">
			<jmesa:springTableFacade
				id="foarjservizi_id" 
				items="${foarjserviziList}" 
				var="foarjservizi_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${foarjservizi_var.id.codice}">${foarjservizi_var.id.codice}</a>
                        </jmesa:htmlColumn>	
                        <c:if test="${centroServiziUrlBrevi eq true }">							
							<jmesa:htmlColumn property="urlServizio" titleKey="foarjservizi.label.urlServizio" />
						</c:if>
						<jmesa:htmlColumn property="nlaServizi.descrizione" titleKey="foarjservizi.label.nlaservizi" />
						<jmesa:htmlColumn property="anonimo" titleKey="foarjservizi.label.anonimo" cellEditor="org.jmesa.custom.SiNoCellEditor"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${foarjservizi_var.id.codice}" title="<fmt:message key="label.edit.record" />${foarjservizi_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="foarjservizi.label.lista_foarjservizi.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<c:choose>
				<c:when test="${centroServiziUrlBrevi eq true || fn:length(foarjserviziList)<1}">
					<li><a href="javascript:doHref('create.htm?codiceprocedimento=${alberoproc.id.codice}','');"><fmt:message key="button.new" /></a></li>
				</c:when>
			</c:choose>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>