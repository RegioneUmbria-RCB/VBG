<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="alberoproc.label.alberoprocmetadati" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="alberoproc.label.alberoprocmetadati" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
       		<div class="etichetta"> 
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div>
	        	<br />
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionebreve}
	        	</div>
	        </div>
    </div>
    <div class="clear"></div>
		<spring-form:form commandName="metadatoAlberoproc" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocMetadati" />
		    </jsp:include>
		    <spring-form:hidden path="codiceIntervento" />
		    
		    <div class="vbg-form">
		    	<fieldset>		 
		    		<legend><fmt:message key="alberoproc.label.alberoprocmetadati" /></legend>   
		    		<div class="form-group">
						<label class="tabella" for="autocompleter_id">
							<fmt:message key="label.metadato" />
						</label> 
						
						<spring:bind path="chiave">
	                
			                <input type="text"
			                    list="metadati" id="autocompleter_id" size="60"
			                    name="${status.expression}" value="${status.value}" 
			                    />
			                    
		               </spring:bind>  
		                  
	                <datalist id="metadati"> 
	                    <c:forEach
	                        items="${listaMetadatiConfigurabili}" var="metadato">
	                        <option>${metadato}</option>
	                    </c:forEach> 
	                </datalist>	
	                
					</div>		
					
					<div class="form-group">
						<label>
							<fmt:message key="label.valore" />
						</label> 
						
	                	<spring-form:input path="valore" size="60" />
			                
					</div>	
							
				</fieldset>	
				</div>
				
				<%-- 
			<table>
				<tr>
					<td>
						<label class="tabella" for="autocompleter_id">
							<fmt:message key="label.metadato" />
						</label> 
					</td>
					<td>
	                
						<spring:bind path="chiave">
	                
			                <input
			                    list="metadati" id="autocompleter_id" size="80"
			                    name="${status.expression}" value="${status.value}" 
			                    />
			                    
		               </spring:bind>  
		                  
	                <datalist id="metadati"> 
	                    <c:forEach
	                        items="${listaMetadatiConfigurabili}" var="metadato">
	                        <option>${metadato}</option>
	                    </c:forEach> 
	                </datalist>
					
					
						
						
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.valore" />
					</td>
					<td>
						<spring-form:input path="valore" size="60" />
					</td>
				</tr>
			</table>
			 --%>
		</spring-form:form>
		
		
		<script type="text/javascript">
		 vbg.ready(() => {
             const inputAutoCompleter = document.getElementById('autocompleter_id');
             
             inputAutoCompleter.addEventListener('input', (e) => {

                 // Chrome genera un event se l'elemento è stato selezionato dalla lista dei suggeriti e un InputEvent con inputType == "insertText" nel caso in cui si sia scritto un testo
                 // Firfox genera sempre un InputEvent ma con inputType == "insertText" nel caso in cui si sia inserito un testo da tastiera
                 // e inputType == "insertReplacementText" nel caso in cui l'elemento sia stato selezionato dalla lista
                 // In entrambi i casi posso verificare se inputType == "insertText", nel caso la condizione sia vera allora il testo è stato
                 // immesso da tastiera
                 var isInputEvent = e.inputType == "insertText";  // (Object.prototype.toString.call(e).indexOf("InputEvent") > -1);

                 if (!isInputEvent) {
                     // caricaElementiTabella();
                 }
             });
             
		   });
		
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertMetadato.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${alberoproc.id.codice}#albmetadati_anchor','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>