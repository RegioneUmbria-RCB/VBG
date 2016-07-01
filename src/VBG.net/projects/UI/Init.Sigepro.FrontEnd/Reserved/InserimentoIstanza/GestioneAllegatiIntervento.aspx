<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" MaintainScrollPositionOnPostback="true" AutoEventWireup="true" Codebehind="GestioneAllegatiIntervento.aspx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAllegatiIntervento" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="uc1" Src="~/Reserved/InserimentoIstanza/Allegati/GrigliaAllegati.ascx" TagName="GrigliaAllegati" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
		<div class="inputForm">

	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="listView">
		
				<script type="text/javascript">
					require(['jquery', 'app/uploadAllegati'], function ($, UploadAllegati) {

						$(function () {
							var loaderUrl = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>';
							var urlBaseNote = '<%=ResolveClientUrl("~/Reserved/InserimentoIstanza/GestioneAllegati_Note.ashx")%>';
							var idComune = '<%=IdComune %>';
							var token = '<%=UserAuthenticationResult.Token %>';
							var software = '<%=Software %>';
							var idDomanda = '<%=IdDomanda %>';
							var provenienza = 'I';

							new UploadAllegati(loaderUrl, urlBaseNote, idComune, token, software, idDomanda, provenienza);
						});
					});
				</script>
		
			<fieldset>

			<cc2:AttributiAllegato runat="server" ID="ltrLegendaAttributi" Legenda="true" />

			<asp:Repeater runat="server" ID="rptCategorie" OnItemDataBound="OnItemDataBound" >
				<ItemTemplate>
					<div style='font-weight: bold; text-transform: uppercase <%= (NumeroCategorie > 1) ? "" : ";display:none"%>'>
						<asp:Literal runat="server" ID="ltrCategoria" Text='<%# Bind("Descrizione") %>' Visible="<%# NumeroCategorie > 0 %>"/>
					</div>

					<uc1:GrigliaAllegati runat="server" 
											ID="grigliaAllegati"
											OnAllegaDocumento="OnAllegaDocumento"
											OnCompilaDocumento="OnCompilaDocumento"
											OnFirmaDocumento="OnFirmaDocumento"
											OnRimuoviDocumento="OnRimuoviDocumento" 
                                            OnErrore="ErroreGriglia"/>
				</ItemTemplate>
			</asp:Repeater>
				
				<div class="bottoni"	>
					<asp:Button runat="server" ID="cmdNuovoAllegato" Text="Nuovo allegato" OnClick="cmdNuovoAllegato_Click" />
				</div>

				<div id="noteAllegato"></div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="newView">

			<fieldset>
				<legend>Inserimento nuovo allegato</legend>
			
				<cc1:LabeledDropDownList runat="server" ID="ddlTipoAllegato" Descrizione="Categoria" Item-DataTextField="Descrizione" Item-DataValueField="Id" />
				<cc1:LabeledTextBox runat="server" ID="txtDescrizioneAllegato" Descrizione="Descrizione" item-columns="70" />
				<div>
					<asp:Label runat="server" ID="Label1" Text="File" AssociatedControlID="fuUploadNuovo" />
					<asp:FileUpload runat="server" ID="fuUploadNuovo" style="width:400px" />
				</div>

				
				
				<div class="bottoni">
					<asp:Button runat="server" ID="cmdAggiungi" Text="Allega" OnClick="cmdAggiungi_Click" />
					<asp:Button runat="server" ID="cmdCancel" Text="Annulla" OnClick="cmdCancel_Click" />
				</div>
			</fieldset>

		</asp:View>
	</asp:MultiView>
				</div>
</asp:Content>
