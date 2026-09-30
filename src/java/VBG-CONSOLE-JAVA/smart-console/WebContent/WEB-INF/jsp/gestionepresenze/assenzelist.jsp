<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.gestionepresenze.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.gestionepresenze.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">

        <div class="intestazione">
			<fmt:message key="label.filtri"></fmt:message>
		</div>
		<br />
        
      		 <span class="parametri">
             <fmt:message key="form.gestionepresenze.anno" />:<label> <c:out value="${assenzeFilter.anno}"/></label>
        	 </span>
       
         <c:if test="${assenzeFilter.mercati.id.codice!=null }">
      		 <span class="parametri">
             <fmt:message key="form.gestionepresenze.mercato" />:<label> <c:out value="${assenzeFilter.mercati.descrizione}"/></label>
        	</span>
           </c:if>   
		<c:if test="${assenzeFilter.mercatiUso.id.codice!=null }">
			<span class="parametri">
              <fmt:message key="form.gestionepresenze.mercatiUso" /> : <label><c:out value="${assenzeFilter.mercatiUso.descrizione}"/></label>
        	</span>
        </c:if>
        <c:if test="${assenzeFilter.assenze!=null }">
       		 <span class="parametri">
             <fmt:message key="form.gestionepresenze.assenze" />:<label> <c:out value="${assenzeFilter.assenze}"/></label>
    		 </span>
        </c:if>
        <br/>

        <form name="presenzeForm" action="controlloAssenze.htm">
				<jmesa:springTableFacade
					id="assenze_id" 
					items="${assenzeList}" 
					var="assenze_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
						<jmesa:htmlColumn property="nominativo" titleKey="form.gestionepresenze.nominativo" >
								
								${assenze_var.nominativo}&nbsp;${assenze_var.nome}
									
							</jmesa:htmlColumn>
							<jmesa:htmlColumn sortable="false" filterable="false" >
							<img src="../images/search.gif" onclick="javascript:dettaglioAnagrafe${assenze_var.id.codiceanagrafe}_${assenze_var.id.idposteggio}_${assenze_var.id.codicemercato}_${assenze_var.id.codiceuso}(${assenze_var.id.codiceanagrafe});" />
									<span id="anagrafe_dettaglio${assenze_var.id.codiceanagrafe}_${assenze_var.id.idposteggio}_${assenze_var.id.codicemercato}_${assenze_var.id.codiceuso}" style="display: none; text-align: left;"></span>
									<script	type="text/javascript">
										var anagrafeVisibile=false;
										function dettaglioAnagrafe${assenze_var.id.codiceanagrafe}_${assenze_var.id.idposteggio}_${assenze_var.id.codicemercato}_${assenze_var.id.codiceuso}(codiceAnagrafe){
											if(anagrafeVisibile==false){
											new Ajax.Request('../ajax/dettaglioAnagrafe.htm', {
												  method: 'post',
												  parameters: {codiceAnagrafe: codiceAnagrafe},
												  onSuccess: function(transport){
													  var response = transport.responseText;		
													  $("anagrafe_dettaglio${assenze_var.id.codiceanagrafe}_${assenze_var.id.idposteggio}_${assenze_var.id.codicemercato}_${assenze_var.id.codiceuso}").innerHTML = response;
													  $("anagrafe_dettaglio${assenze_var.id.codiceanagrafe}_${assenze_var.id.idposteggio}_${assenze_var.id.codicemercato}_${assenze_var.id.codiceuso}").appear();							  
												    },
												  onFailure: function(transport){ 
													var response = transport.responseText;
												    alert(response); }						    		 
												  });
												anagrafeVisibile=true;
											}else{
												$("anagrafe_dettaglio${assenze_var.id.codiceanagrafe}_${assenze_var.id.idposteggio}_${assenze_var.id.codicemercato}_${assenze_var.id.codiceuso}").fade();
												anagrafeVisibile=false;
											}
											  
										}
									</script>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="numeroAssenze" headerStyle="text-align: right"  style="text-align: right" titleKey="form.gestionepresenze.assenze" />
							<jmesa:htmlColumn property="mercato" titleKey="form.gestionepresenze.mercato" />
							<jmesa:htmlColumn property="uso" titleKey="form.gestionepresenze.mercatouso" />
							<jmesa:htmlColumn property="posteggio" titleKey="form.gestionepresenze.posteggio" />
            			</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='controlloAssenze.htm?';
				var _captionTab='<fmt:message key="form.gestionepresenze.title.list" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('createControlloAssenze.htm','');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>