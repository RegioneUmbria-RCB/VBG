<%@ include file="../includes/taglibs.jsp"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="form.sorteggi.title" />
</title>
</head>
<body>
<script type="text/javascript">
			//<![CDATA[
				 var arrayCC = new Array();
				 var arraySC = new Array();				
		   		 
		   		 function changeCCStatus(obj){
		   		 	var i = 0;
		   		 	while(i < arrayCC.length){
						if(obj.checked){
							$('checkCC'+arrayCC[i]).checked=true;
						}else{
							$('checkCC'+arrayCC[i]).checked=false;
						}
						i++;
					}
		   		 }
		   		 
		   		 function changeSCStatus(obj){
		   		 	var i = 0;
		   		 	while(i < arraySC.length){
						if(obj.checked){
							$('checkSC'+arraySC[i]).checked=true;
						}else{
							$('checkSC'+arraySC[i]).checked=false;
						}
						i++;
					}
		   		 }
		    //]]> 
</script>
<span class="titoloPagina">
	<fmt:message key="form.sorteggi.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>	
<div id="subcontent">
<spring-form:form commandName="sorteggiCategorie" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="sorteggiCategorie" />
	</jsp:include>
	<span class="parametri">
		<fmt:message key="form.sorteggi.categoria" />:<label><c:out value="${sorteggiCategorie.descrizione}" /></label>
	</span>
	<div class="jmesa">
		<table class="table">
			<thead class="header">
				<tr>
					<td><fmt:message key="form.sorteggi.descrizione" /></td>
					<td><fmt:message key="form.sorteggi.data" /></td>
					<td><fmt:message key="form.sorteggi.categoria" /></td>
					<td><input type="checkbox" name="" onclick="changeSCStatus(this);" title="<fmt:message key="label.checkbox.selDeselAll" />"/><fmt:message key="form.sorteggi.associa" /></td>
				</tr>
			</thead>
					<tbody class="tbody">
						<%int x=0; %>
						<c:forEach items="${sorteggiCategorie.sorteggiList}" var="sorteggio" varStatus="sorteggioIdx">
						<tr class="<%=(x%2)==0?"odd":"even"%>">
							<td>${sorteggio.stDescrizione}</td>
							<td><fmt:formatDate value="${sorteggio.stDatasorteggio}" pattern="dd/MM/yyyy"/></td>
							<td>${sorteggio.categoria.descrizione}</td>
							<td><spring-form:checkbox id="checkCC${sorteggioIdx.index }" path="sorteggiList[${sorteggioIdx.index}].transientAssociaCategoria" /></td>
						</tr>
						<script type="text/javascript">
							arrayCC.push('${sorteggioIdx.index}');
						</script>
						<%x++; %>
						</c:forEach>
						
						<c:forEach items="${sorteggiCategorie.transientSorteggiSenzaCategoriaList}" var="sorteggioSC" varStatus="sorteggioSCIdx">
						<tr class="<%=(x%2)==0?"odd":"even"%>">
							<td>${sorteggioSC.stDescrizione}</td>
							<td><fmt:formatDate value="${sorteggioSC.stDatasorteggio}" pattern="dd/MM/yyyy"/></td>
							<td>&nbsp;</td>
							<td><spring-form:checkbox id="checkSC${sorteggioSCIdx.index }" path="transientSorteggiSenzaCategoriaList[${sorteggioSCIdx.index}].transientAssociaCategoria" /></td>
						</tr>
						<script type="text/javascript">
							arraySC.push('${sorteggioSCIdx.index}');
						</script>
						<%x++; %>
						</c:forEach>
						
					</tbody>
				</table>
				</div>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${not empty sorteggiCategorie.sorteggiList or not empty sorteggiCategorie.transientSorteggiSenzaCategoriaList}">
	<li><a href="javascript:doSubmit('updateSorteggi.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('view.htm?codice=${sorteggiCategorie.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>