<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipisoggetto.id.codice==null}">
			<fmt:message key="tipisoggetto.label.nuovo_tipisoggetto.title" />
		</c:if> 
		<c:if test="${tipisoggetto.id.codice!=null}">
			<fmt:message key="tipisoggetto.label.dettaglio_tipisoggetto.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipisoggetto.id.codice==null}">
			<fmt:message key="tipisoggetto.label.nuovo_tipisoggetto.title" />
		</c:if> 
		<c:if test="${tipisoggetto.id.codice!=null}">
			<fmt:message key="tipisoggetto.label.dettaglio_tipisoggetto.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include> 
	<div id="subcontent">
		<spring-form:form commandName="tipisoggetto" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipisoggetto" />
		    </jsp:include>
            
            <div class="vbg-form">
                <div class="form-group">
                    <label><fmt:message key="label.descrizione" /></label>
                    <spring-form:input id="tiposoggetto_id" path="tiposoggetto" size="70" />                        
                    <spring-form:errors path="tiposoggetto" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>&nbsp;</label>
                    <spring-form:checkbox id="flagqualita_id" path="flagqualita" value="1" />
                    <fmt:message key="tipisoggetto.label.flagqualita.help" />   
                </div>
                
                <div class="form-group">
                    <label>&nbsp;</label>
                    <spring-form:checkbox id="richiedianagrafecoll_id" path="richiedianagrafecoll" value="1" onclick="visualizzaFlgLegalerap();"/>
                    <fmt:message key="tipisoggetto.label.richiedianagrafecoll" />   
                </div>
                
                <div class="form-group" id="flgLegalerap_tr_id">
                    <label>&nbsp;</label>
                    
                    <spring-form:checkbox id="flgLegalerap_id" path="flgLegalerap" value="1" />
                    <fmt:message key="tipisoggetto.label.flgLegalerap" />   
                </div>
                
                <div class="form-group" id="flagMostraDettIstanza_tr_id" >
                    <label>&nbsp;</label>                   
                    <spring-form:checkbox id="flagMostraDettIstanza_id" path="flagMostraDettIstanza" value="1" />
                    <fmt:message key="tipisoggetto.label.flagMostraDettIstanza" />                                              
                </div>                
                
                <fieldset>
                    <legend><fmt:message key="label.dati_registro_imprese.legend"/></legend>
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.dati_registro_imprese.carica" />
                        </label>
                        <jsp:include page="../includes/autocompletergenerico.jsp">
                            <jsp:param name="idElemento" value="riCariche_id" />        
                            <jsp:param name="propertyPath" value="riCariche" />                 
                            <jsp:param name="pathPropertyDescription" value="riCariche.descrizione" />
                            <jsp:param name="pathPropertyCode" value="riCariche.codice" />
                            <jsp:param name="autocompleterAjax" value="findRiCariche.htm" />
                            <jsp:param name="titleKey" value="label.ricerca_dati_registro_imprese.carica" />
                        </jsp:include>
                    </div>
                </fieldset>
                
                <fieldset>
                    <legend><fmt:message key="tipisoggetto.label.dati_frontoffice.legend"/> </legend>
                    
                    <div class="form-group">
                        <label><fmt:message key="tipisoggetto.label.utilizzo" /></label>
                        
                        <spring-form:select id="utilizzo_id" path="utilizzo">
                            <spring-form:option value=""><fmt:message key="tipisoggetto.label.utilizzo.item_backoffice_frontoffice" /></spring-form:option>
                            <spring-form:option value="B"><fmt:message key="tipisoggetto.label.utilizzo.item_backoffice" /></spring-form:option>
                            <spring-form:option value="F"><fmt:message key="tipisoggetto.label.utilizzo.item_frontoffice" /></spring-form:option>
                        </spring-form:select>   
                        
                        <div class="input-help">
                            <fmt:message key="tipisoggetto.label.utilizzo.help" />
                        </div>
                        
                    </div>
                    
                     <div class="form-group">
	                    <label><fmt:message key="label.descrizione_estesa" /></label>                   
	                    <spring-form:input id="descrizioneEstesa_id" path="descrizioneEstesa" size="100" />
	                    <spring-form:errors path="descrizioneEstesa" cssClass="error"/>                                            
	                </div>     
                    <div class="form-group">
                        <label>
                            <fmt:message key="tipisoggetto.label.tipoanagrafe" />
                        </label>
                        <spring-form:select id="tipoanagrafe_id" path="tipoanagrafe">
                            <spring-form:option value=""></spring-form:option>
                            <spring-form:option value="F"><fmt:message key="tipisoggetto.label.tipoanagrafe.item_fisica" /></spring-form:option>
                            <spring-form:option value="G"><fmt:message key="tipisoggetto.label.tipoanagrafe.item_giuridica" /></spring-form:option>
                        </spring-form:select>
                                                                       
                        <div class="input-help">                                        
                            <fmt:message key="tipisoggetto.label.tipoanagrafe.help" />
                        </div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="tipisoggetto.label.tipodato" />
                        </label>

                        <spring-form:select id="tipodato_id" path="tipodato">
                            <spring-form:option value=""></spring-form:option>
                            <spring-form:option value="A"><fmt:message key="tipisoggetto.label.tipodato.item_azienda" /></spring-form:option>
                            <spring-form:option value="R"><fmt:message key="tipisoggetto.label.tipodato.item_richiedente" /></spring-form:option>
                            <spring-form:option value="T"><fmt:message key="tipisoggetto.label.tipodato.item_tecnico" /></spring-form:option>
                        </spring-form:select>                                               

                        <div class="input-help">                                         
                            <fmt:message key="tipisoggetto.label.tipodato.help" />
                        </div>
                    </div>   
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="tipisoggetto.label.foObbligatorio" />
                        </label>

                        <spring-form:checkbox id="foObbligatorio_id" path="foObbligatorio" value="1" />

                        <div class="input-help">                                        
                            <label for="foObbligatorio_id"><fmt:message key="tipisoggetto.label.foObbligatorio.help" /></label>                        
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="tipisoggetto.label.flgDatialbo" />
                        </label>

                        <spring-form:checkbox id="flgDatialbo_id" path="flgDatialbo" value="1" />

                        <div class="input-help">                                        
                            <label for="flgDatialbo_id"><fmt:message key="tipisoggetto.label.flgDatialbo.help" /></label>                       
                        </div>
                    </div>                
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="tipisoggetto.label.flgSpecificadescrizione" />
                        </label>

                        <spring-form:checkbox id="flgSpecificadescrizione_id" path="flgSpecificadescrizione" value="1" />
                        
                        <div class="input-help">                                         
                            <label for="flgSpecificadescrizione_id"><fmt:message key="tipisoggetto.label.flgSpecificadescrizione.help" /></label>                       
                        </div>
                    </div> 
                        
                    <div class="form-group">
                        <label>
                            <fmt:message key="tipisoggetto.label.flagFoModPratica" />
                        </label>

                        <spring-form:checkbox id="flagFoModPratica_id" path="flagFoModPratica" value="1" />
                        
                        <div class="input-help">                                         
                            <label for="flagFoModPratica_id"><fmt:message key="tipisoggetto.label.flagFoModPratica.help" /></label>                      
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica" />
                        </label>

                        <spring-form:hidden id="flagLivelliVisuraPratica" path="flagLivelliVisuraPratica" />
                            
                        <select id="livelliVisuraSelect" multiple="multiple" size="7">
                            <option value="1"><fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica.datiGenerali" /></option><!-- Dati generali (incluse localizzazioni) -->
                            <option value="2"><fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica.schede" /></option><!--Schede-->
                            <option value="4"><fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica.documenti" /></option> <!-- Documenti --->
                            <option value="8"><fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica.endoprocedimenti" /></option> <!-- Endoprocedimenti -->
                            <option value="16"><fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica.oneri" /></option> <!-- Oneri -->
                            <option value="32"><fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica.movimenti" /></option> <!-- Movimenti -->
                            <option value="64"><fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica.autorizzazioni" /></option> <!-- Autorizzazioni -->
                        </select>
                        
                        <div class="input-help">    
                            <fmt:message key="tipisoggetto.label.flagLivelliVisuraPratica.help" />  
                        </div>
                    </div>
                        
                    
                    <script type="text/javascript">
                    vbg.ready(() => {
                        const flagLivelliVisuraPratica = document.querySelector('#flagLivelliVisuraPratica');
                        const currVal = +flagLivelliVisuraPratica.value;
                        const options = document.querySelectorAll('#livelliVisuraSelect>option')
                        
                        options.forEach((option) => {
                            const optionVal = +option.value
                            option.selected = (currVal & optionVal) === optionVal;
                        });
                        
                        document.querySelector('#livelliVisuraSelect').addEventListener('click', () => {
                            
                            let newVal = 0;
                            
                            options.forEach((option) => {
                                const optionVal = +option.value
                                newVal += (option.selected ? optionVal : 0);
                            });              
                            
                            flagLivelliVisuraPratica.value = newVal;
                        })
                        
                    });
                    
                    </script>
                    
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.ordine" />
                        </label>
                        
                        <spring-form:input id="ordine_id" path="ordine" size="6" />                     
                        <spring-form:errors path="ordine" cssClass="error"/>
                    </div>
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="tipisoggetto.label.flagRiceveNotifiche" />
                        </label>

                        <spring-form:checkbox id="flagRiceveNotifiche_id" path="flagRiceveNotifiche" value="1" />
                        
                        <div class="input-help">                                         
                            <label for="flagRiceveNotifiche_id"><fmt:message key="tipisoggetto.label.flagRiceveNotifiche.help" /></label>                      
                        </div>
                    </div>
                    
                </fieldset>
                
                <fieldset>
                    <legend>
                        <fmt:message key="label.anagrafe_tributaria"/>
                    </legend>

                    <div class="form-group">
                        <label>
                            <fmt:message key="label.qualifica_soggetto"/>
                        </label>
                        
                        <spring-form:input id="atribQualificasoggetto_id" path="atribQualificasoggetto" size="4" />                     
                        <spring-form:errors path="atribQualificasoggetto" cssClass="error"/>
                        <init:help idHelp="atribQualificasoggetto_id_help" textKey="help.tiposoggetto.attributo_qualifica_soggetto"/>
                        
                    </div>

                </fieldset>
                
                <c:if test="${tipisoggetto.id.codice!=null}">
                    <c:if test="${mapping_soggetti eq true }">
                        <fieldset>
                            <legend>
                                <fmt:message key="tipisoggetto.label.mapping" />
                            </legend>
                            
                            <style>
                                .lista-mappature-people {
                                    display:inline-block;
                                    min-width: 500px;
                                }
                            
                                .lista-mappature-people>ul {
                                    margin: 0;
                                    padding: 0;
                                    list-style-type: none;
                                }
                                
                                .lista-mappature-people>ul>li {
                                    border: 1px solid var(--form-element-border-color);
                                    border-bottom: 0;
                                    padding: var(--default-padding);
                                }
                                
                                .lista-mappature-people>ul>li:last-child {
                                    border-bottom: 1px solid var(--form-element-border-color);
                                    margin-bottom: var(--half-padding);
                                }
                                
                                .lista-mappature-people .elimina-mappatura {
                                    display: inline-block;
                                    float: right;
                                    white-space: nowrap;
                                }
                                
                                .lista-mappature-people .elimina-mappatura>a {

                                    color: var(--accent-color);
                                }
                                
                                .lista-mappature-people .aggiungi-mappatura {
                                    display: flex;
                                    align-items: baseline;
                                }
                                
                                .lista-mappature-people .aggiungi-mappatura > input {
                                    flex: 1;
                                }
                                
                                .lista-mappature-people .aggiungi-mappatura > a {
                                    padding-left: var(--half-padding);
                                }
                            </style>

                            <div class="lista-mappature-people">
                                <ul>
                                    <c:forEach items="${tipisoggetto.tipisoggettopeoples}" var="tp">
                                        <li>
                                            ${tp.id.tiporapprpeople }
                                            
                                            
                                            <div class="elimina-mappatura">
                                                <a href="javascript:doSubmit('updateMapping.htm?op=del&tipo=${tp.id.tiporapprpeople }','<fmt:message key="javascript.confirm.delete" />',document.inviodati)" title="<fmt:message key="label.elimina" />">
                                                    <i class="fa fa-trash"></i>
                                                    <fmt:message key="label.elimina" />
                                                </a>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                                
                                <div class="aggiungi-mappatura form-group">
                                    <input type="text" name="tipo" placeholder="Nome nuova mappatura"/>
                                    <a href="javascript:doSubmit('updateMapping.htm?op=add','',document.inviodati)" title="<fmt:message key="label.aggiungi" />">
                                        
                                        <i class="fa fa-plus"></i>
                                        <fmt:message key="label.aggiungi" />
                                    </a>
                                </div>
                            </div>
                        
                        </fieldset>

                    </c:if>
                </c:if>
                
                <c:if test="${not empty tipisoggetto.tipimovTipiSoggetto}">
                    <script type="text/javascript">
                    vbg.ready(() => {
                    	const linkMovimenti = document.querySelectorAll('.apri-movimento').forEach((el) => {
                    		
                    		el.addEventListener('click', (e) => {
                    			e.preventDefault();
                    			const url = `../history/set.htm?ReturnTo=\${document.location}&GoTo=../tipimovimento/view.htm?codice=\${ el.dataset.tipoMovimento }`;
                    			
                    			console.log(url);
                    			
                    			doHref(url,'');       			
                    		});                  		
                    	});                    	
                    });
                    </script>
                    <fieldset>
                        <legend>Movimenti che il soggetto può effettuare</legend>
                        
                        <table class="vbg-table">
                            <thead>
                                <tr>
                                    <th><fmt:message key="tipimovimento.label.codice" /></th>
                                    <th><fmt:message key="tipimovimento.label.movimento"/></th>
                                    <th></th>
                                </tr>
                            </thead>
                        
                            <tbody>
                                <c:forEach items="${tipisoggetto.tipimovTipiSoggetto}" var="ts">
       
                                   <tr>
                                        <td>${ ts.tipimovimento.id.tipomovimento }</td>
                                        <td>${ ts.tipimovimento.movimento }</td>
                                        <td>
                                            <a href="#" class="btn btn-secondary apri-movimento" data-tipo-movimento="${ts.tipimovimento.id.tipomovimento}">
                                                <fmt:message key="label.visualizza"/>                                                
                                                <i class="fa fa-arrow-right"></i>
                                            </a>
                                        
                                        </td>
                                    </tr>                         
                            
                                </c:forEach>
                            </tbody>
                        </table>
                    </fieldset>
                
                </c:if>
                
                
                
                
            </div>
            
				
			<script type="text/javascript">    
			   $('tiposoggetto_id').focus();				
		       visualizzaFlgLegalerap();
		       function visualizzaFlgLegalerap(){
			       if($('richiedianagrafecoll_id')!=null && $('richiedianagrafecoll_id').checked){ 
			    	    showDiv('flgLegalerap_tr_id');					    					    
					}else{
						hideDiv('flgLegalerap_tr_id');												
					}
		       }
		    </script>		
		</spring-form:form>
	</div>
    
        <div>
            <c:if test="${tipisoggetto.id.codice==null}">
                <a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
            </c:if>
            <c:if test="${tipisoggetto.id.codice!=null}">
                <a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
                <a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
            </c:if>
            <a class="btn btn-primary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
        
        </div>
    
    </body>
</html>