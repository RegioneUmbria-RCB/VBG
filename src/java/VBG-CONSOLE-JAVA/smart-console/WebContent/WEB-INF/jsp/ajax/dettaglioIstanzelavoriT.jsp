<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<spring-form:form commandName="istanzelavorit" name="innnerForm${istanzelavorit.id.codice}">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="istanzelavorit" />
	</jsp:include>
	<div id="subcontent">
	<table width="100%" >
		<tr>	
			  <td><div id="aggiornato${istanzelavorit.id.codice}" style="display: none;"></div></td>
		</tr>
		<tr>	
			<td>
				<span id="lavoro" style="display: none"></span>
			</td>
		</tr>
		<tr>	
			<td>
				<spring-form:textarea cols="60" rows="4" path="lavoro"/>
			</td>
		</tr>
		<tr>
			<td>
				<div id="functions">
					<ul>
						<li><a href="javascript:saveLavoro${istanzelavorit.id.codice}(${istanzelavorit.id.codice},document.innnerForm${istanzelavorit.id.codice});"><fmt:message key="button.update" /></a></li>
					</ul>
				</div>
			</td>
		</tr>
	</table>
	</div>
</spring-form:form>

