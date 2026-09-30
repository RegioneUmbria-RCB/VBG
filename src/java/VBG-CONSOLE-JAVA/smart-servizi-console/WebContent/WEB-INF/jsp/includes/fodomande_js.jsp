<%@page import="it.gruppoinit.pal.gp.core.service.FoStatiDomandaService.StatoDomandaFacctEnum"%>	

			function verificaStato(obj,idDomanda, idComuneDomanda){
		
				$.ajax({
				    type: 'POST',
				    cache: false,
				    url: '../istanze/ajaxStatoDomanda.htm',
				    data: { identificativo: idDomanda, idcomunedomanda: idComuneDomanda }, 
				    success: function(data) {		    	
				  		  new statoistanza(obj, data).run();
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) {
				        // alert("Errore durante il recupero dei documenti dal server");
				    },
				    dataType: "json"
					});
			}

			function statoistanza(obj, jsonObj){
				
				this.stato = "";
				this.data = "";
				this.elementDiv = obj;
				
				var self = this;
				
				if(jsonObj){
					if(jsonObj.statoDomanda){
						this.stato = jsonObj.statoDomanda.stato;
						this.data =  jsonObj.statoDomanda.data;
					}
				}
				
				
				function statoMessaggio(){
					
					if(self.stato === '<%= StatoDomandaFacctEnum.ACCETTATA.name()%>'){
						return "<%= StatoDomandaFacctEnum.ACCETTATA.value()%>";
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.RIFIUTATA.name()%>'){
						return "<%= StatoDomandaFacctEnum.RIFIUTATA.value()%>";
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.DINIEGO.name()%>'){
						return "<%= StatoDomandaFacctEnum.DINIEGO.value()%>";
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.IN_COMPILAZIONE.name()%>'){
						return "<%= StatoDomandaFacctEnum.IN_COMPILAZIONE.value()%>";
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.INOLTRATA_SUAP.name()%>'){
						return "<%= StatoDomandaFacctEnum.INOLTRATA_SUAP.value()%>";
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.INTEGRAZIONE_INOLTRATA.name()%>'){
						return "<%= StatoDomandaFacctEnum.INTEGRAZIONE_INOLTRATA.value()%>";
					}					
					if(self.stato === '<%= StatoDomandaFacctEnum.INTEGRAZIONE_RICHIESTA.name()%>'){
						return "<%= StatoDomandaFacctEnum.INTEGRAZIONE_RICHIESTA.value()%>";
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.CONFORMAZIONE_INOLTRATA.name()%>'){
						return "<%= StatoDomandaFacctEnum.CONFORMAZIONE_INOLTRATA.value()%>";
					}					
					if(self.stato === '<%= StatoDomandaFacctEnum.CONFORMAZIONE_RICHIESTA.name()%>'){
						return "<%= StatoDomandaFacctEnum.CONFORMAZIONE_RICHIESTA.value()%>";
					}
					
					if(self.stato === '<%= StatoDomandaFacctEnum.COMUNICAZIONE_INOLTRATA.name()%>'){
						return "<%= StatoDomandaFacctEnum.COMUNICAZIONE_INOLTRATA.value()%>";
					}					
					if(self.stato === '<%= StatoDomandaFacctEnum.COMUNICAZIONE_RICHIESTA.name()%>'){
						return "<%= StatoDomandaFacctEnum.COMUNICAZIONE_RICHIESTA.value()%>";
					}
															
					if(self.stato === '<%= StatoDomandaFacctEnum.INVIATA_CON_ERRORI.name()%>'){
						return "<%= StatoDomandaFacctEnum.INVIATA_CON_ERRORI.value()%>";
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.RICEVUTA_SUAP.name()%>'){
						return "<%= StatoDomandaFacctEnum.RICEVUTA_SUAP.value()%>";
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.RIGETTO.name()%>'){
						return "<%= StatoDomandaFacctEnum.RIGETTO.value()%>";
					}						
					return "&nbsp;";
				}
				
				function semaphore(){
					
					if(self.stato === '<%= StatoDomandaFacctEnum.ACCETTATA.name()%>'){
						return '#90EE90'; // lightgreen
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.RIFIUTATA.name()%>'){
						return '#FF6347'; // tomato
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.DINIEGO.name()%>'){
						return '#FF6347'; // tomato
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.IN_COMPILAZIONE.name()%>'){
						return '#ffec80'; // yellow light
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.INOLTRATA_SUAP.name()%>'){
						return '#90EE90'; // lightgreen
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.INTEGRAZIONE_INOLTRATA.name()%>'){
						return  '#90EE90'; // lightgreen
					}					
					if(self.stato === '<%= StatoDomandaFacctEnum.INTEGRAZIONE_RICHIESTA.name()%>'){
						return '#ffec80'; // yellow light
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.CONFORMAZIONE_INOLTRATA.name()%>'){
						return  '#90EE90'; // lightgreen
					}					
					if(self.stato === '<%= StatoDomandaFacctEnum.CONFORMAZIONE_RICHIESTA.name()%>'){
						return '#ffec80'; // yellow light
					}
					
					if(self.stato === '<%= StatoDomandaFacctEnum.COMUNICAZIONE_INOLTRATA.name()%>'){
						return  '#90EE90'; // lightgreen
					}					
					if(self.stato === '<%= StatoDomandaFacctEnum.COMUNICAZIONE_RICHIESTA.name()%>'){
						return '#ffec80'; // yellow light
					}											
															
					if(self.stato === '<%= StatoDomandaFacctEnum.INVIATA_CON_ERRORI.name()%>'){
						return '#FF6347'; // tomato
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.RICEVUTA_SUAP.name()%>'){
						return  '#90EE90'; // lightgreen
					}
					if(self.stato === '<%= StatoDomandaFacctEnum.RIGETTO.name()%>'){
						return '#FF6347'; // tomato
					}						
					return "#ffffff";
				}
				
				this.run = function(){	
					
					if(this.stato!=""){
						message = statoMessaggio();
						if(this.data){
							message += " in data "+this.data;
						}
						$(this.elementDiv).html(message);
						$(this.elementDiv).parent().css("background-color",semaphore());
						$(this.elementDiv).css("color","black");
						
					}
					
				}
				
			}
			
			
			
			
			