<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" Codebehind="GestioneMessaggi.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMessaggi"
	Title="Messaggi" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="listaView">
			<div class="inputForm">
				<fieldset>
					<div>
						<asp:Label runat="server" ID="Label1" AssociatedControlID="ddlStato" Text="Mostra i messaggi" />
						<asp:DropDownList runat="server" ID="ddlStato" AutoPostBack="true" OnSelectedIndexChanged="ddlStato_SelectedIndexChanged">
							<asp:ListItem Value="0" Text="Non letti" />
							<asp:ListItem Value="1" Text="Letti" />
							<asp:ListItem Value="" Text="Tutti" Selected="True" />
						</asp:DropDownList>
					</div>
					<asp:GridView ID="gvMessaggi" runat="server" 
												  DataKeyNames="Id" 
												  AutoGenerateColumns="false" 
												  OnSelectedIndexChanged="gvMessaggi_SelectedIndexChanged">
						<Columns>
							<asp:TemplateField HeaderText="Data" ItemStyle-Width="10%">
								<ItemTemplate>
									<asp:LinkButton runat="server" ID="lnkSeleziona1" CommandName="Select" Text='<%# DataBinder.Eval(Container.DataItem,"Data","{0:dd/MM/yyyy}") %>' Style='<%# DataBinder.Eval(Container.DataItem,"FlgLetto").ToString() == "0" ? "text-transform:none;font-weight:bold": "text-transform:none;font-weight:normal" %>' />
								</ItemTemplate>
							</asp:TemplateField>
							<asp:TemplateField HeaderText="Mittente" ItemStyle-Width="30%">
								<ItemTemplate>
									<asp:LinkButton runat="server" ID="lnkSeleziona2" CommandName="Select" Text='<%#Bind("Mittente") %>' Style='<%# DataBinder.Eval(Container.DataItem,"FlgLetto").ToString() == "0" ? "text-transform:none;font-weight:bold": "text-transform:none;font-weight:normal" %>' />
								</ItemTemplate>
							</asp:TemplateField>
							<asp:TemplateField HeaderText="Oggetto" ItemStyle-Width="60%">
								<ItemTemplate>
									<asp:LinkButton runat="server" ID="lnkSeleziona3" CommandName="Select" Text='<%#Bind("Oggetto") %>' Style='<%# DataBinder.Eval(Container.DataItem,"FlgLetto").ToString() == "0" ? "text-transform:none;font-weight:bold": "text-transform:none;font-weight:normal" %>' />
								</ItemTemplate>
							</asp:TemplateField>
						</Columns>
					</asp:GridView>
				</fieldset>
			</div>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
			<div class="inputForm">
				<fieldset>
					<legend>Dettagli messaggio</legend>
					<div>
						<asp:Label runat="server" ID="Label2" AssociatedControlID="lblDa" Text="Da" />
						<asp:Label runat="server" ID="lblDa" Text="[Da]" />
					</div>
					
					<div>
						<asp:Label runat="server" ID="Label3" AssociatedControlID="lblInviatoIl" Text="Inviato il" />
						<asp:Label runat="server" ID="lblInviatoIl" Text="[Da]" />
					</div>
					
					<div>
						<asp:Label runat="server" ID="Label4" AssociatedControlID="lblOggetto" Text="Oggetto" />
						<asp:Label runat="server" ID="lblOggetto" Text="[Da]" />
					</div>
					
					<div>
						<asp:Label runat="server" ID="lblCorpo" Text="[Da]" style="border:1px solid #000;width:50%;min-height: 400px;padding:10px;display:block" />
					</div>
					
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdVaiAIstanza" Text="Vai all'istanza" Visible="false" />
						<asp:Button runat="server" ID="cmdChiudi" Text="Chiudi" OnClick="cmdChiudi_Click" />
					</div>
				</fieldset>
			</div>
		</asp:View>
	</asp:MultiView>
</asp:Content>
