<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.localizzazione_scheda_endotipo2" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.localizzazione_scheda_endotipo2" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="stp.label.nome_attivita" />:</div>				
			</div>		
			<div class="parametro">       		 	
				<div>${descrizioneEndo}</div>
			</div>
		</div>
<br class="clear"/> 

<script type="text/javascript">
	function validateForm(theForm){
		if($('suap_id').value==''){
			alert('<fmt:message key="field.required" />');
			$('suap_id').focus();
			return false;
		}
		return true;
	}

</script>

		<spring-form:form commandName="partelocaleschedaendotipo2" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="partelocaleschedaendotipo2" />
		    </jsp:include>
		    <!--  INFO INIZIALI STAR  -->
		    <c:if test="${not empty param.msg}">
		    	<div id="status_msg" class="error_header" >${param.msg }</div>
		    </c:if>
			<table width="100%">			
				<tr class="titoloSezione">
		        	<td colspan="2">
						<fmt:message key="label.comune" />: ${partelocaleschedaendotipo2.entity.SUAP }
			        </td>
		        </tr>
		        <tr class="titoloSezione">
					<td colspan="2">
						<fmt:message key="label.titolo_quadro_b" />
					</td>
				</tr>
		        
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:textarea id="descrizioneLocale_id" path="entity.descrizioneLocale" rows="4" cols="70" />
						<spring-form:errors path="entity.descrizioneLocale" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.altre_info_locali" />
					</td>
					<td>
						<spring-form:textarea id="altreInfoLocal_id" path="entity.altreInfoLocali" rows="4" cols="70" />
						<spring-form:errors path="entity.altreInfoLocali" cssClass="error"/>
					</td>
				</tr>
				</table>
				<!--  INFO INIZIALI END -->
				
				
				<!--  ELENCO ENDO START -->
		     
				<div class="titoloSezione"><fmt:message key="label.titolo_quadro_d"/></div>
				
				<!--  ELENCO ENDO PRIMA START -->
				
				<fieldset><legend><fmt:message key="label.elenco_endo_prima"/></legend>
				
				<!-- TABELLA PRINCIPALE -->
				
				
				<div class="jmesa">
				<table  cellpadding="2" cellspacing="0" class="table"  width="100%">
					<%-- 
					COMMENTATO IN QUANDO NON DEVONO ESSERE PIù INVIATI AL MOMENTO
					<thead>
					 <tr class="header">
				 		<td><fmt:message key="label.elenco_endo_regionali"/></td>
				 	</tr>
					</thead>	
				  	<tr>	
				  		<td valign="top" >
				  		<!--  TABELLA SECONDARIA (REGIONALI)  -->
				  			<table width="100%">
				  	  			 <thead>
				  	     			<tr class="header">
										<td><fmt:message key="label.descrizione" /></td>
										<td><fmt:message key="label.azioni" /></td>
									</tr>
					 			 </thead>	
			  	   				 <tbody class="tbody">
			  	   				 <c:choose>
		               				<c:when test="${fn:length(partelocaleschedaendotipo2.inventarioprocedimentisPrima)==0}">
		                 			<tr class="even">
		                 				<td colspan="2"><fmt:message key="label.informazione_non_presente" /></td>
		                 			</tr>
		              				</c:when>
		              				<c:otherwise>
			               				<%int j=1;%>
					  	  				<c:forEach items="${partelocaleschedaendotipo2.inventarioprocedimentisPrima}" var="endotipo1" varStatus="a">
										<tr class="<%=(j%2)==0?"odd":"even"%>">
											<td>											
												${endotipo1.inventarioprocedimenti.procedimento}
											</td>
											<td width="1%">
												<a class="eliminaRiga" href="javascript:doHref('deleteElencoEndoRegionali.htm?elemento=${endotipo1.inventarioprocedimenti.id.codice}','')" title="<fmt:message key="label.delete" /> ${a.index}">
												<label><fmt:message key="label.edit.record.image" /></label>
							    				</a> 
											</td>
										</tr>
										<%j++; %>
										</c:forEach>
									</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<br/>
							<table>
								<tr style="padding-top: 7px;">
									<td>
										<fmt:message key="label.descrizione" />
									</td>
						     		<td>
						     			<input type="text" id="elencoEndoRegionaliPrima_id" name="_elencoEndoRegionaliPrima" class="searchbox" onchange="checkValue(this,'inventarioprocedimento_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
										<init:autocompleter methodAjax="findInventarioprocedimento.htm" minChars="2" idHidden="inventarioprocedimento_hidden" idInput="elencoEndoRegionaliPrima_id" inputTitleKey="label.ricerca_inventarioprocedimento"/>
										<spring-form:hidden id="inventarioprocedimento_hidden" path="elencoEndoRegionaliPrima"  />
						     		</td>
								</tr>
								<tr>
						  			<td colspan="2">
						     		<div id="functions">
										<ul>
											<li><a href="javascript:doSubmit('addElencoEndoRegionali.htm','',document.inviodati)"><fmt:message key="button.aggiungi" /></a></li>  
						        		</ul>
						     		</div>   
						  			</td>
								</tr>
							</table>
				        
				 	</td>
				 	</tr>
				 	--%>
				 	<thead>
					 <tr class="header">
				 		<td><fmt:message key="label.elenco_endo_locali"/></td>
				 	</tr>
					</thead>
					<tr>
                 	<!--  TABELLA SECONDARIA(REGIONALI) END -->
				 	<td  valign="top"> 
				  	<!--  TABELLA SECONDARIA(LOCALI)  -->
				  			<table width="100%">
				  				<thead>
				  	     			<tr class="header">
										<td width="5%"><fmt:message key="label.codice" /></td>
										<td width="50%"><fmt:message key="label.descrizione" /></td>
										<td width="40%"><fmt:message key="label.allegati" /></td>
										<td width="5%"><fmt:message key="label.azioni" /></td>
									</tr>
								</thead>	
				  				<tbody class="tbody">
				  				<c:if test="${fn:length(partelocaleschedaendotipo2.entity.elencoEndoPrevistiPrima.elencoEndoLocali.endoLocale)==0}">
		                 		<tr class="even">
		                 			<td colspan="3"><fmt:message key="label.informazione_non_presente" /></td>
		                 		</tr>
		            			</c:if>
				  				<c:forEach items="${partelocaleschedaendotipo2.entity.elencoEndoPrevistiPrima.elencoEndoLocali.endoLocale}" var="endolocale" varStatus="a">
								<tr >
									<td style="vertical-align: top;">
										<input value="${endolocale.codice}" size="6" readonly="readonly"/>
									</td>
									<td style="vertical-align: top;">
										<input value="${endolocale.nome}" size="100" readonly="readonly"/>
									</td>
									<td style="vertical-align: top;">
									<table>
									<tr>
										<td><b>Allegato</b></td>
										<td></td>
									</tr>
									<c:forEach items="${endolocale.elencoQuadriStandard5.quadro}"  var="q">
										<%--
										.elencoAllegatiRichiestiQuadro.allegatoRichiesto}" var="arqs">
									 	--%>
									<c:forEach items="${q.elencoAllegatiRichiestiQuadro.allegatoRichiesto}"  var="arqs" varStatus="allStat">
										<tr>
										<td>${arqs.spiegazioniAllegato }</td>
										<td>
											<a class="eliminaRiga"
											 href="javascript:doHref('deleteAllegatoElencoEndoLocaliPrima.htm?endoLocale=${endolocale.codice}&idx=${allStat.index}','')" 
											 title="<fmt:message key="label.delete" />">
												<label><fmt:message key="label.edit.record.image" /></label>
								    		</a> 
							    		</td>
							    		</tr>
							    		</c:forEach>											
									</c:forEach>
									</table>
									
									<div id="functions">
										<ul>
											<li>
																						
											Spiegazione Allegato: <input id="allegatoendoLocalePrima_id" name="allegatoendodescrizione_PRIMA_${endolocale.codice}" size="40"/>										
											<a href="javascript:doSubmit('addAllegatoElencoEndoLocaliPrima.htm?endoLocale=${endolocale.codice}','',document.inviodati);"><fmt:message key="button.aggiungi" /></a></li>  
					        			</ul>
					         		</div> 
									</td>
									
									<td style="vertical-align: top;">
										<a class="eliminaRiga" href="javascript:doHref('deleteElencoEndoLocali.htm?elemento=${endolocale.codice}','')" title="<fmt:message key="label.delete" /> ${a.index}">
										<label><fmt:message key="label.edit.record.image" /></label>
						    			</a> 
						   			</td>
								</tr>
								<tr>
									<td colspan="4" style="border-bottom: 1px  dotted;">&nbsp;
									</td>
								</tr>
								</c:forEach>
								</tbody>
						   </table>
							<br/>
							<table>
								<tr style="padding-top:7px; ">
									<td>
										<fmt:message key="label.descrizione" />
									</td>
				        			<td>      
										<spring-form:input id="endoLocalePrima_id" path="endoLocalePrima.nome" cssClass="searchbox" onchange="checkValue(this,'endoLocalePrima_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>										
										<init:autocompleter methodAjax="findInventarioprocedimento.htm" minChars="2" idHidden="endoLocalePrima_hidden" idInput="endoLocalePrima_id" inputTitleKey="label.ricerca_inventarioprocedimento"/>
										<spring-form:hidden id="endoLocalePrima_hidden" path="endoLocalePrima.codice"  />
					    			</td>
								</tr>
								<tr>
									<td colspan="2">
					        			<div id="functions">
										<ul>
											<li><a href="javascript:doSubmit('addElencoEndoLocali.htm','',document.inviodati);"><fmt:message key="button.aggiungi" /></a></li>  
					        			</ul>
					         			</div>   
					     			</td>
								</tr>
							</table>
				  		</td>
				  </tr>
			</table>
			</div>
		    </fieldset>
		    <!--  ELENCO ENDO PRIMA END  -->
		    
		    
		    <!--  ELENCO ENDO DOPO  START -->
		    <fieldset><legend><fmt:message key="label.elenco_endo_dopo"/></legend>
				<!-- TABELLA PRINCIPALE -->
				<div class="jmesa">
				<table  cellpadding="2" cellspacing="0" class="table"  width="100%">
					<%-- 
					COMMENTATO IN QUANDO NON DEVONO ESSERE PIù INVIATI AL MOMENTO
					<thead>
					 <tr class="header">
				 		<td><fmt:message key="label.elenco_endo_regionali"/></td>				 		
				 	</tr>
					</thead>	
				  	<tr>	
				  	<td valign="top" >
				  	<!--  TABELLA SECONDARIA (REGIONALI)  -->
				
				  	<table width="100%">
				  	    <thead>
				  	     	<tr class="header">
								<td><fmt:message key="label.descrizione" /></td>
								<td><fmt:message key="label.azioni" /></td>
							</tr>
						</thead>	
				  	   	<tbody class="tbody">
				  	   	<c:choose>
               				<c:when test="${fn:length(partelocaleschedaendotipo2.inventarioprocedimentisDopo)==0}">
                 			<tr class="even">
                 				<td colspan="2"><fmt:message key="label.informazione_non_presente" /></td>
                 			</tr>
              				</c:when>
              				<c:otherwise>
              					<%int j=1;%>
								<c:forEach items="${partelocaleschedaendotipo2.inventarioprocedimentisDopo}" var="endotipo1" varStatus="a">
								<tr class="<%=(j%2)==0?"odd":"even"%>">
			  	  					<td>											
										${endotipo1.inventarioprocedimenti.procedimento}
									</td>
									<td width="1%">
										<a class="eliminaRiga" href="javascript:doHref('deleteElencoEndoRegionaliDopo.htm?elemento=${endotipo1.inventarioprocedimenti.id.codice}','')" title="<fmt:message key="label.delete" /> ${a.index}">
										<label><fmt:message key="label.edit.record.image" /></label>
					    				</a> 
									</td>
								</tr>
								<%j++; %>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
					<br/>
					<table>
						<tr style="padding-top: 7px;">
							<td>
								<fmt:message key="label.descrizione" />
							</td>
						    <td>
						    	<input type="text" id="elencoEndoRegionaliDopo_id" name="_elencoEndoRegionaliDopo" class="searchbox" onchange="checkValue(this,'inventarioprocedimento_hidden_dopo')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
								<init:autocompleter methodAjax="findInventarioprocedimento.htm" minChars="2" idHidden="inventarioprocedimento_hidden_dopo" idInput="elencoEndoRegionaliDopo_id" inputTitleKey="label.ricerca_inventarioprocedimento"/>
								<spring-form:hidden id="inventarioprocedimento_hidden_dopo" path="elencoEndoRegionaliDopo"  />
						     </td>
						</tr>
						<tr>
						  	<td colspan="2">
						     	<div id="functions">
								<ul>
									<li><a href="javascript:doSubmit('addElencoEndoRegionaliDopo.htm','',document.inviodati)"><fmt:message key="button.aggiungi" /></a></li>  
						        </ul>
						     	</div>   
						 	</td>
						</tr>
				   	</table>
				 </td>
				 </tr>
				 --%>
				 <thead>
					 <tr class="header">				 		
				 		<td><fmt:message key="label.elenco_endo_locali"/></td>
				 	</tr>
				</thead>
				<tr>	
                 <!--  END TABELLA SECONDARIA(LOCALI)  -->
				 <td  valign="top"> 
				  	<!--  TABELLA SECONDARIA(LOCALI) -->
				  	<table width="100%">
				  		<thead>
				  	     	<tr class="header">
								<td width="5%"><fmt:message key="label.codice" /></td>
								<td width="50%"><fmt:message key="label.descrizione" /></td>
								<td width="40%"><fmt:message key="label.allegati" /></td>
								<td width="5%"><fmt:message key="label.azioni" /></td>
							</tr>
						</thead>	
				  		<tbody class="tbody">
				  	<c:if test="${fn:length(partelocaleschedaendotipo2.entity.elencoEndoPrevistiDopo.elencoEndoLocali.endoLocale)==0}">
		                 	<tr class="even">
		                 		<td colspan="3"><fmt:message key="label.informazione_non_presente" /></td>
		                 	</tr>
		           		 </c:if>
		            	<%int o=1;%>
				  		<c:forEach items="${partelocaleschedaendotipo2.entity.elencoEndoPrevistiDopo.elencoEndoLocali.endoLocale}" var="endolocaledopo" varStatus="a">
						<tr class="<%=(o%2)==0?"odd":"even"%>">
								<td  style="vertical-align: top;">
									<input value="${endolocaledopo.codice}" size="6" readonly="readonly"/>
								</td>
								<td  style="vertical-align: top;">
									<input value="${endolocaledopo.nome}" size="100" readonly="readonly"/>
								</td>
								<td style="vertical-align: top;">
									<table>
									<tr>
										<td><b>Allegato</b></td>
										<td></td>
									</tr>
									<c:forEach items="${endolocaledopo.elencoQuadriStandard5.quadro}"  var="q">										
									<c:forEach items="${q.elencoAllegatiRichiestiQuadro.allegatoRichiesto}"  var="arqs" varStatus="allStat">
										<tr>
										<td>${arqs.spiegazioniAllegato }</td>
										<td>
											<a class="eliminaRiga"
											 href="javascript:doHref('deleteAllegatoElencoEndoLocaliDopo.htm?endoLocale=${endolocaledopo.codice}&idx=${allStat.index}','')" 
											 title="<fmt:message key="label.delete" />">
												<label><fmt:message key="label.edit.record.image" /></label>
								    		</a> 
							    		</td>
							    		</tr>
							    		</c:forEach>											
									</c:forEach>
									</table>
									
									<div id="functions">
										<ul>
											<li>
																						
											Spiegazione Allegato: <input id="allegatoendoLocaleDopo_id" name="allegatoendodescrizione_DOPO_${endolocaledopo.codice}" size="40"/>										
											<a href="javascript:doSubmit('addAllegatoElencoEndoLocaliDopo.htm?endoLocale=${endolocaledopo.codice}','',document.inviodati);"><fmt:message key="button.aggiungi" /></a></li>  
					        			</ul>
					         		</div> 
									</td>
								<td>
									<a class="eliminaRiga" href="javascript:doHref('deleteElencoEndoLocaliDopo.htm?elemento=${endolocaledopo.codice}','')" title="<fmt:message key="label.delete" /> ${a.index}">
										<label><fmt:message key="label.edit.record.image" /></label>
						    		</a> 
						   		</td>
						</tr>
						
						<%o++; %>
						</c:forEach>
						</tbody>
					</table>
							
					<table>
					<tr style="padding-top:7px; ">
						<td>
							<fmt:message key="label.descrizione" />
						</td>
	        			<td>      
							<spring-form:input id="elencoEndoDopo_id" path="endoLocaleDopo.nome" cssClass="searchbox" onchange="checkValue(this,'endoLocaleDopo_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>										
							<init:autocompleter methodAjax="findInventarioprocedimento.htm" minChars="2" idHidden="endoLocaleDopo_hidden" idInput="elencoEndoDopo_id" inputTitleKey="label.ricerca_inventarioprocedimento"/>
							<spring-form:hidden id="endoLocaleDopo_hidden" path="endoLocaleDopo.codice"  />
		    			</td>
					</tr>
					<tr>
						<td colspan="2">
					        <div id="functions">
								<ul>
									<li><a href="javascript:doSubmit('addElencoEndoLocaliDopo.htm','',document.inviodati);"><fmt:message key="button.aggiungi" /></a></li>  
						        </ul>
					         </div>   
					     </td>
					</tr>
					</table>
				  </td>
				  <!--  END TABELLA SECONDARIA(LOCALI) -->
			</tr>
			</table>
			</div>
		    </fieldset>
		    
		    <!--  ELENCO ENDO DOPO END   -->
		  
		    <!--  ELENCO ENDO END -->
		    
		    <!--  ELENCO NORMATIVE LOCALI (TIPO 1) START -->
	
	    	
				    <fieldset><legend><fmt:message key="label.titolo_quadro_d1"/></legend>
					<div class="jmesa">
					<table  cellpadding="2" cellspacing="0" class="table"  width="100%">
						<thead>
						 	<tr class="header">
						 		<td><fmt:message key="label.adempimento"/></td>
						 		<td><fmt:message key="label.normative"/></td>
						 		<td><fmt:message key="label.normativa_comunale"/></td>
						 		<td><fmt:message key="label.url"/></td>				 		
						 	</tr>
						</thead>	
				  	   <tbody class="tbody">
				  	   <c:if test="${fn:length(partelocaleschedaendotipo2.regolamentoComunaleHelpers)==0}">
			                <tr class="even">
			                 	<td colspan="5"><fmt:message key="label.informazione_non_presente" /></td>
				            </tr>
				            </c:if>
				            <%int z=1;%>
						  	<c:forEach items="${partelocaleschedaendotipo2.regolamentoComunaleHelpers}" var="regolamentocomunale1" varStatus="a">
						    <tr class="<%=(z%2)==0?"odd":"even"%>">
								<td valign="top">
									<b>${regolamentocomunale1.descrizioneAdempimento}</b>
								</td>
								<td valign="top">
									<b><fmt:message key="label.normativa_nazionale"/>:</b> ${regolamentocomunale1.normativaRegionale.normaNazionale.value}
									<br />
									<b><fmt:message key="label.normativa_regionale"/>: </b>${regolamentocomunale1.normativaRegionale.normaRegionale.value}
								</td>	
								<td valign="top">
									<spring-form:input path="regolamentoComunaleHelpers[${a.index}].value" size="40"/>
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
	    
    <!-- DOCUMENTAZIONE LOCALE START -->
		<br />
	    <table width="100%">
	    	<tr class="titoloSezione">
	    		<td colspan="2"><fmt:message key="label.titolo_quadro_e"/></td>
	    	</tr>

	    	<!-- ELENCO NORMATIVE LOCALI (TIPO 2) START -->
	    	<tr>
	    		<td colspan="2">
				    <fieldset><legend><fmt:message key="label.titolo_quadro_e1"/></legend>
				    <div class="jmesa">
					<table  cellpadding="2" cellspacing="0" class="table"  width="100%">
						<thead>
						 	<tr class="header">
						 		<td><fmt:message key="label.adempimento"/></td>
						 		<td><fmt:message key="label.normative"/></td>
						 		<td><fmt:message key="label.normativa_comunale"/></td>
						 		<td><fmt:message key="label.url"/></td>				 		
						 	</tr>
						</thead>	
				  	   <tbody class="tbody">
				  	   <c:if test="${fn:length(partelocaleschedaendotipo2.regolamentoComunaleTipo2Helpers)==0}">
			                <tr class="even">
			                 	<td colspan="4"><fmt:message key="label.informazione_non_presente" /></td>
				            </tr>
				            </c:if>
				            <%int q=1;%>
						  	<c:forEach items="${partelocaleschedaendotipo2.regolamentoComunaleTipo2Helpers}" var="regolamentocomunale1" varStatus="a">
						    <tr class="<%=(q%2)==0?"odd":"even"%>">
								<td valign="top">
									<b>${regolamentocomunale1.descrizioneAdempimento}</b>
								</td>
								<td valign="top">
									<b><fmt:message key="label.normativa_nazionale"/>:</b> ${regolamentocomunale1.normativaRegionale.normaNazionale.value}
									<br />
									<b><fmt:message key="label.normativa_regionale"/>: </b>${regolamentocomunale1.normativaRegionale.normaRegionale.value}
								</td>	
								<td valign="top">
									<spring-form:input path="regolamentoComunaleTipo2Helpers[${a.index}].value" size="40"/>
								</td>								
								<td valign="top">
									<spring-form:input path="regolamentoComunaleTipo2Helpers[${a.index}].url" size="50"/>									
								</td>								
							</tr>
							<%q++; %>
							</c:forEach>
								</tbody>
							</table>							
					 	</div>
		 			</fieldset>
	    		</td>
	    	</tr>
	    	<!-- ELENCO NORMATIVE LOCALI (TIPO 2) END -->	 
	    	
			<tr class="titoloSezione">
	    		<td colspan="2"><fmt:message key="label.titolo_quadro_e2"/></td>
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
     	 <tr class="titoloSezione">
    			<td colspan="2"><fmt:message key="label.titolo_quadro_e3"/></td>
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
			    	<spring-form:textarea id="diritti_istruttoria_SUAP_id" path="entity.pagamentoLocale.dirittiIstruttoriaSUAP" rows="4" cols="70" />		     	
		        </td>
	      </tr>
	      <tr>
	        	<td>
					<fmt:message key="label.note" />
				</td>
			    <td>
			    	<spring-form:textarea id="note_pagamento_id" path="entity.pagamentoLocale.notePagamento" rows="4" cols="70" />			     	
		        </td>
	      </tr>
	      
	      

		 <!-- PAGAMETO LOCALE END -->
           
         <!-- ALTRE INFO START -->
	     	 <tr class="titoloSezione">
	    			<td colspan="2"><fmt:message key="label.titolo_quadro_f"/></td>
	    	 </tr>
		     <tr>
		       	<td>
					<fmt:message key="label.adempimenti_successivi_locali" />
				</td>
			    <td>
			    	<spring-form:textarea id="adempimenti_successivi_locali_id" path="entity.adempimentiSuccessiviLocali" rows="4" cols="70" />			    			     	
		        </td>
		     </tr>
	     	 <tr class="titoloSezione">
	    			<td colspan="2"><fmt:message key="label.titolo_quadro_n"/></td>
	    	 </tr>
		     <tr>
		       	<td>
					<fmt:message key="label.note" />
				</td>
			    <td>
			    	<spring-form:textarea id="note_locali_id" path="entity.noteLocali" rows="4" cols="70" />
		        </td>
		     </tr>
    	</table>
    	<!-- ALTRE INFO END -->
           	<script type='text/javascript'>
				$('descrizioneLocale_id').focus();
			</script>	
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