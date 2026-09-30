<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


 <!-- TABELLA FINALE JMESA (NODO FINALE)-->
    		  <!-- STAR -->
    		   
   <div class="jmesa" >
   <%-- 
     <c:if test="${islistLimitata eq true}">
    	 <div>${alertMessage}</div>
     </c:if>
     --%>
	 <table width="100%" border="0"  cellpadding="0"  cellspacing="0"  class="table">
		<thead>
		<tr  class="header">
			<td width="2%"><fmt:message key="label.codice"/></td>
			<td width="25%"><fmt:message key="label.procedimento"/></td>
			<td width="25%"><fmt:message key="label.amministrazione"/></td>
			<td width="14%"><fmt:message key="label.natura"/></td>
			<td width="10%"><fmt:message key="label.data"/></td>
			<td width="8%"><fmt:message key="label.attiva"/></td>
			<%-- 
			<td width="8%"><fmt:message key="label.acquis"/></td>
			<td width="8%"><fmt:message key="label.comm_conf"/></td>
			--%>
		</tr>
		</thead>
        <%
	    	int i=0;
	    %>
		<tbody class="tbody">
		<c:forEach items="${listInventarioprocedimento}" var="inventarioprocedimenti" varStatus="c">														
			<tr id="id_endo_${inventarioprocedimenti.id.codice}" class="<%=(i%2)==0?"odd":"even"%>">
				<td><a name="id_endo_${inventarioprocedimenti.id.codice}_a"/>${inventarioprocedimenti.id.codice}</td>
				<td>${inventarioprocedimenti.procedimento}</td>		
				<td>${inventarioprocedimenti.amministrazioni.amministrazione}</td>
				<td>${inventarioprocedimenti.naturaendo.natura}</td>
				<td><input type="text" id="data_id${inventarioprocedimenti.id.codice}"
						value="<fmt:formatDate value="${istanzeprocedimentiCommand.dataattivazioneCommnad}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />"
						name="dataattivazioneCommnad${inventarioprocedimenti.id.codice}" size="8"
						 onblur="isValidDate(this,true);"/>
                        <a id="caldata${inventarioprocedimenti.id.codice}" 
						 title="<fmt:message key="label.calendar"/>"> 
						 <img src="${pageContext.request.contextPath}/images/cal.gif" alt="<fmt:message key="label.calendar"/>"/></a>
						 <script type='text/javascript'>
						 	setTimeout('setupCal("data_id${inventarioprocedimenti.id.codice}", "caldata${inventarioprocedimenti.id.codice}")', 2000);							 
						 </script>
		       </td>
		       <td>
		       <input id="checkbox_aut_id${inventarioprocedimenti.id.codice}" type="checkbox"
						value="${inventarioprocedimenti.id.codice}" name="insertAutorizzazione"   
						 />
		       </td>
		       <%-- 
		       <td>
		       <input id="checkbox_acq_id${inventarioprocedimenti.id.codice}" type="checkbox"
					  value="${inventarioprocedimenti.id.codice}" name="insertAcquisto"
					  onclick="setOneCheckBox('checkbox_aut_id${inventarioprocedimenti.id.codice}','checkbox_comm_id${inventarioprocedimenti.id.codice}');" />
		       </td>
		       <td>
		       <input id="checkbox_comm_id${inventarioprocedimenti.id.codice}" type="checkbox"
					  value="${inventarioprocedimenti.id.codice}" name="insertAmmissione"
					  onclick="setOneCheckBox('checkbox_acq_id${inventarioprocedimenti.id.codice}','checkbox_aut_id${inventarioprocedimenti.id.codice}');" />
		       </td>
		       --%>
			</tr>
		<%i++;%>
		</c:forEach>
		</tbody>
		
   </table>
 </div>

