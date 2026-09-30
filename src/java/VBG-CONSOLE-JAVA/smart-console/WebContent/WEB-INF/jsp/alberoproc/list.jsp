<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="alberoproc.label.lista_procedimenti.title" />
</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="alberoproc.label.lista_procedimenti.title" />
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
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
		   <jsp:param name="commandName" value="alberoprocCommand" />
		</jsp:include>
		<c:set var="TREE_ADMIN_USER" value="false"/>
		<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">										
			<spring-security:authorize ifNotGranted="ROLE_PREVIOUS_ADMINISTRATOR">
				<c:set var="TREE_ADMIN_USER" value="true"/>
			</spring-security:authorize>
		</spring-security:authorize>
	
		<%--	
		<ul dojoType="dijit.Menu" id="tree_menu" style="display: none;">
			<li id="abi" dojoType="dijit.MenuItem" onClick="abilita(item, true)">Abilita procedimento</li>
			<li id="dis" dojoType="dijit.MenuItem" onClick="alert('Disabilitato');">Disabilita procedimento</li>
		</ul>
		 --%>
		 <%--
		<c:if test="${ TREE_ADMIN_USER eq true}">
			<div dojoType="dojo.data.ItemFileWriteStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>
			<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
			<div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" 
				dndController="dijit.tree.dndSource" 
				checkAcceptance="dragAccept" 
				checkItemAcceptance="dropAccept"
				dragThreshold="8" ><%--<script type="dojo/connect">
		
	    	var menu = dijit.byId("tree_menu");
	    	// when we right-click anywhere on the tree, make sure we open the menu
	    	 menu.bindDomNode(this.domNode);
	    
	    	dojo.connect(menu, "_openMyself", this, function(e){
	      		// get a hold of, and log out, the tree node that was the source of this open event
	      		var tn = dijit.getEnclosingWidget(e.target);
	      		console.debug(tn);
	
	      		// now inspect the data store item that backs the tree node:
	      		console.debug(tn.item);
	
	      		// contrived condition: if this tree node doesn't have any children, disable all of the menu items
	      		// menu.getChildren().forEach(function(i){ i.setDisabled(!tn.item.children); });
	
	      		// IMPLEMENT CUSTOM MENU BEHAVIOR HERE
	
	    	});
			
		
	   </script> -- %>
	   </div>
		</c:if>	
		 --%>
		 <%--
		<c:if test="${ TREE_ADMIN_USER eq false}">
		 --%>
			<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>
			<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
			<div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" />
		<%--	
		</c:if>
		 --%>
	   	<script type="dojo/method" event="onClick" args="item">
		if(item.id != '0'){
			var itemId = alberoprocStore.getValue(item, "id");
			if(itemId>0){
				var rt = '&<%=BackofficeNETConstants.RETURNTO%>='+URLEncode(URLEncode('<%=request.getContextPath()%>/history/back.htm?' + '<%=WebConstants.GOTO%>' + '=%2F'));
				var goTo = '../alberoproc/view.htm?codice='+alberoprocStore.getValue(item, "id");
				var returnTo = URLDecode('${_urlback}');
				historySet(returnTo,goTo);
			}
		}
   		</script>
	
	   <script type="dojo/method" event="getIconClass" args="item, opened">
		if(item.id != '0'){
			var dis = 'false';
			if(item){
				dis = alberoprocStore.getValue(item, "disabilitato");
			}
			if(dis == 'false'){
				return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
			}else{
				return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled"
			}
		}else{
			return "dijitFolderOpened"
		}
   		</script>
   		<%--
			<c:if test="${TREE_ADMIN_USER eq true}">   		   		
			   		 <script type="text/javascript">
			   		 
				  	dojo.require("dojo.data.ItemFileWriteStore");
				    dojo.require("dijit.tree.dndSource");
				    dojo.require("dojo.dnd.common");
				    dojo.require("dojo.dnd.Source");
				  	
					  	function dragAccept(source,nodes){					  		
							 return true;								
					  	} 
				  	    /**
				  	     * check if item to drop is a team
				  	     * @return true if item is team, false otherwise
				  	     */
				  	    function dropAccept(node,source) {
			  	    		return true;
				  	    }
				  	    var alberoSorgente = null;
				  	    var alberoDestinazione = null;
				  	  	dojo.addOnLoad(function(){
					  		dojo.subscribe("/dnd/start", function(source){
					  			if(source.currentWidget){
						  			if(source.currentWidget.item){
					  					alberoSorgente = source.currentWidget.item.id;
						  			}
					  			}
							});
							dojo.subscribe("/dnd/drop", function(source, nodes, copy, target){
									if(source.currentWidget){
							  			if(source.currentWidget.item){
											alberoDestinazione = source.currentWidget.item.id;
							  			}
									}									
									if(alberoSorgente!=null && alberoDestinazione!=null) {
										console.debug("alberoSorgente: "+alberoSorgente);
										console.debug("alberoDestinazione: "+alberoDestinazione);
										doHref('spostaVoceAlbero.htm?_ts='+new Date().getTime()+'&sorgente='+alberoSorgente+'&destinazione='+alberoDestinazione,'<fmt:message key="javascript.confirm.procedere_con_l_operazione" />');
									}else{
										alert('<fmt:message key="03"/>');
										document.location.reload();
									}
							});				
				  		}); 
				  	  
				function abilita(item, seAbilita){
					alert(item);
				}
				</script>  		
			</c:if>   		
		 --%>	
</spring-form:form>
</div>
<div id="functions">
<ul>
<%
				
if(ORMHelper.isConsoleRegionale()){
								    
%>
	<li><a href="javascript:doHref('create.htm?codicepadre=0','');"><fmt:message key="button.new" /></a></li>
<%} %>

	<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
</ul>



</div>
</body>
</html>