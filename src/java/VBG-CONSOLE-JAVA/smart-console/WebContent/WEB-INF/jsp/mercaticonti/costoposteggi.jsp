<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.mercatiConti.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.mercatiConti.costoposteggio" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
		   <jsp:param name="path" value="../mercaticonti/costoposteggi" />
	    </jsp:include>
		<div id="subcontent">
		<span class="parametri"><fmt:message key="form.mercatiConti.mercato" />:<label>${mercati.descrizione}</label></span><br/>

<fieldset><legend><fmt:message key="form.mercatiConti.dataprincipali"></fmt:message></legend>
          <input type="text" id="anno_id" size="4" value="${anno}" />
          <button type="button" class="functionsPlus" onclick="javascript:annoplus();" title="<fmt:message key="form.calendariomercatoParametri.anno.plus" />">+</button>
          <button type="button" class="functionsMinus" onclick="javascript:annominus();" title="<fmt:message key="form.calendariomercatoParametri.anno.minus" />">-</button>			
</fieldset>

			
<fieldset><legend><fmt:message key="form.mercatiConti.importigenerali"></fmt:message>
<a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../mercaticonti/list.htm?mercati.id.codice=${mercati.id.codice}','')"
					title="<fmt:message key="label.edit.record" /> ${ruolo_var.id}"><img
					src="${pageContext.request.contextPath}/images/edit.gif"
					alt="<fmt:message key="label.edit.record" /> ${ruolo_var.id}" /></a>

</legend>
        <div class="jmesa">
	   <table cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td><fmt:message key="form.mercatiConti.descrizione" /></td>
				<td><fmt:message key="form.mercatiConti.contesto" /></td>
				<td style="text-align: right"><fmt:message key="form.mercatiConti.valore" /></td>
				<c:if test="${mercati.flagConsorzio eq true}">
					 <td><fmt:message key="label.percentuale_consorzio" /></td>
				</c:if>
			</tr>	
		</thead>
		<tbody class="tbody">
        <%
         int j = 1;
	    %>
		<c:forEach var="mercatiContiList_var"
				items="${mercatiContiList}"
				varStatus="mercatiContiList_status">
				 <tr class="<%=(j % 2) == 0 ? "odd" : "even"%>">
					<td>${mercatiContiList_var.conti.descrizione}</td>
					<td>${mercatiContiList_var.contesto}</td>
					<td style="text-align: right">
							<c:choose>
								<c:when test="${mercatiContiList_var.flagValore == true}">
									<b><fmt:formatNumber minFractionDigits="2">${mercatiContiList_var.valore}</fmt:formatNumber></b>
									<fmt:message key="label.valuta" />								
								</c:when>
								<c:otherwise>
									<b><fmt:formatNumber minFractionDigits="5">${mercatiContiList_var.valore}</fmt:formatNumber></b>
									(<fmt:message key="form.mercatiConti.coefficiente" />)
								</c:otherwise>
							</c:choose>					
					</td>
					<c:if test="${mercati.flagConsorzio eq true}">
					 	<td>
						 	<c:if test="${mercatiContiList_var.percentualeConsorzio gt 0}">	
						 		${mercatiContiList_var.percentualeConsorzio}% (<b><fmt:formatNumber value="${mercatiContiList_var.transientImportoRidettato}" 
						 		minFractionDigits="2" maxFractionDigits="2"/> <fmt:message key="label.valuta" /></b>	)
						 	</c:if>	
					 	</td>
					</c:if>
       <%
	    j++;
	   %>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</fieldset>			

