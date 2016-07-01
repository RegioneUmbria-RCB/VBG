<%@ Page Title="Untitled" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneDelegaATrasmettere.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneDelegaATrasmettere" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

	<div>
		<asp:Literal runat="server" ID="ltrTestoDelega" />
	</div>	

    <div>
		<asp:Literal runat="server" ID="ltrLinkDownload" />
	</div>	

	<div class="inputForm">
		<fieldset>
		<br />
		<asp:MultiView runat="server" ID="mvDelega" ActiveViewIndex="0">
			<asp:View runat="server" ID="viewNoDelega">
			<div>
				<asp:FileUpload runat="server" ID="fuDelega" Width="300px" />
			</div>
			<div class="bottoni">
				<asp:Button runat="server" ID="cmdUpload" Text="Invia file" 
					onclick="cmdUpload_Click" />			
			</div>
			</asp:View>
			<asp:View runat="server" ID="viewDelega">
				<div>
					<label for="">File caricato:</label>
					<asp:HyperLink runat="server" ID="hlDelega" Target="_blank" Text="Visualizza il file caricato" />
					
					<asp:Label runat="server" 
								ID="lblErroreDelega" 
								CssClass="errori" 
								Text="&nbsp;Attenzione, il file non è stato firmato digitalmente"/>
				</div>
				
				<div class="bottoni">
					<asp:Button runat="server" ID="cmdFirma" Text="Firma" 
							onclick="cmdFirma_Click" />

					<asp:Button runat="server" ID="cmdEliminaDelega" Text="Elimina file" 
						OnClientClick="return confirm('Eliminare la delega a trasmettere\?')" 
						onclick="cmdEliminaDelega_Click" />
				</div>
			</asp:View>
		</asp:MultiView>
		</fieldset>
	&nbsp;</div></asp:Content>