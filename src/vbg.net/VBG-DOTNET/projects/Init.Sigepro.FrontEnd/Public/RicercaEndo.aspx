<%@ Page Title="" Language="C#" MasterPageFile="~/Public/PaginaRicercaFoMaster.Master" AutoEventWireup="True" CodeBehind="RicercaEndo.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.RicercaEndo" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

	<script type="text/javascript">

			function immagineDaTipoElemento(tipoElemento) {
				var url = '<%=ResolveClientUrl("~/Images/file.gif") %>',
					img = $('<img />');

				if (tipoElemento === 'FAM' || tipoElemento === 'CAT')
					url = '<%=ResolveClientUrl("~/Images/folder-closed.gif") %>';

				return img.attr('src', url);
			}

			window.autoCompleteCustomRenderer = function (ul, item) {

				var parts = item.label.split(' - '),
					tipoElemento = parts[0],
					testoElemento = parts[1];

				if (parts.length > 2) {
					for (var i = 2; i < parts.length; i++)
						testoElemento += " - " + parts[i];
				}

				var link = $('<a></a>')
								.append(immagineDaTipoElemento(tipoElemento))
								.append(testoElemento);

				return $('<li></li>')
					.data('item.autocomplete', item)
					.append(link)
					.appendTo(ul);
			};

	</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

	<div href="#" class="bottoneContenuti azione" id="lnkRicerca">
		<div class="titolo">Ricerca</div>
		<div class="descrizione">Testuale</div>
	</div>
	<div class="clear" />

	<cc1:AlberoInterventiJs runat="server" ID="alberoInterventi" 
							EvidenziaVociAttivabiliDaAreaRiservata="true" 
							UrlInterventiService="~/Public/WebServices/AlberoEndoJsService.asmx"
							Note=""
							OnFogliaSelezionata="EndoSelezionato" CookiePrefix="Endo"
							UrlContenutiBoxRicerca="ContenutiBoxRicercaEndo.htm" AutoCompleteCustomRenderer="autoCompleteCustomRenderer" />


</asp:Content>
