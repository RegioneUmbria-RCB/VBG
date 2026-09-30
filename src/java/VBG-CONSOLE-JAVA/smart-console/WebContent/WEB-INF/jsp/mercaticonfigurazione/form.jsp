<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.configurazione_mercati.title" />
	</title>
</head>
<body>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../mercaticonfigurazione/view" />
</jsp:include>
<span class="titoloPagina">
	<fmt:message key="label.configurazione_mercati.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="mercatiConfigurazione" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatiConfigurazione" />
    </jsp:include>
    <fieldset><legend><fmt:message key="form.mercatiConfigurazione.parametriBase" /></legend>
    <br />
	<table>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.settori" />
			</td>
			<td>
				<spring-form:input id="settori_id" path="settori.settore" cssClass="searchbox" onchange="checkValue(this,'settori_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findSettori.htm" idHidden="settori_hidden" idInput="settori_id" inputTitleKey="label.ricerca_settore"/>
				<init:help idHelp="helpsettori" textKey="form.mercatiConfigurazione.settori.help"/>
				<spring-form:errors path="settori.settore" cssClass="error"/> 
				<spring-form:hidden id="settori_hidden" path="settori.id.codicesettore"  />
			</td>
		</tr>	
		</table>		
		<c:if test="${(dispatch == 'view') && (mercatiConfigurazione.settori.id.codicesettore != null) && (mercatiCfgAttivitaList != null)}">
		<br/>
				
						<div class="jmesa">
							<table class="table" width="30%">
								<thead>
									<tr class="header">
										<td colspan="3"><fmt:message key="form.mercatiConfigurazione.mercatiCfgAttivita.title" />
										<init:help idHelp="helpmercatiCfgAttivita" textKey="form.mercatiConfigurazione.mercatiCfgAttivita.title.help"/>
										</td>
									</tr>
									<tr class="header">
										<td><fmt:message key="form.attivita.istat" /></td>
										<td style="text-align: right;" ><fmt:message key="form.mercatiCfgAttivita.coefficiente" /></td>
										<td width="5%"><fmt:message key="label.edit.record" /></td>
									</tr>
								</thead>
						    	<tbody class="tbody">
									<%int i=0; %>
								    <c:forEach items="${mercatiCfgAttivitaList}" var="attivitaBean">
						                 <tr class="<%=(i%2)==0?"odd":"even"%>">
											<td>
						                    	${attivitaBean.attivita.istat}                                
											</td>                    
						                    <td style="text-align: right;">                    
							                	    ${attivitaBean.coefficiente}
							                </td>                   
						                    <td>
						                    	<a class="dettaglioColumn" href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercaticonfigurazione%2FviewMercatiCfgAttivita.htm?codice=${attivitaBean.id.fkCodiceattivita}%26codicesettore=${mercatiConfigurazione.settori.id.codicesettore}" 
												title="<fmt:message key="label.edit.record" /> ${attivitaBean.id.fkCodiceattivita}">
												<label><fmt:message key="label.edit.record.image" /></label></a>
						                    </td>
										</tr>
										<%i++; %>
							 		</c:forEach>
						 		</tbody>
					  		</table>
					  	</div>	
					
			<div id="functions">
				<ul>			
					<li><a href="javascript:doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo=../mercaticonfigurazione/createMercatiCfgAttivita.htm?codicesettore=${mercatiConfigurazione.settori.id.codicesettore}','',document.inviodati)">
							<fmt:message key="button.mercatiCfgAttivita.create" /></a>
					</li>		
				</ul>
			</div>
		<br/>
		</c:if>
	</fieldset>	
	<br />
	<fieldset><legend><fmt:message key="form.mercatiConfigurazione.parametriContabilita" /></legend>
	<br />
	<table>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.causaleCanone" />
			</td>
			<td>
				<spring-form:input id="canone_id" path="causaleCanone.descrizione" cssClass="searchbox" onchange="checkValue(this,'canone_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findRegistrazioniCausaliByPosteggio.htm" idHidden="canone_hidden" idInput="canone_id" inputTitleKey="label.ricerca_causale"/>
				<spring-form:errors path="causaleCanone" cssClass="error"/> 
				<spring-form:hidden id="canone_hidden" path="causaleCanone.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.causaleAumento" />
			</td>
			<td>
				<spring-form:input id="aumento_id" path="causaleAumento.descrizione" cssClass="searchbox" onchange="checkValue(this,'aumento_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findRegistrazioniCausaliByPosteggio.htm" idHidden="aumento_hidden" idInput="aumento_id" inputTitleKey="label.ricerca_causale"/>
				<spring-form:errors path="causaleAumento" cssClass="error"/> 
				<spring-form:hidden id="aumento_hidden" path="causaleAumento.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.causaleDiminuzione" />
			</td>
			<td>
				<spring-form:input id="diminuzione_id" path="causaleDiminuzione.descrizione" cssClass="searchbox" onchange="checkValue(this,'diminuzione_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findRegistrazioniCausaliByPosteggio.htm" idHidden="diminuzione_hidden" idInput="diminuzione_id" inputTitleKey="label.ricerca_causale"/>
				<spring-form:errors path="causaleDiminuzione" cssClass="error"/> 
				<spring-form:hidden id="diminuzione_hidden" path="causaleDiminuzione.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.causaleTransazione" />
			</td>
			<td>
				<spring-form:input id="transazione_id" path="causaleTransazione.descrizione" cssClass="searchbox" onchange="checkValue(this,'transazione_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findRegistrazioniCausaliByPosteggio.htm" idHidden="transazione_hidden" idInput="transazione_id" inputTitleKey="label.ricerca_causale"/>
				<spring-form:errors path="causaleTransazione" cssClass="error"/> 
				<spring-form:hidden id="transazione_hidden" path="causaleTransazione.id.codice"  />
			</td>
		</tr>		
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.contoDefault" />
			</td>
			<td>
				<spring-form:input id="contoDefault_id" path="contoDefault.descrizione" cssClass="searchbox" onchange="checkValue(this,'contoDefault_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findConti.htm" idHidden="contoDefault_hidden" idInput="contoDefault_id" inputTitleKey="label.ricerca_conto"/>
				<init:help idHelp="help_conto_default" textKey="form.mercatiConfigurazione.contoDefault.help"/>
				<spring-form:errors path="contoDefault" cssClass="error"/> 
				<spring-form:hidden id="contoDefault_hidden" path="contoDefault.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.contoInteressi" />
			</td>
			<td>
				<spring-form:input id="contoInteressi_id" path="contoInteressi.descrizione" cssClass="searchbox" onchange="checkValue(this,'contoInteressi_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findConti.htm" idHidden="contoInteressi_hidden" idInput="contoInteressi_id" inputTitleKey="label.ricerca_conto"/>
				<init:help idHelp="help_interessi" textKey="form.mercatiConfigurazione.contoInteressi.help"/>
				<spring-form:errors path="contoInteressi" cssClass="error"/> 
				<spring-form:hidden id="contoInteressi_hidden" path="contoInteressi.id.codice"  />
			</td>
		</tr>		
		</table>	
	<br/>    
    <div class="jmesa">    
	<table class="table" width="30%">
		<thead>
			<tr class="header">
				<td colspan="3"><fmt:message key="form.rangerateizzazioni.parametri.title" /></td>
			</tr>
			<tr class="header">
				<td style="text-align: right;" width="5%"><fmt:message key="label.from" /> (<fmt:message key="label.valuta" />)</td>
				<td style="text-align: right;" width="5%"><fmt:message key="label.to" /></td>
				<td width="90%"><fmt:message key="form.rangerateizzazioni.tiporateizzazione" /></td>
			</tr>
		</thead>
		<c:if test="${!empty listrateizzazioni}" >
    	<tbody class="tbody">
			<%int i=0; %>
		    <c:forEach items="${listrateizzazioni}" var="rat">
                 <tr class="<%=(i%2)==0?"odd":"even"%>">
					<td style="text-align: right;">
                    	${rat.rangeBasso}                                
					</td>                    
                    <td style="text-align: right;">                    
	                    <c:if test="${rat.rangeAlto!=null}">
	                	    ${rat.rangeAlto}
	                    </c:if>
	                    <c:if test="${rat.rangeAlto==null}">
	                    	<fmt:message key="form.rangerateizzazioni.oltre" />
	                    </c:if>
	                </td>                   
                    <td>
                    	${rat.tiporateizzazione.descrizione}
                    </td>
				</tr>
				<%i++; %>
	 		</c:forEach>
 		</tbody>
	 	</c:if>
  		</table>
  	</div>	  	
  	<div id="functions">
		<ul>
		<c:if test="${empty listrateizzazioni}" >
	          <li><a href="javascript:doHref('../rangerateizzazioni/create.htm','');"><fmt:message key="button.insertrateizzazione" /></a></li> 
	    </c:if>
  		<c:if test="${!empty listrateizzazioni}" >    
        	<li><a href="javascript:doHref('../rangerateizzazioni/view.htm?software=${mercatiConfigurazione.id.software}','')"><fmt:message key="button.change" /></a></li>
    	</c:if>
    	</ul>
	</div>
		<br/>
	</fieldset>
		<br />
		<fieldset><legend><fmt:message key="form.mercatiConfigurazione.tipoConcessione.title" />&nbsp;
		<init:help idHelp="helptipoConcessioneTitle" textKey="form.mercatiConfigurazione.tipoConcessione.title.help"/>
		</legend>
		<br />
		<%
		      String  swSettato1="display:none;";
		      String  swTT1="display:inline;";
		    %>
		    
			    <script type="text/javascript">		      
			       function tuttiSw(){
				       if($('id_flag1').checked){			
						    $('letteretipo_id1').style.display="inline";
						    $('letteretipo_id2').style.display="none";
						}else{
							$('letteretipo_id1').style.display="none";
							$('letteretipo_id2').style.display="inline";
						}
			       }
			    </script>
		<table>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.registroAutorizzazioni" />
			</td>
			<td>
				<spring-form:input id="registroAutorizzazioni_id" path="registroAutorizzazioni.trDescrizione" cssClass="searchbox" onchange="checkValue(this,'registroAutorizzazioni_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findTipologiaRegistri.htm" idHidden="registroAutorizzazioni_hidden"  idInput="registroAutorizzazioni_id" inputTitleKey="label.ricerca_tipo_registro"/>
				<init:help idHelp="helpregistroAutorizzazioni" textKey="form.mercatiConfigurazione.registroAutorizzazioni.help"/>
				<spring-form:errors path="registroAutorizzazioni" cssClass="error"/> 
				<spring-form:hidden id="registroAutorizzazioni_hidden" path="registroAutorizzazioni.id.codice"  />
			</td>
		</tr>		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.registroConcessioni" />
			</td>
			<td>
				<spring-form:input id="registroConcessioni_id" path="registroConcessioni.trDescrizione" cssClass="searchbox" onchange="checkValue(this,'registroConcessioni_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findTipologiaRegistri.htm" idHidden="registroConcessioni_hidden" idInput="registroConcessioni_id" inputTitleKey="label.ricerca_tipo_registro"/>
				<init:help idHelp="helpregistroConcessioni" textKey="form.mercatiConfigurazione.registroConcessioni.help"/>
				<spring-form:errors path="registroConcessioni" cssClass="error"/> 
				<spring-form:hidden id="registroConcessioni_hidden" path="registroConcessioni.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.tipoConcessione" />
			</td>
			<td>
				<spring-form:input id="tipoConcessione_id" path="tipoConcessione.descrizione" cssClass="searchbox" onchange="checkValue(this,'tipoConcessione_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findConcessioniTipi.htm" idHidden="tipoConcessione_hidden" idInput="tipoConcessione_id" inputTitleKey="label.ricerca_tipo_concessione"/>
				<spring-form:errors path="tipoConcessione" cssClass="error"/> 
				<spring-form:hidden id="tipoConcessione_hidden" path="tipoConcessione.tipoconcessione"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.durata" />
			</td>
			<td>
				<spring-form:input path="durata" size="4" maxlength="4"/>
				<spring-form:errors path="durata" cssClass="error"/>
				<spring-form:select path="durataTipo">
					<spring-form:option value="Y"><fmt:message key="form.mercatiConfigurazione.durataTipo.anni" /></spring-form:option>
					<spring-form:option value="M"><fmt:message key="form.mercatiConfigurazione.durataTipo.mesi" /></spring-form:option>
					<spring-form:option value="D"><fmt:message key="form.mercatiConfigurazione.durataTipo.giorni" /></spring-form:option>
				</spring-form:select>
				<spring-form:errors path="durataTipo" cssClass="error"/>  
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.causaleConcessione" />
			</td>
			<td>
				<spring-form:input id="causaleConcessione_id" path="causaleConcessione.descrizione" cssClass="searchbox" onchange="checkValue(this,'causaleConcessione_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findConcessioniCausali.htm?flagStorico=false" idHidden="causaleConcessione_hidden" idInput="causaleConcessione_id" inputTitleKey="label.ricerca_causale"/>
				<spring-form:errors path="causaleConcessione" cssClass="error"/> 
				<spring-form:hidden id="causaleConcessione_hidden" path="causaleConcessione.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConfigurazione.letteraTipo" />
			</td>		 		
			<td>		    
			    <div id="letteretipo_id1" style="<%=swSettato1%>"><spring-form:input id="lettere_tipo_id1" path="letteraTipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm?codicesoftware=TT'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id1" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
				<div id="letteretipo_id2" style="<%=swTT1%>"><spring-form:input id="lettere_tipo_id2" path="letteraTipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id2" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
				<spring-form:errors path="letteraTipo" cssClass="error"/> 				
				<spring-form:hidden id="lettere_tipo_hidden" path="letteraTipo.id.codice"  />
			    <input type="checkbox" id="id_flag1" onclick="tuttiSw();" title="<fmt:message key="help.letteretipo_archivi_base" />"/>		        
			    <init:help idHelp="helpletteraTipo" textKey="form.mercatiConfigurazione.letteraTipo.help"/>
			</td>		
		</tr>
	</table>
	<div class="titoloSezione"><fmt:message key="label.campi_dinamici_autorizzazione" />
	<init:help idHelp="help1" textKey="help.campi_dinamici_autorizzazione"/>
	</div>
	<table>
		<tr>
			<td><fmt:message key="form.mercatiConfigurazione.modello" /></td>
			<td><spring-form:input id="dyn2Modellit_id" path="dyn2Modellit.descrizione" size="55" cssClass="searchbox" onchange="checkValue(this,'dyn2Modellit_hidden')" onkeydown="javascript:return searchAll(this,event)" />
				<init:autocompleter methodAjax="findDyn2Modelli.htm" idHidden="dyn2Modellit_hidden" idInput="dyn2Modellit_id" inputTitleKey="label.ricerca_modello"/>
				<spring-form:errors path="dyn2Modellit" cssClass="error"/> 
				<spring-form:hidden id="dyn2Modellit_hidden" path="dyn2Modellit.id.codice"/>
    		</td>
    	</tr>
		<tr>
			<td>
				<fmt:message key="label.campo_dinamico_numero_autorizzazione" />
			</td>
			<td>
				<script type='text/javascript'>
					function callbackDyn2Mod(inputField,queryString){
						return queryString + "&idModello="+$('dyn2Modellit_hidden').value;
					}				
				</script>
				<spring-form:input id="dyn2CampiByFkMerconfDyn2campiNa_id" path="dyn2CampiByFkMerconfDyn2campiNa.nomecampo" cssClass="searchbox" onchange="checkValue(this,'dyn2CampiByFkMerconfDyn2campiNa_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findDyn2Campi.htm" idHidden="dyn2CampiByFkMerconfDyn2campiNa_hidden" idInput="dyn2CampiByFkMerconfDyn2campiNa_id" inputTitleKey="label.ricerca_campo_dinamico" callBack="callbackDyn2Mod" />
				<spring-form:errors path="dyn2CampiByFkMerconfDyn2campiNa" cssClass="error"/> 
				<spring-form:hidden id="dyn2CampiByFkMerconfDyn2campiNa_hidden" path="dyn2CampiByFkMerconfDyn2campiNa.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.campo_dinamico_data_autorizzazione" />
			</td>
			<td>
				<spring-form:input id="dyn2CampiByFkMerconfDyn2campiDa_id" path="dyn2CampiByFkMerconfDyn2campiDa.nomecampo" cssClass="searchbox" onchange="checkValue(this,'dyn2CampiByFkMerconfDyn2campiDa_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findDyn2Campi.htm" idHidden="dyn2CampiByFkMerconfDyn2campiDa_hidden" idInput="dyn2CampiByFkMerconfDyn2campiDa_id" inputTitleKey="label.ricerca_campo_dinamico" callBack="callbackDyn2Mod" />
				<spring-form:errors path="dyn2CampiByFkMerconfDyn2campiDa" cssClass="error"/> 
				<spring-form:hidden id="dyn2CampiByFkMerconfDyn2campiDa_hidden" path="dyn2CampiByFkMerconfDyn2campiDa.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.campo_dinamico_comune_autorizzazione" />
			</td>
			<td>
				<spring-form:input id="dyn2CampiByFkMerconfDyn2campiCa_id" path="dyn2CampiByFkMerconfDyn2campiCa.nomecampo" cssClass="searchbox" onchange="checkValue(this,'dyn2CampiByFkMerconfDyn2campiCa_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findDyn2Campi.htm" idHidden="dyn2CampiByFkMerconfDyn2campiCa_hidden" idInput="dyn2CampiByFkMerconfDyn2campiCa_id" inputTitleKey="label.ricerca_campo_dinamico" callBack="callbackDyn2Mod" />
				<spring-form:errors path="dyn2CampiByFkMerconfDyn2campiCa" cssClass="error"/> 
				<spring-form:hidden id="dyn2CampiByFkMerconfDyn2campiCa_hidden" path="dyn2CampiByFkMerconfDyn2campiCa.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.campo_dinamico_codice_registro_autorizzazione" />
			</td>
			<td>
				<spring-form:input id="dyn2CampiByFkMerconfDyn2campiCodregaut_id" path="dyn2CampiByFkMerconfDyn2campiCodregaut.nomecampo" cssClass="searchbox" onchange="checkValue(this,'dyn2CampiByFkMerconfDyn2campiCodregaut_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findDyn2Campi.htm" idHidden="dyn2CampiByFkMerconfDyn2campiCodregaut_hidden" idInput="dyn2CampiByFkMerconfDyn2campiCodregaut_id" inputTitleKey="label.ricerca_campo_dinamico" callBack="callbackDyn2Mod" />
				<spring-form:errors path="dyn2CampiByFkMerconfDyn2campiCodregaut" cssClass="error"/> 
				<spring-form:hidden id="dyn2CampiByFkMerconfDyn2campiCodregaut_hidden" path="dyn2CampiByFkMerconfDyn2campiCodregaut.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.campo_dinamico_cat_merc" />
			</td>
			<td>
				<spring-form:input id="dyn2CampiByFkMerconfDyn2campiCm_id" path="dyn2CampiByFkMerconfDyn2campiCm.nomecampo" cssClass="searchbox" onchange="checkValue(this,'dyn2CampiByFkMerconfDyn2campiCm_hidden')" onkeydown="javascript:return searchAll(this,event)" size="55"/>
				<init:autocompleter methodAjax="findDyn2Campi.htm" idHidden="dyn2CampiByFkMerconfDyn2campiCm_hidden" idInput="dyn2CampiByFkMerconfDyn2campiCm_id" inputTitleKey="label.ricerca_campo_dinamico" callBack="callbackDyn2Mod" />
				<spring-form:errors path="dyn2CampiByFkMerconfDyn2campiCm" cssClass="error"/> 
				<spring-form:hidden id="dyn2CampiByFkMerconfDyn2campiCm_hidden" path="dyn2CampiByFkMerconfDyn2campiCm.id.codice"  />
				<fmt:message key="label.campo_dinamico_cat_merc_uso" />
				<spring-form:select path="catMercUso">
					<spring-form:option value="">...</spring-form:option>
					<spring-form:option value="<%=WebConstants.MERCATI_CAT_MERC_USO_FIERE %>"><fmt:message key="label.fiere" /></spring-form:option>
					<spring-form:option value="<%=WebConstants.MERCATI_CAT_MERC_USO_MERCATI %>"><fmt:message key="label.mercati" /></spring-form:option>
					<spring-form:option value="<%=WebConstants.MERCATI_CAT_MERC_USO_ENTRAMBI %>"><fmt:message key="label.fiere_e_mercati" /></spring-form:option>
				</spring-form:select>
			</td>
		</tr>
  	</table>
	<br/>
	</fieldset>
	<script type='text/javascript'>
		$('settori_id').focus();
	</script>		
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${(dispatch == 'create')}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${(dispatch == 'view') }">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)">
			<fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
