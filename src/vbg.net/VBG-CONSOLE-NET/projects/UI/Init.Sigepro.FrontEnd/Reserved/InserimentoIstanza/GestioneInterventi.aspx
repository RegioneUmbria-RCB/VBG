<%@ Page Title="Untitled page" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneInterventi.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneInterventi" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<script type="text/javascript">
		function inizializzaContenutoAccordion(elName)
		{
			$(elName + ' #accordion').accordion({ header: "h3", heightStyle: 'content' });
			$(elName + ' #accordion table TR:even').addClass('AlternatingItemStyle');
			$(elName + ' #accordion table TR:odd').addClass('ItemStyle');
		}
	
		$(function(){
			var el = $("#dettagliIntervento");
			var el2 = $("#dettagliEndo");

			if (el.length == 0)
				$('#contenuti').append("<div id='dettagliIntervento'></div>");
				
			if( el2.length == 0 )
				$('#contenuti').append("<div id='dettagliEndo'></div>");
				
			// preparo il dialog per i dettagli dell'endo
			$("#dettagliEndo").dialog({
				width: 600,
				height: 500,
				title: "Dettagli dell\'endoprocedimento",
				modal: true,
				autoOpen: false,
				open: function(){
					inizializzaContenutoAccordion('#dettagliEndo');
				}
			});
			
			$('#dettagliIntervento').dialog({ 
				height: 500,
				width: 600,
				title: "Dettagli dell\'intervento",
				modal: true,
				autoOpen: false,
				open: function() {
					inizializzaContenutoAccordion('#dettagliIntervento');

					$('.linkDettagliendo').click(function(e) {
						e.preventDefault();
						
						var url = $(this).attr('href');
						
						$("#dettagliEndo").load(url, null , function(){
							$(this).dialog('open');
						});
					});
				}
			});

			setTimeout( registraHandlerAlberoInterventi , 500 );
			
		});

		function registraHandlerAlberoInterventi(){
	
			if( typeof AlberoInterventi !== 'undefined' && AlberoInterventi.instance )
			{
				AlberoInterventi.instance.dialogDettaglioInterventiOpened = wrapDescrizioneNodiPadre;
			}
			else
			{
				setTimeout( registraHandlerAlberoInterventi , 500 );
			}
		}
	
	
		function mostraDettagli( sender, id)
		{
			var url = '<%= ResolveClientUrl("~/Public/MostraDettagliIntervento.aspx")%>?IdComune=<%=IdComune %>&Software=<%=Software%>&Id=' + id;
			
			var oldHtml = $(sender).html();
			var clickElement = $(sender);

			clickElement.html('<img src=\'<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>\' />');
			$("#dettagliIntervento").html('');
			$("#dettagliIntervento").load(url, null, function(responseText, textStatus, XMLHttpRequest) {
				clickElement.html(oldHtml);
				$(this).dialog('open');
			});
		}


			function wrapDescrizioneNodiPadre() {
				$('.notePadre').each(function () {
					alert('in');
					var innerContent = this.innerHTML;
					this.innerHTML = "<fieldset class='blocco aperto'><legend>[+]</legend><div class='note'>" + innerContent + "</note></fieldset>";
					/*
					var elNomeNodo = $(this).find('.note > .nomeNodo');
					var testoNomeNodo = "<i>&nbsp;" + elNomeNodo.html() + "</i>";
					elNomeNodo.css('display', 'none');

					var elLegend = $(this).find('legend');
					elLegend.data('nomeNodo', testoNomeNodo);
					elLegend.html('[+]' + testoNomeNodo);
					elLegend.css('cursor', 'pointer');


					$(this).find('.note').css('display', 'none');
					$(this).css('padding', '5px');

					elLegend.click(function () {

						var nomeNodo = $(this).data('nomeNodo');

						var note = $(this).parent().find('.note');

						if (note.is(':visible')) {
							note.css('display', 'none');
							$(this).html('[+]' + nomeNodo);
						}
						else {
							note.css('display', 'block');
							$(this).html('[-]' + nomeNodo);
						}
					});
				});

				$('.notePadre:last').find('legend').click();
				*/
			}
	</script>
	<div class="inputForm">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="vdSelezione">

			<fieldset>
				<legend><asp:Literal runat="server" ID="ltrIntestazioneRicerca" /></legend>
				<asp:Literal runat="server" ID="ltrTestoRicerca" />
			</fieldset>
			<cc1:InterventiTreeRenderer runat="server" 
										ID="treeRenderer" 
										CssClass="treeView" 
										UrlDettagliIntervento="javascript:mostraDettagli(this,{0});"
										UrlIconaHelp="~/Images/help_interventi.gif"
										OnFogliaAlberoSelezionata="FogliaAlberoSelezionata"/>
		</asp:View>
		
		<asp:View runat="server" ID="vwDettaglio">
	
			<fieldset>
				<legend><asp:Literal runat="server" ID="ltrIntestazioneDettaglio" /></legend>
				<asp:Literal runat="server" ID="ltrTestoDettaglio" />
			</fieldset>
		
			<cc1:InterventiTreeRenderer runat="server" 
										ID="treeRendererDettaglio" 
										StartCollapsed="false" 
										CssClass="treeView"
										UrlIconaHelp="~/Images/help_interventi.gif"
										UrlDettagliIntervento="javascript:mostraDettagli(this,{0});"									
										/>
			
			<div class="bottoni">
				<asp:Button runat="server" ID="cmdSelezionaIntervento" Text="Seleziona un altro intervento" OnClick="cmdSelezionaIntervento_Click" />
			</div>
		</asp:View>
	
	</asp:MultiView>
	</div>
	
	<script type="text/javascript">
		
		function closeSubNodes()
		{
			$(this).find('ul').each(closeSubNodes)
			$(this).css("display", "none");
			
		}
	
		$(function() {
			<% if (multiView.ActiveViewIndex == 0){ %>
				$(".treeView ul").find('ul').each(closeSubNodes);
			<% } %>
			$(".treeView span").click(function() {
				$(this).parent().children('ul').toggle();
			});
		});
	</script>
</asp:Content>
