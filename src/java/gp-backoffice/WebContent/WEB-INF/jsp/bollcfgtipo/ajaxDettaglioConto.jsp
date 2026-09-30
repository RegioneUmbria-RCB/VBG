<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<table width="100%">       
	<tr>
		<td>		     
			<input id="conto_id" name="conto" class="searchbox" size="40" onkeydown="return searchAll(this,event)" />
			<init:autocompleter methodAjax="findConti.htm" afterUpdateElement="setHiddenFieldConti"  
				idHidden="conto_id_hidden" idInput="conto_id" inputTitleKey="" minChars="1" />
			<input type="hidden" id="conto_id_hidden" name="conto_id_hidden" />    	
		</td>
	</tr>
	<tr>
		<td><div id="messaggioErrore" class="error_header" style="display:none;"></div></td>
	</tr>
</table>

<div class="vbg-form">
	<table class="vbg-table">
		<thead>
			<tr class="header">	
				<th width="80%"><fmt:message key="label.conto" /></th>
				<th width="20%"><fmt:message key="label.azioni" /></th>
			</tr>
		</thead>
		<tbody>			
		<c:forEach items="${contiList}" var="entry" varStatus="idx">
		<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
		        <c:choose>
		        	<c:when test="${codiceConto == entry.conto.id.codice}">
		        		<c:set value="#aaf981" var="color" scope="page"></c:set>
		        	</c:when>
		            <c:otherwise><c:set value="" var="color" scope="page"></c:set></c:otherwise>
		        </c:choose>
    	 	    <td style="background-color:${color};">${entry.conto.descrizione}</td>
    	 		<td style="background-color:${color}">
    	 			<a class="azioni" style="float: none;" href="javascript:eliminaConto(${entry.conto.id.codice})" 
    	 				title="<fmt:message key="label.elimina" /> ${entry.conto.id.codice}">
				    	<i class="fa fa-trash-o"></i><fmt:message key="label.elimina" />
					</a>			
				</td>
		</tr>    	 
		</c:forEach>				
		</tbody>
	</table>		
</div>