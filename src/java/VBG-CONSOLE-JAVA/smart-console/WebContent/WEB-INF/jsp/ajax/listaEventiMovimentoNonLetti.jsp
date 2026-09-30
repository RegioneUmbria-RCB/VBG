<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
   <div class="jmesa" >
	 <table width="100%" border="0"  cellpadding="0"  cellspacing="0"  class="table">
		<thead>
		<tr class="header">
			<td width="80%"><fmt:message key="label.evento"/></td>
			<td width="20%"><fmt:message key="label.letto"/></td>
		</tr>
		</thead>
        <%
	    	int i=0;
	    %>
		<tbody class="tbody">
		<c:forEach items="${istanzeeventiList}" var="eventimovimento" varStatus="a">														
			  <tr class="<%=(i%2)==0?"odd":"even"%>">
			      <td>${eventimovimento.descrizione}</td>		
				  <td>
					   <input id="flagLettoId${a.index}" type="checkbox" onclick="changeCheckboxValue(flagLettoId${a.index},'${pageContext.request.contextPath}/istanzeeventi/ajaxChangeFlagLetto.htm?codice=${eventimovimento.id.codice}')" ${eventimovimento.flagLetto?'checked':''} />
		          </td>
			</tr>
		<%i++;%>
		</c:forEach>
		</tbody>
   </table>
   <div id="functions">
			<ul>
				<li><a href="javascript:historySet('${_urlback}','../movimenti/listElaborazione.htm?codiceIstanza=${codIstanza}','');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
   
 </div>

