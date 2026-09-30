<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="alberoproc.label.associa_nuovo_ateco.title" />
</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="alberoproc.label.associa_nuovo_ateco.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<div id="subcontent">
	<spring-form:form commandName="alberoproc" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="alberoproc" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../alberoproc/list" />
	</jsp:include>	
	<div id="subcontent">	
		<div class="parametriDiv">
   			<div class="etichetta">
				<div><fmt:message key="label.intervento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${alberoproc.vwAlberoproc.scDescrizione}" /></div>
	 		</div>
		</div>
		<br class="break" />
	    <br class="break" />
	    <label for="solo_scadenze_macrocategoria_id">
	    		<fmt:message key="label.alberoprocateco.solo_macrocategorie" />:  
	    </label>       
	            <%
	            String scadImportantiChecked = "";	
	            if ("1".equals((String) request.getAttribute(WebConstants.CONF_UTENTE_ATECO_SOLO_MACROCATEGORIE))) {
	        		scadImportantiChecked = " checked "; 
				} 
	            %>
	            	<input type="checkbox" id="solo_scadenze_macrocategoria_id" <%= scadImportantiChecked%> onclick="salvaImpostazione()"/>		            	       			
	       		<script type="text/javascript">
	       		function salvaImpostazione()
	       		{
	       			var checked = jQuery('#solo_scadenze_macrocategoria_id').attr('checked');
	       				
	       			saveUserPreference('<%=WebConstants.CONF_UTENTE_ATECO_SOLO_MACROCATEGORIE%>',(checked==='checked') ? 1 : 0);
	       			setTimeout('document.location.reload()', 1000);
	       			
	       		}
	       		
	       		</script>
	    
	    <br class="clear"/>	
	    
		<div dojoType="dojo.data.ItemFileReadStore" jsId="atecoStore" url="${pageContext.request.contextPath}/json/getAteco.htm?idComuneAlberoproc=${alberoproc.id.idcomune }&codiceAlberoproc=${alberoproc.id.codice}&time=<%=System.currentTimeMillis() %>"></div>

		 <div dojoType="dijit.tree.ForestStoreModel" jsId="atecoModel" store="atecoStore"
        		query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.classificazione_ateco_root_label" />" 
        		childrenAttrs="children">
        </div>
		<div dojoType="dijit.Tree" id="tree2" model="atecoModel">
		<script type="dojo/method" event="onClick" args="item">
			
			if(item.id != '0'){
	   			var itemId = atecoStore.getValue(item, "id");
				if(confirm("Associare la voce ateco?")){				
					assegnaAteco(itemId, ${alberoproc.id.codice});
				}
	   		}

   		</script>   		
   		<script type="text/javascript">
   		mostraDettaglioAlberoprocateco();   		
   			function mostraDettaglioAlberoprocateco(codiceAlberoproc){
				new Ajax.Request('${pageContext.request.contextPath}/ajax/dettaglioAlberoprocateco.htm?codiceAlberoproc=${alberoproc.id.codice}', {
					method: 'post',	
					onSuccess: function(transport){						
					  $("lista_alberoprocateco").innerHTML = transport.responseText;
    				  $("lista_alberoprocateco").style.display='';     				  
     				},
     				onFailure: function(transport){ 
     				  $("lista_alberoprocateco").innerHTML= transport.responseText;
     				  $("lista_alberoprocateco").style.display='';     				      				  
     				 }						    		 
     			});
   			}   		
			function assegnaAteco(codiceAteco, codiceAlberoproc){			
				new Ajax.Request('${pageContext.request.contextPath}/alberoprocateco/ajaxAssegnaAteco.htm?codiceAteco='+codiceAteco+'&codiceAlberoproc='+codiceAlberoproc, {
					method: 'post',	
					onSuccess: function(transport){						
	 				  alert(transport.responseText);
     				  mostraDettaglioAlberoprocateco();
     				},
     				onFailure: function(transport){ 
  	 				  alert(transport.responseText);
     				 }						    		 
     			});
     		}
			function eliminaAteco(codiceAteco, codiceAlberoproc){		
				if(confirm('<fmt:message key="javascript.confirm.delete" />')){
					new Ajax.Request('${pageContext.request.contextPath}/alberoprocateco/ajaxEliminaAteco.htm?alberoproc.id.codice='+codiceAlberoproc+'&codiceateco='+codiceAteco, {
						method: 'post',	
						onSuccess: function(transport){						
		 				  alert(transport.responseText);
		 				  mostraDettaglioAlberoprocateco();
		 				},
		 				onFailure: function(transport){ 
			 				  alert(transport.responseText);
		 				 }						    		 
		 			});
				}
	 		}			
			function salvaPreferenza(nomeparametro, objchk){
				var valore = "0";	
				if(objchk.checked==true){
					valore="1";	
				}
				saveUserPreference(nomeparametro, valore);
			}			
			function togliSpinner(){			
				document.getElementById('alberoimg_id').style.display='none';			
			}			
			
					
		</script>		
	</div>
	<span id="lista_alberoprocateco" style="display: none;"></span>
	</div>
</spring-form:form>
</div>

<div id="functions">
<ul>
	<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>