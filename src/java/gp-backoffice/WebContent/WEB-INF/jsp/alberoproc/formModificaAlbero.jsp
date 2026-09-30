<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.AuthLevel"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.AlberoprocAteco" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Ateco" %>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="java.net.URLEncoder" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="it">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message
				key="alberoproc.label.spostamento_alberoproc.title" /></title>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>		
		<style media="all">
			.daSpostare {
			    padding: 16px 0;
			}
			
			fieldset {
			    width: max-content;
			}
			
			.miniTree {
			    list-style: none;
			}
		</style>		 
	</head>

<body>
	<span class="titoloPagina">
		 <fmt:message key="alberoproc.label.spostamento_alberoproc.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../alberoproc/view" />
	</jsp:include>
	
	<input id="idRamoDaSpostare" type="hidden" value="${idRamoDaSpostare}"/>
	
	<div id="messPagina" class="error"></div>
	<div class="corpo">
	    <div id="daSpostare" class="vbg-form">	
			<fieldset>
				<legend><b>Stai per spostare le seguenti voci che hai selezionato: </b></legend>
				<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStoreMini" url="${pageContext.request.contextPath}/json/getAlberoprocMini.htm?id=${idRamoDaSpostare}"></div>
	                       
	            <div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModelMini" store="alberoprocStoreMini" query="{root:'1'}" rootId="<%=WebConstants.ATECO_CODICE_ROOT%>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
	           	            
	            <div dojoType="dijit.Tree" id="tree3" model="alberoprocModelMini" />	
			</fieldset>			
	    </div>

	    <div id="alberoCompleto" class="vbg-form">
	        <fieldset>
	        	<legend><b>Per proseguire seleziona un nodo o una foglia e conferma l'operazione: </b></legend>
	            <div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>
	                       
	            <div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore" query="{root:'1'}" rootId="<%=WebConstants.ATECO_CODICE_ROOT%>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
	            
	            <div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" />
	            
	            <script type="dojo/method" event="onClick" args="item">
		        if(item.id != '0'){
		        	var itemId = alberoprocStore.getValue(item, "id");		 
					var descr = alberoprocStore.getValue(item, "descrizioneEstesa");									
		        	cercaProcedimentoAjax(itemId,descr);
		        }
   		   		 </script>
	           
	            <script type="dojo/method" event="getIconClass" args="item, opened">
                if (item.id != '0') {
                    var dis = 'false';
                    var scPubblica = '';
                    if (item) {
                        dis = alberoprocStore.getValue(item, "disabilitato");
                        scPubblica = alberoprocStore.getValue(item, "scPubblica");
                    }
                    var icona = "";
                    if (dis == 'false') {
                        if (!item || this.model.mayHaveChildren(item)) {
                            if (opened) {
                                icona = "dijitFolderOpened";
                            } else {
                                icona = "dijitFolderClosed";
                            }
                        } else {
                            icona = "dijitLeaf";
                        }

                        if (scPubblica) {
                            if (scPubblica == '0') {
                                icona = icona.replace('dijit', 'nonPubblicare');
                            } else if (scPubblica == '1') {
                                icona = icona.replace('dijit', 'areaRiservataFrontoffice');
                            } else if (scPubblica == '2') {
                                icona = icona.replace('dijit', 'areaRiservata');
                            } else if (scPubblica == '3') {
                                icona = icona.replace('dijit', 'frontoffice');
                            }
                        }

                        return icona;

                    } else {
                        return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled"
                    }
                } else {
                    return "dijitFolderOpened"
                }
            </script>
	        </fieldset>
	    </div>  
    </div> 
    
    <vbg-modal id="vbgmodal">
		<div slot='body'>
			<h1>
                Conferma
             </h1>
             <p>
              	Stai spostando l'elemento selezionato sotto <b><span id="spanDescr"></span></b>. <br/>
              	L'operazione non si può annullare. <br/>
              	Vuoi procedere?
             </p> 
             <input id="scIdHidden" type="hidden" />           
		</div>
		<div slot='footer'>
			<div class="btn btn-primary" id="okModal"><fmt:message key="button.ok"/> </div>
			<div class="btn btn-primary" id="closeModal"><fmt:message key="button.close"/></div>            
		</div>
	</vbg-modal>
	
	<vbg-modal id="vbgPopup" data-auto-open='false'>
		<div slot='body'>
			<h1>
                Info
             </h1>
             <p>
              	L'elemento selezionato è stato spostato con successo.</br>
              	Verrai reindirizzato alla pagina di dettaglio dell'intervento.
             </p>                     
		</div>
		<div slot='footer'>			
			<div class="btn btn-primary" id="closePopup"><fmt:message key="button.close"/></div>            
		</div>
	</vbg-modal>
	
	
	
    <div id="functions">
		<ul>
			<li id="buttonOnRoot"><a href="#"><fmt:message key="button.spostaSuRoot" /></a></li>			
			<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>	
		</ul>
	</div>
	
	<script type="text/javascript">
   
	    const modal = document.getElementById('vbgmodal');
	    const popupInfo = document.getElementById('vbgPopup');
	    const scId = document.getElementById('scIdHidden');
	    const idRamoDaSpostare = document.getElementById('idRamoDaSpostare');	        
	       
		function cercaProcedimentoAjax(codiceAlberoproc, desc){
			
			console.log(codiceAlberoproc+ " "+ desc);			
			document.getElementById('spanDescr').innerHTML = desc;
			scId.value = codiceAlberoproc;			
			modal.open();
			
		}
		
		document.getElementById('okModal').addEventListener('click', (e)=>{		
			
			spostaElementoAlbero(scId.value,idRamoDaSpostare.value);			
			
		});
		
		document.getElementById('closeModal').addEventListener('click', (e)=>{
			modal.close();
			
		});
		
		document.getElementById('closePopup').addEventListener('click', (e)=>{
			popupInfo.close();
			location.href = '../alberoproc/view.htm?codice=${idRamoDaSpostare}';
			vbg.mostraModalCaricamento();
			
		});
		
				
		document.getElementById('buttonOnRoot').addEventListener('click', (e)=>{
			console.log('bottone Sposta su root');
			document.getElementById('spanDescr').innerHTML = 'Albero degli interventi';
			modal.open();			
		});
		
		function spostaElementoAlbero(scId,idRamoDaSpostare){
			
			if(scId==''){
				scId=0;
			}			
			vbg.mostraModalCaricamento();
			var jhqrPr = jQuery.ajax({
				  url: 'ajaxModificaAlbero.htm',
				  data: {
						scId : scId,
						idRamoDaSpostare : idRamoDaSpostare
					},
				  context: document.body,
				  cache: false,					  
				  dataType: "html",				  
				  success: function(data){
					  if (data =='OK') {
						  modal.close();
						  vbg.nascondiModalCaricamento();
						  popupInfo.open();						  
						}	  
					  
				  },
			      error: function (xhr, ajaxOptions, thrownError) {			    	  
			    		 
			    		vbg.nascondiModalCaricamento();
			    		modal.close();			    	 
			    	  	document.getElementById('messPagina').innerHTML = "Si è verificato un errore.";
			      } 
			});	
		}    
	
	</script>
</body>

</html>