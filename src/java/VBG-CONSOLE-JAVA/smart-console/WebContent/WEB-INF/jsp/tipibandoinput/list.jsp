<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.tipibandoinput.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.tipibandoinput.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<span class="parametri"><fmt:message key="form.tipibando.title.prefix"/> <label>${tipibando.descrizione}</label></span>
			<form name="tipibandoinputForm" action="list.htm">
				<jmesa:springTableFacade 
					id="tipibandoinput_id" 
					items="${tipibandoinputList}" 
					var="tipibandoinput_var"
					exportTypes="" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" sortable="false" filterable="false">
                                  <a href="view.htm?codice=${tipibandoinput_var.id.codice}">${tipibandoinput_var.id.codice}</a>
                            </jmesa:htmlColumn>
                            <%--						
							<jmesa:htmlColumn property="tipoinput" titleKey="form.tipibandoinput.tipoinput" />
							--%>
							<jmesa:htmlColumn property="etichetta" titleKey="form.tipibandoinput.etichetta" sortable="false"  filterable="false"/>
							<%--
							<jmesa:htmlColumn property="query" titleKey="form.tipibandoinput.query" />

 							--%>
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="dettaglioColumn" href="view.htm?codice=${tipibandoinput_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${tipibandoinput_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
									</a>									
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			<input type="hidden" value="${tipibando.id.codice}" name="tipibando.id.codice"/>
			</form>
			
			<script type="text/javascript">
				
				var _jmesaUrl='list.htm?tipibando.id.codice=${tipibando.id.codice}&';
				var _captionTab='<fmt:message key="form.tipibandoinput.title.list" />';
				
				function nuovoInput(){
					if($('etichetta_id').value.length!=0){						
						doSubmit('insertDaLista.htm','',document.inviodatisubmit);						
					}else{
						alert('<fmt:message key="form.tipibandoinput.etichetta" /> <fmt:message key="alert.required" />');	
					}					
				}
				
			</script>
		<fieldset>
		<legend><fmt:message key="form.tipibandoinput.title.create" /></legend>
		<spring-form:form commandName="tipibandoinput" name="inviodatisubmit">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="tipibandoinput" />
			    </jsp:include>
			    <input type="hidden" name="tipibando.id.codice" value="${tipibando.id.codice}"/>
		    	<spring-form:hidden path="tipoinput" />
			<table>				
				<tr>
					<td><fmt:message key="form.tipibandoinput.etichetta" /></td>
					<td><spring-form:input id="etichetta_id" path="etichetta" size="100"  maxlength="150"/>
					<spring-form:errors path="etichetta" cssClass="error"/></td>
				</tr>				
			</table>
			
			<div id="functions">
				<ul>				
					<li><a href="javascript:nuovoInput();"><fmt:message key="button.aggiungi" /></a></li>				
				</ul>
			</div>
		</spring-form:form>
		</fieldset>
		</div>
		<br />
		<div id="functions">
			<ul>
				<%--
					<li><a href="javascript:doHref('create.htm?tipibando.id.codice=${tipibando.id.codice}','');"><fmt:message key="button.new" /></a></li>
				--%>
				<li><a href="javascript:doHref('../tipibando/view.htm?codice=${tipibando.id.codice}','');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>