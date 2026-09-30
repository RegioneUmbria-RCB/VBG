<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		
			<fmt:message key="label.alberoprocfotop" />
		
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.alberoprocfotop" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="alberoprocFoTop" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="alberoprocFoTop" />
    </jsp:include>
	<table>
		<tr>
			<td  width="20%"><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="120" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td style="vertical-align: top;"><fmt:message key="label.alberoproc" /></td>
			<td>
				
				<spring-form:input id="alberoproc_hidden" path="alberoproc.id.codice" onchange="cercaProcedimento()" size="9" cssStyle="text-align: right;" />
							<%-- ALBEROPROC DOJO TREE --%>
							<a class="vbg-btn btn-cerca" href="javascript:cercaProcedimento();" id="alberoimg_id" style="vertical-align: bottom;" title="Cerca procedimento" >
							</a>
							<spring-form:input id="alberoproc_descrestesa_hidden" path="alberoproc.vwAlberoproc.scDescrizione" size="100" readonly="true" />
							<spring-form:errors path="alberoproc" cssClass="error" />
							<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" 
								url="${pageContext.request.contextPath}/json/getAlberoproc.htm?_timestamp=<%=String.valueOf(System.currentTimeMillis()) %>"> 
							</div>
							<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" 
								rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" 
								childrenAttrs="children">
							</div>							
							<div id="treeOne"></div>
		   	  				<div id="mostraEndoDiv" style="border: 1px;">&nbsp;</div>
							<script type="text/javascript">
								var treeControl = null;
								var treeInitialized = false;
								
								function cercaProcedimento(){									
									var codProc = $('alberoproc_hidden').value;
									rimuoviValori2();
									if(codProc){
										cercaProcedimentoAjax(codProc);	
									}else{
										apriAlbero();
									}
									$('alberoproc_hidden').focus();
								}
								
								function cercaProcedimentoAjax(codiceAlberoproc){
									if(isNaN(codiceAlberoproc)){
										alert("Ricerca per codice. Inserire un valore numerico");
										return;
									}
									new Ajax.Request('../json/getAlberoprocHelper.htm?hideDisabled=false', {
										  method: 'post',
										  parameters: {id: codiceAlberoproc},
										  onSuccess: function(transport){ 
											var response = transport.responseText;
											//alert(response);
											var json = response.evalJSON();
											if(json.id){
												if(json.padre == 'true'){
													alert("Procedimento non selezionabile.");
												}else{
													assegnaValori2(json);
													$('treeOne').style.display="none";
												}						
											}else{
												alert("Procedimento non trovato o disattivato.");
										    }
										  },
										  onFailure: function(transport){ 
											var response = transport.responseText; 
										    alert("Errore nella ricerca del procedimento!");
										  }						    		 
									} );
								}
								
								jQuery($('alberoproc_hidden')).keypress(function(e) {
							  	  	var code = e.keyCode ? e.keyCode : e.which;
									if(code.toString() == 13) {
										cercaProcedimento(); 
									}
							    });
								
								function apriAlbero() {
							        if(!treeControl){
								        treeControl = new dijit.Tree({
								            model: alberoprocModel,
								            showRoot: true,							            
								            onClick: function(item, node){
								            	if(item.id !='0' ){	
									        		var itemId = alberoprocStore.getValue(item, "id");
									        		if(itemId>0){
														cercaProcedimentoAjax(itemId);
														$('alberoproc_hidden').focus();
									        		}
								            	}
								            },
								            getIconClass: function(item,opened){							            	
								            	treeInitialized = true;
								            	if(item.id!='0'){
									        		var dis = 'false';
									        		if(item){
									        			dis = alberoprocStore.getValue(item, "disabilitato");
									        		}
									        		if(dis == 'false'){
									        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf";
									        		}else{
									        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled";
									        		}
								            	}else{
								            		return "dijitFolderOpened"
								            	}	
								            }
								        },
								        "treeOne");
							        }else{
							        	document.getElementById('treeOne').style.display="";
							        }
							    }
								
								
								
								var endoSplashDiv=null;
								
								jQuery(document).ready(function(){
									 endoSplashDiv = new dijit.Dialog({
							            title: "<fmt:message key='label.endoprocedimenti' />" ,
							            style: "width: 500px"
							        });
								});
								
								function mostraDivEndo(codiceAlberoproc){
									
											
									
								}
								
								var progressivoIstanza='';
								
								function trovaProgressivoIstanza(codiceAlberoproc){
															
								}
								
							function chiudiEndoDiv(){
								
							}
								
							function assegnaValori2(map){
								$('alberoproc_hidden').value = map.id;
								$('alberoproc_descrestesa_hidden').value = map.desc;
							}
								
							function resetEndoInSession(){
								
							}
								
								
								function rimuoviValori2(){
									$('alberoproc_hidden').value = '';
									$('alberoproc_descrestesa_hidden').value = '';
								}
								
								function endoInSession(chkobjid, codiceinventario) {			
																	
								}
								
								</script>
				
				
				
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.ordine" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="3" maxlength="3" cssStyle="text-align: right;" />
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
	</table>

	<script type='text/javascript'>
		$('descrizione_id').focus();


		
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${alberoprocFoTop.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${alberoprocFoTop.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
