<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.istanze.title.search" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.istanze.title.search" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<script type="text/javascript">
					var arrayMOV = new Array();
					var arrayFILTRI = new Array();
		</script>
		<div id="subcontent">
			<spring-form:form commandName="istanzeCommand" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
        			<jsp:param name="commandName" value="istanzeCommand" />
    			</jsp:include>
    			<div class="intestazione">
					<fmt:message key="label.filtri"></fmt:message>
				</div>
				<br />		        
		       	<span class="parametri">		    
					<c:if  test="${istanzeCommand.istanzeFilter.dallaData != null }" >
						<fmt:message key="form.istanze.data.inizio" /><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${istanzeCommand.istanzeFilter.dallaData}"/></label>
					</c:if>
					<script type="text/javascript">
						var di = '<fmt:formatDate pattern="dd/MM/yyyy" value="${istanzeCommand.istanzeFilter.dallaData}"/>';
						if(di){
							arrayFILTRI.push('&dataInizio_id='+di);
						}else{
							arrayFILTRI.push('&dataInizio_id=01/01/1000');
						}
					</script>
					<c:if  test="${istanzeCommand.istanzeFilter.allaData != null }" >
						<fmt:message key="form.istanze.data.fine"/><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${istanzeCommand.istanzeFilter.allaData}"/></label>
					</c:if>
					<script type="text/javascript">
						var df = '<fmt:formatDate pattern="dd/MM/yyyy" value="${istanzeCommand.istanzeFilter.allaData}"/>';
						if(df){
							arrayFILTRI.push('&dataFine_id='+df);
						}else{
							arrayFILTRI.push('&dataFine_id=01/01/3000');
						}
					</script>
		       	</span>
		        <c:if test="${istanzeCommand.istanzeFilter.alberoproc.id.codice != null}">
		      		 <span class="parametri">
		             <fmt:message key="form.istanze.alberoproc" />:<label> <c:out value="${istanzeCommand.istanzeFilter.alberoproc.vwAlberoproc.scDescrizione}"/></label>
		        	</span>	        	
		        </c:if>
		        <script type="text/javascript">
						arrayFILTRI.push('&alberoproc_id=${istanzeCommand.istanzeFilter.alberoproc.id.codice}');
				</script>
		        <c:if test="${not empty istanzeCommand.istanzeFilter.tipoMovimento.id.tipomovimento}">
		      		 <span class="parametri">
		             	<fmt:message key="form.istanze.movimentoDaInserire.legend" />:<label> <c:out value="${istanzeCommand.istanzeFilter.tipoMovimento.movimento}"/></label>
		        	</span>	        	
		        </c:if>
		        <script type="text/javascript">
						arrayFILTRI.push('&codicetipomovimento=${istanzeCommand.istanzeFilter.tipoMovimento.id.tipomovimento}');
				</script>
		        <c:if test="${not empty istanzeCommand.soggettoMovimento}">
			        <span class="parametri">
			             <fmt:message key="form.istanze.soggettoMovimento" />:<label><fmt:message key="form.istanze.dagraduatoria.soggetto.${istanzeCommand.soggettoMovimento}" /></label>
			        </span>
		        </c:if>
		        <script type="text/javascript">
						arrayFILTRI.push('&soggettoMov_id=${istanzeCommand.soggettoMovimento}');
				</script>
		        <c:if test="${not empty sessionScope.erroriList}">
			        <fieldset>
	    			<legend><fmt:message key="form.istanze.error.list" /></legend>
	    				<ol>
							<c:forEach items="${sessionScope.erroriList}" var="err">
								<li>
									${err}					
								</li>
							</c:forEach>
						</ol>					
					</fieldset>
				</c:if>
		        <c:if test="${not empty sessionScope.istanzeNonInseriteList}">
		        <fieldset>
    			<legend><fmt:message key="form.istanze.title.listNonInserite.legend" /></legend>
    			<div class="jmesa">
				<table class="table">
					<thead class="header">
						<tr>
							<td><fmt:message key="form.istanze.numero" /></td>
							<td><fmt:message key="form.istanze.data" /></td>
							<td><fmt:message key="form.istanze.richiedente" /></td>
							<td><fmt:message key="form.istanze.alberoproc" /></td>						
						</tr>
					</thead>
					<tbody class="tbody">
						<%int y=0; %>
						<c:forEach items="${sessionScope.istanzeNonInseriteList}" var="istanzaN" varStatus="istanzaNIdx">
						<tr class="<%=(y%2)==0?"odd":"even"%>">
							<td>${istanzaN.numeroistanza}</td>
							<td><fmt:formatDate value="${istanzaN.data}" pattern="dd/MM/yyyy"/></td>
							<td>${istanzaN.richiedente.descrizioneRichiedente}</td>
							<td>${istanzaN.alberoproc.vwAlberoproc.scDescrizione}</td>
						</tr>
						<%y++; %>
						</c:forEach>					
					</tbody>
				</table>
				</div>
				</fieldset>
				</c:if>
		        <fieldset>
    			<legend><fmt:message key="form.istanze.title.listInserite.legend" /></legend>
    			<div class="jmesa">
				<table class="table">
					<thead class="header">
						<tr>
							<c:if test="${not empty codiceGraduatoria}">
							<td><fmt:message key="label.posizione_graduatoria" /></td>
							</c:if>
							<td><fmt:message key="form.istanze.numero" /></td>
							<td><fmt:message key="form.istanze.data" /></td>
							<td><fmt:message key="form.istanze.richiedente" /></td>
							<td><fmt:message key="form.istanze.alberoproc" /></td>
							<td><fmt:message key="form.istanze.movimentoinserito" /></td>
							<td><fmt:message key="form.istanze.movimentoinserito.nprot" /></td>	
							<td><fmt:message key="form.istanze.movimentoinserito.dataprot" /></td>						
						</tr>
					</thead>
					<tbody class="tbody">
						<%int x=0; %>
						<c:forEach items="${sessionScope.istanzeInseriteList}" var="istanza" varStatus="istanzaIdx">
						<tr class="<%=(x%2)==0?"odd":"even"%>">
							<c:if test="${not empty codiceGraduatoria}">
							<td>${istanza.transientPosGrad}</td>
							</c:if>
							<td>${istanza.numeroistanza}</td>
							<td><fmt:formatDate value="${istanza.data}" pattern="dd/MM/yyyy"/></td>
							<td>${istanza.richiedente.descrizioneRichiedente}</td>
							<td>${istanza.alberoproc.vwAlberoproc.scDescrizione}</td>
							<td>${istanza.transientMovimento.id.codice}</td>
							<td>${istanza.transientMovimento.numeroprotocollo}</td>
							<td><fmt:formatDate value="${istanza.transientMovimento.dataprotocollo}" pattern="dd/MM/yyyy"/></td>
						</tr>
						<%x++; %>
						<script type="text/javascript">
							arrayMOV.push('${istanza.transientMovimento.id.codice}');
						</script>
						</c:forEach>					
					</tbody>
				</table>
				</div>
				</fieldset>
    		</spring-form:form>
    	</div>
    	<% 
    	String urlStampaDaGraduatoria = BackofficeNETConstants.getUrlTo(request,BackofficeNETConstants.getURL_STAMPA_GRADUATORIA()+"?codGrad=SEGNAPOSTOGRADUATORIA&listaCodMov=SEGNAPOSTOMOVIMENTI-SEGNAPOSTOALTRO-","",(String)session.getAttribute(WebConstants.SOFTWARE),true);
    	String urlStampaMassiva = BackofficeNETConstants.getUrlTo(request,BackofficeNETConstants.getURL_STAMPA_MASSIVA_MOV()+"?listaCodMov=SEGNAPOSTOMOVIMENTI-SEGNAPOSTOALTRO-","",(String)session.getAttribute(WebConstants.SOFTWARE),true);
    	pageContext.setAttribute("urlStampaDaGraduatoria",urlStampaDaGraduatoria);
		pageContext.setAttribute("urlStampaMassiva",urlStampaMassiva);
    	%>
    	<script type="text/javascript">
    		function stampaDaGraduatoria(){
    			resetAttribute();
    			var url = '${urlStampaDaGraduatoria}';
    			url = url.replace('SEGNAPOSTOGRADUATORIA','${codiceGraduatoria}');
    			var i = 0;
    			var j = 0;
    			var listaMov='';
		   		while(i < arrayMOV.length){
					listaMov=listaMov+arrayMOV[i];
					if(i<(arrayMOV.length-1)){
						listaMov=listaMov+',';
					}
					i++;
				}
		   		url = url.replace('SEGNAPOSTOMOVIMENTI',listaMov);
		   		var altro = '';
				while(j < arrayFILTRI.length){
					altro = altro + arrayFILTRI[j];					
					j++;
				}
		   		url = url.replace('-SEGNAPOSTOALTRO-',escape(altro));
				window.open(url,66,'width=600,height=250,menubar=no,scrollbars=no,status=yes,resizable=no');
    		}
    		
    		function stampaMassiva(){
    			resetAttribute();
    			var url = '${urlStampaMassiva}&listaCodMov=';
    			var i = 0;
    			var j = 0;
    			var listaMov='';
		   		while(i < arrayMOV.length){
					listaMov=listaMov+arrayMOV[i];
					if(i<(arrayMOV.length-1)){
						listaMov=listaMov+',';
					}
					i++;
				}
		   		url = url.replace('SEGNAPOSTOMOVIMENTI',listaMov);
		   		var altro = '';
				while(j < arrayFILTRI.length){
					altro = altro + arrayFILTRI[j];					
					j++;
				}
		   		url = url.replace('-SEGNAPOSTOALTRO-',escape(altro));
				window.open(url,66,'width=600,height=250,menubar=no,scrollbars=no,status=yes,resizable=no');
    		}

    		function resetAttribute(){
	    			new Ajax.Request('<%=request.getContextPath()%>/istanze/resetInsertAttribute.htm', {
	  				  method: 'post',	
	  				  onSuccess: function(transport){  },
	  				  onFailure: function(transport){  }						    		 
		  			});

    		}
		function chiudi(){
				resetAttribute();
	    		doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','');
	    }
    	</script>
    	<div id="functions">
			<ul>
				<c:if test="${not empty istanzeCommand.soggettoMovimento}">
				<li><a href="javascript:stampaDaGraduatoria();"><fmt:message key="button.stampa_lettera" /></a></li>
				</c:if>
				<c:if test="${empty istanzeCommand.soggettoMovimento}">
				<li><a href="javascript:stampaMassiva();"><fmt:message key="button.stampa_lettera" /></a></li>
				</c:if>
				<li><a href="javascript:chiudi();"><fmt:message key="button.back" /></a></li>
				<li><a href="javascript:resetAttribute();"><fmt:message key="button.reset" /></a></li>
			</ul>
		</div>
	</body>
</html>