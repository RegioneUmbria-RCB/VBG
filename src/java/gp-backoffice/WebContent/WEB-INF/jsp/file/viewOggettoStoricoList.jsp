<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<c:set var="currtime"><%=System.currentTimeMillis() %></c:set>
<div class="vbg-form">
<fieldset>
			<legend><fmt:message key='label.lista_storico' /></legend>
			
				<table class="vbg-table">
					<thead>
						<tr>							
							<th width="5%"><fmt:message key="label.nome" /> </th>
							<th width="2%"><fmt:message key="label.data" /></th>
			                <th width="2%"><fmt:message key="label.azioni" /></th>
			            </tr>
					</thead>
					<tbody>
						<%int i=1;%>
						<c:forEach items="${oggettiStorici}" var="oggettiStorico_var">
							<tr valign="top">
						      <td>						
									${oggettiStorico_var.nomeFile}
							  </td>
							  <td>
							  		<fmt:formatDate value="${oggettiStorico_var.dataSostituzione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
							  </td>		    	              
				              <td>					                  	 
				               	<a class=visualizzaDocColumn style="float: none;" href="../file/ajaxDownload.htm?fileId=${oggettiStorico_var.codiceOggetto}" title="<fmt:message key="label.download" /> ${oggettiStorico_var.codiceOggetto}">
				              	 <label><fmt:message key="label.elimina.azioni" /></label>
					            </a>
								<c:if test="${oggettiStorico_var.ripristinabile}">
									<a class="btn-ripristina" href="javascript: void(0);" onClick="ripristina${idElemento}(${oggettiStorico_var.id});" data-id="${oggettiStorico_var.id}" >
									Ripristina
									</a>
								</c:if>
				              </td>
							</tr>
							<%i++; %>
						</c:forEach>
					</tbody>
				</table>
</fieldset>
</div>