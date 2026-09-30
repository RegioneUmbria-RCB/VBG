<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.MercatiD"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="mercatid.label.lista_posteggi.title" /></title>
</head>
<body>


	<span class="titoloPagina"><fmt:message key="mercatid.label.lista_posteggi.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatid/listconfigurazione" />
	</jsp:include>
	<div id="subcontent">
	<%-- 
		<jsp:include page="../includes/linkmercato.jsp">
			<jsp:param name="codiceMercato" value="${mercati.id.codice}" />
			<jsp:param name="descrizioneMercato" value="${mercati.descrizione}" />											
		</jsp:include>
	--%>
	
	
	
	<div class="clear"></div>	
	<spring-form:form commandName="mercatid" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="mercatid" />
	</jsp:include>

   <div class="titoloSezione">
   		<fmt:message key="mercatid.label.elenco_posteggi" />
		<input id="id_check_posteggi" type="checkbox" name="" onclick="selezionaAndDeselezionaTutti(${sizeListaPosteggi});isposteggiselezionati(${sizeListaPosteggi})" title="<fmt:message key="label.seleziona_tutto" />" ></input>
   </div>
	

	<%-- TABELLA A POSTEGGI AFFIANCATI --%>
	<%-- Tabella principale contenete ogni singolo posteggio, è fatta in modo che ogni riga contenga 4 colonne (4 posteggi) --%>	

	<table border="1"  width="100%">
		<%
		    int i = 0;
		%>
		<c:forEach items="${listMercatiD}" var="posteggi" varStatus="varIndex">
		   <%-- 
		    <c:if test="${posteggi.disabilitato eq true}">
				<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio_disable"></c:set>
			</c:if>
			<c:if test="${posteggi.disabilitato eq false || posteggi.disabilitato == null}">
				<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio"></c:set>
			</c:if>
			--%>
			<c:if test="<%=(i%3)==0%>">
			<tr>
			</c:if>
			<td  width="25%" valign="top" >
            
            <%-- Tabella secondaria (tabella che rappresenta ogni singolo posteggio) --%>
            <%-- START TABELLA SECONDARIA --%>
			<table width="100%" cellpadding="0">
				<%-- PRIMA RIGA START --%>
				<%-- 
				<tr>
				<td class="codice_posteggio" style="padding: 0px;" valign="top"  ><input id="posteggi_checkbox_id${varIndex.index}" type="checkbox"
				    value="${posteggi.id.codice}" name="codiceposteggi" onclick="isposteggiselezionati(${sizeListaPosteggi});"/> 
				    <a href="javascript:void 0" onclick="doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid%2Fview.htm%3fcodice%3d${posteggi.id.codice}','')" title="<fmt:message key="label.dettaglio_posteggio" />">
				    <label class="${style_etichetta_posteggio}">${posteggi.codiceposteggio}</label> 
				    </a> 
                </td>
                <tr>
                	<td>
                	
                		Lunghezza
                		<input type="text" size="3" value="${posteggi.lunghezza}" ></input>
                		Larghezza
                		<input type="text" size="3" value="${posteggi.larghezza}"></input>
                		Superficie
                		<input type="text" size="3" value="${posteggi.superficie}"></input>
                	</td>
                </tr>
                --%>
                <tr>
                	<td>
                	<div id="dettaglioPosteggio_${posteggi}">&nbsp;<img src='${pageContext.request.contextPath}/images/spinner.gif' /></div>	
	                	
	                	<script type="text/javascript">
	                	
	                	jQuery(document).ready(function(){
							visualizzaDettaglioInfo(${posteggi},${codicemercato},${varIndex.index}); 
						});
	                	
	                	</script>
                	</td>
                </tr>
			 </table>
            <%-- END TABELLA SECONDARIA --%>
			<%
			    i++;
			%>
			</td>
			<c:if test="<%=(i%3)==0%>">
			</tr>
			<%
			    i = 0;
			%>
			</c:if>
		</c:forEach>
	</table>
	
    </spring-form:form>
    
    
    </div>

		<script type="text/javascript">
				var codP=""    	
				<%
				String displayButtonModifica = "display:none;";
				String displayButtonLivelliServizio = "display:none;";
				//String displayButtonElimina= "display:none;";
				//String displayButtonAddMerceologie="display:none;";
				//String displayButtonCreaComunicazioni= "display:none;";
				%>
			
				
				function isposteggiselezionati(size){
					var count=0
					for(i=0;i<size;i++){
						if($('posteggi_checkbox_id'+i).checked)
						{
							if(codP.indexOf($('posteggi_checkbox_id'+i).value)<0){
							 codP=codP+$('posteggi_checkbox_id'+i).value+",";
						    }
							count++;
						}else{
							codP=codP.replace($('posteggi_checkbox_id'+i).value+",","");
						}
					}
					if(count>0)
					{
						
						$('modifica_button').appear();
					    //$('elimina_button').appear();
					   
					    $('livello_servizio_button').appear();
					}else
					{
						$('modifica_button').fade();
					    //$('elimina_button').fade();
			
					    $('livello_servizio_button').fade();
					}
			    }
	                	
				function selezionaAndDeselezionaTutti(size){
					
					if($('id_check_posteggi').checked){
						for(i=0;i<size;i++){
							$('posteggi_checkbox_id'+i).checked=true;
						}
					}else{
						for(i=0;i<size;i++){
							$('posteggi_checkbox_id'+i).checked=false;
						}
					}
				}
				
              	var visualizzaDettaglioInfo = function(codiceposteggio,codicemercato,index){
              		
               		var jhqrPr = jQuery.ajax({
               			  url: '${pageContext.request.contextPath}/mercatid/ajaxDettaglioPosteggio.htm?codiceposteggio='+codiceposteggio+"&codicemercato="+codicemercato+"&indice="+index,
               			  context: document.body,
               			  cache: false,					  
               			  dataType: "html",
               			  success: function(data, textStatus, jqXHR){
               				  if(data){
               					  jQuery('#dettaglioPosteggio_'+codiceposteggio).html(data);
               					  jQuery('#dettaglioPosteggio_'+codiceposteggio).show();
               				  }					  
               			  }
               		});
              	}
	            
              	

               	function changeValue(obj,codice,nome_campo) {
	  
	  				var jq_obj=jQuery(obj);
	  				var dato = jq_obj.val();
	  				var div_salvataggio= jq_obj.closest("table").find(".salvataggio");
       				jQuery.ajax({
             			  url: '${pageContext.request.contextPath}/mercatid/ajaxChangeValue.htm?codiceposteggio='+ codice + '&value=' + dato+'&nomecampo='+nome_campo,
             			  context: document.body,
             			  cache: false,					  
             			  dataType: "json",
             			  method: 'post',
             			  	  success: function(data){
             				  if(data){
             					jQuery("#lunghezza_id_"+codice).val(data.lunghezza);
             					jQuery("#larghezza_id_"+codice).val(data.larghezza);
             					jQuery("#superficie_id_"+codice).val(data.superficie);
             					div_salvataggio.html(data.risultato)
             					if(data.status=='ok')
             					{div_salvataggio.addClass("success_header alert alert-success");}
             					else{div_salvataggio.addClass("error_header alert alert-danger");}
             					div_salvataggio.show();
	             				setTimeout(function(){div_salvataggio.hide()},3000);
	             			  }					  
             			  }
             			  
             		});
                 }
               	
               	
               	function changeIndirizzo(codice_indirizzo,codice,nome_campo) {
              	  
	  				
	  				//var div_salvataggio= jq_obj.closest("table").find(".salvataggio");
       				jQuery.ajax({
             			  url: '${pageContext.request.contextPath}/mercatid/ajaxChangeValue.htm?codiceposteggio='+ codice + '&value=' + codice_indirizzo+'&nomecampo='+nome_campo,
             			  context: document.body,
             			  cache: false,					  
             			  dataType: "json",
             			  method: 'post',
             			  	  success: function(data){
             				  if(data){
             					jQuery(".salvataggio").show();
	             				setTimeout(function(){jQuery(".salvataggio").hide()},1000);
	             			  }					  
             			  }
             		});
                 }
               	
               	function dettaglioPosteggi(){
            		
            		var url  = URLDecode('${_urlback}');			
            		ajaxHistorySet(url);			
            		setTimeout("doSubmit('../mercatid/dettaglioPosteggi.htm?codicemercato=${codicemercato}','',document.inviodati)",10);
            	}
               	
				function livelloserviziPosteggi(){
            		
            		var url  = URLDecode('${_urlback}');			
            		ajaxHistorySet(url);			
            		setTimeout("doSubmit('../mercatidlivelloservizio/create.htm','',document.inviodati)",10);
            	}
               	
               	
               	
               	function ajaxHistorySet(url){
            		
            		var jhqr = jQuery.ajax({
            			  url: '../history/ajaxSet.htm?ReturnTo='+url,
            			  context: document.body,
            			  cache: false,				
            			  dataType: "html",
            			  success: function(data) { 				   
            				} 
            			});
            		
            	}
       				
	    </script>




	


<div id="functions">

<ul>
	<li id="modifica_button" style="<%=displayButtonModifica%>"><a href="javascript:dettaglioPosteggi();"><fmt:message key="mercatid.button.changeInfo" /></a></li>	
	<li id="livello_servizio_button" style="<%=displayButtonLivelliServizio%>"><a href="javascript:livelloserviziPosteggi();"><fmt:message key="button.livelli_servizio" /></a></li>	
	
	
    <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>