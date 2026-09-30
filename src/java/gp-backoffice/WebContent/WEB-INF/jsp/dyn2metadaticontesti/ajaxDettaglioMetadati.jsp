<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<div id="editor-dettaglio">

    <div id="form-inserimento-righe">
    
        <table>       
            
            <tr class="selezione-campo-dinamico">
                <td>
                    <fmt:message key="label.campo"/>
                </td>
                <td>     
                    <input id="metadato_id" name="metadato" class="searchbox" size="60" onkeydown="return searchAll(this,event)"/>
                    <init:autocompleter methodAjax="findAllDyn2Campi.htm"  idHidden="metadato_id_hidden" idInput="metadato_id" inputTitleKey="" minChars="1" />
                    <input type="hidden" id="metadato_id_hidden" name="metadato_id_hidden" />       
                </td>
            </tr>
            
            <tr class="validate-contesto">
                <td>
                    <fmt:message key="label.contestoCampo"/>
                </td>
                <td>            
                    <input type="text" id="contestocampo_id"  name="contestoCampo" style="width: 328px;" class="control-to-validate"> 
                    <init:help idHelp="contestocampo_help_id" textKey="dyn2metadaticontesti.help.contesto"/>
                    <div id="errori_contestocampo" class="error validation-feedback">Il campo contiene un valore non valido. Può contenere solo numeri, lettere e _ senza spazi.</div>  
                </td>
            </tr>
            
            <tr>
                <td colspan="2"><div id="errore-dettaglio-schede" style="display:none;"></div></td>
            </tr>
        </table>
        
        <div id="functions">
            <ul>
                <li><a href="#" id="insert-dettaglio-metadati" data-id="${dyn2metadaticontesti.id.codice}" class="bottone-salva"><fmt:message key="button.insert"/> </a></li>
            </ul>
        </div>
    
    </div>

</div>

<c:if test="${not empty dyn2MetadatiList}">
    <div class="jmesa">
    	<table class="table" id="dettaglio-metadati">
    		<thead>
    			<tr class="header">	
    				<td><fmt:message key="label.campo" /></td>
    				<td><fmt:message key="label.contestoCampo" /></td>
    				<td><fmt:message key="label.azioni" /></td>				
    			</tr>
    		</thead>
    		<tbody>
    			
    		<c:forEach items="${dyn2MetadatiList}" var="entry" varStatus="idx">
        		<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
        		        <c:choose>
        		        	<c:when test="${codiceMetadatoContesto == entry.id.codice}">
        		        		<c:set value="#aaf981" var="color" scope="page"></c:set>
        		        	</c:when>
        		            <c:otherwise><c:set value="" var="color" scope="page"></c:set></c:otherwise>
        		        </c:choose>
        		        <td style="background-color:${color};">${entry.dyn2Campi.nomecampo}</td>
            	 	    <td style="background-color:${color};">${entry.contestoCampo}</td>
            	 		<td style="background-color:${color}">
            	 			<a class="elimina-riga eliminaRiga" data-id="${entry.id.codice}" href="#" title="<fmt:message key="label.elimina" /> ${entry.id.codice}">
        				         	 <label><fmt:message key="label.elimina.image" /></label>
        					</a>			
        				</td>
        		</tr>    	 
    		</c:forEach>				
    		</tbody>
    	</table>		
    </div>
</c:if>