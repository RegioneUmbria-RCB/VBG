<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="manifestazione.label.lista_mercatistradario.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="manifestazione.label.lista_mercatistradario.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	 
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.manifestazione" />:</div>
	    	</div>
	    	<div class="parametro">
	    	  <div><c:out value="${mercati.descrizione}" /></div>
			</div>
		</div>


		<form name="mercatistradarioForm" action="listmercatistradario.htm">
			<jmesa:springTableFacade
				id="mercatistradario_id" 
				items="${mercatistradarioList}" 
				var="mercatistradario_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="viewMercatistradario.htm?codicemercato=${mercati.id.codice}&codicemercatostradario=${mercatistradario_var.id.codice}">${mercatistradario_var.id.codice}</a>
                        </jmesa:htmlColumn>								
                        <c:if test="${mercatistradario_var.stradario.datavalidita == null }">
							<jmesa:htmlColumn property="stradario.descrizioneCompleta" titleKey="label.indirizzo" >
							  ${mercatistradario_var.stradario.descrizioneCompleta}
							</jmesa:htmlColumn>
							
							
						</c:if>
						<c:if test="${mercatistradario_var.stradario.datavalidita != null }">
							<jmesa:htmlColumn property="stradario.descrizioneCompleta" titleKey="label.indirizzo" >
							 <label style="text-decoration:line-through;color: red;"> ${mercatistradario_var.stradario.descrizioneCompleta}(Disabilitato)</label>
							</jmesa:htmlColumn>
						</c:if>
						<jmesa:htmlColumn property="stradario.cap" titleKey="label.cap" />
						<jmesa:htmlColumn property="stradario.locfraz" titleKey="label.localita_frazione" />
						<jmesa:htmlColumn property="coefficienteViario" titleKey="manifestazione.label.coefficiente_viario" cellEditor="org.jmesa.view.editor.NumberCellEditor" pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN_CINQUE_DECIMALI %>"  style="text-align: right;"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="eliminaRiga" href="deleteMercatistradariofromList.htm?codice=${mercatistradario_var.id.codice}" title="<fmt:message key="label.elimina" />&nbsp;${mercatistradario_var.stradario.descrizioneCompleta}">
								<label><fmt:message key="label.elimina" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${mercati.id.codice}" name="codicemercato"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listmercatistradario.htm?codicemercato=${mercati.id.codice}&';
			var _captionTab='<fmt:message key="manifestazione.label.lista_mercatistradario.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createMercatistradario.htm?codicemercato=${mercati.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>