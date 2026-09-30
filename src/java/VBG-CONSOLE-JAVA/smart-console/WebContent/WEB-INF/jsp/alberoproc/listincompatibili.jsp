<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.lista_endo_incompatibili" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.lista_endo_incompatibili" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="alberoprocCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocCommand" />
		    </jsp:include>
			
	<!-- variabili per la visualizzazione dei campi bottoni e campi nascosti -->
	<%
	   
		Set endoList = (Set) request.getAttribute("endoList");
		pageContext.setAttribute("sizeendoList", endoList.size());
	%>
	<script type="text/javascript">
	function selezionaAndDeselezionaTutti(size){
		
		if($('id_check_endo').checked){
			for(i=0;i<size;i++){
				$('endo_checkbox_id'+i).checked=true;
			}
		}else{
			for(i=0;i<size;i++){
				$('endo_checkbox_id'+i).checked=false;
			}
		}
	}
	</script>		
			
			<div class="parametriDiv">
			<div class="etichetta">
			<div><fmt:message key="label.procedimento" />:</div>
			<div><fmt:message key="label.endo_procedimento" />:</div>
			</div>
		<div class="parametro">
			<div><c:out value="${alberoprocCommand.vwAlberoproc.scDescrizione}" /></div>
			<div><c:out value="${alberoprocCommand.inventarioprocedimenti.procedimento}" /></div>
		</div>
		</div>
		
        <div class="jmesa" >
        <table border="0"  cellpadding="0"  cellspacing="0"  class="table">
				<thead>
				<tr  class="header">
					<td><fmt:message key="label.endo_procedimento"/></td>
					<td width="15%"><fmt:message key="label.incompatibile"/>
					 <input id="id_check_endo" type="checkbox" name="" onclick="selezionaAndDeselezionaTutti(${sizeendoList})" title="<fmt:message key="label.seleziona_tutto" />" ></input>
					
					</td>
				</tr>
				</thead>
                <%
			   	 	int i=0;
			    %>
				<tbody class="tbody">
				<c:forEach items="${alberoprocCommand.alberoproc.alberoprocEndos}" var="alberoproc_endo" varStatus="alberoproc_endo_index">
					<tr class="<%=(i%2)==0?"odd":"even"%>">
						<td>
							${alberoproc_endo.inventarioprocedimento.procedimento}
						</td>
                        <td>
                            <c:if test="${alberoproc_endo.inventarioprocedimento.flagIncompatibile==true}">
                            	<input id="endo_checkbox_id${alberoproc_endo_index.index}" align="right" checked="checked" type="checkbox" name="listaDiCodiciDegliEndoprocedimentiIncompatibili" value="${alberoproc_endo.inventarioprocedimento.id.codice}" ></input>    
						    </c:if>
						    <c:if test="${alberoproc_endo.inventarioprocedimento.flagIncompatibile==false}">
                            	<input id="endo_checkbox_id${alberoproc_endo_index.index}" align="right" type="checkbox" name="listaDiCodiciDegliEndoprocedimentiIncompatibili" value="${alberoproc_endo.inventarioprocedimento.id.codice}" ></input>
						    </c:if>
						    <input type="hidden" name="listaDiCodiciDeiEndoPerUnProcedimento" value="${alberoproc_endo.inventarioprocedimento.id.codice}" ></input>
						    
						</td>
					</tr>
				<%i++;%>
				</c:forEach>
				</tbody>
			</table>
	    </div>
			
		</spring-form:form>
	</div>
	<div id="functions">
	    <ul>
			<li><a href="javascript:doSubmit('addOrRemoveIncompatibilitaendo.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>