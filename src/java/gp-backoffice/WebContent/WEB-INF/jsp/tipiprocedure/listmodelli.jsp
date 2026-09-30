<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_modelli" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_modelli" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	 	<div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.procedura" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${tipiprocedure.procedura}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
		<form name="tipiprocedureDyn2modellitForm" action="listmodelli.htm">
			<jmesa:springTableFacade
				id="tipiprocedureDyn2modellit_id" 
				items="${tipiprocedureDyn2modellits}" 
				var="modelli_var"
				stateAttr="restore">
				<jmesa:htmlTable>
					<jmesa:htmlRow>		
						<jmesa:htmlColumn property="id.fkD2mtId" titleKey="label.codice" width="2%">
                           	<a href="viewModelli.htm?codicetipoprocedura=${tipiprocedure.id.codice}&codicemodello=${modelli_var.id.fkD2mtId}">${modelli_var.id.fkD2mtId}</a>
                        </jmesa:htmlColumn>			
						<jmesa:htmlColumn property="dyn2Modellit.descrizione" titleKey="label.modello"/>
						<jmesa:htmlColumn property="flagTipofirma" titleKey="label.tipo_firma" filterable="false" sortable="false">
							<c:if test="${modelli_var.flagTipofirma==0}">
								<fmt:message key='alberoprocDyn2modellit.label.no_firma' />
							</c:if>
							<c:if test="${modelli_var.flagTipofirma==1}">
								<fmt:message key='alberoprocDyn2modellit.label.firma_modello' />
							</c:if>
							<c:if test="${modelli_var.flagTipofirma==2}">
								<fmt:message key='alberoprocDyn2modellit.label.firma_ogni_blocco' />
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="flagPubblica" titleKey="label.pubblica" filterable="false" sortable="false">
							<c:if test="${modelli_var.flagPubblica==false}">
								<fmt:message key='label.no' />
							</c:if>
							<c:if test="${modelli_var.flagPubblica==true}">
								<fmt:message key='label.si' />
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="flagFacoltativa" titleKey="label.modello_facoltativo" filterable="false" sortable="false">
							<c:if test="${modelli_var.flagFacoltativa==false}">
								<fmt:message key='label.no' />
							</c:if>
							<c:if test="${modelli_var.flagFacoltativa==true}">
								<fmt:message key='label.si' />
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="ordine" titleKey="label.ordine" width="5%"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewModelli.htm?codicetipoprocedura=${tipiprocedure.id.codice}&codicemodello=${modelli_var.id.fkD2mtId}" title="<fmt:message key="label.edit.record" />${modelli_var.id.fkD2mtId}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>				
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${tipiprocedure.id.codice}" name="codicetipoprocedura"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listmodelli.htm?codicetipoprocedura=${tipiprocedure.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_modelli" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createmodelli.htm?codicetipoprocedura=${tipiprocedure.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>