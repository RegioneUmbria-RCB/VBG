<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
		<fmt:message key="form.foarjstepstestata.title" />
</title>
</head>
<body>
	<span class="titoloPagina"> 
			<fmt:message key="form.foarjstepstestata.title" />
	</span>

	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../foArjStepsTestata/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="foArjStepsTestata" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="foArjStepsTestata" />
			</jsp:include>
			<table>
				<tr>
					<td><fmt:message key="label.descrizione" /></td>
					<td><spring-form:input id="descrizione_id" path="descrizione"
							size="70" /> <spring-form:errors path="descrizione"
							cssClass="error" /></td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<script type='text/javascript'>
		$('descrizione_id').focus();

		function eliminaStep(idStep) {
			doHref(
					'deleteStep.htm?codiceTestata=${foArjStepsTestata.id.codice}&codice='
							+ idStep,
					'<fmt:message key="javascript.confirm.delete" />');
		}
	</script>
	<div id="functions">
		<ul>
			<c:if test="${foArjStepsTestata.id.codice==null}">
				<li><a
					href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message
							key="button.insert" /></a></li>
			</c:if>
			<c:if test="${foArjStepsTestata.id.codice!=null}">
				<li><a
					href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message
							key="button.update" /></a></li>
				<li><a
					href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
							key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message
						key="button.back" /></a></li>
		</ul>

		<c:if test="${foArjStepsTestata.id.codice!=null}">
			<br />
			<div class="jmesa">
				<table border="0" width="70%" cellpadding="4" cellspacing="2"
					class="table">
					<thead>
						<tr class="header">
							<td colspan="2" width="5%"><fmt:message key="label.ordine" /></td>
							<td width="5%"><fmt:message key="label.titolo" /></td>
							<td><fmt:message key="label.descrizione" /></td>
							<td><fmt:message key="label.parametri" /></td>							
							<td><fmt:message key="label.abilitato" /></td>							
							<td><fmt:message key="label.azioni" /></td>
						</tr>
					</thead>
					<tbody class="tbody">
						<%
						    int i = 1;
						%>
						<c:forEach var="listaStepsConfigurati_var"
							items="${listaStepsConfigurati}"
							varStatus="listaStepsConfiguratiStatus">

							<tr class="<%=(i%2)==0?"odd":"even"%>" onmouseover="this.className='highlight'"  onmouseout="this.className='<%=(i%2)==0?"odd":"even"%>'">
								<td>
									<c:if test="${listaStepsConfiguratiStatus.count lt fn:length(listaStepsConfigurati)}">
										<a class="downColumn" style="border: none;" 
											href="updateOrdineStep.htm?isUp=true&codiceTestata=${foArjStepsTestata.id.codice}&codiceStep=${listaStepsConfigurati_var.id.codice}"
											title="<fmt:message key="label.down" /> "> <label><fmt:message
														key="label.azioni" /></label>
										</a> 
									</c:if>
								</td>								
								<td>
									<c:if test="${listaStepsConfiguratiStatus.index gt 0}">
										 <a class="upColumn" style="border: none;" 
											href="updateOrdineStep.htm?isUp=false&codiceTestata=${foArjStepsTestata.id.codice}&codiceStep=${listaStepsConfigurati_var.id.codice}"
											title="<fmt:message key="label.up" /> "> <label><fmt:message
														key="label.azioni" /></label>
										</a>
									</c:if>
								</td>
								<td title="${listaStepsConfigurati_var.foArjStepsBase.nomeStep}">${listaStepsConfigurati_var.titolo}</td>
								<td><c:choose>
										<c:when
											test="${fn:length(listaStepsConfigurati_var.descrizione)>100}">
										${fn:substring(listaStepsConfigurati_var.descrizione,0, 100)}...
										<init:help
												idHelp="helpTitle_${listaStepsConfigurati_var.id.codice}"
												text="${listaStepsConfigurati_var.descrizione}" />
										</c:when>
										<c:otherwise>
										${listaStepsConfigurati_var.descrizione}
									</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:if test="${fn:length(listaStepsConfigurati_var.foArjStepsParamses) gt 0}">
										
											<c:forEach items="${listaStepsConfigurati_var.foArjStepsParamses}" var="parametri">
												<b>${parametri.foArjStepsParamsBase.chiave}</b>: ${parametri.valore}</li>
												<br />
											</c:forEach>											
										
									</c:if>
								</td>								
								
								<td><c:if
										test="${listaStepsConfigurati_var.abilitato eq true}">
										<fmt:message key="label.si" />
									</c:if> <c:if test="${listaStepsConfigurati_var.abilitato eq false}">
										<fmt:message key="label.no" />
									</c:if>
								</td>
								<td><a style="border: none;" class="dettaglioColumn"
									href="../foarjsteps/view.htm?codice=${listaStepsConfigurati_var.id.codice}"><label><fmt:message
												key="label.edit.record.image" /></label></a> <a
									style="clear: both; border: none;" class="eliminaRiga"
									style="float: none;"
									href="javascript:eliminaStep(${listaStepsConfigurati_var.id.codice})"
									title="<fmt:message key="label.elimina" />"> <label><fmt:message
												key="label.elimina.image" /></label>
								</a></td>
							</tr>
							<%i++; %>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${not empty listaStepRimasti}">
				<div id="functions">
					<ul>
						<li><a
							href="javascript:doSubmit('../foarjsteps/create.htm?codiceTestata=${foArjStepsTestata.id.codice}','',document.inviodati)"><fmt:message
									key="button.nuovo_step" /></a></li>
					</ul>
				</div>
			</c:if>
			
		</c:if>


	</div>
</body>
</html>
