<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" Codebehind="GestioneEndo.aspx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndo" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

	<style type="text/css" media="all">
		#alberoEndo ul {margin: 0px;padding:0px;}
		#alberoEndo li{margin-left:20px;list-style-type:none;margin-bottom:2px;}
		#alberoEndo > ul > li{margin-left:5px}
		#alberoEndo > ul > li > ul {margin-top:5px}
		#alberoEndo li > input[type=checkbox]{ }
		#alberoEndo li.famigliaEndo{ font-size: 11px;font-weight:bold;text-transform:uppercase;margin-top:15px;}
		#alberoEndo li.tipoEndo{font-size: 11px; font-style:italic; font-weight:normal;text-transform:capitalize}
		#alberoEndo li.endo{font-size: 10px; font-style:normal; font-weight:normal;text-transform:none}
		#alberoEndo li.endo > img{vertical-align:bottom}
		#alberoEndo li.endo > img:hover{cursor:pointer}
	</style>
	
	<script type="text/javascript">
		function inizializzaContenutoAccordion(elName) {
			$(elName + ' #accordion').accordion({ header: "h3", heightStyle: 'content' });
			$(elName + ' #accordion table TR:even').addClass('AlternatingItemStyle');
			$(elName + ' #accordion table TR:odd').addClass('ItemStyle');
		}

		$(function() {

			var el = $("#dettagliEndo");

			if (el.length == 0)
				$('#contenuti').append("<div id='dettagliEndo'></div>");

			// preparo il dialog per i dettagli dell'endo
			$("#dettagliEndo").dialog({
				width: 600,
				height: 500,
				title: "Dettagli dell\'endoprocedimento",
				modal: true,
				autoOpen: false,
				open: function() {
					inizializzaContenutoAccordion('#dettagliEndo');
				}
			});


			$('li.endo > img').click(function() {
				var idEndo = $(this).attr('idEndo');

				mostraDettagli(this, idEndo);
			});

		});

		function mostraDettagli(sender, id) {
			var url = '<%= ResolveClientUrl("~/Public/MostraDettagliEndo.aspx")%>?IdComune=<%=IdComune %>&Software=<%=Software%>&Id=' + id;

			var oldHtml = $(sender).attr('src');
			var clickElement = $(sender);

			clickElement.attr('src', '<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>');

			$("#dettagliEndo").load(url, null, function(responseText, textStatus, XMLHttpRequest) {
				clickElement.attr('src', oldHtml);
				$(this).dialog('open');
			});
		}
		
	</script>

	<asp:Repeater runat="server" ID="rptFamiglieEndo">
		<HeaderTemplate>
			<div id="alberoEndo">
				<ul>
		</HeaderTemplate>
		<ItemTemplate>
			<li class="famigliaEndo">
				<asp:Literal runat="server" ID="ltrFamigliaEndo" Text='<%# Eval("Descrizione") %>' />
				<asp:Repeater runat="server" ID="rptTipiEndo" DataSource='<%# Eval("TipiEndoprocedimenti") %>'>
					<HeaderTemplate>
						<ul>
					</HeaderTemplate>
					<ItemTemplate>
						<li class="tipoEndo">
							<asp:Literal runat="server" ID="ltrTipoEndo" Text='<%# Eval("Descrizione") %>' />
							
							<asp:Repeater runat="server" ID="rptEndo" DataSource='<%# Eval("Endoprocedimenti") %>'>
								<HeaderTemplate>
									<ul>
								</HeaderTemplate>
								<ItemTemplate>
									<li class="endo">
										<asp:HiddenField runat="server" ID="hidEndo" Value='<%# Eval("Codice")%>' />
										<asp:HiddenField runat="server" ID="hidPrincipale" Value='<%# Eval("Principale")%>' />
										<asp:CheckBox runat="server" ID="chkEndo" Checked='<%#Eval("Richiesto") %>' />
										<asp:Literal runat="server" ID="ltrEndo" Text='<%# Eval("Descrizione") %>' />
										<img src="../../Images/help_interventi.gif" alt="Fare click per ulteriori informazioni" idEndo='<%# DataBinder.Eval(Container.DataItem,"Codice")%>' />
									</li>
								</ItemTemplate>
								<FooterTemplate>
									</ul>
								</FooterTemplate>
							</asp:Repeater>
						</li>
					</ItemTemplate>
					<FooterTemplate>
						</ul>
					</FooterTemplate>
				</asp:Repeater>
			</li>
		</ItemTemplate>
		<FooterTemplate>
				</ul> 
			</div>
		</FooterTemplate>
	</asp:Repeater>
</asp:Content>
