<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<table>       
       <tr>
      	<td>	
       		<fmt:message key="label.comune" />
      	</td>
		<td style="padding-left: 15px;">
			 		     
			<input id="comune_id" name="comune" class="searchbox" size="40" onkeydown="return searchAll(this,event)" />
			<init:autocompleter methodAjax="findComuni.htm" afterUpdateElement="setHiddenFieldComune"  idHidden="comune_id_hidden" idInput="comune_id" inputTitleKey="" minChars="1" />
			<input type="hidden" id="comune_id_hidden" name="comune_id_hidden" />    	
    	</td>
    </tr>
    <tr>
    	<td><div id="messaggioErrore" class="error_header" style="display:none;"></div></td>
    </tr>
</table>

<div class="jmesa">
	<table width="100%">
		<thead>
			<tr class="titoloSezione">	
				<td style="width:50%"><fmt:message key="label.comune" /></td>
				<td style="width:50%"><fmt:message key="label.azioni" /></td>
			</tr>
		</thead>
		<tbody>	
			<c:forEach items="${mailConfigComuni}" var="entry" varStatus="idx">
				<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
			        <c:choose>
			        	<c:when test="">
			        		<c:set value="#aaf981" var="color" scope="page"></c:set>
			        	</c:when>
			            <c:otherwise><c:set value="" var="color" scope="page"></c:set></c:otherwise>
			        </c:choose>
    	 	    <td style="background-color:${color};">${entry.comune}</td>
    	 		<td style="background-color:${color}">
    	 			<a class="eliminaRiga" style="float: none;" href="javascript:eliminaComune(${entry.id})" title="<fmt:message key="label.elimina" /> ${entry.codiceComune}">
				     	<label><fmt:message key="label.elimina.image" /></label>
					</a>			
				</td>
		</tr>    	 
		</c:forEach>			
		</tbody>
	</table>		
</div>