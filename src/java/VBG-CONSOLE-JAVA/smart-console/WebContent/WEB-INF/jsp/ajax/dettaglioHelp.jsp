<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<spring-form:form commandName="helpCommand" name="help">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="helpCommand" />
	</jsp:include>
	<div id="subcontent">
	<table width="100%" >
		<!-- SEZIONE HELP BASE -->
		 <c:if test="${isChangeHelpBase eq true }">
		 <tr>
			<td width="60%" class="titoloSezione" > 
				<fmt:message key='label.inserisci_help_base' />
				
			</td>
			<td>
			<a style="float: right;" class="infoColumn" href="javascript:showAnteprima('id_helpbase');"  
						title="<fmt:message key="label.anteprima"></fmt:message>">
   						<label><fmt:message key="label.anteprima" /></label>
				</a>
			</td>
		</tr>
		</c:if>
		<c:if test="${isChangeHelpBase eq true }">
	    	<tr>
	    		<td colspan="2"> 
	 				<spring-form:textarea id="id_helpbase" path="helpbase.helptext" rows="10" cols="98"/>
	 				<spring-form:hidden path="helpbase.id.contenttype" />
	 	    	</td>
   		</tr>
	 	</c:if>
		<c:if test="${isChangeHelpBase eq false }">
			<tr>
	 	    	<td class="titoloSezione" > 
					<fmt:message key='label.help_base' />
				</td>
			</tr>
			<tr>	
	 	 		<td>
	 				${helpCommand.helpbase.helptext}
	 			</td>
	 		</tr>
		</c:if>
		<c:if test="${isChangeHelpBase eq true }">
		<tr>
			<td colspan="2">
			 <spring-form:select id="id_software" path="helpbase.id.software" onchange="showHelp('id_software',true);" >
			   <spring-form:options items="${listSoftware}" itemValue="codice" itemLabel="descrizione" />
			 </spring-form:select>
			</td>
		</tr>
		</c:if>
		<c:if test="${isChangeHelpBase eq true }">
		<tr>
			<td colspan="2">
				<div id="functions">
					<ul>
						<li><a href="javascript:saveBaseHelp();"><fmt:message key="button.update" /></a></li>
					</ul>
				</div>
			</td>
		</tr>
		</c:if>
		</table>
		<c:if test="${isChangeHelp eq true }">
		<table >
		</c:if>
		<c:if test="${isChangeHelp eq false }">
		<table style="padding-top: 100px;" width="100%">
		</c:if>
		<!-- SEZIONE HELP SPECIFICO -->
		<c:if test="${isChangeHelp eq true }">
		 <tr>
			<td  class="titoloSezione"> 
				<fmt:message key='label.inserisci_help' />
			</td>
			<td>	
				<a style="float: right;" class="infoColumn" href="javascript:showAnteprima('id_help');"  
						title="<fmt:message key="label.anteprima"></fmt:message>">
   						<label><fmt:message key="label.anteprima" /></label>
				</a>
			</td>
		</tr>
		</c:if>
		<c:if test="${isChangeHelp eq true }">
			<tr>
				<td colspan="2"> 
			    	<spring-form:textarea id="id_help" path="help.helptext" rows="10" cols="98"/>
			 		<spring-form:hidden path="help.id.contenttype" />
				 </td>
			</tr>
		</c:if>
	    <c:if test="${isChangeHelp eq false }">
	 		<tr>
		 		<td width="100%" class="titoloSezione" > 
		 			<fmt:message key='label.help' />
	 	 		</td>
	 		</tr>
	 		<tr>
	 			<td width="100%">
	 				${helpCommand.help.helptext}
	 			</td>
	 		</tr>
 		</c:if>
		<c:if test="${isChangeHelp eq true }">
		<tr>
			<td colspan="2">
			 <spring-form:select id="id_software_help" path="help.id.software"  onchange="showHelp('id_software_help',false);" >
			   <spring-form:options items="${listSoftwareAbilitati}" itemValue="codice" itemLabel="descrizione"  />
			 </spring-form:select>
			</td>
		</tr>
		</c:if>
		<c:if test="${isChangeHelp eq true }">
		<tr>
			<td colspan="2">
				<div id="functions">
					<ul>
						<li><a href="javascript:saveHelp();"><fmt:message key="button.update" /></a></li>
					</ul>
				</div>
			</td>
		</tr>
		</c:if>
	</table>
	</div>
</spring-form:form>

