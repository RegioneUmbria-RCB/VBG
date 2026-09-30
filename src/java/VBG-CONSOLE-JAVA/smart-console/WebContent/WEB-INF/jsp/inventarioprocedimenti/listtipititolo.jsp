<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="inventarioprocedimenti.label.lista_tipititolo.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="inventarioprocedimenti.label.lista_tipititolo.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	     <div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${inventarioprocedimenti.procedimento}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
		<form name="tipititoloForm" action="listtipititolo.htm">
			<jmesa:springTableFacade
				id="tipititolo_id" 
				items="${tipititoloList}" 
				var="tipititolo_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>				
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="viewTipititolo.htm?codicetipititolo=${tipititolo_var.id.codice}&codicecomune=${inventarioprocedimenti.id.idcomune}">${tipititolo_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="tipotitolo" titleKey="label.tipo_titolo" />
						<jmesa:htmlColumn property="flgNonPubblicare" titleKey="label.flag_non_pubblicare"  filterable="false" sortable="false">
						<c:choose>
							<c:when test="${tipititolo_var.flgNonPubblicare eq true}">
								<fmt:message key="label.si" />
							</c:when>
							<c:otherwise>
								<fmt:message key="label.no" />
							</c:otherwise>
						</c:choose>	
						</jmesa:htmlColumn>
						
						<jmesa:htmlColumn property="flgAllObbligatorio" titleKey="label.allegato_obbligatorio" filterable="false" sortable="false">
						<c:choose>
							<c:when test="${tipititolo_var.flgAllObbligatorio eq true}">
								<fmt:message key="label.si" />
							</c:when>
							<c:otherwise>
								<fmt:message key="label.no" />
							</c:otherwise>
						</c:choose>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewTipititolo.htm?codicetipititolo=${tipititolo_var.id.codice}&codicecomune=${inventarioprocedimenti.id.idcomune}" title="<fmt:message key="label.edit.record" />&nbsp;${tipotitolo_var.tipotitolo}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${inventarioprocedimenti.id.codice}" name="codiceendo"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listtipititolo.htm?codiceendo=${inventarioprocedimenti.id.codice}&codicecomune=${inventarioprocedimenti.id.idcomune}';
			var _captionTab='<fmt:message key="label.tipo_titolo" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createTipititolo.htm?codiceendo=${inventarioprocedimenti.id.codice}&codicecomune=${inventarioprocedimenti.id.idcomune}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>