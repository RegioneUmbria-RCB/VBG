<!-- CREA UN CAMPO DI RICERCA CHE PERMETTE DI SELEZIONARE UN PROCEDIMENTO MEDIANTE UNA STRUTTURA AD ALBERO  -->

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<!--CHECK PARAMETRI OBBLIGATORI -->

<c:if test="${empty param.propertyPath}">
	[searchAlberoProc.jsp]  Attenzione !! non è stato settato il parametro propertyPath.
	<c:set var="disabilitaFunzionamento" value="true" />
</c:if>
<c:if test="${empty param.pathPropertyDescription}">
	[searchAlberoProc.jsp]  Attenzione !! non è stato settato il parametro pathPropertyDescription.
	<c:set var="disabilitaFunzionamento" value="true" />
</c:if>
<c:if test="${empty param.pathPropertyCode}">
	[searchAlberoProc.jsp]  Attenzione !! non è stato settato il parametro pathPropertyCode.
	<c:set var="disabilitaFunzionamento" value="true" />
</c:if>

<!-- PARMETRI OPZIONALI -->

<c:set var="isSelectLeafDisable" value="" />
<c:if test="${not empty param.isSelectLeafDisable}">
    <c:if test="${param.isSelectLeafDisable eq true}">
		<c:set var="isSelectLeafDisable" value="false" />
	</c:if>
	    <c:if test="${param.isSelectLeafDisable eq false}">
		<c:set var="isSelectLeafDisable" value="true" />
	</c:if>
</c:if>
<c:if test="${empty param.isSelectLeafDisable}">
	<c:set var="isSelectLeafDisable" value="false" />
</c:if>

<c:set var="isSelectNodoPadre" value="" />
<c:if test="${not empty param.isSelectNodoPadre}">
	<c:set var="isSelectNodoPadre" value="${param.isSelectNodoPadre}" />
</c:if>
<c:if test="${empty param.isSelectNodoPadre}">
	<c:set var="isSelectNodoPadre" value="false" />
</c:if>


<spring-form:input id="alberoproc_hidden" path="${param.pathPropertyCode}" onchange="cercaProcedimento()" size="9" cssStyle="text-align: right;" />
	<%-- ALBEROPROC DOJO TREE --%>
	<a href="javascript:cercaProcedimento();" style="vertical-align: bottom;" ><img id="alberoimg_id" border="0" src="${pageContext.request.contextPath }/images/search.gif" title="Cerca procedimento" /></a>
	<spring-form:input id="alberoproc_descrestesa_hidden" path="${param.pathPropertyDescription}" size="100" readonly="true" />
	<spring-form:errors path="${param.propertyPath}" cssClass="error" />
	<%-- <spring-form:hidden	id="alberoproc_hidden" path="${param.pathPropertyCode}"/>--%>
	<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" 
		url="${pageContext.request.contextPath}/json/getAlberoproc.htm?_timestamp=<%=String.valueOf(System.currentTimeMillis()) %>"> 
	</div>
	<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" 
		rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" 
		childrenAttrs="children">
	</div>
	<br />
	<div id="treeOne"></div>
  				<div id="mostraEndoDiv" style="border: 1px;">&nbsp;</div>
	<script type="text/javascript">
		var treeControl = null;
		var treeInitialized = false;
		function cercaProcedimento(){												
			var codProc = $('alberoproc_hidden').value;
				remuvevalue();
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
			new Ajax.Request('../json/getAlberoprocHelper.htm?hideDisabled=${isSelectLeafDisable}', {
				  method: 'post',
				  parameters: {id: codiceAlberoproc},
				  onSuccess: function(transport){ 
					var response = transport.responseText;
					var json = response.evalJSON();
					if(json.id){
						if(!${isSelectNodoPadre} && json.padre == 'true'){
							alert("Procedimento non selezionabile.");
						}else{
							assignvalue(json);
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
		function assignvalue(map){
			$('alberoproc_hidden').value = map.id;
			$('alberoproc_descrestesa_hidden').value = map.desc;		
		}
		function remuvevalue(){
			$('alberoproc_hidden').value = '';
			$('alberoproc_descrestesa_hidden').value = '';
			
		}			
		</script>