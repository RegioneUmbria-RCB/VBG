<%@ Page Title="Le mie pratiche" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master"
	AutoEventWireup="true" CodeBehind="IstanzePresentateV2.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.IstanzePresentateV2" %>

<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.VisuraV2"
	Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div class="inputForm">
		<fieldset>
			<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
				<asp:View runat="server" ID="searchView">
					<cc1:FiltriVisuraV2Control ID="filtriVisuraControl" runat="server"  ContestoVisura="FiltriVisura" ></cc1:FiltriVisuraV2Control>
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdCerca" Text="Cerca" OnClick="cmdCerca_Click" />
					</div>
				</asp:View>
			
				<asp:View runat="server" ID="listView">
					<cc1:ListaPraticheVisuraV2 ID="listaPraticheVisuraV2" runat="server" ContestoVisura="ListaVisura" OnIstanzaSelezionata="listaPraticheVisuraV2_IstanzaSelezionata">
						<EmptyDataTemplate>
							<div style="width:100%;text-align:center;font-style:italic">Non sono state trovate pratiche corrispondenti ai criteri di ricerca ammessi</div>
						</EmptyDataTemplate>

					</cc1:ListaPraticheVisuraV2>
					<div class="bottoni">
						<asp:Button runat="server" ID="Button1" Text="Chiudi" OnClick="cmdChiudi_Click" />
					</div>
				</asp:View>
			</asp:MultiView>

			
		</fieldset>
	</div>
</asp:Content>
