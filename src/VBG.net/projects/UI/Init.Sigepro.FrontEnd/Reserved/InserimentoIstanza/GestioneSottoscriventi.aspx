<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneSottoscriventi.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneSottoscriventi" Title="Untitled Page" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

	<div class="inputForm">
		<fieldset>

			<asp:GridView  runat="server" ID="gvSottoscriventi"
										  AutoGenerateColumns="false" 
										  OnRowDataBound="gvSottoscriventi_RowDataBound" 
										  DataKeyNames="CodiceFiscale" 
										  GridLines="None">
				<Columns>
					<asp:BoundField HeaderText="Nominativo" DataField="Nominativo" />
					<asp:BoundField HeaderText="In qualità di" DataField="TipoSoggetto" />
					<asp:TemplateField HeaderText="Sottoscrive" ItemStyle-HorizontalAlign="Center"> 
						<ItemTemplate>
							<asp:CheckBox runat="server" ID="chkSottoscrive" Checked='<%# Bind("Sottoscrivente") %>' OnCheckedChanged="chkSottoscrive_CheckedChanged" AutoPostBack="true"/>
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField ItemStyle-HorizontalAlign="Center" HeaderText="<span><b>Soggetto avente procura</b><br/><i>Indicare il soggetto a cui è stata rilasciata la procura speciale</i></span>">
						<ItemTemplate>
							<asp:DropDownList runat="server" ID="ddlAventeProcura" >
							</asp:DropDownList>
						</ItemTemplate>
					</asp:TemplateField>
				</Columns>
			</asp:GridView>
<br /><br />
<%--
			<asp:Panel runat="server" ID="pnlNotifica" Visible="false" CssClass="notifica">
					Attenzione! Il documento comprovante la Procura Speciale deve essere OBBLIGATORIAMENTE INSERITO nella sezione "Allegati dell'Istanza".
			</asp:Panel>--%>
			
			<br /><br />
		</fieldset>
	</div>
</asp:Content>
