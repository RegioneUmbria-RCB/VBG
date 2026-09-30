<%@ Page Title="Adempimenti" Language="C#" EnableViewState="false" MasterPageFile="~/AreaRiservataPopupMaster.Master" AutoEventWireup="true" CodeBehind="AlberoInterventi.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.AlberoInterventi" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<script type="text/javascript">
		
		function closeSubNodes()
		{
			$(this).find('ul').each(closeSubNodes)
			$(this).css("display", "none");
			
		}
	
	
		function inizializzaContenutoAccordion(elName)
		{
			$(elName + ' #accordion').accordion({ header: "h3", heightStyle: 'content' });
			$(elName + ' #accordion table TR:even').addClass('AlternatingItemStyle');
			$(elName + ' #accordion table TR:odd').addClass('ItemStyle');
		}

		$(function() {
			var preload_image = new Image(25,25); 
			preload_image.src='<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>'; 
			<% if ( StartCollapsed ){ %>
			$(".treeView ul").find('ul').each(closeSubNodes);
			<%} %>
			
			$(".treeView ul").find('ul').each(function(){
				var element = $(this);
				var spanNodo = $(this).parent().children('.folder')
				if(element.is(':visible') )
				{
					spanNodo.addClass('open');
				}
				else
				{
					spanNodo.removeClass('open');
				}	
			});

			$(".treeView .file").click(function(e) {
			
				if(g_dettagliOpen)
					return;
					
				e.preventDefault();
			
				var element = $(this).children('a');
				var id = $(this).attr('idIntervento');
				element.html('<img src=\'<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>\' />');
				var url = '<%= ResolveClientUrl("~/Public/MostraDettagliIntervento.aspx")%>?IdComune=<%=IdComune %>&Software=<%=Software%>&popup=true&Id=' + id;
				//mostraDettagli( element, id);
				document.location.href = url;				
			});

			$(".treeView .folder").click(function() {
				var element = $(this).parent().children('ul');
				
				element.toggle();
				
				var img = $(this).children('img');
				var imgUrl = img.attr('src');
				
				if(element.is(':visible') )
				{
					$(this).addClass('open');
					img.attr( 'src' , imgUrl.replace('folder-closed.gif','folder.gif') );
				}
				else
				{
					$(this).removeClass('open');
					img.attr( 'src' , imgUrl.replace('folder.gif','folder-closed.gif') );
				}
			});
			
		
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
				close: function(){ g_dettagliOpen = false;},
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
			$('.inputForm').css( 'margin-top',$('#intestazioneInterventi').height() + 'px' );
		});
	
		var g_dettagliOpen = false;
	
		function mostraDettagli( sender, id)
		{
			g_dettagliOpen = true;
			
			var url = '<%= ResolveClientUrl("~/Public/MostraDettagliIntervento.aspx")%>?IdComune=<%=IdComune %>&Software=<%=Software%>&Id=' + id;
			
			var oldHtml = $(sender).html();
			var clickElement = $(sender);

			clickElement.html('<img src=\'<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>\' />');

			$("#dettagliIntervento").load(url, null, function(responseText, textStatus, XMLHttpRequest) {
										clickElement.html(oldHtml);
										$(this).dialog('open');
									});
									
			return false;
		}
	</script>


	<asp:Panel runat="server" ID="pnlAlberoInterventi">
		
		<div id="intestazioneInterventi">
			In questa sezione è possibile selezionare un adempimento per reperire informazioni relative a:
			<ul>
				<li>Leggi e normative</li>
				<li>Modulistiche</li>
				<li>Dichiarazioni</li>
				<li>Allegati da presentare</li>
			</ul>
			<% if (IdAteco != -1){%>
			
				I seguenti adempimenti risultano compatibili con l'attività selezionata: <br />
				<ul>
				<li><% = VoceAteco.Titolo %></li>
				</ul>
			<%} %>
			
		</div>
		
		<div class="inputForm">
			<cc1:InterventiTreeRenderer runat="server" 
										ID="treeRenderer" 
										CssClass="treeView" 
										UrlIconaHelp="~/Images/help_interventi.gif"
										Interactive="false"
										/>
		</div>
	</asp:Panel>
	<div id="contenuti" style="display:none"></div>
</asp:Content>
