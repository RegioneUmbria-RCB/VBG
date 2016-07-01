<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" Codebehind="IstanzeInSospeso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ListaIstanzePresentate"
	Title="Istanze in sospeso" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div class="inputForm">
		<fieldset>
				<asp:GridView ID="dgIstanzePresentate" 
							  runat="server" 
							  DataKeyNames="Id" 
							  AutoGenerateColumns="False" 
							  OnSelectedIndexChanged="dgIstanzePresentate_SelectedIndexChanged" 
							  OnRowDataBound="dgIstanzePresentate_ItemDataBound"  GridLines="None">
					<Columns>
						<asp:TemplateField HeaderText="&nbsp;" ItemStyle-Width="1%">
							<ItemTemplate>
								<asp:CheckBox runat="server" ID="chkChecked" Checked="false" />
							</ItemTemplate>
						</asp:TemplateField>
						<asp:TemplateField HeaderText="Identificativo domanda">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblIdDomanda" Text='<%#Eval("Identificativodomanda") %>' /><br />
								<i>Ultima modifica: <asp:Label runat="server" ID="lblUltimaModifica" Text='<%#Eval("DataUltimaModifica", "{0:dd/MM/yyyy HH:mm}") %>' /></i>
							</ItemTemplate>
						</asp:TemplateField>
						<asp:TemplateField HeaderText="Richiedente" ItemStyle-Width="15%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblRichiedente"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>
						<asp:TemplateField HeaderText="Tipo intervento" ItemStyle-Width="45%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblTipoIntervento"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>
						<%--<asp:TemplateField HeaderText="Indirizzo" ItemStyle-Width="15%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblIndirizzo"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>

						<asp:TemplateField HeaderText="N." ItemStyle-Width="5%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblCivico"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>

						<asp:TemplateField HeaderText="Esp." ItemStyle-Width="5%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblEsponente"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>

						<asp:TemplateField HeaderText="Col." ItemStyle-Width="5%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblColore"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>



						<asp:TemplateField HeaderText="Sc." ItemStyle-Width="5%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblScala"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>

						<asp:TemplateField HeaderText="Int." ItemStyle-Width="5%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblInterno"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>

						<asp:TemplateField HeaderText="Esp.Int." ItemStyle-Width="5%">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblEsponenteInterno"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>--%>

						<asp:TemplateField ItemStyle-HorizontalAlign="Right" ItemStyle-Width="10%">
							<ItemTemplate>
								<asp:LinkButton runat="server" Text="Riprendi" CommandName="Select" CausesValidation="false" ID="Linkbutton1"></asp:LinkButton>
							</ItemTemplate>
						</asp:TemplateField>
					</Columns>
				</asp:GridView>

			<div class="bottoni">
				<asp:Button runat="server" ID="cmdDeleteRows" Text="Elimina le istanze selezionate" OnClick="cmdDeleteRows_Click" OnClientClick="return confirm('Eliminare le domande selezionate\?')" />
			</div>
		</fieldset>

		<fieldset>

			<legend><asp:label runat="server" ID="lblTitoloIstanzeNonAcquisite">Istanze presentate ma non ancora acquisite</asp:label></legend>
			<div>
					<asp:GridView runat="server" ID="dgIstanzeNonAcquisite" 
												 Visible="false" 
												 DataKeyNames="IdDomanda" 
												 AutoGenerateColumns="False" >
					<Columns>
						<asp:TemplateField HeaderText="Richiedente">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblRichiedente"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>
						<asp:TemplateField HeaderText="Tipo intervento">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblTipoIntervento"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>
						<asp:TemplateField HeaderText="Indirizzo">
							<ItemTemplate>
								<asp:Label runat="server" ID="lblIndirizzo"></asp:Label>
							</ItemTemplate>
						</asp:TemplateField>
					</Columns>
				</asp:GridView>
			</div>
		</fieldset>
	</div>
</asp:Content>
