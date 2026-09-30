<%@page import="it.gruppoinit.pal.gp.core.domain.web.MovimentiCommand"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>	
	<fmt:message key="label.movimenti_istanza" />
</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.movimenti_istanza" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${movimento.istanza.id.codice}</c:param>
		</c:import>	
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.movimento" />:</div>
				<div><fmt:message key="label.amministrazione" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					${movimento.movimento} - [${movimento.tipomovimento.id.tipomovimento}]							
				</div>
				<div>
					${movimento.amministrazioni.amministrazione}							
				</div>				
			</div>
		</div>
		<br class="clear" />
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="movimentiCommand" />
    </jsp:include>		
	<spring-form:form commandName="movimentiCommand" name="inviodati">			
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.amministrazione" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					<spring-form:select id="amministrazioni_id" path="entity.amministrazioniStc.id.codice">
						<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
						<spring-form:options items="${amministrazionis}" itemValue="id.codice" itemLabel="amministrazione"/>
					</spring-form:select>											
				</div>
			</div>
		</div>
		<br class="clear"/>								
		<script type="text/javascript">
		
		function scegliEnte(){
			var valori = getSelectTextAndValue($('amministrazioni_id'));
			if(valori[0]!=''){
				doSubmit('scegliEnteTerzoUpdate.htm','',document.inviodati);
			}else{
				alert('<fmt:message key="label.amministrazione" /> <fmt:message key="alert.required" />');
				$('amministrazioni_id').focus();
			}
		}
		</script>
</spring-form:form>
</div>
<div id="functions">
<ul>	
	<li><a href="javascript:scegliEnte();"><fmt:message key="button.ok" /></a></li>
	<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>