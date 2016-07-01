<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="MostraOggettoFoFirmato.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.MostraOggettoFoFirmato" Title="Download allegato firmato digitalmente" %>
<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div class="inputForm">

	<asp:Repeater runat="server" ID="rptEsitiVerificaFirma">
		<ItemTemplate>
			<fieldset>
				<legend>Certificato di firma <%# (Container.ItemIndex + 1)%></legend>
				<cc1:LabeledLabel runat="server" id="lblSoggetto" Descrizione="Soggetto" Value='<%# Bind("Soggetto") %>' />
				<cc1:LabeledLabel runat="server" id="lblEmittente" Descrizione="Emittente" Value='<%# Bind("Emittente") %>' />
				<cc1:LabeledLabel runat="server" id="lblValidoDal" Descrizione="Valido Dal" Value='<%# Bind("ValidoDal") %>'/>
				<cc1:LabeledLabel runat="server" id="lblValidoAl" Descrizione="Valido Al" Value='<%# Bind("ValidoAl") %>'/>
				<cc1:LabeledLabel runat="server" id="lblSeriale" Descrizione="Seriale" Value='<%# Bind("Seriale") %>'/>
				<cc1:LabeledLabel runat="server" id="LabeledLabel1" Descrizione="Firma valida" Value='<%# Eval("EsitoVerificaFirma").ToString() == "True" ? "Si" : "No" %>'/>
				<cc1:LabeledLabel runat="server" id="LabeledLabel2" Descrizione="Revocato" Value='<%# Eval("EsitoVerificaRevoca").ToString() == "True" ? "No" : "Si" %>'/>
			</fieldset>
			<fieldset>
				<legend>Verifica revoca certificati</legend>
				<asp:Repeater runat="server" ID="rptVerificaCertificati" DataSource='<%# Bind("DettaglioVerificaRevoca") %>'>
					<HeaderTemplate>
						<table>
							<thead>
								<tr>
									<th>Soggetto</th>
									<th>Emittente</th>
									<th>Verifica validità al tempo della firma</th>
									<th>Stato</th>
									<th>Data di revoca</th>
								</tr>
							</thead>
							<tbody>
					</HeaderTemplate>
					<ItemTemplate>
						<tr>
							<td><asp:Literal ID="Literal0" runat="server" Text='<%# Bind("Soggetto") %>' /></td>
							<td><asp:Literal ID="Literal1" runat="server" Text='<%# Bind("Emittente") %>' /></td>
							<td><asp:Literal ID="Literal2" runat="server" Text='<%# Eval("VerificaValiditaAlTempoDellaFirma").ToString() == "True" ? "Valido" : "Non valido" %>' /></td>
							<td><asp:Literal ID="Literal3" runat="server" Text='<%# Eval("Revocato").ToString() == "True" ? "Revocato" : "Valido" %>' /></td>
							<td><asp:Literal ID="Literal4" runat="server" Text='<%# Eval("DataDiRevoca") %>' /></td>
						</tr>
					</ItemTemplate>
					<FooterTemplate>
							</tbody>
						</table>
					</FooterTemplate>
				</asp:Repeater>
			</fieldset>
		
		</ItemTemplate>
	</asp:Repeater>

		<fieldset>	
			<div class="bottoni">
				<asp:Button runat="server" ID="lnkDownloadFileNoFirma"  Text="Scarica file senza firma digitale" OnClick="lnkDownloadFileNoFirma_Click" />
				<asp:Button runat="server" ID="lnkDownloadFile"  Text="Scarica file con firma digitale" OnClick="lnkDownloadFile_Click" />
				<asp:Button runat="server" ID="cmdClose"  Text="Chiudi" OnClick="cmdClose_Click" />
			</div>
		</fieldset>
	</div>
</asp:Content>
