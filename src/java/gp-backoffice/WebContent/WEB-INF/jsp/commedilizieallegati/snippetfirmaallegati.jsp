<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

    <input type="hidden" id="allegato-contiene-firme" value="0"/>
    
	<vbg-modal id="popup-dettaglio-riga_template" >
		<div slot='body' id='popup_firme_content'>
			    
		</div>
		<div slot='footer'>
			<div class="btn btn-primary" id="closeButtonModalFirme"><fmt:message key="button.close"/></div>            
		</div>		
	</vbg-modal>	
	
	
	<script type="text/javascript">

	
	vbg.ready(() => {
	
		
			async function countaAllegati(item) {
				
	    		let id_allegato = item.getAttribute('data-id-allegato');
	
	    		const data = new URLSearchParams();
				data.append('id_allegato',id_allegato);
				
				
				const response = await fetch("${pageContext.request.contextPath}/commedilizieallegati/ajaxCountFirme.htm", {
	                method: "POST",
	                cache: "no-cache",
	                body: data
				});
				
				if (response.status === 200) {
					let json = await response.json();
					item.setAttribute("data-count-items", json.count);
					item.innerHTML = templateAllegati(json.count);
					
					document.querySelector('#allegato-contiene-firme').value = "0";
					
					if(json.count>0){
						item.style.cursor = 'pointer';
						document.querySelector('#allegato-contiene-firme').value = "1";
					}
				}else{					
					console.log( await response.text());
				}			
			} 
		
		
			function templateAllegati(count){
				
				
				if(count==0){
					return '';
				}
				let ris = '<div style="color: var(--color-success); display: inline;padding: 2px; font-weight: bold;">TEXT <i class="fas fa-info-circle" ></i></div>'
				if(count == 1){
					return ris.replace('TEXT','1 firma presente');
				}
				return ris.replace('TEXT',count + ' firme presenti');
			}
		
			async function visualizzaFirme(item){
				
				
				if(item.getAttribute('data-count-items') && parseInt(item.getAttribute('data-count-items'))>0){ 
				
					let id_allegato = item.getAttribute('data-id-allegato');
					vbg.mostraModalCaricamento();
					const data = new URLSearchParams();
					data.append('id_allegato',id_allegato);
					const response = await fetch("${pageContext.request.contextPath}/commedilizieallegati/ajaxVisualizzaFirme.htm", {
		                method: "POST",
		                cache: "no-cache",
		                body: data
					});
					
					
					if (response.status === 200) {
						let text = await response.text();
						vbg.nascondiModalCaricamento();
						document.querySelector('#popup_firme_content').innerHTML = text;	
						document.querySelector('#popup-dettaglio-riga_template').open();					
					}else{					
						vbg.nascondiModalCaricamento();
						console.log( await response.text());
					}
				}
			}  
		
		
			document.querySelectorAll('.firme_allegati_presenti').forEach((item) => {
	    		
				countaAllegati(item);
				item.addEventListener('click', () => {
	    			visualizzaFirme(item);       			
	    		});
				
	    	});
			
	
			document.querySelector('#closeButtonModalFirme').addEventListener('click', () => {
				document.querySelector('#popup-dettaglio-riga_template').close();
			});
		
		
	   }); 
		
	</script>