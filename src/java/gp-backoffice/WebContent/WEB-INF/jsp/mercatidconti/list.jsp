<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.mercatiDConti.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.mercatiDConti.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
		<div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.manifestazione" />:</div>
				<div><fmt:message key="label.codiceposteggio" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${mercati.descrizione}" /></div>
				<div><c:out value="${mercatid.codiceposteggio}" /></div>
			</div>
	    </div>		 
	    <div class="clear"></div>	 
			<form name="mercatiDContiForm" action="list.htm">
				<jmesa:springTableFacade
					id="mercatiDConti_id" 
					items="${mercatiDContiList}" 
					var="mercatiDConti_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>	
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${mercatiDConti_var.id.codice}">${mercatiDConti_var.id.codice}</a>
                            </jmesa:htmlColumn>						
							<jmesa:htmlColumn property="conto.descrizioneConto" titleKey="form.mercatiDConti.conto" />
							<c:if test="${false}">
								<jmesa:htmlColumn property="flagCanone" titleKey="form.mercatiDConti.flagCanone" cellEditor="org.jmesa.custom.SiNoCellEditor"/>
							</c:if>							
							<jmesa:htmlColumn property="anno" width="10%"  titleKey="form.mercatiDConti.anno" />
							<jmesa:htmlColumn width="10%" property="valore" titleKey="form.mercatiConti.valore" style="text-align:right;">
							
							<c:choose>
								<c:when test="${mercatiDConti_var.flagValore == true}">
									<b><fmt:formatNumber minFractionDigits="2">${mercatiDConti_var.valore}</fmt:formatNumber></b>
									<fmt:message key="label.valuta" />								
								</c:when>
								<c:otherwise>
									<b><fmt:formatNumber minFractionDigits="5">${mercatiDConti_var.valore}</fmt:formatNumber></b>
									(<fmt:message key="form.mercatiDConti.coefficiente" />)
								</c:otherwise>
							</c:choose>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="contesto" width="10%" titleKey="form.mercatiDConti.contesto" />
							<jmesa:htmlColumn property="percentualeConsorzio" width="10%" titleKey="label.percentuale_consorzio">
								<c:if test="${mercatiDConti_var.percentualeConsorzio gt 0}">	
							 		${mercatiDConti_var.percentualeConsorzio}% (<b><fmt:formatNumber value="${mercatiDConti_var.transientImportoRidettato}" 
							 		minFractionDigits="2" maxFractionDigits="2"/> <fmt:message key="label.valuta" /></b>	)
							 	</c:if>	
							</jmesa:htmlColumn>														
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false">
								<a class="dettaglioColumn" href="view.htm?codice=${mercatiDConti_var.id.codice}"  title="<fmt:message key="label.edit.record" /> ${mercatiDConti_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				
				<input type="hidden" name="posteggio.id.codice" value="${posteggio.id.codice}" />
				
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?posteggio.id.codice=${posteggio.id.codice}&';
				var _captionTab='<fmt:message key="form.mercatiDConti.title.list" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm?posteggio.id.codice=${posteggio.id.codice}','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>			</ul>
		</div>
	</body>
</html>