<fieldset><legend><fmt:message key="form.mercatiConti.importispecifici"></fmt:message></legend>
        
  <div class="jmesa">
	   <table cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
                <td><fmt:message key="form.mercatiConti.posteggio" /></td>
				<td><fmt:message key="form.mercatiConti.descrizione" /></td>
				<td><fmt:message key="form.mercatiConti.contesto" /></td>
				<td style="text-align: right"><fmt:message key="form.mercatiConti.valore" /></td>
				<c:if test="${mercati.flagConsorzio eq true}">
					 <td width="2%"><fmt:message key="label.percentuale_consorzio" /></td>
				</c:if>
                <td><fmt:message key="label.edit.record" /></td>
			</tr>	
		</thead>
		<tbody class="tbody">
        <%
         int k = 1;
	    %>
		<c:forEach var="mercatiDList_var"
				items="${mercatiDList}"
				varStatus="mercatiDList_status">
				 <tr class="<%=(k % 2) == 0 ? "odd" : "even"%>">
				   <%-- Posteggio --%>
                   <td>
					<span class="parametri"><label>${mercatiDList_var.codiceposteggio}</label></span>
                   

					</td>
                   <%-- Per ogni posteggio cicla la descrizione dei conti --%>
                   <td> 
                   <div class="jmesa">
	               <table cellpadding="2" cellspacing="0" border="0" >
                   <tbody class="tbody">
                   <c:forEach var="contiPosteggio_var"
				    items="${mercatiDList_var.listaContiPosteggio}"
				    varStatus="contiPosteggio_status">
                   <tr class="<%=(k % 2) == 0 ? "odd" : "even"%>">
                   <td>${contiPosteggio_var.conto.descrizione}</td>
				   </tr>
                   </c:forEach>
                   </tbody>
                   </table>
                   </div>
                   </td>
                   <%-- Per ogni posteggio cicla il contesto conti --%> 
                   <td> 
                   <div class="jmesa">
				   <table cellpadding="2" cellspacing="0" border="0" >
                   <tbody class="tbody">
                   <c:forEach var="contiPosteggio_var"
				    items="${mercatiDList_var.listaContiPosteggio}"
				    varStatus="contiPosteggio_status">
                   <tr class="<%=(k % 2) == 0 ? "odd" : "even"%>">
                   <td>${contiPosteggio_var.contesto}</td>
                   </tr>
                   </c:forEach>
                   </tbody>
                   </table>
			       </div>
                   </td>  
                   <%-- Per ogni posteggio cicla il valore dei conti --%> 
                   <td> 
                   <div class="jmesa">    
                   <table cellpadding="2" cellspacing="0" border="0" width="100%">
                   <tbody class="tbody">
                   <c:forEach var="contiPosteggio_var"
				    items="${mercatiDList_var.listaContiPosteggio}"
				    varStatus="contiPosteggio_status">
                    <tr class="<%=(k % 2) == 0 ? "odd" : "even"%>">
                   <td style="text-align: right">
                   		<fmt:formatNumber value="${contiPosteggio_var.valore}" minFractionDigits="2" maxFractionDigits="2"></fmt:formatNumber></td>
                   </tr>
                   </c:forEach>
                   </tbody>
                   </table>
                   </div>
                   </td>
                   
                   <c:if test="${mercati.flagConsorzio eq true}">
	                   <td>
	                    <div class="jmesa">    
		                   <table cellpadding="2" cellspacing="0" border="0" width="100%">
		                   <tbody class="tbody">
		                   <c:forEach var="contiPosteggio_var"
						    items="${mercatiDList_var.listaContiPosteggio}"
						    varStatus="contiPosteggio_status">
						    <c:if test="${contiPosteggio_var.percentualeConsorzio gt 0}">	
			                    <tr class="<%=(k % 2) == 0 ? "odd" : "even"%>">
				                   <td style="text-align: right">				                   
				                   		<fmt:formatNumber value="${contiPosteggio_var.percentualeConsorzio}" minFractionDigits="2" maxFractionDigits="2"></fmt:formatNumber>%
				                   		(<b><fmt:formatNumber value="${contiPosteggio_var.transientImportoRidettato}" minFractionDigits="2" maxFractionDigits="2"/> <fmt:message key="label.valuta" /></b>	)
				                   		</td>
				               </tr>
			               </c:if>    
		                   </c:forEach>		                   
		                   </tbody>
		                   </table>
		                   </div>
					 	</td>
					</c:if>
                   
                                      
                   <%-- opzione modifica --%>
                   <td> 
                        <a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../mercatidconti/list.htm?posteggio.id.codice=${mercatiDList_var.id.codice}','')"
					       title="<fmt:message key="label.edit.record" /> ${ruolo_var.id}"><img
					       src="${pageContext.request.contextPath}/images/edit.gif"
					       alt="<fmt:message key="label.edit.record" /> ${ruolo_var.id}" /></a>
                   </td>
       <%
	    k++;
	   %>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>

 
</fieldset>



<script type="text/javascript">

     function annoplus(mercatoid,anno)
     {
         var year=parseFloat($('anno_id').value)+parseFloat(1);
         doHref('../mercaticonti/costoposteggi.htm?mercati.id.codice=${mercati.id.codice}&anno='+year); 
     }

     function annominus(mercatoid,anno)
     {
    	 var year=parseFloat($('anno_id').value)-parseFloat(1);
         doHref('../mercaticonti/costoposteggi.htm?mercati.id.codice=${mercati.id.codice}&anno='+year); 
     }
	
</script>
			
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('adeguamentoIstatStep1.htm?codiceMercato=${mercati.id.codice}&anno='+$('anno_id').value,'')"><fmt:message key="button.adeguamento_istat" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>