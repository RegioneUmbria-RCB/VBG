<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.istanze.dainserire.title.list" /></title>
	</head>
	<body>
		<script type="text/javascript">
			//<![CDATA[
				 var arrayM = new Array();
				 var arrayMP = new Array();				
		   		 
		   		 function changeMStatus(obj){
		   		 	var i = 0;
		   		 	while(i < arrayM.length){
						if(obj.checked){
							$('checkM'+arrayM[i]).checked=true;
						}else{
							$('checkM'+arrayM[i]).checked=false;
						}
						i++;
					}
		   		 }
		   		 
		   		 function changeMPStatus(obj){
		   		 	var i = 0;
		   		 	while(i < arrayMP.length){
						if(obj.checked){
							$('checkMP'+arrayMP[i]).checked=true;
						}else{
							$('checkMP'+arrayMP[i]).checked=false;
						}
						i++;
					}
		   		 }
		    //]]> 
		</script>
		<span class="titoloPagina"><fmt:message key="form.istanze.dainserire.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		<div id="subcontent">
			<spring-form:form commandName="istanzeCommand" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
        			<jsp:param name="commandName" value="istanzeCommand" />
    			</jsp:include>
				<span class="parametri">
		             <fmt:message key="form.istanze.movimentoDaInserire" />:<label> <c:out value="${istanzeCommand.tipoMovimentoDaInserire.movimento}"/></label>
		        </span>
		        <span class="parametri">
		             <fmt:message key="form.istanze.soggettoMovimento" />:<label> <c:out value="${istanzeCommand.soggettoMovimento}"/></label>
		        </span>
				<div class="jmesa">
				<table class="table">
					<thead class="header">
						<tr>
							<td><fmt:message key="form.istanze.numero" /></td>
							<td><fmt:message key="form.istanze.data" /></td>
							<td><fmt:message key="form.istanze.richiedente" /></td>
							<td><fmt:message key="form.istanze.alberoproc" /></td>
							<td><fmt:message key="form.istanze.checkbox.mov" /></td>
							<td><fmt:message key="form.istanze.checkbox.protmov" /></td>
						</tr>
					</thead>
					<tbody class="tbody">
						<%int x=0; %>
						<c:forEach items="${istanzeCommand.istanzeList}" var="istanza" varStatus="istanzaIdx">
						<tr class="<%=(x%2)==0?"odd":"even"%>">
							<td>${istanza.numeroistanza}</td>
							<td><fmt:formatDate value="${istanza.data}" pattern="dd/MM/yyyy"/></td>
							<td>${istanza.richiedente.descrizioneRichiedente}</td>
							<td>${istanza.alberoproc.vwAlberoproc.scDescrizione}</td>
							<td><spring-form:checkbox id="checkM${istanzaIdx.index }" path="istanzeList[${istanzaIdx.index}].transientCheckMov" /></td>
							<td><spring-form:checkbox id="checkMP${istanzaIdx.index }" path="istanzeList[${istanzaIdx.index}].transientCheckProtMov" /></td>
						</tr>
						<script type="text/javascript">
							arrayM.push('${istanzaIdx.index}');
							arrayMP.push('${istanzaIdx.index}');
						</script>
						<%x++; %>
						</c:forEach>
						
					</tbody>
				</table>
				</div>
				<fieldset>
    			<legend><fmt:message key="form.istanze.stampaLettera.legend" /></legend>
			    <div>
			    	<table>
						<tr>
							<td><fmt:message key="form.istanze.stampaLettera" /></td>
							<td>
							
								<spring-form:textarea id="tipoLettera_id"  tabindex="4" path="letteretipo.descrizione" cssClass="searchbox" onchange="checkValue(this,'tipoLettera_hidden')" onkeydown="javascript:return searchAll(this,event)" cols="62" rows="2" />
								<init:autocompleter methodAjax="findLettereTipo.htm" idHidden="tipoLettera_hidden" idInput="tipoLettera_id" inputTitleKey="label.ricerca_lettera_tipo"/>
								<spring-form:errors path="letteretipo.descrizione" cssClass="error"/> 
								<spring-form:hidden	id="tipoLettera_hidden" path="letteretipo.id.codice"/>
							</td>
						</tr>
					</table>
					<div id="functions">
					<ul>		
						<li><a href="javascript:checkStampa();"><fmt:message key="button.stampa_lettera" /></a></li>
					</ul>
					</div>
				</div>
				</fieldset>
				<script type="text/javascript">
					function checkStampa(){
					if($('tipoLettera_hidden').value == ''){
						alert('<fmt:message key="form.istanze.tipoLettera.alert" />');
						$('tipoLettera_id').focus();
						return;
					}
					doSubmit('stampa.htm','');
				}
				</script>
			</spring-form:form>
				
		</div>
		<div id="functions">
			<ul>
				<c:if test="${not empty istanzeCommand.istanzeList}">
				<li><a href="javascript:doSubmit('insertMovimenti.htm','');"><fmt:message key="button.insertMovimenti" /></a></li>
				</c:if>
				<c:if test="${codiceGraduatoria == null}">
				<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
				</c:if>
				<c:if test="${codiceGraduatoria != null}">
				<li><a href="javascript:doHref('listDaGraduatoria.htm?codiceGraduatoria=${codiceGraduatoria }','')"><fmt:message key="button.back" /></a></li>
				</c:if>
			</ul>
		</div>
	</body>
</html>