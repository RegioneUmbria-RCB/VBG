<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.localizzazione_scheda_endotipo1" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.localizzazione_scheda_endotipo1" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="partelocaleschedaendotipo1" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="partelocaleschedaendotipo1" />
		    </jsp:include>
		    <c:if test="${not empty param.msg}">
		    	<div id="status_msg" class="error_header" >${param.msg }</div>
		    </c:if>
		    <!--  INFO INIZIALI STAR  -->
		    	<!-- TABELLA PRINCIPALE -->

		    
		     <!--  ELENCO NORMATIVE LOCALI (TIPO 1) START -->
				    <fieldset><legend><fmt:message key="label.endo1_quadro_e"/></legend>
					<div class="jmesa">
					<table  cellpadding="2" cellspacing="0" class="table"  width="100%">
						<thead>
						 	<tr class="header">
						 		<td><fmt:message key="label.normativa_regionale"/></td>
						 		<td><fmt:message key="label.normativa_comunale"/></td>
						 		<td><fmt:message key="label.url"/></td>				 		
						 	</tr>
						</thead>	
				  	   <tbody class="tbody">
				  	   <c:if test="${fn:length(partelocaleschedaendotipo1.regolamentoComunaleHelpers)==0}">
			                <tr class="even">
			                 	<td colspan="4"><fmt:message key="label.informazione_non_presente" /></td>
				            </tr>
				            </c:if>
				            <%int z=1;%>
						  	<c:forEach items="${partelocaleschedaendotipo1.regolamentoComunaleHelpers}" var="regolamentocomunale1" varStatus="a">
						    <tr class="<%=(z%2)==0?"odd":"even"%>">
								<td valign="top">
									<b>${regolamentocomunale1.descrizioneAdempimento}</b>
									<br />
									<b><fmt:message key="label.normativa_nazionale"/>:</b> ${regolamentocomunale1.normativaRegionale.normaNazionale.value}
									<br />
									<b><fmt:message key="label.normativa_regionale"/>: </b>${regolamentocomunale1.normativaRegionale.normaRegionale.value}
								</td>
								<td valign="top">
									<spring-form:textarea path="regolamentoComunaleHelpers[${a.index}].value" rows="4" cols="50"/>
								</td>								
								<td valign="top">
									<spring-form:input path="regolamentoComunaleHelpers[${a.index}].url" size="50"/>									
								</td>								
							</tr>
							<%z++; %>
							</c:forEach>
								</tbody>
							</table>							
					 	</div>
		 			</fieldset>
	   		
			<!--  ELENCO NORMATIVE LOCALI (TIPO 1) END -->
		    
            
            <!--  DOCUMENTAZIONE LOCALE START -->
			<table width="100%">
	 	     <tr>
			     <td colspan="2" class="titoloSezione"><fmt:message key="label.endo1_quadro_f"/></td>
		     </tr>
		      <tr>
		        	<td>
						<fmt:message key="label.destinatario_documentazione" />
					</td>
				    <td>
				     	<spring-form:textarea id="destinatario_documentazione_id" path="entity.documentazioneLocale.destinatarioDocumentazione" rows="4" cols="70" />
			        </td>
		      </tr>
		      <tr>
		        	<td>
						<fmt:message key="label.note" />
					</td>
				    <td>
				     	<spring-form:textarea id="note_documentazione_id" path="entity.documentazioneLocale.noteDocumentazione" rows="4" cols="70" />
			        </td>
		      </tr>
		    <!-- DOCUMENTAZIONE LOCALE END -->
           
            <!-- PAGAMETO LOCALE START -->

			<tr>
				<td colspan="2" class="titoloSezione"><fmt:message key="label.endo1_quadro_g"/></td>
			</tr>
		      <tr>
		        	<td>
						<fmt:message key="label.contributi_oneri" />
					</td>
				    <td>
				     	<spring-form:textarea id="contributi_oneri_id" path="entity.pagamentoLocale.contributiOneri" rows="4" cols="70" />
			        </td>
		      </tr>
		      <tr>
		        	<td>
						<fmt:message key="label.diritti_segreteria" />
					</td>
				    <td>
				     	<spring-form:textarea id="diritti_segreteria_id" path="entity.pagamentoLocale.dirittiSegreteria" rows="4" cols="70" />
			        </td>
		      </tr>
		      <tr>
		        	<td>
						<fmt:message key="label.diritti_istruttoria_suap" />
					</td>
				    <td>
				     	<spring-form:textarea id="diritti_struttoria_SUAP_id" path="entity.pagamentoLocale.dirittiIstruttoriaSUAP" rows="4" cols="70"  />
			        </td>
		      </tr>
		      <tr>
		        	<td>
						<fmt:message key="label.note" />
					</td>
				    <td>
				     	<spring-form:textarea id="note_pagamento_id" path="entity.pagamentoLocale.notePagamento" rows="4" cols="70"  />
			        </td>
		      </tr>
		   
		   <!-- PAGAMETO LOCALE END -->
           
           <!-- ALTRE INFO START -->

		   	  <tr class="titoloSezione">
		   			<td  colspan="2"><fmt:message key="label.endo1_quadro_l"/></td>
		   	   </tr>
		      <tr>
		        	<td>
						<fmt:message key="label.adempimenti_successivi_locali" />
					</td>
				    <td>
				     	<spring-form:textarea id="adempimenti_successivi_locali_id" path="entity.adempimentiSuccessiviLocali" rows="4" cols="70"  />
			        </td>
		      </tr>
		      <tr class="titoloSezione">
		   			<td  colspan="2"><fmt:message key="label.endo1_quadro_n"/></td>
		   	   </tr>
		      <tr>
		        	<td>
						<fmt:message key="label.note" />
					</td>
				    <td>
				     	<spring-form:textarea id="note_locali_id" path="entity.noteLocali" rows="4" cols="70"  />
			        </td>
		      </tr>
		      
		    </table>
		    <!-- ALTRE INFO END -->
				
	</spring-form:form>
	</div>
	<div id="functions">
		<ul>
		    <li><a href="javascript:doSubmit('invia.htm','',document.inviodati)"><fmt:message key="button.save" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>