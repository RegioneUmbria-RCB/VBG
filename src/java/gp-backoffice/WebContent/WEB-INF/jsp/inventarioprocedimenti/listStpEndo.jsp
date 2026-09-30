<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Endoprocedimenti Regionali CART</title>
</head>
<body>
	<span class="titoloPagina">Endoprocedimenti Regionali CART</span>
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
		<fieldset>
			<legend>Endoprocedimenti di tipo 1</legend>
			<div class="jmesa">
						<table border="0" width="70%" cellpadding="2" cellspacing="0"
							class="table">
							<thead>
								<tr class="header">
									<td>codice endo regionale</td>
									<td>altri endo</td>
									<td>azioni</td>									
								</tr>
							</thead>
							<tbody class="tbody">	
							 <%
							    	int i=0;
							    %>
								<c:forEach items="${endo1List}" var="stp_var" varStatus="i">
									<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
										<td>${stp_var.codiceEndoRegionale}</td>
										<td>
											<span class="verificaaltriendo" data-codice-endo-regionale="${stp_var.codiceEndoRegionale}" data-tipo="tipo1"></span>
										</td>
										<td>
										<div id="functions">
											<ul>
												<li><a href="javascript:doHref('../inventarioprocedimenti/deleteStpEndoTipo1.htm?codiceendo=${inventarioprocedimenti.id.codice}&codice=${stp_var.id.codice}','')"><fmt:message key="button.delete" /></a></li>
											</ul>
										</div>
										</td>
									</tr>	
									 <%
							    	i++;
							    %>
								</c:forEach>
							</tbody>
						</table>
					<div id="functions">
						<ul>
							<li><a href="javascript:doHref('../inventarioprocedimenti/createStpEndoTipo1.htm?codiceendo=${inventarioprocedimenti.id.codice}','')"><fmt:message key="button.new" /></a></li>
						</ul>
					</div>						
			</div>
		</fieldset>	
	
		 <div class="clear"></div>	
			<fieldset>
			<legend>Attività</legend>
			<div class="jmesa">
				<table border="0" width="70%" cellpadding="2" cellspacing="0"
					class="table">
					<thead>
						<tr class="header">
							<td>codice endo regionale</td>
							<td>codice tipologia endo</td>	
							<td>altri endo</td>						
							<td>azioni</td>									
						</tr>
					</thead>
					<tbody class="tbody">	
					 <%
				    	int a=0;
				     %>
						<c:forEach items="${endo2List}" var="stp_var"  varStatus="i">
							<tr class="<%=(a % 2) == 0 ? "odd" : "even"%>">
								<td>${stp_var.codiceEndoRegionale}</td>
								<td>${stp_var.stpTipologieEndo2.descrizione}</td>
								<td>
									<span class="verificaaltriendo" data-codice-endo-regionale="${stp_var.codiceEndoRegionale}" data-tipo="tipo2"></span>
								</td>
								<td>
								<div id="functions">
									<ul>
										<li><a href="javascript:doHref('../inventarioprocedimenti/deleteStpEndoTipo2.htm?codiceendo=${inventarioprocedimenti.id.codice}&codice=${stp_var.id.codice}','')"><fmt:message key="button.delete" /></a></li>
									</ul>
								</div>
								</td>
							</tr>	
							<%a++; %>
						</c:forEach>
					</tbody>
				</table>
				
					<div id="functions">
						<ul>
							<li><a href="javascript:doHref('../inventarioprocedimenti/createStpEndoTipo2.htm?codiceendo=${inventarioprocedimenti.id.codice}&software=${ softwareCART }','')"><fmt:message key="button.new" /></a></li>
						</ul>
					</div>	
				 
				
			</div>
		</fieldset>
	<script type="text/javascript">
	 jQuery('.verificaaltriendo').each(function (idx, item) {
		 	var obj = this;
		 	
			verificaAltriEndo(obj);

    });
	 
	 
	 function verificaAltriEndo(obj){
		 
		 var url = '${pageContext.request.contextPath}/inventarioprocedimenti/ajaxVerificaAltriEndo.htm?codiceendo='+${inventarioprocedimenti.id.codice}+'&tipo=' + jQuery(obj).data("tipo")+"&codiceEndoRegionale="+ jQuery(obj).data("codiceEndoRegionale");
		
			var ajaxOpts = {
					context: this,
					type: 'POST',
					success: function(data){
						jQuery(obj).html(data);
					}
				};
			jQuery.ajax(url,ajaxOpts);  
	 }
	 
	</script>
	</div>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../inventarioprocedimenti/view.htm?codice=${inventarioprocedimenti.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>