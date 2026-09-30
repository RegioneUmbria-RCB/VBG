<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="responsabili.label.lista_responsabili.title" /></title>
	</head>
	<body>
		<span class="titoloPagina">
		<fmt:message key="responsabili.label.lista_responsabili.title" />
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		
		<script type="text/javascript">
			function switchUser(userid) {
				document._switch.j_username.value = userid;
				document._switch.submit();
			}
		</script>
		
		<div id="subcontent">
			<form name="inviodati" action="list.htm">
				<jmesa:springTableFacade
					id="resp_id" 
					items="${responsabili}" 
					var="resp_var" 
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.ResponsabiliFilterMatcherMap">
						<jmesa:htmlTable>
							<jmesa:htmlRow>
								<jmesa:htmlColumn property="id.codice"  titleKey="label.codice" width="2%">
									 <a href="view.htm?codice=${resp_var.id.codice}">${resp_var.id.codice}</a>
								</jmesa:htmlColumn>
								<jmesa:htmlColumn property="titolo" titleKey="responsabili.label.titolo" width="4%"/> 
								<jmesa:htmlColumn property="responsabile" titleKey="responsabili.label.responsabile" /> 
								<jmesa:htmlColumn property="tiporesponsabile.trDescrizione" titleKey="responsabili.label.tiporesponsabile" width="5%"/>
								<jmesa:htmlColumn property="amministratore" titleKey="responsabili.label.amministratore.table" width="5%" style="text-align:center;" headerStyle="text-align:center;" cellEditor="org.jmesa.custom.ResponsabiliCellEditor" filterEditor="org.jmesa.custom.ResponsabiliDroplist"/>
								<jmesa:htmlColumn property="amministratoresoftware" titleKey="responsabili.label.ammistratoresoftware.table" width="5%" style="text-align:center;" headerStyle="text-align:center;"  cellEditor="org.jmesa.custom.ResponsabiliCellEditor" filterEditor="org.jmesa.custom.ResponsabiliDroplist"/>
								<jmesa:htmlColumn property="disabilitato" titleKey="label.disabilitato" width="5%" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.filter.SiNoCustomDropListEditor"/>
								<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="vbg-btn btn-modifica" href="view.htm?codice=${resp_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${resp_var.responsabile}">
									</a>
									<!-- §§§BEGIN§§§ -->
									
										<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
											<c:if test="${'1' != resp_var.amministratore}">
													<spring-security:authorize ifNotGranted="ROLE_PREVIOUS_ADMINISTRATOR">&nbsp;
													<a class="vbg-btn btn-cambia" href="javascript: switchUser('${resp_var.userid}');" title="<fmt:message key="label.switch.user" />">
													</a>
												</spring-security:authorize>
											</c:if>
										</spring-security:authorize>
									
									<!-- §§§END§§§ -->
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
				 </jmesa:springTableFacade>
			</form>
			  
			<form name="_switch" action="<c:url value='../j_spring_security_switch_user'/>" method="post">
    			<input type="hidden" name='j_username'>
    		</form>
    		
			<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="responsabili.label.lista_responsabili.title"/>';
			</script>
			
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>