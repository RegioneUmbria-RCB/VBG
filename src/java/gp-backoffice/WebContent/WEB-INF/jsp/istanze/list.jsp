<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.istanze.title.search" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.istanze.title.search" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
    		<jsp:param name="path" value="../istanze/createSearch" />
		</jsp:include>
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
					<c:if  test="${istanzeCommand.istanzeFilter.allaData != null }" >
						<fmt:message key="form.istanze.data.fine"/><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${istanzeCommand.istanzeFilter.allaData}"/></label>
					</c:if>
		       	</span>
		        <c:if test="${istanzeCommand.istanzeFilter.alberoproc.id.codice != null}">
		      		 <span class="parametri">
		             <fmt:message key="form.istanze.alberoproc" />:<label> <c:out value="${istanzeCommand.istanzeFilter.alberoproc.vwAlberoproc.scDescrizione}"/></label>
		        	</span>
		        </c:if>
		        <c:if test="${not empty istanzeCommand.istanzeFilter.tipoMovimento.id.tipomovimento}">
		      		 <span class="parametri">
		             	<fmt:message key="form.istanze.movimentoDaInserire.legend" />:<label> <c:out value="${istanzeCommand.istanzeFilter.tipoMovimento.movimento}"/></label>
		        	</span>
		        </c:if>
		        <c:if test="${not empty istanzeCommand.soggettoMovimento}">
			        <span class="parametri">
			             <fmt:message key="form.istanze.soggettoMovimento" />:<label><fmt:message key="form.istanze.dagraduatoria.soggetto.${istanzeCommand.soggettoMovimento}" /></label>
			        </span>
		        </c:if>
				<fieldset>
    			<legend><fmt:message key="form.istanze.title.list.legend" />(${fn:length(istanzeCommand.istanzeList)})</legend>
				<div class="jmesa">
				<script type="text/javascript">
					var arrayM = new Array();
					var arrayMP = new Array();
				</script>
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
							<td>
								<input type="checkbox" name="" onclick="changeMStatus(this);" title="<fmt:message key="label.checkbox.selDeselAll" />"/>
								<fmt:message key="form.istanze.checkbox.mov" />
							</td>
							<td><input type="checkbox" name="" onclick="changeMPStatus(this);" title="<fmt:message key="label.checkbox.selDeselAll" />"/><fmt:message key="form.istanze.checkbox.protmov" /></td>
						</tr>
					</thead>
					<tbody class="tbody">
						<%int x=0; %>
						<c:forEach items="${istanzeCommand.istanzeList}" var="istanza" varStatus="istanzaIdx">
						<tr class="<%=(x%2)==0?"odd":"even"%>">
							<c:if test="${not empty codiceGraduatoria}">
							<td>${istanza.transientPosGrad}</td>
							</c:if>
							<td>${istanza.numeroistanza}</td>
							<td><fmt:formatDate value="${istanza.data}" pattern="dd/MM/yyyy"/></td>
							<td>${istanza.richiedente.descrizioneRichiedente}</td>
							<td>${istanza.alberoproc.vwAlberoproc.scDescrizione}</td>
							<td><spring-form:checkbox id="checkM${istanzaIdx.index }" path="istanzeList[${istanzaIdx.index}].transientCheckMov" /></td>
							<td><spring-form:checkbox id="checkMP${istanzaIdx.index }" path="istanzeList[${istanzaIdx.index}].transientCheckProtMov" /></td>							
						</tr>
						<script type="text/javascript">
							arrayM.push('${istanzaIdx.index}');
							arrayMP.push('${istanzaIdx.index}');
						</script>
						<%x++; %>
						</c:forEach>
					</tbody>
				</table>
				</div>
				</fieldset>
			</spring-form:form>				
		</div>
		<jsp:include page="../includes/progressbar.jsp"></jsp:include>
		<div id="functions">
			<ul>		
				<c:if test="${not empty istanzeCommand.istanzeList}">
				<c:if test="${empty error}">
				<li><a href="javascript:insertMovimenti();"><fmt:message key="button.insertMovimenti" /></a></li>
				</c:if>
				</c:if>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		<script type="text/javascript">
			//<![CDATA[					   		 
			var clicked_lcl = false;
		   		function insertMovimenti(){
					if(!clicked_lcl){			   		
						clicked_lcl = true;
			   			var url = '../istanze/insertMovimenti.htm?codiceGraduatoria=${codiceGraduatoria}&soggettoMovimento=${istanzeCommand.soggettoMovimento}';
			   			$('progressbarcontainer').style.display='block';
			   			doSubmit(url,'<fmt:message key="form.istanze.insertMovimenti.alert" />',document.inviodati);			   		
			   			return;
					}
		   		}
		   		
		   		function changeMStatus(obj){
		   		 	var i = 0;
		   		 	while(i < arrayM.length){
						if(obj.checked){
							$('checkM'+arrayM[i]).checked=true;
						}else{
							$('checkM'+arrayM[i]).checked=false;
						}
						i++;
					}
		   		}
		   		 
		   		function changeMPStatus(obj){
		   		 	var i = 0;
		   		 	while(i < arrayMP.length){
						if(obj.checked){
							$('checkMP'+arrayMP[i]).checked=true;
						}else{
							$('checkMP'+arrayMP[i]).checked=false;
						}
						i++;
					}
		   		}
								
		    //]]> 
		</script>
		</body>
</html>